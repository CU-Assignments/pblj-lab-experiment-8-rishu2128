import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve form data
        String studentName = request.getParameter("student_name");
        String rollNumber = request.getParameter("roll_number");
        String subject = request.getParameter("subject");
        String attendanceDate = request.getParameter("attendance_date");
        String status = request.getParameter("status");

        try (Connection conn = DBConnection.getConnection()) {
            // Insert attendance data into database
            String query = "INSERT INTO attendance (student_name, roll_number, subject, attendance_date, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, studentName);
            pstmt.setString(2, rollNumber);
            pstmt.setString(3, subject);
            pstmt.setString(4, attendanceDate);
            pstmt.setString(5, status);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                response.sendRedirect("success.jsp");
            } else {
                out.println("<h3>Failed to save attendance. Try again!</h3>");
            }
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
    }
}
