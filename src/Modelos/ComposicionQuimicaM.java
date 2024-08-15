package Modelos;

public class ComposicionQuimicaM {

    private String especificacion;
    private String cq;
    private String valor;

    public ComposicionQuimicaM(String especificacion, String cq, String valor) {
        this.especificacion = especificacion;
        this.cq = cq;
        this.valor = valor;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getCq() {
        return cq;
    }

    public void setCq(String cq) {
        this.cq = cq;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
