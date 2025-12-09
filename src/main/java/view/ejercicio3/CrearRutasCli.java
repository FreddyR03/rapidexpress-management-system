/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.ejercicio3;

import controller.ConductorController;
import controller.PaqueteController;
import controller.RutaController;
import controller.VehiculoController;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Paquete;
import model.Ruta;
import model.enums.EstadoPaquete;
import model.enums.EstadoRuta;
import util.CLIUtils;

/**
 *
 * @author fredd
 */
public class CrearRutasCli {
    private static ConductorController conductorController = new ConductorController();
    private static VehiculoController vehiculoController = new VehiculoController();
    private static RutaController rutaController = new RutaController();
    private static PaqueteController paqueteController = new PaqueteController();
    
    public static void crearRutaCLI() {

        System.out.println("\n--- Crear Ruta ---");
        
        // 1. FECHA
        String fechaStr = CLIUtils.leerString("Fecha de la ruta (YYYY-MM-DD)");
        LocalDate fecha = LocalDate.parse(fechaStr);

        // 2. SELECCIONAR CONDUCTOR
        conductorController.listarConductores();
        int idConductor = CLIUtils.leerInt("ID del conductor");

        // 3. SELECCIONAR VEHICULO
        vehiculoController.listarVehiculos();
        int idVehiculo = CLIUtils.leerInt("ID del vehículo");
        
        List<Paquete> paquetesRuta = seleccionarPaquetesParaRuta();

        // 4. CREAR OBJETO RUTA
        Ruta ruta = new Ruta(
                0,                  
                fecha,
                vehiculoController.obtenerVehiculoPorId(idVehiculo),
                null,                 
                0.0,                 
                EstadoRuta.PENDIENTE,
                paquetesRuta
        );

        // 5. LLAMAR AL SERVICIO (ESTE TIENE LA VALIDACIÓN)
        try {
            rutaController.crearRuta(idConductor, ruta);
        } catch (Exception e) {
            System.err.println("Error al crear ruta: " + e.getMessage());
        }
    }
    
    private static List<Paquete> seleccionarPaquetesParaRuta() {
        List<Paquete> paquetes = new ArrayList<>();
        System.out.println("\n--- Asignar Paquetes ---");
        
        paqueteController.listarPaquetes();
        
        String respuesta;
        do {
            int idPaquete = CLIUtils.leerInt("ID del paquete a incluir (0 para terminar)");
            if (idPaquete == 0) break;
            
            try {
                // Obtiene el paquete de la base de datos a través del controlador
                Paquete p = paqueteController.obtenerPaquetePorId(idPaquete);
                if (p != null) {
                    paquetes.add(p);
                    System.out.println("Paquete " + idPaquete + " agregado (Peso: " + p.getPeso() + " kg)");
                } else {
                    System.out.println("Paquete no encontrado.");
                }
            } catch (Exception e) {
                System.err.println("Error al agregar paquete: " + e.getMessage());
            }

            respuesta = CLIUtils.leerString("¿Desea agregar otro paquete? (s/n)");
            if (!respuesta.equalsIgnoreCase("s")) break;
        } while (true);
        
        System.out.println("Total de paquetes a incluir: " + paquetes.size());
        return paquetes;
    }
}

