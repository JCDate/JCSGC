package Modelos;

public class ProcesosM {

    private int id;
    private int uid;
    private String no;
    private String codigo;
    private String revision;
    private String proceso;
    private String encargado;
    private String nombreDT;
    private String rutaArchivo;

    public ProcesosM(int id, int uid, String no, String codigo, String revision, String proceso, String encargado, String nombreDT, String rutaArchivo) {
        this.id = id;
        this.uid = uid;
        this.no = no;
        this.codigo = codigo;
        this.revision = revision;
        this.proceso = proceso;
        this.encargado = encargado;
        this.nombreDT = nombreDT;
        this.rutaArchivo = rutaArchivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getNombreDT() {
        return nombreDT;
    }

    public void setNombreDT(String nombreDT) {
        this.nombreDT = nombreDT;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
