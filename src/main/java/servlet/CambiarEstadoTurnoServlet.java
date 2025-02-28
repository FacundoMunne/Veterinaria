package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clases.Turno;
import data.DataTurno;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/cambiarEstadoTurnoServlet")
public class CambiarEstadoTurnoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        int idMascota = Integer.parseInt(request.getParameter("idMascota"));
        int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
        String fechaHoraStr = request.getParameter("fechaHora");
        String accion = request.getParameter("accion");
        
        System.out.println("idMascota: " + idMascota);
        System.out.println("idProfesional: " + idProfesional);
        System.out.println("fechaHora: " + fechaHoraStr);
        System.out.println("Acción: " + accion);


        // Convertir fechaHora a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, formatter);

        // Cambiar el estado del turno
        DataTurno dataTurno = new DataTurno();
        Turno turno = dataTurno.buscarTurnoPorClaveCompuesta(idMascota, idProfesional, fechaHora);
        System.out.println("Turno encontrado: " + (turno != null));

        if (turno != null && "Programado".equals(turno.getEstado())) {
            // Solo actualizar si el estado actual es "Programado"
            turno.setEstado(accion); // Actualizar el estado
            dataTurno.actualizarTurno(turno); // Guardar cambios en la base de datos
        }

        // Redirigir de vuelta a la lista de turnos
        response.sendRedirect("/public/listaTurnosProfServlet?idProfesional=" + idProfesional);
    }
}