<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menú Principal - Veterinaria XYZ</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
    <style>
        /* Estilos personalizados */
        body {
            background-color: #f4f4f4;
            font-family: Arial, sans-serif;
        }
        header {
            background-color: #007bff;
            color: white;
            padding: 1rem 0;
            text-align: center;
        }
        header h1 {
            margin: 0;
            font-size: 2.5rem;
        }
        nav ul {
            list-style: none;
            padding: 0;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 1rem;
            margin: 1rem 0;
        }
        nav ul li {
            margin: 0;
        }
        nav ul li a {
            color: white;
            text-decoration: none;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        nav ul li a:hover {
            background-color: rgba(255, 255, 255, 0.1);
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 1rem;
        }
        .role-section {
            margin-top: 2rem;
            padding: 1rem;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .role-section h2 {
            color: #007bff;
            margin-bottom: 1rem;
        }
    </style>
</head>

<body>

    <header>
        <div class="container">
            <h1>Veterinaria XYZ</h1>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Inicio</a></li>

                    <%-- Menú para clientes --%>
                    <c:if test="${sessionScope.rol eq 'Cliente'}">
                        <li><a href="${pageContext.request.contextPath}/cliente/informacionPersonal.jsp">Información Personal</a></li>
<li><a href="${pageContext.request.contextPath}/listarMascotas" class="button">Mis Mascotas</a></li>                        
<li><a href="${pageContext.request.contextPath}/listaTurnosClien">Mis Turnos</a></li>
                        <li><a href="${pageContext.request.contextPath}/cliente/ajustesCliente.jsp">Ajustes</a></li>
                    </c:if>

                    <%-- Menú para profesionales --%>
                    <c:if test="${sessionScope.rol eq 'Profesional'}">
                        <li><a href="${pageContext.request.contextPath}/profesional/informacionPersonalProf.jsp">Información Personal</a></li>
                        <li><a href="${pageContext.request.contextPath}/listaTurnosProfServlet">Próximos Turnos</a></li>
                        <li><a href="${pageContext.request.contextPath}/listaMascotasProfServlet">Información de Mascotas</a></li>
                        <li><a href="${pageContext.request.contextPath}/profesional/ajustesProfesional.jsp">Ajustes</a></li>
                    </c:if>

                    <%-- Menú para administradores --%>
                    <c:if test="${sessionScope.rol eq 'Admin'}">
                        <li><a href="${pageContext.request.contextPath}/crudcliente">Gestión de Clientes</a></li>
                        <li><a href="${pageContext.request.contextPath}/crudprofesional">Gestión de Profesionales</a></li>
                        <li><a href="${pageContext.request.contextPath}/listaTurnos">Gestión de Turnos</a></li>
                    </c:if>

                    <%-- Cerrar sesión --%>
                    <c:if test="${sessionScope.rol != null}">
                        <li><a href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a></li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </header>

    <main class="container">
        <section class="role-section">
            <h2>Bienvenido, ${sessionScope.rol}</h2>
            <p>Selecciona una opción del menú para comenzar.</p>
        </section>
    </main>
</body>
    <%@ include file="footer.jsp" %>

</html>