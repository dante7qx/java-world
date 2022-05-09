<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>角色功能</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
	<script charset="utf-8">
		$(function(){
			$('#roleList').datagrid({
				pagination:true,
				rownumbers:true,
				fitColumns: true,
				striped: true,
				collapsible: false,
				pagination: true,
				pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
				pageList: [<%=pageList%>],//可以设置每页记录条数的列表 
				toolbar: "#toolbar",
				frozenColumns:[
		             [{field:'ck',checkbox:true}]
		        ]
			});
		});
		
		function roleSubmit() {
			var datas = $('#functionTree').tree('getChecked');
			var roleId = $('#id').attr('value');
			var funcList = "";
			if(datas.length > 0 ){
				for(var i = 0;i <datas.length;i++ ){
					if(datas[i].attributes.hasChildren == false){
						funcList += datas[i].id;
						funcList += ",";
					}
				}
			}

			$('#funcs').val(funcList);
			$('#roleForm').form('submit',{
				url:"bp.role.RoleAction$saveRoleBean.json",
				success:function(data){
					refreshRoleGrid();
					$('#roleManage').window('close');
				},
				dataType: "json",
				onSubmit: function(){  
			    	 return $(this).form('validate');
			    }
			});
		}
		function refreshRoleGrid(){
			$('#roleList').datagrid('reload');  
			$('#roleList').datagrid('clearSelections');
		}
		function showWindow(){
			var roleManage = $("#roleManage");
			roleManage.window('open');
		}
		function loadData(id){
			$.ajax({
				url:'bp.role.RoleAction$queryRole.json?id='+encodeURIComponent(id),
				type:'get',
				cache:false,
				 dataType: "json",
				 success: function (data){
					 loadRole(data);
				}	
			});
		}
		function loadRole(role){
			 $('#id').attr('value',role.id);
			 $('#name').attr('value',role.name);
			 $('#describe').attr('value',role.describe);	
		}
		function doDelete(id){
			$.ajax({
				type: "POST",
				url: "bp.role.RoleAction$deleteRoleBean.json",             
				data: {id: id},
				success: function(data) {
					if(data) {
						refreshRoleGrid();
					} else {
						$.messager.alert('失败',"该角色正在被使用，不能删除！");   
					}
				},
				error: function(msg) {
					$.messager.alert('错误','删除失败，请联系系统管理员！','error');
				},
				dataType: 'json'
			});
		}
		function resetForm(){
			var id = $('#id').val();
			if (id != ''){
				var selected = $('#roleList').datagrid('getSelected');
				loadRole(selected);
			} else {
				$('#name').attr('value','');
			    $('#describe').attr('value','');	
			}
		}
		function roleListHandler(opt){
			if(opt=="add"){
				setWindowTitle('新增角色');
				$('#roleForm').form('clear');
				$('#functionTree').tree({ 
		            animate: true, 
		            checkbox: true,
		            url: 'bp.role.RoleAction$queryRoleFunction.json?roleId=0'
				});
				showWindow();
			} else if(opt=="edit"){
				setWindowTitle('编辑角色');
				var selected = $('#roleList').datagrid('getSelected');
				if (selected){
					$('#roleForm').form('clear');
					loadData(selected.id);
					$('#functionTree').tree({ 
			            animate: true, 
			            checkbox: true,
			            url: 'bp.role.RoleAction$queryRoleFunction.json?roleId='+selected.id
					});
					showWindow();
				} else {
					$.messager.alert('提示',"请选择一行数据后再进行此操作！");
				}
			} else if(opt == "del"){
				var selected = $('#roleList').datagrid('getSelected');
				if (selected && selected.id) {
			    	 $.messager.confirm('确认','确认要删除该角色？',function(ok){
						 if(ok){
							 doDelete(selected.id);
						 }
					 }); 
			   }else{
				   $.messager.alert('提示',"请选择一行数据后再进行此操作！");
			   }
			}
		}
		function setWindowTitle(title){
			var panelTitle = $('.panel-title',$('#roleManage').parent());
			panelTitle.text(title);
		}
		function closeWindow(){
			var roleManage = $("#roleManage");
			roleManage.window("close");
		}
		
		function showRoleAccount() {
			var selected = $('#roleList').datagrid('getSelected');
			if (selected){
				$('#roleAccountList').datagrid({
				    title:'用户列表',
				    fit: true,
					fitColumns: true,
					striped: true,
					url:'bp.user.LoginUserAction$queryRoleAccount.json',
					sortName: 'updateTime',
					sortOrder: 'desc',
					singleSelect: true,
					pagination: true,
					rownumbers: true,
					idField:'userId',
					pageSize: <%=pageSize%>,
					pageList: [<%=pageList%>],
					queryParams: {roleId: selected['id']},
					columns:[[
						{field:'userId',title:'用户ID',width:80,sortable:true},
						{field:'userName',title:'用户名称',width:80,sortable:true},
						{title:'员工',field:'employeeName',width:80,sortable:false},
						{field:'orgNames',title:'组织机构',width:180,sortable:false},
						{field:'state',title:'状态',width:40,align:'center',sortable:false,formatter:function(value){
							if(value == 1){
								return "正常";
							}else if(value == 2){
								return "锁定";
							}
						}},
						{field:'sysUser',title:'系统标志',width:70,align:'center',sortable:false,formatter:function(value){
							if(value == true){
								return "是";
							}else if(value == false){
								return "否";
							}
						}}
					]]
				});
				$("#roleAccountWin").window('open');
			} else {
				$.messager.alert('提示',"请选择一行数据后再进行此操作！");
			}
		}
	</script>
</head>
<body>
	<div class="bdcontent">
		<table id="roleList"  title="角色功能"  singleSelect="true" url="bp.role.RoleAction$query.json">
			<thead>
				<tr>
					<th field="id" width="60" sortable= true >角色编号</th>
					<th field="name" width="120" sortable= true >角色名称</th>
					<th field="describe" width="180" sortable= true >角色描述</th>
					<th field="updateUser" width="80" sortable= true >更新人</th>
					<th field="updateTime" width="80" sortable= true >更新时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar" style="padding:5px;height:auto">
			<div style="text-align: right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-show" plain="true" onclick="showRoleAccount()">角色下的帐户</a> 
				 <w:func id="bp.role.insert">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="roleListHandler('add')">新增</a> 
				</w:func>
				 <w:func id="bp.role.insert">
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="roleListHandler('edit')">编辑</a>
				</w:func>
				 <w:func id="bp.role.delete">
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="roleListHandler('del')">删除</a> 
				</w:func>
			</div>
		</div>
	</div>
	<div id="roleManage" class="easyui-window" modal="true" inline="false"  collapsible="false" minimizable="false"
	 maximizable="false" resizable="false" closed="true"  title="角色信息">
		<div style="margin:10px;overflow:hidden;">
			<form id = "roleForm" method="post" novalidate >
				<input type="hidden" id="id"  name ="id" >
				<input type="hidden" id="funcs"  name ="funcs" >
				<table id="tt2" class="tbcontent" cellpadding="0" cellspacing="0"  style="width:445px">
						<tr>
							<td align="right" class="tbtitle" style="width:30%;">角色名称</td>
							<td><input type="text" class="easyui-validatebox tinput" name="name" id="name"  size="32" maxlength="32" required="true"></td>
						</tr>
						<tr>
							<td align="right" class="tbtitle" >角色描述</td>
							<td>
								<textarea cols="40" rows="2" name="describe" maxlength="128" class="easyui-validatebox tinput"  required="true" type="text"  id="describe"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right" class="tbtitle">功能</td>
							<td>
							   <div style="height:150px;overflow:auto;">
									<ul id="functionTree"  animate="true"></ul>
							  	</div>
							</td>
						</tr>
				</table>
			</form>
			<div style="text-align: center;margin-top:30px;">
				<span class="buttonGaps"><a class="easyui-linkbutton" iconCls="icon-save" href="javascript:;" onclick="roleSubmit()" >保存</a></span>
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;" onclick="resetForm()"> 重置</a></span>
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;" onclick="closeWindow()"> 取消</a></span>
			</div>
		</div>
	</div>
	<div id="roleAccountWin" class="easyui-window" modal="true" inline="false"  collapsible="false" minimizable="false"
	 	maximizable="false" resizable="false" closed="true"  title="角色下的帐户">
	 	<div class="bdcontent" style="width: 600px; height: 300px;">
			<table id="roleAccountList"></table>
		</div>
	</div>
</body>
</html>