package BotonesAccion;

import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.ImageIcon;

public class PanelActionIR extends javax.swing.JPanel {

    public PanelActionIR() {
        initComponents();
    }

    public void initEvent(TableActionEventIR event, int row) {
        btnVerFactura.addActionListener((ActionEvent ae) -> {
            event.onViewFactura(row);
        });

        btnVerCertificado.addActionListener((ActionEvent ae) -> {
            event.onViewCertificado(row);
        });

        btnHojaInstruccion.addActionListener((ActionEvent ae) -> {
            event.onHojaInstruccion(row);
        });
    }

    public void setBtnHojaInstruccionIcon(String rutaIcon) {
        URL iconURL = getClass().getResource(rutaIcon);
        btnHojaInstruccion.setIcon(new ImageIcon(iconURL));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnVerFactura = new BotonesAccion.ActionButton();
        btnVerCertificado = new BotonesAccion.ActionButton();
        btnHojaInstruccion = new BotonesAccion.ActionButton();

        btnVerFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesAccion/view.png"))); // NOI18N

        btnVerCertificado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesAccion/view.png"))); // NOI18N

        btnHojaInstruccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BotonesAccion/edit.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnVerFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVerCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHojaInstruccion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnVerFactura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVerCertificado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnHojaInstruccion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private BotonesAccion.ActionButton btnHojaInstruccion;
    private BotonesAccion.ActionButton btnVerCertificado;
    private BotonesAccion.ActionButton btnVerFactura;
    // End of variables declaration//GEN-END:variables
}
