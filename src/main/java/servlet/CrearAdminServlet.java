package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import data.DataUsuario;
import clases.Usuario;
import clases.Rol;

@WebServlet("/crearAdmin")
public class CrearAdminServlet extends HttpServlet {
    private DataUsuario dataUsuario;

    @Override
    public void init() throws ServletException {
        super.init();
        dataUsuario = new DataUsuario(); // Inicializar DataUsuario
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Recibir datos del formulario JSP
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contraseñaPlana = request.getParameter("password");

            // Crear un usuario administrador
            Usuario admin = new Usuario();
            admin.setNombreUsuario(nombreUsuario);

            // Hashear la contraseña
            String contraseñaHasheada = BCrypt.hashpw(contraseñaPlana, BCrypt.gensalt());
            admin.setContraseña(contraseñaHasheada);

            // Asignar el rol de administrador
            Rol rolAdmin = new Rol();
            rolAdmin.setIdRol(1); // Ajusta el ID según tu base de datos
            admin.setRol(rolAdmin);

            // Guardar el usuario en la base de datos
            dataUsuario.add(admin);

            // Respuesta
            response.getWriter().println("Usuario administrador creado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error al crear el usuario administrador: " + e.getMessage());
        }
    }
}