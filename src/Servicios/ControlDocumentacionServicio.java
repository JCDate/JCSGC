package Servicios;

import Documentos.AgregarDocumentosGUI;
import Documentos.ControlDocumentosGUI;
import Documentos.ProcesosGUI;
import Documentos.FormatosGUI;
import Documentos.ModificarArchivosGUI;
import Documentos.ModificarInfoGUI;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.util.Date;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jnafilechooser.api.JnaFileChooser;
import swing.Button;

public class ControlDocumentacionServicio {

    final String UPDATE_REVISION_FROM_DOCPROCEDIMIENTOS = "UPDATE docprocedimientos SET revision = ? WHERE procedimiento = ?";
    final String UPDATE_PROCEDIMIENTOS_BY_ID = "UPDATE docprocedimientos SET no = ?, codigo = ?, revision = ?, procedimiento = ?, encargado = ? WHERE id = ?";
    final String UPDATE_MANUAL_FROM_DOCUMENTOS = "UPDATE documentos SET revision = ?, fechaActualizacion = ?, nombre = ?, contenido = ? WHERE tipo = 'manual' AND idProcedimiento = ?";
    final String UPDATE_DIAGRAMA_FLUJO_FROM_DOCUMENTOS = "UPDATE documentos SET revision = ?, fechaActualizacion = ?, nombre = ?, contenido = ? WHERE tipo = 'diagrama_flujo' AND idProcedimiento = ?";
    final String UPDATE_DIAGRAMA_TORTUGA_BY_PROCESO = "UPDATE docprocesos SET nombreDt = ?, diagramaTortuga = ? WHERE proceso = ?";
    final String UPDATE_FORMATO = "UPDATE formatos SET nombre = ?, contenido = ? WHERE idProcedimiento = ? AND nombre = ?";
    final String UPDATE_INSTRUCTIVO_FROM_DOCUMENTOS = "UPDATE documentos SET nombre = ?, contenido = ? WHERE idProceso = ? AND tipo_archivo = 'instructivo' AND nombre = ?";
    final String UPDATE_DIAGRAMA_TORTUGA_BY_ID = "UPDATE docprocesos SET nombreDt = ?, diagramaTortuga = ? WHERE id = ?";
    final String UPDATE_DOCUMENTO = "UPDATE documentos SET fechaActualizacion = ?, nombre = ?, contenido = ? WHERE id = ?";
    final String INSERT_INTO_DOCUMENTOS_WITH_SUBQUERY = "INSERT INTO documentos(idProceso, idProcedimiento, revision, fechaActualizacion, tipo, nombre, contenido) VALUES((SELECT idProceso FROM docProcesos WHERE nombreProceso = ?), ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_DOCUMENTOS = "INSERT INTO documentos(idProceso, idProcedimiento, revision, fechaActualizacion, tipo, nombre, contenido) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_FORMATOS = "INSERT INTO formatos(idProcedimiento, nombre, contenido) VALUES(?, ?, ?)";
    final String INSERT_INTO_SOLICITUDES_CAMBIO = "INSERT INTO solicitudescambio(idp, codigo, proceso, procedimiento, revAnterior, revNueva, encargado, accion, tipoArchivo, nombrePrev, nombre, archivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_PROCEDIMIENTOS = "INSERT INTO docprocedimientos(idp, no, codigo, revision, proceso, procedimiento, encargado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_REGISTROS = "INSERT INTO docregistros(idp, fechaModificacion, codigo, proceso, procedimiento, revAnterior, revNueva, encargado, accion, tipoArchivo, nombre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final String DELETE_FROM_DOCUMENTOS = "DELETE FROM documentos WHERE idProcedimiento = ? AND tipo = ? AND nombre = ?";
    final String DELETE_FROM_FORMATOS = "DELETE FROM formatos WHERE idProcedimiento = ? AND nombre = ? AND contenido = ?)";
    final String DELETE_FROM_DOCUMENTOS_BY_ID = "DELETE FROM documentos WHERE id = ?";
    final String DELETE_FROM_FORMATOS_BY_ID = "DELETE FROM formatos WHERE id = ?";
    final String DELETE_FROM_SOLICITUDES_CAMBIO = "DELETE FROM solicitudescambio WHERE codigo = ?";
    final String SELECT_CONTENIDO_NOMBRE_FROM_FORMATOS = "SELECT contenido, nombre FROM formatos WHERE nombre = ?";
    final String SELECT_NOMBRE_ARCHIVO_FROM_SOLICITUDES_CAMBIO = "SELECT nombre, archivo, SUBSTRING_INDEX(nombre, '.', -1) AS extension FROM solicitudescambio WHERE codigo = ?";
    final String SELECT_DIAGRAMA_TORTUGA = "SELECT diagramaTortuga FROM docprocesos WHERE id = ?";
    final String SELECT_FROM_DOCUMENTOS = "SELECT * FROM documentos WHERE idProcedimiento = ?";
    final String SELECT_FROM_SOLICITUDES_CAMBIO = "SELECT * FROM solicitudescambio";
    final String SELECT_CONTENIDO_NOMBRE_FROM_DOCUMENTOS = "SELECT contenido, nombre FROM documentos WHERE tipo = ? AND idProcedimiento = ?";
    final String SELECT_FROM_FORMATOS = "SELECT * FROM formatos WHERE idProcedimiento = ?";
    final String SELECT_FROM_PROCEDIMIENTOS_BY_IDP = "SELECT * FROM docprocedimientos WHERE idp = ?";
    final String SELECT_FROM_PROCESOS = "SELECT * FROM docProcesos WHERE id = ?";
    final String SELECT_FROM_REGISTROS_BY_IDP = "SELECT * FROM docregistros WHERE idp = ?";
    final String SELECT_FROM_REGISTROS = "SELECT * FROM docregistros WHERE idp = ? AND tipoArchivo IN ('MANUAL', 'DIAGRAMA DE FLUJO', 'INSTRUCTIVO', 'FORMATO', 'DIAGRAMA DE TORTUGA') ORDER BY DATE(fechaModificacion) DESC LIMIT 1";

    public void abrirAgregarDocumentosGUI(Usuarios usuario, ProcedimientosM procedimiento) {
        AgregarDocumentosGUI formatos = new AgregarDocumentosGUI(usuario, procedimiento);
        mostrarVentana(formatos);
    }

    public void abrirControlDocumentosGUI(Usuarios usr) {
        ControlDocumentosGUI doc = new ControlDocumentosGUI(usr);
        mostrarVentana(doc);
    }

    public void abrirDocumentacionGUI(Usuarios usr, int idProceso) {
        ProcesosGUI doc = new ProcesosGUI(usr, idProceso);
        mostrarVentana(doc);
    }

    public void abrirFormatosGUI(Usuarios usr, ProcedimientosM procedimiento) {
        FormatosGUI formatos = new FormatosGUI(usr, procedimiento);
        mostrarVentana(formatos);
    }

    public void abrirModificarInfoGUI(Usuarios usr, ProcesosM procesos) {
        ModificarInfoGUI doc = new ModificarInfoGUI(usr, procesos);
        mostrarVentana(doc);
    }

    public void abrirModificarInfoGUI(Usuarios usr, ProcedimientosM procedimiento) {
        ModificarInfoGUI doc = new ModificarInfoGUI(usr, procedimiento);
        mostrarVentana(doc);
    }

    public void abrirModificarArchivosGUI(Usuarios usr, DocumentosM documento) {
        ModificarArchivosGUI doc = new ModificarArchivosGUI(usr, documento);
        mostrarVentana(doc);
    }

    public void abrirModificarArchivosGUI(Usuarios usr, ProcesosM proceso) {
        ModificarArchivosGUI doc = new ModificarArchivosGUI(usr, proceso);
        mostrarVentana(doc);
    }

    public void abrirModificarArchivosGUI(Usuarios usr, ProcedimientosM procedimiento) {
        ModificarArchivosGUI doc = new ModificarArchivosGUI(usr, procedimiento);
        mostrarVentana(doc);
    }

    public void abrirProcedimientosGUI(Usuarios usr, ProcedimientosM procedimiento) {
        ProcedimientosGUI doc = new ProcedimientosGUI(usr, procedimiento);
        mostrarVentana(doc);
    }

    public void abrirRegistrosGUI(Usuarios usr, ProcedimientosM procedimiento) {
        RegistrosGUI formatos = new RegistrosGUI(usr, procedimiento);
        mostrarVentana(formatos);
    }

    public void abrirSolicitudCambioGUI(Usuarios usr) {
        SolicitudesGUI doc = new SolicitudesGUI(usr);
        mostrarVentana(doc);
    }

    public void abrirSolicitudGUI(Usuarios usr, ProcesosM proceso) {
        SolicitudGUI doc = new SolicitudGUI(usr, proceso);
        mostrarVentana(doc);
    }

    public void aceptarSolicitud(Connection conexion, SolicitudesM solicitud) throws SQLException, ClassNotFoundException {
        Date fechaActual = new Date(); // Obtener la fecha actual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formatear la fecha en formato dd/MM/yyyy
        String fechaFormateada = sdf.format(fechaActual);
        switch (solicitud.getAccion()) {
            case "ACTUALIZAR":
                if (solicitud.getTipoArchivo().equals("MANUAL")) {
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_REVISION_FROM_DOCPROCEDIMIENTOS);
                    ps.setString(1, solicitud.getRevNueva());
                    ps.setString(2, solicitud.getProcedimiento());
                    ps.executeUpdate();

                    PreparedStatement ps1 = conexion.prepareStatement(UPDATE_MANUAL_FROM_DOCUMENTOS);
                    ps1.setString(1, solicitud.getRevNueva());
                    ps1.setString(2, fechaFormateada);
                    ps1.setString(3, solicitud.getNombre());
                    ps1.setBytes(4, solicitud.getArchivo());
                    ps1.setInt(5, solicitud.getId());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO")) {
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_REVISION_FROM_DOCPROCEDIMIENTOS);
                    ps.setString(1, solicitud.getRevNueva());
                    ps.setString(2, solicitud.getProcedimiento());
                    ps.executeUpdate();

                    PreparedStatement ps1 = conexion.prepareStatement(UPDATE_DIAGRAMA_FLUJO_FROM_DOCUMENTOS);
                    ps1.setString(1, solicitud.getRevNueva());
                    ps1.setString(2, fechaFormateada);
                    ps1.setString(3, solicitud.getNombre());
                    ps1.setBytes(4, solicitud.getArchivo());
                    ps1.setInt(5, solicitud.getId());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE TORTUGA")) {
                    PreparedStatement ps1 = conexion.prepareStatement(UPDATE_DIAGRAMA_TORTUGA_BY_PROCESO);
                    ps1.setString(1, solicitud.getNombre());
                    ps1.setBytes(2, solicitud.getArchivo());
                    ps1.setString(3, solicitud.getProceso());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("FORMATO")) {
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_FORMATO);
                    ps.setString(1, solicitud.getNombre());
                    ps.setBytes(2, solicitud.getArchivo());
                    ps.setInt(3, solicitud.getId());
                    ps.setString(4, solicitud.getNombreD());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("INSTRUCTIVO")) {
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_INSTRUCTIVO_FROM_DOCUMENTOS);
                    ps.setString(1, solicitud.getNombre());
                    ps.setBytes(2, solicitud.getArchivo());
                    ps.setInt(3, solicitud.getId());
                    ps.setString(4, solicitud.getNombreD());
                    ps.executeUpdate();
                }

                break;
            case "AGREGAR":
                if (solicitud.getTipoArchivo().equals("MANUAL") || solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO") || solicitud.getTipoArchivo().equals("INSTRUCTIVO")) {
                    PreparedStatement ps = conexion.prepareStatement(INSERT_INTO_DOCUMENTOS_WITH_SUBQUERY);
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
                    PreparedStatement ps = conexion.prepareStatement(INSERT_INTO_FORMATOS);
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getNombre());
                    ps.setBytes(3, solicitud.getArchivo());
                    ps.executeUpdate();
                }
                break;
            case "ELIMINAR":
                if (solicitud.getTipoArchivo().equals("MANUAL") || solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO") || solicitud.getTipoArchivo().equals("INSTRUCTIVO")) {
                    PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_DOCUMENTOS);
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getTipoArchivo());
                    ps.setString(3, solicitud.getNombre());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("FORMATO")) {
                    PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_FORMATOS);
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getNombre());
                    ps.setBytes(3, solicitud.getArchivo());
                    ps.executeUpdate();
                }

        }
        insertarRegistro(conexion, solicitud, fechaFormateada);
        eliminarSolicitud(conexion, solicitud.getCodigo());
    }

    public void actualizarDiagramaTortuga(Connection conexion, ProcesosM proceso) {
        try {
            PreparedStatement ps = conexion.prepareStatement(UPDATE_DIAGRAMA_TORTUGA_BY_ID);
            ps.setString(1, proceso.getNombreDT());
            ps.setBytes(2, proceso.getDiagramaTortuga());
            ps.setInt(3, proceso.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Errror al Actualizar el diagrama de tortuga: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarDocumento(Connection conexion, DocumentosM documento) {
        Date fechaActual = new Date();
        try {
            PreparedStatement ps = conexion.prepareStatement(UPDATE_DOCUMENTO);
            ps.setString(1, formatearFecha(fechaActual));
            ps.setString(2, documento.getNombre());
            ps.setBytes(3, documento.getContenido());
            ps.setInt(4, documento.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Errror al Actualizar el documento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarInfoDocumentos(Connection conexion, DocumentosM documento) {
        Date fechaActual = new Date();
        try (PreparedStatement pstmtSelect = conexion.prepareStatement(INSERT_INTO_DOCUMENTOS)) {
            pstmtSelect.setInt(1, documento.getIdProceso());
            pstmtSelect.setInt(2, documento.getIdProcedimiento());
            pstmtSelect.setString(3, documento.getRevision());
            pstmtSelect.setString(4, formatearFecha(fechaActual));
            pstmtSelect.setString(5, documento.getTipo());
            pstmtSelect.setString(6, documento.getNombre());
            pstmtSelect.setBytes(7, documento.getContenido());
            pstmtSelect.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarFormatoNuevo(Connection conexion, FormatosM formato) {
        try (PreparedStatement pstmtSelect = conexion.prepareStatement(INSERT_INTO_FORMATOS)) {
            pstmtSelect.setInt(1, formato.getIdP());
            pstmtSelect.setString(2, formato.getNombre());
            pstmtSelect.setBytes(3, formato.getContenido());
            pstmtSelect.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarProcedimiento(Connection conexion, ProcedimientosM procedimiento) {
        try (PreparedStatement pstmtSelect = conexion.prepareStatement(INSERT_INTO_PROCEDIMIENTOS)) {
            pstmtSelect.setInt(1, procedimiento.getIdp());
            pstmtSelect.setString(2, procedimiento.getNo());
            pstmtSelect.setString(3, procedimiento.getCodigo());
            pstmtSelect.setString(4, procedimiento.getRevision());
            pstmtSelect.setString(5, procedimiento.getProceso());
            pstmtSelect.setString(6, procedimiento.getProcedimiento());
            pstmtSelect.setString(7, procedimiento.getEncargado());
            pstmtSelect.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarSolicitud(Connection conexion, SolicitudesM solicitud) {
        try (PreparedStatement pstmtSelect = conexion.prepareStatement(INSERT_INTO_SOLICITUDES_CAMBIO)) {
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

    public void cargarArchivo(String rutaArchivo, Consumer<byte[]> setterFunction) {
        try {
            byte[] archivoData = leerArchivo(rutaArchivo);
            setterFunction.accept(archivoData);
        } catch (IOException ex) {
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void ejecutarArchivo(Connection conexion, String nombre) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(SELECT_CONTENIDO_NOMBRE_FROM_FORMATOS);
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

    public void ejecutarArchivoSC(Connection conexion, String nombre) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(SELECT_NOMBRE_ARCHIVO_FROM_SOLICITUDES_CAMBIO);
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

    public void ejecutarDiagramaTortuga(Connection conexion, ProcesosM proceso) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(SELECT_DIAGRAMA_TORTUGA);
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

    public void ejecutarFormato(Connection conexion, String nombreFormato) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(SELECT_CONTENIDO_NOMBRE_FROM_FORMATOS);

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
            Utilidades.manejarExcepcion("ERROR al cerrar la conexión: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Utilidades.manejarExcepcion("ERROR al cerrar la conexión: ", ex);
                Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void ejecutarManual(Connection conexion, ProcedimientosM procedimiento, String tipoDocto) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(SELECT_CONTENIDO_NOMBRE_FROM_DOCUMENTOS);

            ps.setString(1, tipoDocto);
            ps.setInt(2, procedimiento.getIdp());

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
                } else {
                    System.out.println("El archivo no se encontró.");
                }
            } else {
                System.out.println("No se encontraron datos para el ID proporcionado.");
            }
        } catch (IOException | SQLException ex) {
            Utilidades.manejarExcepcion("Error al abrir o crear el archivo PDF: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Utilidades.manejarExcepcion("Error al cerrar recursos: ", ex);
                Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void eliminarDocumento(Connection conexion, DocumentosM documento) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_DOCUMENTOS_BY_ID);
            ps.setInt(1, documento.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar el documento seleccionado: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarFormato(Connection conexion, FormatosM formato) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_FORMATOS_BY_ID);
            ps.setInt(1, formato.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar el formato: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarSolicitud(Connection conexion, String nombre) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_SOLICITUDES_CAMBIO);
        ps.setString(1, nombre);
        ps.executeUpdate();
    }

    public boolean esUsuarioAutorizado(Usuarios usuario) {
        return usuario.getId() == 12 || usuario.getId() == 8 || usuario.getId() == 15 || usuario.getId() == 17;
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
            Logger.getLogger(InspeccionReciboServicio.class.getName()).log(Level.SEVERE, null, ex);
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

    public void guardarCambiosProcedimiento(Connection conexion, ProcedimientosM procedimiento) {
        try {
            PreparedStatement ps1 = conexion.prepareStatement(UPDATE_PROCEDIMIENTOS_BY_ID);
            ps1.setString(1, procedimiento.getNo());
            ps1.setString(2, procedimiento.getCodigo());
            ps1.setString(3, procedimiento.getRevision());
            ps1.setString(4, procedimiento.getProcedimiento());
            ps1.setString(5, procedimiento.getEncargado());
            ps1.setInt(6, procedimiento.getId());
            ps1.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al Actualizar la información: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarRegistro(Connection conexion, SolicitudesM solicitud, String fecha) {
        try (PreparedStatement pstmtSelect = conexion.prepareStatement(INSERT_INTO_REGISTROS)) {
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

    public void mostrarVentana(JFrame frame) {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public List<DocumentosM> obtenerDocumentos(Connection conexion, int id) throws SQLException {
        List<DocumentosM> documentos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_DOCUMENTOS)) {
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

    public List<SolicitudesM> obtenerSolicitudes(Connection conexion) throws SQLException {
        List<SolicitudesM> listaSolicitudes = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_SOLICITUDES_CAMBIO)) {

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

    public List<FormatosM> recuperarFormatos(Connection conexion, int id) throws SQLException {
        List<FormatosM> formatos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_FORMATOS)) {
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

    public List<ProcedimientosM> recuperarProcedimientos(Connection conexion, int idp) throws SQLException {
        List<ProcedimientosM> procedimientos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_PROCEDIMIENTOS_BY_IDP)) {
            consulta.setInt(1, idp);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                ProcedimientosM procedimiento = new ProcedimientosM(
                        resultado.getInt("id"),
                        resultado.getInt("idp"),
                        resultado.getString("no"),
                        resultado.getString("codigo"),
                        resultado.getString("revision"),
                        resultado.getString("proceso"),
                        resultado.getString("procedimiento"),
                        resultado.getString("encargado")
                );
                procedimientos.add(procedimiento);
            }
        }
        return procedimientos;
    }

    public ProcesosM recuperarProceso(Connection conexion, int id) throws SQLException {
        ProcesosM proceso = null;
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_PROCESOS)) {
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

    public List<RegistrosM> recuperarRegistros(Connection conexion, int id, Usuarios usr) throws SQLException {
        List<RegistrosM> registros = new ArrayList<>();
        if (usr.getId() == 12 || usr.getId() == 8) {
            try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_REGISTROS_BY_IDP)) {
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
            try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_REGISTROS)) {
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

    public File seleccionarArchivo(Component parentComponent) {
        JnaFileChooser jfc = new JnaFileChooser();
        jfc.addFilter("pdf", "xlsx", "xls", "pdf", "PDF", "ppt", "pptx", "doc", "docx", "png", "jpg", "jpeg", "png");
        boolean action = jfc.showOpenDialog((Window) parentComponent);
        if (action) {
            return jfc.getSelectedFile();
        }

        return null;
    }
}
