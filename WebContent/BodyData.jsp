<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript">
/* var script = document.createElement('script');
script.src = 'http://code.jquery.com/jquery-1.8.3.min.js';
script.type = 'text/javascript';
document.getElementsByTagName('head')[0].appendChild(script); */

function submitform() {
	var serverURL = "http://localhost:8080/SteamingServlet/postJSON";
	
	var objectData = {
		"first_name":document.getElementById('fname').value,
		"last_name":document.getElementById('lname').value
	};
	// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/JSON/stringify
	var jsonData = JSON.stringify( objectData );
	console.log("Sending Json : "+jsonData);
	//alert(jsonData);
	console.log("Keys Length > 1 : ", Object.keys(JSON.parse('{"a": 1, "a": 2}')).length );
	console.log("Keys Length > 2 : ", Object.keys(JSON.parse('{"a": 1, "ab": 2}')).length );

	var xmlhttp;
	if ( window.XMLHttpRequest ) {// code for IE7+, Firefox, Chrome, Opera, Safari
		// https://developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/Using_XMLHttpRequest
		xmlhttp = new XMLHttpRequest();
	}
	xmlhttp.open("POST", serverURL, true);

	/* var urlencodedDATA =  encodeURIComponent( data );
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded"); */
	//xmlhttp.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
	xmlhttp.setRequestHeader('Content-Type', 'text/json');

	xmlhttp.send(jsonData);
	
	xmlhttp.onreadystatechange = function( data ) {
		console.log("Ajax Response Data : ");
		console.log(data);
		//alert(data);
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			console.log("data updated successfully :: ");
			window.location = data.url;
		}
	};
}


/* 

* http://www.json.org
* [RFC 4627](http://www.ietf.org/rfc/rfc4627.txt):
The `application/json` Media Type for JavaScript Object Notation (JSON)
* [RFC 7159](http://www.ietf.org/rfc/rfc7159.txt) The JavaScript Object Notation (JSON) Data Interchange Format - Obsoletes: 4627, 7158

*/
</script>
<script src="http://code.jquery.com/jquery-1.8.3.min.js" type="text/javascript">
// http://jsfiddle.net/jF3eD/1/
$("#myFormJQuery").submit(function() { 
	//var formData = JSON.stringify($("#myFormJQuery").serializeArray());
	//alert(formData);
	
	$.ajax({
		type: "POST",
		url: "http://localhost:8080/SteamingServlet/postJSON",
		data: {
			"first_name":document.getElementById('fname').value,
			"last_name":document.getElementById('lname').value
		},
		success: function( data ) {
			alert("success");
			window.location = data.url;
		},
		error:function(){
			alert("failure");
		},
		dataType: "json",
		contentType : "application/json",
		processData: false
	});
});
</script>
</head>
<body>
	<!-- <form method="POST" id="myFormJQuery" action="http://localhost:8080/SteamingServlet/postJSON" > -->
	<!-- <form method="POST" id="myFormJS"> -->
	<form method="post" action="postJSON" enctype="application/json">
		<p><label for="first_name">First Name:</label>
		<input type="text" name="first_name" id="fname"></p>
		
		<p><label for="last_name">Last Name:</label>
		<input type="text" name="last_name" id="lname"></p>
		
		<!-- SB: {"username":"yashwanth.merugu@gmail.com","password":"Yash@777"} -->
		<!-- Javascript - onclick="submitform() -->
		<!-- <input value="Submit" type="submit" onclick="submitform()"> -->
		
		<!-- SB : first_name=Yashwanth&last_name=M&submit= -->
		<!-- <p><button name="submit">JQuery SUBMIT</button></p> -->
		
		<!-- SB : first_name=Yashwanth&last_name=Merugu -->
		<br/><input type="submit" value="JSON" />
	</form>
</body>
</html>