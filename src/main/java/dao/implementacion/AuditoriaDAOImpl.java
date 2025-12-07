package dao.implementacion;

import dao.interfaces.AuditoriaDAO;
import model.Auditoria;
import config.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaDAOImpl implements AuditoriaDAO {

    @Override
    public void registrarAccion(Auditoria auditoria) throws Exception {
        String sql = "INSERT INTO auditoria (usuario, accion, detalles, fecha_hora) VALUES (?,?,?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, auditoria.getUsuario());
            ps.setString(2, auditoria.getAccion());
            ps.setString(3, auditoria.getDetalles());
            ps.setTimestamp(4, auditoria.getFechaHora() != null ? Timestamp.valueOf(auditoria.getFechaHora()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    auditoria.setIdAuditoria(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public List<Auditoria> obtenerTodas() throws Exception {
        List<Auditoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM auditoria ORDER BY fecha_hora DESC";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearAuditoria(rs));
            }
        }
        return lista;
    }

    @Override
    public List<Auditoria> obtenerPorUsuario(String usuario) throws Exception {
        List<Auditoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM auditoria WHERE usuario=? ORDER BY fecha_hora DESC";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearAuditoria(rs));
                }
            }
        }
        return lista;
    }

    private Auditoria mapearAuditoria(ResultSet rs) throws Exception {
        return new Auditoria(
                rs.getInt("id_auditoria"),
                rs.getString("usuario"),
                rs.getString("accion"),
                rs.getString("detalles"),
                rs.getTimestamp("fecha_hora").toLocalDateTime()
        );
    }
}
