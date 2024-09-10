package InspeccionRecibo;

import Modelos.CalibreIRM;
import Modelos.InspeccionReciboM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.InspeccionReciboServicio;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AgregarCalibreHIGUI extends javax.swing.JFrame {

    // Usuario y Conexión a la base de datos
    private Usuarios usuario;
    private Conexion conexion;

    private InspeccionReciboM inspeccionRecibo;
    private CalibreIRM calibreIRM = new CalibreIRM();

    // Servicios y Utilidades
    private InspeccionReciboServicio irs = new InspeccionReciboServicio();

    public AgregarCalibreHIGUI() {
        try {
            inicializarVentanaYComponentes();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AgregarCalibreHIGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AgregarCalibreHIGUI(Usuarios usr) throws SQLException, ClassNotFoundException {
        inicializarVentanaYComponentes();
    }

    public AgregarCalibreHIGUI(Usuarios usuario, InspeccionReciboM inspeccionRecibo) throws SQLException, ClassNotFoundException {
        this.usuario = usuario;
        this.inspeccionRecibo = inspeccionRecibo;
        inicializarVentanaYComponentes();
    }

    @Override
    public Image getIconImage() {
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
        lblAgregarCalibre = new javax.swing.JLabel();
        lblCalibre = new javax.swing.JLabel();
        txtCalibre = new swing.TextField();
        txtEspecificacion = new swing.TextField();
        lblEspecificacion = new javax.swing.JLabel();
        lblJCIcono = new javax.swing.JLabel();
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));
        btnCancelar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        lblMedidas = new javax.swing.JLabel();
        cbxEspecificaciones = new swing.ComboBoxSuggestion();
        lblDescripcionMP = new javax.swing.JLabel();
        txtDescripcionMP = new swing.TextField();
        chkDescripcionMP = new swing.JCheckBoxCustom(new Color(255,0,0));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAgregarCalibre.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        lblAgregarCalibre.setForeground(new java.awt.Color(10, 110, 255));
        lblAgregarCalibre.setText("<html>Agregar <br>Calibre</html>");
        jPanel1.add(lblAgregarCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, -1, -1));

        lblCalibre.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblCalibre.setForeground(new java.awt.Color(10, 110, 255));
        lblCalibre.setText("CALIBRE:");
        jPanel1.add(lblCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));
        jPanel1.add(txtCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 450, -1));
        jPanel1.add(txtEspecificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 170, 380, -1));

        lblEspecificacion.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblEspecificacion.setForeground(new java.awt.Color(10, 110, 255));
        lblEspecificacion.setText("ESPECIFICACIÓN:");
        jPanel1.add(lblEspecificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 180, -1));

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, 140, 50));

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 150, 50));

        lblMedidas.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblMedidas.setForeground(new java.awt.Color(10, 110, 255));
        lblMedidas.setText("MEDIDAS/TOLERANCIA:");
        jPanel1.add(lblMedidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        jPanel1.add(cbxEspecificaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 410, -1));

        lblDescripcionMP.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblDescripcionMP.setForeground(new java.awt.Color(10, 110, 255));
        lblDescripcionMP.setText("DESCRIPCION:");
        jPanel1.add(lblDescripcionMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        txtDescripcionMP.setText("Lamina en Cinta. Cal. ");
        jPanel1.add(txtDescripcionMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 450, -1));

        chkDescripcionMP.setText("Lamina en Hoja");
        jPanel1.add(chkDescripcionMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try {
            cerrarVentana();
            irs.abrirEspecificacionesGUI(usuario, inspeccionRecibo);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AgregarCalibreHIGUI.class.getName()).log(Level.SEVERE, null, ex);
            irs.manejarExcepcion("ERROR al abrir EspecificacionesGUI", ex);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String calibres = txtCalibre.getText();
        String medidas = txtEspecificacion.getText();
        String especificacion = cbxEspecificaciones.getSelectedItem().toString();
        String descripcion = txtDescripcionMP.getText();

        if (!calibres.trim().equals("") || !medidas.trim().equals("")) {
            calibreIRM.setCalibre(calibres);
            calibreIRM.setMedidas(medidas);
            calibreIRM.setEspecificacion(especificacion);
            calibreIRM.setDescripcionMP(descripcion);
            try {
                irs.agregar(conexion, calibreIRM);
                irs.agregarDMP(conexion, calibreIRM);
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS.");
                cerrarVentana();
            } catch (SQLException ex) {
                irs.manejarExcepcion("Error al agregar la información del calibre", ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "HAY CAMPOS INCOMPLETOS.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void inicializarVentanaYComponentes() throws SQLException, ClassNotFoundException {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.conexion = Conexion.getInstance();

        chkDescripcionMP.addActionListener(e -> {
            if (chkDescripcionMP.isSelected()) {
                txtDescripcionMP.setText("Lamina en Hoja. Cal. " + txtCalibre.getText());
            } else {
                txtDescripcionMP.setText("Lamina en Cinta. Cal." + txtCalibre.getText());
            }
        });

        cbxEspecificaciones.addItem("");
        irs.obtenerEspecificaciones(conexion).forEach(cbxEspecificaciones::addItem);
    }

    private void cerrarVentana() {
        AgregarCalibreHIGUI.this.dispose();
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
            java.util.logging.Logger.getLogger(AgregarCalibreHIGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AgregarCalibreHIGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbxEspecificaciones;
    private javax.swing.JCheckBox chkDescripcionMP;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAgregarCalibre;
    private javax.swing.JLabel lblCalibre;
    private javax.swing.JLabel lblDescripcionMP;
    private javax.swing.JLabel lblEspecificacion;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblMedidas;
    private javax.swing.JTextField txtCalibre;
    private javax.swing.JTextField txtDescripcionMP;
    private javax.swing.JTextField txtEspecificacion;
    // End of variables declaration//GEN-END:variables
}
