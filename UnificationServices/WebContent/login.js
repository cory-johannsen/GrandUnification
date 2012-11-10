/***
 * 
 ***/
function doPost(api){
	var xhr;    

	if(window.XMLHttpRequest){ //IE7+,FF,GC,O,S
		xhr = new XMLHttpRequest();
	}
	else{ //IE6,IE5
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xhr.onerror=function(){
		$("#message").html("Failure: " + " " + xhr.readyState + " " + xhr.status + " "+ xhr.responseText);
	};
	xhr.onloadend=function(){
			var result = JSON.parse(xhr.responseText);
			mSessionValid = true;
			mUser = result.user;
			personalizeHeader(mUser);
			
	};
	xhr.onreadystatechange=function(){//Needed to work on IE, throws errors for the rest
		if(navigator.userAgent.indexOf("MSIE") > -1){
			//ready?
			if(xhr.readyState != 4){
				return false;
			}
			//get status
			var status = xhr.status;
			
			//not successful?
			if(status != 200){
				$("#message").html("Server status "+200);
				return false;
			}
			
			var result = jsonParser(JSON.parse(xhr.responseText));
			$("#message").html(result);			
		}
	};

	var requestData = {};
	requestData.username = $('input:text[name=username]').val();
	requestData.password = $('input:password').val();
	
	var jsonRequest = JSON.stringify(requestData);
	
	xhr.open("POST","service/login",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonRequest);

}//end of doPost