<%@include file="navbar/navbarAdmin.jsp" %>
<%@include file="protect/adminProtect.jsp" %>
<%@ page import="java.util.*" %>
<%@ page autoFlush="true" buffer="1094kb"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page</title>
<script src="js/admin.js"></script>  
<script src="js/adminValidation.js"></script>
<script src="js/bootbox.min.js"></script>                                
<link href="css/admin.css" rel="stylesheet">
</head>
<body>
<%
String firstEntry = (String)request.getAttribute("firstEntry");
String name = "";
String email = "";
String secondEmail = "";
String url = "";
String address = "";
String message = "";
String noCompany = "";
ArrayList<String> categoryList = new ArrayList<String>();
ArrayList<String> categoryEmailList = new ArrayList<String>();
ArrayList<String> errorCompanyList = new ArrayList<String>();
ArrayList<String> errorCategoryList = new ArrayList<String>();
ArrayList<String> errorSubmissionList = new ArrayList<String>();
ArrayList<String> errorAccountList = new ArrayList<String>();
if(firstEntry==null){%>
	<jsp:forward page="/loadAdmin">
	<jsp:param name="adminId" value="<%=adminIdForAdmin%>" />
	</jsp:forward>
<%}else{
	name = (String)request.getAttribute("name");
	email = (String)request.getAttribute("email");
	secondEmail = (String)request.getAttribute("secondEmail");
	url = (String)request.getAttribute("url");
	address = (String)request.getAttribute("address");
	message = (String)request.getAttribute("message");
	noCompany = (String)request.getAttribute("noCompany");
	categoryList = (ArrayList<String>)request.getAttribute("categoryList");
	categoryEmailList = (ArrayList<String>)request.getAttribute("categoryEmailList");
	errorCompanyList = (ArrayList<String>)request.getAttribute("errorCompanyList");
	errorCategoryList = (ArrayList<String>)request.getAttribute("errorCategoryList");
	errorSubmissionList = (ArrayList<String>)request.getAttribute("errorSubmissionList");
	errorAccountList = (ArrayList<String>)request.getAttribute("errorAccountList");
}%>
<script>
var noCompanyJs = '<%=noCompany%>';
noCompanyFunc(noCompanyJs);
</script>
<div class="container">
	<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
	<input type="hidden" id="noCompanyId">
	
	  <!-- Corporate Identity -->
	  <div class="panel panel-default">
	    <div class="panel-heading" role="tab" id="headingOne">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
	          Corporate Identity
	        </a>
	      </h4>
	    </div>
	    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
	      <div class="panel-body">
	      <div class="jumbotron">
	      <!-- Backend validation -->
	      <%if(errorCompanyList.size() != 0){ %>
			<div class="alert alert-danger alert-error">
				<a class="close" data-dismiss="alert">&times;</a>
				<strong>Error!</strong> Please correct the following error:
				<ul>
				<%for(int i = 0; i<errorCompanyList.size(); i++){ %>
				   	<li><%=errorCompanyList.get(i) %></li>
				<%} %>
				</ul>
			</div>
			<%}%>
	      
	      	<form id="companyFormId" class="form-horizontal" method="post" action="saveCompany" role="form" enctype="multipart/form-data">
	  		<input type="hidden" id="adminId" name="adminId" value="<%=adminIdForAdmin%>">
	        <h4>Public Address</h4>
	        <%if(!url.equals("")){ %>
			    <div class="form-group">
			      <label class="control-label col-sm-2" for="preferredAdd">notify.sg/</label>
			      <div class="col-sm-10">
			        <input type="text" class="form-control" id="preferredAdd" name="preferredAdd" value="<%=url %>" readonly>
			      </div>
			    </div>
		      	<br>
	      	<%}else{ %>
	      		<div class="form-group" id="urlError">
			      <label class="control-label col-sm-2" for="preferredAdd">notify.sg/</label>
			      <div class="col-sm-10">
			        <input type="text" class="form-control" id="preferredAdd" name="preferredAdd" placeholder="your preferred address" onKeyDown="limitText(this,44);">
			      </div>
			    </div>
			    <div class="alert alert-danger alert-error" id="errorUrl" style="display:none">
				    <a class="close" data-dismiss="alert">&times;</a>
				    <strong>Error!</strong> <div style="display:inline" id="urlOutcomeError"></div>
				</div>
				<div class="alert alert-success" id="successUrl" style="display:none">
				    <a class="close" data-dismiss="alert">&times;</a>
				    <strong>Success!</strong> <div style="display:inline" id="urlOutcomeSuccess"></div>
				</div>
		        <div style="color:red;">Public Address cannot be changed. Do select with care and keep it short and sweet.</div>
		      	<br>
			<%} %>
	      	<h4 id="logoError">Corporate Logo</h4>
	      	<p><img width="25%" id="logoImg" src="adminLogo?id=<%=adminIdForAdmin%>"></p>
	      	<div id="logoErrorMsg" style="color:#a94442;display:none;">Logo field cannot be empty.</div>
	        <p align="right">
		      <div class="btn btn-lg btn-primary btn-file">
			    <span>Upload</span>
			    <input type="file" id="logo" name="logo"/>
			</div>
		    </p>
	      	<br>
	      	<h4>Corporate Location</h4>
		    <div class="form-group" id="locationError">
		      <label class="control-label col-sm-2" for="companyLocation">Singapore </label>
		      <div class="col-sm-10">
		      	 <%if(!address.equals("")){ %>
		        	<input type="text" class="form-control" id="companyLocation" name="companyLocation" placeholder="Postal Code" value="<%=address%>" onKeyDown="limitText(this,5);">
		        <%}else{ %>
		        	<input type="text" class="form-control" id="companyLocation" name="companyLocation" placeholder="Postal Code" onKeyDown="limitText(this,5);">
		        <%} %>
		        <div id="locationErrorMsg" style="color:#a94442;display:none;">Postal Code field cannot be empty.</div>
		        <div id="locationErrorMsg2" style="color:#a94442;display:none;">Postal Code field can only contain numbers.</div>
		        <div id="locationErrorMsg3" style="color:#a94442;display:none;">Postal Code field needs to have 6 numbers.</div>
		      </div>
		    </div>
		    <div class="form-group" align="right">        
		      <div class="col-sm-offset-2 col-sm-10">
		        <button type="button" class="btn btn-lg btn-primary" onclick="validateCompany()">Save</button>
		      </div>
		    </div>
		    </form> 
	      </div><!-- End of jumbotron -->
	      </div>
	    </div>
	  </div>
	  
	  <!-- Category & Recipient -->
	  <div class="panel panel-default">
	    <div class="panel-heading" role="tab" id="headingTwo">
	      <h4 class="panel-title">
	        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
	          Category & Recipient
	        </a>
	      </h4>
	    </div>
	    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
	      <div class="panel-body">
	        <div class="jumbotron">
	        <!-- Backend validation -->
	      <%if(errorCategoryList.size() != 0){ %>
			<div class="alert alert-danger alert-error">
				<a class="close" data-dismiss="alert">&times;</a>
				<strong>Error!</strong> Please correct the following error:
				<ul>
				<%for(int i = 0; i<errorCategoryList.size(); i++){ %>
				   	<li><%=errorCategoryList.get(i) %></li>
				<%} %>
				</ul>
			</div>
			<%}%>
	        
	        	<form id="categoryFormId" class="form-horizontal" method="post" action="saveCategory" role="form">
	 			<input type="hidden" id="adminId" name="adminId" value="<%=adminIdForAdmin%>">
	 			
       			<div id="firstCategory" class="categoryBorder">
       			<h4 style="display:inline">1st Order</h4>
       			<p id="firstAdd" style="display:inline;float:right">
			      <a class="btn btn-lg btn-primary" onclick="activeSecondCategory()"><i class="glyphicon glyphicon-plus"></i></a>
			    </p><br><br>
			    <div class="form-group" id="categoryError">
			      <label class="control-label col-sm-2" for="category">Category:</label>
			      <div class="col-sm-10">
			        <input type="text" class="form-control" id="category" name="category" placeholder="Enter category" onKeyDown="limitText(this,44);">
			      	<div id="categoryErrorMsg" style="color:#a94442;display:none;">Category field must be provided for this email.</div>
			      </div>
			    </div>
			    <div class="form-group" id="categoryEmailError">
			      <label class="control-label col-sm-2" for="categoryEmail">Email:</label>
			      <div class="col-sm-10">
			        <input type="email" class="form-control" id="categoryEmail" name="categoryEmail" placeholder="Enter email" onKeyDown="limitText(this,147);">
			      	<div id="categoryEmailErrorMsg" style="color:#a94442;display:none;">Email field must be provided for this category.</div>
			      	<div id="categoryEmailErrorMsg2" style="color:#a94442;display:none;">Email field is not in the correct format.</div>
			      </div>
			    </div>
			    </div>
			    
			    <div id="secondCategory" class="categoryBorder" style="display:none">
			    <h4 style="display:inline">2nd Order</h4>
			    <p id="secondAdd" style="display:inline;float:right">
			      <a class="btn btn-lg btn-primary" onclick="activeThirdCategory()"><i class="glyphicon glyphicon-plus"></i></a>
				  <a class="btn btn-lg btn-primary" onclick="deactiveSecondCategory()"><i class="glyphicon glyphicon-trash"></i></a>
			    </p><br><br>
			    <div class="form-group" id="category2Error">
			      <label class="control-label col-sm-2" for="category2">Category:</label>
			      <div class="col-sm-10">
			        <input type="text" class="form-control" id="category2" name="category2" placeholder="Enter category" onKeyDown="limitText(this,44);">
			      	<div id="category2ErrorMsg" style="color:#a94442;display:none;">Category field must be provided for this email.</div>
			      </div>
			    </div>
			    <div class="form-group" id="categoryEmail2Error">
			      <label class="control-label col-sm-2" for="categoryEmail2">Email:</label>
			      <div class="col-sm-10">
			        <input type="email" class="form-control" id="categoryEmail2" name="categoryEmail2" placeholder="Enter email" onKeyDown="limitText(this,147);">
			      	<div id="categoryEmail2ErrorMsg" style="color:#a94442;display:none;">Email field must be provided for this category.</div>
			      	<div id="categoryEmail2ErrorMsg2" style="color:#a94442;display:none;">Email field is not in the correct format.</div>
			      </div>
			    </div>
			    </div><div id="thirdCategory" class="categoryBorder" style="display:none">
			    <h4 style="display:inline">3rd Order</h4>
			    <p id="thirdAdd" style="display:inline;float:right">
			      <a class="btn btn-lg btn-primary" onclick="activeFourthCategory()"><i class="glyphicon glyphicon-plus"></i></a>
				  <a class="btn btn-lg btn-primary" onclick="deactiveThirdCategory()"><i class="glyphicon glyphicon-trash"></i></a>
			    </p><br><br>
			    <div class="form-group" id="category3Error">
			      <label class="control-label col-sm-2" for="category3">Category:</label>
			      <div class="col-sm-10">
			        <input type="text" class="form-control" id="category3" name="category3" placeholder="Enter category" onKeyDown="limitText(this,44);">
			      	<div id="category3ErrorMsg" style="color:#a94442;display:none;">Category field must be provided for this email.</div>
			      </div>
			    </div>
			    <div class="form-group" id="categoryEmail3Error">
			      <label class="control-label col-sm-2" for="categoryEmail3">Email:</label>
			      <div class="col-sm-10">
			        <input type="email" class="form-control" id="categoryEmail3" name="categoryEmail3" placeholder="Enter email" onKeyDown="limitText(this,147);">
			      	<div id="categoryEmail3ErrorMsg" style="color:#a94442;display:none;">Email field must be provided for this category.</div>
			      	<div id="categoryEmail3ErrorMsg2" style="color:#a94442;display:none;">Email field is not in the correct format.</div>
			      </div>
			    </div>
			    </div><div id="fourthCategory" class="categoryBorder" style="display:none">
			    <h4 style="display:inline">4th Order</h4>
			    <p id="fourthAdd" style="display:inline;float:right">
			      <a class="btn btn-lg btn-primary" onclick="activeFifthCategory()"><i class="glyphicon glyphicon-plus"></i></a>
				  <a class="btn btn-lg btn-primary" onclick="deactiveFourthCategory()"><i class="glyphicon glyphicon-trash"></i></a>
			    </p><br><br>
			    <div class="form-group" id="category4Error">
			      <label class="control-label col-sm-2" for="category4">Category:</label>
			      <div class="col-sm-10">
			        <input type="text" class="form-control" id="category4" name="category4" placeholder="Enter category" onKeyDown="limitText(this,44);">
			      	<div id="category4ErrorMsg" style="color:#a94442;display:none;">Category field must be provided for this email.</div>
			      </div>
			    </div>
			    <div class="form-group" id="categoryEmail4Error">
			      <label class="control-label col-sm-2" for="categoryEmail4">Email:</label>
			      <div class="col-sm-10">
			        <input type="email" class="form-control" id="categoryEmail4" name="categoryEmail4" placeholder="Enter email" onKeyDown="limitText(this,147);">
			      	<div id="categoryEmail4ErrorMsg" style="color:#a94442;display:none;">Email field must be provided for this category.</div>
			      	<div id="categoryEmail4ErrorMsg2" style="color:#a94442;display:none;">Email field is not in the correct format.</div>
			      </div>
			    </div>
			    </div><div id="fifthCategory" class="categoryBorder" style="display:none">
			    <h4 style="display:inline">5th Order</h4>
			    <p id="fifthAdd" style="display:inline;float:right">
				  <a class="btn btn-lg btn-primary" onclick="deactiveFifthCategory()"><i class="glyphicon glyphicon-trash"></i></a>
			    </p><br><br>
			    <div class="form-group" id="category5Error">
			      <label class="control-label col-sm-2" for="category5">Category:</label>
			      <div class="col-sm-10">
			        <input type="text" class="form-control" id="category5" name="category5" placeholder="Enter category" onKeyDown="limitText(this,44);">
			      	<div id="category5ErrorMsg" style="color:#a94442;display:none;">Category field must be provided for this email.</div>
			      </div>
			    </div>
			    <div class="form-group" id="categoryEmail5Error">
			      <label class="control-label col-sm-2" for="categoryEmail5">Email:</label>
			      <div class="col-sm-10">
			        <input type="email" class="form-control" id="categoryEmail5" name="categoryEmail5" placeholder="Enter email" onKeyDown="limitText(this,147);">
			      	<div id="categoryEmail5ErrorMsg" style="color:#a94442;display:none;">Email field must be provided for this category.</div>
			      	<div id="categoryEmail5ErrorMsg2" style="color:#a94442;display:none;">Email field is not in the correct format.</div>
			      </div>
			    </div>
			    </div>
			    <script>
			    var categoryListJs = '<%=categoryList%>';
			    var categoryEmailListJs = '<%=categoryEmailList%>';
			    categoryLoad(categoryListJs,categoryEmailListJs);
			    </script>
			    
			   <div class="form-group" align="right">        
		      	<div class="col-sm-offset-2 col-sm-10">
		        <button type="button" class="btn btn-lg btn-primary" onclick="validateCategory()">Save</button>
		      </div>
		    </div>
		    </form>
     		</div><!-- End of jumbotron -->
	      </div>
	    </div>
	  </div>

	  <!-- Submission Settings -->
	  <div class="panel panel-default">
	    <div class="panel-heading" role="tab" id="headingThree">
	      <h4 class="panel-title">
	        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
	          Submission Settings
	        </a>
	      </h4>
	    </div>
	    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
	      <div class="panel-body">
	      <div class="jumbotron" id="thankyouDiv">
	      <!-- Backend validation -->
	      <%if(errorSubmissionList.size() != 0){ %>
			<div class="alert alert-danger alert-error">
				<a class="close" data-dismiss="alert">&times;</a>
				<strong>Error!</strong> Please correct the following error:
				<ul>
				<%for(int i = 0; i<errorSubmissionList.size(); i++){ %>
				   	<li><%=errorSubmissionList.get(i) %></li>
				<%} %>
				</ul>
			</div>
			<%}%>
	      
	      	<form id="submissionFormId" class="form-horizontal" method="post" action="saveSubmission" role="form" enctype="multipart/form-data">
	  		<input type="hidden" id="adminId" name="adminId" value="<%=adminIdForAdmin%>">
	        <h4>Thank You Message</h4>
		    <div class="form-group" id="thankyouError">
		      <label class="control-label col-sm-2" for="preferredAdd">Message:</label>
		      <div class="col-sm-10">
	  			<input type="text" class="form-control" id="thankMessage" name="thankMessage" placeholder="Your message" value="<%=message%>" onKeyDown="limitText(this,44);">
		      	<div id="thankyouErrorMsg" style="color:#a94442;display:none;">Message field cannot be empty.</div>
		      </div>
		    </div><br>
		    <h4>Shown after user submit form</h4>
	        <p><img width="25%" src="adminSub?id=<%=adminIdForAdmin%>" id="incentiveImg"></p>
	        <p align="right">
		      <div class="btn btn-lg btn-primary btn-file">
			    <span>Upload</span>
			    <input type="file" id="incentive" name="incentive"/>
			  </div>
		    </p>
		    <div class="form-group" align="right">        
		      <div class="col-sm-offset-2 col-sm-10">
		        <button type="button" class="btn btn-lg btn-primary" onclick="validateSubmission()">Save</button>
		      </div>
		    </div>
		    </form>
		  </div> <!-- End of jumbotron -->
	      </div>
	    </div>
	  </div>
	  
	  <!-- Accounts Settings -->
	  <div class="panel panel-default">
	    <div class="panel-heading" role="tab" id="headingFour">
	      <h4 class="panel-title">
	        <a class="collapsed" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
	          Accounts Settings
	        </a>
	      </h4>
	    </div>
	    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
	      <div class="panel-body">
	      <div class="jumbotron" id="accountDiv">
	      <%if(errorAccountList.size() != 0){ %>
			<div class="alert alert-danger alert-error">
				<a class="close" data-dismiss="alert">&times;</a>
				<strong>Error!</strong> Please correct the following error:
				<ul>
				<%for(int i = 0; i<errorAccountList.size(); i++){ %>
				   	<li><%=errorAccountList.get(i) %></li>
				<%} %>
				</ul>
			</div>
			<%}%>
	      
	      	<form id="accountFormId" class="form-horizontal" method="post" action="saveAccount" role="form">
	  		<input type="hidden" id="adminId" name="adminId" value="<%=adminIdForAdmin%>">
		    <div class="form-group" id="nameError">
		    <div class="input-group input-group-lg">
	  			<span class="input-group-addon">
	    			<span class="glyphicon glyphicon-user white"></span>
	 		    </span>
	 		    <%if(!name.equals("")){ %>
	  				<input class="form-control" type="text" id="name" name="name" placeholder="Name" value="<%=name%>" onKeyDown="limitText(this,44);">
	  			<%}else{ %>
	  				<input class="form-control" type="text" id="name" name="name" placeholder="Name" onKeyDown="limitText(this,44);">
	  			<%}%>
			</div>
			<div id="nameErrorMsg" style="color:#a94442;display:none;">Name field cannot be empty.</div>
			</div>
		    <div class="form-group" id="emailError">
		    <div class="input-group input-group-lg">
	  			<span class="input-group-addon">
	    			<span class="glyphicon glyphicon-envelope white"></span>
	 		    </span>
	 		    <%if(!email.equals("")){ %>
	  				<input class="form-control" type="text" id="email" name="email" placeholder="Email" value="<%=email%>" onKeyDown="limitText(this,147);">
	  			<%}else{ %>
	  				<input class="form-control" type="text" id="email" name="email" placeholder="Email" onKeyDown="limitText(this,147);">
	  			<%}%>
			</div>
			<div id="emailErrorMsg" style="color:#a94442;display:none;">Email field cannot be empty.</div>
			<div id="emailErrorMsg2" style="color:#a94442;display:none;">Email field is not in the correct format.</div>
			</div>
			<div class="form-group" id="secondEmailError">
		    <div class="input-group input-group-lg">
	  			<span class="input-group-addon">
	    			<span class="glyphicon glyphicon-envelope white"></span>
	 		    </span>
	 		    <%if(!secondEmail.equals("")){ %>
	  				<input class="form-control" type="text" id="secondEmail" name="secondEmail" placeholder="Second Email" value="<%=secondEmail%>" onKeyDown="limitText(this,147);">
	  			<%}else{ %>
	  				<input class="form-control" type="text" id="secondEmail" name="secondEmail" placeholder="Second Email" onKeyDown="limitText(this,147);">
	  			<%}%>
			</div>
			<div id="secondEmailErrorMsg2" style="color:#a94442;display:none;">Second Email field is not in the correct format.</div>
			</div>
			<div class="form-group" id="oldPwdError">
			<div class="input-group input-group-lg">
			  <span class="input-group-addon">
			    <span class="glyphicon glyphicon-lock white"></span>
			  </span>
				<input class="form-control" type="password" id="oldPwd" name="oldPwd" placeholder="Old Password">
			</div>
			<div id="oldPwdErrorMsg" style="color:#a94442;display:none;">Old Password field cannot be empty.</div>
			<div id="oldPwdErrorMsg2" style="color:#a94442;display:none;">Old Password field must be between 8 to 15 characters.</div>
			</div>
			<div class="form-group" id="newPwdError">
			<div class="input-group input-group-lg">
			  <span class="input-group-addon">
			    <span class="glyphicon glyphicon-lock white"></span>
			  </span>
				<input class="form-control" type="password" id="pwd" name="pwd" placeholder="New Password">
			</div>
			<div id="newPwdErrorMsg" style="color:#a94442;display:none;">New Password field cannot be empty.</div>
			<div id="newPwdErrorMsg2" style="color:#a94442;display:none;">New Password field must be between 8 to 15 characters.</div>
			</div>
			<div class="form-group" id="confirmPwdError">
			<div class="input-group input-group-lg">
			  <span class="input-group-addon">
			    <span class="glyphicon glyphicon-ok white"></span>
			  </span>
				<input class="form-control" type="password" id="confirmPwd" name="confirmPwd" placeholder="Confirm New Password">
			</div>
			<div id="confirmPwdErrorMsg" style="color:#a94442;display:none;">Confirm Password field cannot be empty.</div>
			<div id="confirmPwdErrorMsg2" style="color:#a94442;display:none;">Confirm Password field must be between 8 to 15 characters.</div>
			<div id="confirmPwdErrorMsg3" style="color:#a94442;display:none;">Confirm Password do not match New Password.</div>
			</div>
			
		    <div class="form-group" align="right">        
		      <div class="col-sm-offset-2 col-sm-10">
		        <button type="button" class="btn btn-lg btn-primary" onclick="validateAccount()">Save</button>
		      </div>
		    </div>
		    </form>
		  </div>  <!-- End of jumbotron -->
	      </div>
	    </div>
	  </div>
	</div><!-- End accordion -->
	
	<p align="right">
      <a class="btn btn-lg btn-primary" href="logout.jsp" role="button">Log Out</a>
    </p>
	
	
</div>

</body>
</html>