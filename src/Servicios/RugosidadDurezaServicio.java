package Servicios;

import Modelos.RugosidadDurezaM;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RugosidadDurezaServicio {

    private JTable tbl; // Atributo de la clase

    public RugosidadDurezaServicio() {
    }

    public RugosidadDurezaServicio(JTable tbl) {
        this.tbl = tbl; 
    }

    public JTable getTbl() {
        return tbl;
    }

    public void setTbl(JTable tbl) {
        this.tbl = tbl;
    }

    public List<RugosidadDurezaM> recuperarTodas(int a, int b) {
        List<RugosidadDurezaM> listaAnchoLargo = new ArrayList<>(); // La lista para almacenar los valores de ancho y largo
        DefaultTableModel model = (DefaultTableModel) tbl.getModel(); // Se obtiene el modelo de la tabla

        for (int i = 0; i < model.getRowCount(); i++) { // Recorrer los valores de la JTable y asignarlos a los parámetros de la consulta
            // Obtener los valores de cada columna en la fila actual
            String rugosidad = Objects.toString(model.getValueAt(i, 0), "");
            String dureza = Objects.toString(model.getValueAt(i, 1), "");

            RugosidadDurezaM medidas = new RugosidadDurezaM(a, rugosidad, dureza, b); // Crear una nueva instancia de la clase AnchoLargoM 
            listaAnchoLargo.add(medidas);
        }
        return listaAnchoLargo;
    }

}
