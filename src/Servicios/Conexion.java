package Servicios;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {

    private static final String SERVIDOR = "192.168.1.75";
    private static final String BASE_DE_DATOS = "jc_mysql4";
    private static final String URL = "jdbc:mysql://" + SERVIDOR + ":3306/" + BASE_DE_DATOS + "?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";
    private static Conexion instancia;

    private Conexion() {

    }

    public DataSource obtenerFuenteDeDatos() {
        BasicDataSource datos = new BasicDataSource();
        datos.setUrl(URL); // URL de la base de datos
        datos.setUsername(USUARIO);
        datos.setPassword(PASSWORD);
        datos.setInitialSize(50); // tamaño de conexiones a la base de datos
        
        return datos;
    }

    public Connection conectar() throws SQLException {
        return obtenerFuenteDeDatos().getConnection();
    }

    public void desconectar(Connection conexion) {
        try {
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarResultSet(ResultSet resultado) {
        try {
            resultado.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrarStatement(PreparedStatement statement) {
        try {
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Conexion getInstance() {
        if (instancia == null) {
            return instancia = new Conexion();
        }
        return instancia;
    }
}
