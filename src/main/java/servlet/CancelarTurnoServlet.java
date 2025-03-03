package servlet;

import clases.Mascota;
import clases.Profesional;
import clases.Turno;
import data.DataTurno;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/cancelarTurno")
public class CancelarTurnoServlet extends HttpServlet {
    private DataTurno dataTurno = new DataTurno();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros de la solicitud
        String idMascotaParam = request.getParameter("idMascota");
        String idProfesionalParam = request.getParameter("idProfesional");
        String fechaHoraParam = request.getParameter("fechaHora");

        // Verificar que los parámetros no sean nulos o vacíos
        if (idMascotaParam == null || idProfesionalParam == null || fechaHoraParam == null ||
            idMascotaParam.isEmpty() || idProfesionalParam.isEmpty() || fechaHoraParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/public/error.jsp?mensaje=Parámetros inválidos");
            return;
        }

        // Convertir los parámetros a los tipos correctos
        int idMascota = Integer.parseInt(idMascotaParam);
        int idProfesional = Integer.parseInt(idProfesionalParam);
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraParam, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Crear objetos Mascota y Profesional
        Mascota mascota = new Mascota();
        mascota.setIdMascota(idMascota);

        Profesional profesional = new Profesional();
        profesional.setIdProfesional(idProfesional);

        // Crear un objeto Turno con los datos necesarios
        Turno turno = new Turno();
        turno.setMascota(mascota);
        turno.setProfesional(profesional);
        turno.setFechaHora(fechaHora);
        turno.setEstado("Cancelado"); // Cambiar el estado a "Cancelado"

        // Actualizar el turno en la base de datos
        dataTurno.actualizarTurno(turno);

        // Redirigir de vuelta a la lista de turnos
        response.sendRedirect(request.getContextPath() + "/listarTurnos");
    }
}