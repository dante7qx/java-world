package com.epolleo.hr.employee.service;

import javax.annotation.Resource;

import com.epolleo.bp.pub.AbstractService;
import com.epolleo.hr.employee.dao.ibatis.EmployeeWorkDao;
import com.epolleo.hr.employee.bean.EmployeeWorkBean;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.DateUtils;

/**
 * @Description: 员工工作信息的业务类
 * 
 * @author dante
 * @date 2015-09-19 下午10:04:02
 *
 */
public class EmployeeWorkService extends AbstractService<EmployeeWorkBean, EmployeeWorkDao> {
    @Resource
    private SeqService seqService;
    @Resource
    public void setDao(EmployeeWorkDao dao) {
        this.dao = dao;
    }


    public void updateEmployeeWork(EmployeeWorkBean entity) {
    	boolean isUpdate = true;
    	Long id = entity.getId();
    	if(id == null) {
    		isUpdate = false;
    		entity.setId(seqService.getNewId(IdKind.HrEmployeeWorkId));
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
     * 根据employeeId获取员工工作信息
     * 
     * @param employeeId
     * @return
     */
    public EmployeeWorkBean queryByEmpId(Long employeeId) {
    	return dao.queryByEmpId(employeeId);
    }

}