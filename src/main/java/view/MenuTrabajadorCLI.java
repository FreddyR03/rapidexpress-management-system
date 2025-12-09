package view;

import controller.*;
import model.*;
import model.enums.*;
import util.CLIUtils;
import view.ejercicio3.CrearRutasCli;



public class MenuTrabajadorCLI {

    private Usuario usuarioActual;
    private VehiculoController vehiculoController;
    private ConductorController conductorController;
    private PaqueteController paqueteController;
    private RutaController rutaController;
    private AuditoriaController auditoriaController;
    private CrearRutasCli crearRutasCli;

    public MenuTrabajadorCLI(Usuario usuario) {
        this.usuarioActual = usuario;
        this.vehiculoController = new VehiculoController();
        this.conductorController = new ConductorController();
        this.paqueteController = new PaqueteController();
        this.rutaController = new RutaController();
        this.auditoriaController = new AuditoriaController();
    }

    public void mostrarMenu() {
        int opcion = -1;
        do {
            System.out.println("\n=== MENU TRABAJADOR ===");
            System.out.println("1. Gestión de Paquetes");
            System.out.println("2. Gestión de Rutas");
            System.out.println("3. Consultar Vehículos");
            System.out.println("4. Consultar Conductores");
            System.out.println("0. Salir");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> menuPaquetes();
                case 2 -> menuRutas();
                case 3 -> vehiculoController.listarVehiculos();
                case 4 -> conductorController.listarConductores();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void menuPaquetes() {
        int opcion = -1;
        do {
            System.out.println("\n--- Gestión de Paquetes ---");
            System.out.println("1. Crear Paquete");
            System.out.println("2. Listar Paquetes");
            System.out.println("3. Listar Paquetes por Estado");
            System.out.println("0. Volver");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> {
                    String descripcion = CLIUtils.leerString("Descripción");
                    double peso = CLIUtils.leerDouble("Peso (Kg)");
                    String dimensiones = CLIUtils.leerString("Dimensiones");
                    String origen = CLIUtils.leerString("Dirección origen");
                    String destino = CLIUtils.leerString("Dirección destino");
                    String remitente = CLIUtils.leerString("Remitente");
                    String destinatario = CLIUtils.leerString("Destinatario");
                    String tracking = CLIUtils.generarTrackingCode();
                    paqueteController.crearPaquete(tracking, descripcion, peso, dimensiones, origen, destino, remitente, destinatario, EstadoPaquete.EN_BODEGA);
                    auditoriaController.registrarAccion(usuarioActual.getUsername(), "CREAR_PAQUETE", "Tracking: " + tracking);
                }
                case 2 -> paqueteController.listarPaquetes();
                case 3 -> {
                    String estadoStr = CLIUtils.leerString("Estado (EN_BODEGA, ASIGNADO_RUTA, EN_TRANSITO, ENTREGADO, DEVUELTO)").toUpperCase();
                    EstadoPaquete estado = EstadoPaquete.valueOf(estadoStr);
                    paqueteController.listarPaquetesPorEstado(estado);
                }
            }
        } while (opcion != 0);
    }

    private void menuRutas() {
        int opcion;
        // EJERCICIO 3 crear ruta
        do {
            System.out.println("\n--- Gestión de Rutas ---");
            System.out.println("1. Crear Ruta");
            System.out.println("2. Iniciar Ruta");
            System.out.println("3. Finalizar Ruta");
            System.out.println("4. Listar Rutas");
            System.out.println("0. Volver");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> crearRutasCli.crearRutaCLI();
                case 2 -> {
                    int id = CLIUtils.leerInt("ID de la ruta a iniciar");
                    Ruta r = rutaController.obtenerRutaPorId(id);
                    if (r != null) {
                        rutaController.iniciarRuta(r);
                        auditoriaController.registrarAccion(usuarioActual.getUsername(), "INICIAR_RUTA", "ID: " + id);
                    }
                }
                case 3 -> {
                    int id = CLIUtils.leerInt("ID de la ruta a finalizar");
                    Ruta r = rutaController.obtenerRutaPorId(id);
                    if (r != null) {
                        rutaController.finalizarRuta(r);
                        auditoriaController.registrarAccion(usuarioActual.getUsername(), "FINALIZAR_RUTA", "ID: " + id);
                    }
                }
                case 4 -> rutaController.listarRutas();
            }
        } while (opcion != 0);
    }
}
