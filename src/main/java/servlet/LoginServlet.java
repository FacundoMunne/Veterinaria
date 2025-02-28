package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import data.DataUsuario;
import clases.Usuario;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private DataUsuario dataUsuario;

    @Override
    public void init() throws ServletException {
        super.init();
        dataUsuario = new DataUsuario(); // Inicializar DataUsuario
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contraseña = request.getParameter("contraseña");

        // Depuración: Verificar datos recibidos
        System.out.println("DEBUG: Intento de login - Usuario: " + nombreUsuario);

        // Buscar el usuario en la base de datos
        Usuario usuario = dataUsuario.getByNombreUsuario(nombreUsuario);

        if (usuario != null && BCrypt.checkpw(contraseña, usuario.getContraseña())) {
            // Autenticación exitosa
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario); // Guardar el usuario en la sesión
            session.setAttribute("rol", usuario.getRol().getNombre()); // Guardar el nombre del rol en la sesión

            // Obtener y guardar la id correspondiente según el rol
            if (usuario.getRol().getIdRol() == 3) { // Rol de cliente
                int idCliente = dataUsuario.getIdCliente(usuario.getIdUsuario());
                session.setAttribute("idCliente", idCliente);
            } else if (usuario.getRol().getIdRol() == 2) { // Rol de profesional
                int idProfesional = dataUsuario.getIdProfesional(usuario.getIdUsuario());
                session.setAttribute("idProfesional", idProfesional);
            }

            // Depuración: Verificar rol y redirección
            System.out.println("DEBUG: Autenticación exitosa - Rol: " + usuario.getRol().getNombre());

            // Redirigir según el rol del usuario
            if (usuario.getRol().getIdRol() == 3) { // Cliente
                response.sendRedirect(request.getContextPath() + "/public/menu.jsp");
            } else if (usuario.getRol().getIdRol() == 2) { // Profesional
                response.sendRedirect(request.getContextPath() + "/public/menu.jsp");
            } else { // Otros roles (por ejemplo, admin)
                response.sendRedirect(request.getContextPath() + "/public/menu.jsp");
            }
        } else {
            // Autenticación fallida
            System.out.println("DEBUG: Autenticación fallida - Usuario o contraseña incorrectos");

            if (usuario == null) {
                request.setAttribute("error", "El usuario no existe.");
            } else {
                request.setAttribute("error", "Contraseña incorrecta.");
            }

            // Redirigir de nuevo al formulario de login con un mensaje de error
            request.getRequestDispatcher("/public/login.jsp").forward(request, response);
        }
    }
}