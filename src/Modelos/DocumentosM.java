package Modelos;

public class DocumentosM {
    private int id;
    private int idProceso;
    private String fechaActualizacion;
    private String tipo;
    private String nombre;
    private byte[] contenido;
    
    public DocumentosM(int id, int idProceso, String fechaActualizacion, String tipo, String nombre, byte[] contenido) {
        this.id = id;
        this.idProceso = idProceso;
        this.fechaActualizacion = fechaActualizacion;
        this.tipo = tipo;
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }

    public String getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
