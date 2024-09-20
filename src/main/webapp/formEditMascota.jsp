<%@ page import="clases.Mascota, clases.Cliente" %>
<%@ page import="clases.Mascota" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Mascota</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
<%@ include file="header.jsp" %>
    <header class="container">
        <h1>Editar Mascota</h1>
    </header>
    <main class="container">
        <form action="crudmascota" method="post">
        <input type="hidden" name="action" value="edit">
        <% Mascota mascota = (Mascota) request.getAttribute("mascota"); %>
	    <input type="hidden" name="id" value="<%= mascota.getIdMascota() %>">
		
	    <label for="nombre">Nombre:</label>
	    <input type="text" id="nombre" name="nombre" value="<%= mascota.getNombre() %>" required>
	
	    <label for="especie">Especie:</label>
	    <input type="text" id="especie" name="especie" value="<%= mascota.getEspecie() %>" required>
	
	    <label for="raza">Raza:</label>
	    <input type="text" id="raza" name="raza" value="<%= mascota.getRaza() %>" required>
	
	    <label for="edad">Edad:</label>
	    <input type="number" id="edad" name="edad" value="<%= mascota.getEdad() %>" required>

            <label for="clienteId">Cliente:</label>
            <select id="clienteId" name="clienteId">
                <%
			    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
			    
			
			    if (clientes != null) {
			        for (Cliente cliente : clientes) {
			            boolean selected = (mascota != null && cliente.getIdCliente() == mascota.getCliente().getIdCliente());
			%>
			            <option value="<%= cliente.getIdCliente() %>" <%= selected ? "selected" : "" %>>
			                <%= cliente.getNombre() %>
			            </option>
			<%
			        }
			    }
			%>
            </select>

            <button type="submit">Actualizar</button>
        </form>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>
