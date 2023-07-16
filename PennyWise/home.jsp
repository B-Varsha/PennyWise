<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home Page</title>
    <link rel="stylesheet" href="css/homecss.css">
</head>
<body>
    <br><br><br><br><br><br>
    <center>
        <h2>PennyWise - The Expense Management System</h2>
        <h3>Welcome, <%= session.getAttribute("username") %></h3>
    </center>
    <div class="button-container">
         <a href="ViewGroupsServlet">View Groups</a> 
    </div>
</body>
</html>
