<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/ux/app/notybuttons.css">
<title><w:message id="mainTitle" /></title>
<script type="text/javascript" src='${ctx}/ux/app/js/menuLayout.js'></script>
<script type="text/javascript" src='${ctx}/ux/app/js/notifyLayout.js'></script>
<!-- noty -->
<script type="text/javascript" src="${ctx}/ux/app/js/noty/jquery.noty.js"></script>
<script type="text/javascript" src="${ctx}/ux/app/js/noty/layouts/bottomRight.js"></script>
<script type="text/javascript" src="${ctx}/ux/app/js/noty/layouts/center.js"></script>
<script type="text/javascript" src="${ctx}/ux/app/js/noty/themes/default.js"></script>
<script type="text/javascript">
	function changeClock() {
		var d = new Date();
	    var hh = d.getHours()<10?"0"+d.getHours(): d.getHours();
	    var mm = d.getMinutes()<10?"0"+d.getMinutes(): d.getMinutes();
	    var ss = d.getSeconds()<10?"0"+d.getSeconds(): d.getSeconds();
	    var str= d.getFullYear() + "年" + (d.getMonth() + 1) + "月" + d.getDate() + "日 " + hh + ":" + mm + ":" + ss;
	    $("#divclock").text(str);  
	}
	
	var northHeight=102;
	var logoHeight=75;
	var menuHeight=27;
	
	$(function(){		
		window.setInterval(changeClock, 1000);	
		$("#btndisplay").toggle(
			function (){
				$("#divlogo").hide();
				$('#cclayout').layout("panel", "north").panel('resize', {height : menuHeight});
				$('#cclayout').layout("panel", "west").panel('resize', {
					height: $('#cclayout').layout("panel", "west").panel("options").height + logoHeight,
					top :menuHeight
				});
				$('#cclayout').layout("panel", "center").panel('resize', {
					height:$('#cclayout').layout("panel", "center").panel("options").height + logoHeight,
					top :menuHeight
				});
				$("#btndisplay").removeClass("btnup")
				$("#btndisplay").addClass("btndown");
			},
			function (){
				$("#divlogo").show();
				$('#cclayout').layout("panel","north").panel('resize',{height:northHeight});
				$('#cclayout').layout("panel", "west").panel('resize', {
					height:$('#cclayout').layout("panel", "west").panel("options").height - logoHeight,
					top :northHeight
				});
				$('#cclayout').layout("panel", "center").panel('resize', {
					height:$('#cclayout').layout("panel", "center").panel("options").height - logoHeight,
					top :northHeight
				});
				
				$("#btndisplay").removeClass("btndown")
				$("#btndisplay").addClass("btnup");
			}
		);
		
		//bind left show event
		$("#btnmenudisplay").toggle(
			leftMenuHide,
			leftMenuShow
		); 
		
		//define left menu showed state
		if($("#west").css("display") == "none"){
			$("#btnmenudisplay").click();
		}
		
		initMenu();
		menuMouseMove.initTopMenuTree();
	});
	
	function leftMenuHide(){
		$('#cclayout').layout('collapse','west');
		$("#btnmenudisplay").removeClass("btnleft");
		$("#btnmenudisplay").addClass("btnright");
		menuMouseMove.bindMenuHover();
	}
	function leftMenuShow(){
		$('#cclayout').layout('expand','west');
		$("#btnmenudisplay").removeClass("btnright");
		$("#btnmenudisplay").addClass("btnleft");
		menuMouseMove.unbindMenuHover();
	}
	
	function loadWin(strUrl,options,callback){
		$("#cclayout").layout("loading");
		$("#minwin").load(strUrl);
		var bwidth= $("body").width();
		var bheight= $("body").height();
		$('#minwin').window({   
			title:options.title,
			width:options.width,
			height:options.height,   
			modal:options.modal,
			closable:options.closable,
			cache:false,
			left:(bwidth-options.width)/2,
			top:(bheight-options.height)/2,
			onClose:function(){
				$(".easyui-validatebox").removeClass('validatebox-invalid');
				$("#cclayout").layout("loaded");
				if(callback){
					callback();
				}
			},
			onMove: function(){
    			var offset = $(this).parent().offset();
    			if(offset.left<-2){
    				$(this).parent().css("left","0");
    			}
    			if(offset.top<-2){
    				$(this).parent().css("top","0");
    			}
    		}	
		});
		$('#minwin').window('open');
	}
	
	function closeWin(){
		$('#minwin').window('close');
	}
    
    window.onresize=function(){
    	initMenu(true);
    }
   
</script>
</head>
<body id="cclayout" class="easyui-layout" scroll="no">
	<div region="north" border="false" class="laytop">
		<div id="divlogo" class="logo"
		style="height: 75px; background: url(${ctx}/ux/app/images/jrdpbar.png) no-repeat; width: 100%; overflow: hidden; font-size: 0px;"></div>
		<div class="menucont">
			<div class="loginfo">
				<div class="btndiv">
					<a id="btndisplay" href='#' class="displaybtn btnup"></a>
				</div>
				<div class="btndiv">
					<a id="btnmenudisplay" href='#' class="displaybtn btnleft"></a>
				</div>						
				<div class="user">当前用户: <span style="margin-right:10px;">${userId}</span>
					<span id="invalidate"><a id="invalidateHref" href="${ctx}/jsp/login.jsp?logout=1" onclick="">注销</a></span>
					<span id="divclock"></span>
				</div>
			</div>
			<div id="nmenus" class="menus">
				<div class='menuscrollerleft'></div>
				<div class='menuscrollerright'></div>
				<div class='menuwrap'>
					<ul id='css3menu'></ul>
				</div>
				
			</div>
		</div>
	</div>
	<div id="mainPanel" border="false" region="center" class="laymain">
		<div id="tabs" class="easyui-tabs" fit="true" border="false"
			style="overflow: hidden;"></div>
	</div>
	<div region="west" hide="true" split="true" border="false" class="layleft" id="west" collapsed="true" style="display:none;">
		<div class="left-header" id ="navTitle" >导航菜单</div>
		<div id='wnav' animate="true" style="margin-top:6px;"></div>
	</div>
	<!-- <div region="south" border="false" style="display:none;height: 0px" class="laybottom">
		<w:message id="copyRight" />
		<%=application.getAttribute("webx.license")%>
	</div> -->
	<div id="submenu" style="position: absolute;display:none;z-index:5;width:300px;"></div>
	<div id="minwin" class="easyui-window" shadow="false" closed="true" style="left:0px;top:0px;"
		minimizable="false" collapsible="false" maximizable="false"></div>
<%
	Long time = (Long) application.getAttribute("webx.license.trial.time");
	if (time != null) {
	    long days = (time - System.currentTimeMillis()) / 1000 / 3600 / 24;
	    if (days < 31) {
	    	String licAlert = "'系统试用期限还剩"+days+"天! 请联系管理员更新授权!'";
%>
			<script type="text/javascript">
			alert(<%=licAlert%>);
			</script>
<%
		}
	}
%>
</body>
</html>
