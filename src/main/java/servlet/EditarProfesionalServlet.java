package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import data.DataProfesional;
import clases.Profesional;

@WebServlet("/editarProfesional")
public class EditarProfesionalServlet extends HttpServlet {
    private DataProfesional dataProfesional;

    @Override
    public void init() throws ServletException {
        super.init();
        dataProfesional = new DataProfesional();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String redirectUrl = request.getContextPath() + "/profesional/informacionPersonalProf.jsp";
        
        try {
            // 1. Validar sesión
            if (session == null || session.getAttribute("idProfesional") == null) {
                response.sendRedirect(request.getContextPath() + "/public/login.jsp");
                return;
            }

            // 2. Obtener ID del profesional
            int idProfesional = (int) session.getAttribute("idProfesional");

            // 3. Obtener datos del profesional
            Profesional profesional = dataProfesional.getById(idProfesional);
            if (profesional == null) {
                throw new IllegalArgumentException("Profesional no encontrado");
            }

            // 4. Pasar datos a la vista
            request.setAttribute("profesional", profesional);
            request.getRequestDispatcher("/profesional/editarProfesional.jsp").forward(request, response);
            return;
            
        } catch (Exception e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Error al cargar datos del profesional");
            request.setAttribute("errorMessage", "Ocurrió un error al intentar cargar los datos del profesional.");
            request.setAttribute("errorRedirect", request.getContextPath() + "/profesional/informacionPersonalProf.jsp");
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage()); // Puedes agregar detalles del error
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String redirectUrl = request.getContextPath() + "/profesional/informacionPersonalProf.jsp";
        
        try {
            // 1. Validar sesión
            if (session == null || session.getAttribute("idProfesional") == null) {
                response.sendRedirect(request.getContextPath() + "/public/login.jsp");
                return;
            }

            // 2. Validar y obtener parámetros
            int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
            String dni = validateDni(request.getParameter("dni"));
            String nombre = validateParameter(request.getParameter("nombre"), "Nombre");
            String especialidad = validateParameter(request.getParameter("especialidad"), "Especialidad");
            String telefono = validatePhone(request.getParameter("telefono"));
            String email = validateEmail(request.getParameter("email"));

            // 3. Verificar coincidencia de ID de sesión con formulario
            int sessionId = (int) session.getAttribute("idProfesional");
            if (idProfesional != sessionId) {
                throw new SecurityException("No puedes editar otros profesionales");
            }

            // 4. Crear y actualizar profesional
            Profesional profesional = new Profesional();
            profesional.setIdProfesional(idProfesional);
            profesional.setDni(dni);
            profesional.setNombre(nombre);
            profesional.setEspecialidad(especialidad);
            profesional.setTelefono(telefono);
            profesional.setEmail(email);

            dataProfesional.editII(profesional);
            
            // 5. Mensaje de éxito
            request.getSession().setAttribute("successMessage", "Datos actualizados correctamente");
            response.sendRedirect(redirectUrl); // Redirige a la página de información personal
            return;
            
        } catch (NumberFormatException e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "ID de profesional inválido");
            request.setAttribute("errorMessage", "El ID de profesional no es válido.");
            request.setAttribute("errorRedirect", request.getContextPath() + "/profesional/editarProfesional.jsp");
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Entrada inválida");
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("errorRedirect", request.getContextPath() + "/profesional/editarProfesional.jsp");
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (SecurityException e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Error de seguridad");
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("errorRedirect", request.getContextPath() + "/profesional/informacionPersonalProf.jsp");
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } catch (Exception e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Error general");
            request.setAttribute("errorMessage", "Ocurrió un error al intentar actualizar los datos del profesional.");
            request.setAttribute("errorRedirect", request.getContextPath() + "/profesional/informacionPersonalProf.jsp");
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
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
