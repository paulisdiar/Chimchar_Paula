package com.chimchar.skycampus.v3.dominio.ruta;

import java.util.List;

public interface Ruta {

    int distanciaMetros();

    List<Tramo> tramos();
}
