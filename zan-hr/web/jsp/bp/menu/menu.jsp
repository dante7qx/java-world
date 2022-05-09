<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>菜单管理</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<script type="text/javascript" src="${ctx}/ux/iColorPicker.js"></script>
<style>
	html,body{height:100%;}
</style>
<script>
	var imageUrl = '${ctx}/ux/themes/icons/color.png';
	var menuKinds=<w:map2json name="BP_MENU.MENU_KIND"/>;
	$(function() {
		$('#tt').tree({
            animate: true,
            url: 'bp.menu.MenuAction$query.json',
            onClick: function (node) {
            	if(node.id != -1){
            		$('#menuInfoForm').form('clear');
            		var pnode = $(this).tree('getParent', node.target);
                	$("#txtparent").text(pnode.text);
                    //getFuncCode();
                    $('#menuInfoForm').form('load',node.attributes);
                    var bg = node.attributes.backColor;
                	$('#backColor').css('background', bg == null ? "none" : bg);
                    $("#opt").val('update');
                    $("#content").css("display","");
                    var child = $(this).tree('getChildren', node.target);
                    if(child != null ){
                    	addGrid(node.attributes.menuId,node.attributes.name);
                    }
            	}else{
            		  document.getElementById( "content").style.display="none";
            	}
            },
            onContextMenu: function (e, node) {
                e.preventDefault();
                $('#tt').tree('select', node.target);
                if(node.id == -1){
               	$('#root').menu('show', {
                       left: e.pageX,
                       top: e.pageY
                   });
                }else{
                	$('#child').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                }
            }
        });

		//resize scroll of panel
		$("#navtree").panel({
			height:$("#menuPanel").panel("options").height - 65
		});

		getFuncCode();

		//resize true
		$("#menuPanel").panel({
			onResize:function(width,height){
				$("#navtree").panel("resize",{width:width-7,height:$("#menuPanel").panel("options").height - 65});
			}
		});

		$('#menuKind').combobox({
			data:menuKinds,
			editable:false,
			valueField:'key',
			textField:'value'
		});

	});
	function addGrid(id,title){
		$('#childGrid').datagrid({
		    title:title+'子列表',
		    fitColumns: true,
			nowrap: false,
			striped: true,
			collapsible: false,
			remoteSort: false,
			singleSelect:true,
			url:'bp.menu.MenuAction$queryChild.json',
			queryParams:{parentId:id}, //查询条件
			idField:'menuId',
			columns:[[
				{title:'菜单名称',field:'name',width:120},
				{field:'url',title:'菜单URL',width:150},
				{field:'menuOrder',title:'菜单顺序',width:60},
				{field:'funcName',title:'功能ID',width:200}
			]],
			pagination:false
		});
	}

	function expMenu() {
		 var url = "bp.menu.MenuAction$download.json";
		 window.location.href=url;
	}
	function addMenu(){
		var node = $('#tt').tree('getSelected');
		if(node != null){
			$('#menuInfoForm').form('clear');
			$("#opt").val('add');
			$("#txtparent").text(node.text);
			$("#parentId").val(node.id);
			//getFuncCode();
			document.getElementById( "content").style.display="";
            $("#childGrid").parents(".datagrid").hide();
		}else{
			$.messager.alert('提示','请选择节点！');
		}
	}
	function menuButton(type){
		var clickNode = $('#tt').tree('getSelected');
		if(clickNode){
			if(clickNode.id != -1){
				if(type == 'move'){
					menuMove()
				}else if(type == 'del'){
					menuDelete();
				}
			}else{
				$.messager.alert('提示','该节点无此操作！');
			}
		}else{
			$.messager.alert('提示','请选择节点！');
		}
	 }
    function menuMove(){
    	var clickNode = $('#tt').tree('getSelected');
    	var nodeId = clickNode.attributes.menuId;
    	//var pnode = $(this).tree('getParent', clickNode.target);

    	if(clickNode.id != null){
    		 $('#minwin').window('open');
    			$('#minmenu').tree({
    				animate: true,
    		        url: 'bp.menu.MenuAction$queryById.json?id='+nodeId,
    				onClick : function(node) {
    					/*parentId = node.id;*/
    				}
    			});

   			$("#link_task_save").click(function(){
   				if(nodeId != null){
   			    var minmenuNode = $('#minmenu').tree('getSelected');
   				var parentId = minmenuNode.attributes.menuId;
   				if(parentId != null){
   				 $.ajax({
   					type: "POST",
   					url: "bp.menu.MenuAction$updateParentId.json",
   					data: {
   						id : nodeId,
   			 			parentId:parentId
   					},
   					success: function(data) {
   						$('#minwin').window('close');
			 			 $('#tt').tree('reload');
   					},
   					error: function(msg) {
   						$.messager.alert('错误','节点移动失败！','error');
   					}
   				});
   			   }else{
   				$.messager.alert('提示','请选择节点！');
   			   }
   				}
   			});
    	}
	}

    function menuDelete(){
    	var node = $('#tt').tree('getSelected');
    	if(node.id != null){
    		 $.messager.confirm('确认','确认要删除该节点包含的所有节点？',function(ok){
				 if(ok){
					 $.post("bp.menu.MenuAction$delete.json", {
				 			id : node.attributes.menuId
				 		}, function(data) {
				 			 if (data.success) {
				 				$('#tt').tree('reload');
				 				 document.getElementById("content").style.display="none";
					    	}else{
					    		$.messager.alert('错误','删除失败！','error');
					    	}
				 		},"json");
				 }
			 });
    	}
    }
    function getFuncCode(){
    	$('#funcCode').combotree({
    	    url:'bp.function.FunctionAction$query.json',
    	    /*  valueField:'id',
    	    textField:'text',  */
    	    width:260
    	});
    }
   function saveSubmit(){
	   var val = $('#funcCode').combotree('getValue');
		$('#funcCode').val(val);
	   $('#menuInfoForm').form('submit',{
		     url: "bp.menu.MenuAction$update.json",
		     onSubmit: function(){
		    	 return $(this).form('validate');
		      },
		     success:function(data){
		    	 var cToObj=eval('(' + data + ')');
		    	 if (cToObj.success) {
		    		 $('#tt').tree('reload');
		    		 document.getElementById("content").style.display="none";
		    	}else{
		    		$.messager.alert('错误','保存失败！','error');
		    	}
		    }
		});
   }
   function menuReset(){
	   var opt = $('#opt').val();
	   if(opt == 'add'){
		   $('#menuInfoForm').form('clear');
		   $("#opt").val('add');
	   }else if(opt == 'update'){
		   $('#menuInfoForm').form('clear');
		   var node = $('#tt').tree('getSelected');
		   var pnode = $(this).tree('getParent', node.target);
       	   $("#txtparent").text(pnode.text);
           //getFuncCode();
           $('#menuInfoForm').form('load',node.attributes);
           $("#opt").val('update');
	   }

   }
   function winClose(){
		 $('#minwin').window('close');
	 }
</script>
</head>
<body >
<table id="tb" class="body_layout" cellspacing="0">
		<tr>
			<td valign="top" class="body_layout_left">
				<div id="cc" class="easyui-layout" fit=true>
				<div id="menuPanel" region="west" split="true" style="width: 200px;">
				 <div class="easyui-panel"  title="菜单管理" id ="navTitle" fit="true" border="false">
					<div id="toolbar" style="padding:5px;background:#fafafa;">
						<div style ="text-align:right">
							<w:func id="bp.menu.download">
							<a href="#" class="easyui-linkbutton" iconCls="icon-down" plain="true" title="导出菜单" onclick="javascript:expMenu()"></a>
							</w:func>
							<w:func id="bp.menu.update">
							<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" title="增加下级" onclick="javascript:addMenu()"></a>
							</w:func>
							 <w:func id="bp.menu.update">
							<a href="#" class="easyui-linkbutton" iconCls="icon-move" plain="true" title="移到" onclick="javascript:menuButton('move')"></a>
							</w:func>
							 <w:func id="bp.menu.delete">
							<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" title="删除" onclick="javascript:menuButton('del')"></a>
							</w:func>
						</div>
					</div>
					<div class="easyui-panel" id="navtree" border="false" >
						<ul id="tt"  animate="true"></ul>
					</div>
				</div>
				</div>
				<!-- <div region="east" split="true" collapsed="true" title="East" style="width:100px;padding:10px;" >east region</div> -->
				<div region="center" title="菜单信息" id="content" style ="display:none">
				   <form action="" method="post" id="menuInfoForm" novalidate>
					<table cellpadding="4" cellspacing="0" class="tbcontent"
						style="width: 800px;margin:10px;" >
						<tr>
						<td width="20%" align="right" class="tbtitle">功能ID</td>
							<td width="30%"><input id="funcCode" name="funcCode" class="easyui-combotree"  required=true   />
							<input id="menuId" name="menuId"  type ="hidden"/>
							<input id="opt" name="opt"  type ="hidden"/>
							</td>
							<td align="right" class="tbtitle">菜单类型</td>
							<td>
								<input id="showIn" name="showIn" class="easyui-validatebox tinput" style="width:300px" maxlength="128" type="text"
								 title="菜单类型关系到菜单展示的地方，由default win8 hnaimp一个或多个组成，default显示在默认的菜单，win8显示在win8界面，hnaimp显示hnaimp风格菜单。"/>
							</td>
						</tr>
						<tr>
							<td align="right" class="tbtitle">菜单名称</td>
							<td><input id="name" name="name" class="easyui-validatebox tinput" maxlength="16" type="text" required=true /></td>
							<td width="20%" align="right" class="tbtitle">win8类型</td>
							<td width="30%">
								<input id="menuKind" name="menuKind" class="easyui-combobox" style="width:100px" />
							</td>
						</tr>
						<tr>
							<td align="right" class="tbtitle">父菜单</td>
							<td><span id="txtparent" ></span><input id="parentId" name="parentId"  type="hidden" /></td>
							<td align="right" class="tbtitle">高度</td>
							<td>
								<input id="height" name="height" class="easyui-numberbox tinput" data-options="min:0,precision:0,max:3" required=true type="text" style="width:50px"
								title="菜单方块高度单位,win8类型时使用。"/>
							</td>
						</tr>
						<tr>
							<td align="right" class="tbtitle">菜单顺序</td>
							<td><input id="menuOrder" name="menuOrder"  class="easyui-numberbox tinput"  type="text" style="width:50px" /></td>
							<td align="right" class="tbtitle">宽度</td>
							<td>
								<input id="width" name="width" class="easyui-numberbox tinput" data-options="min:0,precision:0,max:5" required=true type="text" style="width:50px"
								title="菜单方块宽度单位,win8类型时使用。"/>
							</td>
						</tr>
						<tr>
							<td align="right" class="tbtitle">菜单URL</td>
							<td><input id="url" name="url" type="text" style="width:300px" class="easyui-validatebox tinput"  required=true  /></td>
							<td align="right" class="tbtitle">背景色</td>
							<td><input id="backColor" name="backColor" type="text" class="iColorPicker tinput" readonly="readonly" style="width:50px"
								title="菜单方块背景色,win8类型时使用。"/></td>
						</tr>
						<tr>
							<td align="right" class="tbtitle">菜单信息</td>
							<td>
								<input id="menuInfo" name="menuInfo" class="easyui-validatebox tinput" style="width:300px" maxlength="256" type="text"
								title="菜单附加信息，可不填。目前用于存储win8菜单的背景图片。"/>
							</td>
							<td align="right" class="tbtitle">打开目标</td>
							<td>
								<select id="target" class="easyui-combobox" name="target" style="width:200px;" required=true>
								    <option value="innerTab" selected="selected">站内链接tab窗口打开</option>
								    <option value="outerTab">站外链接tab窗口打开</option>
								    <option value="innerNew">站内链接新窗口打开</option>
								    <option value="outerNew">站外链接新窗口打开</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="center" colspan="4"><a  href="javascript:;" class="easyui-linkbutton" iconCls="icon-save" onclick="saveSubmit();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo" onclick="menuReset();">重置</a>
							</td>
						</tr>
					</table>
					</form>
					<div style="margin:10px;">
					<table id ="childGrid"></table>
					</div>
				</div>
				<div id="root" class="easyui-menu" style="width:120px;">
					<div iconCls="icon-add" onclick="javascript:addMenu()">增加下级</div>
				</div>
				<div id="child" class="easyui-menu" style="width:120px;">
					<div iconCls="icon-add" onclick="javascript:addMenu()">增加下级</div>
					<div iconCls="icon-move" onclick="menuMove()">移到</div>
					<div iconCls="icon-remove" onclick="menuDelete()">删除</div>
				</div>

				<div id="minwin" class="easyui-window" closed="true" modal="true"
					collapsible="false" maximizable="false" resizable="false"
					draggable="false" minimizable="false" inline="true" title="菜单移动" style="height:400px;width:322px">
					<div style ="margin-left:10px;margin-top:10px;margin-bottom:10px;">
					<div style="height:300px;overflow:auto;">
						<ul id="minmenu"></ul>
					</div>
					<div style="text-align:center;position: relative;top:15px;">
						<span class="buttonGaps"><a id="link_task_save"  class="easyui-linkbutton" iconCls="icon-save">保存</a></span>
						<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
								onclick="winClose()"> 取消</a></span>
					</div>
					</div>
				</div>
	   		</div>
		</td>
	</tr>
</table>
</body>
</html>