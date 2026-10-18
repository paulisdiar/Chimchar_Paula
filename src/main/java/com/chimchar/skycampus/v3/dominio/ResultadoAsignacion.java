package com.chimchar.skycampus.v3.dominio;

import java.util.Optional;

public record ResultadoAsignacion(Optional<Drone> drone, Optional<MotivoRechazo> motivo) {

    public static ResultadoAsignacion asignado(Drone drone) {
        return new ResultadoAsignacion(Optional.of(drone), Optional.empty());
    }

    public static ResultadoAsignacion rechazado(MotivoRechazo motivo) {
        return new ResultadoAsignacion(Optional.empty(), Optional.of(motivo));
    }

    public boolean fueAsignado() {
        return drone.isPresent();
    }
}
