import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserAuthServlet")  // Mapping the Servlet
public class UserAuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Hardcoded credentials (for testing purposes)
    private static final String VALID_USERNAME = "johndoe";
    private static final String VALID_PASSWORD = "securePass123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieving user input from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Checking credentials
        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            out.println("<h2>Welcome, " + username + "!</h2>");
            out.println("<p>Login Successful. Enjoy your session!</p>");
        } else {
            out.println("<h2>Invalid Username or Password!</h2>");
            out.println("<p><a href='loginPage.html'>Try Again</a></p>");
        }
    }
}
