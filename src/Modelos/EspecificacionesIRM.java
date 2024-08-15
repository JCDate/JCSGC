package Modelos;

public class EspecificacionesIRM {

    private int idEir;
    private String especificacion;
    private String calibre;
    private String propiedades;
    private boolean CM;
    private int idDir;
    private int fila;

    public EspecificacionesIRM() {
        this.idEir = 0;
        this.especificacion = null;
        this.calibre = null;
        this.propiedades = null;
        this.CM = false;
        this.idDir = 0;
    }

    public EspecificacionesIRM(int idEir, String especificacion, String calibre, String propiedades, boolean CM, int idDir, int fila) {
        this.idEir = idEir;
        this.especificacion = especificacion;
        this.calibre = calibre;
        this.propiedades = propiedades;
        this.CM = CM;
        this.idDir = idDir;
        this.fila = fila;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getIdEir() {
        return idEir;
    }

    public void setIdEir(int idEir) {
        this.idEir = idEir;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public String getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(String propiedades) {
        this.propiedades = propiedades;
    }

    public boolean isCM() {
        return CM;
    }

    public void setCM(boolean CM) {
        this.CM = CM;
    }

    public int getIdDir() {
        return idDir;
    }

    public void setIdDir(int idDir) {
        this.idDir = idDir;
    }
}
