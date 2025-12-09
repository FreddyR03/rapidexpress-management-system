package controller;

import java.time.LocalDate;
import model.Conductor;
import model.enums.EstadoConductor;
import service.ServicioConductor;

import java.util.List;

public class ConductorController {

    private ServicioConductor conductorService;

    public ConductorController() {
        this.conductorService = new ServicioConductor();
    }

    public void crearConductor(String nombre, String identificacion, String tipoLicencia, String contacto, EstadoConductor estado, LocalDate fecha_vencimiento_licencia) {
        try {
            Conductor conductor = new Conductor(0, nombre, identificacion, tipoLicencia, contacto, estado, fecha_vencimiento_licencia);
            conductorService.crearConductor(conductor);
            System.out.println("Conductor creado: " + conductor);
        } catch (Exception e) {
            System.err.println("Error al crear conductor: " + e.getMessage());
        }
    }

    public void listarConductores() {
        try {
            List<Conductor> conductores = conductorService.listarConductores();
            conductores.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Error al listar conductores: " + e.getMessage());
        }
    }

    public void actualizarConductor(Conductor conductor) {
        try {
            conductorService.actualizarConductor(conductor);
            System.out.println("Conductor actualizado: " + conductor);
        } catch (Exception e) {
            System.err.println("Error al actualizar conductor: " + e.getMessage());
        }
    }

    public boolean conductorDisponible(Conductor conductor) {
        return conductorService.estaDisponible(conductor);
    }
}
