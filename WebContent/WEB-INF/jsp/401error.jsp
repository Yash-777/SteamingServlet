<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
   <title>Setting HTTP Status Code</title>
</head>

<body>
<%
// Set error code and reason.
response.sendError(401, "This request Need authentication!!!" );
out.print("401 : Custom Error Message from StramingServlet Application.");
%>
</body>
</html>

