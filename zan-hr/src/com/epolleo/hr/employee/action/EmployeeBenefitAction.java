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
import com.epolleo.hr.employee.bean.EmployeeBenefitBean;
import com.epolleo.hr.employee.service.EmployeeBenefitService;

/**
 * @Description: 员工薪资福利的Action
 * 
 * @author dante
 * @date 2015-09-26 下午03:55:39
 *
 */
@Function()
public class EmployeeBenefitAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private EmployeeBenefitService service;


    @Function("hr.employee.update")
    public void doUpdate(Context context, @Params EmployeeBenefitBean entity) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	boolean flag = false;
		try {
			service.updateEmployeeBenefit(entity);
			flag = true;
		} catch (Exception e) {
			logger.error("Update EmployeeBenefitBean error.", e);
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
    	EmployeeBenefitBean benefit = service.queryByEmpId(empId);
		context.put("json", benefit);
	}

}