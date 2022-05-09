package com.epolleo.hr.employee.dao.ibatis;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.hr.employee.bean.EmployeeBean;

/**
 * @Description: 员工的数据访问类
 * 
 * @author [开发人员姓名]
 * @date 2015-09-19 下午10:03:53
 *
 */
public class HrEmployeeDao extends AbstractDao<EmployeeBean> {

    /**
     * 新增员工
     * 
     * @param entity
     * @return EmployeeBean
     */
    @Override
    public EmployeeBean save(EmployeeBean entity) {
        return save("insertHrEmployee", entity);
    }

    /**
     * 根据entity删除员工
     * 
     * @param entity
     */
    public void delete(EmployeeBean entity) {
        getSqlMapClientTemplate().update("deleteHrEmployee", entity);
    }

    /**
     * 修改员工
     * 
     * @param entity
     * @return int
     */
    @Override
    public int update(EmployeeBean entity) {
        return update("updateHrEmployee", entity);
    }

    /**
     * 分页查询员工
     * 
     * @param pagingForm
     * @param fuzzy
     * @return PagingResult
     */
    @Override
    public PagingResult<EmployeeBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
        return findPaging("queryHrEmployeePage", pagingForm, fuzzy);
    }

    /**
     * 查询员工的所有记录
     * 
     * @return List
     */
    @Override
    public List<EmployeeBean> findAll() {
        return findAll("queryHrEmployeeList");
    }

    /**
     * 根据Id获取员工
     * 
     * @param id
     * @return EmployeeBean
     */
    @Override
    public EmployeeBean find(Serializable id) {
        return find("queryHrEmployeeById", id);
    }
    
       
}