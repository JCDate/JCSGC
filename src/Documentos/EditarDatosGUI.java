package Documentos;

import Modelos.ProcesosM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.ControlDocumentacionServicio;
import Servicios.Utilidades;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EditarDatosGUI extends javax.swing.JFrame {

    // Atributos
    private Usuarios usuario; // Usuario autenticado en la aplicación
    private ProcesosM proceso; // Objeto para manejar la documentacion por procesos
    private Connection conexion; // Conexión a la Base de Datos
    private ControlDocumentacionServicio cds; // Listas de información de control de documentación

    public EditarDatosGUI() {
        initComponents();
    }

    public EditarDatosGUI(Usuarios usuario, ProcesosM proceso) {
        this.usuario = usuario;
        this.proceso = proceso;
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

        txtNo = new swing.TextField();
        txtNombreProceso = new swing.TextField();
        txtRevision = new swing.TextField();
        txtEncargado = new swing.TextField();
        txtCodigo = new swing.TextField();
        jPanel1 = new javax.swing.JPanel();
        lblJCIcono = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblNo = new javax.swing.JLabel();
        lblNombreProceso = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblRevision = new javax.swing.JLabel();
        lblEncargado = new javax.swing.JLabel();
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(txtNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 370, 30));
        getContentPane().add(txtNombreProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 158, 240, 30));
        getContentPane().add(txtRevision, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 195, 330, 30));
        getContentPane().add(txtEncargado, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 275, 230, 30));
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 235, 350, 30));

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblTitulo.setFont(new java.awt.Font("Wide Latin", 1, 16)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(10, 110, 255));
        lblTitulo.setText("EDITAR DATOS DEL PROCESO");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, -1, -1));

        lblNo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNo.setForeground(new java.awt.Color(10, 110, 255));
        lblNo.setText("NO.:");
        jPanel1.add(lblNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, -1, -1));

        lblNombreProceso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombreProceso.setForeground(new java.awt.Color(10, 110, 255));
        lblNombreProceso.setText("NOMBRE DEL PROCESO:");
        jPanel1.add(lblNombreProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, -1, -1));

        lblCodigo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(10, 110, 255));
        lblCodigo.setText("CÓDIGO:");
        jPanel1.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, -1, -1));

        lblRevision.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRevision.setForeground(new java.awt.Color(10, 110, 255));
        lblRevision.setText("REVISIÓN:");
        jPanel1.add(lblRevision, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, -1, -1));

        lblEncargado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEncargado.setForeground(new java.awt.Color(10, 110, 255));
        lblEncargado.setText("DUEÑO(A) DE PROCESO:");
        jPanel1.add(lblEncargado, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, -1, -1));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 140, 50));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 360, 140, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        actualizarDatos();
    cds.editarProceso(conexion, proceso);
    cerrarVentana();
        JOptionPane.showMessageDialog(this, "ARCHIVO ACTUALIZADO EXITOSAMENTE");
        cds.abrirDocumentacionGUI(usuario, proceso.getId());
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
        cds.abrirDocumentacionGUI(usuario, proceso.getId());
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void inicializarVentanaYComponentes() {
        configurarVentana();
        inicializarConexion();
        inicializarServicios();
        inicializarInformacion();
    }

    private void configurarVentana() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void inicializarConexion() {
        try {
            this.conexion = Conexion.getInstance().conectar();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR de conexión: ", ex);
            Logger.getLogger(EditarDatosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void inicializarServicios() {
        this.cds = new ControlDocumentacionServicio();
    }

    private void inicializarInformacion() {
        txtNo.setText(proceso.getNo());
        txtNombreProceso.setText(proceso.getProceso());
        txtRevision.setText(proceso.getRevision());
        txtCodigo.setText(proceso.getCodigo());
        txtEncargado.setText(proceso.getEncargado());
    }

    private void actualizarDatos() {
        proceso.setNo(txtNo.getText());
        proceso.setRevision(txtRevision.getText());
        proceso.setCodigo(txtCodigo.getText());
        proceso.setEncargado(txtEncargado.getText());
        proceso.setProceso(txtNombreProceso.getText());
    }

    public void cerrarVentana() {
        EditarDatosGUI.this.dispose();
        Conexion.getInstance().desconectar(conexion);
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
            java.util.logging.Logger.getLogger(EditarDatosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new EditarDatosGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblEncargado;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblNo;
    private javax.swing.JLabel lblNombreProceso;
    private javax.swing.JLabel lblRevision;
    private javax.swing.JLabel lblTitulo;
    private swing.TextField txtCodigo;
    private swing.TextField txtEncargado;
    private swing.TextField txtNo;
    private swing.TextField txtNombreProceso;
    private swing.TextField txtRevision;
    // End of variables declaration//GEN-END:variables
}
