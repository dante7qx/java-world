var jobList = new Array();
var closeallnotyflag = false;
$(function() {
	
});
function arrayContains(array,element){
	for(var i = 0; i< array.length;i++){
		if(array[i] == element){
			return true;
		}
	}
	return false;
}
function notifyAddTab(title,url){
	window.parent.addTab(title, url, null);
}
function closeallnoty(){
	closeallnotyflag = true;
	$.noty.closeAll();
	closeallnotyflag = false;
	jobList = new Array();
}
function generateInformation(layout,job) {
	if(window.noty!=undefined){
    var n = noty({
      text : '您有一条新任务，请查看！',
      type: 'information',
      dismissQueue: true,
      layout: layout,
      theme: 'defaultTheme',
      closeWith: ['click'], // ['click', 'button', 'hover']
      callback: {
          onShow: function() {},
          afterShow: function() {},
          onClose: function() {generateConfirm('center',job);},
          afterClose: function() {}
      }
    });
   }
    //var chk = '您有一条新任务，请查看！&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="easyui-linkbutton l-btn l-btn-plain" href="#" iconcls="icon-ok" onclick="closeallnoty(' + n.options.id + ');"><span class="l-btn-left"><span class="l-btn-text icon-close" style="padding-left: 15px;">&nbsp;</span></span></a>';
    //n.setText(chk);
}
function generateConfirm(layout,job) {
	if(!closeallnotyflag){
	var tabname = '';
    var n = noty({
        text: job.content,
        type: 'warning',
        dismissQueue: true,
        layout: layout,
        animation: {
        	open: {height: 'toggle'},
        	close: {height: 'toggle'},
        	easing: 'swing',
        	speed: 200 // opening & closing animation speed
    	},
        theme: 'defaultTheme',
        force: true,
        callback: {
          afterClose: function() {
          	if(tabname!=''&&tabname!=null&&tabname!=undefined){
              notifyAddTab(tabname,job.url);          
          	}
          }
      	},
        buttons: [
          {addClass: 'btn btn-primary', text: '知道了', onClick: function($noty) {
        	  finishNotify(job.id);
              tabname = $(this).parent().siblings('.noty_message').find('a').html();        	  
              $noty.close();
              //noty({dismissQueue: true, force: true, layout: layout,  timeout: 1000,theme: 'defaultTheme', text: '任务已设置完成', type: 'success'});
            }
          },
          {addClass: 'btn btn-danger', text: '再说吧', onClick: function($noty) {
	      		for(var i = 0; i< jobList.length;i++){
	    			if(jobList[i] == job.id){
	    				jobList.splice(i,1);
	    			}
	    		}
              $noty.close();
				if(jobList.length == 0){
					closeallnoty();
				}              
//              noty({dismissQueue: true, force: true, layout: layout,  timeout: 200,theme: 'defaultTheme', text: '再说吧，知道了。', type: 'error'});
            }
          }
        ]
      });
	}	
}
function showNotify(){
    $.ajax({
		type : "POST",
		url : "bp.announce.AnnounceInfoAction$queryNotify.json",
		success:function(data) {
			var theData = eval('(' + data + ')');
			for(var i = 0;i< theData.rows.length;i++){
				var job = theData.rows[i];
				if(!arrayContains(jobList,job.id)){
					if(i == 0&&theData.rows.length > 1){
						if(window.noty!=undefined){
						  var n = noty({
					      text : '关闭全部',
					      type: 'warning',
					      dismissQueue: true,
					      layout: 'bottomRight',
					      theme: 'defaultTheme',
					      closeWith: ['click'],
					      callback: {
					          onClose: function() {closeallnoty();}
					      }
					    });	
						}
					
					}					
					jobList.push(job.id);
					generateInformation('bottomRight',job);	
				}
			}
			
		}
	});
}
function finishNotify(jobid){
	for(var i = 0; i< jobList.length;i++){
		if(jobList[i] == jobid){
			jobList.splice(i,1);
		}
	}
    $.ajax({
		type : "POST",
		url : "bp.announce.AnnounceInfoAction$updateNotify.json",
		data:{
			id:jobid
		},
		success:function(data) {

		}
	});		
	if(jobList.length == 0){
		closeallnoty();
	}
}