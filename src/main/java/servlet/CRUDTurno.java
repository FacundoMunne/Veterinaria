package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.*;
import clases.*;

@WebServlet("/crudTurno")
public class CRUDTurno extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DataTurno dataTurno;

    @Override
    public void init() throws ServletException {
        // Inicializar DataTurno 
        dataTurno = new DataTurno();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperar parámetros de la solicitud
        int idMascota = Integer.parseInt(request.getParameter("idMascota"));
        int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));

        try {
        	Turno turno = dataTurno.getById(idMascota,idProfesional );
            // Eliminar el turno
            dataTurno.remove(turno);
            // Redirigir a la lista de turnos después de la eliminación
            response.sendRedirect("listaTurnos");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "No se pudo eliminar el turno.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
