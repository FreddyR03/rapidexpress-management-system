package dao.implementacion;

import dao.interfaces.MantenimientoDAO;
import model.Mantenimiento;
import model.Vehiculo;
import config.ConexionBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoDAOImpl implements MantenimientoDAO {

    @Override
    public void crearMantenimiento(Mantenimiento mantenimiento) throws Exception {
        String sql = "INSERT INTO mantenimientos (id_vehiculo, fecha, descripcion, tipo) VALUES (?,?,?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, mantenimiento.getVehiculo().getIdVehiculo());
            ps.setDate(2, Date.valueOf(mantenimiento.getFecha()));
            ps.setString(3, mantenimiento.getDescripcion());
            ps.setString(4, mantenimiento.getTipo());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    mantenimiento.setIdMantenimiento(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizarMantenimiento(Mantenimiento mantenimiento) throws Exception {
        String sql = "UPDATE mantenimientos SET id_vehiculo=?, fecha=?, descripcion=?, tipo=? WHERE id_mantenimiento=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mantenimiento.getVehiculo().getIdVehiculo());
            ps.setDate(2, Date.valueOf(mantenimiento.getFecha()));
            ps.setString(3, mantenimiento.getDescripcion());
            ps.setString(4, mantenimiento.getTipo());
            ps.setInt(5, mantenimiento.getIdMantenimiento());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarMantenimiento(int idMantenimiento) throws Exception {
        String sql = "DELETE FROM mantenimientos WHERE id_mantenimiento=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMantenimiento);
            ps.executeUpdate();
        }
    }

    @Override
    public Mantenimiento obtenerMantenimientoPorId(int idMantenimiento) throws Exception {
        String sql = "SELECT * FROM mantenimientos WHERE id_mantenimiento=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMantenimiento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearMantenimiento(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Mantenimiento> obtenerMantenimientosPorVehiculo(int idVehiculo) throws Exception {
        List<Mantenimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM mantenimientos WHERE id_vehiculo=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearMantenimiento(rs));
                }
            }
        }
        return lista;
    }

    private Mantenimiento mapearMantenimiento(ResultSet rs) throws Exception {
        Vehiculo vehiculo = new VehiculoDAOImpl().obtenerVehiculoPorId(rs.getInt("id_vehiculo"));
        return new Mantenimiento(
                rs.getInt("id_mantenimiento"),
                vehiculo,
                rs.getDate("fecha").toLocalDate(),
                rs.getString("descripcion"),
                rs.getString("tipo")
        );
    }
}
