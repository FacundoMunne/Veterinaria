<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Buscar Cliente</title>
    <link rel="stylesheet" href="https://unpkg.com/@picocss/pico@latest/css/pico.min.css">
</head>
<body>
	    <%@ include file="header.jsp" %>
	<header class="container">
		<h1>Turnos</h1>    
	</header>
 	<main>
    <form action="buscarMascotas" method="post">
        <label for="dni">DNI del Cliente:</label>
        <input type="text" id="dni" name="dni" required>
        <input type="submit" value="Buscar">
    </form>
    
   	<div>
   		 <a href="listaTurnos" role="button" class="contrast" style="margin: 0;">Lista de turnos</a>
   	</div>
    </main>
    <%@ include file="footer.jsp" %>
</body>
</html>
