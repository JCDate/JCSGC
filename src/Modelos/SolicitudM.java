package Modelos;

public class SolicitudM {

    private int id;
    private int idp;
    private String nombre;
    private String codigo;
    private String revActual;
    private String nuevaRev;
    private String encargado;
    private byte[] nuevo;

    public SolicitudM(int id, int idp, String nombre, String codigo, String revActual, String nuevaRev, String encargado, byte[] nuevo) {
        this.id = id;
        this.idp = idp;
        this.nombre = nombre;
        this.codigo = codigo;
        this.revActual = revActual;
        this.nuevaRev = nuevaRev;
        this.encargado = encargado;
        this.nuevo = nuevo;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRevActual() {
        return revActual;
    }

    public void setRevActual(String revActual) {
        this.revActual = revActual;
    }

    public String getNuevaRev() {
        return nuevaRev;
    }

    public void setNuevaRev(String nuevaRev) {
        this.nuevaRev = nuevaRev;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public byte[] getNuevo() {
        return nuevo;
    }

    public void setNuevo(byte[] nuevo) {
        this.nuevo = nuevo;
    }
}
