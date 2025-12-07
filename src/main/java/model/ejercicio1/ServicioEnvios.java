/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ejercicio1;

/**
 *
 * @author fredd
 */
public abstract class ServicioEnvios {
    protected double pesoKg;
    
    public ServicioEnvios(double pesoKg){
        this.pesoKg = pesoKg;
    }
    
    public abstract double calcularCosto();
}
