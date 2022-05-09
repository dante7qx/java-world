package com.epolleo.pub.location.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.pub.location.bean.PubLocationBean;

public class PubLocationDao extends AbstractDao<PubLocationBean> {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public List<PubLocationBean> queryLocationList(Map<String, Object> param) {
		return getSqlMapClientTemplate().queryForList("queryHrLocationList",
				param);
	}

}
