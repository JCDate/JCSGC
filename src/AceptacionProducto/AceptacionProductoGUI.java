package AceptacionProducto;

import Modelos.AceptacionProducto;
import Modelos.Iconos;
import Modelos.Usuarios;
import Servicios.AceptacionProductoServicio;
import Servicios.Conexion;
import Servicios.Utilidades;
import Servicios.imgTabla;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import swing.Button;

public class AceptacionProductoGUI extends javax.swing.JFrame {

    // Atributos
    private Usuarios usuario; // Usuario autenticado en la aplicación
    private Connection conexion; // Conexión a la Base de Datos
    private AceptacionProductoServicio aps; // Servicio para manejar la aceptación de productos
    private DefaultTableModel modeloTabla; // Define la estructura de la tabla
    private TableRowSorter<DefaultTableModel> filtroTabla; // Permite filtraer y ordenar la tabla de acuerdo a los criterios definidos
    private List<AceptacionProducto> listaAceptacionProducto; // Listas de información para la gestión de los archivos de aceptación de productos

    // Columnas de la tabla
    private static final int COLUMNA_COMPONENTE = 0;
    private static final int COLUMNA_ARCHIVO_RETENCION = 1;

    public AceptacionProductoGUI() {
        inicializarVentanaYComponentes();
    }

    public AceptacionProductoGUI(Usuarios usuario) {
        this.usuario = usuario;
        inicializarVentanaYComponentes();
    }

    @Override
    public Image getIconImage() { // Método para cambiar el icono en la barra del titulo
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("jc/img/jc.png"));
        return retValue;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblJCIcono = new javax.swing.JLabel();
        txtBuscador = new swing.TextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAceptacionProducto = new javax.swing.JTable();
        btnActualizar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnEliminar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnModificar = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        btnCrear = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));
        getContentPane().add(txtBuscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 50, 200, -1));
        txtBuscador.getAccessibleContext().setAccessibleParent(this);

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Wide Latin", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(10, 110, 255));
        jLabel1.setText("ACEPTACIÓN DEL PRODUCTO");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, -1, -1));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 130, 50));

        tblAceptacionProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Componente", "Ver Rentención Dimensional"
            }
        ));
        tblAceptacionProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAceptacionProductoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAceptacionProducto);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 710, -1));

        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/actualizar.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 210, 50));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/Eliminar.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 210, 50));

        btnModificar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/modificar.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 210, 50));

        btnCrear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCrear.setForeground(new java.awt.Color(255, 255, 255));
        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        btnCrear.setText("NUEVA ENTRADA");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        jPanel1.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 210, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        cerrarVentana();
        aps.abrirModificarAPGUI(usuario);
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void tblAceptacionProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAceptacionProductoMouseClicked
        int columnaSeleccionada = tblAceptacionProducto.getColumnModel().getColumnIndexAtX(evt.getX());
        int filaSeleccionada = tblAceptacionProducto.rowAtPoint(evt.getPoint());
        if (Utilidades.esCeldaValida(tblAceptacionProducto, filaSeleccionada, columnaSeleccionada)) {
            manejarCeldaSeleccionada(filaSeleccionada, columnaSeleccionada);
        }
    }//GEN-LAST:event_tblAceptacionProductoMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblAceptacionProducto.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
            return;
        }

        String nombreComponente = (String) tblAceptacionProducto.getValueAt(filaSeleccionada, 0);

        if (confirmarEliminacion()) {
            eliminarComponente(nombreComponente);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS");
        aps.abrirAceptacionProductoGUI(usuario);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        cerrarVentana();
        aps.abrirAceptacionProductoGUI2(usuario);
    }//GEN-LAST:event_btnCrearActionPerformed

    private void inicializarVentanaYComponentes() {
        try {
            configurarVentana();
            this.conexion = Conexion.getInstance().conectar();
            this.aps = new AceptacionProductoServicio();
            configurarBuscador();
            inicializarTabla();
            inicializarListeners();
            inicializarFiltroTabla();
        } catch (SQLException ex) {
            Utilidades.manejarExcepcion("Error al Abrir AceptacionProductoGUI: ", ex);
            Logger.getLogger(AceptacionProductoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configurarVentana() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void configurarBuscador() {
        txtBuscador.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/find.png")));
        txtBuscador.setHint("Buscar...");
    }

    private void inicializarTabla() {
        try {
            this.modeloTabla = construirModeloTabla();
            this.listaAceptacionProducto = aps.obtenerAceptacionProducto(conexion);
            tblAceptacionProducto.setModel(modeloTabla);
            tblAceptacionProducto.setRowHeight(40);
            mostrarDatosTabla();
        } catch (SQLException | ClassNotFoundException ex) {
            Utilidades.manejarExcepcion("Error al inicializar la tabla: ", ex);
            Logger.getLogger(AceptacionProductoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private DefaultTableModel construirModeloTabla() {
        final String[] nombresColumnas = {"COMPONENTE", "VER DOCUMENTO RETENCIÓN DIMENSIONAL"};

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabla.setColumnIdentifiers(nombresColumnas);

        return modeloTabla;
    }

    private void mostrarDatosTabla() throws SQLException, ClassNotFoundException {
        limpiarTabla();
        llenarTabla();
        configurarRenderizacionTabla();
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    private void llenarTabla() {
        if (listaAceptacionProducto != null && !listaAceptacionProducto.isEmpty()) {
            listaAceptacionProducto.forEach(aceptacionProducto -> {
                Object[] fila = crearFila(aceptacionProducto);
                modeloTabla.addRow(fila);
            });
        }
    }

    private Object[] crearFila(AceptacionProducto aceptacionProducto) {
        Object[] fila = new Object[2];
        fila[COLUMNA_COMPONENTE] = aceptacionProducto.getComponente();
        fila[COLUMNA_ARCHIVO_RETENCION] = Utilidades.crearBoton(aceptacionProducto.getArchivo(), Iconos.ICONO_EXCEL_2, "Vacío");
        return fila;
    }

    private void configurarRenderizacionTabla() {
        tblAceptacionProducto.setDefaultRenderer(Object.class, new imgTabla());
    }

    private void inicializarListeners() {
        txtBuscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent ke) {
                filtrarBusqueda();
            }
        });
    }

    private void filtrarBusqueda() {
        String textoBusqueda = txtBuscador.getText();

        if (textoBusqueda == null || textoBusqueda.trim().isEmpty()) {
            filtroTabla.setRowFilter(null);
            return;
        }

        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter(textoBusqueda, 0);
        filtroTabla.setRowFilter(rowFilter);
    }

    private void inicializarFiltroTabla() {
        filtroTabla = new TableRowSorter(tblAceptacionProducto.getModel());
        tblAceptacionProducto.setRowSorter(filtroTabla);
    }

    private void cerrarVentana() {
        AceptacionProductoGUI.this.dispose();
        Conexion.getInstance().desconectar(conexion);
    }

    private void manejarCeldaSeleccionada(int filaSeleccionada, int columnaSeleccionada) {
        String componente = (String) tblAceptacionProducto.getValueAt(filaSeleccionada, 0);
        Object valor = tblAceptacionProducto.getValueAt(filaSeleccionada, columnaSeleccionada);
        if (valor instanceof JButton) {
            JButton boton = (Button) valor;
            procesarBoton(boton, componente, columnaSeleccionada);
        }
    }

    private void procesarBoton(JButton boton, String componente, int columnaSeleccionada) {
        try {
            String textoBoton = boton.getText();
            switch (textoBoton) {
                case "Vacío":
                    JOptionPane.showMessageDialog(this, "No hay archivo");
                    break;
                default:
                    aps.ejecutarArchivoXLSX(conexion, componente, columnaSeleccionada);
                    break;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Utilidades.manejarExcepcion("ERROR al Ejecutar el archivo xlsx", ex);
            Logger.getLogger(AceptacionProductoGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean confirmarEliminacion() {
        int respuesta = JOptionPane.showConfirmDialog(this, "SE ELIMINARÁ TODA LA INFORMACIÓN DEL COMPONENTE, ¿ESTÁS DE ACUERDO?", "ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }

    private void eliminarComponente(String componente) {
        aps.eliminarAceptacionProducto(conexion, componente);
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS CORRECTAMENTE");
        aps.abrirAceptacionProductoGUI(usuario);
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
            java.util.logging.Logger.getLogger(AceptacionProductoGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AceptacionProductoGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JTable tblAceptacionProducto;
    private swing.TextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
