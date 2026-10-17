package com.chimchar.skycampus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SkyCampusTest {

    @Test
    @DisplayName("El menú recorre entregas, fallos y entradas inválidas")
    void menuCompleto() {
        String entrada = String.join("\n",
                "9",
                "abc",
                "3",
                "1",
                "2", "Bloque A", "3", "1", "D1", "S",
                "2", "", "1",
                "2", "Bloque B", "9",
                "2", "Bloque B", "1", "9",
                "2", "Bloque B", "2", "2", "D99",
                "2", "Bloque A", "4", "3", "D2", "N",
                "2", "Bloque A", "5", "1", "D3", "Z",
                "2", "Bloque A", "1", "1", "D1", "Z",
                "2", "Bloque A", "1", "1", "D2", "Z",
                "2", "Bloque A", "1", "1", "D4", "Z",
                "2", "Bloque A", "1", "1", "D5", "Z",
                "2", "Bloque A", "1", "1",
                "3",
                "4",
                ""
        );

        String salida = ejecutar(entrada);

        assertTrue(salida.contains("No hay misiones registradas."));
        assertTrue(salida.contains("Entrega confirmada."));
        assertTrue(salida.contains("Misión marcada como fallida."));
        assertTrue(salida.contains("Respuesta inválida."));
        assertTrue(salida.contains("Dron no disponible o ID inválido."));
        assertTrue(salida.contains("Origen o destino inválido."));
        assertTrue(salida.contains("Tipo de carga inválido."));
        assertTrue(salida.contains("Opción inválida."));
        assertTrue(salida.contains("No hay drones disponibles."));
        assertTrue(salida.contains("Programa finalizado."));
    }

    private String ejecutar(String entrada) {
        InputStream inOriginal = System.in;
        PrintStream outOriginal = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(
                    entrada.getBytes(StandardCharsets.UTF_8)
            ));
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));

            SkyCampus.main(new String[0]);
        } finally {
            System.setIn(inOriginal);
            System.setOut(outOriginal);
        }

        return buffer.toString(StandardCharsets.UTF_8);
    }
}
