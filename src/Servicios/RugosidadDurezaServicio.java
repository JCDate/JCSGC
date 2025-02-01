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
        List<RugosidadDurezaM> listaAnchoLargo = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String rugosidad = Objects.toString(model.getValueAt(i, 0), "");
            String dureza = Objects.toString(model.getValueAt(i, 1), "");

            RugosidadDurezaM medidas = new RugosidadDurezaM(a, rugosidad, dureza, b);
            listaAnchoLargo.add(medidas);
        }
        return listaAnchoLargo;
    }

}
