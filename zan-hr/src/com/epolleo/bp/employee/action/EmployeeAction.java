/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 *
 * 描述：<描述信息>
 * @author：dante
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.employee.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

import com.epolleo.bp.employee.bean.Employee;
import com.epolleo.bp.employee.bean.EmployeeOrgan;
import com.epolleo.bp.employee.service.EmployeeService;
import com.epolleo.bp.org.bean.OrgBean;
import com.epolleo.bp.org.service.OrgService;
import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.sysparam.service.SysParamService;
import com.epolleo.bp.util.CnToSpellHelper;
import com.epolleo.bp.util.DateUtils;

@Function()
@FunctionDir(id="bp.employee", name="员工")
public class EmployeeAction extends AbstractAction {
    private Logger logger = LoggerFactory.getLogger(EmployeeAction.class);
    @Resource
    private EmployeeService employeeService;
    @Resource
    OrgService orgService;
    @Resource
    SysParamService sysParamService;
    @Resource
    private SeqService seqService;

    /**
     * 员工基本信息查询，包含分页
     * 
     * @param context
     * @param form
     * @param employeeGen
     * @author dante
     * @date：2012-06-28 11:03:09 +0800
     */
    @Function("bp.employee.query")
    @FunctionName("员工查询")
    public void doQuery(Context context, @Params PagingForm form, @Params Employee employee) {
        HashMap<String, Object> filter = new HashMap<String, Object>();
        Object employeeName = employee.getEmployeeName();
        if (employeeName != null) {
            if (employeeName instanceof String) {
                filter.put("employeeName", "%" + employeeName + "%");
            } else {
                filter.put("employeeName", employeeName);
            }
        }
        Object employeeId = employee.getEmployeeId();
        if (employeeId != null) {
            filter.put("employeeId", employeeId);
        }
        Object email = employee.getEmail();
        if (email != null) {
            if (email instanceof String) {
                filter.put("email", "%" + email + "%");
            } else {
                filter.put("email", email);
            }
        }
        form.setFilterMap(filter);
        PagingResult<Employee> result = employeeService.findPaging(form);
        // 设置员工所属的组织机构
        buildEmployeeOrgs(result);
        context.put("json", result);

    }

    /**
     * 组织机构下员工基本信息查询，包含分页
     */
    @Function("bp.employee.orgquery")
    @FunctionName("组织员工查询")
    public void doQueryOrgEmployee(Context context, @Params PagingForm form, @Params Employee employee,
        @Param(name = "orgId") Integer orgId) {
        HashMap<String, Object> filter = new HashMap<String, Object>();

        boolean showSubOrg = sysParamService.getSysParamBoolean("BP.LoadChildrenOrgEmployees");
        if (orgId != null && orgId != -1) {
            if (showSubOrg) {
                filter.put("orgId", "%:" + orgId + ":%");
            } else {
                filter.put("orgId", "%:" + orgId + ":");
            }
        }
        Object employeeName = employee.getEmployeeName();
        if (employeeName != null) {
            filter.put("employeeName", "%" + employeeName + "%");
        }
        form.setFilterMap(filter);
        PagingResult<Employee> result = employeeService.getOrgEmployees(form);
        // 设置员工所属的组织机构
        buildEmployeeOrgs(result);
        context.put("json", result);

    }

    /**
     * 添加或者修改用户基本信息 根据传递过来用户Id查询数据库，判断是添加操作还是修改操作。
     */
    @Function("bp.employee.update")
    @FunctionName("员工更新")
    public void doUpdate(Context context, @Params Employee employee, HttpServletRequest req) {
        logger.debug("to update Id Gen : " + employee);
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            String userId = LoginUser.getCurrentUser().getUserId();
            employee.setUpdateTime(DateUtils.getCurrentDate());
            employee.setUpdateUser(userId);
            if (employee.getEmployeeId() == null && employee.getEmail() != null) {
                // find via email
                Employee old = employeeService.getEmployeeByEmail(employee.getEmail());
                if (old != null) {
                    if (old.getState() == 3) {
                        employee.setEmployeeId(old.getEmployeeId());
                        employee.setState(1);
                    } else {
                        result.put("employee", false);
                        result.put("error", "操作失败，EMail地址已存在！");
                        context.put("json", result);
                        return;
                    }
                }
            }
            // 添加
            if (employee.getEmployeeId() == null) {
                employee.setEmployeeId((int) seqService.getNewId(IdKind.EmployeeId));
                employee.setState(1);
                employee.setCreateTime(DateUtils.getCurrentDate());
                employee.setCreateUser(userId);
                employee.setPyCode(CnToSpellHelper.getPinYinHeadChar(employee.getEmployeeName()));
                employeeService.save(employee);
                result.put("employee", employee);
            }
            // 修改
            else {
                employeeService.update(employee);
                result.put("employee", employee);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("employee", false);
            result.put("error", "操作失败，请联系系统管理员！");
        }

        context.put("json", result);
    }

    /**
     * 删除用户，其实只是将用户的状态更改成已删除，未彻底删除。并将员工对应的用户信息删除
     * 
     * @param context
     * @param id
     * @author dante
     * @date：2012-06-28 11:04:54 +0800
     */
    @Function("bp.employee.delete")
    @FunctionName("员工删除")
    public void doDelete(Context context, @Param(name = "id") Integer id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        int delete = employeeService.delete(id);
        result.put("success", delete == 1);
        context.put("json", result);

    }

    /**
     * 获取员工组织机构关系，以及用户默认组织机构值
     */
    @Function("bp.employee.query")
    @FunctionName("员工查询")
    public void doQueryEmployeeOrg(Context context, @Param(name = "employeeId") String employeeId) {
        List<EmployeeOrgan> result = employeeService.getEmployeeOrgan(employeeId);
        context.put("json", result);
    }

    /**
     * 查询该员工是否拥有默认组织机构
     * 
     * @param context
     * @param employeeId
     * @param orgId
     * @author dante
     * @date：2012-06-28 11:09:32 +0800
     */
    @Function("bp.employee.query")
    public void doQueryEmployeeOrgDefault(Context context, @Param(name = "employeeId") Integer employeeId,
        @Param(name = "orgId") String orgId) {
        Employee employee = employeeService.getEmployee(employeeId);
        context.put("json", employee);
    }

    /**
     * 判断该员工是否拥有该组织机构
     * 
     * @param context
     * @param employeeId
     * @param orgId
     * @author dante
     * @date：2012-06-28 11:10:49 +0800
     */
    @Function("bp.employee.query")
    public void doQueryEmployeeOrgByUId(Context context, @Param(name = "employeeId") Integer employeeId,
        @Param(name = "orgId") int orgId) {
        EmployeeOrgan org = new EmployeeOrgan();
        org.setOrgId(orgId);
        org.setEmployeeId(employeeId);
        int result = employeeService.queryUR(org);
        context.put("json", result);
    }

    /**
     * 判断用户Id是否存在
     */
    @Function("bp.employee.query")
    public void doQueryByEmployeeId(Context context, @Param(name = "employeeId") Integer employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        int state;
        if (employee != null) {
            if ("1".equals(employee.getState()) || "2".equals(employee.getState())) {
                state = 2;
            } else {
                state = 1;
            }
        } else {
            state = 0;
        }
        context.put("json", state);
    }

    @Function("public")
    public void doQueryEmployees(Context context, @Param(name = "q") String q, @Param(name = "eid") Integer eid) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (q != null) {
            params.put("employeeName", "%" + q + "%");
        }
        List<Employee> result = employeeService.findEmployees(params);
        if (eid != null) {
            boolean inc = false;
            for (Employee e : result) {
                if (eid.equals(e.getEmployeeId())) {
                    inc = true;
                    break;
                }
            }
            if (!inc) {
                Employee e = employeeService.getEmployee(eid);
                if (e != null) {
                    result.add(e);
                }
            }
        }
        context.put("json", result);

    }

    /**
     * 查询没有账号的员工
     */
    @Function("public")
    public void doQueryNoAccountEmployees(Context context, @Param(name = "q") String q, @Param(name = "eid") Integer eid) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (q != null) {
            params.put("employeeName", "%" + q + "%");
        }
        List<Employee> result = employeeService.findNoAccountEmployee(params);
        if (eid != null) {
            boolean inc = false;
            for (Employee e : result) {
                if (eid.equals(e.getEmployeeId())) {
                    inc = true;
                    break;
                }
            }
            if (!inc) {
                Employee e = employeeService.getEmployee(eid);
                if (e != null) {
                    result.add(e);
                }
            }
        }
        context.put("json", result);
    }

    /**
     * 通过账号获取员工
     */
    @Function("bp.user.query")
    public void doQueryAccountEmployee(Context context, @Param(name = "account") String account) {
        try {
            Employee employee = employeeService.fetchByAccount(account);
            context.put("json", employee);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            context.put("json", 0);
        }
    }

    /**
     * 获取员工的组织信息，将组织名称以","分隔
     * 
     * @param result
     */
    private void buildEmployeeOrgs(PagingResult<Employee> result) {
        List<Employee> emps = result.getRows();
        if (emps != null && !emps.isEmpty()) {
            for (Employee emp : emps) {
                OrgBean o = orgService.getOrgById(emp.getDefaultOrgId());
                String orgName = o != null ? o.getOrgName() + "* " : "";
                Map<Integer, String> orgMap = employeeService.queryEmployeeOrganizations(emp.getEmployeeId());
                if (orgMap != null && orgMap.size() > 0) {
                    emp.setOrgIds(orgMap.keySet().toArray(new Integer[0]));
                    emp.setOrgNames(orgName + toString(orgMap.values().iterator()));
                } else {
                    emp.setOrgNames(orgName);
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

    /**
     * 按照所属组织机构查询员工
     * 
     * @param context
     * @param orgIdList
     */
    @Function("public")
    public void doQueryEmployeeByOrgs(Context context, @Param(name = "q") String q) {
        LoginUser user = LoginUser.getCurrentUser();

        if (user.getOrganizationId() == null) {
            context.put("json", new ArrayList<Employee>());
            return;
        }

        Map<Integer, String> orgmap = user.getOrganizations();

        Map<String, Object> params = new HashMap<String, Object>();
        ArrayList<String> fullIdList = new ArrayList<String>();
        fullIdList.add("%:" + user.getOrganizationId() + ":%");
        if (orgmap != null) {
            for (Integer id : orgmap.keySet()) {
                fullIdList.add("%:" + id + ":%");
            }
        }
        params.put("fullIdList", fullIdList);
        if (q != null && !"".equals(q.trim())) {
            params.put("name", "%" + q + "%");
        }
        List<Employee> result = employeeService.getEmployeeByOrgs(params);
        context.put("json", result);
    }
}
