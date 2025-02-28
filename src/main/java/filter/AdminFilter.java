package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/admin/*") // Aplica este filtro a todas las URLs bajo /admin
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro (opcional)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Verificar si el usuario es Admin
        if (session != null && "Admin".equals(session.getAttribute("rol"))) {
            // Si es Admin, permitir el acceso
            chain.doFilter(request, response);
        } else {
            // Si no es Admin, denegar el acceso
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/public/error.jsp");
        }
    }

    @Override
    public void destroy() {
        // Limpieza del filtro (opcional)
    }
}