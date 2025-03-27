<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Usuario Administrador</title>
</head>
<body>
    <h1>Crear Usuario Administrador</h1>
    <form action="${pageContext.request.contextPath}/crearAdmin" method="post">
        <label for="nombreUsuario">Nombre de Usuario:</label>
        <input type="text" id="nombreUsuario" name="nombreUsuario" required><br><br>

        <label for="contraseña">Contraseña:</label>
        <input type="password" id="password" name="password" required><br><br>

        <input type="submit" value="Crear Administrador">
    </form>
</body>
</html>