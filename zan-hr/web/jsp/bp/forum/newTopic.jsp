<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>发表新主题</title>
<%@ include file="/jsp/common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/ux/ForumStyle.css">
<script type="text/javascript">
$(function(){
	var localgpid = getParam("gpid");
	var localgpname = getParam("gpname");
	var currgphref = "articleFlat.jsp?gpid="+localgpid+"&gpname="+localgpname;
	var currpsthref = "newTopic.jsp?gpid="+localgpid+"&gpname="+localgpname;
	$(".currgp").attr("href",currgphref).text(localgpname);
	$(".pstnew").attr("href",currpsthref);
	$("#groupId").attr("value",localgpid);
	$("#parentId").attr("value",0);
	$("#isleaf").attr("value",0);
});
function getParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search.substr(1)).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }	
function postSubmit(){
	$('#postInfo').form('submit', {
		url : "bp.forum.ForumTopicAction$save.json",
		onSubmit : function() {
		},
		success : function(data) {
			window.location.href="articleFlat.jsp?gpid="+getParam("gpid")+"&gpname="+getParam("gpname");
		}
	});
	
}

</script>
<meta http-equiv="content-type" content="text/html; charset=GBK">
<!-- ckeditor -->
<script type="text/javascript" src="<%=request.getContextPath() %>/ux/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
window.onload = function()
{		
	CKEDITOR.replace( "topicContext" );
}
</script>
<!-- end of ckeditor -->
</head>
<body class="bdcontent" style="border:1px solid #A4BED4;padding:4px 4px;*padding:3px 4px">

	<br>
		<table>
			<tbody>
			<tr class="navigater">
				<td><a href="ForumGroup.jsp">首页</a> &#187;</td>
				<td><a class="currgp" href="articleFlat.jsp"></a>&#187;</td>
				<td><a class="pstnew" href="post.jsp">发表新主题</a>
				</td>
			</tr>
			</tbody>
		</table>
		<br>
		<table border="0" cellpadding="0" cellspacing="0" width="930"
			height="61">
			<tbody>
				<tr valign="top">
					<td width="99%">
						<div class="forum-table">
							<div class="forum-messagebox">
								<form id="postInfo" method="post"><br>
									<span style="font-size:15;">标题：</span>
									<input  id="topicName" class="easyui-validatebox" name="topicName" required=true style="width:500px;height:30px;font-size:15;"><br> 
									<span style="font-size:15;">内容：</span>
									<textarea id="topicContext" name="topicContext" rows="15" cols="80" style="font-size:20;"></textarea>
									<input id="groupId" type="hidden" name="groupId"/>
									<input id="parentId" type="hidden" name="parentId"/>
									<input id="rootId" type="hidden" name="rootId"/>
									<input id="isleaf" type="hidden" name="isleaf"/>
									<input id="createTime" type="hidden" name="createTime"/>
									<br> 
									<span class="buttonGaps">
									<a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;" onclick="postSubmit();">保存</a></span>
								</form>

							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>

</body>
</html>
