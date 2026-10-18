package com.chimchar.skycampus.v3.dominio.ruta;

import java.util.ArrayList;
import java.util.List;

public class SeguimientoRuta {

    private final List<Tramo> tramos;
    private final List<ObservadorEtapa> observadores = new ArrayList<>();
    private int completadas;

    public SeguimientoRuta(Ruta ruta) {
        this.tramos = ruta.tramos();
    }

    public void suscribir(ObservadorEtapa observador) {
        observadores.add(observador);
    }

    public boolean completarSiguiente() {
        if (completadas >= tramos.size()) {
            return false;
        }
        Tramo tramo = tramos.get(completadas);
        completadas++;
        int numero = completadas;
        observadores.forEach(o -> o.onEtapaCompletada(tramo, numero, tramos.size()));
        return true;
    }
}
