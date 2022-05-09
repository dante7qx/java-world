$.extend($.fn.validatebox.defaults.rules,{
   /*检查用户登录名是否重复*/
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
			                       url: 'LoginUserAction.json?action=bp/user/LoginUserAction&eventSubmitDoQueryByUserId=1&method=query',  
			                       data: param,  
			                       type: 'post',  
			                       dataType: 'json',  
			                       async: false,  
			                       cache: false  
				                   }).responseText;  
				                   if (result == 'false') {  
				                        $.fn.validatebox.defaults.rules.checkUserName.message = '用户ID已存在！';  
				                       return false;  
				                   } else {  
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
    ip:{
    	validator:function(value){
    		return /((?:(?:25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))/.test(value);
    	},
    	message: 'IP地址不正确'
    }

});