package service;

import dao.implementacion.RutaDAOImpl;
import java.time.LocalDate;
import model.Paquete;
import model.Ruta;
import model.enums.EstadoRuta;
import model.enums.EstadoVehiculo;
import model.enums.EstadoConductor;
import model.enums.EstadoPaquete;

import java.util.List;
import model.Conductor;

public class ServicioRuta {

    private RutaDAOImpl rutaDAO;
    private ServicioVehiculo vehiculoService;
    private ServicioConductor conductorService;
    private ServicioPaquete paqueteService;

    public ServicioRuta() {
        this.rutaDAO = new RutaDAOImpl();
        this.vehiculoService = new ServicioVehiculo();
        this.conductorService = new ServicioConductor();
        this.paqueteService = new ServicioPaquete();
    }
    public boolean crearRuta(int idConductor, Ruta ruta) throws Exception {
        
        // EJERCICIO 3
        Conductor conductor = conductorService.obtenerConductorPorId(idConductor);
        
        if(conductor == null){
            System.out.println("Error el conductor no existe");
            return false;
        }
        
        // Validar la fecha de vencimiento de la licencia del conductor
        if(conductor.getFechaVecimientoLicencia().isBefore(LocalDate.now())){
            System.out.println("ALERTA: El conductor " 
                + conductor.getNombreCompleto() +
                " tiene la licencia vencida. No se puede asignar.");
            return false;
        }
        
        // Validar capacidad del vehículo
        double totalPeso = ruta.getEnvios().stream().mapToDouble(Paquete::getPeso).sum();
        if (totalPeso > ruta.getVehiculo().getCapacidadKg()) {
            throw new Exception("El peso total excede la capacidad del vehículo");
        }
        
        ruta.setConductor(conductor);
        ruta.setTotalPesoKg(totalPeso);
        ruta.setEstado(EstadoRuta.PENDIENTE);
        rutaDAO.crearRuta(ruta);
        return true;
    }

    public void iniciarRuta(Ruta ruta) throws Exception {
        // Cambiar estados
        ruta.getVehiculo().setEstado(EstadoVehiculo.EN_RUTA);
        ruta.getConductor().setEstado(EstadoConductor.ACTIVO); // sigue activo pero en ruta
        for (Paquete p : ruta.getEnvios()) {
            p.setEstado(EstadoPaquete.EN_TRANSITO);
            paqueteService.actualizarPaquete(p);
        }
        ruta.setEstado(EstadoRuta.EN_RUTA);
        rutaDAO.actualizarRuta(ruta);
        vehiculoService.actualizarVehiculo(ruta.getVehiculo());
        conductorService.actualizarConductor(ruta.getConductor());
    }

    public void finalizarRuta(Ruta ruta) throws Exception {
        for (Paquete p : ruta.getEnvios()) {
            p.setEstado(EstadoPaquete.ENTREGADO);
            paqueteService.actualizarPaquete(p);
        }
        ruta.getVehiculo().setEstado(EstadoVehiculo.DISPONIBLE);
        ruta.getConductor().setEstado(EstadoConductor.ACTIVO);
        ruta.setEstado(EstadoRuta.FINALIZADA);
        rutaDAO.actualizarRuta(ruta);
        vehiculoService.actualizarVehiculo(ruta.getVehiculo());
        conductorService.actualizarConductor(ruta.getConductor());
    }

    public List<Ruta> listarRutas() throws Exception {
        return rutaDAO.obtenerTodasRutas();
    }

    public Ruta obtenerRutaPorId(int id) throws Exception {
        return rutaDAO.obtenerRutaPorId(id);
    }
}
