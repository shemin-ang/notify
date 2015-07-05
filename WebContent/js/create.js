/* Scripts belongs to notify
 * 
 * */
function validateSuperLogin(){
	var validSuperLogin = true;
	if(document.getElementById("pwd").value.trim() == ""){
		validSuperLogin = false;
		document.getElementById("pwdErrorMsg2").style.display = "none";
		document.getElementById("pwdErrorMsg").style.display = "block";
		document.getElementById("pwdError").className = "form-group has-error";
	}else{
		if(document.getElementById("pwd").value.length < 8 || document.getElementById("pwd").value.length > 15){
			validSuperLogin = false;
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
	if(validSuperLogin == true){
		document.getElementById("createFormId").submit();
	}
}