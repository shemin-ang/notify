/* Scripts belongs to notify
 * 
 * */
function limitText(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    }
}
var noCompanyJs = "";
function noCompanyFunc(noCompany){
	noCompanyJs = noCompany;
}
function validateCompany(){
	var validCompany = true;
	if(document.getElementById("companyLocation").value.trim() == ""){
		validCompany = false;
		document.getElementById("locationError").scrollIntoView();
		document.getElementById("locationErrorMsg3").style.display = "none";
		document.getElementById("locationErrorMsg2").style.display = "none";
		document.getElementById("locationErrorMsg").style.display = "block";
		document.getElementById("locationError").className = "form-group has-error";
	}else if(document.getElementById("companyLocation").value.trim().length != 6){
		validCompany = false;
		document.getElementById("locationError").scrollIntoView();
		document.getElementById("locationErrorMsg2").style.display = "none";
		document.getElementById("locationErrorMsg").style.display = "none";
		document.getElementById("locationErrorMsg3").style.display = "block";
		document.getElementById("locationError").className = "form-group has-error";
	}else{
		var isnum = /^\d+$/.test(document.getElementById("companyLocation").value.trim());
		if(isnum == false){
			validCompany = false;
			document.getElementById("locationError").scrollIntoView();
			document.getElementById("locationErrorMsg").style.display = "none";
			document.getElementById("locationErrorMsg2").style.display = "block";
			document.getElementById("locationErrorMsg3").style.display = "none";
			document.getElementById("locationError").className = "form-group has-error";
		}else{
			document.getElementById("locationErrorMsg").style.display = "none";
			document.getElementById("locationErrorMsg2").style.display = "none";
			document.getElementById("locationErrorMsg3").style.display = "none";
			document.getElementById("locationError").className = "form-group";	
		}
	}

	if(noCompanyJs == "true"){
		if(document.getElementById("logo").value.trim() == ""){
			validCompany = false;
			document.getElementById("logoError").scrollIntoView();
			document.getElementById("logoErrorMsg").style.display = "block";
		}else{
			document.getElementById("logoErrorMsg").style.display = "none"; 	
		}
	}
	
	if(document.getElementById("preferredAdd").readOnly == false){
		if(document.getElementById("errorUrl").style.display == "block"){
			validCompany = false;
			document.getElementById("urlError").scrollIntoView();
			document.getElementById("urlError").className = "form-group has-error";
		}else{
			document.getElementById("urlError").className = "form-group";
		}
	}
	
	//Submit form if all field are valid
	if(validCompany == true){
		document.getElementById("companyFormId").submit();
	}
}
function validateCategory(){
	var validCategory = true;
	document.getElementById("categoryEmailErrorMsg").style.display = "none";
	document.getElementById("categoryEmailError").className = "form-group";
	document.getElementById("categoryErrorMsg").style.display = "none";
	document.getElementById("categoryError").className = "form-group";
	document.getElementById("categoryEmail2ErrorMsg").style.display = "none";
	document.getElementById("categoryEmail2Error").className = "form-group";
	document.getElementById("category2ErrorMsg").style.display = "none";
	document.getElementById("category2Error").className = "form-group";
	document.getElementById("categoryEmail3ErrorMsg").style.display = "none";
	document.getElementById("categoryEmail3Error").className = "form-group";
	document.getElementById("category3ErrorMsg").style.display = "none";
	document.getElementById("category3Error").className = "form-group";
	document.getElementById("categoryEmail4ErrorMsg").style.display = "none";
	document.getElementById("categoryEmail4Error").className = "form-group";
	document.getElementById("category4ErrorMsg").style.display = "none";
	document.getElementById("category4Error").className = "form-group";
	document.getElementById("categoryEmail5ErrorMsg").style.display = "none";
	document.getElementById("categoryEmail5Error").className = "form-group";
	document.getElementById("category5ErrorMsg").style.display = "none";
	document.getElementById("category5Error").className = "form-group";
	document.getElementById("categoryEmailErrorMsg2").style.display = "none";
	document.getElementById("categoryEmail2ErrorMsg2").style.display = "none";
	document.getElementById("categoryEmail3ErrorMsg2").style.display = "none";
	document.getElementById("categoryEmail4ErrorMsg2").style.display = "none";
	document.getElementById("categoryEmail5ErrorMsg2").style.display = "none";
	
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if(document.getElementById("category").value.trim() == ""){
		if(document.getElementById("categoryEmail").value.trim() != ""){
			validCategory = false;
			document.getElementById("firstCategory").scrollIntoView();
			document.getElementById("categoryErrorMsg").style.display = "block";
			document.getElementById("categoryError").className = "form-group has-error";
		}
	}
	if(document.getElementById("category").value.trim() != ""){
		if(document.getElementById("categoryEmail").value.trim() == ""){
			validCategory = false;
			document.getElementById("firstCategory").scrollIntoView();
			document.getElementById("categoryEmailErrorMsg").style.display = "block";
			document.getElementById("categoryEmailError").className = "form-group has-error";
		}else if(!re.test(document.getElementById("categoryEmail").value.trim())){
			validCategory = false;
			document.getElementById("categoryEmailErrorMsg2").style.display = "block";
			document.getElementById("categoryEmailError").className = "form-group has-error";
	    }
	}
	if(document.getElementById("category2").value.trim() == ""){
		if(document.getElementById("categoryEmail2").value.trim() != ""){
			validCategory = false;
			document.getElementById("secondCategory").scrollIntoView();
			document.getElementById("category2ErrorMsg").style.display = "block";
			document.getElementById("category2Error").className = "form-group has-error";
		}
	}
	if(document.getElementById("category2").value.trim() != ""){
		if(document.getElementById("categoryEmail2").value.trim() == ""){
			validCategory = false;
			document.getElementById("secondCategory").scrollIntoView();
			document.getElementById("categoryEmail2ErrorMsg").style.display = "block";
			document.getElementById("categoryEmail2Error").className = "form-group has-error";
		}else if(!re.test(document.getElementById("categoryEmail2").value.trim())){
			validCategory = false;
			document.getElementById("categoryEmail2ErrorMsg2").style.display = "block";
			document.getElementById("categoryEmail2Error").className = "form-group has-error";
	    }
	}
	if(document.getElementById("category3").value.trim() == ""){
		if(document.getElementById("categoryEmail3").value.trim() != ""){
			validCategory = false;
			document.getElementById("thirdCategory").scrollIntoView();
			document.getElementById("category3ErrorMsg").style.display = "block";
			document.getElementById("category3Error").className = "form-group has-error";
		}
	}
	if(document.getElementById("category3").value.trim() != ""){
		if(document.getElementById("categoryEmail3").value.trim() == ""){
			validCategory = false;
			document.getElementById("thirdCategory").scrollIntoView();
			document.getElementById("categoryEmail3ErrorMsg").style.display = "block";
			document.getElementById("categoryEmail3Error").className = "form-group has-error";
		}else if(!re.test(document.getElementById("categoryEmail3").value.trim())){
			validCategory = false;
			document.getElementById("categoryEmail3ErrorMsg2").style.display = "block";
			document.getElementById("categoryEmail3Error").className = "form-group has-error";
	    }
	}
	if(document.getElementById("category4").value.trim() == ""){
		if(document.getElementById("categoryEmail4").value.trim() != ""){
			validCategory = false;
			document.getElementById("fourthCategory").scrollIntoView();
			document.getElementById("category4ErrorMsg").style.display = "block";
			document.getElementById("category4Error").className = "form-group has-error";
		}
	}
	if(document.getElementById("category4").value.trim() != ""){
		if(document.getElementById("categoryEmail4").value.trim() == ""){
			validCategory = false;
			document.getElementById("fourthCategory").scrollIntoView();
			document.getElementById("categoryEmail4ErrorMsg").style.display = "block";
			document.getElementById("categoryEmail4Error").className = "form-group has-error";
		}else if(!re.test(document.getElementById("categoryEmail4").value.trim())){
			validCategory = false;
			document.getElementById("categoryEmail4ErrorMsg2").style.display = "block";
			document.getElementById("categoryEmail4Error").className = "form-group has-error";
	    }
	}
	if(document.getElementById("category5").value.trim() == ""){
		if(document.getElementById("categoryEmail5").value.trim() != ""){
			validCategory = false;
			document.getElementById("fifthCategory").scrollIntoView();
			document.getElementById("category5ErrorMsg").style.display = "block";
			document.getElementById("category5Error").className = "form-group has-error";
		}
	}
	if(document.getElementById("category5").value.trim() != ""){
		if(document.getElementById("categoryEmail5").value.trim() == ""){
			validCategory = false;
			document.getElementById("fifthCategory").scrollIntoView();
			document.getElementById("categoryEmail5ErrorMsg").style.display = "block";
			document.getElementById("categoryEmail5Error").className = "form-group has-error";
		}else if(!re.test(document.getElementById("categoryEmail5").value.trim())){
			validCategory = false;
			document.getElementById("categoryEmail5ErrorMsg2").style.display = "block";
			document.getElementById("categoryEmail5Error").className = "form-group has-error";
	    }
	}
	
	//Submit form if all field are valid
	if(validCategory == true){
		document.getElementById("categoryFormId").submit();
	}
}
function validateSubmission(){
	var validSubmission = true;
	
	if(document.getElementById("thankMessage").value.trim() == ""){
		validSubmission = false;
		document.getElementById("thankyouDiv").scrollIntoView();
		document.getElementById("thankyouErrorMsg").style.display = "block";
		document.getElementById("thankyouError").className = "form-group has-error";
	}else{
		document.getElementById("thankyouErrorMsg").style.display = "none";
		document.getElementById("thankyouError").className = "form-group";
	}
	
	//Submit form if all field are valid
	if(validSubmission == true){
		document.getElementById("submissionFormId").submit();
	}
}
function validateAccount(){
	var validAccount = true;
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	
	if(document.getElementById("name").value.trim() == ""){
		validAccount = false;
		document.getElementById("accountDiv").scrollIntoView();
		document.getElementById("nameErrorMsg").style.display = "block";
		document.getElementById("nameError").className = "form-group has-error";
	}else{
		document.getElementById("nameErrorMsg").style.display = "none";
		document.getElementById("nameError").className = "form-group";
	}
	if(document.getElementById("email").value.trim() == ""){
		validAccount = false;
		document.getElementById("accountDiv").scrollIntoView();
		document.getElementById("emailErrorMsg2").style.display = "none";
		document.getElementById("emailErrorMsg").style.display = "block";
		document.getElementById("emailError").className = "form-group has-error";
	}else{
		if(re.test(document.getElementById("email").value.trim())){
	    	document.getElementById("emailErrorMsg").style.display = "none";
	    	document.getElementById("emailErrorMsg2").style.display = "none";
			document.getElementById("emailError").className = "form-group";
	    }else{
	    	validAccount = false;
			document.getElementById("accountDiv").scrollIntoView();
			document.getElementById("emailErrorMsg").style.display = "none";
			document.getElementById("emailErrorMsg2").style.display = "block";
			document.getElementById("emailError").className = "form-group has-error";
	    }
	}
	if(document.getElementById("secondEmail").value.trim() != ""){
		if(re.test(document.getElementById("secondEmail").value.trim())){
	    	document.getElementById("secondEmailErrorMsg2").style.display = "none";
			document.getElementById("secondEmailError").className = "form-group";
	    }else{
	    	validAccount = false;
			document.getElementById("accountDiv").scrollIntoView();
			document.getElementById("secondEmailErrorMsg2").style.display = "block";
			document.getElementById("secondEmailError").className = "form-group has-error";
	    }
	}
	if(document.getElementById("pwd").value.trim() != "" || document.getElementById("confirmPwd").value.trim() != ""){
		if(document.getElementById("oldPwd").value.trim() == ""){
			validAccount = false;
			document.getElementById("accountDiv").scrollIntoView();
			document.getElementById("oldPwdErrorMsg2").style.display = "none";
			document.getElementById("oldPwdErrorMsg").style.display = "block";
			document.getElementById("oldPwdError").className = "form-group has-error";
		}else{
			if(document.getElementById("oldPwd").value.length < 8 || document.getElementById("oldPwd").value.length > 15){
				validAccount = false;
				document.getElementById("accountDiv").scrollIntoView();
				document.getElementById("oldPwdErrorMsg2").style.display = "block";
				document.getElementById("oldPwdErrorMsg").style.display = "none";
				document.getElementById("oldPwdError").className = "form-group has-error";
			}else{
				document.getElementById("oldPwdErrorMsg").style.display = "none";
		    	document.getElementById("oldPwdErrorMsg2").style.display = "none";
				document.getElementById("oldPwdError").className = "form-group";
			}
		}
	}
	if(document.getElementById("oldPwd").value.trim() != "" || document.getElementById("confirmPwd").value.trim() != ""){
		if(document.getElementById("pwd").value.trim() == ""){
			validAccount = false;
			document.getElementById("accountDiv").scrollIntoView();
			document.getElementById("newPwdErrorMsg2").style.display = "none";
			document.getElementById("newPwdErrorMsg").style.display = "block";
			document.getElementById("newPwdError").className = "form-group has-error";
		}else{
			if(document.getElementById("pwd").value.length < 8 || document.getElementById("pwd").value.length > 15){
				validAccount = false;
				document.getElementById("accountDiv").scrollIntoView();
				document.getElementById("newPwdErrorMsg2").style.display = "block";
				document.getElementById("newPwdErrorMsg").style.display = "none";
				document.getElementById("newPwdError").className = "form-group has-error";
			}else{
				document.getElementById("newPwdErrorMsg").style.display = "none";
		    	document.getElementById("newPwdErrorMsg2").style.display = "none";
				document.getElementById("newPwdError").className = "form-group";
			}
		}
	}
	if(document.getElementById("oldPwd").value.trim() != "" || document.getElementById("pwd").value.trim() != ""){
		if(document.getElementById("confirmPwd").value.trim() == ""){
			validAccount = false;
			document.getElementById("accountDiv").scrollIntoView();
			document.getElementById("confirmPwdErrorMsg3").style.display = "none";
			document.getElementById("confirmPwdErrorMsg2").style.display = "none";
			document.getElementById("confirmPwdErrorMsg").style.display = "block";
			document.getElementById("confirmPwdError").className = "form-group has-error";
		}else{
			if(document.getElementById("confirmPwd").value.length < 8 || document.getElementById("confirmPwd").value.length > 15){
				validAccount = false;
				document.getElementById("accountDiv").scrollIntoView();
				document.getElementById("confirmPwdErrorMsg3").style.display = "none";
				document.getElementById("confirmPwdErrorMsg2").style.display = "block";
				document.getElementById("confirmPwdErrorMsg").style.display = "none";
				document.getElementById("confirmPwdError").className = "form-group has-error";
			}else if(document.getElementById("confirmPwd").value != document.getElementById("pwd").value){
				validAccount = false;
				document.getElementById("accountDiv").scrollIntoView();
				document.getElementById("confirmPwdErrorMsg3").style.display = "block";
				document.getElementById("confirmPwdErrorMsg2").style.display = "none";
				document.getElementById("confirmPwdErrorMsg").style.display = "none";
				document.getElementById("confirmPwdError").className = "form-group has-error";
			}else{
				document.getElementById("confirmPwdErrorMsg").style.display = "none";
		    	document.getElementById("confirmPwdErrorMsg2").style.display = "none";
		    	document.getElementById("confirmPwdErrorMsg3").style.display = "none";
				document.getElementById("confirmPwdError").className = "form-group";
			}
		}
	}
	
	//Submit form if all field are valid
	if(validAccount == true){
		document.getElementById("accountFormId").submit();
	}
}