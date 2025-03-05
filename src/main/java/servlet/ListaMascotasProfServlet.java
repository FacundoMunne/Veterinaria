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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener el idProfesional de la sesión
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("idProfesional") == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            int idProfesional = (int) session.getAttribute("idProfesional");

            // Obtener la lista de mascotas
            List<Mascota> mascotas = dataTurno.getMascotasByProfesional(idProfesional);

            // Pasar las mascotas al JSP
            request.setAttribute("mascotas", mascotas);
            request.getRequestDispatcher("/profesional/listaMascotasProf.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al obtener la lista de mascotas.");
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        }
    }
}
