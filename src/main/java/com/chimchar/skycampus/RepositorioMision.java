package com.chimchar.skycampus;

import java.util.List;

public interface RepositorioMision {

    void guardar(Mision mision);

    List<Mision> listar();
}
