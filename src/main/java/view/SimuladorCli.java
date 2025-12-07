/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import model.ejercicio1.EnvioExpress;
import model.ejercicio1.EnviosEstandar;
import model.ejercicio1.EnviosInteracional;
import model.ejercicio1.ServicioEnvios;
import util.CLIUtils;

/**
 *
 * @author fredd
 */
public class SimuladorCli {
    public static void simular(){
        System.out.println("==== SIMULADOR COSTO ENVIOS ====");
        
        double peso = CLIUtils.leerDouble("Ingrese el peso del paquete(kg)");
        
        System.out.println("Seleccione el tipo de envío:");
        System.out.println("1. Envío Estandar");
        System.out.println("2. Envío Express");
        System.out.println("3. Envío Internacional");
        
        int opcion = CLIUtils.leerInt("Ingrese la opcion que desees: ");
        
        ServicioEnvios servicio = null;
        
        switch(opcion){
            case 1:
                servicio = new EnviosEstandar(peso);
                break;
            case 2: 
                servicio = new EnvioExpress(peso);
                break;
            case 3:
                servicio = new EnviosInteracional(peso);
                break;
            default:
                System.out.println("Tipo inválido.");
                return;
        }
        
        double costo = servicio.calcularCosto();
        
        System.out.println("Costo del evio es: $" + costo);
    }
}
