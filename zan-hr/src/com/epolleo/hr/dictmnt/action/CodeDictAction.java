package com.epolleo.hr.dictmnt.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.hr.dictmnt.bean.CodeDictBean;
import com.epolleo.hr.dictmnt.service.CodeDictService;

/**
 * @Description: 基础数据字典表的Action
 * 
 * @author dante
 * @date 2015-09-23 下午11:45:59
 *
 */
@Function()
public class CodeDictAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private CodeDictService service;


    /**
     * 分页查询基础数据字典表
     *
     * @param context
     * @param form
     * @param entity
     */
    @Function("hr.dictmnt.query")
    public void doQuery(Context context, @Params PagingForm form,
            @Params CodeDictBean entity) {
        PagingResult<CodeDictBean> result = service.findPaging(form);
        context.put("json", result);
    }

    /**
     * 新增基础数据字典表对象
     *
     * @param entity 
     * @param context
     */
    @Function("hr.dictmnt.save")
    public void doSave(Context context, @Params CodeDictBean entity) {
	boolean result = false;
        try {
            service.save(entity);
            result = true;
        } catch (Exception e) {
            logger.error("Save CodeDictBean error.", e);
        }
        context.put("json", result);
    }

    /**
     * 根据Id删除
     *
     * @param context
     * @param id
     * @see Context
     */
    @Function("hr.dictmnt.delete")
    public void doDelete(Context context, @Param(name = "id") Long id) {
        boolean result = false;
        try {
            service.delete(id);
            result = true;
        } catch (Exception e) {
            logger.error("Delete CodeDictBean error.", e);
        }
        context.put("json", result);
    }

    /**
     * 修改基础数据字典表对象
     *
     * @param context
     * @param entity
     */
    @Function("hr.dictmnt.update")
    public void doUpdate(Context context, @Params CodeDictBean entity) {
        boolean result = false;
        try {
	    entity.setUpdateBy(LoginUser.getCurrentUser().getUserId());
            entity.setUpdateDate(DateUtils.getCurrentDate());
            service.update(entity);
            result = true;
        } catch (Exception e) {
            logger.error("Update CodeDictBean error.", e);
        }
        context.put("json", result);
    }

    /**
     * 根据Id获取
     *
     * @param context
     * @param id 
     */
    @Function("hr.dictmnt.query")
    public void doQueryById(Context context, @Param(name = "id") Long id) {
        CodeDictBean bean = service.find(id);
        context.put("json", bean);
    }
    
    /**
     * 根据Type获取基础数据下拉框
     * 
     * @param context
     * @param type
     */
    public void doQueryComboByType(Context context, @Param(name = "type") String type) {
    	List<CodeDictBean> list = service.queryByType(type);
    	context.put("json", list);
    }

}