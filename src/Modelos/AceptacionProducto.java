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
public class AceptacionProducto {

    private int id;
    private String componente;
    private byte[] rdPdf;

    public AceptacionProducto() {
        this.id = 0;
        this.componente = "";
        this.rdPdf = null;
    }

    public AceptacionProducto(int id, String componente, byte[] rdPdf) {
        this.id = id;
        this.componente = componente;
        this.rdPdf = rdPdf;
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

    public byte[] getRdPdf() {
        return rdPdf;
    }

    public void setRdPdf(byte[] rdPdf) {
        this.rdPdf = rdPdf;
    }
}
