import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteGroupServlet")
public class DeleteGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the group ID from the request parameters
        String groupId = request.getParameter("groupid");

        String url = "jdbc:mysql://localhost:3306/pennywise";
        String username = "root";
        String password = "@202mICdnrRzs";

        try (Connection conn = DriverManager.getConnection(url, username, password);) {
            // Delete the group from the group_table
            String sql = "DELETE FROM group_table WHERE groupid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, groupId);
            stmt.executeUpdate();

            // Delete the group from the user_groups table
            sql = "DELETE FROM user_groups WHERE groupid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, groupId);
            stmt.executeUpdate();

            // Delete the group from the expenses table
            sql = "DELETE FROM expenses WHERE groupid = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, groupId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error deleting group from database");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Redirect back to the view groups page
        response.sendRedirect("ViewGroupsServlet");
    }
}