package com.chimchar.skycampus.v2;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AsignacionMenorUso implements EstrategiaAsignacion {

    private final Map<String, Integer> usoAcumulado;

    public AsignacionMenorUso(Map<String, Integer> usoAcumulado) {
        this.usoAcumulado = Map.copyOf(usoAcumulado);
    }

    @Override
    public Optional<Drone> seleccionar(List<Drone> flota, Mision mision) {
        return flota.stream()
                .filter(d -> CriterioApto.esApto(d, mision))
                .min(Comparator
                        .comparingInt((Drone d) -> usoAcumulado.getOrDefault(d.id(), 0))
                        .thenComparing(Comparator.comparingInt(Drone::bateria).reversed()));
    }
}
