<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="clases.Cliente" %>
<%@ page import="data.DataCliente" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Información Personal</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
	 <%@ include file="../public/header.jsp" %>
    <header class="container">
        <h1>Información Personal</h1>
    </header>
    <main class="container">
        <c:if test="${sessionScope.idCliente == null}">
            <p>No has iniciado sesión. <a href="${pageContext.request.contextPath}/login.jsp">Iniciar sesión</a></p>
        </c:if>

        <c:if test="${sessionScope.idCliente != null}">
            <%
                // Obtener el idCliente de la sesión
                int idCliente = (int) session.getAttribute("idCliente");

                // Obtener los datos del cliente
                DataCliente dataCliente = new DataCliente();
                Cliente cliente = dataCliente.getById(idCliente);

                // Guardar el cliente en el alcance de la página
                pageContext.setAttribute("cliente", cliente);
            %>

            <c:if test="${cliente != null}">
                <h2>Datos Actuales</h2>
                <table>
                    <tr>
                        <th>DNI</th>
                        <td>${cliente.dni}</td>
                    </tr>
                    <tr>
                        <th>Nombre</th>
                        <td>${cliente.nombre}</td>
                    </tr>
                    <tr>
                        <th>Dirección</th>
                        <td>${cliente.direccion}</td>
                    </tr>
                    <tr>
                        <th>Teléfono</th>
                        <td>${cliente.telefono}</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>${cliente.email}</td>
                    </tr>
                </table>

                <!-- Enlace para editar -->
				<a href="${pageContext.request.contextPath}/editarCliente" class="secondary">Editar</a>            </c:if>

            <c:if test="${cliente == null}">
                <p>No se encontró información del cliente.</p>
            </c:if>
        </c:if>
    </main>
        <%@ include file="../public/footer.jsp" %>
    
</body>
</html>