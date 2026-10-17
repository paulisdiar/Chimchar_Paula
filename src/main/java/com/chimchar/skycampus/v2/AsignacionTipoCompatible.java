package com.chimchar.skycampus.v2;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AsignacionTipoCompatible implements EstrategiaAsignacion {

    @Override
    public Optional<Drone> seleccionar(List<Drone> flota, Mision mision) {
        List<Drone> aptos = flota.stream()
                .filter(d -> CriterioApto.esApto(d, mision))
                .toList();

        if (mision.prioridad() == Prioridad.URGENTE) {
            Optional<Drone> express = aptos.stream()
                    .filter(d -> d.tipo() == TipoDrone.EXPRESS)
                    .max(Comparator.comparingInt(Drone::bateria));

            if (express.isPresent()) {
                return express;
            }
        }

        return aptos.stream()
                .min(Comparator
                        .comparingInt(Drone::capacidadGramos)
                        .thenComparing(Comparator.comparingInt(Drone::bateria).reversed()));
    }
}
