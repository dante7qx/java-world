/*  
 * Copyright (c) 2012 epolleo.com
 * All rights reserved.
 * @author：cf
 * @date：2012-06-28 11:00:44 +0800
 * @version V1.0 
 */
package com.epolleo.bp.menu.action;

import static com.alibaba.citrus.turbine.util.TurbineUtil.getTurbineRunData;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.citrus.service.requestcontext.buffered.BufferedRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.TurbineRunDataInternal;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.citrus.turbine.util.Function;
import com.alibaba.citrus.turbine.util.FunctionDir;
import com.alibaba.citrus.turbine.util.FunctionName;

import com.epolleo.bp.menu.bean.Int;
import com.epolleo.bp.menu.bean.Menu;
import com.epolleo.bp.menu.bean.MenuTree;
import com.epolleo.bp.menu.bean.Win8Menu;
import com.epolleo.bp.menu.service.MenuService;
import com.epolleo.bp.pub.AbstractAction;
import com.epolleo.bp.pub.LoginUser;
import com.epolleo.bp.seq.bean.IdKind;
import com.epolleo.bp.seq.service.SeqService;
import com.epolleo.bp.util.DateUtils;
import com.epolleo.bp.util.LoginConstant;

@Function()
@FunctionDir(id="bp.menu", name="菜单")
public class MenuAction extends AbstractAction {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private MenuService menuService;

    @Resource
    private SeqService seqService;

    @Resource
    private BufferedRequestContext buffered;

    /**
     * 主页面获取菜单树，不需要虚拟的构造节点
     * 
     * @date：2012-06-28 11:17:29 +0800
     */
    @Function("public")
    public void doQueryForMain(HttpServletRequest request, Context context) {
        LoginUser user = (LoginUser) request.getSession().getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
        String userId = user.getUserId();
        List<MenuTree> result = menuService.getMenuForUser(userId, "default");
        context.put("json", result);
    }
    
    @Function("public")
    public void doQueryForMainImp(HttpServletRequest request, Context context) {
        LoginUser user = (LoginUser) request.getSession().getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
        String userId = user.getUserId();
        List<MenuTree> result = menuService.getMenuForUser(userId, "hnaimp");
        context.put("json", result);
    }
    
    @Function("public")
    public void doGetWin8Menu(HttpServletRequest request, Context context) {
        LoginUser user = (LoginUser) request.getSession().getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
        String userId = user.getUserId();
        List<Win8Menu> result = menuService.getWin8MenuForUser(userId);
        //List<Win8Menu> newResult = new ArrayList<Win8Menu>();
        for(Win8Menu menu : result){
        	menu.setCssHeight();
        	menu.setCssWidth();
        }
        request.setAttribute("menus", result);
		TurbineRunDataInternal tr = (TurbineRunDataInternal) getTurbineRunData(request);
		String target = "jsp/bp/win8UI/index.jsp";
		tr.setTarget(target );
    }

    /*private CallBackMenu getCallBackMenu(Win8Menu menu) {
    	CallBackMenu cbMenu = new CallBackMenu(menu);
		return cbMenu;
	}*/

	/**
     * 菜单管理菜单树构造，有最上层虚拟根节点
     * 
     * @date：2012-06-28 11:17:52 +0800
     */
    @Function("bp.menu.query")
    @FunctionName("菜单查询")
    public void doQuery(HttpServletRequest request, Context context) {
        List<MenuTree> result = menuService.getMenuAll();
        context.put("json", result);
    }

    /**
     * 菜单的修改或者添加
     * 
     * @date：2012-06-28 11:18:32 +0800
     */
    @Function("bp.menu.update")
    @FunctionName("菜单更新")
    public void doUpdate(Context context, @Params Menu menu, HttpServletRequest req) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            com.epolleo.bp.pub.LoginUser loginUser = (com.epolleo.bp.pub.LoginUser) req.getSession().getAttribute(
                LoginConstant.LOGIN_USER_SESSION_KEY);
            if (menu.getMenuId() != 0) {
                menu.setUpdateTime(DateUtils.getCurrentDate());
                menu.setUpdateUser(loginUser.getUserId());
                menuService.doUpdate(menu);
                result.put("success", true);
            } else {
                Long menuId = seqService.getNewId(IdKind.MenuId);
                menu.setMenuId(menuId.intValue());
                menu.setCreateTime(DateUtils.getCurrentDate());
                menu.setCreateUser(loginUser.getUserId());
                menuService.doSave(menu);
                result.put("success", true);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("success", false);
        }

        context.put("json", result);
    }

    /**
     * 删除菜单
     * 
     * @date：2012-06-28 11:18:50 +0800
     */
    @Function("bp.menu.delete")
    @FunctionName("菜单删除")
    public void doDelete(Context context, @Param(name = "id") int id) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            int delete = menuService.doDelete(id);
            result.put("success", delete == 1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("success", false);
        }
        context.put("json", result);

    }

    /**
     * 查询menuId及parentId != id 的菜单信息，并构造菜单树
     * 
     * @date：2012-06-28 11:19:11 +0800
     */
    @Function("bp.menu.query")
    public void doQueryById(Context context, @Param(name = "id") int id) {
        List<MenuTree> result = menuService.getMenuNotId(id);
        context.put("json", result);
    }

    /**
     * 移动菜单，修改parentID
     * 
     * @date：2012-06-28 11:24:01 +0800
     */
    @Function("bp.menu.update")
    public void doUpdateParentId(Context context, @Param(name = "id") int id, @Param(name = "parentId") int parentId) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        try {
            int delete = menuService.updateParentId(id, parentId);
            result.put("success", delete == 1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.put("success", false);
        }
        context.put("json", result);
    }

    /**
     * 查询menuId为id的孩子节点
     */
    @Function("bp.menu.query")
    public void doQueryChild(Context context, @Param(name = "parentId") int parentId) {
        List<Menu> m = menuService.getChild(parentId);
        context.put("json", m);

    }

    /**
     * 导出菜单SQL并提供下载
     */
    @Function("bp.menu.download")
    @FunctionName("菜单下载")
    public void doDownload(HttpServletRequest req, HttpServletResponse resp) {
        // 附件下载/预览时关闭Buffering
        buffered.setBuffering(false);
        List<MenuTree> tree = menuService.getMenuAll();
        MenuTree mt = tree.get(0);

        StringBuffer buf = new StringBuffer(
            "insert into BP_MENU(PARENT_ID,MENU_ID,MENU_ORDER,TITLE,PATH,FUNC_CODE,MENU_KIND,MENU_INFO,WIDTH,HEIGHT,BACK_COLOR,SHOW_IN, TARGET) values");
        synchronized (Int.class) {
            Int.clear();
            makeOrder(mt.getChildren());
            int idx = 1;
            Map<Integer, Int> map = Int.map();
            for (Integer key : map.keySet()) {
                if (key < 0)
                    continue;
                Int i = map.get(key);
                i.setValue(idx++);
            }
            makeSql(mt.getChildren(), buf);
            Int.clear();
        }
        int idx = buf.lastIndexOf(",");
        if (idx > 0)
            buf.replace(idx, idx + 1, ";");

        try {
            String fileName = encodeFileName("bp_menu_insert.sql", req);// 附件描述默认为文件名（无后缀）
            resp.setContentType("text/plain;charset=utf-8");
            String contentDisposition = "attachment";// "inline"

            resp.setHeader("Content-disposition", contentDisposition + ";filename=" + fileName);
            resp.getOutputStream().write(buf.toString().getBytes("utf-8"));
            resp.getOutputStream().close();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    void makeOrder(List<MenuTree> list) {
        if (list != null) {
            for (MenuTree mt : list) {
                Menu m = mt.getAttributes();
                m.sync();
            }
            for (MenuTree mt : list) {
                makeOrder(mt.getChildren());
            }
        }
    }

    void makeSql(List<MenuTree> list, StringBuffer buf) {
        if (list != null) {
            for (MenuTree mt : list) {
                Menu m = mt.getAttributes();
                buf.append("\r\n\t(").append(m.getPid()).append(", ");
                buf.append(m.getMid()).append(", ");
                buf.append(m.getMenuOrder()).append(", ");
                buf.append("'").append(m.getName()).append("', ");
                buf.append("'").append(m.getUrl()).append("', ");
                buf.append("'").append(m.getFuncCode()).append("', ");
                buf.append(m.getMenuKind()).append(", ");
                if(m.getMenuInfo() == null){
                	buf.append("NULL, ");
                }else{
                	buf.append("'").append(m.getMenuInfo()).append("', ");
                }
                buf.append(m.getWidth()).append(", ");
                buf.append(m.getHeight()).append(", ");
                if(m.getBackColor() == null){
                    buf.append("NULL,");
                }else{
                    buf.append("'").append(m.getBackColor()).append("',");
                }
                if(m.getShowIn() == null){
                    buf.append("NULL,");
                }else{
                    buf.append("'").append(m.getShowIn()).append("',");
                }
                if(m.getTarget() == null){
                	buf.append("NULL,");
                }else{
                	buf.append("'").append(m.getTarget()).append("'),");
                }
            }
            for (MenuTree mt : list) {
                makeSql(mt.getChildren(), buf);
            }
        }
    }

    String encodeFileName(String fileName, HttpServletRequest req) {
        String userAgent = req.getHeader("user-agent");
        boolean isMSIE = userAgent.contains("MSIE");
        boolean isMSIE6 = userAgent.contains("MSIE 6.0");
        String returnFileName = "";
        try {
            if (isMSIE) {// IE
                returnFileName = URLEncoder.encode(fileName, "UTF-8");
                if (returnFileName.length() > 150 && isMSIE6) {// 处理IE6 无法下载问题 "KB816868"
                    returnFileName = returnFileName.substring(0, 149);// 截取前150位
                } else {
                    returnFileName = StringUtils.replace(returnFileName, "+", "%20");
                }
            } else {// FF
                returnFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
            }
        } catch (UnsupportedEncodingException e) {
            logger.warn(e.getMessage(), e);
        }
        return returnFileName;
    }
}
