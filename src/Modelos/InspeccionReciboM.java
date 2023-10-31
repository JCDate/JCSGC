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
public class InspeccionReciboM {

    private int id;
    private String fechaFactura;
    private String proveedor;
    private String noFactura;
    private String noPedido;
    private String calibre;
    private String pLamina;
    private String noRollo;
    private String pzKg;
    private String estatus;
    private String noHoja;
    private byte[] certificadopdf;
    private byte[] facturapdf;
    private byte[] hojaIns;

    public InspeccionReciboM(int id, String fechaFactura, String proveedor, String noFactura, String noPedido, String calibre, String pLamina, String noRollo, String pzKg, String estatus, String noHoja, byte[] facturapdf, byte[] certificadopdf, byte[] hojaIns) {
        this.id = id;
        this.fechaFactura = fechaFactura;
        this.proveedor = proveedor;
        this.noFactura = noFactura;
        this.noPedido = noPedido;
        this.calibre = calibre;
        this.pLamina = pLamina;
        this.noRollo = noRollo;
        this.pzKg = pzKg;
        this.estatus = estatus;
        this.noHoja = noHoja;
        this.facturapdf = facturapdf;
        this.certificadopdf = certificadopdf;
        this.hojaIns = hojaIns;
    }

    public InspeccionReciboM() {
        this.id = 0;
        this.fechaFactura = null;
        this.proveedor = null;
        this.noFactura = null;
        this.noPedido = null;
        this.calibre = null;
        this.pLamina = null;
        this.noRollo = null;
        this.pzKg = null;
        this.estatus = null;
        this.noHoja = null;
        this.hojaIns = null;
    }

    public String getNoHoja() {
        return noHoja;
    }

    public void setNoHoja(String noHoja) {
        this.noHoja = noHoja;
    }

    public byte[] getHojaIns() {
        return hojaIns;
    }

    public void setHojaIns(byte[] hojaIns) {
        this.hojaIns = hojaIns;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNoFactura() {
        return noFactura;
    }

    public void setNoFactura(String noFactura) {
        this.noFactura = noFactura;
    }

    public String getNoPedido() {
        return noPedido;
    }

    public void setNoPedido(String noPedido) {
        this.noPedido = noPedido;
    }

    public String getCalibre() {
        return calibre;
    }

    public void setCalibre(String calibre) {
        this.calibre = calibre;
    }

    public String getpLamina() {
        return pLamina;
    }

    public void setpLamina(String pLamina) {
        this.pLamina = pLamina;
    }

    public String getNoRollo() {
        return noRollo;
    }

    public void setNoRollo(String noRollo) {
        this.noRollo = noRollo;
    }

    public String getPzKg() {
        return pzKg;
    }

    public void setPzKg(String pzKg) {
        this.pzKg = pzKg;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public byte[] getCertificadopdf() {
        return certificadopdf;
    }

    public void setCertificadopdf(byte[] certificadopdf) {
        this.certificadopdf = certificadopdf;
    }

    public byte[] getFacturapdf() {
        return facturapdf;
    }

    public void setFacturapdf(byte[] facturapdf) {
        this.facturapdf = facturapdf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
