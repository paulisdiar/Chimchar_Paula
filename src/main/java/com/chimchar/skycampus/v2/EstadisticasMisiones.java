package com.chimchar.skycampus.v2;

import com.chimchar.skycampus.EstadoMision;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstadisticasMisiones {

    public Map<TipoDrone, Long> completadasPorTipo(List<Mision> misiones) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.ENTREGADA)
                .collect(Collectors.groupingBy(
                        m -> m.drone().tipo(),
                        Collectors.counting()
                ));
    }

    public Optional<Drone> droneConMasCompletadas(List<Mision> misiones) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.ENTREGADA)
                .collect(Collectors.groupingBy(
                        Mision::drone,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public double porcentajeFallidas(List<Mision> misiones) {
        if (misiones.isEmpty()) {
            return 0;
        }

        long fallidas = misiones.stream()
                .filter(m -> m.estado() == EstadoMision.FALLIDA)
                .count();

        return fallidas * 100.0 / misiones.size();
    }

    public boolean hayUrgentePendienteMasDe(
            List<Mision> misiones,
            LocalDateTime ahora,
            Duration limite
    ) {
        return misiones.stream()
                .filter(m -> m.prioridad() == Prioridad.URGENTE)
                .filter(m -> m.estado() == EstadoMision.PENDIENTE)
                .anyMatch(m -> Duration.between(m.creadaEn(), ahora)
                        .compareTo(limite) > 0);
    }

    public Map<Boolean, List<Drone>> operatividad(List<Drone> flota) {
        return flota.stream()
                .collect(Collectors.partitioningBy(
                        d -> d.estado() != EstadoDrone.FALLO
                ));
    }

    public int pesoEnVuelo(List<Mision> misiones) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.EN_VUELO)
                .mapToInt(Mision::pesoPaqueteGramos)
                .sum();
    }
}
