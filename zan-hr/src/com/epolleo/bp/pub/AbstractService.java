/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * AbstractService.java
 * Date: 2012-7-11
 */
package com.epolleo.bp.pub;

import java.io.Serializable;
import java.util.List;

/**
 * <p>支持通用的增删改查</p>
 * 
 * Date: 2012-7-11 下午17:03:57
 * 
 * @author BP
 * @version 1.0
 */
public abstract class AbstractService<E, D extends GenericDao<E>> {

	protected D dao;

    /**
     * <p>保存功能。</p>
     * @param entity 待新增的数据实体
     * @return 新增成功后的实体对象
     */
	public E save(E entity) {
		return dao.save(entity);
	}

    /**
     * <p>删除功能。</p>
     * @param entityId
     *            待删除数据的关键值（比如主键Key）
     * @return 如果删除成功，返回1
     */
	public int delete(Serializable id) {
		return dao.delete(id);
	}

    /**
     * <p>更新功能。</p>
     * @param entity
     *            待更新的数据实体，对象须为BaseBean的子类
     * @return 如果更新成功，返回1
     */
	public int update(E entity) {
		return dao.update(entity);
	}

    /**
     * <p>分页及条件查询，默认为模糊查询。</p>
     * @param pagingForm
     *            分页及查询过滤参数
     * @return 指定页的查询结果
     */
	public PagingResult<E> findPaging(PagingForm pagingForm) {
		return dao.findPaging(pagingForm, true);
	}

    /**
     * <p>查询所有记录。</p>
     * @return 所有记录
     */
	public List<E> findAll() {
		return dao.findAll();
	}

    /**
     * <p>查询单条记录。</p>
     * @param id
     *            查询数据的关键值
     * @return 单条记录
     */
	public E find(Serializable id) {
		return dao.find(id);
	}
}
