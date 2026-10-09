package com.chimchar.skycampus;

public class Drone {

    private String id;
    private String modelo;
    private int bateria;
    private boolean disponible;
    private String ubicacion;

    public Drone(String id, String modelo, int bateria,
                 boolean disponible, String ubicacion) {
        this.id = id;
        this.modelo = modelo;
        this.bateria = bateria;
        this.disponible = disponible;
        this.ubicacion = ubicacion;
    }

    public String getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public int getBateria() {
        return bateria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}