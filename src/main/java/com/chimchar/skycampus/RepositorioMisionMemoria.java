package com.chimchar.skycampus;

import java.util.ArrayList;
import java.util.List;

public class RepositorioMisionMemoria implements RepositorioMision {

    private final List<Mision> misiones = new ArrayList<>();

    @Override
    public void guardar(Mision mision) {
        misiones.removeIf(m -> m.id().equals(mision.id()));
        misiones.add(mision);
    }

    @Override
    public List<Mision> listar() {
        return List.copyOf(misiones);
    }
}
