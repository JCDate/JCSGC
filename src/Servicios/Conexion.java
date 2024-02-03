package Servicios;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {

    // Atributos
    private static Conexion instancia;
    private Connection connection;

    // Constructor
    private Conexion() { // Se realiza la conexión con la información de la base de datos
        try {
            String SERVER = "192.168.1.75";
            String nombreBD = "jc_mysql2";
            String url = "jdbc:mysql://" + SERVER + ":3306/" + nombreBD + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
            String usuario = "root";
            String contraseña = "";
            connection = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Hubo un error al conectar a la base de datos");
        }
    }

    public Connection getConnection() { // Método que obtiene la conexión
        return connection;
    }

    public static synchronized Conexion getInstance() { // Método estático para obtener una conexión única
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public void cerrarConexion() { // Método que cierra la conexión
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + ex);
        }
    }
}
