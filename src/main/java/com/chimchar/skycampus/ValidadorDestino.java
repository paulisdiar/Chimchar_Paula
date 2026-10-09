package com.chimchar.skycampus;

import java.util.List;

public class ValidadorDestino extends ValidadorMision {

    private final List<String> destinosPermitidos;

    public ValidadorDestino(List<String> destinosPermitidos) {
        this.destinosPermitidos = List.copyOf(destinosPermitidos);
    }

    @Override
    protected boolean validarRegla(Mision mision) {
        if (mision.destino() == null
                || !destinosPermitidos.contains(mision.destino())) {
            System.out.println(
                    "Rechazada: destino inválido ("
                            + mision.destino() + ")."
            );
            return false;
        }

        System.out.println("Destino validado.");
        return true;
    }
}