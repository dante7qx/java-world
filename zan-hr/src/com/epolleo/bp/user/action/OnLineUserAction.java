package com.epolleo.bp.user.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.annotation.Resource;

import com.alibaba.citrus.service.requestcontext.session.SessionConfig.StoresConfig;
import com.alibaba.citrus.service.requestcontext.session.SessionRequestContext;
import com.alibaba.citrus.service.requestcontext.session.SessionStore.StoreContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.PagingForm;
import com.epolleo.bp.pub.PagingResult;
import com.epolleo.bp.user.bean.OnLineUserBean;
import com.epolleo.bp.user.service.OnLineUserService;


@Function
@FunctionDir(id="bp.online", name="在线管理")
public class OnLineUserAction extends AbstractAction {

    @Resource
    private OnLineUserService onLineUserService;

    @Function("bp.online.query")
    @FunctionName("在线查询")
    public void doQuery(HttpServletRequest request, Context context,
        @Params PagingForm form, @Params OnLineUserBean userBean) {
        HashMap<String, Object> filter = new HashMap<String, Object>();

        Object userId = userBean.getUserId();
        Object userName = userBean.getUserName();
        if (userId != null) {
            if (userId instanceof String) {
                filter.put("userId", "%" + userId + "%");
            } else {
                filter.put("userId", userId);
            }
        }
        if (userName != null) {
            if (userName instanceof String) {
                filter.put("userName", "%" + userName + "%");
            } else {
                filter.put("userName", userName);
            }
        }

        form.setFilterMap(filter);
        PagingResult<OnLineUserBean> result = onLineUserService
            .findPaging(form);
        context.put("json", result);
    }

    @Function("bp.online.logout")
    @FunctionName("在线用户踢出")
    public void doLogOut(HttpServletRequest request,
        final SessionRequestContext sc, Context context,
        @Params String sessionId) {
        String id = request.getParameter("sessionId");
        onLineUserService.deleteOnLineUserBean(id);
        StoresConfig store = sc.getSessionConfig().getStores();
        for (final String name : store.getStoreNames()) {
            store.getStore(name).invaldiate(id, new StoreContext() {
                Object state;

                public void setState(Object s) {
                    state = s;
                }

                public StoreContext getStoreContext(String name) {
                    return this;
                }

                public Object getState() {
                    return state;
                }

                public SessionRequestContext getSessionRequestContext() {
                    return sc;
                }

                public HttpSession getHttpSession() {
                    return null;
                }
            });
        }
    }

}
