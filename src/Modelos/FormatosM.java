package Modelos;

public class FormatosM {

    private int id;
    private int idP;
    private String nombre;
    private byte[] contenido;

    public FormatosM() {
    }

    public FormatosM(int id, int idP, String nombre, byte[] contenido) {
        this.id = id;
        this.idP = idP;
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
}
