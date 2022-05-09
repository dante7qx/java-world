<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>通告信息</title>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="${ctx}/ux/app/js/epolleo-util.js"></script>
<script type="text/javascript">
			
		$(function(){
			$('#AnnounceInfoDatagrid').datagrid({
				pagination:true,
				rownumbers:true,
				title:'通告信息列表',
				url:'bp.announce.AnnounceInfoAction$query.json',
				sortName: 'effDate',
				sortOrder: 'desc',
				fitColumns: true,
				singleSelect: true,
				striped: true,
				collapsible: false,
				idField: 'id',
				pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
				pageList: [<%=pageList%>],//可以设置每页记录条数的列表 	
				toolbar:"#AnnounceInfoToolbar",
				queryParams:{topMark:"yes"},
				columns:[[
							{title:'编号',field:'id',width:110,align:'left',hidden:true},
							{title:'通告内容',field:'content',width:550,align:'left',sortable:false},
							{title:'生效时间',field:'effDate',width:80,align:'left',sortable:true,formatter:function(value,row){
								 if(value != null)
								 	{
										return '<span onclick="optEventHandler(\'view\',\'' + row.id + '\');" style="cursor:pointer;text-decoration:underline">' + value + '</span>' 
									}
							}},
							{title:'失效时间',field:'expDate',width:80,align:'left',sortable:true},
							{title:'更新人',field:'updateBy',width:80,align:'left',sortable:false},
							{title:'更新时间',field:'updateDate',width:105,align:'left',sortable:true}				
						]]
			});
		});
		//根据条件查询
		function doSearch () {
			var params = {
					announceDate:$("#announceDateQuery").datebox('getValue'),
					announceDateEndQuery:$("#announceDateEndQuery").datebox('getValue'),
					content: $('#contentQuery').val(),
					topMark:"no"
			}
			if($('#AnnounceInfoQueryForm').form('validate')){
				$('#AnnounceInfoDatagrid').datagrid({
					url:'bp.announce.AnnounceInfoAction$query.json',
					queryParams:params
				});
			}
		}
	 	//重置
	 	function doReset () {
	 		$('#AnnounceInfoQueryForm').form('clear');
	 	}
	 	
	 	//新增或者查看操作
		function optEventHandler(op,announceId){
			if (op == 'add') {
				$('#AnnounceInfoWin').window({
					title:"新增通告信息",
					width:650,
					height:260,
					href:"addAnnounce.jsp",
					closed:false,
					cache:false,
					modal:true,
					left:null,
					top:null,
					extractor:function(data){return data;}
				});
			}
			if (op == 'view') {
				$('#AnnounceInfoWin').window({
					title:"查看通告信息",
					width:650,
					height:260,
					href:'addAnnounce.jsp?code=' + announceId,
					closed:false,
					cache:false,
					modal:true,
					left:null,
					top:null,
					extractor:function(data){return data;}
				});
			}
			//epolleo.util.showWin('AnnounceInfoWin');
		}
</script>
</head>
<body>
    <div class="bdcontent">
		<table id="AnnounceInfoDatagrid"></table>
	</div>
	<div id="AnnounceInfoToolbar" style="padding:5px;height:auto">
		<form id="AnnounceInfoQueryForm" method="post" novalidate>
		<div>
			<span>内容</span>
			<input id="contentQuery" name="contentQuery" type="text"  class="easyui-validatebox tinput" style="width:280px"></input>
			<span>生效日期</span>
			<input id="announceDateQuery" name="announceDateQuery" type="text" class="easyui-datebox" style="width: 130px; height: 30px; overflow: auto" editable=false></input><span>-</span><input id="announceDateEndQuery" name="announceDateEndQuery" type="text" class="easyui-datebox"  style="width: 130px; height: 30px; overflow: auto" editable=false validType="dateCompare['announceDateQuery','announceDateEndQuery']"></input>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doReset()">重置</a>
		</div>
		<div style="text-align: right;">
				<w:func id="bp.announce.save"><a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="optEventHandler('add','')">新增</a></w:func>
			</div>
		</form>
	</div>
	<div id="AnnounceInfoWin" class="easyui-window" modal="true" inline="false"  collapsible="false" minimizable="false" maximizable="false" resizable="false" closed="true"  
		title="通告信息" style="width: 650px; overflow: hidden;"  >
	</div>
</body>
</html>