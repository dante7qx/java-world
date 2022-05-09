/**
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * AnnounceInfoAction.java
 * Date: 2013-01-24
 */
package com.epolleo.bp.announce.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

import com.epolleo.bp.announce.bean.AnnounceInfoBean;
import com.epolleo.bp.announce.dao.ibatis.AnnounceInfoDao;
import com.epolleo.bp.announce.service.AnnounceInfoService;
import com.epolleo.bp.employee.bean.Employee;
import com.epolleo.bp.employee.bean.EmployeeOrgan;
import com.epolleo.bp.employee.service.EmployeeService;
import com.epolleo.bp.menu.bean.Menu;
import com.epolleo.bp.menu.dao.MenuDao;
import com.epolleo.bp.org.bean.OrgBean;
import com.epolleo.bp.org.service.OrgService;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.bp.util.LoginConstant;

/**
 * <p>
 * 通告信息的Action
 * </p>
 * 
 * @version 1.0
 */
@Function()
@FunctionDir(id = "bp.announce", name = "通告")
public class AnnounceInfoAction {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AnnounceInfoService service;

    @Resource
    private SeqService seqService;

    @Resource
    private AnnounceInfoDao notifyDao;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private OrgService orgService;

    @Resource
    private HttpSession session;

    @Resource
    private MenuDao menuDao;

    /**
     * 处理指定页及条件的查询结果
     */
    @Function("public")
    public void doQuery(Context context, @Params PagingForm form, @Params AnnounceInfoBean entity) {
        if (StringUtils.isNotEmpty(entity.getContent())) {
            form.addFilter("content", "%" + entity.getContent() + "%");
        }
        if (null != entity.getAnnounceDate()) {
            form.addFilter("announceDate", entity.getAnnounceDate());
        }
        if (null != entity.getAnnounceDateEndQuery()) {
            form.addFilter("announceDateEndQuery", entity.getAnnounceDateEndQuery());
        }
        PagingResult<AnnounceInfoBean> result = service.findPaging(form);
        context.put("json", result);
    }

    /**
     * @param entity
     *            新增通告信息对象
     */
    @Function("bp.announce.save")
    @FunctionName("通告保存")
    public void doSave(Context context, @Params AnnounceInfoBean entity, HttpServletRequest req) {
        LoginUser loginUser = (LoginUser) req.getSession().getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
        entity.setId(seqService.getNewId(IdKind.AnnounceId));
        entity.setUpdateBy(loginUser.getUserId());
        entity.setUpdateDate(DateUtils.getCurrentDate());
        service.save(entity);
        context.put("json", entity);
    }

    /**
     * @param id
     *            待删除通告信息的关键值（比如主键Key）
     */
    @Function("bp.announce.delete")
    @FunctionName("通告删除")
    public void doDelete(Context context, @Param(name = "id") Long id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        int delete = service.delete(id);
        result.put("success", delete == 1);
        context.put("json", result);
    }

    /**
     * @param entity
     *            修改通告信息对象
     */
    @Function("bp.announce.update")
    @FunctionName("通告更新")
    public void doUpdate(Context context, @Params AnnounceInfoBean entity, HttpServletRequest req) {
        LoginUser loginUser = (LoginUser) req.getSession().getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
        entity.setUpdateBy(loginUser.getUserId());
        entity.setUpdateDate(DateUtils.getCurrentDate());
        service.update(entity);
        context.put("json", entity);
    }

    // 根据主键查询实体信息
    public void doQueryById(Context context, @Param(name = "id") Long id) {
        AnnounceInfoBean entity = service.find(id);
        context.put("json", entity);
    }

    @Function()
    public void doQueryNotify(Context context) {
        PagingResult<AnnounceInfoBean> result = filterResult(service.getAllNotifyForCurrentUser());
        context.put("json", result);
    }

    @Function("public")
    public void doUpdateNotify(Context context, HttpServletRequest req, @Params AnnounceInfoBean entity) {
        LoginUser loginUser = (LoginUser) req.getSession().getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
        AnnounceInfoBean currentJob = notifyDao.findNotify(entity.getId());
        currentJob.setState(1);
        currentJob.setUpdateDate(new Date());
        currentJob.setUpdateBy(loginUser.getUserId());
        notifyDao.updateNotify(currentJob);
        context.put("json", entity);
    }

    public PagingResult<AnnounceInfoBean> filterResult(List<AnnounceInfoBean> list) {
        LoginUser u = LoginUser.getCurrentUser();

        List<AnnounceInfoBean> rows = new ArrayList<AnnounceInfoBean>();
        Set<String> fset = getFuncIds();
        List<?> userOrgCodes = (u != null && u.getOrgCodes() != null) ? u.getOrgCodes() : Collections.emptyList();
        for (AnnounceInfoBean notify : list) {
            boolean funcFlag = false;
            boolean orgFlag = false;
            if (notify.getFuncIds() == null || notify.getFuncIds().equals("")) {
                funcFlag = true;
            } else {
                String[] fs = notify.getFuncIds().split(";");
                for (String f : fs) {
                    if (fset.contains(f)) {
                        funcFlag = true;
                        break;
                    }
                }
            }
            if (notify.getOrgCodes() == null || notify.getOrgCodes().equals("")) {
                orgFlag = true;
            } else {
                String[] ocs = notify.getOrgCodes().split(";");
                for (String oc : ocs) {
                    if (userOrgCodes.contains(oc)) {
                        orgFlag = true;
                    }
                }
            }
            String logoffuserid = "logoff:" + u.getUserId();
            if ((funcFlag && orgFlag) || logoffuserid.equals(notify.getFuncIds())) {
                rows.add(updateNotifyUrl(u, notify));
            }
            if (rows.size() > 10) {
                break;
            }
        }
        return new PagingResult<AnnounceInfoBean>(rows, rows.size());
    }

    public AnnounceInfoBean updateNotifyUrl(LoginUser loginUser, AnnounceInfoBean notify) {
        String url = notify.getUrl();
        if (url == null || url.equals("")) {
            return notify;
        }
        String path = url;
        int idx = path.indexOf('?');
        if (idx > 0) {
            path = path.substring(0, idx);
        }
        Map<String, String> allMenu = menuDao.getMenuPathTitleMap();

        String title = allMenu.get(path);
        if (title == null) {
            title = "相关页面";
        }
        notify.setContent(notify.getContent() + "<br><a href='javascript:void(0)' onClick='notifyAddTab(\"" + title
            + "\",\"" + url + "\")' >" + title + "</a>");
        return notify;
    }

    @SuppressWarnings("unchecked")
    Set<String> getFuncIds() {
        Set<String> fSet = (Set<String>) session.getAttribute("webx.UserFunctions");
        if (fSet == null) {
            return Collections.emptySet();
        }
        return fSet;
    }

    @Function("public")
    public void doQueryByUserRole(Context context, @Params PagingForm form, @Params AnnounceInfoBean entity) {
        LoginUser user = LoginUser.getCurrentUser();
        String funcIdRange = user.getFunctions().keySet().toString();
        funcIdRange = funcIdRange.replaceAll(",", "\',\'").replaceAll(" ", "").replace("[", "(\'").replace("]", "\')");
        form.addFilter("funcIdRange", funcIdRange);
        PagingResult<AnnounceInfoBean> result = service.queryAnnounceInfoByUserRole(form);
        context.put("json", result);
    }
}