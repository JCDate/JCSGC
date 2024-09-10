package Servicios;

import Documentos.ControlDocumentosGUI;
import Documentos.ProcesosGUI;
import Documentos.FormatosGUI;
import Documentos.ProcedimientosGUI;
import Documentos.RegistrosGUI;
import Documentos.SolicitudGUI;
import Documentos.SolicitudesGUI;
import Modelos.DocumentosM;
import Modelos.FormatosM;
import Modelos.ProcedimientosM;
import Modelos.ProcesosM;
import Modelos.RegistrosM;
import Modelos.SolicitudesM;
import Modelos.Usuarios;
import java.awt.Desktop;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.util.Date;
import javax.swing.JOptionPane;
import swing.Button;

public class ControlDocumentacionServicio {

    Conexion conexion = Conexion.getInstance();

    public void abrirDocumentacionGUI(Usuarios usr, int idProceso) {
        ProcesosGUI doc = new ProcesosGUI(usr, idProceso);
        doc.setVisible(true);
        doc.setLocationRelativeTo(null);
    }

    public void abrirControlDocumentosGUI(Usuarios usr) {
        try {
            ControlDocumentosGUI doc = new ControlDocumentosGUI(usr); // Se crea la instancia de la clase
            doc.setVisible(true); // Se muestra visible al usuario
            doc.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ProcesosM recuperarProceso(Conexion conexion, int id) throws SQLException {
        ProcesosM proceso = null;
        String sql = "SELECT * FROM docProcesos WHERE id = ?";
        try (PreparedStatement consulta = conexion.conectar().prepareStatement(sql)) {
            consulta.setInt(1, id);

            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                proceso = new ProcesosM(
                        resultado.getInt("id"),
                        resultado.getInt("uid"),
                        resultado.getString("no"),
                        resultado.getString("codigo"),
                        resultado.getString("revision"),
                        resultado.getString("proceso"),
                        resultado.getString("encargado"),
                        resultado.getString("nombreDt"),
                        resultado.getBytes("diagramaTortuga")
                );
            }
        }
        return proceso;
    }

    public List<ProcedimientosM> recuperarProcedimientos(Conexion conexion, int idp) throws SQLException {
        List<ProcedimientosM> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM docprocedimientos WHERE idp = ?";
        try (PreparedStatement consulta = conexion.conectar().prepareStatement(sql)) {
            consulta.setInt(1, idp);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                ProcedimientosM procedimiento = new ProcedimientosM(
                        resultado.getInt("id"),
                        resultado.getInt("idp"),
                        resultado.getString("no"),
                        resultado.getString("codigo"),
                        resultado.getString("revision"),
                        resultado.getString("procedimiento"),
                        resultado.getString("encargado")
                );
                procedimientos.add(procedimiento);
            }
        }
        return procedimientos;
    }

    public List<DocumentosM> recuperarDocumentos(Conexion conexion, int id) throws SQLException {
        List<DocumentosM> documentos = new ArrayList<>();
        String sql = "SELECT * FROM documentos WHERE idProcedimiento = ?";
        try (PreparedStatement consulta = conexion.conectar().prepareStatement(sql)) {
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                DocumentosM documento = new DocumentosM(
                        resultado.getInt("id"),
                        resultado.getInt("idProceso"),
                        resultado.getInt("idProcedimiento"),
                        resultado.getString("revision"),
                        resultado.getString("fechaActualizacion"),
                        resultado.getString("tipo"),
                        resultado.getString("nombre"),
                        resultado.getBytes("contenido")
                );
                documentos.add(documento);
            }
        }
        return documentos;
    }

    public List<FormatosM> recuperarFormatos(Conexion conexion, int id) throws SQLException {
        List<FormatosM> formatos = new ArrayList<>();
        String sql = "SELECT * FROM formatos WHERE idProcedimiento = ?";
        try (PreparedStatement consulta = conexion.conectar().prepareStatement(sql)) {
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                FormatosM formato = new FormatosM(
                        resultado.getInt("id"),
                        resultado.getInt("idProcedimiento"),
                        resultado.getString("nombre"),
                        resultado.getBytes("contenido")
                );
                formatos.add(formato);
            }
        }
        return formatos;
    }

    public Button crearBoton(Object object, ImageIcon icon, String texto) {
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

    public void ejecutarDiagramaTortuga(ProcesosM proceso) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.conectar().prepareStatement("SELECT diagramaTortuga FROM docprocesos WHERE id = ?");
            ps.setInt(1, proceso.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                byte[] b = rs.getBytes(1);

                try (InputStream bos = new ByteArrayInputStream(b);
                        OutputStream out = new FileOutputStream(proceso.getNombreDT())) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = bos.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos para el ID proporcionado.");
            }
        } catch (IOException | SQLException ex) {
            System.out.println("Error al abrir o crear el archivo PowerPoint: " + ex.getMessage());
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

    public void ejecutarManual(ProcedimientosM procedimiento, String tipoDocto) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.conectar().prepareStatement("SELECT contenido, nombre FROM documentos WHERE tipo = ? AND idProcedimiento = ?");

            ps.setString(1, tipoDocto);
            ps.setInt(2, procedimiento.getIdp());

            rs = ps.executeQuery();

            if (rs.next()) {
                byte[] b = rs.getBytes("contenido");
                String nombre = rs.getString("nombre");

                String nombreArchivo = nombre;
                try (InputStream bos = new ByteArrayInputStream(b);
                        OutputStream out = new FileOutputStream(procedimiento.getProcedimiento())) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = bos.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }

                // Abrir el archivo con la aplicación predeterminada
                File archivo = new File(nombreArchivo);
                if (archivo.exists()) {
                    Desktop.getDesktop().open(archivo);
//                    System.out.println("Archivo " + nombreArchivo + " abierto correctamente.");
                } else {
                    System.out.println("El archivo no se encontró.");
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

    public void ejecutarFormato(String nombreFormato) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.conectar().prepareStatement("SELECT contenido, nombre FROM formatos WHERE nombre = ?");

            ps.setString(1, nombreFormato);

            rs = ps.executeQuery();

            if (rs.next()) {
                byte[] b = rs.getBytes("contenido");
                String nombre = rs.getString("nombre");

                String nombreArchivo = nombre;
                try (InputStream bos = new ByteArrayInputStream(b);
                        OutputStream out = new FileOutputStream(nombre)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = bos.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }

                // Abrir el archivo con la aplicación predeterminada
                File archivo = new File(nombreArchivo);
                if (archivo.exists()) {
                    Desktop.getDesktop().open(archivo);
                    System.out.println("Archivo " + nombreArchivo + " abierto correctamente.");
                } else {
                    System.out.println("El archivo no se encontró.");
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

    public void ejecutarArchivo(String nombre) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.conectar().prepareStatement("SELECT contenido, nombre FROM formatos WHERE nombre = ?");
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            if (rs.next()) {
                byte[] b = rs.getBytes("contenido");
                String tipo = rs.getString("extension");

                String nombreArchivo = "nuevoArchivo." + tipo;
                try (InputStream bos = new ByteArrayInputStream(b);
                        OutputStream out = new FileOutputStream(nombreArchivo)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = bos.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                    System.out.println("Archivo " + tipo + " creado correctamente.");
                }

                // Abrir el archivo con la aplicación predeterminada
                File archivo = new File(nombreArchivo);
                if (archivo.exists()) {
                    Desktop.getDesktop().open(archivo);
                    System.out.println("Archivo " + nombreArchivo + " abierto correctamente.");
                } else {
                    System.out.println("El archivo no se encontró.");
                }
            } else {
                System.out.println("No se encontraron datos para el ID proporcionado.");
            }
        } catch (IOException | SQLException ex) {
            System.out.println("Error al abrir o crear el archivo: " + ex.getMessage());
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

    public void abrirSolicitudCambioGUI(Usuarios usr) {
        SolicitudesGUI doc = new SolicitudesGUI(usr); // Se crea la instancia de la clase
        doc.setVisible(true); // Se muestra visible al usuario
        doc.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
    }

    public void abrirSolicitudGUI(Usuarios usr, ProcesosM proceso) {
        try {
            SolicitudGUI doc = new SolicitudGUI(usr, proceso); // Se crea la instancia de la clase
            doc.setVisible(true); // Se muestra visible al usuario
            doc.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarSolicitud(SolicitudesM solicitud) {
        String sql = "INSERT INTO solicitudescambio(idp, codigo, proceso, procedimiento, revAnterior, revNueva, encargado, accion, tipoArchivo, nombrePrev, nombre, archivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmtSelect = conexion.conectar().prepareStatement(sql)) {
            pstmtSelect.setInt(1, solicitud.getId());
            pstmtSelect.setString(2, solicitud.getCodigo());
            pstmtSelect.setString(3, solicitud.getProceso());
            pstmtSelect.setString(4, solicitud.getProcedimiento());
            pstmtSelect.setString(5, solicitud.getRevAnterior());
            pstmtSelect.setString(6, solicitud.getRevNueva());
            pstmtSelect.setString(7, solicitud.getEncargado());
            pstmtSelect.setString(8, solicitud.getAccion());
            pstmtSelect.setString(9, solicitud.getTipoArchivo());
            pstmtSelect.setString(10, solicitud.getNombreD());
            pstmtSelect.setString(11, solicitud.getNombre());
            pstmtSelect.setBytes(12, solicitud.getArchivo());
            pstmtSelect.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<SolicitudesM> recuperarSolicitudes(Conexion conexion) throws SQLException {
        List<SolicitudesM> listaSolicitudes = new ArrayList<>();
        String sqlConsulta = "SELECT * FROM solicitudescambio";
        try (PreparedStatement consulta = conexion.conectar().prepareStatement(sqlConsulta)) {

            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                SolicitudesM ap1 = new SolicitudesM(
                        resultado.getInt("idp"),
                        resultado.getString("codigo"),
                        resultado.getString("proceso"),
                        resultado.getString("procedimiento"),
                        resultado.getString("revAnterior"),
                        resultado.getString("revNueva"),
                        resultado.getString("encargado"),
                        resultado.getString("accion"),
                        resultado.getString("tipoArchivo"),
                        resultado.getString("nombrePrev"),
                        resultado.getString("nombre"),
                        resultado.getBytes("archivo")
                );
                listaSolicitudes.add(ap1);
            }
        }
        return listaSolicitudes;
    }

    public void abrirProcedimientosGUI(Usuarios usr, ProcedimientosM procedimiento) {
        try {
            ProcedimientosGUI doc = new ProcedimientosGUI(usr, procedimiento); // Se crea la instancia de la clase
            doc.setVisible(true); // Se muestra visible al usuario
            doc.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abrirFormatosGUI(Usuarios usr, ProcedimientosM procedimiento) {
        FormatosGUI formatos = new FormatosGUI(usr, procedimiento); // Se crea la instancia de la clase
        formatos.setVisible(true); // Se muestra visible al usuario
        formatos.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
    }

    public void abrirRegistrosGUI(Usuarios usr, ProcedimientosM procedimiento) {
        RegistrosGUI formatos = new RegistrosGUI(usr, procedimiento); // Se crea la instancia de la clase
        formatos.setVisible(true); // Se muestra visible al usuario
        formatos.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
    }

    public void ejecutarArchivoSC(String nombre) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.conectar().prepareStatement("SELECT nombre, archivo, SUBSTRING_INDEX(nombre, '.', -1) AS extension FROM solicitudescambio WHERE codigo = ?");
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            if (rs.next()) {
                byte[] b = rs.getBytes("archivo");
                String tipo = rs.getString("extension");
                String nombreDoc = rs.getString("nombre");

                String nombreArchivo = nombreDoc;
                try (InputStream bos = new ByteArrayInputStream(b);
                        OutputStream out = new FileOutputStream(nombreArchivo)) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = bos.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }

                    System.out.println("Archivo " + tipo + " creado correctamente.");
                }

                // Abrir el archivo con la aplicación predeterminada
                File archivo = new File(nombreArchivo);
                if (archivo.exists()) {
                    Desktop.getDesktop().open(archivo);
                    System.out.println("Archivo " + nombreArchivo + " abierto correctamente.");
                } else {
                    System.out.println("El archivo no se encontró.");
                }
            } else {
                System.out.println("No se encontraron datos para el ID proporcionado.");
            }
        } catch (IOException | SQLException ex) {
            System.out.println("Error al abrir o crear el archivo: " + ex.getMessage());
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

    public void eliminarSolicitud(String nombre) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = conexion.conectar().prepareStatement("DELETE FROM solicitudescambio WHERE codigo = ?");
        ps.setString(1, nombre);
        ps.executeUpdate();
    }

    public void aceptarSolicitud(SolicitudesM solicitud) throws SQLException, ClassNotFoundException {
        Date fechaActual = new Date(); // Obtener la fecha actual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formatear la fecha en formato dd/MM/yyyy
        String fechaFormateada = sdf.format(fechaActual);
        switch (solicitud.getAccion()) {
            case "ACTUALIZAR":
                if (solicitud.getTipoArchivo().equals("MANUAL")) {
                    PreparedStatement ps = conexion.conectar().prepareStatement("UPDATE docprocedimientos SET revision = ? WHERE procedimiento = ?");
                    ps.setString(1, solicitud.getRevNueva());
                    ps.setString(2, solicitud.getProcedimiento());
                    ps.executeUpdate();

                    PreparedStatement ps1 = conexion.conectar().prepareStatement("UPDATE documentos SET revision = ?, fechaActualizacion = ?, nombre = ?, contenido = ? WHERE tipo='manual' AND idProcedimiento = ?");
                    ps1.setString(1, solicitud.getRevNueva());
                    ps1.setString(2, fechaFormateada);
                    ps1.setString(3, solicitud.getNombre());
                    ps1.setBytes(4, solicitud.getArchivo());
                    ps1.setInt(5, solicitud.getId());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO")) {
                    PreparedStatement ps = conexion.conectar().prepareStatement("UPDATE docprocedimientos SET revision = ? WHERE procedimiento = ?");
                    ps.setString(1, solicitud.getRevNueva());
                    ps.setString(2, solicitud.getProcedimiento());
                    ps.executeUpdate();

                    PreparedStatement ps1 = conexion.conectar().prepareStatement("UPDATE documentos SET revision = ?, fechaActualizacion = ?, nombre = ?, contenido = ? WHERE tipo='diagrama_flujo' AND idProcedimiento = ?");
                    ps1.setString(1, solicitud.getRevNueva());
                    ps1.setString(2, fechaFormateada);
                    ps1.setString(3, solicitud.getNombre());
                    ps1.setBytes(4, solicitud.getArchivo());
                    ps1.setInt(5, solicitud.getId());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE TORTUGA")) {
                    PreparedStatement ps1 = conexion.conectar().prepareStatement("UPDATE docprocesos SET nombreDt = ?, diagramaTortuga = ? WHERE proceso = ?");
                    ps1.setString(1, solicitud.getNombre());
                    ps1.setBytes(2, solicitud.getArchivo());
                    ps1.setString(3, solicitud.getProceso());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("FORMATO")) {
                    PreparedStatement ps = conexion.conectar().prepareStatement("UPDATE formatos SET nombre = ?, contenido = ? WHERE idProcedimiento = ? AND nombre = ?");
                    ps.setString(1, solicitud.getNombre());
                    ps.setBytes(2, solicitud.getArchivo());
                    ps.setInt(3, solicitud.getId());
                    ps.setString(4, solicitud.getNombreD());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("INSTRUCTIVO")) {
                    PreparedStatement ps = conexion.conectar().prepareStatement("UPDATE documentos SET nombre = ?, contenido = ? WHERE idProceso = ? AND tipo_archivo = 'instructivo' AND nombre = ?");
                    ps.setString(1, solicitud.getNombre());
                    ps.setBytes(2, solicitud.getArchivo());
                    ps.setInt(3, solicitud.getId());
                    ps.setString(4, solicitud.getNombreD());
                    ps.executeUpdate();
                }

                break;
            case "AGREGAR":
                if (solicitud.getTipoArchivo().equals("MANUAL") || solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO") || solicitud.getTipoArchivo().equals("INSTRUCTIVO")) {
                    PreparedStatement ps = conexion.conectar().prepareStatement("INSERT INTO documentos(idProceso, idProcedimiento, revision, fechaActualizacion, tipo, nombre, contenido) VALUES( (SELECT idProceso FROM docProcesos WHERE nombreProceso=?), ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, solicitud.getProceso());
                    ps.setInt(2, solicitud.getId());
                    ps.setString(3, solicitud.getRevNueva());
                    ps.setString(4, fechaFormateada);
                    ps.setString(5, solicitud.getTipoArchivo());
                    ps.setString(6, solicitud.getNombre());
                    ps.setBytes(7, solicitud.getArchivo());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("FORMATO")) {
                    PreparedStatement ps = conexion.conectar().prepareStatement("INSERT INTO formatos(idProcedimiento, nombre, contenido) VALUES(?, ?, ?)");
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getNombre());
                    ps.setBytes(3, solicitud.getArchivo());
                    ps.executeUpdate();
                }
                break;
            case "ELIMINAR":
                if (solicitud.getTipoArchivo().equals("MANUAL") || solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO") || solicitud.getTipoArchivo().equals("INSTRUCTIVO")) {
                    PreparedStatement ps = conexion.conectar().prepareStatement("DELETE FROM documentos WHERE idProcedimiento = ? AND tipo = ? AND nombre = ?");
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getTipoArchivo());
                    ps.setString(3, solicitud.getNombre());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("FORMATO")) {
                    PreparedStatement ps = conexion.conectar().prepareStatement("DELETE FROM formatos WHERE idProcedimiento = ? AND nombre = ? AND contenido = ?)");
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getNombre());
                    ps.setBytes(3, solicitud.getArchivo());
                    ps.executeUpdate();
                }

        }
        insertarRegistro(solicitud, fechaFormateada);
        eliminarSolicitud(solicitud.getCodigo());
    }

    private void insertarRegistro(SolicitudesM solicitud, String fecha) {
        String sql = "INSERT INTO docregistros(idp, fechaModificacion, codigo, proceso, procedimiento, revAnterior, revNueva, encargado, accion, tipoArchivo, nombre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmtSelect = conexion.conectar().prepareStatement(sql)) {
            pstmtSelect.setInt(1, solicitud.getId());
            pstmtSelect.setString(2, fecha);
            pstmtSelect.setString(3, solicitud.getCodigo());
            pstmtSelect.setString(4, solicitud.getProceso());
            pstmtSelect.setString(5, solicitud.getProcedimiento());
            pstmtSelect.setString(6, solicitud.getRevAnterior());
            pstmtSelect.setString(7, solicitud.getRevNueva());
            pstmtSelect.setString(8, solicitud.getEncargado());
            pstmtSelect.setString(9, solicitud.getAccion());
            pstmtSelect.setString(10, solicitud.getTipoArchivo());
            pstmtSelect.setString(11, solicitud.getNombre());
            pstmtSelect.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<RegistrosM> recuperarRegistros(Conexion conexion, int id, Usuarios usr) throws SQLException {
        List<RegistrosM> registros = new ArrayList<>();

        if (usr.getId() == 12 || usr.getId() == 8) {

            String sql = "SELECT * FROM docregistros WHERE idp = ?";
            try (PreparedStatement consulta = conexion.conectar().prepareStatement(sql)) {
                consulta.setInt(1, id);
                ResultSet resultado = consulta.executeQuery();
                while (resultado.next()) {
                    RegistrosM registro = new RegistrosM(
                            resultado.getInt("id"),
                            resultado.getInt("idp"),
                            resultado.getString("fechaModificacion"),
                            resultado.getString("codigo"),
                            resultado.getString("proceso"),
                            resultado.getString("procedimiento"),
                            resultado.getString("revAnterior"),
                            resultado.getString("revNueva"),
                            resultado.getString("encargado"),
                            resultado.getString("accion"),
                            resultado.getString("tipoArchivo"),
                            resultado.getString("nombre")
                    );
                    registros.add(registro);
                }
            }
        } else {
            String sql = "SELECT * \n"
                    + "FROM docregistros \n"
                    + "WHERE idp = ? \n"
                    + "AND tipoArchivo IN ('MANUAL', 'DIAGRAMA DE FLUJO', 'INSTRUCTIVO', 'FORMATO', 'DIAGRAMA DE TORTUGA') \n"
                    + "ORDER BY DATE(fechaModificacion) DESC \n"
                    + "LIMIT 1";
            try (PreparedStatement consulta = conexion.conectar().prepareStatement(sql)) {
                consulta.setInt(1, id);
                ResultSet resultado = consulta.executeQuery();
                while (resultado.next()) {
                    RegistrosM registro = new RegistrosM(
                            resultado.getInt("id"),
                            resultado.getInt("idp"),
                            resultado.getString("fechaModificacion"),
                            resultado.getString("codigo"),
                            resultado.getString("proceso"),
                            resultado.getString("procedimiento"),
                            resultado.getString("revAnterior"),
                            resultado.getString("revNueva"),
                            resultado.getString("encargado"),
                            resultado.getString("accion"),
                            resultado.getString("tipoArchivo"),
                            resultado.getString("nombre")
                    );
                    registros.add(registro);
                }
            }
        }

        return registros;
    }
}
