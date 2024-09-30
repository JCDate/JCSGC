package Servicios;

import InspeccionRecibo.AgregarIrGUI;
import InspeccionRecibo.EspecificacionesGUI;
import InspeccionRecibo.HojaInstruccionGUI;
import InspeccionRecibo.HojaInstruccionGUI2;
import InspeccionRecibo.InspeccionReciboGUI;
import InspeccionRecibo.ModificarIrGUI;
import Modelos.CalibreIRM;
import Modelos.DatosIRM;
import Modelos.EspecificacionM;
import Modelos.InspeccionReciboM;
import Modelos.Usuarios;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Window;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import jnafilechooser.api.JnaFileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import swing.Button;

public class InspeccionReciboServicio {

    private final String tabla = "inspeccionrecibo";
    private String numeroStr;

    final String SQL_CONSULTA_REF_ESPECIFICACION = "SELECT Especificacion, codigoET, fechaEmision, fechaRevision, noRev FROM especificacionesir WHERE Especificacion=?";

    private final String SQL_CONSULTA_ESPECIFICACION = "SELECT especificacion FROM calibresir WHERE calibre = ?";
    private final String SQL_CONSULTA_MEDIDAS_CALIBRE = "SELECT DISTINCT calibre, medidas FROM calibresir WHERE calibre LIKE ? ORDER BY calibre";
    private final String SQL_CONSULTA_CALIBRE = "SELECT DISTINCT medidas FROM calibresir WHERE calibre LIKE ?";
    private final String SQL_CONSULTA_INSPECTORES = "SELECT nombre FROM usuarios WHERE id_tipo=?";
    private final String SELECT_NOMBRE_PROVEEDORES_SQL = "SELECT DISTINCT nombre FROM proveedores";
    public final String SELECT_NO_HOJA_INSTRUCCION_SQL = "SELECT noHoja FROM inspeccionrecibo WHERE noHoja LIKE ? ORDER BY noHoja DESC LIMIT 1";
    private final String SELECT_ID_INSPECCION_RECIBO_SQL = "SELECT id_ir FROM " + this.tabla + " WHERE calibre=? AND fechaFactura=? AND noRollo=? AND pzKg=?";
    private final String SELECT_CERTIFICADO_SQL = "SELECT pdfCertificado, nombreCert FROM " + this.tabla + " WHERE noHoja = ?";
    private final String SELECT_FACTURA_SQL = "SELECT pdfFactura, nombreFact FROM " + this.tabla + " WHERE noHoja = ?";
    private final String SELECT_INSPECCION_RECIBO_SQL = "SELECT * FROM " + this.tabla;
    private final String INSERT_INSPECCION_RECIBO_SQL = "INSERT INTO " + this.tabla + "(fechaFactura,Proveedor,noFactura,noPedido,calibre,pLamina,noRollo,pzKg,estatus,noHoja,pdfFactura,pdfCertificado,hojaInstruccion,nombreHJ,nombreFact,nombreCert) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String DELETE_INSPECCION_RECIBO_SQL = "DELETE FROM " + this.tabla + " WHERE noHoja=? AND fechaFactura=? AND noFactura=? AND noPedido=? AND pzKg=?";
    private final String UPDATE_INSPECCION_RECIBO_SQL = "UPDATE " + this.tabla + " SET fechaFactura=?, Proveedor=?, noFactura=?, noPedido=?, calibre=?, pLamina=?, noRollo=?, pzKg=?, estatus=?, noHoja=?, pdfFactura=?, pdfCertificado=?, hojaInstruccion=?, nombreHJ=? ,nombreFact=?, nombreCert=? WHERE id_ir=?";

    public String direcciomImg = "img\\jc.png";
    // Obtener la conexión a la base de datos

    Connection conexion; 

    public final String DIRECCION_IMG = "img\\jc.png";
    private static final String SQL_INSERCION_CALIBRE_IR = "INSERT INTO calibresir(calibre,medidas,especificacion) VALUES (?,?,?)";
    private static final String SQL_INSERCION_DMP = "INSERT INTO materiaprimasc(calibre,descripcionMP,calibreLamina) VALUES (?,?,?)";
    private static final String SQL_CONSULTA_INSPECCION_RECIBO = "SELECT * FROM inspeccionrecibo";
    private final String SQL_CONSULTA_ESPECIFICACIONES_IR = "SELECT Especificacion FROM especificacionesir";

    public InspeccionReciboServicio() {
        try {
            this.conexion = Conexion.getInstance().conectar();
        } catch (SQLException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    public List<CalibreIRM> recuperarTodasMedidasCalibre(Connection conexion) throws SQLException {
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
        try (PreparedStatement consulta2 = conexion.prepareStatement(SQL_CONSULTA_ESPECIFICACIONES_IR);
                ResultSet resultado2 = consulta2.executeQuery()) {
            while (resultado2.next()) { // Se guardan los calibres en el comboBox
                listaEspecificaciones.add(resultado2.getString("Especificacion"));
            }
        }
        return listaEspecificaciones;
    }

    public void agregar(Connection conexion, InspeccionReciboM irm) throws SQLException {
        try {
            // Inicia la transacción
//            conexion.setAutoCommit(false);

            // Consulta para verificar si el noRollo ya está registrado
            String sql = "SELECT hojaInstruccion FROM inspeccionrecibo WHERE noRollo = ? LIMIT 1";
            try (PreparedStatement pstmtSelect = conexion.prepareStatement(sql)) {
                pstmtSelect.setString(1, irm.getNoRollo());
                ResultSet rs = pstmtSelect.executeQuery();

                if (rs.next()) {
                    // Si el noRollo ya está registrado, obtén la hojainstruccion
                    byte[] hojaInstruccion = rs.getBytes("hojaInstruccion");

                    if (hojaInstruccion != null && hojaInstruccion.length != 0) {
                        // Verifica si la hojainstruccion no está vacía
                        byte[] nuevahojaInstruccion = editarHojaInstruccion(hojaInstruccion, irm);

                        // Inserta el nuevo registro con la hojainstruccion obtenida
                        String sqlInsert = "INSERT INTO inspeccionrecibo(fechaFactura, proveedor, noFactura, noPedido, calibre, pLamina, noRollo, pzKg, estatus, noHoja, pdfFactura, pdfCertificado, hojaInstruccion, nombreHJ, nombreFact, nombreCert) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement pstmtInsert = conexion.prepareStatement(sqlInsert)) {
                            pstmtInsert.setString(1, irm.getFechaFactura());
                            pstmtInsert.setString(2, irm.getProveedor());
                            pstmtInsert.setString(3, irm.getNoFactura());
                            pstmtInsert.setString(4, irm.getNoPedido());
                            pstmtInsert.setString(5, irm.getCalibre());
                            pstmtInsert.setString(6, irm.getpLamina());
                            pstmtInsert.setString(7, irm.getNoRollo());
                            pstmtInsert.setString(8, irm.getPzKg());
                            pstmtInsert.setString(9, irm.getEstatus());
                            pstmtInsert.setString(10, irm.getNoHoja());
                            pstmtInsert.setBytes(11, irm.getFacturapdf());
                            pstmtInsert.setBytes(12, irm.getCertificadopdf());
                            pstmtInsert.setBytes(13, nuevahojaInstruccion); // Utiliza la hojainstruccion obtenida
                            pstmtInsert.setString(14, irm.getNombreHJ());
                            pstmtInsert.setString(15, irm.getNombreFact());
                            pstmtInsert.setString(16, irm.getNombreCert());
                            pstmtInsert.executeUpdate();
                        }
                    } else {
                        // Si hojainstruccion es nula o vacía, realiza la inserción normal
                        String sqlInsertNormal = "INSERT INTO inspeccionrecibo(fechaFactura, proveedor, noFactura, noPedido, calibre, pLamina, noRollo, pzKg, estatus, noHoja, pdfFactura, pdfCertificado, hojaInstruccion, nombreHJ, nombreFact, nombreCert) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement pstmtInsertNormal = conexion.prepareStatement(sqlInsertNormal)) {
                            pstmtInsertNormal.setString(1, irm.getFechaFactura());
                            pstmtInsertNormal.setString(2, irm.getProveedor());
                            pstmtInsertNormal.setString(3, irm.getNoFactura());
                            pstmtInsertNormal.setString(4, irm.getNoPedido());
                            pstmtInsertNormal.setString(5, irm.getCalibre());
                            pstmtInsertNormal.setString(6, irm.getpLamina());
                            pstmtInsertNormal.setString(7, irm.getNoRollo());
                            pstmtInsertNormal.setString(8, irm.getPzKg());
                            pstmtInsertNormal.setString(9, irm.getEstatus());
                            pstmtInsertNormal.setString(10, irm.getNoHoja());
                            pstmtInsertNormal.setBytes(11, irm.getFacturapdf());
                            pstmtInsertNormal.setBytes(12, irm.getCertificadopdf());
                            pstmtInsertNormal.setBytes(13, irm.getHojaIns());
                            pstmtInsertNormal.setString(14, irm.getNombreHJ());
                            pstmtInsertNormal.setString(15, irm.getNombreFact());
                            pstmtInsertNormal.setString(16, irm.getNombreCert());
                            pstmtInsertNormal.executeUpdate();
                        }
                    }
                } else {
                    // Si el noRollo no está registrado, realiza solo la inserción normal
                    String sqlInsertNormal = "INSERT INTO inspeccionrecibo(fechaFactura, proveedor, noFactura, noPedido, calibre, pLamina, noRollo, pzKg, estatus, noHoja, pdfFactura, pdfCertificado, hojaInstruccion, nombreHJ, nombreFact, nombreCert) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement pstmtInsertNormal = conexion.prepareStatement(sqlInsertNormal)) {
                        pstmtInsertNormal.setString(1, irm.getFechaFactura());
                        pstmtInsertNormal.setString(2, irm.getProveedor());
                        pstmtInsertNormal.setString(3, irm.getNoFactura());
                        pstmtInsertNormal.setString(4, irm.getNoPedido());
                        pstmtInsertNormal.setString(5, irm.getCalibre());
                        pstmtInsertNormal.setString(6, irm.getpLamina());
                        pstmtInsertNormal.setString(7, irm.getNoRollo());
                        pstmtInsertNormal.setString(8, irm.getPzKg());
                        pstmtInsertNormal.setString(9, irm.getEstatus());
                        pstmtInsertNormal.setString(10, irm.getNoHoja());
                        pstmtInsertNormal.setBytes(11, irm.getFacturapdf());
                        pstmtInsertNormal.setBytes(12, irm.getCertificadopdf());
                        pstmtInsertNormal.setBytes(13, irm.getHojaIns());
                        pstmtInsertNormal.setString(14, irm.getNombreHJ());
                        pstmtInsertNormal.setString(15, irm.getNombreFact());
                        pstmtInsertNormal.setString(16, irm.getNombreCert());
                        pstmtInsertNormal.executeUpdate();
                    }
                }
            }

            // Confirma la transacción
//            conexion.commit();
        } catch (SQLException ex) {
            // Si hay algún error, realiza un rollback para deshacer los cambios
//            conexion.rollback();
            throw new SQLException("Error al ejecutar la transacción SQL: " + ex.getMessage(), ex);
        } finally {
            // Restaura el modo de autocommit a true
            conexion.setAutoCommit(true);
        }
    }

    public Date formatearFecha(String fecha) {
        try {
            if (fecha == null) {
                return null;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
            manejarExcepcion("Error al formatear la fecha: ", ex);
            return null;
        }
    }

    public String formatearFecha(Date fecha) {
        if (fecha == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(fecha);
    }

    public Button crearBoton(Object object, ImageIcon icon, String texto) {
        Font fuentePersonalizada = new Font("Arial", Font.PLAIN, 10);
        Button boton = new Button();
        if (object != null) {
            if (object instanceof byte[]) {
                byte[] byteArray = (byte[]) object;
                if (byteArray.length != 0) {
                    boton.setIcon(icon);
                } else {
                    boton.setText(texto);
                    boton.setFont(fuentePersonalizada);
                }
            } else {
                boton.setText(texto);
                boton.setFont(fuentePersonalizada);
            }
        } else {
            boton.setText(texto);
        }
        return boton;
    }

    private boolean noRolloExiste(Connection conexion, String noRollo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM inspeccionrecibo WHERE noRollo = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, noRollo);
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    private void copiarHojaInstruccion(Connection conexion, String noRollo, byte[] hojaIns) throws SQLException {
        String sql = "INSERT INTO inspeccionrecibo(noRollo, hojainstruccion) SELECT ?, hojainstruccion FROM inspeccionrecibo WHERE noRollo = ? LIMIT 1";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, noRollo);
            pstmt.setString(2, noRollo);
            pstmt.executeUpdate();
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
                        resultado.getBytes("hojaInstruccion"),
                        resultado.getString("nombreHJ"),
                        resultado.getString("nombreFact"),
                        resultado.getString("nombreCert")
                );
                listaIr.add(ir);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL: " + ex.getMessage(), ex);
        }
        return listaIr;
    }

    public List<InspeccionReciboM> recuperarRegistrosIR(Connection conexion) throws SQLException {
        List<InspeccionReciboM> listaIr = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_INSPECCION_RECIBO_SQL);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                InspeccionReciboM ir = new InspeccionReciboM(
                        resultado.getInt("id_ir"),
                        resultado.getString("fechaFactura"),//
                        resultado.getString("Proveedor"), //
                        resultado.getString("noFactura"), //
                        resultado.getString("noPedido"), //
                        resultado.getString("calibre"), //
                        resultado.getString("pLamina"), //
                        resultado.getString("noRollo"), //
                        resultado.getString("pzKg"), // 
                        resultado.getString("estatus"), //
                        resultado.getString("noHoja"), //
                        null,
                        null,
                        null,
                        "",
                        "",
                        ""
                );
                listaIr.add(ir);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL: " + ex.getMessage(), ex);
        }
        return listaIr;
    }

    public List<String> recuperarInspectores(Connection conexion) {
        List<String> inspectores = new ArrayList<>();
        try {
            PreparedStatement slqNomProv = conexion.prepareStatement(SQL_CONSULTA_INSPECTORES);
            slqNomProv.setInt(1, 5);
            ResultSet registro = slqNomProv.executeQuery();
            while (registro.next()) {
                String inspector = registro.getString("nombre");
                inspectores.add(inspector);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inspectores;
    }

    public List<String> obtenerProveedores(Connection conexion) throws SQLException {
        List<String> proveedores = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_NOMBRE_PROVEEDORES_SQL);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                String proveedor = resultado.getString("nombre");
                proveedores.add(proveedor);
            }
        } catch (SQLException ex) {
            throw new SQLException("Error al recuperar proveedores: " + ex.getMessage(), ex);
        }
        return proveedores;
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
                }

                // Verificar si no se proporcionó un archivo de bytes para el campo certificadopdf
                if (irm.getCertificadopdf() == null || irm.getCertificadopdf().length == 0) {
                    updateConsulta.setBytes(12, irm2.getCertificadopdf());
                } else {
                    updateConsulta.setBytes(12, irm.getCertificadopdf());
                }

                // Verificar si no se proporcionó un archivo de bytes para el campo hojaIns
                if (irm.getHojaIns() == null || irm.getHojaIns().length == 0) {
                    updateConsulta.setBytes(13, irm2.getHojaIns());
                } else {
                    updateConsulta.setBytes(13, irm.getHojaIns());
                }
                updateConsulta.setString(14, irm.getNombreHJ());
                updateConsulta.setString(15, irm.getNombreFact());
                updateConsulta.setString(16, irm.getNombreCert());
                updateConsulta.setInt(17, id);
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
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL de inserción Y NO SE SUBIO NADA: " + ex.getMessage());

        }
    }

    public void ejecutarArchivoPDF(String id, int columna) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Prepara la consulta SQL basada en la columna
            if (columna == 10) {
                ps = conexion.prepareStatement(SELECT_FACTURA_SQL);
            } else if (columna == 11) {
                ps = conexion.prepareStatement(SELECT_CERTIFICADO_SQL);
            }
            ps.setString(1, id);
            rs = ps.executeQuery();

            // Procesa el ResultSet
            if (rs.next()) {
                byte[] pdfBytes = rs.getBytes(1); // Reemplaza 'pdfColumn' con el nombre de la columna
                String nombreArchivo = (columna == 10) ? rs.getString("nombreFact") : rs.getString("nombreCert");
                String nombreDocto = (nombreArchivo != "") ? nombreArchivo : "nuevoArchivo.pdf";

                // Escribe el archivo PDF
                try (InputStream inputStream = new ByteArrayInputStream(pdfBytes);
                        OutputStream outputStream = new FileOutputStream(nombreDocto)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    System.out.println("Archivo PDF creado correctamente.");
                }

                // Abre el archivo PDF
                File archivo = new File(nombreDocto);
                if (archivo.exists()) {
                    Desktop.getDesktop().open(archivo);
                    System.out.println("Archivo " + nombreDocto + " abierto correctamente.");
                } else {
                    System.out.println("El archivo no se encontró.");
                }
            } else {
                System.out.println("No se encontraron datos para el ID proporcionado.");
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al abrir o crear el archivo PDF: " + ex.getMessage());
        } finally {
            // Cierra los recursos
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
        jfc.addFilter("pdf", "xlsx", "xls", "pdf", "PDF", "ppt", "pptx", "doc", "docx");
        boolean action = jfc.showOpenDialog((Window) parentComponent);
        if (action) {
            return jfc.getSelectedFile();
        }

        return null;
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

                    // Verificar la existencia del archivo antes de abrirlo con Excel
                    File archivoXLSX = new File("nuevaHojaInstruccion.xlsx");
                    if (archivoXLSX.exists() && Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(archivoXLSX);
                    } else {
                        System.out.println("El archivo no existe o el escritorio no es compatible para abrirlo.");
                    }
                } catch (IOException ex) {
                    // Manejo de la excepción de E/S
                    System.out.println("Error al escribir en el archivo: " + ex.getMessage());
                } catch (UnsupportedOperationException ex) {
                    // Manejo de la excepción si el escritorio no es compatible
                    System.out.println("El escritorio no es compatible para abrir archivos: " + ex.getMessage());
                } catch (Exception ex) {
                    // Manejo de cualquier otra excepción inesperada
                    System.out.println("Error al descargar el documento: " + ex.getMessage());
                }

            }
            ps.close();
            rs.close();
        } catch (IOException | NumberFormatException | SQLException ex) {
            System.out.println("Error al abrir archivo XLSX: " + ex.getMessage());
        }
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

    public boolean existeNoRollo(String noRollo) throws ClassNotFoundException {
        try {
            String sql = "SELECT COUNT(noRollo) FROM " + this.tabla + " WHERE noRollo = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, noRollo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Retorna verdadero si existe algún rollo con el número proporcionado
            }
            return false; // No se encontraron resultados, por lo que el rollo no existe
        } catch (SQLException ex) {
            Logger.getLogger(SqlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false; // Manejo de excepciones, el rollo no puede existir si hay un error SQL
        }
    }

    public void abrirInspeccionReciboGUI(Usuarios usuario) {
        InspeccionReciboGUI irGUI = new InspeccionReciboGUI(usuario);
        irGUI.setVisible(true);
        irGUI.setLocationRelativeTo(null);
    }

    public void abrirAgregarIrGUI(Usuarios usuario) {
        try {
            AgregarIrGUI agregarGUI = new AgregarIrGUI(usuario);
            agregarGUI.setVisible(true);
            agregarGUI.setLocationRelativeTo(null);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
            manejarExcepcion("Surgio un error al abrir AgregarIrGUI: ", ex);
        }
    }

    public void agregarEspecificacion(Connection conexion, String especificacion) {
        try (PreparedStatement insertEspecificacion = conexion.prepareStatement("INSERT INTO especificacionesir(Especificacion) VALUES (?)")) {
            insertEspecificacion.setString(1, especificacion);
            insertEspecificacion.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL de inserción: " + ex.getMessage());
        }
    }

    public void abrirHojaInstruccionGUI(Usuarios usr, InspeccionReciboM irm) {
        try {
            HojaInstruccionGUI hjGUI = new HojaInstruccionGUI(usr, irm);
            mostrarVentana(hjGUI);
        } catch (SQLException | ClassNotFoundException ex) {
            manejarExcepcion("Surgio un error al abrir HojaInstruccionGUI", ex);
        }
    }

    public void abrirHojaInstruccionGUI(Usuarios usuario, InspeccionReciboM inspeccionRecibo, DatosIRM datosIR, List listaAnchoLargo, List listaRugosidadDureza) {
        try {
            HojaInstruccionGUI hjGUI = new HojaInstruccionGUI(usuario, inspeccionRecibo, datosIR, listaAnchoLargo, listaRugosidadDureza);
            mostrarVentana(hjGUI);
        } catch (SQLException | ClassNotFoundException ex) {
            manejarExcepcion("Surgio un error al abrir HojaInstruccionGUI", ex);
        }
    }

    public String abrirHojaInstruccionGUI2(Usuarios usr, String rutaArchivo, InspeccionReciboM irm) {
        String rutaArchivo2 = null;
        try {

            HojaInstruccionGUI2 hjGUI = new HojaInstruccionGUI2(usr, rutaArchivo, irm);
            mostrarVentana(hjGUI);
            rutaArchivo2 = hjGUI.getRutaArchivo();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            manejarExcepcion("Surgio un error al abrir ACEPTACION PRODUCTO", ex);
        }

        return rutaArchivo2;
    }

    public void manejarExcepcion(String msg, Exception ex) {
        JOptionPane.showMessageDialog(null, msg + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarVentana(JFrame frame) {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private byte[] editarHojaInstruccion(byte[] hojaInstruccion, InspeccionReciboM irm) {
        ByteArrayInputStream bis = new ByteArrayInputStream(hojaInstruccion);
        XSSFWorkbook workbook;
        byte[] hojaNueva = null;
        try {
            workbook = new XSSFWorkbook(bis);
            Sheet sheet = workbook.getSheetAt(0);

            // Número de Hoja
            String[] partes = irm.getNoHoja().split("/"); // Se divide el valor de NoHoja para solo obtener el número
            numeroStr = partes[1];
            int numero = Integer.parseInt(numeroStr);
            if (numero < 10) { // Eliminar ceros a la izquierda
                numeroStr = String.valueOf(numero);
            }
            workbook.setSheetName(workbook.getSheetIndex(sheet), numeroStr); // Cambiar el nombre de la Hoja con el número previamente obtenido

            Row fila = sheet.getRow(5); // Obtén la fila
            Cell celda = fila.getCell(2); // Obtén la celda en la fila
            celda.setCellValue(numeroStr); // Modifica el valor de la celda

            // Fecha
            Row fila2 = sheet.getRow(9); // Obtén la fila
            Cell celda2 = fila2.getCell(8); // Obtén la celda en la fila
            celda2.setCellValue(irm.getFechaFactura()); // Modifica el valor de la celda

            // Factura
            Row fila4 = sheet.getRow(9); // Obtén la fila
            Cell celda4 = fila4.getCell(8); // Obtén la celda en la fila
            celda4.setCellValue(irm.getNoFactura()); // Modifica el valor de la celda

            // No PzKg
            Row fila7 = sheet.getRow(13); // Obtén la fila
            Cell celda7 = fila7.getCell(7); // Obtén la celda en la fila
            celda7.setCellValue(irm.getPzKg()); // Modifica el valor de la celda

            // Descripción
            Row fila3 = sheet.getRow(9); // Obtén la fila
            Cell celda3 = fila3.getCell(1); // Obtén la celda en la fila
            String txtTexto = celda3.getStringCellValue();
            if (txtTexto.contains("HOJA") && irm.getpLamina().equals("CINTA")) {
                txtTexto = txtTexto.replace("HOJA", "CINTA");
            } else if (txtTexto.contains("CINTA") && irm.getpLamina().equals("HOJA")) {
                txtTexto = txtTexto.replace("CINTA", "HOJA");
            }
            celda3.setCellValue(txtTexto);

            bis.close(); // Cerrar el flujo de entrada
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            hojaNueva = bos.toByteArray();
            bos.close();

        } catch (IOException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hojaNueva;
    }

    public String obtenerValorComboBox(JComboBox<String> comboBox) {
        return comboBox.getSelectedItem() != null ? comboBox.getSelectedItem().toString() : "";
    }

    public String obtenerTexto(JTextField textField) {
        return textField.getText().trim();
    }

    public void abrirModificarIrGUI(InspeccionReciboM inspeccionRecibo, Usuarios usuario) {
        try {
            ModificarIrGUI modificar = new ModificarIrGUI(inspeccionRecibo, usuario);
            modificar.setVisible(true);
            modificar.setLocationRelativeTo(null);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
            manejarExcepcion("Error al Abrir ModificarIrGUI", ex);
        }
    }

    public int getIdHI(Connection conexion, InspeccionReciboM irm) throws SQLException {
        String sqlInsertIr = "SELECT id_ir FROM inspeccionrecibo WHERE fechaFactura=? AND noFactura=? AND noPedido=? AND noRollo=? AND pzKg=? AND noHoja=?";
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
            JOptionPane.showMessageDialog(null, "Error al OBTERNER EL ID: " + ex.getMessage());
        }
        return 0;
    }

    public int getData(Connection conexion, int id) throws SQLException {
        String sqlInsertIr = "SELECT * FROM inspeccionrecibo WHERE id_ir=?";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
            sqlInsert.setInt(1, id);
            ResultSet rs = sqlInsert.executeQuery();
            return id;
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    public void abrirModificarIrGUI(InspeccionReciboM inspeccionRecibo, Usuarios usuario, String rutaArchivo) {
        try {
            ModificarIrGUI modificar = new ModificarIrGUI(inspeccionRecibo, usuario, rutaArchivo);
            modificar.setVisible(true);
            modificar.setLocationRelativeTo(null);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abrirEspecificacionesGUI(Usuarios usuario, DatosIRM dirm, InspeccionReciboM inspeccionRecibo, JTable tblAnchoLargo, JTable tblRugosidadDureza) {
        try {
            EspecificacionesGUI ir = new EspecificacionesGUI(usuario, dirm, inspeccionRecibo, tblAnchoLargo, tblRugosidadDureza);
            ir.setVisible(true);
            ir.setLocationRelativeTo(null);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(HojaInstruccionGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> recuperarDescripciones(Connection conexion, InspeccionReciboM inspeccionRecibo) {
        List<String> descripciones = new ArrayList<>();
        try {
            PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM materiaprimasc WHERE calibre LIKE ? AND descripcionMP LIKE ?");
            consulta.setString(1, "%" + inspeccionRecibo.getCalibre().substring(0, 2) + "%");
            consulta.setString(2, "%" + inspeccionRecibo.getpLamina() + "%");

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                String descripcion = resultado.getString("descripcionMP");
                descripciones.add(descripcion);
            }
        } catch (SQLException e) {
            manejarExcepcion("Error al cargar las descripciones: ", e);
        }
        return descripciones;
    }

    public List<String> recuperarMedidas(Connection conexion, InspeccionReciboM inspeccionRecibo) {
        List<String> medidas = new ArrayList<>();
        try {
            PreparedStatement consulta = conexion.prepareStatement(SQL_CONSULTA_CALIBRE);
            consulta.setString(1, "%" + inspeccionRecibo.getCalibre().substring(0, 2) + "%");
            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                String medida = resultado.getString("medidas");
                medidas.add(medida);
            }
        } catch (SQLException e) {
            manejarExcepcion("Error al cargar las medidas: ", e);
        }

        return medidas;
    }

    public List<String> recuperarMedidasCalibre(Connection conexion, InspeccionReciboM inspeccionRecibo) {
        List<String> medidasCalibre = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SQL_CONSULTA_MEDIDAS_CALIBRE)) {
            consulta.setString(1, "%" + inspeccionRecibo.getCalibre().substring(0, 2) + "%");
            ResultSet resultado2 = consulta.executeQuery();

            while (resultado2.next()) {
                medidasCalibre.add(resultado2.getString("calibre") + "   " + resultado2.getString("medidas"));
            }
        } catch (SQLException e) {
            manejarExcepcion("Error al cargar las medidas del calibre: ", e);
        }

        return medidasCalibre;
    }

    public List<String> recuperarEspecificaciones(Connection conexion, String calibre) {
        List<String> especificaciones = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SQL_CONSULTA_ESPECIFICACION)) {
            consulta.setString(1, calibre);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                String especificacion = resultado.getString("especificacion");
                especificaciones.add(especificacion);
            }
        } catch (SQLException e) {
            manejarExcepcion("Error al cargar las medidas del calibre: ", e);
        }

        return especificaciones;
    }

    public EspecificacionM recuperarReferenciasEspecificacion(Connection conexion, String especificacionTecnica) {
        EspecificacionM especificacion = new EspecificacionM();
        try {
            PreparedStatement consulta = conexion.prepareStatement(SQL_CONSULTA_REF_ESPECIFICACION);
            consulta.setString(1, especificacionTecnica);
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) { // Se obtiene la información de la bd
                especificacion.setEspecificacion(resultado.getString("Especificacion"));
                especificacion.setCodigo(resultado.getString("codigoET"));
                especificacion.setFechaEmision(resultado.getString("fechaEmision"));
                especificacion.setFechaRevision(resultado.getString("fechaRevision"));
                especificacion.setNoRev(resultado.getString("noRev"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HojaInstruccionGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return especificacion;
    }

    public void validarArchivos(String rutaArchivoCertificado, String rutaArchivoFactura, String rutaArchivoHojaInstruccion, InspeccionReciboM inspeccionRecibo) {
        // Verifica si la ruta del archivo de certificado no es nula o vacía antes de cargar
        if (rutaArchivoCertificado != null && !rutaArchivoCertificado.isEmpty()) {
            cargarArchivo(rutaArchivoCertificado, inspeccionRecibo::setCertificadopdf);
        }

        // Verifica si la ruta del archivo de factura no es nula o vacía antes de cargar
        if (rutaArchivoFactura != null && !rutaArchivoFactura.isEmpty()) {
            cargarArchivo(rutaArchivoFactura, inspeccionRecibo::setFacturapdf);
        }

        if (rutaArchivoHojaInstruccion != null && !rutaArchivoHojaInstruccion.isEmpty()) {
            // Archivo de Hoja de Instrucción
            cargarArchivo(rutaArchivoHojaInstruccion, inspeccionRecibo::setHojaIns);
        }
    }

}
