<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
var empOtherPage = {
	submitFlag : false,
	paramEmpId : '',
	initPage : function() {
		$('#empOtherDiv').height($(window).height() - 70);
		this.paramEmpId = '${param.empId}';
		this.loadData(false);
	},
	loadData : function(editable) {
		this.editableOtherBtn(editable);
		$.ajax({
			url:'hr.employee.EmployeeOtherAction$queryByEmpId.json',
			type:'post',
			dataType: "json",
			data:{
				empId: empOtherPage.paramEmpId
			},
			success: function (data){
				$('#empOtherFrm').form('clear');
				if(data) {
					$('#empOtherFrm').form('load', data);
					hnasys.att.loadUploadFiles(data['attLotId'], editable, 'empOtherTable','empOtherFrm');
				} else {
					hnasys.att.controlUploadFileBtn(false, 'empOtherTable','empOtherFrm');
				}
				hnasys.util.isEditForm('empOtherFrm', editable);
			}	
		});
	},
	editableOtherBtn : function(editable) {
		if(editable) {
			$('#empOtherFrmEditBtn','#empOtherDiv').hide();
			$('#empOtherFrmSaveBtn,#empOtherFrmRedoBtn,#frmCancelBtn','#empOtherDiv').show();
		} else {
			$('#empOtherFrmEditBtn','#empOtherDiv').show();
			$('#empOtherFrmSaveBtn,#empOtherFrmRedoBtn','#empOtherDiv').hide();
		}
	},
	editOther : function() {
		hnasys.util.isEditForm('empOtherFrm', true);
		hnasys.att.controlUploadFileBtn(true, 'empOtherTable','empOtherFrm');
		this.editableOtherBtn(true);
	},
	submit : function() {
		if(this.submitFlag) {
			return;
		}
		this.submitFlag = true;
		$('#otherEmpId','#empOtherFrm').val(empOtherPage.paramEmpId);
		$('#empOtherFrm').form('submit', {
			url : 'hr.employee.EmployeeOtherAction$update.json',
			success : function(data) {
				 var cToObj = eval('(' + data + ')');
		    	 if(!cToObj.flag) {
		    		 $.messager.alert('错误', '附件上传失败! '+ cToObj, 'error');
		    	 } else if(cToObj.flag == 1) {
		    		 $.messager.alert('提示','保存成功!');
		    		 empOtherPage.loadData(false);
		    	 } else if(cToObj.flag == 2){
		    		 $.messager.alert('错误','保存失败，请联系系统管理员!', 'error');
		    	 }
		    	 empOtherPage.submitFlag = false;
			}
		});
	},
	reset : function() {
		this.loadData(true);
	}
		
};
$(function() {
	empOtherPage.initPage();
});
</script>
<title>补充信息</title>
</head>
<body>
	<div id="empOtherDiv" style="margin:10px;overflow:hidden;">
		<form id="empOtherFrm" method="post" novalidate enctype="multipart/form-data">
			<input id="otherId" name="id" type="hidden" />
			<input id="otherLotId" name="attLotId" type="hidden" />
			<input id="otherEmpId" name="employeeId" type="hidden" />
			<table cellpadding="0" cellspacing="0" class="tbcontent">	
				<tr>
					<td class="tbtitle" width="20%">附件</td>
					<td>
						<input id="deletedFileIds" name="deletedFileIds" type="hidden"/>
						<span id="addFileSpan"><a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="hnasys.att.addFile('empOtherTable','empOtherFrm')">添加</a></span>
						<table id="empOtherTable" cellpadding="0" cellspacing="0" class="tbcontent">
							<tr type="uploadFile">
								<td>
									<input name="attatchFile" type="file" style="width: 70%;"/>
									<a href="javascript:;" class="easyui-linkbutton" iconCls="icon-close" plain="true" onclick="hnasys.att.deleteAttach(this, 'empOtherTable','empOtherFrm')"></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="tbtitle">备注</td>
					<td><textarea name="remark" validType="maxLength['1024']"  maxlength="1024" style="width:99%; height:100px;" class="easyui-validatebox tinput"></textarea></td>
				</tr>
			</table>
		</form>
		<div style="text-align: center;margin-top:15px;">
			<span class="buttonGaps" id="empOtherFrmEditBtn" style="display:none;"><a class="easyui-linkbutton" iconCls="icon-edit" href="javascript:;"
				onclick="empOtherPage.editOther();">编辑</a>
			</span> 
			<span class="buttonGaps" id="empOtherFrmSaveBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
				onclick="empOtherPage.submit()">保存</a>
			</span>
			<span class="buttonGaps" id="empOtherFrmRedoBtn" style="display:none;"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;"
				onclick="empOtherPage.reset()">重置</a>
			</span>
			<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
				onclick="window.close();">取消</a>
			</span>
		</div>
	</div>
</body>
</html>