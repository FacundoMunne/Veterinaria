<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro Exitoso</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
<header>
    <h1>Veterinaria</h1>
    </header>
    <main class="container">
        <h2>¡Registro Exitoso!</h2>
        <p>Tu cuenta ha sido creada correctamente. <a href="${pageContext.request.contextPath}/public/login.jsp">Inicia sesión</a> para continuar.</p>
    </main>
    <%@ include file="footer.jsp" %>

</body>
</html>