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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/cancelarTurno")
public class CancelarTurnoServlet extends HttpServlet {
    private DataTurno dataTurno = new DataTurno();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            // Si no hay sesión, redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Obtener el usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuario");

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

        // Validar permisos del usuario
        if (usuario.getRol().getIdRol() == 1) { // Si es administrador (idRol = 1)
            // El administrador puede cancelar cualquier turno
        } else if (usuario.getRol().getIdRol() == 2) { // Si es profesional (idRol = 2)
            // Verificar que el profesional solo pueda cancelar sus propios turnos
            int idProfesionalSesion = (int) session.getAttribute("idProfesional");
            if (idProfesionalSesion != idProfesional) {
                response.sendRedirect(request.getContextPath() + "/public/error.jsp?mensaje=No tienes permisos para cancelar este turno");
                return;
            }
        } else {
            // Si el rol no es administrador ni profesional, redirigir a error
            response.sendRedirect(request.getContextPath() + "/public/error.jsp?mensaje=Acceso denegado");
            return;
        }

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

        // Redirigir según el rol del usuario
        if (usuario.getRol().getIdRol() == 1) { // Administrador
            response.sendRedirect(request.getContextPath() + "/listaTurnos");
        } else if (usuario.getRol().getIdRol() == 2) { // Profesional
            response.sendRedirect(request.getContextPath() + "/listaTurnosProfServlet");
        } else { // Otros roles (por ejemplo, cliente)
            response.sendRedirect(request.getContextPath() + "/error.jsp?mensaje=Acceso denegado");
        }
    }
}