package com.epolleo.hr.employee.dao.ibatis;

import java.io.Serializable;
import java.util.List;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

import com.epolleo.hr.employee.bean.EmployeeWorkBean;

/**
 * @Description: 员工工作信息的数据访问类
 * 
 * @author dante
 * @date 2015-09-19 下午10:04:02
 *
 */
public class EmployeeWorkDao extends AbstractDao<EmployeeWorkBean> {

    /**
     * 新增员工工作信息
     * 
     * @param entity
     * @return EmployeeWorkBean
     */
    @Override
    public EmployeeWorkBean save(EmployeeWorkBean entity) {
        return save("insertEmployeeWork", entity);
    }

    /**
     * 根据entityId删除员工工作信息
     * 
     * @param entity
     * @return int
     */
    @Override
    public int delete(Serializable entityId) {
        return delete("deleteEmployeeWork", entityId);
    }

    /**
     * 修改员工工作信息
     * 
     * @param entity
     * @return int
     */
    @Override
    public int update(EmployeeWorkBean entity) {
        return update("updateEmployeeWork", entity);
    }

    /**
     * 分页查询员工工作信息
     * 
     * @param pagingForm
     * @param fuzzy
     * @return PagingResult
     */
    @Override
    public PagingResult<EmployeeWorkBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
        return findPaging("queryEmployeeWorkPage", pagingForm, fuzzy);
    }

    /**
     * 查询员工工作信息的所有记录
     * 
     * @return List
     */
    @Override
    public List<EmployeeWorkBean> findAll() {
        return findAll("queryEmployeeWorkList");
    }

    /**
     * 根据Id获取员工工作信息
     * 
     * @param id
     * @return EmployeeWorkBean
     */
    @Override
    public EmployeeWorkBean find(Serializable id) {
        return find("queryEmployeeWorkById", id);
    }
    
    /**
     * 根据employeeId获取员工工作信息
     * 
     * @param employeeId
     * @return
     */
    public EmployeeWorkBean queryByEmpId(Long employeeId) {
        return (EmployeeWorkBean) getSqlMapClientTemplate().queryForObject("queryEmployeeWorkByEmployeeId", employeeId);
    }
       
}