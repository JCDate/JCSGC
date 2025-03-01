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
import javax.swing.JOptionPane;
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
        lblProceso = new javax.swing.JLabel();
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProcedimientos = new javax.swing.JTable();
        btnDiagramaTortuga = new swing.Button(new Color(255, 199, 114),new Color(255, 170, 39));
        btnSolicitudCambio = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnNuevoProcesos = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnModificarDT = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        lblNo = new javax.swing.JLabel();
        lblEncargado = new javax.swing.JLabel();
        lblRevision = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblNoProceso = new javax.swing.JLabel();
        lblEncargadoProceso = new javax.swing.JLabel();
        lblRevisionProceso = new javax.swing.JLabel();
        lblCodigoProceso = new javax.swing.JLabel();
        lblNombreProceso = new javax.swing.JLabel();
        btnEditarDatos = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setToolTipText("");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblProceso.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblProceso.setForeground(new java.awt.Color(2, 75, 181));
        lblProceso.setText("PROCESO: ");
        jPanel1.add(lblProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

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
                "NO", "CÓDIGO", "REVISIÓN", "PROCESO", "DUEÑO(A) DE PROCEDIMIENTO", "OPERACIONES"
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

        lblNo.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblNo.setForeground(new java.awt.Color(2, 75, 181));
        lblNo.setText("NO.");
        jPanel1.add(lblNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        lblEncargado.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblEncargado.setForeground(new java.awt.Color(2, 75, 181));
        lblEncargado.setText("DUEÑO(A) DE PROCESO:");
        jPanel1.add(lblEncargado, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, -1, 20));

        lblRevision.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblRevision.setForeground(new java.awt.Color(2, 75, 181));
        lblRevision.setText("REVISIÓN:");
        jPanel1.add(lblRevision, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, -1, -1));

        lblCodigo.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(2, 75, 181));
        lblCodigo.setText("CÓDIGO:");
        jPanel1.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        lblNoProceso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoProceso.setForeground(new java.awt.Color(10, 110, 255));
        lblNoProceso.setText("1");
        jPanel1.add(lblNoProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 18, -1, -1));

        lblEncargadoProceso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEncargadoProceso.setForeground(new java.awt.Color(10, 110, 255));
        lblEncargadoProceso.setText("ENCARGADO(A)");
        jPanel1.add(lblEncargadoProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, -1, -1));

        lblRevisionProceso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRevisionProceso.setForeground(new java.awt.Color(10, 110, 255));
        lblRevisionProceso.setText("Rev. Proceso");
        jPanel1.add(lblRevisionProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 48, -1, -1));

        lblCodigoProceso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCodigoProceso.setForeground(new java.awt.Color(10, 110, 255));
        lblCodigoProceso.setText("MT-00");
        lblCodigoProceso.setToolTipText("");
        jPanel1.add(lblCodigoProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 78, -1, -1));

        lblNombreProceso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNombreProceso.setForeground(new java.awt.Color(10, 110, 255));
        lblNombreProceso.setText("NombreProceso");
        jPanel1.add(lblNombreProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 18, -1, -1));

        btnEditarDatos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEditarDatos.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarDatos.setText("EDITAR DATOS");
        btnEditarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarDatosActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditarDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 108, 160, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
        cds.abrirControlDocumentosGUI(usuario);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnDiagramaTortugaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagramaTortugaActionPerformed
        if (proceso.getRutaArchivo().isEmpty() || proceso.getRutaArchivo() == null) {
            JOptionPane.showMessageDialog(this, "NO SE ENCONTRÓ EL ARCHIVO BUSCADO");
        } else {
            Utilidades.abrirDocumento(proceso.getRutaArchivo());
        }
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

    private void btnEditarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarDatosActionPerformed
        cerrarVentana();
        cds.abrirEditarDatosGUI(usuario, proceso);
    }//GEN-LAST:event_btnEditarDatosActionPerformed

    private void inicializarVentanaYComponentes() {
        configurarVentana();
        inicializarAtributos();
        configurarVisibilidadBoton();
        cargarDatosProceso();
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

        if (cds.esUsuarioAutorizado(usuario)) {
            btnModificarDT.setVisible(true);
            btnEditarDatos.setVisible(true);
            btnNuevoProcesos.setVisible(true);
        } else {
            btnModificarDT.setVisible(false);
            btnEditarDatos.setVisible(false);
            btnNuevoProcesos.setVisible(false);
        }

    }

    private void cargarDatosProceso() {
        lblNoProceso.setText(proceso.getNo());
        lblNombreProceso.setText(proceso.getProceso());
        lblRevisionProceso.setText(proceso.getRevision());
        lblCodigoProceso.setText(proceso.getCodigo());
        lblEncargadoProceso.setText(proceso.getEncargado());
    }

    private void inicializarTabla() {
        try {
            cargarDatosTabla();
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
            configurarAccionesTabla(true, true, true, true, false, false);
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
                if (Utilidades.confirmarEliminacion()) {
                    cds.eliminarProcedimiento(conexion, listaProcedimientos.get(row));
                    cerrarVentana();
                    cds.abrirDocumentacionGUI(usuario, proceso.getId());
                }
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
    private javax.swing.JButton btnEditarDatos;
    private javax.swing.JButton btnModificarDT;
    private javax.swing.JButton btnNuevoProcesos;
    private javax.swing.JButton btnSolicitudCambio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoProceso;
    private javax.swing.JLabel lblEncargado;
    private javax.swing.JLabel lblEncargadoProceso;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblNo;
    private javax.swing.JLabel lblNoProceso;
    private javax.swing.JLabel lblNombreProceso;
    private javax.swing.JLabel lblProceso;
    private javax.swing.JLabel lblRevision;
    private javax.swing.JLabel lblRevisionProceso;
    private javax.swing.JTable tblProcedimientos;
    // End of variables declaration//GEN-END:variables
}
