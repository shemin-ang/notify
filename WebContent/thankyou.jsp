<%@include file="navbar/navbarUser.jsp" %>
<%@ page import="java.io.File" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Thank You Page</title>
</head>
<body>
<%
String firstEntry = (String)request.getAttribute("firstEntry");
String message = "";
if(firstEntry == null){%>
	<jsp:forward page="/loadThank">
		<jsp:param name="adminId" value="<%=adminId%>" />
	</jsp:forward>
<%}else{
	message = (String)request.getAttribute("message");
}%>
<div class='container'>
	<div class='jumbotron' align="center">
			<h3><%=message %></h3>
		<br>
		<p><img src="thankImage?id=<%=adminId%>"></p>
	</div>
	<p align="right">
      <a class="btn btn-lg btn-primary" href="form.jsp" role="button"> Submit another incident report for this establishment</a><br><br>
      <a class="btn btn-lg btn-primary" href="home.jsp" role="button"> Submit another incident report</a>
    </p>
</div>
</body>
</html>