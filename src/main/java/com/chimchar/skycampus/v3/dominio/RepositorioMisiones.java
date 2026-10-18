package com.chimchar.skycampus.v3.dominio;

import java.util.List;

public interface RepositorioMisiones {

    void guardar(Mision mision);

    List<Mision> todas();
}
