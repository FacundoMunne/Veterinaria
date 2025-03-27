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
	    try {
	        // Obtener la sesión actual
	        HttpSession session = request.getSession(false);

	        if (session != null) {
	            // Invalidar la sesión (esto elimina todos los atributos de la sesión)
	            session.invalidate();
	        }

	        // Redirigir al usuario a la página de login
	        response.sendRedirect(request.getContextPath() + "/public/login.jsp");
	    } catch (Exception e) {
	        // En caso de error, redirigir a una página de error
	        System.out.println("❌ ERROR DURANTE EL LOGOUT: " + e.getMessage());
	        request.setAttribute("errorMessage", "Ocurrió un error al cerrar sesión.");
	        request.getRequestDispatcher("/public/error.jsp").forward(request, response);
	    }
	}}
