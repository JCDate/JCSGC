package Servicios;

import Modelos.Usuarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlUsuarios {

    // CONSULTAS SQL
    final String SELECT_DATOS_USUARIOS_SQL = "SELECT u.id, u.usuario,u.password, u.nombre, u.id_tipo, t.nombre FROM usuarios AS u INNER JOIN tipo_usuario AS t ON u.id_tipo=t.id_tipo WHERE usuario=?";
    final String UPDATE_ULTIMA_SESION_SQL = "UPDATE usuarios SET last_session=? WHERE id= ?";
    final String INSERT_DATOS_USUARIO_SQL = "INSERT INTO usuarios (usuario, password, nombre, id_tipo) VALUES(?,?,?,?)";
    final String SELECT_COUNT_ID_USUARIO_SQL = "SELECT COUNT(id) FROM usuarios WHERE usuario=?";
    private Conexion conexion;

    public SqlUsuarios() {
        this.conexion = Conexion.getInstance(); // Obtener la conexión a la base de datos usando el Singleton
    }

    public boolean login(Usuarios usr) throws ClassNotFoundException {
        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(SELECT_DATOS_USUARIOS_SQL);
            ps.setString(1, usr.getUsuario());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (usr.getPassword().equals(rs.getString(3))) {
                    ps = conexion.conectar().prepareStatement(UPDATE_ULTIMA_SESION_SQL);
                    ps.setString(1, usr.getLast_session());
                    ps.setInt(2, rs.getInt(1));
                    ps.execute();

                    usr.setId(rs.getInt(1));
                    usr.setNombre(rs.getString(4));
                    usr.setId_tipo(rs.getInt(5));
                    usr.setNombre_tipo(rs.getString(6));
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al iniciar sesión: ", ex);
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean registrar(Usuarios usr) {
        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(INSERT_DATOS_USUARIO_SQL);
            ps.setString(1, usr.getUsuario());
            ps.setString(2, usr.getPassword());
            ps.setString(3, usr.getNombre());
            ps.setInt(4, usr.getId_tipo());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al registrar usuario nuevo: ", ex);
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int existeUsuario(String usuarios) throws ClassNotFoundException {
        try {
            PreparedStatement ps = conexion.conectar().prepareStatement(SELECT_COUNT_ID_USUARIO_SQL);
            ps.setString(1, usuarios);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al consultar el id del usuario: ", ex);
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}
