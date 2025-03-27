package servlet;

import clases.Usuario;
import data.DataUsuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebServlet("/actualizarAjustes")
public class ActualizarAjustesServlet extends HttpServlet {
    private DataUsuario dataUsuario = new DataUsuario();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // 1. Validar sesión (con redirección a error.jsp)
        if (session == null || session.getAttribute("usuario") == null) {
            setErrorAttributes(request, "Acceso denegado", "Debe iniciar sesión para realizar esta acción", "#FF9800", null);
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
            return;
        }

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contraseña = request.getParameter("password");

            // 2. Validar inputs
            if (nombreUsuario == null || nombreUsuario.trim().isEmpty() || contraseña == null || contraseña.trim().isEmpty()) {
                throw new IllegalArgumentException("Nombre de usuario y contraseña son obligatorios");
            }



            // 5. Actualizar usuario
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContraseña(contraseña);
            dataUsuario.actualizarUsuario(usuario);
            session.setAttribute("usuario", usuario);

            // 6. Redirigir con mensaje de éxito
            session.setAttribute("successMessage", "Cambios guardados correctamente");

            // 7. Redirigir a la página correspondiente según el rol
            String redirectUrl;
            if (usuario.getRol().getIdRol() == 3) { // Cliente
                redirectUrl = request.getContextPath() + "/cliente/ajustesCliente.jsp";
            } else if (usuario.getRol().getIdRol() == 2) { // Profesional
                redirectUrl = request.getContextPath() + "/profesional/ajustesProfesional.jsp";
            } else { // Redirigir a una página por defecto (si no es cliente ni profesional)
                redirectUrl = request.getContextPath() + "/public/menu.jsp";
            }
            response.sendRedirect(redirectUrl);

        } catch (IllegalArgumentException e) {
            // Error de validación (inputs incorrectos)
            setErrorAttributes(request, "Error de Validación", e.getMessage(), "#FF9800", e);
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);

        } catch (Exception e) {
            // Error inesperado (BD, etc.)
            setErrorAttributes(request, "Error del Sistema", "No se pudieron guardar los cambios. Intente más tarde.", "#F44336", e);
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
        }
    }

    private void setErrorAttributes(HttpServletRequest request, String type, 
                                  String message, String color, Exception e) {
        // Mensajes para el usuario
        request.setAttribute("errorType", type);
        request.setAttribute("errorMessage", message);
        request.setAttribute("errorColor", color);
        request.setAttribute("errorRedirect", request.getContextPath() + "/cliente/ajustesCliente.jsp");
        request.setAttribute("errorButtonText", "Volver a ajustes");
        
        // Detalles técnicos para desarrollo
        if (e != null) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            request.setAttribute("errorDebug", sw.toString());
        }
    }
}