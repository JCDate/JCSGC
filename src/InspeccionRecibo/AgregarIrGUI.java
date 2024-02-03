package InspeccionRecibo;

import Modelos.InspeccionReciboM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.ContadorAnual;
import Servicios.InspeccionReciboServicio;
import Servicios.SQL;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author JC
 */
public class AgregarIrGUI extends javax.swing.JFrame {

    private final InspeccionReciboServicio irs = new InspeccionReciboServicio(); // Instancia de la clase InspeccionReciboServicio
    private final InspeccionReciboM irm; // Instancia de la clase InspeccionReciboM

    String rutaArchivoCertificado = ""; // Variable que guarda la ruta absoluta del archivo de certificado
    String rutaArchivoFactura = ""; // Variable que guarda la ruta absoluta del archivo de la factura
    String rutaArchivoHojaInstruccion = ""; // Variable que guarda la ruta absoluta del archivo de la hoja Instrucción

    Usuarios usr; // Instancia de la clase usuarios con los datos del usuario que inicio Sesión
    Connection conexion; // Objeto de tipo Connection para realizar la conexión a la bd

    ContadorAnual contador = new ContadorAnual();

    /**
     * Creates new form AgregarIr
     *
     * @throws java.lang.ClassNotFoundException
     */
    public AgregarIrGUI() throws ClassNotFoundException { // Constructor por defecto
        initComponentes();
        this.irm = new InspeccionReciboM(); // Se crea una nueva instancia de la clase InspeccionReciboM
        try {
            Statement slqNombreProveedores = conexion.createStatement(); // Se define la consulta preparada
            ResultSet registro = slqNombreProveedores.executeQuery(this.irs.SELECT_NOMBRE_PROVEEDORES_SQL); // Se consultan los nombres de los proveedores 
            while (registro.next()) { //Mientras haya información...
                String columna = registro.getString("nombre"); //Variable que almacena los diferentes nombres encontrados
                cbxProveedor.addItem(columna); // Se agrega al checkBox
            }
        } catch (SQLException ex) { // Se captura la excepción SQLException
            Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public AgregarIrGUI(Usuarios usr) throws ClassNotFoundException { // Constructor
        initComponentes(); // Inicialización de Componentes
        this.irm = new InspeccionReciboM(); // Se crea una nueva instancia de la clase InspeccionReciboM
        try {
            Statement slqNombreProveedor = conexion.createStatement(); // Se define la consulta preparada
            ResultSet registro = slqNombreProveedor.executeQuery(this.irs.SELECT_NOMBRE_PROVEEDORES_SQL); // Se consultan los nombres de los proveedores 
            // DISTINCT sirve para evitar que se muestren resultados duplicados
            while (registro.next()) { // Mientras haya información...
                String columna = registro.getString("nombre"); // Variable que almacena los diferentes nombres encontrados
                cbxProveedor.addItem(columna); // Se agrega al checkBox
            }
        } catch (SQLException ex) { // Se captura la excepción SQLException
            Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        SQL s = new SQL();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String codigoHoja = s.getCodigoHoja(this.irs.SELECT_NO_HOJA_INSTRUCCION_SQL, year);
        String nuevoCodigo = s.obtenerSiguiente(codigoHoja, String.valueOf(year));
        txtNoHoja.setText(nuevoCodigo); // Se genera automaticamente el no. de hoja de Instrucción
    }

    @Override
    public Image getIconImage() { // Método para obtener y cambiar el icono de la aplicación en la barra del titulo
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("jc/img/jc.png")); // Se obtiene la imagen que se quiere poner como icono de la barra 
        return retValue;
    }

    public final void initComponentes() {
        initComponents(); // Inicialización de componente
        this.setResizable(false); // Se define que no se puede redimensionar la ventana 
        this.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema 
        this.setDefaultCloseOperation(0); // Deshabilita el boton de cerrar de la venta
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        lblFechaFactura = new javax.swing.JLabel();
        lblProveedor = new javax.swing.JLabel();
        lblNoPedido = new javax.swing.JLabel();
        lblCalibre = new javax.swing.JLabel();
        lblNoRollo = new javax.swing.JLabel();
        lblPzKg = new javax.swing.JLabel();
        lblEstatus = new javax.swing.JLabel();
        lblHojaInstruccion = new javax.swing.JLabel();
        lblPresentacionLamina = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));
        lblNoFactura = new javax.swing.JLabel();
        dchFechaFactura = new com.toedter.calendar.JDateChooser();
        btnAgregarCertificado = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        lblJCIcono = new javax.swing.JLabel();
        lblAgregar = new javax.swing.JLabel();
        btnAgregarFactura = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        txtNoRollo = new swing.TextField();
        txtPzKg = new swing.TextField();
        txtNoFactura = new swing.TextField();
        txtCalibre = new swing.TextField();
        txtNoPedido = new swing.TextField();
        txtNoHoja = new swing.TextField();
        txtNombreCertificado = new swing.TextField();
        txtNombreFactura = new swing.TextField();
        btnAgregarHojaInstruccion = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        txtNombreHojaInstruccion = new swing.TextField();
        cbxPresentacionLamina = new swing.ComboBoxSuggestion();
        cbxProveedor = new swing.ComboBoxSuggestion();
        cbxEstatus = new swing.ComboBoxSuggestion();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(830, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setToolTipText("");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblFechaFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFechaFactura.setForeground(new java.awt.Color(76, 109, 255));
        lblFechaFactura.setText("FECHA DE FACTURA:");
        jPanel1.add(lblFechaFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 160, -1));

        lblProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblProveedor.setForeground(new java.awt.Color(76, 109, 255));
        lblProveedor.setText("PROVEEDOR:");
        jPanel1.add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 100, -1));

        lblNoPedido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoPedido.setForeground(new java.awt.Color(76, 109, 255));
        lblNoPedido.setText("NO. PEDIDO:");
        jPanel1.add(lblNoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 190, -1, -1));

        lblCalibre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCalibre.setForeground(new java.awt.Color(76, 109, 255));
        lblCalibre.setText("CALIBRE:");
        jPanel1.add(lblCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 140, -1));

        lblNoRollo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoRollo.setForeground(new java.awt.Color(76, 109, 255));
        lblNoRollo.setText("NO. ROLLO:");
        jPanel1.add(lblNoRollo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 210, -1));

        lblPzKg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPzKg.setForeground(new java.awt.Color(76, 109, 255));
        lblPzKg.setText("PZ/Kg:");
        jPanel1.add(lblPzKg, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 290, 210, -1));

        lblEstatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEstatus.setForeground(new java.awt.Color(76, 109, 255));
        lblEstatus.setText("ESTATUS:");
        jPanel1.add(lblEstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 210, -1));

        lblHojaInstruccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHojaInstruccion.setForeground(new java.awt.Color(76, 109, 255));
        lblHojaInstruccion.setText("<html>NO. HOJA DE <br>INSTRUCCIONES: </html>");
        jPanel1.add(lblHojaInstruccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 340, -1, -1));

        lblPresentacionLamina.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPresentacionLamina.setForeground(new java.awt.Color(76, 109, 255));
        lblPresentacionLamina.setText("PRESENTACIÓN DE LAMINA:");
        jPanel1.add(lblPresentacionLamina, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, -1, -1));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 560, -1, 60));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR  ");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 550, 160, 60));

        lblNoFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoFactura.setForeground(new java.awt.Color(76, 109, 255));
        lblNoFactura.setText("NO. FACTURA:");
        jPanel1.add(lblNoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 140, -1));
        jPanel1.add(dchFechaFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 210, -1));

        btnAgregarCertificado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarCertificado.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCertificado.setText("Agregar Certificado");
        btnAgregarCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCertificadoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarCertificado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 180, 40));

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        lblJCIcono.setToolTipText("");
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        lblAgregar.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        lblAgregar.setForeground(new java.awt.Color(10, 110, 255));
        lblAgregar.setText("AGREGAR");
        jPanel1.add(lblAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 260, 50));

        btnAgregarFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarFactura.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarFactura.setText("Agregar Factura");
        btnAgregarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarFacturaActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 383, 150, 40));
        jPanel1.add(txtNoRollo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 270, -1));
        jPanel1.add(txtPzKg, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 290, -1));
        jPanel1.add(txtNoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 250, -1));
        jPanel1.add(txtCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 280, -1));
        jPanel1.add(txtNoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 250, -1));

        txtNoHoja.setEditable(false);
        txtNoHoja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoHojaActionPerformed(evt);
            }
        });
        jPanel1.add(txtNoHoja, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 340, 210, -1));

        txtNombreCertificado.setEditable(false);
        txtNombreCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreCertificadoActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombreCertificado, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 180, 30));

        txtNombreFactura.setEditable(false);
        jPanel1.add(txtNombreFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 200, 30));

        btnAgregarHojaInstruccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarHojaInstruccion.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarHojaInstruccion.setText("<html>Agregar Hoja <br>de Instrucción</html>");
        btnAgregarHojaInstruccion.setToolTipText("");
        btnAgregarHojaInstruccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarHojaInstruccionActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarHojaInstruccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 180, 40));

        txtNombreHojaInstruccion.setEditable(false);
        jPanel1.add(txtNombreHojaInstruccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 500, 190, 30));

        cbxPresentacionLamina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CINTA", "HOJA" }));
        jPanel1.add(cbxPresentacionLamina, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 230, 140, -1));

        jPanel1.add(cbxProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 130, 250, -1));

        cbxEstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LIBERADA", "POR LIBERAR", "RECHAZADA", "CERTIFICADO INCOMPLETO", " " }));
        jPanel1.add(cbxEstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 280, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarFacturaActionPerformed
        seleccionarFactura();
    }//GEN-LAST:event_btnAgregarFacturaActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        goToIspeccionRecibo();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // Obtén la fecha seleccionada
        Date selectedDate = dchFechaFactura.getDate();
        // Crea un objeto SimpleDateFormat para el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String estatus = cbxEstatus.getSelectedItem().toString();
        String calibre = txtCalibre.getText().trim();
        String pLamina = cbxPresentacionLamina.getSelectedItem().toString();
        String proveedor = cbxProveedor.getSelectedItem().toString();
        String noFactura = txtNoFactura.getText().trim();
        String noPedido = txtNoPedido.getText().trim();
        String noRollo = txtNoRollo.getText().trim();
        String pzKg = txtPzKg.getText().trim();

        String noHoja = txtNoHoja.getText().trim();
        String[] partes = noHoja.split("/");
        String numeroStr = partes[1];
        int numero = Integer.parseInt(numeroStr);
        // Eliminar ceros a la izquierda
        numeroStr = String.valueOf(numero);

        String nomHJ = txtNombreHojaInstruccion.getText();
        String nomFactura = txtNombreFactura.getText();
        String nomCert = txtNombreCertificado.getText();

        if (txtCalibre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa el componente");
        } else {
            if (selectedDate == null) {
                try {
                    File archivoSeleccionado = new File(rutaArchivoHojaInstruccion);
                    XSSFWorkbook workbook;
                    try (FileInputStream fis = new FileInputStream(archivoSeleccionado)) {
                        workbook = new XSSFWorkbook(fis);
                        Sheet sheet = workbook.getSheetAt(0); // Obtener la primera hoja del libro (índice 0)
                        // Modificar el contenido del archivo de Excel aquí
                        // No. de Hoja
                        Row rowNoHoja = sheet.getRow(5); // Obtén la fila
                        Cell cellNoHoja = rowNoHoja.getCell(2); // Obtén la celda en la fila
                        // Modifica el valor de la celda
                        cellNoHoja.setCellValue(numeroStr);

                        // Proveedor
                        Row rowProveedor = sheet.getRow(9); // Obtén la fila
                        Cell cellProveedor = rowProveedor.getCell(4); // Obtén la celda en la fila

                        // No Factura
                        Row rowNoFactura = sheet.getRow(9); // Obtén la fila
                        Cell cellNoFactura = rowNoFactura.getCell(6); // Obtén la celda en la fila

                        // Fecha Factura
                        Row rowFechaFactura = sheet.getRow(9); // Obtén la fila
                        Cell cellFechaFactura = rowFechaFactura.getCell(8); // Obtén la celda en la fila

                        // No. Pedido
                        Row rowNoPedido = sheet.getRow(13); // Obtén la fila
                        Cell cellNoPedido = rowNoPedido.getCell(3); // Obtén la celda en la fila

                        // No Rollo
                        Row rowNoRollo = sheet.getRow(13); // Obtén la fila
                        Cell cellNoRollo = rowNoRollo.getCell(5); // Obtén la celda en la fila

                        // No PZKG
                        Row rowNoPzKg = sheet.getRow(13); // Obtén la fila
                        Cell cellNoPzKg = rowNoPzKg.getCell(7); // Obtén la celda en la fila

                        DataFormatter formatter = new DataFormatter();
                        Cell cell = sheet.getRow(9).getCell(8);
                        String formatoFecha = formatter.formatCellValue(cell);

                        irm.setFechaFactura(formatoFecha);
                        irm.setProveedor(cellProveedor.getStringCellValue());
                        irm.setNoFactura(cellNoFactura.getStringCellValue());
                        irm.setNoPedido(cellNoPedido.getStringCellValue());
                        irm.setCalibre(calibre);
                        irm.setpLamina(pLamina);
                        irm.setNoRollo(cellNoRollo.getStringCellValue());
                        irm.setPzKg(cellNoPzKg.getStringCellValue());
                        irm.setEstatus(estatus);
                        irm.setNoHoja(noHoja);

                    }

                    try (FileOutputStream fos = new FileOutputStream(archivoSeleccionado)) { // Guardar los cambios en el archivo
                        workbook.write(fos);
                    }

                    try {
                        // Archivo de Hoja de Instrucción
                        irs.cargarArchivo(rutaArchivoHojaInstruccion, irm::setHojaIns);

                        if (irs.existeNoRollo(txtCalibre.getText()) == 0) {
                            JOptionPane.showMessageDialog(this, "Calibre registrado previamente");
                        }
                        guardarDatos(); // Se guardan los datos

                    } catch (ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(this, "El documento esta abierto o esta siendo utilizado por otro proceso");
                    }

                } catch (IOException e) {
                    Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                // Se almacenan los datos capturados en las variable
                String fechaFactura = dateFormat.format(selectedDate); // Formatea la fecha y guárdala en una variable String

                // Si los campos no estan vacios
                if (!proveedor.isEmpty() || !noFactura.isEmpty() || !noPedido.isEmpty() || !calibre.isEmpty() || !pLamina.isEmpty() || !noRollo.isEmpty() || !pzKg.isEmpty() || !fechaFactura.isEmpty()) {
                    irs.cargarArchivo(rutaArchivoCertificado, irm::setCertificadopdf);
                    irs.cargarArchivo(rutaArchivoFactura, irm::setFacturapdf);

                    // Archivo de Hoja de Instrucción
                    irs.cargarArchivo(rutaArchivoHojaInstruccion, irm::setHojaIns);

                    // Se guardan el resto de los atributos de la instancia
                    irm.setFechaFactura(fechaFactura);
                    irm.setProveedor(proveedor);
                    irm.setNoFactura(noFactura);
                    irm.setNoPedido(noPedido);
                    irm.setCalibre(calibre);
                    irm.setpLamina(pLamina);
                    irm.setNoRollo(noRollo);
                    irm.setPzKg(pzKg);
                    irm.setEstatus(estatus);
                    irm.setNoHoja(noHoja);
                    irm.setNombreHJ(nomHJ);
                    irm.setNombreFact(nomFactura);
                    irm.setNombreCert(nomCert);
                    try {
                        if (irs.existeNoRollo(txtCalibre.getText()) == 0) {
                            JOptionPane.showMessageDialog(this, "Calibre registrado previamente");
                        }
                        guardarDatos(); // Se guardan los datos

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else { // Si el calibre esta vacío se muestra el mensaje
                    JOptionPane.showMessageDialog(null, "DATOS INCOMPLETOS");
                }
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAgregarCertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCertificadoActionPerformed
        seleccionarCertificado();
    }//GEN-LAST:event_btnAgregarCertificadoActionPerformed

    private void txtNombreCertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreCertificadoActionPerformed

    }//GEN-LAST:event_txtNombreCertificadoActionPerformed

    private void btnAgregarHojaInstruccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarHojaInstruccionActionPerformed
        seleccionarHojaInstruccion();
    }//GEN-LAST:event_btnAgregarHojaInstruccionActionPerformed

    private void txtNoHojaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoHojaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoHojaActionPerformed

    public String seleccionarArchivoCertificado(JTextField textField, String rutaArchivo) {
        File archivoSeleccionado = this.irs.seleccionarArchivo(this); // Se selecciona el archivo
        if (archivoSeleccionado != null) {
            String nombreArchivo = archivoSeleccionado.getName(); // Se obtiene el nombre
            rutaArchivo = archivoSeleccionado.getAbsolutePath(); // Actualiza la ruta absoluta
            textField.setText(nombreArchivo);
        }
        return rutaArchivo;
    }

    public void seleccionarCertificado() {
        rutaArchivoCertificado = seleccionarArchivoCertificado(txtNombreCertificado, rutaArchivoCertificado);
    }

    public void seleccionarFactura() {
        rutaArchivoFactura = seleccionarArchivoCertificado(txtNombreFactura, rutaArchivoFactura);
    }

    public void seleccionarHojaInstruccion() {
        rutaArchivoHojaInstruccion = seleccionarArchivoCertificado(txtNombreHojaInstruccion, rutaArchivoHojaInstruccion);
    }

    private void guardarDatos() {
        try {
            this.irs.agregar(conexion, irm); // Se manda a llamar el metodo guardar para almacenar la información en la bd
            JOptionPane.showMessageDialog(this, "DATOS GUARDADOS."); // Si todo salio bien, mandara este mensaje
            goToIspeccionRecibo();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
        }
    }

    private void goToIspeccionRecibo() {
        this.dispose(); // Se liberan los recursos de la interfaz
        try {
            InspeccionReciboGUI irGUI = new InspeccionReciboGUI(usr); // Se crea una instancia de la interfaz gráfica
            irGUI.setVisible(true); // Se hace visible la ventana
            irGUI.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema 
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            java.util.logging.Logger.getLogger(AgregarIrGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new AgregarIrGUI().setVisible(true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCertificado;
    private javax.swing.JButton btnAgregarFactura;
    private javax.swing.JButton btnAgregarHojaInstruccion;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbxEstatus;
    private javax.swing.JComboBox<String> cbxPresentacionLamina;
    private javax.swing.JComboBox<String> cbxProveedor;
    private com.toedter.calendar.JDateChooser dchFechaFactura;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblAgregar;
    private javax.swing.JLabel lblCalibre;
    private javax.swing.JLabel lblEstatus;
    private javax.swing.JLabel lblFechaFactura;
    private javax.swing.JLabel lblHojaInstruccion;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblNoFactura;
    private javax.swing.JLabel lblNoPedido;
    private javax.swing.JLabel lblNoRollo;
    private javax.swing.JLabel lblPresentacionLamina;
    private javax.swing.JLabel lblProveedor;
    private javax.swing.JLabel lblPzKg;
    private javax.swing.JTextField txtCalibre;
    private javax.swing.JTextField txtNoFactura;
    private javax.swing.JTextField txtNoHoja;
    private javax.swing.JTextField txtNoPedido;
    private javax.swing.JTextField txtNoRollo;
    private javax.swing.JTextField txtNombreCertificado;
    private javax.swing.JTextField txtNombreFactura;
    private javax.swing.JTextField txtNombreHojaInstruccion;
    private javax.swing.JTextField txtPzKg;
    // End of variables declaration//GEN-END:variables
}
