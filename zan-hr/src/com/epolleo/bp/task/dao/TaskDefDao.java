/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * TaskDefDao.java
 * Date: 2012-11-10
 */
package com.epolleo.bp.task.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.GenericDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.pub.TaskScheduler;
import com.epolleo.bp.task.bean.TaskDefBean;

/**
 * <p>
 * 定时任务的数据访问类
 * </p>
 * 
 * Date: 2012-11-10 PM03:27:08
 * 
 * @author Song Sun
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class TaskDefDao extends AbstractDao<TaskDefBean> implements GenericDao<TaskDefBean>, ServletContextAware {

    /**
     * @param entity
     *            新增定时任务对象
     * @return 新增成功的定时任务
     * @see TaskDefBean
     */
    @Override
    public TaskDefBean save(TaskDefBean entity) {
        getSqlMapClientTemplate().deleteArgs("deleteTaskRun", entity.getTaskId(), getBpId());
        getSqlMapClientTemplate().insert("insertTaskDef", entity);
        entity.bpId = getBpId();
        getSqlMapClientTemplate().insert("insertTaskRun", entity);
        return entity;
    }

    /**
     * @param entityId
     *            待删除定时任务的关键值（比如主键Key）
     * @return 如果删除成功，返回1
     */
    @Override
    public int delete(Serializable entityId) {
        getSqlMapClientTemplate().deleteArgs("deleteTaskRun", entityId, getBpId());
        return delete("deleteTaskDef", entityId);
    }

    /**
     * @param entity
     *            修改定时任务对象
     * @return 如果修改成功，返回1
     * @see TaskDefBean
     */
    @Override
    public int update(TaskDefBean entity) {
        getSqlMapClientTemplate().deleteArgs("deleteTaskRun", entity.getTaskId(), getBpId());
        entity.bpId = getBpId();
        getSqlMapClientTemplate().insert("insertTaskRun", entity);
        return update("updateTaskDef", entity);
    }

    /**
     * @param pagingForm
     *            分页及查询过滤参数
     * @return 指定页及条件的查询结果
     * @see PagingForm
     */
    @Override
    public PagingResult<TaskDefBean> findPaging(PagingForm pagingForm, boolean fuzzy) {
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
        params.put("bpId", getBpId());
        params.put("_bpId_", "%:" + getBpId() + ":%");
        if (fuzzy) {
            for (String key : pagingForm.getFilter().keySet()) {
                Object value = pagingForm.getFilter().get(key);
                if (value != null && value instanceof String && value.toString().indexOf('%') == -1) {
                    pagingForm.addFilter(key, "%" + value + '%');
                }
            }
        }
        params.putAll(pagingForm.getFilter());
        ArrayList<TaskDefBean> page = new ArrayList<TaskDefBean>();
        int total = getSqlMapClientTemplate().queryForPage(page, "queryTaskDefPage", params, pagingForm.getSkip(),
            pagingForm.getPageSize());
        PagingResult<TaskDefBean> result = new PagingResult<TaskDefBean>(page, total);
        return result;
    }

    public List<TaskDefBean> findAllTask(Date date, String bpId) {
        String _bpId_ = "%:" + getBpId() + ":%";
        return (List<TaskDefBean>) getSqlMapClientTemplate().queryForListArgs("findAllTask", date, bpId, _bpId_);
    }

    public void updateTaskLastInfo(TaskDefBean td) {
        if (td.bpId == null) {
            td.bpId = getBpId();
            getSqlMapClientTemplate().insert("insertTaskRun", td);
        } else {
            getSqlMapClientTemplate().update("updateTaskRunLastInfo", td);
        }
    }

    public boolean tryUpdate(TaskDefBean td, Date lastTime) {
        int row = getSqlMapClientTemplate().updateArgs("compareAndSetLastTime", td.getTaskId(), td.bpId,
            td.getLastTime(), lastTime);
        return row == 1;
    }

    ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext ctx) {
        servletContext = ctx;
    }

    String getBpId() {
        return TaskScheduler.getBpId(servletContext);
    }

}