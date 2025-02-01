package APQP;

import Modelos.ApqpM;
import Modelos.DoctosApqpM;
import Modelos.Usuarios;
import Servicios.ApqpServicio;
import Servicios.Conexion;
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

public class AgregarDoctoApqpGUI extends javax.swing.JFrame {

    // Atributos
    private int operacion; // Define si se trata de una inserción o actualización
    private ApqpM actividad; // Instacia de la clase ApqpM para gestionar los documentos
    private Usuarios usuario; // Usuario autenticado en la aplicación
    private ApqpServicio apqps; // Servicio para manejar el control de documentos
    private Connection conexion; // Conexión a la Base de Datos
    private DoctosApqpM documento; // Instancia para gestionar los documentos de la actividad

    public AgregarDoctoApqpGUI() {
        initComponents();
    }

    public AgregarDoctoApqpGUI(Usuarios usuario, DoctosApqpM documento, int operacion) {
        this.usuario = usuario;
        this.documento = documento;
        this.operacion = operacion;
        inicializarVentanaYComponentes();
    }

    public AgregarDoctoApqpGUI(Usuarios usuario, ApqpM actividad, int operacion) {
        this.usuario = usuario;
        this.actividad = actividad;
        this.operacion = operacion;
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
        lblJCIcono = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblNuevoArchivo = new javax.swing.JLabel();
        btnNuevoArchivo = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        txtNombreArchivo = new swing.TextField();
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblTitulo.setFont(new java.awt.Font("Wide Latin", 1, 12)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(10, 110, 255));
        lblTitulo.setText("AGREGAR ANÁLISIS DE FACTIBILIDAD");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, -1));

        lblNuevoArchivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNuevoArchivo.setForeground(new java.awt.Color(10, 110, 255));
        lblNuevoArchivo.setText("NOMBRE DEL ARCHIVO:");
        jPanel1.add(lblNuevoArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, -1, -1));

        btnNuevoArchivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevoArchivo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoArchivo.setText("Seleccionar Archivo");
        btnNuevoArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoArchivoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 130, 170, 50));
        jPanel1.add(txtNombreArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 330, -1));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CANCELAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 140, 50));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 250, 140, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 310));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoArchivoActionPerformed
        File archivoSeleccionado = Utilidades.seleccionarArchivo(this);
        if (archivoSeleccionado != null) {
            try {
                String nombreArchivo = archivoSeleccionado.getName(); // Obtener el nombre del archivo
                Files.copy(archivoSeleccionado.toPath(), Paths.get("\\\\" + Utilidades.SERVIDOR + "\\archivos\\Apqp\\" + archivoSeleccionado.getName()), StandardCopyOption.REPLACE_EXISTING); // Copiar el archivo al servidor
                txtNombreArchivo.setText(nombreArchivo);

            } catch (IOException ex) {
                Utilidades.manejarExcepcion("ERROR al guardar el archivo: ", ex);
                Logger.getLogger(AgregarDoctoApqpGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnNuevoArchivoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        DoctosApqpM docto = (operacion == 1) ? new DoctosApqpM() : documento;

        docto.setNombreDocto(txtNombreArchivo.getText());
        docto.setRutaArchivo("archivos/Apqp/" + txtNombreArchivo.getText());

        if (operacion == 1) {
            docto.setIdp(actividad.getId());
            apqps.insertarDocumento(conexion, docto);
        } else {
            apqps.actualizarDocumento(conexion, documento);
        }

        notificarActualizacion();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
        abrirDocumentosGUI();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void inicializarVentanaYComponentes() {
        try {
            configurarVentana();
            if (operacion == 2) {
                lblTitulo.setText("MODIFICAR ARCHIVO");
            }
            inicializarAtributos();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al Abrir ApqpGUI: ", ex);
            Logger.getLogger(ApqpGUI.class.getName()).log(Level.SEVERE, null, ex);
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
        this.apqps = new ApqpServicio();
    }

    private void abrirDocumentosGUI() {
        if (actividad != null) {
            apqps.abrirDocumentosApqpGUI(usuario, actividad);
        } else {
            apqps.abrirDocumentosApqpGUI(usuario, documento);
        }
    }

    private void notificarActualizacion() {
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "ARCHIVO ACTUALIZADO EXITOSAMENTE");
        abrirDocumentosGUI();
    }

    private void cerrarVentana() {
        AgregarDoctoApqpGUI.this.dispose();
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
            java.util.logging.Logger.getLogger(AgregarDoctoApqpGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AgregarDoctoApqpGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevoArchivo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblNuevoArchivo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtNombreArchivo;
    // End of variables declaration//GEN-END:variables
}
