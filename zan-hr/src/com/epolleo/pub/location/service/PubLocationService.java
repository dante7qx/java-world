package com.epolleo.pub.location.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.epolleo.bp.pub.AbstractService;
import com.epolleo.pub.location.bean.PubLocationBean;
import com.epolleo.pub.location.dao.ibatis.PubLocationDao;

public class PubLocationService extends AbstractService<PubLocationBean, PubLocationDao> {
	@Resource
	public void setDao(PubLocationDao dao) {
		this.dao = dao;
	}
	
	public List<PubLocationBean> queryLocationList(Map<String, Object> param) {
		return dao.queryLocationList(param);
	}
}
