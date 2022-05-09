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
		
	});
</script>
<title>附件上传</title>
</head>
<body>
	<h1 align="center">附件上传</h1>
	
	<div>
		<form action="upload" enctype="multipart/form-data" method="post">
			<input type="file" name="files" />
			<br>
			<input type="submit" value="上传" />
		</form>		
	</div>
</body>
</html>