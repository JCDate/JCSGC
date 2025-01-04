package Servicios;

import BotonesAccion.TableActionCellEditor;
import BotonesAccion.TableActionCellRender;
import BotonesAccion.TableActionEvent;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
        File archivo = new File(ruta);
        return archivo.exists() && archivo.delete();
    }

    public static void abrirDocumento(String rutaArchivo) {
        try {
            if (rutaArchivo == null || rutaArchivo.isEmpty()) {
                Utilidades.manejarExcepcion("La ruta del archivo no es válida.", null);
                return;
            }

            String urlArchivo = "\\\\" + Utilidades.SERVIDOR + "\\" + rutaArchivo; // Ruta de red
            System.out.println(urlArchivo);
            File archivo = new File(urlArchivo);
            if (!archivo.exists()) {
                Utilidades.manejarExcepcion("El archivo no existe en la ruta especificada.", null);
                return;
            }
            abrirArchivoLocal(archivo);
        } catch (IOException ex) {
            manejarExcepcion("ERROR al abrir el archivo seleccionado: ", ex);
            Logger.getLogger(AceptacionProductoServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void abrirArchivoLocal(File archivo) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(archivo);
    }
}
