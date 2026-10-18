package com.chimchar.skycampus.v3.infraestructura;

import com.chimchar.skycampus.v3.dominio.Mision;
import com.chimchar.skycampus.v3.dominio.RepositorioMisiones;

import java.util.ArrayList;
import java.util.List;

public class RepositorioMisionesMemoria implements RepositorioMisiones {

    private final List<Mision> almacen = new ArrayList<>();

    @Override
    public void guardar(Mision mision) {
        almacen.removeIf(m -> m.id().equals(mision.id()));
        almacen.add(mision);
    }

    @Override
    public List<Mision> todas() {
        return List.copyOf(almacen);
    }
}
