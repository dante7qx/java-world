/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * ReportRecordAction.java
 * Date: 2013-02-26
 */
package com.epolleo.bp.report.action;

import java.util.HashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

import com.epolleo.bp.entity.ReportRecord;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.report.service.ReportRecordService;

/**
 * <p>
 * 报表记录的Action
 * </p>
 * 
 * @version 1.0
 */
@Function()
@FunctionDir(id="bp.report", name="报表记录管理")
public class ReportRecordAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ReportRecordService service;

    @Function("bp.report.query")
    @FunctionName("报表记录查询")
    public void doQuery(Context context, @Params PagingForm form) {
        PagingResult<ReportRecord> result = service.findPaging(form);
        context.put("json", result);
    }

    @Function("bp.report.delete")
    @FunctionName("报表记录删除")
    public void doDelete(Context context, @Param(name = "id") Integer id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        int row = service.delete(id);
        result.put("success", row == 1);
        context.put("json", result);
    }

}