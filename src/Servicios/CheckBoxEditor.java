/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author JC
 */
public class CheckBoxEditor extends DefaultCellEditor {

    private JCheckBox checkBox;

    public CheckBoxEditor() {
        super(new JCheckBox()); // Se inicializa el constructor de la clase padre, con un nuevo JCheckBox como parámetro
        checkBox = (JCheckBox) getComponent(); // Se obtiene el componente de edición de la celda
        checkBox.setHorizontalAlignment(JCheckBox.CENTER); // Se alinea al centro de las celda
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) { // Este método se llama para la edición de la celda
        if (value instanceof Boolean) { // Si el valor de la celda es de tipo booleano 
            // Si el valor de la celda es de tipo Boolean, establece el estado seleccionado del JCheckBox según el valor de la celda. Si el valor es true, se marca el checkbox
            checkBox.setSelected((Boolean) value);
          
        } else {
            checkBox.setSelected(true); // Valor por defecto
        }
        return checkBox; // Se devuelve el componente indicado
    }
}
