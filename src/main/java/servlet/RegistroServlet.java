package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import clases.Cliente;
import clases.Usuario;
import clases.Rol;
import data.DataCliente;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private DataCliente dataCliente;

    @Override
    public void init() throws ServletException {
        dataCliente = new DataCliente();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener los datos del formulario
            String dni = request.getParameter("dni");
            String nombre = request.getParameter("nombre");
            String direccion = request.getParameter("direccion");
            String telefono = request.getParameter("telefono");
            String email = request.getParameter("email");
            String nombreUsuario = request.getParameter("nombreUsuario");
            String contraseña = request.getParameter("contraseña");

            // Validar que todos los campos estén presentes
            if (dni == null || nombre == null || direccion == null || telefono == null || email == null || nombreUsuario == null || contraseña == null) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }

            // Hashear la contraseña
            String contraseñaHasheada = BCrypt.hashpw(contraseña, BCrypt.gensalt());

            // Crear el objeto Usuario
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setContraseña(contraseñaHasheada);
            usuario.setRol(new Rol(3, "Cliente")); // Rol Cliente (idRol = 3)

            // Crear el objeto Cliente
            Cliente cliente = new Cliente();
            cliente.setDni(dni);
            cliente.setNombre(nombre);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            cliente.setEmail(email);

            // Llamar al método add para registrar el cliente y el usuario
            dataCliente.add(cliente, usuario);

            // Si no hubo excepciones, el registro fue exitoso
            response.sendRedirect(request.getContextPath() + "/public/registroExitoso.jsp");

        } catch (IllegalArgumentException e) {
            // Capturar errores de validación de campos
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);

        } catch (Exception e) {
            // Capturar cualquier otra excepción
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error inesperado. Intente nuevamente.");
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
        }
    }
}