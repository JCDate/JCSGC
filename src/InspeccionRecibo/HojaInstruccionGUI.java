/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InspeccionRecibo;

import InspeccionRecibo.AgregarIrGUI;
import InspeccionRecibo.InspeccionReciboGUI;
import Modelos.AnchoLargoM;
import Modelos.DatosIRM;
import Modelos.InspeccionReciboM;
import Modelos.RugosidadDurezaM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.SQL;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JC
 */
public class HojaInstruccionGUI extends javax.swing.JFrame {

    Usuarios usr; // Instancia de la clase Usuarios
    private InspeccionReciboM irm; // Se crea la instancia de inspeccionReciboM
    private DatosIRM dirm; // Se crea la instancia de datosIRM
    //Conexion con = new Conexion(); // Se crea la instancia de la clase Conexion
    Connection conexion; // Se define un objeto de la clase Connection
    List<AnchoLargoM> listaALM = new ArrayList<>();
    final String SQL_CONSULTA_INSPECTORES = "SELECT nombre FROM usuarios WHERE id_tipo=?";
    final String SQL_CONSULTA_CALIBRE = "SELECT DISTINCT medidas FROM calibresir WHERE calibre LIKE ?";

    /**
     * Creates new form hojaInstruccion
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public HojaInstruccionGUI() throws SQLException, ClassNotFoundException {
        initComponents(); // Inicialización de Componentes
        inicializarVentana(); // Se inicializan los componente principales
    }

    public HojaInstruccionGUI(Usuarios usr, InspeccionReciboM irm) throws SQLException, ClassNotFoundException {
        initComponents(); 
        inicializarVentana();
        this.usr = usr; 
        this.irm = irm; 
        this.dirm = new DatosIRM(); // Se crea la instancia de la clase DatosIRM
        txtHojaInstrucciones.setText(String.valueOf(irm.getNoHoja())); // Se muestra el no. que tiene la hoja de instrucción        
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton
        try {

            PreparedStatement slqNomProv = conexion.prepareStatement(SQL_CONSULTA_INSPECTORES); // Se define la consulta preparada
            slqNomProv.setInt(1, 5);
            ResultSet registro = slqNomProv.executeQuery(); // Se ejecuta la consulta preparada
            while (registro.next()) { // Mientras haya información...
                String columna = registro.getString("nombre"); // Variable que almacena los diferentes nombres encontrados
                cbxNombreInspector.addItem(columna); // Se agrega al comboBox
            }
        } catch (SQLException ex) { // Se captura la excepción SQLException
            Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Obtener la fecha actual
        Calendar cal = Calendar.getInstance();
        Date fechaActual = cal.getTime();

        // Establecer la fecha actual como valor predeterminado en el JDateChooser
        dchFechaInspeccion.setDate(fechaActual);

        try {
            // Se realizan las consultas SQL
            PreparedStatement consulta2 = conexion.prepareStatement("SELECT * FROM materiaprimasc WHERE calibre LIKE ? AND descripcionMP LIKE ?");
            consulta2.setString(1, "%" + irm.getCalibre().substring(0, 2) + "%"); // Establece el valor del marcador de posición
            consulta2.setString(2, "%" + irm.getpLamina() + "%"); // Establece el valor del marcador de posición
            ResultSet resultado2 = consulta2.executeQuery();

            while (resultado2.next()) {
                cbxDescripcionMP.addItem(resultado2.getString("descripcionMP"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Surgio un error al cargar la información");
        }

        try {
            // Se realizan las consultas SQL
            PreparedStatement consulta2 = conexion.prepareStatement(SQL_CONSULTA_CALIBRE);
            consulta2.setString(1, "%" + irm.getCalibre().substring(0, 2) + "%"); // Establece el valor del marcador de posición
            ResultSet resultado2 = consulta2.executeQuery();

            while (resultado2.next()) {

                cbxLamina.addItem(resultado2.getString("medidas"));
            }
        } catch (SQLException e) {
            // Muestra información detallada del error
            System.out.println("error");
        }
       
    }

    public HojaInstruccionGUI(Usuarios usr, InspeccionReciboM irm, DatosIRM dirm, List<AnchoLargoM> anchoLargoList, List<RugosidadDurezaM> rdList) throws SQLException, ClassNotFoundException {
        initComponents();
        inicializarVentana();
        this.usr = usr; 
        this.irm = irm; 
        this.dirm = dirm; 
        txtHojaInstrucciones.setText(String.valueOf(irm.getNoHoja()));   
        cbxDescripcionMP.setSelectedItem(dirm.getDescripcionMP());
        cbxLamina.setSelectedItem(dirm.getCalibreLamina());

        try {
            PreparedStatement slqNomProv = conexion.prepareStatement(SQL_CONSULTA_INSPECTORES); 
            slqNomProv.setInt(1, 5);
            ResultSet registro = slqNomProv.executeQuery(); 
            while (registro.next()) { 
                String columna = registro.getString("nombre");
                cbxNombreInspector.addItem(columna);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fechaInspeccion = formato.parse(dirm.getFechaInspeccion());
            dchFechaInspeccion.setDate(fechaInspeccion);
        } catch (ParseException ex) {
            Logger.getLogger(HojaInstruccionGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Obtener el modelo de tabla existente
        DefaultTableModel modelo = (DefaultTableModel) tblAnchoLargo.getModel();
        DefaultTableModel modelo2 = (DefaultTableModel) tblRugosidadDureza.getModel();

        // Limpiar el modelo de tabla
        modelo.setRowCount(0);
        modelo2.setRowCount(0);

        for (AnchoLargoM medida : anchoLargoList) {
            Object[] fila = {medida.getAncho(), medida.getLargo()};
            modelo.addRow(fila);
        }
        
        for (RugosidadDurezaM rd : rdList) {
            Object[] fila = {rd.getRugosidad(), rd.getDureza()};
            modelo2.addRow(fila);
        }

        if (dirm.getAceptacion() == 1) {
            chkAceptacion.setSelected(true);
            chkRechazo.setSelected(false);
        } else {
            chkAceptacion.setSelected(false);
            chkRechazo.setSelected(true);
        }
        tblAnchoLargo.setModel(modelo);
        tblRugosidadDureza.setModel(modelo2);
    }

    public final void inicializarVentana() throws SQLException, ClassNotFoundException {
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton
        this.setResizable(false); // Se define que no se puede redimensionar
        this.setDefaultCloseOperation(0); // Se deshabilita el boton de cerrar de la ventana
    }

    @Override
    public Image getIconImage() { // Método para obtener y cambiar el icono de la aplicación en la barra del titulo
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("jc/img/jc.png")); // Se obtiene la imagen que se quiere poner como icono de la barra 
        return retValue; // Se retorna la imagen
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
        lblHojaInstrucciones = new javax.swing.JLabel();
        lblObeservacionesResultados = new javax.swing.JLabel();
        lblLecturaMuestreo = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAnchoLargo = new javax.swing.JTable();
        lblDisposicionMateriaPrima = new javax.swing.JLabel();
        chkAceptacion = new swing.JCheckBoxCustom(new Color(20, 134, 255));
        chkRechazo = new swing.JCheckBoxCustom(new Color(255,0,0));
        lblJCIcono = new javax.swing.JLabel();
        lblFechaInspeccion = new javax.swing.JLabel();
        dchFechaInspeccion = new com.toedter.calendar.JDateChooser();
        lblPunto1 = new javax.swing.JLabel();
        lblInstruccionesInspeccion = new javax.swing.JLabel();
        lblPunto2 = new javax.swing.JLabel();
        lblNota = new javax.swing.JLabel();
        btnContinuar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtHojaInstrucciones = new swing.TextField();
        lblDescripcionMPR = new javax.swing.JLabel();
        lblCalibreLamina = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxNombreInspector = new swing.ComboBoxSuggestion();
        cbxObservacionesMP = new swing.ComboBoxSuggestion();
        txtObservacionesRD = new swing.TextField();
        cbxLamina = new swing.ComboBoxSuggestion();
        cbxDescripcionMP = new swing.ComboBoxSuggestion();
        chkHoy = new swing.JCheckBoxCustom(new Color(20, 134, 255));
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRugosidadDureza = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(1010, 630));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setMinimumSize(new java.awt.Dimension(1010, 630));
        jPanel1.setPreferredSize(new java.awt.Dimension(1010, 630));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHojaInstrucciones.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        lblHojaInstrucciones.setForeground(new java.awt.Color(10, 110, 255));
        lblHojaInstrucciones.setText("HOJA DE INSTRUCCIÓN");
        jPanel1.add(lblHojaInstrucciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        lblObeservacionesResultados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblObeservacionesResultados.setForeground(new java.awt.Color(76, 109, 255));
        lblObeservacionesResultados.setText("<html>OBSERVACIONES A LOS RESULTADOS DIMENSIONALES, PROPIEDADES MECÁNICAS Y REQUERIMIENTOS QUÍMICOS</html>");
        lblObeservacionesResultados.setToolTipText("");
        jPanel1.add(lblObeservacionesResultados, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 410, 40));

        lblLecturaMuestreo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLecturaMuestreo.setForeground(new java.awt.Color(76, 109, 255));
        lblLecturaMuestreo.setText("LECTURA DE MUESTREO:");
        jPanel1.add(lblLecturaMuestreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        tblAnchoLargo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ANCHO", "LARGO"
            }
        ));
        jScrollPane2.setViewportView(tblAnchoLargo);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 240, 190));

        lblDisposicionMateriaPrima.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDisposicionMateriaPrima.setForeground(new java.awt.Color(76, 109, 255));
        lblDisposicionMateriaPrima.setText("OBSERVACIONES A LA DISPOSICION DE LA MATERIA PRIMA:");
        jPanel1.add(lblDisposicionMateriaPrima, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, -1, -1));

        chkAceptacion.setText("Aceptación");
        chkAceptacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAceptacionActionPerformed(evt);
            }
        });
        jPanel1.add(chkAceptacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 550, 100, 40));

        chkRechazo.setText("Rechazo");
        chkRechazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRechazoActionPerformed(evt);
            }
        });
        jPanel1.add(chkRechazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 550, 90, 40));

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblFechaInspeccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFechaInspeccion.setForeground(new java.awt.Color(76, 109, 255));
        lblFechaInspeccion.setText("FECHA DE INSPECCIÓN: ");
        jPanel1.add(lblFechaInspeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));
        jPanel1.add(dchFechaInspeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 170, 30));

        lblPunto1.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        lblPunto1.setForeground(new java.awt.Color(76, 109, 255));
        lblPunto1.setText("<html>1.- Deberán ser revisadas las propiedades mecánicas y la<br> composición química del material de acuerdo a especificaciones<br>tecnicas establecidas contra certificado de material (<strong>CM</strong>) si estas cumplen se marca con una (✓) o en su defecto, si no cumplen, una (X) en la columna <strong>CM</strong> correspondiente.</html>");
        jPanel1.add(lblPunto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 190, 370, 90));

        lblInstruccionesInspeccion.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblInstruccionesInspeccion.setForeground(new java.awt.Color(76, 109, 255));
        lblInstruccionesInspeccion.setText("Instrucciones para la Inspección:");
        jPanel1.add(lblInstruccionesInspeccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, -1, -1));

        lblPunto2.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        lblPunto2.setForeground(new java.awt.Color(76, 109, 255));
        lblPunto2.setText("<html>2.- Se evalua el aspecto dimensional, midiendo el espesor como<br> se indica en la figura.</html>");
        jPanel1.add(lblPunto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(633, 280, 370, 30));

        lblNota.setForeground(new java.awt.Color(76, 109, 255));
        lblNota.setText("<html><strong>NOTA:</strong> Si al menos un aspecto no se cumple, se rechaza el material y <br> actua conforme al procedimiento <strong>MP-7.10</strong></html>");
        jPanel1.add(lblNota, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 370, -1));

        btnContinuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/continuar.png"))); // NOI18N
        btnContinuar.setContentAreaFilled(false);
        btnContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinuarActionPerformed(evt);
            }
        });
        jPanel1.add(btnContinuar, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 550, -1, -1));

        btnRegresar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        btnRegresar.setBorderPainted(false);
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(76, 109, 255));
        jLabel1.setText("HOJA DE INSPECCIÓN No.");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 60, -1, -1));

        txtHojaInstrucciones.setEditable(false);
        jPanel1.add(txtHojaInstrucciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 50, 170, 30));

        lblDescripcionMPR.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDescripcionMPR.setForeground(new java.awt.Color(76, 109, 255));
        lblDescripcionMPR.setText("DESCRIPCIÓN DE MATERIA PRIMA RECIBIDA: ");
        jPanel1.add(lblDescripcionMPR, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        lblCalibreLamina.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCalibreLamina.setForeground(new java.awt.Color(76, 109, 255));
        lblCalibreLamina.setText("TOLERANCIA DE LAMINA:");
        jPanel1.add(lblCalibreLamina, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(76, 109, 255));
        jLabel2.setText("INSPECTOR:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, -1, -1));

        cbxNombreInspector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNombreInspectorActionPerformed(evt);
            }
        });
        jPanel1.add(cbxNombreInspector, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 270, 30));

        cbxObservacionesMP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MATERIAL DENTRO DE ESPECIFICACIÓN", "MATERIAL FUERA DE ESPECIFICACIÓN (CUARENTENA)", "MATERIAL DAÑADO DE ESPECIFICACIÓN (CUARENTENA)" }));
        jPanel1.add(cbxObservacionesMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 440, 40));

        txtObservacionesRD.setText("SE LIBERAN ___ PIEZAS, CON UN TOTAL DE ___ KG");
        jPanel1.add(txtObservacionesRD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 390, 40));

        jPanel1.add(cbxLamina, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 360, 30));

        jPanel1.add(cbxDescripcionMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 360, -1));

        chkHoy.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        chkHoy.setForeground(new java.awt.Color(76, 109, 255));
        chkHoy.setText("HOY");
        chkHoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHoyActionPerformed(evt);
            }
        });
        jPanel1.add(chkHoy, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, -1, 30));

        tblRugosidadDureza.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "RUGOSIDAD", "DUREZA"
            }
        ));
        jScrollPane1.setViewportView(tblRugosidadDureza);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 420, 400, 110));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkAceptacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAceptacionActionPerformed
        chkRechazo.setSelected(false); // Se cambia el estado del checkbox de rechazo
    }//GEN-LAST:event_chkAceptacionActionPerformed

    private void btnContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinuarActionPerformed
        // Agregar Campos
        String fechaInspeccion = "";
        if (dchFechaInspeccion.getDate() != null) {
            Date selectedDate = dchFechaInspeccion.getDate(); // Obtén la fecha seleccionada
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Crea un objeto SimpleDateFormat para el formato deseado
            String formattedDate = dateFormat.format(selectedDate); // Formatea la fecha y guárdala en una variable String
            fechaInspeccion = formattedDate; // Se almacenan los datos capturados en las variable
        }

        dirm.setFechaInspeccion(fechaInspeccion); // Se guarda la fecha
        dirm.setObsMP(cbxObservacionesMP.getSelectedItem().toString()); // Se guarda los comentarios del apartado "Disposición de Materia Prima"
        dirm.setObservacionesRD(txtObservacionesRD.getText()); // Se guarda los comentarios del apartado "Observaciones a los resultados Dimensionales
        dirm.setAceptacion(chkAceptacion.isSelected() ? 1 : 0); // Si el checkbox de Aceptación es seleccionado, el valor de aceptación es 1, sino, es 0
        dirm.setNoHoja(txtHojaInstrucciones.getText()); // Se Captura el No. de Hoja de Instrucción
        dirm.setDescripcionMP(cbxDescripcionMP.getSelectedItem().toString()); // Se Captura la descripción de materia prima
        dirm.setCalibreLamina(cbxLamina.getSelectedItem().toString()); // Se Captura el calibre de la lamina
        dirm.setInspector(cbxNombreInspector.getSelectedItem().toString());

        /* SQL s = new SQL(); // Se crea la instancia de la clase SQL para que el idAnchoLargo se genere automáticamente
        int codigo = s.autoIncremento("SELECT MAX(idAnchoLargo) FROM datosir"); // Se obitene el máximo valor del campo idAnchoLargo y se le aumenta 1

        dirm.setAnchoLargo(codigo); // Se guarda el valor capturado.    */
        HojaInstruccionGUI.this.dispose(); // Se liberan los recursos de la ventana

        try {
            EspecificacionesGUI ir = new EspecificacionesGUI(usr, this.dirm, this.irm, tblAnchoLargo, tblRugosidadDureza); // Se crea la instancia de la clase EspecificacionesGUI y se mandan los objetos de las clases  datosIRM, inspeccionReciboM y AnchoLargoM
            ir.setVisible(true); // Se muestra visible la ventana
            ir.setLocationRelativeTo(null); // La ventana se muestra al centro de la pantalla
        } catch (SQLException | ClassNotFoundException ex) { // Atrapa los errores definidos
            Logger.getLogger(HojaInstruccionGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnContinuarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        HojaInstruccionGUI.this.dispose(); // Se liberan los recursos de la interfaz
        try {
            InspeccionReciboGUI irGUI = new InspeccionReciboGUI(usr); // Se crea una instancia de la interfaz gráfica
            irGUI.setVisible(true); // Se hace visible la ventana
            irGUI.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema 
        } catch (SQLException | ClassNotFoundException ex) { // Atrapa los errores definidos
            Logger.getLogger(HojaInstruccionGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void chkRechazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRechazoActionPerformed
        chkAceptacion.setSelected(false); // Se cambia el estado del checkbox de aceptación
    }//GEN-LAST:event_chkRechazoActionPerformed

    private void cbxNombreInspectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNombreInspectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxNombreInspectorActionPerformed

    private void chkHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHoyActionPerformed
        if (chkHoy.isSelected()) {
            dchFechaInspeccion.setDate(new java.util.Date());
        } else {
            dchFechaInspeccion.setDate(null);
        }
    }//GEN-LAST:event_chkHoyActionPerformed

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
            java.util.logging.Logger.getLogger(HojaInstruccionGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> { // Crea y muestra el form
            try {
                new HojaInstruccionGUI().setVisible(true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(HojaInstruccionGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnContinuar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxDescripcionMP;
    private javax.swing.JComboBox<String> cbxLamina;
    private javax.swing.JComboBox<String> cbxNombreInspector;
    private javax.swing.JComboBox<String> cbxObservacionesMP;
    private javax.swing.JCheckBox chkAceptacion;
    private javax.swing.JCheckBox chkHoy;
    private javax.swing.JCheckBox chkRechazo;
    private com.toedter.calendar.JDateChooser dchFechaInspeccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCalibreLamina;
    private javax.swing.JLabel lblDescripcionMPR;
    private javax.swing.JLabel lblDisposicionMateriaPrima;
    private javax.swing.JLabel lblFechaInspeccion;
    private javax.swing.JLabel lblHojaInstrucciones;
    private javax.swing.JLabel lblInstruccionesInspeccion;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblLecturaMuestreo;
    private javax.swing.JLabel lblNota;
    private javax.swing.JLabel lblObeservacionesResultados;
    private javax.swing.JLabel lblPunto1;
    private javax.swing.JLabel lblPunto2;
    private javax.swing.JTable tblAnchoLargo;
    private javax.swing.JTable tblRugosidadDureza;
    private javax.swing.JTextField txtHojaInstrucciones;
    private javax.swing.JTextField txtObservacionesRD;
    // End of variables declaration//GEN-END:variables
}
