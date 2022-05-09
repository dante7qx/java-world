package com.epolleo.bp.sysparam.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.citrus.webx.util.WebxUtil;

import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.sysparam.bean.SysParamBean;
import com.epolleo.bp.sysparam.dao.SysParamDao;

/**
 * 
 * SysParamService Date:2012-07-13,17:23:51 +0800
 * 
 * @author nj.sun
 * @version 1.0
 */
public class SysParamService {
    static final Logger log = LoggerFactory.getLogger(SysParamService.class);

    @Resource
    private SysParamDao sysParamDao;

    /**
     * update sysParam
     */
    public int update(SysParamBean sysParamBean, String userId) {
        return sysParamDao.update(sysParamBean, userId);
    }

    /**
     * query sysParam by page
     */
    public PagingResult<SysParamBean> findPaging(PagingForm pagingParam) {
        return sysParamDao.findPaging(pagingParam);
    }

    /**
     * insert sysParam
     */
    public SysParamBean save(SysParamBean sysParamBean, String userId) {
        return sysParamDao.save(sysParamBean, userId);
    }

    /**
     * delete sysParam
     */
    public int delete(String idStr) {
        return sysParamDao.delete(idStr);
    }

    /**
     * get sysParam value
     */
    public int getSysParamInt(String id) {
        return sysParamDao.getSysParamInt(id);
    }

    /**
     * get sysParam value
     */
    public String getSysParamString(String id) {
        return sysParamDao.getSysParamString(id);
    }

    /**
     * get sysParam value
     */
    public double getSysParamDouble(String id) {
        return sysParamDao.getSysParamDouble(id);
    }

    /**
     * get sysParam value
     */
    public Date getSysParamDate(String id) {
        return sysParamDao.getSysParamDate(id);
    }

    /**
     * get sysParam value
     */
    public boolean getSysParamBoolean(String id) {
        return sysParamDao.getSysParamBoolean(id);
    }

    /**
     * get an instance of SysParamService
     */
    public static SysParamService getSysParamService(HttpServletRequest req) {
        ApplicationContext ac = WebxUtil.getCurrentComponent(req)
            .getApplicationContext();
        return (SysParamService) ac.getBean("sysParamService");
    }

    public Map<String, String> getParamGroup(String paraType) {
        paraType = paraType + ".";
        Map<String, String> map = sysParamDao.getParamMapByKeyPrefix(paraType);
        LinkedHashMap<String, String> lmap = new LinkedHashMap<String, String>();

        for (String key : map.keySet()) {
            lmap.put(key.substring(paraType.length()), map.get(key));
        }
        log.info("ParamGroup:" + paraType + " -> " + lmap.toString());
        return lmap;
    }

    public List<SysParamBean> getSysParamAll() {
        return sysParamDao.findAll();
    }
}
