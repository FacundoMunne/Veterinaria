package servlet;

import clases.Mascota;
import clases.Profesional;
import clases.Turno;
import clases.Usuario;
import data.DataTurno;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/cancelarTurno")
public class CancelarTurnoServlet extends HttpServlet {
    private DataTurno dataTurno = new DataTurno();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // 1. Validar sesión
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/public/login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String redirectUrl;
        
        try {
            // 2. Validar parámetros
            String idMascotaParam = request.getParameter("idMascota");
            String idProfesionalParam = request.getParameter("idProfesional");
            String fechaHoraParam = request.getParameter("fechaHora");
            
            if (idMascotaParam == null || idProfesionalParam == null || fechaHoraParam == null ||
                idMascotaParam.isEmpty() || idProfesionalParam.isEmpty() || fechaHoraParam.isEmpty()) {
                throw new IllegalArgumentException("Todos los parámetros son requeridos");
            }

            // 3. Convertir parámetros
            int idMascota = Integer.parseInt(idMascotaParam);
            int idProfesional = Integer.parseInt(idProfesionalParam);
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraParam, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // 4. Preparar y actualizar turno (versión corregida sin constructores con parámetros)
            Mascota mascota = new Mascota();
            mascota.setIdMascota(idMascota);
            
            Profesional profesional = new Profesional();
            profesional.setIdProfesional(idProfesional);
            
            Turno turno = new Turno();
            turno.setMascota(mascota);
            turno.setProfesional(profesional);
            turno.setFechaHora(fechaHora);
            turno.setEstado("Cancelado");

            // Validar si el turno existe antes de actualizar
            Turno turnoExistente = dataTurno.buscarTurnoPorClaveCompuesta(idMascota, idProfesional, fechaHora);
            if (turnoExistente == null) {
                throw new IllegalArgumentException("El turno no existe");
            }

            dataTurno.actualizarTurno(turno);

            // 5. Preparar redirección exitosa dependiendo del rol
            if (usuario.getRol().getIdRol() == 3) {  // Cliente
                redirectUrl = request.getContextPath() + "/listaTurnosClien?success=" + URLEncoder.encode("Turno cancelado exitosamente", "UTF-8");
            } else {  // Admin o Profesional
                redirectUrl = request.getContextPath() + "/listaTurnos?success=" + URLEncoder.encode("Turno cancelado exitosamente", "UTF-8");
            }
            
        } catch (NumberFormatException e) {
            redirectUrl = handleError(request, "Error de formato", "Los IDs deben ser números válidos");
        } catch (DateTimeParseException e) {
            redirectUrl = handleError(request, "Error de fecha", "Formato de fecha/hora inválido");
        } catch (IllegalArgumentException e) {
            redirectUrl = handleError(request, "Datos inválidos", e.getMessage());
        } catch (Exception e) {
            redirectUrl = handleError(request, "Error del sistema", "No se pudo cancelar el turno");
            e.printStackTrace();
        }
        
        response.sendRedirect(redirectUrl);
    }

    private String handleError(HttpServletRequest request, String errorType, String errorMessage) {
        HttpSession session = request.getSession();
        session.setAttribute("errorType", errorType);
        session.setAttribute("errorMessage", errorMessage);

        return request.getContextPath() + "/public/error.jsp";
    }
}


