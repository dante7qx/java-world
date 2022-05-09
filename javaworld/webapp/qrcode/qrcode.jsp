<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
	var QRCode = {
			generQrCode : function() {
				$('#qrCodeImg').attr('src', '../QrCodeServlet?url='+$('#url').val());
			}
	};

	$(function() {
		$('#genQrCodeBtn').click(function() {
				QRCode.generQrCode();
			}
		);
	});
	
	
</script>
<title>二维码Demo</title>
</head>
<body>
	<h1>二维码</h1>
	<div>
		<input id="url" type="text" name="url" />
		<br>
		<input id="genQrCodeBtn" type="button" value="生成二维码" />
	</div>
	<div style="text-align: center;">
		<img id="qrCodeImg" alt="" width="300" height="300" />
	</div>
</body>
</html>