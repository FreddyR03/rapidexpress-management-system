package dao.implementacion;

import dao.interfaces.UsuarioDAO;
import model.Usuario;
import model.enums.RolUsuario;
import config.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void crearUsuario(Usuario usuario) throws Exception {
        String sql = "INSERT INTO usuarios (username, password, rol) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword()); // Nota: en producción se debe hashear
            ps.setString(3, usuario.getRol().name());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void actualizarUsuario(Usuario usuario) throws Exception {
        String sql = "UPDATE usuarios SET username=?, password=?, rol=? WHERE id_usuario=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol().name());
            ps.setInt(4, usuario.getIdUsuario());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminarUsuario(int idUsuario) throws Exception {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        }
    }

    @Override
    public Usuario obtenerUsuarioPorId(int idUsuario) throws Exception {
        String sql = "SELECT * FROM usuarios WHERE id_usuario=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Usuario login(String username, String password) throws Exception {
        String sql = "SELECT * FROM usuarios WHERE username=? AND password=?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Usuario> obtenerTodos() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        }
        return lista;
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("username"),
                rs.getString("password"),
                RolUsuario.valueOf(rs.getString("rol"))
        );
    }
}
