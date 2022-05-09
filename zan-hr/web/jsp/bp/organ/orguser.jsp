<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>组织人员管理</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<style>
	html,body{height:100%;}
</style>
<script>
	var treeId = '#organizationTree';
	$(function() {
		$(treeId).tree({
		    animate: true,
            url: 'bp.org.OrgAction$query.json?root=true&lazy=true',
		    onClick: function(node) {
	            loadOrgUser(node.id);
		    },
            onBeforeExpand: function(node) {
            	$(this).tree("options").url = "bp.org.OrgAction$query.json?lazy=true&parentId=" + node.id;
            }
		});
	});
	
	function loadOrgUser(orgId){
		$("#employeeList").datagrid({
			title:"组织员工",
			toolbar:"#toolbar",
			url:'bp.employee.EmployeeAction$queryOrgEmployee.json',
			singleSelect: true,
			fitColumns: true,
			fit: true,
			pagination: true,
			striped: true,
			rownumbers: true,
	        pageSize: <%=pageSize%>,//每页显示的记录条数，默认为10 
	        pageList: [<%=pageList%>],//可以设置每页记录条数的列表 
			columns:[
						[
						{field:'employeeName',title:'姓名',width:80,sortable:true},
						{field:'pyCode',title:'拼音码',width:80},
						{field:'nickName',title:'昵称',width:80},
						{field:'phone',title:'电话',width:80},
						{field:'mobileNumber',title:'手机',width:80},
						{field:'email',title:'邮箱',width:180},
						{field:'orgNames',title:'组织机构',width:180},
						{field:'address',title:'地址',width:180}
						]
					],
	        onBeforeLoad:function(param){
	        	param.orgId=orgId;
	        	param.employeeName =$("#hemployName").val();
	        } 
		});
	}
	
	function doSearch(){
		$('#employeeList').datagrid('load',{
			employeeName: $('#hemployName').val()
		});
	}

	function queryEmployeeOrg(){
		var uuserId = $('#employeeId').val();
		$('#organsList').treegrid({
			width:604,
			nowrap: false,
			striped: true,
			rownumbers: true,
			title:'员工组织机构',
			fitColumns: true,
			url:'bp.employee.EmployeeAction$queryEmployeeOrg.json',
			queryParams:{employeeId:uuserId},
			idField:'orgId',
			treeField:'orgName',
			columns:[[
				{field:'orgId',title:'机构ID',width:60},
				{field:'orgName',title:'组织机构',width:200},
				{field:'orgType',title:'机构类型',width:80},
				{field:'mark',title:'是否标记',align:'center',editor:{options:{on:'1',off:'0'}},width:50,formatter:function(value,rowData){
			    	var strChk=null;
					if (value){
						strChk='checked="true"';
			    	} else {
			    		strChk='';
			    	}
					return '<input type="checkbox" ' + strChk + ' disabled="disabled"/>';
		    	  }
				},
				{field:'dft',title:'是否默认值',align:'center',editor:{type:'radio',options:{on:'1',off:'0'}},width:60,formatter:function(value,rowData){
					var strRdo=null;
					if (value){
						strRdo='checked="true"';
			    	} else {
			    		strRdo='';
			    	}
					return '<input type="radio" ' + strRdo + ' disabled="disabled"/>';
		    	  }
				}
			]]
		});	 
	 }
	 
	 function doReset(){
		 $("#hemployName").val("");
		 
	 }
</script>
</head>
<body>
	<table id="tb" class="body_layout" cellspacing="0">
		<tr>
			<td valign="top" class="body_layout_left">
				<div id="cc" class="easyui-layout" fit="true">
				    <div id="orgPanel" region="west" split="true" style="width: 200px;">
				    	<div class="easyui-panel" title="组织机构管理"  fit="true" border="false">
					        <div class="easyui-panel" id="navtree" border="false" style="overflow: hidden;">
					        	<ul id="organizationTree" animate="true"></ul>
					        </div>					       
				        </div>
				    </div>
				    <div region="center" title="组织人员信息" >
				    	<div style="height:100%">
					    	<table id="employeeList"></table>
					    	<div id="toolbar"  style="display:none;">
					    		<span class="searchtt">员工姓名</span> 
								  <input id="hemployName" type="text" class="tinput"/>
								<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="doSearch()">查询</a>
								<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="doReset()">重置</a>
					    	</div>
	        			</div>
					</div>
	   			</div>
			</td>
		</tr>
	</table>
</body>
</html>