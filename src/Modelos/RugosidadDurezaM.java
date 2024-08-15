package Modelos;

public class RugosidadDurezaM {
    
    private int id;
    private String rugosidad;
    private String dureza;
    private int idDIR;

    public RugosidadDurezaM(int id, String rugosidad, String dureza, int idDIR) {
        this.id = id;
        this.rugosidad = rugosidad;
        this.dureza = dureza;
        this.idDIR = idDIR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRugosidad() {
        return rugosidad;
    }

    public void setRugosidad(String rugosidad) {
        this.rugosidad = rugosidad;
    }

    public String getDureza() {
        return dureza;
    }

    public void setDureza(String dureza) {
        this.dureza = dureza;
    }

    public int getIdDIR() {
        return idDIR;
    }

    public void setIdDIR(int idDIR) {
        this.idDIR = idDIR;
    }
}
