/**
 * Update header when information is known
 */
var mSessionValid=false;
var mUser=null;

/**
 * @returns {Boolean}
 */
function isSessionValid() {
	return mSessionValid;
}

/**
 * @returns
 */
function getCurrentUser() {
	return mUser;
}

/**
 * @param user
 */
function personalizeHeader(user) {
	var htmlStr = user.displayName
			+ '<input id="logoutButton" type="button" value="Logout" onclick="logout()"/>';
	$(".logout").html(htmlStr);
}

function createJsonConnection() {
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
	
	return xhr;
}

/**
 * @returns {Boolean}
 */
function validateSession() {
	var xhr = createJsonConnection();
	
	xhr.onloadend=function(){
		var result = JSON.parse(xhr.responseText);
		if (result.error != null && result.error != "") {
			$("#message").html(result.error);
			mSessionValid = false;
		}
		else {
			mSessionValid = true;
		}
	};

	var requestData = {};	
	var jsonRequest = JSON.stringify(requestData);
	
	xhr.open("POST","service/session/validate",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonRequest);
}

/**
 * @returns {Boolean}
 */
function loadUser() {
	var xhr = createJsonConnection();
	
	xhr.onloadend=function(){
		var result = JSON.parse(xhr.responseText);
		if (result.error != null && result.error != "") {
			$("#message").html(result.error);
			mSessionValid = false;
		}
		else {
			mUser = result.user;
			personalizeHeader(mUser);
		}
	};

	var requestData = {};	
	var jsonRequest = JSON.stringify(requestData);
	
	xhr.open("POST","service/session/currentuser",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonRequest);
}


/**
 * 
 */
function logout() {
	var xhr = createJsonConnection();
	
	xhr.onloadend=function(){
		var result = JSON.parse(xhr.responseText);
		if (result.error != null || result.error != "") {
			$("#message").html(result.error);
		}
		mSessionValid = false;
		window.location = "login.html";
	};

	var requestData = {};	
	var jsonRequest = JSON.stringify(requestData);
	
	xhr.open("POST","service/logout",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonRequest);
}

document.write('<div class="header">');
document.write('<div id="logout" class="logout"></div>');
document.write('<div id="pageTitle" class="title">The Grand Unification Framework</div>');
document.write('</div><!-- header -->');
validateSession();
if (isSessionValid()) {
	loadUser();
}
