/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * OrgAction.java
 * Date: 2012-6-28
 */
package com.epolleo.bp.org.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

import com.epolleo.bp.org.bean.OrgBean;
import com.epolleo.bp.org.bean.OrgNodeBean;
import com.epolleo.bp.org.bean.OrgTypeBean;
import com.epolleo.bp.org.bean.OrganizationNode;
import com.epolleo.bp.org.service.OrgService;
import com.epolleo.bp.org.service.OrgTypeService;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.CnToSpellHelper;

/**
 * <p>
 * 组织机构的增删改，以及维护组织机构之间的父子关联
 * </p>
 * 
 * @version 1.0
 */
@Function()
@FunctionDir(id="bp.organ", name="组织机构")
public class OrgAction {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OrgService orgService;
    @Resource
    private SeqService seqService;
    @Resource
    private OrgTypeService orgTypeService;

    /**
     * 查询所有的组织结构
     */
    @Function("public")
    public void doQuery(Context context, @Param(name = "parentId") Integer parentId,
        @Param(name = "orgId") Integer orgId, @Param(name = "orgIds") String orgIds,
        @Param(name = "excludeId") Integer excludeId, @Param(name = "root") Boolean root,
        @Param(name = "lazy") Boolean lazy) {
        List<Integer> ids = new ArrayList<Integer>();
        if (orgId != null) {
            ids.add(orgId);
        }
        if (orgIds != null) {
            StringTokenizer st = new StringTokenizer(orgIds, ",");
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                try {
                    int i = Integer.parseInt(s);
                    if (!ids.contains(i)) {
                        ids.add(i);
                    }
                } catch (Exception e) {
                }
            }
        }
        List<OrganizationNode> tree = orgService.getOrgNodeTree(parentId, ids, excludeId, Boolean.TRUE.equals(root),
            Boolean.TRUE.equals(lazy));
        context.put("json", tree);
    }

    /**
     * 查询所有的组织结构
     */
    @Function("public")
    public void doQueryOrgByCurrentUser(Context context) {
        LoginUser user = LoginUser.getCurrentUser();
        Integer parentId = user.getOrganizationId();
        List<OrganizationNode> tree = new ArrayList<OrganizationNode>();
        // 可能有user没有对应的employee关联
        if (parentId == null) {
            context.put("json", tree);
            return;
        }
        tree = orgService.getChildOrganizationForest(parentId);
        context.put("json", tree);
    }

    /**
     * 查询所有的组织结构
     */
    @Function("public")
    public void doQueryOrgByUser(Context context, @Param(name = "orgId") Integer orgId,
        @Param(name = "orgIds") String orgIds, @Param(name = "root") Boolean root, @Param(name = "lazy") Boolean lazy) {

        LoginUser user = LoginUser.getCurrentUser();
        Integer parentId = user.getOrganizationId();
        List<OrganizationNode> tree = new ArrayList<OrganizationNode>();
        // 可能有user没有对应的employee关联
        if (parentId == null) {
            context.put("json", tree);
            return;
        }
        List<Integer> ids = new ArrayList<Integer>();
        if (orgId != null) {
            ids.add(orgId);
        }
        if (orgIds != null) {
            StringTokenizer st = new StringTokenizer(orgIds, ",");
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                try {
                    int i = Integer.parseInt(s);
                    if (!ids.contains(i)) {
                        ids.add(i);
                    }
                } catch (Exception e) {
                }
            }
        }
        tree = orgService.getOrgNodeTree(parentId, ids, null, Boolean.TRUE.equals(root), Boolean.TRUE.equals(lazy));
        context.put("json", tree);
    }

    /**
     * 查询指定父节点下的所有组织结构
     */
    @Function("bp.organ.query")
    public void doQueryChildTree(Context context) {
        if (LoginUser.getCurrentUser().getOrganizationId() == null) {
            context.put("json", null);
            return;
        }
        List<OrganizationNode> tree = orgService.getChildOrganizationForest(LoginUser.getCurrentUser()
            .getOrganizationId());
        context.put("json", tree);
    }

    /**
     * 查询指定parentId下的孩子节点
     */
    @Function("bp.organ.query")
    @FunctionName("组织机构查询")
    public void doQueryChild(Context context, @Param(name = "parentId") int parentId) {
        List<OrgBean> children = orgService.getOrganizationChildren(parentId);
        context.put("json", children);
    }

    /**
     * 根据前台传过来的orgId是否为空，判断调用新增方法还是修改方法
     */
    @Function("bp.organ.update")
    @FunctionName("组织机构更新")
    public void doSave(Context context, @Params OrgBean orgBean) {
        if (orgBean.getOrgId() != null) {
            logger.debug("Update bean : " + orgBean);
            orgService.update(orgBean);
        } else {
            logger.debug("Save bean : " + orgBean);
            orgBean.setOrgId((int) seqService.getNewId(IdKind.OrgId));
            if (orgBean.getOrgPinYin() == null || orgBean.getOrgPinYin().equals("")) {
                String strPinYin = CnToSpellHelper.getPinYinHeadChar(orgBean.getOrgName());
                orgBean.setOrgPinYin(strPinYin);
            }
            orgService.save(orgBean);
        }
        context.put("json", orgBean);
    }

    /**
     * 删除组织机构
     * 
     * @param context
     * @param id
     * @date：2012-07-12 10:58:52 +0800
     */
    @Function("bp.organ.delete")
    @FunctionName("组织机构删除")
    public void doDelete(Context context, @Param(name = "id") Integer id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            int delete = orgService.delete(id);
            result.put("success", delete == 1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("success", false);
        }
        context.put("json", result);
    }

    /**
     * 更新组织机构所属父节点
     * 
     * @param context
     * @param orgBean
     * @date：2012-07-12 10:57:58 +0800
     */
    @Function("bp.organ.update")
    @FunctionName("组织机构更新")
    public void doUpdateParentId(Context context, @Params OrgBean orgBean) {
        orgService.updateParentId(orgBean);
        context.put("json", orgBean);
    }

    /**
     * 返回组织结构类型表中所有的组织结构类型
     */
    @Function("bp.organ.query")
    public void doFindTypeList(Context context) {
        List<OrgTypeBean> grids = orgTypeService.findAll();
        context.put("json", grids);
    }

    /**
     * 根据组织id获得该组织机构信息
     */
    public void doGet(Context context, @Param(name = "orgId") int orgId) {
        OrgBean result = orgService.getOrgById(orgId);
        context.put("json", result);
    }

    @Function("bp.organ.resync")
    @FunctionName("组织机构统计")
    public void doSync(Context context) {
        int count = orgService.syncOrg();
        context.put("json", count);
    }

    @Function("bp.organ.query")
    public void doQueryChildBeanList(Context context) {
        Integer orgId = LoginUser.getCurrentUser().getOrganizationId();
        if (orgId == null ) {
            context.put("json", new ArrayList<OrgNodeBean>());
            return;
        }
        context.put("json", orgService.doQueryChildBeanList(orgId));
    }
}
