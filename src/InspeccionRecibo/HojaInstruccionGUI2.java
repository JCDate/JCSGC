package InspeccionRecibo;

import Modelos.AnchoLargoM;
import Modelos.DatosIRM;
import Modelos.InspeccionReciboM;
import Servicios.ExcelFormato;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.InspeccionReciboServicio;
import Servicios.SQL;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import Servicios.AnchoLargoServicio;
import Servicios.HojaInstruccionServicio;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 *
 * @author JC
 */
public class HojaInstruccionGUI2 extends javax.swing.JFrame {

    Usuarios usr; // Instancia de la clase Usuarios
    public String rutaArchivo, patron;
    final String SQL_CONSULTA_INSPECTORES = "SELECT nombre FROM usuarios WHERE id_tipo=?";
    private Cell celdaFecha, cellObservacionesMP, cellObservaciones, cellDescripcionMP, cellCalibreLamina, cellInspector, cellAR, cellAR2, cellNoHoja;
    private DatosIRM dirm; // Se crea la instancia de datosIRM
    Connection conexion; // Se define un objeto de la clase Connection
    SQL s = new SQL();
    ExcelFormato formato = new ExcelFormato(); // Clase para aplicar formatos de Excel
    private InspeccionReciboServicio irs = new InspeccionReciboServicio(); // Instancia de la clase InspeccionReciboServicio
    FileOutputStream fos;
    private HojaInstruccionServicio his = new HojaInstruccionServicio();
    FileInputStream fileInputStream;
    XSSFWorkbook workbook;
    InspeccionReciboM irm;

    private List al = new ArrayList<>();
    private AnchoLargoServicio als;

    /**
     * Creates new form hojaInstruccion
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public HojaInstruccionGUI2() throws SQLException, ClassNotFoundException {
        initComponents(); // Inicialización de Componentes
        inicializarVentanaYComponentes(); // Se inicializan los componente principales
    }

    public HojaInstruccionGUI2(Usuarios usr, String rutaArchivo, InspeccionReciboM irm) throws SQLException, ClassNotFoundException, IOException {
        inicializarVentanaYComponentes();
        this.usr = usr; // Se asigna el valor de la instancia de usuarios
        this.irm = irm;
        this.rutaArchivo = rutaArchivo; // Se asigna el valor de la instancia de InspeccionReciboM
        this.dirm = new DatosIRM(); // Se crea la instancia de la clase DatosIRM
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton
        this.als = new AnchoLargoServicio(tblAnchoLargo);
        fileInputStream = new FileInputStream(new File(rutaArchivo));
        workbook = new XSSFWorkbook(fileInputStream);
        leerYModificarExcel();
    }

    public final void inicializarVentanaYComponentes() throws SQLException, ClassNotFoundException {
        initComponents(); // Inicialización de Componentes
        this.setResizable(false); // Se define que no se puede redimensionar
        this.setDefaultCloseOperation(0); // Se deshabilita el boton de cerrar de la ventana
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
    }

    @Override
    public Image getIconImage() { // Método para obtener y cambiar el icono de la aplicación en la barra del titulo
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("jc/img/jc.png")); // Se obtiene la imagen que se quiere poner como icono de la barra 
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
        btnRegresar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtHojaInstrucciones = new swing.TextField();
        lblDescripcionMPR = new javax.swing.JLabel();
        lblCalibreLamina = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxNombreInspector = new swing.ComboBoxSuggestion();
        cbxObservacionesMP = new swing.ComboBoxSuggestion();
        txtObservacionesRD = new swing.TextField();
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));
        txtDescripcionMP = new swing.TextField();
        txtToleranciaLamina = new swing.TextField();

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
        jPanel1.add(lblObeservacionesResultados, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 410, 40));

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
        jPanel1.add(lblDisposicionMateriaPrima, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 410, -1, -1));

        chkAceptacion.setText("Aceptación");
        chkAceptacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAceptacionActionPerformed(evt);
            }
        });
        jPanel1.add(chkAceptacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 530, 100, 40));

        chkRechazo.setText("Rechazo");
        chkRechazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRechazoActionPerformed(evt);
            }
        });
        jPanel1.add(chkRechazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 530, 90, 40));

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
        lblNota.setText("<html><strong>NOTA:</strong> Si al menos un aspecto no se cumple, se rechaza el material y  actua conforme al procedimiento <strong>MP-7.10</strong></html>");
        jPanel1.add(lblNota, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 370, -1));

        btnRegresar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        btnRegresar.setBorderPainted(false);
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));

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
        jPanel1.add(cbxObservacionesMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 430, 440, 40));
        jPanel1.add(txtObservacionesRD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 420, 40));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 560, 150, 50));
        jPanel1.add(txtDescripcionMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 350, 40));
        jPanel1.add(txtToleranciaLamina, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 350, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkAceptacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAceptacionActionPerformed
        chkRechazo.setSelected(false); // Se cambia el estado del checkbox de rechazo
    }//GEN-LAST:event_chkAceptacionActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        HojaInstruccionGUI2.this.dispose(); // Se liberan los recursos de la interfaz
        try {
            InspeccionReciboGUI irGUI = new InspeccionReciboGUI(usr); // Se crea una instancia de la interfaz gráfica
            irGUI.setVisible(true); // Se hace visible la ventana
            irGUI.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema 
        } catch (SQLException | ClassNotFoundException ex) { // Atrapa los errores definidos
            Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void chkRechazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRechazoActionPerformed
        chkAceptacion.setSelected(false); // Se cambia el estado del checkbox de aceptación
    }//GEN-LAST:event_chkRechazoActionPerformed

    private void cbxNombreInspectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNombreInspectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxNombreInspectorActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {

            this.dispose();
            try {
                ModificarIr modificar = new ModificarIr(irm, usr, guardarCambios(rutaArchivo)); // Se crea la instancia de la clase ModificarIr
                modificar.setVisible(true); // Se muestra visible la ventana 
                modificar.setLocationRelativeTo(null); // Se coloca en el centro de la pantalla
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException | ParseException ex) {
            Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void leerYModificarExcel() throws IOException {
        fileInputStream = new FileInputStream(new File(rutaArchivo));
        workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0); // Obtener la primera hoja

        // Se obtienen los datos y se muestran
        Row fila1 = sheet.getRow(5);
        cellNoHoja = fila1.getCell(2);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String codigoHoja = s.getCodigoHoja(irs.SELECT_NO_HOJA_INSTRUCCION_SQL, year);
        String nuevoCodigo = s.obtenerSiguiente(codigoHoja, String.valueOf(year));
        txtHojaInstrucciones.setText(nuevoCodigo); // Se genera automaticamente el no. de hoja de Instrucción

        // Patrón de formato de fecha que coincide con tu cadena
        patron = "d 'de' MMMM 'de' yyyy";

        // Crear un objeto SimpleDateFormat con el patrón
        SimpleDateFormat sdf = new SimpleDateFormat(patron);

        celdaFecha = fila1.getCell(6);
        try {
            dchFechaInspeccion.setDate(sdf.parse(celdaFecha.getStringCellValue()));
        } catch (ParseException ex) {
            Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(Level.SEVERE, null, ex);
        }

        Row fila2 = sheet.getRow(9);
        cellDescripcionMP = fila2.getCell(1);

        Row fila3 = sheet.getRow(13);
        cellCalibreLamina = fila3.getCell(1);

        Row fila5 = sheet.getRow(34);
        cellObservaciones = fila5.getCell(1);

        Row fila6 = sheet.getRow(42);
        cellObservacionesMP = fila6.getCell(1);

        Row fila7 = sheet.getRow(60);
        cellInspector = fila7.getCell(1);

        cbxNombreInspector.setSelectedItem(cellInspector.getStringCellValue());

        txtDescripcionMP.setText(cellDescripcionMP.getStringCellValue());
        txtToleranciaLamina.setText(cellCalibreLamina.getStringCellValue());
        txtObservacionesRD.setText(cellObservaciones.getStringCellValue());
        cbxObservacionesMP.setSelectedItem(cellObservacionesMP.getStringCellValue());

        // Tabla Ancho/Largo
        int numFila = 20;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                Row fila4 = sheet.getRow(numFila);
                Cell cellAnchoLargo = fila4.getCell(1);
                tblAnchoLargo.setValueAt(cellAnchoLargo.getStringCellValue(), i, j);
            }
            numFila++;
        }

        Row fila8 = sheet.getRow(39);
        cellAR = fila8.getCell(2);
        cellAR2 = fila8.getCell(7);

        if (cellAR.getStringCellValue().equalsIgnoreCase("√")) {
            chkAceptacion.setSelected(true);
            chkRechazo.setSelected(false);
        } else {
            chkAceptacion.setSelected(false);
            chkRechazo.setSelected(true);
        }
    }

    public String guardarCambios(String nuevaRutaArchivo) throws FileNotFoundException, IOException, ParseException {
        // Agregar Campos
        String fechaInspeccion = "";
        if (dchFechaInspeccion.getDate() != null) {
            Date selectedDate = dchFechaInspeccion.getDate(); // Obtén la fecha seleccionada
            SimpleDateFormat dateFormat = new SimpleDateFormat(patron); // Crea un objeto SimpleDateFormat para el formato deseado
            String formattedDate = dateFormat.format(selectedDate); // Formatea la fecha y guárdala en una variable String
            fechaInspeccion = formattedDate; // Se almacenan los datos capturados en las variable
        }

        celdaFecha.setCellValue(fechaInspeccion); // Se guarda la fecha
        cellObservacionesMP.setCellValue(cbxObservacionesMP.getSelectedItem().toString()); // Se guarda los comentarios del apartado "Disposición de Materia Prima"
        cellObservaciones.setCellValue(txtObservacionesRD.getText()); // Se guarda los comentarios del apartado "Observaciones a los resultados Dimensionales

        cellDescripcionMP.setCellValue(txtDescripcionMP.getText()); // Se Captura la descripción de materia prima
        cellCalibreLamina.setCellValue(txtToleranciaLamina.getText()); // Se Captura el calibre de la lamina
        cellInspector.setCellValue(cbxNombreInspector.getSelectedItem().toString());

        if (chkAceptacion.isSelected()) {
            cellAR.setCellValue("√");
            cellAR2.setCellValue("");
        } else {
            cellAR.setCellValue("");
            cellAR2.setCellValue("√");
        }

        String[] partes = txtHojaInstrucciones.getText().split("/"); // Se divide el valor de NoHoja para solo obtener el número
        String numeroStr = partes[1];
        int numero = Integer.parseInt(numeroStr);
        if (numero < 10) { // Eliminar ceros a la izquierda
            numeroStr = String.valueOf(numero);
        }
        int numeroFila = 20; // Tabla Ancho/Largo
        try {
            Sheet sheet = workbook.getSheetAt(0); // Obtener la primera hoja
            int idIR = his.getId(conexion, this.irm); // Se obtiene el id de la hoja de instrucción
            al = als.recuperarTodas(idIR, dirm.getAnchoLargo());

            for (int i = 0; i < al.size(); i++) { // Se imprimen los valores de ancho y largo del componente
                AnchoLargoM medida = (AnchoLargoM) al.get(i);
                Row row = sheet.getRow(numeroFila);
                if (row == null) {
                    row = sheet.createRow(numeroFila);
                }

                Cell cellAncho = row.createCell(1); // Celda para el ancho
                cellAncho.setCellValue(medida.getAncho());

                Cell cellLargo = row.createCell(2); // Celda para el largo
                cellLargo.setCellValue(medida.getLargo());

                numeroFila++;

                //Aplicación del formato
                cellAncho.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
                cellLargo.setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(Level.SEVERE, null, ex);
        }

        Date selectedDate = dchFechaInspeccion.getDate(); // Obtén la fecha seleccionada
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy"); // Crea un objeto SimpleDateFormat para el formato deseado
        String formattedDate = dateFormat.format(selectedDate); // Formatea la fecha y guárdala en una variable String
        cellNoHoja.setCellValue(numeroStr); // Cambiar el nombre de la Hoja con el número previamente obtenido
        nuevaRutaArchivo = "HI-" + numeroStr + "-" + formato.eliminarSeparadores(formattedDate) + ".xlsx ";  // Ruta del nuevo archivo de Excel

        try (FileOutputStream fos = new FileOutputStream(nuevaRutaArchivo)) {
            workbook.write(fos);
        }

        return nuevaRutaArchivo;
    }

    public FileOutputStream getFos() {
        return fos;
    }

    public String getRutaArchivo() {
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
            java.util.logging.Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> { // Crea y muestra el form
            try {
                new HojaInstruccionGUI2().setVisible(true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxNombreInspector;
    private javax.swing.JComboBox<String> cbxObservacionesMP;
    private javax.swing.JCheckBox chkAceptacion;
    private javax.swing.JCheckBox chkRechazo;
    private com.toedter.calendar.JDateChooser dchFechaInspeccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JTextField txtDescripcionMP;
    private javax.swing.JTextField txtHojaInstrucciones;
    private javax.swing.JTextField txtObservacionesRD;
    private javax.swing.JTextField txtToleranciaLamina;
    // End of variables declaration//GEN-END:variables
}
