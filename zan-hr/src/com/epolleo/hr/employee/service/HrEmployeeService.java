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
import com.epolleo.hr.employee.bean.EmployeeBean;
import com.epolleo.hr.employee.dao.ibatis.HrEmployeeDao;
import com.epolleo.pub.att.service.AttService;

/**
 * @Description: 员工的业务类
 * 
 * @author dante
 * @date 2015-09-19 下午10:03:53
 *
 */
public class HrEmployeeService extends AbstractService<EmployeeBean, HrEmployeeDao> {
	private final static String UPLOAD_MODEL_FILEPATH = "employee";
    @Resource
    private SeqService seqService;
    @Resource
    private AttService attService;
    @Resource
    public void setDao(HrEmployeeDao dao) {
        this.dao = dao;
    }


    public void update(EmployeeBean entity, FileItem[] fileItems) {
    	boolean isUpdate = true;
    	boolean uploadFlag = true;
    	Long id = entity.getId();
    	String attLotId = entity.getAttLotId();
    	if(id == null) {
    		entity.setCreateUser(LoginUser.getCurrentUser().getUserId());
    		entity.setCreateTime(DateUtils.getCurrentDate());
    		entity.setId(seqService.getNewId(IdKind.HrEmployeeId));
    		isUpdate = false;
    	}
    	entity.setUpdateUser(LoginUser.getCurrentUser().getUserId());
        entity.setUpdateTime(DateUtils.getCurrentDate());
        if(fileItems != null && fileItems.length > 0) {
        	if(StringUtils.isEmpty(attLotId)) {
        		attLotId = UUID.randomUUID().toString();
        		entity.setAttLotId(attLotId);
        	}
            uploadFlag = attService.upload(entity.getAttLotId(), fileItems,
            		EmployeeBean.class.getName(), UPLOAD_MODEL_FILEPATH);
        }
        if (uploadFlag) { 
        	if(isUpdate) {
        		dao.update(entity);
        	} else {
        		dao.save(entity);
        	}
        }
    }
    
    public void deleteById(Long id) {
    	EmployeeBean entity = new EmployeeBean();
    	entity.setId(id);
    	entity.setUpdateTime(DateUtils.getCurrentDate());
    	entity.setUpdateUser(LoginUser.getCurrentUser().getUserId());
    	dao.delete(entity);
    }

}