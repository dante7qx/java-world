<%@ page language="java" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title>win8首页</title>
<link rel="stylesheet" type="text/css" href="ux/win8UI/css/metro.css" />
<script src="ux/win8UI/js/jquery.min.js"></script>
<script src="ux/win8UI/js/jquery-1.6.1.min.js"></script>
<script src="ux/win8UI/js/jquery.plugins.min.js"></script>
<script src="ux/win8UI/js/metro.js"></script>
<script src="ux/win8UI/js/jquery.mousewheel.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	$(document).mousewheel(function(event, delta) {
		if (delta > 0){
			$container.stop().animate({
				scrollLeft : '-=' + ($('body').innerWidth() / 1.8)
			}, 400);
		}
		else if (delta < 0){
			$container.stop().animate({
				scrollLeft : '+=' + ($('body').innerWidth() / 1.8)
			}, 400);
		};		
		event.preventDefault();		
	});
	function openTarget(tabName, url, target){
		window.parent.addTab(tabName, url, null, target);
	}
</script>
</head>
<body>
	<div class="metro-layout horizontal">
		<!-- <div class="header">
			<div class="controls">
				<span class="next" title="Scroll left"></span>
				<span class="info">hello, this is win8 UI</span>
				<span class="prev" title="Scroll right"></span>
			</div>
		</div> -->
		<table cellpadding="0" cellspacing="0" class="header">
			<tr class="controls" valign="middle">
				<td width="45px"><span class="prev" title="Scroll right"></span></td>
				<td align="center" width="100%">hello, this is win8 UI</td>
				<td width="45px"><span class="next" title="Scroll left"></span></td>
			</tr>
		</table>
		<div id="content" class="content clearfix">
			<div class="items">
				<c:forEach items="${menus }" var="menu">
					<c:if test="${menu.menuKind==1}">
						<a class="box" href="#" onclick="openTarget('${menu.title}', '${menu.url}', '${menu.target}')"
	        				style="background: ${menu.backColor}; height: ${menu.cssHeight}em; width: ${menu.cssWidth}em;"> <span>${menu.title}</span>
		    				<img class="icon" src="${menu.menuInfo}" alt=""/></a>
					</c:if>
					<c:if test="${menu.menuKind==2}">
						<div class="box" style="background: ${menu.backColor}; height: ${menu.height}em; width: ${menu.width}em;">
							<a  href="#" onclick="openTarget('${menu.title}', '${menu.url}', '${menu.target}')">${menu.title}</a>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>
