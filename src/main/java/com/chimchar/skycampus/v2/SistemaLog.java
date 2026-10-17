package com.chimchar.skycampus.v2;

import java.util.ArrayList;
import java.util.List;

public class SistemaLog implements ObservadorDrone {

    private final List<String> registros = new ArrayList<>();

    @Override
    public void onEstadoCambiado(Drone drone, EstadoDrone nuevo) {
        registros.add("Drone " + drone.id() + " cambió a " + nuevo);
    }

    public List<String> registros() {
        return List.copyOf(registros);
    }
}
