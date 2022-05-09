<%@page import="com.epolleo.bp.util.DateUtils"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>假期管理</title>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="${ctx}/ux/app/js/epolleo-util.js"></script>
<script type="text/javascript" src="${ctx}/ux/app/js/calendar-widget-cn.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/jsp/common/css/calendar-widget.css">
<%
	Date servNow = DateUtils.getCurrentDate();
	int year= servNow.getYear()+1900;
	int month= servNow.getMonth();
%>
<script type="text/javascript">
	var year=<%=year%>;
	var month=<%=month%>;
	
	function prevMonth() {
		var prev_m = month == 0 ? 11 : month - 1;
		var prev_y = prev_m == 11 ? year - 1 : year;
		year = prev_y;
		month = prev_m;
		bulidCalendar(prev_y, prev_m);
		$("#link_task_edit").css("display","");
		$("#link_task_save").css("display","none");
	}
	
	function nextMonth() {
		var next_m = month == 11 ? 0 : month + 1;
		var next_y = next_m == 0 ? year + 1 : year;
		year = next_y;
		month = next_m;
		bulidCalendar(next_y, next_m);
		$("#link_task_edit").css("display","");
		$("#link_task_save").css("display","none");
	}
	
	function bulidCalendar(year, month) {
		$("#calendar").calendarWidget({
			month: month,
			year: year
		 });
		Holiday.loadData(year,month);
	}
	
	function getDayContent(data){		
		var calendar= $("#calendar");
		if(data) {
			var paramDay=null;
			var paramMonth=null;
			$.each(data,function(index){
				if(this.hDate){
					paramDay = parseInt(this.hDate.substring(8,10));
					paramMonth = parseInt(this.hDate.substring(5,7));
					calendar.calendarWidget("loadDayOfCurrentMonth",{
						month:paramMonth-1,
						day:paramDay,
						content :this.hContent
					});
				}
			});
		}
	}
	
	var Holiday={
			editData:function(){
				$("#link_task_save").css("display","");
				$("#link_task_edit").css("display","none");
				$("#calendar").calendarWidget("enable");
			},
			loadData:function(year,month) {
				var startDay= null;
				var endDay= null;
				var opts=$("#calendar").calendarWidget("options");
				if(opts.minDay){
					//prevMonth day
					if(month-1<0){
						startDay = (year-1) + "-" + 12 + "-"+ opts.minDay; 
					}else{
						startDay = year + "-" + (month) + "-"+ opts.minDay; 
					}
				}
				if(opts.maxDay){
					//nextMonth day
					if(month+1>11){
						endDay = (year+1) + "-" + 1 + "-"+ opts.maxDay; 
					}else{
						endDay = year + "-" + (month+1+1) + "-"+ opts.maxDay; 
					}
				}
				$.ajax({
					url:'bp.holiday.HolidayAction$queryByDate.json',
					type:'post',
					dataType: "json",
					data:{
						startDay:startDay,
						endDay:endDay
					},
					success: function (data){
						getDayContent(data);
					}	
				});
			},
			saveData:function(){
				var holidays= $("input[class='chkhd'][checked]");
				var workdays= $("input[class='chkwk'][checked]");
				var arr= new Array();
				var wkarr= new Array();
				$.each(holidays,function(){
					arr.push(year+ "-" + (month+1) +"-" + $(this).val());
				});
				$.each(workdays,function(){
					wkarr.push(year+ "-" + (month+1) +"-" + $(this).val());
				});
				$.ajax({
					type:'post',
					url:'bp.holiday.HolidayAction$save.json',
					dataType: "json",
					data:{
						"year":year,
						"month":month,
						"strDays":arr.join(','),
						"wkDays":wkarr.join(',')
					},
					success: function (data){
						$.messager.alert('提示',"保存成功！");
						$("#calendar").calendarWidget("disable");
						$("#link_task_edit").css("display","");
						$("#link_task_save").css("display","none");
					}	
				});
			},
			formReset:function(){
				/* $("#calendar").calendarWidget("clear");
				this.loadData(year,month); */
				bulidCalendar(year,month);
				this.editData();
			}
	}

$(function(){
	bulidCalendar(year,month);
	$("#link_task_edit").css("display","");
	$("#link_task_save").css("display","none");
});
</script>
</head>
<body>
<w:func id="bp.holiday.query">
	<div id="calgContext1"> 
		<div id="calendar" align="center" style="margin:10px 20px;"></div>
		<w:func id="bp.holiday.save">
		<div style="text-align:center;">
			<a id="link_task_save" href="#"  class="easyui-linkbutton" iconCls="icon-save" onclick="Holiday.saveData()">保存</a>
			<a id="link_task_edit" href="#"  class="easyui-linkbutton" iconCls="icon-edit" onclick="Holiday.editData()">编辑</a>
			<a id="link_task_undo" href="#"  class="easyui-linkbutton" iconcls="icon-undo" onclick="Holiday.formReset()"> 重置</a>
		</div>
		</w:func>
		<div>
			<table style="width:150px;line-height:30px;text-align:center;margin:10px auto;">
				<tr>
					<td class="day_chked">节假日</td>
					<td class="day_chked_wk">工作日</td>
				</tr>
			</table>
		</div>
	</div>
</w:func>
</body>

</html>