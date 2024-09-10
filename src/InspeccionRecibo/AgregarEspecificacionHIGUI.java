package InspeccionRecibo;

import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.InspeccionReciboServicio;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AgregarEspecificacionHIGUI extends javax.swing.JFrame {

    // Usuario y Conexión a la base de datos
    private Usuarios usuario;
    private Conexion conexion;
    
    // Servicios y Utilidades
    private InspeccionReciboServicio irs = new InspeccionReciboServicio();

    public AgregarEspecificacionHIGUI() {
        inicializarVentanaYComponentes();
    }

    public AgregarEspecificacionHIGUI(Connection conexion, Usuarios usuario) {
        this.usuario = usuario;
        inicializarVentanaYComponentes();
    }

    @Override
    public Image getIconImage() { // Método para cambiar el icono en la barra del titulo
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("jc/img/jc.png")); 
        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblAgregarEspecificacion = new javax.swing.JLabel();
        lblNuevaEspecificacion = new javax.swing.JLabel();
        txtNuevaEspecificacion = new swing.TextField();
        btnCancelar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));
        lblJCIcono = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAgregarEspecificacion.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        lblAgregarEspecificacion.setForeground(new java.awt.Color(10, 110, 255));
        lblAgregarEspecificacion.setText("<html><center>AGREGAR<br>ESPECIFICACIÓN</center></html>");
        jPanel1.add(lblAgregarEspecificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, -1));

        lblNuevaEspecificacion.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblNuevaEspecificacion.setForeground(new java.awt.Color(10, 110, 225));
        lblNuevaEspecificacion.setText("NUEVA ESPECIFICACIÓN:");
        jPanel1.add(lblNuevaEspecificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));
        jPanel1.add(txtNuevaEspecificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 310, 40));

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 150, 50));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, 150, 50));

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 320));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        cerrarVentana();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String especificacion = txtNuevaEspecificacion.getText();
        irs.agregarEspecificacion(conexion, especificacion);
        JOptionPane.showMessageDialog(this, "Se añadio la nueva especificación correctamente");
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void inicializarVentanaYComponentes() {
        initComponents(); 
        this.setResizable(false); 
        this.setLocationRelativeTo(null); 
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        this.conexion = Conexion.getInstance(); 
    }

    private void cerrarVentana() {
        AgregarEspecificacionHIGUI.this.dispose();
    }

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
            java.util.logging.Logger.getLogger(AgregarEspecificacionHIGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AgregarEspecificacionHIGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAgregarEspecificacion;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblNuevaEspecificacion;
    private javax.swing.JTextField txtNuevaEspecificacion;
    // End of variables declaration//GEN-END:variables
}
