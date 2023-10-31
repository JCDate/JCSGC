package swing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;

public class PasswordField extends JPasswordField {

    private Icon prefixIcon;
    private Icon suffixIcon;
    private String hint = "";

    public PasswordField() {
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Se crea un borde vacío de 10 pixeles en cada lado
        setBackground(new Color(0, 0, 0, 0)); // Se establece el fondo como transparente (Sin color de fondo)
        setForeground(Color.decode("#7A8C8D")); // Se establece el color del texto en el TextField, el color se decodifica a partir de un hexadecimal
        setFont(new java.awt.Font("sansserif", 0, 13)); // Se establece la fuente y el tamaño del texto dentro del TextField
        setSelectionColor(new Color(75, 175, 152)); // Se establece el color cuando se selecciona el TextField
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Icon getPrefixIcon() {
        return prefixIcon;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        initBorder();
    }

    public Icon getSuffixIcon() {
        return suffixIcon;
    }

    public void setSuffixIcon(Icon suffixIcon) {
        this.suffixIcon = suffixIcon;
        initBorder();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Configura el suavizado de bordes (antialiasing) para mejorar la apariencia de las formas dibujadas
        g2.setColor(new Color(230, 245, 241)); // Establece el color de relleno para las formas que se van a dibujar
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5); // Dibuja un rectángulo redondeado utilizando el color de relleno y las dimensiones del componente parámetros (coordenana x inicial, coordenada y inicial, ancho, alto, radio Horizontal, radio Vertical)
        paintIcon(g); // Método que se encarga de dibujar iconos en el componente
        super.paintComponent(g); // Garantiza que cualquier contenido anterior del componente se pinte correctamente y se realicen las tareas de dibujo estándar asociadas con el componente
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Borra cualquier contenido previo en el componente antes de la personalización.
        if (getText().length() == 0) {
            int h = getHeight();
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Configura el suavizado de bordes (antialiasing) para mejorar la apariencia de las formas dibujadas
            Insets ins = getInsets(); // Obtiene los márgenes internos del componente
            FontMetrics fm = g.getFontMetrics(); // Obtiene la fuente y la altura utilizada en el componente de texto
            g.setColor(new Color(200, 200, 200));
            g.drawString(hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }

    private void paintIcon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (prefixIcon != null) { // Verifica si prefixIcon (el icono de prefijo) no es nulo. Esto es una precaución para asegurarse de que el icono exista antes de intentar dibujarlo.
            Image prefix = ((ImageIcon) prefixIcon).getImage(); // Obtiene la imagen del icono de prefijo.
            int y = (getHeight() - prefixIcon.getIconHeight()) / 2; // Calcula la posición vertical y para el icono.
            g2.drawImage(prefix, 10, y, this); // Dibuja la imagen del icono de prefijo (prefix) en las coordenadas (10, y) utilizando el objeto Graphics2D.
        }
        if (suffixIcon != null) { // Verifica si suffixIcon (el icono de prefijo) no es nulo. Esto es una precaución para asegurarse de que el icono exista antes de intentar dibujarlo.
            Image suffix = ((ImageIcon) suffixIcon).getImage(); // Obtiene la imagen del icono de sufijo.
            int y = (getHeight() - suffixIcon.getIconHeight()) / 2; // Calcula la posición vertical y para el icono.
            g2.drawImage(suffix, getWidth() - suffixIcon.getIconWidth() - 10, y, this); // Dibuja la imagen del icono de prefijo (prefix) en las coordenadas (10, y) utilizando el objeto Graphics2D.
        }
    }

    private void initBorder() {
        int left = 15;
        int right = 15;
        if (prefixIcon != null) {
            left = prefixIcon.getIconWidth() + 15; //  prefix se ajusta en el margen izquierdo
        }
        if (suffixIcon != null) {
            right = suffixIcon.getIconWidth() + 15;  //  suffix se ajusta en el margen derecho
        }
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, left, 10, right)); // Se crea el borde vacío
    }
}
