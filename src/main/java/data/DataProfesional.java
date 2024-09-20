package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import clases.Profesional;

public class DataProfesional {

    public Profesional getById(int id) {
        String query = "SELECT * FROM Profesional WHERE id = ?";
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
                profesional = new Profesional(
                    rs.getInt("id"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    rs.getString("telefono"),
                    rs.getString("email")
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
        return profesional;
    }

    public List<Profesional> getAll() {
        List<Profesional> profesionales = new ArrayList<>();
        String query = "SELECT * FROM Profesional";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DbConnector.getInstancia().getConn();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                profesionales.add(new Profesional(
                    rs.getInt("id"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("especialidad"),
                    rs.getString("telefono"),
                    rs.getString("email")
                ));
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
        String query = "INSERT INTO Profesional (dni, nombre, especialidad, telefono, email) VALUES (?, ?, ?, ?, ?)";
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
        String query = "UPDATE Profesional SET dni = ?, nombre = ?, especialidad = ?, telefono = ?, email = ? WHERE id = ?";
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
            stmt.setInt(6, profesional.getIdProfesional());
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
