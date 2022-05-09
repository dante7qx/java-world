<%@page import="com.epolleo.bp.pub.TaskScheduler"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>定时任务</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<script type="text/javascript">
		$(function(){
			$('#TaskDefDatagrid').datagrid({
				pagination:true,
				rownumbers:true,
				title:'定时任务列表',
				url:'bp.task.TaskDefAction$query.json',
				fitColumns: true,
				singleSelect: true,
				striped: true,
				collapsible: false,
				idField: 'taskId',
				pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
				pageList: [<%=pageList%>],//可以设置每页记录条数的列表 	
				toolbar:"#TaskDefToolbar",			
				frozenColumns:[
		             [{field:'ck',checkbox:true}]
		        ],
				columns:[[
						{title:'任务ID',field:'taskId',width:30,align:'left'},
						{title:'任务标识',field:'beanId',width:80,align:'left',formatter:function(value){return typeFormatter(beanIdData,value)}},
						{title:'状态',field:'enable',width:30,align:'left',formatter:function(value){return value ? '启用':'停用'}},
						{title:'任务名称',field:'taskName',width:80,align:'left'},
						{title:'启用时间',field:'startTime',width:85,align:'left'},
						{title:'终止时间',field:'endTime',width:85,align:'left'},
						{title:'定时方式',field:'shedType',width:40,align:'left',formatter:function(value){return typeFormatter(shedTypeData,value)}},
						{title:'定时设置',field:'shedExpr',width:100,align:'left'},
						{title:'执行标识',field:'execId',width:150,align:'left'},
						{title:'上次执行时间',field:'lastTime',width:85,align:'left'},
						{title:'上次执行情况',field:'lastInfo',width:120,align:'left'}
				]]
			});
        var shedTypeData = <w:map2json name="BP_TASK_DEF.SHED_TYPE"/>; 
        $('#TaskDefForm #shedType').combobox({
				data:shedTypeData,
				editable:false,
				panelHeight : 200,
				valueField:"key",
				textField:"value"
			});

        var beanIdData = <w:set2json name="bpJobs"/>; 
        $('#TaskDefForm #beanId').combobox({
				data:beanIdData,
				editable:false,
				width: 200,
				panelHeight : 200,
				valueField:"key",
				textField:"value"
			});	
	 		});
	/*把枚举类型的key，转化成value，供页面显示*/	
	function typeFormatter(datas,value){
		for(var i=0; i<datas.length; i++){
			if (datas[i].key == value) return datas[i].value;
		}
		return value;
	}
		
	/*查询方法*/
   	function doTaskDefSearch(){
		var params = {
        beanId: $('#beanIdQuery').val()    }
     $('#TaskDefDatagrid').datagrid('load',params);
	}
	/*list列表的重置*/
    function doTaskDefQueryFormReset(){
		$("#TaskDefQueryForm").form("clear");
    }
    
	function openSaveTaskDefWin(){
		$('#TaskDefForm').form('clear');
		$('#TaskDefForm').attr("action", "bp.task.TaskDefAction$save.json");
		$('#TaskDefWindow').window("setTitle", "新增定时任务");
		$('#TaskDefWindow').window("open");
	}

	function openEditTaskDefWin(index) {
		if(typeof(index) != "undefined"){
			$('#TaskDefDatagrid').datagrid('selectRow', index);
		}
		var selected = $('#TaskDefDatagrid').datagrid('getSelected');
		if (selected) {
			$('#TaskDefForm').form('clear');
			$('#TaskDefForm').form('load', selected);
			$('#TaskDefForm').attr("action", "bp.task.TaskDefAction$update.json");
			$('#TaskDefWindow').window("setTitle", "编辑定时任务");
			$('#TaskDefWindow').window("open");
		} else {
			$.messager.alert('提示', "请选择一行数据后再进行此操作！");
		}
	}


	function deleteTaskDef() {
		var selected = $('#TaskDefDatagrid').datagrid('getSelected');
		if (selected) {
			$.messager.confirm('确认','确认要删除该信息吗？',
					function(ok) {
						if (ok) {
							$.ajax({
								type : 'POST',
								url : "bp.task.TaskDefAction$delete.json",
								data : {
									'taskId' : selected.taskId
								},
								success : function(data) {
									$('#TaskDefDatagrid').datagrid('reload');
									$('#TaskDefDatagrid').datagrid('clearSelections');
								},
								error : function(j, status,error) {
									$.messager.alert('提示',error);
								}
							});
						}
					});
		} else {
			$.messager.alert('提示', "请选择一行数据后再进行此操作！");
		}
	}

	function TaskDefFormSubmit() {
		$('#TaskDefForm').form('submit', {
			url : $("#TaskDefForm").attr("action"),
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				$.messager.alert('提示', '定时任务保存成功！');
				$('#TaskDefWindow').window('close');
				$('#TaskDefDatagrid').datagrid('reload');
			}
		});
	}

	function TaskDefFormReset() {
		if( "bp.task.TaskDefAction$save.json" == $('#TaskDefForm').attr("action")){
			$('#TaskDefForm').form('clear');
		}else{
			var selected = $('#TaskDefDatagrid').datagrid('getSelected');
			$('#TaskDefForm input:visible, #TaskDefForm textarea').val('');
			$('#TaskDefForm').form('load', selected);
		}
	}

	function showCronHelp() {
		$('#cronHelp').window({
			title:"关于克龙表达式",
			width:650,
			height:460,
			href:"cronHelp.jsp",
			closed:false,
			cache:false,
			modal:true,
			left:null,
			top:null
		});
	}
</script>
</head>
<body>
   	<div class="bdcontent">
		<table id="TaskDefDatagrid"></table>
	</div>
	<div id="TaskDefToolbar"  style="padding: 5px; height: auto;">
		<form id="TaskDefQueryForm" method="post" novalidate>
			<span class="searchtt">任务标识</span> 
		 	<input id="beanIdQuery" name="beanIdQuery" type="text"  class="tinput">
	        <a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-search"	onclick="doTaskDefSearch();">查询</a>
			<a href="javascript:;" id="button-reset" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doTaskDefQueryFormReset();">重置</a>
			<div style="text-align: right;">
				<w:func id="bp.task.save">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openSaveTaskDefWin()">新增</a> 
				</w:func>
				 <w:func id="bp.task.update">
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditTaskDefWin()">编辑</a>
				</w:func>
				 <w:func id="bp.task.delete">
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteTaskDef()">删除</a>
				</w:func>
			</div>
		</form>
	</div>
	<div id="TaskDefWindow" class="easyui-window" modal="true" inline="false"  collapsible="false" minimizable="false" maximizable="false" resizable="false" closed="true"  >
		<div style="margin:10px;overflow:hidden;">
			<form id="TaskDefForm" method="post" novalidate>
			    <input id="taskId" name="taskId" type="hidden"  editable="false" class="easyui-validatebox tinput"  />
				<table cellpadding="0" cellspacing="0" class="tbcontent" style ="width:400px;">	
					<tr>
						<td class="tbtitle" width="40%">任务标识</td>
						<td><input id="beanId" name="beanId" type="text" editable="false" class="easyui-combobox tinput" panelHeight="auto" required=true /></td>
					</tr>
					<tr>
						<td class="tbtitle">启停状态</td>
						<td><input id="enable" name="enable" type="checkbox" value="true" /></td>
					</tr>
					<tr>
						<td class="tbtitle">任务名称</td>
						<td><input id="taskName" name="taskName" type="text"  validType="maxLength['128']" maxlength="128" editable="false" class="easyui-validatebox tinput"  required=true /></td>
					</tr>
					<tr>
						<td class="tbtitle">启用时间</td>
						<td><input id="startTime" name="startTime" type="text"  editable="false" class="easyui-datetimebox tinput"  /></td>
					</tr>
					<tr>
						<td class="tbtitle">终止时间</td>
						<td><input id="endTime" name="endTime" type="text"  editable="false" class="easyui-datetimebox tinput"  /></td>
					</tr>
					<tr>
						<td class="tbtitle">定时方式</td>
						<td><input id="shedType" name="shedType" type="text" editable="false" class="easyui-combobox tinput" panelHeight="auto" required=true /></td>
					</tr>
					<tr>
						<td class="tbtitle"  style=" text-decoration: underline" onclick="showCronHelp()">定时设置</td>
						<td><input id="shedExpr" name="shedExpr" type="text" size="24" validType="cronExpr['bp.task.TaskDefAction$validCron.json','ce']" editable="false" class="easyui-validatebox tinput" required=true  /></td>
					</tr>
					<tr>
						<td class="tbtitle" title="冒号分隔列表如 :id1:id2:，本系统标识为<%=TaskScheduler.getBpId(application)%>">执行标识</td>
						<td><input id="execId" name="execId" type="text" validType="maxLength['128']" size="32" maxlength="128" editable="false" class="easyui-validatebox tinput"  required=true /></td>
					</tr>
					<tr>
						<td class="tbtitle">上次执行时间</td>
						<td><input id="lastTime" name="lastTime" type="text" editable="false" class="easyui-datetimebox tinput" /></td>
					</tr>
					<tr>
						<td class="tbtitle">上次执行情况</td>
						<td><textarea id="lastInfo" name="lastInfo" readonly="true"  style="width:300px;height:60px" class="tinput"></textarea>
						</td>
					</tr>
					<tr>
						<td class="tbtitle">下次执行时间</td>
						<td><input id="nextTime" name="nextTime" type="text" disabled="true" class="tinput" /></td>
					</tr>
				</table>	
				<div style="text-align: center;margin-top:15px;">
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
									onclick="TaskDefFormSubmit();"> 保存</a></span>
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;"
									onclick="TaskDefFormReset()"> 重置</a></span>
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
									onclick="$('#TaskDefWindow').window('close')"> 取消</a></span>
				</div>				
			</form>
		</div>
	</div>
	<div id="cronHelp" class="easyui-window"  modal="true" inline="false"  collapsible="false" minimizable="false" maximizable="false" resizable="false" closed="true">
	</div>
</body>
</html>