<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织机构管理</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<style type="text/css">
	.col1,.col3{
		text-align: right;
	}
	.col2,.col4{
		width: 120px;
		padding-right: 10px;
	}
</style>
<script>

	$(function() {
		$('#crud-gird').datagrid(
						{
							title : '组织机构类型管理',
							rownumbers:true,
							fitColumns : true,
							striped : true,
							remoteSort : true,
							pagination : true,
							singleSelect: true,
							sortOrder : 'asc',
							sortName : 'orgTypeId',
							idField : 'orgTypeId',
							toolbar : '#toolbar',
							pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
							pageList: [<%=pageList%>],//可以设置每页记录条数的列表 
							columns : [ [ {field : 'ck',checkbox : true}, 
							              {field : 'orgTypeId',title : '组织机构类型编码',width : 40,sortable : true}, 
							              {field : 'orgType',title : '组织机构类型',width : 100,sortable : false} 
							          ] ],
							url : 'OrgTypeAction.json?action=bp/org/OrgTypeAction&eventSubmitDoQuery=1&method=query'
						});
	});
	
	function doSearch() {
		$('#crud-gird').datagrid('load', {
			orgTypeId : $('#input-idKey').val(),
			orgType : $('#input-idName').val()
		});
	}
	
	function doReset() {
		$("#toolbar table input").val('');
	}
	
	function doReset2(){
		var opt = $('#opt').val();
		if(opt == "add"){
		   $('#orgType').attr("value","");
  	   } else if(opt == "update"){
  		   var row = $('#crud-gird').datagrid('getSelected');
  		   $("#orgType").val(row.orgType);
  	   }
	}

	function doDelete() {
		var row = $('#crud-gird').datagrid('getSelected');
		if(!row) {
			$.messager.alert('提示',"请选择一行数据后再进行此操作！");
		} else {
			$.messager.confirm('删除','确定要删除吗？',function(r){
				if (r){
					var orgTypeId = row.orgTypeId;
					 $.post(
							 "OrgTypeAction.json?action=bp/org/OrgTypeAction&eventSubmitDoCheckRelation=1&method=checkRelation", 
							 {id : orgTypeId}, 
							 function(data) {
							if (data.success == "yes") {
								$.post("OrgTypeAction.json?action=bp/org/OrgTypeAction&eventSubmitDoDelete=1&method=delete", {
									id : orgTypeId
								}, function(data) {
									 $('#crud-gird').datagrid('reload');
									 $('#crud-gird').datagrid('unselectAll');
								});
							} else {
								$.messager.alert('删除',"该组织机构正在使用，不能删除")
								}
							},'json');
				}
			});
		}
	}
	
	function doAdd(){
		$('#orgTypeInfoForm').attr('action','OrgTypeAction.json?action=bp/org/OrgTypeAction&eventSubmitDoSave=1&method=save')
		$('#orgTypeInfoForm input').val('');
		$('#opt').attr("value","add");
		$('#orgTypeForm').window('open');
	}
	
	function doUpdate(){
		var row = $('#crud-gird').datagrid('getSelected');
		if(!row){
			$.messager.alert('提示',"请选择一行数据后再进行此操作！");
			return;
		}
		$('#orgTypeInfoForm').attr('action','OrgTypeAction.json?action=bp/org/OrgTypeAction&eventSubmitDoUpdate=1&method=update')
		$('#orgTypeInfoForm input:visible').val('');
		$('#orgTypeId').val(row.orgTypeId);
		$('#orgType').val(row.orgType);
		$('#opt').attr("value","update");
		$('#orgTypeForm').window('open');
	}
	

	function doSubmit() {
		$('#orgTypeInfoForm').form('submit', {
			url : $('#orgTypeInfoForm').attr('action'),
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				$('#orgTypeForm').window('close');
				$('#crud-gird').datagrid('reload');
			}
		});
	}
	function winClose(){
		 $('#orgTypeForm').window('close');
	}
</script>
</head>
<body>
<div class="bdcontent">
	<table id="crud-gird"></table>
	
	<div id="toolbar" style="padding:5px;height:auto">
		<table>
			<tr>
				<td>编码</td>
				<td><input id="input-idKey" type="text" class="tinput"/></td>
				<td>组织机构类型</td>
				<td><input id="input-idName" type="text" class="tinput"/></td>
				<td>
					<a id="button-search" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
					<a id="button-reset" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doReset()">重置</a>
				</td>
			</tr>
		</table>
		<div style="text-align: right;">
			 <w:func id="bp.organType.save">
			<a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="doAdd()">添加</a> 
			</w:func>
			 <w:func id="bp.organType.update">
			<a class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="doUpdate()">编辑</a> 
			</w:func>
			 <w:func id="bp.organType.delete">
			<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="doDelete()">删除</a> 
			</w:func>
		</div>
	</div>

</div>
	<div id="orgTypeForm" class="easyui-window" modal="true" inline="false" collapsible="false" minimizable="false" maximizable="false"
		resizable="false" closed="true" title="组织机构类型信息" style="height: 150px; width: 400px;">
		<form id="orgTypeInfoForm" method="post" action="" novalidate=false>
			<table id="orgContentTable" style="padding:10px;" align ="center">
				<tr>
					<td class="tbtitle" align="right">组织机构类型</td>
					<td>
					<input id="orgTypeId" name="orgTypeId" type="hidden"> 
					<input id="orgType" name="orgType" type="text" class="easyui-validatebox tinput" align="left" size="32" maxLength="64" required=ture>
					<input id="opt" name="opt"  type ="hidden"/>
					<input id="nouse" type="text" style="display:none">
					</td>
				</tr>
				<tr>
					<td height = "3"></td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-save" onclick="doSubmit()">保存</a></span>
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-undo" onclick="doReset2()">重置</a></span>
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close"onclick="winClose()">取消</a></span>
					</td>
				</tr>
			</table>
		</form>
	</div>


</body>
</html>