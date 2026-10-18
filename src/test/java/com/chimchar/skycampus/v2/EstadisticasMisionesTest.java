package com.chimchar.skycampus.v2;

import com.chimchar.skycampus.EstadoMision;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstadisticasMisionesTest {

    private final EstadisticasMisiones estadisticas = new EstadisticasMisiones();

    private final Drone mini = Datos.drone("D-01", TipoDrone.MINI, 85, EstadoDrone.DISPONIBLE, 500);
    private final Drone cargo = Datos.drone("D-02", TipoDrone.CARGO, 67, EstadoDrone.DISPONIBLE, 2000);
    private final Drone express = Datos.drone("D-03", TipoDrone.EXPRESS, 91, EstadoDrone.DISPONIBLE, 800);

    private List<Mision> misionesDelDia() {
        return List.of(
                Datos.mision("M-1", mini, Prioridad.NORMAL, EstadoMision.ENTREGADA, Datos.AHORA),
                Datos.mision("M-2", mini, Prioridad.NORMAL, EstadoMision.ENTREGADA, Datos.AHORA),
                Datos.mision("M-3", express, Prioridad.URGENTE, EstadoMision.ENTREGADA, Datos.AHORA),
                Datos.mision("M-4", cargo, Prioridad.BAJO, EstadoMision.FALLIDA, Datos.AHORA),
                Datos.mision("M-5", cargo, Prioridad.URGENTE, EstadoMision.PENDIENTE,
                        Datos.AHORA.minusMinutes(15)),
                Datos.mision("M-6", express, Prioridad.NORMAL, EstadoMision.EN_VUELO, Datos.AHORA)
        );
    }

    @Test
    @DisplayName("Misiones completadas por tipo de drone")
    void completadasPorTipo() {
        Map<TipoDrone, Long> resultado = estadisticas.completadasPorTipo(misionesDelDia());

        assertEquals(2L, resultado.get(TipoDrone.MINI));
        assertEquals(1L, resultado.get(TipoDrone.EXPRESS));
        assertFalse(resultado.containsKey(TipoDrone.CARGO));
    }

    @Test
    @DisplayName("Drone con más misiones completadas")
    void droneConMasCompletadas() {
        assertEquals("D-01", estadisticas.droneConMasCompletadas(misionesDelDia()).orElseThrow().id());
    }

    @Test
    @DisplayName("Sin misiones completadas no hay drone destacado")
    void sinCompletadas() {
        assertTrue(estadisticas.droneConMasCompletadas(List.of()).isEmpty());
    }

    @Test
    @DisplayName("Porcentaje de misiones fallidas")
    void porcentajeFallidas() {
        assertEquals(100.0 / 6, estadisticas.porcentajeFallidas(misionesDelDia()), 0.0001);
        assertEquals(0, estadisticas.porcentajeFallidas(List.of()));
    }
}
