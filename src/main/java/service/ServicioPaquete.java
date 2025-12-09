package service;

import dao.implementacion.PaqueteDAOImpl;
import model.Paquete;
import model.enums.EstadoPaquete;

import java.util.List;
import model.ejercicio5.AuditLogger;

public class ServicioPaquete {

    private PaqueteDAOImpl paqueteDAO;

    public ServicioPaquete() {
        this.paqueteDAO = new PaqueteDAOImpl();
    }

    public void crearPaquete(Paquete paquete) throws Exception {
        paqueteDAO.crearPaquete(paquete);
    }

    public void actualizarPaquete(Paquete paquete) throws Exception {
        // 1. Obtener el estado ACTUAL (antes de la actualización)
        Paquete paqueteAnterior = paqueteDAO.obtenerPaquetePorId(paquete.getIdPaquete());
        EstadoPaquete estadoAnterior = paqueteAnterior.getEstado();

        // 2. Ejecutar la actualización en la base de datos (Llama al DAO)
        paqueteDAO.actualizarPaquete(paquete);

        // 3. Obtener el estado NUEVO después de la actualización
        EstadoPaquete estadoNuevo = paquete.getEstado();

        // 4. CLAVE: Verificar y llamar al logger
        if (!estadoNuevo.equals(estadoAnterior)) {
            AuditLogger.getInstancia().log( 
                String.valueOf(paquete.getIdPaquete()), 
                estadoNuevo.name()                      
            );
        }
    }

    public void eliminarPaquete(int idPaquete) throws Exception {
        paqueteDAO.eliminarPaquete(idPaquete);
    }

    public Paquete obtenerPaquetePorId(int id) throws Exception {
        return paqueteDAO.obtenerPaquetePorId(id);
    }

    public Paquete obtenerPaquetePorTracking(String trackingCode) throws Exception {
        return paqueteDAO.obtenerPaquetePorTracking(trackingCode);
    }

    public List<Paquete> listarPaquetes() throws Exception {
        return paqueteDAO.obtenerTodosPaquetes();
    }
    
    // EJERCICIO 4
    
    public List<Paquete> ListarPaquetesOlvidados(){
        return paqueteDAO.listarPaquetesOlvidados();
    }

    public List<Paquete> listarPaquetesPorEstado(EstadoPaquete estado) throws Exception {
        return paqueteDAO.obtenerPaquetesPorEstado(estado.name());
    }
}
