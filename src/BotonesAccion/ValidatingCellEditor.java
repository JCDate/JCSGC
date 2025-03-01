package BotonesAccion;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class ValidatingCellEditor extends DefaultCellEditor {

    private JTextField textField = new JTextField();
    private double minValue = 0.0, maxValue = 0.0; // Límites de validación
    private JTable table;

    public ValidatingCellEditor(JTable table) {
        super(new JTextField());
        this.table = table;
        textField.setBorder(new LineBorder(Color.BLACK, 1));

        // Validar cuando el usuario presiona ENTER o cambia el valor
        textField.addActionListener(e -> stopCellEditing());
    }

    public void setRange(double min, double max) {
        this.minValue = min;
        this.maxValue = max;
    }

    @Override
    public boolean stopCellEditing() {
        try {
            // Obtener el valor ingresado por el usuario
            double valorIngresado = Double.parseDouble((String) getCellEditorValue());

            // Obtener la columna donde se encuentra la celda editada (puede ser la primera o la segunda)
            int columna = table.getSelectedColumn();

            // Obtener el texto de la celda que contiene el rango
            String textoRango = (String) table.getValueAt(table.getSelectedRow(), columna == 0 ? 0 : 1); // Si es la primera o segunda columna

            // Extraer el rango de valores de la celda seleccionada
            System.out.println("fila: "+table.getSelectedRow());
            System.out.println("col: "+ columna );
            double[] rango = extraerRango(textoRango); // Llamada al método público

            if (rango != null) {
                double min = rango[0];
                double max = rango[1];

                // Verificar si el valor ingresado está fuera del rango
                if (valorIngresado < min || valorIngresado > max) {
                    JOptionPane.showMessageDialog(null, "Valor fuera de rango (" + min + " - " + max + ")", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Evita que se guarde el valor
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Evita que se guarde el valor
        }

        return super.stopCellEditing(); // Guarda el valor si es válido
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        textField.setText(value == null ? "" : value.toString());
        return textField;
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }

    // Método extraerRango público
    public double[] extraerRango(String texto) {
        texto = texto.replace(" ", ""); // Eliminar espacios
        texto = texto.replace(",", ".");  // Reemplazar la coma por un punto
        texto = texto.replace("\"", "");  // Eliminar las comillas dobles
        String[] partes = texto.split("±");

        if (partes.length == 2) {
            try {
                double valorCentral = Double.parseDouble(partes[0]);
                double margen = Double.parseDouble(partes[1]);
                double min = valorCentral - margen;
                double max = valorCentral + margen;
                return new double[]{min, max}; // Retorna el rango
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null; // Si no se puede parsear el rango, retorna null
    }

    public double parseMinValue(String texto) {
        // Extrae el valor mínimo del formato ".0478 ± .002"
        double[] rango = extraerRango(texto);
        return (rango != null) ? rango[0] : 0.0;
    }

    public double parseMaxValue(String texto) {
        // Extrae el valor máximo del formato ".0478 ± .002"
        double[] rango = extraerRango(texto);
        return (rango != null) ? rango[1] : 0.0;
    }
}
