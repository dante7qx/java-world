<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>审计日志</title>
	<%@ include file="/jsp/common/common.jsp"%>
	<script type="text/javascript">	
		
		$(function(){
				var lastIndex;
				$('#auditLog').datagrid({
					title:'审计日志',
					collapsible:false,
					fitColumns: true,
					nowrap: false,
					striped: true,
					url:'bp.audit.AuditLogAction$query.json',
					sortName: 'operationTime',
					sortOrder: 'desc',
					remoteSort: true,
					idField:'logId',
					singleSelect: true,
					pagination: true,
					rownumbers: true,
					pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
					pageList: [<%=pageList%>],//可以设置每页记录条数的列表 
					columns:[
								[
								{field:'logId',title:'审计日志ID',width:60,sortable:true},
								{field:'userId',title:'用户账号',width:80,sortable:true},
								{field:'userName',title:'用户名',width:80,sortable:false},
								{field:'remoteAddr',title:'IP地址',width:70,sortable:false},
								{field:'moduleName',title:'模块名称',width:110,sortable:false},
								{field:'funcName',title:'功能名称',width:100,sortable:false},
								{field:'operationTime',title:'操作时间',width:120,sortable:true}
								]
							],
					toolbar:"#tb"
			});
				
		});	
		function doSearch(){
			$('#auditLog').datagrid('load',{
				userId: $('#userId').val(),
				userName: $('#userName').val(),
				searchTime:$("#operationTime").datebox('getValue'),
				moduleName:$("#moduleName").val(),
				funcName:$("#funcName").val()
			});
		}
		
		function doReset(){
			$('#userId').attr("value","");
			$('#userName').attr("value","");
			$('#operationTime').datebox('setValue',''); 
			$("#moduleName").attr("value","");
			$("#funcName").attr("value","");
		}
		
	</script>
</head>
<body>
	<div class="bdcontent">
		<table id="auditLog"></table>
	</div>
	<div id="tb" style="height:60px;">
			   <span> 用户账号</span>  
				<input id="userId" type="text" maxlength="32" class="tinput"/>
				<span> 用户名</span> 
				<input id="userName" type="text" maxlength="32" class="tinput"/>
				<span> 操作时间</span> 
				<input id="operationTime" editable="false" class="easyui-datebox" />
				<span> 模块名称</span> 
				<input id="moduleName" type="text" maxlength="32" class="tinput"/>
				<span> 功能名称</span>
				<input id="funcName" type="text" maxlength="32" class="tinput"/>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
		    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doReset()">重置</a>
	</div>
</body>
</html>