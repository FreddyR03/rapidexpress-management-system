/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ejercicio2;

/**
 *
 * @author fredd
 */
public class Cliente {
    private int idCliente;
    private String nombre;
    private String cedula;
    private String telefono;

    // Constructor para insertar
    public Cliente(String nombre, String cedula, String telefono) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    // Constructor completo (BD)
    public Cliente(int id, String nombre, String cedula, String telefono) {
        this.idCliente = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    public int getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getTelefono() { return telefono; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}

