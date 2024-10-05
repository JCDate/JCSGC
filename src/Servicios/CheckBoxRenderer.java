/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

/**
 *
 * @author JC
 */
import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CheckBoxRenderer extends DefaultTableCellRenderer {

    private JCheckBox checkBox;
    private int numCeldas;

    public CheckBoxRenderer(int numCeldas) {
        checkBox = new JCheckBox();
        checkBox.setHorizontalAlignment(JCheckBox.CENTER);
        this.numCeldas = numCeldas;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Verificar si la celda está dentro del rango de celdas que deben tener el checkbox
        boolean mostrarCheckbox = (row < numCeldas);
        if (isSelected) {
            renderer.setBackground(Color.BLUE); // Color de fondo cuando la celda está seleccionada
        } else {
            renderer.setBackground(Color.WHITE); // Color de fondo predeterminado para las celdas
        }
        if (mostrarCheckbox) {
            if (value instanceof Boolean) {
                checkBox.setSelected((Boolean) value);
            } else {
                checkBox.setSelected(true); // Valor por defecto del checkbox
            }
            return checkBox;
        } else {
            // Mantener el renderizado predeterminado para las celdas fuera del rango
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
