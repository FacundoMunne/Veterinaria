package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import clases.Turno;
import data.DataTurno;

@WebServlet("/listaTurnosxProf")
public class ListarTurnosxProf extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DataTurno dataTurno;

    @Override
    public void init() throws ServletException {
        dataTurno = new DataTurno();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        try {
            // 1. Validar sesión y permisos
            if (session == null || session.getAttribute("usuario") == null) {
                request.getSession().setAttribute("errorType", "Acceso denegado");
                request.getSession().setAttribute("errorMessage", "Debe iniciar sesión para acceder a esta función");
                response.sendRedirect(request.getContextPath() + "/public/error.jsp");
                return;
            }

            // 2. Validar y obtener parámetros
            int idProfesional = Integer.parseInt(request.getParameter("idProfesional"));
            String estado = request.getParameter("estado");
            
            // Manejar parámetro opcional con valor por defecto
            String ordenParam = request.getParameter("ordenAscendente");
            boolean ordenAscendente = (ordenParam == null) ? true : Boolean.parseBoolean(ordenParam);

            // 3. Obtener turnos con filtros
            List<Turno> turnos = dataTurno.getTurnosByProfesional(idProfesional, estado, ordenAscendente);
            
            // 4. Pasar datos a la vista
            request.setAttribute("turnos", turnos);
            request.setAttribute("idProfesional", idProfesional);
            request.setAttribute("estadoFiltro", estado);
            request.setAttribute("ordenAscendente", ordenAscendente);
            
            request.getRequestDispatcher("/admin/listaTurnosxProf.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorType", "Error de formato");
            request.getSession().setAttribute("errorMessage", "ID de profesional inválido");
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        } catch (Exception e) {
            request.getSession().setAttribute("errorType", "Error del sistema");
            request.getSession().setAttribute("errorMessage", "Error al obtener la lista de turnos");
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/public/error.jsp");
        }
    }
}