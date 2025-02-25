package data;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import clases.Profesional;
import clases.Usuario;
import clases.Rol;


public class DataProfesional {

	public Profesional getById(int id) {
	    String query = "SELECT p.*, u.idUsuario, u.nombreUsuario, u.contraseña, r.idRol, r.nombre AS nombreRol " +
	                   "FROM Profesional p " +
	                   "JOIN Usuarios u ON p.idUsuario = u.idUsuario " +
	                   "JOIN Roles r ON u.idRol = r.idRol " +
	                   "WHERE p.idProfesional = ?";
	    Profesional profesional = null;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        stmt.setInt(1, id);
	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            // Crear el objeto Usuario
	            Usuario usuario = new Usuario();
	            usuario.setIdUsuario(rs.getInt("idUsuario"));
	            usuario.setNombreUsuario(rs.getString("nombreUsuario"));
	            usuario.setContraseña(rs.getString("contraseña"));
	            
	            // Crear el objeto Rol
	            Rol rol = new Rol();
	            rol.setIdRol(rs.getInt("idRol"));
	            rol.setNombre(rs.getString("nombreRol"));
	            
	            // Asignar el Rol al Usuario
	            usuario.setRol(rol);

	            // Crear el objeto Profesional
	            profesional = new Profesional();
	            profesional.setIdProfesional(rs.getInt("idProfesional"));
	            profesional.setUsuario(usuario);
	            profesional.setDni(rs.getString("dni"));
	            profesional.setNombre(rs.getString("nombre"));
	            profesional.setEspecialidad(rs.getString("especialidad"));
	            profesional.setTelefono(rs.getString("telefono"));
	            profesional.setEmail(rs.getString("email"));
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
	    return profesional;
	}

	public List<Profesional> getAll() {
	    List<Profesional> profesionales = new ArrayList<>();
	    String query = "SELECT p.*, u.idUsuario, u.nombreUsuario, u.contraseña, r.idRol, r.nombre AS nombreRol " +
	                   "FROM Profesional p " +
	                   "JOIN Usuarios u ON p.idUsuario = u.idUsuario " +
	                   "JOIN Roles r ON u.idRol = r.idRol";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            // Crear el objeto Usuario
	            Usuario usuario = new Usuario();
	            usuario.setIdUsuario(rs.getInt("idUsuario"));
	            usuario.setNombreUsuario(rs.getString("nombreUsuario"));
	            usuario.setContraseña(rs.getString("contraseña"));
	            
	            // Crear el objeto Rol
	            Rol rol = new Rol();
	            rol.setIdRol(rs.getInt("idRol"));
	            rol.setNombre(rs.getString("nombreRol"));
	            
	            // Asignar el Rol al Usuario
	            usuario.setRol(rol);

	            // Crear el objeto Profesional
	            Profesional profesional = new Profesional();
	            profesional.setIdProfesional(rs.getInt("idProfesional"));
	            profesional.setUsuario(usuario);
	            profesional.setDni(rs.getString("dni"));
	            profesional.setNombre(rs.getString("nombre"));
	            profesional.setEspecialidad(rs.getString("especialidad"));
	            profesional.setTelefono(rs.getString("telefono"));
	            profesional.setEmail(rs.getString("email"));

	            // Agregar el profesional a la lista
	            profesionales.add(profesional);
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
	    return profesionales;
	}

	public void add(Profesional profesional) {
	    String query = "INSERT INTO Profesional (dni, nombre, especialidad, telefono, email, idUsuario) VALUES (?, ?, ?, ?, ?, ?)";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, profesional.getDni());
	        stmt.setString(2, profesional.getNombre());
	        stmt.setString(3, profesional.getEspecialidad());
	        stmt.setString(4, profesional.getTelefono());
	        stmt.setString(5, profesional.getEmail());
	        stmt.setInt(6, profesional.getUsuario().getIdUsuario()); // Agregar idUsuario
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

	public void edit(Profesional profesional) {
	    String query = "UPDATE Profesional SET dni = ?, nombre = ?, especialidad = ?, telefono = ?, email = ?, idUsuario = ? WHERE idProfesional = ?";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    
	    try {
	        conn = DbConnector.getInstancia().getConn();
	        stmt = conn.prepareStatement(query);
	        stmt.setString(1, profesional.getDni());
	        stmt.setString(2, profesional.getNombre());
	        stmt.setString(3, profesional.getEspecialidad());
	        stmt.setString(4, profesional.getTelefono());
	        stmt.setString(5, profesional.getEmail());
	        stmt.setInt(6, profesional.getUsuario().getIdUsuario()); // Agregar idUsuario
	        stmt.setInt(7, profesional.getIdProfesional());
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
        String query = "DELETE FROM Profesional WHERE id = ?";
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
