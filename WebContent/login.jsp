<%@include file="navbar/navbar.jsp" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<script src="js/login.js"></script> 
<style>
.white, .white a {
  color: #fff;
}
</style>
</head>
<body>

<div class="container">
	<h1>notify</h1>
	<!-- Appear below navbar -->
	<div class="jumbotron">
		<!-- Display error message from login (if any) -->
		<%ArrayList<String> errorLoginList = (ArrayList<String>)request.getAttribute("errorLoginList"); 
		if(errorLoginList != null){ %>
		<div class="alert alert-danger alert-error">
		   <a class="close" data-dismiss="alert">&times;</a>
		   <strong>Error!</strong> Please correct the following error:
		   <ul>
		   <%for(int i = 0; i<errorLoginList.size(); i++){ %>
		   		<li><%=errorLoginList.get(i) %></li>
		   <%} %>
		   </ul>
		</div>
		<%}%>
       	<form id="loginFormId" class="form-horizontal" role="form" method="post" action="login">
	    <div class="form-group" id="emailError">
	    <div class="input-group input-group-lg">
  			<span class="input-group-addon">
    			<span class="glyphicon glyphicon-envelope white"></span>
 		    </span>
  			<input class="form-control" type="text" id="email" name="email" placeholder="Email" onKeyDown="limitText(this,147);">
		</div>
		<div id="emailErrorMsg" style="color:#a94442;display:none;">Email field cannot be empty.</div>
			<div id="emailErrorMsg2" style="color:#a94442;display:none;">Email field is not in the correct format.</div>
		</div>
		<div class="form-group" id="pwdError">
		<div class="input-group input-group-lg">
		  <span class="input-group-addon">
		    <span class="glyphicon glyphicon-lock white"></span>
		  </span>
		  <input class="form-control" type="password" id="pwd" name="pwd" placeholder="Password">
		</div>
		<div id="pwdErrorMsg" style="color:#a94442;display:none;">Password field cannot be empty.</div>
		<div id="pwdErrorMsg2" style="color:#a94442;display:none;">Password field must be between 8 to 15 characters.</div>
		</div>
	    
	    <div class="form-group" align="right">        
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="button" class="btn btn-lg btn-primary" onclick="validateLogin()">Submit</button>
	      </div>
	    </div>
	  </form>
	  <p><a href="forgetpassword.jsp" style="font-size:16px;">Forget Password?</a></p>
     </div><!-- End of jumbotron -->
</div> <!-- End of container -->


</body>
</html>