<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>号码</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<script type="text/javascript">
		$(function(){
		    $('#SequenceWindow').window({
				maximizable:false,
				minimizable:false,
				collapsible:false,
				closed: true,
				modal : true
			});
			
			$('#SequenceDatagrid').datagrid({
				pagination:true,
				rownumbers:true,
				title:'号码列表',
				url:'bp.seq.SeqAction$query.json',
				sortName: 'seqKey',
				sortOrder: 'asc',
				remoteSort: true,
				fitColumns: true,
				singleSelect: true,
				striped: true,
				collapsible: false,
				idField: 'seqKey',
				pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
				pageList: [<%=pageList%>],//可以设置每页记录条数的列表 	
				toolbar:"#SequenceToolbar",			
				frozenColumns:[
		             [{field:'ck',checkbox:true}]
		        ],
		        columns:[[
							{field:'seqKey',title:'编码',width:120,sortable:true},
							{field:'name',title:'名称',width:200,editor:{type:'validatebox',options:{required:true,validType:"maxLength[64]"}},sortable:true},
							{field:'value',title:'当前值',width:80,editor:{type:'numberbox',options:{required:true,validType:"maxLength[9]",precision:0}}},
							{field:'fetchSize',title:'缓存大小',width:80,editor:{type:'numberbox',options:{required:true,validType:"maxLength[8]",precision:0}}},
							{field:'expression',title:'表达式',width:120,editor:{type:'validatebox',options:{validType:"maxLength[32]"}}}
						]]
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
   	function doSequenceSearch(){
		var params = {
        seqKey: $('#querySeqKey').val(),
        name: $('#queryName').val()    }
     $('#SequenceDatagrid').datagrid('load',params);
	}
	/*list列表的重置*/
    function doSequenceQueryFormReset(){
		$("#SequenceQueryForm").form("clear");
    }
    
 
	function openSaveSequenceWin(){
		$('#SequenceForm').form('clear');
		$('#SequenceForm').attr("action", "bp.seq.SeqAction$save.json");
		$('#seqKeyTR', '#SequenceForm').css("display",'');
		$('#SequenceWindow').window("setTitle", "新增号码");
		$('#SequenceWindow').window("open");
	}

	function openEditSequenceWin(index) {
		if(typeof(index) != "undefined"){
			$('#SequenceDatagrid').datagrid('selectRow', index);
		}
		var selected = $('#SequenceDatagrid').datagrid('getSelected');
		if (selected) {
			$('#SequenceForm').form('clear');
			$('#SequenceForm').form('load', selected);
			$('#SequenceForm').attr("action", "bp.seq.SeqAction$update.json");
			$('#seqKeyTR', '#SequenceForm').css("display",'none');
			$('#SequenceWindow').window("setTitle", "编辑号码");
			$('#SequenceWindow').window("open");
		} else {
			$.messager.alert('提示', "请选择一行数据后再进行此操作！");
		}
	}


	function deleteSequence() {
		var selected = $('#SequenceDatagrid').datagrid('getSelected');
		if (selected) {
			$.messager.confirm('确认','确认要删除该信息吗？',
					function(ok) {
						if (ok) {
							$.ajax({
								type : 'POST',
								url : "bp.seq.SeqAction$delete.json",
								data : {
									'id' : selected.id
								},
								success : function(data) {
									$('#SequenceDatagrid').datagrid('reload');
									$('#SequenceDatagrid').datagrid('clearSelections');
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

	function SequenceFormSubmit() {
		$('#SequenceForm').form('submit', {
			url : $("#SequenceForm").attr("action"),
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				$.messager.alert('提示', '号码保存成功！');
				$('#SequenceWindow').window('close');
				$('#SequenceDatagrid').datagrid('reload');
			}
		});
	}

	function SequenceFormReset() {
		if( "bp.seq.SeqAction$save.json" == $('#SequenceForm').attr("action")){
			$('#SequenceForm').form('clear');
		}else{
			var selected = $('#SequenceDatagrid').datagrid('getSelected');
			$('#SequenceForm input:visible, #SequenceForm textarea').val('');
			$('#SequenceForm').form('load', selected);
		}
	}

</script>
</head>
<body>
    	<div class="bdcontent">
		<table id="SequenceDatagrid"></table>
	</div>
	<div id="SequenceToolbar"  style="padding: 5px; height: auto;">
	<form id="SequenceQueryForm" method="post" novalidate>
		 <span class="searchtt">号码编码</span> 
	 <input editable="false" id="querySeqKey" name="querySeqKey" type="text" maxlength="20"  class="tinput">
    		 <span class="searchtt">号码名称</span> 
	 <input editable="false" id="queryName" name="queryName" type="text" maxlength="20"   class="tinput">
        <a href="javascript:;" class="easyui-linkbutton" plain="true" iconCls="icon-search"	onclick="doSequenceSearch();">查询</a>
	<a href="javascript:;" id="button-reset" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doSequenceQueryFormReset();">重置</a>
					<div style="text-align: right;">
					<w:func id="bp.seq.insert">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openSaveSequenceWin()">新增</a> 
					</w:func>
					<w:func id="bp.seq.update">
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditSequenceWin()">编辑</a>
					</w:func>
					<w:func id="bp.seq.delete">
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteSequence()">删除</a>
					</w:func>
		</div>
		</form>
	</div>
		<!-- 编辑新增窗口 -->
	<div id="SequenceWindow">
			<div id="winInfo"  style="margin:10px;overflow:hidden;">
				<form id="SequenceForm" method="post" novalidate>
					<table cellpadding="0" cellspacing="0" class="tbcontent" style ="width:450px;">
					
				<tr id="seqKeyTR">
					<td align="right" class="tbtitle" width="35%">编号</td>
					<td><input id="seqKey" name="seqKey" type="text" class="easyui-validatebox tinput" size="32" maxlength="64" required="true"/></td>
				</tr>
				<tr>
					<td align="right" class="tbtitle">名称</td>
					<td><input id="name" name="name" type="text" class="easyui-validatebox tinput" size="32"  maxlength="64" required="true"/></td>
				</tr>
				<tr>
					<td align="right" class="tbtitle">当前值</td>
					<td><input id="value" name="value" type="text" class="easyui-numberbox tinput" size="10" maxlength="9" required="true"/></td>
				</tr>
				<tr>
					<td align="right" class="tbtitle">缓存大小</td>
					<td><input id="fetchSize" name="fetchSize" type="text" class="easyui-numberbox tinput" size="10" maxlength="8" required="true"/></td>
				</tr>
				<tr>
					<td align="right" class="tbtitle">表达式</td>
					<td><input id="expression" name="expression" type="text" class="easyui-validatebox tinput" size="32" maxlength="32"/></td>
				</tr>
					</table>					
				</form>
				<div style="text-align: center;margin-top:15px;">
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
								onclick="SequenceFormSubmit();"> 保存</a></span>
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;"
								onclick="SequenceFormReset()"> 重置</a></span>
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
								onclick="$('#SequenceWindow').window('close')"> 取消</a></span>
				</div>
			</div>
		</div>
	</body>
</html>