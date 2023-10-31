/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author JC
 */
public class AceptacionPc3 {

    String id;
    String componente;
    String noOp;
    String fecha;
    String variable;
    String especificacion;
    String valor;

    public AceptacionPc3() {
        this.id = "";
        this.componente = "";
        this.noOp = "";
        this.fecha = "";
        this.variable = "";
        this.especificacion = "";
        this.valor = "";
    }

    public AceptacionPc3(String id, String componente, String noOp, String fecha, String variable, String especificacion, String valor) {
        this.id = id;
        this.componente = componente;
        this.noOp = noOp;
        this.fecha = fecha;
        this.variable = variable;
        this.especificacion = especificacion;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

}
