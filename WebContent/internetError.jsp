<%@include file="navbar/navbarAdmin.jsp" %>
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Internet Error</title>
</head>
<body>
<div class="container">
	<div class="jumbotron">
		<div class="alert alert-danger alert-error">
		    <a class="close" data-dismiss="alert">&times;</a>
		    <strong>Error!</strong> Sorry, there seem to be some error loading the page. Please check that your device is connected to the internet/wifi network.
		</div>
		<br>
		<div class="form-group" align="right">        
	      	<div class="col-sm-offset-2 col-sm-10">
	        	<button type="submit" class="btn btn-lg btn-primary"  onclick="history.go(0)">Try Refreshing</button>
	      </div>
	    </div>
	</div><!-- End of jumbotron -->
</div>
</body>
</html>