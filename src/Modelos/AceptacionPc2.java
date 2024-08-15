package Modelos;

public class AceptacionPc2 {

    private String id;
    private String componente;
    private String fecha;
    private String noOrden;
    private String tamLote;
    private String tamMta;
    private String inspector;
    private String turno;
    private String disp;

    public AceptacionPc2() {
        this.id = "";
        this.componente = "";
        this.fecha = "";
        this.noOrden = "";
        this.tamLote = "";
        this.tamMta = "";
        this.inspector = "";
        this.turno = "";
        this.disp = "";
    }

    public AceptacionPc2(String id, String componente, String fecha, String noOrden, String tamLote, String tamMta, String inspector, String turno, String disp) {
        this.id = id;
        this.componente = componente;
        this.fecha = fecha;
        this.noOrden = noOrden;
        this.tamLote = tamLote;
        this.tamMta = tamMta;
        this.inspector = inspector;
        this.turno = turno;
        this.disp = disp;
    }

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getNoOrden() {
        return noOrden;
    }

    public void setNoOrden(String noOrden) {
        this.noOrden = noOrden;
    }

    public String getTamLote() {
        return tamLote;
    }

    public void setTamLote(String tamLote) {
        this.tamLote = tamLote;
    }

    public String getTamMta() {
        return tamMta;
    }

    public void setTamMta(String tamMta) {
        this.tamMta = tamMta;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDisp() {
        return disp;
    }

    public void setDisp(String disp) {
        this.disp = disp;
    }
}
