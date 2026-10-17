package com.chimchar.skycampus;

import java.util.List;

public class AsignadorMision {

    private final EstrategiaAsignacion estrategia;
    private final RepositorioMision repositorio;
    private final AlertaOperador alerta;

    public AsignadorMision(
            EstrategiaAsignacion estrategia,
            RepositorioMision repositorio,
            AlertaOperador alerta
    ) {
        this.estrategia = estrategia;
        this.repositorio = repositorio;
        this.alerta = alerta;
    }

    public Mision asignar(Mision mision, List<Drone> flota) {
        Drone drone = estrategia.elegir(flota)
                .orElseThrow(() -> new IllegalStateException(
                        "No hay drones disponibles."
                ));

        Mision asignada = new Mision(
                mision.id(),
                drone,
                mision.origen(),
                mision.destino(),
                mision.tipoCarga(),
                mision.estado(),
                mision.prioridad(),
                mision.notas(),
                mision.horaMaximaEntrega()
        );

        repositorio.guardar(asignada);
        alerta.enviar(
                "Operador",
                "Misión " + asignada.id()
                        + " asignada al drone " + drone.id()
        );

        return asignada;
    }
}
