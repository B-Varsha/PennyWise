import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewGroupsServlet")
public class ViewGroupsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the username of the currently logged-in user
        String user = (String) request.getSession().getAttribute("username");

        String url = "jdbc:mysql://localhost:3306/pennywise";
        String username = "root";
        String password = "@202mICdnrRzs";

        // Retrieve the list of groups that the user is part of from the database
        List<String> groupidList = new ArrayList<>();
        List<String> groupnameList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);) {
            String sql = "SELECT groupid, groupname FROM group_table WHERE groupid IN " +
                         "(SELECT groupid FROM user_groups WHERE username = ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                groupidList.add(rs.getString("groupid"));
                groupnameList.add(rs.getString("groupname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error retrieving group list from database");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Forward the group list to the view groups page for display
        request.setAttribute("groupidList", groupidList);
        request.setAttribute("groupnameList", groupnameList);
        request.getRequestDispatcher("/viewgroups.jsp").forward(request, response);
    }
}