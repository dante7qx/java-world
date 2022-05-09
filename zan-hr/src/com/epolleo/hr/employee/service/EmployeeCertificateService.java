package com.epolleo.hr.employee.service;

import java.util.List;

import javax.annotation.Resource;

import com.epolleo.bp.pub.AbstractService;
import com.epolleo.hr.employee.dao.ibatis.EmployeeCertificateDao;
import com.epolleo.hr.employee.bean.EmployeeCertificateBean;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.DateUtils;

/**
 * @Description: 员工资格、职称证书的业务类
 * 
 * @author [开发人员姓名]
 * @date 2015-09-19 下午10:03:58
 *
 */
public class EmployeeCertificateService extends AbstractService<EmployeeCertificateBean, EmployeeCertificateDao> {
    @Resource
    private SeqService seqService;
    @Resource
    public void setDao(EmployeeCertificateDao dao) {
        this.dao = dao;
    }

    public void updateEmployeeCertificate(EmployeeCertificateBean entity) {
    	boolean isUpdate = true;
    	Long id = entity.getId();
    	if(id == null) {
    		isUpdate = false;
    		entity.setId(seqService.getNewId(IdKind.HrEmployeeCertificateId));
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
     * 根据empId获取员工资格、职称证书
     * 
     * @param empId
     * @return
     */
    public List<EmployeeCertificateBean> queryByEmpId(Long empId) {
    	return dao.queryByEmpId(empId);
    }
    
    public void delete(Long id) {
    	EmployeeCertificateBean entity = new EmployeeCertificateBean();
    	entity.setId(id);
    	entity.setUpdateUser(LoginUser.getCurrentUser().getUserId());
        entity.setUpdateTime(DateUtils.getCurrentDate());
        dao.delete(entity);
    }
}