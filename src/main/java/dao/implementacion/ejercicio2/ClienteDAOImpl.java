/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.implementacion.ejercicio2;

import config.ConexionBD;
import dao.interfaces.ejercicio2.ClienteDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ejercicio2.Cliente;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes(nombre, cedula, telefono) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getCedula());
            ps.setString(3, cliente.getTelefono());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error insertando cliente: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection con = ConexionBD.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("cedula"),
                        rs.getString("telefono")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error listando clientes: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public boolean existeCedula(String cedula) {
    String sql = "SELECT COUNT(*) FROM clientes WHERE cedula = ?";

        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error consultando cédula");
        }

        return false;
    }

}