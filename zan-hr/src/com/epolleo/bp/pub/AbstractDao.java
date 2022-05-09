/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved. 
 * AbstractService.java
 * Date: 2012-7-11
 */
package com.epolleo.bp.pub;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.ibatis.client.SqlMapClient;
import org.ibatis.spring.support.SqlMapClientDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epolleo.bp.util.DateUtils;

/**
 * <p>
 * 支持通用的增删改查
 * </p>
 * 
 * Date: 2012-7-11 下午17:01:25
 * 
 * @author BP
 * @version 1.0
 */
public abstract class AbstractDao<E> extends SqlMapClientDaoSupport implements
    GenericDao<E> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    public void init(SqlMapClient sqlMapClient) {
        this.setSqlMapClient(sqlMapClient);
    }

    private Pattern namePattern = Pattern.compile("\\w+");

    public final void checkName(String token) {
        Matcher matcher = namePattern.matcher(token);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("invalid chars: " + token);
        }
    }

    /**
     * <p>
     * 保存功能。
     * </p>
     * <p>
     * 如果Dao实现类没有覆盖此方法，说明不支持此类保存操作，将抛出{@link UnsupportedOperationException}
     * </p>
     * 
     * @param entity
     *            待新增的数据实体
     * @return 新增成功后的实体对象
     */
    public E save(E entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param sqlMapId
     *            insert SQL Map Id
     * @param entity
     *            待新增的数据实体
     * @return 新增成功后的实体对象
     * @see #save(String, BaseBean)
     */
    @SuppressWarnings("unchecked")
    protected <T> T save(String sqlMapId, T entity) {
        return (T) getSqlMapClientTemplate().insert(sqlMapId, entity);
    }

    /**
     * 新增数据时，统一辅助设置“新增及更新的操作者及时间戳”
     * 
     * @param sqlMapId
     *            insert SQL Map Id
     * @param entity
     *            待新增的数据实体，对象须为BaseBean的子类
     * @return 新增成功后的实体对象
     * @see #save(String, Object)
     */
    @SuppressWarnings("unchecked")
    protected <T extends BaseBean> T save(String sqlMapId, T entity) {
        LoginUser loginUser = LoginUser.getCurrentUser();
        Date date = DateUtils.getCurrentDate();

        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        if (loginUser != null) {
            String userName = loginUser.getUserId();
            entity.setCreateUser(userName);
            entity.setUpdateUser(userName);
        }

        return (T) getSqlMapClientTemplate().insert(sqlMapId, entity);
    }

    /**
     * <p>
     * 删除功能。
     * </p>
     * <p>
     * 如果Dao实现类没有覆盖此方法，说明不支持此类删除操作，将抛出{@link UnsupportedOperationException}
     * </p>
     * 
     * @param entityId
     *            待删除数据的关键值（比如主键Key）
     * @return 如果删除成功，返回1
     */
    public int delete(Serializable entityId) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param sqlMapId
     *            delete SQL Map Id
     * @param entityId
     *            待删除数据的关键值（比如主键Key）
     * @return 如果删除成功，返回1
     */
    protected int delete(String sqlMapId, Serializable entityId) {
        getSqlMapClientTemplate().delete(sqlMapId, entityId, 1);
        return 1;
    }

    /**
     * <p>
     * 更新功能。
     * </p>
     * <p>
     * 如果Dao实现类没有覆盖此方法，说明不支持此类更新操作，将抛出{@link UnsupportedOperationException}
     * </p>
     * 
     * @param entity
     *            待更新的数据实体，对象须为BaseBean的子类
     * @return 如果更新成功，返回1
     */
    public int update(E entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param sqlMapId
     *            update SQL Map Id
     * @param entity
     *            待更新的数据实体
     * @return 如果更新成功，返回1
     * @see #update(String, BaseBean)
     */
    protected <T> int update(String sqlMapId, T entity) {
        getSqlMapClientTemplate().update(sqlMapId, entity, 1);
        return 1;
    }

    /**
     * 更新数据时，统一辅助设置“更新的操作者及时间戳”
     * 
     * @param sqlMapId
     *            update SQL Map Id
     * @param entity
     *            待更新的数据实体，对象须为BaseBean的子类
     * @return 如果更新成功，返回1
     * @see #update(String, Object)
     */
    protected <T extends BaseBean> int update(String sqlMapId, T entity) {
        LoginUser loginUser = LoginUser.getCurrentUser();
        Date date = DateUtils.getCurrentDate();

        entity.setUpdateTime(date);
        if (loginUser != null) {
            String userName = loginUser.getUserId();
            entity.setUpdateUser(userName);
        }

        getSqlMapClientTemplate().update(sqlMapId, entity, 1);
        return 1;
    }

    /**
     * <p>
     * 分页及条件查询。
     * </p>
     * <p>
     * 如果Dao实现类没有覆盖此方法，说明不支持此类分页及条件查询的操作，将抛出
     * {@link UnsupportedOperationException}
     * </p>
     * 
     * @param pagingForm
     *            分页及查询过滤参数
     * @param fuzzy
     *            是否模糊查询
     * @return 指定页的查询结果
     */
    public PagingResult<E> findPaging(PagingForm pagingForm, boolean fuzzy) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param sqlMapId
     *            select SQL Map Id （带排序分页参数的select SQL语句）
     * @param pagingForm
     *            分页及查询过滤参数
     * @param fuzzy
     *            是否模糊查询
     * @return 指定页的查询结果
     */
    protected <T> PagingResult<T> findPaging(String sqlMapId,
        PagingForm pagingForm, boolean fuzzy) {
        String sort = pagingForm.getSort();
        String order = pagingForm.getOrder();
        if (sort != null) {
            checkName(sort);
        }
        if (order != null) {
            checkName(order);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("sort", pagingForm.getSort());
        params.put("order", pagingForm.getOrder());
        if (fuzzy) {
            for (String key : pagingForm.getFilter().keySet()) {
                Object value = pagingForm.getFilter().get(key);
                if (value != null && value instanceof String
                    && value.toString().indexOf('%') == -1) {
                    pagingForm.addFilter(key, "%" + value + '%');
                }
            }
        }
        params.putAll(pagingForm.getFilter());
        ArrayList<T> page = new ArrayList<T>();
        int total = getSqlMapClientTemplate().queryForPage(page, sqlMapId,
            params, pagingForm.getSkip(), pagingForm.getPageSize());
        PagingResult<T> result = new PagingResult<T>(page, total);
        return result;
    }

    /**
     * <p>
     * 查询单条记录。
     * </p>
     * <p>
     * 如果Dao实现类没有覆盖此方法，说明不支持此类查询单条记录的操作，将抛出{@link UnsupportedOperationException}
     * </p>
     * 
     * @param entityId
     *            查询数据的关键值（比如主键Key）
     * @return 单条记录
     */
    public E find(Serializable entityId) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param sqlMapId
     *            查询单条记录 SQL Map Id
     * @param entityId
     *            查询数据的关键值（比如主键Key）
     * @return 单条记录
     */
    @SuppressWarnings("unchecked")
    protected <T> T find(String sqlMapId, Serializable entityId) {
        T entity = (T) getSqlMapClientTemplate().queryForObject(sqlMapId,
            entityId);
        return entity;
    }

    /**
     * <p>
     * 查询所有记录。
     * </p>
     * <p>
     * 如果Dao实现类没有覆盖此方法，说明不支持查询所有记录的操作，将抛出{@link UnsupportedOperationException}
     * </p>
     * 
     * @return 查询SQL的所有记录
     */
    public List<E> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * @param sqlMapId
     *            查询SQL Map Id
     * @return 查询SQL的所有记录
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> findAll(String sqlMapId) {
        return (List<T>) getSqlMapClientTemplate().queryForList(sqlMapId);
    }
}
