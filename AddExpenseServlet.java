// import java.io.*;
// import java.sql.*;
// import javax.servlet.*;
// import javax.servlet.http.*;
// import java.math.BigDecimal;


// public class AddExpenseServlet extends HttpServlet {
//     public void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//                 HttpSession session = request.getSession();
//         response.setContentType("text/html");
//         PrintWriter out = response.getWriter();
//         int groupId = (int) session.getAttribute("groupid");
//         // Get expense details from request parameters
//         // String groupId = request.getParameter("groupId");
//         String description = request.getParameter("description");
//         String amount = request.getParameter("amount");
//         String payer = request.getParameter("payer");
//         String date = request.getParameter("date");
        
//         // Insert expense into database
//         Connection conn = null;
//         PreparedStatement stmt = null;
//         try {
//             conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pennywise", "root", "@202mICdnrRzs");
//             stmt = conn.prepareStatement("INSERT INTO expenses (groupid, description, amount, payer, date) VALUES (?, ?, ?, ?, ?)");
//             stmt.setInt(1, groupId);
//             stmt.setString(2, description);
//             stmt.setBigDecimal(3, new BigDecimal(amount));
//             stmt.setString(4, payer);
//             stmt.setDate(5, Date.valueOf(date));
//             stmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//             out.println("<html><body><h3>Error inserting expense into database</h3></body></html>");
//             return;
//         } finally {
//             try {
//                 if (stmt != null) stmt.close();
//                 if (conn != null) conn.close();
//             } catch (SQLException e) {
//                 e.printStackTrace();
//             }
//         }
        
//         // Redirect to group page
//         String groupPage = "group" + groupId + ".html";
//         response.sendRedirect(groupPage);
//     }
// }

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddExpenseServlet")
public class AddExpenseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // JDBC driver and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/pennywise";
    
    // Database credentials
    static final String USER = "root";
    static final String PASS = "@202mICdnrRzs";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from request
        String description = request.getParameter("description");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String payer = request.getParameter("payer");
        // String date = request.getParameter("date");
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        
        // Set response content type
        response.setContentType("text/html");
    
        PrintWriter out = response.getWriter();
        
        // Insert expense into expenses table
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
    
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // Insert expense into expenses table
            String sql = "INSERT INTO expenses (groupid, description, amount, payer, date) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(2, description);
            stmt.setDouble(3, amount);
            stmt.setString(4, payer);
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis()); 
            stmt.setDate(5, date);
            stmt.setInt(1, groupId);
            stmt.executeUpdate();

            // Redirect to the group page
            request.getSession().setAttribute("groupId", groupId);
            response.sendRedirect("groups/group" + groupId + ".jsp"); 
            // response.sendRedirect("BalanceServlet");
        } catch (ClassNotFoundException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                out.println("<p>Error: " + e.getMessage() + "</p>");
            }
        }
    }
}

