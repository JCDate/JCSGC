package InspeccionRecibo;

import Modelos.Iconos;
import Servicios.imgTabla;
import Modelos.InspeccionReciboM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.GeneradorExcel;
import Servicios.InspeccionReciboServicio;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import swing.Button;

public class InspeccionReciboGUI extends javax.swing.JFrame {

    // Usuario y Conexión a la base de datos
    private Usuarios usuario;
    private Connection conexion;

    // Definición de la estructura de la tabla
    private DefaultTableModel modeloTabla;

    // Servicios y Utilidades
    private InspeccionReciboServicio irs = new InspeccionReciboServicio();
    private GeneradorExcel excel = new GeneradorExcel();

    // Lista de registros de Inspección Recibo
    private List<InspeccionReciboM> listaInspeccionRecibo;

    // Objeto filtrador de campos de la tabla
    private TableRowSorter<DefaultTableModel> trs;

    // Columnas de la tabla
    private static final int COLUMN_NO_HOJA = 0;
    private static final int COLUMN_FECHA = 1;
    private static final int COLUMN_PROVEEDOR = 2;
    private static final int COLUMN_NO_FACTURA = 3;
    private static final int COLUMN_NO_PEDIDO = 4;
    private static final int COLUMN_CALIBRE = 5;
    private static final int COLUMN_PRESENTACION_LAMINA = 6;
    private static final int COLUMN_NO_ROLLO = 7;
    private static final int COLUMN_PZKG = 8;
    private static final int COLUMN_ESTATUS = 9;
    private static final int COLUMN_VER_FACTURA = 10;
    private static final int COLUMN_VER_CERTIFICADO = 11;
    private static final int COLUMN_VER_HOJA_INSTRUCCION = 12;

    public InspeccionReciboGUI() {
        inicializarVentanaYComponentes();
    }

    public InspeccionReciboGUI(Usuarios usuario) {
        this.usuario = usuario;
        inicializarVentanaYComponentes();
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

        lblReporteInspeccionRecibo = new javax.swing.JLabel();
        btnModificar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnActualizar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnAgregar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnEliminar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        lblJCIcono = new javax.swing.JLabel();
        btnAgregarCalibre = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInspeccionRecibo = new javax.swing.JTable();
        btnToExcel = new swing.Button(new Color(107, 240, 105),new Color(75, 212, 73));
        txtBuscador = new swing.TextField();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(1280, 570));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblReporteInspeccionRecibo.setBackground(new java.awt.Color(255, 255, 255));
        lblReporteInspeccionRecibo.setFont(new java.awt.Font("Wide Latin", 1, 18)); // NOI18N
        lblReporteInspeccionRecibo.setForeground(new java.awt.Color(10, 110, 255));
        lblReporteInspeccionRecibo.setText("REPORTE DE INSPECCIÓN/RECIBO");
        getContentPane().add(lblReporteInspeccionRecibo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 660, 50));

        btnModificar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setMaximumSize(new java.awt.Dimension(115, 40));
        btnModificar.setMinimumSize(new java.awt.Dimension(115, 40));
        btnModificar.setPreferredSize(new java.awt.Dimension(115, 40));
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 250, 130, 40));

        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/actualizar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setMaximumSize(new java.awt.Dimension(115, 40));
        btnActualizar.setMinimumSize(new java.awt.Dimension(115, 40));
        btnActualizar.setPreferredSize(new java.awt.Dimension(115, 40));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        getContentPane().add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 310, 130, 40));

        btnAgregar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 190, 130, 40));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setMaximumSize(new java.awt.Dimension(115, 40));
        btnEliminar.setMinimumSize(new java.awt.Dimension(115, 40));
        btnEliminar.setPreferredSize(new java.awt.Dimension(115, 40));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 370, 130, 40));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.setBorderPainted(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 120, 50));

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        btnAgregarCalibre.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAgregarCalibre.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCalibre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        btnAgregarCalibre.setText("AGREGAR CALIBRE");
        btnAgregarCalibre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCalibreActionPerformed(evt);
            }
        });
        getContentPane().add(btnAgregarCalibre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 180, -1));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tblInspeccionRecibo.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        tblInspeccionRecibo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No.", "FECHA DE FACTURA", "PROVEEDOR", "NO. FACTURA", "NO. PEDIDO", "CALIBRE", "PRESENTACIÓN DE LAMINA", "NO. ROLLO", "PZ/Kg", "ESTATUS", "VER FACTURA", "VER CERTIFICADO", "HOJA DE INSTRUACCIÓN"
            }
        ));
        tblInspeccionRecibo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblInspeccionRecibo.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        tblInspeccionRecibo.setMinimumSize(new java.awt.Dimension(600, 500));
        tblInspeccionRecibo.setPreferredSize(new java.awt.Dimension(600, 500));
        tblInspeccionRecibo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInspeccionReciboMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblInspeccionRecibo);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 139, 1130, -1));

        btnToExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/excel.png"))); // NOI18N
        btnToExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToExcelActionPerformed(evt);
            }
        });
        getContentPane().add(btnToExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 10, 80, 80));

        txtBuscador.setPrefixIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/find.png"))); // NOI18N
        txtBuscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscadorKeyTyped(evt);
            }
        });
        getContentPane().add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 95, 240, 40));

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jPanel1.setMinimumSize(new java.awt.Dimension(130, 350));
        jPanel1.setPreferredSize(new java.awt.Dimension(130, 350));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        cerrarVentana();
        irs.abrirAgregarIrGUI(usuario);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS");
        irs.abrirInspeccionReciboGUI(usuario);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblInspeccionRecibo.getSelectedRow();
        if (filaSeleccionada != -1) {

            String noHoja = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_NO_HOJA);
            String fechaFactura = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_FECHA);
            String noFactura = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_NO_FACTURA);
            String noPedido = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_NO_PEDIDO);
            String pzKg = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_PZKG);

            int respuesta = JOptionPane.showConfirmDialog(this, "LA INFORMACIÓN SELECCIONADA SE ELIMINARÁ,¿ESTÁS DE ACUERDO?", "ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (respuesta == JOptionPane.YES_NO_OPTION) {
                eliminarRegistro(noHoja, fechaFactura, noFactura, noPedido, pzKg);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int filaSeleccionada = tblInspeccionRecibo.getSelectedRow();
        if (filaSeleccionada != -1) {
            cerrarVentana();
            irs.abrirModificarIrGUI(listaInspeccionRecibo.get(filaSeleccionada), usuario);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void tblInspeccionReciboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInspeccionReciboMouseClicked
        int columnaSeleccionada = tblInspeccionRecibo.getColumnModel().getColumnIndexAtX(evt.getX());
        int filaSeleccionada = tblInspeccionRecibo.rowAtPoint(evt.getPoint());

        if (esCeldaValida(filaSeleccionada, columnaSeleccionada)) {
            String noHoja = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, 0);
            int posicion = -1;

            for (int i = 0; i < listaInspeccionRecibo.size(); i++) {
                InspeccionReciboM elemento = listaInspeccionRecibo.get(i);
                if (elemento.getNoHoja().equals(noHoja)) {
                    posicion = i;
                    break;
                }
            }

            Object value = tblInspeccionRecibo.getValueAt(filaSeleccionada, columnaSeleccionada);

            if (value instanceof JButton) {
                JButton boton = (Button) value;
                String textoBoton = boton.getText();
                switch (textoBoton) {
                    case "Vacio":
                        JOptionPane.showMessageDialog(null, "No hay archivo");
                        break;
                    case "Realizar":
                        cerrarVentana();
                        irs.abrirHojaInstruccionGUI(usuario, listaInspeccionRecibo.get(posicion));
                        break;
                    default:
                        try {
                            if (columnaSeleccionada == 10 || columnaSeleccionada == 11) {
                                irs.ejecutarArchivoPDF(noHoja, columnaSeleccionada);
                            } else if (columnaSeleccionada == 12) {
                                irs.ejecutarArchivoXLSX(noHoja, columnaSeleccionada);
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
            }
        }
    }//GEN-LAST:event_tblInspeccionReciboMouseClicked

    private void btnToExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToExcelActionPerformed
        try {
            excel.generarInspeccionReciboXLS(); // Generar el excel
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(this, "Datos Exportados.");
    }//GEN-LAST:event_btnToExcelActionPerformed

    private void txtBuscadorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadorKeyTyped
        txtBuscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent ke) {
                String cadena = (txtBuscador.getText());
                txtBuscador.setText(cadena);
                filtrarTabla();
            }
        });
        trs = new TableRowSorter(tblInspeccionRecibo.getModel());
        tblInspeccionRecibo.setRowSorter(trs);
    }//GEN-LAST:event_txtBuscadorKeyTyped

    private void btnAgregarCalibreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCalibreActionPerformed
        try {
            AgregarCalibreHIGUI irGUI = new AgregarCalibreHIGUI(usuario);
            irGUI.setVisible(true);
            irGUI.setLocationRelativeTo(null);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgregarCalibreActionPerformed

    private void inicializarVentanaYComponentes() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.conexion = Conexion.getInstance().getConnection();

        txtBuscador.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/find.png")));
        txtBuscador.setHint("Buscar...");

        modeloTabla = construirModeloTabla();

        tblInspeccionRecibo.setModel(modeloTabla);
        trs = new TableRowSorter<>(modeloTabla);
        tblInspeccionRecibo.setRowSorter(trs);
        tblInspeccionRecibo.setRowHeight(27);
        tblInspeccionRecibo.setPreferredSize(new Dimension(4500, 4500));  // CONSIDERAR QUITARLAS
        tblInspeccionRecibo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // CONSIDERAR QUITARLAS
        mostrarDatosTabla();
    }

    private DefaultTableModel construirModeloTabla() {
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] nombresColumnas = {"NO.", "FECHA DE FACTURA", "PROVEEDOR", "FACTURA", "PEDIDO",
            "CALIBRE", "PRESENTACIÓN", "ROLLO", "Pz/Kg.", "ESTATUS",
            "VER FACTURA", "VER CERTIFICADO", "VER HOJA DE INSTRUCCIONES"};

        modeloTabla.setColumnIdentifiers(nombresColumnas); // Establecer los nombres de las columnas en el modelo de tabla.

        return modeloTabla;
    }

    public void mostrarDatosTabla() {
        try {
            modeloTabla.setRowCount(0);
            listaInspeccionRecibo = irs.recuperarTodas(conexion);

            if (listaInspeccionRecibo != null) {
                listaInspeccionRecibo.stream().map((ir) -> { // Se utiliza la expresión lambda y las funcion stream para el manejo de la información
                    Object fila[] = new Object[13];
                    fila[COLUMN_NO_HOJA] = ir.getNoHoja();
                    fila[COLUMN_FECHA] = ir.getFechaFactura();
                    fila[COLUMN_PROVEEDOR] = ir.getProveedor();
                    fila[COLUMN_NO_FACTURA] = ir.getNoFactura();
                    fila[COLUMN_NO_PEDIDO] = ir.getNoPedido();
                    fila[COLUMN_CALIBRE] = ir.getCalibre();
                    fila[COLUMN_PRESENTACION_LAMINA] = ir.getpLamina();
                    fila[COLUMN_NO_ROLLO] = ir.getNoRollo();
                    fila[COLUMN_PZKG] = ir.getPzKg();
                    fila[COLUMN_ESTATUS] = ir.getEstatus();

                    // Se crean los botones para el resto de campos
                    fila[COLUMN_VER_FACTURA] = irs.crearBoton(ir.getFacturapdf(), Iconos.ICONO_PDF, "Vacío");
                    fila[COLUMN_VER_CERTIFICADO] = irs.crearBoton(ir.getCertificadopdf(), Iconos.ICONO_PDF, "Vacío");
                    fila[COLUMN_VER_HOJA_INSTRUCCION] = irs.crearBoton(ir.getHojaIns(), Iconos.ICONO_EXCEL_2, "Realizar");
                    return fila;
                }).forEachOrdered((fila) -> { // Cada elemento que se encuentra se agrega como fila a la tabla
                    modeloTabla.addRow(fila);
                });
            }
            tblInspeccionRecibo.setDefaultRenderer(Object.class, new imgTabla());
        } catch (SQLException ex) {
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error al cargar los registros de inspección Recibo: " + ex);
        }
    }

    public void cerrarVentana() {
        InspeccionReciboGUI.this.dispose();
    }

    private void eliminarRegistro(String noHoja, String fechaFactura, String noFactura, String noPedido, String pzKg) {
        try {
            this.irs.eliminar(noHoja, fechaFactura, noFactura, noPedido, pzKg); // Se llama el método de eliminar
            InspeccionReciboGUI.this.dispose(); // Se liberan los recursos de la ventana
            JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS"); // Se muestra el mensaje de confirmación de la eliminación
            this.irs.abrirInspeccionReciboGUI(usuario);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void filtrarTabla() {
        String filtro = txtBuscador.getText();
        try {
            RowFilter<DefaultTableModel, Object> filtroFila = RowFilter.regexFilter(filtro, 2, 3, 5, 7);
            trs.setRowFilter(filtroFila);
        } catch (PatternSyntaxException e) {
            trs.setRowFilter(null);
        }
    }

    private boolean esCeldaValida(int fila, int columna) {
        return fila >= 0 && fila < tblInspeccionRecibo.getRowCount()
                && columna >= 0 && columna < tblInspeccionRecibo.getColumnCount();
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
            java.util.logging.Logger.getLogger(InspeccionReciboGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new InspeccionReciboGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarCalibre;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnToExcel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblReporteInspeccionRecibo;
    private javax.swing.JTable tblInspeccionRecibo;
    private swing.TextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
