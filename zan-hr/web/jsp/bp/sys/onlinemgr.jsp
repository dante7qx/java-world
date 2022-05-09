<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>在线管理</title>
	<%@ include file="/jsp/common/common.jsp"%>
	<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
	<script type="text/javascript">	
		
		$(function(){
				var lastIndex;
				$('#online').datagrid({
					title:'在线管理',
					fitColumns: true,
					nowrap: true,
					striped: true,
					collapsible:false,
					url:'bp.user.OnLineUserAction$query.json',
					sortName: 'birthTime',
					sortOrder: 'desc',
					remoteSort: true,
					idField:'userId',
					singleSelect: true,
					pagination: true,
					rownumbers: true,
					pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
					pageList: [<%=pageList%>],//可以设置每页记录条数的列表 
					frozenColumns:[
			                       	[{field:'ck',checkbox:true}]
			                      ],
					columns:[[
								{field:'userId',title:'用户帐号',width:50,sortable:true},
								{field:'userName',title:'用户姓名',width:50,sortable:false},
								{field:'loginState',title:'在线状态',width:50,sortable:true,formatter:function(value){return value ? '是':'否'}},
								{field:'remoteAddr',title:'IP地址',width:60,sortable:false},
								{field:'organizationName',title:'组织机构',width:70,sortable:false}, 
								{field:'birthTime',title:'登录时间',width:70,sortable:true},
								{field:'sessionId',title:'sessionId',width:10,hidden:true},
								{field:'userAgent',title:'浏览器',width:200}
							]],
					toolbar:"#tb"
			});
		});
			
		
		function doSearch(){
			$('#online').datagrid('load',{
				userId: $('#userId').val(),
				userName: $('#userName').val()
			});
		}
		
		function doReset(){
			$('#userId').attr("value","");
			$('#userName').attr("value","");
		}
		
		function dropOut(){
			var selected = $('#online').datagrid('getSelected');
			if(selected && selected.userId){
				var sessionId = selected.sessionId;
				var userid = selected.userId;
				$.messager.confirm('确认','确认要将 ' + userid + ' 强制退出？',function(ok){  
				    if (ok){  
				    	$.post("bp.user.OnLineUserAction$logOut.json", {
				    		sessionId : sessionId
						}, function(data) {
							 $('#online').datagrid('reload');    
							 $('#online').datagrid('clearSelections');
						});
				    } 
				});	
			}else{
				  $.messager.alert('提示','请选择一行数据后再进行此操作！ ');	
			}
		}
	</script>
</head>
<body>
	<div class="bdcontent">
		<table id="online"></table>
	</div>
	<div id="tb" style="padding:5px;height:auto">

		<span>用户账号</span>  
		<input id="userId" type="text" class="tinput">  
		<span>  用户姓名</span>  
		<input id="userName" type="text" class="tinput">  
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doReset()">重置</a>

	<div style="text-align: right;">
			<w:func id="bp.online.logout">
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="dropOut()">强制退出</a> 
		</w:func>
	</div>
	</div>
</body>
</html>