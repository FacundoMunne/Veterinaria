package clases;

import java.sql.Timestamp;

public class Observacion {
    private int idObservacion;
    private int idMascota;  
    private String observacion;
    private Timestamp fechaObservacion;

    public Observacion() {}

    public Observacion(int idMascota, String observacion) {
        this.idMascota = idMascota;
        this.observacion = observacion;
    }

    public int getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(int idObservacion) {
        this.idObservacion = idObservacion;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Timestamp getFechaObservacion() {
        return fechaObservacion;
    }

    public void setFechaObservacion(Timestamp fechaObservacion) {
        this.fechaObservacion = fechaObservacion;
    }
}