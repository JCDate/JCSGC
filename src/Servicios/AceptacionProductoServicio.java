/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Modelos.AceptacionPc1;
import Modelos.AceptacionPc2;
import Modelos.AceptacionPc3;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JC
 */
public class AceptacionProductoServicio {

    final String SELECT_NO_ROLLO_SQL = "SELECT noRollo FROM inspeccionRecibo";
    private final String tabla = "inspeccionrecibo";
    private final String tabla2 = "inspeccionrecibo";
    private final String tabla3 = "inspeccionrecibo";

    public List<String> obtenerNoRollos(Connection conexion) throws SQLException {
        List<String> listaNoRollos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_NO_ROLLO_SQL);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                listaNoRollos.add(resultado.getString("noRollo"));
            }
        }
        return listaNoRollos;
    }

    public List<AceptacionPc1> recuperarAP1(Connection conexion, String componente) throws SQLException {
        List<AceptacionPc1> listaAp1 = new ArrayList<>();
        String sqlConsulta = "SELECT * FROM aceptacionpc1 WHERE componente=?";
        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
            consulta.setString(1, componente);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                AceptacionPc1 ap1 = new AceptacionPc1(
                        resultado.getString("id"),
                        resultado.getString("componente"),
                        resultado.getString("fecha"),
                        resultado.getString("noRollo"),
                        resultado.getString("inspVisual"),
                        resultado.getString("observacion"),
                        resultado.getString("noParte"),
                        resultado.getString("noTroquel"),
                        resultado.getString("noOps")
                );
                listaAp1.add(ap1);
            }
        }
        return listaAp1;
    }

    public void modificar(Connection conexion, AceptacionPc3 apc3, AceptacionPc3 apc32) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement("SELECT id FROM aceptacionpc3 WHERE noOp=? AND fecha=? AND variable=? AND especificacion=? AND valor=?");
                PreparedStatement updateConsulta = conexion.prepareStatement("UPDATE aceptacionpc3 SET noOp=?, fecha=?, variable=?, especificacion=?, valor=? WHERE id=?")) {
            consulta.setString(1, apc32.getNoOp());
            consulta.setString(2, apc32.getFecha());
            consulta.setString(3, apc32.getVariable());
            consulta.setString(4, apc32.getEspecificacion());
            consulta.setString(5, apc32.getValor());

            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");

                // Utiliza updateConsulta para configurar los parámetros de la consulta UPDATE
                updateConsulta.setString(1, apc3.getNoOp());
                updateConsulta.setString(2, apc3.getFecha());
                updateConsulta.setString(3, apc3.getVariable());
                updateConsulta.setString(4, apc3.getEspecificacion());
                updateConsulta.setString(5, apc3.getValor());
                updateConsulta.setInt(6, id);

                // Ejecuta la consulta UPDATE utilizando updateConsulta
                updateConsulta.executeUpdate();
            } else {
                System.out.println("No se encontró el ID.");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }

    }

    public List<AceptacionPc2> recuperarAP2(Connection conexion, String componente) throws SQLException {
        List<AceptacionPc2> listaAp2 = new ArrayList<>();
        String sqlConsulta = "SELECT * FROM aceptacionpc2 WHERE componente=?";
        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
            consulta.setString(1, componente);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                AceptacionPc2 ap2 = new AceptacionPc2(
                        resultado.getString("id"),
                        resultado.getString("componente"),
                        resultado.getString("fecha"),
                        resultado.getString("noOrden"),
                        resultado.getString("tamLote"),
                        resultado.getString("tamMta"),
                        resultado.getString("inspector"),
                        resultado.getString("turno"),
                        resultado.getString("disp")
                );
                listaAp2.add(ap2);
            }
        }
        return listaAp2;
    }

    public List<AceptacionPc3> recuperarAP3(Connection conexion, String componente) throws SQLException {
        List<AceptacionPc3> listaAp3 = new ArrayList<>();
        String sqlConsulta = "SELECT * FROM aceptacionpc3 WHERE componente=?";
        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
            consulta.setString(1, componente);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                AceptacionPc3 ap3 = new AceptacionPc3(
                        resultado.getString("id"),
                        resultado.getString("componente"),
                        resultado.getString("noOp"),
                        resultado.getString("fecha"),
                        resultado.getString("variable"),
                        resultado.getString("especificacion"),
                        resultado.getString("valor")
                );
                listaAp3.add(ap3);
            }
        }
        return listaAp3;
    }

    public void agregarpc1(Connection conexion, AceptacionPc1 ap1) throws SQLException {
        String sqlInsertIr = "INSERT INTO aceptacionpc1(componente,fecha,noRollo,inspVisual,observacion,noParte,noTroquel,noOps) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
            sqlInsert.setString(1, ap1.getComponente());
            sqlInsert.setString(2, ap1.getFecha());
            sqlInsert.setString(3, ap1.getNoRollo());
            sqlInsert.setString(4, ap1.getInspVisual());
            sqlInsert.setString(5, ap1.getObservacion());
            sqlInsert.setString(6, ap1.getNoParte());
            sqlInsert.setString(7, ap1.getNoTroquel());
            sqlInsert.setString(8, ap1.getNoOps());
            sqlInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    public void agregarpc2(Connection conexion, AceptacionPc2 ap2) throws SQLException {
        String sqlInsertIr = "INSERT INTO aceptacionpc2(componente,fecha,noOrden,tamLote,tamMta,inspector,turno,disp) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
            sqlInsert.setString(1, ap2.getComponente());
            sqlInsert.setString(2, ap2.getFecha());
            sqlInsert.setString(3, ap2.getNoOrden());
            sqlInsert.setString(4, ap2.getTamLote());
            sqlInsert.setString(5, ap2.getTamMta());
            sqlInsert.setString(6, ap2.getInspector());
            sqlInsert.setString(7, ap2.getTurno());
            sqlInsert.setString(8, ap2.getDisp());
            sqlInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    public void agregarpc3(Connection conexion, List<AceptacionPc3> ap3) throws SQLException {
        String sqlInsertIr = "INSERT INTO aceptacionpc3(componente,noOp,fecha,variable,especificacion,valor) VALUES (?,?,?,?,?,?)";
        for (int i = 0; i < ap3.size(); i++) {
            try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
                sqlInsert.setString(1, ap3.get(i).getComponente());
                sqlInsert.setString(2, ap3.get(i).getNoOp());
                sqlInsert.setString(3, ap3.get(i).getFecha());
                sqlInsert.setString(4, ap3.get(i).getVariable());
                sqlInsert.setString(5, ap3.get(i).getEspecificacion());
                sqlInsert.setString(6, ap3.get(i).getValor());
                sqlInsert.executeUpdate();
            } catch (SQLException ex) {
                throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
            }
        }
    }

    public void agregarNuevaVariable(Connection conexion, String nuevaVariable) throws SQLException {
        String sqlInsertIr = "INSERT INTO productosactividadespc(nombreProducto) VALUES (?)";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
            sqlInsert.setString(1, nuevaVariable);
            sqlInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    public void eliminarpc3(Connection conexion, String fecha, String noOp, String variable, String especificacion, String valor) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM aceptacionpc3 WHERE fecha=? AND noOp=? AND variable=? AND especificacion=? AND valor=?");
            ps.setString(1, fecha);
            ps.setString(2, noOp);
            ps.setString(3, variable);
            ps.setString(4, especificacion);
            ps.setString(5, valor);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
