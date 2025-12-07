package dao.implementacion;

import dao.interfaces.VehiculoDAO;
import model.Vehiculo;
import model.enums.EstadoVehiculo;
import config.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAOImpl implements VehiculoDAO {

    @Override
    public void crearVehiculo(Vehiculo vehiculo) throws Exception {
        String sql = "INSERT INTO vehiculos (placa, marca, modelo, anio_fabricacion, capacidad_kg, estado) VALUES (?,?,?,?,?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getModelo());
            ps.setInt(4, vehiculo.getAnioFabricacion());
            ps.setDouble(5, vehiculo.getCapacidadKg());
            ps.setString(6, vehiculo.getEstado().name());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    vehiculo.setIdVehiculo(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizarVehiculo(Vehiculo vehiculo) throws Exception {
        String sql = "UPDATE vehiculos SET placa=?, marca=?, modelo=?, anio_fabricacion=?, capacidad_kg=?, estado=? WHERE id_vehiculo=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getMarca());
            ps.setString(3, vehiculo.getModelo());
            ps.setInt(4, vehiculo.getAnioFabricacion());
            ps.setDouble(5, vehiculo.getCapacidadKg());
            ps.setString(6, vehiculo.getEstado().name());
            ps.setInt(7, vehiculo.getIdVehiculo());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarVehiculo(int idVehiculo) throws Exception {
        String sql = "DELETE FROM vehiculos WHERE id_vehiculo=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            ps.executeUpdate();
        }
    }

    @Override
    public Vehiculo obtenerVehiculoPorId(int idVehiculo) throws Exception {
        String sql = "SELECT * FROM vehiculos WHERE id_vehiculo=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearVehiculo(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Vehiculo> obtenerTodosVehiculos() throws Exception {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearVehiculo(rs));
            }
        }
        return lista;
    }

    private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
        return new Vehiculo(
                rs.getInt("id_vehiculo"),
                rs.getString("placa"),
                rs.getString("marca"),
                rs.getString("modelo"),
                rs.getInt("anio_fabricacion"),
                rs.getDouble("capacidad_kg"),
                EstadoVehiculo.valueOf(rs.getString("estado"))
        );
    }
}
