<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
	<title>上传图片</title>
	<meta http-equiv="content-type" content="text/html; charset=utf8">
	<%@ include file="/jsp/common/common.jsp"%>
	<script type="text/javascript">
	window.onload = function()
	{		
		$("#imageFile").trigger("click");
	}
		function imgUpload() {
			var imgPath = $('#imageFile').val();
			if(!checkImg(imgPath)){
				return false;
			}; 
 			$('#pstinfo').form('submit', {
				url : "bp.forum.ForumTopicAction$upload.json",
				dataType : 'json',
				onSubmit : function() {
				},
				success : function(data) {
					window.returnValue = eval(data); 
					window.close();				
					},
				error : function(data, status, e) 
				{
					$('#result').html('添加失败');
				}
			}); 
		} 
 		function checkImg(path) {
			var file = document.getElementById("upload");
			var i = path.lastIndexOf(".");
			var ext = path.substring(i);
			var ext1 = ext.toLowerCase();
	
			if (ext1 != ".gif" && ext1 != ".ief" && ext1 != ".jpg" && ext1 != ".png"
					&& ext1 != ".jpeg" && ext1 != ".tiff" && ext1 != ".tif"
					&& ext1 != ".bmp" && ext1 != ".svg" && ext1 != ".svgx") {
				alert("不支持图片类型:" + ext);
				return false;
			}
			return true;
		} 
	</script>
</head>
<body>
	<form id="pstinfo" method="post" enctype="multipart/form-data">
		<span style="font-size:15; display: inline-block; height:30px; line-height:25px;">请选择需要上传的图片：</span> 
		<input type="file" id="imageFile" name="imageFile" onchange="imgUpload();"/>
		<br> 
		<span class="buttonGaps">
		<a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;" onclick="imgUpload();">上传</a>
		</span>
	</form>
	<div id="result"></div>
</body>
</html>
