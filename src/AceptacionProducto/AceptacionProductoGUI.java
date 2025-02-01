package AceptacionProducto;

import BotonesAccion.TableActionCellEditor;
import BotonesAccion.TableActionCellRender;
import BotonesAccion.TableActionEvent;
import InspeccionRecibo.InspeccionReciboGUI;
import Modelos.AceptacionProductoM;
import Modelos.Usuarios;
import Paginacion.estilo.PaginationItemRenderStyle1;
import Servicios.AceptacionProductoServicio;
import Servicios.Conexion;
import Servicios.Utilidades;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class AceptacionProductoGUI extends javax.swing.JFrame {

    // Atributos
    private Usuarios usuario; // Usuario autenticado en la aplicación
    private Connection conexion; // Conexión a la Base de Datos
    private AceptacionProductoServicio aps; // Servicio para manejar la aceptación de productos
    private DefaultTableModel modeloTabla; // Definición de la estructura de la tabla
    private TableRowSorter<DefaultTableModel> trs; // filtrado de los campos de la tabla
    private TableRowSorter<DefaultTableModel> filtroTabla; // Permite filtraer y ordenar la tabla de acuerdo a los criterios definidos
    private List<AceptacionProductoM> listaAceptacionProducto; // Listas de información para la gestión de los archivos de aceptación de productos

    // Columnas de la tabla
    private static final int COLUMNA_COMPONENTE = 0;
    private static final int COLUMNA_OPERACIONES = 1;

    public AceptacionProductoGUI() {
        initComponents();
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
        btnCrear = new swing.Button(new Color(255, 214, 125),new Color(255, 200, 81));
        jPanel2 = new javax.swing.JPanel();
        paginacion1 = new Paginacion.Pagination();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        getContentPane().add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 5, -1, -1));

        txtBuscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscadorActionPerformed(evt);
            }
        });
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
                "COMPONENTE", "OPERACIONES"
            }
        ));
        tblAceptacionProducto.setPreferredSize(new java.awt.Dimension(150, 350));
        tblAceptacionProducto.setRowHeight(50);
        jScrollPane1.setViewportView(tblAceptacionProducto);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 710, 380));

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

        btnCrear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCrear.setForeground(new java.awt.Color(255, 255, 255));
        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/1004733.png"))); // NOI18N
        btnCrear.setText("NUEVA ENTRADA");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        jPanel1.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 210, 50));

        jPanel2.setBackground(new java.awt.Color(32, 163, 211));

        paginacion1.setOpaque(false);
        jPanel2.add(paginacion1);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 490, 690, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        cerrarVentana();
        aps.abrirAceptacionProductoGUI2(usuario);
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cerrarVentana();
        JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS");
        aps.abrirAceptacionProductoGUI(usuario);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtBuscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscadorActionPerformed
        filtrarBusqueda();
    }//GEN-LAST:event_txtBuscadorActionPerformed

    private void inicializarVentanaYComponentes() {
        try {
            configurarVentana();
            inicializarAtributos();
            configurarBuscador();
            inicializarTabla(1);
            configurarPaginacion();
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

    private void inicializarAtributos() throws SQLException {
        this.aps = new AceptacionProductoServicio();
        this.conexion = Conexion.getInstance().conectar();
        this.modeloTabla = (DefaultTableModel) tblAceptacionProducto.getModel();
    }

    private void configurarBuscador() {
        txtBuscador.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/find.png")));
        txtBuscador.setHint("Buscar...");
    }

    private void inicializarTabla(int page) {
        try {
            configurarModeloYFiltro();
            mostrarDatosTabla(page);
            configurarAccionesTabla();
        } catch (SQLException | ClassNotFoundException ex) {
            Utilidades.manejarExcepcion("Error al inicializar la tabla: ", ex);
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configurarModeloYFiltro() {
        this.modeloTabla = (DefaultTableModel) tblAceptacionProducto.getModel();
        this.trs = new TableRowSorter(tblAceptacionProducto.getModel());
        tblAceptacionProducto.setRowSorter(trs);
    }

    private void mostrarDatosTabla(int pagina) throws SQLException, ClassNotFoundException {
        limpiarTabla();
        String filtro = txtBuscador.getText();
        int limiteFilas = 7; // Cantidad de Filas por página
        int cantidad = aps.contarRegistros(conexion, filtro); // Obtener la cantidad total de registros
        int paginasTotales = (int) Math.ceil((double) cantidad / limiteFilas);

        cargarDatosTabla(pagina, limiteFilas, filtro);
        llenarTabla();
        paginacion1.setPagegination(pagina, paginasTotales);
    }

    private void cargarDatosTabla(int pagina, int limiteFilas, String filtro) throws SQLException {
        this.listaAceptacionProducto = aps.obtenerAceptacionProducto(conexion, pagina, limiteFilas, filtro);
    }

    private void configurarPaginacion() {
        paginacion1.addEventPagination(pagina -> inicializarTabla(pagina));
        paginacion1.setPaginationItemRender(new PaginationItemRenderStyle1());
    }

    private void configurarAccionesTabla() {
        TableActionEvent event = crearTableActionEvent();
        tblAceptacionProducto.getColumnModel().getColumn(COLUMNA_OPERACIONES).setCellRenderer(new TableActionCellRender(true, true, true, false, false, false));
        tblAceptacionProducto.getColumnModel().getColumn(COLUMNA_OPERACIONES).setCellEditor(new TableActionCellEditor(event, true, true, true, false, false, false));
    }

    private TableActionEvent crearTableActionEvent() {
        return new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                cerrarVentana();
                aps.abrirModificarAPGUI(usuario, listaAceptacionProducto.get(row));
            }

            @Override
            public void onDelete(int row) {
                if (Utilidades.confirmarEliminacion()) {
                    eliminarDocumento(listaAceptacionProducto.get(row));
                }
            }

            @Override
            public void onView(int row) {
                Utilidades.abrirDocumento(listaAceptacionProducto.get(row).getRutaArchivo());
            }

            @Override
            public void onOpenRecords(int row) {
                // Nada...
            }

            @Override
            public void onAccept(int row) {
                // Nada...
            }

            @Override
            public void onReject(int row) {
                // Nada...
            }
        };
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

    private Object[] crearFila(AceptacionProductoM aceptacionProducto) {
        Object[] fila = new Object[2];
        fila[COLUMNA_COMPONENTE] = aceptacionProducto.getComponente();
        fila[COLUMNA_OPERACIONES] = "OPERACIONES";
        return fila;
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

    private void eliminarDocumento(AceptacionProductoM aceptacionProducto) {
        aps.eliminarAceptacionProducto(conexion, aceptacionProducto);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJCIcono;
    private Paginacion.Pagination paginacion1;
    private javax.swing.JTable tblAceptacionProducto;
    private swing.TextField txtBuscador;
    // End of variables declaration//GEN-END:variables
}
