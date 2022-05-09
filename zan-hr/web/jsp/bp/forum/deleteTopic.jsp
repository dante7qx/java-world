<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/ux/ForumStyle.css">
<script type="text/javascript">
$(function(){
	var id = getParam("id");
	var pid = getParam("pid");
	var gpid = getParam("gpid");
	var gpname = getParam("gpname");
	var href = "articleFlat.jsp?gpid=" + gpid + "&gpname=" + gpname;
	$("#currtp").attr("href",href);

    $.ajax({
    	type: "POST",
        url:"bp.forum.ForumTopicAction$delete.json",
        data:{
        	topicId : id,
        	parentId : pid
        },
        dataType:"json",
        success:function(data, textStatus){
        	delayURL(href);
         }
    });
});
function delayURL(url) {
	var sec = $("#time").html();
	if(sec > 0) {
		sec--;
		$("#time").html(sec);
	} else {
		window.location.href = url;
    }
    setTimeout("delayURL('" + url + "')", 1000); 
}
function getParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search.substr(1)).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }	

</script>
<span id="time" style="background:red">3</span>秒钟后自动跳转，如果不跳转，请点击下面链接<a id="currtp" href="articleDetailFlat.jsp">主题列表</a>
