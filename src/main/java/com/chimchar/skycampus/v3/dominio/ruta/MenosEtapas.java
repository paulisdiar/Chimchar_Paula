package com.chimchar.skycampus.v3.dominio.ruta;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class MenosEtapas implements OptimizadorRuta {

    @Override
    public Optional<Ruta> elegir(List<Ruta> candidatas) {
        return candidatas.stream()
                .min(Comparator.comparingInt((Ruta r) -> r.tramos().size())
                        .thenComparingInt(Ruta::distanciaMetros));
    }
}
