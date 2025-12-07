package dao.implementacion;

import dao.interfaces.RutaDAO;
import model.Conductor;
import model.Paquete;
import model.Ruta;
import model.Vehiculo;
import model.enums.EstadoRuta;
import config.ConexionBD;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RutaDAOImpl implements RutaDAO {

    @Override
    public void crearRuta(Ruta ruta) throws Exception {
        String sql = "INSERT INTO rutas (fecha, id_vehiculo, id_conductor, total_peso_kg, estado) VALUES (?,?,?,?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(ruta.getFecha()));
            ps.setInt(2, ruta.getVehiculo().getIdVehiculo());
            ps.setInt(3, ruta.getConductor().getIdConductor());
            ps.setDouble(4, ruta.getTotalPesoKg());
            ps.setString(5, ruta.getEstado().name());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ruta.setIdRuta(rs.getInt(1));
                }
            }

            // Guardar paquetes asociados en ruta_envio
            if (ruta.getEnvios() != null) {
                for (Paquete p : ruta.getEnvios()) {
                    asignarPaqueteARuta(ruta.getIdRuta(), p.getIdPaquete());
                }
            }
        }
    }

    private void asignarPaqueteARuta(int idRuta, int idPaquete) throws SQLException {
        String sql = "INSERT INTO ruta_envio (id_ruta, id_paquete) VALUES (?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idRuta);
            ps.setInt(2, idPaquete);
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizarRuta(Ruta ruta) throws Exception {
        String sql = "UPDATE rutas SET fecha=?, id_vehiculo=?, id_conductor=?, total_peso_kg=?, estado=? WHERE id_ruta=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(ruta.getFecha()));
            ps.setInt(2, ruta.getVehiculo().getIdVehiculo());
            ps.setInt(3, ruta.getConductor().getIdConductor());
            ps.setDouble(4, ruta.getTotalPesoKg());
            ps.setString(5, ruta.getEstado().name());
            ps.setInt(6, ruta.getIdRuta());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarRuta(int idRuta) throws Exception {
        String sql1 = "DELETE FROM ruta_envio WHERE id_ruta=?";
        String sql2 = "DELETE FROM rutas WHERE id_ruta=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps1 = conn.prepareStatement(sql1);
             PreparedStatement ps2 = conn.prepareStatement(sql2)) {
            ps1.setInt(1, idRuta);
            ps1.executeUpdate();

            ps2.setInt(1, idRuta);
            ps2.executeUpdate();
        }
    }

    @Override
    public Ruta obtenerRutaPorId(int idRuta) throws Exception {
        String sql = "SELECT * FROM rutas WHERE id_ruta=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idRuta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearRuta(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Ruta> obtenerTodasRutas() throws Exception {
        List<Ruta> lista = new ArrayList<>();
        String sql = "SELECT * FROM rutas";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearRuta(rs));
            }
        }
        return lista;
    }

    private Ruta mapearRuta(ResultSet rs) throws Exception {
        int idRuta = rs.getInt("id_ruta");
        LocalDate fecha = rs.getDate("fecha").toLocalDate();
        int idVehiculo = rs.getInt("id_vehiculo");
        int idConductor = rs.getInt("id_conductor");
        double totalPeso = rs.getDouble("total_peso_kg");
        EstadoRuta estado = EstadoRuta.valueOf(rs.getString("estado"));

        // Obtener Vehiculo
        Vehiculo vehiculo = new VehiculoDAOImpl().obtenerVehiculoPorId(idVehiculo);
        // Obtener Conductor
        Conductor conductor = new dao.implementacion.ConductorDAOImpl().obtenerConductorPorId(idConductor);
        // Obtener paquetes asignados
        List<Paquete> paquetes = obtenerPaquetesPorRuta(idRuta);

        return new Ruta(idRuta, fecha, vehiculo, conductor, totalPeso, estado, paquetes);
    }

    private List<Paquete> obtenerPaquetesPorRuta(int idRuta) throws Exception {
        List<Paquete> lista = new ArrayList<>();
        String sql = "SELECT p.* FROM paquetes p JOIN ruta_envio re ON p.id_paquete=re.id_paquete WHERE re.id_ruta=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idRuta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new dao.implementacion.PaqueteDAOImpl().obtenerPaquetePorId(rs.getInt("id_paquete")));
                }
            }
        }
        return lista;
    }
}
