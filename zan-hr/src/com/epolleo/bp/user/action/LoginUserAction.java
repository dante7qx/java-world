/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 *
 * 描述：<描述信息>
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.user.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;
import com.alibaba.citrus.util.StringUtil;

import com.epolleo.bp.employee.bean.Employee;
import com.epolleo.bp.employee.service.EmployeeService;
import com.epolleo.bp.org.bean.OrgBean;
import com.epolleo.bp.org.service.OrgService;
import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.user.bean.LoginUser;
import com.epolleo.bp.user.bean.UserRole;
import com.epolleo.bp.user.service.LoginUserService;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.bp.util.LoginConstant;

@Function()
@FunctionDir(id="bp.user", name="用户")
public class LoginUserAction extends AbstractAction {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private LoginUserService loginUserService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    OrgService orgService;

    /**
     * 用户基本信息查询，包含分页
     */
    @Function("bp.user.query")
    @FunctionName("用户查询")
    public void doQuery(Context context, @Params PagingForm form,
        @Params LoginUser userGen) {
        HashMap<String, Object> filter = new HashMap<String, Object>();
        Object userName = userGen.getUserName();
        if (userName != null) {
            filter.put("userName", "%" + userName + "%");
        }
        Object userId = userGen.getUserId();
        if (userId != null) {
            filter.put("userId", "%" + userId + "%");
        }
        Object employeeId = userGen.getEmployeeId();
        if (employeeId != null) {
            filter.put("employeeId", employeeId);
        }
        Object orgId = userGen.getOrgId();
        if (orgId != null && ((Integer) orgId != -1)) {
            filter.put("orgId", orgId);
        }
        form.setFilterMap(filter);
        PagingResult<LoginUser> result = loginUserService.findPaging(form);
        buildEmployeeOrgs(result);
        context.put("json", result);
    }

    /**
     * 添加或者修改用户基本信息 根据传递过来用户Id查询数据库，判断是添加操作还是修改操作。
     */
    @Function("bp.user.update")
    @FunctionName("用户更新")
    public void doUpdate(Context context, @Params LoginUser bean,
        HttpServletRequest req, @Param(name = "roleList") Set<Integer> roleList) {
        roleList.remove(-1);
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            com.epolleo.bp.pub.LoginUser loginUser = (com.epolleo.bp.pub.LoginUser) req
                .getSession()
                .getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
            LoginUser user = loginUserService.getLoginUser(bean.getUserId());
            bean.setUpdateTime(DateUtils.getCurrentDate());
            bean.setUpdateUser(loginUser.getUserId());
            if (bean.getEmployeeId() != null) {
                Employee employee = employeeService.getEmployee(bean
                    .getEmployeeId());
                if (employee != null) {
                    bean.setEmployeeId(employee.getEmployeeId());
                    bean.setUserName(employee.getEmployeeName());
                }
            }
            if (StringUtils.isEmpty(bean.getUserName())) {
                bean.setUserName(bean.getUserId());
            }
            // 添加
            if (user == null) {
                bean.setPassWord(StringUtil.bytesToString(DigestUtils.md5(bean
                    .getUserId() + bean.getPassWord())));
                // 不是系统用户
                if (!loginUser.isSysUser()) {
                    bean.setSysUser(false);
                }
                bean.setPasswordTime(DateUtils.getCurrentDate());
                bean.setState(1);
                bean.setCreateTime(DateUtils.getCurrentDate());
                bean.setCreateUser(loginUser.getUserId());
                loginUserService.save(bean, roleList);
                result.put("userGen", bean);
            }
            // 修改
            else {
                if ("1".equals(bean.getIsUserId())) {
                    bean.setState(1);
                }
                // 验证密码是否修改。
                if (user.getPassWord().equals(bean.getPassWord())) {
                    bean.setPassWord(user.getPassWord());
                    bean.setPasswordTime(user.getPasswordTime());
                } else {
                    bean.setPassWord(StringUtil.bytesToString(DigestUtils
                        .md5(bean.getUserId() + bean.getPassWord())));
                    bean.setPasswordTime(DateUtils.getCurrentDate());
                }
                if (!loginUser.isSysUser()) { // 不是系统用户
                    bean.setSysUser(user.isSysUser());
                }
                if (StringUtils.isEmpty(bean.getUserName())) {
                    bean.setUserName(bean.getUserId());
                }
                loginUserService.update(bean, roleList);
                result.put("userGen", bean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("userGen", false);
        }

        context.put("json", result);
    }

    /**
     * 删除用户，其实只是将用户的状态更改成已删除，未彻底删除
     */
    @Function("bp.user.delete")
    @FunctionName("用户删除")
    public void doDelete(Context context, @Param(name = "id") String id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        int delete = loginUserService.delete(id);
        result.put("success", delete == 1);
        context.put("json", result);

    }

    /**
     * 通过用户Id查询该用户属于那些角色 UserRole中的isCheck用来判断是否属于
     */
    @Function("bp.user.query")
    public void doQueryUserRole(Context context,
        @Param(name = "userId") String userId) {
        List<UserRole> result = loginUserService.getUserRole(userId);
        context.put("json", result);
    }

    @Function("bp.user.query")
    public void doGetUserRoleIdList(Context context,
        @Param(name = "id") String userId) {
        List<Integer> result = loginUserService.getUserRoleIdList(userId);
        context.put("json", result);
    }

    /**
     * 判断用户Id是否存在
     */
    @Function("bp.user.query")
    public void doQueryByUserId(Context context,
        @Param(name = "userId") String userId) {
        LoginUser user = loginUserService.getLoginUser(userId);
        int a;
        if (user != null) {
            if (user.getState() == 1 || user.getState() == 2) {
                a = 2;
            } else {
                a = 1;
            }
        } else {
            a = 0;
        }
        context.put("json", a);
    }

    /**
     * 查询登录用户
     */
    @Function("bp.user.query")
    public void doQueryLoginUser(Context context, HttpServletRequest req) {
        com.epolleo.bp.pub.LoginUser loginUser = (com.epolleo.bp.pub.LoginUser) req
            .getSession().getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
        context.put("json", loginUser);
    }

    /**
     * 查询所选员工的账号信息
     */
    @Function("bp.user.query")
    public void doQueryUserByEmployee(Context context,
        @Param(name = "employeeId") Integer employeeId, HttpServletRequest req) {
        LoginUser user = loginUserService.getUserByEmployeeId(employeeId);
        if (user == null) {
            com.epolleo.bp.pub.LoginUser loginUser = (com.epolleo.bp.pub.LoginUser) req
                .getSession()
                .getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
            user = new LoginUser();
            user.setSysUser(loginUser.isSysUser());
        }
        context.put("json", user);
    }
    
    /**
     * 查询所选角色下的账号信息
     */
    @Function("bp.user.query")
    public void doQueryRoleAccount(Context context, @Params PagingForm form, @Param(name = "roleId") Integer roleId) {
        form.addFilter("roleId", roleId);
        PagingResult<LoginUser> result = loginUserService.findRoleAccountPaging(form);
        buildEmployeeOrgs(result);
        context.put("json", result);
    }

    /**
     * 获取员工的组织信息，将组织名称以","分隔
     */
    private void buildEmployeeOrgs(PagingResult<LoginUser> result) {
        List<LoginUser> users = result.getRows();
        if (users != null && !users.isEmpty()) {
            for (LoginUser user : users) {
                if (user.getEmployeeId() == null)
                    continue;
                Employee emp = employeeService
                    .getEmployee(user.getEmployeeId());

                OrgBean o = orgService.getOrgById(emp.getDefaultOrgId());
                String orgName = o != null ? o.getOrgName() + "* " : "";

                Map<Integer, String> orgMap = employeeService
                    .queryEmployeeOrganizations(user.getEmployeeId());
                if (orgMap != null && orgMap.size() > 0) {
                    user.setOrgIds(toString(orgMap.keySet().iterator()));
                    user.setOrgNames(orgName
                        + toString(orgMap.values().iterator()));
                } else {
                    user.setOrgNames(orgName);
                }
            }
        }
    }

    private String toString(Iterator<?> i) {
        if (!i.hasNext())
            return "";

        StringBuilder sb = new StringBuilder();
        for (;;) {
            Object e = i.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!i.hasNext())
                return sb.toString();
            sb.append(", ");
        }
    }

    public void doQueryMobileById(Context context,
        @Param(name = "userId") String userId) {
        String mobile = loginUserService.queryMobileById(userId);
        if (StringUtils.isNotBlank(mobile)) {
            context.put("json", mobile);
        } else {
            context.put("json", null);
        }
    }

    public void doQueryByRoleId(Context context) {
        List<LoginUser> users = loginUserService.queryByRoleId(301);
        context.put("json", users);
    }
}
