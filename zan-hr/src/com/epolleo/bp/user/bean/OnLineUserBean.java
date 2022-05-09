package com.epolleo.bp.user.bean;

import java.util.Date;

public class OnLineUserBean extends LoginUser {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7773428759849892551L;

    private String organizationName;

    private String sessionId;

    private boolean loginState;

    private String remoteAddr;

    private Date birthTime;

    private String userAgent;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public Date getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(Date birthTime) {
        this.birthTime = birthTime;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public boolean isLoginState() {
        return loginState;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }

    @Override
    public String toString() {
        return "OnLineUserBean [sessionId=" + sessionId + ", state=" + getState() + ", userId=" + getUserId()
            + ", remoteAddr=" + remoteAddr + ", birthTime=" + birthTime + ", userAgent=" + userAgent + "]";
    }

}
