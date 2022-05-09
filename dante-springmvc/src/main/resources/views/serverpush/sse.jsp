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
		if(!!window.EventSource) {
			var source = new EventSource('serverpush/sse');
			s = '';
			source.addEventListener('message', function(e) {
				s += e.data + '<br/>';
				$('#serverMessage').html(s);
			});
			
			source.addEventListener('open', function(e) {
				$('#serverMessage').append('连接打开。');
			}, false);
			
			source.addEventListener('error', function(e) {
				if(e.readyState == EventSource.CLOSED) {
					console.log('连接关闭。');
				} else {
					console.log(e);
				}
			}, false);
		} else {
			console.log('您的浏览器不支持SSE');
		}
	});
</script>
<title>SSE</title>
</head>
<body>
	<h1 align="center">SSE</h1>
	<div id="serverMessage"></div>
</body>
</html>