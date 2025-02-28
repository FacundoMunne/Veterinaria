package servlet;

import java.io.IOException;
import java.util.List;

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
import data.DataUsuario;

@WebServlet("/crudcliente")
public class CRUDCliente extends HttpServlet {
    private DataCliente dataCliente;
    private DataUsuario dataUsuario;

    @Override
    public void init() throws ServletException {
        super.init();
        dataCliente = new DataCliente();
        dataUsuario = new DataUsuario(); // Inicializar DataUsuario
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                // Obtener la lista de clientes
                List<Cliente> clientes = dataCliente.getAll();
                System.out.println("Clientes obtenidos: " + clientes.size()); // Depuración

                // Pasar la lista de clientes al JSP
                request.setAttribute("clientes", clientes);
                request.getRequestDispatcher("/admin/listaClientes.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                // Editar cliente
                int id = Integer.parseInt(request.getParameter("id"));
                Cliente cliente = dataCliente.getById(id);
                request.setAttribute("cliente", cliente);
                request.getRequestDispatcher("/admin/formCliente.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                // Eliminar cliente
                int id = Integer.parseInt(request.getParameter("id"));
                dataCliente.remove(id);
                response.sendRedirect("crudcliente");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entrando al método doPost..."); // Depuración

        // Datos del Cliente
        String id = request.getParameter("idCliente");
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        // Datos del Usuario
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contraseña = request.getParameter("contraseña");

        // Depuración: Imprimir datos recibidos
        System.out.println("DNI: " + dni);
        System.out.println("Nombre: " + nombre);
        System.out.println("Dirección: " + direccion);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Email: " + email);
        System.out.println("Nombre de Usuario: " + nombreUsuario);
        System.out.println("Contraseña: " + contraseña);

        // Crear el Usuario con rol Cliente (idRol = 3)
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setContraseña(BCrypt.hashpw(contraseña, BCrypt.gensalt())); // Hashear la contraseña
        usuario.setRol(new Rol(3, "Cliente")); // Asignar el rol Cliente (idRol = 3)

        // Crear el Cliente (sin Usuario por ahora)
        Cliente cliente = new Cliente(
            id != null && !id.isEmpty() ? Integer.parseInt(id) : 0,
            dni, nombre, direccion, telefono, email
        );

        // Guardar el Cliente y el Usuario en la base de datos
        DataCliente dataCliente = new DataCliente();
        System.out.println("Guardando Cliente y Usuario en la base de datos..."); // Depuración
        dataCliente.add(cliente, usuario); // Pasar ambos objetos al método add

        // Redirigir a la lista de clientes
        System.out.println("Redirigiendo a la lista de clientes..."); // Depuración
        response.sendRedirect("crudcliente");
    }
}