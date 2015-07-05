<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
<link href="css/bootstrap.css" rel="stylesheet">
<link href="css/navbar.css" rel="stylesheet">

<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.min.js"></script>
<script>
$(document).ready(function() {
	$.ajax({
	    url: 'redirectUrl',
	    data: "url="+document.URL,
	    type: 'POST',
	    
	    success:function(response) {
	    	if(response == "success"){
	    		window.location.href = "form.jsp";
	    	}
	    }
	});	

});
</script>
</head>
<body>
<nav class="navbar navbar-default">
     <div class="container-fluid">
       <div class="navbar-header">
         <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
           <span class="sr-only">Toggle navigation</span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
         </button>
         <a class="navbar-brand" href="home.jsp">notify</a>
       </div>
     </div><!--/.container-fluid -->
</nav>

<footer class="footer">
  <div class="container">
    <table class="text-muted"><tr>
    <td><a class="footer-link" href="home.jsp">Home</a></td>
    <td><a class="footer-link" href="home.jsp">About Us</a></td>
    <td><a class="footer-link" href="home.jsp">Contact Us</a></td>
    <td><a class="footer-link" href="home.jsp">Help</a></td>
    </tr></table>
  </div>
</footer>

<div class='container'>
	<div class='jumbotron' align="center">
		<div class="alert alert-danger alert-error">
		    <a class="close" data-dismiss="alert">&times;</a>
		    <h3><strong>Error in Page!</strong></h3> 
		    <p>Either the url does not exist or it is written wrongly.</p>
			<p>Please check your url input again.</p>
		</div>
	</div>
</div>
</body>
</html>