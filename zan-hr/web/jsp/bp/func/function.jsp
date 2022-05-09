<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>系统功能</title>
	<%@ include file="/jsp/common/common.jsp"%>	
	<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
	<style>
		html,body{height:100%;}
	</style>
	<script type="text/javascript">					
		$(function(){
			$('#funcTree').tree({ 
	            url: 'bp.function.FunctionAction$query.json', 
	            onClick: function (node) {     
	            	$('#funcForm').form("clear");
	            	$('#funcForm').show();
            		var pnode = $(this).tree('getParent', node.target);
            		if(pnode!=null){
            			$("#spanparent").text(pnode.text);
            			$("#parent").val(pnode.id);
            		}else{
            			$("#spanparent").text("");
            			$("#parent").val("-1");
            		}
            		
            		var selNode= $(this).tree("getSelected");
            		getFunc(selNode.id);
            		$("#funcName").val(selNode.text);
            		$("#spFuncCode").text(selNode.id);
                	$("#funcCode").val(selNode.id);
	            }
	        });
			
			//resize scroll of panel
			$("#navtree").panel({
				height:$("#funcPanel").panel("options").height - 65
			});	
			
			//resize true
			$("#funcPanel").panel({
				onResize:function(width,height){
					$("#navtree").panel("resize",{width:width-7,height:$("#funcPanel").panel("options").height - 65});
				}
			});
			
		})
		
		function doSearch(){
			$('#funcTree').tree('reload');
		}
				
		function getFunc(funcCode){
			$.ajax({
				type: "POST",
				url: "bp.function.FunctionAction$get.json",
				dataType:"json",
				data: "funcCode="+funcCode,
				success: function(msg) {
					if(msg!=null){
						var obj=msg[0];
						if(obj){
							$("#funcNote").val(obj.funcNote?obj.funcNote:"");							
							$("#funcOrder").val(obj.funcOrder?obj.funcOrder:"");
						}						
					}
				}
			});	
    	}
		
		function synchFunc(){
			$.ajax({
				type: "POST",
				url: "bp.function.FunctionAction$sync.json",
				success: function(msg) {
					$.messager.alert('提示',"同步完成！");
					doSearch();
				},
				error: function(msg) {
					$.messager.alert('提示',"同步失败！");
				}
			});	
		}
    	
    	function saveForm(){
    		$('#funcForm').form("submit",{   
			    url:"bp.function.FunctionAction$updateNote.json",   
			    success:function(data){   
			        if(data){
			        	 doSearch();
			         }
			    }   
			});
    	}
    	
    	function deleteFunc(){
    		var node= $("#funcTree").tree("getSelected");
    		if(!node){
    			$.messager.alert('提示',"请选择一行数据后再进行此操作！");
    			return ;
    		}
    		var children= $("#funcTree").tree("getChildren",node.target);
    		if(children.length>0){
    			$.messager.alert('提示',"请删除子功能后重试！");
    			return;
    		}

    		$.messager.confirm('确认','确定要删除吗？',function(r){
	    		if(r){	
	    			if(node.id && !node.attributes.state){
			    		$.ajax({
							type: "POST",
							url: "bp.function.FunctionAction$delete.json",
							data: {
								id:node.id,
			    				funcCode:node.id
			    			},
							success: function(msg) {
								$.messager.alert('提示',"删除成功！");
								$("#funcTree").tree("remove",node.target);
								//doSearch();
							},
							error: function(msg) {
								$.messager.alert('提示',"此功能正在被使用中，暂时不能删除！");
							}
						});
	    			}else{
	    				$.messager.alert('提示',"选中的功能为系统功能，不能删除！");
	    				return;
	    			}
	    		}
    		});
    	}
    	function resetForm(){
    		var selected = $('#funcTree').tree('getSelected');
    		var pnode = $('#funcTree').tree('getParent', selected.target);
    		if(pnode!=null){
    			$("#spanparent").text(pnode.text);
    			$("#parent").val(pnode.id);
    		}else{
    			$("#spanparent").text("");
    			$("#parent").val("-1");            			
    		}
    		getFunc(selected.id);
    		$("#funcName").val(selected.text);
    		$("#spFuncCode").text(selected.id);
        	$("#funcCode").val(selected.id);
    	}
    	
	</script>
</head>
<body>
	<table id="tb" class="body_layout" cellspacing="0" >
		<tr>
			<td valign="top" class="body_layout_left">
				<div id="cc" class="easyui-layout" fit=true>
					<div id="funcPanel" region="west" split="true"  style="width:200px">
						<div class="easyui-panel"  title="功能管理" id="navTitle" fit="true" border="false">
							<div id="toolbar">
								<div class="body_layout_bar">
									 <w:func id="bp.function.sync">
									<a href="#" class="easyui-linkbutton" iconCls="icon-load" plain="true" title="同步" onclick="synchFunc()"></a>
									</w:func>
									<w:func id="bp.function.delete">
									<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" title="删除" onclick="deleteFunc()"></a> 
									</w:func>
								</div>
							</div>
							<div class="easyui-panel" id="navtree" border="false" >
								<ul id="funcTree"  animate="true"></ul>
							</div>
						</div>
					</div>
					<div id="funcDetail" region="center" title="功能明细">
						<form method="post" id="funcForm" novalidate style="display:none;">
					   		<table cellpadding="0" cellspacing="0" class="tbcontent" style="margin:10px;width:500px;">
								<tr>
									<td class="tbtitle" style="width:40%">功能编号</td>
									<td><span id="spFuncCode"></span><input type="hidden" id="funcCode" name="funcCode"></td>
								</tr>
								<tr>
									<td class="tbtitle">功能名称</td>
									<td><input id="funcName" name="funcName" type="text" class="easyui-validatebox tinput" required="true"/></td>
								</tr>
								<tr>
									<td class="tbtitle">父节点</td>
									<td><span id="spanparent"></span></td>
								</tr>
								<tr>
									<td class="tbtitle">排序</td>
									<td><input id="funcOrder" name="funcOrder" type="text" value="0" class="easyui-validatebox tinput" required="true" validType="number"/></td>
								</tr>
								<tr>
									<td class="tbtitle">备注</td>
									<td><textarea id="funcNote" name="funcNote" style="width:250px;height:100px;" maxlength="128"  class="tinput"></textarea></td>
								</tr>			
								<tr>
									<td colspan="2" align="center">
										<span class="buttonGaps"><a id="link_task_save" href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveForm()">保存</a></span><span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-undo" href="javascript:;" onclick="resetForm()"> 重置</a></span>
									</td>
								</tr>
							</table>
					   </form>
				   </div>
				 </div>
			</td>
		</tr>
	</table>
</body>
</html>