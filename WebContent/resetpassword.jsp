<%@include file="navbar/navbarAdmin.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Password Page</title>
<script src="js/jquery.min.js"></script>
<script src="js/verify.min.js"></script>
<script>
$(document).ready(function() {
    //save url of browser 
	document.getElementById("resetUrl").value = document.URL;
});
</script>
<style>
.white, .white a {
  color: #fff;
}
</style>
</head>
<body>

<div class="container">
	<h1>New Password</h1>
	<!-- Appear below navbar -->
	<div class="jumbotron">
		<!-- Display error message (if any) -->
		<%String errorMsg = (String)request.getAttribute("errorMsg"); 
		if(errorMsg != null){%>
		<div class="alert alert-danger alert-error">
		    <a class="close" data-dismiss="alert">&times;</a>
		    <strong>Error!</strong> <%=errorMsg %>
		</div>
		<%}%>
       	<form class="form-horizontal" role="form" method="post" action="resetPassword">
		<input type="hidden" id="resetUrl" name="resetUrl">
		<div class="form-group">
		<div class="input-group input-group-lg">
		  <span class="input-group-addon">
		    <span class="glyphicon glyphicon-lock white"></span>
		  </span>
		  <input class="form-control" type="password" id="pwd" name="pwd" placeholder="Password" data-validate="required,min(8),max(15)">
		</div>
		</div>
		
		<div class="form-group">
		<div class="input-group input-group-lg">
		  <span class="input-group-addon">
		    <span class="glyphicon glyphicon-ok white"></span>
		  </span>
		  <input class="form-control" type="password" id="confirmpwd" name="confirmpwd" placeholder="Confirm Password" data-validate="required,min(8),max(15)">
		</div>
		</div>
	    
	    <div class="form-group" align="right">        
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-lg btn-primary">Submit</button>
	      </div>
	    </div>
	  </form>
     </div><!-- End of jumbotron -->
</div> <!-- End of container -->


</body>
</html>