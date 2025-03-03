package data;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import clases.Usuario;
import clases.Rol;

public class DataUsuario {
	
	public Usuario getByNombreUsuario(String nombreUsuario) {
		System.out.println("tuvieja");
	    String query = "SELECT u.*, r.idRol, r.nombre AS nombreRol FROM Usuarios u " +
	                   "JOIN Roles r ON u.idRol = r.idRol " +
	                   "WHERE u.nombreUsuario = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    Usuario usuario = null;

	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, nombreUsuario);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            usuario = new Usuario();
	            usuario.setIdUsuario(rs.getInt("idUsuario"));
	            usuario.setNombreUsuario(rs.getString("nombreUsuario"));
	            usuario.setContraseña(rs.getString("contraseña"));

	            Rol rol = new Rol();
	            rol.setIdRol(rs.getInt("idRol"));
	            rol.setNombre(rs.getString("nombreRol"));
	            usuario.setRol(rol);
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
	    return usuario;
	}
	
	public int getIdCliente(int idUsuario) {
	    String query = "SELECT id FROM Cliente WHERE idUsuario = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int idCliente = -1; // Valor por defecto si no se encuentra

	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, idUsuario);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            idCliente = rs.getInt("id");
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
	    return idCliente;
	}
	
	public int getIdProfesional(int idUsuario) {
	    String query = "SELECT id FROM Profesional WHERE idUsuario = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    int idProfesional = -1; // Valor por defecto si no se encuentra

	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, idUsuario);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            idProfesional = rs.getInt("id");
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
	    return idProfesional;
	}

	public boolean add(Usuario usuario) {
        String query = "INSERT INTO usuarios (nombreUsuario, contraseña, idRol) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean exito = false;

        try {
            // Obtener la conexión
            conn = DbConnector.getInstancia().getConn();

            // Preparar la consulta
            stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContraseña()); // La contraseña ya debe estar hasheada
            stmt.setInt(3, usuario.getRol().getIdRol());

            // Ejecutar la consulta
            int filasAfectadas = stmt.executeUpdate();

            // Verificar si se insertó correctamente
            exito = filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return exito;
    }
	
	public void actualizarUsuario(Usuario usuario) {
	    String query = "UPDATE Usuarios SET nombreUsuario = ?, contraseña = ? WHERE idUsuario = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;

	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, usuario.getNombreUsuario());

	        // Hashear la contraseña antes de guardarla
	        String contraseñaHasheada = BCrypt.hashpw(usuario.getContraseña(), BCrypt.gensalt());
	        stmt.setString(2, contraseñaHasheada);

	        stmt.setInt(3, usuario.getIdUsuario());
	        int rowsUpdated = stmt.executeUpdate();

	        // Depuración: Verificar si la actualización fue exitosa
	        if (rowsUpdated > 0) {
	            System.out.println("Usuario actualizado correctamente.");
	        } else {
	            System.out.println("No se encontró el usuario con ID: " + usuario.getIdUsuario());
	        }
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


