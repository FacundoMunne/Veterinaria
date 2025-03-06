<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Mascota</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="../public/header.jsp" %>

    <header class="container">
        <h1>Editar Mascota</h1>
    </header>

    <main class="container">
        <form action="${pageContext.request.contextPath}/crudmascota" method="post">
            <!-- Campo oculto para enviar la acción y el ID de la mascota -->
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="id" value="${mascota.idMascota}">
            <input type="hidden" name="clienteId" value="${sessionScope.idCliente}">

            <!-- Campos del formulario -->
            <div class="grid">
                <div>
                    <label for="nombre">Nombre:</label>
                    <input type="text" id="nombre" name="nombre" value="${mascota.nombre}" required>
                </div>
                <div>
                    <label for="especie">Especie:</label>
                    <input type="text" id="especie" name="especie" value="${mascota.especie}" required>
                </div>
            </div>

            <div class="grid">
                <div>
                    <label for="raza">Raza:</label>
                    <input type="text" id="raza" name="raza" value="${mascota.raza}" required>
                </div>
                <div>
                    <label for="edad">Edad:</label>
                    <input type="number" id="edad" name="edad" value="${mascota.edad}" required>
                </div>
            </div>

            <!-- Botones de acción -->
            <div class="grid">
                <div>
                    <button type="submit" class="secondary">Guardar Cambios</button>
                </div>
                <div>
                    <a href="${pageContext.request.contextPath}/cliente/misMascotas.jsp" class="button contrast">Cancelar</a>
                </div>
            </div>
        </form>
    </main>

    <%@ include file="../public/footer.jsp" %>
</body>
</html>