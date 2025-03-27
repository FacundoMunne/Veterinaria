package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import clases.*;
import data.*;

@WebServlet("/crearTurno")
public class crearTurno extends HttpServlet {

    private DataTurno dataTurno;
    private DataMascota dataMascota;
    private DataProfesional dataProfesional;

    @Override
    public void init() throws ServletException {
        dataTurno = new DataTurno();
        dataMascota = new DataMascota();
        dataProfesional = new DataProfesional();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String redirectUrl;
        
        try {
            // 1. Validar sesión y permisos
            if (session == null || session.getAttribute("usuario") == null) {
                throw new SecurityException("Debe iniciar sesión para realizar esta acción");
            }
            
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            if (usuario.getRol() == null || usuario.getRol().getIdRol() != 1) {
                throw new SecurityException("No tiene permisos para crear turnos");
            }

            // 2. Validar y parsear parámetros
            int idMascota = Integer.parseInt(request.getParameter("idMascota"));
            int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
            LocalDateTime fechaHora = LocalDateTime.parse(
                request.getParameter("fechaHora"), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
            );

            // Validar fecha no pasada
            if (fechaHora.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("No se pueden crear turnos en fechas pasadas");
            }

            // 3. Obtener mascota y profesional
            Mascota mascota = dataMascota.getById(idMascota);
            Profesional profesional = dataProfesional.getById(idProfesional);

            if (mascota == null || profesional == null) {
                throw new IllegalArgumentException("Mascota o profesional no encontrados");
            }

            // 4. Verificar disponibilidad
            if (!dataTurno.isProfesionalAvailable(idProfesional, fechaHora)) {
                throw new IllegalStateException("El profesional no está disponible en ese horario");
            }

            // 5. Crear y guardar turno
            Turno turno = new Turno();
            turno.setMascota(mascota);
            turno.setProfesional(profesional);
            turno.setFechaHora(fechaHora);
            turno.setEstado("Programado");
            
            // Este es el único método que podría lanzar SQLException
            dataTurno.add(turno);

            // 6. Redirección exitosa
            redirectUrl = request.getContextPath() + "/admin/turnosConfirmados.jsp?success=Turno+creado+exitosamente";
            
        } catch (SecurityException e) {
            redirectUrl = handleError(request, "Acceso denegado", e.getMessage());
        } catch (NumberFormatException e) {
            redirectUrl = handleError(request, "Error de formato", "Los IDs deben ser números válidos");
        } catch (DateTimeParseException e) {
            redirectUrl = handleError(request, "Error de fecha", "Formato de fecha/hora inválido (use: yyyy-MM-ddTHH:mm)");
        } catch (IllegalArgumentException e) {
            redirectUrl = handleError(request, "Datos inválidos", e.getMessage());
        } catch (IllegalStateException e) {
            redirectUrl = handleError(request, "No disponible", e.getMessage());
        } catch (Exception e) {
            // Este bloque capturará cualquier otra excepción, incluyendo SQLException si dataTurno.add() la lanza
            redirectUrl = handleError(request, "Error inesperado", "Ocurrió un problema al crear el turno");
            e.printStackTrace();
        }
        
        response.sendRedirect(redirectUrl);
    }

    private String handleError(HttpServletRequest request, String errorType, String errorMessage) {
        // Se establecen los atributos de error en la sesión para mostrar en error.jsp
        HttpSession session = request.getSession();
        session.setAttribute("errorType", errorType);
        session.setAttribute("errorMessage", errorMessage);
        
        // Redirigir a la página de error
        return request.getContextPath() + "/public/error.jsp";
    }
}
