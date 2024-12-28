package Modelos;

public class FormatosM {

    private int id;
    private int idP;
    private String nombre;
    private String rutaArchivo;

    public FormatosM() {
    }

    public FormatosM(int id, int idP, String nombre, String rutaArchivo) {
        this.id = id;
        this.idP = idP;
        this.nombre = nombre;
        this.rutaArchivo = rutaArchivo;
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

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
