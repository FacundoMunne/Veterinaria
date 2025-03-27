package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clases.*;
import data.*;

@WebServlet("/buscarMascotas")
public class BuscarMascotas extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // 1. Validar parámetro DNI
            String dni = validateDni(request.getParameter("dni"));

            // 2. Buscar cliente
            DataCliente dataCliente = new DataCliente();
            Cliente cliente = dataCliente.getByDni(dni);
            if (cliente == null) {
                throw new IllegalArgumentException("No existe un cliente con DNI: " + dni);
            }

            // 3. Obtener datos relacionados
            DataMascota dataMascota = new DataMascota();
            DataProfesional dataProfesional = new DataProfesional();

            List<Mascota> mascotas = dataMascota.getByClienteId(cliente.getIdCliente());
            List<Profesional> profesionales = dataProfesional.getAll();

            // 4. Validar si el cliente tiene mascotas registradas
            if (mascotas == null || mascotas.isEmpty()) {
                throw new IllegalArgumentException("El cliente no tiene mascotas registradas");
            }

            // 5. Enviar datos a la vista
            request.setAttribute("cliente", cliente);
            request.setAttribute("mascotas", mascotas);
            request.setAttribute("profesionales", profesionales);
            request.getRequestDispatcher("/admin/seleccionarTurno.jsp").forward(request, response);

        } catch (IllegalArgumentException e) {
            setErrorAttributes(request, "Error de Validación", e.getMessage(), "#FF9800", e);
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);

        } catch (Exception e) {
            setErrorAttributes(request, "Error del Sistema", "Ocurrió un error al buscar las mascotas", "#F44336", e);
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
        }
    }

    private String validateDni(String dni) throws IllegalArgumentException {
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI es requerido");
        }
        if (!dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("DNI debe contener 8 dígitos numéricos");
        }
        return dni.trim();
    }

    private void setErrorAttributes(HttpServletRequest request, String type, 
                                    String message, String color, Exception e) {
        request.setAttribute("errorType", type);
        request.setAttribute("errorMessage", message);
        request.setAttribute("errorColor", color);
        request.setAttribute("errorRedirect", request.getContextPath() + "/admin/buscarCliente.jsp");
        request.setAttribute("errorButtonText", "Volver a buscar");

        if (e != null) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            request.setAttribute("errorDebug", sw.toString());
        }
    }
}
