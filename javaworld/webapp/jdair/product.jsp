<%@page import="com.dante.jdair.JdProduct,
				com.dante.jdair.vo.*,
				java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>所有飞电在售商品</title>
<%
	List<ProductVo> vos = JdProduct.getAllInlineProduct();
%>
</head>
<body>
	<table border="1" width="700px" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<td style="text-align: center;">名称</td>
				<td style="text-align: center;">售价</td>
			</tr>
		</thead>
		<tbody>
			<%
				for(ProductVo vo : vos) {
					%>
					<tr>
						<td><%=vo.getName() %></td>
						<td><%=vo.getCurrentPrice() %></td>
					</tr>
					<tr>
						<td colspan="2"><img src="<%=vo.getImgUrl() %>" width="720px" height="535px" /></td>
					</tr>
					<%
						List<ProductDetailVo> details = vo.getDetails();
						for(ProductDetailVo detail : details) {
							%>
								<tr>
									<td colspan="2"><img src="<%=detail.getImgUrl() %>" width="720px" height="535px" /></td>
								</tr>
							<%
						}
					%>
					<%
				}
			%>
		</tbody>
	</table>
</body>
</html>