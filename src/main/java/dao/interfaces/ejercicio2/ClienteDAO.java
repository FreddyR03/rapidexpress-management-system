package dao.interfaces.ejercicio2;

import model.ejercicio2.Cliente;
import java.util.List;

public interface ClienteDAO {
    void insertarCliente(Cliente cliente);
    List<Cliente> listarClientes();
    public boolean existeCedula(String cedula);
}
