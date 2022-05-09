package com.epolleo.webx.valve;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
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
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.util.StringUtil;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.user.dao.LoginUserDao;
import com.epolleo.bp.util.LoginConstant;

@Deprecated
public class NtlmAuthorizationValve extends AbstractValve implements
    LoginConstant, ProductionModeAware {

    static final Log log = LogFactory.getLog(NtlmAuthorizationValve.class);

    @Resource
    LoginUserDao userDao;

    public void setInitParameters(Properties prop) {
        Config.setProperty("jcifs.smb.client.soTimeout", "1800000");
        Config.setProperty("jcifs.netbios.cachePolicy", "1200");
        /*
         * The Filter can only work with NTLMv1 as it uses a man-in-the-middle
         * techinque that NTLMv2 specifically thwarts. A real NTLM Filter would
         * need to do a NETLOGON RPC that JCIFS will likely never implement
         * because it requires a lot of extra crypto not used by CIFS.
         */
        Config.setProperty("jcifs.smb.lmCompatibility", "0");
        Config.setProperty("jcifs.smb.client.useExtendedSecurity", "false");

        Enumeration<?> e = prop.keys();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            if (name.startsWith("jcifs.")) {
                Config.setProperty(name, prop.getProperty(name));
            }
        }
        defaultDomain = Config.getProperty("jcifs.smb.client.domain");
        domainController = Config.getProperty("jcifs.http.domainController");
        if (domainController == null) {
            domainController = defaultDomain;
            loadBalance = Config.getBoolean("jcifs.http.loadBalance", true);
        }
        realm = Config.getProperty("jcifs.http.basicRealm");
        if (realm == null)
            realm = "jCIFS";

        int level = Config.getInt("jcifs.util.loglevel", -1);
        if (level != -1) {
            LogStream.setLevel(level);
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
    String realm;

    public void invoke(PipelineContext pipelineContext) throws Exception {
        LoginUser user = null;
        HttpSession session = req.getSession(false);
        boolean ie = String.valueOf(req.getHeader("User-Agent")).contains(
            "MSIE");
        if (session != null) {
            Object o = session.getAttribute(LOGIN_USER_SESSION_KEY);
            if (o instanceof LoginUser) {
                user = (LoginUser) o;
            }
        }
        if (user == null || user.getUserId() == null) {
            String msg = req.getHeader("Authorization");
            if (msg == null) {
                if (ie) {
                    resp.setHeader("WWW-Authenticate", "NTLM");
                } else {
                    resp.setHeader("WWW-Authenticate", "Basic realm=\"" + realm
                        + "\"");
                }
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.setContentLength(0);
                resp.flushBuffer();
                return;
            }
            NtlmPasswordAuthentication ntlm = null;
            try {
                // XXX
                System.out.println(req.getRequestURI() + " - " + msg);
                if (msg != null
                    && (msg.startsWith("NTLM ") || msg.startsWith("Basic "))) {
                    ntlm = negotiate(req, resp, msg);
                } else {
                    HttpSession ssn = req.getSession(false);
                    if (ssn == null
                        || (ntlm = (NtlmPasswordAuthentication) ssn
                            .getAttribute("NtlmHttpAuth")) == null) {
                        resp.setHeader("WWW-Authenticate", "NTLM");
                        resp.addHeader("WWW-Authenticate", "Basic realm=\""
                            + realm + "\"");
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.setContentLength(0);
                        resp.flushBuffer();
                        ntlm = null;
                    }
                }

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            if (ntlm == null) {
                return;
            }
            log.debug("ntlm -> " + ntlm);

            if (user == null) {
                user = new LoginUser();
            }
            user.setUserId(ntlm.getUsername());
            session = req.getSession(true);
            session.setAttribute(LOGIN_USER_SESSION_KEY, user);
        }

        pipelineContext.invokeNext();
    }

    /**
     * Negotiate password hashes with MSIE clients using NTLM SSP
     * 
     * @param req
     *            The servlet request
     * @param resp
     *            The servlet response
     * @return True if the negotiation is complete, otherwise false
     */
    protected NtlmPasswordAuthentication negotiate(HttpServletRequest req,
        HttpServletResponse resp, String msg) throws UnknownHostException,
        SmbException, IOException, ServletException,
        UnsupportedEncodingException {
        UniAddress dc = null;
        NtlmPasswordAuthentication ntlm;
        if (msg.startsWith("NTLM ")) {
            dc = UniAddress.getByName(domainController, true);
            byte[] challenge = SmbSession.getChallenge(dc);

            HttpSession ssn = req.getSession();
            if (loadBalance) {
                NtlmChallenge chal = (NtlmChallenge) ssn
                    .getAttribute("NtlmHttpChal");
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

            if ((ntlm = authenticate(req, resp, challenge)) == null) {
                return null;
            }
            /* negotiation complete, remove the challenge object */
            ssn.removeAttribute("NtlmHttpChal");
        } else {
            String auth = new String(Base64.decode(msg.substring(6)),
                "US-ASCII");
            int index = auth.indexOf(':');
            String user = (index != -1) ? auth.substring(0, index) : auth;
            String password = (index != -1) ? auth.substring(index + 1) : "";
            index = user.indexOf('\\');
            if (index == -1)
                index = user.indexOf('/');
            String domain = (index != -1) ? user.substring(0, index)
                    : defaultDomain;
            user = (index != -1) ? user.substring(index + 1) : user;
            ntlm = new NtlmPasswordAuthentication(domain, user, password);
            dc = UniAddress.getByName(domainController, true);
        }

        String ctx = req.getContextPath();
        if (ctx == null || "/".equals(ctx)) {
            ctx = "";
        }

        TurbineRunData tr = getTurbineRunData(req);
        try {
            if (productionMode || !"superadmin".equals(ntlm.getUsername())) {
                com.epolleo.bp.user.bean.LoginUser user = userDao.getUser(ntlm
                    .getUsername());
                if (user == null) {
                    SmbSession.logon(dc, ntlm);
                    log.debug("userNeedSync -> " + ntlm);
                    tr.setRedirectLocation(ctx + "/jsp/userNeedSync.html");
                    return null;
                } else {
                    if (user.getState() != 1) {
                        log.debug("illegalUser -> " + ntlm);
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        tr.setRedirectLocation(ctx + "/jsp/illegalUser.html");
                        return null;
                    }
                    if (!user.isSysUser()) {
                        SmbSession.logon(dc, ntlm);
                    } else if (productionMode
                        || !"superadmin".equals(ntlm.getUsername())) {
                        if (ntlm.getPassword() == null) {
                            resp.setHeader("WWW-Authenticate", "Basic realm=\""
                                + realm + "\"");
                            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            resp.setContentLength(0);
                            resp.flushBuffer();
                            return null;
                        }
                        String md5 = StringUtil.bytesToString(DigestUtils
                            .md5(ntlm.getUsername() + ntlm.getPassword()));
                        if (!md5.equals(user.getPassWord())) {
                            resp.setHeader("WWW-Authenticate", "Basic realm=\""
                                + realm + "\"");
                            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            resp.setContentLength(0);
                            resp.flushBuffer();
                            return null;
                        }
                    }
                }
            }

            log.info(ntlm + " successfully authenticated against " + dc);
        } catch (SmbAuthException sae) {
            log.info(ntlm.getName() + ": 0x"
                + Hexdump.toHexString(sae.getNtStatus(), 8) + ": " + sae);

            if (sae.getNtStatus() == SmbAuthException.NT_STATUS_ACCESS_VIOLATION) {
                HttpSession ssn = req.getSession(false);
                if (ssn != null) {
                    ssn.removeAttribute("NtlmHttpAuth");
                }
            }
            resp.setHeader("WWW-Authenticate", "Basic realm=\"" + realm + "\"");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentLength(0); /* Marcel Feb-15-2005 */
            resp.flushBuffer();
            return null;
        }
        req.getSession().setAttribute("NtlmHttpAuth", ntlm);
        return ntlm;
    }

    public static NtlmPasswordAuthentication authenticate(
        HttpServletRequest req, HttpServletResponse resp, byte[] challenge)
        throws IOException, ServletException {
        String msg = req.getHeader("Authorization");
        if (msg != null && msg.startsWith("NTLM ")) {
            byte[] src = Base64.decode(msg.substring(5));
            if (src[8] == 1) {
                Type1Message type1 = new Type1Message(src);
                Type2Message type2 = new Type2Message(type1, challenge, null);
                msg = Base64.encode(type2.toByteArray());
                resp.setHeader("WWW-Authenticate", "NTLM " + msg);
            } else if (src[8] == 3) {
                Type3Message type3 = new Type3Message(src);
                byte[] lmResponse = type3.getLMResponse();
                if (lmResponse == null)
                    lmResponse = new byte[0];
                byte[] ntResponse = type3.getNTResponse();
                if (ntResponse == null)
                    ntResponse = new byte[0];
                return new NtlmPasswordAuthentication(type3.getDomain(),
                    type3.getUser(), challenge, lmResponse, ntResponse);
            }
        } else {
            resp.setHeader("WWW-Authenticate", "NTLM");
        }
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setContentLength(0);
        resp.flushBuffer();
        return null;
    }

    public void setProductionMode(boolean productionMode) {
        this.productionMode = productionMode;
    }

}
