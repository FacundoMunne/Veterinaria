<%@ page import="clases.Cliente" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("cliente") != null && ((Cliente)request.getAttribute("cliente")).getIdCliente() > 0 ? "Editar Cliente" : "Agregar Nuevo Cliente" %></title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
	<%@ include file="header.jsp" %>
    <header class="container">
        <h1><%= request.getAttribute("cliente") != null && ((Cliente)request.getAttribute("cliente")).getIdCliente() > 0 ? "Editar Cliente" : "Agregar Nuevo Cliente" %></h1>
    </header>
	
    <main class="container">
		<form action="crudcliente" method="POST">
            <% Cliente cliente = (Cliente) request.getAttribute("cliente"); %>
            <input type="hidden" name="idCliente" value="<%= cliente != null ? cliente.getIdCliente() : "" %>">
            <label for="dni">DNI:</label>
            <input type="text" id="dni" name="dni" value="<%= cliente != null ? cliente.getDni() : "" %>" placeholder="Ingrese el DNI" required>
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="<%= cliente != null ? cliente.getNombre() : "" %>" placeholder="Ingrese el nombre" required>
            <label for="direccion">Dirección:</label>
            <input type="text" id="direccion" name="direccion" value="<%= cliente != null ? cliente.getDireccion() : "" %>" placeholder="Ingrese la dirección">
            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono" value="<%= cliente != null ? cliente.getTelefono() : "" %>" placeholder="Ingrese el teléfono">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= cliente != null ? cliente.getEmail() : "" %>" placeholder="Ingrese el email" required>
            <button type="submit" class="contrast">Guardar Cliente</button>
        </form>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>
