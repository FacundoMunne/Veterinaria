<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="clases.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestionar Turno</title>
        <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
	<%@ include file="header.jsp" %>
    <header class="container">
        <h1>${turno == null ? "Agregar Turno" : "Editar Turno"}</h1>
    </header>

	<main class="container">
    <form action="crudturno" method="post">
        <label for="cliente">Cliente:</label>
    <select id="cliente" name="cliente" onchange="this.form.submit()">
        <option value="">Seleccione un cliente</option>
        <% List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes"); %>
        <% if (clientes != null) { %>
            <% for (Cliente cliente : clientes) { %>
                <option value="<%= cliente.getIdCliente() %>" 
                    <%= cliente.getIdCliente() == Integer.parseInt(request.getAttribute("clienteSeleccionado") != null ? request.getAttribute("clienteSeleccionado").toString() : "0") ? "selected" : "" %>>
                    <%= cliente.getNombre() %>
                </option>
            <% } %>
        <% } else { %>
            <option value="">No hay clientes disponibles</option>
        <% } %>
    </select>
    
    <label for="mascota">Mascota:</label>
    <select id="mascota" name="mascota">
        <option value="">Seleccione una mascota</option>
        <% List<Mascota> mascotas = (List<Mascota>) request.getAttribute("mascotas"); %>
        <% if (mascotas != null) { %>
            <% for (Mascota mascota : mascotas) { %>
                <option value="<%= mascota.getIdMascota() %>">
                    <%= mascota.getNombre() %>
                </option>
            <% } %>
        <% } else { %>
            <option value="">No hay mascotas disponibles</option>
        <% } %>
    </select>
        <label for="profesional">Profesional:</label>
	       <select id="profesional" name="profesional">
	    <option value="">Seleccione un profesional</option>
	    <% 
	        List<Profesional> profesionales = (List<Profesional>) request.getAttribute("profesionales");
	        if (profesionales != null) {
	            for (Profesional profesional : profesionales) {
	    %>
	                <option value="<%= profesional.getIdProfesional() %>">
	                    <%= profesional.getNombre() %>
	                </option>
	    <% 
	            }
	        } else {
	    %>
	        <option value="">No hay profesionales disponibles</option>
	    <% } %>
			</select>

        <label for="fechaHora">Fecha y Hora:</label>
        <input type="datetime-local" id="fechaHora" name="fechaHora" required />

        <input type="hidden" name="action" value="add" />
        <input type="submit" value="Guardar Turno" />
    </form>
    </main>
    <!-- Botón para volver a la lista de turnos -->
    <br>
    <a href="crudturno">Volver a la lista de turnos</a>
     <%@ include file="footer.jsp" %>
</body>
</html>
