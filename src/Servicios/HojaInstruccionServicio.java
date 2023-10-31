/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.InspeccionReciboM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JC
 */
public class HojaInstruccionServicio {

    private final String tabla = "inspeccionrecibo";
    public String direcciomImg = "img\\jc.png";

    public int getId(Connection conexion, InspeccionReciboM irm) throws SQLException {
        String sqlInsertIr = "SELECT id_ir FROM " + tabla + " WHERE fechaFactura=? AND noFactura=? AND noPedido=? AND noRollo=? AND pzKg=? AND noHoja=?";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
            sqlInsert.setString(1, irm.getFechaFactura());
            sqlInsert.setString(2, irm.getNoFactura());
            sqlInsert.setString(3, irm.getNoPedido());
            sqlInsert.setString(4, irm.getNoRollo());
            sqlInsert.setString(5, irm.getPzKg());
            sqlInsert.setString(6, irm.getNoHoja());
            ResultSet rs = sqlInsert.executeQuery();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("id_ir");
            } else {
                System.out.println("id =" + id);
            }
            return id;
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    public int getData(Connection conexion, int id) throws SQLException {
        String sqlInsertIr = "SELECT * FROM " + tabla + " WHERE id_ir=?";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
            sqlInsert.setInt(1, id);
            ResultSet rs = sqlInsert.executeQuery();
            return id;
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

}
