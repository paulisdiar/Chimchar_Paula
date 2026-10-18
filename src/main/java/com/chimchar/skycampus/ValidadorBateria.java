package com.chimchar.skycampus;

public class ValidadorBateria extends Validador {

    private final ValidadorMision validador = new ValidadorMision();

    @Override
    protected boolean validarRegla(Mision mision) {
        if (mision.drone() == null) {
            System.out.println("Rechazada: la misión no tiene drone asignado.");
            return false;
        }

        if (!validador.tieneBateriaSuficiente(mision.drone())) {
            System.out.println(
                    "Rechazada: el drone " + mision.drone().id()
                            + " tiene batería insuficiente ("
                            + mision.drone().bateria()
                            + "%). Mínimo requerido: "
                            + ValidadorMision.BATERIA_MINIMA + "%."
            );
            return false;
        }

        System.out.println("Batería validada.");
        return true;
    }
}
