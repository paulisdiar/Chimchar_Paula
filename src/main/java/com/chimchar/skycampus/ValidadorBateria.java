package com.chimchar.skycampus;

public class ValidadorBateria extends ValidadorMision {

    @Override
    protected boolean validarRegla(Mision mision) {
        if (mision.drone() == null) {
            System.out.println("Rechazada: la misión no tiene drone asignado.");
            return false;
        }

        if (mision.drone().bateria() < 30) {
            System.out.println(
                    "Rechazada: batería insuficiente ("
                            + mision.drone().bateria()
                            + "%). Mínimo requerido: 30%."
            );
            return false;
        }

        System.out.println("Batería validada.");
        return true;
    }
}