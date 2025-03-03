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
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/guardarTurno")
public class GuardarTurnoServlet extends HttpServlet {
    private DataTurno dataTurno = new DataTurno();
    private DataMascota dataMascota = new DataMascota();
    private DataProfesional dataProfesional = new DataProfesional();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los datos del formulario
        int idMascota = Integer.parseInt(request.getParameter("idMascota"));
        int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
        LocalDateTime fechaHora = LocalDateTime.parse(request.getParameter("fechaHora"));

        // Obtener los objetos Mascota y Profesional
        Mascota mascota = dataMascota.getById(idMascota);
        Profesional profesional = dataProfesional.getById(idProfesional);

        if (mascota == null || profesional == null) {
            response.sendRedirect(request.getContextPath() + "/public/error.jsp?mensaje=Mascota o Profesional no encontrado");
            return;
        }

        // Crear un objeto Turno
        Turno turno = new Turno(mascota, profesional, fechaHora, "Programado");

        // Guardar el turno en la base de datos
        dataTurno.add(turno);

        // Redirigir a la lista de mascotas
        response.sendRedirect(request.getContextPath() + "/listarMascotas");
    }
}