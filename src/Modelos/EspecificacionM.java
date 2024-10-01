package Modelos;

public class EspecificacionM {

    private int id;
    private String especificacion;
    private String codigo;
    private String fechaEmision;
    private String fechaRevision;
    private String noRev;
    private int idDatosIR;
    private int fila;
    private int numFila;

    public EspecificacionM() {
    }

    public EspecificacionM(int id, String especificacion, String codigo, String fechaEmision, String fechaRevision, String noRev, int idDatosIR, int fila, int numFila) {
        this.id = id;
        this.especificacion = especificacion;
        this.codigo = codigo;
        this.fechaEmision = fechaEmision;
        this.fechaRevision = fechaRevision;
        this.noRev = noRev;
        this.idDatosIR = idDatosIR;
        this.fila = fila;
        this.numFila = numFila;
    }

    public int getNumFila() {
        return numFila;
    }

    public void setNumFila(int numFila) {
        this.numFila = numFila;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(String fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getNoRev() {
        return noRev;
    }

    public void setNoRev(String noRev) {
        this.noRev = noRev;
    }

    public int getIdDatosIR() {
        return idDatosIR;
    }

    public void setIdDatosIR(int idDatosIR) {
        this.idDatosIR = idDatosIR;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }
}
