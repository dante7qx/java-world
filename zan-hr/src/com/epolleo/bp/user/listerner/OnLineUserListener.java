package com.epolleo.bp.user.listerner;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.citrus.service.requestcontext.session.SessionAttributeInterceptor;
import com.alibaba.citrus.service.requestcontext.session.SessionConfig;
import com.alibaba.citrus.service.requestcontext.session.SessionLifecycleListener;
import com.alibaba.citrus.service.requestcontext.session.SessionRequestContext;
import com.alibaba.citrus.turbine.util.FunctionUtil;
import com.alibaba.citrus.webx.util.WebxUtil;
import com.epolleo.bp.employee.bean.Employee;
import com.epolleo.bp.employee.service.EmployeeService;
import com.epolleo.bp.function.service.FunctionService;
import com.epolleo.bp.org.service.OrgService;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.user.bean.OnLineUserBean;
import com.epolleo.bp.user.service.LoginUserService;
import com.epolleo.bp.user.service.OnLineUserService;
import com.epolleo.bp.util.LoginConstant;

public class OnLineUserListener implements SessionLifecycleListener, SessionAttributeInterceptor {
    final Log log = LogFactory.getLog(getClass());

    private OnLineUserService onlineService;

    private LoginUserService userService;

    private FunctionService funcService;

    private OrgService organService;

    private EmployeeService employeeService;

    public void init(SessionConfig sessionConfig) {

    }

    public Object onRead(SessionRequestContext ctx, String name, Object value) {
        if (name.equals(LoginConstant.LOGIN_USER_SESSION_KEY)) {
            if (value instanceof LoginUser) {
                LoginUser user = (LoginUser) value;
                HttpSession hs = ctx.getRequest().getSession(false);
                if (hs != null && user.getUserId() != null) {
                    String sid = hs.getId();
                    onlineService = getOnLineUserService(ctx);
                    if (!onlineService.isUserOnline(sid)) {
                        hs.invalidate();
                        return null;
                    }
                }
            }
        }
        return value;
    }

    public Object onWrite(SessionRequestContext ctx, String name, Object value) {
        if (name.equals(LoginConstant.LOGIN_USER_SESSION_KEY) && value instanceof LoginUser) {
            LoginUser user = (LoginUser) value;
            HttpSession session = ctx.getRequest().getSession(false);
            if (user.getUserId() != null && session != null) {
                try {
                    HttpServletRequest req = ctx.getRequest();
                    // 保存session
                    OnLineUserBean onlineUser = new OnLineUserBean();
                    onlineUser.setSessionId(session.getId());

                    onlineUser.setUserId(user.getUserId());
                    onlineUser.setBirthTime(new Date(session.getCreationTime()));

                    String userAgent = req.getHeader("User-Agent");
                    if (userAgent.length() > 100) {
                        userAgent = userAgent.substring(0, 100);
                    }
                    onlineUser.setUserAgent(userAgent);
                    onlineUser.setLoginState(true);
                    String ip = req.getRemoteAddr();
                    onlineUser.setRemoteAddr(ip);
                    log.info("Session open -> " + onlineUser);
                    onlineService = getOnLineUserService(ctx);
                    onlineService.saveOnLineUser(onlineUser);
                    setUserAttr(user, ctx);
                    if (user.getFunctions() != null)
                        session.setAttribute("webx.UserFunctions", user.getFunctions().keySet());
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return value;
    }

    public void sessionCreated(SessionRequestContext ctx, HttpSession session) {
    }

    public void sessionInvalidated(SessionRequestContext ctx, HttpSession session) {
        // ## getOnLineUserService(ctx).deleteOnLineUserBean(session.getId());
    }

    public void sessionVisited(SessionRequestContext ctx, HttpSession session) {
    }

    OnLineUserService getOnLineUserService(SessionRequestContext ctx) {
        HttpServletRequest req = ctx.getRequest();
        if (null == onlineService) {
            ApplicationContext ac = WebxUtil.getCurrentComponent(req).getApplicationContext();
            onlineService = (OnLineUserService) ac.getBean("onLineUserService");
        }

        return onlineService;
    }

    LoginUserService getLoginUserService(SessionRequestContext ctx) {
        HttpServletRequest req = ctx.getRequest();
        if (null == userService) {
            ApplicationContext ac = WebxUtil.getCurrentComponent(req).getApplicationContext();
            userService = (LoginUserService) ac.getBean("loginUserService");
        }

        return userService;
    }

    EmployeeService getEmployeeService(SessionRequestContext ctx) {
        HttpServletRequest req = ctx.getRequest();
        if (null == employeeService) {
            ApplicationContext ac = WebxUtil.getCurrentComponent(req).getApplicationContext();
            employeeService = (EmployeeService) ac.getBean("employeeService");
        }

        return employeeService;
    }

    FunctionService getFunctionService(SessionRequestContext ctx) {
        HttpServletRequest req = ctx.getRequest();
        if (null == funcService) {
            ApplicationContext ac = WebxUtil.getCurrentComponent(req).getApplicationContext();
            funcService = (FunctionService) ac.getBean("functionService");
        }

        return funcService;
    }

    OrgService getOrgService(SessionRequestContext ctx) {
        HttpServletRequest req = ctx.getRequest();
        if (null == organService) {
            ApplicationContext ac = WebxUtil.getCurrentComponent(req).getApplicationContext();
            organService = (OrgService) ac.getBean("orgService");
        }

        return organService;
    }

    LoginUser setUserAttr(LoginUser user, SessionRequestContext ctx) {
        // 根据userId查询系统用户对象
        userService = getLoginUserService(ctx);
        com.epolleo.bp.user.bean.LoginUser loginUser = userService.getLoginUser(user.getUserId());
        if (null != loginUser) {
            user.setState(loginUser.getState());
            user.setUserId(loginUser.getUserId());
            user.setUserName(loginUser.getUserName());
            user.setSysUser(loginUser.isSysUser());
            user.setEmployeeId(loginUser.getEmployeeId());

            // 查询用户对应的组织机构信息
            funcService = getFunctionService(ctx);
            HashMap<String, String> map = new HashMap<String, String>();
            if ("superadmin".equals(user.getUserId())) {
                map.putAll(FunctionUtil.allFunctionSet());
            } else {
                map.putAll(funcService.queryUserFunctions(user.getUserId()));
            }
            map.put("public", "");
            user.setFunctions(map);
            if (user.getEmployeeId() != null) {
                employeeService = getEmployeeService(ctx);
                Employee e = employeeService.getEmployee(user.getEmployeeId());
                if (e != null) {
                    user.setAddress(e.getAddress());
                    user.setAliasName(e.getAliasName());
                    user.setEmail(e.getEmail());
                    user.setMobile(e.getMobileNumber());
                    user.setNickName(e.getNickName());
                    user.setPhone(e.getPhone());
                    user.setOrganizationId(e.getDefaultOrgId());
                    user.setOrganizations(employeeService.queryEmployeeOrganizations(user.getEmployeeId()));
                    user.setOrgCodes(employeeService.getOrgCodesByEmployeeId(user.getEmployeeId()));
                }
            }

        }
        return user;
    }
}
