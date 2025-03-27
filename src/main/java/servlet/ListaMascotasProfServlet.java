package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import clases.Mascota;
import data.DataTurno;

@WebServlet("/listaMascotasProfServlet")
public class ListaMascotasProfServlet extends HttpServlet {
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
            // 1. Validar sesión y obtener profesional
            if (session == null || session.getAttribute("idProfesional") == null) {
                response.sendRedirect(request.getContextPath() + "/public/login.jsp");
                return;
            }

            int idProfesional = (int) session.getAttribute("idProfesional");

            // 2. Obtener mascotas con información adicional
            List<Mascota> mascotas = dataTurno.getMascotasByProfesional(idProfesional);
            
            if (mascotas == null || mascotas.isEmpty()) {
                request.setAttribute("infoMessage", "No tiene mascotas asignadas actualmente");
            }

            // 3. Pasar datos a la vista
            request.setAttribute("mascotas", mascotas);
            request.getRequestDispatcher("/profesional/listaMascotasProf.jsp").forward(request, response);
            return;
            
        } catch (ClassCastException e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Error de sesión");
            request.setAttribute("errorMessage", "Error en los datos de sesión");
            request.setAttribute("errorRedirect", request.getContextPath() + "/public/login.jsp");
            request.setAttribute("errorButtonText", "Iniciar sesión");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Error general");
            request.setAttribute("errorMessage", "Error al obtener la lista de mascotas");
            request.setAttribute("errorRedirect", request.getContextPath() + "/profesional/listaMascotasProf.jsp");
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        }
    }
}
