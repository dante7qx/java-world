/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.employee.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.epolleo.bp.employee.bean.Employee;
import com.epolleo.bp.employee.bean.EmployeeOrgan;
import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

@SuppressWarnings("unchecked")
public class EmployeeDao extends AbstractDao {

    public PagingResult<Employee> findPaging(PagingForm pagingParam) {
        String sort = pagingParam.getSort();
        String order = pagingParam.getOrder();
        if (sort != null) {
            checkName(sort);
        }
        if (order != null) {
            checkName(order);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("sort", pagingParam.getSort());
        params.put("order", pagingParam.getOrder());
        params.putAll(pagingParam.getFilter());
        List<Employee> page = new ArrayList<Employee>();
        int total = getSqlMapClientTemplate().queryForPage(page, "selectEmployee", params, pagingParam.getSkip(),
            pagingParam.getPageSize());
        PagingResult<Employee> result = new PagingResult<Employee>(page, total);
        return result;
    }

    public List<Employee> findEmployees(Map<String, Object> params) {
        List<Employee> employees = (List<Employee>) getSqlMapClientTemplate().queryForList("selectEmployee", params, 0,
            100);
        return employees;
    }

    public List<Employee> findNoAccountEmployee(Map<String, Object> params) {
        List<Employee> employees = (List<Employee>) getSqlMapClientTemplate().queryForList("selectNoAccountEmployee",
            params, 0, 100);
        return employees;
    }

    public int update(Employee employee) {
        getSqlMapClientTemplate().update("updateEmployee", employee, 1);
        return 1;
    }

    /**
     * 保存员工基本信息
     */
    public Employee save(Employee employee) {
        return (Employee) getSqlMapClientTemplate().insert("insertEmployee", employee);
    }

    public int delete(Integer id) {
        getSqlMapClientTemplate().delete("deleteUserByEmployeeId", id);
        getSqlMapClientTemplate().delete("deleteEmployee", id, 1);
        return 1;
    }

    public int queryUR(EmployeeOrgan org) {
        int total = (Integer) getSqlMapClientTemplate().queryForObject("selectEmployeeOrgCount", org);
        return total;
    }

    /**
     * 获取员工与组织机构关系
     * 
     * @param employeeId
     * @return
     */
    public List<EmployeeOrgan> getEmployeeOrgan(String employeeId) {
        List<EmployeeOrgan> result = null;
        if (StringUtils.isNotBlank(employeeId)) {
            result = getSqlMapClientTemplate().queryForList("selectEmployeeOrgan", employeeId);
        } else {
            result = getSqlMapClientTemplate().queryForList("selectOrganNoEmployee");
        }
        return result;
    }

    /**
     * 获取当前用户的组织机构ID
     * 
     * @param userId
     * @return
     */
    public Integer getOrgIdForCurrentUser(String userId) {
        return (Integer) getSqlMapClientTemplate().queryForObject("selectOrgForCurrentUser", userId);
    }

    public void deleteEmployeeOrg(int empId) {
        getSqlMapClientTemplate().delete("deleteEmployeeOrg", empId);

    }

    public void insertEmployeeOrg(EmployeeOrgan employeeOrg) {
        getSqlMapClientTemplate().insert("insertEmployeeOrg", employeeOrg);

    }

    public int updateEmployeeUpdateInfo(Employee employee) {
        getSqlMapClientTemplate().update("updateEmployeeUpdateInfo", employee);
        return 1;
    }

    public Employee getEmployee(Integer employeeId) {
        return (Employee) getSqlMapClientTemplate().queryForObject("getEmployee", employeeId);
    }


    public Employee getEmployeeByEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return null;
        }
        return (Employee) getSqlMapClientTemplate().queryForFirst("getEmployeeByEmail", email);
    }

    public Map<Integer, String> getOrgByEmployeeId(Integer employeeId) {
        return getSqlMapClientTemplate().queryForMap("getOrgByEmployeeId", employeeId, "ORG_ID", "ORG_NAME");
    }

    public List<String> getOrgCodesByEmployeeId(Integer employeeId) {
        return getSqlMapClientTemplate().queryForList("getOrgCodesByEmployeeId", employeeId);
    }
    
    public List<EmployeeOrgan> queryOrgByEmployeeId(String employeeId) {
        return getSqlMapClientTemplate().queryForList("queryOrgByEmployeeId", employeeId);
    }

    public PagingResult<Employee> getOrgEmployees(PagingForm pagingParam) {
        String sort = pagingParam.getSort();
        String order = pagingParam.getOrder();
        if (sort != null) {
            checkName(sort);
        }
        if (order != null) {
            checkName(order);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("sort", pagingParam.getSort());
        params.put("order", pagingParam.getOrder());
        params.putAll(pagingParam.getFilter());
        List<Employee> page = new ArrayList<Employee>();
        int total = getSqlMapClientTemplate().queryForPage(page, "getOrgEmployees", params, pagingParam.getSkip(),
            pagingParam.getPageSize());
        PagingResult<Employee> result = new PagingResult<Employee>(page, total);
        return result;
    }

    public List<Employee> getEmployeeByOrgs(Map<String, Object> param) {
        return getSqlMapClientTemplate().queryForList("getEmployeeByOrgs", param, 0, 100);
    }

    public List<Employee> getEmployeesByFuncCode(String funcCode) {
        return getSqlMapClientTemplate().queryForList("getEmployeesByFuncCode", funcCode);
    }
}
