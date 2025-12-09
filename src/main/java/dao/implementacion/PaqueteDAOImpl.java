package dao.implementacion;

import dao.interfaces.PaqueteDAO;
import model.Paquete;
import model.enums.EstadoPaquete;
import config.ConexionBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaqueteDAOImpl implements PaqueteDAO {

    @Override
    public void crearPaquete(Paquete paquete) throws Exception {
        String sql = "INSERT INTO paquetes (tracking_code, descripcion, peso, dimensiones, direccion_origen, direccion_destino, remitente, destinatario, estado, fecha_ingreso) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, paquete.getTrackingCode());
            ps.setString(2, paquete.getDescripcion());
            ps.setDouble(3, paquete.getPeso());
            ps.setString(4, paquete.getDimensiones());
            ps.setString(5, paquete.getDireccionOrigen());
            ps.setString(6, paquete.getDireccionDestino());
            ps.setString(7, paquete.getRemitente());
            ps.setString(8, paquete.getDestinatario());
            ps.setString(9, paquete.getEstado().name());
            ps.setDate(10, Date.valueOf(paquete.getFechaIngreso()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    paquete.setIdPaquete(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizarPaquete(Paquete paquete) throws Exception {
        String sql = "UPDATE paquetes SET tracking_code=?, descripcion=?, peso=?, dimensiones=?, direccion_origen=?, direccion_destino=?, remitente=?, destinatario=?, estado=?, fecha_ingreso=? WHERE id_paquete=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, paquete.getTrackingCode());
            ps.setString(2, paquete.getDescripcion());
            ps.setDouble(3, paquete.getPeso());
            ps.setString(4, paquete.getDimensiones());
            ps.setString(5, paquete.getDireccionOrigen());
            ps.setString(6, paquete.getDireccionDestino());
            ps.setString(7, paquete.getRemitente());
            ps.setString(8, paquete.getDestinatario());
            ps.setString(9, paquete.getEstado().name());
            ps.setDate(10, Date.valueOf(paquete.getFechaIngreso()));
            ps.setInt(11, paquete.getIdPaquete());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarPaquete(int idPaquete) throws Exception {
        String sql = "DELETE FROM paquetes WHERE id_paquete=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPaquete);
            ps.executeUpdate();
        }
    }

    @Override
    public Paquete obtenerPaquetePorId(int idPaquete) throws Exception {
        String sql = "SELECT * FROM paquetes WHERE id_paquete=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPaquete);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPaquete(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Paquete obtenerPaquetePorTracking(String trackingCode) throws Exception {
        String sql = "SELECT * FROM paquetes WHERE tracking_code=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trackingCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPaquete(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Paquete> obtenerTodosPaquetes() throws Exception {
        List<Paquete> lista = new ArrayList<>();
        String sql = "SELECT * FROM paquetes";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearPaquete(rs));
            }
        }
        return lista;
    }

    @Override
    public List<Paquete> obtenerPaquetesPorEstado(String estado) throws Exception {
        List<Paquete> lista = new ArrayList<>();
        String sql = "SELECT * FROM paquetes WHERE estado=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPaquete(rs));
                }
            }
        }
        return lista;
    }

    // EJERCICIO 4
    
    public List<Paquete> listarPaquetesOlvidados(){
        List<Paquete> listaOlvidados = new ArrayList<>();
        String sql = "SELECT id_paquete ,tracking_code, descripcion, peso, dimensiones, direccion_origen, direccion_destino, remitente, destinatario, estado, fecha_ingreso, DATEDIFF(CURDATE(), fecha_ingreso) AS dias_retraso FROM paquetes WHERE estado = ? AND DATEDIFF(CURDATE(), fecha_ingreso) > ?";        
        try(Connection con = ConexionBD.getConnection();
                PreparedStatement pr = con.prepareStatement(sql)){
            pr.setString(1, "EN_BODEGA");
            pr.setInt(2, 5);
            try (ResultSet rs = pr.executeQuery()) {
                while (rs.next()) {
                    listaOlvidados.add(mapearPaquete(rs));
                }
            }
        }catch(SQLException e){
            System.out.println("PaqueteDAO.ListarPaquetesOlvidados. ERROR SQL: " + e.getMessage());
        }
        return listaOlvidados;
    }
    
    private Paquete mapearPaquete(ResultSet rs) throws SQLException {
        return new Paquete(
                rs.getInt("id_paquete"),
                rs.getString("tracking_code"),
                rs.getString("descripcion"),
                rs.getDouble("peso"),
                rs.getString("dimensiones"),
                rs.getString("direccion_origen"),
                rs.getString("direccion_destino"),
                rs.getString("remitente"),
                rs.getString("destinatario"),
                EstadoPaquete.valueOf(rs.getString("estado")),
                rs.getDate("fecha_ingreso").toLocalDate()
        );
    }
    
   
}
