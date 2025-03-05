<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="clases.Profesional" %>
<%@ page import="data.DataProfesional" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Información Personal del Profesional</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
    <%@ include file="../public/header.jsp" %>
    <header class="container">
        <h1>Información Personal del Profesional</h1>
    </header>
    <main class="container">
        <c:if test="${sessionScope.idProfesional == null}">
            <p>No has iniciado sesión. <a href="${pageContext.request.contextPath}/login.jsp">Iniciar sesión</a></p>
        </c:if>

        <c:if test="${sessionScope.idProfesional != null}">
            <%
                // Obtener el idProfesional de la sesión
                int idProfesional = (int) session.getAttribute("idProfesional");

                // Obtener los datos del profesional
                DataProfesional dataProfesional = new DataProfesional();
                Profesional profesional = dataProfesional.getById(idProfesional);

                // Guardar el profesional en el alcance de la página
                pageContext.setAttribute("profesional", profesional);
            %>

            <c:if test="${profesional != null}">
                <h2>Datos Actuales</h2>
                <table>
                    <tr>
                        <th>DNI</th>
                        <td>${profesional.dni}</td>
                    </tr>
                    <tr>
                        <th>Nombre</th>
                        <td>${profesional.nombre}</td>
                    </tr>
                    <tr>
                        <th>Teléfono</th>
                        <td>${profesional.telefono}</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>${profesional.email}</td>
                    </tr>
                    <tr>
                        <th>Especialidad</th>
                        <td>${profesional.especialidad}</td>
                    </tr>
                </table>

                <!-- Enlace para editar -->
                <a href="${pageContext.request.contextPath}/editarProfesional" class="secondary">Editar</a>
            </c:if>

            <c:if test="${profesional == null}">
                <p>No se encontró información del profesional.</p>
            </c:if>
        </c:if>
    </main>
    <%@ include file="../public/footer.jsp" %>
</body>
</html>