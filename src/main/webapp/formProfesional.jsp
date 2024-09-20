<%@ page import="clases.Profesional" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("profesional") != null && ((Profesional)request.getAttribute("profesional")).getIdProfesional() > 0 ? "Editar Profesional" : "Agregar Nuevo Profesional" %></title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <header class="container">
        <h1><%= request.getAttribute("profesional") != null && ((Profesional)request.getAttribute("profesional")).getIdProfesional() > 0 ? "Editar Profesional" : "Agregar Nuevo Profesional" %></h1>
    </header>
    
    <main class="container">
        <form action="crudprofesional" method="POST">
            <% Profesional profesional = (Profesional) request.getAttribute("profesional"); %>
            <input type="hidden" name="idProfesional" value="<%= profesional != null ? profesional.getIdProfesional() : "" %>">
            
            <label for="dni">DNI:</label>
            <input type="text" id="dni" name="dni" value="<%= profesional != null ? profesional.getDni() : "" %>" placeholder="Ingrese el DNI" required>
            
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="<%= profesional != null ? profesional.getNombre() : "" %>" placeholder="Ingrese el nombre" required>
            
            <label for="especialidad">Especialidad:</label>
            <input type="text" id="especialidad" name="especialidad" value="<%= profesional != null ? profesional.getEspecialidad() : "" %>" placeholder="Ingrese la especialidad">
            
            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono" value="<%= profesional != null ? profesional.getTelefono() : "" %>" placeholder="Ingrese el teléfono">
            
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= profesional != null ? profesional.getEmail() : "" %>" placeholder="Ingrese el email" required>
            
            <button type="submit" class="contrast">Guardar Profesional</button>
        </form>
    </main>

    <%@ include file="footer.jsp" %>
</body>
</html>
