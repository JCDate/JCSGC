package Modelos;

public class SolicitudesM {

    private int idp;
    private String codigo;
    private String procedimiento;
    private String nombre;
    private String revAnterior;
    private String revNueva;
    private String encargado;
    private String tipoArchivo;
    private byte[] archivo;
    private String accion;

    public SolicitudesM(int idp, String codigo, String procedimiento, String nombre, String revAnterior, String revNueva, String encargado, String tipoArchivo, byte[] archivo, String accion) {
        this.idp = idp;
        this.codigo = codigo;
        this.procedimiento = procedimiento;
        this.nombre = nombre;
        this.revAnterior = revAnterior;
        this.revNueva = revNueva;
        this.encargado = encargado;
        this.tipoArchivo = tipoArchivo;
        this.archivo = archivo;
        this.accion = accion;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
    
    
}
