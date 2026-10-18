package com.chimchar.skycampus.v3.dominio;

import java.util.List;
import java.util.Optional;

public interface SeleccionDrone {

    Optional<Drone> seleccionar(List<Drone> aptos, Mision mision);
}
