package com.chimchar.skycampus;

import java.time.LocalDateTime;

public record Mision(
        String id,
        Drone drone,
        String origen,
        String destino,
        TipoCarga tipoCarga,
        EstadoMision estado,
        int prioridad,
        String notas,
        LocalDateTime horaMaximaEntrega
) {
    // Constructor compatible con el modelo anterior.
    public Mision(
            String id,
            Drone drone,
            String origen,
            String destino,
            TipoCarga tipoCarga,
            EstadoMision estado
    ) {
        this(
                id,
                drone,
                origen,
                destino,
                tipoCarga,
                estado,
                3,
                "",
                null
        );
    }
}