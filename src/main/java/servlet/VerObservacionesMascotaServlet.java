package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clases.Observacion;
import data.DataObservacion;
import java.sql.SQLException;

@WebServlet("/verObservacionesMascotaServlet")
public class VerObservacionesMascotaServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el idMascota de la solicitud
        int idMascota = Integer.parseInt(request.getParameter("idMascota"));

        // Obtener las observaciones de la mascota
        DataObservacion dataObs = new DataObservacion();
        List<Observacion> observaciones = null;

        try {
            observaciones = dataObs.getByMascotaId(idMascota);
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir el error en la consola del servidor
            request.setAttribute("error", "Error al obtener las observaciones de la mascota.");
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return; // Salir del método para evitar procesamiento adicional
        }

        // Pasar las observaciones a la página JSP
        request.setAttribute("observaciones", observaciones);
        request.getRequestDispatcher("/profesional/verObservaciones.jsp").forward(request, response);
    }
}