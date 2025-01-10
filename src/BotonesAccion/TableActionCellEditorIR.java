package BotonesAccion;

import Modelos.InspeccionReciboM;
import java.awt.Component;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableActionCellEditorIR extends DefaultCellEditor {

    private TableActionEventIR event;
    private List<InspeccionReciboM> listaInspeccionRecibo; // Lista de registros de Inspección Recibo

    public TableActionCellEditorIR(TableActionEventIR event, List<InspeccionReciboM> listaInspeccionRecibo) {
        super(new JCheckBox());
        this.event = event;
        this.listaInspeccionRecibo = listaInspeccionRecibo;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int row, int column) {
        PanelActionIR action = new PanelActionIR();

        String rutaHJ = listaInspeccionRecibo.get(row).getRutaHojaInstruccion();
        String rutaFactura = listaInspeccionRecibo.get(row).getRutaFactura();
        String rutaCertificado = listaInspeccionRecibo.get(row).getRutaCertificado();

        actualizarIcono(rutaHJ, "/jc/img/excelC.png", "/BotonesAccion/edit.png", action::setBtnHojaInstruccionIcon);
        actualizarIcono(rutaFactura, "/BotonesAccion/view.png", "/jc/img/upload.png", action::setBtnFacturaIcon);
        actualizarIcono(rutaCertificado, "/BotonesAccion/view.png", "/jc/img/upload.png", action::setBtnCertificadoIcon);

        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
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

