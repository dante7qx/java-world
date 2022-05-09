<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>定时任务</title>
</head>
<body>
	<div style="margin:10px;overflow:hidden;">
		<p><font><strong>Cron Expression指南</strong></font></p>
		<table class="tbcontent">
			<tbody>
				<tr>
					<th class="confluenceTh">
							<font size="2">
									<font face="Courier New">Field Name</font>
							</font>
					</th>
					<th class="confluenceTh">
							<font size="2">
									<font face="Courier New">Mandatory?</font>
							</font>
					</th>
					<th class="confluenceTh">
							<font size="2">
									<font face="Courier New">Allowed Values</font>
							</font>
					</th>
					<th class="confluenceTh">
							<font size="2">
									<font face="Courier New">Allowed Special Characters</font>
							</font>
					</th>
				</tr>
				<tr>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">Seconds</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">YES</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">0-59</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">, - * /</font>
							</font>
					</td>
				</tr>
				<tr>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">Minutes</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">YES</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">0-59</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">, - * /</font>
							</font>
					</td>
				</tr>
				<tr>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">Hours</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">YES</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">0-23</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">, - * /</font>
							</font>
					</td>
				</tr>
				<tr>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">Day of month</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">YES</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">1-31</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">, - * ? / L W C</font>
							</font>
					</td>
				</tr>
				<tr>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">Month</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">YES</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">1-12 or JAN-DEC</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">, - * /</font>
							</font>
					</td>
				</tr>
				<tr>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">Day of week</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">YES</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">1-7 or SUN-SAT</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">, - * ? / L C #</font>
							</font>
					</td>
				</tr>
				<tr>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">Year</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">NO</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font size="2">
									<font face="Courier New">empty, 1970-2099</font>
							</font>
					</td>
					<td class="confluenceTd">
							<font face="Courier New" size="2">, - * /</font>
					</td>
				</tr>
			</tbody>
		</table>
		<div align="left">
			<font face="Courier New">
			</font>&nbsp;</div>
		<div align="left">
			<font color="#000080" face="Courier New">
				<strong>项目实例：</strong>
			</font>
		</div>
		<div align="left">
			<font color="#000080" face="Courier New">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; second&nbsp; minute&nbsp; hours&nbsp; dayOfMonth&nbsp; month&nbsp; dayOfWeek&nbsp; year</font>
		</div>
		<div align="left">
			<font color="#000080" face="Courier New">每月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 0&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 6&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 6#3&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ?</font>
		</div>		
		<div align="left">
			<div style="text-align:left;">
					<font color="#000080" face="Courier New">每周&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;59&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;59&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;18&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;*&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ?</font>
			</div>
			<div style="text-align:left;">
					<div align="left">
							<font color="#000080" face="Courier New">自定义&nbsp;&nbsp;&nbsp;&nbsp;28&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;47&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;9&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;30&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;?&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 2006</font>
					</div>
			</div>
		</div>
		<div align="left">
			<font color="#000080" face="Courier New">
			</font>&nbsp;</div>
		<div align="left">
			<font color="#000080" face="Courier New">每月：每个月的第三个星期五的上午6:00:00 触发</font>
		</div>
		<div align="left">
			<font color="#000080" face="Courier New">每周：每周的星期日的下午18:59:59 触发</font>
		</div>
		<div align="left">
			<font color="#000080" face="Courier New">自定义：2006年7月30日上午9:47:28 触发</font>
		</div>
		<div align="left">
			<font face="Courier New">
			</font>&nbsp;</div>
		<div align="left">
			<font face="Courier New">
				<p>所有星号对应的段位置，都可以出现后面的符号（, - * /） <br>（? / L C）这些符号可以出现在"一月哪天"和"星期"段位置 <br>（w）只能出现在"一月哪天"段位置 <br>（#）只能出现在"星期"段位置</p>
				<p>解释符号代表的意思： <br>* 代表任意合法的字段 <br>0 * 17 * * ? ：表示在每天的5 PM 到 5:59之间的每一分钟启动scheduler <br><br>? 表示没值被指定 <br>如果同时指定"一月哪天"和"星期"，可能两者对应不起来 <br>0 0,15,30,45 * * * ? ：表示每刻钟启动scheduler <br>所以推荐用法是其中一个指定值，另一个用?指定</p>
				<p>/ 表示时间的增量 <br>0 0/15 * * * ? ：表示每刻钟启动scheduler <br><br>- 表示值的范围 <br>0 45 3-8 ? * * <br><br>L 如果用在"一月哪天"段上，表示一个月的最后一天；如果用在"星期"段上。表示一个星期的最后一天（星期六） <br>0 0 8 L * ? ：表示每个月最后一天的8点启动scheduler <br><br>W 表示最靠近给定时间的一天，（必须是星期一到星期五）</p>
				<p># 例如 6#3表示一个月的第三个星期五</p>
			</font>
		</div>
	</div>
</body>
</html>