// import java.io.IOException;
// import java.io.PrintWriter;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;

// import javax.servlet.ServletException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// @WebServlet("/expenses")
// public class BalanceServlet extends HttpServlet {

//     private static final long serialVersionUID = 1L;

//     protected void doGet(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         processRequest(request, response);
//     }

//     protected void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         processRequest(request, response);
//     }

//     protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         response.setContentType("text/html");
//         PrintWriter out = response.getWriter();

//         // Database connection variables
//         String url = "jdbc:mysql://localhost:3306/pennywise";
//         String username = "root";
//         String password = "@202mICdnrRzs";
//         Connection conn = null;

//         int groupId = (Integer) request.getSession().getAttribute("groupId");

//         out.println(groupId);

//         try {
//             // Load the MySQL JDBC driver
//             Class.forName("com.mysql.jdbc.Driver");

//             // Connect to the database
//             conn = DriverManager.getConnection(url, username, password);



//             // Create the SQL query to retrieve the payer and amount for a group
//             String expenseSql = "SELECT payer, amount " +
//                                 "FROM expenses " +
//                                 "WHERE groupid = ? ";
            


//             // Prepare the SQL statement with the group ID parameter
//             PreparedStatement expensePstmt = conn.prepareStatement(expenseSql);
//             expensePstmt.setInt(1, groupId); // Replace 1 with the actual group ID

//             // Execute the SQL query and retrieve the result set
//             ResultSet expenseRs = expensePstmt.executeQuery();

//             // Create the SQL query to retrieve the number of users for a group
//             String userSql = "SELECT COUNT(*) as num_users " +
//                              "FROM user_groups " +
//                              "WHERE groupid = ?";

//             // Prepare the SQL statement with the group ID parameter
//             PreparedStatement userPstmt = conn.prepareStatement(userSql);
//             userPstmt.setInt(1, groupId); // Replace 1 with the actual group ID

//             // Execute the SQL query and retrieve the result set
//             ResultSet userRs = userPstmt.executeQuery();

//             // Retrieve the number of users for the group
//             int numUsers = 0;
//             if (userRs.next()) {
//                 numUsers = userRs.getInt("num_users");
//             }

//             // Create the SQL query to retrieve the usernames for a group
//             String usernameSql = "SELECT username " +
//                                   "FROM user_groups " +
//                                   "WHERE groupid = ?";

//             // Prepare the SQL statement with the group ID parameter
//             PreparedStatement usernamePstmt = conn.prepareStatement(usernameSql);
//             usernamePstmt.setInt(1, groupId); // Replace 1 with the actual group ID

//             // Execute the SQL query and retrieve the result set
//             ResultSet usernameRs = usernamePstmt.executeQuery();

//             // Store the usernames in an array
//             String[] usernames = new String[numUsers];
//             int i = 0;
//             while (usernameRs.next()) {
//                 usernames[i] = usernameRs.getString("username");
//                 i++;
//             }
//             out.println("<h1>Expense Report</h1>");
//             out.println("<table>");
//             out.println("<tr><th>Payer</th><th>Amount</th></tr>");
//             String payer="";
//             Double amount=0.0;
//             while (expenseRs.next()) {
//                 payer = expenseRs.getString("payer");
//                 amount = expenseRs.getDouble("amount");
//                 out.println("<tr><td>" + payer + "</td><td>" + amount + "</td></tr>");
//             }

//             out.println("</table>");

//             out.println("<p>Number of users in group: " + numUsers + "</p>");

//             out.println("<p>Usernames in group:</p>");
//             out.println("<ul>");

//             for (String user : usernames) {
//                 out.println("<li>" + user + "</li>");
//             }

//             out.println("</ul>");

//             double averageExpense= amount/numUsers;

//             for (String user : usernames) {
//                 out.println("<li>" + user + "</li>");
//                     if (!user.equals(payer)) {
//                         double owedAmount = averageExpense;
//                         out.println("<p>" + user + " owes " + payer + " $" + owedAmount + "</p>");
//                     }
//                     else
//                         if (user.equals(payer)) {
//                             double balance = amount - averageExpense;
//                             out.println("<p>" + payer + " needs to receive $" + balance + " to balance</p>");
//                         }
//                 }
//                 // Clean up resources
//                 expenseRs.close();
//                 expensePstmt.close();
//                 userRs.close();
//                 userPstmt.close();
//                 usernameRs.close();

//         } catch (ClassNotFoundException e) {
//             out.println("<p>Error: " + e.getMessage() + "</p>");
//         } catch (SQLException e) {
//             out.println("<p>Error: " + e.getMessage() + "</p>");
//         } finally {
//             // Clean up resources
//             try {
//                 if (conn != null) {
//                     conn.close();
//                 }
//             } catch (SQLException e) {
//                 out.println("<p>Error: " + e.getMessage() + "</p>");
//             }
//         }
//     }
// }

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

@WebServlet("/expenses")
public class BalanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Database connection variables
        String url = "jdbc:mysql://localhost:3306/pennywise";
        String username = "root";
        String password = "@202mICdnrRzs";
        Connection conn = null;

        int groupId = (Integer) request.getSession().getAttribute("groupId");

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Connect to the database
            conn = DriverManager.getConnection(url, username, password);



            // Create the SQL query to retrieve the payer and amount for a group
            String expenseSql = "SELECT payer, amount " +
                                "FROM expenses " +
                                "WHERE groupid = ? ";
            


            // Prepare the SQL statement with the group ID parameter
            PreparedStatement expensePstmt = conn.prepareStatement(expenseSql);
            expensePstmt.setInt(1, groupId); // Replace 1 with the actual group ID

            // Execute the SQL query and retrieve the result set
            ResultSet expenseRs = expensePstmt.executeQuery();

            // Create the SQL query to retrieve the number of users for a group
            String userSql = "SELECT COUNT(*) as num_users " +
                             "FROM user_groups " +
                             "WHERE groupid = ?";

            // Prepare the SQL statement with the group ID parameter
            PreparedStatement userPstmt = conn.prepareStatement(userSql);
            userPstmt.setInt(1, groupId); // Replace 1 with the actual group ID

            // Execute the SQL query and retrieve the result set
            ResultSet userRs = userPstmt.executeQuery();

            // Retrieve the number of users for the group
            int numUsers = 0;
            if (userRs.next()) {
                numUsers = userRs.getInt("num_users");}


            // Create the SQL query to retrieve the usernames for a group
            String usernameSql = "SELECT username " +
                                  "FROM user_groups " +
                                  "WHERE groupid = ?";

            // Prepare the SQL statement with the group ID parameter
            PreparedStatement usernamePstmt = conn.prepareStatement(usernameSql);
            usernamePstmt.setInt(1, groupId); // Replace 1 with the actual group ID

            // Execute the SQL query and retrieve the result set
            ResultSet usernameRs = usernamePstmt.executeQuery();

            // Store the usernames in an array
            String[] usernames = new String[numUsers];
            int i = 0;
            while (usernameRs.next()) {
                usernames[i] = usernameRs.getString("username");
                i++;
            }


            out.println("<h1>Expense Report</h1>");
            out.println("<table>");
            out.println("<tr><th>Payer</th><th>Amount</th></tr>");
            String payer="";
            Double amount=0.0;
            double Total_amount = 0.0;

            while (expenseRs.next()) {
                payer = expenseRs.getString("payer");
                amount = expenseRs.getDouble("amount");
                Total_amount += amount;
                out.println("<tr><td>" + payer + "</td><td>" + amount + "</td></tr>");}

            out.println("<tr><td>Total Expense = </td><td>" + Total_amount + "</td></tr>");


            out.println("</table>");

            out.println("<p>Number of users in group: " + numUsers + "</p>");

            out.println("<p>Usernames in group:</p>");
            out.println("<ul>");

            for (String user : usernames) {
                out.println("<li>" + user + "</li>");
            }

            out.println("</ul>");

            double averageExpense= Total_amount/numUsers;

            for (String user : usernames) {
                out.println("<li>" + user + "</li>");
                    if (!user.equals(payer)) {
                        double owedAmount = averageExpense;
                        out.println("<p>" + user + " owes " + payer + " $" + owedAmount + "</p>");
                    }
                    else
                        if (user.equals(payer)) {
                            double balance = Total_amount - averageExpense;
                            out.println("<p>" + payer + " needs to receive $" + balance + " to balance</p>");
                        }
                }
                // Clean up resources
                expenseRs.close();
                expensePstmt.close();
                userRs.close();
                userPstmt.close();
                usernameRs.close();

        } catch (ClassNotFoundException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        } finally {
            // Clean up resources
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                out.println("<p>Error: " + e.getMessage() + "</p>");
            }
        }
    }
}