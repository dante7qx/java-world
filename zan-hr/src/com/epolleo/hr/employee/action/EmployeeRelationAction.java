package com.epolleo.hr.employee.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.epolleo.hr.employee.bean.EmployeeRelationBean;
import com.epolleo.hr.employee.service.EmployeeRelationService;

/**
 * @Description: 员工家属信息的Action
 * 
 * @author dante
 * @date 2015-09-19 下午10:04:06
 *
 */
@Function()
public class EmployeeRelationAction {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private EmployeeRelationService service;

	/**
	 * 根据empId获取
	 *
	 * @param context
	 * @param form
	 * @param entity
	 */
	@Function("hr.employee.query")
	public void doQueryByEmpId(Context context,
			@Param(name = "empId") Long empId) {
		List<EmployeeRelationBean> list = service.queryByEmpId(empId);
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
			logger.error("Delete EmployeeRelationBean error.", e);
		}
		context.put("json", result);
	}

	/**
	 * 修改员工家属信息对象
	 *
	 * @param context
	 * @param entity
	 */
	@Function("hr.employee.update")
	public void doUpdate(Context context, @Params EmployeeRelationBean entity) {
		boolean result = false;
		try {
			service.updateEmployeeRelation(entity);
			result = true;
		} catch (Exception e) {
			logger.error("Update EmployeeRelationBean error.", e);
		}
		context.put("json", result);
	}

}