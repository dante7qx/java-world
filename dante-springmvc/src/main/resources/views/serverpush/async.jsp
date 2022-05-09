<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/assets/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
	var asyncPage = {
		defer: function() {
			$.get('serverpush/defer', function(data) {
				$('#serverMessage').append('<br/>' + data);
				asyncPage.defer();
			});
		}
	};
	$(function() {
		asyncPage.defer();
	});
</script>
<title>Servlet 3.0 + 异步方法处理</title>
</head>
<body>
	<h1 align="center">Servlet 3.0 + 异步方法处理</h1>
	<div id="serverMessage">
		<label>服务器时间：</label>
	</div>
</body>
</html>