package Modelos;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Iconos {
    
    public static final ImageIcon ICONO_JC = new ImageIcon(getImage("/jc/img/jc.png"));
    public static final ImageIcon ICONO_WORD = new ImageIcon(getImage("/jc/img/word.png"));
    public static final ImageIcon ICONO_EXCEL = new ImageIcon(getImage("/jc/img/excel.png"));
    public static final ImageIcon ICONO_POWER_POINT = new ImageIcon(getImage("/jc/img/powerpoint.png"));
    public static final ImageIcon ICONO_PDF = new ImageIcon(getImage("/jc/img/PDF.png"));
    public static final ImageIcon ICONO_VER = new ImageIcon(getImage("/jc/img/ver2.png"));
    public static final ImageIcon ICONO_ACEPTAR = new ImageIcon(getImage("/jc/img/ok2.png"));
    public static final ImageIcon ICONO_RECHAZAR = new ImageIcon(getImage("/jc/img/cancelar.png"));
    public static final ImageIcon ICONO_SOLICITUD = new ImageIcon(getImage("/jc/img/antecedentesfamilia.png"));
    public static final ImageIcon ICONO_REGISTROS = new ImageIcon(getImage("/jc/img/lista.png"));
    
    public static Image getImage(String ruta) {
        try {
            ImageIcon imageIcon = new ImageIcon(Iconos.class.getResource(ruta)); //Se crea un objeto de la clase ImageIcon y se obtiene la dirección de la ruta
            Image mainIcon = imageIcon.getImage(); // Se obtiene la imagen
            return mainIcon; // Se retorna 
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el stack trace de la excepción
        }
        return null;
    }

}
