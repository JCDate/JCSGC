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
public class Materia {
    private String nombre;
        private int horasPorSemana;

        public Materia(String nombre, int horasPorSemana) {
            this.nombre = nombre;
            this.horasPorSemana = horasPorSemana;
        }

        public String getNombre() {
            return nombre;
        }

        public int getHorasPorSemana() {
            return horasPorSemana;
        }
}
