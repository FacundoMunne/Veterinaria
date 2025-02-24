<%@ include file="header.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="clases.Turno" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.util.Date" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista Turnos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@latest/css/pico.min.css">
</head>
<main class="container">
    <h1>Turnos del Profesional</h1>
    
    <form method="get" action="listaTurnosProfServlet">
        <input type="hidden" name="idProfesional" value="${idProfesional}">
        
        <label for="estado">Estado:</label>
        <select id="estado" name="estado">
            <option value="">Todos</option>
            <option value="Programado" ${param.estado == 'Programado' ? 'selected' : ''}>Programado</option>
            <option value="Recepcionado" ${param.estado == 'Recepcionado' ? 'selected' : ''}>Recepcionado</option>
            <option value="Cancelado" ${param.estado == 'Cancelado' ? 'selected' : ''}>Cancelado</option>
        </select>
        
        <label for="orden">Ordenar por fecha:</label>
        <select id="orden" name="orden">
            <option value="asc" ${param.orden == 'asc' ? 'selected' : ''}>Ascendente</option>
            <option value="desc" ${param.orden == 'desc' ? 'selected' : ''}>Descendente</option>
        </select>
        
        <button type="submit">Filtrar</button>
    </form>

    <!-- Tabla de turnos -->
    <table>
        <thead>
            <tr>
                <th>Fecha y Hora</th>
                <th>Mascota</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
           <% 
               SimpleDateFormat formatoFecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy 'a las' HH:mm");
               List<Turno> turnos = (List<Turno>) request.getAttribute("turnos");
               if (turnos != null) {
                   for (Turno turno : turnos) {
                       // Convertir LocalDateTime a Date
                       LocalDateTime fechaHoraLocal = turno.getFechaHora();
                       Date fechaHoraDate = Date.from(fechaHoraLocal.atZone(ZoneId.systemDefault()).toInstant());
                       String fechaFormateada = formatoFecha.format(fechaHoraDate);
           %>
                       <tr>
                           <td><%= fechaFormateada %></td>
                           <td><%= turno.getMascota().getNombre() %></td>
                           <td><%= turno.getEstado() %></td>
                           <td>
                               <form method="post" action="cambiarEstadoTurnoServlet" style="display: inline;">
                                   <input type="hidden" name="idMascota" value="<%= turno.getMascota().getIdMascota() %>">
                                   <input type="hidden" name="idProfesional" value="<%= turno.getProfesional().getIdProfesional() %>">
                                   <input type="hidden" name="fechaHora" value="<%= turno.getFechaHora() %>">
                                   <button type="submit" name="accion" value="Recepcionado" <%= !"Programado".equals(turno.getEstado()) ? "disabled" : "" %>>Recepcionar</button>
									<button type="submit" name="accion" value="Cancelado" <%= !"Programado".equals(turno.getEstado()) ? "disabled" : "" %>>Cancelar</button>
                               </form>
                           </td>
                       </tr>
           <%
                   }
               }
           %>
        </tbody>
    </table>
</main>

<%@ include file="footer.jsp" %>