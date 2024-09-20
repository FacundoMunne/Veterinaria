<%@ page import="clases.Mascota" %>
<%@ page import="clases.Cliente" %>
<%@ page import="data.DataCliente" %>
<%@ page import="data.DataMascota" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario de Mascota</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
<%@ include file="header.jsp" %>
    <header class="container">
        <h1>Formulario de Mascota</h1>
        <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
    </header>

    <main class="container">
        <%
            String action = "add";
            Mascota mascota = null;
            int clienteId = Integer.parseInt(request.getParameter("clienteId"));
            DataCliente dataCliente = new DataCliente();
            List<Cliente> clientes = dataCliente.getAll();

            if (request.getParameter("idMascota") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                DataMascota dataMascota = new DataMascota();
                mascota = dataMascota.getById(id);
                action = "update";
            }
        %>
        <form action="crudmascota" method="post">
            <input type="hidden" name="action" value="<%= action %>" />
            <input type="hidden" name="id" value="<%= mascota != null ? mascota.getIdMascota() : "" %>" />
            <input type="hidden" name="clienteId" value="<%= clienteId %>" />
            
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="<%= mascota != null ? mascota.getNombre() : "" %>" required />
            
            <label for="especie">Especie:</label>
            <input type="text" id="especie" name="especie" value="<%= mascota != null ? mascota.getEspecie() : "" %>" required />
            
            <label for="raza">Raza:</label>
            <input type="text" id="raza" name="raza" value="<%= mascota != null ? mascota.getRaza() : "" %>" required />
            
            <label for="edad">Edad:</label>
            <input type="number" id="edad" name="edad" value="<%= mascota != null ? mascota.getEdad() : "" %>" required />
            
            <label for="cliente">Cliente:</label>
            <select id="cliente" name="clienteId" required>
                <%
                    for (Cliente cliente : clientes) {
                %>
                <option value="<%= cliente.getIdCliente() %>" <%= cliente.getIdCliente() == clienteId ? "selected" : "" %>><%= cliente.getNombre() %></option>
                <% 
                    }
                %>
            </select>
            
            <button type="submit" class="contrast">Guardar</button>
        </form>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>
