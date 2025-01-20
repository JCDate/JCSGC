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
import java.awt.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jnafilechooser.api.JnaFileChooser;

public class ControlDocumentacionServicio {

    final String UPDATE_REVISION_FROM_DOCPROCEDIMIENTOS = "UPDATE docprocedimientos SET revision = ? WHERE procedimiento = ?";
    final String UPDATE_PROCEDIMIENTOS_BY_ID = "UPDATE docprocedimientos SET no = ?, codigo = ?, revision = ?, procedimiento = ?, encargado = ? WHERE id = ?";
    final String UPDATE_MANUAL_FROM_DOCUMENTOS = "UPDATE documentos SET revision = ?, fechaActualizacion = ?, nombre = ?, rutaArchivo = ? WHERE tipo = 'MANUAL' AND idProcedimiento = ?";
    final String UPDATE_DIAGRAMA_FLUJO_FROM_DOCUMENTOS = "UPDATE documentos SET revision = ?, fechaActualizacion = ?, nombre = ?, rutaArchivo = ? WHERE tipo = 'DIAGRAMA DE FLUJO' AND idProcedimiento = ?";
    final String UPDATE_DIAGRAMA_TORTUGA_BY_PROCESO = "UPDATE docprocesos SET nombreDt = ?, rutaArchivo = ? WHERE proceso = ?";
    final String UPDATE_FORMATO = "UPDATE formatos SET nombre = ?, rutaArchivo = ? WHERE idProcedimiento = ? AND nombre = ?";
    final String UPDATE_INSTRUCTIVO_FROM_DOCUMENTOS = "UPDATE documentos SET nombre = ?, rutaArchivo = ? WHERE idProcedimiento = ? AND tipo = 'INSTRUCTIVO' AND nombre = ?";
    final String UPDATE_DIAGRAMA_TORTUGA_BY_ID = "UPDATE docprocesos SET nombreDt = ?, rutaArchivo = ? WHERE id = ?";
    final String UPDATE_DOCUMENTO = "UPDATE documentos SET fechaActualizacion = ?, nombre = ?, rutaArchivo = ? WHERE id = ?";
    final String INSERT_INTO_DOCUMENTOS_WITH_SUBQUERY = "INSERT INTO documentos(idProceso, idProcedimiento, revision, fechaActualizacion, tipo, nombre, contenido) VALUES((SELECT idProceso FROM docProcesos WHERE nombreProceso = ?), ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_DOCUMENTOS = "INSERT INTO documentos(idProceso, idProcedimiento, revision, fechaActualizacion, tipo, nombre, rutaArchivo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_FORMATOS = "INSERT INTO formatos(idProcedimiento, nombre, rutaArchivo) VALUES(?, ?, ?)";
    final String INSERT_INTO_SOLICITUDES_CAMBIO = "INSERT INTO solicitudescambio(idp, codigo, proceso, procedimiento, revAnterior, revNueva, encargado, accion, tipoArchivo, nombrePrev, nombre, rutaArchivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        ModificarArchivosGUI doc = new ModificarArchivosGUI(usr, documento, "documentos");
        mostrarVentana(doc);
    }

    public void abrirModificarArchivosGUI(Usuarios usr, ProcesosM proceso) {
        ModificarArchivosGUI doc = new ModificarArchivosGUI(usr, proceso, "DiagramasTortuga");
        mostrarVentana(doc);
    }

    public void abrirModificarArchivosGUI(Usuarios usr, ProcedimientosM procedimiento) {
        ModificarArchivosGUI doc = new ModificarArchivosGUI(usr, procedimiento, "Formatos");
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
                    eliminarManualAnterior(conexion, solicitud.getId(), "MANUAL");
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_REVISION_FROM_DOCPROCEDIMIENTOS);
                    ps.setString(1, solicitud.getRevNueva());
                    ps.setString(2, solicitud.getProcedimiento());
                    ps.executeUpdate();

                    PreparedStatement ps1 = conexion.prepareStatement(UPDATE_MANUAL_FROM_DOCUMENTOS);
                    ps1.setString(1, solicitud.getRevNueva());
                    ps1.setString(2, fechaFormateada);
                    ps1.setString(3, solicitud.getNombre());
                    ps1.setString(4, solicitud.getRutaArchivo());
                    ps1.setInt(5, solicitud.getId());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO")) {
                    eliminarManualAnterior(conexion, solicitud.getId(), "DIAGRAMA DE FLUJO");
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_REVISION_FROM_DOCPROCEDIMIENTOS);
                    ps.setString(1, solicitud.getRevNueva());
                    ps.setString(2, solicitud.getProcedimiento());
                    ps.executeUpdate();

                    PreparedStatement ps1 = conexion.prepareStatement(UPDATE_DIAGRAMA_FLUJO_FROM_DOCUMENTOS);
                    ps1.setString(1, solicitud.getRevNueva());
                    ps1.setString(2, fechaFormateada);
                    ps1.setString(3, solicitud.getNombre());
                    ps1.setString(4, solicitud.getRutaArchivo());
                    ps1.setInt(5, solicitud.getId());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE TORTUGA")) {
                    PreparedStatement ps1 = conexion.prepareStatement(UPDATE_DIAGRAMA_TORTUGA_BY_PROCESO);
                    ps1.setString(1, solicitud.getNombre());
                    ps1.setString(2, solicitud.getRutaArchivo());
                    ps1.setString(3, solicitud.getProceso());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("FORMATO")) {
                    eliminarManualAnterior(conexion, solicitud.getId(), "FORMATO");

                    PreparedStatement ps = conexion.prepareStatement(UPDATE_FORMATO);
                    ps.setString(1, solicitud.getNombre());
                    ps.setString(2, solicitud.getRutaArchivo());
                    ps.setInt(3, solicitud.getId());
                    ps.setString(4, solicitud.getNombreD());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("INSTRUCTIVO")) {
                    eliminarManualAnterior(conexion, solicitud.getId(), "INSTRUCTIVO");
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_INSTRUCTIVO_FROM_DOCUMENTOS);
                    ps.setString(1, solicitud.getNombre());
                    ps.setString(2, solicitud.getRutaArchivo());
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
                    ps.setString(7, solicitud.getRutaArchivo());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("FORMATO")) {
                    PreparedStatement ps = conexion.prepareStatement(INSERT_INTO_FORMATOS);
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getNombre());
                    ps.setString(3, solicitud.getRutaArchivo());
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
                    ps.setString(3, solicitud.getRutaArchivo());
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
            ps.setString(2, proceso.getRutaArchivo());
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
            ps.setString(3, documento.getRutaArchivo());
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
            pstmtSelect.setString(7, documento.getRutaArchivo());
            pstmtSelect.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarFormatoNuevo(Connection conexion, FormatosM formato) {
        try (PreparedStatement pstmtSelect = conexion.prepareStatement(INSERT_INTO_FORMATOS)) {
            pstmtSelect.setInt(1, formato.getIdP());
            pstmtSelect.setString(2, formato.getNombre());
            pstmtSelect.setString(3, formato.getRutaArchivo());
            pstmtSelect.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al agregar el formato", ex);
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
            pstmtSelect.setString(12, solicitud.getRutaArchivo());
            pstmtSelect.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
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

    public void eliminarManualAnterior(Connection conexion, int id, String tipoArchivo) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM documentos WHERE id = ? AND tipo = ?");
            ps.setInt(1, id);
            ps.setString(2, tipoArchivo);
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
            Utilidades.manejarExcepcion("ERROR al registrar la solicitud", ex);
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
                        resultado.getString("rutaArchivo")
                );
                documentos.add(documento);
            }
        }
        return documentos;
    }
    
    public List<DocumentosM> obtenerInstructivos(Connection conexion, int id,String tipoArchivo) throws SQLException {
        List<DocumentosM> documentos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM documentos WHERE idProcedimiento = ? AND tipo = ?")) {
            consulta.setInt(1, id);
            consulta.setString(2, tipoArchivo);
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
                        resultado.getString("rutaArchivo")
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
                        resultado.getString("rutaArchivo")
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
                        resultado.getString("rutaArchivo")
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
                        resultado.getString("rutaArchivo")
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

    // Método para limpiar el nombre del archivo
    private String limpiarNombreArchivo(String nombreArchivo) {
        // Reemplaza los caracteres no válidos en el sistema de archivos
        return nombreArchivo.replaceAll("[<>:\"/\\|?*]", "_");
    }

    public void abrirDocumento(String rutaArchivo) {
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            Utilidades.manejarExcepcion("La ruta del archivo no es válida.", null);
            return;
        }

        String urlArchivo = "\\\\" + Utilidades.SERVIDOR + "\\" + rutaArchivo; // Ruta de red

        try {
            File archivo = new File(urlArchivo);
            if (!archivo.exists()) {
                Utilidades.manejarExcepcion("El archivo no existe en la ruta especificada.", null);
                return;
            }

            abrirArchivoLocal(archivo);
        } catch (IOException ex) {
            Utilidades.manejarExcepcion("Error al abrir el archivo local: ", ex);
        }
    }

    private void abrirArchivoLocal(File archivo) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(archivo);
    }

    public void eliminarDiagramaAnterior(Connection conexion, ProcesosM proceso) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE nombreDt, rutaArchivo FROM docProcesos WHERE id = ?");
            ps.setInt(1, proceso.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar el documento seleccionado: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarDiagramaAnterior(ProcesosM proceso) {
        String urlArchivo = "\\\\" + Utilidades.SERVIDOR + "\\" + proceso.getRutaArchivo(); // Ruta de red

        if (!Utilidades.eliminarArchivo(urlArchivo)) {
            JOptionPane.showMessageDialog(null, "El archivo no se pudo eliminar o no existe.");
        }
    }

    public void eliminarArchivoFormato(FormatosM formato) {
        String urlArchivo = "\\\\" + Utilidades.SERVIDOR + "\\" + formato.getRutaArchivo(); // Ruta de red

        if (!Utilidades.eliminarArchivo(urlArchivo)) {
            JOptionPane.showMessageDialog(null, "El archivo no se pudo eliminar o no existe.");
        }
    }

    public void migrarArchivos(Connection conexion) {
        String carpetaDestino = "C:/xampp/htdocs/archivos/ControlDocumentos/Solicitudes/";
        File carpeta = new File(carpetaDestino);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT id, nombre, archivo FROM solicitudescambio WHERE archivo IS NOT NULL AND archivo != '' ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idp = rs.getInt("id");
                byte[] documento = rs.getBytes("archivo");

                // Guarda el archivo en el sistema de archivos
                String nombreArchivo = rs.getString("nombre"); // Usa un formato adecuado
                File archivoDestino = new File(carpetaDestino + nombreArchivo);
                Files.write(archivoDestino.toPath(), documento);

                // Actualiza la ruta en la base de datos
                PreparedStatement psUpdate = conexion.prepareStatement("UPDATE solicitudescambio SET rutaArchivo = ? WHERE id = ?");
                psUpdate.setString(1, "archivos/ControlDocumentos/Solicitudes/" + nombreArchivo);
                psUpdate.setInt(2, idp);
                psUpdate.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarProcedimiento(Connection conexion, ProcedimientosM procedimiento) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM docprocedimientos WHERE id = ?");
            ps.setInt(1, procedimiento.getId());
            ps.executeUpdate();

            ps = conexion.prepareStatement("DELETE FROM documentos WHERE idProcedimiento = ?");
            ps.setInt(1, procedimiento.getId());
            ps.executeUpdate();

            ps = conexion.prepareStatement("DELETE FROM formatos WHERE idProcedimiento = ?");
            ps.setInt(1, procedimiento.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
