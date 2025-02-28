package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import data.DataCliente;
import clases.Cliente;

@WebServlet("/editarCliente")
public class EditarClienteServlet extends HttpServlet {
    private DataCliente dataCliente;

    @Override
    public void init() throws ServletException {
        super.init();
        dataCliente = new DataCliente(); // Inicializar DataCliente
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el idCliente de la sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idCliente") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        int idCliente = (int) session.getAttribute("idCliente");

        // Obtener los datos del cliente
        Cliente cliente = dataCliente.getById(idCliente);

        if (cliente != null) {
            // Guardar el cliente en el alcance de la solicitud
            request.setAttribute("cliente", cliente);
            // Redirigir al JSP de edición
            request.getRequestDispatcher("/cliente/editarCliente.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/cliente/informacionPersonal.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener los datos del formulario
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        // Crear un objeto Cliente con los datos actualizados
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        cliente.setDni(dni);
        cliente.setNombre(nombre);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);

        // Actualizar los datos en la base de datos
        dataCliente.editII(cliente);

        // Redirigir de vuelta a la página de información personal
        response.sendRedirect(request.getContextPath() + "/cliente/informacionPersonal.jsp");
    }
}