package Servicios;

import InspeccionRecibo.EspecificacionesGUI;
import Modelos.CalibreIRM;
import Modelos.InspeccionReciboM;
import Modelos.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JC
 */
public class CalibreIrmServicio {

    public final String DIRECCION_IMG = "img\\jc.png";
    private static final String SQL_INSERCION_CALIBRE_IR = "INSERT INTO calibresir(calibre,medidas,especificacion) VALUES (?,?,?)";
    private static final String SQL_INSERCION_DMP = "INSERT INTO materiaprimasc(calibre,descripcionMP,calibreLamina) VALUES (?,?,?)";
    private static final String SQL_CONSULTA_INSPECCION_RECIBO = "SELECT * FROM inspeccionrecibo";
    private final String SQL_CONSULTA_ESPECIFICACION = "SELECT Especificacion FROM especificacionesir";

    public void agregar(Connection conexion, CalibreIRM cirm) throws SQLException {
        try (PreparedStatement sqlInsert = conexion.prepareStatement(SQL_INSERCION_CALIBRE_IR)) {
            sqlInsert.setString(1, cirm.getCalibre());
            sqlInsert.setString(2, cirm.getMedidas());
            sqlInsert.setString(3, cirm.getEspecificacion());
            sqlInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public void agregarDMP(Connection conexion, CalibreIRM cirm) throws SQLException {
        try (PreparedStatement sqlInsert = conexion.prepareStatement(SQL_INSERCION_CALIBRE_IR)) {
            sqlInsert.setString(1, cirm.getCalibre());
            sqlInsert.setString(2, cirm.getDescripcionMP());
            sqlInsert.setString(3, cirm.getMedidas());
            sqlInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public List<CalibreIRM> recuperarTodas(Connection conexion) throws SQLException {
        List<CalibreIRM> listaCalibreIRM = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SQL_CONSULTA_INSPECCION_RECIBO);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                listaCalibreIRM.add(new CalibreIRM(resultado.getString("calibre"), resultado.getString("medidas"), resultado.getString("especificacion"), ""));
            }
        }
        return listaCalibreIRM;
    }

    public void abrirEspecificacionesGUI(Usuarios usr, InspeccionReciboM irm) throws SQLException, ClassNotFoundException {
        EspecificacionesGUI esGUI = new EspecificacionesGUI(usr, irm); // Se crea una instancia de la interfaz gráfica
        esGUI.setVisible(true); // Se hace visible la ventana
        esGUI.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema 
    }

    public List<String> obtenerEspecificaciones(Connection conexion) throws SQLException {
        // Se realizan las consultas SQL
        List<String> listaEspecificaciones = new ArrayList<>();
        try (PreparedStatement consulta2 = conexion.prepareStatement(SQL_CONSULTA_ESPECIFICACION);
                ResultSet resultado2 = consulta2.executeQuery()) {
            while (resultado2.next()) { // Se guardan los calibres en el comboBox
                listaEspecificaciones.add(resultado2.getString("Especificacion"));
            }
        }
        return listaEspecificaciones;
    }
}
