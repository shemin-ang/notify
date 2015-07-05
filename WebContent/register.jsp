<%@include file="navbar/navbarAdmin.jsp" %>
<%@include file="protect/superAdminProtect.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register Page</title>
<script src="js/verify.min.js"></script>
<script>
function limitText(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    }
}
</script>
<style>
.white, .white a {
  color: #fff;
}
</style>
</head>
<body>

<div class="container">
	<h1>Register New Admin</h1>
	<!-- Appear below navbar -->
	<div class="jumbotron">
		<!-- Display error message from login (if any) -->
		<%String errorMsg = (String)request.getAttribute("errorMsg"); 
		String successMsg = (String)request.getAttribute("successMsg"); 
		if(errorMsg != null){%>
		<div class="alert alert-danger alert-error">
		    <a class="close" data-dismiss="alert">&times;</a>
		    <strong>Error!</strong> <%=errorMsg %>
		</div>
		<%}
		if(successMsg != null){%>
		<div class="alert alert-success">
	        <a class="close" data-dismiss="alert">&times;</a>
	        <strong>Success!</strong> <%=successMsg %>
	    </div>
		<%}%>
       	<form class="form-horizontal" role="form" method="post" action="createAdmin">
	    <div class="form-group">
		<div class="input-group input-group-lg">
		  <span class="input-group-addon">
		    <span class="glyphicon glyphicon-user white"></span>
		  </span>
		  <input class="form-control" type="text" id="name" name="name" placeholder="Name" data-validate="required" onKeyDown="limitText(this,49);">
		</div>
		</div>
	    
	    <div class="form-group">
	    <div class="input-group input-group-lg">
  			<span class="input-group-addon">
    			<span class="glyphicon glyphicon-envelope white"></span>
 		    </span>
  			<input class="form-control" type="text" id="email" name="email" placeholder="Email" data-validate="required" onKeyDown="limitText(this,147);">
		</div>
		</div>
	    
	    <div class="form-group" align="right">        
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="submit" class="btn btn-lg btn-primary">Submit</button>
	      </div>
	    </div>
	  </form>
     </div><!-- End of jumbotron -->
     
     <p align="right">
     	<a href="logoutAdmin.jsp"><button type="button" class="btn btn-lg btn-primary">Logout</button></a>
     </p>
</div> <!-- End of container -->


</body>
</html>