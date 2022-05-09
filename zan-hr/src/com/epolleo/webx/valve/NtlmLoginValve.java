package com.epolleo.webx.valve;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jcifs.Config;
import jcifs.UniAddress;
import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;
import jcifs.smb.NtlmChallenge;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbAuthException;
import jcifs.smb.SmbException;
import jcifs.smb.SmbSession;
import jcifs.util.Base64;
import jcifs.util.Hexdump;
import jcifs.util.LogStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.service.configuration.ProductionModeAware;
import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.alibaba.citrus.util.StringEscapeUtil;
import com.alibaba.citrus.util.StringUtil;

import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.user.dao.LoginUserDao;
import com.epolleo.bp.util.LoginConstant;
import com.epolleo.webx.jcaptcha.JCaptchaService;

/**
 * 
 * NtlmLoginValve对IE用户做AD集成认证，认证失败则转向登录页做手工认证。
 * <p>
 * Date: 2012-08-30,18:15:05 +0800
 * 
 * @author Song Sun
 * @version 1.0
 */
public class NtlmLoginValve extends AbstractValve implements LoginConstant, ProductionModeAware {

    static final Log log = LogFactory.getLog(NtlmLoginValve.class);

    @Resource
    LoginUserDao userDao;

    private LdapAuthentication ldapAuthentication;

    public void setInitParameters(Properties prop) {

        Config.setProperty("jcifs.smb.client.soTimeout", "1800000");
        Config.setProperty("jcifs.netbios.cachePolicy", "1200");
        Config.setProperty("jcifs.smb.lmCompatibility", "0");
        Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");

        Enumeration<?> e = prop.keys();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            if (name.startsWith("jcifs.")) {
                Config.setProperty(name, prop.getProperty(name));
            }
        }
        ntlmOnlyIE = "true".equals(prop.getProperty("ntlmOnlyIE", "false"));
        loginPage = prop.getProperty("loginPage", "/jsp/login.jsp");
        ignorePath = prop.getProperty("ignorePath", null);
        defaultDomain = Config.getProperty("jcifs.smb.client.domain");
        domainController = Config.getProperty("jcifs.http.domainController");
        if (domainController == null) {
            domainController = defaultDomain;
            loadBalance = Config.getBoolean("jcifs.http.loadBalance", true);
        }

        int level = Config.getInt("jcifs.util.loglevel", -1);
        if (level != -1) {
            LogStream.setLevel(level);
        }
        if (ldapAuthentication != null) {
            throw new IllegalStateException("ntlm and ldap co-exists!");
        }
    }

    @Resource
    private HttpServletRequest req;

    @Resource
    private HttpServletResponse resp;

    boolean productionMode;

    String defaultDomain;
    String domainController;
    boolean loadBalance;
    boolean ntlmOnlyIE;
    String loginPage;
    String ignorePath;

    boolean isIE() {
        return !ntlmOnlyIE || String.valueOf(req.getHeader("User-Agent")).contains("MSIE");
    }

    public void invoke(PipelineContext pipelineContext) throws Exception {
        TurbineRunDataInternal tr = (TurbineRunDataInternal) getTurbineRunData(req);
        if (ignorePath == null || !String.valueOf(tr.getTarget()).startsWith(ignorePath)) {

            LoginUser user = null;
            HttpSession session = req.getSession();
            Object o = session.getAttribute(LOGIN_USER_SESSION_KEY);
            if (o instanceof LoginUser) {
                user = (LoginUser) o;
            }
            String msg = req.getHeader("Authorization");
            if (user == null || user.getUserId() == null) {
                System.out.println(req.getRequestURI() + " - " + req.getSession().getId() + " - " + msg);
                String username = req.getParameter("username");
                String auth = null;
                if (msg == null && username == null && defaultDomain != null && isIE()) {
                    resp.setHeader("WWW-Authenticate", "NTLM");
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    resp.setContentLength(0);
                    resp.flushBuffer();
                    return;
                } else if (defaultDomain == null || msg == null || !msg.startsWith("NTLM ")) {
                    // if defaultDomain is null, means no ntlm auth
                    try {
                        auth = localNegotiate(defaultDomain != null);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                } else {
                    try {
                        auth = ntlmNegotiate(msg);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                    if (resp.isCommitted()) {
                        return;
                    }
                }
                if (auth == null) {
                    sendToLoginPage(null);
                } else if (auth.length() > 0) {
                    log.debug("userId -> " + auth);
                    if (user == null) {
                        user = new LoginUser();
                    }
                    user.setUserId(auth);
                    session = req.getSession(true);
                    try {
                        session.setAttribute(LOGIN_USER_SESSION_KEY, user);
                    } catch (IllegalStateException e) {
                        // session is invalid
                        sendToLoginPage(null);
                    }
                    req.setAttribute(LOGIN_USER_SESSION_KEY, user);
                }
            } else if (msg != null && msg.startsWith("NTLM ") && req.getContentLength() == 0) {
                log.debug("NTLM Re-authenticate occurred!!!");
                // http://lists.samba.org/archive/jcifs/2004-December/004459.html
                UniAddress dc = null;
                byte[] challenge = null;
                HttpSession ssn = req.getSession();
                if (loadBalance) {
                    // TODO load balanced?
                    NtlmChallenge chal = (NtlmChallenge) ssn.getAttribute("NtlmHttpChal");
                    if (chal == null) {
                        chal = SmbSession.getChallengeForDomain();
                        ssn.setAttribute("NtlmHttpChal", chal);
                    }
                    dc = chal.dc;
                    challenge = chal.challenge;
                } else {
                    dc = UniAddress.getByName(domainController, true);
                    challenge = SmbSession.getChallenge(dc);
                }
                byte[] src = Base64.decode(msg.substring(5));
                if (src[8] == 1) {
                    Type1Message type1 = new Type1Message(src);
                    Type2Message type2 = new Type2Message(type1, challenge, null);
                    msg = Base64.encode(type2.toByteArray());
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    resp.setHeader("WWW-Authenticate", "NTLM " + msg);
                    resp.setContentLength(0);
                    resp.flushBuffer();
                    return;
                }
                // resp.setStatus(HttpServletResponse.SC_LENGTH_REQUIRED);
                // resp.setContentLength(0);
                // resp.flushBuffer();
                // return;
            }
        }

        pipelineContext.invokeNext();
    }

    String localNegotiate(boolean ntlmEnabled) throws IOException, ServletException {

        boolean enableCaptcha = !"false".equals(req.getSession().getServletContext().getAttribute("bp.enableCaptcha"));

        String checkcode = req.getParameter("identify");
        if (enableCaptcha) {
            if (checkcode != null) {
                try {
                    String sessionId = req.getSession().getId();
                    Boolean b = JCaptchaService.getInstance().validateResponseForID(sessionId, checkcode);
                    if (!Boolean.TRUE.equals(b)) {
                        sendToLoginPage("badCode");
                        return null;
                    }
                } catch (Exception e) {
                    sendToLoginPage("badCode");
                    return null;
                }
            } else {
                sendToLoginPage(null);
                return null;
            }
        }

        String user = req.getParameter("username");
        String pw = req.getParameter("password");
        if (user == null || pw == null) {
            if (ntlmEnabled && isIE()) {
                resp.setHeader("WWW-Authenticate", "NTLM");
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.setContentLength(0);
                resp.flushBuffer();
                return null;
            }
            sendToLoginPage(null);
            return null;
        }
        if (enableCaptcha) {
            user = uncrypt(StringEscapeUtil.unescapeURL(user), checkcode);
            pw = uncrypt(StringEscapeUtil.unescapeURL(pw), checkcode);
        } else {
            user = uncrypt(StringEscapeUtil.unescapeURL(user), "1q2w");
            pw = uncrypt(StringEscapeUtil.unescapeURL(pw), "1q2w");
        }
        req.setAttribute("username", user);
        if (ntlmEnabled) {
            HttpSession ssn = req.getSession();
            UniAddress dc = null;
            if (loadBalance) {
                // TODO load balanced?
                NtlmChallenge chal = (NtlmChallenge) ssn.getAttribute("NtlmHttpChal");
                if (chal == null) {
                    chal = SmbSession.getChallengeForDomain();
                    ssn.setAttribute("NtlmHttpChal", chal);
                }
                dc = chal.dc;
            } else {
                dc = UniAddress.getByName(domainController, true);
            }
            // UniAddress dc = UniAddress.getByName(domainController, true);
            String u = authenticate(dc, null, user, pw);
            if (u != null) {
                ssn.removeAttribute("NtlmHttpChal");
            }
            return u;
        } else {
            return authenticate(null, null, user, pw);
        }
    }

    /*-
    function crypt(s, t) {
    var k = 0x39;
    for (i = 0; i < t.length; i++) {
    k ^= t.charCodeAt(i);
    }
    var r = "";
    for (i = 0; i < s.length; i++) {
    r = r.concat(String.fromCharCode(k ^ s.charCodeAt(i)));
    }
    return r;
    }
     */
    String uncrypt(String s, String t) {
        // ## change here when login.jsp changed
        char k = 0x39;
        for (int i = 0; i < t.length(); i++) {
            k ^= t.charAt(i);
        }
        StringBuffer r = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            r.append((char) (k ^ s.charAt(i)));
        }
        return r.toString();
    }

    /**
     * Negotiate password hashes with MSIE clients using NTLM SSP
     * 
     * @param req
     *            The servlet request
     * @param resp
     *            The servlet response
     */
    String ntlmNegotiate(String msg) throws IOException, ServletException {
        UniAddress dc = null;
        byte[] challenge = null;
        NtlmPasswordAuthentication ntlm = null;
        HttpSession ssn = req.getSession();
        if (loadBalance) {
            // TODO load balanced?
            NtlmChallenge chal = (NtlmChallenge) ssn.getAttribute("NtlmHttpChal");
            if (chal == null) {
                chal = SmbSession.getChallengeForDomain();
                ssn.setAttribute("NtlmHttpChal", chal);
            }
            dc = chal.dc;
            challenge = chal.challenge;
        } else {
            dc = UniAddress.getByName(domainController, true);
            challenge = SmbSession.getChallenge(dc);
        }

        byte[] src = Base64.decode(msg.substring(5));
        if (src[8] == 1) {
            Type1Message type1 = new Type1Message(src);
            Type2Message type2 = new Type2Message(type1, challenge, null);
            msg = Base64.encode(type2.toByteArray());
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setHeader("WWW-Authenticate", "NTLM " + msg);
            resp.setContentLength(0);
            resp.flushBuffer();
            return "";
        } else if (src[8] == 3) {
            Type3Message type3 = new Type3Message(src);
            byte[] lmResponse = type3.getLMResponse();
            if (lmResponse == null)
                lmResponse = new byte[0];
            byte[] ntResponse = type3.getNTResponse();
            if (ntResponse == null)
                ntResponse = new byte[0];
            ntlm = new NtlmPasswordAuthentication(type3.getDomain(), type3.getUser(), challenge, lmResponse, ntResponse);
        }
        if (ntlm == null) {
            sendToLoginPage(null);
            return null;
        }

        /* negotiation complete, remove the challenge object */
        ssn.removeAttribute("NtlmHttpChal");

        return authenticate(dc, ntlm, ntlm.getUsername(), null);
    }

    String authenticate(UniAddress dc, NtlmPasswordAuthentication ntlm, String u, String p) throws SmbException {
        if (dc != null && ntlm == null) {
            ntlm = new NtlmPasswordAuthentication(defaultDomain, u, p);
        }
        try {
            if (productionMode || !"superadmin".equals(u)) {
                com.epolleo.bp.user.bean.LoginUser user = userDao.getUser(u);
                if (user == null) {
                    if (dc != null) {
                        SmbSession.logon(dc, ntlm);
                        sendToLoginPage("userNotSync");
                    } else if (ldapAuthentication != null) {
                        if (ldapAuthentication.authenticate(u, p) != null) {
                            sendToLoginPage("userNotSync");
                        } else {
                            sendToLoginPage("illegalUser");
                        }
                    } else {
                        log.debug("illegalUser -> " + ntlm);
                        sendToLoginPage("illegalUser");
                    }
                    return null;
                } else {
                    if (user.getState() != 1) {
                        log.debug("illegalUserState -> " + ntlm);
                        sendToLoginPage("illegalUserState");
                        return null;
                    }
                    if (!user.isSysUser()) {
                        if (dc != null) {
                            SmbSession.logon(dc, ntlm);
                        } else if (ldapAuthentication != null) {
                            String auth = ldapAuthentication.authenticate(u, p);
                            if (auth == null) {
                                sendToLoginPage("illegalUser");
                            }
                            return auth;
                        }
                    } else if (productionMode || !"superadmin".equals(u)) {
                        if (p == null) {
                            sendToLoginPage(null);
                            return null;
                        }
                        String md5 = StringUtil.bytesToString(DigestUtils.md5(u + p));
                        if (!md5.equals(user.getPassWord())) {
                            sendToLoginPage("illegalUser");
                            return null;
                        }
                    }
                }
            }
            if (ntlm != null) {
                log.info(ntlm + " successfully authenticated against " + dc);
            } else {
                log.info(u + " successfully authenticated!");
            }
        } catch (SmbAuthException sae) {
            log.info(ntlm.getName() + ": 0x" + Hexdump.toHexString(sae.getNtStatus(), 8) + ": " + sae);

            sendToLoginPage("illegalUser");

            return null;
        } catch (Exception e) {
            log.info(u + " login failed: " + e.getMessage(), e);

            sendToLoginPage("illegalUser");

            return null;
        }
        return u;
    }

    void sendToLoginPage(String errorId) {
        TurbineRunDataInternal tr = (TurbineRunDataInternal) getTurbineRunData(req);
        tr.setTarget(loginPage);
        String sp = req.getServletPath();
        if (sp != null && sp.length() > 1 && "1".equals(req.getParameter("bp.jump"))) {
            System.out.println(req.getServletPath()+"?"+req.getQueryString());
            System.out.println(req.getServletPath());
            req.setAttribute("bp.jump", req.getServletPath()+"?"+req.getQueryString());
        }
        tr.setAction(null); // ## bug: 已转至登陆页，无须执行既定action
        if (errorId != null)
            req.setAttribute("errorId", errorId);
        String userId = req.getParameter("username");
        if (userId == null && !productionMode) {
            userId = "superadmin";
            req.setAttribute("username", userId);
        }
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    public void setLdapAuthentication(LdapAuthentication la) {
        if (defaultDomain != null) {
            throw new IllegalStateException("ntlm and ldap co-exists!");
        }
        this.ldapAuthentication = la;
    }

    public void setProductionMode(boolean productionMode) {
        this.productionMode = productionMode;
    }

    public static void main(String... args) {
        System.out.println(StringUtil.bytesToString(DigestUtils.md5("superadmin123456")));
    }
}
