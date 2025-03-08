package servlet;

import java.io.IOException;
import java.sql.SQLException;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/cambiarEstadoTurnoServlet")
public class CambiarEstadoTurnoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            // Si no hay sesión, redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Obtener parámetros del formulario
        int idMascota = Integer.parseInt(request.getParameter("idMascota"));
        int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
        String fechaHoraStr = request.getParameter("fechaHora");
        String accion = request.getParameter("accion");
        String observacion = request.getParameter("observacion"); // Nuevo parámetro

        // Convertir fechaHora a LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr, formatter);

        // Cambiar el estado del turno
        DataTurno dataTurno = new DataTurno();
        DataObservacion dataObs = new DataObservacion(); // Nueva instancia para manejar observaciones
        Turno turno = dataTurno.buscarTurnoPorClaveCompuesta(idMascota, idProfesional, fechaHora);

        if (turno != null && "Programado".equals(turno.getEstado())) {
            // Solo actualizar si el estado actual es "Programado"
            turno.setEstado(accion); // Actualizar el estado
            dataTurno.actualizarTurno(turno); // Guardar cambios en la base de datos

            // Si la acción es "Recepcionado", guardar la observación
            if ("Recepcionado".equals(accion) && observacion != null && !observacion.isEmpty()) {
                Observacion obs = new Observacion(idMascota, observacion);
                try {
					dataObs.add(obs);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            }
        }

        // Redirigir según el rol del usuario
        if (usuario.getRol().getIdRol() == 1) { // Administrador
            response.sendRedirect(request.getContextPath() + "/listaTurnos");
        } else if (usuario.getRol().getIdRol() == 2) { // Profesional
            response.sendRedirect(request.getContextPath() + "/listaTurnosProfServlet?idProfesional=" + idProfesional);
        } else { // Otros roles (por ejemplo, cliente)
            response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=Acceso denegado");
        }
    }
}