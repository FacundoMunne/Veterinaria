<%@ page import="java.util.*" %>
<%@ page import="clases.Cliente" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Clientes</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
<%@ include file="header.jsp" %>
    <header class="container">
        <h1>Lista de Clientes</h1>
    </header>

    <main class="container">
        <table role="grid">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>DNI</th>
                    <th>Nombre</th>
                    <th>Dirección</th>
                    <th>Teléfono</th>
                    <th>Email</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
                    if (clientes != null && !clientes.isEmpty()) {
                        for (Cliente cliente : clientes) {
                %>
                <tr>
                    <td><%= cliente.getIdCliente() %></td>
                    <td><%= cliente.getDni() %></td>
                    <td><%= cliente.getNombre() %></td>
                    <td><%= cliente.getDireccion() %></td>
                    <td><%= cliente.getTelefono() %></td>
                    <td><%= cliente.getEmail() %></td>
                    <td>
                        <a href="crudcliente?action=edit&id=<%= cliente.getIdCliente() %>" class="secondary">Editar</a>
                        <a href="crudcliente?action=delete&id=<%= cliente.getIdCliente() %>" class="contrast">Eliminar</a>
                        <a href="crudmascota?action=list&clienteId=<%= cliente.getIdCliente() %>" class="contrast">Ver Mascotas</a> 
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7">No se encontraron clientes.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
        <a href="formCliente.jsp" class="contrast">Agregar Nuevo Cliente</a>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>
