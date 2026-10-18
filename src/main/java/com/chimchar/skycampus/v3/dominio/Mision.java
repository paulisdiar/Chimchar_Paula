package com.chimchar.skycampus.v3.dominio;

import com.chimchar.skycampus.EstadoMision;
import com.chimchar.skycampus.v2.Prioridad;

public record Mision(
        String id,
        String sedeOrigen,
        String sedeDestino,
        int pesoGramos,
        Prioridad prioridad,
        EstadoMision estado,
        String droneId,
        int minutosEntrega
) {

    public Mision asignada(String idDrone) {
        return new Mision(id, sedeOrigen, sedeDestino, pesoGramos, prioridad,
                EstadoMision.EN_VUELO, idDrone, minutosEntrega);
    }
}
