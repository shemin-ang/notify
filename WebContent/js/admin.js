/* Scripts belongs to notify
 * 
 * */
$(document).ready(function() {
	$("#logo").change(function(){
		var isValid = validateFile("logo");
		if(isValid == true){
			if(this.files[0].size < 5000000){
				readLogoURL(this);
			}else{
				bootbox.alert("Please reduce the file size to less than 5 MB");
			}	
		}
	});
	$("#incentive").change(function(){
		var isValid = validateFile("incentive");
		if(isValid == true){
			if(this.files[0].size < 5000000){
				readThankURL(this);
			}else{
				bootbox.alert("Please reduce the file size to less than 5 MB");
			}	
		}
	});
	
	//Timing when user keys in private address
	//setup before functions
	var typingTimer;                //timer identifier
	var doneTypingInterval = 2000;  //time in ms, 3 second for example

	//on keyup, start the countdown
	$('#preferredAdd').keyup(function(){
	    clearTimeout(typingTimer);
	    typingTimer = setTimeout(doneTyping, doneTypingInterval);
	});

	//on keydown, clear the countdown 
	$('#preferredAdd').keydown(function(){
	    clearTimeout(typingTimer);
	});
});
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
    	bootbox.alert("Allowed Extensions are : *." + allowedExtension.join(', *.'));
    }
    return isValidFile;
}
function readLogoURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#logoImg').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
function readThankURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#incentiveImg').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
function imageIsLoaded(e) {
    $('#logoImg').attr('src', e.target.result);
};
function doneTyping () {
	$.ajax({
	    url: 'checkUrl',
	    data: "adminUrl="+document.getElementById("preferredAdd").value,
	    type: 'POST',
	    
	    success:function(response) {
	    	if(response == "available"){
	    		document.getElementById("successUrl").style.display = 'block';
	    		document.getElementById("errorUrl").style.display = 'none';
	    		document.getElementById("urlOutcomeSuccess").innerHTML = "This address is available.";
	    	}else{
	    		document.getElementById("successUrl").style.display = 'none';
	    		document.getElementById("errorUrl").style.display = 'block';
	    		document.getElementById("urlOutcomeError").innerHTML = "This address is already taken.";
	    	}
	    }
	});
}
function activeSecondCategory(){
	document.getElementById("firstAdd").style.display = 'none';
	document.getElementById("secondCategory").style.display = 'block';
	document.getElementById("secondAdd").style.display = 'block';
}
function deactiveSecondCategory(){
	document.getElementById("category2").value="";
	document.getElementById("categoryEmail2").value="";
	document.getElementById("firstAdd").style.display = 'block';
	document.getElementById("secondCategory").style.display = 'none';
	document.getElementById("secondAdd").style.display = 'none';
}
function activeThirdCategory(){
	document.getElementById("secondAdd").style.display = 'none';
	document.getElementById("thirdCategory").style.display = 'block';
	document.getElementById("thirdAdd").style.display = 'block';
}
function deactiveThirdCategory(){
	document.getElementById("category3").value="";
	document.getElementById("categoryEmail3").value="";
	document.getElementById("secondAdd").style.display = 'block';
	document.getElementById("thirdCategory").style.display = 'none';
	document.getElementById("thirdAdd").style.display = 'none';
}
function activeFourthCategory(){
	document.getElementById("thirdAdd").style.display = 'none';
	document.getElementById("fourthCategory").style.display = 'block';
	document.getElementById("fourthAdd").style.display = 'block';
}
function deactiveFourthCategory(){
	document.getElementById("category4").value="";
	document.getElementById("categoryEmail4").value="";
	document.getElementById("thirdAdd").style.display = 'block';
	document.getElementById("fourthCategory").style.display = 'none';
	document.getElementById("fourthAdd").style.display = 'none';
}
function activeFifthCategory(){
	document.getElementById("fourthAdd").style.display = 'none';
	document.getElementById("fifthCategory").style.display = 'block';
}
function deactiveFifthCategory(){
	document.getElementById("category5").value="";
	document.getElementById("categoryEmail5").value="";
	document.getElementById("fourthAdd").style.display = 'block';
	document.getElementById("fifthCategory").style.display = 'none';
}
function categoryLoad(categoryList,categoryEmailList){
	var categoryArr = categoryList.substring(1,categoryList.length-1).split(",");
	var categoryEmailArr = categoryEmailList.substring(1,categoryEmailList.length-1).split(",");
	if(categoryArr.length==1){
		document.getElementById("category").value=categoryArr[0];
		document.getElementById("categoryEmail").value=categoryEmailArr[0];
	}else if(categoryArr.length==2){
		activeSecondCategory();
		document.getElementById("category").value=categoryArr[0];
		document.getElementById("categoryEmail").value=categoryEmailArr[0];
		document.getElementById("category2").value=categoryArr[1];
		document.getElementById("categoryEmail2").value=categoryEmailArr[1];
	}else if(categoryArr.length==3){
		activeSecondCategory();
		activeThirdCategory();
		document.getElementById("category").value=categoryArr[0];
		document.getElementById("categoryEmail").value=categoryEmailArr[0];
		document.getElementById("category2").value=categoryArr[1];
		document.getElementById("categoryEmail2").value=categoryEmailArr[1];
		document.getElementById("category3").value=categoryArr[2];
		document.getElementById("categoryEmail3").value=categoryEmailArr[2];
	}else if(categoryArr.length==4){
		activeSecondCategory();
		activeThirdCategory();
		activeFourthCategory();
		document.getElementById("category").value=categoryArr[0];
		document.getElementById("categoryEmail").value=categoryEmailArr[0];
		document.getElementById("category2").value=categoryArr[1];
		document.getElementById("categoryEmail2").value=categoryEmailArr[1];
		document.getElementById("category3").value=categoryArr[2];
		document.getElementById("categoryEmail3").value=categoryEmailArr[2];
		document.getElementById("category4").value=categoryArr[3];
		document.getElementById("categoryEmail4").value=categoryEmailArr[3];
	}else{
		activeSecondCategory();
		activeThirdCategory();
		activeFourthCategory();
		activeFifthCategory();
		document.getElementById("category").value=categoryArr[0];
		document.getElementById("categoryEmail").value=categoryEmailArr[0];
		document.getElementById("category2").value=categoryArr[1];
		document.getElementById("categoryEmail2").value=categoryEmailArr[1];
		document.getElementById("category3").value=categoryArr[2];
		document.getElementById("categoryEmail3").value=categoryEmailArr[2];
		document.getElementById("category4").value=categoryArr[3];
		document.getElementById("categoryEmail4").value=categoryEmailArr[3];
		document.getElementById("category5").value=categoryArr[4];
		document.getElementById("categoryEmail5").value=categoryEmailArr[4];
	}
}