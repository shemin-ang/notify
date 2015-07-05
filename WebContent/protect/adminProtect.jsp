<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
<% // only users can see this page 
if (session.getAttribute("adminIdForAdmin")==null){ %> 
	<jsp:forward page = "home.jsp" />
<%}
String adminIdForAdmin = (String) session.getAttribute("adminIdForAdmin"); 
%> 
</body>
</html>