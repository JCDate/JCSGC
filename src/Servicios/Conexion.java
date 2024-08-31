package Servicios;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {

    private static Conexion instancia;
    private Connection conexion;

    private Conexion() {
        try {
            String SERVER = "192.168.1.75";
            String BASE_DATOS = "jc_mysql2";
            String url = "jdbc:mysql://" + SERVER + ":3306/" + BASE_DATOS + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
            String usuario = "root";
            String contraseña = "";
            conexion = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de conexión a la BD: " + ex);
        }
    }

    public Connection getConnection() {
        return conexion;
    }

    public static synchronized Conexion getInstance() { // Método estático para obtener una conexión única
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex);
        }
    }
}
