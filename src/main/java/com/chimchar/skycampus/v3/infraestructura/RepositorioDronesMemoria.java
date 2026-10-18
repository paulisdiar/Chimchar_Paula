package com.chimchar.skycampus.v3.infraestructura;

import com.chimchar.skycampus.v3.dominio.Drone;
import com.chimchar.skycampus.v3.dominio.RepositorioDrones;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RepositorioDronesMemoria implements RepositorioDrones {

    private final Map<String, Drone> almacen = new LinkedHashMap<>();

    @Override
    public List<Drone> porSede(String sede) {
        return almacen.values().stream().filter(d -> d.sede().equals(sede)).toList();
    }

    @Override
    public List<Drone> todos() {
        return List.copyOf(almacen.values());
    }

    @Override
    public void guardar(Drone drone) {
        almacen.put(drone.id(), drone);
    }
}
