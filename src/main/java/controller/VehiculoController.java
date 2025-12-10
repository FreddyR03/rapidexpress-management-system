package controller;

import model.Vehiculo;
import model.enums.EstadoVehiculo;
import service.ServicioVehiculo;

import java.util.List;

public class VehiculoController {

    private ServicioVehiculo vehiculoService;

    public VehiculoController() {
        this.vehiculoService = new ServicioVehiculo();
    }

    public void crearVehiculo(String placa, String marca, String modelo, int anio, double capacidad, EstadoVehiculo estado, String tipoVehiculo) {
        try {
            if(tipoVehiculo.equalsIgnoreCase("motocicleta")){
                if(capacidad > 25){
                    System.out.println("Error se excede de peso la motocicleta");
                }else{
                    Vehiculo vehiculo = new Vehiculo(placa, marca, modelo, anio, capacidad, estado, tipoVehiculo);
                    vehiculoService.crearVehiculo(vehiculo);
                    System.out.println("Vehículo creado: " + vehiculo + " tipo de vehiculo: " + tipoVehiculo);
                }
            }else{
                Vehiculo vehiculo = new Vehiculo(placa, marca, modelo, anio, capacidad, estado, tipoVehiculo);
                vehiculoService.crearVehiculo(vehiculo);
                System.out.println("Vehículo creado: " + vehiculo + " tipo de vehiculo: " + tipoVehiculo);
            }
        } catch (Exception e) {
            System.err.println("Error al crear vehículo: " + e.getMessage());
        }
    }

    public void listarVehiculos() {
        try {
            List<Vehiculo> vehiculos = vehiculoService.listarVehiculos();
            vehiculos.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Error al listar vehículos: " + e.getMessage());
        }
    }

    public void actualizarVehiculo(Vehiculo vehiculo) {
        try {
            if(vehiculo.getTipoVehiculo().equalsIgnoreCase("motocicleta")){
                if(vehiculo.getCapacidadKg() > 25){
                    System.out.println("Error se excede de peso la motocicleta");
                }
            }else{
                vehiculoService.actualizarVehiculo(vehiculo);
                System.out.println("Vehículo actualizado: " + vehiculo);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
        }
    }
    
    public Vehiculo obtenerVehiculoPorId(int idVehiculo) {
    try {
        return vehiculoService.obtenerVehiculoPorId(idVehiculo);
    } catch (Exception e) {
        System.err.println("Error al obtener vehículo: " + e.getMessage());
        return null;
    }
}
}
