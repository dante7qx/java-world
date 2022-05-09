package com.epolleo.hr.employee.service;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import com.epolleo.bp.pub.AbstractService;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.hr.employee.bean.EmployeeOtherBean;
import com.epolleo.hr.employee.dao.ibatis.EmployeeOtherDao;
import com.epolleo.pub.att.service.AttService;

/**
 * @Description: 员工补充信息的业务类
 * 
 * @author dante
 * @date 2015-09-26 下午03:56:07
 *
 */
public class EmployeeOtherService extends AbstractService<EmployeeOtherBean, EmployeeOtherDao> {
	
	private final static String UPLOAD_MODEL_FILEPATH = "employeeOther";
    
	@Resource
    private SeqService seqService;
	@Resource
    private AttService attService;
    @Resource
    public void setDao(EmployeeOtherDao dao) {
        this.dao = dao;
    }

    public void updateEmployeeOther(EmployeeOtherBean entity, FileItem[] fileItems) {
    	boolean isUpdate = true;
        boolean uploadFlag = true;
        Long id = entity.getId();
        if(id == null) {
        	isUpdate = false;
        	id = seqService.getNewId(IdKind.HrEmployeeOtherId);
        	entity.setId(id);
    		entity.setCreateUser(LoginUser.getCurrentUser().getUserId());
            entity.setCreateTime(DateUtils.getCurrentDate());
        }
        entity.setUpdateUser(LoginUser.getCurrentUser().getUserId());
        entity.setUpdateTime(DateUtils.getCurrentDate());
        if(fileItems != null && fileItems.length > 0) {
        	String attLotId = entity.getAttLotId();
        	if(StringUtils.isEmpty(attLotId)) {
        		entity.setAttLotId(UUID.randomUUID() + "");
        	}
            uploadFlag = attService.upload(entity.getAttLotId(), fileItems,
            	EmployeeOtherBean.class.getName(), UPLOAD_MODEL_FILEPATH);
        }
        if (uploadFlag) { // 上传新的
        	if(isUpdate) {
        		attService.deleteExistAttachment(entity.getDeletedFileIds()); // 删除被删的附件
                dao.update(entity);
        	} else {
        		dao.save(entity);
        	}
            
        }
    }
    
    /**
     * 根据empId获取员工补充信息
     * 
     * @param empId
     * @return
     */
    public EmployeeOtherBean queryByEmpId(Long empId) {
    	return dao.queryByEmpId(empId);
    }

}