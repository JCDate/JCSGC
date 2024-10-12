package Servicios;

import Modelos.ComposicionQuimicaM;
import Modelos.DatosIRM;
import Modelos.PropiedadMecanicaM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class EspecificacionesServicio {

    final String SQL_INSERCION_DATOS_IR = "INSERT INTO datosir(fechaInspeccion, idAnchoLargo, observaciones, obsMP, noHoja, idIR) VALUES (?, ?, ?, ?, ?, ?)";
    final String SQL_CONSULTA_PROPIEDADES_MC = "SELECT pm, valor FROM propiedadesmc WHERE especificacionTecnica = ?";
    final String SQL_CONSULTA_COMPOSICION_QM = "SELECT cq, valor FROM composicionquimica WHERE especificacionTecnica = ?";

    public void agregarDatosIR(Connection conexion, DatosIRM dirm, int id) throws SQLException {
        try (PreparedStatement sqlInsert = conexion.prepareStatement(SQL_INSERCION_DATOS_IR)) {
            sqlInsert.setString(1, dirm.getFechaInspeccion());
            sqlInsert.setInt(2, dirm.getAnchoLargo());
            sqlInsert.setString(3, dirm.getObsMP());
            sqlInsert.setString(4, dirm.getObservacionesRD());
            sqlInsert.setString(5, dirm.getNoHoja());
            sqlInsert.setInt(6, id);
            sqlInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    public List<PropiedadMecanicaM> obtenerPropiedadesMecanicas(Connection conexion, String espeTecnica) throws SQLException {
        List<PropiedadMecanicaM> listaIr = new ArrayList<>(); // Se crea una nueva lista 
        PreparedStatement consulta = conexion.prepareStatement(SQL_CONSULTA_PROPIEDADES_MC);
        consulta.setString(1, espeTecnica);
        ResultSet resultado = consulta.executeQuery();
        while (resultado.next()) {
            String pm = resultado.getString("pm");
            String valor = resultado.getString("valor");
            PropiedadMecanicaM propiedadMecanica = new PropiedadMecanicaM(espeTecnica, pm, valor);
            listaIr.add(propiedadMecanica); 
        }
        return listaIr;
    }

    public List<ComposicionQuimicaM> obtenerCM(Connection conexion, String espeTecnica) throws SQLException {
        List<ComposicionQuimicaM> listaIr = new ArrayList<>(); // Se crea una nueva lista 
        PreparedStatement consulta = conexion.prepareStatement(SQL_CONSULTA_COMPOSICION_QM);
        consulta.setString(1, espeTecnica);
        ResultSet resultado = consulta.executeQuery();
        while (resultado.next()) { // Se almacena la información encontrada
            String cq = resultado.getString("cq");
            String valor = resultado.getString("valor");
            ComposicionQuimicaM composicionQuimica = new ComposicionQuimicaM(espeTecnica, cq, valor);
            listaIr.add(composicionQuimica); 
        }
        return listaIr;
    }

    public void agregarFilasTabla(DefaultTableModel tableModel, int size) {
        int numCeldas = size;
        int numFilas = tableModel.getRowCount();
        if (numFilas < numCeldas) {
            for (int i = numFilas; i < numCeldas; i++) {
                tableModel.addRow(new Object[tableModel.getColumnCount()]);
            }
        }
    }
}
