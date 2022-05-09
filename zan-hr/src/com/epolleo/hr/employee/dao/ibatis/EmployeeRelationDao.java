package com.epolleo.hr.employee.dao.ibatis;

import java.io.Serializable;
import java.util.List;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

import com.epolleo.hr.employee.bean.EmployeeRelationBean;

/**
 * @Description: 员工家属信息的数据访问类
 * 
 * @author dante
 * @date 2015-09-19 下午10:04:06
 *
 */
public class EmployeeRelationDao extends AbstractDao<EmployeeRelationBean> {

    /**
     * 新增员工家属信息
     * 
     * @param entity
     * @return EmployeeRelationBean
     */
    @Override
    public EmployeeRelationBean save(EmployeeRelationBean entity) {
        return save("insertEmployeeRelation", entity);
    }

    /**
     * 根据entity删除员工家属信息
     * 
     * @param entity
     */
    public void delete(EmployeeRelationBean entity) {
    	getSqlMapClientTemplate().update("deleteEmployeeRelation", entity);
    }

    /**
     * 修改员工家属信息
     * 
     * @param entity
     * @return int
     */
    @Override
    public int update(EmployeeRelationBean entity) {
        return update("updateEmployeeRelation", entity);
    }

    /**
     * 分页查询员工家属信息
     * 
     * @param pagingForm
     * @param fuzzy
     * @return PagingResult
     */
    @Override
    public PagingResult<EmployeeRelationBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
        return findPaging("queryEmployeeRelationPage", pagingForm, fuzzy);
    }

    /**
     * 查询员工家属信息的所有记录
     * 
     * @return List
     */
    @Override
    public List<EmployeeRelationBean> findAll() {
        return findAll("queryEmployeeRelationList");
    }

    /**
     * 根据Id获取员工家属信息
     * 
     * @param id
     * @return EmployeeRelationBean
     */
    @Override
    public EmployeeRelationBean find(Serializable id) {
        return find("queryEmployeeRelationById", id);
    }
    
    /**
     * 根据empId获取员工家属信息
     * 
     * @param id
     * @return List<EmployeeRelationBean>
     */
    public List<EmployeeRelationBean> queryByEmployeeId(Long empId) {
    	return getSqlMapClientTemplate().queryForList("queryEmployeeRelationByEmpId", empId);
    }
}