package com.chimchar.skycampus.v2;

import com.chimchar.skycampus.EstadoMision;

import java.time.LocalDateTime;
import java.util.List;

final class Datos {

    static final LocalDateTime AHORA = LocalDateTime.of(2026, 10, 17, 10, 0);

    private Datos() {
    }

    static Drone drone(String id, TipoDrone tipo, int bateria, EstadoDrone estado, int capacidad) {
        return new Drone(id, tipo, bateria, estado == EstadoDrone.DISPONIBLE, estado, capacidad);
    }

    static List<Drone> crearFlotaTest() {
        return List.of(
                drone("D-01", TipoDrone.MINI, 85, EstadoDrone.DISPONIBLE, 500),
                drone("D-02", TipoDrone.CARGO, 67, EstadoDrone.DISPONIBLE, 2000),
                drone("D-03", TipoDrone.EXPRESS, 91, EstadoDrone.DISPONIBLE, 800),
                drone("D-04", TipoDrone.MINI, 18, EstadoDrone.DISPONIBLE, 500),
                drone("D-05", TipoDrone.EXPRESS, 55, EstadoDrone.DISPONIBLE, 800)
        );
    }

    static Mision crearMisionTest(Prioridad prioridad, int peso) {
        return new Mision("M-1", null, "Biblioteca", peso, prioridad,
                EstadoMision.PENDIENTE, AHORA);
    }

    static Mision mision(String id, Drone drone, Prioridad prioridad,
                         EstadoMision estado, LocalDateTime creada) {
        return new Mision(id, drone, "Bloque A", 200, prioridad, estado, creada);
    }
}
