package BotonesAccion;

import Modelos.InspeccionReciboM;
import java.awt.Component;
import java.util.List;
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
        if (rutaHJ != null && !rutaHJ.isEmpty()) {
            action.setBtnHojaInstruccionIcon("/jc/img/excelC.png");
        } else {
            action.setBtnHojaInstruccionIcon("/BotonesAccion/edit.png");
        }
        
        
        action.initEvent(event, row);
        action.setBackground(jtable.getSelectionBackground());
        return action;
    }
}