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
public class AceptacionPc2 {

    String id;
    String componente;
    String fecha;
    String noOrden;
    String tamLote;
    String tamMta;
    String inspector;
    String turno;
    String disp;

    public AceptacionPc2() {
        this.id = "";
        this.componente = "";
        this.fecha = "";
        this.noOrden = "";
        this.tamLote = "";
        this.tamMta = "";
        this.inspector = "";
        this.turno = "";
        this.disp = "";
    }

    public AceptacionPc2(String id, String componente, String fecha, String noOrden, String tamLote, String tamMta, String inspector, String turno, String disp) {
        this.id = id;
        this.componente = componente;
        this.fecha = fecha;
        this.noOrden = noOrden;
        this.tamLote = tamLote;
        this.tamMta = tamMta;
        this.inspector = inspector;
        this.turno = turno;
        this.disp = disp;
    }

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getNoOrden() {
        return noOrden;
    }

    public void setNoOrden(String noOrden) {
        this.noOrden = noOrden;
    }

    public String getTamLote() {
        return tamLote;
    }

    public void setTamLote(String tamLote) {
        this.tamLote = tamLote;
    }

    public String getTamMta() {
        return tamMta;
    }

    public void setTamMta(String tamMta) {
        this.tamMta = tamMta;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDisp() {
        return disp;
    }

    public void setDisp(String disp) {
        this.disp = disp;
    }
    
    
    
    
    
    
    
    
    /*
    for (AceptacionPc3 dataVariable : this.ap3m) {
    String variable = dataVariable.getVariable() + "\n" + dataVariable.getEspecificacion();
    String valor = dataVariable.getValor();
    
    // Busca el objeto correspondiente en ap2m (usando un identificador único como ejemplo)
    String id = dataVariable.getId(); // Reemplaza esto con el campo adecuado en ap3m que relaciona con ap2m
    AceptacionPc2 objetoAp2m = buscarObjetoEnAp2mPorId(id); // Implementa una función que busque en ap2m por el id
    
    // Elije el mapa correcto según el valor de noOp
    TreeMap<String, TreeMap<String, Map<String, String>>> selectedMap;
    switch (dataVariable.getNoOp()) {
        // ... casos anteriores ...

        default:
            selectedMap = null; // O maneja de alguna otra manera
            break;
    }

    // Agrega los datos al mapa seleccionado, incluyendo campos de ap2m
    if (selectedMap != null) {
        selectedMap.computeIfAbsent(dataVariable.getFecha(), k -> new TreeMap<>())
                .computeIfAbsent(dataVariable.getNoOp(), k -> new LinkedHashMap<>())
                .put(variable, valor);
        
        // Agrega campos de ap2m
        if (objetoAp2m != null) {
            selectedMap.computeIfAbsent(dataVariable.getFecha(), k -> new TreeMap<>())
                    .computeIfAbsent("NoOrden", k -> new TreeMap<>())
                    .computeIfAbsent(dataVariable.getNoOp(), k -> new LinkedHashMap<>())
                    .put("NoOrden", objetoAp2m.getNoOrden());
            // Repite este proceso para otros campos de ap2m que quieras agregar
        }
    }
}

    
    */
}
