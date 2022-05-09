<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>回复帖子的界面</title>
<meta http-equiv="content-type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" href="${ctx}/ux/ForumStyle.css">
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript">
$(function(){
	var id = getParam("id");
	var rootid = getParam("rootId");
	var localgpid = getParam("gpid");
	var localgpname = getParam("gpname");
	var currgphref = "articleFlat.jsp?gpid="+localgpid+"&gpname="+localgpname;
	$("#currgp").attr("href",currgphref).text(localgpname);
	$("#totopicshow").attr("href",currgphref);
	
	$("#rtid").attr("value",rootid);
	$("#groupId").attr("value",localgpid);
	$("#pid").attr("value",id);
	$("#isleaf").attr("value",1);
	
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
			window.location.href="replyDeal.jsp?rootId="+getParam("rootId")+"&gpid="+getParam("gpid")+"&gpname="+getParam("gpname");;
		}
	});
	
}
</script>
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
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr valign="top">
				<td width="99%">
					<p><a href="ForumGroup.jsp">首页</a> &#187; 
					<a id="currgp" href="articleFlat.jsp"></a>
					</p>
					<h2>
						回复主题
					</h2>
			</tr>		
		</tbody>
	</table>

	<table border="0" cellpadding="0" cellspacing="0" width="930" height="61">
		<tbody>
			<tr valign="top">
				<td width="99%">
					<div class="forum-table">
						<div class="forum-messagebox">
							<form id="postInfo" method="post">
								<input id="pid" type="hidden" name="parentId" value="" /> 
								<input id="rtid" type="hidden" name="rootId" value="" /> 
								<input id="isleaf" type="hidden" name="isleaf" value="" />
								<input id="groupId" type="hidden" name=groupId value="" />
								<br>
								<span style="font-size:15;">标题：</span>
								<input id="topicName" class="easyui-validatebox" name="topicName" required=true style="width:500px;height:30px;font-size:15;"><br> 
								<span style="font-size:15;">内容：</span>
								<textarea id="topicContext" name="topicContext" rows="15" cols="80" style="font-size:20;"></textarea>
								<br>
								<span class="buttonGaps">
								<a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;" onclick="postSubmit();">保存</a></span>
							</form>
						</div>
					</div>

					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td nowrap="nowrap" width="1%"><br>
								<br>
								</td>
								<td align="center" width="98%"><table border="0"
										cellpadding="0" cellspacing="0">
										<tbody>
											<tr>
												<td></td>
												<td><a id="totopicshow" href="articleFlat.jsp">返回到主题列表</a></td>
											</tr>
										</tbody>
									</table>
								</td>
								<td nowrap="nowrap" width="1%">&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
