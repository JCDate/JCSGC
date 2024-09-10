package Documentos;

import Modelos.DocumentosM;
import Modelos.FormatosM;
import Modelos.ProcedimientosM;
import Modelos.ProcesosM;
import Modelos.SolicitudesM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.ControlDocumentacionServicio;
import Servicios.InspeccionReciboServicio;
import Servicios.SQL;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SolicitudGUI extends javax.swing.JFrame {

    private Usuarios usr;
    private Conexion conexion;
    private ProcesosM proceso;
    private SolicitudesM solicitudes;
    private List<ProcedimientosM> listProcedimientos;
    private List<DocumentosM> listInstructivos;
    private List<FormatosM> listFormatos;
    int position, selectedIndex;
    private ControlDocumentacionServicio cds = new ControlDocumentacionServicio();
    private final InspeccionReciboServicio irs = new InspeccionReciboServicio();

    String rutaArchivo = "";

    public SolicitudGUI() {
        initComponents();
    }

    public SolicitudGUI(Usuarios usr, ProcesosM proceso) throws SQLException, ClassNotFoundException {
        this.usr = usr;
        this.proceso = proceso;
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

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        lblJCIcono = new javax.swing.JLabel();
        lblSolicitud = new javax.swing.JLabel();
        lblProceso = new javax.swing.JLabel();
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        btnSolicitud = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));
        lblAccion = new javax.swing.JLabel();
        cbxAccion = new swing.ComboBoxSuggestion();
        lblFormatoNuevo = new javax.swing.JLabel();
        txtNuevaRevision = new swing.TextField();
        btnNuevoArchivo = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        lblRevAnterior = new javax.swing.JLabel();
        lblRevNueva = new javax.swing.JLabel();
        lblEncargado = new javax.swing.JLabel();
        txtEncargado = new swing.TextField();
        txtRevAnterior = new swing.TextField();
        txtNombre = new swing.TextField();
        lblTipo = new javax.swing.JLabel();
        cbxTipoArchivo = new swing.ComboBoxSuggestion();
        lblProcedimiento = new javax.swing.JLabel();
        cbxProcedimientos = new swing.ComboBoxSuggestion();
        lblDocumentos = new javax.swing.JLabel();
        cbxDocumentos = new swing.ComboBoxSuggestion();

        jInternalFrame1.setVisible(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblSolicitud.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblSolicitud.setForeground(new java.awt.Color(10, 110, 255));
        lblSolicitud.setText("SOLICITUD DE CAMBIO");
        jPanel1.add(lblSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 340, 50));

        lblProceso.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblProceso.setForeground(new java.awt.Color(10, 110, 255));
        lblProceso.setText("Proceso:");
        jPanel1.add(lblProceso, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, -1, -1));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.setToolTipText("");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 130, 50));

        btnSolicitud.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSolicitud.setForeground(new java.awt.Color(255, 255, 255));
        btnSolicitud.setText("SOLICITAR CAMBIO");
        btnSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolicitudActionPerformed(evt);
            }
        });
        jPanel1.add(btnSolicitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 500, 270, 50));

        lblAccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAccion.setForeground(new java.awt.Color(10, 110, 255));
        lblAccion.setText("ACCIÓN:");
        jPanel1.add(lblAccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        cbxAccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ACTUALIZAR", "AGREGAR", "ELIMINAR" }));
        cbxAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxAccionActionPerformed(evt);
            }
        });
        jPanel1.add(cbxAccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 260, -1));

        lblFormatoNuevo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFormatoNuevo.setForeground(new java.awt.Color(10, 110, 255));
        lblFormatoNuevo.setText("NUEVO FORMATO:");
        jPanel1.add(lblFormatoNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, -1, -1));
        jPanel1.add(txtNuevaRevision, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 200, -1));

        btnNuevoArchivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNuevoArchivo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoArchivo.setText("Seleccionar Archivos");
        btnNuevoArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoArchivoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevoArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 400, 200, 50));

        lblRevAnterior.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRevAnterior.setForeground(new java.awt.Color(10, 110, 255));
        lblRevAnterior.setText("REVISIÓN ANTERIOR:");
        jPanel1.add(lblRevAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        lblRevNueva.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRevNueva.setForeground(new java.awt.Color(10, 110, 255));
        lblRevNueva.setText("NUEVA REVISIÓN:");
        jPanel1.add(lblRevNueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        lblEncargado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEncargado.setForeground(new java.awt.Color(10, 110, 255));
        lblEncargado.setText("DUEÑO DEL PROCESO: ");
        jPanel1.add(lblEncargado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        txtEncargado.setEnabled(false);
        jPanel1.add(txtEncargado, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 300, -1));
        jPanel1.add(txtRevAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 170, -1));
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 190, -1));

        lblTipo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(10, 110, 255));
        lblTipo.setText("TIPO DE ARCHIVO:");
        jPanel1.add(lblTipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        cbxTipoArchivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MANUAL", "DIAGRAMA DE FLUJO", "DIAGRAMA DE TORTUGA", "INSTRUCTIVO", "FORMATO" }));
        cbxTipoArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoArchivoActionPerformed(evt);
            }
        });
        jPanel1.add(cbxTipoArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 270, -1));

        lblProcedimiento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblProcedimiento.setForeground(new java.awt.Color(10, 110, 255));
        lblProcedimiento.setText("PROCEDIMIENTO:");
        jPanel1.add(lblProcedimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        cbxProcedimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxProcedimientosActionPerformed(evt);
            }
        });
        jPanel1.add(cbxProcedimientos, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 250, 350, -1));

        lblDocumentos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDocumentos.setForeground(new java.awt.Color(10, 110, 255));
        lblDocumentos.setText("DOCUMENTOS:");
        jPanel1.add(lblDocumentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 260, 120, -1));

        jPanel1.add(cbxDocumentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 370, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
        cds.abrirControlDocumentosGUI(usr);
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitudActionPerformed
        JOptionPane.showMessageDialog(this, "SE HA REALIZADO LA SOLICITUD DE CAMBIO");

        String codigo = listProcedimientos.get(selectedIndex).getCodigo();
        String nombreProceso = proceso.getProceso();
        String procedimiento = listProcedimientos.get(selectedIndex).getProcedimiento();
        String antiguaRev = txtRevAnterior.getText();
        String nuevaRev = txtNuevaRevision.getText();
        String encargado = txtEncargado.getText();
        String accion = cbxAccion.getSelectedItem().toString();
        String tipoArchivo = cbxTipoArchivo.getSelectedItem().toString();
        String nombre = txtNombre.getText();
        String nombreD = cbxDocumentos.isVisible() ? cbxDocumentos.getSelectedItem().toString() : "";

        solicitudes = new SolicitudesM(listProcedimientos.get(selectedIndex).getId(), codigo, nombreProceso, procedimiento, antiguaRev, nuevaRev, encargado, accion, tipoArchivo, nombreD, nombre, null);
        irs.cargarArchivo(rutaArchivo, solicitudes::setArchivo);

        cds.agregarSolicitud(solicitudes);
        cerrarVentana();
        cds.abrirControlDocumentosGUI(usr);
    }//GEN-LAST:event_btnSolicitudActionPerformed

    private void btnNuevoArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoArchivoActionPerformed
        seleccionarArchivo();

    }//GEN-LAST:event_btnNuevoArchivoActionPerformed

    private void cbxAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxAccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxAccionActionPerformed

    private void cbxProcedimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProcedimientosActionPerformed
        // Obtener el índice del ítem seleccionado.
        cbxDocumentos.removeAllItems();

        selectedIndex = cbxProcedimientos.getSelectedIndex();

        // Actualizar el campo de texto con la revisión correspondiente al procedimiento seleccionado.
        if (selectedIndex >= 0) { // Asegurarse de que hay un ítem seleccionado.
            txtRevAnterior.setText(listProcedimientos.get(selectedIndex).getRevision());
        }

        if (cbxTipoArchivo.getSelectedItem().toString().equals("INSTRUCTIVO")) {
            lblDocumentos.setVisible(true);
            cbxDocumentos.setVisible(true);
            try {
                actualizarDoctos("instructivos", selectedIndex);
            } catch (SQLException ex) {
                Logger.getLogger(SolicitudGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (cbxTipoArchivo.getSelectedItem().toString().equals("FORMATO")) {
            lblDocumentos.setVisible(true);
            cbxDocumentos.setVisible(true);
            try {
                actualizarDoctos("formato", selectedIndex);
            } catch (SQLException ex) {
                Logger.getLogger(SolicitudGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cbxProcedimientosActionPerformed

    private void cbxTipoArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoArchivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTipoArchivoActionPerformed

    public void cerrarVentana() {
        SolicitudGUI.this.dispose();
    }

    private void inicializarVentanaYComponentes() throws SQLException, ClassNotFoundException {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.conexion = Conexion.getInstance();
        lblProceso.setText("PROCESO: " + proceso.getProceso());

        listProcedimientos = cds.recuperarProcedimientos(conexion, proceso.getId());

        for (int i = 0; i < listProcedimientos.size(); i++) {
            cbxProcedimientos.addItem(listProcedimientos.get(i).getProcedimiento());
            position = i;
        }

        solicitarActualizacion();

        txtEncargado.setText(proceso.getEncargado());

        cbxAccion.addActionListener((ActionEvent ae) -> {

            String accion = cbxAccion.getSelectedItem().toString();
            switch (accion) {
                case "ACTUALIZAR":
                    solicitarActualizacion();
                    break;
                case "AGREGAR":
                    solicitarInsercion();
                    break;
                case "ELIMINAR":
                    System.out.println("3");
                    break;
            }
        });

        lblDocumentos.setVisible(false);
        cbxDocumentos.setVisible(false);

    }

    private void solicitarActualizacion() {
        cbxTipoArchivo.addActionListener((ActionEvent ae) -> {
            try {
                String archivo = cbxTipoArchivo.getSelectedItem().toString();
                switch (archivo) {
                    case "MANUAL":
                    case "DIAGRAMA DE FLUJO":
                        lblProcedimiento.setVisible(true);
                        cbxProcedimientos.setVisible(true);
                        lblDocumentos.setVisible(false);
                        cbxDocumentos.setVisible(false);
                        lblRevAnterior.setVisible(true);
                        txtNuevaRevision.setVisible(true);
                        lblRevNueva.setVisible(true);
                        txtRevAnterior.setVisible(true);

                        break;
                    case "DIAGRAMA DE TORTUGA":

                        lblDocumentos.setVisible(false);
                        cbxDocumentos.setVisible(false);
                        lblProcedimiento.setVisible(false);
                        cbxProcedimientos.setVisible(false);
                        lblRevAnterior.setVisible(false);
                        txtNuevaRevision.setVisible(false);
                        lblRevNueva.setVisible(false);
                        txtRevAnterior.setVisible(false);
                        break;
                    case "INSTRUCTIVO":
                        actualizarDoctos("instructivos", selectedIndex);

                        lblProcedimiento.setVisible(true);
                        cbxProcedimientos.setVisible(true);
                        lblRevAnterior.setVisible(false);
                        txtNuevaRevision.setVisible(false);
                        lblRevNueva.setVisible(false);
                        txtRevAnterior.setVisible(false);
                        break;
                    case "FORMATO":
                        actualizarDoctos("formato", selectedIndex);
                        lblProcedimiento.setVisible(true);
                        cbxProcedimientos.setVisible(true);
                        lblRevAnterior.setVisible(false);
                        txtNuevaRevision.setVisible(false);
                        lblRevNueva.setVisible(false);
                        txtRevAnterior.setVisible(false);
                        break;
                    default:
                        break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(SolicitudGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    public void seleccionarArchivo() {
        rutaArchivo = seleccionarArchivoCertificado(txtNombre, rutaArchivo);
    }

    public String seleccionarArchivoCertificado(JTextField textField, String rutaArchivo) {
        File archivoSeleccionado = this.irs.seleccionarArchivo(this); // Se selecciona el archivo
        if (archivoSeleccionado != null) {
            String nombreArchivo = archivoSeleccionado.getName(); // Se obtiene el nombre
            rutaArchivo = archivoSeleccionado.getAbsolutePath(); // Actualiza la ruta absoluta
            textField.setText(nombreArchivo);
        }
        return rutaArchivo;
    }

    public String seleccionarArchivoP(JTextField textField, String rutaArchivo) {
        File archivoSeleccionado = this.irs.seleccionarArchivo(this); // Se selecciona el archivo
        if (archivoSeleccionado != null) {
            String nombreArchivo = archivoSeleccionado.getName(); // Se obtiene el nombre
            rutaArchivo = archivoSeleccionado.getAbsolutePath(); // Actualiza la ruta absoluta
            textField.setText(nombreArchivo);
        }
        return rutaArchivo;
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
            java.util.logging.Logger.getLogger(SolicitudGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SolicitudGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnNuevoArchivo;
    private javax.swing.JButton btnSolicitud;
    private javax.swing.JComboBox<String> cbxAccion;
    private javax.swing.JComboBox<String> cbxDocumentos;
    private javax.swing.JComboBox<String> cbxProcedimientos;
    private javax.swing.JComboBox<String> cbxTipoArchivo;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAccion;
    private javax.swing.JLabel lblDocumentos;
    private javax.swing.JLabel lblEncargado;
    private javax.swing.JLabel lblFormatoNuevo;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblProcedimiento;
    private javax.swing.JLabel lblProceso;
    private javax.swing.JLabel lblRevAnterior;
    private javax.swing.JLabel lblRevNueva;
    private javax.swing.JLabel lblSolicitud;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JTextField txtEncargado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNuevaRevision;
    private javax.swing.JTextField txtRevAnterior;
    // End of variables declaration//GEN-END:variables

    private void actualizarDoctos(String docto, int index) throws SQLException {
        lblDocumentos.setVisible(true);
        cbxDocumentos.setVisible(true);
        boolean hayInstructivos = false;
        boolean hayFormatos = true;
        if (docto.equals("instructivos")) {
            cbxDocumentos.removeAllItems();
            listInstructivos = cds.recuperarDocumentos(conexion, listProcedimientos.get(index).getId());

            for (int i = 0; i < listInstructivos.size(); i++) {
                if (listInstructivos.get(i).getTipo().equalsIgnoreCase("instructivo")) {
                    cbxDocumentos.addItem(listInstructivos.get(i).getNombre());
                    hayInstructivos = true;
                }
            }
            visualizarCbxDocumentos(hayInstructivos);

        } else {
            cbxDocumentos.removeAllItems();

            listFormatos = cds.recuperarFormatos(conexion, listProcedimientos.get(index).getId());

            for (int i = 0; i < listFormatos.size(); i++) {

                cbxDocumentos.addItem(listFormatos.get(i).getNombre());
                hayFormatos = true;
            }

            visualizarCbxDocumentos(hayFormatos);
        }
    }

    public void visualizarCbxDocumentos(boolean documentos) {
        if (!documentos) {
            lblDocumentos.setVisible(false);
            cbxDocumentos.setVisible(false);
        } else {
            lblDocumentos.setVisible(true);
            cbxDocumentos.setVisible(true);
        }
    }

    private void solicitarInsercion() {
        txtRevAnterior.setVisible(false);
        lblRevAnterior.setVisible(false);
        lblRevNueva.setText("REVISIÓN: ");
    }
}
