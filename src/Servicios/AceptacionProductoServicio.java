package Servicios;

import AceptacionProducto.AceptacionProductoGUI2;
import AceptacionProducto.AceptacionProductoGUI;
import AceptacionProducto.AgregarVariableGUI;
import AceptacionProducto.ModificarAPGUI;
import AceptacionProducto.ModificarRDGUI;
import AceptacionProducto.RetencionDimensionalGUI;
import Modelos.AceptacionPc1;
import Modelos.AceptacionPc2;
import Modelos.AceptacionPc3;
import Modelos.AceptacionProductoM;
import Modelos.DatosFilaRD;
import Modelos.Usuarios;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AceptacionProductoServicio {

    final String INSERT_INTO_ACEPTACION_PC1 = "INSERT INTO aceptacionpc1(componente, fecha, noRollo, inspVisual, observacion, noParte, noOps) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_ACEPTACION_PC2 = "INSERT INTO aceptacionpc2(componente, fecha, noOrden, tamLote, tamMta, inspector, turno, disp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_ACEPTACION_PC3 = "INSERT INTO aceptacionpc3(componente, noOp, noTroquel, fecha, variable, especificacion, valor, procesoCritico) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_PRODUCTOS_ACTIVIDADES_PC = "INSERT INTO productosactividadespc(nombreProducto) VALUES (?)";
    final String INSERT_INTO_ACEPTACION_PRODUCTO = "INSERT INTO aceptacionproducto(componente, rutaArchivo) VALUES (?, ?)";
    final String SELECT_NO_ROLLO_SQL = "SELECT noRollo FROM inspeccionRecibo";
    final String SELECT_ACEPTACION_PC1_BY_COMPONENTE = "SELECT * FROM aceptacionpc1 WHERE componente = ?";
    final String SELECT_ACEPTACION_PC2_BY_COMPONENTE = "SELECT * FROM aceptacionpc2 WHERE componente = ?";
    final String SELECT_ACEPTACION_PC3_BY_COMPONENTE = "SELECT * FROM aceptacionpc3 WHERE componente = ? ORDER BY noOp, STR_TO_DATE(fecha, '%d/%m/%Y')";
    final String SELECT_ACEPTACION_PRODUCTO_ORDER_BY_COMPONENTE = "SELECT * FROM aceptacionProducto ORDER BY componente";
    final String SELECT_ID_FROM_ACEPTACION_PC3 = "SELECT id FROM aceptacionpc3 WHERE noOp = ? AND fecha = ? AND variable = ? AND especificacion = ? AND valor = ?";
    final String SELECT_NOMBRE_PRODUCTO = "SELECT nombreProducto FROM productosactividadespc";
    final String SELECT_ESPECIFICACION_FROM_DESCRIPCION_PC3 = "SELECT t1.especificacion FROM descripcion3pc AS t1 JOIN descripcion2pc AS t2 ON t1.componente = t2.componente AND t1.noPartesP = t2.noPartesP AND t1.no = t2.no WHERE t2.producto LIKE ? AND t1.componente = ? GROUP BY t1.especificacion, t2.producto";
    final String SELECT_NOMBRE_PRODUCTO_JOIN_DECRIPCION_PC2 = "SELECT pa.nombreProducto FROM productosactividadespc AS pa JOIN descripcion2pc AS d2 ON pa.nombreProducto = d2.producto WHERE d2.componente = ?";
    final String SELECT_ACEPTACION_PC2 = "SELECT noOrden, fecha, tamLote, tamMta, inspector, turno, disp FROM aceptacionpc2 WHERE fecha = ? AND componente = ? GROUP BY noOrden, fecha, tamLote, tamMta, inspector, turno, disp";
    final String SELECT_ACEPTACION_PRODUCTO = "SELECT * FROM aceptacionproducto WHERE componente = ?";
    final String SELECT_HOJA_INSTRUCCION_SQL = "SELECT retencionDimensionalpdf FROM aceptacionproducto WHERE componente = ?";
    final String SELECT_ACEPTACION_PC1 = "SELECT * FROM aceptacionpc1 WHERE componente = ?";
    final String SELECT_FILA_FROM_ESPECIFICACIONES_IR = "SELECT fila FROM especificacionesir WHERE especificacion = ?";
    final String SELECT_COUNT_FROM_ACEPTACION_PC1 = "SELECT COUNT(*) FROM aceptacionpc1 WHERE componente = ? AND fecha = ?";
    final String SELECT_COUNT_FROM_ACEPTACION_PC2 = "SELECT COUNT(*) FROM aceptacionpc2 WHERE componente = ? AND fecha = ? AND noOrden = ?";
    final String SELECT_COUNT_FROM_ACEPTACION_PC3 = "SELECT COUNT(*) FROM aceptacionpc3 WHERE componente = ? AND noOp = ? AND fecha = ? AND variable = ? AND valor = ?";
    final String SELECT_FECHA_ACEPTACION_PC1 = "SELECT fecha FROM aceptacionpc1 WHERE componente = ?";
    final String SELECT_ACEPTACION_PC1_JOIN_ACEPTACION_PC2 = "SELECT a1.id AS id_pc1, a2.id AS id_pc2, a1.*, a2.* FROM aceptacionpc1 a1 INNER JOIN aceptacionpc2 a2 ON a1.fecha = a2.fecha AND a1.componente = a2.componente WHERE a1.componente = ? AND a1.fecha = ?";
    final String SELECT_NO_ORDEN_FROM_ACEPTACION_PC2 = "SELECT noOrden FROM aceptacionpc2 WHERE componente = ? AND fecha = ?";
    final String UPDATE_ACEPTACION_PC3 = "UPDATE aceptacionpc3 SET noOp = ?, fecha = ?, variable = ?, especificacion = ?, valor = ? WHERE id = ?";
    final String UPDATE_ACEPTACION_PRODUCTO = "UPDATE aceptacionproducto SET rutaArchivo = ? WHERE componente = ?";
    final String UPDATE_ACEPTACION_PC1 = "UPDATE aceptacionpc1 SET noRollo = ?, inspVisual = ?, observacion = ? WHERE id = ? ";
    final String UPDATE_ACEPTACION_PC2 = "UPDATE aceptacionpc2 SET noOrden = ?, tamLote = ?, tamMta = ?, inspector = ?, turno = ?, disp = ? WHERE id = ?";
    final String UPDATE_NO_ORDEN_ACEPTACION_PC2 = "UPDATE aceptacionpc2 SET noOrden = ? WHERE componente = ? AND fecha = ?";
    final String UPDATE_NO_OPS_ACEPTACION_PC1 = "UPDATE aceptacionpc1 SET noOps = ? WHERE componente = ? ";
    final String DELETE_ACEPTACION_PC3 = "DELETE FROM aceptacionpc3 WHERE fecha = ? AND noOp = ? AND noTroquel = ? AND variable = ? AND especificacion = ? AND valor = ?";
    final String SELECT_COMPONENTE_FROM_ANTECEDENTES_FAMILIA = "SELECT TRIM(componente) AS componente FROM antecedentesfamilia";
    final String DELETE_ACEPTACION_PRODUCTO = "DELETE FROM aceptacionproducto WHERE componente = ?";
    final String DELETE_ACEPTACION_PC1 = "DELETE FROM aceptacionpc1 WHERE componente = ?";
    final String DELETE_ACEPTACION_PC2 = "DELETE FROM aceptacionpc2 WHERE componente = ?";
    final String DELETE_ACEPTACION_PC3_WHERE_COMPONENTE = "DELETE FROM aceptacionpc3 WHERE componente = ?";

    public void abrirAceptacionProductoGUI(Usuarios usr) {
        AceptacionProductoGUI apGUI = new AceptacionProductoGUI(usr);
        mostrarVentana(apGUI);
    }

    public void abrirAceptacionProductoGUI2(Usuarios usr) {
        AceptacionProductoGUI2 ap2 = new AceptacionProductoGUI2(usr);
        mostrarVentana(ap2);
    }

    public void abrirAgregarVariableGUI(Usuarios usr, AceptacionPc1 apc1, AceptacionPc2 apc2) {
        AgregarVariableGUI avGUI = new AgregarVariableGUI(usr, apc1, apc2);
        mostrarVentana(avGUI);
    }

    public void abrirRetencionDimensionalGUI(Usuarios usr, AceptacionPc1 apc1, AceptacionPc2 apc2) {
        RetencionDimensionalGUI rdGUI = new RetencionDimensionalGUI(usr, apc1, apc2);
        mostrarVentana(rdGUI);
    }

    public void abrirRetencionDimensionalGUI(Usuarios usr, AceptacionPc1 apc1) {
        RetencionDimensionalGUI rdGUI = new RetencionDimensionalGUI(usr, apc1);
        mostrarVentana(rdGUI);
    }

    public void abrirRetencionDimensionalGUI(Usuarios usr) {
        RetencionDimensionalGUI rdGUI = new RetencionDimensionalGUI(usr);
        mostrarVentana(rdGUI);
    }

    public void abrirModificarAPGUI(Usuarios usr, AceptacionProductoM aceptacionProducto) {
        ModificarAPGUI moGUI = new ModificarAPGUI(usr, aceptacionProducto);
        mostrarVentana(moGUI);
    }

    public void abrirModificarRDGUI(AceptacionPc3 filaSeleccionada, Usuarios usr, AceptacionPc1 apc1, AceptacionPc2 apc2) {
        ModificarRDGUI modificar = new ModificarRDGUI(filaSeleccionada, usr, apc1, apc2);
        mostrarVentana(modificar);
    }

    public void abrirUrlEnNavegador(String url) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI(url));
    }

    public void actualizarNoOps(Connection conexion, AceptacionPc1 apc1) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement(UPDATE_NO_OPS_ACEPTACION_PC1)) {
            ps.setString(1, apc1.getNoOps());
            ps.setString(2, apc1.getComponente());
            ps.executeUpdate();
        }
    }

    public void agregarAceptacionPc1(Connection conexion, AceptacionPc1 ap1) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(INSERT_INTO_ACEPTACION_PC1)) {
            consulta.setString(1, ap1.getComponente());
            consulta.setString(2, ap1.getFecha());
            consulta.setString(3, ap1.getNoRollo());
            consulta.setString(4, ap1.getInspVisual());
            consulta.setString(5, ap1.getObservacion());
            consulta.setString(6, ap1.getNoParte());
            consulta.setString(7, ap1.getNoOps());
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL de inserción: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarAceptacionPc2(Connection conexion, AceptacionPc2 ap2) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(INSERT_INTO_ACEPTACION_PC2)) {
            consulta.setString(1, ap2.getComponente());
            consulta.setString(2, ap2.getFecha());
            consulta.setString(3, ap2.getNoOrden());
            consulta.setString(4, ap2.getTamLote());
            consulta.setString(5, ap2.getTamMta());
            consulta.setString(6, ap2.getInspector());
            consulta.setString(7, ap2.getTurno());
            consulta.setString(8, ap2.getDisp());
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL de inserción: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarAceptacionPc3(Connection conexion, List<AceptacionPc3> listaAceptacionPc3) throws SQLException {
        for (int i = 0; i < listaAceptacionPc3.size(); i++) {
            try (PreparedStatement consulta = conexion.prepareStatement(INSERT_INTO_ACEPTACION_PC3)) {
                consulta.setString(1, listaAceptacionPc3.get(i).getComponente());
                consulta.setString(2, listaAceptacionPc3.get(i).getNoOp());
                consulta.setString(3, listaAceptacionPc3.get(i).getNoTroquel());
                consulta.setString(4, listaAceptacionPc3.get(i).getFecha());
                consulta.setString(5, listaAceptacionPc3.get(i).getVariable());
                consulta.setString(6, listaAceptacionPc3.get(i).getEspecificacion());
                consulta.setString(7, listaAceptacionPc3.get(i).getValor());
                consulta.setInt(8, listaAceptacionPc3.get(i).getProcesoCritico());
                consulta.executeUpdate();
            } catch (SQLException ex) {
                Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL de inserción: ", ex);
                Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void agregarNuevaVariable(Connection conexion, String nuevaVariable) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(INSERT_INTO_PRODUCTOS_ACTIVIDADES_PC)) {
            consulta.setString(1, nuevaVariable);
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL de inserción: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<AceptacionPc2> buscarObjetoEnAp2mPorFecha(Connection conexion, String fecha, String componente, AceptacionPc2 apc2) throws SQLException {
        List<AceptacionPc2> ap2c = new ArrayList<>();

        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ACEPTACION_PC2)) {
            consulta.setString(1, fecha);
            consulta.setString(2, componente);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                apc2 = new AceptacionPc2("", "",
                        resultado.getString("fecha"), resultado.getString("noOrden"), resultado.getString("tamLote"),
                        resultado.getString("tamMta"), resultado.getString("inspector"), resultado.getString("turno"), resultado.getString("disp"));
                ap2c.add(apc2);
            }
        }
        return ap2c;
    }

    public void cargarNoRollos(Connection conexion, JComboBox cbxNoRollo) {
        try {
            List<String> rollos = obtenerNoRollos(conexion);
            rollos.forEach(cbxNoRollo::addItem);
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Surgio un error al cargar los No's de Rollos: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ejecutarArchivoXLSX(Connection conexion, String componente, int column) throws ClassNotFoundException, SQLException {
        ResultSet rs;
        byte[] b = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(SELECT_HOJA_INSTRUCCION_SQL);
            ps.setString(1, componente);
            rs = ps.executeQuery();
            while (rs.next()) {
                b = rs.getBytes(1);
            }
            try (InputStream bos = new ByteArrayInputStream(b)) {
                int tamanoInput = bos.available();
                byte[] datosXLSX = new byte[tamanoInput];
                bos.read(datosXLSX, 0, tamanoInput);
                try (OutputStream out = new FileOutputStream("-" + componente + ".xlsx")) {
                    out.write(datosXLSX);
                    File archivoXLSX = new File("-" + componente + ".xlsx");
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(archivoXLSX);
                    }
                }
            }
            ps.close();
            rs.close();
        } catch (IOException | NumberFormatException | SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al ejecutar el archivo excel: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarAceptacionPc3(Connection conexion, AceptacionPc3 apc3) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_ACEPTACION_PC3);
            ps.setString(1, apc3.getFecha());
            ps.setString(2, apc3.getNoOp());
            ps.setString(3, apc3.getNoTroquel());
            ps.setString(4, apc3.getVariable());
            ps.setString(5, apc3.getEspecificacion());
            ps.setString(6, apc3.getValor());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar la información: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarAceptacionProducto(Connection conexion, AceptacionProductoM aceptacionProducto) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_ACEPTACION_PRODUCTO);
            ps.setString(1, aceptacionProducto.getComponente());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar la información de aceptacionProducto: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_ACEPTACION_PC1);
            ps.setString(1, aceptacionProducto.getComponente());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar la información de aceptacionPc1: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_ACEPTACION_PC2);
            ps.setString(1, aceptacionProducto.getComponente());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar la información de aceptacionPc2: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_ACEPTACION_PC3_WHERE_COMPONENTE);
            ps.setString(1, aceptacionProducto.getComponente());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar la información de aceptacionPc3: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean esValorValido(String especificacion, String txtValor) {
        String seleccion = especificacion;

        String valorIngresado = txtValor.trim();
        if (seleccion.contains("-")) {
            String[] valoresRango = seleccion.split("-");
            try {
                double valorMin = Double.parseDouble(valoresRango[0]);
                double valorMax = Double.parseDouble(valoresRango[1]);

                if (valorMin > valorMax) {
                    JOptionPane.showMessageDialog(null, "Los límites del rango no están en el orden correcto", "Alerta", JOptionPane.WARNING_MESSAGE);
                    return false;
                }

                double valor = Double.parseDouble(valorIngresado);
                if (valor < valorMin || valor > valorMax) {
                    JOptionPane.showMessageDialog(null, "El valor ingresado está fuera del rango seleccionado (" + seleccion + ")", "Alerta", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            } catch (NumberFormatException ex) {
                Utilidades.manejarExcepcion("Por favor, ingrese un valor numérico válido: ", ex);
                Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    public boolean existeRegistroPc1(Connection conexion, AceptacionPc1 apc1) throws SQLException {
        try (PreparedStatement ps = conexion.prepareCall(SELECT_COUNT_FROM_ACEPTACION_PC1)) {
            ps.setString(1, apc1.getComponente());
            ps.setString(2, apc1.getFecha());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean existeRegistroPc2(Connection conexion, AceptacionPc2 apc2) throws SQLException {
        try (PreparedStatement ps = conexion.prepareCall(SELECT_COUNT_FROM_ACEPTACION_PC2)) {
            ps.setString(1, apc2.getComponente());
            ps.setString(2, apc2.getFecha());
            ps.setString(3, apc2.getNoOrden());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean existeRegistroPc3(Connection conexion, AceptacionPc3 apc3) throws SQLException {
        try (PreparedStatement ps = conexion.prepareCall(SELECT_COUNT_FROM_ACEPTACION_PC3)) {
            ps.setString(1, apc3.getComponente());
            ps.setString(2, apc3.getNoOp());
            ps.setString(3, apc3.getFecha());
            ps.setString(4, apc3.getVariable());
            ps.setString(5, apc3.getValor());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<AceptacionPc3> filtrarNuevosDatos(Connection conexion, List<AceptacionPc3> listNuevosDatos) throws SQLException {
        List<AceptacionPc3> nuevosDatos = new ArrayList<>();
        for (AceptacionPc3 dato : listNuevosDatos) {
            if (!existeRegistroPc3(conexion, dato)) {
                nuevosDatos.add(dato);
            }
        }
        return nuevosDatos;
    }

    public Date formatearFecha(String fecha) {
        try {
            if (fecha == null) {
                return null;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.parse(fecha);
        } catch (ParseException ex) {
            Utilidades.manejarExcepcion("Error al formatear la fecha: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
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

    public void guardarArchivoRetencionDimensional(Connection conexion, AceptacionProductoM ap) throws SQLException {
        try (PreparedStatement sqlSelectStatement = conexion.prepareStatement(SELECT_ACEPTACION_PRODUCTO)) {
            sqlSelectStatement.setString(1, ap.getComponente());
            ResultSet resultSet = sqlSelectStatement.executeQuery();

            if (resultSet.next()) {
                try (PreparedStatement sqlUpdateStatement = conexion.prepareStatement(UPDATE_ACEPTACION_PRODUCTO)) {
                    sqlUpdateStatement.setString(1, ap.getRutaArchivo());
                    sqlUpdateStatement.setString(2, ap.getComponente());
                    sqlUpdateStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "El Archivo se actualizo Correctamente");
                }
            } else {
                try (PreparedStatement sqlInsert = conexion.prepareStatement(INSERT_INTO_ACEPTACION_PRODUCTO)) {
                    sqlInsert.setString(1, ap.getComponente());
                    sqlInsert.setString(2, ap.getRutaArchivo());
                    sqlInsert.executeUpdate();
                    JOptionPane.showMessageDialog(null, "El Archivo se genero y se guardo Correctamente");
                }
            }

        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL de inserción: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public byte[] leerArchivo(String rutaArchivo) throws IOException {
        File archivo = new File(rutaArchivo);
        byte[] pdf = new byte[(int) archivo.length()];
        if (archivo.exists()) {
            try (InputStream input = new FileInputStream(archivo)) {
                input.read(pdf);
            }
        }
        return pdf;
    }

    public void modificarAceptacionPc3(Connection conexion, AceptacionPc3 apc3, AceptacionPc3 apc32) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ID_FROM_ACEPTACION_PC3);
                PreparedStatement updateConsulta = conexion.prepareStatement(UPDATE_ACEPTACION_PC3)) {
            consulta.setString(1, apc32.getNoOp());
            consulta.setString(2, apc32.getFecha());
            consulta.setString(3, apc32.getVariable());
            consulta.setString(4, apc32.getEspecificacion());
            consulta.setString(5, apc32.getValor());

            ResultSet rs = consulta.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                updateConsulta.setString(1, apc3.getNoOp());
                updateConsulta.setString(2, apc3.getFecha());
                updateConsulta.setString(3, apc3.getVariable());
                updateConsulta.setString(4, apc3.getEspecificacion());
                updateConsulta.setString(5, apc3.getValor());
                updateConsulta.setInt(6, id);
                updateConsulta.executeUpdate();
            } else {
                System.out.println("No se encontró el ID.");
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL de inserción: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modificarInfoRetencionDimensional(Connection conexion, DatosFilaRD datosFila) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(UPDATE_ACEPTACION_PC1)) {
            consulta.setString(1, datosFila.getNoRollo());
            consulta.setString(2, datosFila.getInspVisual());
            consulta.setString(3, datosFila.getObservaciones());
            consulta.setInt(4, datosFila.getId_pc1());
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al modificar los datos: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        try (PreparedStatement consulta = conexion.prepareStatement(UPDATE_ACEPTACION_PC2)) {
            consulta.setString(1, datosFila.getNoOrden());
            consulta.setString(2, datosFila.getTamLote());
            consulta.setString(3, datosFila.getTamMta());
            consulta.setString(4, datosFila.getInsp());
            consulta.setString(5, datosFila.getTurno());
            consulta.setString(6, datosFila.getDisp());
            consulta.setInt(7, datosFila.getId_pc2());
            consulta.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al modificar los datos: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modificarOrden(Connection conexion, AceptacionPc2 apc2) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement(UPDATE_NO_ORDEN_ACEPTACION_PC2)) {
            ps.setString(1, apc2.getNoOrden());
            ps.setString(2, apc2.getComponente());
            ps.setString(3, apc2.getFecha());
            ps.executeUpdate();
        }
    }

    public void mostrarVentana(JFrame frame) {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public List<AceptacionPc1> obtenerAceptacionPc1(Connection conexion, String componente) throws SQLException {
        List<AceptacionPc1> listaAp1 = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ACEPTACION_PC1_BY_COMPONENTE)) {
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
                        resultado.getString("noOps")
                );
                listaAp1.add(ap1);
            }
        }
        return listaAp1;
    }

    public List<AceptacionPc2> obtenerAceptacionPc2(Connection conexion, String componente) throws SQLException {
        List<AceptacionPc2> listaAp2 = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ACEPTACION_PC2_BY_COMPONENTE)) {
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

    public List<AceptacionPc3> obtenerAceptacionPc3(Connection conexion, String componente) throws SQLException {
        List<AceptacionPc3> listaAp3 = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ACEPTACION_PC3_BY_COMPONENTE)) {
            consulta.setString(1, componente);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                AceptacionPc3 ap3 = new AceptacionPc3(
                        resultado.getString("id"),
                        resultado.getString("componente"),
                        resultado.getString("noOp"),
                        resultado.getString("noTroquel"),
                        resultado.getString("fecha"),
                        resultado.getString("variable"),
                        resultado.getString("especificacion"),
                        resultado.getString("valor"),
                        resultado.getInt("procesoCritico")
                );
                listaAp3.add(ap3);
            }
        }
        return listaAp3;
    }

    public List<AceptacionProductoM> obtenerAceptacionProducto(Connection conexion) {
        List<AceptacionProductoM> lista = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ACEPTACION_PRODUCTO_ORDER_BY_COMPONENTE);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                AceptacionProductoM ir = new AceptacionProductoM(
                        resultado.getInt("id"),
                        resultado.getString("componente"),
                        resultado.getString("rutaArchivo")
                );
                lista.add(ir);
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al recuperar la información", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<AceptacionProductoM> obtenerAceptacionProducto(Connection conexion, int page, int limit, String filtro) {
        String sql;
        List<AceptacionProductoM> lista = new ArrayList<>();
        boolean filtrar = filtro != null && !filtro.trim().isEmpty();

        if (filtrar) {
            sql = "SELECT * FROM aceptacionProducto WHERE componente = ? ORDER BY componente LIMIT ?, ?";
        } else {
            sql = "SELECT * FROM aceptacionProducto ORDER BY componente LIMIT ?, ?";
        }

        int limite = (page - 1) * limit;
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

            if (filtrar) {
                consulta.setString(1, "%" + filtro + "%");
                consulta.setInt(2, limite);
                consulta.setInt(3, limit);
            } else {
                consulta.setInt(1, limite);
                consulta.setInt(2, limit);
            }

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                AceptacionProductoM ir = new AceptacionProductoM(
                        resultado.getInt("id"),
                        resultado.getString("componente"),
                        resultado.getString("rutaArchivo")
                );
                lista.add(ir);
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al recuperar la información", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<String> obtenerDatos(PreparedStatement consulta, String columna) throws SQLException {
        List<String> listaDatos = new ArrayList<>();

        try (ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                listaDatos.add(resultado.getString(columna));
            }
        }

        return listaDatos;
    }

    public int obtenerEspecificacion(Connection conexion, String especificacion) throws SQLException {
        PreparedStatement consulta = conexion.prepareStatement(SELECT_FILA_FROM_ESPECIFICACIONES_IR);
        consulta.setString(1, especificacion);
        ResultSet resultado = consulta.executeQuery();
        int fila = 0;
        if (resultado.next()) {
            fila = resultado.getInt("fila");
        }
        return fila;
    }

    public List<String> obtenerEspecificaciones(Connection conexion, String variable, String componente) throws SQLException {
        List<String> listaEspecificaciones = new ArrayList<>();
        String producto = variable + "%"; // Agregar el comodín % al final para buscar palabras que comiencen con la variable
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ESPECIFICACION_FROM_DESCRIPCION_PC3)) {
            consulta.setString(1, producto);
            consulta.setString(2, componente);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                listaEspecificaciones.add(resultado.getString("especificacion"));
            }
        }
        return listaEspecificaciones;
    }

    public List<String> obtenerFechasRetencionDimensional(Connection conexion, String componenteSeleccionado) throws SQLException {
        List<String> nuevosDatos = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SELECT_FECHA_ACEPTACION_PC1)) {
            ps.setString(1, componenteSeleccionado);
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                nuevosDatos.add(resultado.getString("fecha"));
            }
        }
        return nuevosDatos;
    }

    public AceptacionPc1 obtenerInfoPc1(Connection conexion, String selectedComponente) throws SQLException {
        AceptacionPc1 ap1 = new AceptacionPc1();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_ACEPTACION_PC1)) {
            consulta.setString(1, selectedComponente);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                AceptacionPc1 ap = new AceptacionPc1(
                        resultado.getString("id"),
                        resultado.getString("componente"),
                        resultado.getString("fecha"),
                        resultado.getString("noRollo"),
                        resultado.getString("inspVisual"),
                        resultado.getString("observacion"),
                        resultado.getString("noParte"),
                        resultado.getString("noOps")
                );
                ap1 = ap;
            }
        }
        return ap1;
    }

    public List<DatosFilaRD> obtenerInfoRetencionDimensional(Connection conexion, String componenteSeleccionado, String fechaSeleccionada) throws SQLException {
        List<DatosFilaRD> datosFilaRD = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(SELECT_ACEPTACION_PC1_JOIN_ACEPTACION_PC2)) {
            ps.setString(1, componenteSeleccionado);
            ps.setString(2, fechaSeleccionada);
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                DatosFilaRD datoRD = new DatosFilaRD(
                        resultado.getInt("id_pc1"),
                        resultado.getInt("id_pc2"),
                        resultado.getString("noRollo"),
                        resultado.getString("inspVisual"),
                        resultado.getString("observacion"),
                        resultado.getString("noOrden"),
                        resultado.getString("inspector"),
                        resultado.getString("tamLote"),
                        resultado.getString("turno"),
                        resultado.getString("tamMta"),
                        resultado.getString("disp")
                );
                datosFilaRD.add(datoRD);
            }
        }
        return datosFilaRD;
    }

    public List<String> obtenerListaComponentes(Connection conexion) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_COMPONENTE_FROM_ANTECEDENTES_FAMILIA)) {
            return obtenerDatos(consulta, "componente");
        }
    }

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

    public AceptacionPc2 obtenerOrden(Connection conexion, AceptacionPc1 apc1) throws SQLException {
        AceptacionPc2 apc2 = new AceptacionPc2();
        try (PreparedStatement ps = conexion.prepareStatement(SELECT_NO_ORDEN_FROM_ACEPTACION_PC2)) {
            ps.setString(1, apc1.getComponente());
            ps.setString(2, apc1.getFecha());
            ResultSet resultado = ps.executeQuery();

            while (resultado.next()) {
                if (resultado.getString("noOrden") != null) {
                    apc2.setNoOrden(resultado.getString("noOrden"));
                } else {
                    apc2.setNoOrden(resultado.getString(""));
                }
            }
        }
        return apc2;
    }

    public List<String> obtenerVariables(Connection conexion) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_NOMBRE_PRODUCTO)) {
            return obtenerDatos(consulta, "nombreProducto");
        }
    }

    public List<String> obtenerVariablesPC(Connection conexion, String variable) throws SQLException {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_NOMBRE_PRODUCTO_JOIN_DECRIPCION_PC2)) {
            consulta.setString(1, variable);
            return obtenerDatos(consulta, "nombreProducto");
        }
    }

    public void abrirDocumento(String rutaArchivo) {
        try {
            if (rutaArchivo == null || rutaArchivo.isEmpty()) {
                Utilidades.manejarExcepcion("La ruta del archivo no es válida.", null);
                return;
            }

            String urlArchivo = "\\\\" + Utilidades.SERVIDOR + "\\" + rutaArchivo; // Ruta de red

            File archivo = new File(urlArchivo);
            if (!archivo.exists()) {
                Utilidades.manejarExcepcion("El archivo no existe en la ruta especificada.", null);
                return;
            }
            abrirArchivoLocal(archivo);
        } catch (IOException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void abrirArchivoLocal(File archivo) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(archivo);
    }

    public int contarRegistros(Connection conexion, String filtro) {
        String sql;
        boolean filtrar = filtro != null && !filtro.trim().isEmpty();
        if (filtrar) {
            sql = "SELECT COUNT(*) FROM aceptacionproducto WHERE componente = ?";
        } else {
            sql = "SELECT COUNT(*) FROM aceptacionproducto";
        }
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            if (filtrar) {
                String filtroLike = "%" + filtro + "%";
                consulta.setString(1, filtroLike); // Para número de hoja
            }

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
}
