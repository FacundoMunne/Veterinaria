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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	
            // Obtener los parámetros del formulario
            int idMascota = Integer.parseInt(request.getParameter("idMascota"));
            System.out.print(idMascota);
            int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime fechaHora = LocalDateTime.parse(request.getParameter("fechaHora"), formatter);

            // Buscar la Mascota y el Profesional 
            Mascota mascota = dataMascota.getById(idMascota);
            Profesional profesional = dataProfesional.getById(idProfesional);

            // Verificar que no sean nulos
            if (mascota == null || profesional == null) {
                request.setAttribute("errorMessage", "No se pudo encontrar la mascota o el profesional.");
                request.getRequestDispatcher("prueba.html").forward(request, response);
                return ;
            }

            // Utilizar el constructor de Turno 
            Turno turno = new Turno(mascota, profesional, fechaHora);

            // Guardar el turno en la base de datos
            dataTurno.add(turno);

            // Redirigir a la página de confirmación
            response.sendRedirect("turnosConfirmados.jsp");

        } catch (NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Datos inválidos. Por favor, verifica los valores ingresados.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } 
    }

}





