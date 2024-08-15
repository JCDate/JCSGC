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

    private boolean over;
    private Animator animator;
    private int tamanioTarget;
    private float tamanioAnimator;
    private Point puntoPresion;
    private float opacidad;
    private Color colorEfecto = new Color(255, 255, 255);
    private Color colorEntrada;
    private Color colorSalida;

    public Button() {
        super();
        colorEntrada = new Color(121, 190, 255);
        colorSalida = new Color(10, 110, 255);
        inicializarBoton(colorSalida);
    }

    public Button(Color cEntrada, Color cSalida) {
        super();
        colorEntrada = cEntrada;
        colorSalida = cSalida;
        inicializarBoton(colorSalida);
    }

    public Color getColorEfecto() {
        return colorEfecto;
    }

    public void setColorEfecto(Color colorEfecto) {
        this.colorEfecto = colorEfecto;
    }

    private void inicializarBoton(Color colorInicial) {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setBackground(colorInicial);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
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
            public void mouseEntered(MouseEvent me) {
                setBackground(colorEntrada);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(colorSalida);
                over = false;
            }
        });

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    opacidad = 1 - fraction;
                }
                tamanioAnimator = fraction * tamanioTarget;
                repaint();
            }
        };

        animator = new Animator(700, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, height, height);
        if (puntoPresion != null) {
            g2.setColor(colorEfecto);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, opacidad));
            g2.fillOval((int) (puntoPresion.x - tamanioAnimator / 2), (int) (puntoPresion.y - tamanioAnimator / 2), (int) tamanioAnimator, (int) tamanioAnimator);
        }
        g2.dispose();
        grphcs.drawImage(img, 0, 0, null);

        super.paintComponent(grphcs);
    }
}