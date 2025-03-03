package servlet;

import clases.Profesional;
import data.DataProfesional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/sacarTurno")
public class SacarTurnoServlet extends HttpServlet {
    private DataProfesional dataProfesional = new DataProfesional();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el idMascota de la solicitud
        String idMascotaParam = request.getParameter("idMascota");
        if (idMascotaParam == null || idMascotaParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/listarMascotas");
            return;
        }

        int idMascota = Integer.parseInt(idMascotaParam);

        // Obtener la lista de profesionales disponibles
        List<Profesional> profesionales = dataProfesional.getAll();

        // Guardar el idMascota y la lista de profesionales en el alcance de la solicitud
        request.setAttribute("idMascota", idMascota);
        request.setAttribute("profesionales", profesionales);

        // Redirigir al JSP para seleccionar profesional y horario
        request.getRequestDispatcher("/cliente/seleccionarTurno.jsp").forward(request, response);
    }
}