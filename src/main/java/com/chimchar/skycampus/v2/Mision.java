package com.chimchar.skycampus.v2;

import com.chimchar.skycampus.EstadoMision;

import java.time.LocalDateTime;

public record Mision(
        String id,
        Drone drone,
        String destino,
        int pesoPaqueteGramos,
        Prioridad prioridad,
        EstadoMision estado,
        LocalDateTime creadaEn
) {
}
