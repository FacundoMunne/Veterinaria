<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Agregar Mascota</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="../public/header.jsp" %>
    <header class="container">
        <h1>Agregar Mascota</h1>
    </header>
    <main class="container">
        <form action="${pageContext.request.contextPath}/agregarMascotaServlet" method="POST">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" placeholder="Ingrese el nombre de la mascota" required>

            <label for="especie">Especie:</label>
            <input type="text" id="especie" name="especie" placeholder="Ingrese la especie (por ejemplo, Perro, Gato)" required>

            <label for="raza">Raza:</label>
            <input type="text" id="raza" name="raza" placeholder="Ingrese la raza de la mascota" required>

            <label for="edad">Edad:</label>
            <input type="number" id="edad" name="edad" placeholder="Ingrese la edad de la mascota" required>

            

            <button type="submit">Agregar Mascota</button>
        </form>
    </main>
    <%@ include file="../public/footer.jsp" %>
</body>
</html>