/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * AbstractService.java
 * Date: 2012-7-11
 */
package com.epolleo.bp.org.service;

import javax.annotation.Resource;

import com.epolleo.bp.org.bean.OrgTypeBean;
import com.epolleo.bp.org.dao.ibatis.OrgTypeDao;
import com.epolleo.bp.pub.AbstractService;

/**
 * <p>组织机构类型管理的业务类</p>
 * 
 * Date: 2012-7-11 下午15:02:33
 * 
 * @author BP
 * @version 1.0
 */
public class OrgTypeService extends AbstractService<OrgTypeBean, OrgTypeDao> {

    @Resource
	public void setDao(OrgTypeDao dao) {
		this.dao = dao;
	}

}
