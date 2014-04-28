/***
 * 
 ***/
function doPost(api){
	var xhr= createJsonConnection();
	
	xhr.onloadend=function(){
		var result = JSON.parse(xhr.responseText);
		if (result.error != null && result.error != "") {
			$("#message").html(result.error);
			mSessionValid = false;
			mUser = null;
		}
		else {
			mSessionValid = true;
			mUser = result.user;
			personalizeHeader(mUser);
			window.location = "index.html";
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