var _menus;
var iconImage = 'icon-nav';
var titleIcon = 'icon-sys';
$(function() {
	loadMenus();
	tabClose();
	tabCloseEven();
	// 导航菜单绑定初始化
	$("#wnav").accordion({
		animate : false
	});
	addMenue();
	var firstMenuName = $('#css3menu a:first').attr('name');
	var navPanel = $('#navTitle');
	if (_menus.length == 0) {
		return;
	}
	navPanel.text(_menus[firstMenuName].text);
	if(_menus[firstMenuName].children != null){
	addNav(_menus[firstMenuName].children);
	InitLeftMenu();
	}
	$('#css3menu a').click(function() {
		$('#css3menu a').removeClass('active');
		$(this).addClass('active');
		// 修改导航栏的title
		var navPanel = $('#navTitle');
		navPanel.text($(this).text());
		var d = _menus[$(this).attr('name')].children;
		Clearnav();
		addNav(d);
		InitLeftMenu();
	});
	
	if(_startPage_){
		addTab("起始页", _startPage_, null);
	}
});
function loadMenus() {
	$.ajax({
		url : 'bp.menu.MenuAction$queryForMain.json',
		type : 'post',
		dataType : "json",
		async : false,
		success : function(data) {
			_menus = data;
		}
	});
}
function Clearnav() {
	var pp = $('#wnav').accordion('panels');

	$.each(pp, function(i, n) {
		if (n) {
			var t = n.panel('options').title;
			$('#wnav').accordion('remove', t);
		}
	});

	pp = $('#wnav').accordion('getSelected');
	if (pp) {
		var title = pp.panel('options').title;
		$('#wnav').accordion('remove', title);
	}
}
// 添加菜单
function addMenue() {
	var nmenus = [];
	var subMenu=[];
	$.each(_menus, function(index, e) {
		nmenus.push("<li nodeId='" + e.id + "'>");
		nmenus.push("<a name='" + index + "' href='javascript:;' title='" + e.text + "'> " + e.text + "" + "</a></li>");
		subMenu.push(getChildNode(e.id,e.children,index));
	});
	$('#css3menu').append(nmenus.join(""));
	$("#submenu").append(subMenu.join(""));
}

//add child menu to current node;
function getChildNode(nodeId,chdlist,pIndex) {
    var strHtml = [];
    strHtml.push("<ul pid='"+nodeId+"' >");
    $.each(chdlist, function (index, menuNode) {
    	strHtml.push("<li nodeId='" + menuNode.id + "'>");
        if (menuNode.children && menuNode.children.length > 0) {
        	strHtml.push("<div style='margin:0px 1px;'><a href='javascript:void(0)' onclick='topMenuClick("+pIndex+","+menuNode.id+",this,'"+menuNode.attributes.target+"')' ref='"+menuNode.attributes.url+"'>" + menuNode.text + "</a></div>");
        	strHtml.push( getChildNode(menuNode.id,menuNode.children,pIndex));
        } else {
        	strHtml.push("<a href='javascript:void(0)' onclick='topMenuClick("+pIndex+","+menuNode.id+",this,\""+menuNode.attributes.target+"\")' ref='"+menuNode.attributes.url+"'>" + menuNode.text + "</a>");
        }
        strHtml.push("</li>")
    });
    strHtml.push("</ul>");
    return strHtml.join("");
}

function topMenuClick(index,nodeId,oa, target){
	$("#submenu").hide();
	var url=$(oa).attr("ref");
	var data = _menus[index].children;
	Clearnav();
	addNav(data);
	InitLeftMenu();
	//var node=$('#wnav').tree("find",nodeId);
	//$('#wnav').tree("select",node.target);
	if(url){
		addTab($(oa).text(), url, null, target);
	}
}

function addNav(data) {
	$('#wnav').tree({
		checkbox : false,
		animate : true,
		data : data,
		onClick : function(node) {
			$(this).tree('toggle', node.target);
			var b = $(this).tree('isLeaf', node.target);
			if (b) {
				var url = node.attributes.url;// $("div",
				// node.attributes).attr("url");
				addTab(node.text, url, null, node.attributes.target);
			}
		}
	});
}

// 初始化左侧
function InitLeftMenu() {
	hoverMenuItem();
//	$('#wnav li a').live('click', function() {
//		var tabTitle = $(this).children('.nav').text();
//		var url = $(this).attr("rel");
//		var menuid = $(this).attr("ref");
//		var icon = getIcon(menuid, icon);
//		addTab(tabTitle, url, icon);
//		$('#wnav li div').removeClass("selected");
//		$(this).parent().addClass("selected");
//	});
}

/**
 * 菜单项鼠标Hover
 */
function hoverMenuItem() {
	$(".easyui-accordion").find('a').hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});
}

// 获取左侧导航的图标
function getIcon(menuid) {
	var icon = 'icon ';
	icon += iconImage;
	return icon;
}

function addTab(subtitle, url, icon, target) {
	if(!target){
		target = "innerTab";
	}
	if(target == 'innerNew'){
		window.open(ctxRoot+url, '_blank');
		return;
	}else if(target == 'outerNew'){
		window.open(url, '_blank');
		return;
	}
	if (!$('#tabs').tabs('exists', subtitle)) {
		var length = $('#tabs').tabs('tabs').length;
		if (length >= limitNum) {
			// 第一个Tab选项卡的Title
			var title = $('#tabs').tabs('tabs')[0].panel('options').tab.text();
			// 关闭第一个Tab选项卡
			$('#tabs').tabs('close', title);
		}
		$('#tabs').tabs('add', {
			title : subtitle,
			content : createFrame(url,subtitle, target),
			closable : true,
			icon : icon,
			onResize: function(width,height) {
				$("iframe:first",this).width(width);
				$("iframe:first",this).height(height);
			}
		});
	} else {
		var tab = $('#tabs').tabs('getTab', subtitle);
		$('#tabs').tabs('update', {
			tab : tab,
			options : {
				title : subtitle,
				content : createFrame(url,subtitle, target)
			}
		});
		$('#tabs').tabs('select', subtitle);
	}
	$('#tabs').tabs('getSelected').css('overflow', 'hidden');
	tabClose();
}

function createFrame(url,subtitle, target) {
	var s;
	if(target == 'innerTab'){
		s = '<iframe scrolling="auto" frameborder="0"  src="' + ctxRoot + url
			+ '" style="width:100%;height:100%;"></iframe>';
		return s;
	}
	if(target == 'outerTab'){
		s = '<iframe scrolling="auto" frameborder="0"  src="' +  url
			+ '" style="width:100%;height:100%;"></iframe>';
		return s;
	}
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#tabs').tabs('close', subtitle);
	});
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});
		var subtitle = $(this).children(".tabs-closable").text();
		$('#mm').data("currtab", subtitle);
		$('#tabs').tabs('select', subtitle);
		return false;
	});
}
// 绑定右键菜单事件
function tabCloseEven() {
	// 刷新
	$('#mm-tabupdate').click(function() {
		var currTab = $('#tabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#tabs').tabs('update', {
			tab : currTab,
			options : {
				content : createFrame(url)
			}
		});
	});
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#tabs').tabs('close', currtab_title);
	});
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
			$('#tabs').tabs('close', t);
		});
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			// msgShow('系统提示','后边没有啦~~','error');
			alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#tabs').tabs('close', t);
		});
		return false;
	});

	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide');
	});
}

// 弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
	$.messager.alert(title, msgString, msgType);
}

// 本地时钟
function clockon() {
	var now = new Date();
	var year = now.getFullYear(); // getFullYear getYear
	var month = now.getMonth();
	var date = now.getDate();
	var day = now.getDay();
	var hour = now.getHours();
	var minu = now.getMinutes();
	var sec = now.getSeconds();
	var week;
	month = month + 1;
	if (month < 10)
		month = "0" + month;
	if (date < 10)
		date = "0" + date;
	if (hour < 10)
		hour = "0" + hour;
	if (minu < 10)
		minu = "0" + minu;
	if (sec < 10)
		sec = "0" + sec;
	var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	week = arr_week[day];
	var time = "";
	time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu
			+ ":" + sec + " " + week;

	$("#bgclock").html(time);

	var timer = setTimeout("clockon()", 200);
}


function initMenu(isResize) {
	var menuWidth = $(".menucont").width() - $(".loginfo").width();
	if(isResize){
		menuWidth=$(window).width()-$(".loginfo").width();
	}		
	var tabsWidth=0;
	$("ul li","#nmenus").each(function() {
		tabsWidth += $(this).outerWidth(true);
	});
	var ulWidth = tabsWidth - menuWidth		
	$("#nmenus").width(menuWidth);
	var tabsScrollerLeft = $(".menuscrollerleft","#nmenus");
	var tabsScrollerRight = $(".menuscrollerright","#nmenus").css({right:0});
	var headerWidth = $("div#nmenus").width();
	if (tabsWidth > headerWidth) {
		tabsScrollerLeft.show();
		tabsScrollerRight.show();
		$(".menuwrap").css({
			marginLeft : tabsScrollerLeft.outerWidth(),
			marginRight : tabsScrollerRight.outerWidth() ,
			left : 0,
			width : headerWidth - tabsScrollerLeft.outerWidth() - tabsScrollerRight.outerWidth()
		});
	}else{
		tabsScrollerLeft.hide();
		tabsScrollerRight.hide();
		$(".menuwrap").css({
			marginLeft :0,
			left : 0,
			width : headerWidth
		});
		$(".menuwrap").scrollLeft(0);
	}

    $(".menuscrollerright").click(function (e) {
    	var tabsWrap = $(".menuwrap");
		var pos = Math.min(tabsWrap.scrollLeft() + 100,
				getTabLeftPosition());
		tabsWrap.animate({
			scrollLeft : pos
		}, 400);
    });
    $(".menuscrollerleft").click(function () {
    	var tabsWrap = $(".menuwrap");
		var pos = tabsWrap.scrollLeft() - 100;
		tabsWrap.animate({
			scrollLeft : pos
		}, 400);
    });
    
    function getTabLeftPosition() {
		var tabsHeader = $("div#nmenus");
		var outerWidth = 0;
		$("ul li", tabsHeader).each(function() {
			outerWidth += $(this).outerWidth(true);
		});
		var width = tabsHeader.children("div.menuwrap").width();
		var paddingLeft = parseInt(tabsHeader.find("ul").css("padding-left"));
		return outerWidth - width + paddingLeft;
	}
};

var menuMouseMove={
	menuFlag:false,
	initTopMenuTree:function (){
		//mouse over;
		$("#submenu li").hover(function () {
			menuMouseMove.menuFlag = true;
			menuMouseMove.sublimoveenter(this, $(this).parent().width());
		}, function() {
		    //$(this).css("border-right", "1px solid #ccc");
		    $(this).find("ul").hide();
		    setTimeout(function () {
	            if (!menuMouseMove.menuFlag) {
	                $("#submenu").hide();
	            }
	        }, 500);
		});
		
		$("#submenu").hover(function () {
			menuMouseMove.menuFlag = true;
	    }, function () {
	    	menuMouseMove.menuFlag = false;
	    });
	},
	limoveenter:function (doc, offset) {
	    $("#submenu").children().hide();
	    var oa = $("#submenu").find("ul[pid='" + $(doc).attr("nodeId") + "']");
	    if (oa) {
	        $(oa).show();
	        $("#submenu").css({ "top": offset.top + 27, "left": offset.left });
	        $("#submenu").show();
	        oa.children("li").width(oa.width());
	    }
	},
	sublimoveenter:function (doc, left) {
	    var oa = $(doc).find("ul");
	    if (oa.length) {
	        var liwidth = oa.width();
	        oa.children("li").width(liwidth-2);
	        var offset = $(doc).parent().offset();
	        //$(doc).css("border-right", "0px");
	        oa.css("left", left - 2);
	        oa.css("border-top", "1px solid #ccc");
	        //oa.children().first().css("border-left", "0px");
	        oa.show();
	    }
	},
	bindMenuHover:function (){
		//mouse over;
		$("#css3menu>li").hover(function () {
			menuMouseMove.menuFlag = true;
			menuMouseMove.limoveenter(this, $(this).offset());
	    }, function () {
	        setTimeout(function () {
	            if (!menuMouseMove.menuFlag) {
	                $("#submenu").hide();
	            }
	        }, 500);
	        menuMouseMove.menuFlag = false;
	    });
	},
	unbindMenuHover:function (){
		$("#css3menu>li").unbind('mouseenter mouseleave');
		$("#submenu").hide();
	}
}
