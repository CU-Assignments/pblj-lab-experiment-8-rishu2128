import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h2>Employee List</h2>");
        out.println("<form method='GET' action='EmployeeServlet'>");
        out.println("Search by ID: <input type='text' name='empId'>");
        out.println("<input type='submit' value='Search'></form><br>");

        String empId = request.getParameter("empId");
        
        try (Connection conn = DBConnection.getConnection()) {
            if (empId != null && !empId.isEmpty()) {
                // Fetch employee by ID
                String query = "SELECT * FROM employees WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, Integer.parseInt(empId));
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    out.println("<h3>Employee Details:</h3>");
                    out.println("ID: " + rs.getInt("id") + "<br>");
                    out.println("Name: " + rs.getString("name") + "<br>");
                    out.println("Department: " + rs.getString("department") + "<br>");
                    out.println("Salary: $" + rs.getDouble("salary") + "<br>");
                } else {
                    out.println("<p>No employee found with ID: " + empId + "</p>");
                }
            } else {
                // Fetch all employees
                String query = "SELECT * FROM employees";
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();
                
                out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th></tr>");
                while (rs.next()) {
                    out.println("<tr><td>" + rs.getInt("id") + "</td>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getString("department") + "</td>");
                    out.println("<td>$" + rs.getDouble("salary") + "</td></tr>");
                }
                out.println("</table>");
            }
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
    }
}
