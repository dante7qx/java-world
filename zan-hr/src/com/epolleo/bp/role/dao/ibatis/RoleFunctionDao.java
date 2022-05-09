/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * RoleAction.java
 * Date: 2012-06-29 15:45:03 +0800  
 */
package com.epolleo.bp.role.dao.ibatis;

import java.sql.SQLException;
import java.util.Set;

import javax.annotation.Resource;

import org.ibatis.client.SqlMapClient;
import org.ibatis.spring.support.SqlMapClientDaoSupport;

/**
 * RoleFunctionDao.java
 */
public class RoleFunctionDao extends SqlMapClientDaoSupport {

    @Resource
    public void init(SqlMapClient sqlMapClient) {
        this.setSqlMapClient(sqlMapClient);
    }

    @Deprecated
    public void insertRoleFunction(int roleId, String func) {
        getSqlMapClientTemplate().insertArgs("insertRoleFunction", roleId, func);
    }

    public void deleteRoleFunctionByRoleId(int roleId) {
        getSqlMapClientTemplate().delete("deleteRoleFunctionByRoleId", roleId);
    }

    public void insertRoleFunctions(int roleId, Set<String> fset) {
        try {
            getSqlMapClientTemplate().getSqlMapClient().startBatch();
            int count = 0;
            for (String f : fset) {
                getSqlMapClientTemplate().getSqlMapClient().insertArgs("insertRoleFunction", roleId, f);
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

}