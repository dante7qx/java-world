<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow:hidden">
<head>
<title>用户管理</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<%
	boolean sigleAccount = "true".equals(application.getAttribute("bp.sigleAccount4EachEmployee"));
%>
<script type="text/javascript">
	var sysUser = <%=user.isSysUser()%>;
	$(function() {
		$('#userList').datagrid({
			    title:'用户列表',
			    fitColumns: true,
				striped: true,
				url:'bp.user.LoginUserAction$query.json',
				sortName: 'updateTime',
				sortOrder: 'desc',
				singleSelect: true,
				pagination: true,
				rownumbers: true,
				idField:'userId',
				pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10
				pageList: [<%=pageList%>],//可以设置每页记录条数的列表
				frozenColumns:[
		                       	[{field:'ck',checkbox:true}]
		                      ],
				columns:[[
					{field:'userId',title:'用户ID',width:50,sortable:true},
					{field:'userName',title:'用户名称',width:60,sortable:true},
					{title:'员工',field:'employeeName',width:60,sortable:false},
					{field:'orgNames',title:'组织机构',width:120,sortable:false},
					{field:'state',title:'状态',width:40,align:'center',sortable:false,formatter:function(value){
						if(value == 1){
							return "正常";
						}else if(value == 2){
							return "锁定";
						}
					}},
					{field:'sysUser',title:'系统标志',width:60,align:'center',sortable:false,formatter:function(value){
						if(value == true){
							return "是";
						}else if(value == false){
							return "否";
						}
					}},
					{field:'createUser',title:'创建人',width:80,sortable:false},
					{field:'createTime',title:'创建时间',width:80,sortable:false},
					{field:'updateUser',title:'更新人',width:80,sortable:false},
					{field:'updateTime',title:'更新时间',width:80,sortable:false}
				]],
				toolbar:"#toolbar"
		});
		
		// 初始化员工查询下拉框
		$('#queryEmpId').combobox({
			  mode:'remote',
			  panelHeight: 'auto',
		      url:"bp.employee.EmployeeAction$queryEmployees.json",
			  valueField:'employeeId',
			  textField:'employeeName',
		      formatter: function(row){
		        return '<div class="combobox-item">'+row.employeeName + (row.email?'  '+row.email:'') +'</div>';
		      }
		});

		$('#queryOrgId').combotree({
			 mode:'remote',
		     url:"bp.org.OrgAction$query.json?lazy=true",
			 valueField:'orgId',
			 textField:'orgName',
			 editable: false,
             onBeforeExpand: function(node) {
            	 $(this).tree("options").url = "bp.org.OrgAction$query.json?lazy=true&parentId=" + node.id;
             }
		});
	});

	function autoSearchEmployee(eid) {
		$('#employeeId').combobox({
			mode:'remote',
		<% if (sigleAccount) { %>
			url:"bp.employee.EmployeeAction$queryNoAccountEmployees.json",
		<% } else { %>
			url:"bp.employee.EmployeeAction$queryEmployees.json",
		<% } %>
			valueField:'employeeId',
			textField:'employeeName',
			onBeforeLoad: function(param){
				param.eid = eid;
			},
			formatter: function(row){
				return '<div class="combobox-item">'+row.employeeName + (row.email? '  ' + row.email:'') +'</div>';
			}
		});
	}

	function userListHandler(op){
		if(op=="add"){
			setWindowTitle("新增用户");
			$('#employeeId').combobox('setText', '')
			autoSearchEmployee('');
			$('#userList').datagrid('clearSelections');//clear selections
			$('#userInfoForm').form('clear');
			$('#userId').attr('required', 'true').css('display', '');
			$('#userIdShow').css('display', 'none');
			$('#passWordConfirm').validatebox({ required:true });
			$('#opt').attr("value","add");
			if (sysUser) {   //系统用户
				$('#sysUser').removeAttr("disabled");
				$('#usermark').css("display",'');
		  	} else{
		  		 $('#sysUser').attr("disabled",true);
		  		 $('#usermark').css("display",'none');
		  	}
			$('#userstatus').css("display",'none');
			$('#confirmPwd').css("display",'');
			$('#updateuserId').val('');
			
			$('#roleList').combotree({
				mode:'remote',
			    url:"bp.role.RoleAction$queryRoleTree.json",
				editable: false,
			 	        multiple: true
			});
			$('#roleList').combotree('clear');
			
			$('#userEdit').window('open');
		}
		if(op=="edit"){
			  setWindowTitle("编辑用户");
			  var selected = $('#userList').datagrid('getSelected');
				if (selected && selected.userId){
				   if(selected.userId != 'superadmin') {
					$('#userInfoForm').form('clear');
					autoSearchEmployee(selected.employeeId);
					$('#userInfoForm').form('load',selected);
					
					$('#userId').attr('required', false).css('display', 'none');
					$('#userIdShow').attr({'value':$('#userId').val(), 'disabled':'true'}).css('display', '');
					$('#passWordConfirm').validatebox({ required:false });
					
					if(selected.sysUser){
					 $('#sysUser').attr("checked","true");
					}
					$('#opt').attr("value","update");
					if (sysUser) {   //系统用户
						$('#sysUser').removeAttr("disabled");
						}else{
							$('#sysUser').attr("disabled",true);
						}
					$('#usermark').css("display",'');
					$('#userstatus').css("display",'');
					$('#confirmPwd').css("display",'none');
					$('#updateuserId').val(selected.userId);
					$('#roleList').combotree({
						mode:'remote',
					    url:"bp.role.RoleAction$queryRoleTree.json",
						editable: false,
					 	        multiple: true
					});
					$.ajax({
						type : "POST",
						dataType:"json",
						url : "bp.user.LoginUserAction$getUserRoleIdList.json",
						data : {
							id : selected.userId
						},
						success : function(data) {
							$('#roleList').combotree('setValues', data);
						}
					});
					 $('#userEdit').window('open');
				   }else{
					   $.messager.alert('提示',"系统超级用户，不可编辑！");
				   }
		 	}else{
				$.messager.alert('提示',"请选择一行数据后再进行此操作！");
			}
		  }
		if(op=="del"){
			var row = $('#userList').datagrid('getSelected');
		    if (row && row.userId) {
		    	  if(row.userId != 'superadmin'){
		    	 $.messager.confirm('确认','确认要删除该用户？',function(ok){
					 if(ok){
						 doDelete(row.userId);
					 }
				 });
		    	  }else{
		    		  $.messager.alert('提示','系统超级用户，不可删除！');
		    	  }
		   }else{
			   $.messager.alert('提示','请选择一行数据后再进行此操作！');
		   }
		}
	}

	function onSearch() {
		$('#userList').datagrid('load', {
			userId : $('#queryUserId').val(),
			employeeId : $('#queryEmpId').combobox('getValue'),
			orgId : $('#queryOrgId').combo('getValue')
		});
	}
	function doReset() {
		$('#queryUserId').attr("value", "");
		$('#queryEmpId').combobox('clear');
		$('#queryOrgId').combo('clear');
	}
	function userInfoSubmit() {
		var userId = $('#userId').val();
		var opt = $('#opt').val();
		var isUserId = $('#isUserId').val();
		var employeeId = $('#employeeId').combobox('getValue');
		if (employeeId && isNaN(employeeId)) {
			$.messager.alert('提示', '请选择员工！');
			return;
		}
		$('#userInfoForm').form('submit', {
			url : "bp.user.LoginUserAction$update.json",
			onSubmit : function() {
				if($('#opt').val() == 'update') {
					return $('#passWord').validatebox('isValid');
				} else {
					return $(this).form('validate');
				}
			},
			success : function(data) {
				var cToObj = eval('(' + data + ')');
				if (cToObj.userGen) {
					if (cToObj.userGen.isUserId == '1') {
						$.messager.alert('提示', '删除的用户已恢复！');
					} else {
						$.messager.alert('提示', '用户保存成功！');
					}
					$('#updateuserId').val(cToObj.userGen.userId);
					winClose();
					$('#userList').datagrid('reload');
				} else {
					$.messager.alert('错误', '保存失败！', 'error');
				}
			}
		});
	}
	
	function doDelete(userId) {
		$.ajax({
			type : "POST",
			url : "bp.user.LoginUserAction$delete.json",
			data : {
				id : userId
			},
			success : function(data) {
				$('#userList').datagrid('reload');
			},
			error : function(msg) {
				$.messager.alert('Error', "删除失败！");
			}
		});
	}

	function loadDataGrid(userId) {
		$('#userRoleList').datagrid('reload', {
			userId : userId
		});
	}
	function updateUserRole(userId, roleId, check) {
		$.ajax({
			type : "POST",
			url : "bp.user.LoginUserAction$updateUserRole.json",
			data : {
				userId : userId,
				roleId : roleId,
				check : check
			},
			success : function(data) {
				loadDataGrid($('#updateuserId').val());
			},
			error : function(msg) {
				$.messager.alert('Error', "更新失败！");
			}
		});
	}

	function winClose() {
		$('#userEdit').window('close');
	}

	function setWindowTitle(title) {
		var panelTitle = $('.panel-title', $('#userEdit').parent());
		panelTitle.text(title);
	}
</script>
</head>
<body>
	<div class="bdcontent">
		<table id="userList"></table>
	</div>
	<div id="toolbar" style="padding:5px;height:auto">
		<table>
			<tr>
				<td>用户ID</td>
				<td><input id="queryUserId" type="text" class="tinput"/></td>
				<td>员工</td>
				<td><input id="queryEmpId" class="easyui-combobox" style="width: 200px;"/></td>
				<td>组织机构</td>
				<td><input id="queryOrgId" style="width: 200px;"/></td>
				<td><a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-search"	onclick="onSearch();">查询</a></td>
				<td><a href="javascript:;" id="button-reset" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doReset();">重置</a></td>
			</tr>
		</table>
		<div style="text-align: right;">
		 <w:func id="bp.user.update">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="userListHandler('add')">新增</a>
			</w:func>
			 <w:func id="bp.user.update">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="userListHandler('edit')">编辑</a>
			</w:func>
			 <w:func id="bp.user.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="userListHandler('del')">删除</a>
			</w:func>
		</div>
	</div>

	<!-- 编辑窗口 -->
	<div id="userEdit" class="easyui-window" modal="true" inline="false"  collapsible="false" minimizable="false"
		 maximizable="false" resizable="false" closed="true"  title="用户信息" >
			<div id="winInfo"  style="margin:10px;overflow:hidden;">
		<form id="userInfoForm" method="post" novalidate>
			<table cellpadding="0" cellspacing="0" class="tbcontent" style ="width:505px;">
				<tr>
					<td class="tbtitle"  width="30%">用户ID</td>
					<td>
						<input id="userId" type="text" class="easyui-validatebox tinput"  name="userId"  required="true" maxlength="16" validType="checkUserName"/>
						<input id="userIdShow" type="text" class="easyui-validatebox tinput"  name="userIdShow" />
						<input id="opt" type="hidden" name="opt"/>
						<input id="isUserId" type="hidden" name="isUserId"/>
						<input id="updateuserId" type="hidden" name="updateuserId"/>
					</td>
				</tr>
				<tr>
					<td class="tbtitle">员工</td>
					<td>
						<input id="employeeId" class="easyui-combobox"  name="employeeId" style="width: 200px;"></input>
					</td>
				</tr>
				<tr>
					<td class="tbtitle">密码
					</td>
					<td><input class="easyui-validatebox tinput" type="password" validtype="pwd" id='passWord' name="passWord" required=true></input>
					</td>
				</tr>
				<tr id="confirmPwd">
					<td class="tbtitle">确认密码</td>
					<td id="PWD"><input class="easyui-validatebox tinput" type="password"  id='passWordConfirm' name="passWordConfirm" required=false  validType="equals['passWord']"></input>
					</td>
				</tr>
				<tr id ="usermark">
					<td class="tbtitle">系统用户标志
					</td>
					<td><input id="sysUser" type="checkbox" name="sysUser" ></input></td>
				</tr>
				<tr>
					<td class="tbtitle">用户角色</td>
					<td><input id="roleList" type="text" class="easyui-combotree tinput" name="roleList" style="width:250px;" required=true></input></td>
				</tr>
				<tr id="userstatus">
					<td class="tbtitle">状态
					</td>
					<td>
						<select name="state" valueField="id" textField="state" panelHeight="auto" style="width: 50px;">
					        <option value="1">正常</option>
					        <option value="2">锁定</option>
					    </select>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align: center;margin-top:15px;">
		<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
						onclick="userInfoSubmit();"> 保存</a></span>
		<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
						onclick="winClose()"> 取消</a></span>
		</div>
		</div>
	</div>
</body>
</html>