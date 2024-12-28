package Documentos;

import BotonesAccion.TableActionCellEditor;
import BotonesAccion.TableActionCellRender;
import BotonesAccion.TableActionEvent;
import Modelos.ProcedimientosM;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class ProcesosGUI extends javax.swing.JFrame {

    // Atributos
    private Usuarios usuario; // Usuario autenticado en la aplicación
    private ProcesosM proceso; // Objeto para manejar la documentacion por procesos
    private Connection conexion; // Conexión a la Base de Datos
    private DefaultTableModel modeloTabla; // Definición de la estructura de la tabla
    private ControlDocumentacionServicio cds; // Listas de información de control de documentación
    private List<ProcedimientosM> listaProcedimientos; // Lista de procedimientos

    public ProcesosGUI() {
        initComponents();
    }

    public ProcesosGUI(Usuarios usuario) {
        this.usuario = usuario;
        inicializarVentanaYComponentes();
    }

    public ProcesosGUI(Usuarios usuario, int idProceso) {
        try {
            this.usuario = usuario;
            this.cds = new ControlDocumentacionServicio();
            this.conexion = Conexion.getInstance().conectar();
            this.proceso = cds.recuperarProceso(conexion, idProceso);
            inicializarVentanaYComponentes();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al Abrir ProcesosGUI: ", ex);
            Logger.getLogger(ProcesosGUI.class.getName()).log(Level.SEVERE, null, ex);
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
        lblTitulo = new javax.swing.JLabel();
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProcedimientos = new javax.swing.JTable();
        btnDiagramaTortuga = new swing.Button(new Color(255, 199, 114),new Color(255, 170, 39));
        btnSolicitudCambio = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnNuevoProcesos = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnModificarDT = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setToolTipText("");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblTitulo.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(10, 110, 255));
        lblTitulo.setText("PROCESO: ");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, -1, -1));

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

        tblProcedimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO", "CÓDIGO", "REVISIÓN", "PROCESO", "DUEÑO(A) DE PROCESO", "OPERACIONES"
            }
        ));
        tblProcedimientos.setRowHeight(50);
        jScrollPane1.setViewportView(tblProcedimientos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1230, 350));

        btnDiagramaTortuga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/powerpoint.png"))); // NOI18N
        btnDiagramaTortuga.setToolTipText("");
        btnDiagramaTortuga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagramaTortugaActionPerformed(evt);
            }
        });
        jPanel1.add(btnDiagramaTortuga, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 10, 80, 80));

        btnSolicitudCambio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSolicitudCambio.setForeground(new java.awt.Color(255, 255, 255));
        btnSolicitudCambio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Ordenes.png"))); // NOI18N
        btnSolicitudCambio.setText("SOLICITUD DE CAMBIO");
        btnSolicitudCambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolicitudCambioActionPerformed(evt);
            }
        });
        jPanel1.add(btnSolicitudCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 500, 300, 50));

        btnNuevoProcesos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevoProcesos.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoProcesos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        btnNuevoProcesos.setText("NUEVO PROCEDIMIENTO");
        btnNuevoProcesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProcesosActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoProcesos, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 490, 250, 50));

        btnModificarDT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModificarDT.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarDT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        btnModificarDT.setText("ACTUALIZAR DIAGRAMA");
        btnModificarDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDTActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificarDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 100, 250, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
        cds.abrirControlDocumentosGUI(usuario);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnDiagramaTortugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagramaTortugaActionPerformed
        cds.abrirDocumento(proceso.getRutaArchivo());
    }//GEN-LAST:event_btnDiagramaTortugaActionPerformed

    private void btnSolicitudCambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitudCambioActionPerformed
        cerrarVentana();
        cds.abrirSolicitudGUI(usuario, proceso);
    }//GEN-LAST:event_btnSolicitudCambioActionPerformed

    private void btnNuevoProcesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProcesosActionPerformed
        cerrarVentana();
        cds.abrirModificarInfoGUI(usuario, proceso);
    }//GEN-LAST:event_btnNuevoProcesosActionPerformed

    private void btnModificarDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDTActionPerformed
        cerrarVentana();
        cds.abrirModificarArchivosGUI(usuario, proceso);
    }//GEN-LAST:event_btnModificarDTActionPerformed

    private void inicializarVentanaYComponentes() {
        configurarVentana();
        inicializarAtributos();
        configurarVisibilidadBoton();
        lblTitulo.setText("PROCESO: " + proceso.getProceso());
        inicializarTabla();
    }

    private void configurarVentana() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void inicializarAtributos() {
        this.modeloTabla = (DefaultTableModel) tblProcedimientos.getModel();
    }

    private void configurarVisibilidadBoton() {
        int uid = proceso == null ? usuario.getId() : proceso.getUid();
        if (usuario.getId() == uid || usuario.getId() == 8) {
            btnSolicitudCambio.setVisible(true);
        } else {
            btnSolicitudCambio.setVisible(false);
        }
    }

    private void inicializarTabla() {
        try {
            cargarDatosTabla();
            configurarAccionesTabla(true, true, false, true, false, false);
            mostrarDatosTabla();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al inicializar la tabla: ", ex);
            Logger.getLogger(FormatosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configurarAccionesTabla(boolean btnEditar, boolean btnEliminar, boolean btnVer, boolean btnRegistro, boolean btnAceptar, boolean btnRechazar) {
        TableActionEvent event = crearTableActionEvent();
        tblProcedimientos.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRender(btnEditar, btnEliminar, btnVer, btnRegistro, btnAceptar, btnRechazar));
        tblProcedimientos.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditor(event, btnEditar, btnEliminar, btnVer, btnRegistro, btnAceptar, btnRechazar));
    }

    private void cargarDatosTabla() throws SQLException {
        this.listaProcedimientos = cds.recuperarProcedimientos(conexion, proceso.getId());
    }

    public void mostrarDatosTabla() {
        limpiarTabla();
        llenarTabla();
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    private void llenarTabla() {
        if (listaProcedimientos != null && !listaProcedimientos.isEmpty()) {
            listaProcedimientos.forEach(procedimiento -> {
                Object[] fila = crearFila(procedimiento);
                modeloTabla.addRow(fila);
            });
        }
    }

    private Object[] crearFila(ProcedimientosM procedimiento) {
        Object[] fila = new Object[6];
        fila[0] = procedimiento.getNo();
        fila[1] = procedimiento.getCodigo();
        fila[2] = procedimiento.getRevision();
        fila[3] = procedimiento.getProcedimiento();
        fila[4] = procedimiento.getEncargado();
        fila[5] = "OPERACIONES";

        if (cds.esUsuarioAutorizado(usuario)) {
            configurarAccionesTabla(true, false, true, true, false, false);
        } else {
            configurarAccionesTabla(false, false, true, true, false, false); // Editar, Eliminar, Ver, Registros, Aceptar, Rechazar
        }
        return fila;
    }

    private TableActionEvent crearTableActionEvent() {
        return new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                cerrarVentana();
                cds.abrirModificarInfoGUI(usuario, listaProcedimientos.get(row));
            }

            @Override
            public void onDelete(int row) {
                // Nada ...
            }

            @Override
            public void onView(int row) {
                cerrarVentana();
                cds.abrirProcedimientosGUI(usuario, listaProcedimientos.get(row));
            }

            @Override
            public void onOpenRecords(int row) {
                cerrarVentana();
                cds.abrirRegistrosGUI(usuario, listaProcedimientos.get(row));
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

    public void cerrarVentana() {
        ProcesosGUI.this.dispose();
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
            java.util.logging.Logger.getLogger(ProcesosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ProcesosGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnDiagramaTortuga;
    private javax.swing.JButton btnModificarDT;
    private javax.swing.JButton btnNuevoProcesos;
    private javax.swing.JButton btnSolicitudCambio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblProcedimientos;
    // End of variables declaration//GEN-END:variables
}
