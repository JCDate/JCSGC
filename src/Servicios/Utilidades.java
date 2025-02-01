package Servicios;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import jnafilechooser.api.JnaFileChooser;
import swing.Button;

public class Utilidades {

    public static final String SERVIDOR = "192.168.1.75";

    public static Button crearBoton(Object object, ImageIcon icon, String texto) {
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

    public static boolean confirmarEliminacion() {
        int respuesta = JOptionPane.showConfirmDialog(null, "SE ELIMINARÁ LA INFORMACIÓN SELECCIONADA, ¿ESTÁS DE ACUERDO?", "ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public static boolean esCeldaValida(JTable tabla, int filaSeleccionada, int columnaSeleccionada) {
        return filaSeleccionada < tabla.getRowCount() && filaSeleccionada >= 0 && columnaSeleccionada < tabla.getColumnCount() && columnaSeleccionada >= 0;
    }

    public static void manejarExcepcion(String msg, Exception ex) {
        JOptionPane.showMessageDialog(null, msg + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean eliminarArchivo(String ruta) {
        if (ruta == null || ruta.isEmpty()) {
            System.err.println("La ruta proporcionada es nula o está vacía.");
            return false;
        }

        Path archivo = Paths.get(ruta);

        try {
            if (Files.exists(archivo)) {
                return Files.deleteIfExists(archivo);
            } else {
                System.err.println("El archivo no existe: " + ruta);
            }
        } catch (IOException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public static void abrirDocumento(String rutaArchivo) {
        try {
            if (rutaArchivo == null || rutaArchivo.isEmpty()) {
                Utilidades.manejarExcepcion("La ruta del archivo no es válida.", null);
                return;
            }

            // Normalizar la ruta reemplazando \ con /
            String rutaNormalizada = rutaArchivo.replace("\\", "/");

            // Codificar cada segmento de la ruta correctamente
            String urlArchivo = "http://" + Utilidades.SERVIDOR + "/"
                    + Stream.of(rutaNormalizada.split("/"))
                            .map(segmento -> {
                                try {
                                    return URLEncoder.encode(segmento, StandardCharsets.UTF_8.name());
                                } catch (Exception e) {
                                    throw new RuntimeException("Error al codificar segmento: " + segmento, e);
                                }
                            })
                            .collect(Collectors.joining("/"))
                            .replace("+", "%20"); // Reemplazar '+' por '%20' para espacios

            System.out.println("URL codificada: " + urlArchivo);

            // Descargar el archivo en un archivo temporal
            Path destino = Files.createTempFile("archivo", obtenerExtension(rutaArchivo));
            try (InputStream in = new URL(urlArchivo).openStream()) {
                Files.copy(in, destino, StandardCopyOption.REPLACE_EXISTING);
            }

            // Abrir el archivo descargado
            File archivoDescargado = destino.toFile();
            Desktop.getDesktop().open(archivoDescargado);

        } catch (Exception ex) {
            manejarExcepcion("ERROR al abrir el archivo seleccionado: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// Método auxiliar para obtener la extensión del archivo
    private static String obtenerExtension(String rutaArchivo) {
        int index = rutaArchivo.lastIndexOf('.');
        return (index > 0) ? rutaArchivo.substring(index) : "";
    }

    public static boolean camposCompletos(String... campos) {
        if (campos == null) {
            return false;
        }
        for (String campo : campos) {
            if (campo.isEmpty() || campo == null) {
                return false;
            }
        }
        return true;
    }

    public static Date formatearFecha(String fecha) {
        try {
            if (fecha == null) {
                return null;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.parse(fecha);
        } catch (ParseException ex) {
            Utilidades.manejarExcepcion("ERROR al formatear la fecha: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String formatearFecha(Date fecha) {
        if (fecha == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(fecha);
    }

    public static File seleccionarArchivo(Component parentComponent) {
        JnaFileChooser jfc = new JnaFileChooser();
        jfc.addFilter("pdf", "xlsx", "xls", "pdf", "PDF", "ppt", "pptx", "doc", "docx", "png", "jpg", "jpeg", "png");
        boolean action = jfc.showOpenDialog((Window) parentComponent);
        if (action) {
            return jfc.getSelectedFile();
        }

        return null;
    }

    private static String limpiarNombreArchivo(String nombreArchivo) {
        return nombreArchivo.replaceAll("[<>:\"/\\|?*]", "_"); // Reemplaza los caracteres no válidos en el sistema de archivos
    }
}
