/**
 * Update header when information is known
 */
var mSessionValid=false;
var mUser=null;
function personalizeHeader(user) {
	var htmlStr = user.displayName
			+ '<input id="logoutButton" type="button" value="Logout" onclick="logout()"/>';
	$(".logout").html(htmlStr);
}
function validateSession() {
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
		if (result.error != null && result.error != "") {
			logout();
		}
		else {
			mSessionValid = true;
		}
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
	var jsonRequest = JSON.stringify(requestData);
	
	xhr.open("POST","service/session/validate",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonRequest);
	
	return true;
}
function isSessionValid() {
	return mSessionValid;
}
function logout() {
	var xhr;
	
	if(window.XMLHttpRequest){ //IE7+,FF,GC,O,S
		xhr = new XMLHttpRequest();
	}
	else{ //IE6,IE5
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xhr.onerror=function(){
		$("#message").html("Failure: " + " " + xhr.readyState + " " + xhr.status + " "+ xhr.responseText);
		mSessionValid = false;
	};
	xhr.onloadend=function(){
		var result = JSON.parse(xhr.responseText);
		if (result.error != null || result.error != "") {
			$("#message").html(result.error);
		}
		mSessionValid = false;
		window.location = "login.html";
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
	var jsonRequest = JSON.stringify(requestData);
	
	xhr.open("POST","service/logout",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonRequest);
}
if (validateSession()) {
	document.write('<div class="header">');
	document.write('<div id="logout" class="logout"></div>');
	if (mUser != null) {
		personalizeHeader(mUser);
	}
	document.write('<div id="pageTitle" class="title">The Grand Unification Framework</div>');
	document.write('</div><!-- header -->');
}
else {
	mSessionValid = false;
}
