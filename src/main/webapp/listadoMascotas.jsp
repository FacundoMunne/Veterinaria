<%@ page import="java.util.*" %>
<%@ page import="clases.Mascota" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Mascotas</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
<%@ include file="header.jsp" %>
    <header class="container">
        <h1>Lista de Mascotas</h1>
    </header>

    <main class="container">
        <table role="grid">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Especie</th>
                    <th>Raza</th>
                    <th>Edad</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Mascota> mascotas = (List<Mascota>) request.getAttribute("mascotas");
                    if (mascotas != null && !mascotas.isEmpty()) {
                        for (Mascota mascota : mascotas) {
                %>
                <tr>
                    <td><%= mascota.getIdMascota() %></td>
                    <td><%= mascota.getNombre() %></td>
                    <td><%= mascota.getEspecie() %></td>
                    <td><%= mascota.getRaza() %></td>
                    <td><%= mascota.getEdad() %></td>
                    <td>
                        <a href="crudmascota?action=update&id=<%= mascota.getIdMascota() %>&clienteId=<%= request.getParameter("clienteId") %>" class="secondary">Editar</a>
                        <a href="crudmascota?action=delete&id=<%= mascota.getIdMascota() %>&clienteId=<%= request.getParameter("clienteId") %>" class="contrast">Eliminar</a>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6">No se encontraron mascotas.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
        <a href="formMascota.jsp?clienteId=<%= request.getParameter("clienteId") %>" class="contrast">Agregar Nueva Mascota</a>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>
