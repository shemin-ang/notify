/* Scripts belongs to notify
 * 
 * */
$(document).ready(function() {	
	var text_max = 99;
    $('#textarea_feedback').html(text_max + ' characters remaining');

    $('#feedback').keyup(function() {
        var text_length = $('#feedback').val().length;
        var text_remaining = text_max - text_length;

        $('#textarea_feedback').html(text_remaining + ' characters remaining');
    });   
});
function limitText(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    }
}
function validateContact(){
	var validContact = true;
	
	if(document.getElementById("email").value.trim() == ""){
		validContact = false;
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
	    	validContact = false;
			document.getElementById("emailErrorMsg").style.display = "none";
			document.getElementById("emailErrorMsg2").style.display = "block";
			document.getElementById("emailError").className = "form-group has-error";
	    }
	}
	
	if(document.getElementById("feedback").value.trim() == ""){
		validContact = false;
		document.getElementById("feedbackErrorMsg").style.display = "block";
		document.getElementById("feedbackError").className = "form-group has-error";
	}else{
		document.getElementById("feedbackErrorMsg").style.display = "none";
		document.getElementById("feedbackError").className = "form-group";
	}
	
	//Submit form if all field are valid
	if(validContact == true){
		document.getElementById("contactFormId").submit();
	}
}