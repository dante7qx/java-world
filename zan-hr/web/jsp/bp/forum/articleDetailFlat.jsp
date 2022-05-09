<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>帖子详细内容</title>
<meta http-equiv="content-type" content="text/html; charset=utf8">
<%@ include file="/jsp/common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/ux/ForumStyle.css">
<script type="text/javascript">
function getParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search.substr(1)).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }		
$(function(){
	var topicid = getParam("id");
	var localgpid = getParam("gpid");
	var localgpname = getParam("gpname");
	var currgphref = "articleFlat.jsp?gpid="+localgpid+"&gpname="+localgpname;
	$("#totopicshow").attr("href",currgphref);
	$(".currgp").attr("href",currgphref).text(localgpname);
    $.ajax({
    	type: "POST",
        url:"bp.forum.ForumTopicAction$query.json",
		data : {
			rootId : topicid
		},
        dataType:"json",
        success:function(data, textStatus){	
        	for(i=0;i<data.total;i++)
        	{
      			var row=$("#detailTopics").clone();
				var modifyhref = "modifyTopic.jsp?id="+ data.rows[i].topicId+"&rootId="+data.rows[i].rootId+"&gpid="+localgpid+"&gpname="+localgpname;
				//var deletehref = "deleteTopic.jsp?id="+data.rows[i].topicId+"&isLeaf="+data.rows[i].isleaf+"&pid="+data.rows[i].parentId+"&from="+window.location.href;
				var deletehref = "deleteTopic.jsp?id="+ data.rows[i].topicId+"&pid="+data.rows[i].parentId+"&gpid="+localgpid+"&gpname="+localgpname;
				var replyhref = "replyTopic.jsp?id="+data.rows[i].topicId+"&rootId="+data.rows[i].rootId+"&gpid="+localgpid+"&gpname="+localgpname;
				var floor = i == 0 ? "楼主" : "" + i + "楼" +"-------"+data.rows[i].topicName;
        		if(i==0){
        			$("#tpname").text("主题："+data.rows[0].topicName);
        			$("#replycurr").attr("href",replyhref)
        			}				
				row.find("#modifytp").text("修改").attr("href",modifyhref);
				row.find("#deletetp").text("删除").attr("href",deletehref);
				row.find("#replytp").text("回复").attr("href",replyhref);
				
				row.find("#ctname").text(data.rows[i].createrName);
				row.find("#floorIdx").text(floor);
				row.find("#tpcontext").html(data.rows[i].topicContext);
				
				row.attr("class","forum-odd newrows");
				$("#detailTopics").parent().append(row);
    　　　　　　}
        	$(".newrows").attr("style","display:");
         }
    });	
});
</script>
</head>
<body class="bdcontent" style="border:1px solid #A4BED4;padding:4px 4px;*padding:3px 4px">

	<br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr valign="top">
				<td width="99%">
					<p><a href="ForumGroup.jsp">首页</a> &#187; 
					<a class="currgp" href="articleFlat.jsp"></a>
					</p>
					<h2 id="tpname">
					</h2>
			</tr>
		</tbody>
	</table>
	<table border="0" cellpadding="0" cellspacing="0">
		<tbody>
			<tr>
				<td>
					<a id="replycurr" href="replyTopic.jsp">回复本主题</a>
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
						<div class="forum-messagebox">
							<table border="0" cellpadding="0"cellspacing="0" width="100%">
								<tbody>
									<tr id="detailTopics" class="forum-odd" valign="top" style="display:none">
										<td class="forum-firsttd" >
											<!-- 个人信息的table -->
											<table border="0" cellpadding="0" cellspacing="0" width="150">
												<tbody>
													<tr class="forum-detail">
														<td>
															<table border="0" cellpadding="0"
																	cellspacing="0" width="100%">
																	<tbody>
																		<tr valign="top">
																			<td style="padding: 0px;" width="1%">
																				<nobr>
																					<span id="ctname"></span>
																				</nobr>
																			</td>
																			<td style="padding: 0px;" width="99%">
																			<br>
																			</td>
																		</tr>
																	</tbody>
																</table> 
																<br> <br> 
																<br> <br> 
														</td>
													</tr>
												</tbody>
											</table> <!--个人信息table结束-->
										</td>
										<td width="99%">
											<table border="0"
												cellpadding="0" cellspacing="0" width="100%">
												<tbody>
													<tr class="forum-detail" valign="top">
														<td width="1%"></td>
														<td width="97%"><span id="floorIdx" class="forum-subject"></span></td>
														<td nowrap="nowrap" width="1%"></td>
														<td width="1%">
															<table border="0" cellpadding="0" cellspacing="0">
																<tbody>
																	<tr>
																		<td></td>
																		<td nowrap="nowrap" style="display:">		
																			<w:func id="bp.forumGroup.save">																	
																			<a id="modifytp" href="modifyTopic.jsp"></a> 
																			<a id="deletetp" href="deleteTopic.jsp" ></a>
																			</w:func>
																		</td>
																		<td nowrap="nowrap">
																			<a id="replytp" href="replyTopic.jsp"></a>
																		</td>
																	</tr>
																</tbody>
															</table>
														</td>
													</tr>
													<tr class="forum-detail">
														<td id="tpcontext" colspan="4"
															style="border-top: 1px solid rgb(204, 204, 204);"><br><br><br></td>
													</tr>
													<tr class="forum-detail">
														<td colspan="4" style="font-size: 9pt;"><br><br></td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div>
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
													<td><a id="totopicshow"
														href="articleFlat.jsp">返回到主题列表</a>
													</td>
												</tr>
											</tbody>
										</table>
									</td>
									<td nowrap="nowrap" width="1%">&nbsp;</td>
								</tr>
							</tbody>
						</table>
					</div>
				</td>
				<td width="1%">&nbsp;</td>
			</tr>
		</tbody>
	</table>

</body>
</html>
