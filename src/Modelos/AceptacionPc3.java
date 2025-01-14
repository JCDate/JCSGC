package Modelos;

public class AceptacionPc3 {

    private int id;
    private String componente;
    private String noOp;
    private String noTroquel;
    private String fecha;
    private String variable;
    private String especificacion;
    private String valor;
    int procesoCritico;

    public AceptacionPc3() {
    }

    public AceptacionPc3(int id, String componente, String noOp, String noTroquel, String fecha, String variable, String especificacion, String valor, int procesoCritico) {
        this.id = id;
        this.componente = componente;
        this.noOp = noOp;
        this.noTroquel = noTroquel;
        this.fecha = fecha;
        this.variable = variable;
        this.especificacion = especificacion;
        this.valor = valor;
        this.procesoCritico = procesoCritico;
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

    public String getNoOp() {
        return noOp;
    }

    public void setNoOp(String noOp) {
        this.noOp = noOp;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getProcesoCritico() {
        return procesoCritico;
    }

    public void setProcesoCritico(int procesoCritico) {
        this.procesoCritico = procesoCritico;
    }

    public String getNoTroquel() {
        return noTroquel;
    }

    public void setNoTroquel(String noTroquel) {
        this.noTroquel = noTroquel;
    }
}
