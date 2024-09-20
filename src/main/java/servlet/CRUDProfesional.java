package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clases.Profesional;
import data.DataProfesional;

@WebServlet("/crudprofesional")
public class CRUDProfesional extends HttpServlet {
    private DataProfesional dataProfesional;

    @Override
    public void init() throws ServletException {
        super.init();
        dataProfesional = new DataProfesional(); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                List<Profesional> profesionales = dataProfesional.getAll();
                request.setAttribute("profesionales", profesionales);
                request.getRequestDispatcher("listaProfesionales.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Profesional profesional = dataProfesional.getById(id);
                request.setAttribute("profesional", profesional);
                request.getRequestDispatcher("formProfesional.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                dataProfesional.remove(id);
                response.sendRedirect("crudprofesional");
            }
        } catch (NumberFormatException e) {
            throw new ServletException("Invalid number format", e);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("idProfesional");
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String especialidad = request.getParameter("especialidad");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        Profesional profesional = new Profesional(
            id != null && !id.isEmpty() ? Integer.parseInt(id) : 0,
            dni, nombre, especialidad, telefono, email
        );

        try {
            if (profesional.getIdProfesional() > 0) {
                dataProfesional.edit(profesional); // Editar profesional existente
            } else {
                dataProfesional.add(profesional); // Agregar nuevo profesional
            }
            response.sendRedirect("crudprofesional"); // Redirige a la lista de profesionales
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
