<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/assets/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
</script>
<title>欢迎登录</title>
</head>
<body>
	<h3>${errorMsg}</h3>
	<form action="${ctx}/login" method="post">
		<label>用户名：</label>
		<input name="userName" type="text"/>
		<br>
		<label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
		<input name="password" type="password"/>
		<br>
		<input type="submit" value="登录"/>
	</form>
</body>
</html>