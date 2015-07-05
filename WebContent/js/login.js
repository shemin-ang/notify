/* Scripts belongs to notify
 * 
 * */
$(document).ready(function() {
	//Submit form when "enter" is pressed
	$('#loginFormId').keypress(function(e){
		if(e.which == 13){
			validateLogin();           
		}
      });
});	

function limitText(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    }
}
function validateLogin(){
	var validLogin = true;
	
	if(document.getElementById("email").value.trim() == ""){
		validLogin = false;
		document.getElementById("emailErrorMsg2").style.display = "none";
		document.getElementById("emailErrorMsg").style.display = "block";
		document.getElementById("emailError").className = "form-group has-error";
	}else{
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		if(re.test(document.getElementById("email").value.trim())){
	    	document.getElementById("emailErrorMsg").style.display = "none";
	    	document.getElementById("emailErrorMsg2").style.display = "none";
			document.getElementById("emailError").className = "form-group";
	    }else{
	    	validLogin = false;
			document.getElementById("emailErrorMsg").style.display = "none";
			document.getElementById("emailErrorMsg2").style.display = "block";
			document.getElementById("emailError").className = "form-group has-error";
	    }
	}
	if(document.getElementById("pwd").value.trim() == ""){
		validLogin = false;
		document.getElementById("pwdErrorMsg2").style.display = "none";
		document.getElementById("pwdErrorMsg").style.display = "block";
		document.getElementById("pwdError").className = "form-group has-error";
	}else{
		if(document.getElementById("pwd").value.length < 8 || document.getElementById("pwd").value.length > 15){
			validLogin = false;
			document.getElementById("pwdErrorMsg2").style.display = "block";
			document.getElementById("pwdErrorMsg").style.display = "none";
			document.getElementById("pwdError").className = "form-group has-error";
		}else{
			document.getElementById("pwdErrorMsg").style.display = "none";
	    	document.getElementById("pwdErrorMsg2").style.display = "none";
			document.getElementById("pwdError").className = "form-group";
		}
	}
	
	//Submit form if all field are valid
	if(validLogin == true){
		document.getElementById("loginFormId").submit();
	}
}