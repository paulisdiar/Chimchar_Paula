package com.chimchar.skycampus.v3;

import com.chimchar.skycampus.v2.TipoDrone;
import com.chimchar.skycampus.v3.aplicacion.PlanificadorRuta;
import com.chimchar.skycampus.v3.dominio.ruta.MenorDistancia;
import com.chimchar.skycampus.v3.dominio.ruta.Ruta;
import com.chimchar.skycampus.v3.dominio.ruta.RutaCompuesta;
import com.chimchar.skycampus.v3.dominio.ruta.SeguimientoRuta;
import com.chimchar.skycampus.v3.dominio.ruta.Tramo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RutasTest {

    private final PlanificadorRuta planificador = new PlanificadorRuta(new MenorDistancia());

    @Test
    @DisplayName("Una ruta larga se divide en tramos de máximo 5 km")
    void divideEnTramos() {
        Ruta ruta = planificador.dividir("ECI", "UNAL", 12000);

        assertEquals(3, ruta.tramos().size());
        assertEquals(12000, ruta.distanciaMetros());
        assertTrue(ruta.tramos().stream().allMatch(t -> t.distanciaMetros() <= 5000));
        assertEquals("UNAL", ruta.tramos().get(2).destino());
    }

    @Test
    @DisplayName("Una ruta compuesta suma sus partes y aplana los tramos")
    void compositeSumaPartes() {
        RutaCompuesta anidada = new RutaCompuesta()
                .agregar(new Tramo("A", "B", 1000))
                .agregar(new RutaCompuesta().agregar(new Tramo("B", "C", 2000)));

        assertEquals(3000, anidada.distanciaMetros());
        assertEquals(2, anidada.tramos().size());
    }

    @Test
    @DisplayName("La estrategia elige la ruta de menor distancia")
    void estrategiasDeRuta() {
        Ruta corta = planificador.dividir("ECI", "UNAL", 4000);
        Ruta larga = new RutaCompuesta()
                .agregar(new Tramo("ECI", "X", 1000))
                .agregar(new Tramo("X", "UNAL", 1000));
        List<Ruta> candidatas = List.of(corta, larga);

        assertEquals(larga, planificador.mejor(candidatas).orElseThrow());
    }

    @Test
    @DisplayName("El seguimiento avisa a los observadores cada etapa completada")
    void observadorDeEtapas() {
        SeguimientoRuta seguimiento = new SeguimientoRuta(planificador.dividir("ECI", "UNAL", 7000));
        List<String> avisos = new ArrayList<>();
        seguimiento.suscribir((tramo, numero, total) -> avisos.add(numero + "/" + total));

        assertTrue(seguimiento.completarSiguiente());
        assertTrue(seguimiento.completarSiguiente());
        assertFalse(seguimiento.completarSiguiente());

        assertEquals(List.of("1/2", "2/2"), avisos);
    }

    @Test
    @DisplayName("La fábrica correcta crea el drone según peso y distancia del tramo")
    void fabricaPorTramo() {
        Tramo largo = new Tramo("ECI", "UNAL", 4000);
        Tramo corto = new Tramo("ECI", "UNAL", 800);

        assertEquals(TipoDrone.CARGO, planificador.droneParaEtapa(corto, 900, "D-90").tipo());
        assertEquals(TipoDrone.EXPRESS, planificador.droneParaEtapa(largo, 200, "D-91").tipo());
        assertEquals(TipoDrone.MINI, planificador.droneParaEtapa(corto, 200, "D-92").tipo());
    }
}
