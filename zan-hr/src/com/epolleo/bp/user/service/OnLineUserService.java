package com.epolleo.bp.user.service;

import javax.annotation.Resource;

import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.user.bean.OnLineUserBean;
import com.epolleo.bp.user.dao.OnLineUserDao;

public class OnLineUserService {

    @Resource
    private OnLineUserDao onlineDao;

    public void saveOnLineUser(OnLineUserBean user) {
        onlineDao.deleteOnLineUser(user);
        onlineDao.save(user);
    }

    public PagingResult<OnLineUserBean> findPaging(PagingForm pagingParam) {
        return onlineDao.findPaging(pagingParam);
    }

    public void deleteOnLineUserBean(String sessionId) {
        onlineDao.delete(sessionId);
    }

    public void updateOnLineUserBean(OnLineUserBean onLineUserBean) {
        onlineDao.update(onLineUserBean);
    }

    @Deprecated
    public void deleteOnLineUser(OnLineUserBean user) {
        onlineDao.deleteOnLineUser(user);
    }

    public boolean isUserOnline(String sessionId) {
        return onlineDao.isUserOnline(sessionId);
    }

}
