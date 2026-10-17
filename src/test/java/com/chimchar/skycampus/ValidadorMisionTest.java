package com.chimchar.skycampus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidadorMisionTest {

    private ValidadorMision validador;

    @BeforeEach
    void setUp() {
        validador = new ValidadorMision();
    }

    @Test
    @DisplayName("Drone con batería de 85% puede ser asignado")
    void droneBateriaSuficiente_puedeAsignarse() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");

        boolean resultado = validador.tieneBateriaSuficiente(drone);

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Drone con batería de 18% no puede ser asignado")
    void droneBateriaCritica_noAsignable() {
        Drone drone = new Drone("D-04", "DJI Mini 3", 18, true, "Bloque B");

        boolean resultado = validador.tieneBateriaSuficiente(drone);

        assertFalse(resultado);
    }

    @Test
    @DisplayName("Drone con exactamente 30% de batería puede ser asignado")
    void droneBateriaEnElLimite_puedeAsignarse() {
        Drone drone = new Drone("D-05", "DJI Mini 3", 30, true, "Bloque D");

        boolean resultado = validador.tieneBateriaSuficiente(drone);

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Destino existente no lanza excepción")
    void destinoValido_noLanzaExcepcion() {
        String destino = "Biblioteca";

        assertDoesNotThrow(() -> validador.validarDestino(destino));
    }

    @Test
    @DisplayName("Destino inexistente lanza DestinoInvalidoException")
    void destinoInvalido_lanzaExcepcion() {
        String destino = "Edificio Inexistente";

        assertThrows(
                DestinoInvalidoException.class,
                () -> validador.validarDestino(destino)
        );
    }

    @Test
    @DisplayName("Destino nulo lanza DestinoInvalidoException")
    void destinoNulo_lanzaExcepcion() {
        assertThrows(
                DestinoInvalidoException.class,
                () -> validador.validarDestino(null)
        );
    }

    @Test
    @DisplayName("Drone disponible es reconocido como disponible")
    void droneDisponible_devuelveVerdadero() {
        Drone drone = new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C");

        boolean resultado = validador.droneEstaDisponible(drone);

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Drone en misión no está disponible")
    void droneOcupado_devuelveFalso() {
        Drone drone = new Drone("D-02", "DJI Mini 3", 42, false, "Biblioteca");

        boolean resultado = validador.droneEstaDisponible(drone);

        assertFalse(resultado);
    }

    @Test
    @DisplayName("La disponibilidad no depende del nivel de batería")
    void droneDisponibleConBateriaCritica_siguienteDisponible() {
        Drone drone = new Drone("D-04", "DJI Mini 3", 18, true, "Bloque B");

        boolean resultado = validador.droneEstaDisponible(drone);

        assertTrue(resultado);
    }
}
