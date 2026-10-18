package com.chimchar.skycampus.v3.infraestructura;

import com.chimchar.skycampus.v2.Prioridad;

public record SolicitudMision(
        String sedeOrigen,
        String sedeDestino,
        int pesoGramos,
        Prioridad prioridad
) {
}
