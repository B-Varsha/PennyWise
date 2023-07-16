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

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "jdbc:mysql://localhost:3306/pennywise";
        String username = "root";
        String password = "@202mICdnrRzs";

        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String emailid = request.getParameter("emailid");
        String phoneno = request.getParameter("phoneno");

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO users (username, password, emailid, phoneno) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, user);
            stmt.setString(2, pass);
            stmt.setString(3, emailid);
            stmt.setString(4, phoneno);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new record was inserted successfully!");
                conn.commit();
                request.getSession().setAttribute("username", user);
                response.sendRedirect("home.jsp");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error inserting record into database");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
