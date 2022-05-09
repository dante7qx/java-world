<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>员工信息</title>
<%@ include file="/jsp/common/common4tab.jsp"%>
<style type="text/css">
.gridOpIco {
	margin: 0px auto;
	display:inline-block;
	width:16px;
	height:16px;
}
</style>
<script type="text/javascript" src="${ctx}/ux/app/js/hnasys-util.js"></script>
<script type="text/javascript" src="${ctx}/ux/app/js/hnasys-constant.js"></script>
<script type="text/javascript">
var empDetailPage = {
	paramId : "${param.id}",
	genDictData : <w:map2json name="hr_employee.Gen"/>,
	horoscopeDictData : <w:map2json name="hr_employee.Horoscope"/>,
	householdDictData : <w:map2json name="hr_employee.Household"/>,
	politicsDictData : <w:map2json name="hr_employee.Politics"/>,
	educationDictData : <w:map2json name="hr_employee.Education"/>,
	languageSkillDictData : <w:map2json name="hr_employee.LanguageSkill"/>,
	marriageDictData : <w:map2json name="hr_employee.Marriage"/>,
	relationDictData : <w:map2json name="hr_employee_relation.Relation"/>,
	capacityLevelDictData : <w:map2json name="hr_employee_work.CapacityLevel"/>,
	contractDeadlineDictData : <w:map2json name="hr_employee_work.ContractDeadline"/>,
	contractTypeDictData : <w:map2json name="hr_employee_work.ContractType"/>,
	
	WORK_TYPE : {
		ZSYG : 10, //正式员工
		SXYG : 20, //实习员工
		LSG  : 30 //临时工
	},
	
	initTabs : function() {
		$('#centerTabs').tabs({			
			onSelect : function(title,index){
				var tab =  $('#centerTabs').tabs('getTab',title);
				var tabHref = tab.panel('options').href;
				var strHref ="";
				var id = $('#id','#empInfoFrm').val();
				if(tabHref==null){
					if(title == "薪资福利"){
						strHref = 'benefit.jsp?empId='+id;
					} else if(title == "补充信息"){
						strHref = 'other.jsp?empId='+id;
					}  
					if(strHref!=""){
						tab.panel({href:strHref ,extractor:function(data){return data;}});
					}
				} else {
					$('#centerTabs').tabs('getSelected').panel('refresh');
				}
			}
		});
	},
	initPage : function() {
		this.initCombobox();
		if(this.paramId == 0) {
			this.hideOtherTabs(true);
			this.editableInfoBtn(true);
		} else {
			this.loadData(false);
		}
		this.loadCertInfo(this.paramId);
		this.loadWorkInfo(this.paramId, false);
		this.loadRelationInfo(this.paramId);
		
		this.fireWorkTypeClickEvt();
		$('#centerTabDiv').height($(window).height() - 50);
		$('#infoPanel,#certificatePanel,#workInfoPanel,#relationInfoPanel','#centerTabDiv').panel('resize',{
			width: $(window).width() - 55
		});
	},
	loadData : function(editable) {
		this.editableInfoBtn(editable);
		$.ajax({
			url:'hr.employee.EmployeeAction$queryById.json',
			type:'post',
			dataType: "json",
			data:{
				id: empDetailPage.paramId
			},
			success: function (data){
				$('#empInfoFrm').form('clear');
				$('#empInfoFrm').form('load', data);
				empDetailPage.fillAge(data['birthday']);
				if(data['attLotId']) {
					$('#empPhoto','#empInfoFrm').attr('src', 'pub.att.AttAction$img.json?lotId='+data['attLotId']+'&t='+new Date().getTime());
				}
				if(data['isActive']) {
					$('#isActive','#empInfoFrm').attr('checked','checked');
				}
				hnasys.util.isEditForm('empInfoFrm', editable);
			}	
		});
	},
	hideOtherTabs : function(hidden) {
		hnasys.util.dispalyTab('centerTabs', '薪资福利', !hidden);
		hnasys.util.dispalyTab('centerTabs', '补充信息', !hidden);
	},
	initCombobox : function() {
		$('#gen','#empInfoFrm').combobox({
			data: empDetailPage.genDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		$('#horoscope','#empInfoFrm').combobox({
			data: empDetailPage.horoscopeDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		$('#household','#empInfoFrm').combobox({
			data: empDetailPage.householdDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		$('#politics','#empInfoFrm').combobox({
			data: empDetailPage.politicsDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		$('#education','#empInfoFrm').combobox({
			data: empDetailPage.educationDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		$('#languageSkill','#empInfoFrm').combobox({
			data: empDetailPage.languageSkillDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		$('#marriage','#empInfoFrm').combobox({
			data: empDetailPage.marriageDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		$('#birthday','#empInfoFrm').datebox({
			onChange : function(newValue, oldValue) {
				empDetailPage.fillAge(newValue);
			}
		})
	},
	fillAge : function(birthday) {
		if(birthday) {
			var yearStr = birthday.substring(0,4);
			var curYear = new Date().getFullYear();
			$('#ageSpan','#empInfoFrm').text(curYear - parseInt(yearStr,10));
		} else {
			$('#ageSpan','#empInfoFrm').text('');
		}
	},
	uploadPhoto : function() {
		$('#empInfoFrm').form('submit', {
			url : 'hr.employee.EmployeeAction$uploadImg.json',
			onSubmit : function() {
				return empDetailPage.validAtt();
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if(data['flag']) {
					$('#empPhoto','#empInfoFrm').attr('src', 'hr.employee.EmployeeAction$showImg.json?imgInstanceId='+data['imgInstanceId']);
				} else {
					$.messager.alert('错误','上传失败，请联系系统管理员!', 'error');
				}
			}
		});
	},
	validAtt :　function () {
		var result = true;
		$('input[name="photoAtt"]','#empInfoFrm').each(
			function(i, n) {
				if (n.value != "") {
					var re = new RegExp("(.jpg|.jpeg|.gif|.png)$");
					if (!re.test(n.value.toLowerCase())) {
						$.messager.alert("提示",
								"只能选择格式为:pdf,jpg,jpeg,gif,png\n的图片文件上传!");
						result = false;
						return false;
					}
				}
			}
		);
		return result;
	},
	generateBirthday : function() {
		if($('#idNum','#empInfoFrm').validatebox('isValid')) {
			var yearStr = $('#idNum','#empInfoFrm').val().substring(6, 10);
			var monthStr = $('#idNum','#empInfoFrm').val().substring(10, 12);
			var dayStr = $('#idNum','#empInfoFrm').val().substring(12, 14);
			var birthdayVal = yearStr + '-' + monthStr + '-' + parseInt(dayStr,10);
			var curYear = new Date().getFullYear();
			$('#birthday','#empInfoFrm').datebox('setValue',birthdayVal);
		} else {
			$('#birthday','#empInfoFrm').datebox('clear');
		}
	},
	submitEmpInfo : function() {
		$('#empInfoFrm').form('submit', {
			url : 'hr.employee.EmployeeAction$update.json',
			success : function(data) {
				var cToObj = eval('(' + data + ')');
		    	 if(!cToObj.flag) {
		    		 $.messager.alert('错误', '附件上传失败! '+ cToObj, 'error');
		    	 } else if(cToObj.flag == 1) {
		    		 empDetailPage.paramId = cToObj.data['id'];
		    		 $('#id','#empInfoFrm').val(cToObj.data['id']);
		    		 $('#attLotId','#empInfoFrm').val(cToObj.data['attLotId']);
		    		 empDetailPage.editableInfoBtn(false);
		    		 hnasys.util.isEditForm('empInfoFrm', false);
		    		 $.messager.alert('提示','保存成功!');
		    		 window.opener.empListPage.load();
		    	 } else if(cToObj.flag == 2){
		    		 $.messager.alert('错误','保存失败，请联系系统管理员!', 'error');
		    	 }
			}
		});
	},
	delEmpInfo : function() {
		$.messager.confirm('提示','你确定要删除吗?',function(r){
		    if (r){
		    	$.ajax({
					url:'hr.employee.EmployeeAction$delete.json',
					type:'post',
					dataType: "json",
					data:{
						id: $('#id','#empInfoFrm').val()
					},
					success: function (data){
						var cToObj = eval('(' + data + ')');
						if(cToObj) {
							$.messager.alert('提示','删除成功!');
							window.close();
						} else {
							$.messager.alert('错误','删除失败，请联系系统管理员!', 'error');
						}
					}	
				});
		    }
		});
	},
	resetEmpInfo : function() {
		if(empDetailPage.paramId == 0) {
			$('#empInfoFrm').form('clear');
		} else {
			this.loadData(true);
		}
	},
	cancelEmpInfo : function() {
		var empId = $('#id','#empInfoFrm').val();
		if(empId) {
			this.loadData(false);
		} else {
			window.close();
		}
		
	},
	editableInfoBtn : function(editable) {
		if(editable) {
			$('#frmEditBtn,#frmDelBtn','#empInfoBtnDiv').hide();
			$('#uploadPhotoAttTd','#empInfoFrm').children().show();
			$('#frmSaveBtn,#frmRedoBtn,#frmCancelBtn','#empInfoBtnDiv').show();
		} else {
			$('#frmEditBtn,#frmDelBtn','#empInfoBtnDiv').show();
			$('#uploadPhotoAttTd','#empInfoFrm').children().hide();
			$('#frmSaveBtn,#frmRedoBtn,#frmCancelBtn','#empInfoBtnDiv').hide();
		}
	},
	editInfo : function() {
		hnasys.util.isEditForm('empInfoFrm', true);
		this.editableInfoBtn(true);
	},
	loadCertInfo : function(empId) {
		$('#empCertGridList').datagrid({
			rownumbers:true,
			url:'hr.employee.EmployeeCertificateAction$queryByEmpId.json',
			fitColumns: true,
			singleSelect: true,
			striped: true,
			collapsible: false,
			pagination: false,
			idField: 'id',
			height: 300,
			width: $(window).width() - 70,
			toolbar:"#empCertToolbar",
			queryParams: {empId: empId},
			columns:[[
					{title:'证书名称',field:'name',width:110,align:'left',sortable:false,formatter:function(value,row,index){
						return '<a href="javascript:;" onclick="empDetailPage.editCert(\''+index+'\')">'+value+'</a>';
					}},
					{title:'签发日期',field:'signDate',width:70,align:'center',sortable:false},
					{title:'有效期至',field:'expDate',width:70,align:'center',sortable:false,formatter:function(val) {
						return val ? val : '终身';
					}},
					{title:'操作',field:'id',width:60,align:'center',sortable:false,formatter:function(value,row,index) {
						return '<span><a href="javascript:;" title="删除" onclick="empDetailPage.delCert('+value+')" class="gridOpIco icon-remove"></a></span>';
					}}
			]]
		});
	},
	editCert : function(rowIndex) {
		var empId = $('#id','#empInfoFrm').val();
		if(!empId) {
			$.messager.alert('提示','请先保存员工基本信息!');
			return;
		}
		$('#empCertWin').show().window('open');
		$('#empCertFrm').form('clear');
		if(rowIndex >= 0) {
			$('#empCertGridList').datagrid('selectRow', rowIndex);
			var row = $('#empCertGridList').datagrid('getSelected');
			$('#empCertFrm').form('load', row);
			this.editableCertBtn(false);
			hnasys.util.isEditForm('empCertFrm', false);
		} else {
			this.editableCertBtn(true);
			hnasys.util.isEditForm('empCertFrm', true);
		}
	},
	delCert : function(certId) {
		if(!certId) {
			certId = $('#certId','#empCertFrm').val();
		}
		$.messager.confirm('提示','你确定要删除吗?',function(r){
		    if (r){
		    	$.ajax({
					url:'hr.employee.EmployeeCertificateAction$delete.json',
					type:'post',
					dataType: "json",
					data:{
						id: certId
					},
					success: function (data){
						var cToObj = eval('(' + data + ')');
						if(cToObj) {
							$.messager.alert('提示','删除成功!');
							$('#empCertGridList').datagrid('reload');
						} else {
							$.messager.alert('错误','删除失败，请联系系统管理员!', 'error');
						}
					}	
				});
		    }
		});
	},
	submitEmpCert : function() {
		$('#certEmployeeId','#empCertFrm').val($('#id','#empInfoFrm').val());
		$('#empCertFrm').form('submit', {
			url : 'hr.employee.EmployeeCertificateAction$update.json',
			success : function(data) {
				 var cToObj = eval('(' + data + ')');
		    	 if(cToObj) {
		    		 $.messager.alert('提示','保存成功!');
		    		 $('#empCertWin').window('close');
		    		 $('#empCertGridList').datagrid('reload');
		    	 } else {
		    		 $.messager.alert('错误','保存失败，请联系系统管理员!', 'error');
		    	 }
			}
		});
	},
	resetEmpCert : function() {
		var id = $('#certId','#empCertFrm').val();
		if(id) {
			$('#empCertFrm').form('load', $('#empCertGridList').datagrid('getSelected'));
		} else {
			$('#empCertFrm').form('clear');
		}
	},
	editableCertBtn : function(editable) {
		if(editable) {
			$('#certFrmEditBtn,#certFrmDelBtn','#empCertFrm').hide();
			$('#certFrmSaveBtn,#certFrmRedoBtn','#empCertFrm').show();
		} else {
			$('#certFrmEditBtn,#certFrmDelBtn','#empCertFrm').show();
			$('#certFrmSaveBtn,#certFrmRedoBtn','#empCertFrm').hide();
		}
	},
	editCertInfo : function() {
		this.editableCertBtn(true);
		hnasys.util.isEditForm('empCertFrm', true);
	},
	
	loadRelationInfo : function(empId) {
		$('#empRelationGridList').datagrid({
			rownumbers:true,
			url:'hr.employee.EmployeeRelationAction$queryByEmpId.json',
			fitColumns: true,
			singleSelect: true,
			striped: true,
			collapsible: false,
			pagination: false,
			idField: 'id',
			height: 300,
			width: $(window).width() - 70,
			toolbar:"#empRelationToolbar",
			queryParams: {empId: empId},
			columns:[[
					{title:'姓名',field:'name',width:110,align:'left',sortable:false,formatter:function(value,row,index){
						return '<a href="javascript:;" onclick="empDetailPage.editRelation(\''+index+'\')">'+value+'</a>';
					}},
					{title:'与本人关系',field:'relation',width:90,align:'center',sortable:false,formatter:function(val) {
						return hnasys.util.typeFormatter(empDetailPage.relationDictData, val);
					}},
					{title:'联系电话',field:'phone',width:110,align:'left',sortable:false},
					{title:'操作',field:'id',width:60,align:'center',sortable:false,formatter:function(value,row,index) {
						return '<span><a href="javascript:;" title="删除" onclick="empDetailPage.delRelation('+value+')" class="gridOpIco icon-remove"></a></span>';
					}}
			]]
		});
	},
	editRelation : function(rowIndex) {
		var empId = $('#id','#empInfoFrm').val();
		if(!empId) {
			$.messager.alert('提示','请先保存员工基本信息!');
			return;
		}
		$('#relation','#empRelationFrm').combobox({
			data: empDetailPage.relationDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		$('#empRelationWin').show().window('open');
		$('#empRelationFrm').form('clear');
		if(rowIndex >= 0) {
			$('#empRelationGridList').datagrid('selectRow', rowIndex);
			var row = $('#empRelationGridList').datagrid('getSelected');
			$('#empRelationFrm').form('load', row);
			this.editableRelationBtn(false);
			hnasys.util.isEditForm('empRelationFrm', false);
		} else {
			this.editableRelationBtn(true);
			hnasys.util.isEditForm('empRelationFrm', true);
		}
	},
	delRelation : function(relationId) {
		if(!relationId) {
			relationId = $('#relationId','#empRelationFrm').val();
		}
		$.messager.confirm('提示','你确定要删除吗?',function(r){
		    if (r){
		    	$.ajax({
					url:'hr.employee.EmployeeRelationAction$delete.json',
					type:'post',
					dataType: "json",
					data:{
						id: relationId
					},
					success: function (data){
						var cToObj = eval('(' + data + ')');
						if(cToObj) {
							$.messager.alert('提示','删除成功!');
							$('#empRelationGridList').datagrid('reload');
						} else {
							$.messager.alert('错误','删除失败，请联系系统管理员!', 'error');
						}
					}	
				});
		    }
		});
	},
	submitEmpRelation : function() {
		$('#relationEmployeeId','#empRelationFrm').val($('#id','#empInfoFrm').val());
		$('#empRelationFrm').form('submit', {
			url : 'hr.employee.EmployeeRelationAction$update.json',
			success : function(data) {
				 var cToObj = eval('(' + data + ')');
		    	 if(cToObj) {
		    		 $.messager.alert('提示','保存成功!');
		    		 $('#empRelationWin').window('close');
		    		 $('#empRelationGridList').datagrid('reload');
		    	 } else {
		    		 $.messager.alert('错误','保存失败，请联系系统管理员!', 'error');
		    	 }
			}
		});
	},
	resetEmpRelation : function() {
		var id = $('#relationId','#empRelationFrm').val();
		if(id) {
			$('#empRelationFrm').form('load', $('#empRelationGridList').datagrid('getSelected'));
		} else {
			$('#empRelationFrm').form('clear');
		}
	},
	editableRelationBtn : function(editable) {
		if(editable) {
			$('#relationFrmEditBtn,#relationFrmDelBtn','#empRelationFrm').hide();
			$('#relationFrmSaveBtn,#relationFrmRedoBtn','#empRelationFrm').show();
		} else {
			$('#relationFrmEditBtn,#relationFrmDelBtn','#empRelationFrm').show();
			$('#relationFrmSaveBtn,#relationFrmRedoBtn','#empRelationFrm').hide();
		}
	},
	editRelationInfo : function() {
		this.editableRelationBtn(true);
		hnasys.util.isEditForm('empRelationFrm', true);
	},
	
	initWorkCombobox: function() {
		$('#post','#empWorkInfoFrm').combobox({
			url : 'hr.dictmnt.CodeDictAction$queryComboByType.json',
			editable: false,
			valueField: "id",
			textField: "value",
			panelHeight : 'auto',
			onBeforeLoad: function(param){
				param.type = epolleo.constant.type.RZGW;
			}
		});
		
		$('#capacityLevel','#empWorkInfoFrm').combobox({
			data: empDetailPage.capacityLevelDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		
		$('#contractDeadline','#empWorkInfoFrm').combobox({
			data: empDetailPage.contractDeadlineDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		
		$('#contractType','#empWorkInfoFrm').combobox({
			data: empDetailPage.contractTypeDictData,
			editable: false,
			valueField: "key",
			textField: "value",
			panelHeight : 'auto'
		});
		
	},
	initWorkOrgCombobox : function(orgId, deptId) {
		$('#company','#empWorkInfoFrm').combotree({
			url: 'pub.org.PubOrgAction$queryTreeByType.json?typeId='+epolleo.constant.org_type.GS+'&nodeLevel=3',
			editable: false,
			panelHeight: 'auto',
			onChange: function(newValue, oldValue) {
				$('#dept','#empWorkInfoFrm').combobox({
					url : 'pub.org.PubOrgAction$queryTreeByPidAndTypeId.json',
					editable: false,
					valueField: "id",
					textField: "name",
					panelHeight : 'auto',
					onBeforeLoad: function(param){
						param.pid = newValue;
						param.typeId = epolleo.constant.org_type.BM;
					}
				});
				$('#dept','#empWorkInfoFrm').combobox('clear');
			}
		});
		if(orgId) {
			$('#company','#empWorkInfoFrm').combotree('setValue', orgId);
		} 
		if(deptId) {
			$('#dept','#empWorkInfoFrm').combobox('setValue', deptId);
		}
	},
	initWorkPlaceCombobox : function(id) {
		$('#workPlaceId','#empWorkInfoFrm').combobox({
			url : 'pub.location.PubLocationAction$queryCombo.json',
			editable: false,
			valueField: "id",
			textField: "name",
			panelHeight : 'auto',
			onBeforeLoad: function(param){
				if(id) {
					param.id = id;
				} else {
					param.isNew = true;
				}
			}
		});
	},
	loadWorkInfo : function(empId, editable) {
		this.initWorkCombobox();
		this.editableWorkBtn(editable);
		$.ajax({
			url:'hr.employee.EmployeeWorkAction$queryByEmpId.json',
			type:'post',
			dataType: "json",
			data:{
				empId: empId
			},
			success: function (data){
				$('#empWorkInfoFrm').form('clear');
				if(data) {
					$('#empWorkInfoFrm').form('load', data);
					empDetailPage.initWorkOrgCombobox(data['company'], data['dept']);
					empDetailPage.initWorkPlaceCombobox(data['workPlaceId']);
					empDetailPage.handlerWorkType(data['type']);
				} else {
					empDetailPage.initWorkOrgCombobox('', '');
					empDetailPage.initWorkPlaceCombobox('');
				}
				hnasys.util.isEditForm('empWorkInfoFrm', editable);
			}	
		});
	},
	editableWorkBtn : function(editable) {
		if(editable) {
			$('#workFrmEditBtn','#empWorkInfoFrm').hide();
			$('#workFrmSaveBtn,#workFrmRedoBtn,#workFrmCancelBtn','#empWorkInfoFrm').show();
		} else {
			$('#workFrmEditBtn','#empWorkInfoFrm').show();
			$('#workFrmSaveBtn,#workFrmRedoBtn,#workFrmCancelBtn','#empWorkInfoFrm').hide();
		}
	},
	editWorkInfo : function() {
		if(!$('#id','#empInfoFrm').val()) {
			$.messager.alert('提示','请先保存员工基本信息!');
			return;
		}
		this.editableWorkBtn(true);
		hnasys.util.isEditForm('empWorkInfoFrm', true);
	},
	cancelEmpWork : function() {
		this.loadWorkInfo($('#id','#empInfoFrm').val(), false);
	},
	submitEmpWork : function() {
		$('#workInfoEmpId','#empWorkInfoFrm').val($('#id','#empInfoFrm').val());
		$('#empWorkInfoFrm').form('submit', {
			url : 'hr.employee.EmployeeWorkAction$update.json',
			success : function(data) {
				 var cToObj = eval('(' + data + ')');
		    	 if(cToObj.flag) {
		    		 $.messager.alert('提示','保存成功!');
		    		 $('#workInfoId','#empWorkInfoFrm').val(cToObj.data['id']);
		    		 empDetailPage.editableWorkBtn(false)
		    		 hnasys.util.isEditForm('empWorkInfoFrm', false);
		    	 } else {
		    		 $.messager.alert('错误','保存失败，请联系系统管理员!', 'error');
		    	 }
			}
		});
	},
	resetEmpWork : function() {
		this.loadWorkInfo($('#id','#empInfoFrm').val(), true);
	},
	fireWorkTypeClickEvt : function() {
		$('input[name="type"]','#empWorkInfoFrm').click(function() {
			var val = $(this).val();
			empDetailPage.handlerWorkType(val);
		});
	},
	handlerWorkType : function(val) {
		if(empDetailPage.WORK_TYPE.ZSYG == val) {
			$('tr[cusType="zsygTr"]','#empWorkInfoFrm').show();
			$('.easyui-combobox','tr[cusType="zsygTr"]').combobox({required: true});
			$('.easyui-datebox','tr[cusType="zsygTr"]').datebox({required: true});
			$('tr[cusType="sxygTr"]','#empWorkInfoFrm').hide();
			$('.easyui-datebox','tr[cusType="sxygTr"]').datebox({required: false}).datebox('clear');
			$('tr[cusType="lsgTr"]','#empWorkInfoFrm').hide();
			$('.easyui-datebox','tr[cusType="lsgTr"]').datebox({required: false}).datebox('clear');
		} else if(empDetailPage.WORK_TYPE.SXYG == val) {
			$('tr[cusType="zsygTr"]','#empWorkInfoFrm').hide();
			$(':radio','tr[cusType="zsygTr"]').removeAttr('checked');
			$('.easyui-combobox','tr[cusType="zsygTr"]').combobox({required: false}).combobox('setValue','');
			$('.easyui-datebox','tr[cusType="zsygTr"]').datebox({required: false}).datebox('clear');
			$('tr[cusType="sxygTr"]','#empWorkInfoFrm').show();
			$('.easyui-datebox','tr[cusType="sxygTr"]').datebox({required: true});
			$('tr[cusType="lsgTr"]','#empWorkInfoFrm').hide();
			$('.easyui-datebox','tr[cusType="lsgTr"]').datebox({required: false}).datebox('clear');
		} else if(empDetailPage.WORK_TYPE.LSG == val) {
			$('tr[cusType="zsygTr"]','#empWorkInfoFrm').hide();
			$(':radio','tr[cusType="zsygTr"]').removeAttr('checked');
			$('.easyui-combobox','tr[cusType="zsygTr"]').combobox({required: false}).combobox('setValue','');
			$('.easyui-datebox','tr[cusType="zsygTr"]').datebox({required: false}).datebox('clear');
			$('tr[cusType="sxygTr"]','#empWorkInfoFrm').hide();
			$('.easyui-datebox','tr[cusType="sxygTr"]').datebox({required: false}).datebox('clear');
			$('tr[cusType="lsgTr"]','#empWorkInfoFrm').show();
			$('.easyui-datebox','tr[cusType="lsgTr"]').datebox({required: true});
		}
	}
};

$(function(){
	empDetailPage.initTabs();
	empDetailPage.initPage(); 
});


</script>
</head>
<body style="overflow: hidden;">
	<div id="centerTabs" class="easyui-tabs" tools="#tab-tools" style="margin:10px; position: relative; overflow: hidden;">
		<div title="基本信息" tools="#p-tools" style="overflow:auto;padding: 5px;" closable="false">
			<div id="centerTabDiv" style="overflow: auto;">
				<!-- 基本信息 -->
				<div id="infoPanel" class="easyui-panel" title="基本信息" style="overflow:hidde;padding:5px;margin-bottom:10px;position:relative;" data-options="collapsible:true,fir:true">
					<form id="empInfoFrm" method="post" novalidate enctype="multipart/form-data">
						<input id="id" name="id" type="hidden" />
						<input id="attLotId" name="attLotId" type="hidden" />
						<table cellpadding="0" cellspacing="0" class="tbcontent">	
							<tr>
								<td class="tbtitle" width="100px;">员工编号</td>
								<td><input id="code" name="code" type="text"  validType="maxLength['64']" maxlength="64" class="easyui-validatebox tinput"  /></td>
								<td class="tbtitle" width="100px;">员工姓名</td>
								<td><input id="chnName" name="chnName" type="text"  validType="maxLength['64']" maxlength="64" required="true" class="easyui-validatebox tinput"  /></td>
								<td class="tbtitle" width="100px;">身份证号</td>
								<td><input id="idNum" name="idNum" type="text"  validType="identityCode" class="easyui-validatebox tinput" required="true" onblur="empDetailPage.generateBirthday()"/></td>
								<td class="tbtitle" width="100px;">出生日期</td>
								<td><input id="birthday" name="birthday" type="text" class="easyui-datebox tinput" required="true" /></td>
								<td rowspan="4" colspan="2" style="text-align: center;">
									<img id="empPhoto" alt="" src="" style="width:148px;height:201px;">
								</td>
							</tr>
							<tr>
								<td class="tbtitle">性别</td>
								<td><input id="gen" name="gen" type="text"  editable="false" class="easyui-combobox tinput" required="true" panelHeight="auto"  /></td>
								<td class="tbtitle">英文名</td>
								<td><input id="engName" name="engName" type="text"  validType="maxLength['64']" maxlength="64" required="true" class="easyui-validatebox tinput"  /></td>
								<td class="tbtitle">籍贯</td>
								<td><input id="recruitment" name="recruitment" type="text"  validType="maxLength['32']" maxlength="32" required="true" class="easyui-validatebox tinput"  /></td>
								<td class="tbtitle">年龄</td>
								<td><span id="ageSpan"></span></td>
							</tr>
							<tr>
								<td class="tbtitle">星座</td>
								<td><input id="horoscope" name="horoscope" type="text"  editable="false" class="easyui-combobox tinput" required="true" panelHeight="auto"  /></td>
								<td class="tbtitle">员工曾用名</td>
								<td><input id="usedName" name="usedName" type="text"  validType="maxLength['64']" maxlength="64" required="true" class="easyui-validatebox tinput"  /></td>
								<td class="tbtitle">户口性质</td>
								<td><input id="household" name="household" type="text"  editable="false" class="easyui-combobox tinput"  required="true" /></td>
								<td class="tbtitle">政治面貌</td>
								<td><input id="politics" name="politics" type="text"  editable="false" class="easyui-combobox tinput"  required="true"  /></td>
							</tr>
							<tr>
								<td class="tbtitle">婚姻状况</td>
								<td><input id="marriage" name="marriage" type="text"  editable="false" class="easyui-combobox tinput" required="true" /></td>
								<td class="tbtitle">联系电话</td>
								<td><input id="phone" name="phone" type="text"  validType="maxLength['32']" maxlength="32" required="true" class="easyui-validatebox tinput"  /></td>
								<td class="tbtitle">电子邮箱</td>
								<td><input id="email" name="email" type="text" validType="email" class="easyui-validatebox tinput" required="true" /></td>
								<td class="tbtitle">健康证明有效期</td>
								<td><input id="healthValid" name="healthValid" type="text" class="easyui-datebox tinput" required="true" style="width:110px;" /></td>
							</tr>
							<tr>
								<td class="tbtitle">学历</td>
								<td><input id="education" name="education" type="text"  editable="false" class="easyui-combobox tinput"  required="true"  /></td>
								<td class="tbtitle">专业</td>
								<td><input id="major" name="major" type="text"  validType="maxLength['64']" maxlength="64" required="true" class="easyui-validatebox tinput"  /></td>
								<td class="tbtitle">毕业院校</td>
								<td><input id="graduateInstitution" name="graduateInstitution" type="text" validType="maxLength['128']" maxlength="128" required="true" class="easyui-validatebox tinput"  /></td>
								<td class="tbtitle">毕业时间</td>
								<td><input id="graduateDate" name="graduateDate" type="text" class="easyui-datebox tinput" required="true" style="width:110px;" /></td>
								<td id="uploadPhotoAttTd">
									<input id="photoAtt" width="50" type="file" name="photoAtt" onchange="empDetailPage.uploadPhoto()" />
								</td>
							</tr>
							<tr>
								<td class="tbtitle">语种</td>
								<td><input id="languageSkill" name="languageSkill" type="text"  class="easyui-combobox tinput"  required="true" /></td>
								<td class="tbtitle">已生效</td>
								<td><input id="isActive" name="isActive" type="checkbox" /></td>
								<td class="tbtitle">紧急联系人/电话</td>
								<td colspan="4"><textarea id="urgencyLinkman" name="urgencyLinkman" validType="maxLength['512']"  maxlength="512" required="true" style="width:98%; height:80px;" class="easyui-validatebox tinput"></textarea></td>
							</tr>
							<tr>
								<td class="tbtitle">档案存放地</td>
								<td colspan="8"><textarea id="fileLocation" name="fileLocation" validType="maxLength['1024']"  maxlength="1024" required="true" style="width:98%; height:80px;" class="easyui-validatebox tinput"></textarea></td>
							</tr>
							<tr>
								<td class="tbtitle">户口所在地</td>
								<td colspan="8"><textarea id="householdLocation" name="householdLocation" validType="maxLength['1024']"  maxlength="1024" required="true" style="width:98%; height:80px;" class="easyui-validatebox tinput"></textarea></td>
							</tr>
							<tr>
								<td class="tbtitle">居住地址</td>
								<td colspan="8"><textarea id="dwell" name="dwell" validType="maxLength['1024']"  maxlength="1024" required="true" style="width:98%; height:80px;" class="easyui-validatebox tinput"></textarea></td>
							</tr>
						</table>					
					</form>
					<div id="empInfoBtnDiv" style="text-align: center;margin-top:15px;">
						<span class="buttonGaps" id="frmEditBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-edit" href="javascript:;"
							onclick="empDetailPage.editInfo()">编辑</a>
						</span>
						<span class="buttonGaps" id="frmDelBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-remove" href="javascript:;"
							onclick="empDetailPage.delEmpInfo()">删除</a>
						</span>
						<span class="buttonGaps" id="frmSaveBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
							onclick="empDetailPage.submitEmpInfo()">保存</a>
						</span>
						<span class="buttonGaps" id="frmRedoBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;"
							onclick="empDetailPage.resetEmpInfo()">重置</a>
						</span>
						<span class="buttonGaps" id="frmCancelBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-cancel" href="javascript:;"
								onclick="empDetailPage.cancelEmpInfo()">取消</a>
						</span>
					</div>
				</div>
			
				<!-- 资格、职称证书 -->
				<div id="certificatePanel" class="easyui-panel" title="资格、职称证书" style="padding:5px;margin-bottom:10px;position:relative;" data-options="collapsible:true">
					<div>
						<table id="empCertGridList"></table>
					</div>
					<div id="empCertToolbar" style="padding:5px;height:auto;text-align:right;">
						<span><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="empDetailPage.editCert(-1)">新增</a></span>
					</div>
				</div>
				
				<!-- 工作信息 -->
				<div id="workInfoPanel" class="easyui-panel" title="工作信息" style="padding:5px;margin-bottom:10px;position:relative;" data-options="collapsible:true">
					<form id="empWorkInfoFrm" method="post" novalidate>
						<input id="workInfoId" name="id" type="hidden" />
						<input id="workInfoEmpId" name="employeeId" type="hidden" />
						<table cellpadding="0" cellspacing="0" class="tbcontent">	
							<tr>
								<td class="tbtitle" width="100px;">入职公司</td>
								<td><input id="company" name="company" type="text"  editable="false" class="easyui-combobox tinput" required="true" style="width:130px;"  /></td>
								<td class="tbtitle" width="100px;">入职部门</td>
								<td><input id="dept" name="dept" type="text"  editable="false" class="easyui-combobox tinput" required="true" style="width:130px;" /></td>
								<td class="tbtitle" width="100px;">入职岗位</td>
								<td><input id="post" name="post" type="text"  editable="false" class="easyui-combobox tinput" required="true" style="width:130px;"  /></td>
								<td class="tbtitle" width="100px;">工作地点</td>
								<td><input id="workPlaceId" name="workPlaceId" type="text"  editable="false" class="easyui-combobox tinput" required="true" style="width:130px;"  /></td>
							</tr>
							<tr>
								<td class="tbtitle">入职日期</td>
								<td><input id="entrantDate" name="entrantDate" type="text" class="easyui-datebox tinput" required="true" style="width:130px;" /></td>
								<td class="tbtitle">能力级别</td>
								<td><input id="capacityLevel" name="capacityLevel" type="text"  editable="false" class="easyui-combobox tinput" required="true" style="width:130px;" /></td>
								<td class="tbtitle">员工类型</td>
								<td colspan="3">
									<input name="type" value="10" type="radio" />正式员工&nbsp;&nbsp;
									<input name="type" value="20" type="radio" />实习员工&nbsp;&nbsp;
									<input name="type" value="30" type="radio" />临时工
								</td>
							</tr>
							<tr cusType="zsygTr" style="display: none;">
								<td class="tbtitle" rowspan="2">劳动合同</td>
								<td class="tbtitle">试用期</td>
								<td>
									<input name="trialPeriod" value="2" type="radio" />有&nbsp;&nbsp;
									<input name="trialPeriod" value="1" type="radio" />无
								</td>
								<td class="tbtitle">合同期限/年</td>
								<td><input id="contractDeadline" name="contractDeadline" type="text" editable="false" class="easyui-combobox" style="width:90px;"/></td>
								<td class="tbtitle">合同类型</td>
								<td colspan="2"><input id="contractType" name="contractType" type="text" editable="false" class="easyui-combobox" style="width:130px;" /></td>
							</tr>
							<tr cusType="zsygTr" style="display: none;">
								<td class="tbtitle">合同期限</td>
								<td colspan="3">
									<input id="contractStartDate" name="contractStartDate" type="text" class="easyui-datebox tinput" validType="dateCompare['contractStartDate','contractEndDate','合同开始时间不能大于结束时间','empWorkInfoFrm']" style="width:130px;" />
									<label>&nbsp;到&nbsp;</label>
									<input id="contractEndDate" name="contractStartDate" type="text" class="easyui-datebox tinput" style="width:130px;" />
								</td>
								<td class="tbtitle">试用期结束日期</td>
								<td colspan="2"><input id="trialDeadline" name="trialDeadline" type="text" class="easyui-datebox tinput" style="width:130px;" /></td>
							</tr>
							<tr cusType="sxygTr" style="display: none;">
								<td class="tbtitle">实习期限</td>
								<td colspan="7">
									<input id="practiceStartDate" name="practiceStartDate" type="text" class="easyui-datebox" style="width:130px;" validType="dateCompare['practiceStartDate','practiceEndDate','实习开始时间不能大于结束时间','empWorkInfoFrm']"/>
									<label>&nbsp;到&nbsp;</label>
									<input id="practiceEndDate" name="practiceEndDate" type="text" class="easyui-datebox" style="width:130px;"/>
								</td>
							</tr>
							<tr cusType="lsgTr" style="display: none;">
								<td class="tbtitle">兼职期限</td>
								<td colspan="7">
									<input id="parttimeStartDate" name="parttimeStartDate" type="text" class="easyui-datebox tinput" style="width:130px;" validType="dateCompare['parttimeStartDate','parttimeStartDate','兼职开始时间不能大于结束时间','empWorkInfoFrm']"/>
									<label>&nbsp;到&nbsp;</label>
									<input id="parttimeEndDate" name="parttimeEndDate" type="text" class="easyui-datebox tinput" style="width:130px;"/>
								</td>
							</tr>
						</table>
						<div style="text-align: center;margin-top:15px;">
							<span class="buttonGaps" id="workFrmEditBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-edit" href="javascript:;"
									onclick="empDetailPage.editWorkInfo()">编辑</a>
							</span>
							<span class="buttonGaps" id="workFrmSaveBtn" style="display:none;"><a class="easyui-linkbutton" iconCls="icon-save" href="javascript:;"
								onclick="empDetailPage.submitEmpWork()">保存</a>
							</span>
							<span class="buttonGaps" id="workFrmRedoBtn" style="display:none;"><a class="easyui-linkbutton" iconCls="icon-undo" href="javascript:;"
								onclick="empDetailPage.resetEmpWork()">重置</a>
							</span> 
							<span class="buttonGaps" id="workFrmCancelBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-cancel" href="javascript:;"
								onclick="empDetailPage.cancelEmpWork()">取消</a>
							</span>
						</div>
					</form>
				</div>
				
				<!-- 家属信息 -->
				<div id="relationInfoPanel" class="easyui-panel" title="家属信息" style="padding:5px;margin-bottom:10px;position:relative;" data-options="collapsible:true">
					<div>
						<table id="empRelationGridList"></table>
					</div>
					<div id="empRelationToolbar" style="padding:5px;height:auto;text-align:right;">
						<span><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="empDetailPage.editRelation(-1)">新增</a></span>
					</div>
				</div>
			</div>
		</div>
		<div title="薪资福利" tools="#p-tools" style="overflow:auto;" closable="false"></div>
		<div title="补充信息" tools="#p-tools" style="overflow:auto;" closable="false"></div>
	</div>			
	
	<div id="empCertWin" class="easyui-window" modal="true" inline="false"
			collapsible="false" minimizable="false" maximizable="false"
			resizable="false" closed="true" title="资格、职称证书"
			style="height: 250px; width: 600px; display: none;">
		<div style="margin:10px;overflow:hidden;">
			<form id="empCertFrm" method="post" novalidate>
				<input id="certId" name="id" type="hidden" />
				<input id="certEmployeeId" name="employeeId" type="hidden" />
				<table cellpadding="0" cellspacing="0" class="tbcontent">	
					<tr>
						<td class="tbtitle" width="30%">证书名称</td>
						<td><input type="text" name="name" class="easyui-validatebox tinput" required="true" /></td>
					</tr>
					<tr>
						<td class="tbtitle">签发日期</td>
						<td><input type="text" id="certSignDate" name="signDate" class="easyui-datebox" required="true" validType="dateCompare['certSignDate','certExpDate','签发开始时间不能大于结束时间','empCertFrm']"/></td>
					</tr>
					<tr>
						<td class="tbtitle">有效期至</td>
						<td><input type="text" id="certExpDate" name="expDate" class="easyui-datebox" /></td>
					</tr>
				</table>
				<div style="text-align: center;margin-top:15px;">
					<span class="buttonGaps" id="certFrmEditBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-edit" href="javascript:;"
							onclick="empDetailPage.editCertInfo()">编辑</a>
					</span>
					<span class="buttonGaps" id="certFrmDelBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-remove" href="javascript:;"
						onclick="empDetailPage.delCert('')">删除</a>
					</span>
					<span class="buttonGaps" id="certFrmSaveBtn" style="display:none;"><a class="easyui-linkbutton" iconCls="icon-save" href="javascript:;"
						onclick="empDetailPage.submitEmpCert()">保存</a>
					</span>
					<span class="buttonGaps" id="certFrmRedoBtn" style="display:none;"><a class="easyui-linkbutton" iconCls="icon-undo" href="javascript:;"
						onclick="empDetailPage.resetEmpCert()">重置</a>
					</span> 
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-cancel" href="javascript:;"
						onclick="$('#empCertWin').window('close')">取消</a>
					</span>
				</div>					
			</form>
		</div>		
	</div>
	
	<div id="empRelationWin" class="easyui-window" modal="true" inline="false"
			collapsible="false" minimizable="false" maximizable="false"
			resizable="false" closed="true" title="家属信息"
			style="height: 250px; width: 600px; display: none;">
		<div style="margin:10px;overflow:hidden;">
			<form id="empRelationFrm" method="post" novalidate>
				<input id="relationId" name="id" type="hidden" />
				<input id="relationEmployeeId" name="employeeId" type="hidden" />
				<table cellpadding="0" cellspacing="0" class="tbcontent">	
					<tr>
						<td class="tbtitle" width="30%">姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox tinput" required="true" /></td>
					</tr>
					<tr>
						<td class="tbtitle">与本人关系</td>
						<td><input id="relation" type="text" name="relation" class="easyui-combobox" required="true" style="width:130px;"/></td>
					</tr>
					<tr>
						<td class="tbtitle">联系电话</td>
						<td><input type="text" name="phone" class="easyui-validatebox tinput" required="true"/></td>
					</tr>
				</table>
				<div style="text-align: center;margin-top:15px;">
					<span class="buttonGaps" id="relationFrmEditBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-edit" href="javascript:;"
							onclick="empDetailPage.editRelationInfo()">编辑</a>
					</span>
					<span class="buttonGaps" id="relationFrmDelBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-remove" href="javascript:;"
						onclick="empDetailPage.delRelation('')">删除</a>
					</span>
					<span class="buttonGaps" id="relationFrmSaveBtn" style="display:none;"><a class="easyui-linkbutton" iconCls="icon-save" href="javascript:;"
						onclick="empDetailPage.submitEmpRelation()">保存</a>
					</span>
					<span class="buttonGaps" id="relationFrmRedoBtn" style="display:none;"><a class="easyui-linkbutton" iconCls="icon-undo" href="javascript:;"
						onclick="empDetailPage.resetEmpRelation()">重置</a>
					</span> 
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-cancel" href="javascript:;"
						onclick="$('#empRelationWin').window('close')">取消</a>
					</span>
				</div>					
			</form>
		</div>		
	</div>
</body>
</html>