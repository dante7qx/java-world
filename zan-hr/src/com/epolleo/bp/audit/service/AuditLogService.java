package com.epolleo.bp.audit.service;

import javax.annotation.Resource;

import com.epolleo.bp.audit.bean.AuditLog;
import com.epolleo.bp.audit.dao.AuditLogDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

public class AuditLogService {

    @Resource
    private AuditLogDao auditLogDao;

    public PagingResult<AuditLog> findPaging(PagingForm pagingParam) {
        return auditLogDao.findPaging(pagingParam);
    }

    public AuditLog saveAuditLog(AuditLog auditLog) {
        return this.auditLogDao.saveAuditLog(auditLog);
    }
}
