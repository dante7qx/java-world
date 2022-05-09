/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * OrgDao.java
 * Date: 2012-6-28
 */
package com.epolleo.bp.org.dao.ibatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epolleo.bp.org.bean.OrgBean;
import com.epolleo.bp.org.bean.OrgNodeBean;
import com.epolleo.bp.pub.AbstractDao;

/**
 * <p>
 * 组织机构管理的数据访问类（iBatis）
 * </p>
 * 
 * @author BP
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class OrgDao extends AbstractDao<OrgBean> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public int update(OrgBean orgBean) {
        return update("updateOrg", orgBean);
    }

    @Override
    public OrgBean save(OrgBean orgBean) {
        return save("insertOrg", orgBean);
    }

    @Override
    public int delete(Serializable id) {
        return delete("deleteOrg", id);
    }

    @Override
    public OrgBean find(Serializable entityId) {
        return find("queryOrgById", entityId);
    }

    // 查询已经存在的组织机构
    public List<OrgNodeBean> findAllOrgSimpBean() {
        return (List<OrgNodeBean>) getSqlMapClientTemplate().queryForList(
            "queryOrgSimple");
    }

    /**
     * 修改组织机构的父子关系
     */
    public int updateParentId(OrgBean orgBean) {
        return update("updateOrgParent", orgBean);
    }

    /**
     * 查询指定parentId下的孩子节点
     */
    public List<OrgBean> queryChild(int parentId) {
        List<OrgBean> result = (List<OrgBean>) getSqlMapClientTemplate()
            .queryForList("queryOrgChildAndType", parentId);
        return result;
    }
    
    /**
     * 查询所有顶级节点
     */
    public List<OrgBean> queryAllRootOrgNode() {
    	return getSqlMapClientTemplate().queryForList("queryAllRootOrgNode");
    }
    
    /**
     * 获取制定typeId下的所有节点
     * 
     * @param typeId
     * @return
     */
    public List<OrgBean> queryOrgNodeByTypeId(int typeId) {
    	return getSqlMapClientTemplate().queryForList("queryOrgNodeByTypeId", typeId);
    }
    
    /**
     * 获取制定pid和typeId下的所有节点
     * 
     * @param pid
     * @param typeId
     * @return
     */
    public List<OrgBean> queryOrgNodeByPidAndTypeId(int pid, int typeId) {
    	return (List<OrgBean>) getSqlMapClientTemplate().queryForListArgs("queryOrgNodeByPidAndTypeId", pid, typeId);
    }
    
    /**
     * 查询所有组织机构，排除自身ID或父ID为excludeId的组织机构
     */
    public List<OrgNodeBean> queryExcludeId(int excludeId) {
        List<OrgNodeBean> result = (List<OrgNodeBean>) getSqlMapClientTemplate()
            .queryForList("querySimpleOrgExcludeId", excludeId);
        return result;
    }

    // 查询该机构类型是否正在被使用
    public Integer countUsedOrgTypeId(int orgTypeId) {
        return (Integer) getSqlMapClientTemplate().queryForObject(
            "countUsedOrgTypeId", orgTypeId);
    }

    public List<OrgNodeBean> queryAllChild(Integer parentId) {
        return (List<OrgNodeBean>) getSqlMapClientTemplate().queryForList(
            "queryOrgBeanTreeFromId", "%:" + parentId + ":%");
    }
    
    public OrgNodeBean queryOrgByCode(String orgCode) {
        return (OrgNodeBean) getSqlMapClientTemplate().queryForObject(
            "queryOrgByCode", orgCode);
    }

    public Map<Integer, Integer> getOrgRelationMap() {
        return (Map<Integer, Integer>) getSqlMapClientTemplate().queryForMap(
            "getOrgRelationMap", null, "orgId", "parentId");
    }

    public List<Integer> getOrgDir() {
        return (List<Integer>) getSqlMapClientTemplate().queryForList(
            "getOrgDir");
    }

    public Map<Integer, OrgNodeBean> getOrgNodeBeanMap() {
        return (Map<Integer, OrgNodeBean>) getSqlMapClientTemplate()
            .queryForMap("getOrgRelationMap", null, "orgId");
    }

    public void updateOrgFullId(Integer orgId, String fullId) {
        getSqlMapClientTemplate().updateArgs("updateOrgFullId", orgId, fullId);
    }
}
