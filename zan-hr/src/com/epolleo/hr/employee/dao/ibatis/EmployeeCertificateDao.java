package com.epolleo.hr.employee.dao.ibatis;

import java.io.Serializable;
import java.util.List;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

import com.epolleo.hr.employee.bean.EmployeeCertificateBean;

/**
 * @Description: 员工资格、职称证书的数据访问类
 * 
 * @author dante
 * @date 2015-09-19 下午10:03:58
 *
 */
public class EmployeeCertificateDao extends AbstractDao<EmployeeCertificateBean> {

    /**
     * 新增员工资格、职称证书
     * 
     * @param entity
     * @return EmployeeCertificateBean
     */
    @Override
    public EmployeeCertificateBean save(EmployeeCertificateBean entity) {
        return save("insertEmployeeCertificate", entity);
    }

    /**
     * 根据entity删除员工资格、职称证书
     * 
     * @param entity
     */
    public void delete(EmployeeCertificateBean entity) {
        getSqlMapClientTemplate().update("deleteEmployeeCertificate", entity);
    }

    /**
     * 修改员工资格、职称证书
     * 
     * @param entity
     * @return int
     */
    @Override
    public int update(EmployeeCertificateBean entity) {
        return update("updateEmployeeCertificate", entity);
    }

    /**
     * 分页查询员工资格、职称证书
     * 
     * @param pagingForm
     * @param fuzzy
     * @return PagingResult
     */
    @Override
    public PagingResult<EmployeeCertificateBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
        return findPaging("queryEmployeeCertificatePage", pagingForm, fuzzy);
    }

    /**
     * 查询员工资格、职称证书的所有记录
     * 
     * @return List
     */
    @Override
    public List<EmployeeCertificateBean> findAll() {
        return findAll("queryEmployeeCertificateList");
    }

    /**
     * 根据Id获取员工资格、职称证书
     * 
     * @param id
     * @return EmployeeCertificateBean
     */
    @Override
    public EmployeeCertificateBean find(Serializable id) {
        return find("queryEmployeeCertificateById", id);
    }
    
    /**
     * 根据empId获取员工资格、职称证书
     * 
     * @param empId
     * @return
     */
    public List<EmployeeCertificateBean> queryByEmpId(Long empId) {
    	return getSqlMapClientTemplate().queryForList("queryEmployeeCertificateByEmpId", empId);
    }
    
    
}