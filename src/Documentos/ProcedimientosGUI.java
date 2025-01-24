package Documentos;

import BotonesAccion.TableActionCellEditor;
import BotonesAccion.TableActionCellRender;
import BotonesAccion.TableActionEvent;
import Modelos.DocumentosM;
import Modelos.ProcedimientosM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.ControlDocumentacionServicio;
import Servicios.Utilidades;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProcedimientosGUI extends javax.swing.JFrame {

    // Atributos
    private Usuarios usuario; // Usuario autenticado en la aplicación
    private Connection conexion; // Conexión a la Base de Datos
    private ProcedimientosM procedimiento; // Objeto para manejar la documentacion por procedimiento
    private DefaultTableModel modeloTabla; // Definición de la estructura de la tabla
    private ControlDocumentacionServicio cds; // Listas de información de control de documentación
    private List<DocumentosM> listaDocumentos; // Listas para el control del formato

    public ProcedimientosGUI() {
        initComponents();
    }

    public ProcedimientosGUI(Usuarios usuario, ProcedimientosM procedimiento) {
        this.usuario = usuario;
        this.procedimiento = procedimiento;
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

        txtBuscador = new swing.TextField();
        jPanel1 = new javax.swing.JPanel();
        lblJCIcono = new javax.swing.JLabel();
        lblProcedimiento = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocumentos = new javax.swing.JTable();
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        jLabel1 = new javax.swing.JLabel();
        btnFormatos = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnAgregarDocumentos = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscador.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/find.png"))); // NOI18N
        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyTyped(evt);
            }
        });
        getContentPane().add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 70, 260, 40));
        txtBuscador.getAccessibleContext().setAccessibleDescription("");
        txtBuscador.getAccessibleContext().setAccessibleParent(this);

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblProcedimiento.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblProcedimiento.setForeground(new java.awt.Color(10, 110, 255));
        lblProcedimiento.setText("Procedimiento:");
        jPanel1.add(lblProcedimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 880, 60));

        tblDocumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "TIPO DE DOCUMENTO", "FECHA DE ACTUALIZACIÓN", "NOMBRE", "ARCHIVO", "REGISTROS"
            }
        ));
        tblDocumentos.setRowHeight(50);
        jScrollPane1.setViewportView(tblDocumentos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 960, 300));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 130, 50));

        jLabel1.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(10, 110, 255));
        jLabel1.setText("Documentos:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, -1, -1));

        btnFormatos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFormatos.setForeground(new java.awt.Color(255, 255, 255));
        btnFormatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Factura.png"))); // NOI18N
        btnFormatos.setText("VER FORMATOS");
        btnFormatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFormatosActionPerformed(evt);
            }
        });
        jPanel1.add(btnFormatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 190, 50));

        btnAgregarDocumentos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarDocumentos.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarDocumentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        btnAgregarDocumentos.setText("<html><center>AGREGAR<br> DOCUMENTOS</center></html>");
        btnAgregarDocumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDocumentosActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarDocumentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 190, 50));
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 940, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
        cds.abrirDocumentacionGUI(usuario, procedimiento.getIdp());
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnFormatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFormatosActionPerformed
        cerrarVentana();
        cds.abrirFormatosGUI(usuario, procedimiento);
    }//GEN-LAST:event_btnFormatosActionPerformed

    private void btnAgregarDocumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDocumentosActionPerformed
        cerrarVentana();
        cds.abrirAgregarDocumentosGUI(usuario, procedimiento);
    }//GEN-LAST:event_btnAgregarDocumentosActionPerformed

    private void txtBuscadorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyTyped
 
    }//GEN-LAST:event_txtBuscadorKeyTyped

    private void inicializarVentanaYComponentes() {
        try {
            configurarVentana();
            lblProcedimiento.setText("<html>PROCEDIMIENTO: <br>" + procedimiento.getProcedimiento() + "</html>");
            inicializarAtributos();
            inicializarTabla();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al abrir ProcedimientosGUI: ", ex);
            Logger.getLogger(ProcedimientosGUI.class.getName()).log(Level.SEVERE, null, ex);
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
        this.modeloTabla = (DefaultTableModel) tblDocumentos.getModel();
    }

    private void inicializarTabla() {
        try {
            cargarDatosTabla();
            configurarAccionesTabla(false, false, true, false, false, false);
            mostrarDatosTabla();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al inicializar la tabla: ", ex);
            Logger.getLogger(FormatosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarDatosTabla() throws SQLException {
        this.listaDocumentos = cds.obtenerDocumentos(conexion, procedimiento.getId());
    }

    private void configurarAccionesTabla(boolean btnEditar, boolean btnEliminar, boolean btnVer, boolean btnRegistro, boolean btnAceptar, boolean btnRegistrar) {
        TableActionEvent event = crearTableActionEvent();
        tblDocumentos.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRender(btnEditar, btnEliminar, btnVer, btnRegistro, btnAceptar, btnRegistrar));
        tblDocumentos.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event, btnEditar, btnEliminar, btnVer, btnRegistro, btnAceptar, btnRegistrar));
    }

    private TableActionEvent crearTableActionEvent() {
        return new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                cerrarVentana();
                cds.abrirModificarArchivosGUI(usuario, listaDocumentos.get(row));
            }

            @Override
            public void onDelete(int row) {
                if (Utilidades.confirmarEliminacion()) {
                    eliminarDocumento(listaDocumentos.get(row));
                }
            }

            @Override
            public void onView(int row) {
                cds.abrirDocumento(listaDocumentos.get(row).getRutaArchivo());
            }

            @Override
            public void onOpenRecords(int row) {
                // Nada ...
            }

            @Override
            public void onAccept(int row) {
                // Nada ...
            }

            @Override
            public void onReject(int row) {
                // Nada ...
            }
        };
    }

    public void mostrarDatosTabla() {
        limpiarTabla();
        llenarTabla();
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    private void llenarTabla() {
        if (listaDocumentos != null && !listaDocumentos.isEmpty()) {
            listaDocumentos.forEach(documento -> {
                Object[] fila = crearFila(documento);
                modeloTabla.addRow(fila);
            });
        }
    }

    private Object[] crearFila(DocumentosM documento) {
        Object[] fila = new Object[4];
        fila[0] = documento.getTipo();
        fila[1] = documento.getFechaActualizacion();
        fila[2] = documento.getNombre();
        fila[3] = "OPERACIONES";

        if (cds.esUsuarioAutorizado(usuario)) {
            configurarAccionesTabla(true, true, true, false, false, false);
        } else {
            configurarAccionesTabla(true, false, true, false, false, false);
        }
        return fila;
    }

    public void cerrarVentana() {
        ProcedimientosGUI.this.dispose();
        Conexion.getInstance().desconectar(conexion);
    }

    private void eliminarDocumento(DocumentosM documento) {
        cds.eliminarDocumento(conexion, documento);
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS CORRECTAMENTE");
        cds.abrirDocumentacionGUI(usuario, procedimiento.getIdp());
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
            java.util.logging.Logger.getLogger(ProcedimientosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ProcedimientosGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarDocumentos;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnFormatos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblProcedimiento;
    private javax.swing.JTable tblDocumentos;
    private swing.TextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
