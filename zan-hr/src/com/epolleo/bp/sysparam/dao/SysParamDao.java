package com.epolleo.bp.sysparam.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.sysparam.bean.SysParamBean;
import com.epolleo.bp.util.DateUtils;

/**
 * 
 * SysParamDao Date:2012-07-13,17:10:47 +0800
 * 
 * @author nj.sun
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class SysParamDao extends AbstractDao<SysParamBean> {
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * query sysParam by Page
     */
    public PagingResult<SysParamBean> findPaging(PagingForm pagingParam) {
        String sort = pagingParam.getSort();
        String order = pagingParam.getOrder();
        if (sort != null) {
            checkName(sort);
        }
        if (order != null) {
            checkName(order);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("sort", pagingParam.getSort());
        params.put("order", pagingParam.getOrder());
        params.putAll(pagingParam.getFilter());
        ArrayList<SysParamBean> page = new ArrayList<SysParamBean>();
        int total = getSqlMapClientTemplate().queryForPage(page, "selectSysParam", params, pagingParam.getSkip(),
            pagingParam.getPageSize());
        PagingResult<SysParamBean> result = new PagingResult<SysParamBean>(page, total);
        return result;
    }

    /**
     * update sysParam
     */
    public int update(SysParamBean sysParamGen, String userId) {
        Date date = DateUtils.getCurrentDate();
        sysParamGen.setUpdateTime(date);
        sysParamGen.setUpdateUser(userId);

        getSqlMapClientTemplate().update("updateSysParam", sysParamGen, 1);
        return 1;
    }

    /**
     * insert sysParam
     */
    public SysParamBean save(SysParamBean sysParamGen, String userId) {
        Date date = DateUtils.getCurrentDate();
        sysParamGen.setCreateTime(date);
        sysParamGen.setCreateUser(userId);
        sysParamGen.setUpdateTime(date);
        sysParamGen.setUpdateUser(userId);

        return (SysParamBean) getSqlMapClientTemplate().insert("insertSysParam", sysParamGen);
    }

    /**
     * delete sysParam
     */
    public int delete(String id) {
        getSqlMapClientTemplate().delete("deleteSysParam", id, 1);
        return 1;
    }

    /**
     * get sysParam by id
     */
    public SysParamBean getSysParamByKey(String id) {
        SysParamBean bean = (SysParamBean) (getSqlMapClientTemplate().queryForObject("getSysParamBean", id));
        return bean;
    }

    /**
     * get sysParam value
     */
    public String getSysParamValue(String id) {
        SysParamBean bean = getSysParamByKey(id);
        if (bean == null) {
            logger.warn("No sys param: " + id);
            return null;
        }
        if (bean.getParamValue() != null && bean.getParamValue().length() > 0) {
            return bean.getParamValue();
        } else {
            return bean.getDefaultValue();
        }
    }

    /**
     * get sysParam value
     */
    public int getSysParamInt(String id) {
        try {
            return Integer.parseInt(getSysParamValue(id));
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * get sysParam value
     */
    public String getSysParamString(String id) {
        try {
            return getSysParamValue(id);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * get sysParam value
     */
    public boolean getSysParamBoolean(String id) {
        try {
            return Boolean.parseBoolean(getSysParamValue(id));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * get sysParam value
     */
    public double getSysParamDouble(String id) {
        try {
            return Double.parseDouble(getSysParamValue(id));
        } catch (Exception e) {
            return 0.0d;
        }
    }

    /**
     * get sysParam value
     */
    public Date getSysParamDate(String id) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(getSysParamValue(id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return new Date();
    }

    public List<SysParamBean> findAll() {
        return (List<SysParamBean>) getSqlMapClientTemplate().queryForList("getSysParamAll");
    }

    public Map<String, String> getParamMapByKeyPrefix(String keyPrefix) {
        if (keyPrefix == null) {
            keyPrefix = "";
        }
        keyPrefix += "%";
        return (Map<String, String>) getSqlMapClientTemplate().queryForMap("getSysParamBeanMap", keyPrefix, "paramKey",
            "paramValue");
    }
}
