package com.chimchar.skycampus;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AsignacionMayorBateria implements EstrategiaAsignacion {

    @Override
    public Optional<Drone> elegir(List<Drone> flota) {
        return flota.stream()
                .filter(Drone::disponible)
                .max(Comparator.comparingInt(Drone::bateria));
    }
}
