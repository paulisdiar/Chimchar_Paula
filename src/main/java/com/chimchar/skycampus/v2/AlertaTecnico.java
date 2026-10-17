package com.chimchar.skycampus.v2;

import java.util.ArrayList;
import java.util.List;

public class AlertaTecnico implements ObservadorDrone {

    private final List<String> alertas = new ArrayList<>();

    @Override
    public void onEstadoCambiado(Drone drone, EstadoDrone nuevo) {
        if (nuevo == EstadoDrone.FALLO) {
            alertas.add("Revisar drone " + drone.id() + " en FALLO");
        }
    }

    public List<String> alertas() {
        return List.copyOf(alertas);
    }
}
