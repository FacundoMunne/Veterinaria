package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clases.Turno;
import data.DataTurno;

@WebServlet("/listaTurnosProfServlet")
public class listaTurnosProf extends HttpServlet {

    private DataTurno dataTurno;

    @Override
    public void init() throws ServletException {
        dataTurno = new DataTurno();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 📌 DEBUG: Verificar parámetros recibidos
            String idProfesionalStr = request.getParameter("idProfesional");
            String estado = request.getParameter("estado");
            String orden = request.getParameter("orden");

            System.out.println("DEBUG: Parámetros recibidos en la URL:");
            System.out.println("idProfesional: " + idProfesionalStr);
            System.out.println("estado: " + estado);
            System.out.println("orden: " + orden);

            // Si no hay idProfesional, mandar a error
            if (idProfesionalStr == null || idProfesionalStr.isEmpty()) {
                System.out.println("❌ ERROR: idProfesional es nulo o vacío.");
                request.setAttribute("errorMessage", "ID de profesional no proporcionado.");
                response.sendRedirect(request.getContextPath() + "/public/error.jsp");
                return;
            }

            // Convertir idProfesional a int
            int idProfesional = Integer.parseInt(idProfesionalStr);

            // Determinar orden ascendente o descendente
            boolean ordenAscendente = orden == null || orden.equalsIgnoreCase("asc");

            // 📌 DEBUG: Verificando valores antes de la consulta
            System.out.println("✅ ID Profesional convertido: " + idProfesional);
            System.out.println("✅ Estado: " + (estado != null ? estado : "No especificado"));
            System.out.println("✅ Orden Ascendente: " + ordenAscendente);

            // Obtener turnos desde la BD
            List<Turno> turnos = dataTurno.getTurnosByProfesional(idProfesional, estado, ordenAscendente);

            // 📌 DEBUG: Verificar si la lista tiene datos
            System.out.println("🔍 Cantidad de turnos obtenidos: " + turnos.size());
            for (Turno t : turnos) {
                System.out.println("🟢 Turno - Fecha: " + t.getFechaHora() + 
                                   ", Mascota: " + t.getMascota().getNombre() + 
                                   ", Profesional: " + t.getProfesional().getNombre());
            }

            // Pasar datos al JSP
            request.setAttribute("turnos", turnos);
            request.setAttribute("idProfesional", idProfesional);
            request.getRequestDispatcher("/profesional/listaTurnosProf.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            System.out.println("❌ ERROR: idProfesional no es un número válido.");
            e.printStackTrace();
            request.setAttribute("errorMessage", "ID de profesional no válido.");
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");

        } catch (Exception e) {
            System.out.println("❌ ERROR GENERAL:");
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        }
    }
}