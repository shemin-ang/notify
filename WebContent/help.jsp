<%@include file="navbar/navbarAdmin.jsp" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Help Page</title>
<style>
img{
	margin-left: auto;
	margin-right: auto;
}
</style>
</head>
<body>

<div class="container">
	<h1>Notify in 1, 2, 3</h1>
	<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
	  <!-- Indicators -->
	  <ol class="carousel-indicators">
	    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
	    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
	    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
	  </ol>
	
	  <!-- Wrapper for slides -->
	  <div class="carousel-inner" role="listbox">
	    <div class="item active">
	      <img src="img/help_1.png">
	      <div class="carousel-caption">
	        1st step: Select Incident Location
	      </div>
	    </div>
	    
	    <div class="item">
	      <img src="img/help_2.png">
	      <div class="carousel-caption">
	        2nd Step: Fill in Incident Report
	      </div>
	    </div>
	    
	    <div class="item">
	      <img src="img/help_3.png">
	      <div class="carousel-caption">
	        3rd Step: Receive your Appreciation of Thanks!
	      </div>
	    </div>
	    
	   <br><br><br> <!-- Spacing for caption -->    
	  </div>
	
	  <!-- Controls -->
	  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
	    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
	    <span class="sr-only">Previous</span>
	  </a>
	  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
	    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
	    <span class="sr-only">Next</span>
	  </a>
	</div>

</div> <!-- End of container -->


</body>
</html>