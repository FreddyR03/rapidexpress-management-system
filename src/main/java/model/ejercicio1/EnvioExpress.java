/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ejercicio1;

/**
 *
 * @author fredd
 */
public class EnvioExpress extends ServicioEnvios{
    public EnvioExpress(double pesokg){
        super(pesokg);
    }
    
    @Override
    public double calcularCosto(){
        return 20000 + 2000 * pesoKg;
    }
}
