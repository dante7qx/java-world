<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>报表记录</title>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript">
	$(function() {
		$('#ReportRecordDatagrid').datagrid({
			pagination : true,
			rownumbers : true,
			title : '报表记录列表',
			url : 'bp.report.ReportRecordAction$query.json',
			sortName: 'createTime',
			sortOrder : 'desc',
			fitColumns : true,
			singleSelect : true,
			striped : true,
			collapsible : false,
			idField : 'seqId',
			pageSize : _pageSize_,//每页显示的记录条数，默认为10 
			pageList : _pageList_,//可以设置每页记录条数的列表    
			toolbar : "#ReportRecordToolbar",            
            frozenColumns:[[{field:'ck',checkbox:true}]],
            columns:[[
                    {title:'流水号',field:'seqId',width:30,align:'left',sortable:true},
                    {title:'报表ID',field:'reportId',width:100,align:'left',sortable:true},
                    {title:'报表参数',field:'reportKey',width:100,align:'left',sortable:true},
                    {title:'报表名称',field:'reportTitle',width:100,align:'left'},
                    {title:'创建机构',field:'orgName',width:60,align:'left'},
                    {title:'创建人',field:'userId',width:50,align:'left',sortable:true},
                    {title:'创建时间',field:'createTime',width:80,align:'left',sortable:true}
            ]]
		});
	});
	/*把枚举类型的key，转化成value，供页面显示*/
	function typeFormatter(datas, value) {
		for ( var i = 0; i < datas.length; i++) {
			if (datas[i].key == value)
				return datas[i].value;
		}
		return value;
	}

	function deleteReportRecord() {
		var selected = $('#ReportRecordDatagrid').datagrid('getSelected');
		if (selected) {
			$.messager.confirm('确认', '确认要删除该信息吗？', function(ok) {
				if (ok) {
					$.ajax({
						type : 'POST',
						url : "bp.report.ReportRecordAction$delete.json",
						data : {
							id : selected.seqId
						},
						success : function(data) {
							$('#ReportRecordDatagrid').datagrid('reload');
						},
						error : function(j, status, error) {
							$.messager.alert('提示', error);
						}
					});
				}
			});
		} else {
			$.messager.alert('提示', "请选择一行数据后再进行此操作！");
		}
	}
</script>
</head>
<body>
	<div class="bdcontent">
		<table id="ReportRecordDatagrid"></table>
	</div>
	<div id="ReportRecordToolbar" style="padding: 5px; height: auto;">
		<form id="ReportRecordQueryForm" method="post" novalidate>
			<div style="text-align: right;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
					plain="true" onclick="deleteReportRecord()">删除</a>
			</div>
		</form>
	</div>
</body>
</html>