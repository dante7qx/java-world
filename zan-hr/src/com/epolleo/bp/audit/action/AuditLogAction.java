package com.epolleo.bp.audit.action;

import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;
import com.alibaba.citrus.util.DateUtil;

import com.epolleo.bp.audit.bean.AuditLog;
import com.epolleo.bp.audit.service.AuditLogService;
import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

@Function()
@FunctionDir(id="bp.audit", name="审计")
public class AuditLogAction extends AbstractAction {

    @Resource
    private AuditLogService auditLogService;

    @Function("bp.audit.query")
    @FunctionName("审计查询")
    public void doQuery(Context context, @Params PagingForm form,
        @Params AuditLog auditLog) {
        HashMap<String, Object> filter = new HashMap<String, Object>();
        Object userId = auditLog.getUserId();

        if (userId != null) {
            if (userId instanceof String) {
                filter.put("userId", "%" + userId + "%");
            } else {
                filter.put("userId", userId);
            }
        }
        Object userName = auditLog.getUserName();
        if (userName != null) {
            if (userName instanceof String) {
                filter.put("userName", "%" + userName + "%");
            } else {
                filter.put("userName", userName);
            }
        }
        Date d = auditLog.getSearchTime();
        if (d != null) {
            filter.put("startOperationTime", DateUtil.getStartDate(d));
            filter.put("endOperationTime", DateUtil.getNextDate(d));
        }
        Object moduleName = auditLog.getModuleName();
        if (moduleName != null) {
            if (moduleName instanceof String) {
                filter.put("moduleName", "%" + moduleName + "%");
            } else {
                filter.put("moduleName", moduleName);
            }
        }
        Object funcName = auditLog.getFuncName();
        if (funcName != null) {
            if (funcName instanceof String) {
                filter.put("funcName", "%" + funcName + "%");
            } else {
                filter.put("funcName", funcName);
            }
        }
        form.setFilterMap(filter);
        PagingResult<AuditLog> result = auditLogService.findPaging(form);
        context.put("json", result);
    }
}
