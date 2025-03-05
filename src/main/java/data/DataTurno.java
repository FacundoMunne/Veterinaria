package data;

import java.util.*;
import java.sql.*;
import java.time.LocalDateTime;

import clases.*;



public class DataTurno {
	
	public List<Turno> getAll() {
	    List<Turno> turnos = new ArrayList<>();
	    String query = "SELECT * FROM Turnos";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            DataProfesional dataProfesional = new DataProfesional();
	            Profesional profesional = dataProfesional.getById(rs.getInt("idProfesional"));

	            DataMascota dataMascota = new DataMascota();
	            Mascota mascota = dataMascota.getById(rs.getInt("idMascota"));

	            // Crear Turno con estado
	            Turno turno = new Turno(
	                mascota,
	                profesional,
	                rs.getTimestamp("fechaHora").toLocalDateTime(),
	                rs.getString("estado") // Aquí añadimos el estado
	            );

	            turnos.add(turno);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) DbConnector.getInstancia().releaseConn();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return turnos;
	}

	 
	 public Turno getById(int idMascota, int idProfesional) {
		    String query = "SELECT fechaHora FROM Turnos WHERE idMascota = ? AND idProfesional = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    Turno turno = null;
		    DataMascota dataMascota = new DataMascota();
		    DataProfesional dataProfesional = new DataProfesional();

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        if (conn == null) {
		            System.out.println("La conexión es nula");
		            return null;
		        }

		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, idMascota);
		        stmt.setInt(2, idProfesional);

		        System.out.println("Ejecutando query: " + stmt);
		        rs = stmt.executeQuery();

		        if (rs.next()) {
		            turno = new Turno();
		            turno.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());
		            turno.setMascota(dataMascota.getById(idMascota));
		            turno.setProfesional(dataProfesional.getById(idProfesional));
		        }

		    } catch (SQLException e) {
		        System.out.println("Error SQL: " + e.getMessage());
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    
		    return turno;
		}
	 

	 
	    
	 public void add(Turno turno) {
		    String query = "INSERT INTO Turnos (fechaHora, idMascota, idProfesional, estado) VALUES (?, ?, ?, ?)";
		    Connection conn = null;
		    PreparedStatement stmt = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setTimestamp(1, Timestamp.valueOf(turno.getFechaHora()));
		        stmt.setInt(2, turno.getMascota().getIdMascota());
		        stmt.setInt(3, turno.getProfesional().getIdProfesional());
		        stmt.setString(4, turno.getEstado()); // Aquí se agrega el estado

		        int rowsInserted = stmt.executeUpdate();
		        if (rowsInserted > 0) {
		            System.out.println("El turno se guardó correctamente.");
		        }
		        conn.commit();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}


	 public void edit(Turno turno) {
		    String query = "UPDATE Turnos SET fechaHora = ?, idMascota = ?, idProfesional = ?, estado = ? WHERE fechaHora = ? AND idMascota = ? AND idProfesional = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setTimestamp(1, Timestamp.valueOf(turno.getFechaHora()));
		        stmt.setInt(2, turno.getMascota().getIdMascota());
		        stmt.setInt(3, turno.getProfesional().getIdProfesional());
		        stmt.setString(4, turno.getEstado()); // Aquí se agrega el estado
		        stmt.setTimestamp(5, Timestamp.valueOf(turno.getFechaHora()));
		        stmt.setInt(6, turno.getMascota().getIdMascota());
		        stmt.setInt(7, turno.getProfesional().getIdProfesional());
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}



	 public void remove(Turno turno) {
		    String query = "DELETE FROM Turnos WHERE fechaHora = ? AND idMascota = ? AND idProfesional = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setTimestamp(1, Timestamp.valueOf(turno.getFechaHora()));
		        stmt.setInt(2, turno.getMascota().getIdMascota());
		        stmt.setInt(3, turno.getProfesional().getIdProfesional());
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}
	 
	 public boolean isProfesionalAvailable(int idProfesional, LocalDateTime fechaHora) {
		    String query = "SELECT COUNT(*) FROM Turnos WHERE idProfesional = ? AND fechaHora = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, idProfesional);
		        stmt.setTimestamp(2, java.sql.Timestamp.valueOf(fechaHora));
		        rs = stmt.executeQuery();

		        if (rs.next()) {
		            int count = rs.getInt(1);
		            return count == 0; 
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return false; 
		}

	 public List<Turno> getTurnosByProfesional(int idProfesional, String estado, boolean ordenAscendente) {
		    List<Turno> turnos = new ArrayList<>();
		    String query = "SELECT * FROM Turnos WHERE idProfesional = ?";
		    
		    // Agregar filtro por estado si es necesario
		    if (estado != null && !estado.isEmpty()) {
		        query += " AND estado = ?";
		    }

		    // Ordenar por fechaHora, ascendente o descendente
		    query += " ORDER BY fechaHora " + (ordenAscendente ? "ASC" : "DESC");

		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, idProfesional);

		        // Si se pasa un estado, agregar el parámetro
		        int paramIndex = 2;
		        if (estado != null && !estado.isEmpty()) {
		            stmt.setString(paramIndex++, estado);
		        }

		        rs = stmt.executeQuery();

		        // Crear instancias fuera del bucle para mejorar eficiencia
		        DataMascota dataMascota = new DataMascota();
		        DataProfesional dataProfesional = new DataProfesional();

		        while (rs.next()) {
		            // Obtener los datos del turno y asignar las relaciones
		            Turno turno = new Turno(
		                dataMascota.getById(rs.getInt("idMascota")),
		                dataProfesional.getById(rs.getInt("idProfesional")),
		                rs.getTimestamp("fechaHora").toLocalDateTime(),
		                rs.getString("estado")
		            );
		            turnos.add(turno);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace(); // Considera manejar mejor las excepciones aquí.
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return turnos;
		}
	 
	 public Turno buscarTurnoPorClaveCompuesta(int idMascota, int idProfesional, LocalDateTime fechaHora) {
		    Turno turno = null;
		    String query = "SELECT * FROM Turnos WHERE idMascota = ? AND idProfesional = ? AND fechaHora = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, idMascota);
		        stmt.setInt(2, idProfesional);
		        stmt.setObject(3, fechaHora);
		        rs = stmt.executeQuery();

		        if (rs.next()) {
		            DataProfesional dataProfesional = new DataProfesional();
		            Profesional profesional = dataProfesional.getById(rs.getInt("idProfesional"));

		            DataMascota dataMascota = new DataMascota();
		            Mascota mascota = dataMascota.getById(rs.getInt("idMascota"));

		            // Crear Turno con estado
		            turno = new Turno(
		                mascota,
		                profesional,
		                rs.getTimestamp("fechaHora").toLocalDateTime(),
		                rs.getString("estado") // Aquí añadimos el estado
		            );
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		    return turno;
		}

	 public void actualizarTurno(Turno turno) {
		    String query = "UPDATE Turnos SET estado = ? WHERE idMascota = ? AND idProfesional = ? AND fechaHora = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setString(1, turno.getEstado());
		        stmt.setInt(2, turno.getMascota().getIdMascota());
		        stmt.setInt(3, turno.getProfesional().getIdProfesional());
		        stmt.setObject(4, turno.getFechaHora());
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}


	 public List<Turno> getByClienteId(int idCliente) {
		    List<Turno> turnos = new ArrayList<>();
		    String query = "SELECT t.*, m.nombre AS nombreMascota, p.nombre AS nombreProfesional " +
		                   "FROM Turnos t " +
		                   "JOIN Mascotas m ON t.idMascota = m.idMascota " +
		                   "JOIN Profesional p ON t.idProfesional = p.id " +
		                   "WHERE m.idCliente = ? AND t.fechaHora >= NOW() AND t.estado = 'Programado'";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, idCliente);
		        rs = stmt.executeQuery();

		        while (rs.next()) {
		            // Crear objetos Mascota y Profesional
		            DataMascota dataMascota = new DataMascota();
		            Mascota mascota = dataMascota.getById(rs.getInt("idMascota"));

		            DataProfesional dataProfesional = new DataProfesional();
		           Profesional profesional = dataProfesional.getById((rs.getInt("idProfesional")));

		            // Crear objeto Turno
		            Turno turno = new Turno();
		            turno.setMascota(mascota);
		            turno.setProfesional(profesional);
		            turno.setFechaHora(rs.getObject("fechaHora", LocalDateTime.class));
		            turno.setEstado(rs.getString("estado"));

		            // Agregar el turno a la lista
		            turnos.add(turno);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return turnos;
		}
	 
	 
	 public List<Mascota> getMascotasByProfesional(int idProfesional) {
		    List<Mascota> mascotas = new ArrayList<>();
		    String query = "SELECT DISTINCT m.* FROM Mascotas m " +
		                   "JOIN Turnos t ON m.idMascota = t.idMascota " +
		                   "WHERE t.idProfesional = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, idProfesional);
		        rs = stmt.executeQuery();

		        while (rs.next()) {
		            Mascota mascota = new Mascota();
		            mascota.setIdMascota(rs.getInt("idMascota"));
		            mascota.setNombre(rs.getString("nombre"));
		            mascota.setEspecie(rs.getString("especie"));
		            mascota.setRaza(rs.getString("raza"));
		            mascota.setEdad(rs.getInt("edad"));
		            mascotas.add(mascota);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return mascotas;
		}
	 
	 public List<Turno> getHistorialTurnosByMascotaAndProfesional(int idMascota, int idProfesional) {
		    List<Turno> turnos = new ArrayList<>();
		    String query = "SELECT t.*, m.nombre AS nombreMascota, p.nombre AS nombreProfesional " +
		                   "FROM Turnos t " +
		                   "JOIN Mascotas m ON t.idMascota = m.idMascota " +
		                   "JOIN Profesional p ON t.idProfesional = p.id " +
		                   "WHERE t.idMascota = ? AND t.idProfesional = ? " +
		                   "ORDER BY t.fechaHora DESC";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, idMascota);
		        stmt.setInt(2, idProfesional);
		        rs = stmt.executeQuery();

		        while (rs.next()) {
		            // Crear objetos Mascota y Profesional
		            Mascota mascota = new Mascota();
		            mascota.setIdMascota(rs.getInt("idMascota"));
		            mascota.setNombre(rs.getString("nombreMascota"));

		            Profesional profesional = new Profesional();
		            profesional.setIdProfesional(rs.getInt("idProfesional"));
		            profesional.setNombre(rs.getString("nombreProfesional"));

		            // Crear objeto Turno
		            Turno turno = new Turno();
		            turno.setMascota(mascota);
		            turno.setProfesional(profesional);
		            turno.setFechaHora(rs.getTimestamp("fechaHora").toLocalDateTime());
		            turno.setEstado(rs.getString("estado"));

		            // Agregar el turno a la lista
		            turnos.add(turno);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		            if (conn != null) DbConnector.getInstancia().releaseConn();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return turnos;
		}
}
