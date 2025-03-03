<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mis Mascotas</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
<%@ include file="../public/header.jsp" %>
    <header class="container">
        <h1>Mis Mascotas</h1>
    </header>
    
    <main class="container">
        <c:if test="${empty mascotas}">
            <p>No tienes mascotas registradas.</p>
        </c:if>

        <c:if test="${not empty mascotas}">
            <table>
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>Especie</th>
                        <th>Raza</th>
                        <th>Edad</th>
                      	<th>Acciones</th>
                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="mascota" items="${mascotas}">
                        <tr>
                            <td>${mascota.nombre}</td>
                            <td>${mascota.especie}</td>
                            <td>${mascota.raza}</td>
                            <td>${mascota.edad}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/sacarTurno?idMascota=${mascota.idMascota}" class="button">Sacar Turno</a>
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