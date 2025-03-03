package servlet;

import clases.Mascota;
import data.DataMascota;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/listarMascotas")
public class ListarMascotasServlet extends HttpServlet {
    private DataMascota dataMascota = new DataMascota();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);

        // Verificar si el usuario está logueado y es un cliente
        if (session == null || session.getAttribute("idCliente") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Obtener la idCliente de la sesión
        int idCliente = (int) session.getAttribute("idCliente");

        // Obtener las mascotas del cliente
        List<Mascota> mascotas = dataMascota.getByClienteId(idCliente);

        // Guardar las mascotas en el alcance de la solicitud
        request.setAttribute("mascotas", mascotas);

        // Redirigir al JSP que muestra la lista de mascotas
        request.getRequestDispatcher("/cliente/misMascotas.jsp").forward(request, response);
    }
}