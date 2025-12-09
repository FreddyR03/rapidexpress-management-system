package controller;

import java.util.ArrayList;
import model.Paquete;
import model.enums.EstadoPaquete;
import service.ServicioPaquete;

import java.util.List;

public class PaqueteController {

    private ServicioPaquete paqueteService;

    public PaqueteController() {
        this.paqueteService = new ServicioPaquete();
    }

    public void crearPaquete(String trackingCode, String descripcion, double peso, String dimensiones,
                             String origen, String destino, String remitente, String destinatario, EstadoPaquete estado) {
        try {
            Paquete paquete = new Paquete(0, trackingCode, descripcion, peso, dimensiones, origen, destino, remitente, destinatario, estado);
            paqueteService.crearPaquete(paquete);
            System.out.println("Paquete creado: " + paquete);
        } catch (Exception e) {
            System.err.println("Error al crear paquete: " + e.getMessage());
        }
    }

    public void listarPaquetes() {
        try {
            List<Paquete> paquetes = paqueteService.listarPaquetes();
            paquetes.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Error al listar paquetes: " + e.getMessage());
        }
    }

    public List<Paquete> listarPaquetesPorEstado(EstadoPaquete estado) {
        try {
            List<Paquete> paquetes = paqueteService.listarPaquetesPorEstado(estado);
            paquetes.forEach(System.out::println);
            return paquetes;
        } catch (Exception e) {
            System.err.println("Error al listar paquetes por estado: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public Paquete obtenerPaquetePorId(int id) {
        try {
            return paqueteService.obtenerPaquetePorId(id);
        } catch (Exception e) {
            System.err.println("Error al obtener paquete por ID: " + e.getMessage());
            return null; 
        }
    }

    public void actualizarPaquete(Paquete paquete) {
        try {
            paqueteService.actualizarPaquete(paquete);
            System.out.println("Paquete actualizado: " + paquete);
        } catch (Exception e) {
            System.err.println("Error al actualizar paquete: " + e.getMessage());
        }
    }
}
