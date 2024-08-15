package InspeccionRecibo;

import InspeccionRecibo.HojaInstruccionGUI;
import InspeccionRecibo.InspeccionReciboGUI;
import Modelos.ComposicionQuimicaM;
import Modelos.CustomTableCellRenderer;
import Servicios.CheckBoxEditor;
import Servicios.CheckBoxRenderer;
import Servicios.ExcelEditor;
import Modelos.DatosIRM;
import Modelos.InspeccionReciboM;
import Modelos.PropiedadMecanicaM;
import Modelos.Usuarios;
import Servicios.AnchoLargoServicio;
import Servicios.Conexion;
import Servicios.EspecificacionesServicio;
import Servicios.HojaInstruccionServicio;
import Servicios.InspeccionReciboServicio;
import Servicios.RugosidadDurezaServicio;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private Usuarios usr;
    private Connection conexion;
    private DatosIRM dirm;
    private JTable tableAnchoLargo, tableRD;
    private List<JTable> tableList = new ArrayList<>();
    private InspeccionReciboM irm;
    private final InspeccionReciboServicio irs = new InspeccionReciboServicio();
    private List<JTable> tables;
    private HojaInstruccionServicio his = new HojaInstruccionServicio();
    private AnchoLargoServicio als;
    private RugosidadDurezaServicio rds;
    private EspecificacionesServicio es = new EspecificacionesServicio();
    private ExcelEditor xls = new ExcelEditor();
    private List al = new ArrayList<>();
    private List rd = new ArrayList<>();

    // Consultas
    final String SQL_CONSULTA_ESPECIFICACION = "SELECT especificacion FROM calibresir WHERE calibre = ?";
    final String SQL_CONSULTA_CALIBRE = "SELECT DISTINCT calibre, medidas FROM calibresir WHERE calibre LIKE ? ORDER BY calibre";
    final String SQL_CONSULTA_REF_ESPECIFICACION = "SELECT codigoET, fechaEmision, fechaRevision, noRev FROM especificacionesir WHERE Especificacion=?";

    /**
     * Creates new form EspecificacionesGUI
     *
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public EspecificacionesGUI() throws SQLException, ClassNotFoundException {
        initComponents(); // Inicialización de Componentes
        inicializarVentana(); // Se definen algunas propiedades para las ventanas
    }

    public EspecificacionesGUI(Usuarios usr, DatosIRM dirm, InspeccionReciboM irm, JTable tableal, JTable tablerd) throws SQLException, ClassNotFoundException {
        initComponents();
        this.usr = usr;
        this.dirm = dirm;
        this.irm = irm;
        this.tableAnchoLargo = tableal;
        this.tableRD = tablerd;
        this.als = new AnchoLargoServicio(tableAnchoLargo);
        this.rds = new RugosidadDurezaServicio(tableRD);
        inicializarVentana(); // Se definen algunas propiedades para las ventanas
    }

    public EspecificacionesGUI(Usuarios usr, InspeccionReciboM irm) throws SQLException, ClassNotFoundException {
        initComponents();// Inicialización de Componentes
        this.usr = usr;
        this.irm = irm;
        inicializarVentana(); // Se definen algunas propiedades para las ventanas
    }

    public EspecificacionesGUI(Usuarios usr) throws SQLException, ClassNotFoundException {
        initComponents();// Inicialización de Componentes
        inicializarVentana(); // Se definen algunas propiedades para las ventanas
        this.usr = usr;
    }

    public final void inicializarVentana() throws SQLException, ClassNotFoundException {
        this.setResizable(false); // Se especifica que la ventana no se puede redimensionar
        this.setDefaultCloseOperation(0); // Se deshabilita el boton de cerrar de la ventana
        tables = new ArrayList<>(); // Arreglo de las tablas
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton

        pnlTablas.setLayout(new BoxLayout(pnlTablas, BoxLayout.Y_AXIS)); // Configuración del layout de las tablas

        // Se realizan las consultas SQL
        try (PreparedStatement consulta2 = conexion.prepareStatement(SQL_CONSULTA_CALIBRE)) {
            consulta2.setString(1, "%" + irm.getCalibre().substring(0, 2) + "%");
            ResultSet resultado2 = consulta2.executeQuery();

            while (resultado2.next()) { // Se guardan los calibres en el comboBox
                cbxCalibre.addItem(resultado2.getString("calibre") + "   " + resultado2.getString("medidas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            cbxEspecificacionTecnica.removeAllItems();
            // Utiliza una expresión regular para dividir el texto en dos partes: "Cal 20" y el resto.
            String[] partes = cbxCalibre.getSelectedItem().toString().split("   ");

            // Verifica si se obtuvieron dos partes (el texto "Cal 20" y el resto).
            if (partes.length == 2) {
                String cal20 = partes[0]; // Contiene "Cal x"
                String restoDelTexto = partes[1]; // Contiene ", medidas"

                // Continúa con el resto de tu código
                PreparedStatement consulta3 = conexion.prepareStatement(SQL_CONSULTA_ESPECIFICACION);
                consulta3.setString(1, partes[0]);
                ResultSet resultado = consulta3.executeQuery();
                while (resultado.next()) { // Se guardan las especificaciones en el comboBox
                    cbxEspecificacionTecnica.addItem(resultado.getString("especificacion"));
                }
            } else {
                // Maneja el caso en el que no se dividió correctamente el texto.
                System.out.println("El texto no se dividió en dos partes correctamente.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(EspecificacionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbxCalibre.addActionListener(e -> {
            try {
                cbxEspecificacionTecnica.removeAllItems();
                // Utiliza una expresión regular para dividir el texto en dos partes: "Cal 20" y el resto.
                String[] partes = cbxCalibre.getSelectedItem().toString().split("   ");

                // Verifica si se obtuvieron dos partes (el texto "Cal 20" y el resto).
                if (partes.length == 2) {
                    String cal20 = partes[0]; // Contiene "Cal x"
                    String restoDelTexto = partes[1]; // Contiene ", medidas"

                    // Continúa con el resto de tu código
                    PreparedStatement consulta3 = conexion.prepareStatement(SQL_CONSULTA_ESPECIFICACION);
                    consulta3.setString(1, partes[0]);
                    ResultSet resultado = consulta3.executeQuery();
                    while (resultado.next()) { // Se guardan las especificaciones en el comboBox
                        cbxEspecificacionTecnica.addItem(resultado.getString("especificacion"));
                    }
                } else {
                    // Maneja el caso en el que no se dividió correctamente el texto.
                    System.out.println("El texto no se dividió en dos partes correctamente.");
                }

            } catch (SQLException ex) {
                Logger.getLogger(EspecificacionesGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        btnAceptar.addActionListener(e -> { // La escucha del boton para agregar las tablas
            String espeTecnica = (String) cbxEspecificacionTecnica.getSelectedItem();
            String calibre = (String) cbxCalibre.getSelectedItem();
            try {
                crearTabla(espeTecnica, calibre);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(EspecificacionesGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnEliminar.addActionListener(ae -> eliminarUltimaTabla()); // La escucha del boton de eliminar tabla
    }

    private void crearTabla(String espeTecnica, String calibres) throws SQLException, ClassNotFoundException {
        // Obtener la lista de propiedades mecánicas para la especificación técnica
        List<PropiedadMecanicaM> propiedadesMecanicas = this.es.obtenerPropiedadesMecanicas(conexion, espeTecnica);
        List<ComposicionQuimicaM> composicionQuimica = this.es.obtenerCM(conexion, espeTecnica);
        // Variables donde se guardan los datos de la bd
        String codigo = "";
        String fechaEm = "";
        String fechaRev = "";
        String noRev = "";
        // Se realiza la consulta
        try {
            PreparedStatement consulta = conexion.prepareStatement(SQL_CONSULTA_REF_ESPECIFICACION);
            consulta.setString(1, espeTecnica);
            ResultSet resultado = consulta.executeQuery();
            if (resultado.next()) { // Se obtiene la información de la bd
                codigo = resultado.getString("codigoET");
                fechaEm = resultado.getString("fechaEmision");
                fechaRev = resultado.getString("fechaRevision");
                noRev = resultado.getString("noRev");
            }
        } catch (SQLException ex) {
            Logger.getLogger(HojaInstruccionGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Estructura de la tabla
        Object[][] data = {
            {espeTecnica, "Código: " + codigo, calibres, "", "", "", ""},
            {"", "F. de emisión: " + fechaEm, "", "", "", "", ""},
            {"", "Fecha de rev:" + fechaRev, "", "", "", "", ""},
            {"", "No. de rev: " + noRev, "", "", "", "", ""},};

        // Columnas de la tabla
        String[] columnNames = {"Especificación Técnica", "Ref. del Documento", "Calibres Requeridos", "Propiedades Mecánicas", "CM", "Composición Química", "CM"};

        // Modelo de tabla
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Crear la tabla
        JTable newTable = new JTable(tableModel);
        CustomTableCellRenderer renderer = new CustomTableCellRenderer();
        newTable.setDefaultRenderer(Object.class, renderer);
        TableColumnModel columnModel = newTable.getColumnModel();

        // Configuración de tamaño de columnas
        final int COLUMN_CHECKBOX_1 = 4;
        final int COLUMN_CHECKBOX_2 = 6;
        columnModel.getColumn(COLUMN_CHECKBOX_1).setMinWidth(35);
        columnModel.getColumn(COLUMN_CHECKBOX_1).setMaxWidth(35);
        columnModel.getColumn(COLUMN_CHECKBOX_2).setMinWidth(35);
        columnModel.getColumn(COLUMN_CHECKBOX_2).setMaxWidth(35);

        // Agregar filas adicionales a la tabla si es necesario
        this.es.agregarFilasTabla(tableModel, propiedadesMecanicas.size());
        this.es.agregarFilasTabla(tableModel, composicionQuimica.size());

        // Establecer valores en las celdas correspondientes
        int columnaPropiedades = 3;
        int columnaPropiedadesCM = 5;
        for (int i = 0; i < propiedadesMecanicas.size(); i++) {
            Object valorPropiedad = propiedadesMecanicas.get(i).getPm() + "   " + propiedadesMecanicas.get(i).getValor();
            tableModel.setValueAt(valorPropiedad, i, columnaPropiedades);
        }
        for (int i = 0; i < composicionQuimica.size(); i++) {
            Object valorPropiedadCM = composicionQuimica.get(i).getCq() + "   " + composicionQuimica.get(i).getValor();
            tableModel.setValueAt(valorPropiedadCM, i, columnaPropiedadesCM);
        }

        // Configurar la combinación de celdas solo en la primera columna
        int columnToCheckBox = 4; // Índice de la columna en la que deseas agregar el checkbox
        columnModel.getColumn(columnToCheckBox).setCellRenderer(new CheckBoxRenderer(propiedadesMecanicas.size()));
        columnModel.getColumn(columnToCheckBox).setCellEditor(new CheckBoxEditor());

        int columnToCheckBox2 = 6; // Índice de la columna en la que deseas agregar el checkbox
        columnModel.getColumn(columnToCheckBox2).setCellRenderer(new CheckBoxRenderer(composicionQuimica.size()));
        columnModel.getColumn(columnToCheckBox2).setCellEditor(new CheckBoxEditor());
        newTable.setPreferredScrollableViewportSize(new Dimension(100, 30));

        // Tamaño de las columnas
        newTable.getColumnModel().getColumn(4).setMinWidth(35);
        newTable.getColumnModel().getColumn(4).setMaxWidth(35);
        newTable.getColumnModel().getColumn(6).setMinWidth(35);
        newTable.getColumnModel().getColumn(6).setMaxWidth(35);

        // Añadir tabla a la lista
        pnlTablas.add(new JScrollPane(newTable));
        tableList.add(newTable);

        // Actualizar la ventana
        pnlTablas.revalidate();
        pnlTablas.repaint();
    }

    @Override
    public Image getIconImage() { // Método para obtener y cambiar el icono de la aplicación en la barra del titulo
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("jc/img/jc.png")); // Se obtiene la imagen que se quiere poner como icono de la barra 
        return retValue;
    }

    private void eliminarUltimaTabla() {
        // Verificar si hay tablas para eliminar
        // Remover la tabla de la lista
        tableList.removeAll(tables);
        // Eliminar la tabla del panel
        pnlTablas.removeAll();
        // Actualizar la ventana
        pnlTablas.revalidate();
        pnlTablas.repaint();
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
        jButton1 = new javax.swing.JButton();
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

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/boton_regresar.png"))); // NOI18N
        jButton1.setText("btnRegresar");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 630, 120, 50));

        jPanel1.add(cbxEspecificacionTecnica, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 100, 330, 30));

        btnAceptar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/ok2.png"))); // NOI18N
        btnAceptar.setText("ACEPTAR");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        EspecificacionesGUI.this.dispose(); // Se liberan los recursos de la ventana 
        JOptionPane.showMessageDialog(this, "DATOS DE TABLA ELIMINADOS"); // Se muestra el mensaje de actualización
        eliminarUltimaTabla();
        try {
            EspecificacionesGUI esGUI = new EspecificacionesGUI(usr, dirm, irm, tableAnchoLargo, tableRD); //Se crea la instancia de la clase
            esGUI.setVisible(true); //Se hace visible la ventana
            esGUI.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(EspecificacionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        EspecificacionesGUI.this.dispose(); // Se liberan los recursos de la interfaz
        try {
            int idIR = his.getId(conexion, this.irm); // Se obtiene el id de la hoja de instrucción
            al = als.recuperarTodas(idIR, dirm.getAnchoLargo());
            rd = als.recuperarTodas(idIR, dirm.getAnchoLargo());
            HojaInstruccionGUI hj = new HojaInstruccionGUI(usr, irm, dirm, al, rd); // Se crea una instancia de la interfaz gráfica
            hj.setVisible(true); // Se hace visible la ventana
            hj.setLocationRelativeTo(null); // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema 
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(EspecificacionesGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            int idIR = his.getId(conexion, this.irm);
            List ald = als.recuperarTodas(idIR, dirm.getAnchoLargo());
            List rdd = rds.recuperarTodas(idIR, dirm.getAnchoLargo());
            String HojaIns = this.xls.generarHojaInstruccion(conexion, this.dirm, this.irm, this.tableAnchoLargo, tableList, ald, rdd);
            this.irm.setHojaIns(this.irs.leerArchivo(HojaIns));
            this.irs.subirHI(conexion, this.irm);

            EspecificacionesGUI.this.dispose();
            InspeccionReciboGUI irGUI = new InspeccionReciboGUI(usr);
            irGUI.setVisible(true);
            irGUI.setLocationRelativeTo(null);

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejecutar al guardar la hoja de instrucción ESPECIFICACIONESGUI: " + ex.getMessage());
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCalibre;
    private javax.swing.JLabel lblEspecificacionTecnica;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JPanel pnlTablas;
    // End of variables declaration//GEN-END:variables

}
