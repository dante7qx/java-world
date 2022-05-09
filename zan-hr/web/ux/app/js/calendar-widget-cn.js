(function($) {
	function calendarWidget(el, params) {

		var now = new Date();
		var thismonth = now.getMonth();
		var thisyear = now.getYear() + 1900;

		var opts = {
			month : thismonth,
			year : thisyear
		};

		$.extend(opts, params);

		var monthChnNames = [ '1月', '2月', '3月', '4月', '5月',
		   				'6月', '7月', '8月', '9月', '10月', '11月',
		   				'12月' ];
		var dayChnNames = [ '周日', '周一', '周二', '周三',
		 				'周四', '周五', '周六' ];
		month = i = parseInt(opts.month);
		year = parseInt(opts.year);
		var m = 0;
		var table = '';

		// next month
		if (month == 11) {
			var next_month = '<a href="?month=' + 1 + '&amp;year=' + (year + 1)
					+ '" title="' + monthChnNames[0] + ' ' + (year + 1) + '">'
					+ monthChnNames[0] + ' ' + (year + 1) + '</a>';
		} else {
			var next_month = '<a href="?month=' + (month + 2) + '&amp;year='
					+ (year) + '" title="' + monthChnNames[month + 1] + ' '
					+ (year) + '">' + monthChnNames[month + 1] + ' ' + (year)
					+ '</a>';
		}

		// previous month
		if (month == 0) {
			var prev_month = '<a href="?month=' + 12 + '&amp;year='
					+ (year - 1) + '" title="' + monthChnNames[11] + ' '
					+ (year - 1) + '">' + monthChnNames[11] + ' ' + (year - 1)
					+ '</a>';
		} else {
			var prev_month = '<a href="?month=' + (month) + '&amp;year='
					+ (year) + '" title="' + monthChnNames[month - 1] + ' '
					+ (year) + '">' + monthChnNames[month - 1] + ' ' + (year)
					+ '</a>';
		}
		// uncomment the following lines if you'd like to display calendar month
		// based on 'month' and 'view' paramaters from the URL
		// table += ('<div class="nav-prev">'+ prev_month +'</div>');
		// table += ('<div class="nav-next">'+ next_month +'</div>');
		table += ('<table class="calendar-month " ' + i + ' " cellpadding="0" cellspacing="0">');

		table += '<tr>';
		table += '<th class="current-month-header" colspan="7"><img src="../../../ux/app/images/prev.png" class="move-month" onclick="prevMonth()"/>&nbsp;&nbsp;&nbsp;&nbsp;'
			   + year + '年' + monthChnNames[month]  
			   + '&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../../ux/app/images/next.png" class="move-month"  onclick="nextMonth()"/></th>';
		table += '</tr>';
		
		table += '<tr>';
		for (d = 0; d < 7; d++) {
			table += '<th class="weekday">' + dayChnNames[d] + '</th>';
		}

		table += '</tr>';

		var days = getDaysInMonth(month, year);
		var firstDayDate = new Date(year, month, 1);
		var firstDay = firstDayDate.getDay();

		var prev_days = getDaysInMonth(month, year);
		var firstDayDate = new Date(year, month, 1);
		var firstDay = firstDayDate.getDay();

		var prev_m = month == 0 ? 11 : month - 1;
		var prev_y = prev_m == 11 ? year - 1 : year;
		var prev_days = getDaysInMonth(prev_m, prev_y);
		firstDay = (firstDay == 0 && firstDayDate) ? 7 : firstDay;

		var calOpts=$.data(el,"calendar");
		
		var i = 0;
		var strType="节假日";
		var isWeekday= true; 
		for (j = 0; j < 42; j++) {
			if (j % 7 == 6 || j % 7 == 0){
				strType="工作日";
				isWeekday=true;
			}else{
				strType="节假日";
				isWeekday=false;
			}
			if ((j < firstDay)) {
				table += ('<td class="other-month '+ (isWeekday?' day_chked" workDay="true" ':' day_chked_wk"') +'><div class="day" attrday="'+(prev_days - firstDay + j + 1)+'">' + (prev_days - firstDay + j + 1) + '</div></td>');
				if(!calOpts.minDay){
					calOpts.minDay = (prev_days - firstDay + j + 1);
				}
			} else if ((j >= firstDay + getDaysInMonth(month, year))) {
				i = i + 1;
				table += ('<td class="other-month '+ (isWeekday?'day_chked" workDay="true" ':' day_chked_wk"') +'><div class="day" attrday="'+i+'">' + i + '</div></td>');
				calOpts.maxDay = i;
			} else {
				table += '<td class="current-month day' + (j - firstDay + 1)
						+ (isWeekday?' day_chked" workDay="true" ':' day_chked_wk"') +'><div class="day" attrday="'+(j - firstDay + 1)+'" style="text-align:middle;">' + (j - firstDay + 1) 
						+ '</div><div class="day_chbox" style="display:none;">'+strType+'<input type="checkbox" value="'+(j - firstDay + 1)+'" '+(isWeekday?' class="chkhd" ':' class="chkwk" ')+'/></div></td>';
			}
			if (j % 7 == 6)
				table += ('</tr>');
		}

		table += ('</table>');
		$(el).html(table);
	}
	
	function enable(el){
		$(".current-month",$(el)).click(function(event){
			var oa=$("input[type='checkbox']",$(this));
			var e=event || window.event; 
			var state= $(e.srcElement).attr("type");
			var checked=null;
			if(state=="checkbox"){
				checked= oa.attr("checked");
			}else{
				checked = !oa.attr("checked");
			}
			if(checked){
				oa.attr("checked","checked");
				if($(this).attr("workDay")){
					$(this).removeClass("day_chked").addClass("day_chked_wk");
				}else{
					$(this).removeClass("day_chked_wk").addClass("day_chked");
				}
			}else{
				if($(this).attr("workDay")){
					$(this).removeClass("day_chked_wk").addClass("day_chked");
				}else{
					$(this).removeClass("day_chked").addClass("day_chked_wk");
				}
				oa.removeAttr("checked");
			}
		});
		$(".day_chbox",$(el)).show();
	}
	
	function disable(el){
		$(".current-month",$(el)).unbind("click");
		$(".day_chbox",$(el)).hide();
		//var oa=$("input[type='checkbox']",$(el)).hide();
	}
	
	function clear(el){
		$(".current-month",$(el)).removeClass("day_chked");
		$("input[type='checkbox']",$(el)).removeAttr("checked");
	}

	function getDaysInMonth(month, year) {
		var daysInMonth = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
		if ((month == 1) && (year % 4 == 0)
				&& ((year % 100 != 0) || (year % 400 == 0))) {
			return 29;
		} else {
			return daysInMonth[month];
		}
	}
	
	function loadDayOfCurrentMonth(el,params) {
		var oa= null;
		var cssMonth= ".current-month .day";
		if(params.month!=month){
			cssMonth =".other-month .day"
		}
		$(cssMonth,$(el)).each(function(){
			var day = parseInt($(this).attr("attrday"));
			if(day == params.day) {
				if(params.content){
					$(this).html(day +'<div class="day_cont">' + params.content + '</div>');
				}
				oa = $(this).parent();
				$("input[type='checkbox']",oa).attr("checked","checked")
				if(oa.attr("workDay")){
					$(this).parent().removeClass("day_chked").addClass("day_chked_wk");
				}else{
					$(this).parent().removeClass("day_chked_wk").addClass("day_chked");
				}
				return false;
			}
		});
	}

	// jQuery plugin initialisation
	$.fn.calendarWidget = function(options,params) {
		if (typeof options == "string") {
			return $.fn.calendarWidget.methods[options](this, params);
		}
		return  this.each(function(){
			var state=$.data(this,"calendar");
			if(!state){
				$.data(this,"calendar",$.fn.calendarWidget.defaults);
			}
			calendarWidget(this, options);
		})
	};
	
	$.fn.calendarWidget.defaults={
		minDay:null,   //日历本页第一天（上个月）
		maxDay:null,    //日历本页最后一天（下个月）
		onShowDay:function(content){
		},
	};
	
	/*$.fn.calendarWidget.load = function(divId, paramDay, paramInfo) {
		loadData(divId, paramDay, paramInfo);
	};*/
	
	$.fn.calendarWidget.methods = {
		options : function(jq) {
			return $.data(jq[0], "calendar");
		},
		loadDayOfCurrentMonth: function(jq,params){
			return jq.each(function(){
				loadDayOfCurrentMonth(this,params);
			});
		},
		enable:function(jq){
			enable(jq[0]);
		},
		disable:function(jq){
			disable(jq[0]);
		},
		clear:function(jq){
			clear(jq[0]);
		}
	};

})(jQuery);