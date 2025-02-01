package Servicios;

import java.time.LocalDate;

public class ContadorAnual {

    private LocalDate fechaActual;

    public ContadorAnual() {
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
