package Servicios;

import Documentos.ControlDocumentosGUI;
import Documentos.ProcesosGUI;
import Documentos.FormatosGUI;
import Documentos.ProcedimientosGUI;
import Documentos.SolicitudGUI;
import Documentos.SolicitudesGUI;
import Modelos.DocumentosM;
import Modelos.FormatosM;
import Modelos.ProcedimientosM;
import Modelos.ProcesosM;
import Modelos.SolicitudesM;
import Modelos.Usuarios;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
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

    Connection conexion = Conexion.getInstance().getConnection();

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

    public ProcesosM recuperarProceso(Connection conexion, int id) throws SQLException {
        ProcesosM proceso = null;
        String sql = "SELECT * FROM docProcesos WHERE id = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
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

    public List<ProcedimientosM> recuperarProcedimientos(Connection conexion, int idp) throws SQLException {
        List<ProcedimientosM> procedimientos = new ArrayList<>();
        String sql = "SELECT * FROM docprocedimientos WHERE idp = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
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

    public DocumentosM recuperarDiagramaFlujo(Connection conexion, int id) throws SQLException {
        DocumentosM documento = null;
        String sql = "SELECT * FROM documentos WHERE tipo = 'diagrama_flujo' AND idProcedimiento = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                documento = new DocumentosM(
                        resultado.getInt("id"),
                        resultado.getInt("idProcedimiento"),
                        resultado.getString("fechaActualizacion"),
                        resultado.getString("tipo"),
                        resultado.getString("nombre"),
                        resultado.getBytes("contenido")
                );
            }
        }
        return documento;
    }
    
    public DocumentosM recuperarManual(Connection conexion, int id) throws SQLException {
        DocumentosM documento = null;
        String sql = "SELECT * FROM documentos WHERE tipo = 'manual' AND idProcedimiento = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, id);
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) {
                documento = new DocumentosM(
                        resultado.getInt("id"),
                        resultado.getInt("idProcedimiento"),
                        resultado.getString("fechaActualizacion"),
                        resultado.getString("tipo"),
                        resultado.getString("nombre"),
                        resultado.getBytes("contenido")
                );
            }
        }
        return documento;
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
            ps = conexion.prepareStatement("SELECT diagramaTortuga FROM docprocesos WHERE id = ?");
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
    
    public void ejecutarManual(int id) throws ClassNotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("SELECT contenido FROM documentos WHERE tipo='manual' idProcedimiento = ?");

            ps.setInt(1, id);
          
            rs = ps.executeQuery();

            if (rs.next()) {
                byte[] b = rs.getBytes(1);

                try (InputStream bos = new ByteArrayInputStream(b);
                        OutputStream out = new FileOutputStream("Manual.doc")) {

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

//    public void abrirSolicitudCambioGUI(Usuarios usr) {
//        SolicitudesGUI doc = new SolicitudesGUI(usr); // Se crea la instancia de la clase
//        doc.setVisible(true); // Se muestra visible al usuario
//        doc.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
//    }
//
//    public void agregarSolicitud(SolicitudesM solicitud) {
//        String sql = "INSERT INTO solicitudescambio(idp, codigo, nombreProceso, nombre, revAnterior, revNueva, encargado, tipoArchivo, archivo, accion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement pstmtSelect = conexion.prepareStatement(sql)) {
//            pstmtSelect.setInt(1, solicitud.getIdp());
//            pstmtSelect.setString(2, solicitud.getCodigo());
//            pstmtSelect.setString(3, solicitud.getProcedimiento());
//            pstmtSelect.setString(4, solicitud.getNombre());
//            pstmtSelect.setString(5, solicitud.getRevAnterior());
//            pstmtSelect.setString(6, solicitud.getRevNueva());
//            pstmtSelect.setString(7, solicitud.getEncargado());
//            pstmtSelect.setString(8, solicitud.getTipoArchivo());
//            pstmtSelect.setBytes(9, solicitud.getArchivo());
//            pstmtSelect.setString(10, solicitud.getAccion());
//            pstmtSelect.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//    public List<SolicitudesM> recuperarSolicitudes(Connection conexion) throws SQLException {
//        List<SolicitudesM> listaSolicitudes = new ArrayList<>();
//        String sqlConsulta = "SELECT * FROM solicitudescambio";
//        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
//
//            ResultSet resultado = consulta.executeQuery();
//            while (resultado.next()) {
//                SolicitudesM ap1 = new SolicitudesM(
//                        resultado.getInt("idp"),
//                        resultado.getString("codigo"),
//                        resultado.getString("nombreProceso"),
//                        resultado.getString("nombre"),
//                        resultado.getString("revAnterior"),
//                        resultado.getString("revNueva"),
//                        resultado.getString("encargado"),
//                        resultado.getString("tipoArchivo"),
//                        resultado.getBytes("archivo"),
//                        resultado.getString("accion")
//                );
//                listaSolicitudes.add(ap1);
//            }
//        }
//        return listaSolicitudes;
//    }
//
//    public void abrirProcedimientosGUI(Usuarios usr, ProcesosM proceso) {
//        try {
//            ProcedimientosGUI doc = new ProcedimientosGUI(usr, proceso); // Se crea la instancia de la clase
//            doc.setVisible(true); // Se muestra visible al usuario
//            doc.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void abrirControlDocumentosGUI(Usuarios usr) {
//        try {
//            ControlDocumentosGUI doc = new ControlDocumentosGUI(usr); // Se crea la instancia de la clase
//            doc.setVisible(true); // Se muestra visible al usuario
//            doc.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void abrirSolicitudGUI(Usuarios usr, ProcesosM proceso) {
//        try {
//            SolicitudGUI doc = new SolicitudGUI(usr, proceso); // Se crea la instancia de la clase
//            doc.setVisible(true); // Se muestra visible al usuario
//            doc.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
//        } catch (SQLException | ClassNotFoundException ex) {
//            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public List<ProcesosM> recuperarProcesos(Connection conexion, String nombreProceso) throws SQLException {
//        List<ProcesosM> procedimientos = new ArrayList<>();
//        String sql = "SELECT * FROM docprocesos WHERE proceso = ?";
//        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
//            consulta.setString(1, nombreProceso);
//            ResultSet resultado = consulta.executeQuery();
//            while (resultado.next()) {
//                ProcesosM ap1 = new ProcesosM(
//                        resultado.getInt("id"),
//                        resultado.getString("no"),
//                        resultado.getString("codigo"),
//                        resultado.getString("revision"),
//                        resultado.getString("proceso"),
//                        resultado.getString("procedimiento"),
//                        resultado.getString("encargado")
//                );
//                procedimientos.add(ap1);
//            }
//        }
//        return procedimientos;
//    }
//
//    public List<FormatosM> recuperarFormatos(Connection conexion, String nombreFormato) throws SQLException {
//        List<FormatosM> formatos = new ArrayList<>();
//        String sql = "SELECT * FROM formatos WHERE id_procedimiento = ?";
//        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
//            consulta.setString(1, nombreFormato);
//            ResultSet resultado = consulta.executeQuery();
//            while (resultado.next()) {
//                FormatosM ap1 = new FormatosM(
//                        resultado.getInt("id"),
//                        resultado.getInt("id_procedimiento"),
//                        resultado.getString("nombre"),
//                        resultado.getBytes("contenido")
//                );
//                formatos.add(ap1);
//            }
//        }
//        return formatos;
//    }
//
//
//
//    public List<DocumentosM> recuperarDiagramasTortuga(Connection conexion, int id) throws SQLException {
//        List<DocumentosM> documentos = new ArrayList<>();
//        String sql = "SELECT * FROM documentos WHERE tipo = 'diagrama_tortuga' AND id_procedimiento = ?";
//        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
//            consulta.setInt(1, id);
//            ResultSet resultado = consulta.executeQuery();
//            while (resultado.next()) {
//                DocumentosM ap1 = new DocumentosM(
//                        resultado.getInt("id"),
//                        resultado.getInt("id_procedimiento"),
//                        resultado.getString("tipo"),
//                        resultado.getString("nombre"),
//                        resultado.getBytes("contenido")
//                );
//                documentos.add(ap1);
//            }
//        }
//        return documentos;
//    }
//
//    public List<FormatosM> recuperarFormatos(Connection conexion, int id) throws SQLException {
//        List<FormatosM> formatos = new ArrayList<>();
//        String sql = "SELECT * FROM formatos WHERE id_procedimiento = ?";
//        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
//            consulta.setInt(1, id);
//            ResultSet resultado = consulta.executeQuery();
//            while (resultado.next()) {
//                FormatosM ap1 = new FormatosM(
//                        resultado.getInt("id"),
//                        resultado.getInt("id_procedimiento"),
//                        resultado.getString("nombre"),
//                        resultado.getBytes("contenido")
//                );
//                formatos.add(ap1);
//            }
//        }
//        return formatos;
//    }
//
//    public Image getImage(String ruta) {
//        try {
//            ImageIcon imageIcon = new ImageIcon(getClass().getResource(ruta)); //Se crea un objeto de la clase ImageIcon y se obtiene la dirección de la ruta
//            Image mainIcon = imageIcon.getImage(); // Se obtiene la imagen
//            return mainIcon; // Se retorna 
//        } catch (Exception e) {
//        }
//        return null;
//    }
//

//

//
//    public void ejecutarArchivoPPT(int id) throws ClassNotFoundException, SQLException {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            // Prepara la consulta SQL para seleccionar el contenido del archivo
//            ps = conexion.prepareStatement("SELECT contenido FROM documentos WHERE tipo = 'diagrama_flujo' AND id = ?");
//            ps.setInt(1, id);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                byte[] b = rs.getBytes(1);
//
//                try (InputStream bos = new ByteArrayInputStream(b);
//                        OutputStream out = new FileOutputStream("nuevoArchivo.ppt")) {
//
//                    byte[] buffer = new byte[1024];
//                    int bytesRead;
//
//                    while ((bytesRead = bos.read(buffer)) != -1) {
//                        out.write(buffer, 0, bytesRead);
//                    }
//
//                    System.out.println("Archivo PowerPoint creado correctamente.");
//                }
//            } else {
//                System.out.println("No se encontraron datos para el ID proporcionado.");
//            }
//        } catch (IOException | SQLException ex) {
//            System.out.println("Error al abrir o crear el archivo PowerPoint: " + ex.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Error al cerrar recursos: " + e.getMessage());
//            }
//        }
//    }
//
//    public void abrirFormatosGUI(Usuarios usr, ProcesosM proceso) {
//        FormatosGUI formatos = new FormatosGUI(usr, proceso); // Se crea la instancia de la clase
//        formatos.setVisible(true); // Se muestra visible al usuario
//        formatos.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
//    }
//
//    public void ejecutarArchivo(String nombre) throws ClassNotFoundException, SQLException {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            ps = conexion.prepareStatement("SELECT contenido, SUBSTRING_INDEX(nombre, '.', -1) AS extension FROM formatos WHERE nombre = ?");
//            ps.setString(1, nombre);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                byte[] b = rs.getBytes("contenido");
//                String tipo = rs.getString("extension");
//
//                String nombreArchivo = "nuevoArchivo." + tipo;
//                try (InputStream bos = new ByteArrayInputStream(b);
//                        OutputStream out = new FileOutputStream(nombreArchivo)) {
//
//                    byte[] buffer = new byte[1024];
//                    int bytesRead;
//
//                    while ((bytesRead = bos.read(buffer)) != -1) {
//                        out.write(buffer, 0, bytesRead);
//                    }
//
//                    System.out.println("Archivo " + tipo + " creado correctamente.");
//                }
//
//                // Abrir el archivo con la aplicación predeterminada
//                File archivo = new File(nombreArchivo);
//                if (archivo.exists()) {
//                    Desktop.getDesktop().open(archivo);
//                    System.out.println("Archivo " + nombreArchivo + " abierto correctamente.");
//                } else {
//                    System.out.println("El archivo no se encontró.");
//                }
//            } else {
//                System.out.println("No se encontraron datos para el ID proporcionado.");
//            }
//        } catch (IOException | SQLException ex) {
//            System.out.println("Error al abrir o crear el archivo: " + ex.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Error al cerrar recursos: " + e.getMessage());
//            }
//        }
//    }
//
//    public void ejecutarArchivoSC(String nombre) throws ClassNotFoundException, SQLException {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            ps = conexion.prepareStatement("SELECT archivo, SUBSTRING_INDEX(nombre, '.', -1) AS extension FROM solicitudescambio WHERE codigo = ?");
//            ps.setString(1, nombre);
//            rs = ps.executeQuery();
//
//            if (rs.next()) {
//                byte[] b = rs.getBytes("archivo");
//                String tipo = rs.getString("extension");
//
//                String nombreArchivo = "nuevoArchivo." + tipo;
//                try (InputStream bos = new ByteArrayInputStream(b);
//                        OutputStream out = new FileOutputStream(nombreArchivo)) {
//
//                    byte[] buffer = new byte[1024];
//                    int bytesRead;
//
//                    while ((bytesRead = bos.read(buffer)) != -1) {
//                        out.write(buffer, 0, bytesRead);
//                    }
//
//                    System.out.println("Archivo " + tipo + " creado correctamente.");
//                }
//
//                // Abrir el archivo con la aplicación predeterminada
//                File archivo = new File(nombreArchivo);
//                if (archivo.exists()) {
//                    Desktop.getDesktop().open(archivo);
//                    System.out.println("Archivo " + nombreArchivo + " abierto correctamente.");
//                } else {
//                    System.out.println("El archivo no se encontró.");
//                }
//            } else {
//                System.out.println("No se encontraron datos para el ID proporcionado.");
//            }
//        } catch (IOException | SQLException ex) {
//            System.out.println("Error al abrir o crear el archivo: " + ex.getMessage());
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            } catch (SQLException e) {
//                System.out.println("Error al cerrar recursos: " + e.getMessage());
//            }
//        }
//    }
//
//    public void eliminarSolicitud(String nombre) throws SQLException, ClassNotFoundException {
//        PreparedStatement ps = conexion.prepareStatement("DELETE FROM solicitudescambio WHERE codigo = ?");
//        ps.setString(1, nombre);
//        ps.executeUpdate();
//    }
//
//    public void aceptarSolicitud(SolicitudesM solicitud) throws SQLException, ClassNotFoundException {
//        Date fechaActual = new Date(); // Obtener la fecha actual
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formatear la fecha en formato dd/MM/yyyy
//        String fechaFormateada = sdf.format(fechaActual);
//        System.out.println("idp: " + solicitud.getIdp());
//        switch (solicitud.getAccion()) {
//            case "ACTUALIZAR":
//                if (solicitud.getTipoArchivo().equals("INSTRUCTIVO/MANUAL")) {
//                    PreparedStatement ps = conexion.prepareStatement("UPDATE docprocedimientos SET nivelRev = ?, instructivo = ? WHERE idProceso = ?");
//                    ps.setString(1, solicitud.getRevNueva());
//                    ps.setBytes(2, solicitud.getArchivo());
//                    ps.setInt(3, solicitud.getIdp());
//                    ps.executeUpdate();
//                }
//
//                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO")) {
//                    PreparedStatement ps = conexion.prepareStatement("UPDATE documentos SET nombre = ?, contenido = ? WHERE id_procedimiento = ? AND tipo = 'diagrama_flujo'");
//                    ps.setString(1, solicitud.getNombre());
//                    ps.setBytes(2, solicitud.getArchivo());
//                    ps.setInt(3, solicitud.getIdp());
//                    ps.executeUpdate();
//                }
//
//                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE TORTUGA")) {
//                    PreparedStatement ps = conexion.prepareStatement("UPDATE documentos SET nombre = ?, contenido = ? WHERE id_procedimiento = ? AND tipo = 'diagrama_tortuga'");
//                    ps.setString(1, solicitud.getNombre());
//                    ps.setBytes(2, solicitud.getArchivo());
//                    ps.setInt(3, solicitud.getIdp());
//                    ps.executeUpdate();
//                }
//
//                if (solicitud.getTipoArchivo().equals("FORMATO")) {
//                    PreparedStatement ps = conexion.prepareStatement("UPDATE formatos SET nombre = ?, contenido = ? WHERE idProceso = ?");
//                    ps.setString(1, solicitud.getNombre());
//                    ps.setBytes(2, solicitud.getArchivo());
//                    ps.setInt(3, solicitud.getIdp());
//                    ps.executeUpdate();
//                }
//
//                if (solicitud.getTipoArchivo().equals("OTROS INSTRUCTIVOS")) {
//                    PreparedStatement ps = conexion.prepareStatement("UPDATE documentos SET nombre = ?, contenido = ? WHERE idProceso = ? AND tipo_archivo = 'instructivo'");
//                    ps.setString(1, solicitud.getNombre());
//                    ps.setBytes(2, solicitud.getArchivo());
//                    ps.setInt(3, solicitud.getIdp());
//                    ps.executeUpdate();
//                }
//
//                PreparedStatement ps = conexion.prepareStatement("UPDATE docprocesos SET codigo = ?, revision = ? WHERE id = ?");
//                ps.setString(1, solicitud.getCodigo());
//                ps.setString(2, solicitud.getRevNueva());
//                ps.setInt(3, solicitud.getIdp());
//                ps.executeUpdate();
//
//                break;
//            case "AGREGAR":
//
//                if (solicitud.getTipoArchivo().equals("INSTRUCTIVO/MANUAL")) {
//                    PreparedStatement ps1 = conexion.prepareStatement("INSERT INTO docprocedimientos(idProceso, nivelRev, fecha, nombre, instructivo) VALUES(?, ?, ?, ?, ?)");
//                    ps1.setInt(1, solicitud.getIdp());
//                    ps1.setString(2, solicitud.getRevNueva());
//                    ps1.setString(3, fechaFormateada);
//                    ps1.setString(4, solicitud.getProcedimiento());
//                    ps1.setBytes(5, solicitud.getArchivo());
//                    ps1.executeUpdate();
//                }
//
//                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE FLUJO")) {
//                    PreparedStatement ps1 = conexion.prepareStatement("INSERT INTO documentos(id_procedimiento, tipo, nombre, contenido) VALUES(?, ?, ?, ?)");
//                    ps1.setInt(1, solicitud.getIdp());
//                    ps1.setString(2, "diagrama_flujo");
//                    ps1.setString(3, solicitud.getNombre());
//                    ps1.setBytes(4, solicitud.getArchivo());
//                    ps1.executeUpdate();
//                }
//
//                if (solicitud.getTipoArchivo().equals("DIAGRAMA DE TORTUGA")) {
//                    PreparedStatement ps1 = conexion.prepareStatement("INSERT INTO documentos(id_procedimiento, tipo, nombre, contenido) VALUES(?, ?, ?, ?)");
//                    ps1.setInt(1, solicitud.getIdp());
//                    ps1.setString(2, "diagrama_tortuga");
//                    ps1.setString(3, solicitud.getNombre());
//                    ps1.setBytes(4, solicitud.getArchivo());
//                    ps1.executeUpdate();
//                    System.out.println("si se ejecuto el diagram t");
//                }
//
//                if (solicitud.getTipoArchivo().equals("FORMATO")) {
//                    PreparedStatement ps1 = conexion.prepareStatement("INSERT INTO formatos SET nombre = ?, contenido = ? WHERE idProceso = ?");
//                    ps1.setInt(1, solicitud.getIdp());
//                    ps1.setString(2, solicitud.getNombre());
//                    ps1.setBytes(3, solicitud.getArchivo());
//                    ps1.executeUpdate();
//                }
//
//                if (solicitud.getTipoArchivo().equals("OTROS INSTRUCTIVOS")) {
//                    PreparedStatement ps1 = conexion.prepareStatement("INSERT INTO documentos(id_procedimiento, tipo, nombre, contenido) VALUES(?, ?, ?, ?)");
//                    ps1.setInt(1, solicitud.getIdp());
//                    ps1.setString(2, "instructivo");
//                    ps1.setString(3, solicitud.getNombre());
//                    ps1.setBytes(4, solicitud.getArchivo());
//                    ps1.executeUpdate();
//                }
//                break;
//        }
//        insertarRegistro(solicitud, fechaFormateada);
//        eliminarSolicitud(solicitud.getCodigo());
//    }
//
//    private void insertarRegistro(SolicitudesM solicitud, String fecha) {
//        String sql = "INSERT INTO docregistros(idp, codigo, nombre, revAnterior, revNueva, encargado, accion) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement pstmtSelect = conexion.prepareStatement(sql)) {
//            pstmtSelect.setInt(1, solicitud.getIdp());
//            pstmtSelect.setString(2, solicitud.getCodigo());
//            pstmtSelect.setString(3, solicitud.getNombre());
//            pstmtSelect.setString(4, solicitud.getRevAnterior());
//            pstmtSelect.setString(5, solicitud.getRevNueva());
//            pstmtSelect.setString(6, solicitud.getEncargado());
//            pstmtSelect.setString(7, solicitud.getAccion());
//            pstmtSelect.setString(8, fecha);
//            pstmtSelect.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(ControlDocumentacionServicio.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
