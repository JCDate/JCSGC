package Documentos;

import APQP.AgregarDoctoApqpGUI;
import Modelos.DocumentosM;
import Modelos.FormatosM;
import Modelos.ProcedimientosM;
import Modelos.ProcesosM;
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

public class ModificarArchivosGUI extends javax.swing.JFrame {

    // Atributos
    private Usuarios usuario; // Usuario autenticado en la aplicación
    private Connection conexion; // Conexión a la Base de Datos
    private ProcesosM proceso; // Manejar la informacion del archivo
    private String carpeta; // Define el nombre de la carpeta donde se guardaran los archivos
    private String tipoOperacion; // Define si la operación corresponde a un documento, proceso o procedimiento
    private DocumentosM documento; // Manejar la informacion del archivo
    private ProcedimientosM procedimiento; // Manejar la informacion del formato
    private ControlDocumentacionServicio cds; // Servicio para manejar el control de documentos

    public ModificarArchivosGUI() {
        initComponents();
    }

    public ModificarArchivosGUI(Usuarios usuario, DocumentosM documento, String carpeta) {
        this.usuario = usuario;
        this.documento = documento;
        System.out.println("documento: "+documento.getIdProcedimiento());
        System.out.println("documento proceso: "+documento.getIdProceso());
        this.carpeta = carpeta;
        this.tipoOperacion = "documento";
        inicializarVentanaYComponentes();
    }

    public ModificarArchivosGUI(Usuarios usuario, ProcesosM proceso, String carpeta) {
        this.usuario = usuario;
        this.proceso = proceso;
        this.carpeta = carpeta;
        this.tipoOperacion = "proceso";
        inicializarVentanaYComponentes();
    }

    public ModificarArchivosGUI(Usuarios usuario, ProcedimientosM procedimiento, String carpeta) {
        this.usuario = usuario;
        this.procedimiento = procedimiento;
        this.carpeta = carpeta;
        this.tipoOperacion = "procedimiento";
        System.out.println(procedimiento.getProcedimiento());
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
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));
        lblNuevoArchivo = new javax.swing.JLabel();
        txtNombreArchivo = new swing.TextField();
        btnNuevoArchivo = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(730, 345));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblTitulo.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(10, 110, 255));
        lblTitulo.setText("MODIFICAR ARCHIVOS");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 140, 50));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 270, 140, 50));

        lblNuevoArchivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNuevoArchivo.setForeground(new java.awt.Color(10, 110, 255));
        lblNuevoArchivo.setText("NOMBRE DEL ARCHIVO:");
        jPanel1.add(lblNuevoArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));
        jPanel1.add(txtNombreArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 290, -1));
        txtNombreArchivo.getAccessibleContext().setAccessibleDescription("");

        btnNuevoArchivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevoArchivo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoArchivo.setText("Seleccionar Archivo");
        btnNuevoArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoArchivoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, 170, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        int idProceso;
        if (procedimiento != null) {
            idProceso = procedimiento.getIdp();
        } else if (proceso != null) {
            idProceso = proceso.getId();
        } else {
            idProceso = documento.getIdProcedimiento();
        }

        cerrarVentana();
        cds.abrirDocumentacionGUI(usuario, idProceso);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnNuevoArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoArchivoActionPerformed
        File archivoSeleccionado = Utilidades.seleccionarArchivo(this);
        if (archivoSeleccionado != null) {
            try {
                String nombreArchivo = archivoSeleccionado.getName(); // Obtener el nombre del archivo
                Files.copy(archivoSeleccionado.toPath(), Paths.get("\\\\" + Utilidades.SERVIDOR + "\\archivos\\ControlDocumentos\\" + this.carpeta + "\\" + archivoSeleccionado.getName()), StandardCopyOption.REPLACE_EXISTING); // Copiar el archivo al servidor
                txtNombreArchivo.setText(nombreArchivo);
            } catch (IOException ex) {
                Utilidades.manejarExcepcion("ERROR al guardar el archivo: ", ex);
                Logger.getLogger(AgregarDoctoApqpGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnNuevoArchivoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        switch (tipoOperacion) {
            case "documento":
                actualizarDocumento();
                break;
            case "procedimiento":
                agregarFormato();
                break;
            default:
                actualizarDiagrama();
                break;
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void inicializarVentanaYComponentes() {
        configurarVentana();
        inicializarAtributos();
        definirTitulo();
    }

    private void configurarVentana() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void inicializarAtributos() {
        try {
            this.conexion = Conexion.getInstance().conectar();
            this.cds = new ControlDocumentacionServicio();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al inicializar los atributos: ", ex);
            Logger.getLogger(ModificarArchivosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void definirTitulo() {
        switch (tipoOperacion) {
            case "documento":
                lblTitulo.setText("ACTUALIZAR DOCUMENTO: ");
                break;
            case "procedimiento":
                lblTitulo.setText("AGREGAR FORMATO: ");
                break;
            case "proceso":
                lblTitulo.setText("ACTUALIZAR DIAGRAMA: ");
                break;
            default:
                lblTitulo.setText("ACTUALIZAR DOCUMENTOS: ");
        }
    }

    private void cerrarVentana() {
        ModificarArchivosGUI.this.dispose();
        Conexion.getInstance().desconectar(conexion);
    }

    private void actualizarDocumento() {
        documento.setNombre(txtNombreArchivo.getText());
        documento.setRutaArchivo("archivos/ControlDocumentos/documentos/" + txtNombreArchivo.getText());
        cds.actualizarDocumento(conexion, documento);
        notificarActualizacion();
        cds.abrirDocumentacionGUI(usuario, documento.getIdProceso());
    }

    private void agregarFormato() {
        FormatosM formato = new FormatosM();
        formato.setNombre(txtNombreArchivo.getText());
        formato.setIdP(procedimiento.getId());
        formato.setRutaArchivo("archivos/ControlDocumentos/Formatos/" + txtNombreArchivo.getText());
        cds.agregarFormatoNuevo(conexion, formato);
        notificarActualizacion();
        cds.abrirFormatosGUI(usuario, procedimiento);
    }

    private void actualizarDiagrama() {
        cds.eliminarDiagramaAnterior(proceso);
        proceso.setNombreDT(txtNombreArchivo.getText());
        proceso.setRutaArchivo("archivos/ControlDocumentos/DiagramasTortuga/" + txtNombreArchivo.getText());
        cds.actualizarDiagramaTortuga(conexion, proceso);
        notificarActualizacion();
        cds.abrirDocumentacionGUI(usuario, proceso.getId());
    }

    private void notificarActualizacion() {
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "ARCHIVO ACTUALIZADO EXITOSAMENTE");
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
            java.util.logging.Logger.getLogger(ModificarArchivosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ModificarArchivosGUI().setVisible(true);
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
