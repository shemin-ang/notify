<%@include file="navbar/navbarUser.jsp" %>
<%@ page import="java.util.*" %>
<%@ page autoFlush="true" buffer="1094kb"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Specific Public Form</title>

<script src="js/form.js"></script>
<script src="js/moment-with-locales.js"></script>
<script src="js/bootstrap-datetimepicker.js"></script>
<script src="js/bootbox.min.js"></script> 
<link href="css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="css/form.css" rel="stylesheet">
<link href="css/loadAjax.css" rel="stylesheet">
</head>
<body>
<%
String firstEntry = (String)request.getAttribute("firstEntry");
ArrayList<String> categoryOrderList = new ArrayList<String>();
ArrayList<String> categoryList = new ArrayList<String>();
ArrayList<String> errorList = new ArrayList<String>();
if(firstEntry == null){%>
	<jsp:forward page="/loadForm">
		<jsp:param name="adminId" value="<%=adminId%>" />
	</jsp:forward>
<%}else{
	categoryOrderList = (ArrayList<String>)request.getAttribute("categoryOrderList");
	categoryList = (ArrayList<String>)request.getAttribute("categoryList");
	errorList = (ArrayList<String>)request.getAttribute("errorList");
}%>
<div id="loading">
  <img id="loading-image" src="img/ajax-loader.gif"/>
</div>

<div class="container">
	<h1>Notify an Incident</h1><br>
	<form id="reportFormId" class="form-horizontal" method="post" action="writeForm" enctype="multipart/form-data">
    <input type="hidden" id="adminId" name="adminId" value="<%=adminId%>">
	<!-- Backend Validation Error Message -->
	<%if(errorList.size() != 0){ %>
		<div class="alert alert-danger alert-error">
		   <a class="close" data-dismiss="alert">&times;</a>
		   <strong>Error!</strong> Please correct the following error:
		   <ul>
		   <%for(int i = 0; i<errorList.size(); i++){ %>
		   		<li><%=errorList.get(i) %></li>
		   <%} %>
		   </ul>
		</div>
	<%}%>
	
	<!-- Date & Time -->
	<div class="jumbotron" id="datetimepickerDiv">
       	<div class="form-group" id="datetimepickerError">
	      <label for="dateTime"><h3>Date/Time of Incident<font color="red">*</font>:</h3></label>
	      <input type="text" class="form-control" id="datetimepicker" name="datetimepicker" placeholder="Date and Time">
	      <div id="dateTimeErrorMsg" style="color:#a94442;display:none;">Date/Time field cannot be empty.</div>
	      <div id="dateTimeErrorMsg2" style="color:#a94442;display:none;">You have entered a date/time in the future. Please enter a valid date/time.</div>
	      
	      <script>
	      $(function () {
              $('#datetimepicker').datetimepicker();
          });
	      </script>
	    </div>
     </div><!-- End of jumbotron -->
     
     <!-- Location -->
	<div class="jumbotron" id="locationDiv">
       	<div class="form-group" id="locationError">
	      <label for="location"><h3>Location of Incident<font color="red">*</font>:</h3></label>
	      <input type="text" class="form-control" id="location" name="location" placeholder="Enter location" onKeyDown="limitText(this,44);">
	      <div id="locationErrorMsg" style="color:#a94442;display:none;">Location field cannot be empty.</div>
	    </div>
     </div><!-- End of jumbotron -->
     
	<!-- Category -->
	<div class="jumbotron" id="categoryDiv">
		<div class="form-group">
			<label for="categoryDD"><h3>Incident Category<font color="red">*</font>:</h3></label>
			<select class="form-control" name="categoryDD" id="categoryDD" onchange="displayOthersTextbox()">
			</select>
		</div>
		<div class="form-group" id="showHideOthers">
			<input type="text" class="form-control" id="otherCategory" name="otherCategory" placeholder="Please Specify">
			<div id="otherCategoryErrorMsg" style="color:#a94442;display:none;">Please specify a category or select one from the above dropdown list.</div>
		</div>
		<script type="text/javascript">
			var categoryOrderListJS = '<%=categoryOrderList%>';
			categoryListJS = '<%=categoryList%>';
			loadCategoryList(categoryOrderListJS,categoryListJS);
		</script>
     </div><!-- End of jumbotron -->
     
     <!-- Descriptions -->
	<div class="jumbotron" id="descriptionDiv">
		<div class="form-group" id="descriptionError">
	      <label for="descriptions"><h3>Incident Description <font color="red">*</font>:</h3></label>
	      <p><font size="3">(Please provide as much information as possible to allow relevant parties to attend to the matter more effectively.)</font></p>
	      <textarea class="form-control" id="descriptions" name="descriptions" maxlength="1000"></textarea>
	      <div id="textarea_feedback"></div>
	      <div id="descriptionErrorMsg" style="color:#a94442;display:none;">Description field cannot be empty.</div>
	    </div>
     </div><!-- End of jumbotron -->
     
     <!-- Photo -->
	<div class="jumbotron">
       	<h3>Photo of Incident</h3>
       	<p><img width="30%" id="uploadImg" src="img/no_image.png"></p>
		 <div class="btn btn-lg btn-primary btn-file">
		    <span>Take a Photo</span>
		    <input type="file" id="photo" name="photo"/>
		</div>	
     </div><!-- End of jumbotron -->
     
     <!-- Contacts -->
	<div class="jumbotron" id="contactDiv">
		<h3 style="display:inline;">Contact Me</h3>
		<ul class="nav nav-pills" style="display:inline;float:right;">
		  <li id="yesPill" class="active"><a onclick="yesContact()">Yes</a></li>
		  <li id="noPill"><a onclick="noContact()">No</a></li>
		</ul><br><br>
		<div id="contactId">
	    <div class="form-group">
	    <div class="input-group input-group-lg">
  			<span class="input-group-addon">
    			<span class="glyphicon glyphicon-user white"></span>
 		    </span>
  			<input class="form-control" type="text" id="name" name="name" placeholder="Name" onKeyDown="limitText(this,44);">
		</div>
		</div>
	    <div class="form-group" id="emailError">
	    <div class="input-group input-group-lg">
  			<span class="input-group-addon">
    			<span class="glyphicon glyphicon-envelope white"></span>
 		    </span>
  			<input class="form-control" type="text" id="email" name="email" placeholder="Email" onKeyDown="limitText(this,147);">
		</div>
		<div id="emailErrorMsg" style="color:#a94442;display:none;">Email field cannot be empty.</div>
		<div id="emailErrorMsg2" style="color:#a94442;display:none;">Please enter a valid email address.</div>
		</div>
		<div class="form-group" id="telephoneError">
		<div class="input-group input-group-lg">
		  <span class="input-group-addon">
		    <span class="glyphicon glyphicon-earphone white"></span>
		  </span>
		  <input class="form-control" type="text" id="telephone" name="telephone" placeholder="Telephone" onKeyDown="limitText(this,7);">
		</div>
		<div id="telephoneErrorMsg" style="color:#a94442;display:none;">Telephone field can only contain numbers.</div>
		<div id="telephoneErrorMsg2" style="color:#a94442;display:none;">Telephone field must have 8 numbers.</div>
		</div>
		</div>
     </div><!-- End of jumbotron -->

     <div class="form-group" align="right">        
      <div class="col-sm-offset-2 col-sm-10">
        <button type="button" class="btn btn-lg btn-primary" onclick="validateForm()">Submit &raquo;</button>
      </div>
      <div>
      	<img src="headerLogo?id=<%=adminId%>" height="100px"><br>
      	<img src="img/notifypowered.png" height="25px">
      </div>
    </div>
     </form>
</div> <!-- End of container -->

</body>
</html>