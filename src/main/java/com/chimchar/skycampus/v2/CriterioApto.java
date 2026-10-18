package com.chimchar.skycampus.v2;

public final class CriterioApto {

    public static final int BATERIA_MINIMA = 30;
    public static final int PESO_MINIMO_CARGO = 100;

    private CriterioApto() {
    }

    public static boolean esApto(Drone drone, Mision mision) {
        return drone.disponible()
                && drone.estado() == EstadoDrone.DISPONIBLE
                && drone.bateria() >= BATERIA_MINIMA
                && drone.capacidadGramos() >= mision.pesoPaqueteGramos()
                && !(drone.tipo() == TipoDrone.CARGO
                && mision.pesoPaqueteGramos() < PESO_MINIMO_CARGO);
    }
}
