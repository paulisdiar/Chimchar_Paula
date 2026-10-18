package com.chimchar.skycampus.v3.dominio;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SeleccionMayorBateria implements SeleccionDrone {

    @Override
    public Optional<Drone> seleccionar(List<Drone> aptos, Mision mision) {
        return aptos.stream().max(Comparator.comparingInt(Drone::bateria));
    }
}
