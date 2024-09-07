package InspeccionRecibo;

import Modelos.AnchoLargoM;
import Modelos.DatosIRM;
import Modelos.InspeccionReciboM;
import Servicios.ExcelFormato;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.InspeccionReciboServicio;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import Servicios.AnchoLargoServicio;
import Servicios.GeneradorExcel;
import Servicios.RugosidadDurezaServicio;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class HojaInstruccionGUI2 extends javax.swing.JFrame {

    // Usuario y Conexión a la base de datos
    private Usuarios usuario;
    private Connection conexion;

    // Lista de proveedores
    List<String> listaInspectores = new ArrayList<>();

    // Servicios y Utilidades
    private InspeccionReciboServicio irs = new InspeccionReciboServicio();
    private RugosidadDurezaServicio rds = new RugosidadDurezaServicio();
    private AnchoLargoServicio als = new AnchoLargoServicio();
    private GeneradorExcel excel = new GeneradorExcel();
    private ExcelFormato formato = new ExcelFormato();

    // Objetos para la manipulación de los datos
    private InspeccionReciboM inspeccionRecibo;
    private DatosIRM dirm = new DatosIRM();

    // Objetos para editar el archivo Excel
    private XSSFWorkbook workbook;
    private Sheet hoja1;

    // Objetos para el formato de la fecha y para guardar la ruta del archivo Excel
    private String formatoFecha;
    private String rutaArchivo;

    public HojaInstruccionGUI2() throws SQLException, ClassNotFoundException {
        inicializarVentanaYComponentes();
    }

    public HojaInstruccionGUI2(Usuarios usuario, String rutaArchivo, InspeccionReciboM inspeccionRecibo) throws SQLException, ClassNotFoundException, IOException {
        this.usuario = usuario;
        this.inspeccionRecibo = inspeccionRecibo;
        this.rutaArchivo = rutaArchivo;
        inicializarVentanaYComponentes();
    }

    @Override
    public Image getIconImage() { // Método para obtener y cambiar el icono de la aplicación en la barra del titulo
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
        jPanel1.add(lblDisposicionMateriaPrima, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, -1, -1));

        chkAceptacion.setText("Aceptación");
        chkAceptacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAceptacionActionPerformed(evt);
            }
        });
        jPanel1.add(chkAceptacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 580, 100, 40));

        chkRechazo.setText("Rechazo");
        chkRechazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRechazoActionPerformed(evt);
            }
        });
        jPanel1.add(chkRechazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 580, 90, 40));

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
        jPanel1.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, -1, -1));

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

        jPanel1.add(cbxNombreInspector, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 270, 30));

        cbxObservacionesMP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MATERIAL DENTRO DE ESPECIFICACIÓN", "MATERIAL FUERA DE ESPECIFICACIÓN (CUARENTENA)", "MATERIAL DAÑADO DE ESPECIFICACIÓN (CUARENTENA)" }));
        jPanel1.add(cbxObservacionesMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 440, 40));
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
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 580, 150, 50));
        jPanel1.add(txtDescripcionMP, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 350, 40));
        jPanel1.add(txtToleranciaLamina, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 350, 40));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 400, 110));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chkAceptacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAceptacionActionPerformed
        chkRechazo.setSelected(false);
    }//GEN-LAST:event_chkAceptacionActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        cerrarVentana();
        irs.abrirInspeccionReciboGUI(usuario);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void chkRechazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRechazoActionPerformed
        chkAceptacion.setSelected(false);
    }//GEN-LAST:event_chkRechazoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            cerrarVentana();
            irs.abrirModificarIrGUI(inspeccionRecibo, usuario, guardarCambios(rutaArchivo));
        } catch (IOException | ParseException ex) {
            Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(Level.SEVERE, null, ex);
            irs.manejarExcepcion("Error al abrir ModificarGUI", ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void chkHoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHoyActionPerformed
        if (chkHoy.isSelected()) {
            dchFechaInspeccion.setDate(new java.util.Date());
        } else {
            dchFechaInspeccion.setDate(null);
        }
    }//GEN-LAST:event_chkHoyActionPerformed

    public final void inicializarVentanaYComponentes() throws SQLException, ClassNotFoundException {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.conexion = Conexion.getInstance().getConnection();

        listaInspectores = irs.recuperarInspectores(conexion);
        listaInspectores.forEach(cbxNombreInspector::addItem);

        als.setTbl(tblAnchoLargo);
        rds.setTbl(tblRugosidadDureza);

        try {
            leerDatosExcel();
        } catch (IOException ex) {
            Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(Level.SEVERE, null, ex);
            irs.manejarExcepcion("Error al cargar el archivo excel: ", ex);
        }
    }

    private void leerDatosExcel() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(rutaArchivo));
        workbook = new XSSFWorkbook(fileInputStream);
        hoja1 = workbook.getSheetAt(0); // Obtener la primera hoja        

        txtHojaInstrucciones.setText(inspeccionRecibo.getNoHoja());
        formatoFecha = "d 'de' MMMM 'de' yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatoFecha);

        try {
            dchFechaInspeccion.setDate(sdf.parse(excel.getDatosCeldas(hoja1, 5, 6)));
        } catch (ParseException ex) {
            Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtDescripcionMP.setText(excel.getDatosCeldas(hoja1, 9, 1));
        txtToleranciaLamina.setText(excel.getDatosCeldas(hoja1, 13, 1));
        txtObservacionesRD.setText(excel.getDatosCeldas(hoja1, 34, 1));
        cbxObservacionesMP.setSelectedItem(excel.getDatosCeldas(hoja1, 42, 1));
        cbxNombreInspector.setSelectedItem(excel.getDatosCeldas(hoja1, 60, 1));

        // Tabla Ancho/Largo
        int numFila = 20;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                Row filaAnchoLargo = hoja1.getRow(numFila);
                Cell cellAnchoLargo = filaAnchoLargo.getCell(2);
                tblRugosidadDureza.setValueAt(cellAnchoLargo.getStringCellValue(), i, j);
            }
            numFila++;
        }

        // Crea la tabla de destino
        String[][] table = new String[5][2];

        // Lee las filas y columnas de la tabla de Excel
        for (int i = 0; i < 2; i++) {
            Row row = hoja1.getRow(i + 42); // 42 y 43 son las filas que deseas leer
            for (int j = 0; j < 5; j++) {
                Cell cell = row.getCell(j + 1); // 1-5 son las columnas que deseas leer
                String value = cell.getStringCellValue();
                table[j][i] = value; // Asigna el valor a la tabla de destino
            }
        }

        // Imprime la tabla de destino
        for (int i = 0; i < 5; i++) {
            System.out.println(table[i][0] + " " + table[i][1]);
        }

    }

    public String guardarCambios(String nuevaRutaArchivo) throws FileNotFoundException, IOException, ParseException {
        String fechaInspeccion = "";
        if (dchFechaInspeccion.getDate() != null) {
            Date selectedDate = dchFechaInspeccion.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatoFecha);
            String formattedDate = dateFormat.format(selectedDate);
            fechaInspeccion = formattedDate;
        }

        excel.setDatosCeldas(hoja1, 5, 6, fechaInspeccion); // Fecha de Inspección
        excel.setDatosCeldas(hoja1, 9, 1, txtDescripcionMP.getText()); // DescripciónMP
        excel.setDatosCeldas(hoja1, 13, 1, txtToleranciaLamina.getText()); // Calibre Lamina
        excel.setDatosCeldas(hoja1, 34, 1, txtObservacionesRD.getText()); // ObservacionesRD
        excel.setDatosCeldas(hoja1, 42, 1, cbxObservacionesMP.getSelectedItem().toString()); // ObservacionesMP
        excel.setDatosCeldas(hoja1, 60, 1, cbxNombreInspector.getSelectedItem().toString()); // Inspector

        if (chkAceptacion.isSelected()) {
            excel.setDatosCeldas(hoja1, 39, 2, "√"); // Aceptación
            excel.setDatosCeldas(hoja1, 39, 7, ""); // Rechazo
        } else {
            excel.setDatosCeldas(hoja1, 39, 2, ""); // Aceptación
            excel.setDatosCeldas(hoja1, 39, 7, "√"); // Rechazo
        }

        String[] partes = txtHojaInstrucciones.getText().split("/"); // Se divide el valor de NoHoja para solo obtener el número
        String numeroStr = partes[1];
        int numero = Integer.parseInt(numeroStr);
        if (numero < 10) {
            numeroStr = String.valueOf(numero);
        }
        int numeroFila = 20; // indice de la Tabla Ancho/Largo
        try {
            int idHojaInstruccion = irs.getIdHI(conexion, inspeccionRecibo);
            List anchoLargo = als.capturarValores(idHojaInstruccion, dirm.getAnchoLargo());

            for (int i = 0; i < anchoLargo.size(); i++) {
                AnchoLargoM medida = (AnchoLargoM) anchoLargo.get(i);
                Row row = hoja1.getRow(numeroFila);

                excel.setDatosCeldas(hoja1, numeroFila, 1, medida.getAncho()); // Ancho
                excel.setDatosCeldas(hoja1, numeroFila, 2, medida.getLargo()); // Largo

                numeroFila++;

                //Aplicación del formato
                row.getCell(1).setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
                row.getCell(2).setCellStyle(formato.estiloTblAnchoLargo(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HojaInstruccionGUI2.class.getName()).log(Level.SEVERE, null, ex);
        }

        Date fechaSeleccionada = dchFechaInspeccion.getDate();
        String fechaFormateada = irs.formatearFecha(fechaSeleccionada);
        excel.setDatosCeldas(hoja1, 5, 1, numeroStr);

        nuevaRutaArchivo = "HI-" + numeroStr + "-" + formato.eliminarSeparadores(fechaFormateada) + ".xlsx ";

        try (FileOutputStream fos = new FileOutputStream(nuevaRutaArchivo)) {
            workbook.write(fos);
        }

        return nuevaRutaArchivo;
    }

    public void cerrarVentana() {
        HojaInstruccionGUI2.this.dispose();
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
    private javax.swing.JTextField txtDescripcionMP;
    private javax.swing.JTextField txtHojaInstrucciones;
    private javax.swing.JTextField txtObservacionesRD;
    private javax.swing.JTextField txtToleranciaLamina;
    // End of variables declaration//GEN-END:variables
}
