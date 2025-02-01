package Modelos;

public class DatosIRM {

    private int idDatosIR;
    private String fechaInspeccion;
    private String inspector;
    private String noHoja;
    private int anchoLargo;
    private String descripcionMP;
    private String calibreLamina;
    private String observacionesRD;
    private String obsMP;
    private int aceptacion;
    private int idIr;

    public DatosIRM() {
    }

    public DatosIRM(int idDatosIR, String fechaInspeccion, String inspector, String noHoja, int anchoLargo, String descripcionMP, String calibreLamina, String observacionesRD, String obsMP, int aceptacion, int idIr) {
        this.idDatosIR = idDatosIR;
        this.fechaInspeccion = fechaInspeccion;
        this.inspector = inspector;
        this.noHoja = noHoja;
        this.anchoLargo = anchoLargo;
        this.descripcionMP = descripcionMP;
        this.calibreLamina = calibreLamina;
        this.observacionesRD = observacionesRD;
        this.obsMP = obsMP;
        this.aceptacion = aceptacion;
        this.idIr = idIr;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getDescripcionMP() {
        return descripcionMP;
    }

    public void setDescripcionMP(String descripcionMP) {
        this.descripcionMP = descripcionMP;
    }

    public String getCalibreLamina() {
        return calibreLamina;
    }

    public void setCalibreLamina(String calibreLamina) {
        this.calibreLamina = calibreLamina;
    }

    public int getIdDatosIR() {
        return idDatosIR;
    }

    public void setIdDatosIR(int idDatosIR) {
        this.idDatosIR = idDatosIR;
    }

    public String getFechaInspeccion() {
        return fechaInspeccion;
    }

    public void setFechaInspeccion(String fechaInspeccion) {
        this.fechaInspeccion = fechaInspeccion;
    }

    public String getNoHoja() {
        return noHoja;
    }

    public void setNoHoja(String noHoja) {
        this.noHoja = noHoja;
    }

    public int getAnchoLargo() {
        return anchoLargo;
    }

    public void setAnchoLargo(int anchoLargo) {
        this.anchoLargo = anchoLargo;
    }

    public int getAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(int aceptacion) {
        this.aceptacion = aceptacion;
    }

    public String getObservacionesRD() {
        return observacionesRD;
    }

    public void setObservacionesRD(String observacionesRD) {
        this.observacionesRD = observacionesRD;
    }

    public String getObsMP() {
        return obsMP;
    }

    public void setObsMP(String obsMP) {
        this.obsMP = obsMP;
    }

    public int getIdIr() {
        return idIr;
    }

    public void setIdIr(int idIr) {
        this.idIr = idIr;
    }
}
