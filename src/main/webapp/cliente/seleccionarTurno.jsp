<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sacar Turno</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
	    <%@ include file="../public/header.jsp" %>

    <header class="container">
        <h1>Sacar Turno</h1>
    </header>
    <main class="container">
        <form action="${pageContext.request.contextPath}/guardarTurno" method="post">
            <input type="hidden" name="idMascota" value="${idMascota}">

            <label for="profesional">Seleccionar Profesional:</label>
            <select id="profesional" name="idProfesional" required>
                <c:forEach var="profesional" items="${profesionales}">
                    <option value="${profesional.idProfesional}">${profesional.nombre} - ${profesional.especialidad}</option>
                </c:forEach>
            </select><br><br>

            <label for="fechaHora">Fecha y Hora:</label>
            <input type="datetime-local" id="fechaHora" name="fechaHora" required><br><br>

            <button type="submit">Confirmar Turno</button>
        </form>

        <a href="${pageContext.request.contextPath}/listarMascotas" class="button">Volver</a>
    </main>
            <%@ include file="../public/footer.jsp" %>
    
</body>
</html>