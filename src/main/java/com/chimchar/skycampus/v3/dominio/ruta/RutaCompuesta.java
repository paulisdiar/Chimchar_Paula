package com.chimchar.skycampus.v3.dominio.ruta;

import java.util.ArrayList;
import java.util.List;

public class RutaCompuesta implements Ruta {

    private final List<Ruta> partes = new ArrayList<>();

    public RutaCompuesta agregar(Ruta parte) {
        partes.add(parte);
        return this;
    }

    @Override
    public int distanciaMetros() {
        return partes.stream().mapToInt(Ruta::distanciaMetros).sum();
    }

    @Override
    public List<Tramo> tramos() {
        return partes.stream().flatMap(p -> p.tramos().stream()).toList();
    }
}
