//Set Namespace 
var hnasys = {};

hnasys.util = {
    /* 转化页面是编辑状态还是阅读模式*/
    editForm: function (id, flag) {
        if (flag) { //编辑
            var texts = $("#" + id).find("input[type='text']");
            texts.attr('disabled', false);
            var textareas = $("#" + id).find("textarea");
            textareas.attr('disabled', false);
            var checkboxs = $("#" + id).find("input[type='checkbox']");
            checkboxs.attr('disabled', false);
            var combos = $("#" + id).find("input").filter('.easyui-combobox');
            combos.combobox("enable");
            /* var dateTimeBox =  $("#"+id).find("input").filter('.easyui-datetimebox');
            dateTimeBox.datetimebox("enable");
            var dateBox =  $("#"+id).find("input").filter('.easyui-datebox');
            dateBox.datetimebox("enable");*/
        } else { //阅读
            var texts = $("#" + id).find("input[type='text']");
            texts.attr('disabled', true);
            var textareas = $("#" + id).find("textarea");
            textareas.attr('disabled', true);
            var checkboxs = $("#" + id).find("input[type='checkbox']");
            checkboxs.attr('disabled', true);
            var combos = $("#" + id).find("input").filter('.easyui-combobox');
            combos.combobox("disable");
            /* var dateTimeBox =  $("#"+id).find("input").filter('.easyui-datetimebox');
            dateTimeBox.datetimebox("disable");
            var dateBox =  $("#"+id).find("input").filter('.easyui-datebox');
            dateBox.datetimebox("disable");*/
        }
    },
    //弹出窗口中事件改变显示不同按钮，id 为按钮所在的div
    formButtonState: function (id, state, isDelButton) {
        if (typeof (isDelButton) == 'undefined') {
            isDelButton = false;
        }
        if (state == 'add') {
            $("#" + id).find('a[name="formSave"]').parent().css('display', '');
            $("#" + id).find('a[name="formReset"]').parent().css('display', '');
            $("#" + id).find('a[name="formEdit"]').parent().css('display', 'none');
            if (isDelButton) {
                $("#" + id).find('a[name="formDel"]').parent().css('display', 'none');
            }
        }
        if (state == 'edit') {
            $("#" + id).find('a[name="formSave"]').parent().css('display', '');
            $("#" + id).find('a[name="formReset"]').parent().css('display', '');
            $("#" + id).find('a[name="formEdit"]').parent().css('display', 'none');
            if (isDelButton) {
                $("#" + id).find('a[name="formDel"]').parent().css('display', '');
            }
        }
        if (state == 'view') {
            $("#" + id).find('a[name="formSave"]').parent().css('display', 'none');
            $("#" + id).find('a[name="formReset"]').parent().css('display', 'none');
            $("#" + id).find('a[name="formEdit"]').parent().css('display', '');
            if (isDelButton) {
                $("#" + id).find('a[name="formDel"]').parent().css('display', '');
            }
        }
        if (state == 'all') {
            $("#" + id).find('a[name="formSave"]').parent().css('display', 'none');
            $("#" + id).find('a[name="formReset"]').parent().css('display', 'none');
            $("#" + id).find('a[name="formEdit"]').parent().css('display', 'none');
            if (isDelButton) {
                $("#" + id).find('a[name="formDel"]').parent().css('display', 'none');
            }
        }
    },
    //判断tab标签是隐藏还是显示，id为tab标签的id
    dispalyTab: function (id, title, flag) {
        var tab = $('#' + id).tabs('getTab', title);
        if(tab){
        	if (flag) {
                tab.panel().css('display', '');
                var tabT = $('.tabs-title:contains("' + title + '")');
                tabT.parent().parent().css('display', '');
            } else {
                tab.panel().css('display', 'none');
                var tabT = $('.tabs-title:contains("' + title + '")');
                tabT.parent().parent().css('display', 'none');
            }
        }
        
    },
    //关闭弹出框，根据传入的win的id来判断
    closeWin: function (id) {
        var win = $("#" + id);
        win.window("close");
    },
    //显示弹出框口
    showWin: function (id) {
        var win = $("#" + id);
        win.window("open");
    },
    //设置弹出窗口的title
    setWinTitle: function (id, title) {
        var panelTitle = $('.panel-title', $('#' + id).parent());
        panelTitle.text(title);
    },
    /*把枚举类型的key，转化成value，供页面显示*/
    typeFormatter: function (datas, value) {
        for (var i = 0; i < datas.length; i++) {
            if (datas[i].key == value) return datas[i].value;
        }
        return value;
    },
    /*把枚举类型的key，转化成value，供页面显示*/
    typeFormatter1: function (datas, value) {
        for (var i = 0; i < datas.length; i++) {
            if (datas[i].id == value) return datas[i].name;
        }
        return value;
    },
    formatDate: function (endDate) {
        var month = endDate.getMonth() + 1;
        var year = endDate.getFullYear();
        var date = endDate.getDate();
        if (month < 10) {
            month = "0" + month;
        }
        if (date < 10) {
            date = "0" + date;
        }
        return year + "-" + month + "-" + date;
    },
    isEditForm: function (id, flag) {
        if (flag) { //编辑			  
        	$('input[type="text"],textarea,input[type="file"]', '#' + id).each(function(){
        		$(this).removeClass('formborder')
            	if($(this).attr("rdonly")!="true"){
            		$(this).removeAttr('readonly');
            	}
            });
            $('input[type="radio"],input[type="checkbox"]', '#' + id).each(function(){
            	if($(this).attr("dsable")!="true"){
            		$(this).removeAttr('disabled');
            	}
            });
            $('.combo', '#' + id).removeClass('formborder').find('.combo-arrow').show();
        } else {
            $('input[type="text"],textarea,input[type="file"]', '#' + id).each(function(){
            	$(this).addClass('formborder');
            	if($(this).attr("rdonly")){
            		$(this).attr('readonly', 'readonly');
            	}else{
            		if($(this).attr("readonly")){
	            		$(this).attr("rdonly","true");
	            	}else{
	            		$(this).attr('readonly', 'readonly').attr("rdonly","false");
	            	}
            	}
            });
            $('input[type="radio"],input[type="checkbox"]', '#' + id).each(function(){
            	if($(this).attr("dsable")){
            		$(this).attr('disabled', true)
            	}else{
	            	if($(this).attr("disabled")){
	            		$(this).attr('dsable', "true");
	            	}else{
	            		$(this).attr('disabled', true).attr('dsable', "false");
	            	}
            	}
            });
            $('.combo', '#' + id).addClass('formborder').find('.combo-arrow').hide();
        }
    },
    tableMerges : function (id, fieldArr) {
		for ( var i = 0; i < fieldArr.length; i++) {
			hnasys.util.tableMerge(id, fieldArr[i]);
		}
	},
	tableMerge : function (id, field) {
		var tabId = '#' + id;
		var rows = $(tabId).datagrid('getRows');
		var len = rows.length;
		var rowStr = "";
		var merges = [];
		var j = 0;
		var x = 0;
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if (i == 0) {
				rowStr = row[field];
				var merObj = {};
				merObj.index = 0;
				merges[j] = merObj;
			}
			if (rowStr != row[field]) {
				rowStr = row[field];
				merges[j].rowspan = x;
				x = 1;
				j++;
				var merObj = {};
				merObj.index = i;
				merges[j] = merObj;
			} else {
				x++;
			}
			if (i == (rows.length - 1)) {
				merges[j].rowspan = x;
			}
		}
		for ( var i = 0; i < merges.length; i++) {
			$(tabId).datagrid('mergeCells', {
				index : merges[i].index,
				field : field,
				rowspan : merges[i].rowspan
			});
		}
	},
	printPage : function() {
		if (parseInt(navigator.appVersion) >= 4) {
			 window.print();
		}
	}
};

//附件工具
hnasys.att = {
		addFile : function(tableId, formId) {
			var attachPart = $('#'+tableId+' tr:last','#'+formId).clone();
			$('#'+tableId,'#'+formId).append(attachPart);
		},
		deleteAttach : function(obj, tableId, formId) {
			var attSize = $('#'+tableId+' tr','#'+formId).filter('[type="uploadFile"]').size();
			if(attSize == 1) {
				  var file = $(obj).prev();
				  file.after(file.clone().val(""));  
				  file.remove(); 
			} else {
				$(obj).parent().parent().remove();
			}
		},
		deleteExistAttach : function(id, obj, formId) {
			var delId = $('#deletedFileIds','#'+formId).val() + id + ",";
			$('#deletedFileIds','#'+formId).val(delId);
			$(obj).parent().parent().parent().remove();
		},
		doAttCheck : function(obj, pkId, formId) {	// pkId: 主键id
			var val = $(obj).val();
			if(!val) return;
			$(':file','#'+formId).each(function(){
				if(obj != this) {
					if(val == $(this).val()) {
						$.messager.alert('提示', '文件名不能重复!');
						$(obj).after($(obj).clone().val(""));  
						$(obj).remove();
						return;
					}
				}
			});
			// 已保存
			if($("#"+pkId,'#'+formId).val()) {
				var last = val.lastIndexOf("\\") + 1;
				var existVal = val.substring(last, val.length);
				$('input[type="existFile"]','#'+formId).each(function(){
					if(existVal == $(this).val()) {
						$.messager.alert('提示', '文件名不能重复!');
						$(obj).after($(obj).clone().val(""));  
						$(obj).remove();
						return;
					}
				});
			}
		},
		downloadFile : function(id) {
			if (id != null && id != "") {
				var url = "util.common.FileIOAction$download.json?attId=" + id;
				window.location.href = url;
			} else {
				$.messager.alert("提示", "文件不可用，请检查！");
			}
		},
		clearUploadFiles : function(tableId, formId) {
			$('#'+tableId+' tr','#'+formId).filter('[type="uploadFile"]').each(function(i) {
				if(i == 0) {
					  var file = $(this).find(':file');
					  file.after(file.clone().val(""));  
					  file.remove();
				} else {
					$(this).remove();
				}
			});
			$('#'+tableId+' tr','#'+formId).filter('[type="existFile"]').each(function(i) {
				$(this).remove();
			});
			$('#deletedFileIds','#'+formId).val('');
		},
		buildUploadFiles : function(data, tableId, formId) {
			var existAtt = "";
			for(var i=0; i<data.length; i++) {
				existAtt += "<tr type='existFile'><td><div style='width:448px;'>"
						  + "<input type='existFile' readonly='readonly' style='width:400px;border:0;' value="+data[i]['description']+">"
						  + '<span class="l-btn-text icon-close" style="width:20px;height:20px;display:block;float:right;cursor:pointer;" onclick="hnasys.att.deleteExistAttach(\'' + data[i]['id'] + '\', this, \'' + formId + '\')"></span>'
						  + '<span class="l-btn-text icon-down" style="width:20px;height:20px;display:block;float:right;cursor:pointer;" onclick="hnasys.att.downloadFile(\'' + data[i]['id'] + '\')"></span>'
						  + "</div></td></tr>";
			}
			$('#'+tableId+' tr:last','#'+formId).before(existAtt);
		},
		loadUploadFiles : function(lotId, editable, tableId, formId) {
			if(!lotId) {
				lotId = "";
			}
			$.ajax({
				type : 'POST',
				url : 'pub.att.AttAction$queryByLotIds.json',
				data: {lotId : lotId},
				dataType : 'json',
				success : function(data) {
					hnasys.att.clearUploadFiles(tableId, formId);
					hnasys.att.buildUploadFiles(data, tableId, formId);
					hnasys.att.controlUploadFileBtn(editable, tableId, formId);
				}
			});
		},
		controlUploadFileBtn : function(editable, tableId, formId) {
			$('input[type="existFile"]','#'+formId).each(function(){
				if(editable) {
					$(this).next().show();
				} else {
					$(this).next().hide();
				}
			});
			if(editable) {
				$('#'+tableId,'#'+formId).prev().show();
				$('#'+tableId+' tr:last','#'+formId).show();
			} else {
				$('#'+tableId,'#'+formId).prev().hide();
				$('#'+tableId+' tr:last','#'+formId).hide();
			}
		}
};

