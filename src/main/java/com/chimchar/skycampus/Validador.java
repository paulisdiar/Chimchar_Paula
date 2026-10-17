package com.chimchar.skycampus;

public abstract class Validador {

    private Validador siguiente;

    public Validador establecerSiguiente(
            Validador siguiente
    ) {
        this.siguiente = siguiente;
        return siguiente;
    }

    public final boolean validar(Mision mision) {
        if (!validarRegla(mision)) {
            return false;
        }

        return siguiente == null || siguiente.validar(mision);
    }

    protected abstract boolean validarRegla(Mision mision);
}