package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import clases.*;
import data.*;

@WebServlet("/buscarMascotas")
public class BuscarMascotas extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dni = request.getParameter("dni");

        // Obtener cliente y mascotas 
        DataCliente dataCliente = new DataCliente();
        Cliente cliente = dataCliente.getByDni(dni);

        if (cliente != null) {
            DataMascota dataMascota = new DataMascota();
            DataProfesional dataProfesional = new DataProfesional();
            List<Mascota> mascotas = dataMascota.getByClienteId(cliente.getIdCliente());
            List<Profesional> profesionales = dataProfesional.getAll();
            

            request.setAttribute("cliente", cliente);
            request.setAttribute("mascotas", mascotas);
            request.setAttribute("profesionales", profesionales);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("seleccionarTurno.jsp");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("error", "Cliente no encontrado");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
