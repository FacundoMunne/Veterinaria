<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="clases.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seleccionar Turno</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
	<%@ include file="header.jsp" %>
    <h1>Seleccionar Turno</h1>
    <form action="crearTurno" method="post">
        <h2>Cliente: ${cliente.nombre}</h2>

        <label for="mascota">Seleccionar Mascota:</label>
        <select id="mascota" name="idMascota" required>
	    <%
	        List<Mascota> mascotas = (List<Mascota>) request.getAttribute("mascotas");
	        if (mascotas != null) {
	            for (Mascota mascota : mascotas) {
	    %>
	                <option value="<%= mascota.getIdMascota() %>">
	                    <%= mascota.getNombre() %> - <%= mascota.getEspecie() %>
	                </option>
	    <%
	            }
	        } else {
	    %>
	            <option>No hay mascotas disponibles</option>
	    <%
	        }
	    %>
	</select>
	
	
        <label for="profesional">Seleccionar Profesional:</label>

        <select id="profesional" name="idProfesional" required>
	    <%
	        List<Profesional> profesionales = (List<Profesional>) request.getAttribute("profesionales");
	        if (profesionales != null) {
	            for (Profesional profesional : profesionales) {
	    %>
	                <option value="<%= profesional.getIdProfesional() %>">
	                    <%= profesional.getNombre() %> - <%= profesional.getEspecialidad() %>
	                </option>
	    <%
	            }
	        } else {
	    %>
	            <option>No hay profesionales disponibles</option>
	    <%
	        }
	    %>
	</select>
        <br>

        <label for="fechaHora">Fecha y Hora:</label>
        <input type="datetime-local" id="fechaHora" name="fechaHora" required>
        <br>

        <input type="submit" value="Crear Turno">
    </form>
     <%@ include file="footer.jsp" %>
</body>
</html>
