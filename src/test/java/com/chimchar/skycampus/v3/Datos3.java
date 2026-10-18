package com.chimchar.skycampus.v3;

import com.chimchar.skycampus.EstadoMision;
import com.chimchar.skycampus.v2.EstadoDrone;
import com.chimchar.skycampus.v2.Prioridad;
import com.chimchar.skycampus.v2.TipoDrone;
import com.chimchar.skycampus.v3.dominio.Drone;
import com.chimchar.skycampus.v3.dominio.Mision;

final class Datos3 {

    private Datos3() {
    }

    static Drone drone(String id, String sede, TipoDrone tipo, int bateria, int capacidad) {
        return new Drone(id, sede, tipo, bateria, EstadoDrone.DISPONIBLE, capacidad);
    }

    static Mision mision(String id, String sede, Prioridad prioridad, EstadoMision estado, String droneId, int minutos) {
        return new Mision(id, sede, "UNAL", 300, prioridad, estado, droneId, minutos);
    }

    static Mision pendiente(String sede, int peso) {
        return new Mision("M-0", sede, "UNAL", peso, Prioridad.NORMAL, EstadoMision.PENDIENTE, null, 0);
    }
}
