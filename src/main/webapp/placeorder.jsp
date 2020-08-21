<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Place Order(user)</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<hr />

	<h1 align="center" style="font: normal; font-size: 20px; color: blue;">Step-2 : Select Products</h1>

	<div align="center">
		<table>
			<tr>
				<td>
					<form action="user?action=saveorder" method="post">
						<label>Address:<textarea name="address" rows="4" cols="50" required></textarea>
						</label>
						<button>Next>> Submit Order</button>
					</form>
				</td>
			</tr>
		</table>
	</div>

	<hr />
	<jsp:include page="footer.jsp" />
</body>
</html>