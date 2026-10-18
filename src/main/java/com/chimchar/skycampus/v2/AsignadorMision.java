package com.chimchar.skycampus.v2;

import java.util.List;
import java.util.Optional;

public class AsignadorMision {

    private final ApiMeteorologica clima;
    private final EstrategiaAsignacion estrategia;
    private final ObservadorDrone notificador;

    public AsignadorMision(
            ApiMeteorologica clima,
            EstrategiaAsignacion estrategia,
            ObservadorDrone notificador
    ) {
        this.clima = clima;
        this.estrategia = estrategia;
        this.notificador = notificador;
    }

    public Optional<Drone> asignar(List<Drone> flota, Mision mision) {
        if (!clima.esApto()) {
            return Optional.empty();
        }

        flota.stream()
                .filter(Drone::disponible)
                .mapToInt(Drone::capacidadGramos)
                .max()
                .ifPresent(maximo -> {
                    if (mision.pesoPaqueteGramos() > maximo) {
                        throw new PaqueteMuyPesadoException(maximo);
                    }
                });

        Optional<Drone> elegido = estrategia.seleccionar(flota, mision);

        elegido.ifPresent(d -> notificador.onEstadoCambiado(d, EstadoDrone.EN_VUELO));

        return elegido;
    }
}
