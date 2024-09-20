	package data;

	import java.sql.*;
	import java.util.ArrayList;
	import java.util.List;
	import clases.Mascota;
	import clases.Cliente;

	public class DataMascota {

		public Mascota getById(int id) {
		    String query = "SELECT * FROM Mascotas WHERE idMascota = ?";
		    Mascota mascota = null;
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    
		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, id);
		        rs = stmt.executeQuery();
		        
		        if (rs.next()) {
		            DataCliente dataCliente = new DataCliente();
		            Cliente cliente = dataCliente.getById(rs.getInt("idCliente"));
		            
		            mascota = new Mascota(
		                rs.getInt("idMascota"),
		                rs.getString("nombre"),
		                rs.getString("especie"),
		                rs.getString("raza"),
		                rs.getInt("edad"),
		                cliente
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
		    return mascota;
		}

		public List<Mascota> getByClienteId(int clienteId) {
		    List<Mascota> mascotas = new ArrayList<>();
		    String query = "SELECT * FROM Mascotas WHERE idCliente = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, clienteId);
		        rs = stmt.executeQuery();

		        while (rs.next()) {
		            Cliente cliente = new DataCliente().getById(rs.getInt("idCliente")); 
		            Mascota mascota = new Mascota(
		                rs.getInt("idMascota"),
		                rs.getString("nombre"),
		                rs.getString("especie"),
		                rs.getString("raza"),
		                rs.getInt("edad"),
		                cliente
		            );
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


		public void add(Mascota mascota) {
		    String query = "INSERT INTO Mascotas (nombre, especie, raza, edad, idCliente) VALUES (?, ?, ?, ?, ?)";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    
		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setString(1, mascota.getNombre());
		        stmt.setString(2, mascota.getEspecie());
		        stmt.setString(3, mascota.getRaza());
		        stmt.setInt(4, mascota.getEdad());
		        stmt.setInt(5, mascota.getCliente().getIdCliente());
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


		public void edit(Mascota mascota) {
		    String query = "UPDATE Mascotas SET nombre = ?, especie = ?, raza = ?, edad = ?, idCliente = ? WHERE idMascota = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    
		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setString(1, mascota.getNombre());
		        stmt.setString(2, mascota.getEspecie());
		        stmt.setString(3, mascota.getRaza());
		        stmt.setInt(4, mascota.getEdad());
		        stmt.setInt(5, mascota.getCliente().getIdCliente()); 
		        stmt.setInt(6, mascota.getIdMascota());
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

		public void remove(int id) {
		    String query = "DELETE FROM Mascotas WHERE idMascota = ?";
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    
		    try {
		        conn = DbConnector.getInstancia().getConn();
		        stmt = conn.prepareStatement(query);
		        stmt.setInt(1, id);
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


