package com.chimchar.skycampus.v3;

import com.chimchar.skycampus.EstadoMision;
import com.chimchar.skycampus.v2.Prioridad;
import com.chimchar.skycampus.v3.aplicacion.AnalisisRed;
import com.chimchar.skycampus.v3.aplicacion.MetricasSede;
import com.chimchar.skycampus.v3.dominio.Mision;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnalisisRedTest {

    private final AnalisisRed analisis = new AnalisisRed();

    static Stream<Object[]> escenarios() {
        Mision unica = Datos3.mision("M-1", "ECI", Prioridad.URGENTE, EstadoMision.ENTREGADA, "D-01", 10);
        List<Mision> red = List.of(
                Datos3.mision("M-1", "ECI", Prioridad.URGENTE, EstadoMision.ENTREGADA, "D-01", 10),
                Datos3.mision("M-2", "ECI", Prioridad.NORMAL, EstadoMision.ENTREGADA, "D-01", 20),
                Datos3.mision("M-3", "ECI", Prioridad.NORMAL, EstadoMision.FALLIDA, "D-02", 0),
                Datos3.mision("M-4", "UNAL", Prioridad.NORMAL, EstadoMision.ENTREGADA, "D-07", 30)
        );
        List<Mision> empate = List.of(
                Datos3.mision("M-1", "UNAL", Prioridad.NORMAL, EstadoMision.ENTREGADA, "D-07", 10),
                Datos3.mision("M-2", "ECI", Prioridad.NORMAL, EstadoMision.ENTREGADA, "D-01", 10)
        );
        return Stream.of(
                new Object[]{"sede vacía", List.of(), "ECI", Optional.empty()},
                new Object[]{"una misión", List.of(unica), "ECI", Optional.of(100.0)},
                new Object[]{"empate entre sedes", empate, "ECI", Optional.of(100.0)},
                new Object[]{"red completa", red, "ECI", Optional.of(200.0 / 3)}
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("escenarios")
    @DisplayName("Tasa de éxito por sede en distintos escenarios")
    void tasaDeExito(String nombre, List<Mision> misiones, String sede, Optional<Double> esperada) {
        Optional<MetricasSede> metricas = analisis.metricasDe(sede, misiones);

        assertEquals(esperada.isPresent(), metricas.isPresent());
        esperada.ifPresent(t -> assertEquals(t, metricas.orElseThrow().tasaExito(), 0.0001));
    }

    @Test
    @DisplayName("Una sola pasada entrega tiempo, drone más usado y urgentes")
    void metricasCompletas() {
        List<Mision> misiones = List.of(
                Datos3.mision("M-1", "ECI", Prioridad.URGENTE, EstadoMision.ENTREGADA, "D-01", 10),
                Datos3.mision("M-2", "ECI", Prioridad.NORMAL, EstadoMision.ENTREGADA, "D-01", 20),
                Datos3.mision("M-3", "ECI", Prioridad.NORMAL, EstadoMision.FALLIDA, "D-02", 0),
                Datos3.mision("M-4", "ECI", Prioridad.URGENTE, EstadoMision.PENDIENTE, null, 0)
        );

        MetricasSede m = analisis.metricasPorSede(misiones).get("ECI");

        assertEquals(4, m.total());
        assertEquals(15.0, m.tiempoPromedioMinutos(), 0.0001);
        assertEquals(Optional.of("D-01"), m.droneMasUtilizado());
        assertEquals(50.0, m.porcentajeUrgentes(), 0.0001);
    }

    @Test
    @DisplayName("Sede más activa y ranking con comparador compuesto")
    void sedeMasActivaYRanking() {
        List<Mision> misiones = List.of(
                Datos3.mision("M-1", "ECI", Prioridad.NORMAL, EstadoMision.ENTREGADA, "D-01", 10),
                Datos3.mision("M-2", "ECI", Prioridad.NORMAL, EstadoMision.ENTREGADA, "D-01", 10),
                Datos3.mision("M-3", "UNAL", Prioridad.NORMAL, EstadoMision.ENTREGADA, "D-07", 10),
                Datos3.mision("M-4", "UNIANDES", Prioridad.NORMAL, EstadoMision.FALLIDA, "D-09", 0)
        );

        Map<String, MetricasSede> metricas = analisis.metricasPorSede(misiones);

        assertEquals(Optional.of("ECI"), analisis.sedeMasActiva(misiones));
        assertEquals(List.of("ECI", "UNAL", "UNIANDES"), analisis.ranking(metricas));
        assertTrue(analisis.sedeMasActiva(List.of()).isEmpty());
    }
}
