package com.chimchar.skycampus.v3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArquitecturaTest {

    private static final Path BASE = Path.of("src/main/java/com/chimchar/skycampus/v3");

    private List<String> importsDe(String capa) throws IOException {
        try (Stream<Path> archivos = Files.walk(BASE.resolve(capa))) {
            return archivos.filter(p -> p.toString().endsWith(".java"))
                    .flatMap(p -> lineas(p).stream())
                    .filter(l -> l.startsWith("import "))
                    .toList();
        }
    }

    private List<String> lineas(Path archivo) {
        try {
            return Files.readAllLines(archivo);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    @DisplayName("El dominio solo importa java y el propio proyecto")
    void dominioSinLibreriasExternas() throws IOException {
        List<String> prohibidos = importsDe("dominio").stream()
                .filter(i -> !i.startsWith("import java.") && !i.startsWith("import com.chimchar."))
                .toList();

        assertEquals(List.of(), prohibidos);
    }

    @Test
    @DisplayName("El dominio no depende de aplicación ni de infraestructura")
    void dominioNoDependeDeCapasExternas() throws IOException {
        assertTrue(importsDe("dominio").stream()
                .noneMatch(i -> i.contains("v3.aplicacion") || i.contains("v3.infraestructura")));
    }

    @Test
    @DisplayName("La aplicación no depende de infraestructura")
    void aplicacionNoDependeDeInfraestructura() throws IOException {
        assertTrue(importsDe("aplicacion").stream().noneMatch(i -> i.contains("v3.infraestructura")));
    }
}
