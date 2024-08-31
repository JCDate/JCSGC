package Modelos;

public class RegistrosM {

    private int id;
    private int idp;
    private String fecha;
    private String codigo;
    private String proceso;
    private String procedimiento;
    private String revAnterior;
    private String revNueva;
    private String encargado;
    private String accion;
    private String tipoArchivo;
    private String nombre;

    public RegistrosM(int id, int idp, String fecha, String codigo, String proceso, String procedimiento, String revAnterior, String revNueva, String encargado, String accion, String tipoArchivo, String nombre) {
        this.id = id;
        this.idp = idp;
        this.fecha = fecha;
        this.codigo = codigo;
        this.proceso = proceso;
        this.procedimiento = procedimiento;
        this.revAnterior = revAnterior;
        this.revNueva = revNueva;
        this.encargado = encargado;
        this.accion = accion;
        this.tipoArchivo = tipoArchivo;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
