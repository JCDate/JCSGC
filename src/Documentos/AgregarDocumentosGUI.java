package Documentos;

import Modelos.DocumentosM;
import Modelos.ProcedimientosM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.ControlDocumentacionServicio;
import Servicios.Utilidades;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AgregarDocumentosGUI extends javax.swing.JFrame {

    // Atributos
    private Usuarios usuario; // Usuario autenticado en la aplicación
    private String rutaArchivo; // Guarda la ruta del nuevo documento
    private Connection conexion; // Conexión a la Base de Datos
    private DocumentosM documento; // Maneja la información del documento 
    private ProcedimientosM procedimiento; // maneja la información del procedimiento actual
    private ControlDocumentacionServicio cds; // Servicio para manejar el control de documentos

    public AgregarDocumentosGUI() {
        initComponents();
    }

    public AgregarDocumentosGUI(Usuarios usuario, ProcedimientosM procedimiento) {
        this.usuario = usuario;
        this.procedimiento = procedimiento;
        this.documento = new DocumentosM();
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
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));
        lblJCIcono = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblRevision = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtRevision = new swing.TextField();
        txtNombreArchivo = new swing.TextField();
        cbxTipoArchivo = new swing.ComboBoxSuggestion();
        btnSeleccionarArchivo = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 140, 50));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 430, 140, 50));

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblTitulo.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(10, 110, 255));
        lblTitulo.setText("AGREGAR DOCUMENTOS");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, 10));

        lblRevision.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRevision.setForeground(new java.awt.Color(10, 110, 255));
        lblRevision.setText("REVISIÓN:");
        jPanel1.add(lblRevision, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, -1, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(10, 110, 255));
        jLabel3.setText("TIPO DE ARCHIVO:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(10, 110, 255));
        jLabel4.setText("ARCHIVO:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, -1, -1));
        jPanel1.add(txtRevision, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 280, -1));
        jPanel1.add(txtNombreArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 280, -1));

        cbxTipoArchivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DIAGRAMA DE FLUJO", "MANUAL", "INSTRUCTIVO" }));
        jPanel1.add(cbxTipoArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, 220, -1));

        btnSeleccionarArchivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSeleccionarArchivo.setForeground(new java.awt.Color(255, 255, 255));
        btnSeleccionarArchivo.setText("Seleccionar Archivo");
        btnSeleccionarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarArchivoActionPerformed(evt);
            }
        });
        jPanel1.add(btnSeleccionarArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 330, 170, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarArchivoActionPerformed
        File archivoSeleccionado = cds.seleccionarArchivo(this);
        if (archivoSeleccionado != null) {
            try {
                String nombreArchivo = archivoSeleccionado.getName(); // Obtener el nombre del archivo
                String tipoArchivo = cbxTipoArchivo.getSelectedItem().toString();
                Files.copy(archivoSeleccionado.toPath(), Paths.get("\\\\"+Utilidades.SERVIDOR+"\\archivos\\ControlDocumentos\\documentos\\" + archivoSeleccionado.getName()), StandardCopyOption.REPLACE_EXISTING); // Copiar el archivo al servidor
                txtNombreArchivo.setText(nombreArchivo);
                rutaArchivo = "\\\\" + Utilidades.SERVIDOR + "\\archivos\\ControlDocumentos\\" + tipoArchivo + "\\" + archivoSeleccionado.getName();
            } catch (IOException ex) {
                Utilidades.manejarExcepcion("ERROR al guardar el archivo: ", ex);
                Logger.getLogger(AgregarDocumentosGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnSeleccionarArchivoActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
        cds.abrirProcedimientosGUI(usuario, procedimiento);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        configurarDocumento();
        cds.actualizarInfoDocumentos(conexion, documento);
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "ARCHIVO ACTUALIZADO EXITOSAMENTE");
        cds.abrirDocumentacionGUI(usuario, documento.getIdProceso());
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void inicializarVentanaYComponentes() {
        try {
            configurarVentana();
            inicializarAtributos();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al Abrir AgregarDocumentosGUI: ", ex);
            Logger.getLogger(AgregarDocumentosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configurarVentana() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void inicializarAtributos() throws SQLException {
        this.conexion = Conexion.getInstance().conectar();
        this.cds = new ControlDocumentacionServicio();
    }

    private void configurarDocumento() {
        documento.setIdProcedimiento(procedimiento.getId());
        documento.setIdProceso(procedimiento.getIdp());
        documento.setRevision(txtRevision.getText());
        documento.setTipo(cbxTipoArchivo.getSelectedItem().toString());
        documento.setNombre(txtNombreArchivo.getText());
        documento.setRutaArchivo("archivos/ControlDocumentos/documentos/" + rutaArchivo);
    }

    private void cerrarVentana() {
        AgregarDocumentosGUI.this.dispose();
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
            java.util.logging.Logger.getLogger(AgregarDocumentosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AgregarDocumentosGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSeleccionarArchivo;
    private javax.swing.JComboBox<String> cbxTipoArchivo;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblRevision;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtNombreArchivo;
    private javax.swing.JTextField txtRevision;
    // End of variables declaration//GEN-END:variables
}
