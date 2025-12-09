package service;

import dao.implementacion.PaqueteDAOImpl;
import model.Paquete;
import model.enums.EstadoPaquete;

import java.util.List;

public class ServicioPaquete {

    private PaqueteDAOImpl paqueteDAO;

    public ServicioPaquete() {
        this.paqueteDAO = new PaqueteDAOImpl();
    }

    public void crearPaquete(Paquete paquete) throws Exception {
        paqueteDAO.crearPaquete(paquete);
    }

    public void actualizarPaquete(Paquete paquete) throws Exception {
        paqueteDAO.actualizarPaquete(paquete);
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
