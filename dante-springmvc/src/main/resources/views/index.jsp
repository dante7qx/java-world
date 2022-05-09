<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/assets/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		setTimeout(function() {
			$('h1').after('<h2 align="center">Dog King</h2>');
		}, 3000);
	});
</script>
<title>Welcome to Spring MVC World!</title>
</head>
<body>
	<h1 align="center">Welcome to Spring MVC World!</h1>
	<p align="center">
		<img alt="" src="${ctx}/assets/image/bg.jpg" />
	</p>
</body>
</html>