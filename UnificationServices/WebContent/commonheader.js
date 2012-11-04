document.write('<div class="header">');
document.write('<div id="logout" class="logout"></div>');
document.write('<div id="pageTitle" class="title">The Grand Unification Framework</div>');
document.write('</div><!-- header -->');
function getUserFromCookie() {
	var cookies = document.cookie.split(";");
	var cookieIndex = 0;
	var cookieName = null;
	var cookieValue = null;
	var userCookieValue = null;
	var user = null;
	for (cookieIndex = 0; cookieIndex < cookies.length; cookieIndex++) {
		cookieName = cookies[cookieIndex].substr(0, cookies[cookieIndex]
				.indexOf("="));
		cookieValue = cookies[cookieIndex].substr(cookies[cookieIndex]
				.indexOf("=") + 1);
		cookieName = cookieName.replace(/^\s+|\s+$/g, "");
		if (cookieName == "UnificationUser") {
			userCookieValue = unescape(cookieValue);
			break;
		}
	}
	if (userCookieValue == null || userCookieValue == "") {
		// No user cookie present, request from server? Redirect to login?
		window.location = "login.html";
	} else {
		// Extract the uid, name, roles, etc from the cookie and store them in a
		// user object
		user = new Object();
		var username = userCookieValue[0];
		if (username != null && username != "") {
			user.username = username;
		}
		var parameters = userCookieValue.split(";");
		var parameterIndex = 0;
		var parameterName = null;
		var parameterValue = null;
		for (parameterIndex = 1; parameterIndex < parameters.length(); parameterIndex++) {
			parameterName = parameters[parameterIndex].substr(0,
					parameters[parameterIndex].indexOf("="));
			parameterValue = parameters[parameterIndex]
					.substr(parameters[parameterIndex].indexOf("=") + 1);
			if (parameterName == "name") {
				user.name = parameterValue;
			}
			if (parameterName == "max-age") {
				user.timeout = parameterValue;
			}
		}
	}
}
function storeUserToCookie(user) {
	document.cookie = "UnificationUser=" + encodeURIComponent(user.username)
			+ "; name=" + encodeURIComponent(user.name)
			+ "; max-age=" + (user.timeout / 1000) + "; path=/";
}
/**
 * Update header when information is known
 */
function personalizeHeader(fname, userId) {
	var htmlStr = fname
			+ '<input id="logoutButton" type="button" value="Logout" onclick="logout()"/>';
	$(".logout").html(htmlStr);
}
function getSessionId() {
	var cookies = document.cookie.split(";");
	var cookieIndex = 0;
	var cookieName = null;
	var cookieValue = null;
	var sessionCookie = null;
	
	for (cookieIndex = 0; cookieIndex < cookies.length; cookieIndex++) {
		cookieName = cookies[cookieIndex].substr(0, cookies[cookieIndex]
				.indexOf("="));
		cookieValue = cookies[cookieIndex].substr(cookies[cookieIndex]
				.indexOf("=") + 1);
		cookieName = cookieName.replace(/^\s+|\s+$/g, "");
		if (cookieName == "JSESSIONID") {
			sessionCookie = unescape(cookieValue);
			break;
		}
	}
	return sessionCookie;
}
function validateSession() {
	var xhr;
	var sessionId = getSessionId();
	if (sessionId == null) {
		// No session ID present, redirect to login
		window.location = "login.html";
	}
	
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
	requestData.sessionId = cookieValue;
	
	var jsonRequest = JSON.stringify(requestData);
	
	xhr.open("POST","service/session/validate",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonRequest);
	
	return true;
}
function logout() {
	var xhr;
	var sessionId = getSessionId();
	
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
		if (result.error != null || result.error != "") {
			$("#message").html(result.error);
		}
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
	requestData.sessionId = sessionId;
	
	var jsonRequest = JSON.stringify(requestData);
	
	xhr.open("POST","service/logout",true);
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(jsonRequest);
	window.location = "login.html";
}