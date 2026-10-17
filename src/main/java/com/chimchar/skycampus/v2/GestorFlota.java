package com.chimchar.skycampus.v2;

import java.util.ArrayList;
import java.util.List;

public class GestorFlota {

    private final List<ObservadorDrone> observadores = new ArrayList<>();

    public void suscribir(ObservadorDrone observador) {
        observadores.add(observador);
    }

    public Drone cambiarEstado(Drone drone, EstadoDrone nuevo) {
        Drone actualizado = new Drone(
                drone.id(),
                drone.tipo(),
                drone.bateria(),
                nuevo == EstadoDrone.DISPONIBLE,
                nuevo,
                drone.capacidadGramos()
        );

        observadores.forEach(o -> o.onEstadoCambiado(actualizado, nuevo));

        return actualizado;
    }
}
