package Servicios;

import InspeccionRecibo.AgregarCalibreHIGUI;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    private GeneradorExcel excel = new GeneradorExcel(); // Servicio para trabajar con archivos excel 
    final String SQL_CONSULTA_REF_ESPECIFICACION = "SELECT Especificacion, codigoET, fechaEmision, fechaRevision, noRev FROM especificacionesir WHERE Especificacion=?";
    final String SQL_CONSULTA_ESPECIFICACION = "SELECT especificacion FROM calibresir WHERE calibre = ?";
    final String SQL_CONSULTA_MEDIDAS_CALIBRE = "SELECT DISTINCT calibre, medidas FROM calibresir WHERE calibre LIKE ? ORDER BY calibre";
    final String SQL_CONSULTA_CALIBRE = "SELECT DISTINCT medidas FROM calibresir WHERE calibre LIKE ?";
    final String SQL_CONSULTA_INSPECTORES = "SELECT nombre FROM usuarios WHERE id_tipo=?";
    final String SELECT_NOMBRE_PROVEEDORES_SQL = "SELECT DISTINCT nombre FROM proveedores";
    final String SELECT_NO_HOJA_INSTRUCCION_SQL = "SELECT noHoja FROM inspeccionrecibo WHERE noHoja LIKE ? ORDER BY noHoja DESC LIMIT 1";
    final String SELECT_ID_INSPECCION_RECIBO_SQL = "SELECT id FROM inspeccionrecibo WHERE calibre=? AND fechaFactura=? AND noRollo=? AND pzKg=?";
    final String SELECT_CERTIFICADO_SQL = "SELECT pdfCertificado, nombreCert FROM inspeccionrecibo WHERE noHoja = ?";
    final String SELECT_FACTURA_SQL = "SELECT pdfFactura, nombreFact FROM inspeccionrecibo WHERE noHoja = ?";
    final String SELECT_INSPECCION_RECIBO_SQL = "SELECT * FROM inspeccionrecibo noHoja LIKE ? OR proveedor LIKE ? OR noFactura LIKE ? OR calibre LIKE ? OR noRollo LIKE ? OR LIMIT ?, ?";
    final String INSERT_INTO_INSPECCION_RECIBO = "INSERT INTO inspeccionrecibo(fechaFactura, proveedor, noFactura, noPedido, calibre, pLamina, noRollo, pzKg, estatus, noHoja, nombreHJ, nombreFact, NombreCert, rutaFactura, rutaCertificado, rutaHojaInstruccion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final String DELETE_INSPECCION_RECIBO_SQL = "DELETE FROM inspeccionrecibo WHERE id = ?";
    final String SELECT_COUNT_INSPECCION_RECIBO = "SELECT COUNT(*) FROM inspeccionrecibo WHERE noHoja LIKE ? OR proveedor LIKE ? OR noFactura LIKE ? OR calibre LIKE ? OR noRollo LIKE ?";
    final String SELECT_HOJA_INSTRUCCION_BY_NO_ROLLO = "SELECT hojaInstruccion FROM inspeccionrecibo WHERE noRollo = ? LIMIT 1";
    final String UPDATE_INSPECCION_RECIBO_SQL = "UPDATE inspeccionrecibo SET fechaFactura=?, proveedor=?, noFactura=?, noPedido=?, calibre=?, pLamina=?, noRollo=?, pzKg=?, estatus=?, noHoja=?, nombreHJ = ?, nombreFact = ?, nombreCert = ?, rutaFactura = ?, rutaCertificado = ?, rutaHojaInstruccion = ? WHERE id=?";

    public String direcciomImg = "img\\jc.png";

    public final String DIRECCION_IMG = "img\\jc.png";
    private static final String SQL_INSERCION_CALIBRE_IR = "INSERT INTO calibresir(calibre,medidas,especificacion) VALUES (?,?,?)";
    private static final String SQL_INSERCION_DMP = "INSERT INTO materiaprimasc(calibre,descripcionMP,calibreLamina) VALUES (?,?,?)";
    private static final String SQL_CONSULTA_INSPECCION_RECIBO = "SELECT * FROM inspeccionrecibo";
    private final String SQL_CONSULTA_ESPECIFICACIONES_IR = "SELECT Especificacion FROM especificacionesir";

    public void agregarCalibre(Connection conexion, CalibreIRM cirm) throws SQLException {
        try (PreparedStatement sqlInsert = conexion.prepareStatement(SQL_INSERCION_CALIBRE_IR)) {
            sqlInsert.setString(1, cirm.getCalibre());
            sqlInsert.setString(2, cirm.getMedidas());
            sqlInsert.setString(3, cirm.getEspecificacion());
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

    public void abrirEspecificacionesGUI(Usuarios usr, InspeccionReciboM irm) {
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

    public void agregarInspeccion(Connection conexion, InspeccionReciboM irm) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_HOJA_INSTRUCCION_BY_NO_ROLLO)) {
            consulta.setString(1, irm.getNoRollo());
            try (ResultSet rs = consulta.executeQuery()) {
                String nuevaRutaHJ = null;

                if (rs.next()) { // Obtiene la hoja de instrucción si existe
                    String hojaInstruccion = rs.getString("rutaHojaInstruccion");
                    if (hojaInstruccion != null && !hojaInstruccion.isEmpty()) {
                        nuevaRutaHJ = editarHojaInstruccion(hojaInstruccion, irm);
                    }
                }

                insertarInspeccion(conexion, irm, nuevaRutaHJ); // Inserta el nuevo registro
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al guardar el registro: ", ex);
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarInspeccion(Connection conexion, InspeccionReciboM irm, String nuevaRutaHJ) throws SQLException {
        try (PreparedStatement pstmtInsert = conexion.prepareStatement(INSERT_INTO_INSPECCION_RECIBO)) {
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
            pstmtInsert.setString(11, irm.getNombreHojaInstruccion());
            pstmtInsert.setString(12, irm.getNombreFact());
            pstmtInsert.setString(13, irm.getNombreCert());
            pstmtInsert.setString(14, irm.getRutaFactura());
            pstmtInsert.setString(15, irm.getRutaCertificado());
            pstmtInsert.setString(16, nuevaRutaHJ != null ? nuevaRutaHJ : irm.getRutaHojaInstruccion());

            pstmtInsert.executeUpdate();
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

    public List<InspeccionReciboM> obtenerTodasInspecciones(Connection conexion, int page, int limit, String filtro) throws SQLException {
        List<InspeccionReciboM> listaIr = new ArrayList<>();
        int limite = (page - 1) * limit;
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_INSPECCION_RECIBO_SQL)) {
            consulta.setString(1, "%" + filtro + "%");
            consulta.setString(2, "%" + filtro + "%");
            consulta.setString(3, "%" + filtro + "%");
            consulta.setString(4, "%" + filtro + "%");
            consulta.setString(5, "%" + filtro + "%");
            consulta.setInt(6, limite);
            consulta.setInt(7, limit);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                InspeccionReciboM ir = new InspeccionReciboM(
                        resultado.getInt("id"),
                        resultado.getString("fechaFactura"),
                        resultado.getString("proveedor"),
                        resultado.getString("noFactura"),
                        resultado.getString("noPedido"),
                        resultado.getString("calibre"),
                        resultado.getString("pLamina"),
                        resultado.getString("noRollo"),
                        resultado.getString("pzKg"),
                        resultado.getString("estatus"),
                        resultado.getString("noHoja"),
                        resultado.getString("nombreHJ"),
                        resultado.getString("nombreFact"),
                        resultado.getString("nombreCert"),
                        resultado.getString("rutaFactura"),
                        resultado.getString("rutaCertificado"),
                        resultado.getString("rutaHojaInstruccion")
                );
                listaIr.add(ir);
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL: ", ex);
        }
        return listaIr;
    }

    public List<InspeccionReciboM> obtenerTodasInspecciones(Connection conexion) throws SQLException {
        List<InspeccionReciboM> listaIr = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM inspeccionrecibo")) {
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                InspeccionReciboM ir = new InspeccionReciboM(
                        resultado.getInt("id"),
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
                        null,
                        null,
                        null,
                        resultado.getString("nombreHJ"),
                        resultado.getString("nombreFact"),
                        resultado.getString("nombreCert")
                );
                listaIr.add(ir);
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL: ", ex);
        }
        return listaIr;
    }

    public List<InspeccionReciboM> recuperarRegistrosIR(Connection conexion) throws SQLException {
        List<InspeccionReciboM> listaIr = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_INSPECCION_RECIBO_SQL);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                InspeccionReciboM ir = new InspeccionReciboM(
                        resultado.getInt("id"),
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

    public void eliminarRegistro(Connection conexion, InspeccionReciboM inspeccionRecibo) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_INSPECCION_RECIBO_SQL);
            ps.setInt(1, inspeccionRecibo.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar el registro: ", ex);
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modificarRegistro(Connection conexion, InspeccionReciboM irm, InspeccionReciboM irm2) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ID_INSPECCION_RECIBO_SQL);
                PreparedStatement uConsulta = conexion.prepareStatement(UPDATE_INSPECCION_RECIBO_SQL)) {
            consulta.setString(1, irm2.getCalibre());
            consulta.setString(2, irm2.getFechaFactura());
            consulta.setString(3, irm2.getNoRollo());
            consulta.setString(4, irm2.getPzKg());
            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                uConsulta.setString(1, irm.getFechaFactura());
                uConsulta.setString(2, irm.getProveedor());
                uConsulta.setString(3, irm.getNoFactura());
                uConsulta.setString(4, irm.getNoPedido());
                uConsulta.setString(5, irm.getCalibre());
                uConsulta.setString(6, irm.getpLamina());
                uConsulta.setString(7, irm.getNoRollo());
                uConsulta.setString(8, irm.getPzKg());
                uConsulta.setString(9, irm.getEstatus());
                uConsulta.setString(10, irm.getNoHoja());
                uConsulta.setString(11, irm.getNombreHojaInstruccion());
                uConsulta.setString(12, irm.getNombreFact());
                uConsulta.setString(13, irm.getNombreCert());
                uConsulta.setString(14, irm.getRutaFactura());
                uConsulta.setString(15, irm.getRutaCertificado());
                uConsulta.setString(16, irm.getRutaHojaInstruccion());
                uConsulta.setInt(17, id);
                uConsulta.executeUpdate();
            } else {
                System.out.println("No se encontró el ID.");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
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

    public boolean existeNoRollo(Connection conexion, String noRollo) throws ClassNotFoundException {
        try {
            String sql = "SELECT COUNT(noRollo) FROM inspeccionrecibo WHERE noRollo = ?";
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
        AgregarIrGUI agregarGUI = new AgregarIrGUI(usuario);
        agregarGUI.setVisible(true);
        agregarGUI.setLocationRelativeTo(null);

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
        HojaInstruccionGUI hjGUI = new HojaInstruccionGUI(usr, irm);
        mostrarVentana(hjGUI);

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

        HojaInstruccionGUI2 hjGUI = new HojaInstruccionGUI2(usr, rutaArchivo, irm);
        mostrarVentana(hjGUI);
        rutaArchivo2 = hjGUI.getRutaArchivo();
        return rutaArchivo2;
    }

    public void manejarExcepcion(String msg, Exception ex) {
        JOptionPane.showMessageDialog(null, msg + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarVentana(JFrame frame) {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private String editarHojaInstruccion(String rutaHojaInstruccion, InspeccionReciboM irm) {
        FileInputStream fis = null;
        String nuevoArchivo = "";
        try {
            fis = new FileInputStream("\\\\" + Utilidades.SERVIDOR + "\\" + rutaHojaInstruccion);
            XSSFWorkbook workbook;
            String numeroStr;
            try {
                workbook = new XSSFWorkbook(fis);
                Sheet hoja1 = workbook.getSheetAt(0);

                // Número de Hoja
                String[] partes = irm.getNoHoja().split("/"); // Se divide el valor de NoHoja para solo obtener el número
                numeroStr = partes[1];
                int numero = Integer.parseInt(numeroStr);
                if (numero < 10) { // Eliminar ceros a la izquierda
                    numeroStr = String.valueOf(numero);
                }
                workbook.setSheetName(workbook.getSheetIndex(hoja1), numeroStr); // Cambiar el nombre de la Hoja con el número previamente obtenido

                // Modifica los campos con la información del nuevo registro
                excel.setDatosCeldas(hoja1, 5, 2, numeroStr); // Número de Hoja
                excel.setDatosCeldas(hoja1, 9, 8, irm.getFechaFactura()); // Fecha Factura
                excel.setDatosCeldas(hoja1, 9, 6, irm.getNoFactura()); // No. Factura
                excel.setDatosCeldas(hoja1, 13, 7, irm.getPzKg()); // Pz/Kg

                // Descripción
                Row fila3 = hoja1.getRow(9); // Obtén la fila
                Cell celda3 = fila3.getCell(1); // Obtén la celda en la fila
                String txtTexto = celda3.getStringCellValue();
                if (txtTexto.contains("HOJA") && irm.getpLamina().equals("CINTA")) {
                    txtTexto = txtTexto.replace("HOJA", "CINTA");
                } else if (txtTexto.contains("CINTA") && irm.getpLamina().equals("HOJA")) {
                    txtTexto = txtTexto.replace("CINTA", "HOJA");
                }
                celda3.setCellValue(txtTexto);

                nuevoArchivo = "\\\\" + Utilidades.SERVIDOR + "\\archivos\\InspeccionRecibo\\HojasInstruccion\\HI-" + workbook.getSheetIndex(hoja1) + "-" + irm.getFechaFactura();

                // Crear un nuevo archivo con los cambios
                try (FileOutputStream fos = new FileOutputStream(nuevoArchivo)) {
                    workbook.write(fos); // Escribir los cambios en el nuevo archivo
                }

            } catch (IOException ex) {
                Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            return nuevoArchivo;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return nuevoArchivo;
    }

    public String obtenerValorComboBox(JComboBox<String> comboBox) {
        return comboBox.getSelectedItem() != null ? comboBox.getSelectedItem().toString() : "";
    }

    public String obtenerTexto(JTextField textField) {
        return textField.getText().trim();
    }

    public void abrirModificarIrGUI(InspeccionReciboM inspeccionRecibo, Usuarios usuario) {
        ModificarIrGUI modificar = new ModificarIrGUI(inspeccionRecibo, usuario);
        modificar.setVisible(true);
        modificar.setLocationRelativeTo(null);

    }

    public int getIdHI(Connection conexion, InspeccionReciboM irm) throws SQLException {
        String sqlInsertIr = "SELECT id FROM inspeccionrecibo WHERE fechaFactura=? AND noFactura=? AND noPedido=? AND noRollo=? AND pzKg=? AND noHoja=?";
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
                id = rs.getInt("id");
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
        String sqlInsertIr = "SELECT * FROM inspeccionrecibo WHERE id=?";
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
        EspecificacionesGUI ir = new EspecificacionesGUI(usuario, dirm, inspeccionRecibo, tblAnchoLargo, tblRugosidadDureza);
        ir.setVisible(true);
        ir.setLocationRelativeTo(null);
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

    public void validarArchivos(String rutaCertificado, String rutaFactura, String rutaHojaInstruccion, InspeccionReciboM inspeccionRecibo) {
        // Valida y asigna rutas
        asignarRutaSiValida(rutaCertificado, inspeccionRecibo::setRutaCertificado);
        asignarRutaSiValida(rutaFactura, inspeccionRecibo::setRutaFactura);
        asignarRutaSiValida(rutaHojaInstruccion, inspeccionRecibo::setRutaHojaInstruccion);
    }

    private void asignarRutaSiValida(String ruta, Consumer<String> setter) {
        if (ruta != null && !ruta.isEmpty()) {
            setter.accept(ruta);
        }
    }

    public void abrirAgregarCalibreHIGUI() {
        AgregarCalibreHIGUI irGUI = new AgregarCalibreHIGUI();
        irGUI.setVisible(true);
        irGUI.setLocationRelativeTo(null);
    }

    public String generarCodigoDeHoja() {
        SQL sql = new SQL();
        int year = obtenerAnioActual();
        String codigoHoja = sql.getCodigoHoja(SELECT_NO_HOJA_INSTRUCCION_SQL, year);
        String nuevoCodigo = sql.obtenerSiguiente(codigoHoja, String.valueOf(year));
        return nuevoCodigo;
    }

    public int obtenerAnioActual() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public void migrarArchivos(Connection conexion) {
        String carpetaDestino = "C:/xampp/htdocs/archivos/InspeccionRecibo/HojasInstruccion/";
        File carpeta = new File(carpetaDestino);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT id, nombreHJ, hojaInstruccion FROM inspeccionrecibo WHERE hojaInstruccion IS NOT NULL AND hojaInstruccion != '' ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idp = rs.getInt("id");
                byte[] documento = rs.getBytes("hojaInstruccion");

                // Guarda el archivo en el sistema de archivos
                String nombreArchivo = rs.getString("nombreHJ"); // Usa un formato adecuado
                File archivoDestino = new File(carpetaDestino + nombreArchivo);
                Files.write(archivoDestino.toPath(), documento);

                // Actualiza la ruta en la base de datos
                PreparedStatement psUpdate = conexion.prepareStatement("UPDATE inspeccionrecibo SET rutaHojaInstruccion = ? WHERE id = ?");
                psUpdate.setString(1, "archivos/InspeccionRecibo/HojasInstruccion/" + nombreArchivo);
                psUpdate.setInt(2, idp);
                psUpdate.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int contarRegistros(Connection conexion, String filtro) {
        String sql;
        boolean filtrar = filtro != null && !filtro.trim().isEmpty(); 
        if (filtrar) {
            sql = SELECT_COUNT_INSPECCION_RECIBO;
        } else {
            sql = "SELECT COUNT(*) FROM inspeccionRecibo";
        }
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_COUNT_INSPECCION_RECIBO)) {
            String filtroLike = "%" + filtro + "%";
            consulta.setString(1, filtroLike); // Para número de hoja
            consulta.setString(2, filtroLike); // Para proveedor
            consulta.setString(3, filtroLike); // Para factura
            consulta.setString(4, filtroLike); // Para calibre
            consulta.setString(5, filtroLike); // Para número de rollo

            // Ejecutar la consulta y obtener el resultado
            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.first()) {
                    return resultado.getInt(1); // Retorna la cantidad de registros
                }
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL: ", ex);
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /*
    
    public int contarRegistros(Connection conexion, String filtro) {
    String sql = "SELECT COUNT(*) FROM tabla WHERE columna1 LIKE ? OR columna2 LIKE ?";
    try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
        // Establecer los valores del filtro
        String filtroLike = "%" + filtro + "%";
        consulta.setString(1, filtroLike); // Para columna1
        consulta.setString(2, filtroLike); // Para columna2

        // Ejecutar la consulta y obtener el resultado
        try (ResultSet resultado = consulta.executeQuery()) {
            if (resultado.first()) {
                return resultado.getInt(1); // Retorna la cantidad de registros
            }
        }
    } catch (SQLException ex) {
        Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL: ", ex);
    }
    return 0; // Si hay un error, retorna 0
}
    
     */
}
