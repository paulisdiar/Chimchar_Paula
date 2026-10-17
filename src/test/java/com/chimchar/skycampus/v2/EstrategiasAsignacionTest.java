package com.chimchar.skycampus.v2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstrategiasAsignacionTest {

    @Test
    @DisplayName("Mayor batería elige el drone apto con más carga")
    void mayorBateria() {
        Optional<Drone> elegido = new AsignacionMayorBateria()
                .seleccionar(Datos.crearFlotaTest(), Datos.crearMisionTest(Prioridad.NORMAL, 200));

        assertEquals("D-03", elegido.orElseThrow().id());
    }

    @Test
    @DisplayName("Menor uso acumulado elige el drone menos usado")
    void menorUso() {
        EstrategiaAsignacion estrategia = new AsignacionMenorUso(
                Map.of("D-01", 5, "D-02", 3, "D-03", 7, "D-05", 3)
        );

        Optional<Drone> elegido = estrategia.seleccionar(
                Datos.crearFlotaTest(), Datos.crearMisionTest(Prioridad.NORMAL, 200)
        );

        assertEquals("D-02", elegido.orElseThrow().id());
    }

    @Test
    @DisplayName("Menor uso desempata por mayor batería")
    void menorUsoDesempata() {
        EstrategiaAsignacion estrategia = new AsignacionMenorUso(Map.of());

        Optional<Drone> elegido = estrategia.seleccionar(
                Datos.crearFlotaTest(), Datos.crearMisionTest(Prioridad.NORMAL, 200)
        );

        assertEquals("D-03", elegido.orElseThrow().id());
    }

    @Test
    @DisplayName("Tipo compatible elige el drone más pequeño que cabe")
    void tipoCompatibleNormal() {
        Optional<Drone> elegido = new AsignacionTipoCompatible()
                .seleccionar(Datos.crearFlotaTest(), Datos.crearMisionTest(Prioridad.NORMAL, 200));

        assertEquals("D-01", elegido.orElseThrow().id());
    }

    @Test
    @DisplayName("Urgente sin EXPRESS apto cae al drone más pequeño que cabe")
    void urgenteSinExpress() {
        List<Drone> flota = List.of(
                Datos.drone("D-01", TipoDrone.MINI, 85, EstadoDrone.DISPONIBLE, 500),
                Datos.drone("D-03", TipoDrone.EXPRESS, 20, EstadoDrone.DISPONIBLE, 800)
        );

        Optional<Drone> elegido = new AsignacionTipoCompatible()
                .seleccionar(flota, Datos.crearMisionTest(Prioridad.URGENTE, 300));

        assertEquals("D-01", elegido.orElseThrow().id());
    }

    @Test
    @DisplayName("Una estrategia nueva se usa sin cambiar el asignador")
    void estrategiaNuevaComoLambda() {
        EstrategiaAsignacion ultimo = (flota, mision) -> flota.stream()
                .filter(d -> CriterioApto.esApto(d, mision))
                .reduce((a, b) -> b);

        Optional<Drone> elegido = ultimo.seleccionar(
                Datos.crearFlotaTest(), Datos.crearMisionTest(Prioridad.NORMAL, 200)
        );

        assertEquals("D-05", elegido.orElseThrow().id());
    }

    @ParameterizedTest(name = "{0} con {1}% de batería y {2} g")
    @CsvSource({
            "MINI, 85, 200, true",
            "MINI, 29, 200, false",
            "MINI, 85, 600, false",
            "CARGO, 85, 99, false",
            "CARGO, 85, 100, true",
            "EXPRESS, 30, 800, true"
    })
    @DisplayName("Criterio de aptitud del drone")
    void criterioApto(TipoDrone tipo, int bateria, int peso, boolean esperado) {
        int capacidad = tipo == TipoDrone.CARGO ? 2000 : tipo == TipoDrone.EXPRESS ? 800 : 500;
        Drone drone = Datos.drone("D-09", tipo, bateria, EstadoDrone.DISPONIBLE, capacidad);

        boolean resultado = CriterioApto.esApto(drone, Datos.crearMisionTest(Prioridad.NORMAL, peso));

        assertEquals(esperado, resultado);
    }

    @Test
    @DisplayName("Un drone ocupado no es apto aunque marque disponible")
    void droneEnVueloNoEsApto() {
        Drone drone = new Drone("D-09", TipoDrone.MINI, 90, true, EstadoDrone.EN_VUELO, 500);

        assertTrue(!CriterioApto.esApto(drone, Datos.crearMisionTest(Prioridad.NORMAL, 100)));
    }
}
