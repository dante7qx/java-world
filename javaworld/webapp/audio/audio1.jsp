<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
	var AudioPlayer = {
			setMp3 : function() {
				$('audio').attr('src', '../AudioPlayerServlet');
			}
	};

	$(function() {
		AudioPlayer.setMp3();
	});
</script>
<title>播放器</title>
</head>
<body>
	<h1>播放器</h1>
	<audio src="" controls="controls">
	</audio>
	
	<!--  
	<audio src="water.mp3" controls="controls">
	</audio>
	-->
</body>
</html>