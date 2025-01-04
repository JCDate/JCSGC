package Modelos;

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
    private String nombreHojaInstruccion;
    private String nombreFact;
    private String nombreCert;
    private String rutaFactura;
    private String rutaCertificado;
    private String rutaHojaInstruccion;

    public InspeccionReciboM() {
    }

    public InspeccionReciboM(int id, String fechaFactura, String proveedor, String noFactura, String noPedido, String calibre, String pLamina, String noRollo, String pzKg, String estatus, String noHoja, String nombreHojaInstruccion, String nombreFact, String nombreCert, String rutaFactura, String rutaCertificado, String rutaHojaInstruccion) {
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
        this.nombreHojaInstruccion = nombreHojaInstruccion;
        this.nombreFact = nombreFact;
        this.nombreCert = nombreCert;
        this.rutaFactura = rutaFactura;
        this.rutaCertificado = rutaCertificado;
        this.rutaHojaInstruccion = rutaHojaInstruccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
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

    public String getNoHoja() {
        return noHoja;
    }

    public void setNoHoja(String noHoja) {
        this.noHoja = noHoja;
    }

    public String getNombreHojaInstruccion() {
        return nombreHojaInstruccion;
    }

    public void setNombreHojaInstruccion(String nombreHojaInstruccion) {
        this.nombreHojaInstruccion = nombreHojaInstruccion;
    }

    public String getNombreFact() {
        return nombreFact;
    }

    public void setNombreFact(String nombreFact) {
        this.nombreFact = nombreFact;
    }

    public String getNombreCert() {
        return nombreCert;
    }

    public void setNombreCert(String nombreCert) {
        this.nombreCert = nombreCert;
    }

    public String getRutaFactura() {
        return rutaFactura;
    }

    public void setRutaFactura(String rutaFactura) {
        this.rutaFactura = rutaFactura;
    }

    public String getRutaCertificado() {
        return rutaCertificado;
    }

    public void setRutaCertificado(String rutaCertificado) {
        this.rutaCertificado = rutaCertificado;
    }

    public String getRutaHojaInstruccion() {
        return rutaHojaInstruccion;
    }

    public void setRutaHojaInstruccion(String rutaHojaInstruccion) {
        this.rutaHojaInstruccion = rutaHojaInstruccion;
    }
}
