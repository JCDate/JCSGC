package Modelos;

public class PropiedadMecanicaM {

    private String especificacion;
    private String pm;
    private String valor;

    public PropiedadMecanicaM(String especificacion, String pm, String valor) {
        this.especificacion = especificacion;
        this.pm = pm;
        this.valor = valor;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
