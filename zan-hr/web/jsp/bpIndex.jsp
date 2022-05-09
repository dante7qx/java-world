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
							{title:'生效时间',field:'effDate',width:80,align:'left',sortable:true},
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
	 	
</script>
</head>
<body>
    <div class="bdcontent">
		<table id="AnnounceInfoDatagrid"></table>
	</div>
	<div id="AnnounceInfoToolbar"  style="padding: 5px; height: auto;">
		<form id="AnnounceInfoQueryForm" method="post" novalidate>
			 <table>
				 <tr>
					 <td class="tableTdAlign"><span class="searchtt">内容</span></td>
					 <td style="width:300px">
					 	<input id="contentQuery" name="contentQuery" type="text"  class="easyui-validatebox tinput" style="width:280px"></input>
					 </td>
					  <td class="tableTdAlign"> <span class="searchtt">生效日期</span></td>
					 <td>
					 	<input id="announceDateQuery" name="announceDateQuery" type="text" class="easyui-datebox" style="width: 130px; height: 30px; overflow: auto" editable=false></input>
					 </td>
	 				 <td class="tableTdAlign"> <span class="searchtt">-</span></td>
					 <td>
					 	<input id="announceDateEndQuery" name="announceDateEndQuery" type="text" class="easyui-datebox"  style="width: 130px; height: 30px; overflow: auto"
					 		   editable=false validType="dateCompare['announceDateQuery','announceDateEndQuery']"></input>
					 </td>
					 <td style="width:100px">&nbsp;</td>
					 <td>
						<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
						<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doReset()">重置</a>
					</td>
				 </tr>
			 </table>
		</form>
	</div>
</body>
</html>