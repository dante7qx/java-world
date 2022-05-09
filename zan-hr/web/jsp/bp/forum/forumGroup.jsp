<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>论坛</title>
<%@ include file="/jsp/common/common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=utf8">
<link rel="stylesheet" type="text/css" href="${ctx}/ux/ForumStyle.css">
<script type="text/javascript">
$(function(){
    $.ajax({
    	type: "POST",
        url:"bp.forum.ForumGroupAction$query.json",
        dataType:"json",
        success:function(data, textStatus){
        	for(i=0;i<data.total;i++)
    　　　　　　　　{
        				var row=$("#groupContents").clone();
						var href = "articleFlat.jsp?gpid=" + data.rows[i].groupId + "&gpname=" + data.rows[i].groupName;
						var modifyhref = "newGroup.jsp?gpid=" + data.rows[i].groupId;

						row.find("#agpname").attr("href", href).text(data.rows[i].groupName);
						row.find("#gpdesc").html(data.rows[i].groupDesc);
						row.find("#ctname").text(data.rows[i].createrName);
						row.find("#cttime").text(data.rows[i].createTime);
						row.find("#uttime").text(data.rows[i].updateTime);
						row.find("#gpid").text(data.rows[i].groupId);
						row.find("#modifytp").attr("href",modifyhref);

						row.attr("class","newrows");
						$("#groupContents").parent().append(row);
    　　　　　　　　}
        	$(".newrows").attr("style","display:");

         }
    });
});
function deleteGroup(eventTag){
	var event = eventTag || window.event;
	var currentObj = event.srcElement == undefined ? $(event.currentTarget) : $(event.srcElement);
	var gpid = currentObj.parents("#groupContents").find("#gpid").html();
	
	var emptyFlag = true;
    $.ajax({
    	type: "POST",
        url:"bp.forum.ForumTopicAction$query.json",
        data:{
        	groupId : gpid
        },
        dataType:"json",
        success:function(data, textStatus){
        	if(data.total > 0){
        		$.messager.alert('提示',"请先删除该板块下的所有帖子后再删除板块！");
        	}else{
        	    $.ajax({
        	    	type: "POST",
        	        url:"bp.forum.ForumGroupAction$delete.json",
        	        data:{
        	        	groupId : gpid
        	        },
        	        dataType:"json",
        	        success:function(data, textStatus){
        	        	window.location.href="forumGroup.jsp";
        	         }
        	    });        		
        	}
         }
    });
}
</script>
</head>
<body class="bdcontent" style="border:1px solid #A4BED4;padding:4px 4px;*padding:3px 4px">

	<br>

	<table border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<td><w:func id="bp.forumGroup.save"><a href="newGroup.jsp" class="easyui-linkbutton" plain="true" iconCls="icon-add">创建新板块</a></w:func>
				</td>
			</tr>
		</tbody>
	</table>
	<br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr valign="top">
				<td width="99%">
					<div class="forum-table">
						<table id="contents" cellpadding="0" cellspacing="0" width="100%"><thead>
								<tr>
									<th class="forum-firsttd" width="4%">操作</th>
									<th width="20%">板块</th>
									<th width="48%">板块描述</th>
									<th width="8%">创建者</th>
									<th width="8%">创建时间</th>
									<th width="8%" style="border-right : 1px #A4BED4 solid;">更新时间</th>
								</tr>
							<tbody>
								<tr id="groupContents" style="display:none">
								
									<td id="manage" nowrap="nowrap" class="forum-firsttd" >
										<w:func id="bp.forumGroup.save">																	
											<a id="modifytp" href="newGroup.jsp">修改</a> 
											<a id="deletetp" href="#" onclick="deleteGroup(event)">删除</a>
										</w:func>
									</td>								
									<td  id="gpname" nowrap="nowrap" ><a id="agpname" href="articleFlat.jsp" style="text-decoration: NONE"></a></td>
									<td  id="gpdesc" nowrap="nowrap" ></td>
									<td  id="ctname" nowrap="nowrap" ></td>
									<td  id="cttime" nowrap="nowrap" ></td>
									<td  id="gpid" nowrap="nowrap" style="display:none"></td>
									<td  id="uttime" nowrap="nowrap" class="forum-lssttd"></td>
								</tr>							
							</tbody>
						</table>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
