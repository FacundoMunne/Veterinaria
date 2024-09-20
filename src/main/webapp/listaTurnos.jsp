<%@ page import="clases.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Turnos</title>
     <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
<%@ include file="header.jsp" %>
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
          
                <td><%=turno.getMascota().getNombre() %></td>
                <td><%=turno.getProfesional().getNombre() %></td>
                <td><%=turno.getFechaHora() %></td>
                <td>
                   
                    <a href="crudTurno?action=delete&idMascota=<%= turno.getMascota().getIdMascota() %>&idProfesional=<%= turno.getProfesional().getIdProfesional() %>">Borrar</a>

</td>
                </td>
            </tr>
        <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7">No se encontraron Turnos.</td>
                </tr>
                <% 
                    }
                %>
        </tbody>
    </table>
</main>
    <a href="buscarClienteTurno.jsp">Agregar nuevo Turno</a>
	<%@ include file="footer.jsp" %>
</body>
</html>
