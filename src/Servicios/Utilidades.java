package Servicios;

import BotonesAccion.TableActionCellEditor;
import BotonesAccion.TableActionCellRender;
import BotonesAccion.TableActionEvent;
import java.awt.Font;
import java.io.File;
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
}
