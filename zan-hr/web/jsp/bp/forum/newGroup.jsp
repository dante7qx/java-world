<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>发表新主题</title>
<meta http-equiv="content-type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="${ctx}/ux/ForumStyle.css">
<%@ include file="/jsp/common/common.jsp"%>
<!-- ckeditor -->
<script type="text/javascript" src="<%=request.getContextPath() %>/ux/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
$(function(){
	var localgpid = getParam("gpid");
	$("#groupId").val(localgpid);
	if(localgpid != null)
	    $.ajax({
	    	type: "POST",
	        url:"bp.forum.ForumGroupAction$query.json",
	        data:{groupId:localgpid},
	        dataType:"json",
	        success:function(data, textStatus){
	        	$("#groupName").val(data.rows[0].groupName);
	        	$("#groupDesc").val(data.rows[0].groupDesc);
	         }
	    });		
});
function postSubmit(){
	var localgpid = getParam("gpid");
	if(localgpid != null){
		$('#postInfo').form('submit', {
			url : "bp.forum.ForumGroupAction$update.json",
			onSubmit : function() {
				
			},
			success : function(data) {
				window.location.href="forumGroup.jsp";
			}
		});
	}else{
		$('#postInfo').form('submit', {
			url : "bp.forum.ForumGroupAction$save.json",
			onSubmit : function() {
				
			},
			success : function(data) {
				window.location.href="forumGroup.jsp";
			}
		});		
	}	

	
}
function getParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search.substr(1)).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }

</script>
<!-- end of ckeditor -->
</head>
<body class="bdcontent" style="border:1px solid #A4BED4;padding:4px 4px;*padding:3px 4px">
	<br>
	<table>
		<tbody>
			<tr>
				<td><h2>创建新板块</h2></td>
			</tr>
		</tbody>
	</table>
	<div>
		<a href="ForumGroup.jsp">首页</a><br>
	</div>
	<br>
	<table border="0" cellpadding="0" cellspacing="0" width="930" height="61">
		<tbody>
			<tr valign="top">
				<td width="99%">
					<div class="forum-table">
						<div class="forum-messagebox">
							<form id="postInfo" method="post">
							<br>
								<span style="font-size:15; display: inline-block; height:30px; line-height:25px;">板块名称：</span> 
								<input id="groupName" class="easyui-validatebox" name="groupName" required=true style="width:450px;height:30px;font-size:15;"> <br> 
								<span style="font-size:15;">板块描述：</span>
								<textarea id="groupDesc" name="groupDesc" style="width:450px;height:100px;font-size:15;"></textarea>
								<input id="groupId" name="groupId" type="hidden">
								<br> 			
								<div style="text-align:center;">						
									<span class="buttonGaps">
									<a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;" onclick="postSubmit();">保存</a></span>
								</div>
							</form>
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
