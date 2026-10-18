package com.chimchar.skycampus.v3.aplicacion;

import com.chimchar.skycampus.EstadoMision;
import com.chimchar.skycampus.v2.Prioridad;
import com.chimchar.skycampus.v3.dominio.Mision;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AnalisisRed {

    public Map<String, MetricasSede> metricasPorSede(List<Mision> misiones) {
        return misiones.stream()
                .collect(Collectors.groupingBy(Mision::sedeOrigen, acumulador()));
    }

    public Optional<MetricasSede> metricasDe(String sede, List<Mision> misiones) {
        return Optional.ofNullable(metricasPorSede(misiones).get(sede));
    }

    public Optional<String> sedeMasActiva(List<Mision> misiones) {
        return misiones.stream()
                .filter(m -> m.estado() == EstadoMision.ENTREGADA)
                .collect(Collectors.groupingBy(Mision::sedeOrigen, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.<String, Long>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                .map(Map.Entry::getKey);
    }

    public List<String> ranking(Map<String, MetricasSede> metricas) {
        Comparator<String> orden = Comparator
                .comparingDouble((String s) -> metricas.get(s).tasaExito()).reversed()
                .thenComparing(Comparator.comparingLong((String s) -> metricas.get(s).total()).reversed())
                .thenComparing(Comparator.naturalOrder());
        return metricas.keySet().stream().sorted(orden).toList();
    }

    private Collector<Mision, Acumulador, MetricasSede> acumulador() {
        return Collector.of(Acumulador::new, Acumulador::agregar, Acumulador::combinar, Acumulador::resultado);
    }

    private static final class Acumulador {
        private long total;
        private long entregadas;
        private long minutos;
        private long urgentes;
        private final Map<String, Long> usos = new HashMap<>();

        void agregar(Mision mision) {
            total++;
            if (mision.estado() == EstadoMision.ENTREGADA) {
                entregadas++;
                minutos += mision.minutosEntrega();
            }
            if (mision.prioridad() == Prioridad.URGENTE) {
                urgentes++;
            }
            if (mision.droneId() != null) {
                usos.merge(mision.droneId(), 1L, Long::sum);
            }
        }

        Acumulador combinar(Acumulador otro) {
            total += otro.total;
            entregadas += otro.entregadas;
            minutos += otro.minutos;
            urgentes += otro.urgentes;
            otro.usos.forEach((id, n) -> usos.merge(id, n, Long::sum));
            return this;
        }

        MetricasSede resultado() {
            Optional<String> masUsado = usos.entrySet().stream()
                    .max(Map.Entry.<String, Long>comparingByValue().thenComparing(Map.Entry.comparingByKey()))
                    .map(Map.Entry::getKey);
            double promedio = entregadas == 0 ? 0 : (double) minutos / entregadas;
            return new MetricasSede(
                    total,
                    100.0 * entregadas / total,
                    promedio,
                    masUsado,
                    100.0 * urgentes / total
            );
        }
    }
}
