<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Upload</title>
</head>
<body>
<center>
	<h1>File Upload</h1>
	<form method="post" action="files/UploadFile" enctype="multipart/form-data">
		File1: <input type="file" name="file1" size="60" /> <br/>
		File2: <input type="file" name="file2" /> <br/>
		<br/><input type="submit" value="Upload" />
	</form>
</center>
</body>
</html>