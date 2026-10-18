package com.chimchar.skycampus;

import java.util.List;
import java.util.stream.Collectors;

public class GeneradorReporte {

    public String generar(List<Mision> misiones) {
        String detalle = misiones.stream()
                .map(m -> m.id()
                        + " | " + m.origen() + " -> " + m.destino()
                        + " | " + m.tipoCarga()
                        + " | " + (m.drone() == null ? "Sin asignar" : m.drone().id())
                        + " | " + m.estado())
                .collect(Collectors.joining("\n"));

        return "REPORTE DE MISIONES\n" + detalle
                + "\nTotal: " + misiones.size();
    }
}
