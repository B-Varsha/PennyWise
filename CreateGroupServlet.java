import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;


@WebServlet("/CreateGroupServlet")
public class CreateGroupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // JDBC driver and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/pennywise";
    
    // Database credentials
    static final String USER = "root";
    static final String PASS = "@202mICdnrRzs";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from request
        String groupname = request.getParameter("groupname");
        String[] members = request.getParameterValues("members");
        System.out.println("groupName: " + groupname);
        // String username = (String) request.getSession().getAttribute("username");
    
        // Set response content type
        response.setContentType("text/html");
    
        PrintWriter out = response.getWriter();
        String title = "Create Group Result";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " +
                "transitional//en\">\n";
        
        // Insert group into group_table
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
    
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Insert group into group_table
            String sql = "INSERT INTO group_table (groupname) VALUES (?)";
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, groupname);
            stmt.executeUpdate();
            
            // Get generated group id
            rs = stmt.getGeneratedKeys();
            int groupId = 0;
            if (rs.next()) {
                groupId = rs.getInt(1);
            } else {
                out.println("<p>Error: Group id not generated</p>");
                out.println("</body></html>");
                return;
            }
            
            // Insert group members into user_groups
            sql = "INSERT INTO user_groups (username, groupid) VALUES (?, ?)";
            for (String member : members) {
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, member);
                stmt.setInt(2, groupId);
                stmt.executeUpdate();
            }

            String pageName = "group" + groupId + ".jsp";
            String miniProjPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\miniproj\\groups\\";
            String pagePath = miniProjPath + pageName;
            File file = new File(pagePath);
            PrintWriter pageOut = new PrintWriter(file);
            
            

            
            String pageDocType = "<!doctype html public \"-//w3c//dtd html 4.0 " +
                    "transitional//en\">\n";

                    pageOut.println("<%@ page import=\"java.sql.Connection\" %>");
                    pageOut.println("<%@ page import=\"java.sql.DriverManager\" %>");
                    pageOut.println("<%@ page import=\"java.sql.PreparedStatement\" %>");
                    pageOut.println("<%@ page import=\"java.sql.ResultSet\" %>");
                    pageOut.println("<%@ page import=\"java.sql.SQLException\" %>");
                    
                    pageOut.println("<%@ page contentType=\"text/html\" pageEncoding=\"UTF-8\" %>");
                    
                    pageOut.println(pageDocType +
                        "<html>\n" +
                        "<head><title>" + groupname + "</title></head>\n" +
                        "<h1 align=\"center\">" + groupname + "</h1>\n" +
                        "<h2>Expenses:</h2>\n");

                    pageOut.println("<link rel='stylesheet' type='text/css' href='C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\miniproj\\css\\forgroups.css'>");
                    
                    pageOut.println("<%" +
                        "try {" +
                        "    Class.forName(\"com.mysql.jdbc.Driver\");" +
                        "    Connection conn = DriverManager.getConnection(\"jdbc:mysql://localhost:3306/pennywise\", \"root\", \"@202mICdnrRzs\");" +
                        "    PreparedStatement stmt = conn.prepareStatement(\"SELECT * FROM expenses WHERE groupid=?\");" +
                        "    stmt.setInt(1, " + groupId + ");" +
                        "    ResultSet rs = stmt.executeQuery();" +
                        "    while (rs.next()) {" +
                        "        int expenseId = rs.getInt(\"expenseid\");" +
                        "        String description = rs.getString(\"description\");" +
                        "        double amount = rs.getDouble(\"amount\");" +
                        "        String payer = rs.getString(\"payer\");" +
                        "        String date = rs.getString(\"date\");" +
                        "        out.println(\"<p>\" + description + \" (\" + amount + \"): paid by \" + payer + \" on \" + date + \"</p>\");" +
                        "    }" +
                        "    rs.close();" +
                        "    stmt.close();" +
                        "    conn.close();" +
                        "} catch (ClassNotFoundException | SQLException e) {" +
                        "    e.printStackTrace();" +
                        "}" +
                        "%>");

                    // Add CSS to the HTML file
                    out.println("<style>");
                    out.println("body {background-image: url('C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\miniproj\\css\\back2.jpg'); background-size: cover; font-family: Arial, sans-serif; color: white}");
                    out.println("h1 {text-align: center; color: white; font-weight: bold; }");
                    out.println(".expense-item {");
                    out.println("    background-color: #f9f9f9;");
                    out.println("    padding: 10px;");
                    out.println("    border-radius: 5px;");
                    out.println("    margin-bottom: 10px;");
                    out.println("}");
                    out.println(".expense-description {");
                    out.println("    font-weight: bold;");
                    out.println("}");
                    out.println(".expense-amount {");
                    out.println("    color: #007bff;");
                    out.println("}");
                    out.println(".expense-payer {");
                    out.println("    font-style: italic;");
                    out.println("}");
                    out.println(".expense-date {");
                    out.println("    color: #6c757d;");
                    out.println("    font-size: 12px;");
                    out.println("}");
                    out.println(".add-expense-form {");
                    out.println("    margin-top: 20px;");
                    out.println("}");
                    out.println(".add-expense-input {");
                    out.println("    margin-bottom: 10px;");
                    out.println("    padding: 5px;");
                    out.println("    border-radius: 3px;");
                    out.println("    border: 1px solid #ccc;");
                    out.println("    width: 200px;");
                    out.println("}");
                    out.println(".add-expense-submit {");
                    out.println("    background-color: #007bff;");
                    out.println("    color: #fff;");
                    out.println("    border: none;");
                    out.println("    padding: 5px 10px;");
                    out.println("    border-radius: 3px;");
                    out.println("    cursor: pointer;");
                    out.println("}");
                    out.println("</style>");

                    
                    pageOut.println("<h2>Add Expense:</h2>\n" +
                        "<form action=\"http://localhost:8088/miniproj/AddExpenseServlet\" method=\"post\">\n" +
                        "Description: <input type=\"text\" name=\"description\"><br>\n" +
                        "Amount: <input type=\"number\" name=\"amount\" min=\"0\" step=\"0.01\"><br>\n" +
                        "Payer: <input type=\"text\" name=\"payer\"><br>\n" +
                        "<input type=\"hidden\" name=\"groupId\" value=\"" + groupId + "\">\n" +
                        "<input type=\"submit\" value=\"Add\">\n" +
                        "</form>\n" +
                        "<form action=\"http://localhost:8088/miniproj//BalanceServlet\" method=\"get\">\n" +
                        "<input type=\"submit\" value=\"Go to Balance\">\n" +
                        "</form>\n" +
                        "</body></html>");
                    
                    
                    
            // Get balances for group
//             sql = "SELECT payer, SUM(amount) AS balance FROM expenses WHERE groupid=? GROUP BY username";
//             stmt = conn.prepareStatement(sql);
//             stmt.setInt(1, groupId);
//             rs = stmt.executeQuery();
//             pageOut.println("<h2>Balances:</h2>\n");
//             while (rs.next()) {
//                 String username = rs.getString("username");
// double balance = rs.getDouble("balance");
// pageOut.println("<p>" + username + ": " + balance + "</p>");
//}
    // Add form to add new expense
   
    // Close page writer
    pageOut.close();
    
    // Redirect to new group webpage
    response.sendRedirect("groups/" + pageName);

    
} catch (SQLException se) {
    // Handle errors for JDBC
    se.printStackTrace();
    out.println("<p>Error: " + se.getMessage() + "</p>");
    out.println("<p>Line number: " + se.getStackTrace()[0].getLineNumber() + "</p>");

} catch (Exception e) {
    // Handle errors for Class.forName
    e.printStackTrace();
    out.println("<p>Error: " + e.getMessage() + "</p>");
    out.println("<p>Line number: " + e.getStackTrace()[0].getLineNumber() + "</p>");

} finally {
    // Close resources
    try {
        if (rs != null) {
            rs.close();
        }
    } catch (SQLException se) {
        se.printStackTrace();
    }
    try {
        if (stmt != null) {
            stmt.close();
        }
    } catch (SQLException se) {
        se.printStackTrace();
    }
    try {
        if (conn != null) {
            conn.close();
        }
    } catch (SQLException se) {
        se.printStackTrace();
    }
}
    }
}