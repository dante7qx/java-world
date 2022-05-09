/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * OrgService.java
 * Date: 2012-6-28
 */
package com.epolleo.bp.org.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epolleo.bp.org.bean.OrgBean;
import com.epolleo.bp.org.bean.OrgNodeBean;
import com.epolleo.bp.org.bean.OrganizationNode;
import com.epolleo.bp.org.dao.ibatis.OrgDao;
import com.epolleo.bp.pub.AbstractService;

/**
 * <p>
 * 组织机构管理的业务类
 * </p>
 * 
 * Date: 2012-7-11 下午14:22:33
 * 
 * @author BP
 * @version 1.0
 */
public class OrgService extends AbstractService<OrgBean, OrgDao> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    public void setDao(OrgDao dao) {
        this.dao = dao;
    }

    // 更新父节点
    public int updateParentId(OrgBean orgBean) {
        return dao.updateParentId(orgBean);
    }

    /**
     * 查询组织机构子树，此方法用于机构维护，因此不能利用FULL_ID来计算上下级关系
     */
    public List<OrganizationNode> getOrgNodeTree(Integer parentId, List<Integer> ids, Integer excludeId,
        boolean makeRoot, boolean lazy) {
        OrgNodeBean root = null;
        List<OrganizationNode> tree = new ArrayList<OrganizationNode>();

        Map<Integer, OrgNodeBean> all = dao.getOrgNodeBeanMap();
        if (all.size() < 200) {
            lazy = false;
        }

        List<Integer> path = new ArrayList<Integer>();
        if (ids != null) {
            for (Integer i : ids) {
                for (OrgNodeBean o = all.get(i); o != null;) {
                    if (!path.contains(o.getParentId()))
                        path.add(0, o.getParentId());
                    o = all.get(o.getParentId());
                }
            }
        }
        if (!path.contains(parentId)) {
            path.clear();
            path.add(parentId);
        }
        if (parentId == null) {
            root = new OrgNodeBean();
            root.setOrgName("所有组织机构");
        } else {
            root = all.get(parentId);
            if (root == null) {
                // 非法父节点，返回空tree
                return tree;
            }
        }
        if (lazy) {
            List<Integer> dir = dao.getOrgDir();

            Map<Integer, OrganizationNode> nodes = new HashMap<Integer, OrganizationNode>();
            for (OrgNodeBean o : all.values()) {
                if (path.contains(o.getParentId())) {
                    OrganizationNode n = new OrganizationNode(o);
                    nodes.put(o.getOrgId(), n);
                    if (dir.contains(o.getOrgId())) {
                        n.setState(path.contains(o.getOrgId()));
                    }
                }
            }
            OrganizationNode pn = new OrganizationNode(root);
            nodes.put(root.getOrgId(), pn);

            for (OrgNodeBean o : all.values()) {
                if (excludeId != null && (excludeId.equals(o.getOrgId()) || excludeId.equals(o.getParentId()))) {
                    continue;
                }
                OrganizationNode parent = nodes.get(o.getParentId());
                if (parent != null && nodes.get(o.getOrgId()) != null) {
                    parent.addChild(nodes.get(o.getOrgId()));
                }
            }
            if (makeRoot) {
                tree.add(pn);
                pn.setState(true);
                if (parentId == null) {
                    root.setOrgId(-1);
                }
            } else {
                if (pn.getChildren() == null) {
                    return new ArrayList<OrganizationNode>();
                }
                return pn.getChildren();
            }
        } else {
            Map<Integer, OrganizationNode> nodes = new HashMap<Integer, OrganizationNode>();
            for (OrgNodeBean o : all.values()) {
                nodes.put(o.getOrgId(), new OrganizationNode(o));
            }
            OrganizationNode pn = new OrganizationNode(root);
            nodes.put(root.getOrgId(), pn);

            for (OrgNodeBean o : all.values()) {
                if (excludeId != null && (excludeId.equals(o.getOrgId()) || excludeId.equals(o.getParentId()))) {
                    continue;
                }
                OrganizationNode parent = nodes.get(o.getParentId());
                if (parent != null) {
                    parent.addChild(nodes.get(o.getOrgId()));
                }
            }
            for (Integer i : path) {
                OrganizationNode n = nodes.get(i);
                if (n != null) {
                    n.setState(true);
                }
            }
            //tree.add(nodes.get(parentId));
            if (makeRoot) {
                tree.add(pn);
                pn.setState(true);
                if (parentId == null) {
                    root.setOrgId(-1);
                }
            } else {
                if (pn.getChildren() == null) {
                    return new ArrayList<OrganizationNode>();
                }
                return pn.getChildren();
            }
        }
        return tree;
    }

    public List<OrganizationNode> getChildOrganizationForest(Integer parentId) {
        OrgNodeBean orgBean = new OrgNodeBean();
        orgBean.setOrgName("所在组织机构");
        List<OrgNodeBean> orgBeans = null;
        if (parentId != null) {
            orgBeans = dao.queryAllChild(parentId);
            orgBean.setOrgName(dao.find(parentId).getOrgName());
            orgBean.setOrgId(parentId.intValue());
        } else {
            // orgBeans = dao.findAll();
            return null;
        }
        OrganizationNode root = getOrganizationTree(orgBeans, new OrganizationNode(orgBean));
        orgBean.setOrgId(parentId == null ? -1 : parentId.intValue());
        List<OrganizationNode> tree = new ArrayList<OrganizationNode>();
        tree.add(root);
        return tree;
    }

    private OrganizationNode getOrganizationTree(List<OrgNodeBean> orgBeans, OrganizationNode node) {
        if (node == null) {
            return null;
        }
        if (orgBeans != null && !orgBeans.isEmpty()) {
            Map<Integer, OrganizationNode> nodes = new HashMap<Integer, OrganizationNode>();
            for (OrgNodeBean orgBean : orgBeans) {
                nodes.put(orgBean.getOrgId(), new OrganizationNode(orgBean));
            }
            nodes.put(node.getId(), node);

            for (OrgNodeBean orgBean : orgBeans) {
                OrganizationNode parent = nodes.get(orgBean.getParentId());
                if (parent != null) {
                    parent.addChild(nodes.get(orgBean.getOrgId()));
                }
            }
        }
        return node;
    }

    // 获得子节点
    public List<OrgBean> getOrganizationChildren(int parentId) {
        return dao.queryChild(parentId);
    }

    // 检查该组织结构类型是否正在使用
    public boolean checkRelation(int id) {
        Integer c = dao.countUsedOrgTypeId(id);
        return c != null && c.intValue() > 0;
    }

    /**
     * 根据指定组织id获取组织详细信息
     */
    public OrgBean getOrgById(int orgId) {
        return dao.find(orgId);
    }

    public OrgNodeBean queryOrgByCode(String orgCode) {
        return dao.queryOrgByCode(orgCode);
    }
    
    /**
     * 查询所有顶级节点
     */
    public List<OrgBean> queryAllRootOrgNode() {
    	return dao.queryAllRootOrgNode();
    }
    
    /**
     * 获取制定typeId下的所有节点
     * 
     * @param typeId
     * @return
     */
    public List<OrgBean> queryOrgNodeByTypeId(int typeId) {
    	return dao.queryOrgNodeByTypeId(typeId);
    }
    
    /**
     * 获取制定pid和typeId下的所有节点
     * 
     * @param pid
     * @param typeId
     * @return
     */
    public List<OrgBean> queryOrgNodeByPidAndTypeId(int pid, int typeId) {
    	return dao.queryOrgNodeByPidAndTypeId(pid, typeId);
    }
    
    public int syncOrg() {
        Map<Integer, Integer> link = dao.getOrgRelationMap();
        Map<Integer, String> result = new HashMap<Integer, String>();

        for (Integer id : link.keySet()) {
            calcFullId(link, result, id);
        }

        for (Integer orgId : result.keySet()) {
            dao.updateOrgFullId(orgId, result.get(orgId));
        }
        return link.size();
    }

    String calcFullId(Map<Integer, Integer> link, Map<Integer, String> result, Integer oid) {
        String fid = result.get(oid);
        if (fid == null) {
            Integer pid = link.get(oid);
            if (pid == null) {
                fid = ":" + oid + ":";
                result.put(oid, fid);
                return fid;
            } else {
                String fpid = calcFullId(link, result, pid);
                fid = fpid + oid + ":";
                result.put(oid, fid);
                return fid;
            }
        }
        return fid;
    }

    /*-
    public List<OrganizationSimpNode> getChildOrganizationForest(
        Set<Integer> keySet) {
        List<OrganizationSimpNode> tree = new ArrayList<OrganizationSimpNode>();
        Map<String, Integer> idvsFullid = new HashMap<String, Integer>();
        List<Integer> uniqKey = new ArrayList<Integer>();
        if (keySet != null && keySet.size() != 0) {
            for (Integer id : keySet) {
                idvsFullid.put(dao.getOrgFullId(id), id);
                uniqKey.add(id);
            }
            Set<String> fullIdList = idvsFullid.keySet();
            for (String fullId : fullIdList) {
                for (String id : fullIdList) {
                    if (fullId.contains(id) && !fullId.equals(id)) {
                        uniqKey.remove(idvsFullid.get(fullId));
                        break;
                    }
                }
            }
            for (Integer id : uniqKey) {
                OrgSimpBean orgBean = new OrgSimpBean();
                orgBean.setOrgName("所在组织机构");
                List<OrgSimpBean> orgBeans = null;
                orgBeans = dao.queryAllChild(id);
                orgBean.setOrgName(dao.find(id).getOrgName());
                orgBean.setOrgId(id);
                OrganizationSimpNode root = getOrganizationSimpTree(orgBeans,
                    new OrganizationSimpNode(orgBean));
                orgBean.setOrgId(id == null ? -1 : id);
                tree.add(root);
            }
            return tree;
        } else {
            return tree;
        }
    }
     */

    public ArrayList<OrgNodeBean> doQueryChildBeanList(Integer orgId) {
        ArrayList<OrgNodeBean> orgList = new ArrayList<OrgNodeBean>();
        orgList.addAll(dao.queryAllChild(orgId));
        return orgList;
    }
}
