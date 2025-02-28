package servlet;

import clases.Cliente;
import clases.Mascota;
import data.DataCliente;
import data.DataMascota;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/crudmascota")
public class CRUDMascota extends HttpServlet {
    private DataCliente dataCliente = new DataCliente();
    private DataMascota dataMascota = new DataMascota();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String especie = request.getParameter("especie");
        String raza = request.getParameter("raza");
        int edad = Integer.parseInt(request.getParameter("edad"));
        int clienteId = Integer.parseInt(request.getParameter("clienteId"));

        System.out.println("Acción recibida: " + action); // Depuración
        System.out.println("ID de mascota recibido: " + idParam); // Depuración
        System.out.println("Nombre de mascota: " + nombre); // Depuración
        System.out.println("Especie de mascota: " + especie); // Depuración
        System.out.println("Raza de mascota: " + raza); // Depuración
        System.out.println("Edad de mascota: " + edad); // Depuración
        System.out.println("ID de cliente recibido: " + clienteId); // Depuración

        // Obtener el cliente asociado a la mascota
        Cliente cliente = dataCliente.getById(clienteId);
        if (cliente == null) {
            System.out.println("Error: No se encontró el cliente con ID " + clienteId); // Depuración
            response.sendRedirect(request.getContextPath() + "/public/error.jsp?mensaje=Cliente no encontrado");
            return;
        }
        System.out.println("Cliente encontrado: " + cliente.getNombre()); // Depuración

        if ("add".equals(action)) {
            // Crear una nueva mascota
            Mascota mascota = new Mascota(nombre, especie, raza, edad, cliente);
            System.out.println("Creando nueva mascota: " + mascota.getNombre()); // Depuración

            // Insertar la mascota en la base de datos
            dataMascota.add(mascota);
            System.out.println("Mascota insertada correctamente."); // Depuración

        } else if ("edit".equals(action) && idParam != null) {
            // Editar una mascota existente
            int id = Integer.parseInt(idParam);
            System.out.println("Editando mascota con ID: " + id); // Depuración

            Mascota mascota = new Mascota(id, nombre, especie, raza, edad, cliente);
            System.out.println("Datos de la mascota a editar: " + mascota.getNombre()); // Depuración

            // Actualizar la mascota en la base de datos
            dataMascota.edit(mascota);
            System.out.println("Mascota actualizada correctamente."); // Depuración
        } else {
            System.out.println("Acción no reconocida o ID no proporcionado."); // Depuración
            response.sendRedirect(request.getContextPath() + "/public/error.jsp?mensaje=Acción no válida");
            return;
        }

        // Redirigir a la lista de mascotas del cliente
        response.sendRedirect(request.getContextPath() + "/crudmascota?action=list&clienteId=" + clienteId);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String clienteIdParam = request.getParameter("clienteId");

        System.out.println("Acción recibida: " + action); // Depuración
        System.out.println("clienteId recibido: " + clienteIdParam); // Depuración

        if ("list".equals(action)) {
            try {
                int clienteId = Integer.parseInt(clienteIdParam);
                System.out.println("Buscando mascotas para el cliente ID: " + clienteId); // Depuración

                List<Mascota> mascotas = dataMascota.getByClienteId(clienteId);
                System.out.println("Número de mascotas encontradas: " + mascotas.size()); // Depuración

                request.setAttribute("mascotas", mascotas);
                request.getRequestDispatcher("/admin/listadoMascotas.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir clienteId a entero: " + e.getMessage()); // Depuración
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/public/error.jsp");
            }
        } else if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int clienteId = Integer.parseInt(clienteIdParam);
                System.out.println("Eliminando mascota ID: " + id + " del cliente ID: " + clienteId); // Depuración

                dataMascota.remove(id);
                response.sendRedirect(request.getContextPath() + "/crudmascota?action=list&clienteId=" + clienteId);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir ID a entero: " + e.getMessage()); // Depuración
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/public/error.jsp");
            }
        } else if ("update".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                System.out.println("Editando mascota ID: " + id); // Depuración

                Mascota mascota = dataMascota.getById(id);
                if (mascota != null) {
                    System.out.println("Mascota encontrada: " + mascota.getNombre()); // Depuración
                } else {
                    System.out.println("Mascota no encontrada para el ID: " + id); // Depuración
                }

                request.setAttribute("mascota", mascota);
                request.setAttribute("clientes", dataCliente.getAll());
                request.getRequestDispatcher("/admin/formEditMascota.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir ID a entero: " + e.getMessage()); // Depuración
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/public/error.jsp");
            }
        } else {
            System.out.println("Acción no reconocida: " + action); // Depuración
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        }
    }
}