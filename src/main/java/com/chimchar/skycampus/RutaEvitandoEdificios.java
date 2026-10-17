package com.chimchar.skycampus;

public class RutaEvitandoEdificios implements EstrategiaRuta {

    @Override
    public String calcular(String origen, String destino) {
        return origen + " -> Corredor aéreo central -> " + destino;
    }
}
