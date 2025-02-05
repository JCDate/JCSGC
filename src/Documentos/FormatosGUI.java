package Documentos;

import BotonesAccion.TableActionCellEditor;
import BotonesAccion.TableActionCellRender;
import BotonesAccion.TableActionEvent;
import Modelos.FormatosM;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormatosGUI extends javax.swing.JFrame {

    // Atributos
    private Usuarios usuario; // Usuario autenticado en la aplicación
    private Connection conexion; // Conexión a la Base de Datos
    private ProcedimientosM procedimiento; // Objeto para manejar la documentacion por procedimiento
    private DefaultTableModel modeloTabla; // Definición de la estructura de la tabla
    private List<FormatosM> listaFormatos; // Listas para el control del formato
    private ControlDocumentacionServicio cds; // Listas de información de control de documentación

    public FormatosGUI() {
        initComponents();
    }

    public FormatosGUI(Usuarios usuario, ProcedimientosM procedimiento) {
        try {
            this.usuario = usuario;
            this.procedimiento = procedimiento;
            inicializarVentanaYComponentes();
        } catch (ClassNotFoundException ex) {
            Utilidades.manejarExcepcion("ERROR al abrir la ventana FormatosGUI:", ex);
            Logger.getLogger(FormatosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        lblFormato = new javax.swing.JLabel();
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFormatos = new javax.swing.JTable();
        btnAgregarFormato = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblFormato.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblFormato.setForeground(new java.awt.Color(10, 110, 255));
        lblFormato.setText("FORMATOS DEL PROCESO: ");
        jPanel1.add(lblFormato, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 600, 30));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 130, 50));

        tblFormatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOMBRE", "VER FORMATO"
            }
        ));
        tblFormatos.setRowHeight(50);
        jScrollPane1.setViewportView(tblFormatos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 660, -1));

        btnAgregarFormato.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarFormato.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarFormato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        btnAgregarFormato.setText("AGREGAR FORMATO");
        btnAgregarFormato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarFormatoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarFormato, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 490, 210, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
        cds.abrirProcedimientosGUI(usuario, procedimiento);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnAgregarFormatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarFormatoActionPerformed
        cerrarVentana();
        cds.abrirModificarArchivosGUI(usuario, procedimiento);
    }//GEN-LAST:event_btnAgregarFormatoActionPerformed

    public void cerrarVentana() {
        FormatosGUI.this.dispose();
        Conexion.getInstance().desconectar(conexion);
    }

    private void inicializarVentanaYComponentes() throws ClassNotFoundException {
        try {
            configurarVentana();
            inicializarAtributos();
            lblFormato.setText("<html>FORMATOS DEL PROCESO: <br>" + procedimiento.getProcedimiento() + "</html>");
            inicializarTabla();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al inicializar los componentes: ", ex);
            Logger.getLogger(FormatosGUI.class.getName()).log(Level.SEVERE, null, ex);
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
        this.listaFormatos = new ArrayList<>();
        this.cds = new ControlDocumentacionServicio();
        this.modeloTabla = (DefaultTableModel) tblFormatos.getModel();
    }

    private void inicializarTabla() {
        try {
            cargarDatosTabla();
            mostrarDatosTabla();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al inicializar laa tabla: ", ex);
            Logger.getLogger(FormatosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarDatosTabla() throws SQLException {
        this.listaFormatos = cds.recuperarFormatos(conexion, procedimiento.getId());
    }

    private void configurarAccionesTabla(boolean btnEditar, boolean btnEliminar, boolean btnVer, boolean btnRegistro, boolean btnAceptar, boolean btnRechazar) {
        TableActionEvent event = crearTableActionEvent();
        tblFormatos.getColumnModel().getColumn(1).setCellRenderer(new TableActionCellRender(btnEditar, btnEliminar, btnVer, btnRegistro, btnAceptar, btnRechazar));
        tblFormatos.getColumnModel().getColumn(1).setCellEditor(new TableActionCellEditor(event, btnEditar, btnEliminar, btnVer, btnRegistro, btnAceptar, btnRechazar));
    }

    private TableActionEvent crearTableActionEvent() {
        return new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                // Nada ...
            }

            @Override
            public void onDelete(int row) {
                if (Utilidades.confirmarEliminacion()) {
                    eliminarDocumento(listaFormatos.get(row));
                }
            }

            @Override
            public void onView(int row) {
                cerrarVentana();
                Utilidades.abrirDocumento(listaFormatos.get(row).getRutaArchivo());
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
        if (listaFormatos != null && !listaFormatos.isEmpty()) {
            listaFormatos.forEach(formato -> {
                Object[] fila = crearFila(formato);
                modeloTabla.addRow(fila);
            });
        }
    }

    private Object[] crearFila(FormatosM formato) {
        Object[] fila = new Object[2];
        fila[0] = formato.getNombre();
        fila[1] = "OPERACIONES";

        if (cds.esUsuarioAutorizado(usuario)) {
            configurarAccionesTabla(false, true, true, false, false, false);
        } else {
            configurarAccionesTabla(false, false, true, false, false, false);
        }
        return fila;
    }

    private void eliminarDocumento(FormatosM formato) {
        cds.eliminarFormato(conexion, formato);
        cds.eliminarArchivoFormato(formato);
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS CORRECTAMENTE");
        cds.abrirFormatosGUI(usuario, procedimiento);
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
            java.util.logging.Logger.getLogger(FormatosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FormatosGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarFormato;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFormato;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JTable tblFormatos;
    // End of variables declaration//GEN-END:variables
}
