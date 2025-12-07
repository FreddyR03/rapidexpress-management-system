package view;

import controller.*;
import model.Conductor;
import model.Mantenimiento;
import model.Usuario;
import model.Vehiculo;
import model.enums.EstadoConductor;
import model.enums.EstadoVehiculo;
import model.enums.RolUsuario;
import model.enums.EstadoPaquete;
import model.Ruta;
import util.CLIUtils;

import java.time.LocalDate;

public class MenuAdministradorCLI {

    private UsuarioController usuarioController;
    private VehiculoController vehiculoController;
    private ConductorController conductorController;
    private PaqueteController paqueteController;
    private RutaController rutaController;
    private MantenimientoController mantenimientoController;
    private AuditoriaController auditoriaController;

    private Usuario usuarioActual;

    public MenuAdministradorCLI(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
        this.usuarioController = new UsuarioController();
        this.vehiculoController = new VehiculoController();
        this.conductorController = new ConductorController();
        this.paqueteController = new PaqueteController();
        this.rutaController = new RutaController();
        this.mantenimientoController = new MantenimientoController();
        this.auditoriaController = new AuditoriaController();
    }

    public void iniciar() {
        int opcion = -1;
        do {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Gestión de Usuarios");
            System.out.println("2. Gestión de Vehículos");
            System.out.println("3. Gestión de Conductores");
            System.out.println("4. Gestión de Paquetes");
            System.out.println("5. Gestión de Rutas");
            System.out.println("6. Gestión de Mantenimientos");
            System.out.println("7. Auditoría");
            System.out.println("8. Simular costo de envios");
            System.out.println("0. Salir");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> menuUsuarios();
                case 2 -> menuVehiculos();
                case 3 -> menuConductores();
                case 4 -> menuPaquetes();
                case 5 -> menuRutas();
                case 6 -> menuMantenimientos();
                case 7 -> menuAuditoria();
                case 8 -> SimuladorCli.simular();
            }

        } while (opcion != 0);
    }

    private void menuUsuarios() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Usuarios ---");
            System.out.println("1. Crear Usuario");
            System.out.println("2. Listar Usuarios");
            System.out.println("3. Actualizar Usuario");
            System.out.println("4. Eliminar Usuario");
            System.out.println("0. Volver");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> {
                    String username = CLIUtils.leerString("Username");
                    String password = CLIUtils.leerString("Password");
                    String rolStr = CLIUtils.leerString("Rol (ADMIN, TRABAJADOR)").toUpperCase();
                    RolUsuario rol = RolUsuario.valueOf(rolStr);
                    usuarioController.registrarUsuario(new Usuario(0, username, password, rol));
                    auditoriaController.registrarAccion(usuarioActual.getUsername(), "CREAR_USUARIO", "Usuario: " + username);
                }
                case 2 -> usuarioController.listarUsuarios();
                case 3 -> {
                    usuarioController.listarUsuarios();
                    int id = CLIUtils.leerInt("Ingrese ID del usuario a actualizar");
                    String username = CLIUtils.leerString("Nuevo username");
                    String password = CLIUtils.leerString("Nueva contraseña");
                    String rolStr = CLIUtils.leerString("Nuevo rol (ADMIN, TRABAJADOR)").toUpperCase();
                    RolUsuario rol = RolUsuario.valueOf(rolStr);
                    usuarioController.actualizarUsuario(new Usuario(id, username, password, rol));
                    auditoriaController.registrarAccion(usuarioActual.getUsername(), "ACTUALIZAR_USUARIO", "ID: " + id);
                }
                case 4 -> {
                    usuarioController.listarUsuarios();
                    int id = CLIUtils.leerInt("Ingrese ID del usuario a eliminar");
                    usuarioController.eliminarUsuario(id);
                    auditoriaController.registrarAccion(usuarioActual.getUsername(), "ELIMINAR_USUARIO", "ID: " + id);
                }
            }

        } while (opcion != 0);
    }

    private void menuVehiculos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Vehículos ---");
            System.out.println("1. Crear Vehículo");
            System.out.println("2. Listar Vehículos");
            System.out.println("3. Actualizar Vehículo");
            System.out.println("0. Volver");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> {
                    String placa = CLIUtils.leerString("Placa");
                    String marca = CLIUtils.leerString("Marca");
                    String modelo = CLIUtils.leerString("Modelo");
                    int anio = CLIUtils.leerInt("Año de fabricación");
                    double capacidad = CLIUtils.leerDouble("Capacidad (Kg)");
                    String estadoStr = CLIUtils.leerString("Estado (DISPONIBLE, EN_RUTA, EN_MANTENIMIENTO)").toUpperCase();
                    EstadoVehiculo estado = EstadoVehiculo.valueOf(estadoStr);
                    vehiculoController.crearVehiculo(placa, marca, modelo, anio, capacidad, estado);
                    auditoriaController.registrarAccion(usuarioActual.getUsername(), "CREAR_VEHICULO", "Placa: " + placa);
                }
                case 2 -> vehiculoController.listarVehiculos();
                case 3 -> {
                    vehiculoController.listarVehiculos();
                    int id = CLIUtils.leerInt("ID del vehículo a actualizar");
                    String placa = CLIUtils.leerString("Nueva placa");
                    String marca = CLIUtils.leerString("Nueva marca");
                    String modelo = CLIUtils.leerString("Nuevo modelo");
                    int anio = CLIUtils.leerInt("Nuevo año");
                    double capacidad = CLIUtils.leerDouble("Nueva capacidad");
                    String estadoStr = CLIUtils.leerString("Nuevo estado (DISPONIBLE, EN_RUTA, EN_MANTENIMIENTO)").toUpperCase();
                    EstadoVehiculo estado = EstadoVehiculo.valueOf(estadoStr);
                    vehiculoController.actualizarVehiculo(new Vehiculo(id, placa, marca, modelo, anio, capacidad, estado));
                    auditoriaController.registrarAccion(usuarioActual.getUsername(), "ACTUALIZAR_VEHICULO", "ID: " + id);
                }
            }
        } while (opcion != 0);
    }

    private void menuConductores() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Conductores ---");
            System.out.println("1. Crear Conductor");
            System.out.println("2. Listar Conductores");
            System.out.println("3. Actualizar Conductor");
            System.out.println("0. Volver");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> {
                    String nombre = CLIUtils.leerString("Nombre completo");
                    String identificacion = CLIUtils.leerString("Identificación");
                    String tipoLicencia = CLIUtils.leerString("Tipo de licencia");
                    String contacto = CLIUtils.leerString("Contacto");
                    String estadoStr = CLIUtils.leerString("Estado (ACTIVO, DE_VACACIONES, INACTIVO)").toUpperCase();
                    EstadoConductor estado = EstadoConductor.valueOf(estadoStr);
                    conductorController.crearConductor(nombre, identificacion, tipoLicencia, contacto, estado);
                    auditoriaController.registrarAccion(usuarioActual.getUsername(), "CREAR_CONDUCTOR", "Identificación: " + identificacion);
                }
                case 2 -> conductorController.listarConductores();
                case 3 -> {
                    conductorController.listarConductores();
                    int id = CLIUtils.leerInt("ID del conductor a actualizar");
                    String nombre = CLIUtils.leerString("Nuevo nombre");
                    String identificacion = CLIUtils.leerString("Nueva identificación");
                    String tipoLicencia = CLIUtils.leerString("Nuevo tipo de licencia");
                    String contacto = CLIUtils.leerString("Nuevo contacto");
                    String estadoStr = CLIUtils.leerString("Nuevo estado (ACTIVO, DE_VACACIONES, INACTIVO)").toUpperCase();
                    EstadoConductor estado = EstadoConductor.valueOf(estadoStr);
                    conductorController.actualizarConductor(new Conductor(id, nombre, identificacion, tipoLicencia, contacto, estado));
                    auditoriaController.registrarAccion(usuarioActual.getUsername(), "ACTUALIZAR_CONDUCTOR", "ID: " + id);
                }
            }
        } while (opcion != 0);
    }

    private void menuPaquetes() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Paquetes ---");
            System.out.println("1. Crear Paquete");
            System.out.println("2. Listar Paquetes");
            System.out.println("3. Listar por Estado");
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
                    String estadoStr = CLIUtils.leerString("Estado (EN_BODEGA, ASIGNADO_A_RUTA, EN_TRANSITO, ENTREGADO, DEVUELTO)").toUpperCase();
                    EstadoPaquete estado = EstadoPaquete.valueOf(estadoStr);
                    paqueteController.listarPaquetesPorEstado(estado);
                }
            }
        } while (opcion != 0);
    }

    private void menuRutas() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Rutas ---");
            System.out.println("1. Iniciar Ruta");
            System.out.println("2. Finalizar Ruta");
            System.out.println("3. Listar Rutas");
            System.out.println("0. Volver");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> {
                    int id = CLIUtils.leerInt("ID de la ruta a iniciar");
                    Ruta r = rutaController.obtenerRutaPorId(id);
                    if (r != null) {
                        rutaController.iniciarRuta(r);
                        auditoriaController.registrarAccion(usuarioActual.getUsername(), "INICIAR_RUTA", "ID: " + id);
                    }
                }
                case 2 -> {
                    int id = CLIUtils.leerInt("ID de la ruta a finalizar");
                    Ruta r = rutaController.obtenerRutaPorId(id);
                    if (r != null) {
                        rutaController.finalizarRuta(r);
                        auditoriaController.registrarAccion(usuarioActual.getUsername(), "FINALIZAR_RUTA", "ID: " + id);
                    }
                }
                case 3 -> rutaController.listarRutas();
            }
        } while (opcion != 0);
    }

    private void menuMantenimientos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Mantenimientos ---");
            System.out.println("1. Registrar Mantenimiento");
            System.out.println("2. Listar Mantenimientos por Vehículo");
            System.out.println("3. Actualizar Mantenimiento");
            System.out.println("4. Eliminar Mantenimiento");
            System.out.println("0. Volver");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> {
                    int vehiculoId = CLIUtils.leerInt("ID del vehículo");
                    Vehiculo vehiculo = vehiculoController.obtenerVehiculoPorId(vehiculoId);
                    if (vehiculo != null) {
                        String detalle = CLIUtils.leerString("Detalle del mantenimiento");
                        String tipo = CLIUtils.leerString("Tipo de mantenimiento");
                        mantenimientoController.registrarMantenimiento(new Mantenimiento(vehiculo, LocalDate.now(), detalle, tipo));
                        auditoriaController.registrarAccion(usuarioActual.getUsername(), "REGISTRAR_MANTENIMIENTO", "Vehículo: " + vehiculo.getPlaca());
                    } else {
                        System.out.println("Vehículo no encontrado");
                    }
                }
                case 2 -> {
                    int vehiculoId = CLIUtils.leerInt("ID del vehículo");
                    mantenimientoController.listarMantenimientosPorVehiculo(vehiculoId);
                }
                case 3 -> {
                    int id = CLIUtils.leerInt("ID del mantenimiento a actualizar");
                    int vehiculoId = CLIUtils.leerInt("Nuevo ID del vehículo");
                    Vehiculo vehiculo = vehiculoController.obtenerVehiculoPorId(vehiculoId);
                    if (vehiculo != null) {
                        String detalle = CLIUtils.leerString("Nuevo detalle");
                        String tipo = CLIUtils.leerString("Nuevo tipo");
                        mantenimientoController.actualizarMantenimiento(new Mantenimiento(id, vehiculo, LocalDate.now(), detalle, tipo));
                        auditoriaController.registrarAccion(usuarioActual.getUsername(), "ACTUALIZAR_MANTENIMIENTO", "ID: " + id);
                    } else {
                        System.out.println("Vehículo no encontrado");
                    }
                }
                case 4 -> {
                    int id = CLIUtils.leerInt("ID del mantenimiento a eliminar");
                    mantenimientoController.eliminarMantenimiento(id);
                    auditoriaController.registrarAccion(usuarioActual.getUsername(), "ELIMINAR_MANTENIMIENTO", "ID: " + id);
                }
            }
        } while (opcion != 0);
    }

    private void menuAuditoria() {
        int opcion;
        do {
            System.out.println("\n--- Auditoría ---");
            System.out.println("1. Listar todas las acciones");
            System.out.println("2. Listar acciones por usuario");
            System.out.println("0. Volver");
            opcion = CLIUtils.leerInt("Seleccione una opción");

            switch (opcion) {
                case 1 -> auditoriaController.listarTodas();
                case 2 -> {
                    String user = CLIUtils.leerString("Usuario");
                    auditoriaController.listarPorUsuario(user);
                }
            }
        } while (opcion != 0);
    }
}
