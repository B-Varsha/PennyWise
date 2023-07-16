<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!doctype html public "-//w3c//dtd html 4.0 transitional//en">
<html>
<head><title>shopping</title></head>
<link rel="stylesheet" type="text/css" href="C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\miniproj\css\forgroups.css">
<h1 align="center">shopping</h1>
<h2>Expenses:</h2>

<%try {    Class.forName("com.mysql.jdbc.Driver");    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pennywise", "root", "@202mICdnrRzs");    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM expenses WHERE groupid=?");    stmt.setInt(1, 2);    ResultSet rs = stmt.executeQuery();    while (rs.next()) {        int expenseId = rs.getInt("expenseid");        String description = rs.getString("description");        double amount = rs.getDouble("amount");        String payer = rs.getString("payer");        String date = rs.getString("date");        out.println("<p>" + description + " (" + amount + "): paid by " + payer + " on " + date + "</p>");    }    rs.close();    stmt.close();    conn.close();} catch (ClassNotFoundException | SQLException e) {    e.printStackTrace();}%>
<style>
body {background-image: url('css/back2.jpg'); background-size: cover; font-family: Arial, sans-serif; }
h1 {text-align: center; color: white; font-weight: bold; }
</style>
<h2>Add Expense:</h2>
<form action="http://localhost:8088/miniproj/AddExpenseServlet" method="post">
Description: <input type="text" name="description"><br>
Amount: <input type="number" name="amount" min="0" step="0.01"><br>
Payer: <input type="text" name="payer"><br>
<input type="hidden" name="groupId" value="2">
<input type="submit" value="Add">
</form>
<form action="http://localhost:8088/miniproj//BalanceServlet" method="get">
<input type="submit" value="Go to Balance">
</form>
</body></html>
