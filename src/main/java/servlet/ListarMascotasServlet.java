package servlet;

import clases.Mascota;
import data.DataMascota;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/listarMascotas")
public class ListarMascotasServlet extends HttpServlet {
    private DataMascota dataMascota;

    @Override
    public void init() throws ServletException {
        dataMascota = new DataMascota();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String redirectUrl = request.getContextPath() + "/public/login.jsp";
        
        try {
            // 1. Validar sesión y rol
            if (session == null || session.getAttribute("idCliente") == null) {
                request.getSession().setAttribute("errorMessage", "Debe iniciar sesión como cliente para acceder a esta función");
                response.sendRedirect(redirectUrl);
                return;
            }

            // 2. Obtener ID del cliente
            int idCliente = (int) session.getAttribute("idCliente");

            // 3. Obtener mascotas del cliente
            List<Mascota> mascotas = dataMascota.getByClienteId(idCliente);
            
            // 4. Manejar caso sin mascotas
            if (mascotas.isEmpty()) {
                request.setAttribute("infoMessage", "No tiene mascotas registradas aún");
            }

            // 5. Pasar datos a la vista
            request.setAttribute("mascotas", mascotas);
            request.getRequestDispatcher("/cliente/misMascotas.jsp").forward(request, response);
            return;
            
        } catch (ClassCastException e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Error de sesión");
            request.setAttribute("errorMessage", "Error en los datos de sesión");
            request.setAttribute("errorRedirect", request.getContextPath() + "/public/login.jsp");
            request.setAttribute("errorButtonText", "Iniciar sesión");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            // Redirigir a error.jsp con detalles del error
            request.setAttribute("errorType", "Error general");
            request.setAttribute("errorMessage", "Error al obtener la lista de mascotas");
            request.setAttribute("errorRedirect", request.getContextPath() + "/cliente/misMascotas.jsp");
            request.setAttribute("errorButtonText", "Volver");
            request.setAttribute("errorDebug", e.getMessage());
            request.getRequestDispatcher("/public/error.jsp").forward(request, response);
            return;
        }
    }
}
