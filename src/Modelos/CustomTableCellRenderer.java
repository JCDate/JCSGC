/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author JC
 */
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Cambiar el color de fondo de las celdas
        if (isSelected) {
            renderer.setBackground(Color.BLUE); // Color de fondo cuando la celda está seleccionada
        } else {
            renderer.setBackground(Color.WHITE); // Color de fondo predeterminado para las celdas
        }
        return renderer;
    }
}
