package com.chimchar.skycampus.v2;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AsignacionMayorBateria implements EstrategiaAsignacion {

    @Override
    public Optional<Drone> seleccionar(List<Drone> flota, Mision mision) {
        return flota.stream()
                .filter(d -> CriterioApto.esApto(d, mision))
                .max(Comparator.comparingInt(Drone::bateria));
    }
}
