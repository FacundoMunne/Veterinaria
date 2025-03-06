<%@ page import="clases.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Turnos</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="../public/header.jsp" %>
    <header class="container">
        <h1>Turnos Actuales</h1>
    </header>
    <main class="container">
        <table border="1">
            <thead>
                <tr>
                    <th>Mascota</th>
                    <th>Profesional</th>
                    <th>Fecha y Hora</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Turno> turnos = (List<Turno>) request.getAttribute("listaTurnos");
                    if (turnos != null) {
                        for (Turno turno : turnos) {
                %>
                <tr>
                    <td><%= turno.getMascota().getNombre() %></td>
                    <td><%= turno.getProfesional().getNombre() %></td>
                    <td><%= turno.getFechaHora() %></td>
                    <td><%= turno.getEstado() %></td>
                     <td>
                               <form method="post" action="${pageContext.request.contextPath}/cambiarEstadoTurnoServlet" style="display: inline;">
                                   <input type="hidden" name="idMascota" value="<%= turno.getMascota().getIdMascota() %>">
                                   <input type="hidden" name="idProfesional" value="<%= turno.getProfesional().getIdProfesional() %>">
                                   <input type="hidden" name="fechaHora" value="<%= turno.getFechaHora() %>">
                                   <button type="submit" name="accion" value="Recepcionado" <%= !"Programado".equals(turno.getEstado()) ? "disabled" : "" %>>Recepcionar</button>
                                   <button type="submit" name="accion" value="Cancelado" <%= !"Programado".equals(turno.getEstado()) ? "disabled" : "" %>>Cancelar</button>
                               </form>
                           </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5">No se encontraron Turnos.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </main>
    <a href="buscarClienteTurno.jsp">Agregar nuevo Turno</a>
    <%@ include file="../public/footer.jsp" %>
</body>
</html>