package com.chimchar.skycampus.v2;

public interface ObservadorDrone {

    void onEstadoCambiado(Drone drone, EstadoDrone nuevo);
}
