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
public class DataEntry {

    public String noOp;
    public String fecha;
    public String especificacion;
    public String valorTerceraColumna;

    public DataEntry(String noOp, String fecha, String especificacion, String valorTerceraColumna) {
        this.noOp = noOp;
        this.fecha = fecha;
        this.especificacion = especificacion;
        this.valorTerceraColumna = valorTerceraColumna;
    }

}
