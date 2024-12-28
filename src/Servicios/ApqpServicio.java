package Servicios;

import APQP.AgregarDoctoApqpGUI;
import APQP.AgregarActividadGUI;
import APQP.ApqpGUI;
import APQP.DocumentosApqpGUI;
import APQP.ModificarApqpGUI;
import Modelos.ApqpM;
import Modelos.DoctosApqpM;
import Modelos.Usuarios;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import jnafilechooser.api.JnaFileChooser;

public class ApqpServicio {

    // Consultas a la Base de Datos
    final String DELETE_FROM_DOCTOSAQPQ_BY_ID = "DELETE FROM doctosapqp WHERE id = ?";
    final String DELETE_FROM_DOCUMENTOS_BY_IDP = "DELETE FROM doctosapqp WHERE idp = ?";
    final String DELETE_FROM_APQP_BY_ID = "DELETE FROM apqp WHERE id = ?";
    final String INSERT_INTO_APQP = "INSERT INTO apqp(familia, etapa, actividad, requerimientos, factibilidad, equipo) VALUES(?, ?, ?, ?, ?, ?)";
    final String INSERT_INTO_DOCTOSAPQP = "INSERT INTO doctosapqp(idp, nombreDocumento, rutaArchivo) VALUES(?, ?, ?)";
    final String SELECT_COUNT_FAMILIAS_FROM_ANTECEDENTES_FAMILIAS = "SELECT COUNT(*) AS total_familias, FAM FROM (SELECT DISTINCT SUBSTRING_INDEX(familia, ' ', 2) AS FAM, CAST(SUBSTRING_INDEX(familia, ' ', -1) AS UNSIGNED) AS orden FROM antecedentesfamilia WHERE familia LIKE 'FAM%') AS resultados GROUP BY FAM ORDER BY resultados.orden";
    final String SELECT_FAMILIAS_FROM_AQPQ = "SELECT DISTINCT SUBSTRING_INDEX(familia, ' ', 2) AS FAM FROM apqp WHERE familia LIKE 'FAM%' ORDER BY CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(familia, ' ', 2), ' ', -1) AS UNSIGNED)";
    final String SELECT_FROM_APQP = "SELECT * FROM apqp WHERE familia = ? ORDER BY CAST(SUBSTRING_INDEX(actividad, '.', 1) AS UNSIGNED), CAST(SUBSTRING_INDEX(actividad, '.', -1) AS UNSIGNED)";
    final String SELECT_FROM_DOCTOSAPQP_BY_ID = "SELECT * FROM doctosapqp WHERE idp = ?";
    final String UPDATE_APQP_BY_ID = "UPDATE apqp SET etapa = ?, actividad = ?, requerimientos = ?, factibilidad = ?, equipo = ? WHERE id = ?";
    final String UPDATE_DOCUMENTOS_FROM_DOCTOSAPQP = "UPDATE doctosapqp SET nombreDocumento = ?, rutaArchivo = ? WHERE id = ?";

    public void abrirAgregarDoctoApqpGUI(Usuarios usuario, ApqpM actividad, int operacion) {
        AgregarDoctoApqpGUI apqpGUI = new AgregarDoctoApqpGUI(usuario, actividad, operacion);
        apqpGUI.setVisible(true);
    }

    public void abrirAgregarDoctoApqpGUI(Usuarios usuario, DoctosApqpM documento, int operacion) {
        AgregarDoctoApqpGUI apqpGUI = new AgregarDoctoApqpGUI(usuario, documento, operacion);
        apqpGUI.setVisible(true);
    }

    public void abrirApqpGUI(Usuarios usuario) {
        ApqpGUI apqpGUI = new ApqpGUI(usuario);
        apqpGUI.setVisible(true);
    }

    private void abrirArchivoLocal(File archivo) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(archivo);
    }

    public void abrirDocumento(String rutaArchivo) {
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            Utilidades.manejarExcepcion("La ruta del archivo no es válida.", null);
            return;
        }

        String urlArchivo = "\\\\" + Utilidades.SERVIDOR + "\\archivos\\Apqp\\" + rutaArchivo; // Ruta de red

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

    public void abrirDocumentosApqpGUI(Usuarios usuario, ApqpM actividad) {
        DocumentosApqpGUI apqpGUI = new DocumentosApqpGUI(usuario, actividad);
        apqpGUI.setVisible(true);
    }

    public void abrirDocumentosApqpGUI(Usuarios usuario, DoctosApqpM documento) {
        DocumentosApqpGUI apqpGUI = new DocumentosApqpGUI(usuario, documento);
        apqpGUI.setVisible(true);
    }

    public void abrirAgregarEtapaGUI(Usuarios usuario) {
        AgregarActividadGUI apqpGUI = new AgregarActividadGUI(usuario);
        apqpGUI.setVisible(true);
    }

    public void abrirModificarApqpGUI(Usuarios usuario, ApqpM actividad) {
        ModificarApqpGUI modificar = new ModificarApqpGUI(usuario, actividad);
        modificar.setVisible(true);
    }

    public void actualizarDocumento(Connection conexion, DoctosApqpM documento) {
        try {
            PreparedStatement ps = conexion.prepareStatement(UPDATE_DOCUMENTOS_FROM_DOCTOSAPQP);
            ps.setString(1, documento.getNombreDocto());
            ps.setString(2, documento.getRutaArchivo());
            ps.setInt(3, documento.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Errror al Actualizar el documento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> cargarFamilias(Connection conexion) throws SQLException {
        List<String> listaNoRollos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FAMILIAS_FROM_AQPQ);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                listaNoRollos.add(resultado.getString("FAM"));
            }
        }
        return listaNoRollos;
    }

    public void eliminarDocumento(Connection conexion, DoctosApqpM documento) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_DOCTOSAQPQ_BY_ID);
            ps.setInt(1, documento.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Errror al Actualizar el documento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarDocumento(Connection conexion, ApqpM actividad) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_DOCUMENTOS_BY_IDP);
            ps.setInt(1, actividad.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Errror al Actualizar el documento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarActividad(Connection conexion, ApqpM actividad) {
        try {
            PreparedStatement ps = conexion.prepareStatement(DELETE_FROM_APQP_BY_ID);
            ps.setInt(1, actividad.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Errror al Actualizar el documento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarCambios(Connection conexion, ApqpM actividad) {
        try (PreparedStatement ps = conexion.prepareStatement(UPDATE_APQP_BY_ID)) {
            ps.setString(1, actividad.getEtapa());
            ps.setString(2, actividad.getActividad());
            ps.setString(3, actividad.getRequerimientos());
            ps.setString(4, actividad.getFactibilidad());
            ps.setString(5, actividad.getEquipo());
            ps.setInt(6, actividad.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al ejecutar la consulta SQL de inserción: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarActividad(Connection conexion, ApqpM actividad) {
        try {
            PreparedStatement ps = conexion.prepareStatement(INSERT_INTO_APQP);
            ps.setString(1, actividad.getFamilia());
            ps.setString(2, actividad.getEtapa());
            ps.setString(3, actividad.getActividad());
            ps.setString(4, actividad.getRequerimientos());
            ps.setString(5, actividad.getFactibilidad());
            ps.setString(6, actividad.getEquipo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Errror al Actualizar el documento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarDocumento(Connection conexion, DoctosApqpM documento) {
        try {
            PreparedStatement ps = conexion.prepareStatement(INSERT_INTO_DOCTOSAPQP);
            ps.setInt(1, documento.getIdp());
            ps.setString(2, documento.getNombreDocto());
            ps.setString(3, documento.getRutaArchivo());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Errror al Actualizar el documento: ", ex);
            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerFamilias(Connection conexion, JComboBox cbxFamilia) {
        try {
            List<String> familias = cargarFamilias(conexion);
            familias.forEach(cbxFamilia::addItem);
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Surgio un error al cargar las Familias: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int obtenerFamilias(Connection conexion) {
        int cantidad = 0;

        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_COUNT_FAMILIAS_FROM_ANTECEDENTES_FAMILIAS);
                ResultSet resultado = consulta.executeQuery()) {

            if (resultado.next()) {
                cantidad = resultado.getInt("total_familias");
            }
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Surgió un error al cargar las Familias: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }

    public List<ApqpM> recuperarActividades(Connection conexion, String familia) throws SQLException {
        List<ApqpM> actividades = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_APQP)) {
            consulta.setString(1, familia);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                ApqpM actividad = new ApqpM(
                        resultado.getInt("id"),
                        resultado.getString("familia"),
                        resultado.getString("etapa"),
                        resultado.getString("actividad"),
                        resultado.getString("requerimientos"),
                        resultado.getString("factibilidad"),
                        resultado.getString("equipo"),
                        resultado.getBytes("documento")
                );
                actividades.add(actividad);
            }
        }
        return actividades;
    }

    public List<DoctosApqpM> recuperarDocumentos(Connection conexion, int idp) throws SQLException {
        List<DoctosApqpM> actividades = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(SELECT_FROM_DOCTOSAPQP_BY_ID)) {
            consulta.setInt(1, idp);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                DoctosApqpM documento = new DoctosApqpM(
                        resultado.getInt("id"),
                        resultado.getInt("idp"),
                        resultado.getString("nombreDocumento"),
                        resultado.getString("rutaArchivo")
                );
                actividades.add(documento);
            }
        }
        return actividades;
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
