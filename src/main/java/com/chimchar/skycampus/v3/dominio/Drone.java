package com.chimchar.skycampus.v3.dominio;

import com.chimchar.skycampus.v2.EstadoDrone;
import com.chimchar.skycampus.v2.TipoDrone;

public record Drone(
        String id,
        String sede,
        TipoDrone tipo,
        int bateria,
        EstadoDrone estado,
        int capacidadGramos
) {

    public static final int BATERIA_MINIMA = 30;

    public boolean estaApto() {
        return estado == EstadoDrone.DISPONIBLE && bateria >= BATERIA_MINIMA;
    }

    public Drone conEstado(EstadoDrone nuevo) {
        return new Drone(id, sede, tipo, bateria, nuevo, capacidadGramos);
    }
}
