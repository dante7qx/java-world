package com.epolleo.bp.util;

import static com.alibaba.citrus.util.Assert.assertNotNull;
import static com.alibaba.citrus.util.StringUtil.trimToNull;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.citrus.service.uribroker.URIBrokerService;
import com.alibaba.citrus.service.uribroker.uri.URIBroker;
import com.alibaba.citrus.springext.support.BeanSupport;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.pipeline.valve.PageAuthorizationValve.Callback;
import com.epolleo.bp.pub.LoginUser;

public class LoginUserAuth extends BeanSupport implements
    Callback<LoginUserAuth.Status>, LoginConstant {
    private static final Log log = LogFactory.getLog(LoginUserAuth.class);

    @Resource
    private URIBrokerService uriBrokerService;

    @Resource
    private HttpServletRequest request;

    private String sessionKey;
    private String brokerId;
    private String returnKey;

    public void setSessionKey(String sessionKey) {
        this.sessionKey = trimToNull(sessionKey);
    }

    public void setRedirectURI(String brokerId) {
        this.brokerId = trimToNull(brokerId);
    }

    public void setReturnKey(String returnKey) {
        this.returnKey = trimToNull(returnKey);
    }

    @Override
    public void init() {
        assertNotNull(uriBrokerService, "could not get URIBrokerService");

        if (sessionKey == null) {
            sessionKey = LOGIN_USER_SESSION_KEY;
        }

        if (brokerId == null) {
            brokerId = LOGIN_LINK;
        }

        if (returnKey == null) {
            returnKey = LOGIN_RETURN_KEY;
        }
    }

    public Status onStart(TurbineRunData rundata) {
        HttpSession session = request.getSession();
        LoginUser user = null;

        try {
            user = (LoginUser) session.getAttribute(sessionKey);
        } catch (Exception e) {
            log.warn("Failed to read LoginUser from session: " + sessionKey);
        }

        if (user == null) {
            // 创建匿名用户
            user = new LoginUser();
            try {
                session.setAttribute(sessionKey, user);
            } catch (Exception e) {
            }
        }

        // 将user设置到rundata中，以便其它程序使用。
        LoginUser.setCurrentUser(user);

        return new Status(rundata, user);
    }

    public String getUserName(Status status) {
        return status.user.getUserId();
    }

    public String[] getRoleNames(Status status) {
        return status.user.getRoles();
    }

    public String[] getActions(Status status) {
        return null;
    }

    public void onAllow(Status status) throws Exception {
    }

    public void onDeny(Status status) throws Exception {
        HttpServletRequest request = status.rundata.getRequest();
        HttpServletResponse response = status.rundata.getResponse();
        URIBroker redirectURI = uriBrokerService.getURIBroker(brokerId);

        assertNotNull(redirectURI, "no URI broker found: %s", brokerId);

        StringBuffer buf = request.getRequestURL();
        String queryString = trimToNull(request.getQueryString());

        if (queryString != null) {
            buf.append('?').append(queryString);
        }

        String returnURL = buf.toString();

        response.sendRedirect(redirectURI.addQueryData(returnKey, returnURL)
            .render());
    }

    static class Status {
        private final TurbineRunData rundata;
        private LoginUser user;

        public Status(TurbineRunData rundata, LoginUser user) {
            this.rundata = rundata;
            this.user = user;
        }
    }
}
