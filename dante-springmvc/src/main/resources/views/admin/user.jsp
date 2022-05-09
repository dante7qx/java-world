<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/assets/easyui/themes/icon.css">
<style type="text/css">
#userFrm input {
	width: 180px;
}
</style>
<script type="text/javascript" src="${ctx}/assets/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${ctx}/assets/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
$(function() {
	UserPage.initUI();
	UserPage.initRoleCombo();
	UserPage.loadUserGrid();
});
var UserPage = {
	initUI: function() {
		$('#userPanel').panel('resize', {
			width: $(window).width() - 345,
			height: $(window).height() - 90
		});
	},
	initRoleCombo: function() {
		$('#roleIds','#userFrm').combobox({
		    url:'${ctx}/role/querytree',
		    valueField:'id',
		    textField:'name',
		    multiple: true,
		    editable: true,
		    required: true
		});
	},
	loadUserGrid: function() {
		$('#userGridlist').datagrid({
		    url: '${ctx}/sysuser/query',
		    singleSelect: true,
		    fitColumns: true,
		    idField: 'id',
		    width: 300,
		    height: $(window).height() - 90,
		    toolbar: '#userToolbar',
		    columns: [[
		        {field:'userName',title:'用户名',halign:'center',width:100},
		        {field:'id',title:'操作',halign:'center',formatter: function(value) {
		        	return "<span><a href='javascript:;'>修改</a>"
		        		  + "<span>&nbsp;&nbsp;</span>"
		        		  <fn:hasPermission name="user:delete">
		        		  +"<a href='javascript:;' onclick='UserPage.deleteUser("+value+")'>删除</a></span>"
		        		  </fn:hasPermission>
		        		  ;
		        }}
		    ]],
		    onClickRow: function(index, row) {
		    	UserPage.loadUser(row['id']);
		    }
		});
	},
	loadUser: function(userId) {
		$.ajax({
			   type: 'POST',
			   url: '${ctx}/sysuser/queryById',
			   data: {
				   id: userId
			   },
			   success: function(data) {
				   $('#roleIds','#userFrm').combobox('clear');
				   $('#userFrm').form('clear').form('load', data);
			   }
		   });
	},
	addUser: function() {
		$('#roleIds','#userFrm').combobox('clear');
	    $('#userFrm').form('clear');
	    $('#userGridlist').datagrid('clearSelections');
	},
	updateUser: function() {
		$('#userFrm').form('submit', {
    		url: '${ctx}/sysuser/update',
    	    success: function(data){
    	    	try {
    	    		var result = eval('(' + data + ')');
        	        if (result.flag){
        	        	$('#userGridlist').datagrid('reload');
        	        	setTimeout(function(){
        	        		$('#userGridlist').datagrid('selectRecord', result.data.id);
        	        	}, 100);
        	        } else {
        	        	$.messager.alert('错误','保存失败！');
        	        }
    	    	} catch(e) {
    	    		$.messager.alert('错误',data);
    	    	}
    	    }
    	});
	},
	deleteUser: function(id) {
		$.ajax({
	 		   type: 'POST',
	 		   url: '${ctx}/sysuser/delete',
	 		   data: {
	 			   id: id
	 		   },
	 		   success: function(result) {
	 			   try {
	 				  if(result.flag) {
	 					 $('#userFrm').form('clear');
	 					$('#userGridlist').datagrid('reload');
	 				  } else {
	 					 $.messager.alert('错误','删除失败！');
	 				  }
	 			   } catch (e) {
	 				  $.messager.alert('错误',data);
	 			   }
	 		   }
	 	   });
	}
};
</script>
</head>
<body>
	<h1 align="center">用户管理</h1>
	<div>
		<div style="float: left;margin-right: 10px;">
			<table id="userGridlist"></table>
		</div>
		<div id="userPanel" class="easyui-panel" style="padding: 10px;">
			<form id="userFrm" method="post">
				<input name="id" type="hidden" />
				<div style="margin:5px;">
		        	<label>用户:</label>
			        <input class="easyui-validatebox" type="text" id="userName" name="userName" required="true"/>
			    </div>
			    <div style="margin:5px;">
		        	<label>密码:</label>
			        <input class="easyui-passwordbox" type="password" id="password" name="password" required="true"/>
			    </div>
			    <div style="margin:5px;">
			        <label>角色:</label>
			        <input id="roleIds" name="roleIds" />
			    </div>
			    <div>
			    	<span onclick="UserPage.updateUser()">
			    		<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-save">保存</a>
			    	</span>
			    </div>
			</form>
		</div>
		<fn:hasPermission name="user:update">
		<div id="userToolbar" style="padding:2px 5px;text-align: right;">
			<span onclick="UserPage.addUser()">
				<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-add">新增</a>
			</span>
		</div>
		</fn:hasPermission>
	</div>
</body>
</html>