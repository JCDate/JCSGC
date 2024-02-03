package AceptacionProducto;

import Modelos.AceptacionPc1;
import Modelos.AceptacionPc2;
import Modelos.AceptacionPc3;
import Modelos.AceptacionProducto;
import Modelos.Usuarios;
import Servicios.AceptacionProductoServicio;
import Servicios.Conexion;
import Servicios.ExcelEditor;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author JC
 */
public class RetencionDimensional extends javax.swing.JFrame {

    // Atributos
    private Usuarios usr; // La instancia del usuario actual del sistema
    private Connection conexion; // Conexión a la BD

    private AceptacionProducto ap = new AceptacionProducto(); // Se crea la instancia de la clase Aceptación Producto
    private AceptacionPc1 apc1, apc1Auxiliar; // Se crean las instancias de la clase AceptacionPc1
    private AceptacionPc2 apc2; // Se crea la instancia de la clase AceptacionPc2
    private AceptacionPc3 apc3, nuevosDatos; // Se crean la instancias de la clase AceptacionPc3
    private ExcelEditor xls = new ExcelEditor(); // Se crea la instancia de la clase ExcelEditor

    private AceptacionProductoServicio aps = new AceptacionProductoServicio(); // Se crea la instancia de la clase AceptacionProductoServicio

    private List<AceptacionPc1> ap1m; // Se crea una lista de objetos de la clase AceptacionPc1
    private List<AceptacionPc2> ap2m; // Se crea una lista de objetos de la clase AceptacionPc2
    private List<AceptacionPc3> ap3m; // Se crea una lista de objetos de la clase AceptacionPc3
    List<AceptacionPc3> listNuevosDatos = new ArrayList<>(); // Se crea una lista auxiliar de objetos de la clase AceptacionPc3 para guardar la información nueva

    // Constantes para los índicar los nombres de las columnas
    private static final int COLUMN_NO_FECHA = 0;
    private static final int COLUMN_NO_OP = 1;
    private static final int COLUMN_VARIABLE = 2;
    private static final int COLUMN_ESPECIFICACION = 3;
    private static final int COLUMN_VALOR = 4;

    private static final String MSG_ERROR_RD = "Surgió un error al inicializar los componentes de RETENCIÓN DIMENSIONAL"; // Constante para mostrar el mensaje de las excepciones

    private DefaultTableModel modeloTabla; // Define el modelo de la tabla

    /**
     * Creates new form RetencionDimensional
     */
    public RetencionDimensional() {
        inicializarVentanaYComponentes(); // Inicialización de Componentes
    }

    public RetencionDimensional(Usuarios usr) throws SQLException, ClassNotFoundException {
        // Definición de los atributos de la clase
        this.usr = usr;
        this.ap1m = new ArrayList<>();
        this.ap2m = new ArrayList<>();
        inicializarVentanaYComponentes(); // Inicialización de Componentes
    }

    public RetencionDimensional(Usuarios usr, AceptacionPc1 apc1) throws SQLException, ClassNotFoundException {
        // Definición de los atributos de la clase
        this.usr = usr;
        this.apc1 = apc1;

        inicializarVentanaYComponentes(); // Inicialización de Componentes
    }

    public RetencionDimensional(Usuarios usr, AceptacionPc1 apc1, AceptacionPc2 apc2) throws SQLException, ClassNotFoundException {
        // Definición de los atributos de la clase
        this.usr = usr;
        this.apc1 = apc1;
        this.apc2 = apc2;

        inicializarVentanaYComponentes(); // Inicialización de Componentes

        // Se muestra la información del componente en los campos de la ventana principal
        cbxComponente.setSelectedItem(apc1.getComponente());
        txtNoParte.setText(apc1.getNoParte());
        txtNoTroquel.setText(apc1.getNoTroquel());
        txtNoOperaciones.setText(apc1.getNoOps());

        txtNoOrden.setText(apc2.getNoOrden());
        txtInspector.setText(apc2.getInspector());
        txtTamLote.setText(apc2.getTamLote());
        txtTamMta.setText(apc2.getTamMta());
        txtTurno.setText(apc2.getTurno());
        txtDisp.setText(apc2.getDisp());
    }

    @Override
    public Image getIconImage() { // Método para obtener y cambiar el icono de la aplicación en la barra del titulo
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("jc/img/jc.png")); // Se obtiene la imagen que se quiere poner como icono de la barra 
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
        lblRetencionDimensional = new javax.swing.JLabel();
        btnPlanControl = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRD = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lblNoOrden = new javax.swing.JLabel();
        lblTamLote = new javax.swing.JLabel();
        lblTamMuestra = new javax.swing.JLabel();
        txtNoOrden = new swing.TextField();
        txtTamLote = new swing.TextField();
        txtTamMta = new swing.TextField();
        txtInspector = new swing.TextField();
        txtTurno = new swing.TextField();
        lblTurno = new javax.swing.JLabel();
        lblDisp = new javax.swing.JLabel();
        txtDisp = new swing.TextField();
        lblInsp = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblComponente = new javax.swing.JLabel();
        cbxComponente = new swing.ComboBoxSuggestion();
        lblNoParte = new javax.swing.JLabel();
        txtNoParte = new swing.TextField();
        lblNoTroquel = new javax.swing.JLabel();
        txtNoTroquel = new swing.TextField();
        lblNoOperaciones = new javax.swing.JLabel();
        txtNoOperaciones = new swing.TextField();
        btnRegresar = new javax.swing.JButton();
        pnlPC = new javax.swing.JPanel();
        lblVariable = new javax.swing.JLabel();
        lblNoOp = new javax.swing.JLabel();
        lblValor = new javax.swing.JLabel();
        txtFecha = new swing.TextField();
        cbxVariable = new swing.ComboBoxSuggestion();
        txtValor = new swing.TextField();
        btnAceptar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        lblEspecificacion = new javax.swing.JLabel();
        cbxEspecificacionPLG = new swing.ComboBoxSuggestion();
        txtEspecificacionPLG = new swing.TextField();
        lblProcesoCritico = new javax.swing.JLabel();
        cbxProcesoCritico = new swing.JCheckBoxCustom(new Color(255,0,0));
        btnAgregarVariable = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        lblFecha = new javax.swing.JLabel();
        txtNoOp = new swing.TextField();
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));
        btnEliminar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        btnActualizar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnModificar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblRetencionDimensional.setFont(new java.awt.Font("Wide Latin", 1, 18)); // NOI18N
        lblRetencionDimensional.setForeground(new java.awt.Color(10, 110, 255));
        lblRetencionDimensional.setText("RETENCIÓN DIMENSIONAL");
        jPanel1.add(lblRetencionDimensional, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, -1));

        btnPlanControl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPlanControl.setForeground(new java.awt.Color(255, 255, 255));
        btnPlanControl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/PDF.png"))); // NOI18N
        btnPlanControl.setText("PLAN DE CONTROL");
        btnPlanControl.setActionCommand("<html><center>Ver</center>Plan de Control</html>");
        btnPlanControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlanControlActionPerformed(evt);
            }
        });
        jPanel1.add(btnPlanControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 620, 200, 40));

        tblRD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblRD);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 770, 490));

        jPanel3.setBackground(new java.awt.Color(32, 163, 211));
        jPanel3.setForeground(new java.awt.Color(95, 158, 180));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNoOrden.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoOrden.setForeground(new java.awt.Color(255, 255, 255));
        lblNoOrden.setText("NO. ORDEN:");
        jPanel3.add(lblNoOrden, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        lblTamLote.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTamLote.setForeground(new java.awt.Color(255, 255, 255));
        lblTamLote.setText("TAM. LOTE:");
        jPanel3.add(lblTamLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lblTamMuestra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTamMuestra.setForeground(new java.awt.Color(255, 255, 255));
        lblTamMuestra.setText("TAM. MTA:");
        jPanel3.add(lblTamMuestra, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));
        jPanel3.add(txtNoOrden, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 90, -1));

        txtTamLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTamLoteActionPerformed(evt);
            }
        });
        jPanel3.add(txtTamLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 90, -1));
        jPanel3.add(txtTamMta, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 90, -1));

        txtInspector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInspectorActionPerformed(evt);
            }
        });
        jPanel3.add(txtInspector, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 80, -1));
        jPanel3.add(txtTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 80, -1));

        lblTurno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTurno.setForeground(new java.awt.Color(255, 255, 255));
        lblTurno.setText("TURNO:");
        jPanel3.add(lblTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, -1, -1));

        lblDisp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDisp.setForeground(new java.awt.Color(255, 255, 255));
        lblDisp.setText("DISP:");
        jPanel3.add(lblDisp, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));
        jPanel3.add(txtDisp, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 80, -1));

        lblInsp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblInsp.setForeground(new java.awt.Color(255, 255, 255));
        lblInsp.setText("INSP:");
        jPanel3.add(lblInsp, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 370, 140));

        jPanel4.setBackground(new java.awt.Color(32, 163, 211));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblComponente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblComponente.setForeground(new java.awt.Color(255, 255, 255));
        lblComponente.setText("COMPONENTE:");
        jPanel4.add(lblComponente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jPanel4.add(cbxComponente, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 140, -1));

        lblNoParte.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoParte.setForeground(new java.awt.Color(255, 255, 255));
        lblNoParte.setText("NO. PARTE:");
        jPanel4.add(lblNoParte, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, -1, 20));
        jPanel4.add(txtNoParte, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, 90, -1));

        lblNoTroquel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoTroquel.setForeground(new java.awt.Color(255, 255, 255));
        lblNoTroquel.setText("NO. TROQUEL:");
        jPanel4.add(lblNoTroquel, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, -1, -1));
        jPanel4.add(txtNoTroquel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 80, -1));

        lblNoOperaciones.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoOperaciones.setForeground(new java.awt.Color(255, 255, 255));
        lblNoOperaciones.setText("NO. OPERACIONES:");
        jPanel4.add(lblNoOperaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, 150, -1));
        jPanel4.add(txtNoOperaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, 50, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 1000, 70));

        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 150, 50));

        pnlPC.setBackground(new java.awt.Color(32, 163, 211));
        pnlPC.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVariable.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblVariable.setForeground(new java.awt.Color(255, 255, 255));
        lblVariable.setText("VARIABLE:");
        pnlPC.add(lblVariable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        lblNoOp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoOp.setForeground(new java.awt.Color(255, 255, 255));
        lblNoOp.setText("NO. OP:");
        pnlPC.add(lblNoOp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        lblValor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblValor.setForeground(new java.awt.Color(255, 255, 255));
        lblValor.setText("VALOR/RANGO:");
        pnlPC.add(lblValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));
        pnlPC.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 280, -1));

        pnlPC.add(cbxVariable, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 260, -1));
        pnlPC.add(txtValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 230, -1));

        btnAceptar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("ACEPTAR");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        pnlPC.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 200, -1));

        lblEspecificacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEspecificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblEspecificacion.setText("ESPECIFICACIÓN PLG:");
        pnlPC.add(lblEspecificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        pnlPC.add(cbxEspecificacionPLG, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 180, 30));
        pnlPC.add(txtEspecificacionPLG, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 180, -1));

        lblProcesoCritico.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblProcesoCritico.setForeground(new java.awt.Color(255, 255, 255));
        lblProcesoCritico.setText("PROCESO CRÍTICO: ");
        pnlPC.add(lblProcesoCritico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));
        pnlPC.add(cbxProcesoCritico, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 30, 30));

        btnAgregarVariable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAgregarVariable.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarVariable.setText("AGREGAR VARIABLE");
        btnAgregarVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarVariableActionPerformed(evt);
            }
        });
        pnlPC.add(btnAgregarVariable, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 160, -1));

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("FECHA:");
        pnlPC.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));
        pnlPC.add(txtNoOp, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 280, -1));

        jPanel1.add(pnlPC, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 370, 340));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 620, 140, 30));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 610, 80, 40));

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/actualizar.png"))); // NOI18N
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 610, 80, 40));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        btnModificar.setToolTipText("");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 610, 80, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtInspectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInspectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInspectorActionPerformed

    private void txtTamLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTamLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTamLoteActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        cerrarVentana(); // Se liberan los recursos de la interfaz
        aps.abrirAceptacionProductoGUI2(usr); // Se abre la Ventana de Aceptación Producto 2 (donde se captura la información) de la hoja 1 del documento de Retención Dimensional 
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnPlanControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlanControlActionPerformed
        String componenteCodificado = ""; // Codifica el valor del parámetro para que sea seguro en una URL
        Object componenteSeleccionado = cbxComponente.getSelectedItem(); // Se obtiene el componente
        if (componenteSeleccionado != null) { // Si el componente no es null ...
            String componentePc = componenteSeleccionado.toString(); // Se guarda el componenten en esta variable
            try {
                componenteCodificado = URLEncoder.encode(componentePc, "UTF-8"); // Codifica el componente para se agregado en la url
            } catch (UnsupportedEncodingException ex) {
                aps.manejarExcepcion("Surgio un error al consultar el PLAN DE CONTROL", ex); // Se muestra el mensaje de la excepción al usuario
            }
        }

        // Construye la URL con el valor del parámetro de consulta
        String phpFilePath = "http://192.168.1.75/produccion/php/PlanDeControlCuadro.php?componente=" + componenteCodificado;

        try {
            aps.openUrlInBrowser(phpFilePath); // Se abre el navegador con el plan de control
        } catch (IOException | URISyntaxException ex) {
            aps.manejarExcepcion("Error al abrir la URL en el navegador", ex); // Se muestra el mensaje de la excepción al usuario
        }
    }//GEN-LAST:event_btnPlanControlActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        Set<String> valoresPermitidos = new HashSet<>(Arrays.asList("1", "2", "3", "4", "5", "if", "IF")); // Definir un conjunto de valores permitidos
        String especificacion = obtenerEspecificacionPLG();

        if (valoresPermitidos.contains(txtNoOp.getText())) { // Si el valor está en el conjunto...
            if (aps.verificarRango(especificacion, txtValor.getText())) {
                Object componenteSeleccionado = cbxComponente.getSelectedItem(); // Obtén el componente
                if (componenteSeleccionado != null) { // Si el componente NO es null..
                    String componente = componenteSeleccionado.toString(); // Se obtiene el componente

                    String fecha = apc1.getFecha(); // Obtén la fecha formateada
                    String noOp = "if".equalsIgnoreCase(txtNoOp.getText()) ? "IF" : txtNoOp.getText(); // si se escribe "if" en minúsuculas, que se cambien a mayúsculas

                    // Se obtienen el resto de la información del registro 
                    String variable = obtenerVariableFormateada();
                    String especificacionPLG = obtenerEspecificacionPLG();
                    String valor = txtValor.getText();
                    int procesoCritico = cbxProcesoCritico.isSelected() ? 1 : 0;

                    Object[] filaDatos = {fecha, noOp, variable, especificacionPLG, valor}; // Se guarda la nueva fila de información 
                    nuevosDatos = new AceptacionPc3("", componente, noOp, fecha, variable, especificacionPLG, valor, procesoCritico); // Se crean objetos de la clase AceptacionPc3

                    listNuevosDatos.add(nuevosDatos); // Se añaden a la lista de nueva información a guardar
                    modeloTabla.addRow(filaDatos); // Se añaden a la tabla
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "El número de Operación es incorrecto", "Alerta", JOptionPane.WARNING_MESSAGE); // En caso de que el número de información no sea valido o sea nulo, se muestra el mensaje
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            int noOperacion = Integer.parseInt(txtNoOperaciones.getText()); // Se convierte el texto registrado en el campo en número
            if (noOperacion < 1 || noOperacion > 5) { // Si el número ingresado no esta en este rango...
                JOptionPane.showMessageDialog(this, "El número de operaciones debe estar en el rango del 1 al 5", "Alerta", JOptionPane.WARNING_MESSAGE); // Se muestra el mensaje haciendo la aclaración al usuario
            } else {
                setAceptacionPc1(); // Se modifica la información del objeto de AceptacionPc1
                setAceptacionPc2(); // Se modifica la información del objeto de AceptacionPc1
                try {
                    // Se realizan las consultas de inserción de los datos en la BD
                    aps.agregarpc1(conexion, apc1);
                    aps.agregarpc2(conexion, apc2);
                    aps.agregarpc3(conexion, listNuevosDatos);

                    String retencionDimensional = xls.generarExcelRD(conexion, ap1m, aps, cbxComponente, ap3m, apc2); // Se genera el documento de Retención Dimensional
                    this.ap.setComponente(cbxComponente.getSelectedItem().toString()); // Se captura el componente en el objeto de AceptacionProducto
                    this.ap.setRdPdf(aps.leerArchivo(retencionDimensional)); // Se captura el documento de Retención Dimensional
                    aps.subirRD(conexion, ap); // Se sube el documento y el componente a la BD

                    cerrarVentana(); // Se liberan los recursos de la interfaz
                    aps.abrirAceptacionProductoGUI(usr); // Se abre la ventana principal de AceptacionProducto
                } catch (IOException | SQLException | ClassNotFoundException ex) {
                    aps.manejarExcepcion("Surgio un error al Guardar los Datos", ex); // Se muestra el mensaje de la excepción al usuario 
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido."); // Se muestra el mensaje al usuario haciendo la aclaración al usuario
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAgregarVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarVariableActionPerformed
        cerrarVentana(); // Se cierra la ventana actual
        setAceptacionPc1();
        setAceptacionPc2(); // // Se modifica y captura la información del objeto de AceptacionPc2
        aps.abrirAgregarVariableGUI(usr, apc1, apc2); // Se abre la ventana de AgregarVariableGUI
    }//GEN-LAST:event_btnAgregarVariableActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        setAceptacionPc1(); // Se modifica la información del objeto de AceptacionPc1
        setAceptacionPc2(); // Se modifica la información del objeto de AceptacionPc2

        cerrarVentana(); // Se liberan los recursos de la ventana 
        JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS"); // Se muestra el mensaje de actualización
        aps.abrirRetencionDimensionalGUI(usr, apc1, apc2); // Se abre la ventana principal de RentencionDimensional
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblRD.getSelectedRow(); // Se obtiene la fila seleccionada de la tabla
        if (filaSeleccionada != -1) { // getSelectedRow() devuelve -1 cuando no hay ninguna fila seleccionada

            // Se capturan los valores de los datos obtenidos de las columnas
            String fecha = (String) tblRD.getValueAt(filaSeleccionada, COLUMN_NO_FECHA);
            String noOp = (String) tblRD.getValueAt(filaSeleccionada, COLUMN_NO_OP);
            String variable = (String) tblRD.getValueAt(filaSeleccionada, COLUMN_VARIABLE);
            String especificacion = (String) tblRD.getValueAt(filaSeleccionada, COLUMN_ESPECIFICACION);
            String valor = (String) tblRD.getValueAt(filaSeleccionada, COLUMN_VALOR);

            int respuesta = JOptionPane.showConfirmDialog(this, "LA INFORMACIÓN SELECCIONADA SE ELIMINARÁ, ¿ESTÁS DE ACUERDO?", "ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE); // Mensaje de confirmación de eliminación

            if (respuesta == JOptionPane.YES_OPTION) { // Si se confirma la eliminación...
                try {
                    eliminarRegistro(fecha, noOp, variable, especificacion, valor); // Se manda a eliminar la información de la fila seleccionada
                } catch (Exception ex) {
                    aps.manejarExcepcion("Surgio un error al ELIMINAR EL REGISTRO", ex); // Se muestra el mensaje de la excepción al usuario
                }
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int filaSeleccionada = tblRD.getSelectedRow(); // Se obtiene el indice de la fila seleccionada
        if (filaSeleccionada > 0) { // Si la fila seleccionada es mayor o igual a 0...
            setAceptacionPc1(); // Se modifica la información del objeto de AceptacionPc1
            setAceptacionPc2(); // Se modifica la información del objeto de AceptacionPc2

            cerrarVentana(); // Se cierra la ventana actual
            aps.abrirModificarRDGUI(this.ap3m.get(filaSeleccionada), usr, apc1, apc2); // Se abre la ventana ModificarRDGUI
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila, es posible que necesite guardar primero la información"); // Se muestra el mensaje, en caso de que el usuario no haya seleccionado una fila
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void inicializarVentanaYComponentes() {
        initComponents(); // Inicialización de Componentes
        this.setResizable(false); // Se define que no se puede redimensionar
        this.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema 
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Se deshabilita el boton de cerrar de la ventana
        this.ap3m = new ArrayList<>(); // Se crea la instancia de la lista del objeto ap3m 

        this.modeloTabla = new DefaultTableModel() { // Se crea el modelo default para la tabla
            @Override
            public boolean isCellEditable(int row, int column) { // Las celdas no se pueden editar
                return false;
            }
        };

        try {
            this.conexion = Conexion.getInstance().getConnection(); // Inicialización de la conexión a la BD
            initComponentes(); // Se inicializan los componentes de la interfaz
            initListeners(); // Se inicializan los listeners pera manejar los eventos
        } catch (ClassNotFoundException ex) {
            aps.manejarExcepcion(MSG_ERROR_RD, ex); // Se muestra el mensaje de la excepción al usuario  
        }
        DefaultTableModel dtm = buildTableModel(); // Se asigna la tabla con el modelo por default

        txtValor.addActionListener((ActionEvent ae) -> { // Se captura el evento para verificar que el rango sea el correcto
            aps.verificarRango(obtenerEspecificacionPLG(), txtValor.getText().trim()); // Método para verificar que el valor ingresado sea válido
        });

        tblRD.setModel(dtm); // Se define el modelo para la tabla de la ventana principal
        tblRD.setRowHeight(32); // Se define el tamaño de la altura de las filas
        txtEspecificacionPLG.setVisible(false); // El TextField de Especificación por default se mantiene invisible
        TableColumnModel columnModel = tblRD.getColumnModel(); // Se obtiene el modelo de la tabla

        // Se configura la anchura de las columnas de la tabla
        setColumnWidth(columnModel.getColumn(1), 60);
        setColumnWidth(columnModel.getColumn(4), 60);

        txtFecha.setText(apc1.getFecha()); // Se muestra la fecha en el campo respectivo
        txtFecha.setEnabled(false); // Se deshabilita la modificación del campo de fecha
        actualizarEstadoBotonPlanControl(); // Se actualiza el estado del plan de control
        info(); // Información definida por default en la ventana principal
    }

    private void initListeners() {
        cbxComponente.addActionListener((ActionEvent ae) -> { // Se capturan los eventos del ComboBox cbxComponente
            try {
                cbxVariable.removeAllItems(); // Se eliminan todos los items del ComboBox cbxVatriable
                String componenteSeleccionado = cbxComponente.getSelectedItem().toString(); // Se captura el componente seleccionado
                List<String> variablesPlanControl = aps.obtenerVariablesPC(conexion, componenteSeleccionado); // Se obtiene la lista de variables del Plan de control del componente
                List<String> variablesLista = aps.obtenerVariables(conexion); // Se obtiene la lista de variables general
                this.apc1Auxiliar = aps.recuperarInfoPc1(conexion, componenteSeleccionado); // Se recupera la información del componente
                this.ap3m = aps.recuperarAP3(conexion, cbxComponente.getSelectedItem().toString()); // Se recupera la información de la BD de la tabla "AceptacionPc3" 
                datosTabla(); // Se obtiene la información previamente capturada 

                // Se muestra la información recuperada del componente
                txtNoParte.setText(apc1Auxiliar.getNoParte());
                txtNoTroquel.setText(apc1Auxiliar.getNoTroquel());
                txtNoOperaciones.setText(apc1Auxiliar.getNoOps());

                if (variablesPlanControl.isEmpty()) { // Si la lista de variables del plan de control del componente esta vacía...
                    deshabilitarBotonPlanControl();
                    variablesLista.forEach(cbxVariable::addItem); // Se captura la información de la otra lista, la lista general
                } else {
                    habilitarBotonPlanControl();
                    variablesPlanControl.forEach(cbxVariable::addItem); // Se captura la información de la lista de Plan de control
                }
            } catch (SQLException | ClassNotFoundException ex) {
                aps.manejarExcepcion(MSG_ERROR_RD, ex); // Se muestra el mensaje de la excepción al usuario
            }
        });

        cbxVariable.addActionListener((ActionEvent ae) -> {
            cbxEspecificacionPLG.removeAllItems(); // Se eliminan todos los items del ComboBox cbxVatriable
            Object variableSeleccionada = cbxVariable.getSelectedItem(); // Se obtiene la variable Seleccionada
            if (variableSeleccionada != null) { // Si la variable no es null...
                String variable = variableSeleccionada.toString(); // Se guarda en la variable
                String componente = cbxComponente.getSelectedItem().toString(); // Se captura el componente
                try {
                    aps.obtenerEspecificaciones(conexion, variable, componente).forEach(cbxEspecificacionPLG::addItem); // Se obtiene la lista de especificaciones
                } catch (SQLException ex) {
                    aps.manejarExcepcion("Surgió un error al obtener las especificaciones", ex); // Se muestra el mensaje de la excepción al usuario
                }
            }
        });
    }

    private void initComponentes() throws ClassNotFoundException {
        try {
            cbxComponente.addItem(""); // Se agrega el item con texto vacío
            aps.obtenerComponetes(conexion).forEach(cbxComponente::addItem); // Se obtiene la lista de los componentes
            this.ap2m = aps.recuperarAP2(conexion, cbxComponente.getSelectedItem().toString()); // Se recupera la información de la BD de la tabla "AceptacionPc2"
        } catch (SQLException ex) {
            aps.manejarExcepcion(MSG_ERROR_RD, ex); // Se muestra el mensaje de la excepción al usuario
        }
    }

    private void setAceptacionPc1() {
        // Se captura el valor de los componentes en el objeto de AceptacionPc1
        apc1.setFecha(txtFecha.getText());
        apc1.setComponente(cbxComponente.getSelectedItem().toString());
        apc1.setNoParte(txtNoParte.getText());
        apc1.setNoTroquel(txtNoTroquel.getText());
        apc1.setNoOps(txtNoOperaciones.getText());
    }

    private void setAceptacionPc2() {
        // Se captura la información
        String componente = cbxComponente.getSelectedItem().toString();
        String fechap = apc1.getFecha();
        String noOrden = txtNoOrden.getText();
        String tamLote = txtTamLote.getText();
        String tamMta = txtTamMta.getText();
        String inspector = txtInspector.getText();
        String turno = txtTurno.getText();
        String disp = txtDisp.getText();
        apc2 = new AceptacionPc2("", componente, fechap, noOrden, tamLote, tamMta, inspector, turno, disp); // Se crea el nuevo objeto de AceptacionPc2
    }

    private void deshabilitarBotonPlanControl() {
        // Se deshabilita el boton para visualizar el plan de control y se habilita el campo textfield para la especificación
        btnPlanControl.setEnabled(false);
        cbxEspecificacionPLG.setVisible(false);
        txtEspecificacionPLG.setVisible(true);
    }

    private void habilitarBotonPlanControl() {
        // Se habilita el boton para visualizar el plan de control y se deshabilita el campo textfield para la especificación
        btnPlanControl.setEnabled(true);
        cbxEspecificacionPLG.setVisible(true);
        txtEspecificacionPLG.setVisible(false);
    }

    public void datosTabla() throws SQLException, ClassNotFoundException {
        modeloTabla.setRowCount(0); // Elimina todas las filas de la tabla
        if (this.ap3m != null) {
            this.ap3m.stream().map((apc3) -> { // Se utiliza la expresión lambda y las funcion stream para el manejo de la información
                Object fila[] = new Object[5]; // Se crea el arreglo para guardar los valores
                fila[COLUMN_NO_FECHA] = apc3.getFecha();
                fila[COLUMN_NO_OP] = apc3.getNoOp();
                fila[COLUMN_VARIABLE] = apc3.getVariable();
                fila[COLUMN_ESPECIFICACION] = apc3.getEspecificacion();
                fila[COLUMN_VALOR] = apc3.getValor();
                return fila;
            }).forEachOrdered((fila) -> {  // Cada elemento que se encuentra se agrega como fila a la tabla
                modeloTabla.addRow(fila); // Se añade la fila 
            });
        }
    }

    private DefaultTableModel buildTableModel() { // Método para construir el modelo de tabla
        // Se definen las columnas
        modeloTabla.addColumn("FECHA");
        modeloTabla.addColumn("NO. OPERACIÓN");
        modeloTabla.addColumn("VARIABLE");
        modeloTabla.addColumn("ESPECIFICACIÓN");
        modeloTabla.addColumn("VALOR");
        return modeloTabla;
    }

    private void cerrarVentana() {
        RetencionDimensional.this.dispose(); // Se liberan los recursos de la interfaz
    }

    private String obtenerEspecificacionPLG() {
        String especificacionPLG;
        if (cbxEspecificacionPLG.getSelectedItem() != null) { // Si se selecciona una especificación de la lista...
            especificacionPLG = cbxEspecificacionPLG.getSelectedItem().toString(); // Se captura para guardar 
        } else {
            especificacionPLG = txtEspecificacionPLG.getText(); // Si la lista es null, se captura para guardar lo que haya en el campo Jtexfield
        }
        return especificacionPLG;
    }

    private String obtenerVariableSeleccionada() {
        // Se obtiene la variable seleccionada
        Object selectedVariable = cbxVariable.getSelectedItem();
        return selectedVariable != null ? selectedVariable.toString() : "";
    }

    private String obtenerVariableFormateada() {
        // Si es proceso crítico se añade el prefijo de una "C" antes de la información
        String variable = cbxProcesoCritico.isSelected() ? "C " + obtenerVariableSeleccionada() : obtenerVariableSeleccionada();
        return variable;
    }

    public final void info() {
        // Se modifican los componentes con información por default
        txtInspector.setText("JC");
        txtTamLote.setText("400");
        txtTamMta.setText("3");
        txtDisp.setText("A");
        txtTurno.setText("1");
    }

    private void setColumnWidth(TableColumn column, int width) {
        column.setMinWidth(width); // Se modifica el mínimo de acho de la columna
        column.setMaxWidth(width); // Se modifica el máximo de acho de la columna
    }

    private void actualizarEstadoBotonPlanControl() {
        btnPlanControl.setEnabled(!cbxComponente.getSelectedItem().toString().isEmpty()); // Se modifica el estado del botón para visualizar el plan de control
    }

    private void eliminarRegistro(String fecha, String noOp, String variable, String especificacion, String valor) {
        setAceptacionPc1(); // Se modifica la información del objeto de AceptacionPc1
        setAceptacionPc2(); // Se modifica la información del objeto de AceptacionPc2

        aps.eliminarpc3(conexion, fecha, noOp, variable, especificacion, valor); // Se llama el método de eliminar
        cerrarVentana(); // Se liberan los recursos de la ventana
        JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS"); // Se muestra el mensaje de confirmación de la eliminación
        aps.abrirRetencionDimensionalGUI(usr, apc1, apc2); // Se abre la ventana de Retención Dimensional
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
            java.util.logging.Logger.getLogger(RetencionDimensional.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new RetencionDimensional().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregarVariable;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnPlanControl;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxComponente;
    private javax.swing.JComboBox<String> cbxEspecificacionPLG;
    private javax.swing.JCheckBox cbxProcesoCritico;
    private javax.swing.JComboBox<String> cbxVariable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblComponente;
    private javax.swing.JLabel lblDisp;
    private javax.swing.JLabel lblEspecificacion;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblInsp;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblNoOp;
    private javax.swing.JLabel lblNoOperaciones;
    private javax.swing.JLabel lblNoOrden;
    private javax.swing.JLabel lblNoParte;
    private javax.swing.JLabel lblNoTroquel;
    private javax.swing.JLabel lblProcesoCritico;
    private javax.swing.JLabel lblRetencionDimensional;
    private javax.swing.JLabel lblTamLote;
    private javax.swing.JLabel lblTamMuestra;
    private javax.swing.JLabel lblTurno;
    private javax.swing.JLabel lblValor;
    private javax.swing.JLabel lblVariable;
    private javax.swing.JPanel pnlPC;
    private javax.swing.JTable tblRD;
    private javax.swing.JTextField txtDisp;
    private javax.swing.JTextField txtEspecificacionPLG;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtInspector;
    private javax.swing.JTextField txtNoOp;
    private javax.swing.JTextField txtNoOperaciones;
    private javax.swing.JTextField txtNoOrden;
    private javax.swing.JTextField txtNoParte;
    private javax.swing.JTextField txtNoTroquel;
    private javax.swing.JTextField txtTamLote;
    private javax.swing.JTextField txtTamMta;
    private javax.swing.JTextField txtTurno;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
