<!DOCTYPE html>
<html>
<head>
	<title>Login Page</title>
	<link rel="stylesheet" type="text/css" href="css\logincss.css">
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
	<form method="post" action="http://localhost:8088/miniproj/LoginServlet">
		<label for="username">USERNAME:</label>
		<input type="text" id="username" name="username"><br><br>
		<label for="password">PASSWORD:</label>
		<input type="password" id="password" name="password"><br><br>
		<span id="error" style="color: red;"></span>
		<input type="submit" value="Login">
		<input id="sign" type="button" value="Sign Up" onclick="location.href='signup.jsp'" />
	</form>
</body>
</html>
