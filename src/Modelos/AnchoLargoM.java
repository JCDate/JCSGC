package Modelos;

public class AnchoLargoM {

    private int idAL;
    private String ancho;
    private String largo;
    private int idDIR;

    public AnchoLargoM(int idAL, String ancho, String largo, int idDIR) {
        this.idAL = idAL;
        this.ancho = ancho;
        this.largo = largo;
        this.idDIR = idDIR;
    }

    public int getIdAL() {
        return idAL;
    }

    public void setIdAL(int idAL) {
        this.idAL = idAL;
    }

    public String getAncho() {
        return ancho;
    }

    public void setAncho(String ancho) {
        this.ancho = ancho;
    }

    public String getLargo() {
        return largo;
    }

    public void setLargo(String largo) {
        this.largo = largo;
    }

    public int getIdDIR() {
        return idDIR;
    }

    public void setIdDIR(int idDIR) {
        this.idDIR = idDIR;
    }
}
