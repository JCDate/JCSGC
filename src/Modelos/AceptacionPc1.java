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
public class AceptacionPc1 {
    String id;
    String componente;
    String fecha;
    String noRollo;
    String inspVisual;
    String observacion;
    String noParte;
    String noTroquel;
    String noOps;
    
    public AceptacionPc1() {
        this.id = "";
        this.componente = "";
        this.fecha = "";
        this.noRollo = "";
        this.inspVisual = "";
        this.observacion = "";
        this.noParte = "";
        this.noTroquel = "";
        this.noOps = "";
    }

    public AceptacionPc1(String id, String componente, String fecha, String noRollo, String inspVisual, String observacion, String noParte, String noTroquel, String noOps) {
        this.id = id;
        this.componente = componente;
        this.fecha = fecha;
        this.noRollo = noRollo;
        this.inspVisual = inspVisual;
        this.observacion = observacion;
        this.noParte = noParte;
        this.noTroquel = noTroquel;
        this.noOps = noOps;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNoRollo() {
        return noRollo;
    }

    public void setNoRollo(String noRollo) {
        this.noRollo = noRollo;
    }

    public String getInspVisual() {
        return inspVisual;
    }

    public void setInspVisual(String inspVisual) {
        this.inspVisual = inspVisual;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNoParte() {
        return noParte;
    }

    public void setNoParte(String noParte) {
        this.noParte = noParte;
    }

    public String getNoTroquel() {
        return noTroquel;
    }

    public void setNoTroquel(String noTroquel) {
        this.noTroquel = noTroquel;
    }

    public String getNoOps() {
        return noOps;
    }

    public void setNoOps(String noOps) {
        this.noOps = noOps;
    }
}
