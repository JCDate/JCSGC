package BotonesAccion;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableActionCellEditor extends DefaultCellEditor {

    private TableActionEvent event;
    private boolean showEdit;
    private boolean showDelete;
    private boolean showView;
    private boolean showOpenRecords;
    private boolean showAccept;
    private boolean showReject;

    // Constructor con las opciones para mostrar/ocultar botones
    public TableActionCellEditor(TableActionEvent event, boolean showEdit, boolean showDelete, boolean showView, boolean showOpenRecords, boolean showAccept, boolean showReject) {
        super(new JCheckBox());
        this.event = event;
        this.showEdit = showEdit;
        this.showDelete = showDelete;
        this.showView = showView;
        this.showOpenRecords = showOpenRecords;
        this.showAccept = showAccept;
        this.showReject = showReject;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean isSelected, int row, int column) {
        // Crear un PanelAction con las opciones adecuadas
        PanelAction action = new PanelAction(showEdit, showDelete, showView, showOpenRecords, showAccept, showReject);

        // Iniciar el evento con la fila correspondiente
        action.initEvent(event, row);

        // Configurar el fondo según la selección
        action.setBackground(jtable.getSelectionBackground());

        // Retornar el PanelAction configurado
        return action;
    }
}
