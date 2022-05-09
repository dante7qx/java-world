package com.epolleo.hr.employee.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.hr.employee.bean.EmployeeBean;
import com.epolleo.hr.employee.service.HrEmployeeService;
import com.epolleo.pub.att.service.AttService;

/**
 * @Description: 员工的Action
 * 
 * @author dante
 * @date 2015-09-19 下午10:03:53
 *
 */
@Function()
public class EmployeeAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private HrEmployeeService service;
    @Resource
    private ParserRequestContext parser;
    @Resource
    private AttService attService;


    /**
     * 分页查询员工
     *
     * @param context
     * @param form
     * @param entity
     */
    @Function("hr.employee.query")
    public void doQuery(Context context, @Params PagingForm form,
            @Params EmployeeBean entity) {
    	if(StringUtils.isNotEmpty(entity.getChnName())) {
    		form.addFilter("chnName", "%"+entity.getChnName()+"%");
    	}
    	if(entity.getGen() != null) {
    		form.addFilter("gen", entity.getGen());
    	}
    	if(entity.getOrgId() != null) {
    		form.addFilter("orgId", entity.getOrgId());
    	}
    	if(entity.getWorkPlaceId() != null) {
    		form.addFilter("workPlaceId", entity.getWorkPlaceId());
    	}
    	if(entity.getEntrantStartDate() != null) {
    		form.addFilter("entrantStartDate", entity.getEntrantStartDate());
    	}
    	if(entity.getEntrantEndDate() != null) {
    		form.addFilter("entrantEndDate", entity.getEntrantEndDate());
    	}
    	if(entity.getIsActive() == null) {
    		entity.setIsActive(true);
    	}
    	form.addFilter("isActive", entity.getIsActive());
        PagingResult<EmployeeBean> result = service.findPaging(form);
        context.put("json", result);
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
            service.deleteById(id);
            result = true;
        } catch (Exception e) {
            logger.error("Delete EmployeeBean error.", e);
        }
        context.put("json", result);
    }

    /**
     * 修改员工对象
     *
     * @param context
     * @param entity
     */
    @Function("hr.employee.update")
    public void doUpdate(Context context, @Params EmployeeBean entity, HttpServletRequest req) {
        boolean isAttVaild = attService.attVerify(req, context, parser, null,
            "photoAtt");
        Map<String, Object> result = new HashMap<String, Object>();
        if (isAttVaild) {
            try {
                FileItem[] fileItems = parser.getParameters().getFileItems(
                    "photoAtt");
                service.update(entity, fileItems);
                result.put("flag", 1);
                result.put("data", entity);
            } catch (Exception e) {
                result.put("flag", 2);
                logger.error("Update EmployeeBean error.", e);
            }
            context.put("json", result);
        }
    }

    /**
     * 根据Id获取
     *
     * @param context
     * @param id 

     */
    @Function("hr.employee.query")
    public void doQueryById(Context context, @Param(name = "id") Long id) {
        EmployeeBean bean = service.find(id);
        context.put("json", bean);
    }
    
    /**
     * 上传图片
     * 
     * @param context
     * @param request
     * @param response
     */
    public void doUploadImg(Context context, HttpServletRequest request,
        HttpServletResponse response) {
        boolean flag = false;
        String imgInstanceId = "";
        Map<String, Object> result = new HashMap<String, Object>();
        FileItem fileItem = parser.getParameters().getFileItem("photoAtt");
        if (fileItem != null) {
            imgInstanceId = UUID.randomUUID().toString();
            request.getSession().setAttribute(imgInstanceId, fileItem);
            flag = true;
        }
        result.put("flag", flag);
        result.put("imgInstanceId", imgInstanceId);
        context.put("json", result);

    }

    /**
     * 显示临时图片
     * 
     * @param context
     * @param request
     * @param response
     * @param imgInstanceId
     */
    public void doShowImg(Context context, HttpServletRequest request,
        HttpServletResponse response, @Param(name = "imgInstanceId") String imgInstanceId) {
        OutputStream out = null;
        InputStream in = null;
        response.setContentType("image/jpg;charset=utf-8");
        try {
            out = new BufferedOutputStream(response.getOutputStream());
            FileItem fileItem = (FileItem) request.getSession().getAttribute(imgInstanceId);
            if (fileItem == null)
                return;
            in = new BufferedInputStream(fileItem.getInputStream());
            byte[] buff = new byte[1024];
            while (in.read(buff) != -1) {
                out.write(buff);
            }
        } catch (Exception e) {
            logger.error("doShowImg attendantspecialtyBean error.", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
            }
            
            request.getSession().removeAttribute(imgInstanceId);
        }
    }

}