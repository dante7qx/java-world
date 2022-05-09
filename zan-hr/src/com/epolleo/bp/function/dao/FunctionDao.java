/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 */
package com.epolleo.bp.function.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.epolleo.bp.function.bean.FunctionBean;
import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.util.DateUtils;

/**
 * FunctionDao
 * <p>
 * Date: 2012-07-10,15:35:47 +0800
 * 
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class FunctionDao extends AbstractDao {

    /**
     * Query Function By Code
     */
    public List<FunctionBean> query(String funcCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("funcCode", funcCode);
        return getSqlMapClientTemplate().queryForList("selectFunction", params);
    }

    /**
     * Update Function
     */
    public int update(FunctionBean functionGen, String userId) {
        Date date = DateUtils.getCurrentDate();
        functionGen.setUpdateTime(date);
        functionGen.setUpdateUser(userId);

        getSqlMapClientTemplate().update("updateFunction", functionGen, 1);
        return 1;
    }

    /**
     * Update a part of Function
     */
    public int updateNote(FunctionBean functionGen, String userId) {
        Date date = DateUtils.getCurrentDate();
        functionGen.setUpdateTime(date);
        functionGen.setUpdateUser(userId);
        getSqlMapClientTemplate().update("updateFunctionNote", functionGen, 1);
        return 1;
    }

    /**
     * Insert Function
     */
    public FunctionBean save(FunctionBean functionGen, String userId) {
        Date date = DateUtils.getCurrentDate();
        functionGen.setCreateTime(date);
        functionGen.setCreateUser(userId);
        functionGen.setUpdateTime(date);
        functionGen.setUpdateUser(userId);

        return (FunctionBean) getSqlMapClientTemplate().insert("insertFunction", functionGen);
    }

    /**
     * Insert Function
     */
    public void saveBatch(List<FunctionBean> fs, String userId) {
        try {
            getSqlMapClientTemplate().getSqlMapClient().startBatch();
            Date date = DateUtils.getCurrentDate();
            int count = 0;
            for (FunctionBean f : fs) {

                f.setCreateTime(date);
                f.setCreateUser(userId);
                f.setUpdateTime(date);
                f.setUpdateUser(userId);

                getSqlMapClientTemplate().getSqlMapClient().insert("insertFunction", f);
                count++;
                if (count % 200 == 0) {
                    getSqlMapClientTemplate().getSqlMapClient().executeBatch();
                    getSqlMapClientTemplate().getSqlMapClient().startBatch();
                }
            }
            getSqlMapClientTemplate().getSqlMapClient().executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete Function
     */
    public int delete(String id) {
        getSqlMapClientTemplate().delete("deleteFunction", id, 1);
        return 1;
    }

    /**
     * Get All Functions List
     */
    public List<FunctionBean> getFuncAll() {
        return getSqlMapClientTemplate().queryForList("selectFuncAll");
    }

    /**
     * Get Functions by UserID
     */
    public Map<String, String> queryFuncByUserId(String userId) {
        return (Map<String, String>) getSqlMapClientTemplate().queryForMap("queryFuncByUserId", userId, "CODE", "NAME");
    }

    /**
     * 返回所有功能的CODE,NAME的Map
     */
    public Map<String, String> getFuncMap() {
        return (Map<String, String>) getSqlMapClientTemplate().queryForMap("queryFuncMap", null, "CODE", "NAME");
    }

    public void updateFuncState(String fId, boolean flag, String userId, Date t) {
        getSqlMapClientTemplate().updateArgs("updateFuncState", fId, flag, userId, t);

    }

    public int updateFuncStateBatch(Map<String, String> fMap, HashSet<String> all, String userId, Date t) {
        int count = 0;
        try {
            getSqlMapClientTemplate().getSqlMapClient().startBatch();
            for (String f : fMap.keySet()) {
                getSqlMapClientTemplate().getSqlMapClient()
                    .updateArgs("updateFuncState", f, all.contains(f), userId, t);
                count++;
                if (count % 200 == 0) {
                    getSqlMapClientTemplate().getSqlMapClient().executeBatch();
                    getSqlMapClientTemplate().getSqlMapClient().startBatch();
                }
            }
            getSqlMapClientTemplate().getSqlMapClient().executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
