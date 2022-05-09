<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>薪资福利</title>
<script type="text/javascript">
var empBenefitPage = {
	paramEmpId : '',
	initPage : function() {
		$('#benefitDiv').height($(window).height() - 70);
		this.paramEmpId = '${param.empId}';
		this.loadData(false);
	},
	loadData : function(editable) {
		this.editableBenefitBtn(editable);
		$.ajax({
			url:'hr.employee.EmployeeBenefitAction$queryByEmpId.json',
			type:'post',
			dataType: "json",
			data:{
				empId: empBenefitPage.paramEmpId
			},
			success: function (data){
				$('#empBenefitFrm').form('clear');
				if(data) {
					$('#empBenefitFrm').form('load', data);
				}
				hnasys.util.isEditForm('empBenefitFrm', editable);
			}	
		});
	},
	editableBenefitBtn : function(editable) {
		if(editable) {
			$('#benefitFrmEditBtn','#benefitDiv').hide();
			$('#benefitFrmSaveBtn,#benefitFrmRedoBtn,#frmCancelBtn','#benefitDiv').show();
		} else {
			$('#benefitFrmEditBtn','#benefitDiv').show();
			$('#benefitFrmSaveBtn,#benefitFrmRedoBtn','#benefitDiv').hide();
		}
	},
	editBenefit : function() {
		hnasys.util.isEditForm('empBenefitFrm', true);
		this.editableBenefitBtn(true);
	},
	submit : function() {
		$('#benefitEmpId','#empBenefitFrm').val(empBenefitPage.paramEmpId);
		$('#empBenefitFrm').form('submit', {
			url : 'hr.employee.EmployeeBenefitAction$update.json',
			success : function(data) {
				 var cToObj = eval('(' + data + ')');
		    	 if(cToObj.flag) {
		    		 $('#benefitId','#empInfoFrm').val(cToObj.data['id']);
		    		 empBenefitPage.editableBenefitBtn(false);
		    		 hnasys.util.isEditForm('empBenefitFrm', false);
		    		 $.messager.alert('提示','保存成功!');
		    	 } else {
		    		 $.messager.alert('错误','保存失败，请联系系统管理员!', 'error');
		    	 }
			}
		});
	},
	reset : function() {
		this.loadData(true);
	}
		
};
$(function() {
	empBenefitPage.initPage();
});
</script>
</head>
<body>
	<div id="benefitDiv" style="margin:10px;overflow:hidden;">
		<form id="empBenefitFrm" method="post" novalidate>
			<input id="benefitId" name="id" type="hidden" />
			<input id="benefitEmpId" name="employeeId" type="hidden" />
			<table cellpadding="0" cellspacing="0" class="tbcontent">	
				<tr>
					<td class="tbtitle">税前工资</td>
					<td><input id="preTaxSalary" name="preTaxSalary" type="text" validType="priceNumber" required="true" class="easyui-validatebox tinput"  /></td>
					<td class="tbtitle">试用期工资</td>
					<td><input id="trialPeriodSalary" name="trialPeriodSalary" type="text" validType="priceNumber" required="true" class="easyui-validatebox tinput"  /></td>
					<td class="tbtitle">工资卡开户行</td>
					<td><input id="bankAccount" name="bankAccount" type="text"  validType="maxLength['64']" maxlength="64" required="true" class="easyui-validatebox tinput" style="width:170px;" /></td>
					<td class="tbtitle">工资卡号</td>
					<td><input id="salaryCardNo" name="salaryCardNo" type="text"  validType="maxLength['64']" maxlength="64" required="true" class="easyui-validatebox tinput" style="width:170px;" /></td>
				</tr>
				<tr>
					<td class="tbtitle">住房公积金缴存基数</td>
					<td><input id="providentFundDepositBase" name="providentFundDepositBase" type="text" validType="priceNumber" required="true" class="easyui-validatebox tinput" /></td>
					<td class="tbtitle">保险缴存基数</td>
					<td><input id="insureDepositBase" name="insureDepositBase" type="text" validType="priceNumber" required="true" class="easyui-validatebox tinput" /></td>
					<td class="tbtitle">起缴日期</td>
					<td colspan="3"><input id="payDate" name="payDate" type="text" required="true" class="easyui-datebox"  /></td>
				</tr>
			</table>					
		</form>
		<div style="text-align: center;margin-top:15px;">
			<span class="buttonGaps" id="benefitFrmEditBtn" style="display:none;"><a class="easyui-linkbutton" iconCls="icon-edit" href="javascript:;"
				onclick="empBenefitPage.editBenefit();">编辑</a>
			</span> 
			<span class="buttonGaps" id="benefitFrmSaveBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
				onclick="empBenefitPage.submit()">保存</a>
			</span>
			<span class="buttonGaps" id="benefitFrmRedoBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;"
				onclick="empBenefitPage.reset()">重置</a>
			</span>
			<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
				onclick="window.close();">取消</a>
			</span>
		</div>
	</div>
</body>
</html>