<%@ page import="java.util.*" %>
<%@ page import="clases.Profesional" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Profesionales</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
<%@ include file="header.jsp" %>

    <header class="container">
        <h1>Lista de Profesionales</h1>
    </header>

    <main class="container">
        <table role="grid">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>DNI</th>
                    <th>Nombre</th>
                    <th>Especialidad</th>
                    <th>Teléfono</th>
                    <th>Email</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Profesional> profesionales = (List<Profesional>) request.getAttribute("profesionales");
                    if (profesionales != null) {
                        for (Profesional profesional : profesionales) {
                %>
                <tr>
                    <td><%= profesional.getIdProfesional() %></td>
                    <td><%= profesional.getDni() %></td>
                    <td><%= profesional.getNombre() %></td>
                    <td><%= profesional.getEspecialidad() %></td>
                    <td><%= profesional.getTelefono() %></td>
                    <td><%= profesional.getEmail() %></td>
                    <td>
                        <a href="crudprofesional?action=edit&id=<%= profesional.getIdProfesional() %>" class="secondary">Editar</a>
                        <a href="crudprofesional?action=delete&id=<%= profesional.getIdProfesional() %>" class="contrast">Eliminar</a>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7">No se encontraron profesionales.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
        <a href="formProfesional.jsp" class="contrast">Agregar Nuevo Profesional</a>
    </main>

    <%@ include file="footer.jsp" %>
</body>
</html>
