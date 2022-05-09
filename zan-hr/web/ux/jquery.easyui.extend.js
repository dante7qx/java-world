$.extend($.fn.datagrid.defaults, {
	localOperation: false
});
$.extend($.fn.datagrid.methods, {
	getRowByIndex:function(jqs,index){
		var jq = jqs[0];
		var opts = $.data(jq, "datagrid").options;
		var rows = $.data(jq, "datagrid").data.rows;
		if(index >=0 && index < rows.length){
			return rows[index];
		}else{
			return null;
		}
	},
	getRowById:function(jqs,id){
		var jq = jqs[0];
		var opts = $.data(jq, "datagrid").options;
		var rows = $.data(jq, "datagrid").data.rows;
		for (var i = 0; i < rows.length; i++) {
			if (rows[i][opts.idField] == id) {
				return rows[i];
			}
		}
		return null;
	}
});

$.extend($.fn.layout.methods, {
	//显示遮罩   
	loading : function(jq, msg) {
		var panel = $("#cclayout");//$(this).tabs("getSelected");  
		if (msg == undefined) {
			msg = "正在加载数据，请稍候...";
		}
		$("<div class=\"datagrid-mask\"></div>").css({
			display : "block",
			width : panel.width(),
			height : panel.height(),
			top : 0,
			left : 0
		}).css("z-index", "4").appendTo(panel);
		$("<div class=\"datagrid-mask-msg\"></div>").html(msg)
				.appendTo(panel).css(
						{
							display : "block",
							left : (panel.width() - $(
									"div.datagrid-mask-msg", panel)
									.outerWidth()) / 2,
							top : (panel.height() - $(
									"div.datagrid-mask-msg", panel)
									.outerHeight()) / 2
						});
	},
	//隐藏遮罩   
	loaded : function(jq) {
		var panel = $("#cclayout"); //$(this).tabs("getSelected");  
		panel.find("div.datagrid-mask-msg").remove();
		panel.find("div.datagrid-mask").remove();
	}
});


$.extend($.fn.validatebox.defaults.rules, {
	checkUserName : {
		validator : function(value){
		  var opt = $('#opt').val();
			if(opt == 'add'){
			if(!/[A-Za-z0-9_.]+$/.test(value)){
				 $.fn.validatebox.defaults.rules.checkUserName.message = '用户ID只能英文字母、数字、下划线、点的组合！'; 	
				 return false;
			}else{
				var param = {userId:value};
				var result = $.ajax({  
			                       url: 'bp.user.LoginUserAction$queryByUserId.json',  
			                       data: param,  
			                       type: 'post',  
			                       dataType: 'json',  
			                       async: false,  
			                       cache: false  
				                   }).responseText;  
				                   if (result == 2) {  
				                        $.fn.validatebox.defaults.rules.checkUserName.message = '用户ID已存在！';  
				                       return false;  
				                   } else if(result == 1){
				                	   $('#isUserId').val("1");
				                	   return true;
				                   }else {  
				                        return true;  
				                }  

			}}else{
				return true;
			}
			},
	        message: ''
		},
	tel : {
		validator : function(value){
			return /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/.test(value);
		}, 
		 message: '请输入有效的号码，如：010-56895869.'
	},
	equals: {
        validator : function (value, param) {
        	 var opt = $('#opt').val();
        	 if(opt == 'add'){
        		 return $.trim(value) == $.trim($("#"+param).val());
        	 }else{
        		 return true;
        	 }
        	 
        },
        message: '两次输入的密码不一致！'
    },
    mobile: {  
	        validator: function (value) {  
	           return /(^0?[1][358][0-9]{9}$)/.test(value);  
	       },  
	        message: '手机号码不正确.'  
    	   },
   pwd : {
	   validator: function (value) {  
		   if(value.length<6){
			   return false;
		   }else{
			   return true;
		   }
       },  
        message: '密码长度不能小于6位.' 
   },
   numberType : {
	   validator : function (value) {
			return /[0-9]+$/.test(value);
	   	},
		message: '请输入数字.'
		   
	 },
	 ip:{
    	validator:function(value){
    		return /^([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])\.([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])\.([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])\.([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])$/.test(value);
    	},
    	message: 'IP地址不正确'
	 },
	 ipv:{
		 validator:function(value){
			 return /^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/.test(value);
		 },
		 message:'IPv6地址不正确'
		 
	 },
	 cronExpr: {
         validator: function (value, param) {
             var data = {};
             data[param[1]] = value;
             var result = $.ajax({
                 url: param[0],
                 dataType: "json",
                 data: data,
                 async: false,
                 cache: false,
                 type: "post"
             });
             if (result.status == 200) {
	             if (result.responseText == "true") {
	            	return true; 
	             } else {
	            	 this.message = "克龙表达式不正确: " + result.responseText;
	             	return false; 
	             }
             } else {
            	 this.message = "克龙表达式无法校验，内部错误: " + result.status;
	             return false; 
             }
         },
         message: "克龙表达式不正确。"
     },
	 mac:{
		 validator:function(value){
			 return /[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}/.test(value);
		 },
		 message:'Mac地址不正确'
	 },
	 ipRange:{
		 validator:function(value){
			 if(/(?:(?:25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)\.){1,3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))?$/.test(value)){
				 return true;
			 } else {
				 return /::/.test(value)?/^([\da-f]{1,4}(:|::)){1,6}([\da-f]{1,4})?$/i.test(value):/^([\da-f]{1,4}:){1,7}([\da-f]{1,4})?$/i.test(value);
			 }
		 },
		 message:'IP地址段填写不正确'
	 },
	 maxLength : {
		validator : function(value, param) {
			var len = $.trim(value).length;
			return len <= param[0];
		},
		message : '长度不能大于{0}'
	},
	ipComm : {
		validator:function(value){
			return (/^([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])\.([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])\.([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])\.([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])$/.test(value)) 
			|| (/^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/.test(value));
		},
		message : 'IP地址填写不正确'
	},
	isNotNull: {
		validator:function(value){
			return $.trim(value).length > 0;
		},
		message : '内容不能为空'
	},
	comboboxEdit : {
		validator : function (value, param) {
			if((value == $("#"+param).combobox('getValue'))
				||(value!="" && $("#"+param).combobox('getValue')=="")){
				$("#"+param).combobox('clear');
				return false;
			}else{
				return true;
			}
		},
		message: '请选择下拉框中内容！' 
	},
	recentTwoMonth : {
		validator : function (value,param) {
			var d = new Date(value.replace(/-/g,'/'));
			var now = new Date();
			if((now.getTime()/1000/60/60/24-d.getTime()/1000/60/60/24)>60||d>now){
				$("#"+param).datetimebox('clear');
				return false;
			}else{
				return true;
			}
		},
		message: '只能选择近两个月以内的时间。' 
	},
	identityCode : {
		validator: function (value, param) {
			var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
			if(!value || !/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/.test(value)){
				this.message = "身份证号格式错误";
             	return false; 
            } else if(!city[value.substr(0,2)]){
                this.message = "地址编码错误";
             	return false;
            } else{
                //18位身份证需要验证最后一位校验位
                if(value.length == 18){
                	value = value.split('');
                    //∑(ai×Wi)(mod 11)
                    //加权因子
                    var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                    //校验位
                    var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                    var sum = 0;
                    var ai = 0;
                    var wi = 0;
                    for (var i = 0; i < 17; i++) {
                        ai = value[i];
                        wi = factor[i];
                        sum += ai * wi;
                    }
                    var last = parity[sum % 11];
                    if(parity[sum % 11] != value[17].toUpperCase()){
                    	this.message = "校验位错误";
                     	return false;
                    }
                }
            }
			return true;
        },
        message: ""
	},
	priceNumber : {
		validator : function(value) {
			return /^(\d+)(\.?)(\d{0,2})$/.test(value);
		},
		message : '请输入正数(可保留两位小数)，如：100.00或100.0或100'
	},
	dateCompare : {
		validator : function(value,param){
			var beginDate;
			var endDate;
			if(param[3]){
				beginDate = $("#"+param[0],"#"+param[3]).datebox('getValue');
				endDate = $("#"+param[1],"#"+param[3]).datebox('getValue');
			}
			else{
				beginDate = $("#"+param[0]).datebox('getValue');
				endDate = $("#"+param[1]).datebox('getValue');
			}
			if(beginDate && endDate){
				beginDate =  beginDate.replace(/-/g,"/");
				var date1 = new Date(beginDate);
				endDate =  endDate.replace(/-/g,"/");
				var date2 = new Date(endDate);
				if(date1 > date2){
					if(param[2]){
						$.fn.validatebox.defaults.rules.dateCompare.message =  '{2}';
					}else{
						$.fn.validatebox.defaults.rules.dateCompare.message =  '开始日期不能大于结束日期!';
					}
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		},
		message : '开始日期不能大于结束日期!'
	}
});

var Page={
	heightMap : new Array(),
	validForm : function(formId){			
		var flag = true;	
		$('#' + formId + '.input,#.'+formId+'select').each(function () {
		    if ($(this).attr('required') || $(this).attr('validType')) {
			    if (!$(this).validatebox('isValid')) {
			        flag = false;
			        return;
			    }
		    }
		});
		return flag;
	},
	closeWin:function(){
		if(closeWin){
			closeWin();
		}
	}
};

$(document).ready(function() {
    $.ajaxSetup({
        complete:function(request, textStatus){  
            if(request.status == "401"){  
                //如果超时就处理 ，指定要跳转的页面 
            	if (confirm("当前会话已经过期，是否重新登录？")) {
            		window.parent.location.href='';
            	}
                //alert("当前会话已经过期，请刷新页面重新登录！");
             } 
            //else if(request.status == "500") {
            //    alert("服务器端处理错误，请查看异常日志！");
            //}
          }  
     }); 
});

