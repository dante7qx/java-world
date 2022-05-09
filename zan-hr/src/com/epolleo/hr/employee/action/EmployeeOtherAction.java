package com.epolleo.hr.employee.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.epolleo.hr.employee.bean.EmployeeOtherBean;
import com.epolleo.hr.employee.service.EmployeeOtherService;
import com.epolleo.pub.att.service.AttService;

/**
 * @Description: 员工补充信息的Action
 * 
 * @author dante
 * @date 2015-09-26 下午03:56:07
 *
 */
@Function()
public class EmployeeOtherAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ParserRequestContext parser;
    @Resource
    private AttService attService;
    @Resource
    private EmployeeOtherService service;


    /**
     * 根据empId获取
     *
     * @param context
     * @param empId 
     */
    @Function("hr.employee.query")
    public void doQueryByEmpId(Context context,
			@Param(name = "empId") Long empId) {
    	EmployeeOtherBean benefit = service.queryByEmpId(empId);
		context.put("json", benefit);
	}
    
    /**
     * 修改员工补充信息
     * 
     * @param context
     * @param entity
     * @param req
     */
    @Function("hr.employee.update")
    public void doUpdate(Context context, @Params EmployeeOtherBean entity, HttpServletRequest req) {
        boolean isAttVaild = attService.attVerify(req, context, parser, null,
        "attatchFile");
        Map<String, Object> result = new HashMap<String, Object>();
        if (isAttVaild) {
            try {
                FileItem[] fileItems = parser.getParameters().getFileItems(
                    "attatchFile");
                service.updateEmployeeOther(entity, fileItems);
                result.put("flag", 1);
                result.put("data", entity);
            } catch (Exception e) {
                result.put("flag", 2);
                logger.error("Update EmployeeOtherBean error.", e);
            }
            context.put("json", result);
        }
    }

}