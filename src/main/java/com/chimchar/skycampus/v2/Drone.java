package com.chimchar.skycampus.v2;

public record Drone(
        String id,
        TipoDrone tipo,
        int bateria,
        boolean disponible,
        EstadoDrone estado,
        int capacidadGramos
) {
}
