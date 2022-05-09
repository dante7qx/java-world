<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>员工管理</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<%
	boolean sigleAccount = "true".equals(application.getAttribute("bp.sigleAccount4EachEmployee"));
	boolean fetchEmployee = "true".equals(application.getAttribute("bp.hna.fetchEmployee"));
%>
<script type="text/javascript">
	var defaultOrg = null;
	$(function() {
		var lastIndex;
		$('#employeeList').datagrid({
			    title:'员工列表',
			    fitColumns: true,
				striped: true,
				url:'bp.employee.EmployeeAction$query.json',
				sortName: 'updateTime',
				sortOrder: 'desc',
				singleSelect: true,
				pagination: true,
				rownumbers: true,
				idField:'employeeId',
				pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10
				pageList: [<%=pageList%>],//可以设置每页记录条数的列表
				frozenColumns:[
		                       	[{field:'ck',checkbox:true}]
		                      ],
				columns:[[
			//		{title:'员工ID',field:'employeeId',width:50,sortable:true},
					{title:'姓名',field:'employeeName',width:90,sortable:true},
					{field:'pyCode',title:'拼音码',width:60,sortable:false},
					{field:'orgNames',title:'组织机构',width:200,sortable:false},
					{field:'phone',title:'电话',width:100,sortable:false},
					{field:'mobileNumber',title:'手机号码',width:100,sortable:false},
					{field:'email',title:'邮箱',width:150,sortable:false},
					{field:'state',title:'状态',width:40,align:'center',sortable:false,formatter:function(value){
						if(value == 1){
							return "正常";
						}else if(value == 2){
							return "锁定";
						}
						return value;
					}},
					{field:'updateUser',title:'更新人',width:70,sortable:false},
					{field:'updateTime',title:'更新时间',width:120,sortable:false}
				]],
				toolbar:"#toolbar",
				onClickRow:function(rowIndex){
					if (lastIndex != rowIndex){
						$('#employeeList').datagrid('cancelEdit', lastIndex);
					}
					lastIndex = rowIndex;
				}
		});
	});

	  function employeeListHandler(op){
		  if(op == "add"){
			setWindowTitle("新增员工", $('#employeeEdit'));
			$('#employeeInfoForm').form('clear');
			$('#employeeList').datagrid('clearSelections');
			$('#opt').attr("value", "add");
			// 设置form中的可编辑字段可用
			$('#knowAccount').parent().parent().css("display", '');
			$('#pyCode').parent().parent().css("display",'none');

			// 状态字段不可见
			$('#state').removeAttr('disabled').parent().parent().css("display",'none');
			// 显示form中的button
			$('#employeeInfoForm+div .easyui-linkbutton').each(function(i) {
				$(this).css('display', '');
			});

			$('#defaultOrgId').combotree({
				 mode:'remote',
			     url:"bp.org.OrgAction$query.json?lazy=true",
				 valueField:'orgId',
				 textField:'orgName',
	             onBeforeExpand: function(node) {
	            	 $(this).tree("options").url = "bp.org.OrgAction$query.json?lazy=true&parentId=" + node.id;
	             }
			});
			// 查询组织树
			$('#orgIds').combotree({
				 mode:'remote',
			     url:"bp.org.OrgAction$query.json?lazy=true",
				 valueField:'orgId',
				 textField:'orgName',
	             onBeforeExpand: function(node) {
	            	 $(this).tree("options").url = "bp.org.OrgAction$query.json?lazy=true&parentId=" + node.id;
	             },
	    	     multiple:true,
	    	     cascadeCheck:false
			});
			$('#employeeEdit').window('open');
		  } else if(op == "edit"){	// 编辑员工
			var selected = $('#employeeList').datagrid('getSelected');
			if (selected && selected.employeeId){
				setWindowTitle("编辑员工", $('#employeeEdit'));
				$('#knowAccount').parent().parent().css("display", 'none');
				// 填充form
				$('#employeeInfoForm').form('clear');
				$('#defaultOrgId').combotree({
					 mode:'remote',
				     url:"bp.org.OrgAction$query.json?lazy=true&orgId=" + selected.defaultOrgId,
					 valueField:'orgId',
					 textField:'orgName',
		             onBeforeExpand: function(node) {
		            	 $(this).tree("options").url = "bp.org.OrgAction$query.json?lazy=true&parentId=" + node.id + "&orgId=" + selected.defaultOrgId;
		             }
				});
				$('#orgIds').combotree({
					 mode:'remote',
				     url:"bp.org.OrgAction$query.json?lazy=true&orgIds=" + selected.orgIds,
					 valueField:'orgId',
					 textField:'orgName',
		             onBeforeExpand: function(node) {
		            	 $(this).tree("options").url = "bp.org.OrgAction$query.json?lazy=true&parentId=" + node.id + "&orgIds=" + selected.orgIds;
		             },
		    	     multiple:true,
		    	     cascadeCheck:false
				});
				$('#employeeInfoForm').form('load',selected);
				$('#opt').attr("value","update");
				// 设置form中的可编辑字段可用
				$('#state').removeAttr('disabled');
				$('#employeeInfoForm input').each(function(i) {
					$(this).removeAttr('disabled');
				});
				// 设置pyCode可编辑且添加非空约束
				$('#pyCode').validatebox({ required:true }).parent().parent().css("display",'');
				// $('#orgNames').parent().parent().css("display",'none');
				$('#state').parent().parent().css("display",'');
				// 显示form中的button
				$('#employeeInfoForm+div .easyui-linkbutton').each(function(i) {
					$(this).css('display', '');
				});
				// 查询组织树

				$('#employeeEdit').window('open');
			}else{
				$.messager.alert('提示',"请选择一行数据后再进行此操作！");
			}
		  } else if(op == "del"){	// 删除员工
			var row = $('#employeeList').datagrid('getSelected');
			if (row && row.employeeId) {
				$.messager.confirm('确认','确认要删除该员工？',function(ok){
					if(ok) doDelete(row.employeeId);
				});
			}else{
				$.messager.alert('提示','请选择一行数据后再进行此操作！');
			}
		 }
	  }
	  function onSearch(){
		 $('#employeeList').datagrid('load',{
		   employeeName: $('#queryEmployeeName').val(),
		   employeeId: $('#queryEmployeeId').val(),
		   email: $('#queryEmail').val()
		});
	 }
	 function doReset(){
		 $('#queryEmployeeName').attr("value","");
		 $('#queryEmployeeId').attr("value","");
		 $('#queryEmail').attr("value","");

		onSearch();
	 }
	 function employeeInfoSubmit(){
		 $('#employeeInfoForm').form('submit',{
		     url: "bp.employee.EmployeeAction$update.json",
		     success:function(data){
		    	  var cToObj=eval('(' + data + ')');
		    	  if (cToObj.employee) {
		    		 $.messager.alert('提示','员工保存成功！');
		    		 $('#employeeId').val(cToObj.employee.employeeId);
		    		 $('#pyCode').val(cToObj.employee.pyCode);
				     winClose('employeeEdit');
				     $('#employeeList').datagrid('reload');
		    	 }else if (cToObj.error != null) {
		    		$.messager.alert('错误',cToObj.error,'error');
		    	 }else{
		    		$.messager.alert('错误','保存失败！','error');
		    	 }
		    }
		});
	 }
	function formReset(){
		var opt = $('#opt').val();
		if(opt == 'add'){
			$('#employeeInfoForm').form('clear');
			$('#opt').attr("value","add");
		}else if(opt == 'update'){
			$('#employeeInfoForm').form('clear');
			var selected = $('#employeeList').datagrid('getSelected');
			 $('#employeeInfoForm').form('load',selected);
			 $('#opt').attr("value","update");
		}
	}
	 function doDelete(employeeId){
		 $.ajax({
			type: "POST",
			url: "bp.employee.EmployeeAction$delete.json",
			data: {
				id : employeeId
			},
			success: function(data) {
				 $('#employeeList').datagrid('reload');
				 $('#employeeList').datagrid('clearSelections');
			},
			error: function(msg) {
				$.messager.alert('Error',"删除失败！");
			}
		});
	 }

	function userListHandler(){
		var selected = $('#employeeList').datagrid('getSelected');
		if (selected && selected.employeeId){
			setWindowTitle("员工账号", $('#userEdit'));
			$.post("bp.user.LoginUserAction$queryUserByEmployee.json",			{employeeId:selected.employeeId},
					function(data) {
						if(data.userId) {	// 已创建账号
							 $('#userInfoForm').form('clear');
							 $('#userInfoForm').form('load', data);
							 $('#empId').attr('value', selected.employeeId);
							 $('#empName').attr('disabled', 'true');
							 $('#userId').attr('required', false).css('display', 'none');
							 $('#userIdShow').attr({'value':$('#userId').val(), 'disabled':'true'}).css('display', '');
							 $('#passWordConfirm').validatebox({ required:false });
							 $('#opt').attr("value","update");
							 $('#sysUser').removeAttr("disabled").attr("checked", data.sysUser);
							 $('#usermark').css("display",'');
							 $('#userstatus').css("display",'');
							 $('#confirmPwd').css("display",'none');
							 $('#updateuserId').val(data.userId);
							 queryUserRole(data.userId);
						} else {	// 没创建账号
							$('#userInfoForm').form('clear');
							$('#opt').attr("value","add");
							$('#userId').attr('required', 'true').css('display', '');
							$('#userIdShow').css('display', 'none');
							$('#empId').attr('value', selected.employeeId);
							$('#empName').attr('value', selected.employeeName).attr('disabled', 'true');
							$('#passWordConfirm').validatebox({ required:true });
					 		if (data.sysUser) {   //系统用户
					 			$('#sysUser').removeAttr("disabled");
					 			$('#usermark').css("display",'');
					    	} else{
					    		 $('#sysUser').attr("disabled",true);
					    		 $('#usermark').css("display",'none');
					    	}
					 		$('#userstatus').css("display",'none');
							$('#confirmPwd').css("display",'');
							$('#updateuserId').val('');
							queryUserRole(null);
						}
						$('#userEdit').window('open');
					},"json");
		} else {
			$.messager.alert('提示',"请选择一行数据后再进行此操作！");
		}

	}

	function queryUserRole(userId) {
		$('#roleList').combotree({
			mode:'remote',
		    url:"bp.role.RoleAction$queryRoleTree.json",
			editable: false,
		 	        multiple: true
		});
		if (userId) {
			$.ajax({
				type : "POST",
				dataType:"json",
				url : "bp.user.LoginUserAction$getUserRoleIdList.json",
				data : {
					id : userId
				},
				success : function(data) {
					$('#roleList').combotree('setValues', data);
				}
			});
		} else {
			$('#roleList').combotree('clear');
		}
	}

	function userInfoSubmit() {
		var userId = $('#userId').val();
		var opt = $('#opt').val();
		var isUserId = $('#isUserId').val();
		$('#userInfoForm').form('submit', {
			url : "bp.user.LoginUserAction$update.json",
			onSubmit : function() {
				return $(this).form('validate');
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
					winClose('userEdit');
				} else {
					$.messager.alert('错误', '保存失败！', 'error');
				}
			}
		});
	}

	function winClose(idStr){
		$('#'+idStr).window('close');
	}

	function setWindowTitle(title, obj){
		var panelTitle = $('.panel-title', obj.parent());
		panelTitle.text(title);
	}
	<% if (fetchEmployee)  { %>
	function autoFetchEmployee() {
		var account = $('#knowAccount').val();
		if(account) {
			$.ajax({
				type : "POST",
				dataType:"json",
				url : "bp.employee.EmployeeAction$queryAccountEmployee.json",
				data : {
					account : account
				},
				success : function(data) {
					if(data == 0) {
						$.messager.alert('错误', '提取失败，请联系系统管理员！', 'error');
						return;
					}
					if(!data) {
						$.messager.alert('提示', '无匹配员工！');
						return;
					}
					var defaultOrgId = data.defaultOrgId;
					if(defaultOrgId != 0) {
						$('#defaultOrgId').combotree({
							 mode:'remote',
						     url:"bp.org.OrgAction$query.json?lazy=true&orgId=" + defaultOrgId,
							 valueField:'orgId',
							 textField:'orgName',
				             onBeforeExpand: function(node) {
				            	 $(this).tree("options").url = "bp.org.OrgAction$query.json?lazy=true&parentId=" + node.id + "&orgId=" + defaultOrgId;
				             }
						});
					}
					$('#employeeInfoForm').form('load', data);
					if(defaultOrgId == 0) {
						$('#defaultOrgId').combotree('clear');
					}
				}
			});
		} else {
			$.messager.alert('提示', '请先填写完整账号！');
		}
	}
	<% } %>
</script>
</head>
<body>
	<div class="bdcontent">
		<table id="employeeList"></table>
	</div>
	<div id="toolbar" style="padding:5px;height:auto">
		<div>
		<!--
			<span>员工ID</span>
			<input id="queryEmployeeId" type="text" class="tinput"/>
		-->
			<span> 姓名 </span><input id="queryEmployeeName" type="text" class="tinput"/>
			<span> 邮箱 </span><input id="queryEmail" type="text" class="tinput"/>
			<a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-search"	onclick="onSearch();">查询</a>
			<a href="javascript:;" id="button-reset" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doReset();">重置</a>
		</div>
		<div style="text-align: right;">
			<% if (sigleAccount) { %><w:func id="bp.user.update"><a href="#" class="easyui-linkbutton" iconCls="icon-move" plain="true" onclick="userListHandler()">账号管理</a></w:func><% } %>
			<w:func id="bp.employee.update">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="employeeListHandler('add')">新增</a>
			</w:func>
			<w:func id="bp.employee.update">
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="employeeListHandler('edit')">编辑</a>
			</w:func>
			<w:func id="bp.employee.delete">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="employeeListHandler('del')">删除</a>
			</w:func>
		</div>
	</div>

	<!-- 员工编辑窗口 -->
	<div id="employeeEdit" class="easyui-window" modal="true" inline="false"  collapsible="false" minimizable="false"
	 maximizable="false" resizable="false" closed="true"  title="员工信息">
			<div style="margin:10px;overflow:hidden;">
		<form id="employeeInfoForm" method="post" novalidate>
			<table cellpadding="0" cellspacing="0" class="tbcontent" style ="width:505px;">
		<% if (fetchEmployee)  { %><tr>
					<td class="tbtitle" width="30%">已知账号</td>
					<td>
						<input id="knowAccount" type="text" class="tinput" name="knowAccount"/>
						<a class="easyui-linkbutton" iconcls="icon-back" href="javascript:;"
						onclick="autoFetchEmployee()"> 提取</a>
					</td>
				</tr> <% } %>
				<tr>
					<td class="tbtitle">姓名</td>
					<td>
						<input id="employeeName" type="text" class="easyui-validatebox tinput" name="employeeName" required=true></input>
						<input id="opt" type="hidden" name="opt"/>
						<input id="employeeId" type="hidden" name="employeeId"/>
					</td>
				</tr>
				<tr>
					<td class="tbtitle">拼音码</td>
					<td><input id="pyCode" type="text" class="easyui-validatebox tinput" name="pyCode"></input></td>
				</tr>
				<tr>
					<td class="tbtitle">所属组织</td>
					<td><input id="defaultOrgId" type="text" class="easyui-combotree tinput" name="defaultOrgId" required=true style="width:200px;"></input></td>
				</tr>
				<tr>
					<td class="tbtitle">别名</td>
					<td><input id="aliasName" class="easyui-validatebox tinput" type="text" name="aliasName"></input></td>
				</tr>
				<tr>
					<td class="tbtitle">昵称</td>
					<td><input id="nickName" class="easyui-validatebox tinput" type="text" name="nickName"></input></td>
				</tr>
				<tr>
					<td class="tbtitle">电话</td>
					<td><input id="phone" class="easyui-validatebox tinput" type="text" name="phone" validType="tel"></input>
					</td>
				</tr>
				<tr>
					<td class="tbtitle">手机号码</td>
					<td><input id="mobileNumber" class="easyui-validatebox tinput" type="text" name="mobileNumber"  validType="mobile"></input>
					</td>
				</tr>
				<tr>
					<td class="tbtitle">地址</td>
					<td><input id="adreess" class="easyui-validatebox tinput" type="text" name="address" style="width:300px;"></input>
					</td>
				</tr>
				<tr>
					<td class="tbtitle">邮箱</td>
					<td><input id="email" class="easyui-validatebox tinput" type="text" name="email"  validType="email" style="width:150px;"></input>
					</td>
				</tr>
				<tr>
					<td class="tbtitle">对应组织（兼）</td>
					<td><input id="orgIds" type="text" class="easyui-combotree tinput" name="orgIds" style="width:250px;"></input></td>
				</tr>
				<tr>
					<td class="tbtitle">状态</td>
					<td>
						<select id="state" name="state" valueField="id" textField="state" panelHeight="auto" style="width: 60px;">
					        <option value="1">正常</option>
					        <option value="2">锁定</option>
					    </select>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align: center;margin-top:15px;">
		<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
						onclick="employeeInfoSubmit()"> 保存</a></span>
		<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;"
						onclick="formReset();"> 重置</a></span>
		<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
						onclick="winClose('employeeEdit')"> 取消</a></span>
		</div>
		</div>
	</div>

	<!-- 账号编辑窗口 -->
	<div id="userEdit" class="easyui-window" modal="true" inline="false"  collapsible="false" minimizable="false"
		maximizable="false" resizable="false" closed="true"  title="用户信息" >
			<div style="margin:10px;overflow:hidden;">
		<form id="userInfoForm" method="post" novalidate>
			<table cellpadding="0" cellspacing="0" class="tbcontent" style ="width:505px;">
				<tr>
					<td class="tbtitle"  width="40%">用户ID</td>
					<td>
						<input id="userId" type="text" class="easyui-validatebox tinput"  name="userId"  required="true" validType="checkUserName"/>
						<input id="userIdShow" type="text" name="userIdShow" />
						<input id="opt" type="hidden" name="opt"/>
						<input id="isUserId" type="hidden" name="isUserId"/>
						<input id="updateuserId" type="hidden" name="updateuserId"/>
						<input id="empId" type="hidden" name="employeeId"></input>
					</td>
				</tr>
				<tr>
					<td class="tbtitle">员工</td>
					<td>
						<input id="empName" type="text" name="employeeName"></input>
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
						<select class="easyui-combobox" name="state" valueField="id" textField="state" panelHeight="50" style="width: 60px;">
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
						onclick="winClose('userEdit');"> 取消</a></span>
		</div>
		</div>
	</div>
</body>
</html>