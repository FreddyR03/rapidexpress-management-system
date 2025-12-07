/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.ejercicio1;

/**
 *
 * @author fredd
 */
public class EnviosEstandar extends ServicioEnvios{ 

    public EnviosEstandar(double pesoKg) {
        super(pesoKg);
    }
    
   @Override
   public double calcularCosto(){
       return 10000 + 1000 * pesoKg;
   }
}
