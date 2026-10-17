package com.chimchar.skycampus.v2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AsignadorMisionTest {

    private ApiMeteorologica clima;
    private ObservadorDrone notificador;
    private AsignadorMision asignador;

    @BeforeEach
    void setUp() {
        clima = Mockito.mock(ApiMeteorologica.class);
        notificador = Mockito.mock(ObservadorDrone.class);
        asignador = new AsignadorMision(clima, new AsignacionMayorBateria(), notificador);
    }

    @Test
    @DisplayName("Drone de mayor batería se asigna a misión NORMAL")
    void misionNormal_asignaDroneMayorBateria() {
        Mockito.when(clima.esApto()).thenReturn(true);
        List<Drone> flota = Datos.crearFlotaTest();
        Mision mision = Datos.crearMisionTest(Prioridad.NORMAL, 200);

        Optional<Drone> asignado = asignador.asignar(flota, mision);

        assertTrue(asignado.isPresent());
        assertEquals(91, asignado.get().bateria());
        Mockito.verify(notificador).onEstadoCambiado(asignado.get(), EstadoDrone.EN_VUELO);
    }

    @Test
    @DisplayName("Con clima adverso no se asigna ningún drone")
    void climaAdverso_retornaVacio() {
        Mockito.when(clima.esApto()).thenReturn(false);
        List<Drone> flota = Datos.crearFlotaTest();
        Mision mision = Datos.crearMisionTest(Prioridad.NORMAL, 200);

        Optional<Drone> resultado = asignador.asignar(flota, mision);

        assertTrue(resultado.isEmpty());
        Mockito.verify(notificador, Mockito.never())
                .onEstadoCambiado(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Sin drones aptos devuelve vacío")
    void sinDronesAptos_retornaVacio() {
        Mockito.when(clima.esApto()).thenReturn(true);
        List<Drone> flota = List.of(
                Datos.drone("D-01", TipoDrone.MINI, 20, EstadoDrone.DISPONIBLE, 500),
                Datos.drone("D-02", TipoDrone.MINI, 90, EstadoDrone.FALLO, 500)
        );
        Mision mision = Datos.crearMisionTest(Prioridad.NORMAL, 200);

        Optional<Drone> resultado = asignador.asignar(flota, mision);

        assertTrue(resultado.isEmpty());
        Mockito.verify(notificador, Mockito.never()).onEstadoCambiado(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Paquete más pesado que la capacidad de la flota lanza excepción con el máximo")
    void paqueteMuyPesado_lanzaExcepcion() {
        Mockito.when(clima.esApto()).thenReturn(true);
        List<Drone> flota = Datos.crearFlotaTest();
        Mision mision = Datos.crearMisionTest(Prioridad.NORMAL, 3000);

        PaqueteMuyPesadoException error = assertThrows(
                PaqueteMuyPesadoException.class,
                () -> asignador.asignar(flota, mision)
        );

        assertEquals(2000, error.pesoMaximoGramos());
        Mockito.verify(notificador, Mockito.never()).onEstadoCambiado(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Misión URGENTE recibe un drone EXPRESS")
    void misionUrgente_asignaDroneExpress() {
        Mockito.when(clima.esApto()).thenReturn(true);
        AsignadorMision urgente = new AsignadorMision(
                clima, new AsignacionTipoCompatible(), notificador
        );
        List<Drone> flota = Datos.crearFlotaTest();
        Mision mision = Datos.crearMisionTest(Prioridad.URGENTE, 300);

        Optional<Drone> asignado = urgente.asignar(flota, mision);

        assertEquals(TipoDrone.EXPRESS, asignado.orElseThrow().tipo());
        assertEquals("D-03", asignado.get().id());
        Mockito.verify(notificador).onEstadoCambiado(asignado.get(), EstadoDrone.EN_VUELO);
    }

    @Test
    @DisplayName("Con la flota sin drones disponibles devuelve vacío")
    void flotaVacia_retornaVacio() {
        Mockito.when(clima.esApto()).thenReturn(true);
        Mision mision = Datos.crearMisionTest(Prioridad.NORMAL, 200);

        Optional<Drone> resultado = asignador.asignar(List.of(), mision);

        assertTrue(resultado.isEmpty());
    }
}
