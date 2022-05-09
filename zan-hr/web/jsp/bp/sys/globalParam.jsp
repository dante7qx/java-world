<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>全局参数</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<script type="text/javascript">
			
		$(function(){
		    $('#SysParamWindow').window({
				maximizable:false,
				minimizable:false,
				collapsible:false,
				closed: true,
				modal : true
			});
			
			$('#SysParamDatagrid').datagrid({
				pagination:true,
				rownumbers:true,
				title:'全局参数列表',
				url:'bp.sysparam.SysParamAction$query.json',
				//sortName: 'paramKey',
				sortOrder: 'asc',
				fitColumns: true,
				singleSelect: true,
				striped: true,
				collapsible: false,
				idField: 'paramKey',
				pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
				pageList: [<%=pageList%>],//可以设置每页记录条数的列表 	
				toolbar:"#SysParamToolbar",			
				frozenColumns:[
		             [{field:'ck',checkbox:true}]
		        ],
				columns:[[
						{title:'参数ID',field:'paramKey',width:110,align:'left',sortable:true},
						{title:'参数名称',field:'paramName',width:110,align:'left',sortable:true},
						{title:'参数值',field:'paramValue',width:110,align:'left',sortable:true},
						{title:'参数类型',field:'paramType',width:40,align:'left',sortable:true,formatter:function(value){return typeFormatter(paramTypeData,value)}},
						{title:'默认值',field:'defaultValue',width:110,align:'left',sortable:true,formatter:function(value){return value == null ? '' : value}},
						{title:'是否同步',field:'syncFlag',width:30,align:'center',sortable:true,formatter:function(value){return value == true ? '是' : '否'}}
				]]
			});
        var paramTypeData = <w:map2json name="BP_SYS_PARAM.PARAM_TYPE"/>; 
        $('#SysParamForm #paramType').combobox({
				data:paramTypeData,
				editable:false,
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
   	function doSysParamSearch(){
		var params = {
		paramKey: $('#queryParamKey').val(),
		paramName: $('#queryParamName').val()
    	}
     $('#SysParamDatagrid').datagrid('load',params);
	}
	/*list列表的重置*/
    function doSysParamQueryFormReset(){
		$("#SysParamQueryForm").form("clear");
    }
    
 
	function openSaveSysParamWin(){
		$('#SysParamForm').form('clear');
		$('#SysParamForm').attr("action", "bp.sysparam.SysParamAction$save.json");
		$('#paramKeyTR', '#SysParamForm').css("display",'');
		$('#SysParamWindow').window("setTitle", "新增全局参数");
		$('#SysParamWindow').window("open");
	}

	function openEditSysParamWin(index) {
		if(typeof(index) != "undefined"){
			$('#SysParamDatagrid').datagrid('selectRow', index);
		}
		var selected = $('#SysParamDatagrid').datagrid('getSelected');
		if (selected) {
			$('#SysParamForm').form('clear');
			$('#SysParamForm').form('load', selected);
			if(selected.syncFlag){
				$('#syncFlag').attr("checked","true");
			}
			$('#SysParamForm').attr("action", "bp.sysparam.SysParamAction$update.json");
			$('#paramKeyTR', '#SysParamForm').css("display",'none');
			$('#SysParamWindow').window("setTitle", "编辑全局参数");
			$('#SysParamWindow').window("open");
		} else {
			$.messager.alert('提示', "请选择一行数据后再进行此操作！");
		}
	}
	
	function expData() {
		 var url = "bp.sysparam.SysParamAction$download.json";
		 window.location.href=url;
	}

	function deleteSysParam() {
		var selected = $('#SysParamDatagrid').datagrid('getSelected');
		if (selected) {
			$.messager.confirm('确认','确认要删除该信息吗？',
					function(ok) {
						if (ok) {
							$.ajax({
										type : 'POST',
										url : "bp.sysparam.SysParamAction$delete.json",
										data : {
											'id' : selected.id
										},
										success : function(data) {
											$('#SysParamDatagrid').datagrid('reload');
											$('#SysParamDatagrid').datagrid('clearSelections');
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

	function SysParamFormSubmit() {
		$('#SysParamForm').form('submit', {
			url : $("#SysParamForm").attr("action"),
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				$.messager.alert('提示', '全局参数保存成功！');
				$('#SysParamWindow').window('close');
				$('#SysParamDatagrid').datagrid('reload');
			}
		});
	}

	function SysParamFormReset() {
		if( "bp.sysparam.SysParamAction$save.json" == $('#SysParamForm').attr("action")){
			$('#SysParamForm').form('clear');
		}else{
			var selected = $('#SysParamDatagrid').datagrid('getSelected');
			$('#SysParamForm input:visible, #SysParamForm textarea').val('');
			$('#SysParamForm').form('load', selected);
		}
	}

</script>
</head>
<body>
    	<div class="bdcontent">
		<table id="SysParamDatagrid"></table>
	</div>
	<div id="SysParamToolbar"  style="padding: 5px; height: auto;">
	<form id="SysParamQueryForm" method="post" novalidate>
		 <span class="searchtt">参数ID</span> 
	 <input editable="false" id="queryParamKey" name="queryParamKey" type="text" maxlength='32' class="tinput">
    		 <span class="searchtt">参数名称</span> 
	 <input editable="false" id="queryParamName" name="queryParamName" type="text" maxlength='32' class="tinput">
        <a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-search"	onclick="doSysParamSearch();">查询</a>
	<a href="javascript:;" id="button-reset" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doSysParamQueryFormReset();">重置</a>
					<div style="text-align: right;">
					<w:func id="bp.sysparam.insert">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openSaveSysParamWin()">新增</a> 
					</w:func>
					<w:func id="bp.sysparam.update">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditSysParamWin()">编辑</a>
					</w:func>
					<w:func id="bp.sysparam.delete">
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteSysParam()">删除</a>
					</w:func>
					<w:func id="bp.sysparam.download">
					<a href="#" class="easyui-linkbutton" iconCls="icon-down" plain="true" onclick="javascript:expData()">导出 </a>
					</w:func>
		</div>
		</form>
	</div>
		<!-- 编辑新增窗口 -->
	<div id="SysParamWindow">
			<div id="winInfo"  style="margin:10px;overflow:hidden;">
				<form id="SysParamForm" method="post" novalidate>
					<table cellpadding="0" cellspacing="0" class="tbcontent" style ="width:505px;">	
						<tr id="paramKeyTR">
							<td class="tbtitle" width="40%">参数ID</td>
							<td><input id="paramKey" name="paramKey" type="text"  validType="maxLength['128']" size="32" maxlength="128" editable="false" class="easyui-validatebox tinput"  required=true  /></td>
						</tr>
						<tr>
							<td class="tbtitle">参数名称</td>
							<td><input id="paramName" name="paramName" type="text"  validType="maxLength['128']" size="32" maxlength="128" editable="false" class="easyui-validatebox tinput"  required=true  /></td>
						</tr>
						<tr>
							<td class="tbtitle">参数值</td>
							<td><input id="paramValue" name="paramValue" type="text"  validType="maxLength['256']" size="32" maxlength="128" editable="false" class="easyui-validatebox tinput"  required=true /></td>
						</tr>
						<tr>
							<td class="tbtitle">参数类型</td>
							<td><input id="paramType" name="paramType" type="text"  editable="false" class="easyui-combobox tinput"  panelHeight="180px"  required=true  /></td>
						</tr>
						<tr>
							<td class="tbtitle">默认值</td>
							<td><input id="defaultValue" name="defaultValue" type="text"  validType="maxLength['256']" size="32" maxlength="128" editable="false" class="easyui-validatebox tinput"  required=true   /></td>
						</tr>
						<tr>
							<td class="tbtitle">是否同步</td>
							<td><input id="syncFlag" type="checkbox" name="syncFlag" ></input></td>
						</tr>
					</table>					
				</form>
				<div style="text-align: center;margin-top:15px;">
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
								onclick="SysParamFormSubmit();"> 保存</a></span>
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;"
								onclick="SysParamFormReset()"> 重置</a></span>
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
								onclick="$('#SysParamWindow').window('close')"> 取消</a></span>
				</div>
			</div>
		</div>
	</body>
</html>