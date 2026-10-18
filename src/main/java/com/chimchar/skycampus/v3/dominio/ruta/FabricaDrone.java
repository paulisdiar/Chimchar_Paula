package com.chimchar.skycampus.v3.dominio.ruta;

import com.chimchar.skycampus.v3.dominio.Drone;

public abstract class FabricaDrone {

    public Drone preparar(String id, String sede) {
        return crear(id, sede);
    }

    protected abstract Drone crear(String id, String sede);
}
