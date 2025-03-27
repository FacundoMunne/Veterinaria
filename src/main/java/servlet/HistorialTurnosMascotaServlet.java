package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import clases.Turno;
import data.DataTurno;

@WebServlet("/historialTurnosMascotaServlet")
public class HistorialTurnosMascotaServlet extends HttpServlet {
    private DataTurno dataTurno;

    @Override
    public void init() throws ServletException {
        dataTurno = new DataTurno();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String redirectUrl = request.getContextPath() + "/public/error.jsp";
        
        try {
            // 1. Validar sesión
            if (session == null || session.getAttribute("idProfesional") == null) {
                response.sendRedirect(request.getContextPath() + "/public/login.jsp");
                return;
            }

            // 2. Validar y obtener parámetros
            int idMascota = Integer.parseInt(request.getParameter("idMascota"));
            int idProfesional = (int) session.getAttribute("idProfesional");

            // 3. Obtener historial de turnos
            List<Turno> turnos = dataTurno.getHistorialTurnosByMascotaAndProfesional(idMascota, idProfesional);
            
            // 4. Ordenar turnos por fecha (más recientes primero)
            turnos.sort((t1, t2) -> t2.getFechaHora().compareTo(t1.getFechaHora()));

            // 5. Pasar datos a la vista
            request.setAttribute("turnos", turnos);
            request.getRequestDispatcher("/profesional/historialTurnosMascota.jsp").forward(request, response);
            return;
            
        } catch (NumberFormatException e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "ID inválido");
            request.setAttribute("errorMessage", "ID de mascota inválido");
            request.setAttribute("errorRedirect", request.getContextPath() + "/seleccionarTurno.jsp");
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        } catch (IllegalStateException e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Sesión inválida");
            request.setAttribute("errorMessage", "Error al acceder a la sesión");
            request.setAttribute("errorRedirect", request.getContextPath() + "/public/login.jsp");
            request.setAttribute("errorButtonText", "Iniciar sesión");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Error general");
            request.setAttribute("errorMessage", "Error al obtener el historial de turnos");
            request.setAttribute("errorRedirect", request.getContextPath() + "/profesional/historialTurnosMascota.jsp");
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        }
    }
}