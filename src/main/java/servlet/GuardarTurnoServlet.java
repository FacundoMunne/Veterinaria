package servlet;

import clases.Mascota;
import clases.Profesional;
import clases.Turno;
import data.DataMascota;
import data.DataProfesional;
import data.DataTurno;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/guardarTurno")
public class GuardarTurnoServlet extends HttpServlet {
    private DataTurno dataTurno;
    private DataMascota dataMascota;
    private DataProfesional dataProfesional;

    @Override
    public void init() throws ServletException {
        super.init();
        dataTurno = new DataTurno();
        dataMascota = new DataMascota();
        dataProfesional = new DataProfesional();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String redirectUrl = request.getContextPath() + "/listarMascotas";
        
        int idMascota = 0;  // Declarar idMascota fuera del try, para que sea accesible en los catch
        try {
            // 1. Validar sesión
            if (session == null || session.getAttribute("usuario") == null) {
                response.sendRedirect(request.getContextPath() + "/public/login.jsp");
                return;
            }

            // 2. Validar y parsear parámetros
            idMascota = Integer.parseInt(request.getParameter("idMascota"));
            int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
            LocalDateTime fechaHora = LocalDateTime.parse(
                request.getParameter("fechaHora"),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            );

            // 3. Validar fecha futura
            if (fechaHora.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("No se pueden agendar turnos en fechas pasadas");
            }

            // 4. Obtener mascota y profesional
            Mascota mascota = dataMascota.getById(idMascota);
            Profesional profesional = dataProfesional.getById(idProfesional);

            if (mascota == null || profesional == null) {
                throw new IllegalArgumentException("Mascota o profesional no encontrados");
            }

            // 5. Verificar disponibilidad del profesional
            if (!dataTurno.isProfesionalAvailable(idProfesional, fechaHora)) {
                throw new IllegalStateException("El profesional no está disponible en ese horario");
            }

            // 6. Crear y guardar turno
            Turno turno = new Turno();
            turno.setMascota(mascota);
            turno.setProfesional(profesional);
            turno.setFechaHora(fechaHora);
            turno.setEstado("Programado");
            
            dataTurno.add(turno);

            // 7. Mensaje de éxito
            session.setAttribute("successMessage", "Turno agendado exitosamente para " + 
                fechaHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            
        } catch (NumberFormatException e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "ID inválido");
            request.setAttribute("errorMessage", "Los IDs deben ser números válidos");
            request.setAttribute("errorRedirect", request.getContextPath() + "/sacarTurno?idMascota=" + idMascota);
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        } catch (DateTimeParseException e) {
            request.setAttribute("errorType", "Fecha inválida");
            request.setAttribute("errorMessage", "Formato de fecha/hora inválido (use: yyyy-MM-ddTHH:mm)");
            request.setAttribute("errorRedirect", request.getContextPath() + "/sacarTurno?idMascota=" + idMascota);
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorType", "Entrada inválida");
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("errorRedirect", request.getContextPath() + "/sacarTurno?idMascota=" + idMascota);
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        } catch (IllegalStateException e) {
            request.setAttribute("errorType", "Disponibilidad del profesional");
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("errorRedirect", request.getContextPath() + "/sacarTurno?idMascota=" + idMascota);
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            request.setAttribute("errorType", "Error general");
            request.setAttribute("errorMessage", "Error al agendar el turno");
            request.setAttribute("errorRedirect", request.getContextPath() + "/sacarTurno?idMascota=" + idMascota);
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(redirectUrl);
    }


}
