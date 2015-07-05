<%@include file="navbar/navbarAdmin.jsp" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Contact Us Page</title>
<script src="js/contact.js"></script>  
</head>
<body>

<div class="container">
	<h1>Contact Us</h1>
	<!-- Appear below navbar -->
	<div class="jumbotron">
		<%ArrayList<String> errorList = (ArrayList<String>)request.getAttribute("errorList");
		String successMsg = (String)request.getAttribute("successMsg"); 
		if(errorList != null){ %>
		<div class="alert alert-danger alert-error">
		   <a class="close" data-dismiss="alert">&times;</a>
		   <strong>Error!</strong> Please correct the following error:
		   <ul>
		   <%for(int i = 0; i<errorList.size(); i++){ %>
		   		<li><%=errorList.get(i) %></li>
		   <%} %>
		   </ul>
		</div>
		<%}else if(successMsg != null){%>
			<div class="alert alert-success">
			    <a class="close" data-dismiss="alert">&times;</a>
			    <strong>Success!</strong> <%=successMsg %>
			</div>
		<%}%>
		
		<form id="contactFormId" class="form-horizontal" role="form" method="post" action="contactUs">
		<div class="form-group">
	      <label class="control-label col-sm-2" for="name">Name:</label>
	      <div class="col-sm-10">
	        <input type="text" class="form-control" id="name" name="name" placeholder="Name" onKeyDown="limitText(this,44);">
	      </div>
	    </div>
	    <div class="form-group" id="emailError">
	      <label class="control-label col-sm-2" for="email">Email<font color="red">*</font>:</label>
	      <div class="col-sm-10">
	        <input type="text" class="form-control" id="email" name="email" placeholder="Email" onKeyDown="limitText(this,44);">
	      	<div id="emailErrorMsg" style="color:#a94442;display:none;">Email field cannot be empty.</div>
	      	<div id="emailErrorMsg2" style="color:#a94442;display:none;">Email field is in the wrong format.</div>
	      </div>
	    </div>
		<div class="form-group" id="feedbackError">
	      <label class="control-label col-sm-2" for="feedback">Issues/ Feedback<font color="red">*</font>:</label>
	      <div class="col-sm-10">
	        <textarea class="form-control" id="feedback" name="feedback" maxlength="99"></textarea>
	      	<div id="textarea_feedback"></div>
	      	<div id="feedbackErrorMsg" style="color:#a94442;display:none;">Issues/ Feedback field cannot be empty.</div>
	      </div>
	    </div>
		
		<div class="form-group" align="right">        
	      <div class="col-sm-offset-2 col-sm-10">
	        <button type="button" class="btn btn-lg btn-primary" onclick="validateContact()">Submit &raquo;</button>
	      </div>
	    </div>
		</form>
     </div><!-- End of jumbotron -->
</div> <!-- End of container -->


</body>
</html>