package com.chimchar.skycampus.v2;

import com.chimchar.skycampus.EstadoMision;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestorMisionesTest {

    private static final ApiMeteorologica CLIMA_APTO = () -> true;

    static Stream<EstrategiaAsignacion> estrategias() {
        return Stream.of(
                new AsignacionMayorBateria(),
                new AsignacionMenorUso(Map.of("D-01", 4)),
                new AsignacionTipoCompatible(),
                (flota, mision) -> flota.stream()
                        .filter(d -> CriterioApto.esApto(d, mision))
                        .findFirst()
        );
    }

    @ParameterizedTest
    @MethodSource("estrategias")
    @DisplayName("GestorMisiones funciona con cualquier EstrategiaAsignacion")
    void funcionaConCualquierEstrategia(EstrategiaAsignacion estrategia) {
        List<Drone> notificados = new ArrayList<>();
        GestorMisiones gestor = new GestorMisiones(
                new AsignadorMision(CLIMA_APTO, estrategia, (d, e) -> notificados.add(d))
        );

        Optional<Mision> resultado = gestor.procesar(
                Datos.crearMisionTest(Prioridad.NORMAL, 200), Datos.crearFlotaTest()
        );

        assertTrue(resultado.isPresent());
        assertEquals(EstadoMision.EN_VUELO, resultado.get().estado());
        assertEquals(1, notificados.size());
        assertEquals(1, gestor.misiones().size());
    }

    @Test
    @DisplayName("Sin drone asignable la misión queda registrada como pendiente")
    void sinDrone_quedaPendiente() {
        ApiMeteorologica adverso = () -> false;
        GestorMisiones gestor = new GestorMisiones(
                new AsignadorMision(adverso, new AsignacionMayorBateria(), (d, e) -> { })
        );

        Optional<Mision> resultado = gestor.procesar(
                Datos.crearMisionTest(Prioridad.NORMAL, 200), Datos.crearFlotaTest()
        );

        assertTrue(resultado.isEmpty());
        assertEquals(EstadoMision.PENDIENTE, gestor.misiones().get(0).estado());
    }

    @Test
    @DisplayName("La asignación con 50 drones tarda menos de 500 ms")
    void asignacionRapidaCon50Drones() {
        List<Drone> flota = IntStream.rangeClosed(1, 50)
                .mapToObj(i -> Datos.drone(
                        "D-" + i, TipoDrone.MINI, 30 + i, EstadoDrone.DISPONIBLE, 500))
                .toList();
        AsignadorMision asignador = new AsignadorMision(
                CLIMA_APTO, new AsignacionMayorBateria(), (d, e) -> { });

        Optional<Drone> asignado = assertTimeout(
                Duration.ofMillis(500),
                () -> asignador.asignar(flota, Datos.crearMisionTest(Prioridad.NORMAL, 200))
        );

        assertEquals("D-50", asignado.orElseThrow().id());
    }
}
