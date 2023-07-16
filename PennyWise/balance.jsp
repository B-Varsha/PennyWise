<!DOCTYPE html>
<html>
<head>
	<title>Balances</title>
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
	<p> GroupID: , <%= session.getAttribute("username") %> </p>
    <form method="post" action="http://localhost:8088/miniproj/BalanceServlet">
		<label for="groupid">GROUP ID:</label>
		<input type="text" id="groupid" name="groupid"><br><br>
		<input type="submit" value="submit">
	</form>
</body>
</html>
