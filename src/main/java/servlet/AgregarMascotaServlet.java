package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import clases.Mascota;
import clases.Cliente;
import data.DataMascota;

@WebServlet("/agregarMascotaServlet")
public class AgregarMascotaServlet extends HttpServlet {
    private DataMascota dataMascota;

    @Override
    public void init() throws ServletException {
        dataMascota = new DataMascota();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión actual
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("idCliente") == null) {
            // Si no hay sesión o no hay un cliente autenticado, redirigir al login
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Obtener el ID del cliente de la sesión
        int idCliente = (int) session.getAttribute("idCliente");

        // Obtener los datos del formulario
        String nombre = request.getParameter("nombre");
        String especie = request.getParameter("especie");
        String raza = request.getParameter("raza");
        int edad = Integer.parseInt(request.getParameter("edad"));

        // Crear un objeto Cliente con el ID de la sesión
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);

        // Crear un objeto Mascota con los datos del formulario
        Mascota mascota = new Mascota();
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setRaza(raza);
        mascota.setEdad(edad);
        mascota.setCliente(cliente); // Asignar el objeto Cliente

        // Guardar la mascota en la base de datos
        dataMascota.add(mascota);

        // Redirigir de vuelta a la lista de mascotas
        response.sendRedirect(request.getContextPath() + "/listarMascotas");
    }
}