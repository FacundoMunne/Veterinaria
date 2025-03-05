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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener el idMascota desde los parámetros
            int idMascota = Integer.parseInt(request.getParameter("idMascota"));

            // Obtener el idProfesional de la sesión
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("idProfesional") == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
            int idProfesional = (int) session.getAttribute("idProfesional");

            // Obtener el historial de turnos
            List<Turno> turnos = dataTurno.getHistorialTurnosByMascotaAndProfesional(idMascota, idProfesional);

            // Pasar los turnos al JSP
            request.setAttribute("turnos", turnos);
            request.getRequestDispatcher("/profesional/historialTurnosMascota.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al obtener el historial de turnos.");
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        }
    }
}
