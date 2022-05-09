<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>员工信息</title>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="${ctx}/ux/app/js/hnasys-util.js"></script>
<script type="text/javascript" src="${ctx}/ux/app/js/hnasys-constant.js"></script>
<script type="text/javascript">
var empListPage = {
	genDictData : <w:map2json name="hr_employee.Gen"/>,
	horoscopeDictData : <w:map2json name="hr_employee.Horoscope"/>,
	workTypeDictData : <w:map2json name="hr_employee_work.Type"/>,
	load : function() {
		$('#empGridList').datagrid({
			rownumbers:true,
			title:'员工信息列表',
			url:'hr.employee.EmployeeAction$query.json',
			sortName: 'chnName',
			sortOrder: 'asc',
			fitColumns: true,
			singleSelect: true,
			striped: true,
			collapsible: false,
			pagination: true,
			idField: 'id',
			pageSize: <%=pageSize%>,
			pageList: [<%=pageList%>],
			toolbar:"#empToolbar",			
			columns:[[
					{title:'姓名',field:'chnName',width:110,align:'center',sortable:false,formatter:function(value,row,index){
						return '<a href="javascript:;"  onclick="empListPage.saveOrUpdate(\''+row['id']+'\')">'+value+'</a>';
					}},
					{title:'性别',field:'gen',width:50,align:'center',sortable:false,formatter:function(val) {
						return hnasys.util.typeFormatter(empListPage.genDictData, val);
					}},
					{title:'星座',field:'horoscope',width:50,align:'center',sortable:false,formatter:function(val) {
						return hnasys.util.typeFormatter(empListPage.horoscopeDictData, val);
					}},
					{title:'组织机构',field:'orgName',width:100,align:'left',sortable:false},
					{title:'部门',field:'dept',width:110,align:'left',sortable:false,formatter:function(val) {
						return hnasys.util.typeFormatter(empListPage.workTypeDictData, val);
					}},
					{title:'工作地点',field:'workPlaceName',width:100,align:'left',sortable:false},
					{title:'用工类型',field:'workType',width:70,align:'center',sortable:false,formatter:function(val) {
						return hnasys.util.typeFormatter(empListPage.workTypeDictData, val);
					}},
					{title:'职位',field:'post',width:100,align:'left',sortable:false},
					{title:'入职时间',field:'entrantDate',width:110,align:'left',sortable:false},
					{title:'联系方式',field:'phone',width:110,align:'left',sortable:false}
			]]
		});
	},
	initQueryCombobox : function() {
		$('#queryGen').combobox({
			data: empListPage.genDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		$('#queryCompany').combotree({
			url: 'pub.org.PubOrgAction$queryAllTree.json',
			editable: false,
			panelHeight: 'auto'
		});
		$('#queryWorkPlace').combobox({
			url: 'pub.location.PubLocationAction$queryCombo.json',
			editable: false,
			valueField: "id",
			textField: "name",
			panelHeight : 'auto'
		});
	},
	saveOrUpdate : function(id) {
		window.open('employeedetail.jsp?id='+id,"employeeDetail",' left=0,top=0,width='+ (screen.availWidth - 10) +',height='+ (screen.availHeight-50) +',scrollbars,resizable=yes,impListToolbar=yes');
	},
	search : function() {
		var isActive = $('#queryActive').attr('checked') ? 1 : 0;
		var orgNode = $('#queryCompany').combotree('tree').tree('getSelected');
		var queryCompany = orgNode ? orgNode.id : ''
		$('#empGridList').datagrid('load',{
			chnName: $('#queryChnName').val(),
			gen: $('#queryGen').combobox('getValue'),
			orgId: queryCompany,
			workPlaceId: $('#queryWorkPlace').combobox('getValue'),
			entrantStartDate: $('#queryEntrantStartDate').datebox('getValue'),
			entrantEndDate: $('#queryEntrantEndDate').datebox('getValue'),
			isActive: isActive
		});
	},
	reset : function() {
		$('#queryChnName').val('');
		$('#queryCompany').combotree('clear');
		$('#queryGen,#queryWorkPlace').combobox('clear');
		$('#queryEntrantStartDate,#queryEntrantEndDate').combobox('clear');
		$('#queryActive').attr('checked','checked');
	}
}

$(function() {
	empListPage.initQueryCombobox();
	empListPage.load();
});
</script>
</head>
<body>
    <div class="bdcontent">
		<table id="empGridList"></table>
    </div>
	<div id="empToolbar" style="padding:5px;height:auto">
		<table style="width: 100%;">
			<tr>
				<td>
					<table>
						<tr>
							<td>姓名</td>
							<td><input id="queryChnName" class="tinput" /></td>
							<td>性别</td>
							<td><input id="queryGen" class="tinput" style="width:50px;"/></td>
							<td>组织机构</td>
							<td><input id="queryCompany" class="tinput" /></td>
							<td>工作地点</td>
							<td><input id="queryWorkPlace" class="tinput" /></td>
							<td>入职时间</td>
							<td><input id="queryEntrantStartDate" class="easyui-datebox" style="width:90px;"/></td>
							<td>~</td>
							<td><input id="queryEntrantEndDate" class="easyui-datebox" style="width:90px;"/></td>
							<td>已生效</td>
							<td><input id="queryActive" type="checkbox" checked="checked"></td>
							<td><a href="javascript:;" class="easyui-linkbutton" plain="true"
								iconCls="icon-search" onclick="empListPage.search()">查询</a>
							</td>
							<td><a href="javascript:;" class="easyui-linkbutton" plain="true"
								iconCls="icon-undo" onclick="empListPage.reset()">重置</a>
							</td>
						</tr>
					</table>
				</td>
				<td align="right">
					<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="empListPage.saveOrUpdate(0)">新增</a>
				</td>
			</tr>
		</table>
	</div>
	
	<!-- 
	<div id="empWin" class="easyui-window" modal="true" inline="false"
			collapsible="false" minimizable="false" maximizable="false"
			resizable="false" closed="true"
			style="height: 550px; width: 900px; display: none;"></div>
	-->
</body>
</html>