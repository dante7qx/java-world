package com.epolleo.hr.dictmnt.dao.ibatis;

import java.io.Serializable;
import java.util.List;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.hr.dictmnt.bean.CodeDictBean;

/**
 * @Description: 基础数据字典表的数据访问类
 * 
 * @author dante
 * @date 2015-09-23 下午11:45:59
 *
 */
public class CodeDictDao extends AbstractDao<CodeDictBean> {

    /**
     * 新增基础数据字典表
     * 
     * @param entity
     * @return CodeDictBean
     */
    @Override
    public CodeDictBean save(CodeDictBean entity) {
        return save("insertCodeDict", entity);
    }

    /**
     * 根据entityId删除基础数据字典表
     * 
     * @param entity
     * @return int
     */
    @Override
    public int delete(Serializable entityId) {
        return delete("deleteCodeDict", entityId);
    }

    /**
     * 修改基础数据字典表
     * 
     * @param entity
     * @return int
     */
    @Override
    public int update(CodeDictBean entity) {
        return update("updateCodeDict", entity);
    }

    /**
     * 分页查询基础数据字典表
     * 
     * @param pagingForm
     * @param fuzzy
     * @return PagingResult
     */
    @Override
    public PagingResult<CodeDictBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
        return findPaging("queryCodeDictPage", pagingForm, fuzzy);
    }

    /**
     * 查询基础数据字典表的所有记录
     * 
     * @return List
     */
    @Override
    public List<CodeDictBean> findAll() {
        return findAll("queryCodeDictList");
    }

    /**
     * 根据Id获取基础数据字典表
     * 
     * @param id
     * @return CodeDictBean
     */
    @Override
    public CodeDictBean find(Serializable id) {
        return find("queryCodeDictById", id);
    }
    
    /**
     * 根据Type获取基础数据字典表
     * 
     * @param type
     * @return
     */
    public List<CodeDictBean> queryByType(String type) {
    	return getSqlMapClientTemplate().queryForList("queryCodeDictByType", type);
    }
}