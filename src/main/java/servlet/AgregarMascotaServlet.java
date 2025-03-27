package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import clases.Mascota;
import clases.Cliente;
import data.DataMascota;

@WebServlet("/agregarMascotaServlet")
public class AgregarMascotaServlet extends HttpServlet {
    private DataMascota dataMascota = new DataMascota();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // 1. Validar sesión (con forward a error.jsp)
        if (session == null || session.getAttribute("idCliente") == null) {
            setErrorAttributes(request, "Acceso denegado", "Debe iniciar sesión para realizar esta acción", "#FF9800", null);
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        }

        try {
            // 2. Validar y obtener parámetros
            String nombre = validateParameter(request, "nombre", "Nombre es requerido", 2, 50);
            String especie = validateParameter(request, "especie", "Especie es requerida", 2, 30);
            String raza = request.getParameter("raza"); 
            int edad = validateAge(request.getParameter("edad"));

            // 3. Crear objetos
            Cliente cliente = new Cliente();
            cliente.setIdCliente((int) session.getAttribute("idCliente"));

            Mascota mascota = new Mascota();
            mascota.setNombre(nombre);
            mascota.setEspecie(especie);
            mascota.setRaza(raza != null ? raza.trim() : "");
            mascota.setEdad(edad);
            mascota.setCliente(cliente);

            // 4. Guardar en BD
            dataMascota.add(mascota);

            // 5. Redirigir con éxito
            session.setAttribute("successMessage", "Mascota agregada correctamente");
            response.sendRedirect(request.getContextPath() + "/listarMascotas");

        } catch (NumberFormatException e) {
            setErrorAttributes(request, "Error de Formato", "La edad debe ser un número válido", "#FF9800", e);
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            setErrorAttributes(request, "Error de Validación", e.getMessage(), "#FF9800", e);
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
        } catch (Exception e) {
            setErrorAttributes(request, "Error del Sistema", "No se pudo agregar la mascota", "#F44336", e);
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
        }
    }

    // Métodos auxiliares
    private void setErrorAttributes(HttpServletRequest request, String type, 
                                    String message, String color, Exception e) {
        request.setAttribute("errorType", type);
        request.setAttribute("errorMessage", message);
        request.setAttribute("errorColor", color);
        request.setAttribute("errorRedirect", request.getContextPath() + "/public/agregarMascota.jsp");
        request.setAttribute("errorButtonText", "Volver al formulario");
        
        if (e != null) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            request.setAttribute("errorDebug", sw.toString());
        }
    }

    private String validateParameter(HttpServletRequest request, String paramName, String errorMessage, int minLength, int maxLength) 
            throws IllegalArgumentException {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
        value = value.trim();
        if (value.length() < minLength || value.length() > maxLength) {
            throw new IllegalArgumentException(paramName + " debe tener entre " + minLength + " y " + maxLength + " caracteres");
        }
        return value;
    }

    private int validateAge(String ageStr) throws IllegalArgumentException {
        if (ageStr == null || ageStr.trim().isEmpty()) {
            throw new IllegalArgumentException("La edad es requerida");
        }
        int age = Integer.parseInt(ageStr);
        if (age <= 0) {
            throw new IllegalArgumentException("La edad debe ser un número positivo");
        }
        return age;
    }
}