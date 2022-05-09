package com.epolleo.pub.org.action;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.util.Function;
import com.epolleo.pub.org.bean.PubOrgBean;
import com.epolleo.pub.org.bean.PubOrgTreeBean;
import com.epolleo.pub.org.service.PubOrgService;

@Function()
public class PubOrgAction {

	@Resource
	private PubOrgService service;

	public void doQueryAllTree(Context context) {
		List<PubOrgTreeBean> trees = service.queryOrgTree();
		context.put("json", trees);
	}
	
	public void doQueryTreeByType(Context context, @Param(name="typeId") int typeId, @Param(name="nodeLevel") int nodeLevel) {
		List<PubOrgTreeBean> trees = service.queryOrgTreeByType(typeId, nodeLevel);
		context.put("json", trees);
	}
	
	public void doQueryTreeByPidAndTypeId(Context context, @Param(name="pid") int pid, @Param(name="typeId") int typeId) {
		List<PubOrgBean> orgs = service.queryByPidAndTypeId(pid, typeId);
		context.put("json", orgs);
	}
}
