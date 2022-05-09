package com.epolleo.pub.location.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.util.Function;
import com.epolleo.pub.location.bean.PubLocationBean;
import com.epolleo.pub.location.service.PubLocationService;

@Function()
public class PubLocationAction {

	@Resource
	private PubLocationService service;
	
	public void doQueryCombo(Context context, @Param(name="id") Long id, @Param(name="isNew") Boolean isNew) {
		Map<String, Object> param = new HashMap<String, Object>();
		if(isNew != null && isNew) {
			param.put("isNew", 1);
		} else if(isNew != null && !isNew) {
			param.put("isNew", 0);
		}
		if(id != null) {
			param.put("id", id);
		}
		List<PubLocationBean> list = service.queryLocationList(param);
		context.put("json", list);
	}
}
