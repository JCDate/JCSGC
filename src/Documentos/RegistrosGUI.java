package Documentos;

import Modelos.Iconos;
import Modelos.Usuarios;
import Modelos.ProcedimientosM;
import Modelos.RegistrosM;
import Servicios.Conexion;
import Servicios.ControlDocumentacionServicio;
import Servicios.imgTabla;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import swing.Button;

public class RegistrosGUI extends javax.swing.JFrame {

    private Conexion conexion;
    private Usuarios usr;
    private ProcedimientosM procedimiento;
    private DefaultTableModel modeloTabla;
    private List<RegistrosM> listaRegistros = new ArrayList<>();
    private ControlDocumentacionServicio cds = new ControlDocumentacionServicio();

    public RegistrosGUI() {
        try {
            inicializarVentanaYComponentes();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RegistrosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RegistrosGUI(Usuarios usr, ProcedimientosM procedimiento) {
        try {
            this.usr = usr;
            this.procedimiento = procedimiento;
            inicializarVentanaYComponentes();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RegistrosGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Image getIconImage() {
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
        lblRegistroCambio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRegistros = new javax.swing.JTable();
        btnCerrar = new swing.Button(new Color(255, 76, 76),new Color(255, 50, 50));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(251, 251, 251));
        jPanel1.setForeground(new java.awt.Color(242, 242, 242));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblJCIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/jcLogo.png"))); // NOI18N
        jPanel1.add(lblJCIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblRegistroCambio.setFont(new java.awt.Font("Wide Latin", 1, 14)); // NOI18N
        lblRegistroCambio.setForeground(new java.awt.Color(10, 110, 255));
        lblRegistroCambio.setText("REGISTROS DE CAMBIOS");
        jPanel1.add(lblRegistroCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, -1, -1));

        tblRegistros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "FECHA", "CÓDIGO", "PROCESO", "PROCEDIMIENTO", "REV. ANTERIOR", "REV. NUEVA", "ENCARGADO", "ACCIÓN", "ARCHIVO", "NOMBRE"
            }
        ));
        jScrollPane1.setViewportView(tblRegistros);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 980, 360));

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jc/img/cancelar.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.setToolTipText("");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 130, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        cerrarVentana();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void inicializarVentanaYComponentes() throws SQLException, ClassNotFoundException {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.conexion = Conexion.getInstance();

        this.modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        listaRegistros = cds.recuperarRegistros(conexion, procedimiento.getId(), usr);

        DefaultTableModel tblModeloDocumentos = construirTabla();
        tblRegistros.setModel(tblModeloDocumentos);
        tblRegistros.setRowHeight(40);
        mostrarDatosTabla();
    }

    private DefaultTableModel construirTabla() {
        modeloTabla.addColumn("FECHA");
        modeloTabla.addColumn("CÓDIGO");
        modeloTabla.addColumn("PROCESO");
        modeloTabla.addColumn("PROCEDIMIENTO");
        modeloTabla.addColumn("REV. ANTERIOR");
        modeloTabla.addColumn("REV. NUEVA");
        modeloTabla.addColumn("ENCARGADO");
        modeloTabla.addColumn("ACCIÓN");
        modeloTabla.addColumn("TIPO ARCHIVO");
        modeloTabla.addColumn("NOMBRE");
        return modeloTabla;
    }

    public void mostrarDatosTabla() throws SQLException, ClassNotFoundException {
        modeloTabla.setRowCount(0);
        if (this.listaRegistros != null) {
            listaRegistros.stream().map((registro) -> { // Se utiliza la expresión lambda y las funcion stream para el manejo de la información
                Object fila[] = new Object[10];
                fila[0] = registro.getFecha();
                fila[1] = registro.getCodigo();
                fila[2] = registro.getProceso();
                fila[3] = registro.getProcedimiento();
                fila[4] = registro.getRevAnterior();
                fila[5] = registro.getRevNueva();
                fila[6] = registro.getEncargado();
                fila[7] = registro.getAccion();
                fila[8] = registro.getTipoArchivo();
                fila[9] = registro.getNombre();

                return fila;
            }).forEachOrdered((fila) -> { // Cada elemento que se encuentra se agrega como fila a la tabla
                modeloTabla.addRow(fila);
            });
        }
        tblRegistros.setDefaultRenderer(Object.class, new imgTabla());
    }

    public void cerrarVentana() {
        RegistrosGUI.this.dispose();
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistrosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new RegistrosGUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJCIcono;
    private javax.swing.JLabel lblRegistroCambio;
    private javax.swing.JTable tblRegistros;
    // End of variables declaration//GEN-END:variables
}
