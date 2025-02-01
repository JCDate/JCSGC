package Modelos;

public class ApqpM {
    private int id;
    private String familia;
    private String etapa;
    private String actividad;
    private String requerimientos;
    private String factibilidad;
    private String equipo;
    private byte[] documento;

    public ApqpM() {
    }
    
    public ApqpM(int id, String familia, String etapa, String actividad, String requerimientos, String factibilidad, String equipo, byte[] documento) {
        this.id = id;
        this.familia = familia;
        this.etapa = etapa;
        this.actividad = actividad;
        this.requerimientos = requerimientos;
        this.factibilidad = factibilidad;
        this.equipo = equipo;
        this.documento = documento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(String requerimientos) {
        this.requerimientos = requerimientos;
    }

    public String getFactibilidad() {
        return factibilidad;
    }

    public void setFactibilidad(String factibilidad) {
        this.factibilidad = factibilidad;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }
}
