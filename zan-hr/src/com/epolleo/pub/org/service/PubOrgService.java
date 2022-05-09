package com.epolleo.pub.org.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.epolleo.bp.org.bean.OrgBean;
import com.epolleo.bp.org.service.OrgService;
import com.epolleo.bp.pub.AbstractService;
import com.epolleo.pub.org.bean.PubOrgBean;
import com.epolleo.pub.org.bean.PubOrgTreeAttrBean;
import com.epolleo.pub.org.bean.PubOrgTreeBean;
import com.epolleo.pub.org.dao.ibatis.PubOrgDao;

public class PubOrgService extends AbstractService<PubOrgBean, PubOrgDao> {
	@Resource
	private OrgService orgService;
	@Resource
	public void setDao(PubOrgDao dao) {
		this.dao = dao;
	}
	
	public List<PubOrgTreeBean> queryOrgTree() {
		List<OrgBean> orgList = orgService.queryAllRootOrgNode();
		if(orgList == null || orgList.isEmpty()) {
			return new ArrayList<PubOrgTreeBean>();
		}
		List<PubOrgTreeBean> trees = new ArrayList<PubOrgTreeBean>();
		for (OrgBean org : orgList) {
			int id = org.getOrgId();
			PubOrgTreeBean tree = this.convertToPubOrgTree(org);
			trees.add(tree);
			this.buildChildTree(tree, id);
		}
		return trees;
	}
	
	public List<PubOrgTreeBean> queryOrgTreeByType(int typeId, int nodeLevel) {
		List<OrgBean> orgList = orgService.queryAllRootOrgNode();
		if(orgList == null || orgList.isEmpty()) {
			return new ArrayList<PubOrgTreeBean>();
		}
		List<PubOrgTreeBean> trees = new ArrayList<PubOrgTreeBean>();
		for (OrgBean org : orgList) {
			int id = org.getOrgId();
			PubOrgTreeBean tree = this.convertToPubOrgTree(org);
			trees.add(tree);
			this.buildChildTree(tree, id, typeId, nodeLevel);
		}
		return trees;
	}
	
	
	public List<PubOrgBean> queryByPidAndTypeId(int pid, int typeId)  {
		List<OrgBean> orgs = orgService.queryOrgNodeByPidAndTypeId(pid, typeId);
		List<PubOrgBean> pubOrgs = new ArrayList<PubOrgBean>();
		if(orgs != null && !orgs.isEmpty()) {
			for (OrgBean org : orgs) {
				pubOrgs.add(this.convertToPubOrgBean(org));
			}
		}
		return pubOrgs;
	}
	
	
	private void buildChildTree(PubOrgTreeBean tree, int pid, int typeId, int nodeLevel) {
		if(nodeLevel == 1) {
			return;
		}
		List<OrgBean> childOrgs = orgService.getOrganizationChildren(pid);
		if(childOrgs != null && !childOrgs.isEmpty()) {
			for (OrgBean org : childOrgs) {
				if(org.getOrgTypeId().intValue() > typeId) {
					continue;
				}
				if(nodeLevel == 2 && org.getOrgTypeId().intValue() != typeId) {
					continue;
				} 
				PubOrgTreeBean childTree = this.convertToPubOrgTree(org);
				tree.addChild(childTree);
				this.buildChildTree(childTree, org.getOrgId(), typeId, nodeLevel - 1);
			}
		}
	}
	
	private void buildChildTree(PubOrgTreeBean tree, int pid) {
		List<OrgBean> childOrgs = orgService.getOrganizationChildren(pid);
		if(childOrgs != null && !childOrgs.isEmpty()) {
			for (OrgBean org : childOrgs) {
				PubOrgTreeBean childTree = this.convertToPubOrgTree(org);
				tree.addChild(childTree);
				this.buildChildTree(childTree, org.getOrgId());
			}
		}
	}
	
	private PubOrgTreeBean convertToPubOrgTree(OrgBean org) {
		PubOrgTreeBean tree = new PubOrgTreeBean();
		tree.setId(org.getOrgId());
		tree.setText(org.getOrgName());
		tree.setState("open");
		
		PubOrgTreeAttrBean attr = new PubOrgTreeAttrBean();
		attr.setCode(org.getOrgCode());
		attr.setTypeId(org.getOrgTypeId());
		attr.setPid(org.getParentId());
		tree.setAttributes(attr);
		
		return tree;
	}
	
	private PubOrgBean convertToPubOrgBean(OrgBean org) {
		PubOrgBean pubOrg = new PubOrgBean();
		pubOrg.setId(org.getOrgId());
		pubOrg.setName(org.getOrgName());
		pubOrg.setCode(org.getOrgCode());
		pubOrg.setPid(org.getParentId());
		pubOrg.setTypeId(org.getOrgTypeId());
		return pubOrg;
		
	}
}
