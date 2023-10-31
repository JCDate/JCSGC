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
public class DatosFila {

    private Object especificacion;
    private Object calibre;
    private Object propiedad;
    private Object cmPm;
    private Object composicion;
    private Object cmCq;
    private int fila;

    public DatosFila(Object especificacion, Object calibre, Object propiedad, Object cmPm, Object composicion, Object cmCq, int fila) {
        this.especificacion = especificacion;
        this.calibre = calibre;
        this.propiedad = propiedad;
        this.cmPm = cmPm;
        this.composicion = composicion;
        this.cmCq = cmCq;
        this.fila = fila;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public Object getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(Object especificacion) {
        this.especificacion = especificacion;
    }

    public Object getCalibre() {
        return calibre;
    }

    public void setCalibre(Object calibre) {
        this.calibre = calibre;
    }

    public Object getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Object propiedad) {
        this.propiedad = propiedad;
    }

    public Object getCmPm() {
        return cmPm;
    }

    public void setCmPm(Object cmPm) {
        this.cmPm = cmPm;
    }

    public Object getComposicion() {
        return composicion;
    }

    public void setComposicion(Object composicion) {
        this.composicion = composicion;
    }

    public Object getCmCq() {
        return cmCq;
    }

    public void setCmCq(Object cmCq) {
        this.cmCq = cmCq;
    }

    public int getIncrementoFilas(int num) {
        int incremento = 0;
        switch (num) {
            case 1:
                incremento = 6;
                break;
            case 2:
                incremento = 10;
                break;
            case 3:
                incremento = 14;
                break;
            case 4:
                incremento = 21;
                break;
            case 5:
                incremento = 25;
                break;
            case 6:
                incremento = 29;
                break;
            case 7:
                incremento = 35;
                break;
            case 8:
                incremento = 39;
                break;
            case 9:
                incremento = 47;
                break;
            case 10:
                incremento = 55;
                break;
            case 11:
                incremento = 62;
                break;
            case 12:
                incremento = 66;
                break;
            case 13:
                incremento = 70;
                break;
            case 14:
                incremento = 75;
                break;
            default:
                incremento = 0;
                break;
        }
        return incremento;
    }

    public int getElemento(String especificacion) {
        int elemento = 0;
        switch (especificacion) {
            case "LAMINA HSLAS (ASTM A1011/A1011M)":
                elemento = 6;
                break;
            case "LAMINA DE ACERO AL CARBON (ASTM A 1011/A 1011M-09 Gr. 50 Hot Rolled Steel)":
                elemento = 10;
                break;
            case "LAMINA DE ACERO INOXIDABLE (HOJAS) (30304 Stainless Steel)":
                elemento = 14;
                break;
            case "LAMINA DE ACERO AL CARBON (SAE 1006-1008)":
                elemento = 21;
                break;
            case "LAMINA DE ACERO AL CARBON (QS 1010 ZK (GMW F104) ASTM A620 AKDQ)":
                elemento = 25;
                break;
            case "LAMINA DE BERYLLIUM COPPER (ASTM B194/B197/B197M-98 (C17200 1/2 HM))":
                elemento = 29;
                break;
            case "LAMINA DE ACERO AL CARBON (ASTM A620 AKDQ (SAE 1010))":
                elemento = 35;
                break;
            case "LAMINA DE ACERO INOXIDABLE (HOJAS) (ASTM A240 (S30304) SAE 30304)":
                elemento = 39;
                break;
            case "LAMINA DE ACERO INOXIDABLE (ASTM A240 (S30100) SAE J412 REF. ASTM A 666-96B ))":
                elemento = 47;
                break;
            case "LAMINA DE  ALUMINO (SAE 5052-H32  ASTM-B209QQA-250/8EAMS-4015F)":
                elemento = 55;
                break;
            case "LAMINA DE LATON (ASTM B36/B36M H02 (C26000))":
                elemento = 62;
                break;
            case "LAMINA DE ACERO AL CARBON ROLADA EN CALIENTE (ASTM A570 Gr. 50 )":
                elemento = 66;
                break;
            case "1006/1008 Cold Rolled Steel (Aluminium Killed, Drawing Steel)":
                elemento = 70;
                break;
            case "J13929 High Strength, Low Alloy Steel (ASTM A-568)":
                elemento = 75;
                break;
            default:
                elemento = 0;
                break;
        }
        return elemento;
    }
}
