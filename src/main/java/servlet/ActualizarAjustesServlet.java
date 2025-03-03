package servlet;

import clases.Usuario;
import data.DataUsuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/actualizarAjustes")
public class ActualizarAjustesServlet extends HttpServlet {
    private DataUsuario dataUsuario = new DataUsuario();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);

        // Verificar si el usuario está logueado
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Obtener los datos del formulario
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contraseña = request.getParameter("contraseña");

        // Actualizar el nombre de usuario y la contraseña en el objeto Usuario
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContraseña(contraseña);

        // Guardar los cambios en la base de datos
        dataUsuario.actualizarUsuario(usuario);

        // Actualizar el usuario en la sesión
        session.setAttribute("usuario", usuario);

        // Redirigir de vuelta al menú con un mensaje de éxito
        response.sendRedirect(request.getContextPath() + "/public/menu.jsp?mensaje=Cambios guardados correctamente");
    }
}