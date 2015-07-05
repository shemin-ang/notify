<%@include file="navbar/navbar.jsp" %>
<%@ page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home Page</title>
<script>
function chooseCompany(id) {
	document.getElementById("adminId").value = id;
    document.getElementById("submitCompany").submit();
}
</script>
<link href="css/loadLogo.css" rel="stylesheet">
<style>
img{
	cursor: pointer; 
	cursor: hand;
}
</style>
</head>
<body>
<%
String firstEntry = (String)request.getAttribute("firstEntry");
String errorMsg = "";
ArrayList<String> adminList = new ArrayList<String>();
if(firstEntry == null){%>
	<script>
	if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition, showError);
    } else { 
    	$('#loadLogo').hide();
    	document.getElementById("locationError").style.display = 'block';
    	document.getElementById("demo").innerHTML = "Geolocation is not supported by this browser.";
    }
	function showPosition(position) {
		document.getElementById("currentLat").value = position.coords.latitude;
        document.getElementById("currentLng").value = position.coords.longitude;
        document.getElementById("submitCoord").submit();
	}
	function showError(error) {
		$('#loadLogo').hide();
		document.getElementById("locationError").style.display = 'block';
	    switch(error.code) {
	        case error.PERMISSION_DENIED:
	        	document.getElementById("demo").innerHTML = "User denied the request for Geolocation.";
	            break;
	        case error.POSITION_UNAVAILABLE:
	        	document.getElementById("demo").innerHTML = "Location information is unavailable.";
	            break;
	        case error.TIMEOUT:
	        	document.getElementById("demo").innerHTML = "The request to get user location timed out.";
	            break;
	        case error.UNKNOWN_ERROR:
	        	document.getElementById("demo").innerHTML = "An unknown error occurred.";
	            break;
	    }
	}
	</script>
<%}else{
	adminList = (ArrayList<String>)request.getAttribute("adminList");
	errorMsg = (String)request.getAttribute("errorMsg");%>
	<script>
	$(document).ready(function() {
		/*var snd = new Audio("img/home.wav"); // buffers automatically when created
		snd.play();*/
		setInterval(function () {
			$('#loadLogo').hide();
		}, 2000);
		document.getElementById("locationInfo").style.display = 'none';
	});
	</script>
<%}%>
<form method="post" id="submitCoord" action="nearestCompany">
	<input type="hidden" id="currentLat" name="currentLat">
	<input type="hidden" id="currentLng" name="currentLng">
</form>
<form method="post" id="submitCompany" action="choosenCompany">
	<input type="hidden" id="adminId" name="adminId">
</form>

<div id="loadLogo">
  <img id="loading-logo" src="img/ajax-loader.gif"/>
	<!-- <img id="loading-logo" src="img/notifylogo.png"/> -->
</div>

<div class="container">
	<!-- Appear below navbar -->
    <div class="alert alert-info" id="locationInfo">
	    <a class="close" data-dismiss="alert">&times;</a>
	    <strong>Heads up!</strong> Please ensure your location service is on!
	</div>
    <div class="alert alert-danger alert-error" id="locationError" style="display:none">
	    <a class="close" data-dismiss="alert">&times;</a>
	    <strong>Error!</strong> <p id="demo" style="display:inline"></p>
	</div>
	<%if(errorMsg.equals("")){ %>
	<div class="jumbotron">
		<h3>Nearby Establishments</h3>
		<%for(int i = 0; i<adminList.size(); i++){%>
			<p align="center"><img src="homeLogo?id=<%=adminList.get(i)%>" onclick="chooseCompany('<%=adminList.get(i)%>')" width="200px"></p>
		<%} %>
    </div>
     </div><!-- End of jumbotron -->
     <%}else{ %>
     	<div class="jumbotron">
		<div class="alert alert-danger alert-error">
		    <a class="close" data-dismiss="alert">&times;</a>
		    <strong>Error!</strong> <%=errorMsg %>
		</div>
	     </div><!-- End of jumbotron -->
     <%} %>
</div> <!-- End of container -->

</body>
</html>