package com.chimchar.skycampus.v2;

import java.util.List;
import java.util.Optional;

public class AsignadorMision {

    private final ApiMeteorologica clima;
    private final EstrategiaAsignacion estrategia;

    public AsignadorMision(
            ApiMeteorologica clima,
            EstrategiaAsignacion estrategia
    ) {
        this.clima = clima;
        this.estrategia = estrategia;
    }

    public Optional<Drone> asignar(List<Drone> flota, Mision mision) {
        if (!clima.esApto()) {
            return Optional.empty();
        }

        return estrategia.seleccionar(flota, mision);
    }
}
