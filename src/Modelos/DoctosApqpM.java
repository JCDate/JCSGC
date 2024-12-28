package Modelos;

public class DoctosApqpM {
    
    private int id;
    private int idp;
    private String nombreDocto;
    private String rutaArchivo;

    public DoctosApqpM() {
    }

    public DoctosApqpM(int id, int idp, String nombreDocto, String rutaArchivo) {
        this.id = id;
        this.idp = idp;
        this.nombreDocto = nombreDocto;
        this.rutaArchivo = rutaArchivo;
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

    public String getNombreDocto() {
        return nombreDocto;
    }

    public void setNombreDocto(String nombreDocto) {
        this.nombreDocto = nombreDocto;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
