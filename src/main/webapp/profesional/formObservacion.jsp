<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ingresar Observación</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="../public/header.jsp" %>

    <header class="container">
        <h1>Ingresar Observación</h1>
    </header>

    <main class="container">
        <form method="post" action="${pageContext.request.contextPath}/cambiarEstadoTurnoServlet">
            <input type="hidden" name="idMascota" value="${param.idMascota}">
            <input type="hidden" name="idProfesional" value="${param.idProfesional}">
            <input type="hidden" name="fechaHora" value="${param.fechaHora}">
            <input type="hidden" name="accion" value="Recepcionado">
            
            <label for="observacion">Observación:</label>
            <textarea id="observacion" name="observacion" required></textarea>
            
            <button type="submit">Guardar Observación</button>
            <a href="${pageContext.request.contextPath}/listaTurnosProfServlet?idProfesional=${param.idProfesional}" class="button contrast">Cancelar</a>
        </form>
    </main>

    <%@ include file="../public/footer.jsp" %>
</body>
</html>