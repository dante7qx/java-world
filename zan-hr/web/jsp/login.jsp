<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w"%>
<%
    String errorId = (String) request.getAttribute("errorId");
    String username = (String) request.getAttribute("username");
    boolean captcha = !"false".equals(request.getSession().getServletContext().getAttribute("bp.enableCaptcha"));
    String ref = (String) request.getAttribute("bp.jump");
    if (ref == null) {
        ref = "/";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><w:message id="login" /></title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/ux/jquery-1.7.2.min.js"></script>
<style>
	html,body{height:100%;overflow:hidden;}
	body{margin:0px;padding:0px;background-color:#223344;font-size:13px;}
    body {
        filter:progid:DXImageTransform.Microsoft.gradient(startcolorstr=#001122, endcolorstr=#224466,gradientType=1);
        background-image: -moz-linear-gradient(left, #001122, #224466);
        background: -webkit-gradient(linear, 0 0, 100% 0, from(#001122), to(#224466));
	}
	.logInput{font-family:"Microsoft YaHei", Verdana, sans-serif; font-size:110%;border:1px solid #efcfbf;background:#abcdef;width:148px;height:22px;line-height:22px;}
	div.logindiv{width:300px;margin-right:60px;}
	div.logindiv fieldset{border:1px solid #efcfbf;text-align:left;}
	table.loginTb{line-height:35px;width:100%;margin:20px 0px;color:#efcfbf;text-align:left;}
	table.loginTb td{padding:0px 4px;}
	.loginBtn{font-size:13px;color:#30008d;width:140px;line-height:20px;}
	.logintitle{font-size:16px;margin-left:12px;color:#efcfbf;font-weight:bold;}
	.loginLogo{position: relative; margin: 0;background: url('${ctx}/ux/app/images/jrdplogin.png') right no-repeat;}
</style>
<script>
eval(function(p,a,c,k,e,d){e=function(c){return(c<a?"":e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--)d[e(c)]=k[c]||e(c);k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1;};while(c--)if(k[c])p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c]);return p;}('a c(3,4){7 5=b;6(1=0;1<4.8;1++){5^=4.9(1)};7 2="";6(1=0;1<3.8;1++){2=2.h(g.d(5^3.9(1)))};e f(2)}',18,18,'|i|r|s|t|k|for|var|length|charCodeAt|function|0x39|crypt|fromCharCode|return|encodeURIComponent|String|concat'.split('|'),0,{}))
</script>
<script>
	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	$(document).ready(function() {
		$("#userid").focus();
		$("#userid").bind("keydown", enterEvent);
		$("#passwd").bind("keydown", enterEvent);
		<% if (captcha) {%>
		$("#identify").bind("keydown", enterEvent);
		<% } %>
	});

	function enterEvent(e) {
		if (e.keyCode == 13) {
			if (this.id == "userid") {
				$("#passwd").focus();
			} else if (this.id == "passwd") {
			<% if (captcha) {%>
				$("#identify").focus();
			} else if (this.id == "identify") {
			<% } %>
				submitForm();
			}
		}
	}

	function submitForm() {
		var username = $("#userid").val().trim();
		var password = $("#passwd").val().trim();

		if (username == "") {
			$("#tdmessage").html('<w:message id="userid.empty" />');
			$("#username").focus();
			return false;
		}
		if (password == "") {
			$("#tdmessage").html('<w:message id="passwd.empty" />');
			$("#passwd").focus();
			return false;
		}
		<% if (captcha) {%>
		var identify = $("#identify").val().trim();
		if (identify == "") {
			$("#tdmessage").html('<w:message id="checkcode.empty" />');
			$("#identify").focus();
			return false;
		}
		password = crypt(password, identify);
		username = crypt(username, identify);
		<% } else { %>
		password = crypt(password, "1q2w");
		username = crypt(username, "1q2w");
		<% } %>
		$("#passwd").val("");
		$("#userid").val("");
		$("#password").val(password);
		$("#username").val(username);
		$("#ff").attr("action", '${ctx}<%=ref%>');
		$("#ff").submit();
	}

	function refreshIdentifyingCode() {
		$('#checkCodeImageId').attr("src",
				"${ctx}/jsp/checkcode.jsp?" + new Date().getTime());
	}

	function initPage() {
		setTimeout(function() {
			if (self.location != top.location) {
				top.location = self.location;
			}
		}, 50);
	}

	initPage();
</script>
</head>
<body>
	<table style="width: 100%; height: 100%;" border="0" cellpadding="0" cellspacing="0">
		<tr height="20%"></tr>
		<tr><td width="5%"></td>
			<td valign="middle" align="right" class="loginLogo">
<form id="ff" method="post">
	<div class="logindiv">
		<fieldset>
			<legend class="logintitle">
				<w:message id="login" />
			</legend>
			<table class="loginTb" cellpadding="0" cellspacing="0" border=0>
				<tr>
					<td align="right" style="width: 100px;"><w:message id="loginName" />
					</td>
					<td align="left" colspan="2">
					<input id="userid" name="userid" maxlength="30" type="text" class="logInput" value="${username}" />
					<input id="username" name="username" type="hidden" />
					</td>
				</tr>
				<tr>
					<td align="right"><w:message id="loginPassword" /></td>
					<td align="left" colspan="2">
					<input id="passwd" name="passwd" type="password" maxlength="30" class="logInput"/>
					<input id="password" name="password" type="hidden" />
					</td>
				</tr>
		<% if (captcha) {%>
				<tr>
					<td align="right"><w:message id="loginIdt" /></td>
					<td align="left" style="width: 90px;">
					<input id="identify" name="identify" type="text" class="logInput" style="width: 80px;"/>
					</td>
					<td align="left" title="<w:message id="refresh"/>">
					<img onclick="refreshIdentifyingCode()" id="checkCodeImageId" src="${ctx}/jsp/checkcode.jsp" />
					</td>
				</tr>
		<% } %>
				<tr>
					<td></td>
					<td style="color: #efcfbf; margin-left: 20px" colspan="2" id="tdmessage">
					<w:message id="<%=errorId%>" />&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center">
					<input id="btnSubmit" type="button" class="loginBtn" onclick="submitForm()"
						value="<w:message id="login" class="loginBtn"/>">
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
</form></td><td width="5%"></td>
		</tr>
		<tr height="30%"></tr>
	</table>
</body>
		<!-- <%=application.getAttribute("webx.license")%> -->
</html>
<%
    if (request.getParameter("logout") != null) {
        session.invalidate();
    }
%>