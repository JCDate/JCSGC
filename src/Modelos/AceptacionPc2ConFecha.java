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
public class AceptacionPc2ConFecha {

    private String fecha;
    private AceptacionPc2 dataVariable;

    public AceptacionPc2ConFecha(String fecha, AceptacionPc2 dataVariable) {
        this.fecha = fecha;
        this.dataVariable = dataVariable;
    }

    public String getFecha() {
        return fecha;
    }

    public AceptacionPc2 getDataVariable() {
        return dataVariable;

    }
}
