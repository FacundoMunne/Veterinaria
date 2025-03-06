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
        <!-- Verificar si el usuario está autenticado -->
        <c:if test="${empty sessionScope.usuario}">
            <p>No estás autenticado. Por favor, <a href="${pageContext.request.contextPath}/public/login.jsp">inicia sesión</a>.</p>
        </c:if>

        <c:if test="${not empty sessionScope.usuario}">
            <!-- Mostrar mensaje si no hay mascotas -->
            <c:if test="${empty mascotas}">
                <p>No tienes mascotas registradas.</p>
            </c:if>

            <!-- Mostrar la tabla de mascotas -->
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
                                    <!-- Enlaces para editar, eliminar y sacar turno -->
                                    <a href="crudmascota?action=update&id=${mascota.idMascota}&clienteId=${sessionScope.idCliente}" class="secondary">Editar</a>
                                    <a href="crudmascota?action=delete&id=${mascota.idMascota}&clienteId=${sessionScope.idCliente}" class="contrast">Eliminar</a>
                                    <a href="${pageContext.request.contextPath}/sacarTurno?idMascota=${mascota.idMascota}" class="button">Sacar Turno</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <!-- Enlace para agregar una nueva mascota -->
            <a href="${pageContext.request.contextPath}/cliente/agregarMascota.jsp?clienteId=${sessionScope.idCliente}" class="button">Agregar Mascota</a>

            <!-- Enlace para volver al menú -->
            <a href="${pageContext.request.contextPath}/public/menu.jsp" class="button">Volver al Menú</a>
        </c:if>
    </main>

    <%@ include file="../public/footer.jsp" %>
</body>
</html>