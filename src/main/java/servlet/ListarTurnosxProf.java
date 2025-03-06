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

@WebServlet("/listaTurnosxProf")
public class ListarTurnosxProf extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataTurno dataTurno = new DataTurno();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
        String estado = request.getParameter("estado"); // Puedes pasar el estado como parámetro si lo deseas
        boolean ordenAscendente = true; // Puedes cambiar esto según lo que necesites

        List<Turno> turnos = dataTurno.getTurnosByProfesional(idProfesional, estado, ordenAscendente);

        request.setAttribute("turnos", turnos);
        request.getRequestDispatcher("/admin/listaTurnosxProf.jsp").forward(request, response);
    }
}