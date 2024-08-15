package Modelos;

public class ProcedimientosM {
    
    private int id;
    private int idp;
    private String no;
    private String codigo;
    private String revision;
    private String procedimiento;
    private String encargado;

    public ProcedimientosM(int id, int idp, String no, String codigo, String revision, String procedimiento, String encargado) {
        this.id = id;
        this.idp = idp;
        this.no = no;
        this.codigo = codigo;
        this.revision = revision;
        this.procedimiento = procedimiento;
        this.encargado = encargado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }
    
    
    
}
