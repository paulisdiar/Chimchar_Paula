package com.chimchar.skycampus.v2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObservadoresTest {

    private GestorFlota gestor;
    private PanelOperador panel;
    private SistemaLog log;
    private AlertaTecnico tecnico;
    private Drone drone;

    @BeforeEach
    void setUp() {
        gestor = new GestorFlota();
        panel = new PanelOperador();
        log = new SistemaLog();
        tecnico = new AlertaTecnico();
        gestor.suscribir(panel);
        gestor.suscribir(log);
        gestor.suscribir(tecnico);
        drone = Datos.drone("D-01", TipoDrone.MINI, 85, EstadoDrone.DISPONIBLE, 500);
    }

    @Test
    @DisplayName("Un cambio de estado notifica al panel y al log pero no al técnico")
    void cambioNormal() {
        Drone actualizado = gestor.cambiarEstado(drone, EstadoDrone.EN_VUELO);

        assertEquals(EstadoDrone.EN_VUELO, actualizado.estado());
        assertFalse(actualizado.disponible());
        assertEquals(List.of("D-01 -> EN_VUELO"), panel.notificaciones());
        assertEquals(1, log.registros().size());
        assertTrue(tecnico.alertas().isEmpty());
    }

    @Test
    @DisplayName("Un drone en FALLO también alerta al técnico")
    void cambioAFallo() {
        gestor.cambiarEstado(drone, EstadoDrone.FALLO);

        assertEquals(1, tecnico.alertas().size());
        assertTrue(tecnico.alertas().get(0).contains("D-01"));
    }

    @Test
    @DisplayName("Volver a DISPONIBLE marca el drone como disponible")
    void volverADisponible() {
        Drone actualizado = gestor.cambiarEstado(drone, EstadoDrone.DISPONIBLE);

        assertTrue(actualizado.disponible());
    }

    @Test
    @DisplayName("Un cuarto observador se suscribe sin modificar GestorFlota")
    void cuartoObservador() {
        List<String> recibidos = new ArrayList<>();
        ObservadorDrone cuarto = (d, nuevo) -> recibidos.add(d.id() + ":" + nuevo);
        gestor.suscribir(cuarto);

        gestor.cambiarEstado(drone, EstadoDrone.ATERRIZANDO);

        assertEquals(List.of("D-01:ATERRIZANDO"), recibidos);
        assertEquals(1, log.registros().size());
    }

    @Test
    @DisplayName("El panel no acumula más de 7 notificaciones sin confirmar")
    void panelMaximoSiete() {
        for (int i = 0; i < 10; i++) {
            gestor.cambiarEstado(drone, EstadoDrone.EN_VUELO);
        }

        assertEquals(PanelOperador.MAXIMO_SIN_CONFIRMAR, panel.notificaciones().size());
    }

    @Test
    @DisplayName("El cambio de estado llega a todos los observadores en menos de 1 segundo")
    void notificacionRapida() {
        assertTimeout(
                Duration.ofSeconds(1),
                () -> gestor.cambiarEstado(drone, EstadoDrone.FALLO)
        );

        assertEquals(1, panel.notificaciones().size());
        assertEquals(1, log.registros().size());
        assertEquals(1, tecnico.alertas().size());
    }
}
