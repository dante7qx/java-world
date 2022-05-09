<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>地理区域</title>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript">
			
		$(function(){
		    $('#LocWindow').window({
				width : 470,
				height : 320,
				maximizable:false,
				minimizable:false,
				collapsible:false,
				closed: true,
				modal : true
			});
			
			/* formatter:function(value,row,index){
							return '<a href="#"  onclick="openEditLocWin('+index+')">'+value+'</a>';
						} */
		
			$('#LocDatagrid').datagrid({
				pagination:true,
				rownumbers:true,
				title:'地理区域列表',
				url:'bp.location.LocAction$query.json',
				//sortName: 'LocId',
				sortOrder: 'asc',
				fitColumns: true,
				singleSelect: true,
				striped: true,
				collapsible: false,
				idField: 'LocId',
				pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
				pageList: [<%=pageList%>],//可以设置每页记录条数的列表 	
				toolbar:"#LocToolbar",			
				frozenColumns:[
		             [{field:'ck',checkbox:true}]
		        ],
				columns:[[
						{title:'地理区域ID',field:'locId',width:110,align:'left',sortable:true},
						{title:'地理区域名称',field:'locName',width:110,align:'left',sortable:true},
						{title:'地理区域备注',field:'locNote',width:110,align:'left',sortable:true},
						{title:'X坐标',field:'locX',width:110,align:'left',sortable:false},
						{title:'Y坐标',field:'locY',width:110,align:'left',sortable:false},
						{title:'标识码',field:'locCode',width:110,align:'left',sortable:true},
						{title:'状态',field:'enable', width:110,align:'left',sortable:false,formatter:function(value){return value ? '启用':'停用'}}
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
   
	function openSaveLocWin(){
		$('#LocForm').form('clear');
		$('#LocForm').attr("action", "bp.location.LocAction$save.json");
		$('#LocWindow').window("setTitle", "新增地理区域");
		$('#LocWindow').window("open");
	}

	function openEditLocWin(index) {
		if(typeof(index) != "undefined"){
			$('#LocDatagrid').datagrid('selectRow', index);
		}
		var selected = $('#LocDatagrid').datagrid('getSelected');
		if (selected) {
			$('#LocForm').form('clear');
			$('#LocForm').form('load', selected);
			$('#LocForm').attr("action", "bp.location.LocAction$update.json");
			$('#LocWindow').window("setTitle", "编辑地理区域");
			$('#LocWindow').window("open");
		} else {
			$.messager.alert('提示', "请选择一行数据后再进行此操作！");
		}
	}


	function deleteLoc() {
		var selected = $('#LocDatagrid').datagrid('getSelected');
		if (selected) {
			$.messager.confirm('确认','确认要删除该信息吗?',
					function(ok) {
						if (ok) {
							$.ajax({
										type : 'POST',
										url : "bp.location.LocAction$delete.json",
										data : {
											'id' : selected.locId
										},
										success : function(data) {
											$('#LocDatagrid').datagrid('reload');
											$('#LocDatagrid').datagrid('clearSelections');
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

	function LocFormSubmit() {
		$('#LocForm').form('submit', {
			url : $("#LocForm").attr("action"),
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				$.messager.alert('提示', '地理区域保存成功！');
				$('#LocWindow').window('close');
				$('#LocDatagrid').datagrid('reload');
			}
		});
	}

	function LocFormReset() {
		if( "bp.location.LocAction$save.json" == $('#LocForm').attr("action")){
			$('#LocForm').form('clear');
		}else{
			var selected = $('#LocDatagrid').datagrid('getSelected');
			$('#LocForm').form('load', selected);
		}
	}

</script>
</head>
<body style="margin: 10px">
    	<div class="bdcontent">
		<table id="LocDatagrid"></table>
	</div>
	<div id="LocToolbar"  style="padding: 5px; height: auto;">
	<form id="LocQueryForm" method="post" novalidate>
				<div style="text-align: right;">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openSaveLocWin()">新增</a> 
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openEditLocWin()">编辑</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteLoc()">删除</a>
		</div>
		</form>
	</div>
		<!-- 编辑新增窗口 -->
	<div id="LocWindow">
			<div id="winInfo"  style="margin:10px;overflow:hidden;">
				<form id="LocForm" method="post" novalidate>
					<input id="LocId" name="locId" type="hidden"  validType="maxLength['10']" maxlength="10" editable="false" class="easyui-validatebox tinput"  />
					<table cellpadding="0" cellspacing="0" class="tbcontent" style ="width:420px;">	
						<tr>
							<td class="tbtitle">地理区域名称</td>
							<td><input style ="width:300px;" id="LocName" name="locName" type="text"  validType="maxLength['256']" maxlength="256" editable="false" class="easyui-validatebox tinput"  data-options="required:true"/></td>
						</tr>
						<tr>
							<td class="tbtitle">地理区域备注</td>
							<td><textarea style="width:300px;" id="LocNote" name="locNote" validType="maxLength['512']"  maxlength="512"  rows="2" class="easyui-validatebox tinput"></textarea>
							</td>
						</tr>
						<tr>
							<td class="tbtitle">X坐标</td>
							<td><input style="width:300px;" id="LocX" name="locX" type="text"  editable="false" class="easyui-numberbox tinput"  data-options="required:true,validType:'numberType'"/></td>
						</tr>
						<tr>
							<td class="tbtitle">Y坐标</td>
							<td><input style="width:300px;" id="LocY" name="locY" type="text"  editable="false" class="easyui-numberbox tinput"  data-options="required:true,validType:'numberType'"/></td>
						</tr>
						<tr>
							<td class="tbtitle">标识码</td>
							<td><input style="width:300px;" id="LocCode" name="locCode" type="text"  validType="maxLength['64']" maxlength="64" editable="false" class="easyui-validatebox tinput"  /></td>
						</tr>
						<tr>
							<td class="tbtitle">状态</td>
							<td><input id="Enable" name="enable" type="checkbox" value="true" /></td>
						</tr>
					</table>					
				</form>
				<div style="text-align: center;margin-top:15px;">
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
								onclick="LocFormSubmit();"> 保存</a></span>
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;"
								onclick="LocFormReset()"> 重置</a></span>
				<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
								onclick="$('#LocWindow').window('close')"> 取消</a></span>
				</div>
			</div>
		</div>
	</body>
</html>