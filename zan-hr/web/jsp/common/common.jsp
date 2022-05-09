<%@page trimDirectiveWhitespaces="true" %>
<%@page import="com.epolleo.bp.pub.LoginUser"%>
<%@page import="com.epolleo.bp.util.LoginConstant"%>
<%@page import="com.epolleo.bp.sysparam.service.SysParamService"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<% 
	String theme = (String) request.getSession().getServletContext().getAttribute("bp.hna.theme");
	if (theme == null) {
	    theme = "default";
	}
	SysParamService  sys = SysParamService.getSysParamService(request);
	int pageSize = 10;
	String  pageList = "5,10,15";
	int tabCount = 5;
	LoginUser user = (LoginUser)session.getAttribute(LoginConstant.LOGIN_USER_SESSION_KEY);
	request.setAttribute("userId", user.getUserId());
	String startPage = null;
	try{
		pageSize =  sys.getSysParamInt("BP.PageSize");
	} catch(Exception e){  
	}
	try{
		pageList =  sys.getSysParamString("BP.PageList");
	} catch(Exception e){
	}
	try{
	    tabCount =  sys.getSysParamInt("BP.TabCount");
	} catch(Exception e){
	}
	try{
	    startPage =  sys.getSysParamString("BP.StartPage");
	} catch(Exception e){
	}
%>
<script type="text/javascript">
	// tab页的最大个数
    var limitNum = <%=tabCount%>
    var _startPage_ = <%=startPage == null ? "null" : "'" + startPage +"'"%>;
    var ctxRoot = "${ctx}/";
    var _pageSize_ = <%=pageSize%>; //每页显示的记录条数，默认为10 
    var _pageList_ = [<%=pageList%>]; //可以设置每页记录条数的列表 

    function winClose(winId){
    	$('#'+winId).window('close');
    }
</script>
<link rel="stylesheet" type="text/css" href="${ctx}/ux/themes/<%=theme%>/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/ux/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/ux/app-<%=theme%>.css">

<script type="text/javascript" src="${ctx}/ux/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/ux/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/ux/jquery.easyui.extend.js"></script>
<script type="text/javascript" src="${ctx}/ux/locale/easyui-lang-zh_CN.js"></script>