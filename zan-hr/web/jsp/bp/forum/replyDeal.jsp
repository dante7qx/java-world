<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/jsp/common/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/ux/ForumStyle.css">
<script type="text/javascript">
$(function(){
	var rootid = getParam("rootId");
	var currgphref = "articleDetailFlat.jsp?id=" + rootid+"&gpid="+getParam("gpid")+"&gpname="+getParam("gpname");
	$("#currtp").attr("href",currgphref);
	delayURL(currgphref);
});
function getParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = decodeURI(window.location.search.substr(1)).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }	
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
</script>
<span id="time" style="background:red">3</span>秒钟后自动跳转，如果不跳转，请点击下面链接<a id="currtp" href="articleDetailFlat.jsp">主题列表</a>
