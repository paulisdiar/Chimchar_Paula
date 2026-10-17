package com.chimchar.skycampus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RutasYReporteTest {

    @Test
    @DisplayName("La ruta directa une origen y destino")
    void rutaDirecta() {
        EstrategiaRuta ruta = new RutaDirecta();

        String resultado = ruta.calcular("Bloque A", "Biblioteca");

        assertEquals("Bloque A -> Biblioteca", resultado);
    }

    @Test
    @DisplayName("El reporte de una lista vacía indica total cero")
    void reporteVacio() {
        String reporte = new GeneradorReporte().generar(List.of());

        assertTrue(reporte.endsWith("Total: 0"));
    }
}
