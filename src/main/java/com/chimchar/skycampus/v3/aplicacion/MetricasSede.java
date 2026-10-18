package com.chimchar.skycampus.v3.aplicacion;

import java.util.Optional;

public record MetricasSede(
        long total,
        double tasaExito,
        double tiempoPromedioMinutos,
        Optional<String> droneMasUtilizado,
        double porcentajeUrgentes
) {
}
