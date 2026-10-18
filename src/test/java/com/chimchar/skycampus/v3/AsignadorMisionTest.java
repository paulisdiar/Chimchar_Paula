package com.chimchar.skycampus.v3;

import com.chimchar.skycampus.v2.EstadoDrone;
import com.chimchar.skycampus.v2.TipoDrone;
import com.chimchar.skycampus.v3.aplicacion.AsignadorMision;
import com.chimchar.skycampus.v3.dominio.Drone;
import com.chimchar.skycampus.v3.dominio.Mision;
import com.chimchar.skycampus.v3.dominio.MotivoRechazo;
import com.chimchar.skycampus.v3.dominio.RegistroSedes;
import com.chimchar.skycampus.v3.dominio.RepositorioDrones;
import com.chimchar.skycampus.v3.dominio.RepositorioMisiones;
import com.chimchar.skycampus.v3.dominio.ResultadoAsignacion;
import com.chimchar.skycampus.v3.dominio.SeleccionMayorBateria;
import com.chimchar.skycampus.v3.dominio.ServicioAerocivil;
import com.chimchar.skycampus.v3.dominio.ServicioClima;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AsignadorMisionTest {

    private RegistroSedes sedes;
    private ServicioClima clima;
    private ServicioAerocivil aerocivil;
    private RepositorioDrones drones;
    private RepositorioMisiones misiones;
    private AsignadorMision asignador;

    @BeforeEach
    void preparar() {
        sedes = Mockito.mock(RegistroSedes.class);
        clima = Mockito.mock(ServicioClima.class);
        aerocivil = Mockito.mock(ServicioAerocivil.class);
        drones = Mockito.mock(RepositorioDrones.class);
        misiones = Mockito.mock(RepositorioMisiones.class);
        asignador = new AsignadorMision(sedes, clima, aerocivil, drones, misiones, new SeleccionMayorBateria());

        when(sedes.estaActiva("ECI")).thenReturn(true);
        when(clima.condicionesAptas("ECI")).thenReturn(true);
        when(aerocivil.autoriza(any())).thenReturn(true);
        when(drones.porSede("ECI")).thenReturn(List.of(
                Datos3.drone("D-01", "ECI", TipoDrone.MINI, 85, 500),
                Datos3.drone("D-02", "ECI", TipoDrone.EXPRESS, 91, 800)
        ));
    }

    @Test
    @DisplayName("Asigna el drone apto con más batería y lo guarda en vuelo")
    void asignaMayorBateria() {
        ResultadoAsignacion r = asignador.asignar(Datos3.pendiente("ECI", 300));

        assertEquals("D-02", r.drone().orElseThrow().id());
        verify(drones).guardar(any(Drone.class));
        verify(misiones).guardar(any(Mision.class));
    }

    @Test
    @DisplayName("Sede inactiva rechaza sin consultar clima")
    void sedeInactiva() {
        when(sedes.estaActiva("ECI")).thenReturn(false);

        ResultadoAsignacion r = asignador.asignar(Datos3.pendiente("ECI", 300));

        assertEquals(MotivoRechazo.SEDE_INACTIVA, r.motivo().orElseThrow());
        verify(clima, never()).condicionesAptas(any());
    }

    @Test
    @DisplayName("Clima adverso no asigna ni guarda")
    void climaAdverso() {
        when(clima.condicionesAptas("ECI")).thenReturn(false);

        ResultadoAsignacion r = asignador.asignar(Datos3.pendiente("ECI", 300));

        assertEquals(MotivoRechazo.CLIMA_ADVERSO, r.motivo().orElseThrow());
        verify(drones, never()).guardar(any());
    }

    @Test
    @DisplayName("Sin drones aptos rechaza")
    void sinDrones() {
        when(drones.porSede("ECI")).thenReturn(List.of(
                new Drone("D-04", "ECI", TipoDrone.MINI, 18, EstadoDrone.DISPONIBLE, 500)
        ));

        ResultadoAsignacion r = asignador.asignar(Datos3.pendiente("ECI", 300));

        assertEquals(MotivoRechazo.SIN_DRONES, r.motivo().orElseThrow());
    }

    @Test
    @DisplayName("Paquete más pesado que cualquier drone apto rechaza")
    void paqueteMuyPesado() {
        ResultadoAsignacion r = asignador.asignar(Datos3.pendiente("ECI", 2500));

        assertEquals(MotivoRechazo.PAQUETE_PESADO, r.motivo().orElseThrow());
    }

    @Test
    @DisplayName("Aerocivil rechaza la ruta")
    void aerocivilRechaza() {
        when(aerocivil.autoriza(any())).thenReturn(false);

        ResultadoAsignacion r = asignador.asignar(Datos3.pendiente("ECI", 300));

        assertEquals(MotivoRechazo.AEROCIVIL_RECHAZA, r.motivo().orElseThrow());
        assertTrue(!r.fueAsignado());
    }
}
