package BotonesAccion;

import Modelos.InspeccionReciboM;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableActionCellRenderIR extends DefaultTableCellRenderer {

    private List<InspeccionReciboM> listaInspeccionRecibo; // Lista de registros de Inspección Recibo

    public TableActionCellRenderIR(List<InspeccionReciboM> listaInspeccionRecibo) {
        this.listaInspeccionRecibo = listaInspeccionRecibo;
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSeleted, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        PanelActionIR action = new PanelActionIR();

        String rutaHJ = listaInspeccionRecibo.get(row).getRutaHojaInstruccion();
        String rutaFactura = listaInspeccionRecibo.get(row).getRutaFactura();
        String rutaCertificado = listaInspeccionRecibo.get(row).getRutaCertificado();

        actualizarIcono(rutaHJ, "/jc/img/excelC.png", "/BotonesAccion/edit.png", action::setBtnHojaInstruccionIcon);
        actualizarIcono(rutaFactura, "/BotonesAccion/view.png", "/jc/img/upload.png", action::setBtnFacturaIcon);
        actualizarIcono(rutaCertificado, "/BotonesAccion/view.png", "/jc/img/upload.png", action::setBtnCertificadoIcon);

        if (isSeleted == false && row % 2 == 0) {
            action.setBackground(Color.WHITE);
        } else {
            action.setBackground(com.getBackground());
        }
        return action;
    }

    private void actualizarIcono(String ruta, String iconoDisponible, String iconoNoDisponible, Consumer<String> setIcono) {
        if (ruta != null && !ruta.isEmpty()) {
            setIcono.accept(iconoDisponible);
        } else {
            setIcono.accept(iconoNoDisponible);
        }
    }
}
