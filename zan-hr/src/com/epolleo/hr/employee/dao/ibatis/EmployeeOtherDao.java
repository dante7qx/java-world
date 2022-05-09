package com.epolleo.hr.employee.dao.ibatis;

import java.io.Serializable;
import java.util.List;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.hr.employee.bean.EmployeeOtherBean;

/**
 * @Description: 员工补充信息的数据访问类
 * 
 * @author dante
 * @date 2015-09-26 下午03:56:07
 *
 */
public class EmployeeOtherDao extends AbstractDao<EmployeeOtherBean> {

    /**
     * 新增员工补充信息
     * 
     * @param entity
     * @return EmployeeOtherBean
     */
    @Override
    public EmployeeOtherBean save(EmployeeOtherBean entity) {
        return save("insertEmployeeOther", entity);
    }

    /**
     * 根据entityId删除员工补充信息
     * 
     * @param entity
     * @return int
     */
    @Override
    public int delete(Serializable entityId) {
        return delete("deleteEmployeeOther", entityId);
    }

    /**
     * 修改员工补充信息
     * 
     * @param entity
     * @return int
     */
    @Override
    public int update(EmployeeOtherBean entity) {
        return update("updateEmployeeOther", entity);
    }

    /**
     * 分页查询员工补充信息
     * 
     * @param pagingForm
     * @param fuzzy
     * @return PagingResult
     */
    @Override
    public PagingResult<EmployeeOtherBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
        return findPaging("queryEmployeeOtherPage", pagingForm, fuzzy);
    }

    /**
     * 查询员工补充信息的所有记录
     * 
     * @return List
     */
    @Override
    public List<EmployeeOtherBean> findAll() {
        return findAll("queryEmployeeOtherList");
    }

    /**
     * 根据Id获取员工补充信息
     * 
     * @param id
     * @return EmployeeOtherBean
     */
    @Override
    public EmployeeOtherBean find(Serializable id) {
        return find("queryEmployeeOtherById", id);
    }
    
    /**
     * 根据empId获取员工补充信息
     * 
     * @param empId
     * @return
     */
    public EmployeeOtherBean queryByEmpId(Long empId) {
    	return (EmployeeOtherBean) getSqlMapClientTemplate().queryForObject("queryEmployeeOtherByEmpId", empId);
    }
       
}