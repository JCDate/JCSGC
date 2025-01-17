package Servicios;

import Modelos.AnchoLargoM;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AnchoLargoServicio {

    private JTable tbl; // Atributo de la clase
    
    public AnchoLargoServicio() {
    }

    public AnchoLargoServicio(JTable tbl) {
        this.tbl = tbl; // Asignación de la Tabla Ancho/Largo al Atributo
    }

    public JTable getTbl() {
        return tbl;
    }

    public void setTbl(JTable tbl) {
        this.tbl = tbl;
    }

    public List<AnchoLargoM> capturarValores(int a, int b) {
        List<AnchoLargoM> listaAnchoLargo = new ArrayList<>(); // La lista para almacenar los valores de ancho y largo
        DefaultTableModel model = (DefaultTableModel) tbl.getModel(); // Se obtiene el modelo de la tabla

        for (int i = 0; i < model.getRowCount(); i++) { // Recorrer los valores de la JTable y asignarlos a los parámetros de la consulta
            // Obtener los valores de cada columna en la fila actual
            String ancho = Objects.toString(model.getValueAt(i, 0), "");
            String largo = Objects.toString(model.getValueAt(i, 1), "");

            AnchoLargoM medidas = new AnchoLargoM(a, ancho, largo, b); // Crear una nueva instancia de la clase AnchoLargoM 
            listaAnchoLargo.add(medidas); // Se agrega a la lista
        }
        return listaAnchoLargo;
    }
}
