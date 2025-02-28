package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Invalidar la sesión (esto elimina todos los atributos de la sesión)
            session.invalidate();
        }

        // Redirigir al usuario a la página de login
        response.sendRedirect(request.getContextPath() + "/public/login.jsp");
    }
}