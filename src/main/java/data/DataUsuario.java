package data;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import clases.Usuario;
import clases.Rol;

public class DataUsuario {
	
	public int guardarUsuario(Usuario usuario) {
        String query = "INSERT INTO Usuario (nombreUsuario, contraseña, idRol) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        int idUsuario = 0;

        try {
            conn = DbConnector.getInstancia().getConn();
            stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getContraseña());
            stmt.setInt(3, usuario.getRol().getIdRol());
            stmt.executeUpdate();

            // Obtener el ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                idUsuario = rs.getInt(1);
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
        return idUsuario;
    }
}


