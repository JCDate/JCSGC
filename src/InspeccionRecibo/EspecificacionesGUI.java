package InspeccionRecibo;

import Modelos.ComposicionQuimicaM;
import Modelos.CustomTableCellRenderer;
import Servicios.CheckBoxEditor;
import Servicios.CheckBoxRenderer;
import Servicios.GeneradorExcel;
import Modelos.DatosIRM;
import Modelos.EspecificacionM;
import Modelos.InspeccionReciboM;
import Modelos.PropiedadMecanicaM;
import Modelos.Usuarios;
import Servicios.AnchoLargoServicio;
import Servicios.Conexion;
import Servicios.EspecificacionesServicio;
import Servicios.InspeccionReciboServicio;
import Servicios.RugosidadDurezaServicio;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class EspecificacionesGUI extends JFrame {

    // Usuario y Conexión a la base de datos
    private Usuarios usuario;
    private Connection conexion;

    // Objetos para manipulación de los datos
    private DatosIRM datosIR;
    private EspecificacionM especificacion;
    private InspeccionReciboM inspeccionRecibo;

    // Servicios y Utilidades
    private InspeccionReciboServicio irs = new InspeccionReciboServicio();
    private AnchoLargoServicio als = new AnchoLargoServicio();
    private RugosidadDurezaServicio rds = new RugosidadDurezaServicio();
    private EspecificacionesServicio es = new EspecificacionesServicio();
    private GeneradorExcel xls = new GeneradorExcel();

    // Tablas para controlar la rugosidad y el ancho
    private JTable tblRugosidadDureza;
    private JTable tblAnchoLargo;

    // Listas de datos
    private List<JTable> listaTablas = new ArrayList<>();
    private List<String> listaMedidasCalibre = new ArrayList<>();
    private List<String> listaEspecificacion = new ArrayList<>();
    private List listaAnchoLargo = new ArrayList<>();
    private List listaRugosidadDureza = new ArrayList<>();
    private List<JTable> tablas;

    public EspecificacionesGUI() throws SQLException, ClassNotFoundException {
        inicializarVentanaYComponentes();
    }

    public EspecificacionesGUI(Usuarios usuario) throws SQLException, ClassNotFoundException {
        inicializarVentanaYComponentes();
        this.usuario = usuario;
    }

    public EspecificacionesGUI(Usuarios usuario, InspeccionReciboM inspeccionRecibo) throws SQLException, ClassNotFoundException {
        inicializarVentanaYComponentes();
        this.usuario = usuario;
        this.inspeccionRecibo = inspeccionRecibo;
    }

    public EspecificacionesGUI(Usuarios usuario, DatosIRM datosIR, InspeccionReciboM inspeccionRecibo, JTable tblRugosidadDureza, JTable tblAnchoLargo) throws SQLException, ClassNotFoundException {
        inicializarVentanaYComponentes();
        this.usuario = usuario;
        this.datosIR = datosIR;
        this.inspeccionRecibo = inspeccionRecibo;
        this.tblRugosidadDureza = tblRugosidadDureza;
        this.tblAnchoLargo = tblAnchoLargo;
        als.setTbl(tblRugosidadDureza);
        rds.setTbl(tblAnchoLargo);
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
        jLabel2 = new javax.swing.JLabel();
        cbxEspecificacionTecnica = new swing.ComboBoxSuggestion();
        btnAceptar = new swing.Button(new Color(175, 255, 158),new Color(106, 223, 80));
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlTablas = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JButton();
        cbxCalibre = new swing.ComboBoxSuggestion();
        lblEspecificacionTecnica = new javax.swing.JLabel();
        lblCalibre = new javax.swing.JLabel();
        btnEliminar = new swing.Button(new Color(255, 133, 133),new Color(255, 63, 63));
        btnGuardar = new swing.Button(new Color(121, 190, 255),new Color(10, 110, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(1190, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setMinimumSize(new java.awt.Dimension(1190, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(1190, 600));
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        jLabel2.setFont(new java.awt.Font("Wide Latin", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(10, 110, 255));
        jLabel2.setText("<html>ESPECIFICACIONES TÉCNICAS DEL CLIENTE PARA INSPECCIÓN/RECIBO DE MATERIA PRIMA</html>");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 630, 70));

        jPanel1.add(cbxEspecificacionTecnica, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 100, 330, 30));

        btnAceptar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/ok2.png"))); // NOI18N
        btnAceptar.setText("ACEPTAR");
        jPanel1.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 60, 120, 30));

        pnlTablas.setLayout(new javax.swing.BoxLayout(pnlTablas, javax.swing.BoxLayout.LINE_AXIS));
        jScrollPane1.setViewportView(pnlTablas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1170, 390));

        btnRegresar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        btnRegresar.setBorderPainted(false);
        btnRegresar.setContentAreaFilled(false);
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 150, 50));

        jPanel1.add(cbxCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 230, 30));

        lblEspecificacionTecnica.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEspecificacionTecnica.setForeground(new java.awt.Color(10, 110, 255));
        lblEspecificacionTecnica.setText("ESPECIFICACIÓN TÉCNICA:");
        jPanel1.add(lblEspecificacionTecnica, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, -1, -1));

        lblCalibre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCalibre.setForeground(new java.awt.Color(10, 110, 255));
        lblCalibre.setText("CALIBRE:");
        jPanel1.add(lblCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR TABLA");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 20, 160, 30));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 540, 140, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "DATOS DE TABLA ELIMINADOS");
        eliminarUltimaTabla();
        irs.abrirEspecificacionesGUI(usuario, datosIR, inspeccionRecibo, tblAnchoLargo, tblRugosidadDureza);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        cerrarVentana();

        int idIR = obtenerIdHojaInstruccion();

        listaAnchoLargo = als.capturarValores(idIR, datosIR.getAnchoLargo());
        listaRugosidadDureza = rds.recuperarTodas(idIR, datosIR.getAnchoLargo());

        irs.abrirHojaInstruccionGUI(usuario, inspeccionRecibo, datosIR, listaAnchoLargo, listaRugosidadDureza);
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        try {
            int idIR = obtenerIdHojaInstruccion();

            List ald = als.capturarValores(idIR, datosIR.getAnchoLargo());
            List rdd = rds.recuperarTodas(idIR, datosIR.getAnchoLargo());

            String HojaIns = xls.generarHojaInstruccion(conexion, datosIR, inspeccionRecibo, tblAnchoLargo, listaTablas, ald, rdd);

            inspeccionRecibo.setHojaIns(this.irs.leerArchivo(HojaIns));

            irs.subirHI(conexion, inspeccionRecibo);

            cerrarVentana();

            irs.abrirInspeccionReciboGUI(usuario);
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(EspecificacionesGUI.class.getName()).log(Level.SEVERE, null, ex);
            irs.manejarExcepcion("ERROR al guarda la información: ", ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void inicializarVentanaYComponentes() throws SQLException, ClassNotFoundException {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.conexion = Conexion.getInstance().getConnection();

        pnlTablas.setLayout(new BoxLayout(pnlTablas, BoxLayout.Y_AXIS));

        listaMedidasCalibre = irs.recuperarMedidasCalibre(conexion, inspeccionRecibo);
        listaMedidasCalibre.forEach(cbxCalibre::addItem);

        actualizarEspecificacionesTecnicas();

        cbxCalibre.addActionListener(e -> actualizarEspecificacionesTecnicas());

        btnAceptar.addActionListener(e -> {
            String espeTecnica = (String) cbxEspecificacionTecnica.getSelectedItem();
            String calibre = (String) cbxCalibre.getSelectedItem();
            try {
                crearTabla(espeTecnica, calibre);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(EspecificacionesGUI.class.getName()).log(Level.SEVERE, null, ex);
                irs.manejarExcepcion("Error al crear la tabla especificada: ", ex);
            }
        });

        btnEliminar.addActionListener(ae -> eliminarUltimaTabla());
    }

    private void actualizarEspecificacionesTecnicas() {
        cbxEspecificacionTecnica.removeAllItems();

        String[] calibreYMedidas = cbxCalibre.getSelectedItem().toString().split("   ");

        if (calibreYMedidas.length == 2) {
            String calibre = calibreYMedidas[0];
            listaEspecificacion = irs.recuperarEspecificaciones(conexion, calibre);
            listaEspecificacion.forEach(cbxEspecificacionTecnica::addItem);
        }
    }

    private void crearTabla(String especificacionTecnica, String calibres) throws SQLException, ClassNotFoundException {
        // Obtener datos
        List<PropiedadMecanicaM> propiedadesMecanicas = es.obtenerPropiedadesMecanicas(conexion, especificacionTecnica);
        List<ComposicionQuimicaM> composicionQuimica = es.obtenerCM(conexion, especificacionTecnica);
        especificacion = irs.recuperarReferenciasEspecificacion(conexion, especificacionTecnica);

        // Crear modelo de tabla
        DefaultTableModel modeloTabla = crearModeloTabla(especificacion, calibres);

        // Configurar la tabla
        JTable nuevaTabla = crearTablaConModelo(modeloTabla);
        configurarColumnasTabla(nuevaTabla.getColumnModel(), propiedadesMecanicas.size(), composicionQuimica.size());

        // Establecer valores en la tabla
        establecerValoresEnTabla(modeloTabla, propiedadesMecanicas, composicionQuimica);

        // Añadir tabla al panel
        pnlTablas.add(new JScrollPane(nuevaTabla));
        listaTablas.add(nuevaTabla);

        // Actualizar la ventana
        pnlTablas.revalidate();
        pnlTablas.repaint();
    }

    private DefaultTableModel crearModeloTabla(EspecificacionM especificacionTecnica, String calibres) {
        String codigo = especificacion.getCodigo();
        String fechaEm = especificacion.getFechaEmision();
        String fechaRev = especificacion.getFechaRevision();
        String noRev = especificacion.getNoRev();
      

        Object[][] datos = {
            {especificacionTecnica, "Código: " + codigo, calibres, "", "", "", ""},
            {"", "F. de emisión: " + fechaEm, "", "", "", "", ""},
            {"", "Fecha de rev:" + fechaRev, "", "", "", "", ""},
            {"", "No. de rev: " + noRev, "", "", "", "", ""}
        };

        String[] nombresColumnas = {"Especificación Técnica", "Ref. del Documento", "Calibres Requeridos", "Propiedades Mecánicas", "CM", "Composición Química", "CM"};

        return new DefaultTableModel(datos, nombresColumnas);
    }

    private JTable crearTablaConModelo(DefaultTableModel modeloTabla) {
        JTable newTable = new JTable(modeloTabla);
        CustomTableCellRenderer renderer = new CustomTableCellRenderer();
        newTable.setDefaultRenderer(Object.class, renderer);
        newTable.setPreferredScrollableViewportSize(new Dimension(100, 30));
        return newTable;
    }

    private void configurarColumnasTabla(TableColumnModel columnModel, int numPropiedadesMecanicas, int numComposicionQuimica) {
        configurarTamañoColumna(columnModel, 4, 35, 35);
        configurarTamañoColumna(columnModel, 6, 35, 35);

        columnModel.getColumn(4).setCellRenderer(new CheckBoxRenderer(numPropiedadesMecanicas));
        columnModel.getColumn(4).setCellEditor(new CheckBoxEditor());

        columnModel.getColumn(6).setCellRenderer(new CheckBoxRenderer(numComposicionQuimica));
        columnModel.getColumn(6).setCellEditor(new CheckBoxEditor());
    }

    private void configurarTamañoColumna(TableColumnModel columnModel, int columnIndex, int minWidth, int maxWidth) {
        columnModel.getColumn(columnIndex).setMinWidth(minWidth);
        columnModel.getColumn(columnIndex).setMaxWidth(maxWidth);
    }

    private void establecerValoresEnTabla(DefaultTableModel modeloTabla, List<PropiedadMecanicaM> propiedadesMecanicas, List<ComposicionQuimicaM> composicionQuimica) {
    int columnaPropiedades = 3;
    int columnaPropiedadesCM = 5;

    for (int i = 0; i < propiedadesMecanicas.size(); i++) {
        String valorPropiedad = propiedadesMecanicas.get(i).getPm() + "   " + propiedadesMecanicas.get(i).getValor();
        modeloTabla.setValueAt(valorPropiedad, i, columnaPropiedades);
    }
    for (int i = 0; i < composicionQuimica.size(); i++) {
        String valorPropiedadCM = composicionQuimica.get(i).getCq() + "   " + composicionQuimica.get(i).getValor();
        modeloTabla.setValueAt(valorPropiedadCM, i, columnaPropiedadesCM);
    }
}
    
    private void eliminarUltimaTabla() {
        listaTablas.removeAll(tablas);
        pnlTablas.removeAll();
        pnlTablas.revalidate();
        pnlTablas.repaint();
    }

    private void cerrarVentana() {
        EspecificacionesGUI.this.dispose();
    }

    private int obtenerIdHojaInstruccion() {
        try {
            return irs.getIdHI(conexion, inspeccionRecibo);
        } catch (SQLException ex) {
            Logger.getLogger(EspecificacionesGUI.class.getName()).log(Level.SEVERE, null, ex);
            irs.manejarExcepcion("Error al Obtener el id de la hoja de instrución solicitada: ", ex);
            return 0;
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
            java.util.logging.Logger.getLogger(EspecificacionesGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new EspecificacionesGUI().setVisible(true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(EspecificacionesGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxCalibre;
    private javax.swing.JComboBox<String> cbxEspecificacionTecnica;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCalibre;
    private javax.swing.JLabel lblEspecificacionTecnica;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JPanel pnlTablas;
    // End of variables declaration//GEN-END:variables
}
