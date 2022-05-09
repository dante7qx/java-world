package com.epolleo.hr.employee.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.epolleo.hr.employee.bean.EmployeeWorkBean;
import com.epolleo.hr.employee.service.EmployeeWorkService;

/**
 * @Description: 员工工作信息的Action
 * 
 * @author dante
 * @date 2015-09-19 下午10:04:02
 *
 */
@Function()
public class EmployeeWorkAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private EmployeeWorkService service;

    /**
     * 修改员工工作信息对象
     *
     * @param context
     * @param entity
     */
    @Function("hr.employee.update")
    public void doUpdate(Context context, @Params EmployeeWorkBean entity) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	boolean flag = false;
		try {
			service.updateEmployeeWork(entity);
			flag = true;
		} catch (Exception e) {
			logger.error("Update EmployeeWorkBean error.", e);
		}
		result.put("flag", flag);
        result.put("data", entity);
		context.put("json", result);
    }

    /**
     * 根据empId获取
     *
     * @param context
     * @param empId 

     */
    @Function("hr.employee.query")
    public void doQueryByEmpId(Context context,
			@Param(name = "empId") Long empId) {
    	EmployeeWorkBean work = service.queryByEmpId(empId);
		context.put("json", work);
	}
}