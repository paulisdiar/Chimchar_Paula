package com.chimchar.skycampus;

public class Mision {

    private String id;
    private Drone drone;
    private String origen;
    private String destino;
    private TipoCarga tipoCarga;
    private EstadoMision estado;

    public Mision(String id, String origen, String destino,
                  TipoCarga tipoCarga) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.tipoCarga = tipoCarga;
        this.estado = EstadoMision.PENDIENTE;
        this.drone = null;
    }

    public String getId() {
        return id;
    }

    public Drone getDrone() {
        return drone;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public TipoCarga getTipoCarga() {
        return tipoCarga;
    }

    public EstadoMision getEstado() {
        return estado;
    }

    public void asignarDrone(Drone drone) {
        this.drone = drone;
    }

    public void setEstado(EstadoMision estado) {
        this.estado = estado;
    }
}