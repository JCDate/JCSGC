package Modelos;

public class SolicitudesM {

    private int id;
    private String codigo;
    private String proceso;
    private String procedimiento;
    private String revAnterior;
    private String revNueva;
    private String encargado;
    private String accion;
    private String tipoArchivo;
    private String nombreD;
    private String nombre;
    private String rutaArchivo;

    public SolicitudesM() {
    }

    public SolicitudesM(int id, String codigo, String proceso, String procedimiento, String revAnterior, String revNueva, String encargado, String accion, String tipoArchivo, String nombreD, String nombre, String rutaArchivo) {
        this.id = id;
        this.codigo = codigo;
        this.proceso = proceso;
        this.procedimiento = procedimiento;
        this.revAnterior = revAnterior;
        this.revNueva = revNueva;
        this.encargado = encargado;
        this.accion = accion;
        this.tipoArchivo = tipoArchivo;
        this.nombreD = nombreD;
        this.nombre = nombre;
        this.rutaArchivo = rutaArchivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getRevAnterior() {
        return revAnterior;
    }

    public void setRevAnterior(String revAnterior) {
        this.revAnterior = revAnterior;
    }

    public String getRevNueva() {
        return revNueva;
    }

    public void setRevNueva(String revNueva) {
        this.revNueva = revNueva;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getNombreD() {
        return nombreD;
    }

    public void setNombreD(String nombreD) {
        this.nombreD = nombreD;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
