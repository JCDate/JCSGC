/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.ComposicionQuimicaM;
import Modelos.DatosIRM;
import Modelos.PropiedadMecanicaM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JC
 */
public class EspecificacionesServicio {

    private final String tabla = "datosir";
    public String direcciomImg = "img\\jc.png";
    final String SQL_INSERCION_DATOS_IR = "INSERT INTO " + tabla + "(fechaInspeccion,idAnchoLargo,observaciones,obsMP,noHoja,idIR) VALUES (?,?,?,?,?,?)";
    final String SQL_CONSULTA_PROPIEDADES_MC = "SELECT pm,valor FROM propiedadesmc WHERE especificacionTecnica=?";
    final String SQL_CONSULTA_COMPOSICION_QM = "SELECT cq,valor FROM composicionquimica WHERE especificacionTecnica=?";

    public void agregarDatosIR(Conexion conexion, DatosIRM dirm, int id) throws SQLException {
        try (PreparedStatement sqlInsert = conexion.conectar().prepareStatement(SQL_INSERCION_DATOS_IR)) { // Se realiza la consulta
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

    public List<PropiedadMecanicaM> obtenerPropiedadesMecanicas(Conexion con, String espeTecnica) throws SQLException {
        List<PropiedadMecanicaM> listaIr = new ArrayList<>(); // Se crea una nueva lista 
        PreparedStatement consulta = con.conectar().prepareStatement(SQL_CONSULTA_PROPIEDADES_MC);
        consulta.setString(1, espeTecnica);
        ResultSet resultado = consulta.executeQuery();
        while (resultado.next()) { // Se almacena la información encontrada
            String pm = resultado.getString("pm");
            String valor = resultado.getString("valor");
            // Se crea una instancia de la clase propiedadMecanica
            PropiedadMecanicaM propiedadMecanica = new PropiedadMecanicaM(espeTecnica, pm, valor);
            listaIr.add(propiedadMecanica); // Se agrega a la lista
        }
        return listaIr;
    }

    public List<ComposicionQuimicaM> obtenerCM(Conexion con, String espeTecnica) throws SQLException {
        List<ComposicionQuimicaM> listaIr = new ArrayList<>(); // Se crea una nueva lista 
        PreparedStatement consulta = con.conectar().prepareStatement(SQL_CONSULTA_COMPOSICION_QM);
        consulta.setString(1, espeTecnica);
        ResultSet resultado = consulta.executeQuery();
        while (resultado.next()) { // Se almacena la información encontrada
            String cq = resultado.getString("cq");
            String valor = resultado.getString("valor");
            // Se crea una instancia de la clase composición química
            ComposicionQuimicaM composicionQuimica = new ComposicionQuimicaM(espeTecnica, cq, valor);
            listaIr.add(composicionQuimica); // Se agrega a la lista
        }
        return listaIr;
    }

    public void agregarFilasTabla(DefaultTableModel tableModel, int size) {
        // Determinar el número de celdas a mostrar en la columna específica
        int numCeldas = size;
        // Verificar si es necesario agregar filas adicionales a la tabla
        int numFilas = tableModel.getRowCount();
        if (numFilas < numCeldas) {
            for (int i = numFilas; i < numCeldas; i++) {
                tableModel.addRow(new Object[tableModel.getColumnCount()]);
            }
        }
    }
}
