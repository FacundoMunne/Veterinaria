<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
    <style>
        body {
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            text-align: center;
        }

        header, footer {
            background-color: #6200ea;
            color: white;
            padding: 1rem;
        }

        main {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        nav ul {
            list-style: none;
            padding: 0;
            display: flex;
            justify-content: center;
            gap: 1rem;
        }

        nav ul li {
            margin: 0;
        }

        footer {
            text-align: center;
        }
    </style>
</head>
<body>
<header>
        <h1>Veterinaria</h1>
    </header>
    <main class="container">
        <h1>Login</h1>
        <form action="${pageContext.request.contextPath}/login" method="POST">
    <label for="nombreUsuario">Nombre de Usuario:</label>
    <input type="text" id="nombreUsuario" name="nombreUsuario" placeholder="Ingrese su nombre de usuario" required>
    
    <label for="contraseña">Contraseña:</label>
    <input type="password" id="contraseña" name="contraseña" placeholder="Ingrese su contraseña" required>
    
    <button type="submit" class="contrast">Iniciar Sesión</button>
</form>
        <% if (request.getAttribute("error") != null) { %>
            <p style="color: red;"><%= request.getAttribute("error") %></p>
        <% } %>
    </main>
    <footer>
        <p>Veterinaria XYZ - Todos los derechos reservados</p>
        <p>Contacto: 123-456-7890 | Email: contacto@veterinariaxyz.com</p>
    </footer>
</body>
</html>