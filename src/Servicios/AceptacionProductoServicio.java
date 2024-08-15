package Servicios;

import AceptacionProducto.AceptacionProductoGUI2;
import AceptacionProducto.AceptacionProductoGUI;
import AceptacionProducto.AgregarVariableGUI;
import AceptacionProducto.ModificarAPGUI;
import AceptacionProducto.ModificarRDGUI;
import AceptacionProducto.RetencionDimensional;
import Modelos.AceptacionPc1;
import Modelos.AceptacionPc2;
import Modelos.AceptacionPc3;
import Modelos.AceptacionProducto;
import Modelos.DatosFilaRD;
import Modelos.Usuarios;
import java.awt.Desktop;
import java.awt.Image;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AceptacionProductoServicio {

    final String SELECT_NO_ROLLO_SQL = "SELECT noRollo FROM inspeccionRecibo";

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
                        resultado.getString("noOps")
                );
                listaAp1.add(ap1);
            }
        }
        return listaAp1;
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
        String sqlConsulta = "SELECT * FROM aceptacionpc3 WHERE componente = ? ORDER BY fecha, noOp";
        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
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

    public void agregarpc1(Connection conexion, AceptacionPc1 ap1) throws SQLException {
        String sqlInsertIr = "INSERT INTO aceptacionpc1(componente,fecha,noRollo,inspVisual,observacion,noParte,noOps) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
            sqlInsert.setString(1, ap1.getComponente());
            sqlInsert.setString(2, ap1.getFecha());
            sqlInsert.setString(3, ap1.getNoRollo());
            sqlInsert.setString(4, ap1.getInspVisual());
            sqlInsert.setString(5, ap1.getObservacion());
            sqlInsert.setString(6, ap1.getNoParte());
            sqlInsert.setString(7, ap1.getNoOps());
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
        String sqlInsertIr = "INSERT INTO aceptacionpc3(componente,noOp,noTroquel,fecha,variable,especificacion,valor,procesoCritico) VALUES (?,?,?,?,?,?,?,?)";
        for (int i = 0; i < ap3.size(); i++) {
            try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
                sqlInsert.setString(1, ap3.get(i).getComponente());
                sqlInsert.setString(2, ap3.get(i).getNoOp());
                sqlInsert.setString(3, ap3.get(i).getNoTroquel());
                sqlInsert.setString(4, ap3.get(i).getFecha());
                sqlInsert.setString(5, ap3.get(i).getVariable());
                sqlInsert.setString(6, ap3.get(i).getEspecificacion());
                sqlInsert.setString(7, ap3.get(i).getValor());
                sqlInsert.setInt(8, ap3.get(i).getProcesoCritico());
                sqlInsert.executeUpdate();
            } catch (SQLException ex) {
                throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
            }
        }
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

    public void agregarNuevaVariable(Connection conexion, String nuevaVariable) throws SQLException {
        String sqlInsertIr = "INSERT INTO productosactividadespc(nombreProducto) VALUES (?)";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
            sqlInsert.setString(1, nuevaVariable);
            sqlInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    public void eliminarpc3(Connection conexion, AceptacionPc3 apc3) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM aceptacionpc3 WHERE fecha = ? AND noOp = ? AND variable = ? AND especificacion = ? AND valor = ?");
            ps.setString(1, apc3.getFecha());
            ps.setString(2, apc3.getNoOp());
            ps.setString(3, apc3.getVariable());
            ps.setString(4, apc3.getEspecificacion());
            ps.setString(5, apc3.getValor());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abrirAceptacionProductoGUI(Usuarios usr) {
        try {
            AceptacionProductoGUI apGUI = new AceptacionProductoGUI(usr);
            mostrarVentana(apGUI);
        } catch (SQLException | ClassNotFoundException ex) {
            manejarExcepcion("Surgio un error al abrir ACEPTACION PRODUCTO", ex);
        }
    }

    public void abrirRetencionDimensionalGUI(Usuarios usr, AceptacionPc1 apc1, AceptacionPc2 apc2) {
        RetencionDimensional rdGUI = new RetencionDimensional(usr, apc1, apc2);
        mostrarVentana(rdGUI);
    }

    public void abrirRetencionDimensionalGUI(Usuarios usr, AceptacionPc1 apc1) {
        RetencionDimensional rdGUI = new RetencionDimensional(usr, apc1);
        mostrarVentana(rdGUI);
    }

    public Image getImage(String ruta) {
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(ruta));
            Image mainIcon = imageIcon.getImage();
            return mainIcon;
        } catch (Exception e) {
            manejarExcepcion("Surgio un error al cargar el logo", e);
        }
        return null;
    }

    public boolean verificarRango(String especificacion, String txtValor) {
        String seleccion = especificacion;

        String valorIngresado = txtValor.trim();
        if (seleccion.contains("-")) {
            String[] valoresRango = seleccion.split("-");

            try {
                double valorMin = Double.parseDouble(valoresRango[0]);
                double valorMax = Double.parseDouble(valoresRango[1]);

                if (valorMin > valorMax) {
                    JOptionPane.showMessageDialog(null, "Los límites del rango no están en el orden correcto", "Alerta", JOptionPane.WARNING_MESSAGE);
                    //txtValor.setText("");
                    return false;
                }

                double valor = Double.parseDouble(valorIngresado);
                if (valor < valorMin || valor > valorMax) {
                    JOptionPane.showMessageDialog(null, "El valor ingresado está fuera del rango seleccionado (" + seleccion + ")", "Alerta", JOptionPane.WARNING_MESSAGE);
                    //txtValor.setText("");
                    return false;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico válido", "Alerta", JOptionPane.WARNING_MESSAGE);
                //txtValor.setText("");
                return false;
            }
        }
        return true;
    }

    public void abrirRetencionDimensionalGUI(Usuarios usr) {
        RetencionDimensional rdGUI = new RetencionDimensional(usr);
        mostrarVentana(rdGUI);
    }

    public void abrirModificarAPGUI(Usuarios usr) {
        try {
            ModificarAPGUI moGUI = new ModificarAPGUI(usr);
            mostrarVentana(moGUI);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarVentana(JFrame frame) {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void manejarExcepcion(String msg, Exception ex) {
        JOptionPane.showMessageDialog(null, msg + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public String formatearFecha(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public void cargarNoRollos(Connection conexion, JComboBox cbxNoRollo) {
        try {
            List<String> rollos = obtenerNoRollos(conexion);
            rollos.forEach(cbxNoRollo::addItem);
        } catch (SQLException ex) {
            manejarExcepcion("Surgio un error al cargar los No's de Rollos: ", ex);
        }

    }

    public void abrirUrlEnNavegador(String url) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI(url));
    }

    public void abrirAceptacionProductoGUI2(Usuarios usr) {
        try {
            AceptacionProductoGUI2 ap2 = new AceptacionProductoGUI2(usr); // Se crea la instancia de la clase
            ap2.setVisible(true); // Se muestra visible al usuario
            ap2.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
        } catch (SQLException | ClassNotFoundException ex) {
            manejarExcepcion("Surgio un error al abrir ACEPTACIÓN PRODUCTO", ex);
        }
    }

    public void abrirAgregarVariableGUI(Usuarios usr, AceptacionPc1 apc1, AceptacionPc2 apc2) {
        AgregarVariableGUI avGUI = new AgregarVariableGUI(usr, apc1, apc2);
        avGUI.setVisible(true);
        avGUI.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
    }

    public void abrirModificarRDGUI(AceptacionPc3 filaSeleccionada, Usuarios usr, AceptacionPc1 apc1, AceptacionPc2 apc2) {
        ModificarRDGUI modificar = new ModificarRDGUI(filaSeleccionada, usr, apc1, apc2);
        modificar.setVisible(true);
        modificar.setLocationRelativeTo(null);
    }

    public List<String> obtenerDatos(Connection conexion, String query, String columna) throws SQLException {
        List<String> listaDatos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(query);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                listaDatos.add(resultado.getString(columna));
            }
        }
        return listaDatos;
    }

    public List<String> obtenerComponetes(Connection conexion) throws SQLException {
        String query = "SELECT componente FROM antecedentesfamilia";
        return obtenerDatos(conexion, query, "componente");
    }

    public List<String> obtenerVariablesPC(Connection conexion, String variable) throws SQLException {
        String query = "SELECT pa.nombreProducto FROM productosactividadespc AS pa JOIN descripcion2pc AS d2 ON pa.nombreProducto = d2.producto WHERE d2.componente = '" + variable + "'";
        return obtenerDatos(conexion, query, "nombreProducto");
    }

    public List<String> obtenerVariables(Connection conexion) throws SQLException {
        String query = "SELECT nombreProducto FROM productosactividadespc";
        return obtenerDatos(conexion, query, "nombreProducto");
    }

    public List<String> obtenerEspecificaciones(Connection conexion, String variable, String componente) throws SQLException {
        List<String> listaEspecificaciones = new ArrayList<>();
        String producto = variable + "%"; // Agregar el comodín % al final para buscar palabras que comiencen con la variable
        String sqlConsulta = "SELECT t1.especificacion FROM descripcion3pc AS t1 JOIN descripcion2pc AS t2 ON t1.componente = t2.componente AND t1.noPartesP = t2.noPartesP AND t1.no = t2.no WHERE t2.producto LIKE ? AND t1.componente = ? GROUP BY t1.especificacion, t2.producto";
        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
            consulta.setString(1, producto);
            consulta.setString(2, componente);

            // Ejecutar la consulta y procesar los resultados
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                listaEspecificaciones.add(resultado.getString("especificacion"));
            }
        }
        return listaEspecificaciones;
    }

    public List<AceptacionPc2> buscarObjetoEnAp2mPorFecha(Connection conexion, String fecha, String componente, AceptacionPc2 apc2) throws SQLException {
        String sqlConsulta = "SELECT noOrden, fecha, tamLote, tamMta, inspector, turno, disp\n"
                + "FROM aceptacionpc2\n"
                + "WHERE fecha = ? AND componente = ?\n"
                + "GROUP BY noOrden, fecha, tamLote, tamMta, inspector, turno, disp";

        List<AceptacionPc2> ap2c = new ArrayList<>();

        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
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

    public void subirRD(Connection conexion, AceptacionProducto ap) throws SQLException {
        String sqlSelect = "SELECT * FROM aceptacionproducto WHERE componente=?";
        String sqlInsertIr = "INSERT INTO aceptacionproducto(componente,retencionDimensionalpdf) VALUES (?,?)";
        String sqlUpdate = "UPDATE aceptacionproducto SET retencionDimensionalpdf = ? WHERE componente = ?";

        try (PreparedStatement sqlSelectStatement = conexion.prepareStatement(sqlSelect)) {
            sqlSelectStatement.setString(1, ap.getComponente());
            ResultSet resultSet = sqlSelectStatement.executeQuery();

            if (resultSet.next()) {
                try (PreparedStatement sqlUpdateStatement = conexion.prepareStatement(sqlUpdate)) {
                    sqlUpdateStatement.setBytes(1, ap.getRdPdf());
                    sqlUpdateStatement.setString(2, ap.getComponente());
                    sqlUpdateStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "El Archivo se actualizo Correctamente");
                }
            } else {
                try (PreparedStatement sqlInsert = conexion.prepareStatement(sqlInsertIr)) {
                    sqlInsert.setString(1, ap.getComponente());
                    sqlInsert.setBytes(2, ap.getRdPdf());
                    sqlInsert.executeUpdate();
                    JOptionPane.showMessageDialog(null, "El Archivo se genero y se guardo Correctamente");
                }
            }

        } catch (SQLException ex) {
            throw new SQLException("Error al ejecutar la consulta SQL de inserción: " + ex.getMessage(), ex);
        }
    }

    private TreeMap<String, TreeMap<String, Map<String, String>>> crearTreeMap(Comparator<String> comparator) {
        return new TreeMap<>(comparator);
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

    public List<AceptacionProducto> recuperarAPs(Connection conexion) {
        List<AceptacionProducto> listaIr = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM aceptacionProducto ORDER BY componente");
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                AceptacionProducto ir = new AceptacionProducto(
                        resultado.getInt("id"),
                        resultado.getString("componente"),
                        resultado.getBytes("retencionDimensionalpdf")
                );
                listaIr.add(ir);
            }
        } catch (SQLException ex) {
            manejarExcepcion("Error al recuperar la información", ex);
        }
        return listaIr;
    }

    public void eliminarAP(Connection conexion, String componente) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM aceptacionproducto WHERE componente = ?");
            ps.setString(1, componente);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM aceptacionpc1 WHERE componente = ?");
            ps.setString(1, componente);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM aceptacionpc2 WHERE componente=?");
            ps.setString(1, componente);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM aceptacionpc3 WHERE componente=?");
            ps.setString(1, componente);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ejecutarArchivoXLSX(Connection conexion, String componente, int column) throws ClassNotFoundException, SQLException {
        final String SELECT_HOJA_INSTRUCCION_SQL = "SELECT retencionDimensionalpdf FROM aceptacionproducto WHERE componente = ?";
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
                    // Abrir archivo con Excel
                    File archivoXLSX = new File("-" + componente + ".xlsx");
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

    public AceptacionPc1 recuperarInfoPc1(Connection conexion, String selectedComponente) throws SQLException {
        AceptacionPc1 ap1 = new AceptacionPc1();
        String sqlConsulta = "SELECT * FROM aceptacionpc1 WHERE componente=?";
        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
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

    public int obtenerEspecificacion(Connection conexion, String especificacion) throws SQLException {
        String sqlConsulta = "SELECT fila FROM especificacionesir WHERE especificacion=?";
        PreparedStatement consulta = conexion.prepareStatement(sqlConsulta);
        consulta.setString(1, especificacion);
        ResultSet resultado = consulta.executeQuery();
        int fila = 0;
        if (resultado.next()) {
            fila = resultado.getInt("fila");
        }
        return fila;
    }

    public boolean existeRegistroPc1(Connection conexion, AceptacionPc1 apc1) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM aceptacionpc1 WHERE componente = ? AND fecha = ?";
        try (PreparedStatement ps = conexion.prepareCall(consulta)) {
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
        String consulta = "SELECT COUNT(*) FROM aceptacionpc2 WHERE componente = ? AND fecha = ? ";
        try (PreparedStatement ps = conexion.prepareCall(consulta)) {
            ps.setString(1, apc2.getComponente());
            ps.setString(2, apc2.getFecha());
//            ps.setString(3, apc2.getNoOrden());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean existeRegistroPc3(Connection conexion, AceptacionPc3 apc3) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM aceptacionpc3 WHERE componente = ? AND noOp = ? AND fecha = ? AND variable = ? AND valor = ?";
        try (PreparedStatement ps = conexion.prepareCall(consulta)) {
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

    public List<String> obtenerFechasRD(Connection conexion, String componenteSeleccionado) throws SQLException {
        List<String> nuevosDatos = new ArrayList<>();
        String selectFechas = "SELECT fecha FROM aceptacionpc1 WHERE componente = ?";
        try (PreparedStatement ps = conexion.prepareStatement(selectFechas)) {
            ps.setString(1, componenteSeleccionado);
            ResultSet resultado = ps.executeQuery(); // execute the query
            while (resultado.next()) {
                nuevosDatos.add(resultado.getString("fecha")); // add the result to the list
            }
        }
        return nuevosDatos;
    }

    public DatosFilaRD obtenerInfoRD(Connection conexion, String componenteSeleccionado, String fechaSeleccionada) throws SQLException {
        DatosFilaRD datosFilaRD = new DatosFilaRD();
        String selectInfo = "SELECT * FROM aceptacionpc1 a1 "
                + "INNER JOIN aceptacionpc2 a2 ON a1.fecha = a2.fecha AND a1.componente = a2.componente "
                + "WHERE a1.componente = ? AND a1.fecha = ?";

        try (PreparedStatement ps = conexion.prepareStatement(selectInfo)) {
            ps.setString(1, componenteSeleccionado);
            ps.setString(2, fechaSeleccionada);
            ResultSet resultado = ps.executeQuery();
            Map<String, DatosFilaRD> dataRowMap = new HashMap<>();

            while (resultado.next()) {
                String key = resultado.getString("fecha") + "-" + resultado.getString("componente");

                DatosFilaRD dataRow;
                if (dataRowMap.containsKey(key)) {
                    dataRow = dataRowMap.get(key);
                } else {
                    dataRow = new DatosFilaRD();
                    dataRow.setNoRollo(resultado.getString("noRollo"));
                    dataRow.setInspVisual(resultado.getString("inspVisual"));
                    dataRow.setObservaciones(resultado.getString("observacion"));
                    dataRow.setTamLote(resultado.getString("tamLote"));
                    dataRow.setTamMta(resultado.getString("tamMta"));
                    dataRow.setInsp(resultado.getString("inspector"));
                    dataRow.setTurno(resultado.getString("turno"));
                    dataRow.setDisp(resultado.getString("disp"));
                    dataRowMap.put(key, dataRow);
                }

                // Agrega el valor de noOrden
                dataRow.agregarOtraOrden(resultado.getString("noOrden"));
            }

            // Asume que sólo hay una combinación de fecha y componente para los parámetros dados
            if (!dataRowMap.isEmpty()) {
                datosFilaRD = dataRowMap.values().iterator().next();
            }
        }

        return datosFilaRD;
    }

    public void modificarInfoRD(Connection conexion, AceptacionPc1 apc1, DatosFilaRD datosFila) throws SQLException {
        String sql1 = "UPDATE aceptacionpc1 SET noRollo = ?, inspVisual = ?, observacion = ? WHERE componente = ? AND fecha = ?";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sql1)) {
            sqlInsert.setString(1, apc1.getNoRollo());
            sqlInsert.setString(2, apc1.getInspVisual());
            sqlInsert.setString(3, apc1.getObservacion());
            sqlInsert.setString(4, apc1.getComponente());
            sqlInsert.setString(5, apc1.getFecha());
            sqlInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al modificar los datos: " + ex.getMessage(), ex);
        }

        String sql2 = "UPDATE aceptacionpc2 SET noOrden = ?, tamLote = ?, tamMta = ?, inspector = ?, turno = ?, disp =? WHERE componente = ? AND fecha = ?";
        try (PreparedStatement sqlInsert = conexion.prepareStatement(sql2)) {
            sqlInsert.setString(1, String.join(", ", datosFila.getNoOrden()));
            sqlInsert.setString(2, datosFila.getTamLote());
            sqlInsert.setString(3, datosFila.getTamMta());
            sqlInsert.setString(4, datosFila.getInsp());
            sqlInsert.setString(5, datosFila.getTurno());
            sqlInsert.setString(6, datosFila.getDisp());
            sqlInsert.setString(7, apc1.getComponente());
            sqlInsert.setString(8, apc1.getFecha());
            sqlInsert.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error al modificar los datos: " + ex.getMessage(), ex);
        }
    }

    public AceptacionPc2 buscarOrden(Connection conexion, AceptacionPc1 apc1) throws SQLException {
        AceptacionPc2 apc2 = new AceptacionPc2();
        String sql = "SELECT noOrden FROM aceptacionpc2 WHERE componente = ? AND fecha = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
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

    public void modificarOrden(Connection conexion, AceptacionPc2 apc2) throws SQLException {
        String consulta = "UPDATE aceptacionpc2 SET noOrden = ? WHERE componente = ? AND fecha = ?";
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            // Configurar los parámetros en el orden correcto
            ps.setString(1, apc2.getNoOrden());   // Primero el noOrden
            ps.setString(2, apc2.getComponente()); // Luego el componente
            ps.setString(3, apc2.getFecha());      // Finalmente la fecha

            // Ejecutar la consulta de actualización
            ps.executeUpdate();
        }
    }
}
