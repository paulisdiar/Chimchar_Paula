package com.chimchar.skycampus;

import java.time.LocalDateTime;

public class MisionBuilder {

    private String id;
    private Drone drone;
    private String origen;
    private String destino;
    private TipoCarga tipoCarga;
    private EstadoMision estado = EstadoMision.PENDIENTE;

    // Campos opcionales
    private int prioridad = 3;
    private String notas = "";
    private LocalDateTime horaMaximaEntrega;

    public MisionBuilder id(String id) {
        this.id = id;
        return this;
    }

    public MisionBuilder drone(Drone drone) {
        this.drone = drone;
        return this;
    }

    public MisionBuilder origen(String origen) {
        this.origen = origen;
        return this;
    }

    public MisionBuilder destino(String destino) {
        this.destino = destino;
        return this;
    }

    public MisionBuilder tipoCarga(TipoCarga tipoCarga) {
        this.tipoCarga = tipoCarga;
        return this;
    }

    public MisionBuilder estado(EstadoMision estado) {
        this.estado = estado;
        return this;
    }

    public MisionBuilder prioridad(int prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public MisionBuilder notas(String notas) {
        this.notas = notas;
        return this;
    }

    public MisionBuilder horaMaximaEntrega(
            LocalDateTime horaMaximaEntrega
    ) {
        this.horaMaximaEntrega = horaMaximaEntrega;
        return this;
    }

    public Mision build() {
        if (drone == null || origen == null || destino == null) {
            throw new IllegalStateException(
                    "Drone, origen y destino son obligatorios."
            );
        }

        if (id == null || id.isBlank()) {
            throw new IllegalStateException(
                    "El ID de la misión es obligatorio."
            );
        }

        if (tipoCarga == null) {
            throw new IllegalStateException(
                    "El tipo de carga es obligatorio."
            );
        }

        return new Mision(
                id,
                drone,
                origen,
                destino,
                tipoCarga,
                estado,
                prioridad,
                notas,
                horaMaximaEntrega
        );
    }
}