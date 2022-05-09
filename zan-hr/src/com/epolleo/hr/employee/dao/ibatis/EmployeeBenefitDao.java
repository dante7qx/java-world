package com.epolleo.hr.employee.dao.ibatis;

import java.io.Serializable;
import java.util.List;

import oracle.net.aso.e;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.hr.employee.bean.EmployeeBenefitBean;

/**
 * @Description: 员工薪资福利的数据访问类
 * 
 * @author dante
 * @date 2015-09-26 下午03:55:39
 *
 */
public class EmployeeBenefitDao extends AbstractDao<EmployeeBenefitBean> {

    /**
     * 新增员工薪资福利
     * 
     * @param entity
     * @return EmployeeBenefitBean
     */
    @Override
    public EmployeeBenefitBean save(EmployeeBenefitBean entity) {
        return save("insertEmployeeBenefit", entity);
    }

    /**
     * 根据entityId删除员工薪资福利
     * 
     * @param entity
     * @return int
     */
    @Override
    public int delete(Serializable entityId) {
        return delete("deleteEmployeeBenefit", entityId);
    }

    /**
     * 修改员工薪资福利
     * 
     * @param entity
     * @return int
     */
    @Override
    public int update(EmployeeBenefitBean entity) {
        return update("updateEmployeeBenefit", entity);
    }

    /**
     * 分页查询员工薪资福利
     * 
     * @param pagingForm
     * @param fuzzy
     * @return PagingResult
     */
    @Override
    public PagingResult<EmployeeBenefitBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
        return findPaging("queryEmployeeBenefitPage", pagingForm, fuzzy);
    }

    /**
     * 查询员工薪资福利的所有记录
     * 
     * @return List
     */
    @Override
    public List<EmployeeBenefitBean> findAll() {
        return findAll("queryEmployeeBenefitList");
    }

    /**
     * 根据Id获取员工薪资福利
     * 
     * @param id
     * @return EmployeeBenefitBean
     */
    @Override
    public EmployeeBenefitBean find(Serializable id) {
        return find("queryEmployeeBenefitById", id);
    }
    
    /**
     * 根据empId获取员工薪资福利
     * 
     * @param empId
     * @return
     */
    public EmployeeBenefitBean queryByEmpId(Long empId) {
    	return (EmployeeBenefitBean) getSqlMapClientTemplate().queryForObject("queryEmployeeBenefitByEmpId", empId);
    }
}