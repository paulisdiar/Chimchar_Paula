package com.chimchar.skycampus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AsignadorMisionTest {

    private RepositorioMisionMemoria repositorio;
    private List<String> mensajes;
    private AsignadorMision asignador;
    private Mision mision;

    @BeforeEach
    void setUp() {
        repositorio = new RepositorioMisionMemoria();
        mensajes = new ArrayList<>();
        asignador = new AsignadorMision(
                new AsignacionMayorBateria(),
                repositorio,
                (operador, mensaje) -> mensajes.add(operador + ": " + mensaje)
        );
        mision = new Mision(
                "M-1", null, "Bloque A", "Biblioteca",
                TipoCarga.SOBRE, EstadoMision.PENDIENTE
        );
    }

    @Test
    @DisplayName("Asigna el drone con mayor batería, guarda y alerta")
    void asigna_guardaYAlerta() {
        List<Drone> flota = List.of(
                new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A"),
                new Drone("D-03", "DJI Mini 3", 91, true, "Bloque C"),
                new Drone("D-02", "DJI Mini 3", 99, false, "Biblioteca")
        );

        Mision asignada = asignador.asignar(mision, flota);

        assertEquals("D-03", asignada.drone().id());
        assertEquals(1, repositorio.listar().size());
        assertEquals(1, mensajes.size());
        assertTrue(mensajes.get(0).contains("D-03"));
    }

    @Test
    @DisplayName("Sin drones disponibles lanza excepción")
    void sinDrones_lanzaExcepcion() {
        List<Drone> flota = List.of(
                new Drone("D-02", "DJI Mini 3", 42, false, "Biblioteca")
        );

        assertThrows(
                IllegalStateException.class,
                () -> asignador.asignar(mision, flota)
        );
    }

    @Test
    @DisplayName("Guardar una misión con el mismo ID la reemplaza")
    void guardarMismoId_reemplaza() {
        Drone drone = new Drone("D-01", "DJI Mini 3", 85, true, "Bloque A");
        Mision entregada = new Mision(
                "M-1", drone, "Bloque A", "Biblioteca",
                TipoCarga.SOBRE, EstadoMision.ENTREGADA
        );

        repositorio.guardar(mision);
        repositorio.guardar(entregada);

        assertEquals(1, repositorio.listar().size());
        assertEquals(
                EstadoMision.ENTREGADA,
                repositorio.listar().get(0).estado()
        );
    }
}
