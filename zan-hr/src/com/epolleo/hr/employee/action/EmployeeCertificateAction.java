package com.epolleo.hr.employee.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.hr.employee.bean.EmployeeCertificateBean;
import com.epolleo.hr.employee.service.EmployeeCertificateService;

/**
 * @Description: 员工资格、职称证书的Action
 * 
 * @author dante
 * @date 2015-09-19 下午10:03:58
 *
 */
@Function()
public class EmployeeCertificateAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private EmployeeCertificateService service;

    /**
     * 根据empId获取
     *
     * @param context
     * @param empId 
     */
    @Function("hr.employee.query")
    public void doQueryByEmpId(Context context, @Param(name = "empId") Long empId) {
        List<EmployeeCertificateBean> list = service.queryByEmpId(empId);
        context.put("json", list);
    }
    
    /**
     * 根据Id删除
     *
     * @param context
     * @param id
     * @see Context
     */
    @Function("hr.employee.delete")
    public void doDelete(Context context, @Param(name = "id") Long id) {
        boolean result = false;
        try {
            service.delete(id);
            result = true;
        } catch (Exception e) {
            logger.error("Delete EmployeeCertificateBean error.", e);
        }
        context.put("json", result);
    }

    /**
     * 修改员工资格、职称证书对象
     *
     * @param context
     * @param entity
     */
    @Function("hr.employee.update")
    public void doUpdate(Context context, @Params EmployeeCertificateBean entity) {
        boolean result = false;
        try {
            service.updateEmployeeCertificate(entity);
            result = true;
        } catch (Exception e) {
            logger.error("Update EmployeeCertificateBean error.", e);
        }
        context.put("json", result);
    }


}