<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>BP论坛</title>
<meta http-equiv="content-type" content="text/html; charset=utf8">
<%@ include file="/jsp/common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/ux/ForumStyle.css">
<script type="text/javascript">
function searchSubmit(){
	var searchhref = "searchFlat.jsp?gpid="+getParam("gpid")+"&gpname="+getParam("gpname")+"&keyword="+$("#searchInfo").val();
	window.location.href = searchhref;
}
function getParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search.substr(1)).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }	
$(function(){
	var pageSize = 20;
	var localgpid = getParam("gpid");
	var localgpname = getParam("gpname");
	var currpage = getParam("pageNo");
	if(currpage==null){currpage=1;}
	var startPos = (currpage-1)*pageSize;
	var currgphref = "articleFlat.jsp?gpid="+localgpid+"&gpname="+localgpname;
	var currpsthref = "newTopic.jsp?gpid="+localgpid+"&gpname="+localgpname;
	$(".currgp").attr("href",currgphref).text(localgpname);
	$(".pstnew").attr("href",currpsthref);
    $.ajax({
		type: "POST",
        url:"bp.forum.ForumTopicAction$query.json",
		data : {
			groupId : localgpid,
			parentId : 0,
			isleaf	: startPos
		},
        dataType:"json",
        error:   function(){ 
            //alert(arguments[1]); 
    		},
        success:function(data, textStatus){
        	if(data==null){return;}
        	var totalpage = parseInt((data.rows[0].groupId + pageSize - 1)/pageSize);
			var currpageprev = currpage==1?1:currpage-1;
        	var currpagenext = currpage<totalpage?currpage-0+1:currpage;
        	$("#currpage").text("当前第"+currpage+"页,");
        	$("#totalpage").text("共"+totalpage+"页 -  ");
        	var firstpghref = "articleFlat.jsp?gpid="+localgpid+"&pageNo="+1+"&gpname="+localgpname;;
        	var prevpghref = "articleFlat.jsp?gpid="+localgpid+"&pageNo="+currpageprev+"&gpname="+localgpname;;
        	var nextpghref = "articleFlat.jsp?gpid="+localgpid+"&pageNo="+currpagenext+"&gpname="+localgpname;;
        	var lastpghref = "articleFlat.jsp?gpid="+localgpid+"&pageNo="+totalpage+"&gpname="+localgpname;;
        	$("#firstpg").attr("href",firstpghref);	
        	$("#prevpg").attr("href",prevpghref);	
        	$("#nextpg").attr("href",nextpghref);	
        	$("#lastpg").attr("href",lastpghref);	
        	for(i=0;i<data.total;i++)
    　　　　　　　　{
        				var sty = i%2 == 0 ? "forum-even" : "forum-odd";
        				sty = sty+" newrows";
        				var row=$("#contents").clone();
						var topicnamehref = "articleDetailFlat.jsp?id=" + data.rows[i].topicId+"&gpid="+localgpid+"&gpname="+localgpname;
						var modifyhref = "modifyTopic.jsp?id="+ data.rows[i].topicId+"&rootId="+data.rows[i].rootId+"&gpid="+localgpid+"&gpname="+localgpname;
						var deletehref = "deleteTopic.jsp?id="+data.rows[i].topicId+"&isLeaf="+data.rows[i].isleaf+"&pid="+data.rows[i].parentId+"&from="+window.location.href;
						row.find("#modifytp").text("修改").attr("href",modifyhref);
						row.find("#deletetp").text("删除").attr("href",deletehref);;
						row.find("#ctname").text(data.rows[i].createrName);
						row.find("#topicname").attr("href", topicnamehref).text(data.rows[i].topicName);
						row.find("#ctname").text(data.rows[i].createrName);
						row.find("#ttreply").text(data.rows[i].replycount);
						row.find("#cttime").text(data.rows[i].createTime);
						row.attr("class",sty);
						$("#contents").parent().append(row);

    　　　　　　　　}
			$(".newrows").attr("style","display:");
         }
    });
});
</script>
</head>
<body class="bdcontent" style="border:1px solid #A4BED4;padding:4px 4px;*padding:3px 4px">

	<br>

	<table>
		<tbody>
			<tr class="navigater">
				<td><a href="ForumGroup.jsp">首页</a> &#187;</td>
				<td><a class="currgp" href="articleFlat.jsp"></a>&#187;</td>
				<td><a class="pstnew" href="newTopic.jsp">发表新主题</a></td>
			</tr>
		</tbody>
	</table>
	<!-- 分页 -->
	<table border="0" width="100%">
		<tbody>
			<tr>
				<td width="73%"><span class="nobreak"> 
					<span id="currpage"></span> <span id="totalpage"></span> <span
							class="forum-paginator"> [</span>
					</span> <span class="nobreak"> <span class="forum-paginator">
								<a id="firstpg" href="articleFlat.jsp">首页</a>
						</span>
					</span> <span class="nobreak"> <span class="forum-paginator">|</span>
					</span> <span class="nobreak"> <span class="forum-paginator">
								<a id="prevpg" href="articleFlat.jsp">上一页</a>
						</span>
					</span> <span class="nobreak"> <span class="forum-paginator">|</span>
					</span> <span class="nobreak"> <span class="forum-paginator">
								<a id="nextpg" href="articleFlat.jsp">下一页</a> |&nbsp; <a
								id="lastpg" href="articleFlat.jsp">末页</a> ]
						</span>
					</span>
				</td>
				<td>
					<input id="searchInfo" class="easyui-validatebox" style="width:200px;">
					<span class="buttonGaps">
					<a class="easyui-linkbutton" iconcls="icon-search" href="javascript:;" onclick="searchSubmit();">搜索</a></span>
					
				</td>
				<td></td>
			</tr>
		</tbody>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr valign="top">
				<td width="99%">
					<div class="forum-table">
						<table cellpadding="0" cellspacing="0" width="100%">
							<thead>
								<tr>
									<th width="70%" nowrap="nowrap" class="forum-firsttd">主题</th>
									<th width="10%" nowrap="nowrap">作者</th>
									
									<th width="5%"  nowrap="nowrap">回复</th>
									<th width="10%" nowrap="nowrap" style="border-right : 1px #A4BED4 solid;">创建时间</th>
								</tr>
							</thead>
							<tbody>
								<tr id="contents" style="display: none">
									<td nowrap="nowrap" class="forum-firsttd"><a id="topicname" href="articleDetailFlat.jsp" style="text-decoration: NONE"></a></td>
									<td nowrap="nowrap" id="ctname"></td>
									
									<td nowrap="nowrap" id="ttreply"></td>
									<td nowrap="nowrap" id="cttime" class="forum-lssttd"></td>
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
