package com.chimchar.skycampus;

import java.util.List;

public class ValidadorMision {

    public static final int BATERIA_MINIMA = 30;

    public static final List<String> DESTINOS = List.of(
            "Bloque A",
            "Bloque B",
            "Bloque C",
            "Bloque D",
            "Biblioteca"
    );

    public boolean tieneBateriaSuficiente(Drone drone) {
        return drone.bateria() >= BATERIA_MINIMA;
    }

    public void validarDestino(String destino) {
        if (destino == null || !DESTINOS.contains(destino)) {
            throw new DestinoInvalidoException(
                    "Destino inválido: " + destino
            );
        }
    }

    public boolean droneEstaDisponible(Drone drone) {
        return drone.disponible();
    }
}
