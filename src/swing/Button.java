package swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Button extends JButton {

    // Atributos
    private boolean over; // Indica si el ratón esta sobre el botón
    private Animator animator; // Se utiliza para animar el cambio de tamaño y opacidad del botón al ser presionado
    private int tamanioTarget; // Tamaño objetivo del efecto visual al presionar el botón
    private float tamanioAnimator; // Tamaño actual del efecto visual al presionar el botón.
    private Point puntoPresion; // Almacena las coordenadas del punto donde se presionó el botón
    private float opacidad; // Valor de opacidad para el efecto visual
    private Color colorEfecto = new Color(255, 255, 255); // Color del efecto visual al presionar el botón, inicializado como color blanco
    private Color colorEntrada; // Color de fondo cuando el ratón entra en el botón
    private Color colorSalida; // Color de fondo cuando el ratón sale del botón

    // Constructores
    public Button() {
        super(); // Se utiliza para llamar al constructor de la clase padre, en este caso, la clase JButton
        // Por defecto se definen con tonalidades azules
        colorEntrada = new Color(121, 190, 255);
        colorSalida = new Color(10, 110, 255);
        inicializarBoton(colorSalida);
    }

    public Button(Color cEntrada, Color cSalida) {
        super(); // Se utiliza para llamar al constructor de la clase padre, en este caso, la clase JButton
        colorEntrada = cEntrada;
        colorSalida = cSalida;
        inicializarBoton(colorSalida);
    }
    
    // Getters y Setters
    public Color getColorEfecto() {
        return colorEfecto;
    }

    public void setColorEfecto(Color colorEfecto) {
        this.colorEfecto = colorEfecto;
    }
    
    private void inicializarBoton(Color colorInicial) {
        setContentAreaFilled(false); // Se utiliza para controlar el área de contenido y definirla como no rellena
        setBorder(new EmptyBorder(5, 0, 5, 0)); // Establece el borde
        setBackground(colorInicial); // Establece el color de fondo
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Se define el tipo de cursor que aparecera cuando el ratón pase por el área del botón
        
        addMouseListener(new MouseAdapter() { // Se gestionan los eventos del ratón
            @Override
            public void mousePressed(MouseEvent me) { // Se inicia la animación del botón
                tamanioTarget = Math.max(getWidth(), getHeight()) * 2;
                tamanioAnimator = 0;
                puntoPresion = me.getPoint();
                opacidad = 0.5f;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }

            @Override
            public void mouseEntered(MouseEvent me) { // Cambia el fondo del botón cuando el ratón entra en el área del botón
                setBackground(colorEntrada);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent me) { // Cambia el fondo del botón cuando el ratón sale del área del botón
                setBackground(colorSalida);
                over = false;
            }
        });
        
        TimingTarget target = new TimingTargetAdapter() { // Controla la animación ajustando el tamaño y la opacidad del efecto visual al presionar el botón
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    opacidad = 1 - fraction;
                }
                tamanioAnimator = fraction * tamanioTarget;
                repaint();
            }
        };
        
        // Configuración de la animación
        animator = new Animator(700, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
    }

    @Override
    protected void paintComponent(Graphics grphcs) { // Se sobrecarga el método para personalizar la representación visual del botón
        int width = getWidth();
        int height = getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // Se crea una imagen bufferizada para dibujar el fondo del botón y el efecto visual
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, height, height);
        if (puntoPresion != null) {
            g2.setColor(colorEfecto);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, opacidad)); // Se ajusta la opacidad del efecto visual
            g2.fillOval((int) (puntoPresion.x - tamanioAnimator / 2), (int) (puntoPresion.y - tamanioAnimator / 2), (int) tamanioAnimator, (int) tamanioAnimator);
        }
        g2.dispose();
        grphcs.drawImage(img, 0, 0, null);
        
        // Dibuja la imagen bufferizada en el componente y luego se llama a super.paintComponent(grphcs) para realizar la representación estándar del botón.
        super.paintComponent(grphcs);
    }
}
