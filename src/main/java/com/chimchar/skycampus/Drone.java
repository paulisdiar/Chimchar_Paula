package com.chimchar.skycampus;

public record Drone(
        String id,
        String modelo,
        int bateria,
        boolean disponible,
        String ubicacion
) {
}
