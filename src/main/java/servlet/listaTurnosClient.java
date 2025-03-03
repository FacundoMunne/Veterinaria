package servlet;

import clases.Turno;
import data.DataTurno;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/listarTurnos")
public class listaTurnosClient extends HttpServlet {
    private DataTurno dataTurno = new DataTurno();

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

        // Obtener los turnos asociados a las mascotas del cliente
        List<Turno> turnos = dataTurno.getByClienteId(idCliente);

        // Guardar los turnos en el alcance de la solicitud
        request.setAttribute("turnos", turnos);

        // Redirigir al JSP que muestra la lista de turnos
        request.getRequestDispatcher("/cliente/misTurnos.jsp").forward(request, response);
    }
}