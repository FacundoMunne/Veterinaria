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
            // Obtener la sesión actual
            HttpSession session = request.getSession(false);

            // Verificar si la sesión existe y si contiene el idProfesional
            if (session == null || session.getAttribute("idProfesional") == null) {
                System.out.println("❌ ERROR: No hay sesión o el idProfesional no está en la sesión.");
                request.setAttribute("errorMessage", "Debes iniciar sesión para acceder a esta página.");
                response.sendRedirect(request.getContextPath() + "/public/error.jsp");
                return;
            }

            // Obtener el idProfesional desde la sesión
            int idProfesional = (int) session.getAttribute("idProfesional");

            // Obtener parámetros adicionales (estado y orden) desde la solicitud
            String estado = request.getParameter("estado");
            String orden = request.getParameter("orden");

            // 📌 DEBUG: Verificar valores obtenidos
            System.out.println("✅ ID Profesional obtenido de la sesión: " + idProfesional);
            System.out.println("✅ Estado: " + (estado != null ? estado : "No especificado"));
            System.out.println("✅ Orden: " + (orden != null ? orden : "No especificado"));

            // Determinar orden ascendente o descendente
            boolean ordenAscendente = orden == null || orden.equalsIgnoreCase("asc");

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

        } catch (Exception e) {
            System.out.println("❌ ERROR GENERAL:");
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        }
    }
}