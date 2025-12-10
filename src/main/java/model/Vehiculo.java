package model;

import model.enums.EstadoVehiculo;

public class Vehiculo {
    private int idVehiculo;
    private String placa;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private double capacidadKg;
    private EstadoVehiculo estado;
    private String tipoVehiculo;

    public Vehiculo(int idVehiculo, String placa, String marca, String modelo, int anioFabricacion, double capacidadKg, EstadoVehiculo estado, String tipoVehiculo) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.capacidadKg = capacidadKg;
        this.estado = estado;
        this.tipoVehiculo = tipoVehiculo;
    }
    
    public Vehiculo(String placa, String marca, String modelo, int anioFabricacion, double capacidadKg, EstadoVehiculo estado, String tipoVehiculo) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.capacidadKg = capacidadKg;
        this.estado = estado;
        this.tipoVehiculo = tipoVehiculo;
    }

    public int getIdVehiculo() { return idVehiculo; }
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAnioFabricacion() { return anioFabricacion; }
    public double getCapacidadKg() { return capacidadKg; }
    public EstadoVehiculo getEstado() { return estado; }
    public String getTipoVehiculo(){ return tipoVehiculo; }

    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }
    public void setPlaca(String placa) { this.placa = placa; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAnioFabricacion(int anioFabricacion) { this.anioFabricacion = anioFabricacion; }
    public void setCapacidadKg(double capacidadKg) { this.capacidadKg = capacidadKg; }
    public void setEstado(EstadoVehiculo estado) { this.estado = estado; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo;}
    
    @Override
    public String toString() {
        return "Vehiculo{" +
               "idVehiculo=" + idVehiculo +
               ", placa='" + placa + '\'' +
               ", marca='" + marca + '\'' +
               ", modelo='" + modelo + '\'' +
               ", anioFabricacion=" + anioFabricacion +
               ", capacidadKg=" + capacidadKg +
               ", estado=" + estado +
               ", tipo de vehiculo=" + tipoVehiculo +
               '}';
    }
}
