/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 *
 * 描述：<描述信息>
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.employee.service;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hnas.esb.entity.GenerateReqMsg;
import com.hnas.esb.entity.Parameter;
import com.hnas.event.ESBService;

import com.epolleo.bp.employee.bean.ESBConfBean;
import com.epolleo.bp.employee.bean.Employee;
import com.epolleo.bp.employee.bean.EmployeeOrgan;
import com.epolleo.bp.employee.dao.ibatis.EmployeeDao;
import com.epolleo.bp.org.bean.OrgNodeBean;
import com.epolleo.bp.org.service.OrgService;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

/**
 * 员工管理service
 * 
 * @author dante
 * @date 2012-06-2810:48:40 +0800
 */
public class EmployeeService {

    private Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Resource
    private EmployeeDao employeeDao;

    @Resource
    private OrgService orgService;

    @Resource
    private ESBConfBean esbConfBean;

    EmployeeHelper helper = new EmployeeHelper();

    public PagingResult<Employee> findPaging(PagingForm pagingParam) {
        return employeeDao.findPaging(pagingParam);
    }

    public List<Employee> findEmployees(Map<String, Object> params) {
        return employeeDao.findEmployees(params);
    }

    public List<Employee> findNoAccountEmployee(Map<String, Object> params) {
        return employeeDao.findNoAccountEmployee(params);
    }

    public Employee save(Employee employee) {
        employeeDao.save(employee);
        employeeDao.deleteEmployeeOrg(employee.getEmployeeId());
        if (employee.getOrgIds() != null) {
            for (int orgId : employee.getOrgIds()) {
                EmployeeOrgan eo = new EmployeeOrgan();
                eo.setEmployeeId(employee.getEmployeeId());
                eo.setOrgId(orgId);
                employeeDao.insertEmployeeOrg(eo);
            }
        }
        return employee;
    }

    public int delete(Integer idStr) {
        return employeeDao.delete(idStr);
    }

    public int update(Employee employee) {
        int ret = employeeDao.update(employee);
        employeeDao.deleteEmployeeOrg(employee.getEmployeeId());
        if (employee.getOrgIds() != null) {
            for (int orgId : employee.getOrgIds()) {
                EmployeeOrgan eo = new EmployeeOrgan();
                eo.setEmployeeId(employee.getEmployeeId());
                eo.setOrgId(orgId);
                employeeDao.insertEmployeeOrg(eo);
            }
        }
        return ret;
    }

    public Employee getEmployee(Integer employeeId) {
        return employeeDao.getEmployee(employeeId);
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeDao.getEmployeeByEmail(email);
    }

    public int queryUR(EmployeeOrgan org) {
        return employeeDao.queryUR(org);
    }

    public List<EmployeeOrgan> getEmployeeOrgan(String employeeId) {
        List<EmployeeOrgan> result = new ArrayList<EmployeeOrgan>();
        // 获取到原始数据
        List<EmployeeOrgan> list = employeeDao.getEmployeeOrgan(employeeId);
        Map<Integer, Object> m = new LinkedHashMap<Integer, Object>();
        // 把初始数据存放到map中，以orgid为key
        for (EmployeeOrgan employeeOrgan : list) {
            EmployeeOrgan uo = new EmployeeOrgan();
            if (employeeOrgan.getDefaultOrgId() != null
                && employeeOrgan.getDefaultOrgId().equals(employeeOrgan.getOrgId())) {
                uo.setDft(true);
            }
            if (employeeOrgan.getEmployeeId() != null) {
                uo.setMark(true);
            }
            uo.setOrgId(employeeOrgan.getOrgId());
            uo.setOrgName(employeeOrgan.getOrgName());
            uo.setOrgType(employeeOrgan.getOrgType());
            uo.setParentId(employeeOrgan.getParentId());
            uo.setChildren(null);
            // uo.setState("closed");
            m.put(uo.getOrgId(), uo);
        }
        // 遍历map
        Set<Integer> keySet = m.keySet();
        for (Integer key : keySet) {
            EmployeeOrgan employeeOrgan = (EmployeeOrgan) m.get(key);
            int prentId = employeeOrgan.getParentId();
            if (prentId == 0) { // 根节点
                result.add(employeeOrgan);
            } else {
                for (Integer k : keySet) {
                    EmployeeOrgan employeeOrg = (EmployeeOrgan) m.get(k);
                    if (k == prentId) {
                        if (employeeOrg.getChildren() == null) {
                            List<EmployeeOrgan> l = new ArrayList<EmployeeOrgan>();
                            l.add(employeeOrgan);
                            employeeOrg.setChildren(l);
                        } else {
                            employeeOrg.getChildren().add(employeeOrgan);
                        }
                    }
                }
            }
        }
        return result;
    }

    public Employee fetchByAccount(String account) {
        Employee employee = null;
        /*- for deerjet, call mule
        HnaUserRequest request = new HnaUserRequest();
        request.setAccount(account);
        HnaUserResponse response = HnaUserSync.sync(request);
        Data data = response.getData();
        if(data != null) {
            List<DtRetu> list = data.getNewDataSet().getDtRetu();
            if(list != null && list.size() == 1) {
                employee = new Employee();
                employee.setEmail(account + "@hnair.com");
                DtRetu dtRetu = list.get(0);
                employee.setEmployeeName(dtRetu.getVcName());
                employee.setMobileNumber(dtRetu.getVcMobile());
                employee.setPhone(dtRetu.getVcWorkPhone());
                
                String nNodeID = dtRetu.getNNodeID();
                if(StringUtils.isNotEmpty(nNodeID)) {
                    OrgNodeBean orgNodeBean = orgService.queryOrgByCode(nNodeID);
                    if(orgNodeBean != null) {
                        employee.setDefaultOrgId(orgNodeBean.getOrgId());
                    }
                }
            }
        }
         */

        List<Parameter> parameter = new ArrayList<Parameter>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        parameter.add(new Parameter("StartDate", sdf.format(new Date(0L))));
        parameter.add(new Parameter("EndDate", sdf.format(new Date())));
        parameter.add(new Parameter("Account", account));
        try {
            ESBService es = new ESBService(new URL(esbConfBean.soapUrl));
            String message = GenerateReqMsg.generate("HRESB_EmpDirectory", esbConfBean.userId, esbConfBean.userPwd,
                parameter, esbConfBean.privateKeyPath);
            logger.info(message);
            String ret = es.getProjectsHnaESBServiceInitialESBService().getDataFromInnerESB(message);
            logger.info(ret);
            if (ret != null) {
                Document doc = DocumentHelper.parseText(ret);
                Element root = doc.getRootElement();
                String text = root.element("ResponseInfo").element("Result").getText();
                if ("1".equals(text)) {
                    Element e = root.element("Data").element("NewDataSet").element("dtRetu");
                    Employee emp = new Employee();
                    String orgCode = e.elementText("nNodeID");
                    if (orgCode != null) {
                        OrgNodeBean orgNodeBean = orgService.queryOrgByCode(orgCode);
                        if (orgNodeBean != null) {
                            emp.setDefaultOrgId(orgNodeBean.getOrgId());
                        }
                    }
                    emp.setEmployeeName(e.elementText("vcName"));
                    emp.setEmail(e.elementText("vcAccount") + "@hnair.com");
                    helper.fillMobilePhone(emp, esbConfBean, es, orgCode, e.elementText("vcEmployeeID"));
                    return emp;
                }
                return null;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return employee;
    }

    public Map<Integer, String> queryEmployeeOrganizations(Integer eId) {
        return employeeDao.getOrgByEmployeeId(eId);
    }

    public List<String> getOrgCodesByEmployeeId(Integer employeeId) {
        return employeeDao.getOrgCodesByEmployeeId(employeeId);
    }

    public List<EmployeeOrgan> queryOrgByEmployeeId(String userId) {
        return employeeDao.queryOrgByEmployeeId(userId);
    }
    
    public PagingResult<Employee> getOrgEmployees(PagingForm pagingParam) {
        return employeeDao.getOrgEmployees(pagingParam);
    }

    public List<Employee> getEmployeeByOrgs(Map<String, Object> param) {
        return employeeDao.getEmployeeByOrgs(param);
    }

}

class EmployeeHelper {

    void fillMobilePhone(Employee emp, ESBConfBean ec, ESBService es, String orgCode, String empCode) throws Exception {
        List<Parameter> parameter = new ArrayList<Parameter>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        parameter.add(new Parameter("StartDate", sdf.format(new Date(0L))));
        parameter.add(new Parameter("EndDate", sdf.format(new Date())));
        parameter.add(new Parameter("EmployeeID", empCode));
        parameter.add(new Parameter("OrganID", orgCode));

        String message = GenerateReqMsg.generate("HRESB_EmpMessageInfo", ec.userId, ec.userPwd, parameter,
            ec.privateKeyPath);
        String ret = es.getProjectsHnaESBServiceInitialESBService().getDataFromInnerESB(message);
        if (ret != null) {
            Document doc = DocumentHelper.parseText(ret);
            Element root = doc.getRootElement();
            String text = root.element("ResponseInfo").element("Result").getText();
            if ("1".equals(text)) {
                Element e = root.element("Data").element("NewDataSet").element("dtRetu");
                emp.setMobileNumber(e.elementText("vcMobile"));
                emp.setPhone(e.elementText("vcWorkPhone"));
            }
        }

    }
}