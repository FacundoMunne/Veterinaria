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
        dataCliente = new DataCliente();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String redirectUrl = request.getContextPath() + "/cliente/informacionPersonal.jsp";
        
        try {
            // 1. Validar sesión
            if (session == null || session.getAttribute("idCliente") == null) {
                response.sendRedirect(request.getContextPath() + "/public/login.jsp");
                return;
            }

            // 2. Obtener ID del cliente
            int idCliente = (int) session.getAttribute("idCliente");

            // 3. Obtener datos del cliente
            Cliente cliente = dataCliente.getById(idCliente);
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente no encontrado");
            }

            // 4. Pasar datos a la vista
            request.setAttribute("cliente", cliente);
            request.getRequestDispatcher("/cliente/editarCliente.jsp").forward(request, response);
            return;
            
        } catch (Exception e) {
            request.getSession().setAttribute("errorType", "Error al cargar datos del cliente");
            request.getSession().setAttribute("errorMessage", "No se pudo cargar la información del cliente.");
            request.getSession().setAttribute("errorDebug", e.toString());
            request.getSession().setAttribute("errorRedirect", request.getContextPath() + "/cliente/informacionPersonal.jsp");
            request.getSession().setAttribute("errorButtonText", "Volver a la página de información");
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String redirectUrl = request.getContextPath() + "/cliente/informacionPersonal.jsp";
        
        try {
            // 1. Validar sesión
            if (session == null || session.getAttribute("idCliente") == null) {
                response.sendRedirect(request.getContextPath() + "/public/login.jsp");
                return;
            }

            // 2. Validar y obtener parámetros
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
            String dni = validateDni(request.getParameter("dni"));
            String nombre = validateParameter(request.getParameter("nombre"), "Nombre");
            String direccion = request.getParameter("direccion");
            String telefono = validatePhone(request.getParameter("telefono"));
            String email = validateEmail(request.getParameter("email"));

            // 3. Verificar coincidencia de ID de sesión con formulario
            int sessionId = (int) session.getAttribute("idCliente");
            if (idCliente != sessionId) {
                throw new SecurityException("No puedes editar otros clientes");
            }
            // 4. Crear y actualizar cliente
            Cliente cliente = new Cliente();
            cliente.setIdCliente(idCliente);
            cliente.setDni(dni);
            cliente.setNombre(nombre);
            cliente.setDireccion(direccion);
            cliente.setTelefono(telefono);
            cliente.setEmail(email);

            dataCliente.editII(cliente);
            
            // 5. Mensaje de éxito
            request.getSession().setAttribute("successMessage", "Datos actualizados correctamente");
            response.sendRedirect(redirectUrl); // Redirige a la página de información personal
            return;
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorType", "Error en los datos ingresados");
            request.getSession().setAttribute("errorMessage", "El ID de cliente no es válido.");
            request.getSession().setAttribute("errorDebug", e.toString());
            request.getSession().setAttribute("errorRedirect", request.getContextPath() + "/cliente/editarCliente.jsp");
            request.getSession().setAttribute("errorButtonText", "Volver a editar cliente");
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("errorType", "Error de validación");
            request.getSession().setAttribute("errorMessage", e.getMessage());
            request.getSession().setAttribute("errorDebug", e.toString());
            request.getSession().setAttribute("errorRedirect", request.getContextPath() + "/cliente/editarCliente.jsp");
            request.getSession().setAttribute("errorButtonText", "Volver a editar cliente");
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        } catch (SecurityException e) {
            request.getSession().setAttribute("errorType", "Error de seguridad");
            request.getSession().setAttribute("errorMessage", e.getMessage());
            request.getSession().setAttribute("errorDebug", e.toString());
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        } catch (Exception e) {
            request.getSession().setAttribute("errorType", "Error al actualizar cliente");
            request.getSession().setAttribute("errorMessage", "Ocurrió un problema al actualizar los datos del cliente.");
            request.getSession().setAttribute("errorDebug", e.toString());
            request.getSession().setAttribute("errorRedirect", request.getContextPath() + "/cliente/informacionPersonal.jsp");
            request.getSession().setAttribute("errorButtonText", "Volver a la página de información");
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        }
    }

    // Métodos auxiliares de validación
    private String validateParameter(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " es requerido");
        }
        return value.trim();
    }

    private String validateDni(String dni) {
        dni = validateParameter(dni, "DNI");
        if (!dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("DNI debe tener 8 dígitos numéricos");
        }
        return dni;
    }

    private String validatePhone(String phone) {
        phone = validateParameter(phone, "Teléfono");
        if (!phone.matches("[0-9]{9,15}")) {
            throw new IllegalArgumentException("Teléfono debe contener entre 9 y 15 dígitos");
        }
        return phone;
    }

    private String validateEmail(String email) {
        email = validateParameter(email, "Email");
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        return email;
    }
}
