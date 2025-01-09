package BotonesAccion;

import Modelos.InspeccionReciboM;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
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
        if (rutaHJ != null && !rutaHJ.isEmpty()) {
            action.setBtnHojaInstruccionIcon("/jc/img/excelC.png");
        } else {
            action.setBtnHojaInstruccionIcon("/BotonesAccion/edit.png");
        }

        if (isSeleted == false && row % 2 == 0) {
            action.setBackground(Color.WHITE);
        } else {
            action.setBackground(com.getBackground());
        }
        return action;
    }
}
