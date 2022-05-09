<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/ztree/css/demo.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/ztree/css/zTreeStyle/zTreeStyle.css">
<style type="text/css">
#menuFrm input {
	width: 150px;
}
</style>
<script type="text/javascript" src="${ctx}/assets/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/ztree/jquery.ztree.all.min.js"></script>
<script type="text/javascript">
$(function() {
	MenuPage.initLayout();
	MenuPage.loadMenuTree();
	MenuPage.loadPermissionCombotree();
});
var MenuPage = {
	initLayout: function() {
		$('#menuDiv').layout('resize', {
			width: $(window).width() - 20,
			height: $(window).height() - 90
		})
	},
    loadMenuTree: function() {
		   var zTreeObj;
		   var setting = {
			   callback: {
				   onClick: function(event, treeId, treeNode) {
					   MenuPage.loadFormByNode(treeNode);
				   }
			   }
		   };
		   $.ajax({
			   type: 'POST',
			   url: '${ctx}/menu/querytree',
			   data: {},
			   success: function(data) {
				   zTreeObj = $.fn.zTree.init($("#menuTree"), setting, data);
			   }
		   });
	},
	loadPermissionCombotree: function(permissionId) {
		$('#permissionId','#menuFrm').combotree({
		    url: '${ctx}/permission/querytree',
		    panelWidth: 200,
		    panelHeight: 260,
		    required: true
		});
	},
	loadFormByNode: function(node) {
		$('#menuFrm').form('clear').form('load', node);
	},
	addNode: function() {
		var treeObj = $.fn.zTree.getZTreeObj("menuTree");
	    var nodes = treeObj.getSelectedNodes();
	    var newNode = {
	    		id: '',
	    		pid: -1,
	    		name: '',
	    		pathUrl: '',
	    		permissionId: ''
	    };
	    if(nodes.length > 0) {
	    	newNode.pid = nodes[0].id;
	    }
	    this.loadFormByNode(newNode);
    },
    delNode: function() {
    	
    },
    updateNode: function() {
    	var pid = $('#pid','#menuFrm').val();
    	$('#pid','#menuFrm').val(pid ? pid : '-1');
    	$('#menuFrm').form('submit', {
    		url: '${ctx}/menu/updatetree',
    	    success: function(data){
    	    	try {
    	    		var result = eval('(' + data + ')');
        	        if (result.flag){
//         	            alert(result.data['code'])
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
	<h1 align="center">菜单管理</h1>
	<div id="menuDiv" class="easyui-layout">
		<div data-options="region:'west',split:true" title="功能菜单" style="width:260px;">
			<div style="padding: 10px;">
				<span onclick="MenuPage.addNode()">
					<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-add">添加节点</a>
				</span>
				<span onclick="MenuPage.delNode()">
					<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-remove">删除节点</a>
				</span>
			</div>
			<div>
				<ul id="menuTree" class="ztree"></ul>
			</div>
		</div>
		<div data-options="region:'center',title:'菜单设置'">
			<div style="margin:15px;padding:10px;">
				<form id="menuFrm" method="post">
					<input type="hidden" id="id" name="id"/>
					<input type="hidden" id="pid" name="pid"/>
					<div style="margin:5px;">
				        <label>名称:</label>
				        <input class="easyui-validatebox" type="text" id="name" name="name" required="true"/>
				    </div>
				    <div style="margin:5px;">
				        <label>链接:</label>
				        <input class="easyui-validatebox" type="text" id="pathUrl" name="pathUrl" required="true" />
				    </div>
				    <div style="margin:5px;">
				        <label>顺序:</label>
				        <input class="easyui-validatebox" type="text" id="showOrder" name="showOrder" required="true" />
				    </div>
				    <div style="margin:5px;">
				        <label>权限:</label>
				        <input id="permissionId" name="permissionId" />
				    </div>
				    <div>
				    	<span onclick="MenuPage.updateNode()">
				    		<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-save">保存</a>
				    	</span>
				    </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>