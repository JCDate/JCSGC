package Modelos;

public class AceptacionProductoM {

    private int id;
    private String componente;
    private String rutaArchivo;

    public AceptacionProductoM() {
        this.id = 0;
        this.componente = "";
        this.rutaArchivo = "";
    }

    public AceptacionProductoM(int id, String componente, String rutaArchivo) {
        this.id = id;
        this.componente = componente;
        this.rutaArchivo = rutaArchivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
