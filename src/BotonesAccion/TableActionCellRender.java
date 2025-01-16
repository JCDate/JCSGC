package BotonesAccion;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableActionCellRender extends DefaultTableCellRenderer {

    private boolean showEdit;
    private boolean showDelete;
    private boolean showView;
    private boolean showOpenRecords;
    private boolean showAccept;
    private boolean showReject;
    
    public TableActionCellRender(boolean showEdit, boolean showDelete, boolean showView, boolean showOpenRecords, boolean showAccept, boolean showReject) {
        this.showEdit = showEdit;
        this.showDelete = showDelete;
        this.showView = showView;
        this.showOpenRecords = showOpenRecords;
        this.showAccept = showAccept;
        this.showReject = showReject;
    }

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean isSelected, boolean bln1, int row, int column) {
        Component com = super.getTableCellRendererComponent(jtable, o, isSelected, bln1, row, column);

        // Definir qué botones mostrar según el contexto
        PanelAction action = new PanelAction(showEdit, showDelete, showView, showOpenRecords, showAccept, showReject);

        if (!isSelected && row % 2 == 0) {
            action.setBackground(Color.WHITE);
        } else {
            action.setBackground(com.getBackground());
        }

        return action;
    }
}
