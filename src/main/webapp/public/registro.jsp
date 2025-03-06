<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>
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
            flex-direction: column;
            justify-content: flex-start;
            align-items: center;
            padding-top: 20px;
        }

        form {
            width: 100%;
            max-width: 400px;
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
        <h2>Registro</h2>
        <form action="${pageContext.request.contextPath}/registro" method="POST">
            <!-- Datos del Cliente -->
            <label for="dni">DNI:</label>
            <input type="text" id="dni" name="dni" placeholder="Ingrese su DNI" required>

            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" placeholder="Ingrese su nombre" required>

            <label for="direccion">Dirección:</label>
            <input type="text" id="direccion" name="direccion" placeholder="Ingrese su dirección" required>

            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono" placeholder="Ingrese su teléfono" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" placeholder="Ingrese su email" required>

            <!-- Datos del Usuario -->
            <label for="nombreUsuario">Nombre de Usuario:</label>
            <input type="text" id="nombreUsuario" name="nombreUsuario" placeholder="Ingrese su nombre de usuario" required>

            <label for="contraseña">Contraseña:</label>
            <input type="password" id="contraseña" name="contraseña" placeholder="Ingrese su contraseña" required>

            <button type="submit">Registrarse</button>
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