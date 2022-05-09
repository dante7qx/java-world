package com.epolleo.hr.employee.service;

import java.util.List;

import javax.annotation.Resource;

import com.epolleo.bp.pub.AbstractService;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.hr.employee.bean.EmployeeRelationBean;
import com.epolleo.hr.employee.dao.ibatis.EmployeeRelationDao;

/**
 * @Description: 员工家属信息的业务类
 * 
 * @author dante
 * @date 2015-09-19 下午10:04:06
 *
 */
public class EmployeeRelationService extends AbstractService<EmployeeRelationBean, EmployeeRelationDao> {
    @Resource
    private SeqService seqService;
    @Resource
    public void setDao(EmployeeRelationDao dao) {
        this.dao = dao;
    }


    public void updateEmployeeRelation(EmployeeRelationBean entity) {
    	boolean isUpdate = true;
    	Long id = entity.getId();
    	if(id == null) {
    		isUpdate = false;
    		entity.setId(seqService.getNewId(IdKind.HrEmployeeRelationId));
    		entity.setCreateUser(LoginUser.getCurrentUser().getUserId());
            entity.setCreateTime(DateUtils.getCurrentDate());
    	}
        entity.setUpdateUser(LoginUser.getCurrentUser().getUserId());
        entity.setUpdateTime(DateUtils.getCurrentDate());
        if(isUpdate) {
        	dao.update(entity);
        } else {
        	dao.save(entity);
        }
    }

    /**
     * 根据empId获取员工家属信息
     * 
     * @param empId
     * @return
     */
    public List<EmployeeRelationBean> queryByEmpId(Long empId) {
    	return dao.queryByEmployeeId(empId);
    }
    
    public void delete(Long id) {
    	EmployeeRelationBean entity = new EmployeeRelationBean();
    	entity.setId(id);
    	entity.setUpdateUser(LoginUser.getCurrentUser().getUserId());
        entity.setUpdateTime(DateUtils.getCurrentDate());
        dao.delete(entity);
    }
    
    

}