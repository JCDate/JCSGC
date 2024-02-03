package InspeccionRecibo;

import Modelos.InspeccionReciboM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.InspeccionReciboServicio;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author JC
 */
public class ModificarIr extends javax.swing.JFrame {

    private final InspeccionReciboServicio irs = new InspeccionReciboServicio();
    private final InspeccionReciboM irm, irm2;
    Usuarios usr;
    Connection conexion;

    String rutaArchivoCertificado = ""; // Variable que guarda la ruta absoluta del archivo de certificado
    String rutaArchivoFactura = ""; // Variable que guarda la ruta absoluta del archivo de la factura
    String rutaArchivoHojaInstruccion = ""; // Variable que guarda la ruta absoluta del archivo de la hoja Instrucción

    /**
     * Creates new form ModificarIr
     */
    public ModificarIr() {
        initComponentes();
        this.irm = new InspeccionReciboM();
        this.irm2 = this.irm;
    }

    public ModificarIr(InspeccionReciboM irm, Usuarios usr) throws ClassNotFoundException {
        initComponentes();
        this.usr = usr;
        this.irm = irm;
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton

        txtNoFactura.setText(this.irm.getNoFactura());
        txtNoPedido.setText(this.irm.getNoPedido());
        txtCalibre.setText(this.irm.getCalibre());
        txtNoRollo.setText(this.irm.getNoRollo());
        txtPzKg.setText(this.irm.getPzKg());
        txtNombreFactura.setText(this.irm.getNombreFact());
        txtNombreCertificado.setText(this.irm.getNombreCert());
        txtNombreHojaInstruccion.setText(this.irm.getNombreHJ());
        txtNoHoja.setText(String.valueOf(this.irm.getNoHoja()));

        cbxEstatus.setSelectedItem(this.irm.getEstatus());
        cbxPresentacionLamina.setSelectedItem(this.irm.getpLamina());
        cbxProveedor.setSelectedItem(this.irm.getProveedor());

        this.irm2 = new InspeccionReciboM(this.irm.getId(), this.irm.getFechaFactura(), "", "", "", this.irm.getCalibre(), "", this.irm.getNoRollo(), this.irm.getPzKg(), "", "", this.irm.getFacturapdf(), this.irm.getCertificadopdf(), this.irm.getHojaIns(), this.irm.getNombreHJ(), this.irm.getNombreFact(), this.irm.getNombreCert());

        try {
            Statement slqNombreProveedor = conexion.createStatement(); // Se define la consulta preparada
            ResultSet registro = slqNombreProveedor.executeQuery(this.irs.SELECT_NOMBRE_PROVEEDORES_SQL); // Se consultan los nombres de los proveedores 
            while (registro.next()) { // Mientras haya información...
                String columna = registro.getString("nombre"); // Variable que almacena los diferentes nombres encontrados
                cbxProveedor.addItem(columna); // Se agrega al checkBox
            }
        } catch (SQLException ex) { // Se captura la excepción SQLException
            Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Crear un objeto SimpleDateFormat para analizar la cadena de fecha

            Date fecha = dateFormat.parse(this.irm.getFechaFactura()); // Parsear la cadena de fecha en un objeto Date

            dchFechaFactura.setDate(fecha); // Establecer la fecha en el JDateChooser
        } catch (ParseException e) {
        }
    }

    public ModificarIr(InspeccionReciboM irm, Usuarios usr, String rutaArchivoHJ) throws ClassNotFoundException {
        initComponentes();
        this.usr = usr;
        this.irm = irm;
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton

        txtNoFactura.setText(this.irm.getNoFactura());
        txtNoPedido.setText(this.irm.getNoPedido());
        txtCalibre.setText(this.irm.getCalibre());
        txtNoRollo.setText(this.irm.getNoRollo());
        txtPzKg.setText(this.irm.getPzKg());
        txtNoHoja.setText(String.valueOf(this.irm.getNoHoja()));

        cbxEstatus.setSelectedItem(this.irm.getEstatus());
        cbxPresentacionLamina.setSelectedItem(this.irm.getpLamina());
        cbxProveedor.setSelectedItem(this.irm.getProveedor());

        this.irm2 = new InspeccionReciboM(this.irm.getId(), this.irm.getFechaFactura(), "", "", "", this.irm.getCalibre(), "", this.irm.getNoRollo(), this.irm.getPzKg(), "", "", this.irm.getFacturapdf(), this.irm.getCertificadopdf(), this.irm.getHojaIns(), this.irm.getNombreHJ(), this.irm.getNombreFact(), this.irm.getNombreCert());

        try {
            Statement slqNombreProveedor = conexion.createStatement(); // Se define la consulta preparada
            ResultSet registro = slqNombreProveedor.executeQuery(this.irs.SELECT_NOMBRE_PROVEEDORES_SQL); // Se consultan los nombres de los proveedores 
            while (registro.next()) { // Mientras haya información...
                String columna = registro.getString("nombre"); // Variable que almacena los diferentes nombres encontrados
                cbxProveedor.addItem(columna); // Se agrega al checkBox
            }
        } catch (SQLException ex) { // Se captura la excepción SQLException
            Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Crear un objeto SimpleDateFormat para analizar la cadena de fecha

            Date fecha = dateFormat.parse(this.irm.getFechaFactura()); // Parsear la cadena de fecha en un objeto Date

            dchFechaFactura.setDate(fecha); // Establecer la fecha en el JDateChooser
        } catch (ParseException e) {
        }

        rutaArchivoHojaInstruccion = rutaArchivoHJ;

        txtNombreHojaInstruccion.setText(rutaArchivoHojaInstruccion);
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

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        txtNoHoja = new swing.TextField();
        lblProveedor = new javax.swing.JLabel();
        lblEstatus = new javax.swing.JLabel();
        lblNoRollo = new javax.swing.JLabel();
        lblModificar = new javax.swing.JLabel();
        lblPzKg = new javax.swing.JLabel();
        btnAgregarFactura =  new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        txtNoPedido = new swing.TextField();
        lblNoPedido = new javax.swing.JLabel();
        lblFechaFactura = new javax.swing.JLabel();
        txtPzKg = new swing.TextField();
        txtNoRollo = new swing.TextField();
        lblNoFactura = new javax.swing.JLabel();
        lblCalibre = new javax.swing.JLabel();
        lblHojaInstruccion = new javax.swing.JLabel();
        txtCalibre = new swing.TextField();
        lblPresentacionLamina = new javax.swing.JLabel();
        btnGuardar = new swing.Button();
        txtNoFactura = new swing.TextField();
        lblJCIcono = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        btnAgregarCertificado = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        txtNombreCertificado = new swing.TextField();
        txtNombreFactura = new swing.TextField();
        btnAgregarHojaInstrucciones = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        txtNombreHojaInstruccion = new swing.TextField();
        cbxPresentacionLamina = new swing.ComboBoxSuggestion();
        cbxProveedor = new swing.ComboBoxSuggestion();
        cbxEstatus = new swing.ComboBoxSuggestion();
        dchFechaFactura = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setMinimumSize(new java.awt.Dimension(900, 576));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 576));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNoHoja.setEditable(false);
        jPanel1.add(txtNoHoja, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, 210, -1));

        lblProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblProveedor.setForeground(new java.awt.Color(76, 109, 255));
        lblProveedor.setText("PROVEEDOR:");
        jPanel1.add(lblProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, 100, -1));

        lblEstatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEstatus.setForeground(new java.awt.Color(76, 109, 255));
        lblEstatus.setText("ESTATUS:");
        jPanel1.add(lblEstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 210, -1));

        lblNoRollo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoRollo.setForeground(new java.awt.Color(76, 109, 255));
        lblNoRollo.setText("NO. ROLLO:");
        jPanel1.add(lblNoRollo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 210, 20));

        lblModificar.setBackground(new java.awt.Color(10, 110, 255));
        lblModificar.setFont(new java.awt.Font("Wide Latin", 1, 24)); // NOI18N
        lblModificar.setForeground(new java.awt.Color(10, 110, 255));
        lblModificar.setText("MODIFICAR");
        lblModificar.setMaximumSize(new java.awt.Dimension(562, 25));
        lblModificar.setMinimumSize(new java.awt.Dimension(562, 25));
        jPanel1.add(lblModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, -1, -1));

        lblPzKg.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPzKg.setForeground(new java.awt.Color(76, 109, 255));
        lblPzKg.setText("PZ/Kg:");
        jPanel1.add(lblPzKg, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 290, 210, -1));

        btnAgregarFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarFactura.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarFactura.setText("Subir Factura");
        btnAgregarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarFacturaActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 170, 40));
        jPanel1.add(txtNoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 190, 250, -1));

        lblNoPedido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoPedido.setForeground(new java.awt.Color(76, 109, 255));
        lblNoPedido.setText("NO. PEDIDO:");
        jPanel1.add(lblNoPedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 190, -1, -1));

        lblFechaFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblFechaFactura.setForeground(new java.awt.Color(76, 109, 255));
        lblFechaFactura.setText("FECHA DE FACTURA:");
        jPanel1.add(lblFechaFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 160, -1));
        jPanel1.add(txtPzKg, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, 290, -1));
        jPanel1.add(txtNoRollo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 280, -1));

        lblNoFactura.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNoFactura.setForeground(new java.awt.Color(76, 109, 255));
        lblNoFactura.setText("NO. FACTURA:");
        jPanel1.add(lblNoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 140, -1));

        lblCalibre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCalibre.setForeground(new java.awt.Color(76, 109, 255));
        lblCalibre.setText("CALIBRE:");
        jPanel1.add(lblCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 140, -1));

        lblHojaInstruccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHojaInstruccion.setForeground(new java.awt.Color(76, 109, 255));
        lblHojaInstruccion.setText("<html>NO. HOJA DE <br>INSTRUCCIONES: </html>");
        jPanel1.add(lblHojaInstruccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 340, -1, -1));
        jPanel1.add(txtCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 290, -1));

        lblPresentacionLamina.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPresentacionLamina.setForeground(new java.awt.Color(76, 109, 255));
        lblPresentacionLamina.setText("PRESENTACIÓN DE LAMINA:");
        jPanel1.add(lblPresentacionLamina, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 240, -1, -1));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 560, 160, 60));
        jPanel1.add(txtNoFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 270, -1));

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        lblJCIcono.setToolTipText("");
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 570, -1, 60));

        btnAgregarCertificado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarCertificado.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCertificado.setText("Subir Certificado");
        btnAgregarCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCertificadoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarCertificado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 170, 40));

        txtNombreCertificado.setEditable(false);
        jPanel1.add(txtNombreCertificado, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, 190, -1));

        txtNombreFactura.setEditable(false);
        jPanel1.add(txtNombreFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 200, -1));

        btnAgregarHojaInstrucciones.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarHojaInstrucciones.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarHojaInstrucciones.setText("<html>Subir Hoja de <br> Instrucciones</html>");
        btnAgregarHojaInstrucciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarHojaInstruccionesActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarHojaInstrucciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 170, -1));

        txtNombreHojaInstruccion.setEditable(false);
        jPanel1.add(txtNombreHojaInstruccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, 200, -1));

        cbxPresentacionLamina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CINTA", "HOJA" }));
        jPanel1.add(cbxPresentacionLamina, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 240, 140, -1));

        jPanel1.add(cbxProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 240, -1));

        cbxEstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "LIBERADA", "POR LIBERAR", "RECHAZADA", "CERTIFICADO INCOMPLETO" }));
        jPanel1.add(cbxEstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 340, 300, -1));
        jPanel1.add(dchFechaFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 230, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        goToInspeccionReciboGUI();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // Obtén la fecha seleccionada
        Date selectedDate = dchFechaFactura.getDate();
        // Crea un objeto SimpleDateFormat para el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Se almacenan los datos capturados en las variable
        String fechaFactura = dateFormat.format(selectedDate); // Formatea la fecha y guárdala en una variable String
        String proveedor = cbxProveedor.getSelectedItem().toString();
        String noFactura = txtNoFactura.getText();
        String noPedido = txtNoPedido.getText();
        String calibre = txtCalibre.getText();
        String pLamina = cbxPresentacionLamina.getSelectedItem().toString();
        String noRollo = txtNoRollo.getText();
        String pzKg = txtPzKg.getText();
        String estatus = cbxEstatus.getSelectedItem().toString();
        String noHoja = txtNoHoja.getText();
        String nombreHJ = txtNombreHojaInstruccion.getText();
        String nombreFact = txtNombreFactura.getText();
        String nombreCert = txtNombreCertificado.getText();

        // Si los campos no estan vacios
        if (!proveedor.isEmpty() || !noFactura.isEmpty() || !noPedido.isEmpty() || !calibre.isEmpty() || !pLamina.isEmpty() || !noRollo.isEmpty() || !pzKg.isEmpty() || !estatus.isEmpty() || !fechaFactura.isEmpty()) {
            this.irm.setFechaFactura(fechaFactura);
            this.irm.setProveedor(proveedor);
            this.irm.setNoFactura(noFactura);
            this.irm.setNoPedido(noPedido);
            this.irm.setCalibre(calibre);
            this.irm.setpLamina(pLamina);
            this.irm.setNoRollo(noRollo);
            this.irm.setPzKg(pzKg);
            this.irm.setEstatus(estatus);
            this.irm.setNoHoja(noHoja);
            this.irm.setNoHoja(noHoja);
            this.irm.setNombreHJ(nombreHJ);
            this.irm.setNombreFact(nombreFact);
            this.irm.setNombreCert(nombreCert);
            this.irs.cargarArchivo(rutaArchivoFactura, this.irm::setFacturapdf);
            this.irs.cargarArchivo(rutaArchivoCertificado, this.irm::setCertificadopdf);
            this.irs.cargarArchivo(rutaArchivoHojaInstruccion, this.irm::setHojaIns);

            modificarDatos(); // Se realiza la modificación
        } else {
            JOptionPane.showMessageDialog(this, "HAY CAMPOS INCOMPLETOS.");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAgregarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarFacturaActionPerformed
        seleccionarFactura();
    }//GEN-LAST:event_btnAgregarFacturaActionPerformed

    private void btnAgregarCertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCertificadoActionPerformed
        seleccionarCertificado();
    }//GEN-LAST:event_btnAgregarCertificadoActionPerformed

    private void btnAgregarHojaInstruccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarHojaInstruccionesActionPerformed
        seleccionarHojaInstruccion();
        String rutaAux = rutaArchivoHojaInstruccion;
        this.dispose();
        rutaArchivoHojaInstruccion = irs.abrirHojaInstruccionGUI2(usr, rutaAux, irm);
    }//GEN-LAST:event_btnAgregarHojaInstruccionesActionPerformed

    public String seleccionarArchivoCertificado(JTextField textField, String rutaArchivo) {
        File archivoSeleccionado = this.irs.seleccionarArchivo(this); // Se selecciona el archivo
        if (archivoSeleccionado != null) {
            String nombreArchivo = archivoSeleccionado.getName(); // Se obtiene el nombre
            rutaArchivo = archivoSeleccionado.getAbsolutePath(); // Actualiza la ruta absoluta
            textField.setText(nombreArchivo);
        }
        return rutaArchivo;
    }

    private void modificarDatos() {
        try {
            this.irs.modificar(conexion, this.irm, this.irm2);
            JOptionPane.showMessageDialog(this, "DATOS GUARDADOS.");
            goToInspeccionReciboGUI();
        } catch (SQLException ex) {
            Logger.getLogger(ModificarIr.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
        }
    }

    private void goToInspeccionReciboGUI() {
        try {
            this.dispose();
            InspeccionReciboGUI irGUI = new InspeccionReciboGUI(usr);
            irGUI.setVisible(true);
            irGUI.setLocationRelativeTo(null);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ModificarIr.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public final void initComponentes() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(0);
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
            java.util.logging.Logger.getLogger(ModificarIr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ModificarIr().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCertificado;
    private javax.swing.JButton btnAgregarFactura;
    private javax.swing.JButton btnAgregarHojaInstrucciones;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cbxEstatus;
    private javax.swing.JComboBox<String> cbxPresentacionLamina;
    private javax.swing.JComboBox<String> cbxProveedor;
    private com.toedter.calendar.JDateChooser dchFechaFactura;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCalibre;
    private javax.swing.JLabel lblEstatus;
    private javax.swing.JLabel lblFechaFactura;
    private javax.swing.JLabel lblHojaInstruccion;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblModificar;
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
