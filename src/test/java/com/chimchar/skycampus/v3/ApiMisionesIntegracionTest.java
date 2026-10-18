package com.chimchar.skycampus.v3;

import com.chimchar.skycampus.v2.EstadoDrone;
import com.chimchar.skycampus.v2.Prioridad;
import com.chimchar.skycampus.v2.TipoDrone;
import com.chimchar.skycampus.v3.aplicacion.AsignadorMision;
import com.chimchar.skycampus.v3.aplicacion.GestorZonas;
import com.chimchar.skycampus.v3.aplicacion.MonitorFlota;
import com.chimchar.skycampus.v3.dominio.Drone;
import com.chimchar.skycampus.v3.dominio.SeleccionMayorBateria;
import com.chimchar.skycampus.v3.infraestructura.ApiMisiones;
import com.chimchar.skycampus.v3.infraestructura.RepositorioDronesMemoria;
import com.chimchar.skycampus.v3.infraestructura.RepositorioMisionesMemoria;
import com.chimchar.skycampus.v3.infraestructura.RespuestaMision;
import com.chimchar.skycampus.v3.infraestructura.SolicitudMision;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class ApiMisionesIntegracionTest {

    private boolean climaApto;
    private boolean aerocivilAutoriza;
    private GestorZonas zonas;
    private RepositorioDronesMemoria drones;
    private RepositorioMisionesMemoria misiones;
    private ApiMisiones api;

    @BeforeEach
    void preparar() {
        climaApto = true;
        aerocivilAutoriza = true;
        zonas = new GestorZonas(Set.of("ECI", "UNAL"));
        drones = new RepositorioDronesMemoria();
        misiones = new RepositorioMisionesMemoria();
        drones.guardar(Datos3.drone("D-01", "ECI", TipoDrone.MINI, 85, 500));
        drones.guardar(Datos3.drone("D-02", "ECI", TipoDrone.EXPRESS, 91, 800));
        drones.guardar(Datos3.drone("D-07", "UNAL", TipoDrone.CARGO, 70, 2000));
        api = new ApiMisiones(new AsignadorMision(
                zonas, sede -> climaApto, m -> aerocivilAutoriza, drones, misiones, new SeleccionMayorBateria()));
    }

    private SolicitudMision solicitud(String sede, int peso) {
        return new SolicitudMision(sede, "UNAL", peso, Prioridad.NORMAL);
    }

    @Test
    @DisplayName("Clima apto crea la misión con el drone asignado en vuelo")
    void climaAptoCreaMision() {
        RespuestaMision r = api.crear(solicitud("ECI", 300));

        assertEquals(201, r.codigo());
        assertEquals("D-02", r.droneId());
        assertEquals("EN_VUELO", r.estado());
        assertEquals(1, misiones.todas().size());
        assertEquals(EstadoDrone.EN_VUELO, drones.porSede("ECI").get(1).estado());
    }

    @Test
    @DisplayName("Clima adverso responde 409")
    void climaAdverso() {
        climaApto = false;

        RespuestaMision r = api.crear(solicitud("ECI", 300));

        assertEquals(409, r.codigo());
        assertEquals("CLIMA_ADVERSO", r.detalle());
        assertNull(r.droneId());
    }

    @Test
    @DisplayName("Sin drones disponibles responde 409")
    void sinDrones() {
        api.crear(solicitud("ECI", 300));
        api.crear(solicitud("ECI", 300));

        RespuestaMision r = api.crear(solicitud("ECI", 300));

        assertEquals("SIN_DRONES", r.detalle());
    }

    @Test
    @DisplayName("Paquete pesado responde 422")
    void paquetePesado() {
        RespuestaMision r = api.crear(solicitud("ECI", 2500));

        assertEquals(422, r.codigo());
        assertEquals("PAQUETE_PESADO", r.detalle());
    }

    @Test
    @DisplayName("Aerocivil rechaza y no cambia la flota")
    void aerocivilRechaza() {
        aerocivilAutoriza = false;

        RespuestaMision r = api.crear(solicitud("ECI", 300));

        assertEquals("AEROCIVIL_RECHAZA", r.detalle());
        assertEquals(0, misiones.todas().size());
    }

    @Test
    @DisplayName("Sede inactiva responde 409 y el monitor muestra la flota por sede")
    void sedeInactiva() {
        zonas.desactivar("UNAL");

        RespuestaMision r = api.crear(solicitud("UNAL", 300));
        Map<String, Map<TipoDrone, Long>> flota = new MonitorFlota(drones).dronesPorSedeYTipo();

        assertEquals("SEDE_INACTIVA", r.detalle());
        assertEquals(1L, flota.get("UNAL").get(TipoDrone.CARGO));
        assertEquals(2, flota.get("ECI").size());
    }

    @Test
    @DisplayName("Asignar con 100 drones en la red tarda menos de 500 ms")
    void asignacionRapidaConCienDrones() {
        for (int i = 0; i < 100; i++) {
            drones.guardar(Datos3.drone("X-" + i, "ECI", TipoDrone.MINI, 40 + i % 50, 500));
        }

        assertTimeout(Duration.ofMillis(500), () -> api.crear(solicitud("ECI", 300)));
    }
}
