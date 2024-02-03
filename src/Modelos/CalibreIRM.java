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
public class CalibreIRM {

    private String calibre;
    private String medidas;
    private String especificacion;
    private String descripcionMP;

    public CalibreIRM(String calibre, String medidas, String especificacion, String descripcionMP) {
        this.calibre = calibre;
        this.medidas = medidas;
        this.especificacion = especificacion;
        this.descripcionMP = descripcionMP;
    }

    public CalibreIRM() {

    }

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public String getDescripcionMP() {
        return descripcionMP;
    }

    public void setDescripcionMP(String descripcionMP) {
        this.descripcionMP = descripcionMP;
    }
    
    

}
