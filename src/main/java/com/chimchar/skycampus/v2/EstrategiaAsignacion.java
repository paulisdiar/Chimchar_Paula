package com.chimchar.skycampus.v2;

import java.util.List;
import java.util.Optional;

public interface EstrategiaAsignacion {

    Optional<Drone> seleccionar(List<Drone> flota, Mision mision);
}
