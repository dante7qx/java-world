/**
 * This file was generated by Data Model Tool
 * See <a href="http://www.hna.net/dmt/schema">http://www.hna.net/dmt/schema</a>
 *
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * HolidayDao.java
 * Date: 2013-12-02
 */
package com.epolleo.bp.holiday.dao.ibatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;

import com.epolleo.bp.holiday.bean.HolidayBean;

/**
 * <p>节假日管理的数据访问类</p>
 * 
 * Date: 2013-12-02 下午01:36:49
 * 
 * @author TODO
 * @version 1.0
 */
public class HolidayDao extends AbstractDao<HolidayBean> {

    /**
     * @param entity 新增节假日管理对象
     * @return 新增成功的节假日管理
     * @see HolidayBean
     */
    @Override
    public HolidayBean save(HolidayBean entity) {
        return save("insertHoliday", entity);
    }

    /**
     * @param entityId 待删除节假日管理的关键值（比如主键Key）
     * @return 如果删除成功，返回1
     */
    @Override
    public int delete(Serializable entityId) {
        return delete("deleteHoliday", entityId);
    }

    /**
     * @param entity 修改节假日管理对象
     * @return 如果修改成功，返回1
     * @see HolidayBean
     */
    @Override
    public int update(HolidayBean entity) {
        return update("updateHoliday", entity);
    }

    /**
     * @param pagingForm 分页及查询过滤参数
     * @return 指定页及条件的查询结果
     * @see PagingForm
     */
    @Override
    public PagingResult<HolidayBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
        return findPaging("queryHolidayPage", pagingForm, fuzzy);
    }

    /**
     * @return 查询节假日管理的所有记录
     * @see HolidayBean
     */
    @Override
    public List<HolidayBean> findAll() {
        return findAll("queryHolidayList");
    }

    /**
     * @return 返回指定关键值（比如主键Key）的节假日管理
     * @see HolidayBean
     */
    @Override
    public HolidayBean find(Serializable id) {
        return find("queryHolidayById", id);
    }
    
    /**
     * 根据日期查询当月节假日
     * @param filter
     * @return
     */
    public List<HolidayBean> queryByDate(Map<String,Object> filter){
        return (List<HolidayBean>)getSqlMapClientTemplate().queryForList("queryHolidayListByDate", filter);
        
    }
       
    /**
     * 根据年月删除节假日
     * @param yearMonth
     * @return
     */
    public int deleteByYearMonth(Map<String,Object> filter){
        return getSqlMapClientTemplate().delete("deleteByYearMonth", filter);
    }
}