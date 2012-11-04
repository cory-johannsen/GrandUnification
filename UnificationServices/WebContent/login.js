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
			storeUserToCookie(result);
			personalizeHeader(result.name, result,username);
			window.location = "index.html";
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
/***
 *
 ***/
function jsonParser(tmpObj){
	var rval = new String();
	var keys = Object.keys(tmpObj);

	for(var key = 0; key < keys.length; key++){
		if(tmpObj.hasOwnProperty(keys[key])){//ensure property is in THIS object, not inherited

			if(typeof tmpObj[keys[key]] === 'object'){
				rval = rval + '<div class="object">' + keys[key] + " : " + jsonParser(tmpObj[keys[key]])+'</div>';
			}
			else{
				if(key === 0){
					rval = rval + '<div class="data">';
				}

				rval = rval + keys[key] + " : " + tmpObj[keys[key]];

				if(key < keys.length - 1){
					rval = rval + ', ';
				}
				else{
					rval = rval + '</div>';
				}
			}
		}
	}
	return(rval);
}//end of jsonReviver