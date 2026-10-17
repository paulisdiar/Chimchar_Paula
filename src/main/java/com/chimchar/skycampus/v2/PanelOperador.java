package com.chimchar.skycampus.v2;

import java.util.ArrayList;
import java.util.List;

public class PanelOperador implements ObservadorDrone {

    public static final int MAXIMO_SIN_CONFIRMAR = 7;

    private final List<String> notificaciones = new ArrayList<>();

    @Override
    public void onEstadoCambiado(Drone drone, EstadoDrone nuevo) {
        if (notificaciones.size() >= MAXIMO_SIN_CONFIRMAR) {
            notificaciones.remove(0);
        }

        notificaciones.add(drone.id() + " -> " + nuevo);
    }

    public List<String> notificaciones() {
        return List.copyOf(notificaciones);
    }

    public void confirmarTodas() {
        notificaciones.clear();
    }
}
