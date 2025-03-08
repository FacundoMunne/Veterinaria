package data;

import clases.Observacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataObservacion {
    private Connection conn;

    public DataObservacion() {
        conn = DbConnector.getInstancia().getConn();
    }

    public void add(Observacion observacion) throws SQLException {
        String query = "INSERT INTO Observaciones (idMascota, observacion) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, observacion.getIdMascota());
            stmt.setString(2, observacion.getObservacion());
            stmt.executeUpdate();
        }
    }

    public List<Observacion> getByMascotaId(int idMascota) throws SQLException {
        List<Observacion> observaciones = new ArrayList<>();
        String query = "SELECT * FROM Observaciones WHERE idMascota = ? ORDER BY fechaObservacion DESC";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idMascota);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Observacion obs = new Observacion();
                obs.setIdObservacion(rs.getInt("idObservacion"));
                obs.setIdMascota(rs.getInt("idMascota"));
                obs.setObservacion(rs.getString("observacion"));
                obs.setFechaObservacion(rs.getTimestamp("fechaObservacion"));
                observaciones.add(obs);
            }
        }

        return observaciones;
    }
}