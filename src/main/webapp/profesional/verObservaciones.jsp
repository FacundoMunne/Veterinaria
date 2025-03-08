<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Observaciones de la Mascota</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="../public/header.jsp" %>
    <header class="container">
        <h1>Observaciones de la Mascota</h1>
    </header>
    <main class="container">
        <c:if test="${not empty observaciones}">
            <table>
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Observación</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="obs" items="${observaciones}">
                        <tr>
                            <td>${obs.fechaObservacion}</td>
                            <td>${obs.observacion}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty observaciones}">
            <p>No hay observaciones registradas para esta mascota.</p>
        </c:if>

        <!-- Botón para volver -->
        <a href="${pageContext.request.contextPath}/listaMascotasProfServlet" class="button">Volver</a>
    </main>
    <%@ include file="../public/footer.jsp" %>
</body>
</html>