package com.epolleo.webx.valve;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import javax.annotation.Resource;

import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.util.LoginConstant;

@Deprecated
public class BasicAuthorizationValve extends AbstractValve implements
    LoginConstant {

    @Resource
    private HttpServletRequest req;

    @Resource
    private HttpServletResponse resp;

    public void invoke(PipelineContext pipelineContext) throws Exception {
        LoginUser user = null;
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object o = session.getAttribute(LOGIN_USER_SESSION_KEY);
            if (o instanceof LoginUser) {
                user = (LoginUser) o;
            }
        }
        if (user == null || user.getUserId() == null) {
            String authorization = req.getHeader("Authorization");
            if (authorization != null && (authorization.startsWith("Basic "))) {
                authorization = authorization.substring(6);
                byte[] bytes = null;
                try {
                    bytes = Base64.decodeBase64(authorization
                        .getBytes("ISO-8859-1"));
                } catch (UnsupportedEncodingException e) {
                }
                if (bytes != null) {
                    try {
                        String s = new String(bytes, "ISO-8859-1");
                        if (s.indexOf(":") > 0) {
                            s = s.substring(0, s.indexOf(":"));
                            if (user == null) {
                                user = new LoginUser();
                            }
                            user.setUserId(s);
                            session = req.getSession(true);
                            session.setAttribute(LOGIN_USER_SESSION_KEY, user);
                        }
                    } catch (UnsupportedEncodingException e) {
                    }
                }
            }

        }
        if (user == null || user.getUserId() == null) {
            resp.setHeader("WWW-Authenticate",
                "Basic realm=\"Authorization Needed!\"");
            resp.sendError(401, "Unauthorized");
            return;
        }

        pipelineContext.invokeNext();
    }

}
