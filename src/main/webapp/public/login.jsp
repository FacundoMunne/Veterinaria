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
            flex-direction: column; /* Cambia la dirección a columna */
            justify-content: flex-start; /* Alinea el contenido en la parte superior */
            align-items: center; /* Centra horizontalmente */
            padding-top: 20px; /* Espacio arriba del contenido */
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

        h2 {
            font-size: 2rem;
            color: white;
            margin: 0;
            margin-bottom: 20px; /* Espacio debajo del título */
        }

        form {
            width: 100%;
            max-width: 400px; /* Ancho máximo del formulario */
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 0.5rem;
            margin-bottom: 1rem;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            width: 100%;
            padding: 0.75rem;
            background-color: #6a1b9a;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #4a148c;
        }

        p.error {
            color: red;
            margin-top: 1rem;
        }
    </style>
</head>
<body>
    <header>
        <h1>Veterinaria</h1>
    </header>
    <main class="container">
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/login" method="POST">
            <label for="nombreUsuario">Nombre de Usuario:</label>
            <input type="text" id="nombreUsuario" name="nombreUsuario" placeholder="Ingrese su nombre de usuario" required>
            
            <label for="contraseña">Contraseña:</label>
            <input type="password" id="contraseña" name="contraseña" placeholder="Ingrese su contraseña" required>
            
            <button type="submit" class="contrast">Iniciar Sesión</button>
        </form>
        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
    </main>
    <footer>
        <p>Veterinaria XYZ - Todos los derechos reservados</p>
        <p>Contacto: 123-456-7890 | Email: contacto@veterinariaxyz.com</p>
    </footer>
</body>
</html>