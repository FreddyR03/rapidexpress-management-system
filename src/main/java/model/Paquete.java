package model;

import java.time.LocalDate;
import model.enums.EstadoPaquete;

public class Paquete {
    private int idPaquete;
    private String trackingCode; 
    private String descripcion;
    private double peso;
    private String dimensiones;
    private String direccionOrigen;
    private String direccionDestino;
    private String remitente;
    private String destinatario;
    private EstadoPaquete estado;
    private LocalDate fechaIngreso;

    public Paquete(int idPaquete, String trackingCode, String descripcion, double peso, String dimensiones,
                   String direccionOrigen, String direccionDestino, String remitente, String destinatario, EstadoPaquete estado,
                   LocalDate fechaIngreso) {
        this.idPaquete = idPaquete;
        this.trackingCode = trackingCode;
        this.descripcion = descripcion;
        this.peso = peso;
        this.dimensiones = dimensiones;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
    }

    // Constructor sin ID para creación desde menú
    public Paquete(String trackingCode, String descripcion, double peso, String dimensiones,
                   String direccionOrigen, String direccionDestino, String remitente, String destinatario, EstadoPaquete estado,
                   LocalDate fechaIngreso) {
        this.trackingCode = trackingCode;
        this.descripcion = descripcion;
        this.peso = peso;
        this.dimensiones = dimensiones;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
    }

    public int getIdPaquete() { return idPaquete; }
    public String getTrackingCode() { return trackingCode; }
    public String getDescripcion() { return descripcion; }
    public double getPeso() { return peso; }
    public String getDimensiones() { return dimensiones; }
    public String getDireccionOrigen() { return direccionOrigen; }
    public String getDireccionDestino() { return direccionDestino; }
    public String getRemitente() { return remitente; }
    public String getDestinatario() { return destinatario; }
    public EstadoPaquete getEstado() { return estado; }
    public LocalDate getFechaIngreso(){ return fechaIngreso; }

    public void setIdPaquete(int idPaquete) { this.idPaquete = idPaquete; }
    public void setTrackingCode(String trackingCode) { this.trackingCode = trackingCode; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPeso(double peso) { this.peso = peso; }
    public void setDimensiones(String dimensiones) { this.dimensiones = dimensiones; }
    public void setDireccionOrigen(String direccionOrigen) { this.direccionOrigen = direccionOrigen; }
    public void setDireccionDestino(String direccionDestino) { this.direccionDestino = direccionDestino; }
    public void setRemitente(String remitente) { this.remitente = remitente; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public void setEstado(EstadoPaquete estado) { this.estado = estado; }
    public void setFechaIngreso(LocalDate fechaIngreso) {this.fechaIngreso = fechaIngreso; }

    @Override
    public String toString() {
        return "Paquete{" +
                "idPaquete=" + idPaquete +
                ", trackingCode='" + trackingCode + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", peso=" + peso +
                ", dimensiones='" + dimensiones + '\'' +
                ", direccionOrigen='" + direccionOrigen + '\'' +
                ", direccionDestino='" + direccionDestino + '\'' +
                ", remitente='" + remitente + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", estado=" + estado +
                ", fecha ingreso " + fechaIngreso +
                '}';
    }
}
