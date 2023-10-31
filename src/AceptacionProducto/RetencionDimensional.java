/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AceptacionProducto;

import InspeccionRecibo.InspeccionReciboGUI;
import Modelos.AceptacionPc1;
import Modelos.AceptacionPc2;
import Modelos.AceptacionPc3;
import Modelos.Usuarios;
import Servicios.AceptacionProductoServicio;
import Servicios.Conexion;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author JC
 */
public class RetencionDimensional extends javax.swing.JFrame {

    Usuarios usr;
    Connection conexion;

    AceptacionPc1 apc1;
    AceptacionPc2 apc2;
    AceptacionPc3 apc3;

    AceptacionPc3 nuevosDatos;
    List<AceptacionPc3> listNuevosDatos;

    AceptacionProductoServicio aps = new AceptacionProductoServicio();

    int filaBaseNoOp1 = 9;
    int filaBaseNoOp3 = 47;
    int filaBaseNoOp4 = 85;
    int filaBaseNoOp5 = 123;
    int columnaBase = 8;

    DefaultTableModel tm;
    DefaultTableModel dt = new DefaultTableModel() { // Se crea el modelo default para la tabla
        @Override
        public boolean isCellEditable(int row, int column) { // Las celdas no se pueden editar
            return false;
        }
    };

    private List<AceptacionPc1> ap1m;
    private List<AceptacionPc2> ap2m;
    private List<AceptacionPc3> ap3m;

    // Constantes para los índices de columnas
    private static final int COLUMN_NO_FECHA = 0;
    private static final int COLUMN_NO_OP = 1;
    private static final int COLUMN_VARIABLE = 2;
    private static final int COLUMN_ESPECIFICACION = 3;
    private static final int COLUMN_VALOR = 4;

    /**
     * Creates new form RetencionDimensional
     */
    public RetencionDimensional() {
        initVentana(); // Inicialización de Componentes
    }

    public RetencionDimensional(Usuarios usr, AceptacionPc1 apc1) throws SQLException, ClassNotFoundException {
        initVentana();
        this.usr = usr;
        this.apc1 = apc1;
    }

    public RetencionDimensional(Usuarios usr) throws SQLException, ClassNotFoundException {
        initVentana();
        this.usr = usr;
        this.ap1m = new ArrayList<>();
        this.ap2m = new ArrayList<>();
    }

    public final void initVentana() {
        initComponents();
        this.setResizable(false); // Se define que no se puede redimensionar
        this.setDefaultCloseOperation(0); // Se deshabilita el boton de cerrar de la ventana
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton
        this.ap3m = new ArrayList<>();
        DefaultTableModel dtm = buildTableModel(); // Se asigna la tabla con el modelo por default

        txtValor.addActionListener((ActionEvent ae) -> {
            verificarRango();
        });

        try {
            initComponentes();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
        }

        initListeners();

        tblRD.setModel(dtm); // Se define el modelo para la tabla de la ventana principal
        tblRD.setRowHeight(32); // Se define el tamaño de la altura de las filas
        txtEspecificacionPLG.setVisible(false);
        TableColumnModel columnModel = tblRD.getColumnModel();
        setColumnWidth(columnModel.getColumn(1), 60);
        setColumnWidth(columnModel.getColumn(4), 60);

        actualizarEstadoBotonPlanControl();
        info();
    }

    private void initListeners() {
        cbxComponente.addActionListener((ActionEvent ae) -> {
            try {
                String componenteSeleccionado = cbxComponente.getSelectedItem().toString();
                List<String> variables = obtenerVariablesPC(componenteSeleccionado);
                this.ap2m = this.aps.recuperarAP2(conexion, componenteSeleccionado);
                this.ap3m = this.aps.recuperarAP3(conexion, componenteSeleccionado);
                datosTabla();
                cbxVariable.removeAllItems();
                cbxEspecificacionPLG.removeAllItems();
                if (variables.isEmpty()) {
                    btnPlanControl.setEnabled(false);
                    cbxEspecificacionPLG.setVisible(false);
                    txtEspecificacionPLG.setVisible(true);
                    obtenerVariables().forEach(cbxVariable::addItem);
                } else {
                    btnPlanControl.setEnabled(true);
                    cbxEspecificacionPLG.setVisible(true);
                    txtEspecificacionPLG.setVisible(false);
                    variables.forEach(cbxVariable::addItem);
                }

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        cbxVariable.addActionListener((ActionEvent ae) -> {
            try {
                Object selectedVariable = cbxVariable.getSelectedItem();

                if (selectedVariable != null) {
                    String variable = selectedVariable.toString();
                    String componente = cbxComponente.getSelectedItem().toString();
                    obtenerEspecificaciones(variable, componente).forEach(cbxEspecificacionPLG::addItem);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void datosTabla() throws SQLException, ClassNotFoundException {
        dt.setRowCount(0); // Elimina todas las filas de la tabla
        if (this.ap3m != null) { // Si el tamaño de la lista es mayor a 0
            for (int i = 0; i < this.ap3m.size(); i++) { // Se recorren todos los elementos de la lista
                Object fila[] = new Object[5]; // Se crea el arreglo para guardar los valores
                // Se captura la información en el arreglo
                fila[COLUMN_NO_FECHA] = this.ap3m.get(i).getFecha();
                fila[COLUMN_NO_OP] = this.ap3m.get(i).getNoOp();
                fila[COLUMN_VARIABLE] = this.ap3m.get(i).getVariable();
                fila[COLUMN_ESPECIFICACION] = this.ap3m.get(i).getEspecificacion();
                fila[COLUMN_VALOR] = this.ap3m.get(i).getValor();
                dt.addRow(fila); // Se añade la fila 
            }
        } else {
            dt.setRowCount(0); // Elimina todas las filas de la tabla
        }
    }

    // Método para construir el modelo de tabla
    private DefaultTableModel buildTableModel() {
        // Se definen las columnas
        dt.addColumn("FECHA");
        dt.addColumn("NO. OPERACIÓN");
        dt.addColumn("VARIABLE");
        dt.addColumn("ESPECIFICACIÓN");
        dt.addColumn("VALOR");
        return dt;
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
        btnImprimirRD = new swing.Button(new Color(107, 240, 105),new Color(75, 212, 73));
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
        txtNoOp = new swing.TextField();
        cbxVariable = new swing.ComboBoxSuggestion();
        txtValor = new swing.TextField();
        btnAceptar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        lblEspecificacion = new javax.swing.JLabel();
        cbxEspecificacionPLG = new swing.ComboBoxSuggestion();
        txtEspecificacionPLG = new swing.TextField();
        lblProcesoCritico = new javax.swing.JLabel();
        cbxProcesoCritico = new swing.JCheckBoxCustom(new Color(255,0,0));
        btnAgregarVariable = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        dchFecha = new com.toedter.calendar.JDateChooser();
        lblFecha = new javax.swing.JLabel();
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

        btnImprimirRD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excelC.png"))); // NOI18N
        btnImprimirRD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirRDActionPerformed(evt);
            }
        });
        jPanel1.add(btnImprimirRD, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 610, 80, 40));

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
        pnlPC.add(txtNoOp, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 280, -1));

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
        pnlPC.add(dchFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 280, -1));

        lblFecha.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        lblFecha.setText("FECHA:");
        pnlPC.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

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
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 630, 140, -1));

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
        RetencionDimensional.this.dispose(); // Se liberan los recursos de la interfaz
        try {
            AceptacionProducto apGUI = new AceptacionProducto(usr); // Se crea una instancia de la interfaz gráfica
            apGUI.setVisible(true); // Se hace visible la ventana
            apGUI.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema 
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnPlanControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlanControlActionPerformed
        String componentepc = cbxComponente.getSelectedItem().toString();
        String componenteEncoded = ""; //Codifica el valor del parámetro para que sea seguro en una URL
        try {
            componenteEncoded = URLEncoder.encode(componentepc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        // Construye la URL con el valor del parámetro de consulta
        String phpFilePath = "http://192.168.1.66/produccion/php/PlanDeControlCuadro.php?componente=" + componenteEncoded;
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(phpFilePath));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnPlanControlActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if (verificarRango()) {
            String componente = cbxComponente.getSelectedItem().toString();
            // Obtén la fecha seleccionada
            Date selectedDate = dchFecha.getDate();
            // Crea un objeto SimpleDateFormat para el formato deseado
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            // Se almacenan los datos capturados en las variable
            String fecha = dateFormat.format(selectedDate); // Formatea la fecha y guárdala en una variable String
            String noOp = txtNoOp.getText();
            String variable = cbxProcesoCritico.isSelected() ? "Ø " + cbxVariable.getSelectedItem().toString() : cbxVariable.getSelectedItem().toString();

            String especificacionPLG;

            if (cbxEspecificacionPLG.getSelectedItem() != null) {
                especificacionPLG = cbxEspecificacionPLG.getSelectedItem().toString();
            } else {
                especificacionPLG = txtEspecificacionPLG.getText();
            }

            String valor = txtValor.getText();

            Object[] rowData = {fecha, noOp, variable, especificacionPLG, valor};
            // Agrega la fila al modelo de tabla
            nuevosDatos = new AceptacionPc3("", componente, noOp, fecha, variable, especificacionPLG, valor);

            listNuevosDatos.add(nuevosDatos);
            dt.addRow(rowData);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String componente = cbxComponente.getSelectedItem().toString();
        String fecha = apc1.getFecha();
        System.out.println(fecha);
        String noOrden = txtNoOrden.getText();
        String tamLote = txtTamLote.getText();
        String tamMta = txtTamMta.getText();
        String inspector = txtInspector.getText();
        String turno = txtTurno.getText();
        String disp = txtDisp.getText();

        apc1.setComponente(cbxComponente.getSelectedItem().toString());
        apc1.setNoParte(txtNoParte.getText());
        apc1.setNoTroquel(txtNoTroquel.getText());
        apc1.setNoOps(txtNoOperaciones.getText());

        apc2 = new AceptacionPc2("", componente, fecha, noOrden, tamLote, tamMta, inspector, turno, disp);
        try {
            aps.agregarpc1(conexion, apc1);
            aps.agregarpc2(conexion, apc2);
            aps.agregarpc3(conexion, listNuevosDatos);

            RetencionDimensional.this.dispose(); // Se liberan los recursos de la interfaz
            JOptionPane.showMessageDialog(this, "DATOS GUARDADOS EN EL LUGAR DONDE SE GUARDAN LOS DATOS");
            try {
                RetencionDimensional apGUI = new RetencionDimensional(usr, apc1); // Se crea una instancia de la interfaz gráfica
                apGUI.setVisible(true); // Se hace visible la ventana
                apGUI.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema 
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnImprimirRDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirRDActionPerformed
        try {
            generarExcel();
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImprimirRDActionPerformed

    private void btnAgregarVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarVariableActionPerformed
        RetencionDimensional.this.dispose();
        AgregarVariable avGUI = new AgregarVariable(usr, apc1);
        avGUI.setVisible(true);
    }//GEN-LAST:event_btnAgregarVariableActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        RetencionDimensional.this.dispose(); // Se liberan los recursos de la ventana 
        JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS"); // Se muestra el mensaje de actualización
        try {
            RetencionDimensional irGUI = new RetencionDimensional(usr, apc1); //Se crea la instancia de la clase
            irGUI.setVisible(true); //Se hace visible la ventana
            irGUI.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblRD.getSelectedRow();
        if (filaSeleccionada != -1) {
            String fecha = (String) tblRD.getValueAt(filaSeleccionada, 0);
            String noOp = (String) tblRD.getValueAt(filaSeleccionada, 1);
            String variable = (String) tblRD.getValueAt(filaSeleccionada, 2);
            String especificacion = (String) tblRD.getValueAt(filaSeleccionada, 3);
            String valor = (String) tblRD.getValueAt(filaSeleccionada, 4);
            int respuesta = JOptionPane.showConfirmDialog(this, "LA INFORMACIÓN SELECCIONADA SE ELIMINARÁ, ¿ESTÁS DE ACUERDO?", "ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (respuesta == JOptionPane.YES_NO_OPTION) {
                eliminarRegistro(fecha, noOp, variable, especificacion, valor);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int filaSeleccionada = tblRD.getSelectedRow(); // Se obtiene el indice de la fila seleccionada...
        if (filaSeleccionada >= 0) { // Si la fila seleccionada es mayor o igual a 0
            RetencionDimensional.this.dispose();
            ModificarRD modificar = new ModificarRD(this.ap3m.get(filaSeleccionada), usr);
            modificar.setVisible(true);
            modificar.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

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
    private javax.swing.JButton btnImprimirRD;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnPlanControl;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxComponente;
    private javax.swing.JComboBox<String> cbxEspecificacionPLG;
    private javax.swing.JCheckBox cbxProcesoCritico;
    private javax.swing.JComboBox<String> cbxVariable;
    private com.toedter.calendar.JDateChooser dchFecha;
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

    private List<String> obtenerDatos(String query, String columna) throws SQLException {
        List<String> listaDatos = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(query);
                ResultSet resultado = consulta.executeQuery()) {
            while (resultado.next()) {
                listaDatos.add(resultado.getString(columna));
            }
        }
        return listaDatos;
    }

    private List<String> obtenerComponetes() throws SQLException {
        String query = "SELECT componente FROM antecedentesfamilia";
        return obtenerDatos(query, "componente");
    }

    private List<String> obtenerVariablesPC(String variable) throws SQLException {
        String query = "SELECT pa.nombreProducto FROM productosactividadespc AS pa JOIN descripcion2pc AS d2 ON pa.nombreProducto = d2.producto WHERE d2.componente = '" + variable + "'";
        return obtenerDatos(query, "nombreProducto");
    }

    private List<String> obtenerVariables() throws SQLException {
        String query = "SELECT nombreProducto FROM productosactividadespc";
        return obtenerDatos(query, "nombreProducto");
    }

    private List<String> obtenerEspecificaciones(String variable, String componente) throws SQLException {
        List<String> listaEspecificaciones = new ArrayList<>();
        String producto = variable + "%"; // Agregar el comodín % al final para buscar palabras que comiencen con la variable
        String sqlConsulta = "SELECT t1.especificacion FROM descripcion3pc AS t1 JOIN descripcion2pc AS t2 ON t1.componente = t2.componente AND t1.noPartesP = t2.noPartesP AND t1.no = t2.no WHERE t2.producto LIKE ? AND t1.componente = ? GROUP BY t1.especificacion, t2.producto";
        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
            consulta.setString(1, producto);
            consulta.setString(2, componente);

            // Ejecutar la consulta y procesar los resultados
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                listaEspecificaciones.add(resultado.getString("especificacion"));
            }
        }
        return listaEspecificaciones;
    }

    private boolean verificarRango() {
        String seleccion;
        if (cbxEspecificacionPLG.getSelectedItem() != null) {
            seleccion = cbxEspecificacionPLG.getSelectedItem().toString();
        } else {
            seleccion = txtEspecificacionPLG.getText().trim();
        }

        String valorIngresado = txtValor.getText().trim();
        if (seleccion.contains("-")) { // Con esto se define que se seleccionó un rango
            String[] valoresRango = seleccion.split("-");
            try {
                double valorMin = Double.parseDouble(valoresRango[0]);
                double valorMax = Double.parseDouble(valoresRango[1]);
                double valor = Double.parseDouble(valorIngresado);
                if (valor < valorMin || valor > valorMax) {
                    JOptionPane.showMessageDialog(this, "El valor ingresado está fuera del rango seleccionado (" + seleccion + ")", "Alerta", JOptionPane.WARNING_MESSAGE);
                    txtValor.setText("");
                    return false;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor numérico válido", "Alerta", JOptionPane.WARNING_MESSAGE);
                txtValor.setText("");
            }
        }
        return true;
    }

    public void generarExcel() throws IOException, SQLException, ClassNotFoundException {
        String filePath = "RETENCION-DIMENSIONAL.xlsx";
        this.ap1m = this.aps.recuperarAP1(conexion, cbxComponente.getSelectedItem().toString()); // Se recupera la información de la BD de la tabla "AceptacionPc1"
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Obtén la primera hoja del libro
            int fila = 6;
            if (this.ap1m == null || this.ap1m.isEmpty()) {
                System.out.println("Lista Vacia");
            } else {
                for (int i = 0; i < this.ap1m.size(); i++) {
                    // Fecha
                    Row rowFecha = sheet.getRow(fila); // Obtén la fila
                    Cell cellFecha = rowFecha.getCell(3); // Obtén la celda en la fila
                    cellFecha.setCellValue(this.ap1m.get(i).getFecha()); // Modifica el valor de la celda

                    // No Rollo
                    Row rowNoRollo = sheet.getRow(fila); // Obtén la fila
                    Cell cellNoRollo = rowNoRollo.getCell(8); // Obtén la celda en la fila
                    cellNoRollo.setCellValue(this.ap1m.get(i).getNoRollo()); // Modifica el valor de la celda

                    // Inspección Visual
                    Row rowInps = sheet.getRow(fila); // Obtén la fila
                    Cell cellInps = rowInps.getCell(11); // Obtén la celda en la fila
                    cellInps.setCellValue(this.ap1m.get(i).getInspVisual()); // Modifica el valor de la celda

                    // Observación
                    Row rowObservacion = sheet.getRow(fila); // Obtén la fila
                    Cell cellObservacion = rowObservacion.getCell(16); // Obtén la celda en la fila
                    cellObservacion.setCellValue(this.ap1m.get(i).getObservacion()); // Modifica el valor de la celda

                    fila++;
                }
                XSSFSheet sheet2 = workbook.getSheetAt(1); // Obtén la segunda hoja del libro

                int[] filas = {2, 40, 79, 119}; // Filas para mostrar info del componente
                int[] columnas = {13, 20, 26}; // columnas respectivas

                for (int filan : filas) {
                    for (int columna : columnas) {
                        Row rowComponente = sheet2.getRow(filan); // Obtén la fila
                        Cell cell = rowComponente.getCell(columna);
                        switch (columna) {
                            case 13:
                                cell.setCellValue(this.ap1m.get(0).getComponente());
                                break;
                            case 20:
                                cell.setCellValue(this.ap1m.get(0).getNoParte());
                                break;
                            case 26:
                                cell.setCellValue(this.ap1m.get(0).getNoTroquel());
                                break;
                        }
                    }
                }

                int[] filaProcesos = {4, 42, 81, 121}; //No Ops

                for (int filaP : filaProcesos) {
                    // NoOps
                    int fila2 = 12;
                    for (int i = 1; i <= Integer.parseInt(this.ap1m.get(0).getNoOps()); i++) {
                        // NoOps
                        Row rowNoOps = sheet2.getRow(filaP); // Obtén la fila
                        Cell cellNoOps = rowNoOps.getCell(fila2); // Obtén la celda en la fila
                        cellNoOps.setCellValue("X"); // Modifica el valor de la celda
                        fila2 += 4;
                    }
                }

                // Crear un estilo con formato de texto que permita saltos de línea
                CellStyle cellStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setFontHeightInPoints((short) 7); // Cambia el tamaño del texto aquí (14 puntos en este caso)
                cellStyle.setRotation((short) 90); // Cambia el ángulo de rotación aquí (90 grados en este caso)
                cellStyle.setFont(font);
                cellStyle.setWrapText(true); // Permite saltos de línea
                // Crear un margen en la parte superior
                cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

                // Listas para almacenar las variables en orden
                List<String> listVariables1 = new ArrayList<>();
                List<String> listVariables3 = new ArrayList<>();
                List<String> listVariables4 = new ArrayList<>();
                List<String> listVariables5 = new ArrayList<>();

                TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp12 = new TreeMap<>(new Comparator<String>() {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    @Override
                    public int compare(String fecha1, String fecha2) {
                        try {
                            Date date1 = sdf.parse(fecha1);
                            Date date2 = sdf.parse(fecha2);
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            return 0;
                        }
                    }
                });
                TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp3 = new TreeMap<>(new Comparator<String>() {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    @Override
                    public int compare(String fecha1, String fecha2) {
                        try {
                            Date date1 = sdf.parse(fecha1);
                            Date date2 = sdf.parse(fecha2);
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            return 0;
                        }
                    }
                });
                TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp4 = new TreeMap<>(new Comparator<String>() {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    @Override
                    public int compare(String fecha1, String fecha2) {
                        try {
                            Date date1 = sdf.parse(fecha1);
                            Date date2 = sdf.parse(fecha2);
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            return 0;
                        }
                    }
                });
                TreeMap<String, TreeMap<String, Map<String, String>>> mapVariablesNoOp5 = new TreeMap<>(new Comparator<String>() {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    @Override
                    public int compare(String fecha1, String fecha2) {
                        try {
                            Date date1 = sdf.parse(fecha1);
                            Date date2 = sdf.parse(fecha2);
                            return date1.compareTo(date2);
                        } catch (ParseException e) {
                            return 0;
                        }
                    }
                });
                TreeMap<String, TreeMap<String, Map<String, String>>> selectedMap;
                for (AceptacionPc3 dataVariable : this.ap3m) {
                    String variable = dataVariable.getVariable() + "\n" + dataVariable.getEspecificacion();
                    String valor = dataVariable.getValor();
                    // Busca el objeto correspondiente en ap2m (usando un identificador único como ejemplo)
                    String id = dataVariable.getFecha(); // Reemplaza esto con el campo adecuado en ap3m que relaciona con ap2m
                    String comp = dataVariable.getComponente(); // Reemplaza esto con el campo adecuado en ap3m que relaciona con ap2m
                    List<AceptacionPc2> objetoAp2m = buscarObjetoEnAp2mPorFecha(id, comp); // Implementa una función que busque en ap2m por el id                    

                    switch (dataVariable.getNoOp()) {
                        case "1":
                        case "2":
                            selectedMap = mapVariablesNoOp12;
                            if (!listVariables1.contains(variable)) {
                                listVariables1.add(variable);
                            }
                            break;
                        case "3":
                            selectedMap = mapVariablesNoOp3;
                            if (!listVariables3.contains(variable)) {
                                listVariables3.add(variable);
                            }
                            break;
                        case "4":
                            selectedMap = mapVariablesNoOp4;
                            if (!listVariables4.contains(variable)) {
                                listVariables4.add(variable);
                            }
                            break;
                        case "5":
                            selectedMap = mapVariablesNoOp5;
                            if (!listVariables5.contains(variable)) {
                                listVariables5.add(variable);
                            }
                            break;
                        default:
                            // En caso de valores de noOp desconocidos
                            selectedMap = null; // O maneja de alguna otra manera
                            break;
                    }

                    if (selectedMap != null) {
                        selectedMap.computeIfAbsent(dataVariable.getFecha(), k -> new TreeMap<>())
                                .computeIfAbsent(dataVariable.getNoOp(), k -> new LinkedHashMap<>())
                                .put(variable, valor);
                        // Agrega campos de ap2m
                        int numO = 0;
                        for (AceptacionPc2 dataVariable3 : objetoAp2m) {
                            // Agrega el tamaño y el número de orden al mapa
                            Map<String, String> variablesMap = selectedMap.get(dataVariable.getFecha()).get(dataVariable.getNoOp());
                            variablesMap.put("noOrden" + numO, dataVariable3.getNoOrden());
                            variablesMap.put("tamLote" + numO, dataVariable3.getTamLote());
                            variablesMap.put("tamMta" + numO, dataVariable3.getTamMta());
                            variablesMap.put("insp" + numO, dataVariable3.getInspector());
                            variablesMap.put("turno" + numO, dataVariable3.getTurno());
                            variablesMap.put("disp" + numO, dataVariable3.getDisp());
                        }
                    }
                }

                filaBaseNoOp1 = 9;
                filaBaseNoOp3 = 47;
                filaBaseNoOp4 = 85;
                filaBaseNoOp5 = 123;
                getProceso(mapVariablesNoOp12, columnaBase, listVariables1, filaBaseNoOp1, sheet2, 5, workbook);

                /*  getProceso(mapVariablesNoOp3, columnaBase, listVariables3, filaBaseNoOp3, sheet2, 43, workbook);
                getProceso(mapVariablesNoOp4, columnaBase, listVariables4, filaBaseNoOp4, sheet2, 82, workbook);
                getProceso(mapVariablesNoOp5, columnaBase, listVariables5, filaBaseNoOp5, sheet2, 122, workbook);
                 */
            }
            // Crear un nuevo archivo de Excel para guardar los cambios
            String newFilePath = "-" + this.ap1m.get(0).getComponente() + ".xlsx";  // Ruta del nuevo archivo de Excel
            FileOutputStream fos = new FileOutputStream(newFilePath);
            workbook.write(fos);
        }
    }

    public final void info() {
        txtNoParte.setText("52127861");
        txtNoTroquel.setText("9125 - 9126");
        txtNoOperaciones.setText("4");
        txtNoOrden.setText("P008503-72");
        txtInspector.setText("JC");
        txtTamLote.setText("400");
        txtTamMta.setText("3");
        txtDisp.setText("A");
        txtTurno.setText("1");
    }

    private void initComponentes() throws ClassNotFoundException {
        try {
            obtenerComponetes().forEach(cbxComponente::addItem);
            this.ap2m = this.aps.recuperarAP2(conexion, cbxComponente.getSelectedItem().toString()); // Se recupera la información de la BD de la tabla "AceptacionPc2"
        } catch (SQLException ex) {
            Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setColumnWidth(TableColumn column, int width) {
        column.setMinWidth(width);
        column.setMaxWidth(width);
    }

    private void actualizarEstadoBotonPlanControl() {
        btnPlanControl.setEnabled(!cbxComponente.getSelectedItem().toString().isEmpty());
    }

    private void getProceso(TreeMap<String, TreeMap<String, Map<String, String>>> map, int columnaBase, List<String> listVariables, int fila, Sheet sheet2, int rows, XSSFWorkbook workbook) {
        int tamanoEntrada1 = 0;
        int tamanoEntrada2 = 0;

        for (Map.Entry<String, TreeMap<String, Map<String, String>>> entrada1 : map.entrySet()) {
            tamanoEntrada1++; // Incrementa por cada entrada en map
            int tamanoEntrada2Actual = entrada1.getValue().entrySet().size(); // Para obtener el tamaño de entrada2 en esta entrada1 particular
            tamanoEntrada2 += tamanoEntrada2Actual;
        }

        int filasNecesarias = (tamanoEntrada2 > 28) ? tamanoEntrada2 - 28 : 0; // Añade más filas base según tus necesidades

        if (filasNecesarias > 0) {
            // Inserta las filas necesarias en la hoja
            sheet2.shiftRows(fila + 1, sheet2.getLastRowNum() + 1, filasNecesarias, true, false);

            // Ajusta las filas base para reflejar las inserciones
            filaBaseNoOp1 += filasNecesarias;
            filaBaseNoOp3 += filasNecesarias;
            filaBaseNoOp4 += filasNecesarias;
            filaBaseNoOp5 += filasNecesarias;
        }
        int baseNuevas = columnaBase;
        for (int i = 0; i < filasNecesarias; i++) {
            int filaNecesaria = fila + i + 1; // La fila actual
            Row row = sheet2.getRow(filaNecesaria);
            if (row == null) {
                row = sheet2.createRow(filaNecesaria);
            }

            for (int j = 0; j < 22; j += 2) {
                // Crea un CellRangeAddress para combinar las celdas en pares
                CellRangeAddress region = new CellRangeAddress(filaNecesaria, filaNecesaria, baseNuevas + j, baseNuevas + j + 1);
                // Combina las celdas en la hoja
                sheet2.addMergedRegion(region);
            }
        }

        baseNuevas += 2; // Avanzar a la siguiente pareja de columnas base

        for (Map.Entry<String, TreeMap<String, Map<String, String>>> entrada1 : map.entrySet()) {
            for (Map.Entry<String, Map<String, String>> entrada2 : entrada1.getValue().entrySet()) {
                String noOp = entrada2.getKey(); // Convertir noOp a entero
                Row row = sheet2.getRow(fila);
                fila++; // Incrementar la fila para el próximo valor con noOp igual a 1
                if (row != null) {
                    Cell cellNoOp = row.getCell(0);
                    if (cellNoOp == null) {
                        cellNoOp = row.createCell(0);
                    }
                    cellNoOp.setCellValue(noOp); // Número de operación

                    Cell cellFecha = row.getCell(1);
                    if (cellFecha == null) {
                        cellFecha = row.createCell(1);
                    }
                    cellFecha.setCellValue(entrada1.getKey());
                    Row variableRow = sheet2.getRow(rows);

                    if (variableRow == null) {
                        variableRow = sheet2.createRow(rows);
                    }

                    int variableColumna = 9; // Se comienzan a imprimir desde esta columna
                    int celdaBarra = 8;
                    int celdaVar = 9;
                    for (String variable : listVariables) {
                        Cell variableCelda = variableRow.getCell(variableColumna);
                        // Verificar si la celda es nula
                        if (variableCelda == null) {
                            // Si la celda es nula, crea una nueva celda en su lugar
                            variableCelda = variableRow.createCell(variableColumna);

                            sheet2.addMergedRegion(new CellRangeAddress(5, 8, celdaBarra, celdaBarra));
                            sheet2.addMergedRegion(new CellRangeAddress(5, 8, celdaVar, celdaVar));
                            sheet2.setColumnWidth(celdaBarra, 570); // Establece el ancho de la columna 0
                            sheet2.setColumnWidth(celdaVar, 1450); // Establece el ancho de la columna 0

                            // Crear filas y celdas
                            Row sourceRow = sheet2.getRow(5);
                            Cell sourceCell = sourceRow.getCell(10);

                            Row targetRow = sheet2.getRow(5);
                            Cell targetCell = targetRow.createCell(celdaBarra);

                            // Crear filas y celdas
                            Row sourceRow2 = sheet2.getRow(5);
                            Cell sourceCell2 = sourceRow2.getCell(9);

                            Row targetRow2 = sheet2.getRow(5);
                            Cell targetCell2 = targetRow2.createCell(celdaVar);

                            // Crear un estilo de celda
                            CellStyle cellStyle4 = workbook.createCellStyle();
                            CellStyle cellStyle5 = workbook.createCellStyle();

                            cellStyle4.cloneStyleFrom(sourceCell.getCellStyle()); // Copia el estilo de la celda de origen
                            cellStyle5.cloneStyleFrom(sourceCell2.getCellStyle()); // Copia el estilo de la celda de origen
                            sheet2.setColumnWidth(celdaBarra, 600);
                            // Aplicar el estilo a la celda de destino
                            targetCell.setCellStyle(cellStyle4);
                            targetCell2.setCellStyle(cellStyle5);

                        }
                        variableCelda.setCellValue(variable);
                        celdaBarra += 2;
                        celdaVar += 2;
                        variableColumna += 2;

                    }
                    Map<String, String> variablesMap = entrada2.getValue();
                    int columna = columnaBase; // Inicializar la columna en función de columnaBase
                    for (String variable : listVariables) {
                        Cell cellVariable = row.getCell(columna);
                        Cell cellVariable2 = row.getCell(columna + 1);
                        if (cellVariable == null || columna > 42 || cellVariable2 == null || columna > 42) {
                            // Si la celda es nula, crea una nueva celda en su lugar
                            cellVariable = row.createCell(columna);
                            cellVariable2 = row.createCell(columna + 1);
                        }
                        if (variablesMap.containsKey(variable)) {
                            cellVariable.setCellValue(variablesMap.get(variable));

                            ///////////
                            // Crear filas y celdas
                            Row sourceRow = sheet2.getRow(9);
                            Cell sourceCell = sourceRow.getCell(8);

                            Row sourceRow2 = sheet2.getRow(9);
                            Cell sourceCell2 = sourceRow2.getCell(9);

                            // Crear un estilo de celda
                            CellStyle cellStyle4 = workbook.createCellStyle();
                            CellStyle cellStyle5 = workbook.createCellStyle();

                            cellStyle4.cloneStyleFrom(sourceCell.getCellStyle()); // Copia el estilo de la celda de origen
                            cellStyle5.cloneStyleFrom(sourceCell2.getCellStyle()); // Copia el estilo de la celda de origen

                            cellVariable.setCellStyle(cellStyle4);
                            cellVariable2.setCellStyle(cellStyle5);

                        } else {
                            cellVariable.setCellValue("N/A");

                            int columnaBase2 = 30; // Definir la columna base a partir de la cual se combinarán las celdas en pares

                            for (String variable3 : listVariables) {
                                for (int i = 0; i < 2; i++) {
                                    // Para cada variable, se iterará dos veces para combinar celdas en pares

                                    int columna2 = columnaBase2 + i; // Ajustar la columna en función de la columna base

                                    Cell cellVariable4 = row.getCell(columna2);
                                    Cell cellVariable22 = row.getCell(columna2 + 1);

                                    if (cellVariable == null || cellVariable2 == null) {
                                        // Si la celda es nula, crea una nueva celda en su lugar
                                        cellVariable = row.createCell(columna);
                                        cellVariable2 = row.createCell(columna + 1);
                                    }

                                    if (variablesMap.containsKey(variable)) {
                                        cellVariable.setCellValue(variablesMap.get(variable));

                                        // Combinar las celdas en pares
                                        CellRangeAddress region = new CellRangeAddress(
                                                row.getRowNum(), // Fila inicial
                                                row.getRowNum(), // Fila final (misma fila)
                                                columna, // Columna inicial
                                                columna + 1 // Columna final (celda siguiente)
                                        );
                                        sheet2.addMergedRegion(region);

                                        // Copiar el estilo de celda de origen para combinar
                                        Row sourceRow = sheet2.getRow(9); // Puedes ajustar la fila de origen según tus necesidades
                                        Cell sourceCell = sourceRow.getCell(columnaBase);
                                        CellStyle cellStyle = workbook.createCellStyle();
                                        cellStyle.cloneStyleFrom(sourceCell.getCellStyle());

                                        cellVariable.setCellStyle(cellStyle);
                                        cellVariable2.setCellStyle(cellStyle);
                                    } else {
                                        cellVariable.setCellValue("N/A");

                                        // Combinar las celdas en pares
                                        CellRangeAddress region = new CellRangeAddress(
                                                row.getRowNum(), // Fila inicial
                                                row.getRowNum(), // Fila final (misma fila)
                                                columna, // Columna inicial
                                                columna + 1 // Columna final (celda siguiente)
                                        );
                                        sheet2.addMergedRegion(region);

                                        // Copiar el estilo de celda de origen para combinar
                                        Row sourceRow = sheet2.getRow(9); // Puedes ajustar la fila de origen según tus necesidades
                                        Cell sourceCell = sourceRow.getCell(columnaBase);
                                        CellStyle cellStyle = workbook.createCellStyle();
                                        cellStyle.cloneStyleFrom(sourceCell.getCellStyle());

                                        cellVariable.setCellStyle(cellStyle);
                                        cellVariable2.setCellStyle(cellStyle);
                                    }
                                }
                            }

                            //////////////////////////////////////////////
                            // Crear filas y celdas
                            Row sourceRow = sheet2.getRow(9);
                            Cell sourceCell = sourceRow.getCell(8);

                            Row sourceRow2 = sheet2.getRow(9);
                            Cell sourceCell2 = sourceRow2.getCell(9);

                            // Crear un estilo de celda
                            CellStyle cellStyle4 = workbook.createCellStyle();
                            CellStyle cellStyle5 = workbook.createCellStyle();

                            cellStyle4.cloneStyleFrom(sourceCell.getCellStyle()); // Copia el estilo de la celda de origen
                            cellStyle5.cloneStyleFrom(sourceCell2.getCellStyle()); // Copia el estilo de la celda de origen

                            cellVariable.setCellStyle(cellStyle4);
                            cellVariable2.setCellStyle(cellStyle5);
                        }
                    }

                    // Concatenar valores de 'noOrden'
                    Set<String> valoresUnicosNoOrden = new HashSet<>();
                    Set<String> valoresUnicosTamLote = new HashSet<>();
                    Set<String> valoresUnicosTamMta = new HashSet<>();
                    Set<String> valoresUnicosInsp = new HashSet<>();
                    Set<String> valoresUnicosTurno = new HashSet<>();
                    Set<String> valoresUnicosDisp = new HashSet<>();

                    // Iterar sobre los valores y agregarlos a los conjuntos correspondientes
                    variablesMap.entrySet().forEach((variableEntry) -> {
                        String variableKey = variableEntry.getKey();
                        String valor = variableEntry.getValue();

                        if (variableKey.startsWith("noOrden")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosNoOrden.add(valor);
                            }
                        } else if (variableKey.startsWith("tamLote")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosTamLote.add(valor);
                            }
                        } else if (variableKey.startsWith("tamMta")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosTamMta.add(valor);
                            }
                        } else if (variableKey.startsWith("insp")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosInsp.add(valor);
                            }
                        } else if (variableKey.startsWith("turno")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosTurno.add(valor);
                            }
                        } else if (variableKey.startsWith("disp")) {
                            if (!valor.isEmpty()) {
                                valoresUnicosDisp.add(valor);
                            }
                        }
                    });

                    // Crear cadenas de concatenación a partir de los valores únicos en los conjuntos
                    String noOrdenConcatenado = String.join("\n", valoresUnicosNoOrden);
                    String tamLoteConcatenado = String.join("\n", valoresUnicosTamLote);
                    String tamMtaConcatenado = String.join("\n", valoresUnicosTamMta);
                    String inspConcatenado = String.join("\n", valoresUnicosInsp);
                    String turnoConcatenado = String.join("\n", valoresUnicosTurno);
                    String dispConcatenado = String.join("\n", valoresUnicosDisp);

                    // Obtener el valor actual en la celda (si existe)
                    Cell cellNoOrden = row.getCell(2); // Ajusta 'columnaNoOrden' según la ubicación deseada
                    Cell cellTamLote = row.getCell(3); // Ajusta 'columnaNoOrden' según la ubicación deseada
                    Cell cellTamMta = row.getCell(4); // Ajusta 'columnaNoOrden' según la ubicación deseada
                    Cell cellInsp = row.getCell(5); // Ajusta 'columnaNoOrden' según la ubicación deseada
                    Cell cellTurno = row.getCell(6); // Ajusta 'columnaNoOrden' según la ubicación deseada
                    Cell cellDisp = row.getCell(7); // Ajusta 'columnaNoOrden' según la ubicación deseada

                    if (cellNoOrden == null) {
                        cellNoOrden = row.createCell(2);
                    }

                    if (cellTamLote == null) {
                        cellTamLote = row.createCell(3);
                    }

                    if (cellTamMta == null) {
                        cellTamMta = row.createCell(4);
                    }

                    if (cellInsp == null) {
                        cellInsp = row.createCell(5);
                    }

                    if (cellTurno == null) {
                        cellTurno = row.createCell(6);
                    }

                    if (cellDisp == null) {
                        cellDisp = row.createCell(7);
                    }

                    String valorActual = cellNoOrden.getStringCellValue();
                    String valorActual2 = cellTamLote.getStringCellValue();
                    String valorActual3 = cellTamMta.getStringCellValue();
                    String valorActual4 = cellInsp.getStringCellValue();
                    String valorActual5 = cellTurno.getStringCellValue();
                    String valorActual6 = cellDisp.getStringCellValue();

                    // Concatenar el valor actual con el nuevo valor de 'noOrden' (manteniendo los saltos de línea)
                    if (!valorActual.isEmpty()) {
                        noOrdenConcatenado = valorActual + "\n" + noOrdenConcatenado;
                    }

                    if (!valorActual2.isEmpty()) {
                        tamLoteConcatenado = valorActual2 + "\n" + tamLoteConcatenado;
                    }

                    if (!valorActual3.isEmpty()) {
                        tamMtaConcatenado = valorActual3 + "\n" + tamMtaConcatenado;
                    }

                    if (!valorActual4.isEmpty()) {
                        inspConcatenado = valorActual4 + "\n" + inspConcatenado;
                    }

                    if (!valorActual5.isEmpty()) {
                        turnoConcatenado = valorActual5 + "\n" + turnoConcatenado;
                    }

                    if (!valorActual6.isEmpty()) {
                        dispConcatenado = valorActual6 + "\n" + dispConcatenado;
                    }

                    // Crear un estilo para la celda
                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setWrapText(true); // Esto permite que el texto se ajuste automáticamente
                    Font font = workbook.createFont();
                    font.setFontHeightInPoints((short) 6); // Cambia el tamaño del texto aquí (14 puntos en este caso)
                    cellStyle.setFont(font);

                    // Aplica el estilo a la celda
                    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
                    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                    cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

                    cellNoOp.setCellStyle(cellStyle);
                    cellFecha.setCellStyle(cellStyle);
                    cellNoOrden.setCellStyle(cellStyle);
                    cellTamLote.setCellStyle(cellStyle);
                    cellTamMta.setCellStyle(cellStyle);
                    cellInsp.setCellStyle(cellStyle);
                    cellTurno.setCellStyle(cellStyle);
                    cellDisp.setCellStyle(cellStyle);

                    // Colocar el valor concatenado en la celda correspondiente
                    cellNoOrden.setCellValue(noOrdenConcatenado);
                    cellTamLote.setCellValue(tamLoteConcatenado);
                    cellTamMta.setCellValue(tamMtaConcatenado);
                    cellInsp.setCellValue(inspConcatenado);
                    cellTurno.setCellValue(turnoConcatenado);
                    cellDisp.setCellValue(dispConcatenado);

                }
            }
        }
    }

    private List<AceptacionPc2> buscarObjetoEnAp2mPorFecha(String fecha, String componente) throws SQLException {
        String sqlConsulta = "SELECT noOrden, fecha, tamLote, tamMta, inspector, turno, disp\n"
                + "FROM aceptacionpc2\n"
                + "WHERE fecha = ? AND componente = ?\n"
                + "GROUP BY noOrden, fecha, tamLote, tamMta, inspector, turno, disp";
        List<AceptacionPc2> ap2c = new ArrayList<>();
        try (PreparedStatement consulta = conexion.prepareStatement(sqlConsulta)) {
            consulta.setString(1, fecha);
            consulta.setString(2, componente);
            ResultSet resultado = consulta.executeQuery();
            while (resultado.next()) {
                apc2 = new AceptacionPc2("", "",
                        resultado.getString("fecha"), resultado.getString("noOrden"), resultado.getString("tamLote"),
                        resultado.getString("tamMta"), resultado.getString("inspector"), resultado.getString("turno"), resultado.getString("disp"));
                ap2c.add(apc2);
            }
        }
        return ap2c;
    }

    private void eliminarRegistro(String fecha, String noOp, String variable, String especificacion, String valor) {
        this.aps.eliminarpc3(conexion, fecha, noOp, variable, especificacion, valor); // Se llama el método de eliminar
        RetencionDimensional.this.dispose(); // Se liberan los recursos de la ventana
        JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS"); // Se muestra el mensaje de confirmación de la eliminación
        RetencionDimensional.this.dispose(); // Se liberan los recursos de la interfaz
        try {
            RetencionDimensional apGUI = new RetencionDimensional(usr, apc1); // Se crea una instancia de la interfaz gráfica
            apGUI.setVisible(true); // Se hace visible la ventana
            apGUI.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RetencionDimensional.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
