<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de Turnos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="../public/header.jsp" %>
    <header class="container">
        <h1>Historial de Turnos</h1>
    </header>
    <main class="container">
        <c:if test="${not empty turnos}">
            <table>
                <thead>
                    <tr>
                        <th>Fecha y Hora</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="turno" items="${turnos}">
                        <tr>
                            <td>${turno.fechaHora}</td>
                            <td>${turno.estado}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty turnos}">
            <p>No hay turnos registrados para esta mascota.</p>
        </c:if>
                <a href="${pageContext.request.contextPath}/listaMascotasProfServlet" class="button">Volver</a>
        
    </main>
    <%@ include file="../public/footer.jsp" %>
</body>
</html>