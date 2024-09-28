package Modelos;

public class AceptacionProducto {

    private int id;
    private String componente;
    private byte[] archivo;

    public AceptacionProducto() {
        this.id = 0;
        this.componente = "";
        this.archivo = null;
    }

    public AceptacionProducto(int id, String componente, byte[] archivo) {
        this.id = id;
        this.componente = componente;
        this.archivo = archivo;
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

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
}
