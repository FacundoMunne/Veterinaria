package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clases.Cliente;
import data.DataCliente;

@WebServlet("/crudcliente")
public class CRUDCliente extends HttpServlet {
    private DataCliente dataCliente;

    @Override
    public void init() throws ServletException {
        super.init();
        dataCliente = new DataCliente(); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null) {
                List<Cliente> clientes = dataCliente.getAll();
                request.setAttribute("clientes", clientes);
                request.getRequestDispatcher("listaClientes.jsp").forward(request, response);
            } else if (action.equals("edit")) {
                // Editar cliente
                int id = Integer.parseInt(request.getParameter("id"));
                Cliente cliente = dataCliente.getById(id);
                request.setAttribute("cliente", cliente);
                request.getRequestDispatcher("formCliente.jsp").forward(request, response);
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
        String id = request.getParameter("idCliente");
        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String direccion = request.getParameter("direccion");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        Cliente cliente = new Cliente(
            id != null && !id.isEmpty() ? Integer.parseInt(id) : 0,
            dni, nombre, direccion, telefono, email
        );

        try {
            if (cliente.getIdCliente() > 0) {
                dataCliente.edit(cliente); // Editar cliente existente
            } else {
                dataCliente.add(cliente); // Agregar nuevo cliente
            }
            response.sendRedirect("crudcliente"); // Redirige a la lista de clientes
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
