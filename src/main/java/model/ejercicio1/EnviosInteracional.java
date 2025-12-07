/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ejercicio1;

/**
 *
 * @author fredd
 */
public class EnviosInteracional extends ServicioEnvios{
    public EnviosInteracional(double pesokg){
        super(pesokg);
    }
    
    @Override
    public double calcularCosto(){
        return 50000 + 5000 * pesoKg;
    }
}
