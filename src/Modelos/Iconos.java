package Modelos;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Iconos {

    public static final ImageIcon ICONO_JC = new ImageIcon(getImage("/jc/img/jc.png"));
    public static final ImageIcon ICONO_WORD = new ImageIcon(getImage("/jc/img/word.png"));
    public static final ImageIcon ICONO_EXCEL = new ImageIcon(getImage("/jc/img/excel.png"));
    public static final ImageIcon ICONO_EXCEL_2 = new ImageIcon(getImage("/jc/img/excelC.png"));
    public static final ImageIcon ICONO_POWER_POINT = new ImageIcon(getImage("/jc/img/powerpoint.png"));
    public static final ImageIcon ICONO_PDF = new ImageIcon(getImage("/jc/img/PDF.png"));
    public static final ImageIcon ICONO_VER = new ImageIcon(getImage("/jc/img/ver2.png"));
    public static final ImageIcon ICONO_ACEPTAR = new ImageIcon(getImage("/jc/img/ok2.png"));
    public static final ImageIcon ICONO_RECHAZAR = new ImageIcon(getImage("/jc/img/cancelar.png"));
    public static final ImageIcon ICONO_SOLICITUD = new ImageIcon(getImage("/jc/img/antecedentesfamilia.png"));
    public static final ImageIcon ICONO_REGISTROS = new ImageIcon(getImage("/jc/img/lista.png"));
    public static final ImageIcon ICONO_MODIFICAR = new ImageIcon(getImage("/jc/img/modificar.png"));
    public static final ImageIcon ICONO_IMAGEN = new ImageIcon(getImage("/jc/img/IMAGEN.png"));

    public static Image getImage(String ruta) {
        try {
            ImageIcon imageIcon = new ImageIcon(Iconos.class.getResource(ruta));
            Image mainIcon = imageIcon.getImage(); 
            return mainIcon;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar el icono: " + e);
        }
        return null;
    }
}
