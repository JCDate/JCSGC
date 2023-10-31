/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

/**
 *
 * @author JC
 */
public class ScrollBarCustom extends JScrollBar {

    public ScrollBarCustom() {
        setUI(new ModernScrollBarUI()); // Se establece la apariencia
        setPreferredSize(new Dimension(8, 8)); // Se establecen las dimensiones
        setForeground(new Color(180, 180, 180)); // Se establece el color del primer plano, el "tubo" de dezplazamiento
        setBackground(Color.WHITE); // Se establece el color de la barra
        setUnitIncrement(20); // Define cuánto se moverá el contenido cuando el usuario realice un pequeño desplazamiento 20 unidades
    }
}
