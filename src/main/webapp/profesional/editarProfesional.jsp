<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Información del Profesional</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="../public/header.jsp" %>
    <header class="container">
        <h1>Editar Información del Profesional</h1>
    </header>
    <main class="container">
        <c:if test="${profesional != null}">
            <form action="${pageContext.request.contextPath}/editarProfesional" method="post">
                <input type="hidden" name="idProfesional" value="${profesional.idProfesional}">

                <label for="dni">DNI:</label>
                <input type="text" id="dni" name="dni" value="${profesional.dni}" required><br><br>

                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" value="${profesional.nombre}" required><br><br>

                <label for="especialidad">Especialidad:</label>
                <input type="text" id="especialidad" name="especialidad" value="${profesional.especialidad}" required><br><br>

                <label for="telefono">Teléfono:</label>
                <input type="text" id="telefono" name="telefono" value="${profesional.telefono}" required><br><br>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${profesional.email}" required><br><br>

                <button type="submit">Guardar Cambios</button>
            </form>
        </c:if>

        <c:if test="${profesional == null}">
            <p>No se encontró información del profesional.</p>
        </c:if>
    </main>
    <%@ include file="../public/footer.jsp" %>
</body>
</html>