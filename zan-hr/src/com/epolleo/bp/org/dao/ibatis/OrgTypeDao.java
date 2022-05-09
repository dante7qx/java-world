/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * OrgDao.java
 * Date: 2012-7-11
 */
package com.epolleo.bp.org.dao.ibatis;

import java.io.Serializable;
import java.util.List;

import com.epolleo.bp.org.bean.OrgTypeBean;
import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

/**
 * <p>组织机构类型管理的数据访问类（iBatis）</p>
 * 
 * Date: 2012-7-11 下午15:02:44
 * 
 * @author BP
 * @version 1.0
 */
public class OrgTypeDao extends AbstractDao<OrgTypeBean> {

    @Override
    public PagingResult<OrgTypeBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
        return findPaging("selectOrgType", pagingForm, fuzzy);
    }

    @Override
    public int update(OrgTypeBean orgTypeBean) {
        return update("updateOrgType", orgTypeBean);
    }

    @Override
    public OrgTypeBean save(OrgTypeBean orgTypeBean) {
        return save("insertOrgType", orgTypeBean);
    }

    @Override
    public int delete(Serializable id) {
        return delete("deleteOrgType", id);
    }

    @Override
    public OrgTypeBean find(Serializable orgTypeId) {
        return find("findTypeById", orgTypeId);
    }

    @Override
    public List<OrgTypeBean> findAll() {
        return findAll("queryOrgTypeList");
    }
}
