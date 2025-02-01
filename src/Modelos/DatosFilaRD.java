package Modelos;



public class DatosFilaRD {

    private int id_pc1;
    private int id_pc2;
    private String noRollo;
    private String inspVisual;
    private String observaciones;
    private String noOrden;
    private String insp;
    private String tamLote;
    private String turno;
    private String tamMta;
    private String disp;

    public DatosFilaRD() {
    }

    public DatosFilaRD(int id_pc1, int id_pc2, String noRollo, String inspVisual, String observaciones, String noOrden, String insp, String tamLote, String turno, String tamMta, String disp) {
        this.id_pc1 = id_pc1;
        this.id_pc2 = id_pc2;
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

    public int getId_pc1() {
        return id_pc1;
    }

    public void setId_pc1(int id_pc1) {
        this.id_pc1 = id_pc1;
    }

    public int getId_pc2() {
        return id_pc2;
    }

    public void setId_pc2(int id_pc2) {
        this.id_pc2 = id_pc2;
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

    public String getNoOrden() {
        return noOrden;
    }

    public void setNoOrden(String noOrden) {
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
}
