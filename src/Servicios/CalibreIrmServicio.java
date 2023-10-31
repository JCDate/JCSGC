/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.CalibreIRM;
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
    private static final String SQL_CONSULTA_INSPECCION_RECIBO = "SELECT * FROM inspeccionrecibo";

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

    public List<CalibreIRM> recuperarTodas(Connection conexion) throws SQLException {
        List<CalibreIRM> listaCalibreIRM = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SQL_CONSULTA_INSPECCION_RECIBO);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                listaCalibreIRM.add(new CalibreIRM(resultado.getString("calibre"), resultado.getString("medidas"),resultado.getString("especificacion")));
            }
        }
        return listaCalibreIRM;
    }

}
