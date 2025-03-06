<%@ page import="java.util.List" %>
<%@ page import="clases.Turno" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Turnos del Profesional</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="../public/header.jsp" %>

    <header class="container">
        <h1>Turnos del Profesional</h1>
    </header>

    <main class="container">
        <table role="grid">
            <thead>
                <tr>
                    <th>ID Mascota</th>
                    <th>Nombre Mascota</th>
                    <th>Nombre Profesional</th>
                    <th>Fecha y Hora</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Turno> turnos = (List<Turno>) request.getAttribute("turnos");
                    if (turnos != null && !turnos.isEmpty()) {
                        for (Turno turno : turnos) {
                %>
                <tr>
                    <td><%= turno.getMascota().getIdMascota() %></td>
                    <td><%= turno.getMascota().getNombre() %></td>
                    <td><%= turno.getProfesional().getNombre() %></td>
                    <td><%= turno.getFechaHora() %></td>
                    <td><%= turno.getEstado() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5">No se encontraron turnos para este profesional.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/crudprofesional" class="contrast">Volver a la lista de profesionales</a>
    </main>

    <%@ include file="../public/footer.jsp" %>
</body>
</html>