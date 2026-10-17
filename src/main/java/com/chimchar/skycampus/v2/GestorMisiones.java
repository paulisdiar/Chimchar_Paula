package com.chimchar.skycampus.v2;

import com.chimchar.skycampus.EstadoMision;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestorMisiones {

    private final AsignadorMision asignador;
    private final List<Mision> misiones = new ArrayList<>();

    public GestorMisiones(AsignadorMision asignador) {
        this.asignador = asignador;
    }

    public Optional<Mision> procesar(Mision mision, List<Drone> flota) {
        Optional<Drone> drone = asignador.asignar(flota, mision);

        if (drone.isEmpty()) {
            misiones.add(mision);
            return Optional.empty();
        }

        Mision asignada = new Mision(
                mision.id(),
                drone.get(),
                mision.destino(),
                mision.pesoPaqueteGramos(),
                mision.prioridad(),
                EstadoMision.EN_VUELO,
                mision.creadaEn()
        );

        misiones.add(asignada);

        return Optional.of(asignada);
    }

    public List<Mision> misiones() {
        return List.copyOf(misiones);
    }
}
