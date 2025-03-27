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
        try {
            // Obtener el idMascota de la solicitud
            int idMascota = Integer.parseInt(request.getParameter("idMascota"));

            // Obtener las observaciones de la mascota
            DataObservacion dataObs = new DataObservacion();
            List<Observacion> observaciones = dataObs.getByMascotaId(idMascota);

            // Pasar las observaciones a la página JSP
            request.setAttribute("observaciones", observaciones);
            request.getRequestDispatcher("/profesional/verObservaciones.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Capturar el error si el idMascota no es un número válido
            System.out.println("ERROR: El idMascota no es un número válido.");
            request.setAttribute("error", "El ID de la mascota no es válido.");
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);

        } catch (SQLException e) {
            // Capturar error de SQL y redirigir a error.jsp
            System.out.println("ERROR: Fallo al obtener las observaciones de la base de datos.");
            e.printStackTrace();
            request.setAttribute("error", "Error al obtener las observaciones de la mascota.");
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);

        } catch (Exception e) {
            // Capturar cualquier otro error inesperado
            System.out.println("ERROR INESPERADO: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error inesperado. Intente nuevamente.");
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
        }
    }
}
