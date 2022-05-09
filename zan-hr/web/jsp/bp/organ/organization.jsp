<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>组织机构管理</title>
<%@ include file="/jsp/common/common.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/webx" prefix="w" %>
<style>
	html,body{height:100%;}
</style>
<script>
    $(function() {
        function loadData() {
            $('#orgTypeId').combobox({
                url:'bp.org.OrgAction$findTypeList.json',
                valueField:'orgTypeId',
                textField:'orgType',
                width:150
            });
            $('#locId').combobox({
                url:'bp.location.LocAction$getEnableLoc.json',
                valueField:'locId',
                textField:'locName',
                width:150
            });
        }
        function clearDefault() {
            $('#orgTypeId').combobox('setValues', '');
            $('#locId').combobox('setValues', '');
        }

        function addGrid(parentId, title) {
            $('#childGrid').datagrid({
                title: title+'子列表',
                fitColumns: true,
                rownumbers:true,
                nowrap: false,
                striped: true,
                collapsible: false,
                remoteSort: false,
                url: 'bp.org.OrgAction$queryChild.json',
                queryParams: {parentId:parentId}, //查询条件
                idField: 'orgId',
                columns: [[
                    {field:'orgId',title:'机构ID',width:30},
                    {field:'orgName',title:'组织机构',width:100},
                    {field:'orgCode',title:'机构标识码',width:40},
                    {field:'fullId',title:'机构全号',width:90},
                    {field:'fullCode',title:'机构全码',width:80},
                    {field:'orgType',title:'组织机构类型',width:50},
                    {field:'enable',title:'是否有效',width:30,align:'center',formatter : function(value, rowIndex) {
    					var enable = "否"
                    	if (value == true) {
                    		enable = '是';
    					}
    					if (value == false) {
    						enable = '否';
    					}
    					return "<span>" + enable + "</span>";
    				}},
                    {field:'orgPinYin',title:'拼音缩写',width:30,align:'center'},
                    {field:'showOrder',title:'显示顺序',width:30,align:'center'}
                ]],
                pagination:false
            });
            $("#childGrid").parents(".datagrid").show();
        }

        var treeId = '#organizationTree';							
        $(treeId).tree({
            animate: true,
            url: 'bp.org.OrgAction$query.json?root=true&lazy=true',
            onClick: function(node) {
                if (node.id == -1) {
                    $("#content").hide();
                } else {
                    $('#editForm').form('clear');
                    var pnode = $(this).tree('getParent', node.target);
                    $("#txtparent").text(pnode.text);
                    //显示顺序
                    loadData();
                    
                    loadOrgForm(node.id);
                    //$('#editForm').form('load',node.attributes);
                   
                    $("#opt").val('update');
                    $("#content").show();
                    var child = $(this).tree('getChildren', node.target);
                    if (child != null ) {
                        addGrid(node.attributes.orgId, node.attributes.orgName);
                    }
                }
            },
            onContextMenu: function (e, node) {
                e.preventDefault();
                $(treeId).tree('select', node.target);
                var position = { left: e.pageX, top: e.pageY };
                if (node.id == -1) {
                   $('#rootContextMenu').menu('show', position);
                } else {
                    $('#childContextMenu').menu('show', position);
                }
            },
            onBeforeExpand: function(node) {
            	$(this).tree("options").url = "bp.org.OrgAction$query.json?lazy=true&parentId=" + node.id;
            }
        });
        
        
        function loadOrgForm(orgId){
			$.ajax({
				type: "POST",
				url: "bp.org.OrgAction$get.json",
				dataType:"json",
				data: "orgId="+orgId,
				success: function(msg) {
					if(msg!=null){
						//var obj=msg[0];
						$('#editForm').form('load',msg);
						if(msg.fullId){
							$('#txtfullId').text(msg.fullId);
						}else{
							$('#txtfullId').text("");
						}
						if(msg.locId == 0){
							$('#locId').combobox('setValues', '');
						}
					}
				}
			});	
    	}
        
        //点击同步
		function synchOrg(event){
			$.ajax({
				type: "POST",
				url: "bp.org.orgAction$sync.json",
				success: function(msg) {
					$.messager.alert('提示',"同步完成！");
				},
				error: function(msg) {
					$.messager.alert('提示',"同步失败！");
				}
			});	
		}
        $("[iconCls='icon-load']").click(synchOrg);

        // 点击增加
        function add(event) {
            var node = $(treeId).tree('getSelected');
            if (node == null) {
                $.messager.alert('提示', '请选择相应节点后再进行此操作！');
            } else {
                $('#editForm').form('clear');
                $("#opt").val('add');
                $("#txtparent").text(node.text);
                if (node.id != '-1') {
                    $("#parentId").val(node.id);
                }
                loadData();
                clearDefault();
                $("#content").show();
                $("#childGrid").parents(".datagrid").hide();
            }
        }
        $("[iconCls='icon-add']").click(add);

        function move(event) {
            var clickNode = $(treeId).tree('getSelected');
            if (!clickNode) {
                $.messager.alert('提示','请选择相应节点后再进行此操作！');
            } else if(clickNode.id == -1) {
                $.messager.alert('提示','该节点无此操作！');
            } else if (clickNode.id != null) {
                var nodeId = clickNode.attributes.orgId;
                $('#moveWindow').window('open');
                $('#moveTree').tree({
                    animate: true,
                    url: 'bp.org.OrgAction$query.json?root=true&root=true&lazy=true&excludeId='+nodeId,
                    onBeforeExpand: function(node) {
                    	$(this).tree("options").url = "bp.org.OrgAction$query.json?lazy=true&excludeId=" + nodeId + "&parentId=" + node.id;
                    }
                });
            }
        }
        $("[iconCls='icon-move']").click(move);

        function updateParent(event) {
        	//clickNode表示移动树上选中节点
            var clickNode = $('#moveTree').tree('getSelected');
            //orgNode表示tree上选中节点
            var orgNode = $(treeId).tree('getSelected');
            //treeParent表示树上选中节点的父节点
            var treeParent = $(treeId).tree('getParent', orgNode.target);
            //clickNode在orgTree上实际对应的节点
            var treeNode = $(treeId).tree('find',clickNode.attributes.orgId);
            if (clickNode == null || orgNode == null) {
                $.messager.alert('提示','请选择相应节点后再进行此操作！');
            } else {
                var parentId = clickNode.attributes.orgId;
                var orgId = orgNode.attributes.orgId;
                if (treeParent.attributes.orgId == parentId
                        || (orgNode.attributes.parentId == null && parentId == '-1')) {
                    $.messager.alert('提示','请选择其它节点！');
                } else {
                    var params = { orgId : orgId };
                    if (parentId != "-1") {
                        params.parentId = parentId;
                    }
                    $.ajax({
                        url: "bp.org.OrgAction$updateParentId.json",
                        data: params,
                        type: 'post',
                        success: function(data) {
                            $('#moveWindow').window('close');
                            var childData = $('#organizationTree').tree('getData', orgNode.target);
                           	var childList = $('#organizationTree').tree('getChildren', treeNode.target);
                         	if (childList != null && childList != '') {
                         		var beforeNode = nodePosition(treeNode,treeNode.attributes.orgId,orgNode.attributes.showOrder);
                         		//调用pop方法 返回该节点数据包含其子节点
                         		var popData = $('#organizationTree').tree('pop',orgNode.target);
                         		if (beforeNode != null) {
                         			$('#organizationTree').tree('insert', {
                                 		before:beforeNode.target,
                                 		data:popData
                                 	});
                         		} else {
                         			appendMoveNode(treeNode,popData);
                         		}
                         	} else {
                         		//调用pop方法 返回该节点数据包含其子节点
                         		var popData = $('#organizationTree').tree('pop',orgNode.target);
                         		appendMoveNode(treeNode,popData)
                         	} 
                            $("#content").hide();
                        },
                        error: function() {
                            $.messager.alert('提示','移动出错！');
                            $(treeId).tree('reload');
                            $("#content").hide();
                        }
                    });
                }
            }
        }
        $("#link_task_save").click(updateParent);

        function doDelete(event) {
            var clickNode = $(treeId).tree('getSelected');
            if (!clickNode) {
                $.messager.alert('提示','请选择相应节点后再进行此操作！');
            } else if(clickNode.id == -1) {
                $.messager.alert('提示','该节点无此操作！');
            } else if (clickNode.id != null) {
                $.messager.confirm('确认','确认要删除该节点？',function(ok) {
                    if (ok) {
                        $.post("bp.org.OrgAction$delete.json", {
                            id : clickNode.attributes.orgId
                        }, function(data) {
                            if (!data.success) {
                                $.messager.alert('提示','该节点删除失败! 可能是存在子节点或者节点已经不存在或者正在被使用！', function() {
                                    $("#content").hide();
                                });
                            } else {
                            	$('#organizationTree').tree('remove',clickNode.target);
                                $("#content").hide();
                            }
                        }, "json");
                    }
                });
            }
        }
        $("[iconCls='icon-remove']").click(doDelete);

        function doSave() {
            $('#editForm').form('submit', {
                url: "bp.org.OrgAction$save.json",
                onSubmit: function() {
                    if (!$(this).form('validate')) {
                        return false;
                    }
                    var combo = $("#orgTypeId");
                    var text = $.trim(combo.combobox("getText"));
                    if (text != "") {
                        var record = null;
                        $.each(combo.combobox("getData"), function(i, v) {
                            if ($.trim(v.orgType) == text) {
                                record = v;
                            }
                        });
                        if (!record) {
                            $.messager.alert('提示','请下拉选择已有的组织机构类型！');
                            return false;
                        } else {
                            combo.combobox("setValue", record.orgTypeId);
                        }
                    }
                    return true;
                },
                success: function(data) {
                	var opt = $('#opt').val();
                	if (data) {
                		var data = $.parseJSON(data);
                     	var selected = $('#organizationTree').tree('getSelected');
                		 if (opt == 'add') {
                         	var child = $('#organizationTree').tree('getChildren', selected.target);
                         	if (child != null && child != '') {
                         		var beforeNode = nodePosition(selected,data.parentId,data.showOrder);
                         		if (beforeNode != null) {
                         			insertAddNode(beforeNode,data,data.children);
                         		} else {
                         			appendAddNode(selected,data,data.children);
                         		}
                         	} else {
                         		appendAddNode(selected,data,data.children);
                         	}
                             $("#content").hide();
                         } else {
                         	var childData = $('#organizationTree').tree('getData', selected.target);
                           	var parentNode = $('#organizationTree').tree('getParent', selected.target);
                         	if (parentNode != null && parentNode != '') {
                         		var beforeNode = nodePosition(parentNode,data.parentId,data.showOrder);
                         		//调用pop方法 返回该节点数据包含其子节点
                         		var popData = $('#organizationTree').tree('pop',selected.target);
                         		if (beforeNode != null) {
                         			insertAddNode(beforeNode,data,childData.children);
                         		} else {
                         			appendAddNode(parentNode,data,childData.children);
                         		}
                         	} 
                         	$("#content").hide();
                         }
                	}
                }
            });
        }
        //根据选中节点的showOrder判断其所在位置
        function nodePosition(parentNode,parentId,showOrder) {
        	var beforeNode = null;
     		$.each($('#organizationTree').tree('getChildren', parentNode.target), function(i, v) {
     			if (parentId == v.attributes.parentId) {
     				if (v.attributes.showOrder > showOrder) {
             			beforeNode = v;
             			return false;
             		}
     			}
             });
     		return beforeNode;
        }
        //添加或更新时调用append方法在末尾添加节点
        function appendAddNode(selected,data,children) {
        		$('#organizationTree').tree('append', {
          		parent: selected.target,
          		data: [{
          		    id: data.orgId,
          			text: $('#orgName').val(),
          			children:children,
          			attributes:{
          				orgId: data.orgId,
              			orgName: $('#orgName').val(),
              			orgPinYin: $('#orgPinYin').val(),
          				orgTypeId:data.orgTypeId,
              			orgType:data.orgType,
              			showOrder:data.showOrder,
              			parentId:data.parentId
          	        }
          		}]
          	});
        }
       //添加或更新时调用insert方法在相应位置插入节点
       function insertAddNode(beforeNode,data,children) {
    	   $('#organizationTree').tree('insert', {
          		before:beforeNode.target,
          		data: {
          		    id: data.orgId,
          			text: $('#orgName').val(),
          			children:children,
          			attributes:{
          				orgId: data.orgId,
              			orgName: $('#orgName').val(),
              			orgPinYin: $('#orgPinYin').val(),
          				orgTypeId:data.orgTypeId,
              			orgType:data.orgType,
              			showOrder:data.showOrder,
              			parentId:data.parentId
          	        }
          		}
          });  
       }
       //移动时调用append方法追加节点
      function appendMoveNode(treeNode,popData) {
    	  $('#organizationTree').tree('append', {
   	  		parent: treeNode.target,
   	  		data: [{
   	  		    id: popData.attributes.orgId,
   	  			text: popData.attributes.orgName,
   	  			children:popData.children,
   	  			attributes:{
   	  				orgId: popData.attributes.orgId,
   	      			orgName: popData.attributes.orgName,
   	      			orgPinYin: popData.attributes.orgPinYin,
   	  				orgTypeId:popData.attributes.orgTypeId,
   	      			orgType:popData.attributes.orgType,
   	      			showOrder:popData.attributes.showOrder,
   	      			parentId:popData.attributes.parentId
   	  	        }
   	  		}]
   	  	});   
       }
      
        $("#editForm [iconCls='icon-save']").click(doSave);
        
        function reset(event) {
        	var opt = $('#opt').val();
     	   if(opt == 'add'){
     		   $('#editForm').form('clear');
        	   loadData();
     		   $("#opt").val('add');
     	   } else if(opt == 'update'){
     		   $('#editForm').form('clear');
     		   var node = $('#organizationTree').tree('getSelected');
     		   var pnode = $(this).tree('getParent', node.target);
            	   $("#txtparent").text(pnode.text);
            	   loadData();
            	   loadOrgForm(node.id);
                $('#editForm').form('load',node.attributes);
                $("#opt").val('update');
     	   }
        }
        $("[iconCls='icon-undo']").click(reset);
        
      //resize scroll of panel
		$("#navtree").panel({
			height:$("#orgPanel").panel("options").height - 65
		});	
		
		//resize true
		$("#orgPanel").panel({
			onResize:function(width,height){
				$("#navtree").panel("resize",{width:width-7,height:$("#orgPanel").panel("options").height - 65});
			}
		});
        
    });//ready end~
    function winClose(){
  		 $('#moveWindow').window('close');
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
					        <div id="toolbar" >
					            <div class="body_layout_bar">
					            	<w:func id="bp.organ.resync">
					            	<a href="#" class="easyui-linkbutton" iconCls="icon-load" 
					            		plain="true" title="重新统计上下级关系" ></a>
					            		</w:func>
					            		 <w:func id="bp.organ.update">
					                <a href="#" class="easyui-linkbutton" iconCls="icon-add"
					                    plain="true" title="增加下级"></a> 
					                    </w:func>
					                     <w:func id="bp.organ.update">
					                <a href="#" class="easyui-linkbutton" iconCls="icon-move" plain="true"
					                    title="移到"></a> 
					                    </w:func>
					                     <w:func id="bp.organ.delete">
					                <a href="#" class="easyui-linkbutton" iconCls="icon-remove"
					                    plain="true" title="删除"></a>
					                    </w:func>
					            </div>
					        </div>
					        <div class="easyui-panel" id="navtree" border="false" >
					        	<ul id="organizationTree" animate="true"></ul>
					        </div>
				        </div>
				    </div>
				    <div region="center" title="组织机构信息" id="content" style="display: none">
				        <form action="" method="post" id="editForm" novalidate="true">
				            <table cellpadding="4" cellspacing="0" class="tbcontent"
				                style="width: 800px; margin: 10px;">
				                <tr>
				                    <td align="right" class="tbtitle" width="20%">组织机构名称</td>
				                    <td width="30%">
				                    <input id="orgName" name="orgName" style="width:230px"
				                        class="easyui-validatebox tinput" type="text" required=true />
				                    <input id="orgId" name="orgId" type="hidden" />
				                    <input id="opt" name="opt"  type ="hidden"/>
				                    </td>
				                    <td align="right" class="tbtitle" width="20%">机构标识码</td>
				                    <td width="30%"><input id="orgCode" name="orgCode" class="easyui-validatebox tinput" type="text" required=true/></td>
				                </tr>
				                <tr>
				                    <td align="right" class="tbtitle">机构全号</td>
				                    <td><span id="txtfullId"></span><input id="fullId"
				                        name="fullId" type="hidden" /></td>
				                    <td align="right" class="tbtitle">机构全码</td>
				                    <td><input id="fullCode" name="fullCode" class="easyui-validatebox tinput" type="text" /></td>
				                </tr>
				                <tr>
				                    <td align="right" class="tbtitle">简称</td>
				                    <td><input id="shortName" name="shortName" class="easyui-validatebox tinput" type="text"  /></td>
				                    <td align="right" class="tbtitle">全称</td>
				                    <td><input id="fullName" name="fullName" class="easyui-validatebox tinput" type="text"  /></td>
				                </tr>
				                <tr>
				                    <td align="right" class="tbtitle">父组织机构</td>
				                    <td><span id="txtparent"></span><input id="parentId"
				                        name="parentId" type="hidden" /></td>
				                    <td align="right" class="tbtitle">拼音码</td>
				                    <td><input id="orgPinYin" name="orgPinYin" type="text" class="tinput"/></td>
				                </tr>
				                <tr>
				                    <td align="right" class="tbtitle">组织机构类型</td>
				                    <td><input id="orgTypeId" name="orgTypeId" required="true" panelHeight="auto" style="width:130px;"
				                        class="easyui-combobox" /><input id="orgType" name="orgType"
				                        type="hidden" /></td>
									<td align="right" class="tbtitle">显示顺序</td>
									<td><input id="showOrder" name="showOrder"  class="easyui-numberbox tinput"  type="text" style="width:50px" /></td>
							    </tr>
				                <tr>
				                    <td align="right" class="tbtitle">所在地理区域</td>
				                    <td><input id="locId" name="locId" panelHeight="auto" style="width:130px;"
				                        class="easyui-combobox" /></td>
									<td align="right" class="tbtitle"></td>
									<td></td>
							    </tr>
							    <tr>
								<td align="center" colspan="4">
									<span class="buttonGaps"><a  href="javascript:;" class="easyui-linkbutton" iconCls="icon-save">保存</a></span>
									<span class="buttonGaps"><a  href="javascript:;" class="easyui-linkbutton" iconCls="icon-undo">重置</a></span>
								</td>
							</tr>
				            </table>
				        </form>
				        <div style="margin:10px;">
				       		<table id="childGrid" ></table>
				    	</div>
				    </div>
				
				    <div id="rootContextMenu" class="easyui-menu" style="width: 120px;">
				        <div iconCls="icon-add">增加下级</div>
				    </div>
				
				    <div id="childContextMenu" class="easyui-menu" style="width: 120px;">
				        <div iconCls="icon-add">增加下级</div>
				        <div iconCls="icon-move">移到</div>
				        <div iconCls="icon-remove">删除</div>
				    </div>
				
				    <div id="moveWindow" class="easyui-window" closed="true" modal="true"
				        collapsible="false" maximizable="false" resizable="false"
				        draggable="false" minimizable="false" inline="true" title="组织机构移动"
				        style="height: 365px; width:308px;">
				        <div style="height:260px;overflow:auto;">
				            <ul id="moveTree"></ul>
				        </div>
				        <div style="text-align: center;position: relative;top:24px;">
				            <span class="buttonGaps"><a id="link_task_save" class="easyui-linkbutton" iconCls="icon-save">保存</a></span>
				            <span class="buttonGaps"><a class="easyui-linkbutton" iconcls="icon-close" onclick="winClose()">取消</a></span>
				        </div>
				    </div>
   			</div>
		</td>
	</tr>
</table>
</body>
</html>