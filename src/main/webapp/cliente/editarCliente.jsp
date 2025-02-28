<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Información Personal</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
	    <%@ include file="../public/header.jsp" %>
    <header class="container">
        <h1>Editar Información Personal</h1>
    </header>
    <main class="container">
        <c:if test="${cliente != null}">
            <form action="${pageContext.request.contextPath}/editarCliente" method="post">
                <input type="hidden" name="idCliente" value="${cliente.idCliente}">

                <label for="dni">DNI:</label>
                <input type="text" id="dni" name="dni" value="${cliente.dni}" required><br><br>

                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" value="${cliente.nombre}" required><br><br>

                <label for="direccion">Dirección:</label>
                <input type="text" id="direccion" name="direccion" value="${cliente.direccion}" required><br><br>

                <label for="telefono">Teléfono:</label>
                <input type="text" id="telefono" name="telefono" value="${cliente.telefono}" required><br><br>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${cliente.email}" required><br><br>

                <button type="submit">Guardar Cambios</button>
            </form>
        </c:if>

        <c:if test="${cliente == null}">
            <p>No se encontró información del cliente.</p>
        </c:if>
    </main>
        <%@ include file="../public/footer.jsp" %>
</body>
</html>