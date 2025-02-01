package Servicios;

import Modelos.AnchoLargoM;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AnchoLargoServicio {

    private JTable tbl;

    public AnchoLargoServicio() {
    }

    public AnchoLargoServicio(JTable tbl) {
        this.tbl = tbl;
    }

    public JTable getTbl() {
        return tbl;
    }

    public void setTbl(JTable tbl) {
        this.tbl = tbl;
    }

    public List<AnchoLargoM> capturarValores(int a, int b) {
        List<AnchoLargoM> listaAnchoLargo = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tbl.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String ancho = Objects.toString(model.getValueAt(i, 0), "");
            String largo = Objects.toString(model.getValueAt(i, 1), "");

            AnchoLargoM medidas = new AnchoLargoM(a, ancho, largo, b);
            listaAnchoLargo.add(medidas);
        }
        return listaAnchoLargo;
    }
}
