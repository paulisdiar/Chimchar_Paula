package com.chimchar.skycampus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MisionBuilderTest {

    private final Drone drone =
            new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C");

    private MisionBuilder base() {
        return new MisionBuilder()
                .id("M-1")
                .drone(drone)
                .origen("Bloque C")
                .destino("Biblioteca")
                .tipoCarga(TipoCarga.SOBRE);
    }

    @Test
    @DisplayName("Los campos opcionales toman su valor por defecto")
    void campoOpcionalesPorDefecto() {
        Mision mision = base().build();

        assertEquals(3, mision.prioridad());
        assertEquals("", mision.notas());
        assertNull(mision.horaMaximaEntrega());
        assertEquals(EstadoMision.PENDIENTE, mision.estado());
    }

    @Test
    @DisplayName("Los campos opcionales se pueden definir")
    void campoOpcionalesDefinidos() {
        LocalDateTime limite = LocalDateTime.of(2026, 10, 20, 15, 0);

        Mision mision = base()
                .prioridad(1)
                .notas("Urgente: examen mañana")
                .horaMaximaEntrega(limite)
                .estado(EstadoMision.EN_VUELO)
                .build();

        assertEquals(1, mision.prioridad());
        assertEquals("Urgente: examen mañana", mision.notas());
        assertEquals(limite, mision.horaMaximaEntrega());
        assertEquals(EstadoMision.EN_VUELO, mision.estado());
    }

    @Test
    @DisplayName("Sin drone, origen o destino no se puede construir")
    void faltanObligatorios_lanzaExcepcion() {
        MisionBuilder builder = new MisionBuilder().id("M-1");

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    @DisplayName("Sin ID no se puede construir")
    void sinId_lanzaExcepcion() {
        MisionBuilder builder = new MisionBuilder()
                .drone(drone)
                .origen("Bloque C")
                .destino("Biblioteca")
                .tipoCarga(TipoCarga.SOBRE);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    @DisplayName("Sin tipo de carga no se puede construir")
    void sinTipoCarga_lanzaExcepcion() {
        MisionBuilder builder = new MisionBuilder()
                .id("M-1")
                .drone(drone)
                .origen("Bloque C")
                .destino("Biblioteca");

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    @DisplayName("El constructor reducido de Mision usa valores por defecto")
    void constructorReducido_usaDefectos() {
        Mision mision = new Mision(
                "M-2", drone, "Bloque A", "Bloque B",
                TipoCarga.CARPETA, EstadoMision.PENDIENTE
        );

        assertEquals(3, mision.prioridad());
        assertEquals("", mision.notas());
    }
}
