/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.sgc;

import componente.PanelCover;
import componente.PanelLoginAndRegister;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author JC
 */
public class Login extends javax.swing.JFrame {

    private final DecimalFormat decimalFormat = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US)); // Se formatean los numeros de acuerdo a la convención regional de EE.UU. con hasta 3 decimales a la derecha y no mostrara los ceros no significavitos de la izquierda
    private MigLayout layout; // La libreria MigLayout es de Diseño GUI para java
    private PanelCover cover; // El componente que muestra los saludos 
    private PanelLoginAndRegister loginAndRegister; // El componente que mostrará el inicio de sesión o registro de acuerdo a los botones
    private boolean isLogin = true; // Al ser true, primero se mostrara la vista para iniciar sesión
    // Constantes
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        init();
    }

    @Override
    public Image getIconImage() { // Método para obtener y cambiar el icono de la aplicación en la barra del titulo
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("jc/img/jc.png")); // Se obtiene la imagen que se quiere poner como icono de la barra 
        return retValue;
    }

    private void init() {
        layout = new MigLayout("fill, insets 0"); // Los parámetros hacen que los componentes cubran todo el espacio del contenedor y que no haya márgenes
        cover = new PanelCover(); // Se inicializa el componente que mostrara los saludos
        loginAndRegister = new PanelLoginAndRegister(); // Se inicializa el componente para iniciarSesion/Registrarse
        TimingTarget target = new TimingTargetAdapter() { // Control de las animaciones
            @Override
            public void timingEvent(float fraction) { // Interfaz para el desarrollo de animaciones, controla las animaciones en función del tiempo
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if (fraction <= 0.5f) { // Se calcula las dimensiones del componente que muestra los saludos
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
                }
                if (isLogin) { // Si esta en la interfaz de login o inicio de sesión...
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if (fraction >= 0.5f) {
                        cover.registerRight(fractionCover * 100);
                    } else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else { // Si esta en la vista de registro...
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.registerLeft(fraction * 100);
                    } else {
                        cover.loginLeft((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }
                // Se formatean los numeros decimales
                fractionCover = Double.valueOf(decimalFormat.format(fractionCover));
                fractionLogin = Double.valueOf(decimalFormat.format(fractionLogin));
                // Se actualizan las restricciones de diseño
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                layeredPane.revalidate(); // Se reflejan los cambios en el contenedor principal
            }

            @Override
            public void end() {
                isLogin = !isLogin; // Cuando la animación terminó, se invierte el valor de login
            }
        };
        Animator animator = new Animator(800, target); // El tiempo que durará la animación del componenten
        animator.setAcceleration(0.5f); // Valor de aceleración
        animator.setDeceleration(0.5f); // Valor de Desaceleración
        animator.setResolution(0);  // La animación se Ejecutara con la mayor precisión posible
        layeredPane.setLayout(layout); // Se define el layout del componente principal
        // Se configuran las restricciones de las animaciones

        layeredPane.add(cover, "width " + coverSize + "%, pos " + (isLogin ? "1al" : "0al") + " 0 n 100%");
        layeredPane.add(loginAndRegister, "width " + loginSize + "%, pos " + (isLogin ? "0al" : "1al") + " 0 n 100%"); //  1al as 100%
        loginAndRegister.showRegister(!isLogin); // Cuando no se muestre la vista de login, se mostrará la de registro
        cover.login(isLogin); // La vista inicial será la de inicio de Sesión
        cover.addEvent((ActionEvent ae) -> {
            if (!animator.isRunning()) {
                animator.start(); // Arranca la animación al dar click
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layeredPane = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());

        layeredPane.setBackground(new java.awt.Color(255, 255, 255));
        layeredPane.setOpaque(true);

        javax.swing.GroupLayout layeredPaneLayout = new javax.swing.GroupLayout(layeredPane);
        layeredPane.setLayout(layeredPaneLayout);
        layeredPaneLayout.setHorizontalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 933, Short.MAX_VALUE)
        );
        layeredPaneLayout.setVerticalGroup(
            layeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(layeredPane)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane layeredPane;
    // End of variables declaration//GEN-END:variables
}
