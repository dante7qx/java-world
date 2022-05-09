package com.epolleo.bp.user.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.epolleo.bp.announce.bean.AnnounceInfoBean;
import com.epolleo.bp.announce.dao.ibatis.AnnounceInfoDao;
import com.epolleo.bp.pub.AbstractDao;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.sysparam.dao.SysParamDao;
import com.epolleo.bp.user.bean.OnLineUserBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnLineUserDao extends AbstractDao<OnLineUserBean> {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    SysParamDao sysParamDao;

    @Resource
    AnnounceInfoDao announceInfoDao;

    public PagingResult<OnLineUserBean> findPaging(PagingForm pagingParam) {
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
        ArrayList<OnLineUserBean> page = new ArrayList<OnLineUserBean>();
        int total = getSqlMapClientTemplate().queryForPage(page, "selectOnLineUser", params, pagingParam.getSkip(),
            pagingParam.getPageSize());
        PagingResult<OnLineUserBean> result = new PagingResult<OnLineUserBean>(page, total);
        return result;
    }

    public int update(OnLineUserBean onLineUserBean) {
        getSqlMapClientTemplate().update("updateOnLineUser", onLineUserBean, 1);
        return 1;
    }

    public OnLineUserBean save(OnLineUserBean onLineUserBean) {
        return (OnLineUserBean) getSqlMapClientTemplate().insert("insertOnLineUser", onLineUserBean);
    }

    public int delete(String sessionId) {
        getSqlMapClientTemplate().delete("deleteOnLineUser", sessionId, 1);
        return 1;
    }

    public boolean isUserOnline(String sessionId) {
        String userId = (String) getSqlMapClientTemplate().queryForObject("getOnlineUserId", sessionId);
        return userId != null;
    }

    public int deleteOnLineUser(OnLineUserBean user) {
        try {
            if (sysParamDao.getSysParamBoolean("BP.SingleLogin")) {
                Date d = new Date();
                d.setTime(d.getTime() - 10L * 60L * 1000L);
                List<String> addrs = (List<String>) getSqlMapClientTemplate().queryForListArgs("getOnLineUserAddr", user.getUserId(), user.getRemoteAddr(), d);
                if (addrs.size() > 0) {
                    String from = user.getRemoteAddr();
                    AnnounceInfoBean nb = new AnnounceInfoBean();
                    nb.setContent("来自[" + from + "]的用户[" + user.getUserId() + "]踢出了来自" + addrs + "的同个用户！");
                    nb.setEffDate(new Date());
                    Date expDate = new Date();
                    expDate.setTime(expDate.getTime() + 5L * 60L * 1000L);
                    nb.setExpDate(expDate);
                    nb.setUpdateBy(user.getUserId());
                    nb.setUpdateDate(new Date());
                    nb.setFuncIds("logoff:" + user.getUserId());
                    nb.setUrl(null);
                    nb.setOrgCodes(null);
                    nb.setState(0);
                    announceInfoDao.saveNotify(nb);
                }
                getSqlMapClientTemplate().delete("deleteOnLineUserId", user.getUserId());
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        return getSqlMapClientTemplate().delete("deleteOnLineUser", user.getSessionId());
    }
}
