package com.chimchar.skycampus.v3.aplicacion;

import com.chimchar.skycampus.v2.EstadoDrone;
import com.chimchar.skycampus.v2.TipoDrone;
import com.chimchar.skycampus.v3.dominio.Drone;
import com.chimchar.skycampus.v3.dominio.RepositorioDrones;

import java.util.Map;
import java.util.stream.Collectors;

public class MonitorFlota {

    private final RepositorioDrones drones;

    public MonitorFlota(RepositorioDrones drones) {
        this.drones = drones;
    }

    public Map<String, Map<TipoDrone, Long>> dronesPorSedeYTipo() {
        return drones.todos().stream()
                .collect(Collectors.groupingBy(
                        Drone::sede,
                        Collectors.groupingBy(Drone::tipo, Collectors.counting())));
    }

    public Map<EstadoDrone, Long> dronesPorEstado(String sede) {
        return drones.porSede(sede).stream()
                .collect(Collectors.groupingBy(Drone::estado, Collectors.counting()));
    }
}
