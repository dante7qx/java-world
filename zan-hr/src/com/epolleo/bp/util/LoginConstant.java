package com.epolleo.bp.util;

/**
 * Login WEB层的常量。
 */
public interface LoginConstant {
    /** 在session中保存login用户对象的key。 */
    String LOGIN_USER_SESSION_KEY = "BP.LoginUser";

    /** Login页面返回URL的key。 */
    String LOGIN_RETURN_KEY = "return";

    /** 如果未指定return，登录以后就跳到该URL。 */
    String LOGIN_RETURN_DEFAULT_LINK = "loginHomeLink";

    /** 登录URL的名字。 */
    String LOGIN_LINK = "loginLink";

}
