/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import InspeccionRecibo.InspeccionReciboGUI;
import Modelos.InspeccionReciboM;
import Modelos.Usuarios;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Window;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import jnafilechooser.api.JnaFileChooser;
import swing.Button;

/**
 *
 * @author JC
 */
public class InspeccionReciboServicio {

    // CONSULTAS SQL
    private final String tabla = "inspeccionrecibo";

    public final String SELECT_NOMBRE_PROVEEDORES_SQL = "SELECT DISTINCT nombre FROM proveedores"; // DISTINCT sirve para evitar que se muestren resultados duplicados
    public final String SELECT_NO_HOJA_INSTRUCCION_SQL = "SELECT noHoja FROM inspeccionrecibo ORDER BY noHoja DESC LIMIT 1";
    private final String SELECT_ID_INSPECCION_RECIBO_SQL = "SELECT id_ir FROM " + this.tabla + " WHERE calibre=? AND fechaFactura=? AND noRollo=? AND pzKg=?";
    private final String SELECT_CERTIFICADO_SQL = "SELECT pdfCertificado FROM " + this.tabla + " WHERE noHoja = ?";
    private final String SELECT_FACTURA_SQL = "SELECT pdfFactura FROM " + this.tabla + " WHERE noHoja = ?";
    private final String SELECT_INSPECCION_RECIBO_SQL = "SELECT * FROM " + this.tabla;
    private final String INSERT_INSPECCION_RECIBO_SQL = "INSERT INTO " + this.tabla + "(fechaFactura,Proveedor,noFactura,noPedido,calibre,pLamina,noRollo,pzKg,estatus,noHoja,pdfFactura,pdfCertificado,hojaInstruccion) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String DELETE_INSPECCION_RECIBO_SQL = "DELETE FROM " + this.tabla + " WHERE noHoja=? AND fechaFactura=? AND noFactura=? AND noPedido=? AND pzKg=?";
    private final String UPDATE_INSPECCION_RECIBO_SQL = "UPDATE " + this.tabla + " SET fechaFactura=?, Proveedor=?, noFactura=?, noPedido=?, calibre=?, pLamina=?, noRollo=?, pzKg=?, estatus=?, noHoja=?, pdfFactura=?, pdfCertificado=?, hojaInstruccion=? WHERE id_ir=?";

    public String direcciomImg = "img\\jc.png";

    Connection conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos

    public void agregar(Connection conexion, InspeccionReciboM irm) throws SQLException {
        try (PreparedStatement sqlInsertarIR = conexion.prepareStatement(INSERT_INSPECCION_RECIBO_SQL)) {
            sqlInsertarIR.setString(1, irm.getFechaFactura());
            sqlInsertarIR.setString(2, irm.getProveedor());
            sqlInsertarIR.setString(3, irm.getNoFactura());
            sqlInsertarIR.setString(4, irm.getNoPedido());
            sqlInsertarIR.setString(5, irm.getCalibre());
            sqlInsertarIR.setString(6, irm.getpLamina());
            sqlInsertarIR.setString(7, irm.getNoRollo());
            sqlInsertarIR.setString(8, irm.getPzKg());
            sqlInsertarIR.setString(9, irm.getEstatus());
            sqlInsertarIR.setString(10, irm.getNoHoja());
            sqlInsertarIR.setBytes(11, irm.getFacturapdf());
            sqlInsertarIR.setBytes(12, irm.getCertificadopdf());
            sqlInsertarIR.setBytes(13, irm.getHojaIns());
            sqlInsertarIR.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    public List<InspeccionReciboM> recuperarTodas(Connection conexion) throws SQLException {
        List<InspeccionReciboM> listaIr = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_INSPECCION_RECIBO_SQL);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                InspeccionReciboM ir = new InspeccionReciboM(
                        resultado.getInt("id_ir"),
                        resultado.getString("fechaFactura"),
                        resultado.getString("Proveedor"),
                        resultado.getString("noFactura"),
                        resultado.getString("noPedido"),
                        resultado.getString("calibre"),
                        resultado.getString("pLamina"),
                        resultado.getString("noRollo"),
                        resultado.getString("pzKg"),
                        resultado.getString("estatus"),
                        resultado.getString("noHoja"),
                        resultado.getBytes("pdfFactura"),
                        resultado.getBytes("pdfCertificado"),
                        resultado.getBytes("hojaInstruccion")
                );
                listaIr.add(ir);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL: " + ex.getMessage(), ex);
        }
        return listaIr;
    }

    public void eliminar(String noHoja, String fechaFactura, String noFactura, String noPedido, String pzKg) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = conexion.prepareStatement(DELETE_INSPECCION_RECIBO_SQL);
        ps.setString(1, noHoja);
        ps.setString(2, fechaFactura);
        ps.setString(3, noFactura);
        ps.setString(4, noPedido);
        ps.setString(5, pzKg);
        ps.executeUpdate();
    }

    public void modificar(Connection conexion, InspeccionReciboM irm, InspeccionReciboM irm2) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ID_INSPECCION_RECIBO_SQL);
                PreparedStatement updateConsulta = conexion.prepareStatement(UPDATE_INSPECCION_RECIBO_SQL)) {
            consulta.setString(1, irm2.getCalibre());
            consulta.setString(2, irm2.getFechaFactura());
            consulta.setString(3, irm2.getNoRollo());
            consulta.setString(4, irm2.getPzKg());
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_ir");

                updateConsulta.setString(1, irm.getFechaFactura());
                updateConsulta.setString(2, irm.getProveedor());
                updateConsulta.setString(3, irm.getNoFactura());
                updateConsulta.setString(4, irm.getNoPedido());
                updateConsulta.setString(5, irm.getCalibre());
                updateConsulta.setString(6, irm.getpLamina());
                updateConsulta.setString(7, irm.getNoRollo());
                updateConsulta.setString(8, irm.getPzKg());
                updateConsulta.setString(9, irm.getEstatus());
                updateConsulta.setString(10, irm.getNoHoja());

                // Usar el operador ternario para definir los campos de bytes en la consulta
                // Verificar si no se proporcionó un archivo de bytes para el campo facturapdf
                if (irm.getFacturapdf() == null || irm.getFacturapdf().length == 0) {
                    updateConsulta.setBytes(11, irm2.getFacturapdf());
                } else {
                    updateConsulta.setBytes(11, irm.getFacturapdf());
                    System.out.println(Arrays.toString(irm.getFacturapdf()));
                }

                // Verificar si no se proporcionó un archivo de bytes para el campo certificadopdf
                if (irm.getCertificadopdf() == null || irm.getCertificadopdf().length == 0) {
                    updateConsulta.setBytes(12, irm2.getCertificadopdf());
                } else {
                    updateConsulta.setBytes(12, irm.getCertificadopdf());
                    System.out.println(Arrays.toString(irm.getCertificadopdf()));
                }

                // Verificar si no se proporcionó un archivo de bytes para el campo hojaIns
                if (irm.getHojaIns() == null || irm.getHojaIns().length == 0) {
                    updateConsulta.setBytes(13, irm2.getHojaIns());
                } else {
                    updateConsulta.setBytes(13, irm.getHojaIns());
                    System.out.println(Arrays.toString(irm.getHojaIns()));
                }

                updateConsulta.setInt(14, id);
                updateConsulta.executeUpdate();
            } else {
                System.out.println("No se encontró el ID.");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }

    public void subirHI(Connection conexion, InspeccionReciboM irm) throws SQLException {
        String sqlInsertIr = "UPDATE " + this.tabla + " SET hojaInstruccion=? WHERE id_ir=?";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
            sqlInsert.setBytes(1, irm.getHojaIns());
            sqlInsert.setInt(2, irm.getId());
            sqlInsert.executeUpdate();
            JOptionPane.showMessageDialog(null, "El Archivo se genero y se guardo Correctamente");
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    // Permite mostrar PDF contenido en la base de datos
    public void ejecutarArchivoPDF(String id, int column) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (column == 10) {
                ps = conexion.prepareStatement(SELECT_FACTURA_SQL);
            } else if (column == 11) {
                ps = conexion.prepareStatement(SELECT_CERTIFICADO_SQL);
            }
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                byte[] b = rs.getBytes(1);

                try (InputStream bos = new ByteArrayInputStream(b);
                        OutputStream out = new FileOutputStream("nuevoArchivo.pdf")) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = bos.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                    System.out.println("Archivo PDF creado correctamente.");
                }
            } else {
                System.out.println("No se encontraron datos para el ID proporcionado.");
            }
        } catch (IOException | SQLException ex) {
            System.out.println("Error al abrir o crear el archivo PDF: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }

    public byte[] leerArchivo(String rutaArchivo) throws IOException {
        File archivo = new File(rutaArchivo); // Se crea un nuevo archivo con la ruta especificada
        byte[] pdf = new byte[(int) archivo.length()]; // Se crea un arreglo de bytes de la misma longitud del archivo previamente generado
        if (archivo.exists()) {
            try (InputStream input = new FileInputStream(archivo)) { // Se crea una instancia para leer los bytes del archivo
                input.read(pdf); // Se lee la información capturada del arreglo de bytes, lee el archivo de bytes y lo almacena en la variable pdf
            }
        }
        return pdf; // Se regresa el archivo
    }

    public File seleccionarArchivo(Component parentComponent) {

        JnaFileChooser jfc = new JnaFileChooser();
        jfc.addFilter("pdf", "xlsx", "xls", "pdf", "PDF");
        boolean action = jfc.showOpenDialog((Window) parentComponent);
        if (action) {
            return jfc.getSelectedFile();
        }

        return null;

        /*JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Archivos PDF y Excel", "pdf", "xlsx", "xls");
        fileChooser.setFileFilter(extensionFilter);
        int seleccion = fileChooser.showOpenDialog(parentComponent);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null; // Si no se seleccionó nungún archivo */
    }

    public void ejecutarArchivoXLSX(String id, int column) throws ClassNotFoundException, SQLException {
        final String SELECT_HOJA_INSTRUCCION_SQL = "SELECT hojaInstruccion FROM inspeccionRecibo WHERE noHoja = ?";
        //PreparedStatement ps = null;
        ResultSet rs;
        byte[] b = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(SELECT_HOJA_INSTRUCCION_SQL);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                b = rs.getBytes(1);
            }
            try (InputStream bos = new ByteArrayInputStream(b)) {
                int tamanoInput = bos.available();
                byte[] datosXLSX = new byte[tamanoInput];
                bos.read(datosXLSX, 0, tamanoInput);
                try (OutputStream out = new FileOutputStream("nuevaHojaInstruccion.xlsx")) {
                    out.write(datosXLSX);
                    // Abrir archivo con Excel
                    File archivoXLSX = new File("nuevaHojaInstruccion.xlsx");
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(archivoXLSX);
                    }
                }
            }
            ps.close();
            rs.close();
        } catch (IOException | NumberFormatException | SQLException ex) {
            System.out.println("Error al abrir archivo XLSX: " + ex.getMessage());
        }
    }

    public Button crearBoton(Object object, ImageIcon icon, String texto, Color a, Color b) {
        // Crear una fuente con el tamaño deseado
        Font customFont = new Font("Arial", Font.PLAIN, 10);
        Button boton; // Se define el boton
        if (object != null) { // Si el objeto que se manda es nulo
            if (object instanceof byte[]) { // Si es un arreglo de bytes                
                byte[] byteArray = (byte[]) object; // Se crea el arreglo de bytes del objecto
                if (byteArray.length != 0) { // Si la longitud es diferente a cero...
                    boton = new Button(); // Se crea el nuevo boton
                    // Agregar un MouseListener al botón
                    //boton.setRolloverEnabled(true);
                    boton.setIcon(icon);
                } else {
                    boton = new Button(); // Se crea el nuevo boton
                    boton.setText(texto);
                    boton.setFont(customFont);
                }
            } else {
                boton = new Button(); // Se crea el nuevo boton
                boton.setText(texto);
                boton.setFont(customFont);
            }
        } else {
            boton = new Button(); // Se crea el nuevo boton
            boton.setText(texto);
        }
        return boton;
    }

    public List<String> selectProveedores(Connection conexion) throws SQLException {
        List<String> listaProveedores = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_NOMBRE_PROVEEDORES_SQL);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                String proveedor = resultado.getString("nombre");
                listaProveedores.add(proveedor);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL: " + ex.getMessage(), ex);
        }
        return listaProveedores;
    }

    public void cargarArchivo(String rutaArchivo, Consumer<byte[]> setterFunction) {
        try {
            byte[] archivoData = this.leerArchivo(rutaArchivo);
            setterFunction.accept(archivoData);
        } catch (IOException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int existeCalibre(String usuarios) throws ClassNotFoundException {
        try {
            String sql = "SELECT COUNT(calibre) FROM " + this.tabla + " WHERE calibre=?";

            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, usuarios);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;

        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    public void goToInspeccionRecibo(Usuarios usr) {
        try {
            InspeccionReciboGUI irGUI = new InspeccionReciboGUI(usr); //Se crea la instancia de la clase
            irGUI.setVisible(true); //Se hace visible la ventana
            irGUI.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarEspecificacion(Connection conexion, String especificacion) {
        try (PreparedStatement sqlInsertET = conexion.prepareStatement("INSERT INTO especificacionesir1(Especificacion) VALUES ?")) {

        } catch (SQLException ex) {
            try {
                throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
            } catch (SQLException ex1) {
                Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

}
