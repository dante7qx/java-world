<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>通告信息</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<script type="text/javascript">
  $(function(){
	var announceId = '${param.code}';
		if(announceId != ''){
			$('#opt').attr("value","update");
			viewData(announceId);
		}else{
			$('#opt').attr("value","add");
			epolleo.util.isEditForm('addAnnounceForm', true);
			epolleo.util.formButtonState('addAnnounceButton', 'add', true);
		}
	});
		function delAnnounceInfo(){
			 $.messager.confirm('确认','确认要删除该信息吗？',function(ok){
				 if(ok){
					 $.ajax({
	                   type: 'POST',
	                   url: "bp.announce.AnnounceInfoAction$delete.json",
	                   data: {
	                       'id': $('#id').val()
	                   },
	                   success: function(data) {
	                	    var cToObj = eval('(' + data + ')');
	                        $('#AnnounceInfoDatagrid').datagrid('reload');
	                        $.messager.alert('提示',"已删除该通告!");
	                        epolleo.util.closeWin('AnnounceInfoWin');
	                   },
	                   error: function(j, status, error) {
	                        $.messager.alert('提示',error);
	                   }
		                });
				 }
			 });
		}
		function infoSubmit(){
			 var opt = $('#opt').val();
			 var url = "bp.announce.AnnounceInfoAction$save.json";
			 if(opt == 'update') {
				 url = "bp.announce.AnnounceInfoAction$update.json";
			 }
				 $('#addAnnounceForm').form('submit',{  
					     url: url,  
					     onSubmit: function(){ 
					    	 return $(this).form('validate');
					      },
					      success:function(data){ 
					    	  var cToObj=eval('(' + data + ')'); 
					    	  if(typeof(cToObj) == 'string'){
									$.messager.alert('错误', data);
									return;
								}
					    	  $.messager.alert('提示','通告信息保存成功!');
					    	  $('#AnnounceInfoDatagrid').datagrid('reload');
					    	  epolleo.util.closeWin('AnnounceInfoWin');
					      },
					      error: function(j, status, error) {
	                          $.messager.alert('提示',error);
	                     }
			      }); 
		}
		//重置之后将隐藏域值还原
		function formReset(){
			var opt = $('#opt').val();
			if(opt == 'add'){
					$('#addAnnounceForm').form('clear');
					$('#opt').attr("value","add");
			}else if(opt == 'update'){
				var id = $('#id').val();
				$('#addAnnounceForm').form('clear');
				$('#opt').attr("value","update");
				viewData(id,true);
			 }
		}
		//查看详细信息
		function viewData(code,isbutton) {
			if(typeof(isbutton) == 'undefined'){
				isbutton = false;
			}
			$.ajax({
				type : "POST",
				url : "bp.announce.AnnounceInfoAction$queryById.json?id="+code,
				dataType : "json",
				//async : false,
				success : function(data) {
					if (data != null) {
						$('#addAnnounceForm').form('clear');
						$('#addAnnounceForm').form('load', data);
						$('#opt').attr("value","update");
						$('#id').val(data.id);
					}
					if(!isbutton){
						epolleo.util.isEditForm('addAnnounceForm', false);
						epolleo.util.formButtonState('addAnnounceButton', 'view', true);
						}
				}
			});
		}
		 //编辑
	    function editButtonClick() {
	    	$('#opt').val('update');
	    	epolleo.util.isEditForm('addAnnounceForm', true);
	    	epolleo.util.formButtonState('addAnnounceButton', 'edit', true);
	    }
</script>
</head>
<body>
		<div id="M_AnnounceInfo"  style="margin:10px;overflow:hidden;">
				<form id="addAnnounceForm" method="post" novalidate>
					<table cellpadding="0" cellspacing="0" class="tbcontent">	
					    <input id="opt" type="hidden" name="opt"/>
					    <input id="id" type="hidden" name="id"/>
						<tr>
							<td class="tbtitle"  width="120">生效日期</td>
							<td width="150"><input id="effDate" type="text" class="easyui-datebox"  name="effDate" required=true/></td>
							<td class="tbtitle"  width="120">失效日期</td>
							<td width="150"><input id="expDate" type="text" name="expDate" class="easyui-datebox"/></td>
						</tr>
						<tr>
							<td class="tbtitle">内容</td>
							<td colspan="3">
								<textarea id="content" style="width: 500px; height: 120px;overflow-y:auto;" class="easyui-validatebox tinput"
							        name="content"  maxlength="512" required=true></textarea>
							</td>
						</tr>
					</table>					
				</form>
				<div id="addAnnounceButton" style="text-align: center;margin-top:15px;">
					<w:func id="bp.announce.update">
					<span class="buttonGaps"><a href="#" class="easyui-linkbutton" iconCls="icon-edit" href="javascript:;"
							onclick="editButtonClick()" name="formEdit">编辑
						</a>
					</span>
					</w:func>
					<w:func id="bp.announce.delete">
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-remove" href="javascript:;" name="formDel"
									onclick="delAnnounceInfo();"> 删除</a>
					</span>
					</w:func>
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-save" href="javascript:;"
									onclick="infoSubmit();" name="formSave"> 保存</a></span>
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;"
									onclick="formReset()" name="formReset"> 重置</a></span>
					<span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" href="javascript:;"
									onclick="epolleo.util.closeWin('AnnounceInfoWin');"> 取消</a></span>
				</div>
			</div>
</body>
</html>