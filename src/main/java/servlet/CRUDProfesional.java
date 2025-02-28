package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import clases.Profesional;
import clases.Usuario;
import clases.Rol;
import data.DataProfesional;
import data.DataUsuario;

@WebServlet("/crudprofesional")
public class CRUDProfesional extends HttpServlet {
    private DataProfesional dataProfesional;
    private DataUsuario dataUsuario;

    @Override
    public void init() throws ServletException {
        super.init();
        dataProfesional = new DataProfesional();
        dataUsuario = new DataUsuario(); // Inicializar DataUsuario
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                // Obtener la lista de profesionales
                List<Profesional> profesionales = dataProfesional.getAll();
                System.out.println("Profesionales obtenidos: " + profesionales.size()); // Depuración

                // Pasar la lista de profesionales al JSP
                request.setAttribute("profesionales", profesionales);
                request.getRequestDispatcher("admin/listaProfesionales.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                // Editar profesional
                int id = Integer.parseInt(request.getParameter("id"));
                Profesional profesional = dataProfesional.getById(id);
                request.setAttribute("profesional", profesional);
                request.getRequestDispatcher("/admin/formProfesional.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                // Eliminar profesional
                int id = Integer.parseInt(request.getParameter("id"));
                dataProfesional.remove(id);
                response.sendRedirect("crudprofesional");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entrando al método doPost..."); // Depuración

        // Datos del Profesional
        String id = request.getParameter("idProfesional");
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String especialidad = request.getParameter("especialidad");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        // Datos del Usuario
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contraseña = request.getParameter("contraseña");

        // Depuración: Imprimir datos recibidos
        System.out.println("DNI: " + dni);
        System.out.println("Nombre: " + nombre);
        System.out.println("Especialidad: " + especialidad);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Email: " + email);
        System.out.println("Nombre de Usuario: " + nombreUsuario);
        System.out.println("Contraseña: " + contraseña);

        // Crear el Usuario con rol Profesional (idRol = 2)
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContraseña(BCrypt.hashpw(contraseña, BCrypt.gensalt())); // Hashear la contraseña
        usuario.setRol(new Rol(2, "Profesional")); // Asignar el rol Profesional (idRol = 2)

        // Crear el Profesional (sin Usuario por ahora)
        Profesional profesional = new Profesional(
            id != null && !id.isEmpty() ? Integer.parseInt(id) : 0,
            dni, nombre, especialidad, telefono, email
        );

        // Guardar el Profesional y el Usuario en la base de datos
        System.out.println("Guardando Profesional y Usuario en la base de datos..."); // Depuración
        dataProfesional.add(profesional, usuario); // Pasar ambos objetos al método add

        // Redirigir a la lista de profesionales
        System.out.println("Redirigiendo a la lista de profesionales..."); // Depuración
        response.sendRedirect("crudprofesional");
    }
}