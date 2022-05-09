<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${ctx}/assets/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
	var cusConverter = {
		req: function() {
			$.ajax({
				url: 'convert',
				data: '32-但丁',
				type: 'POST',
				contentType: 'application/x-dante',
				success: function(data) {
					$('#resp').text(data);
				}
			})
		}
	};
</script>
<title>自定义消息转换器</title>
</head>
<body>
	<h1 align="center">自定义消息转换器</h1>
	<div id="resp"></div>
	<input type="button" onclick="cusConverter.req()" value="请求"/>
</body>
</html>