package com.chimchar.skycampus.v3.dominio.ruta;

import java.util.List;

public record Tramo(String origen, String destino, int distanciaMetros) implements Ruta {

    @Override
    public List<Tramo> tramos() {
        return List.of(this);
    }
}
