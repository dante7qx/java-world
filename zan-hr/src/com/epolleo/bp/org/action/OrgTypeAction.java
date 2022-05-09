/*-
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 */
package com.epolleo.bp.org.action;

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

import com.epolleo.bp.org.bean.OrgTypeBean;
import com.epolleo.bp.org.service.OrgService;
import com.epolleo.bp.org.service.OrgTypeService;
import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;

/**
 * 组织结构类型controller层
 */
@Function()
@FunctionDir(id="bp.organType", name="组织机构类型")
public class OrgTypeAction extends AbstractAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OrgTypeService orgTypeService;
    @Resource
    private SeqService seqService;
    @Resource
    private OrgService orgService;

    /**
     * 组织结构类型更新
     */
    @Function("bp.organType.update")
    @FunctionName("组织机构类型更新")
    public void doUpdate(Context context, @Params OrgTypeBean orgTypeBean) {
        logger.debug("to update Id : " + orgTypeBean);
        orgTypeService.update(orgTypeBean);
        context.put("json", orgTypeBean);
    }

    /**
     * 组织结构类型查询
     */
    @Function("bp.organType.query")
    @FunctionName("组织机构类型查询")
    public void doQuery(Context context, @Params PagingForm form,
        @Params OrgTypeBean orgTypeBean) {
        form.addFilter("orgTypeId", orgTypeBean.getOrgTypeId());
        form.addFilter("orgType", orgTypeBean.getOrgType());
        PagingResult<OrgTypeBean> result = orgTypeService.findPaging(form);
        context.put("json", result);
    }

    /**
     * 组织机构类型保存
     */
    @Function("bp.organType.save")
    @FunctionName("组织机构类型保存")
    public void doSave(Context context, @Params OrgTypeBean orgTypeBean) {
        logger.debug("to Save Id Gen : " + orgTypeBean);
        // 生成记录id
        orgTypeBean.setOrgTypeId(seqService.getNewIdString(IdKind.OrgTypeId));
        orgTypeService.save(orgTypeBean);
        context.put("json", orgTypeBean);
    }

    /**
     * 组织结构类型删除
     */
    @Function("bp.organType.delete")
    @FunctionName("组织机构类型删除")
    public void doDelete(Context context, @Param(name = "id") String id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        int delete = orgTypeService.delete(id);
        result.put("success", delete == 1);
        context.put("json", result);
    }

    /**
     * 检查该组织机构类型是否已经被使用
     */
    @Function()
    public void doCheckRelation(Context context, @Param(name = "id") int id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        boolean used = orgService.checkRelation(id);
        if (used) {
            result.put("success", "no");
            context.put("json", result);
        } else {
            result.put("success", "yes");
            context.put("json", result);
        }
    }
}
