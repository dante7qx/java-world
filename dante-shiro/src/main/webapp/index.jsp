<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>首页</title>
</head>
<body>
	<h2>Hello Shiro World！</h2>
	<shiro:guest>
		<h3>您是一个游客！</h3><a href="register">注册</a>
	</shiro:guest>
	<shiro:notAuthenticated>
		<a href="login">登录</a>
	</shiro:notAuthenticated>
	<shiro:authenticated>
		<h3>你好, <shiro:principal property="userName" type="org.dante.shiro.domain.User"/>, 欢迎来到Shiro的世界！<a href="logout">注销</a></h3>
	</shiro:authenticated>
	<shiro:hasRole name="ROLE[1]">
		<a href="admin.jsp">进入系统管理</a>
	</shiro:hasRole>
	<shiro:hasPermission name="garsp:query">
		<a href="garsp/info.jsp">通航信息</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="garsp:add">
		<a href="garsp/add.jsp">添加通航信息</a>
	</shiro:hasPermission>
</body>
</html>