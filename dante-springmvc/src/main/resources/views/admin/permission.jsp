<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/ztree/css/demo.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="${ctx}/assets/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
$(function() {
	PermissionPage.initLayout();
	PermissionPage.loadPermissionTree();
});
var PermissionPage = {
	initLayout: function() {
		$('#permissionDiv').layout('resize', {
			width: $(window).width() - 20,
			height: $(window).height() - 90
		})
	},
    loadPermissionTree: function() {
		   var zTreeObj;
		   var setting = {
			   callback: {
				   onClick: function(event, treeId, treeNode) {
					   PermissionPage.loadFormByNode(treeNode);
				   }
			   }
		   };
		   $.ajax({
			   type: 'POST',
			   url: '${ctx}/permission/querytree',
			   data: {},
			   success: function(data) {
				   zTreeObj = $.fn.zTree.init($("#permissionTree"), setting, data);
			   }
		   });
	},
	loadFormByNode: function(node) {
		$('#permissionFrm').form('clear').form('load', node);
	},
	addNode: function() {
		var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	    var nodes = treeObj.getSelectedNodes();
	    if(nodes.length == 0) {
	    	$.messager.alert('提示','请选择节点！');
	    	return;
	    }
	    var node = nodes[0];
	    var newNode = {};
	    newNode.id = '';
	    newNode.pid = node.id;
	    newNode.name = '';
	    newNode.code = '';
	    this.loadFormByNode(newNode);
    },
    delNode: function() {
    	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
	    var nodes = treeObj.getSelectedNodes();
	    if(nodes.length == 0) {
	    	$.messager.alert('提示','请选择节点！');
	    	return;
	    } else {
	    	$.ajax({
	 		   type: 'POST',
	 		   url: '${ctx}/permission/deleteById',
	 		   data: {
	 			   id: nodes[0].id
	 		   },
	 		   success: function(result) {
	 			   try {
	 				  if(result.flag) {
	 					 treeObj.removeNode(nodes[0]);
	 					 $('#permissionFrm').form('clear');
	 				  } else {
	 					 $.messager.alert('错误','删除失败！');
	 				  }
	 			   } catch (e) {
	 				  $.messager.alert('错误',data);
	 			   }
	 		   }
	 	   });
	    }
    },
    updateNode: function() {
    	$('#permissionFrm').form('submit', {
    		url: '${ctx}/permission/updatetree',
    	    success: function(data){
    	    	try {
    	    		var result = eval('(' + data + ')');
        	        if (result.flag){
        	        	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
        	        	treeObj.addNodes(treeObj.getSelectedNodes()[0], result.data);
        	        } else {
        	        	$.messager.alert('错误','保存失败！');
        	        }
    	    	} catch(e) {
    	    		$.messager.alert('错误',data);
    	    	}
    	    }
    	});
    }
};
</script>
</head>
<body>
	<h1 align="center">权限管理</h1>
	<div id="permissionDiv" class="easyui-layout">
		<div data-options="region:'west',split:true" title="功能权限" style="width:260px;">
			<div style="padding: 10px;">
				<span onclick="PermissionPage.addNode()">
					<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加节点</a>
				</span>
				<span onclick="PermissionPage.delNode()">
					<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-remove">删除节点</a>
				</span>
			</div>
			<div>
				<ul id="permissionTree" class="ztree"></ul>
			</div>
		</div>
		<div data-options="region:'center',title:'权限设置'">
			<div style="margin:15px;padding:10px;">
				<form id="permissionFrm" method="post">
					<input type="hidden" id="id" name="id"/>
					<input type="hidden" id="pid" name="pid"/>
					<div style="margin:5px;">
				        <label>名称:</label>
				        <input class="easyui-validatebox" type="text" id="name" name="name" required="true"/>
				    </div>
				    <div style="margin:5px;">
				        <label>权限:</label>
				        <input class="easyui-validatebox" type="text" id="code" name="code" required="true" />
				    </div>
				    <div>
				    	<span onclick="PermissionPage.updateNode()">
				    		<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-save">保存</a>
				    	</span>
				    </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>