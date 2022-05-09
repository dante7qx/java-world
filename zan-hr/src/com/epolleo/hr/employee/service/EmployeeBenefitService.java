package com.epolleo.hr.employee.service;

import javax.annotation.Resource;

import com.epolleo.bp.pub.AbstractService;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.hr.employee.bean.EmployeeBenefitBean;
import com.epolleo.hr.employee.dao.ibatis.EmployeeBenefitDao;

/**
 * @Description: 员工薪资福利的业务类
 * 
 * @author dante
 * @date 2015-09-26 下午03:55:39
 *
 */
public class EmployeeBenefitService extends AbstractService<EmployeeBenefitBean, EmployeeBenefitDao> {
    @Resource
    private SeqService seqService;
    @Resource
    public void setDao(EmployeeBenefitDao dao) {
        this.dao = dao;
    }


    public void updateEmployeeBenefit(EmployeeBenefitBean entity) {
    	boolean isUpdate = true;
    	Long id = entity.getId();
    	if(id == null) {
    		isUpdate = false;
    		entity.setId(seqService.getNewId(IdKind.HrEmployeeBenefitId));
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
     * 根据empId获取员工薪资福利
     * 
     * @param empId
     * @return
     */
    public EmployeeBenefitBean queryByEmpId(Long empId) {
    	return dao.queryByEmpId(empId);
    }

}