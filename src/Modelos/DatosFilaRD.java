package Modelos;

import java.util.ArrayList;
import java.util.List;

public class DatosFilaRD {

    private String noRollo;
    private String inspVisual;
    private String observaciones;
    private List<String> noOrden;
    private String insp;
    private String tamLote;
    private String turno;
    private String tamMta;
    private String disp;

    public DatosFilaRD() {
        this.noRollo = "";
        this.inspVisual = "";
        this.observaciones = "";
        this.noOrden = new ArrayList<>();
        this.insp = "";
        this.tamLote = "";
        this.turno = "";
        this.tamMta = "";
        this.disp = "";
    }

    public DatosFilaRD(String noRollo, String inspVisual, String observaciones, List<String> noOrden, String insp, String tamLote, String turno, String tamMta, String disp) {
        this.noRollo = noRollo;
        this.inspVisual = inspVisual;
        this.observaciones = observaciones;
        this.noOrden = noOrden;
        this.insp = insp;
        this.tamLote = tamLote;
        this.turno = turno;
        this.tamMta = tamMta;
        this.disp = disp;
    }
    
    public String getNoRollo() {
        return noRollo;
    }

    public void setNoRollo(String noRollo) {
        this.noRollo = noRollo;
    }

    public String getInspVisual() {
        return inspVisual;
    }

    public void setInspVisual(String inspVisual) {
        this.inspVisual = inspVisual;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<String> getNoOrden() {
        return noOrden;
    }

    public void setNoOrden(List<String> noOrden) {
        this.noOrden = noOrden;
    }

    public String getInsp() {
        return insp;
    }

    public void setInsp(String insp) {
        this.insp = insp;
    }

    public String getTamLote() {
        return tamLote;
    }

    public void setTamLote(String tamLote) {
        this.tamLote = tamLote;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getTamMta() {
        return tamMta;
    }

    public void setTamMta(String tamMta) {
        this.tamMta = tamMta;
    }

    public String getDisp() {
        return disp;
    }

    public void setDisp(String disp) {
        this.disp = disp;
    }

    public void agregarOtraOrden(String orden) {
        this.noOrden.add(orden);
    }
}
