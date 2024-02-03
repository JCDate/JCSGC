package Servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author JC
 */
public class SQL {

    Connection conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos
    ContadorAnual contador = new ContadorAnual(); // Contador de reportes de inspección

    public int autoIncremento(String sql) {
        int id = 1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException ex) {
            System.out.println("idmaximo" + ex.getMessage());
            id = 1;
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }
        return id;
    }

    public String getCodigoHoja(String sql, int year) {
        String numHoja = "";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(year) + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    numHoja = rs.getString(1);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el código de la hoja: " + ex.getMessage());
            numHoja = "";
        }
        return numHoja;
    }

    public String obtenerSiguiente(String ultimoCodigo, String prefijo) {
        String numeroStr = "";
        if (ultimoCodigo == null || ultimoCodigo.length() <= prefijo.length()) {
            numeroStr = "0";
        } else {
            numeroStr = ultimoCodigo.substring(prefijo.length() + 1); // Extraer el número del código
        }
        int numero = Integer.parseInt(numeroStr); // Convertir el número a entero
        int siguienteNumero = contador.obtenerSiguienteNumero(numero);
        String nuevoNumeroStr = String.format("%03d", siguienteNumero); // Formatear el número con ceros a la izquierda
        return prefijo + "/" + nuevoNumeroStr; // Generar el nuevo código
    }
}
