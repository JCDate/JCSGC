/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import java.time.LocalDate;

/**
 *
 * @author JC
 */
public class ContadorAnual {

    private int contador;
    private LocalDate fechaActual;

    public ContadorAnual() {
        this.contador = 0;
        fechaActual = LocalDate.now();
    }

    public int obtenerSiguienteNumero(int contador) {
        LocalDate fechaActualNueva = LocalDate.now();
        if (fechaActualNueva.getYear() != fechaActual.getYear()) {
            contador = 0;
        }
        fechaActual = fechaActualNueva;
        contador++;
        return contador;
    }

}
