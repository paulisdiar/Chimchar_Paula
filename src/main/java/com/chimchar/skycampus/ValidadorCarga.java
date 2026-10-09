package com.chimchar.skycampus;

import java.util.Map;

public class ValidadorCarga extends ValidadorMision {

    private final Map<String, Double> capacidadesPorDrone;
    private final Map<TipoCarga, Double> pesosPorCarga;

    public ValidadorCarga(
            Map<String, Double> capacidadesPorDrone,
            Map<TipoCarga, Double> pesosPorCarga
    ) {
        this.capacidadesPorDrone = Map.copyOf(capacidadesPorDrone);
        this.pesosPorCarga = Map.copyOf(pesosPorCarga);
    }

    @Override
    protected boolean validarRegla(Mision mision) {
        if (mision.drone() == null || mision.tipoCarga() == null) {
            System.out.println(
                    "Rechazada: falta el drone o el tipo de carga."
            );
            return false;
        }

        String idDrone = mision.drone().id();
        Double capacidad = capacidadesPorDrone.get(idDrone);
        Double peso = pesosPorCarga.get(mision.tipoCarga());

        if (capacidad == null || peso == null) {
            System.out.println(
                    "Rechazada: falta la capacidad del drone "
                            + "o el peso del tipo de carga."
            );
            return false;
        }

        if (peso > capacidad) {
            System.out.println(
                    "Rechazada: la carga supera la capacidad del drone."
            );
            return false;
        }

        System.out.println("Carga validada.");
        return true;
    }
}