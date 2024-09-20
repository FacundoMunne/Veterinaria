package data;

import java.util.*;
import java.sql.*;
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

		            Turno turno = new Turno(
		                mascota,
		                profesional,
		                rs.getTimestamp("fechaHora").toLocalDateTime()
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
		    String query = "INSERT INTO Turnos (fechaHora, idMascota, idProfesional) VALUES (?, ?, ?)";
		    Connection conn = null;
		    PreparedStatement stmt = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        if (conn == null) {
		            System.out.println("La conexión es nula");
		            return;
		        }

		        stmt = conn.prepareStatement(query);
		        stmt.setTimestamp(1, Timestamp.valueOf(turno.getFechaHora()));
		        stmt.setInt(2, turno.getMascota().getIdMascota());
		        stmt.setInt(3, turno.getProfesional().getIdProfesional());

		        System.out.println("Ejecutando query: " + stmt);
		        int rowsInserted = stmt.executeUpdate();
		        System.out.println("Filas insertadas: " + rowsInserted);

		        if (rowsInserted > 0) {
		            System.out.println("El turno se guardó correctamente.");
		        } else {
		            System.out.println("No se insertaron filas.");
		        }

		        conn.commit(); // Si auto-commit está desactivado
		    } catch (SQLException e) {
		        System.out.println("Error SQL: " + e.getMessage());
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
		    String query = "UPDATE Turnos SET fechaHora = ?, idMascota = ?, idProfesional = ? WHERE fechaHora = ? AND idMascota = ? AND idProfesional = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setTimestamp(1, Timestamp.valueOf(turno.getFechaHora()));
		        stmt.setInt(2, turno.getMascota().getIdMascota());
		        stmt.setInt(3, turno.getProfesional().getIdProfesional());
		        stmt.setTimestamp(4, Timestamp.valueOf(turno.getFechaHora())); // Parte de la clave primaria
		        stmt.setInt(5, turno.getMascota().getIdMascota()); // Parte de la clave primaria
		        stmt.setInt(6, turno.getProfesional().getIdProfesional()); // Parte de la clave primaria
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


}
