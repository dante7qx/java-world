<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/ztree/css/demo.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/ztree/css/zTreeStyle/zTreeStyle.css">
<style type="text/css">
#roleFrm input {
	width: 150px;
}
</style>
<script type="text/javascript" src="${ctx}/assets/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
$(function() {
	RolePage.initLayout();
	RolePage.loadRoleTree();
	RolePage.loadPermissionCombotree();
});
var RolePage = {
	initLayout: function() {
		$('#roleDiv').layout('resize', {
			width: $(window).width() - 20,
			height: $(window).height() - 90
		})
	},
    loadRoleTree: function(queryRole) {
		   var zTreeObj;
		   var setting = {
			   callback: {
				   onClick: function(event, treeId, treeNode) {
					   RolePage.loadFormByNode(treeNode.id);
				   }
			   }
		   };
		   $.ajax({
			   type: 'POST',
			   url: '${ctx}/role/querytree',
			   data: {
				   name: queryRole
			   },
			   success: function(data) {
				   zTreeObj = $.fn.zTree.init($("#roleTree"), setting, data);
			   }
		   });
	},
	loadPermissionCombotree: function(permissionId) {
		$('#permissionIds','#roleFrm').combotree({
		    url: '${ctx}/permission/querytree',
		    panelWidth: 200,
		    panelHeight: 260,
		    required: true,
		    editable: true,
		    multiple: true
		});
	},
	loadFormByNode: function(roleId) {
		$.ajax({
			   type: 'POST',
			   url: '${ctx}/role/queryById',
			   data: {
				   id: roleId
			   },
			   success: function(data) {
				   $('#roleFrm').form('clear').form('load', data);
			   }
		   });
	},
	addNode: function() {
	    var newNode = {
	    		id: '',
	    		name: '',
	    		remark: '',
	    		permissionId: ''
	    };
	    this.loadFormByNode(newNode);
    },
    delNode: function() {
    	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
	    var nodes = treeObj.getSelectedNodes();
	    if(nodes.length == 0) {
	    	$.messager.alert('提示','请选择节点！');
	    	return;
	    } else {
	    	$.ajax({
	 		   type: 'POST',
	 		   url: '${ctx}/role/deleteById',
	 		   data: {
	 			   id: nodes[0].id
	 		   },
	 		   success: function(result) {
	 			   try {
	 				  if(result.flag) {
	 					 treeObj.removeNode(nodes[0]);
	 					 $('#roleFrm').form('clear');
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
    	$('#roleFrm').form('submit', {
    		url: '${ctx}/role/updatetree',
    	    success: function(data){
    	    	try {
    	    		var result = eval('(' + data + ')');
        	        if (result.flag){
        	        	var treeObj = $.fn.zTree.getZTreeObj("roleTree");
        	        	treeObj.addNodes(null, result.data);
        	        } else {
        	        	$.messager.alert('错误','保存失败！');
        	        }
    	    	} catch(e) {
    	    		$.messager.alert('错误',data);
    	    	}
    	    }
    	});
    },
    searchRoleTree: function() {
    	var queryRole = $('#queryRole').val();
    	RolePage.loadRoleTree(queryRole);
    }
};
</script>
</head>
<body>
	<h1 align="center">角色管理</h1>
	<div id="roleDiv" class="easyui-layout">
		<div data-options="region:'west',split:true" title="功能角色" style="width:260px;">
			<div style="padding: 10px;">
				<span onclick="RolePage.addNode()">
					<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加节点</a>
				</span>
				<span onclick="RolePage.delNode()">
					<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-remove">删除节点</a>
				</span>
			</div>
			<div style="padding-left: 10px;">
				<label>角色：</label>
				<span>
					<input type="text" id="queryRole" style="width:120px;"/>
					<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="RolePage.searchRoleTree()"></a>
				</span>
			</div>
			<div>
				<ul id="roleTree" class="ztree"></ul>
			</div>
		</div>
		<div data-options="region:'center',title:'角色设置'">
			<div style="margin:15px;padding:10px;">
				<form id="roleFrm" method="post">
					<input type="hidden" id="id" name="id"/>
					<div style="margin:5px;">
				        <label>名称:</label>
				        <input class="easyui-validatebox" type="text" id="name" name="name" required="true"/>
				    </div>
				    <div style="margin:5px;">
				        <label style="vertical-align:middle;">备注:</label>
				        <textarea class="easyui-validatebox" id="remark" name="remark" required="true" style="width:150px;height:80px;overflow: auto;"></textarea>
				    </div>
				    <div style="margin:5px;">
				        <label>权限:</label>
				        <input id="permissionIds" name="permissionIds" />
				    </div>
				    <div>
				    	<span onclick="RolePage.updateNode()">
				    		<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-save">保存</a>
				    	</span>
				    </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>