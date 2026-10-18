package com.chimchar.skycampus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CadenaValidacionTest {

    private Validador cadena;

    @BeforeEach
    void setUp() {
        Validador bateria = new ValidadorBateria();
        bateria.establecerSiguiente(
                        new ValidadorDestino(ValidadorMision.DESTINOS)
                )
                .establecerSiguiente(
                        new ValidadorCarga(
                                Map.of("D-01", 0.5, "D-03", 0.1),
                                Map.of(
                                        TipoCarga.SOBRE, 0.05,
                                        TipoCarga.LIBRO, 0.5
                                )
                        )
                );
        cadena = bateria;
    }

    private Mision mision(Drone drone, String destino, TipoCarga carga) {
        return new Mision(
                "M-1", drone, "Bloque A", destino, carga,
                EstadoMision.PENDIENTE
        );
    }

    @Test
    @DisplayName("Misión válida pasa los tres validadores")
    void misionValida_pasaLaCadena() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");

        boolean resultado = cadena.validar(
                mision(drone, "Biblioteca", TipoCarga.SOBRE)
        );

        assertTrue(resultado);
    }

    @Test
    @DisplayName("Batería insuficiente rechaza la misión")
    void bateriaInsuficiente_rechaza() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 18, true, "Bloque A");

        boolean resultado = cadena.validar(
                mision(drone, "Biblioteca", TipoCarga.SOBRE)
        );

        assertFalse(resultado);
    }

    @Test
    @DisplayName("Misión sin drone es rechazada")
    void sinDrone_rechaza() {
        boolean resultado = cadena.validar(
                mision(null, "Biblioteca", TipoCarga.SOBRE)
        );

        assertFalse(resultado);
    }

    @Test
    @DisplayName("Destino inexistente rechaza la misión")
    void destinoInvalido_rechaza() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");

        boolean resultado = cadena.validar(
                mision(drone, "Edificio Inexistente", TipoCarga.SOBRE)
        );

        assertFalse(resultado);
    }

    @Test
    @DisplayName("Destino nulo rechaza la misión")
    void destinoNulo_rechaza() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");

        boolean resultado = cadena.validar(
                mision(drone, null, TipoCarga.SOBRE)
        );

        assertFalse(resultado);
    }

    @Test
    @DisplayName("Carga que supera la capacidad rechaza la misión")
    void cargaExcedeCapacidad_rechaza() {
        Drone drone = new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C");

        boolean resultado = cadena.validar(
                mision(drone, "Biblioteca", TipoCarga.LIBRO)
        );

        assertFalse(resultado);
    }

    @Test
    @DisplayName("Drone sin capacidad registrada rechaza la misión")
    void droneSinCapacidad_rechaza() {
        Drone drone = new Drone("D-09", "DJI Mini 3", 91, true, "Bloque C");

        boolean resultado = cadena.validar(
                mision(drone, "Biblioteca", TipoCarga.SOBRE)
        );

        assertFalse(resultado);
    }

    @Test
    @DisplayName("Carga sin tipo rechaza la misión")
    void sinTipoCarga_rechaza() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");

        boolean resultado = cadena.validar(
                mision(drone, "Biblioteca", null)
        );

        assertFalse(resultado);
    }

    @Test
    @DisplayName("establecerSiguiente devuelve el validador enlazado")
    void establecerSiguiente_devuelveElSiguiente() {
        Validador primero = new ValidadorBateria();
        Validador segundo = new ValidadorBateria();

        Validador resultado = primero.establecerSiguiente(segundo);

        assertSame(segundo, resultado);
    }
}
