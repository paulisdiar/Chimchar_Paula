package com.chimchar.skycampus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AsignacionMayorBateriaTest {

    private final EstrategiaAsignacion estrategia =
            new AsignacionMayorBateria();

    @Test
    @DisplayName("Elige el drone disponible con más batería")
    void eligeMayorBateria() {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A"),
                new Drone("D-05", "DJI Mini 3", 67, true, "Bloque D"),
                new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C")
        );

        Optional<Drone> elegido = estrategia.elegir(flota);

        assertEquals("D-03", elegido.orElseThrow().id());
    }

    @Test
    @DisplayName("Ignora los drones no disponibles")
    void ignoraNoDisponibles() {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 99, false, "Bloque A"),
                new Drone("D-05", "DJI Mini 3", 67, true, "Bloque D")
        );

        Optional<Drone> elegido = estrategia.elegir(flota);

        assertEquals("D-05", elegido.orElseThrow().id());
    }

    @Test
    @DisplayName("Sin drones disponibles devuelve vacío")
    void sinDisponibles_devuelveVacio() {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 99, false, "Bloque A")
        );

        Optional<Drone> elegido = estrategia.elegir(flota);

        assertTrue(elegido.isEmpty());
    }
}
