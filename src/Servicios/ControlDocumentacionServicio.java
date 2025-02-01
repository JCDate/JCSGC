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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ControlDocumentacionServicio {

    final String DELETE_FROM_DOCUMENTOS = "DELETE FROM documentos WHERE idProcedimiento = ? AND tipo = ?";
    final String DELETE_FROM_DOCUMENTOS_WHERE_ID = "DELETE FROM documentos WHERE id = ?";
    final String DELETE_FROM_DOCUMENTOS_WHERE_IDP = "DELETE FROM documentos WHERE idProcedimiento = ?";
    final String DELETE_FROM_DOCPROCEDIMIENTOS_WHERE_ID = "DELETE FROM docprocedimientos WHERE id = ?";
    final String DELETE_FROM_FORMATOS = "DELETE FROM formatos WHERE idProcedimiento = ? AND nombre = ? AND rutaArchivo = ?";
    final String DELETE_FROM_FORMATOS_WHERE_ID = "DELETE FROM formatos WHERE id = ?";
    final String DELETE_FROM_FORMATOS_WHERE_IDP = "DELETE FROM formatos WHERE idProcedimiento = ?";
    final String DELETE_FROM_SOLICITUDES_CAMBIO = "DELETE FROM solicitudescambio WHERE idp = ?";
    final String INSERT_INTO_DOCUMENTOS = "INSERT INTO documentos(idProceso, idProcedimiento, revision, fechaActualizacion, tipo, nombre, rutaArchivo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_DOCUMENTOS_WITH_SUBQUERY = "INSERT INTO documentos(idProceso, idProcedimiento, revision, fechaActualizacion, tipo, nombre, rutaArchivo) VALUES((SELECT id FROM docProcesos WHERE proceso = ?), ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_FORMATOS = "INSERT INTO formatos(idProcedimiento, nombre, rutaArchivo) VALUES(?, ?, ?)";
    final String INSERT_INTO_PROCEDIMIENTOS = "INSERT INTO docprocedimientos(idp, no, codigo, revision, proceso, procedimiento, encargado) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_REGISTROS = "INSERT INTO docregistros(idp, fechaModificacion, codigo, proceso, procedimiento, revAnterior, revNueva, encargado, accion, tipoArchivo, nombre) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_SOLICITUDES_CAMBIO = "INSERT INTO solicitudescambio(idp, codigo, proceso, procedimiento, revAnterior, revNueva, encargado, accion, tipoArchivo, nombrePrev, nombre, rutaArchivo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    final String SELECT_COUNT_FROM_DOCUMENTOS_WHERE_IDP_AND_TIPO = "SELECT COUNT(*) FROM documentos WHERE idProcedimiento = ? AND tipo = ?";
    final String SELECT_COUNT_FROM_DOCPROCESOS_WHERE_ID_AND_RUTA_ARCHIVO = "SELECT COUNT(*) FROM docprocesos WHERE id = ? AND rutaArchivo != ''";
    final String SELECT_CONTENIDO_NOMBRE_FROM_DOCUMENTOS = "SELECT contenido, nombre FROM documentos WHERE tipo = ? AND idProcedimiento = ?";
    final String SELECT_CONTENIDO_NOMBRE_FROM_FORMATOS = "SELECT contenido, nombre FROM formatos WHERE nombre = ?";
    final String SELECT_DIAGRAMA_TORTUGA = "SELECT diagramaTortuga FROM docprocesos WHERE id = ?";
    final String SELECT_FROM_DOCUMENTOS = "SELECT * FROM documentos WHERE idProcedimiento = ?";
    final String SELECT_FROM_DOCUMENTOS_WHERE_IDP_AND_TIPO = "SELECT * FROM documentos WHERE idProcedimiento = ? AND tipo = ?";
    final String SELECT_FROM_FORMATOS = "SELECT * FROM formatos WHERE idProcedimiento = ?";
    final String SELECT_FROM_PROCEDIMIENTOS_WHERE_IDP = "SELECT * FROM docprocedimientos WHERE idp = ?";
    final String SELECT_FROM_PROCESOS = "SELECT * FROM docProcesos WHERE id = ?";
    final String SELECT_FROM_REGISTROS = "SELECT * FROM docregistros WHERE idp = ? AND tipoArchivo IN ('MANUAL', 'DIAGRAMA DE FLUJO', 'INSTRUCTIVO', 'FORMATO', 'DIAGRAMA DE TORTUGA') ORDER BY DATE(fechaModificacion) DESC LIMIT 1";
    final String SELECT_FROM_REGISTROS_WHERE_IDP = "SELECT * FROM docregistros WHERE idp = ?";
    final String SELECT_FROM_SOLICITUDES_CAMBIO = "SELECT * FROM solicitudescambio";
    final String SELECT_NOMBRE_ARCHIVO_FROM_SOLICITUDES_CAMBIO = "SELECT nombre, archivo, SUBSTRING_INDEX(nombre, '.', -1) AS extension FROM solicitudescambio WHERE codigo = ?";
    final String SELECT_NOMBRE_FROM_DOCUMENTOS_WHERE_IDP_AND_TIPO = "SELECT nombre FROM documentos WHERE idProceso = ? AND tipo = ?";
    final String SELECT_NOMBREDT_FROM_DOCPROCESOS_WHERE_ID = "SELECT nombreDt FROM docprocesos WHERE id = ?";
    final String SELECT_RUTA_ARCHIVO_FROM_DOCUMENTOS = "SELECT rutaArchivo FROM documentos WHERE idProcedimiento = ? AND tipo = ?";
    final String SELECT_RUTA_ARCHIVO_FROM_DOCPROCESOS = "SELECT rutaArchivo FROM docprocesos WHERE proceso = ?";
    final String SELECT_RUTA_ARCHIVO_FROM_FORMATOS = "SELECT rutaArchivo FROM formatos WHERE idProcedimiento = ? AND nombre = ?";
    final String UPDATE_REVISION_FROM_DOCPROCEDIMIENTOS = "UPDATE docprocedimientos SET revision = ? WHERE procedimiento = ?";
    final String UPDATE_PROCEDIMIENTOS_WHERE_ID = "UPDATE docprocedimientos SET no = ?, codigo = ?, revision = ?, procedimiento = ?, encargado = ? WHERE id = ?";
    final String UPDATE_MANUAL_FROM_DOCUMENTOS = "UPDATE documentos SET revision = ?, fechaActualizacion = ?, nombre = ?, rutaArchivo = ? WHERE tipo = 'MANUAL' AND idProcedimiento = ?";
    final String UPDATE_DIAGRAMA_FLUJO_FROM_DOCUMENTOS = "UPDATE documentos SET revision = ?, fechaActualizacion = ?, nombre = ?, rutaArchivo = ? WHERE tipo = 'DIAGRAMA DE FLUJO' AND idProcedimiento = ?";
    final String UPDATE_DIAGRAMA_TORTUGA = "UPDATE docprocesos SET nombreDt = '', rutaArchivo = '' WHERE proceso = ?";
    final String UPDATE_DIAGRAMA_TORTUGA_WHERE_PROCESO = "UPDATE docprocesos SET nombreDt = ?, rutaArchivo = ? WHERE proceso = ?";
    final String UPDATE_FORMATO = "UPDATE formatos SET nombre = ?, rutaArchivo = ? WHERE idProcedimiento = ? AND nombre = ?";
    final String UPDATE_INSTRUCTIVO_FROM_DOCUMENTOS = "UPDATE documentos SET nombre = ?, rutaArchivo = ? WHERE idProcedimiento = ? AND tipo = 'INSTRUCTIVO' AND nombre = ?";
    final String UPDATE_DIAGRAMA_TORTUGA_WHERE_ID = "UPDATE docprocesos SET nombreDt = ?, rutaArchivo = ? WHERE id = ?";
    final String UPDATE_DOCUMENTO = "UPDATE documentos SET fechaActualizacion = ?, nombre = ?, rutaArchivo = ? WHERE id = ?";

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
        ModificarArchivosGUI doc = new ModificarArchivosGUI(usr, documento, "Documentos");
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
                    eliminarDocumentoAnterior(conexion, solicitud.getId(), solicitud.getTipoArchivo());
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_REVISION_FROM_DOCPROCEDIMIENTOS);
                    ps.setString(1, solicitud.getRevNueva());
                    ps.setString(2, solicitud.getProcedimiento());
                    ps.executeUpdate();

                    solicitud.setRutaArchivo(actualizarRutaDocumento(solicitud.getRutaArchivo(), "Documentos"));
                    PreparedStatement ps1 = conexion.prepareStatement(UPDATE_MANUAL_FROM_DOCUMENTOS);
                    ps1.setString(1, solicitud.getRevNueva());
                    ps1.setString(2, fechaFormateada);
                    ps1.setString(3, solicitud.getNombre());
                    ps1.setString(4, solicitud.getRutaArchivo());
                    ps1.setInt(5, solicitud.getId());
                    ps1.executeUpdate();

                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO")) {
                    eliminarDocumentoAnterior(conexion, solicitud.getId(), solicitud.getTipoArchivo());
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_REVISION_FROM_DOCPROCEDIMIENTOS);
                    ps.setString(1, solicitud.getRevNueva());
                    ps.setString(2, solicitud.getProcedimiento());
                    ps.executeUpdate();

                    solicitud.setRutaArchivo(actualizarRutaDocumento(solicitud.getRutaArchivo(), "Documentos"));
                    PreparedStatement ps1 = conexion.prepareStatement(UPDATE_DIAGRAMA_FLUJO_FROM_DOCUMENTOS);
                    ps1.setString(1, solicitud.getRevNueva());
                    ps1.setString(2, fechaFormateada);
                    ps1.setString(3, solicitud.getNombre());
                    ps1.setString(4, solicitud.getRutaArchivo());
                    ps1.setInt(5, solicitud.getId());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE TORTUGA")) {
                    PreparedStatement ps1 = conexion.prepareStatement(UPDATE_DIAGRAMA_TORTUGA_WHERE_PROCESO);
                    eliminarDiagramaTortugaAnterior(conexion, solicitud.getProceso());
                    solicitud.setRutaArchivo(actualizarRutaDocumento(solicitud.getRutaArchivo(), "DiagramasTortuga"));
                    ps1.setString(1, solicitud.getNombre());
                    ps1.setString(2, solicitud.getRutaArchivo());
                    ps1.setString(3, solicitud.getProceso());
                    ps1.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("FORMATO")) {
                    eliminarFormatoAnterior(conexion, solicitud.getId(), solicitud.getNombreD());
                    solicitud.setRutaArchivo(actualizarRutaDocumento(solicitud.getRutaArchivo(), "Formatos"));
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_FORMATO);
                    ps.setString(1, solicitud.getNombre());
                    ps.setString(2, solicitud.getRutaArchivo());
                    ps.setInt(3, solicitud.getId());
                    ps.setString(4, solicitud.getNombreD());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("INSTRUCTIVO")) {
                    eliminarDocumentoAnterior(conexion, solicitud.getId(), "INSTRUCTIVO");
                    solicitud.setRutaArchivo(actualizarRutaDocumento(solicitud.getRutaArchivo(), "Documentos"));
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
                    solicitud.setRutaArchivo(actualizarRutaDocumento(solicitud.getRutaArchivo(), "Documentos"));
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
                    eliminarFormatoAnterior(conexion, solicitud.getId(), solicitud.getNombreD());
                    solicitud.setRutaArchivo(actualizarRutaDocumento(solicitud.getRutaArchivo(), "Formatos"));
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getNombre());
                    ps.setString(3, solicitud.getRutaArchivo());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE TORTUGA")) {
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_DIAGRAMA_TORTUGA_WHERE_PROCESO);
                    solicitud.setRutaArchivo(actualizarRutaDocumento(solicitud.getRutaArchivo(), "DiagramasTortuga"));
                    ps.setString(1, solicitud.getNombre());
                    ps.setString(2, solicitud.getRutaArchivo());
                    ps.setString(3, solicitud.getProceso());
                    ps.executeUpdate();
                }

                break;
            case "ELIMINAR":
                if (solicitud.getTipoArchivo().equals("MANUAL") || solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO") || solicitud.getTipoArchivo().equals("INSTRUCTIVO")) {
                    PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_DOCUMENTOS);
                    eliminarDocumentoAnterior(conexion, solicitud.getId(), solicitud.getTipoArchivo());
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getTipoArchivo());
                    ps.executeUpdate();
                }

                if (solicitud.getTipoArchivo().equals("FORMATO")) {
                    PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_FORMATOS);
                    eliminarFormatoAnterior(conexion, solicitud.getId(), solicitud.getNombreD());
                    ps.setInt(1, solicitud.getId());
                    ps.setString(2, solicitud.getNombreD());
                    ps.setString(3, solicitud.getRutaArchivo());
                    System.out.println("ID: " + solicitud.getId());
                    System.out.println("Nombre: " + solicitud.getNombreD());
                    System.out.println("Ruta Archivo: " + solicitud.getRutaArchivo());

                    ps.executeUpdate();

                }

                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE TORTUGA")) {
                    PreparedStatement ps = conexion.prepareStatement(UPDATE_DIAGRAMA_TORTUGA);
                    solicitud.setRutaArchivo(actualizarRutaDocumento(solicitud.getRutaArchivo(), "DiagramasTortuga"));
                    eliminarDiagramaTortugaAnterior(conexion, solicitud.getProceso());
                    ps.setString(1, solicitud.getProceso());
                    ps.executeUpdate();
                }
        }
        insertarRegistro(conexion, solicitud, fechaFormateada);
        eliminarSolicitud(conexion, solicitud.getId());
    }

    private String actualizarRutaDocumento(String rutaArchivo, String carpeta) {
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            Utilidades.manejarExcepcion("La ruta del archivo es nula o está vacía.", null);
            return null;
        }

        String nuevaRuta = null;
        String urlArchivo = "\\\\" + Utilidades.SERVIDOR + "\\" + rutaArchivo.replace("/", "\\");
        File archivoSeleccionado = new File(urlArchivo);
        if (archivoSeleccionado.exists() && archivoSeleccionado.isFile()) {
            try {
                // Construir la nueva ruta de destino
                Path destino = Paths.get(
                        "\\\\" + Utilidades.SERVIDOR,
                        "archivos",
                        "ControlDocumentos",
                        carpeta,
                        archivoSeleccionado.getName()
                );

                // Copiar el archivo al servidor
                Files.copy(archivoSeleccionado.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                nuevaRuta = "archivos\\ControlDocumentos\\" + carpeta + "\\" + archivoSeleccionado.getName();

                // Eliminar el archivo original
                Utilidades.eliminarArchivo(urlArchivo);
            } catch (IOException ex) {
                Utilidades.manejarExcepcion("Error al copiar o eliminar el archivo.", ex);
                Logger.getLogger(SolicitudGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Utilidades.manejarExcepcion("El archivo seleccionado no existe o no es válido.", null);
        }

        return nuevaRuta;
    }

    public void actualizarDiagramaTortuga(Connection conexion, ProcesosM proceso) {
        try {
            PreparedStatement ps = conexion.prepareStatement(UPDATE_DIAGRAMA_TORTUGA_WHERE_ID);
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
            ps.setString(1, Utilidades.formatearFecha(fechaActual));
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
            pstmtSelect.setString(4, Utilidades.formatearFecha(fechaActual));
            pstmtSelect.setString(5, documento.getTipo());
            pstmtSelect.setString(6, documento.getNombre());
            pstmtSelect.setString(7, documento.getRutaArchivo());
            pstmtSelect.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al actualizar el documento: ", ex);
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
            Utilidades.manejarExcepcion("ERROR al agregar el formato: ", ex);
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
            Utilidades.manejarExcepcion("ERROR al insertar la información del procedimiento: ", ex);
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
            Utilidades.manejarExcepcion("ERROR al insertar el registro de solicitud de cambio: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarDocumento(Connection conexion, DocumentosM documento) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_DOCUMENTOS_WHERE_ID);
            ps.setInt(1, documento.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar el documento seleccionado: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarDocumentoAnterior(Connection conexion, int id, String tipoArchivo) {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_RUTA_ARCHIVO_FROM_DOCUMENTOS)) {
            consulta.setInt(1, id);
            consulta.setString(2, tipoArchivo);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    String rutaArchivo = resultado.getString("rutaArchivo");
                    eliminarDocumentoAnterior(rutaArchivo);
                }
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar el documento anterior: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarFormatoAnterior(Connection conexion, int id, String nombre) {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_RUTA_ARCHIVO_FROM_FORMATOS)) {
            consulta.setInt(1, id);
            consulta.setString(2, nombre);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    String rutaArchivo = resultado.getString("rutaArchivo");
                    eliminarDocumentoAnterior(rutaArchivo);
                }
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al consultar la ruta del archivo: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarDiagramaTortugaAnterior(Connection conexion, String proceso) {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_RUTA_ARCHIVO_FROM_DOCPROCESOS)) {
            consulta.setString(1, proceso);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    String rutaArchivo = resultado.getString("rutaArchivo");
                    eliminarDocumentoAnterior(rutaArchivo);
                }
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al consultar la información del archivo: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarFormato(Connection conexion, FormatosM formato) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_FORMATOS_WHERE_ID);
            ps.setInt(1, formato.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar el formato: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarSolicitud(Connection conexion, int id) throws SQLException, ClassNotFoundException {
        PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_SOLICITUDES_CAMBIO);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public boolean esUsuarioAutorizado(Usuarios usuario) {
        return usuario.getId() == 12 || usuario.getId() == 8 || usuario.getId() == 15 || usuario.getId() == 17;
    }

    public void guardarCambiosProcedimiento(Connection conexion, ProcedimientosM procedimiento) {
        try {
            PreparedStatement ps1 = conexion.prepareStatement(UPDATE_PROCEDIMIENTOS_WHERE_ID);
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
            Utilidades.manejarExcepcion("ERROR al registrar la solicitud: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al consultar la información de los documentos: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return documentos;
    }

    public List<DocumentosM> obtenerInstructivos(Connection conexion, int id, String tipoArchivo) throws SQLException {
        List<DocumentosM> documentos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_DOCUMENTOS_WHERE_IDP_AND_TIPO)) {
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
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al consultar la información de la tabla documentos por idp: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al consultar la información de las solicitudes de cambio: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al consultar información de los formatos: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return formatos;
    }

    public List<ProcedimientosM> recuperarProcedimientos(Connection conexion, int idp) throws SQLException {
        List<ProcedimientosM> procedimientos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_PROCEDIMIENTOS_WHERE_IDP)) {
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
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al consultar la información de los procedimientos por idp: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al consultar la información de los procesos: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proceso;
    }

    public List<RegistrosM> recuperarRegistros(Connection conexion, int id, Usuarios usr) throws SQLException {
        List<RegistrosM> registros = new ArrayList<>();
        if (usr.getId() == 12 || usr.getId() == 8) {
            try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_REGISTROS_WHERE_IDP)) {
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
            } catch (SQLException ex) {
                Utilidades.manejarExcepcion("ERROR al consultar la información de los registros por idp: ", ex);
                Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
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
            } catch (SQLException ex) {
                Utilidades.manejarExcepcion("ERROR al consultar la información de los registros: ", ex);
                Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registros;
    }

    public void eliminarDocumentoAnterior(String rutaArchivo) {
        String urlArchivo = "\\\\" + Utilidades.SERVIDOR + "\\" + rutaArchivo.replace("/", "\\"); // Ruta de red

        if (!Utilidades.eliminarArchivo(urlArchivo)) {
            JOptionPane.showMessageDialog(null, "El archivo no se pudo eliminar o no existe.");
        }
    }

    public void eliminarDocumentoAnterior(ProcesosM proceso) {
        String urlArchivo = "\\\\" + Utilidades.SERVIDOR + "\\" + proceso.getRutaArchivo(); // Ruta de red

        if (!Utilidades.eliminarArchivo(urlArchivo)) {
            JOptionPane.showMessageDialog(null, "El archivo no se pudo eliminar o no existe.");
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

    public void eliminarProcedimiento(Connection conexion, ProcedimientosM procedimiento) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_DOCPROCEDIMIENTOS_WHERE_ID);
            ps.setInt(1, procedimiento.getId());
            ps.executeUpdate();

            ps = conexion.prepareStatement(DELETE_FROM_DOCUMENTOS_WHERE_IDP);
            ps.setInt(1, procedimiento.getId());
            ps.executeUpdate();

            ps = conexion.prepareStatement(DELETE_FROM_FORMATOS_WHERE_IDP);
            ps.setInt(1, procedimiento.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al eliminar el procedimiento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean existeDocumento(Connection conexion, int id, String tipo) {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_COUNT_FROM_DOCUMENTOS_WHERE_IDP_AND_TIPO)) {
            consulta.setInt(1, id);
            consulta.setString(2, tipo);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al verificar la existencia del manual: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean existeDiagramaTortuga(Connection conexion, int id) {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_COUNT_FROM_DOCPROCESOS_WHERE_ID_AND_RUTA_ARCHIVO)) {
            consulta.setInt(1, id);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al verificar la existencia del diagrama de tortuga: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String obtenerNombreDocumento(Connection conexion, int id, String tipo) {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_NOMBRE_FROM_DOCUMENTOS_WHERE_IDP_AND_TIPO)) {
            consulta.setInt(1, id);
            consulta.setString(2, tipo);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getString("nombre");
                }
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al obtener el nombre del documento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String obtenerNombreDiagramaTortuga(Connection conexion, int id) {
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_NOMBREDT_FROM_DOCPROCESOS_WHERE_ID)) {
            consulta.setInt(1, id);

            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getString("nombreDt");
                }
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al obtener el nombre del documento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
