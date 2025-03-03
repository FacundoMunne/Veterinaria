<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Turnos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
	    <%@ include file="../public/header.jsp" %>

    <header class="container">
        <h1>Mis Turnos</h1>
    </header>
    <main class="container">
        <c:if test="${empty turnos}">
            <p>No tienes turnos programados.</p>
        </c:if>

        <c:if test="${not empty turnos}">
            <table>
                <thead>
                    <tr>
                        <th>Mascota</th>
                        <th>Profesional</th>
                        <th>Fecha y Hora</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="turno" items="${turnos}">
                        <tr>
                            <td>${turno.mascota.nombre}</td>
                            <td>${turno.profesional.nombre}</td>
                            <td>${turno.fechaHora}</td>
                            <td>${turno.estado}</td>
                            <td>
                                <c:if test="${turno.estado eq 'Programado'}">
                                    <a href="${pageContext.request.contextPath}/cancelarTurno?idMascota=${turno.mascota.idMascota}&idProfesional=${turno.profesional.idProfesional}&fechaHora=${turno.fechaHora}" class="button">Cancelar</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <a href="${pageContext.request.contextPath}/public/menu.jsp" class="button">Volver al Menú</a>
    </main>
            <%@ include file="../public/footer.jsp" %>
    
</body>
</html>