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
import java.util.*;

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

        Cliente cliente = dataCliente.getById(clienteId);

        if ("add".equals(action)) {
            Mascota mascota = new Mascota(nombre, especie, raza, edad, cliente);
            dataMascota.add(mascota);
        } else if ("edit".equals(action) && idParam!=null) {
            int id = Integer.parseInt(idParam);
            Mascota mascota = new Mascota(id, nombre, especie, raza, edad, cliente);
            dataMascota.edit(mascota);
        }

        response.sendRedirect("crudmascota?action=list&clienteId=" + clienteId);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String clienteIdParam = request.getParameter("clienteId");

        if ("list".equals(action)) {
            try {
                int clienteId = Integer.parseInt(clienteIdParam);
                List<Mascota> mascotas = dataMascota.getByClienteId(clienteId);
                System.out.println(mascotas.size());
                request.setAttribute("mascotas", mascotas);
                request.getRequestDispatcher("listadoMascotas.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int clienteId = Integer.parseInt(clienteIdParam);
                dataMascota.remove(id);
                response.sendRedirect("crudmascota?action=list&clienteId=" + clienteId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }}
        else if ("update".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Mascota mascota = dataMascota.getById(id);
                request.setAttribute("mascota", mascota);
                request.setAttribute("clientes", dataCliente.getAll());
                request.getRequestDispatcher("formEditMascota.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        }
            
        }
        
    }
