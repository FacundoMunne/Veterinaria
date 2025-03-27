<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajustes de Cuenta</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
	    <%@ include file="../public/header.jsp" %>

    <header class="container">
        <h1>Ajustes de Cuenta</h1>
    </header>
    <main class="container">
        <form action="${pageContext.request.contextPath}/actualizarAjustes" method="post">
            <label for="nombreUsuario">Nuevo Nombre de Usuario:</label>
            <input type="text" id="nombreUsuario" name="nombreUsuario" required><br><br>

            <label for="contraseña">Nueva Contraseña:</label>
            <input type="password" id="password" name="password" required><br><br>

            <button type="submit">Guardar Cambios</button>
        </form>

        <a href="${pageContext.request.contextPath}/menu.jsp" class="button">Volver al Menú</a>
    </main>
            <%@ include file="../public/footer.jsp" %>
    
</body>
</html>