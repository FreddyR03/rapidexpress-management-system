package dao.implementacion;

import dao.interfaces.ConductorDAO;
import model.Conductor;
import model.enums.EstadoConductor;
import config.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConductorDAOImpl implements ConductorDAO {

    @Override
    public void crearConductor(Conductor conductor) throws Exception {
        String sql = "INSERT INTO conductores (nombre_completo, identificacion, tipo_licencia, numero_contacto, estado) VALUES (?,?,?,?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, conductor.getNombreCompleto());
            ps.setString(2, conductor.getIdentificacion());
            ps.setString(3, conductor.getTipoLicencia());
            ps.setString(4, conductor.getNumeroContacto());
            ps.setString(5, conductor.getEstado().name());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    conductor.setIdConductor(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizarConductor(Conductor conductor) throws Exception {
        String sql = "UPDATE conductores SET nombre_completo=?, identificacion=?, tipo_licencia=?, numero_contacto=?, estado=? WHERE id_conductor=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, conductor.getNombreCompleto());
            ps.setString(2, conductor.getIdentificacion());
            ps.setString(3, conductor.getTipoLicencia());
            ps.setString(4, conductor.getNumeroContacto());
            ps.setString(5, conductor.getEstado().name());
            ps.setInt(6, conductor.getIdConductor());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarConductor(int idConductor) throws Exception {
        String sql = "DELETE FROM conductores WHERE id_conductor=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idConductor);
            ps.executeUpdate();
        }
    }

    @Override
    public Conductor obtenerConductorPorId(int idConductor) throws Exception {
        String sql = "SELECT * FROM conductores WHERE id_conductor=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idConductor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearConductor(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Conductor obtenerConductorPorIdentificacion(String identificacion) throws Exception {
        String sql = "SELECT * FROM conductores WHERE identificacion=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, identificacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearConductor(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Conductor> obtenerTodosConductores() throws Exception {
        List<Conductor> lista = new ArrayList<>();
        String sql = "SELECT * FROM conductores";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearConductor(rs));
            }
        }
        return lista;
    }

    private Conductor mapearConductor(ResultSet rs) throws SQLException {
        return new Conductor(
                rs.getInt("id_conductor"),
                rs.getString("nombre_completo"),
                rs.getString("identificacion"),
                rs.getString("tipo_licencia"),
                rs.getString("numero_contacto"),
                EstadoConductor.valueOf(rs.getString("estado"))
        );
    }
}
