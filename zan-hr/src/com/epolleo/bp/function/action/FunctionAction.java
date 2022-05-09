/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 */
package com.epolleo.bp.function.action;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

import com.epolleo.bp.function.bean.FunctionBean;
import com.epolleo.bp.function.bean.FunctionTreeBean;
import com.epolleo.bp.function.service.FunctionService;
import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.LoginUser;

/**
 * FunctionAction
 * <p>
 * Date: 2012-07-10,15:34:38 +0800
 * 
 * @version 1.0
 */
@Function
@FunctionDir(id = "bp.function", name = "功能")
public class FunctionAction extends AbstractAction {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    FunctionService funcService;

    /**
     * update a part of function
     */
    @Function("bp.function.update")
    @FunctionName("功能更新")
    public void doUpdateNote(Context context, @Params FunctionBean fb) {
        funcService.updateNote(fb, LoginUser.getCurrentUser().getUserId());
        context.put("json", fb);
    }

    /**
     * get function
     */
    public void doGet(Context context, @Params FunctionBean fb) {
        List<FunctionBean> result = funcService.query(fb.getFuncCode());

        context.put("json", result);
    }

    /**
     * query Function tree
     */
    @Function("bp.function.query")
    @FunctionName("功能查询")
    public void doQuery(Context context) {
        List<FunctionTreeBean> result = funcService.getFunctionTree();
        context.put("json", result);
    }

    /**
     * delete function
     */
    @Function("bp.function.delete")
    @FunctionName("功能删除")
    public void doDelete(Context context, @Param(name = "funcCode") String funcCode) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        int delete = funcService.delete(funcCode);
        result.put("success", delete == 1);
        context.put("json", result);
    }

    /**
     * synchronize with database
     */
    @Function("bp.function.sync")
    @FunctionName("功能同步")
    public void doSync(Context context) {
        int count = funcService.syncFunction(LoginUser.getCurrentUser().getUserId());
        context.put("json", count);
    }

    /**
     * query functions
     */
    public void doQueryAll(Context context) {
        List<FunctionBean> result = funcService.getAll();
        context.put("json", result);
    }
}
