package com.chimchar.skycampus.v3.dominio.ruta;

import com.chimchar.skycampus.v2.EstadoDrone;
import com.chimchar.skycampus.v2.TipoDrone;
import com.chimchar.skycampus.v3.dominio.Drone;

public class FabricaExpress extends FabricaDrone {

    @Override
    protected Drone crear(String id, String sede) {
        return new Drone(id, sede, TipoDrone.EXPRESS, 100, EstadoDrone.DISPONIBLE, 800);
    }
}
