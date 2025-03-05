package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import data.DataProfesional; // Cambiar a DataProfesional
import clases.Profesional; // Cambiar a Profesional

@WebServlet("/editarProfesional")
public class EditarProfesionalServlet extends HttpServlet {
    private DataProfesional dataProfesional; // Cambiar a DataProfesional

    @Override
    public void init() throws ServletException {
        super.init();
        dataProfesional = new DataProfesional(); // Inicializar DataProfesional
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el idProfesional de la sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idProfesional") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int idProfesional = (int) session.getAttribute("idProfesional");

        // Obtener los datos del profesional
        Profesional profesional = dataProfesional.getById(idProfesional);

        if (profesional != null) {
            // Guardar el profesional en el alcance de la solicitud
            request.setAttribute("profesional", profesional);
            // Redirigir al JSP de edición
            request.getRequestDispatcher("/profesional/editarProfesional.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/profesional/informacionPersonal.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los datos del formulario
        int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String especialidad = request.getParameter("especialidad");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        // Crear un objeto Profesional con los datos actualizados
        Profesional profesional = new Profesional();
        profesional.setIdProfesional(idProfesional);
        profesional.setDni(dni);
        profesional.setNombre(nombre);
        profesional.setEspecialidad(especialidad);
        profesional.setTelefono(telefono);
        profesional.setEmail(email);

        // Actualizar los datos en la base de datos
        dataProfesional.editII(profesional);

        // Redirigir de vuelta a la página de información personal
        response.sendRedirect(request.getContextPath() + "/profesional/informacionPersonalProf.jsp");
    }
}