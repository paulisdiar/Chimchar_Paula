package com.chimchar.skycampus.v3.dominio.ruta;

import java.util.List;
import java.util.Optional;

public interface OptimizadorRuta {

    Optional<Ruta> elegir(List<Ruta> candidatas);
}
