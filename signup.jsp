<!DOCTYPE html>
<html>
<head>
	<title>SignUp Page</title>
	<link rel="stylesheet" href="css\signcss.css">
	<script type="text/javascript">
		function showError() {
    var error = '<%= request.getParameter("error") %>';
    if (error && error !== "null") {
        document.getElementById("error").innerHTML = error;
    }
}

	</script>

</head>
<body onload="showError()">
	<br><br><br>
	<form action="http://localhost:8088/miniproj/SignUpServlet" method="post">
		<label for="username">Username:</label>
		<input type="text" id="username" name="username"><br><br>

		<label for="password">Password:</label>
		<input type="password" id="password" name="password"><br><br>

		<label for="emailid">Email ID:</label>
		<input type="email" id="emailid" name="emailid"><br><br>

		<label for="phoneno">Phone Number:</label>
		<input type="tel" id="phoneno" name="phoneno"><br><br>

		<input type="submit" value="Submit">
	</form>
	<br><br>
	<br><br>

</body>
</html>
