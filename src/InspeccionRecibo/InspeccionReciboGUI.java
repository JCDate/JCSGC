package InspeccionRecibo;

import Servicios.imgTabla;
import Modelos.InspeccionReciboM;
import Modelos.Usuarios;
import Servicios.Conexion;
import Servicios.InspeccionReciboServicio;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import swing.Button;

public class InspeccionReciboGUI extends javax.swing.JFrame {

    private final InspeccionReciboServicio irs = new InspeccionReciboServicio();
    private List<InspeccionReciboM> irm; 
    private Connection conexion;
    private Usuarios usr; 
    private final toExcel excel = new toExcel();
    private TableRowSorter<DefaultTableModel> trs;

    // Constantes para los índices de columnas
    private static final int COLUMN_NO_HOJA = 0;
    private static final int COLUMN_FECHA = 1;
    private static final int COLUMN_PROVEEDOR = 2;
    private static final int COLUMN_NO_FACTURA = 3;
    private static final int COLUMN_NO_PEDIDO = 4;
    private static final int COLUMN_CALIBRE = 5;
    private static final int COLUMN_P_LAMINA = 6;
    private static final int COLUMN_NO_ROLLO = 7;
    private static final int COLUMN_PZKG = 8;
    private static final int COLUMN_ESTATUS = 9;
    private static final int COLUMN_VER_FACTURA = 10;
    private static final int COLUMN_VER_CERTIFICADO = 11;
    private static final int COLUMN_VER_HOJA_INSTRUCCION = 12;

    public InspeccionReciboGUI(Usuarios usr) throws SQLException, ClassNotFoundException {
        initComponentes();
        this.usr = usr;
        try {
            this.InspeccionReciboGUI();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InspeccionReciboGUI() throws SQLException, ClassNotFoundException {
        initComponentes(); // Se inicializan los componente principales
    }

    private void InspeccionReciboGUI() throws SQLException, ClassNotFoundException {
        tblInspeccionRecibo.setDefaultRenderer(Object.class, new imgTabla()); //Se configura el renderizador de la tabla

        txtBuscador.setPrefixIcon(new ImageIcon(getClass().getResource("/icon/find.png")));
        txtBuscador.setHint("Buscar...");

        // Construir el modelo de tabla
        DefaultTableModel dt = buildTableModel(); // Se asigna la tabla con el modelo por default

        // Se obtienen los iconos 
        ImageIcon iconoPdf = new ImageIcon(getImage("/jc/img/PDF.png"));
        ImageIcon iconoExcel = new ImageIcon(getImage("/jc/img/excelC.png"));

        this.irm = this.irs.recuperarTodas(conexion); // Se recupera la información de la BD de la tabla "inspeccionrecibo"
        if (this.irm.size() > 0) { // Si el tamaño de la lista es mayor a 0
            for (int i = 0; i < this.irm.size(); i++) { // Se recorren todos los elementos de la lista
                Object fila[] = new Object[13]; // Se crea el arreglo para guardar los valores

                // Se captura la información en el arreglo
                fila[COLUMN_NO_HOJA] = this.irm.get(i).getNoHoja();
                fila[COLUMN_FECHA] = this.irm.get(i).getFechaFactura();
                fila[COLUMN_PROVEEDOR] = this.irm.get(i).getProveedor();
                fila[COLUMN_NO_FACTURA] = this.irm.get(i).getNoFactura();
                fila[COLUMN_NO_PEDIDO] = this.irm.get(i).getNoPedido();
                fila[COLUMN_CALIBRE] = this.irm.get(i).getCalibre();
                fila[COLUMN_P_LAMINA] = this.irm.get(i).getpLamina();
                fila[COLUMN_NO_ROLLO] = this.irm.get(i).getNoRollo();
                fila[COLUMN_PZKG] = this.irm.get(i).getPzKg();
                fila[COLUMN_ESTATUS] = this.irm.get(i).getEstatus();

                // Se crean los botones para el resto de campos
                fila[COLUMN_VER_FACTURA] = this.irs.crearBoton(this.irm.get(i).getFacturapdf(), iconoPdf, "Vacío");
                fila[COLUMN_VER_CERTIFICADO] = this.irs.crearBoton(this.irm.get(i).getCertificadopdf(), iconoPdf, "Vacío");
                fila[COLUMN_VER_HOJA_INSTRUCCION] = this.irs.crearBoton(this.irm.get(i).getHojaIns(), iconoExcel, "Realizar");
                dt.addRow(fila); // Se añade la fila 
            }

            tblInspeccionRecibo.setModel(dt); // Se define el modelo para la tabla de la ventana principal
            trs = new TableRowSorter<>(dt);
            tblInspeccionRecibo.setRowSorter(trs); // Se establece el objeto que filtra la tabla
            tblInspeccionRecibo.setRowHeight(27); // Se define el tamaño de la altura de las filas
            tblInspeccionRecibo.setPreferredSize(new Dimension(4500, 4500)); // Aumentar la altura; // Se define el tamaño de la altura de las filas
            tblInspeccionRecibo.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Se define el tamaño de la altura de las filas

        }
    }

    public final void initComponentes() throws SQLException, ClassNotFoundException {
        initComponents(); // Inicialización de Componentes
        this.setResizable(false); // Se define que no se puede redimensionar
        this.setDefaultCloseOperation(0); // Se deshabilita el boton de cerrar de la ventana
        this.conexion = Conexion.getInstance().getConnection(); // Obtener la conexión a la base de datos usando el Singleton
    }

    @Override
    public Image getIconImage() { // Método para obtener y cambiar el icono de la aplicación en la barra del titulo
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("jc/img/jc.png")); // Se obtiene la imagen que se quiere poner como icono de la barra 
        return retValue;
    }

    private DefaultTableModel buildTableModel() {
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] columnNames = {"NO.", "FECHA DE FACTURA", "PROVEEDOR", "FACTURA", "PEDIDO",
            "CALIBRE", "PRESENTACIÓN", "ROLLO", "Pz/Kg.", "ESTATUS",
            "VER FACTURA", "VER CERTIFICADO", "VER HOJA DE INSTRUCCIONES"}; // Columnas de la tabla

        dt.setColumnIdentifiers(columnNames); // Establecer los nombres de las columnas en el modelo de tabla.

        return dt;
    }

    public Image getImage(String ruta) {
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource(ruta)); //Se crea un objeto de la clase ImageIcon y se obtiene la dirección de la ruta
            Image mainIcon = imageIcon.getImage(); // Se obtiene la imagen
            return mainIcon; // Se retorna 
        } catch (Exception e) {
        }
        return null;
    }

    private void eliminarRegistro(String noHoja, String fechaFactura, String noFactura, String noPedido, String pzKg) {
        try {
            this.irs.eliminar(noHoja, fechaFactura, noFactura, noPedido, pzKg); // Se llama el método de eliminar
            InspeccionReciboGUI.this.dispose(); // Se liberan los recursos de la ventana
            JOptionPane.showMessageDialog(this, "DATOS ELIMINADOS"); // Se muestra el mensaje de confirmación de la eliminación
            this.irs.goToInspeccionRecibo(usr);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void filtroBuscador() {
        String filtro = txtBuscador.getText();
        try {
            RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter(filtro, 2, 3, 5, 7); // Crea un filtro que coincida en cualquier columna con el texto ingresado
            trs.setRowFilter(rowFilter); // Aplica el filtro al TableRowSorter
        } catch (PatternSyntaxException e) {
            // Si la expresión regular es inválida, no aplicar ningún filtro
            trs.setRowFilter(null);
        }
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
        InspeccionReciboGUI.this.dispose(); // Se liberan los recursos de la ventana actual
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        InspeccionReciboGUI.this.dispose(); // Se liberan los recursos de la ventana actual
        try {
            AgregarIrGUI agregarGUI = new AgregarIrGUI(usr); // Se crea la instancia de la clase AgregarIr
            agregarGUI.setVisible(true); // Se muestra la ventana visible
            agregarGUI.setLocationRelativeTo(null); // Se muestra al centro de la pantalla
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AgregarIrGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        InspeccionReciboGUI.this.dispose(); // Se liberan los recursos de la ventana 
        JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS"); // Se muestra el mensaje de actualización
        irs.goToInspeccionRecibo(this.usr);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tblInspeccionRecibo.getSelectedRow(); // Se obtiene la fila seleccionada
        if (filaSeleccionada != -1) { // Si el indice de la fila es diferente de -1

            String noHoja = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_NO_HOJA);
            String fechaFactura = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_FECHA);
            String noFactura = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_NO_FACTURA);
            String noPedido = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_NO_PEDIDO);
            String pzKg = (String) tblInspeccionRecibo.getValueAt(filaSeleccionada, COLUMN_PZKG);
          
            int resp = JOptionPane.showConfirmDialog(null, "LA INFORMACIÓN SELECCIONADA SE ELIMINARÁ,¿ESTÁS DE ACUERDO?", "ALERTA", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (resp == JOptionPane.YES_NO_OPTION) {
                eliminarRegistro(noHoja, fechaFactura, noFactura, noPedido, pzKg);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int fila_seleccionada = tblInspeccionRecibo.getSelectedRow(); // Se obtiene el indice de la fila seleccionada ... 
        if (fila_seleccionada >= 0) { // Si la fila seleccionada es mayor o igual a 0
            InspeccionReciboGUI.this.dispose(); // Se liberan los recursos de la ventana

            try {
                ModificarIr modificar = new ModificarIr(this.irm.get(fila_seleccionada), usr); // Se crea la instancia de la clase ModificarIr
                modificar.setVisible(true); // Se muestra visible la ventana 
                modificar.setLocationRelativeTo(null); // Se coloca en el centro de la pantalla
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else { // Si no se selecciona ninguna fila...
            JOptionPane.showMessageDialog(this, "Por favor seleccione una fila.");
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void tblInspeccionReciboMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInspeccionReciboMouseClicked
        int column = tblInspeccionRecibo.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = tblInspeccionRecibo.rowAtPoint(evt.getPoint()); // Obtener la fila en base a las coordenadas del evento

        if (row < tblInspeccionRecibo.getRowCount() && row >= 0 && column < tblInspeccionRecibo.getColumnCount() && column >= 0) {// Si las coordenadas estan dentro de los limites de la tabla... 
            String id = (String) tblInspeccionRecibo.getValueAt(row, 0); // Se guarda el valor de la celda (row,0) en la primera columna
            int posicion = -1; // Variable para almacenar la posición del elemento encontrado

            for (int i = 0; i < this.irm.size(); i++) {
                InspeccionReciboM elemento = this.irm.get(i);
                if (elemento.getNoHoja().equals(id)) {
                    posicion = i;
                    break; // Si solo quieres encontrar la primera posición coincidente, puedes salir del bucle
                }
            }

            Object value = tblInspeccionRecibo.getValueAt(row, column); // Se obtiene el valor de la celda en la columna y fila especificados
            if (value instanceof JButton) { // Si el valor de la celda es un boton...
                JButton boton = (Button) value;
                String textoBoton = boton.getText(); // Se obtiene el texto del boton
                switch (textoBoton) { // Según el texto del boton...
                    case "Vacio":
                        JOptionPane.showMessageDialog(null, "No hay archivo");
                        break;
                    case "Realizar":
                        InspeccionReciboGUI.this.dispose(); // Se liberan los recursos del sistema
                        try {
                            HojaInstruccionGUI hj = new HojaInstruccionGUI(usr, this.irm.get(posicion)); // Se crea la instancia para ir a la ventana de HojaInstruccionGUI
                            hj.setVisible(true); // Se pone en visible la ventana
                            hj.setLocationRelativeTo(null);   // Indica que la ventana actual se abrirá al centro de la pantalla principal del sistema
                        } catch (SQLException | ClassNotFoundException ex) {
                            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:
                        try {
                            if (column == 10 || column == 11) { // Si le dio a ver factura o ver certificado
                                irs.ejecutarArchivoPDF(id, column);
                                Desktop.getDesktop().open(new File("nuevoArchivo.pdf"));
                            } else if (column == 12) {
                                irs.ejecutarArchivoXLSX(id, column);
                            }
                        } catch (ClassNotFoundException | SQLException | IOException ex) {
                            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                }
            }
        }
    }//GEN-LAST:event_tblInspeccionReciboMouseClicked

    private void btnToExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToExcelActionPerformed
        try {
            excel.WriteExcelIR(); // Generar el excel
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
                filtroBuscador();
            }
        });
        trs = new TableRowSorter(tblInspeccionRecibo.getModel());
        tblInspeccionRecibo.setRowSorter(trs);
    }//GEN-LAST:event_txtBuscadorKeyTyped

    private void btnAgregarCalibreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCalibreActionPerformed
        try {
            AgregarCalibreHIGUI irGUI = new AgregarCalibreHIGUI(usr); 
            irGUI.setVisible(true); 
            irGUI.setLocationRelativeTo(null); 
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAgregarCalibreActionPerformed

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
            try {
                new InspeccionReciboGUI().setVisible(true);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(InspeccionReciboGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
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
