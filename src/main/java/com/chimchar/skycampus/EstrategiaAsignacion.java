package com.chimchar.skycampus;

import java.util.List;
import java.util.Optional;

public interface EstrategiaAsignacion {

    Optional<Drone> elegir(List<Drone> flota);
}
