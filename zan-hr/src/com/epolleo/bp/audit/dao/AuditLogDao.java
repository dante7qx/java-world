package com.epolleo.bp.audit.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.epolleo.bp.audit.bean.AuditLog;
import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

public class AuditLogDao extends AbstractDao {

    public PagingResult<AuditLog> findPaging(PagingForm pagingParam) {
        String sort = pagingParam.getSort();
        String order = pagingParam.getOrder();
        if (sort != null) {
            checkName(sort);
        }
        if (order != null) {
            checkName(order);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("sort", pagingParam.getSort());
        params.put("order", pagingParam.getOrder());
        params.putAll(pagingParam.getFilter());
        ArrayList<AuditLog> page = new ArrayList<AuditLog>();
        int total = getSqlMapClientTemplate().queryForPage(page,
            "selectAuditLog", params, pagingParam.getSkip(),
            pagingParam.getPageSize());
        PagingResult<AuditLog> result = new PagingResult<AuditLog>(page, total);
        return result;
    }

    public AuditLog saveAuditLog(AuditLog auditLog) {
        return (AuditLog) getSqlMapClientTemplate().insert("insertAuditLog",
            auditLog);
    }
}
