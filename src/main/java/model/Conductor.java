package model;

import java.time.LocalDate;
import model.enums.EstadoConductor;

public class Conductor {
    private int idConductor;
    private String nombreCompleto;
    private String identificacion;
    private String tipoLicencia;
    private String numeroContacto;
    private EstadoConductor estado;
    private LocalDate fechaVencimientoLicencia;

    public Conductor(int idConductor, String nombreCompleto, String identificacion, String tipoLicencia, String numeroContacto, EstadoConductor estado, LocalDate fechaVencimientoLicencia) {
        this.idConductor = idConductor;
        this.nombreCompleto = nombreCompleto;
        this.identificacion = identificacion;
        this.tipoLicencia = tipoLicencia;
        this.numeroContacto = numeroContacto;
        this.estado = estado;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    // Constructor sin ID para creación desde menú
    public Conductor(String nombreCompleto, String identificacion, String tipoLicencia, String numeroContacto, EstadoConductor estado, LocalDate fechaVencimientoLicencia) {
        this.nombreCompleto = nombreCompleto;
        this.identificacion = identificacion;
        this.tipoLicencia = tipoLicencia;
        this.numeroContacto = numeroContacto;
        this.estado = estado;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    public int getIdConductor() { return idConductor; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getIdentificacion() { return identificacion; }
    public String getTipoLicencia() { return tipoLicencia; }
    public String getNumeroContacto() { return numeroContacto; }
    public EstadoConductor getEstado() { return estado; }
    public LocalDate getFechaVecimientoLicencia(){ return fechaVencimientoLicencia; }

    public void setIdConductor(int idConductor) { this.idConductor = idConductor; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public void setTipoLicencia(String tipoLicencia) { this.tipoLicencia = tipoLicencia; }
    public void setNumeroContacto(String numeroContacto) { this.numeroContacto = numeroContacto; }
    public void setEstado(EstadoConductor estado) { this.estado = estado; }
    public void setFechaVecimientoLicencia(LocalDate fechaVencimientoLicencia){this.fechaVencimientoLicencia = fechaVencimientoLicencia;}

    @Override
    public String toString() {
        return "Conductor{" +
               "idConductor=" + idConductor +
               ", nombreCompleto='" + nombreCompleto + '\'' +
               ", identificacion='" + identificacion + '\'' +
               ", tipoLicencia='" + tipoLicencia + '\'' +
               ", numeroContacto='" + numeroContacto + '\'' +
               ", estado=" + estado +
               ", fecha licencia=" + fechaVencimientoLicencia +
               '}';
    }
}
