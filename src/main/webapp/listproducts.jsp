<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(Admin)</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<hr />

	<c:choose>
		<c:when test="${isAdminVerified==null || isAdminVerified==false}">
			<div align="center">
				<div>
					<br> <br> <br>
					<p style="font: normal; font-size: 30px; color: red;">
						You are not authorized to view this Page. Try <a href="index.jsp">Login</a> first.
					</p>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<h1 align="center"	style="font: normal; font-size: 30px; color: blue;">Corona Kit-All Products(Admin)</h1>
			<c:choose>
				<c:when test="${products==null || products.isEmpty() }">
					<p style="font: normal; font-size: 30px; color: red;">No products found. Add some products!!</p>
				</c:when>
				<c:otherwise>
					<div align="center">
						<div style="display: inline-block; border: thin solid black; padding: 10px;">
							<table border="2" cellspacing="2px" cellpadding="2px">
								<thead>
									<tr style="color: brown; font-weight: bold">
										<td>Product Id</td>
										<td>Name</td>
										<td>Description</td>
										<td>cost</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${products }" var="product">
										<tr>
											<td>${product.id }</td>
											<td>${product.productName }</td>
											<td>${product.productDescription }</td>
											<td>${product.cost }</td>
											<td><a href="admin?action=editproduct&productId=${product.id }">Edit</a></td>
											<td><a href="admin?action=deleteproduct&productId=${product.id }">Delete</a></td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			<c:if test="${deleteProductErrMsg!=null}">
				<p style="font: normal; font-size: 20px; color: red;">${deleteProductErrMsg }</p>
			</c:if>
		</c:otherwise>
	</c:choose>

	<hr />
	<jsp:include page="footer.jsp" />
</body>
</html>