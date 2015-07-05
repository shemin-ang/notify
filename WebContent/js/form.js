/* Scripts belongs to notify
 * 
 * */
var categoryListJS;
$(document).ready(function() {	
	$('#loading').hide();

	var text_max = 1000;
    $('#textarea_feedback').html(text_max + ' characters remaining');

    $('#descriptions').keyup(function() {
        var text_length = $('#descriptions').val().length;
        var text_remaining = text_max - text_length;

        $('#textarea_feedback').html(text_remaining + ' characters remaining');
    }); 
    
    //Upload photo section
    $("#photo").change(function(){
		var isValid = validateFile("photo");
		if(isValid == true){
			if(this.files[0].size < 5000000){
				readPhotoURL(this);
			}else{
				bootbox.alert("Please reduce the file size to less than 5 MB");
			}	
		}
	});
    
    $('#datetimepicker').datetimepicker();
   
    //Display current date time to user as default
    var currentdate = new Date();
    var currentMinute = currentdate.getMinutes();
    if(currentdate.getMinutes().toString().length == 1){
    	currentMinute = "0"+currentdate.getMinutes();
    }
    var currentMonth = (currentdate.getMonth()+1).toString();
    if(currentMonth.length == 1){
    	currentMonth = "0"+currentMonth;
    }
    var currentDay = currentdate.getDate().toString();
    if(currentDay.length == 1){
    	currentDay = "0"+currentDay;
    }
    var hours = currentdate.getHours();
    var mid='AM';
    if(hours>11){
	    mid='PM';
    }
    if (hours > 12) {
    	hours = hours-12;
    }
    if (hours == 0) {
    	hours = 12;
    }
    var datetime = currentMonth + "/" +  currentDay + "/" + currentdate.getFullYear() + " "  
                    + hours + ":" + currentMinute + " " + mid;
                    
    document.getElementById("datetimepicker").value = datetime; 
    
  //Timing when user keys in private address
	//setup before functions
	var typingTimer;                //timer identifier
	var doneTypingInterval = 2000;  //time in ms, 3 second for example

	//on keyup, start the countdown
	$('#otherCategory').keyup(function(){
	    clearTimeout(typingTimer);
	    typingTimer = setTimeout(doneTyping, doneTypingInterval);
	});

	//on keydown, clear the countdown 
	$('#otherCategory').keydown(function(){
	    clearTimeout(typingTimer);
	});
    
});
function imageIsLoaded(e) {
    $('#uploadImg').attr('src', e.target.result);
};
function loadCategoryList(categoryOrderList,categoryList) {
    var select = document.getElementById("categoryDD");
    if(categoryList == "[]"){
    	select.options.add(new Option("Others", "0"));
    	document.getElementById("showHideOthers").style.display = 'block';
    }else{
    	var categoryOrderSplit = categoryOrderList.substring(1,categoryOrderList.length-1).split(",");
    	var categorySplit = categoryList.substring(1,categoryList.length-1).split(",");
    	for(var i = 0; i < categorySplit.length; i++) {
    		select.options.add(new Option(categorySplit[i], categoryOrderSplit[i]));
        }
    	select.options.add(new Option("Others", "0"));
    	document.getElementById("showHideOthers").style.display = 'none';
    }
};
function displayOthersTextbox(){
	var select = document.getElementById("categoryDD");
	if(select.value == "0"){
		document.getElementById("showHideOthers").style.display = 'block';
	}else{
		document.getElementById("showHideOthers").style.display = 'none';
	}
}
function validateFile(id) {
    var allowedExtension = ['jpeg', 'jpg','png'];
    var fileExtension = document.getElementById(id).value.split('.').pop().toLowerCase();
    var isValidFile = false;
    for(var index in allowedExtension) {
        if(fileExtension === allowedExtension[index]) {
            isValidFile = true; 
            break;
        }
    }
    if(!isValidFile) {
    	bootbox.alert('Allowed Extensions are : *.' + allowedExtension.join(', *.'));
    }
    return isValidFile;
}
function readPhotoURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#uploadImg').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
function noContact(){
	 document.getElementById("contactId").style.display = "none";
	 document.getElementById("noPill").className = "active";
	 document.getElementById("yesPill").className = "";
}
function yesContact(){
	document.getElementById("contactId").style.display = "block";
	document.getElementById("noPill").className = "";
	document.getElementById("yesPill").className = "active";
}
function doneTyping () {
	var categoryListJSSplit = categoryListJS.substring(1,categoryListJS.length-1).split(",");
	var sameCategory = false;
	var sameCategoryIndex = 0;
	for(var i = 0; i<categoryListJSSplit.length; i++){
		var trimCategory = jQuery.trim(categoryListJSSplit[i]);
		if(document.getElementById("otherCategory").value.toUpperCase() == trimCategory.toUpperCase()){
			sameCategory = true;
			sameCategoryIndex = i;
			break;
		}
	}
	if(sameCategory == true){
		document.getElementById("categoryDD").selectedIndex = sameCategoryIndex;
		document.getElementById("otherCategory").value="";
		document.getElementById("showHideOthers").style.display = "none";
		document.getElementById("otherCategoryErrorMsg").style.display = "none";
		document.getElementById("showHideOthers").className = "form-group";
	}
}
function validateForm(){
	var validForm = true;
	var isnum = /^\d+$/.test(document.getElementById("telephone").value.trim());
	if(isnum == false){
		if(document.getElementById("telephone").value.trim() != ""){
			validForm = false;
			document.getElementById("contactDiv").scrollIntoView();
			document.getElementById("telephoneErrorMsg2").style.display = "none";
			document.getElementById("telephoneErrorMsg").style.display = "block";
			document.getElementById("telephoneError").className = "form-group has-error";
		}
	}else if(document.getElementById("telephone").value.trim().length != 8){
		validForm = false;
		document.getElementById("contactDiv").scrollIntoView();
		document.getElementById("telephoneErrorMsg").style.display = "none";
		document.getElementById("telephoneErrorMsg2").style.display = "block";
		document.getElementById("telephoneError").className = "form-group has-error";
	}else{
		document.getElementById("telephoneErrorMsg").style.display = "none";
		document.getElementById("telephoneErrorMsg2").style.display = "none";
		document.getElementById("telephoneError").className = "form-group";	
	}
	if(document.getElementById("yesPill").className == "active"){
		if(document.getElementById("email").value.trim() == ""){
			validForm = false;
			document.getElementById("contactDiv").scrollIntoView();
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
		    	validForm = false;
				document.getElementById("contactDiv").scrollIntoView();
				document.getElementById("emailErrorMsg").style.display = "none";
				document.getElementById("emailErrorMsg2").style.display = "block";
				document.getElementById("emailError").className = "form-group has-error";
		    }
			
		}
	}
	if(document.getElementById("descriptions").value.trim() == ""){
		validForm = false;
		document.getElementById("descriptionDiv").scrollIntoView();
		document.getElementById("descriptionErrorMsg").style.display = "block";
		document.getElementById("descriptionError").className = "form-group has-error";
	}else{
		document.getElementById("descriptionErrorMsg").style.display = "none";
		document.getElementById("descriptionError").className = "form-group";
	}
	if(document.getElementById("showHideOthers").style.display == "block"){
		if(document.getElementById("otherCategory").value.trim() == ""){
			validForm = false;
			document.getElementById("categoryDiv").scrollIntoView();
			document.getElementById("otherCategoryErrorMsg").style.display = "block";
			document.getElementById("showHideOthers").className = "form-group has-error";
		}else{
			document.getElementById("otherCategoryErrorMsg").style.display = "none";
			document.getElementById("showHideOthers").className = "form-group";	
		}
	}
	if(document.getElementById("location").value.trim() == ""){
		validForm = false;
		document.getElementById("locationDiv").scrollIntoView();
		document.getElementById("locationErrorMsg").style.display = "block";
		document.getElementById("locationError").className = "form-group has-error";	
	}else{
		document.getElementById("locationErrorMsg").style.display = "none";
		document.getElementById("locationError").className = "form-group";	
	}
	var currentdate = new Date();
	if(document.getElementById("datetimepicker").value.trim() == ""){
		validForm = false;
		document.getElementById("datetimepickerDiv").scrollIntoView();
		document.getElementById("dateTimeErrorMsg").style.display = "block";
		document.getElementById("dateTimeErrorMsg2").style.display = "none";
		document.getElementById("datetimepickerError").className = "form-group has-error";	
	}else if(currentdate > new Date(document.getElementById("datetimepicker").value) == false){
		validForm = false;
		document.getElementById("datetimepickerDiv").scrollIntoView();
		document.getElementById("dateTimeErrorMsg").style.display = "none";
		document.getElementById("dateTimeErrorMsg2").style.display = "block";
		document.getElementById("datetimepickerError").className = "form-group has-error";	
	}else{
		document.getElementById("dateTimeErrorMsg").style.display = "none";
		document.getElementById("dateTimeErrorMsg2").style.display = "none";
		document.getElementById("datetimepickerError").className = "form-group";
	}
	
	//Submit form if all field are valid
	if(validForm == true){
		$('#loading').show();
		document.getElementById("reportFormId").submit();
	}
	
}
function limitText(limitField, limitNum) {
    if (limitField.value.length > limitNum) {
        limitField.value = limitField.value.substring(0, limitNum);
    }
}