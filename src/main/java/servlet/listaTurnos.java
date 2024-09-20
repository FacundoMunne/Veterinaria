package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clases.*;
import data.*;

@WebServlet("/listaTurnos")
public class listaTurnos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Crear instancias 
        DataTurno dataTurno = new DataTurno();
        DataCliente dataCliente = new DataCliente();
        DataMascota dataMascota = new DataMascota();
        DataProfesional dataProfesional = new DataProfesional();

        // Obtener la lista de turnos actuales
        List<Turno> listaTurnos = dataTurno.getAll();
        List<Cliente> listaClientes = new ArrayList<Cliente>();

        // Para cada turnola mascota y el profesional relacionados
        for (Turno turno : listaTurnos) {
            Mascota mascota = dataMascota.getById(turno.getMascota().getIdMascota());
            Cliente cliente = dataCliente.getById(mascota.getCliente().getIdCliente()); 
            listaClientes.add(cliente);
            Profesional profesional = dataProfesional.getById(turno.getProfesional().getIdProfesional());
            LocalDateTime fecha = turno.getFechaHora();

            // Añadir los objetos relacionados al turno 
            turno.setMascota(mascota);
            turno.setProfesional(profesional);
        }

        // Guardar la lista de turnos 
        request.setAttribute("listaTurnos", listaTurnos);
        request.setAttribute("listaClientes", listaClientes); 

        // Redirigir para mostrar los turnos
        request.getRequestDispatcher("listaTurnos.jsp").forward(request, response);
    }
}
