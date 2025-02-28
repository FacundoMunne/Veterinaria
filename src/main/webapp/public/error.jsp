<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Veterinaria</title>
    <!-- Pico CSS -->
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
<%@ include file="header.jsp" %>
    <main class="container">
        <header>
            <h1 style="color: red;">Ocurrió un Error</h1>
        </header>
        <section>
            <p><strong>Detalles del error:</strong></p>
            <blockquote>
                ${errorMessage}
            </blockquote>
            <a href="prueba.html" role="button" class="secondary">Volver al formulario de turnos</a>
        </section>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>
