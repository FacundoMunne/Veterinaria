package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import clases.Turno;
import clases.Usuario;
import clases.Observacion;
import data.DataTurno;
import data.DataObservacion;

@WebServlet("/cambiarEstadoTurnoServlet")
public class CambiarEstadoTurnoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // 1. Validar sesión
        if (session == null || session.getAttribute("usuario") == null) {
            setErrorAndRedirect(session, response, "Acceso denegado", 
                "Debe iniciar sesión para realizar esta acción", "#FF9800", "/");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        try {
            // 2. Validar y obtener parámetros
            int idMascota = parseInteger(request.getParameter("idMascota"), "ID de mascota inválido");
            int idProfesional = parseInteger(request.getParameter("idProfesional"), "ID de profesional inválido");
            String accion = validateAction(request.getParameter("accion"));
            LocalDateTime fechaHora = parseDate(request.getParameter("fechaHora"));

            // 3. Buscar turno
            DataTurno dataTurno = new DataTurno();
            Turno turno = dataTurno.buscarTurnoPorClaveCompuesta(idMascota, idProfesional, fechaHora);
            if (turno == null) {
                throw new IllegalArgumentException("Turno no encontrado");
            }

            if (!"Programado".equals(turno.getEstado())) {
                throw new IllegalStateException("Solo se pueden modificar turnos en estado 'Programado'");
            }

            // 4. Actualizar estado del turno
            turno.setEstado(accion);
            dataTurno.actualizarTurno(turno);

            // 5. Registrar observación si es necesario
            String observacion = request.getParameter("observacion");
            if ("Recepcionado".equals(accion) && observacion != null && !observacion.trim().isEmpty()) {
                DataObservacion dataObs = new DataObservacion();
                Observacion obs = new Observacion(idMascota, observacion.trim());
                dataObs.add(obs);
            }

            // 6. Redirigir según rol del usuario
            String successUrl = (usuario.getRol().getIdRol() == 1)
                    ? "/listaTurnos?success=Estado+actualizado+correctamente"
                    : "/listaTurnosProfServlet?idProfesional=" + idProfesional + "&success=Estado+actualizado+correctamente";
            response.sendRedirect(request.getContextPath() + successUrl);

        } catch (IllegalArgumentException | IllegalStateException e) {
            setErrorAndRedirect(session, response, "Error de validación", e.getMessage(), "#FF9800", "/listaTurnos");
        } catch (DateTimeParseException e) {
            setErrorAndRedirect(session, response, "Error de fecha", "Formato de fecha/hora inválido (yyyy-MM-ddTHH:mm)", "#FF9800", "/listaTurnos");
        } catch (SQLException e) {
            e.printStackTrace();
            setErrorAndRedirect(session, response, "Error de base de datos", "No se pudo completar la operación", "#F44336", "/listaTurnos");
        } catch (Exception e) {
            e.printStackTrace();
            setErrorAndRedirect(session, response, "Error inesperado", "Ocurrió un problema al procesar la solicitud", "#F44336", "/listaTurnos");
        }
    }

    private int parseInteger(String value, String errorMessage) throws IllegalArgumentException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private LocalDateTime parseDate(String fechaHoraStr) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(fechaHoraStr, formatter);
    }

    private String validateAction(String accion) throws IllegalArgumentException {
        if (!"Recepcionado".equals(accion) && !"Cancelado".equals(accion)) {
            throw new IllegalArgumentException("Acción no permitida para el turno");
        }
        return accion;
    }

    private void setErrorAndRedirect(HttpSession session, HttpServletResponse response,
                                     String errorType, String errorMessage, String errorColor, String redirectPath) 
                                     throws IOException {
        if (session != null) {
            session.setAttribute("errorType", errorType);
            session.setAttribute("errorMessage", errorMessage);
            session.setAttribute("errorColor", errorColor);
            session.setAttribute("errorRedirect", redirectPath);
            session.setAttribute("errorButtonText", "Volver");
        }
        response.sendRedirect("/public/error.jsp");
    }
}
