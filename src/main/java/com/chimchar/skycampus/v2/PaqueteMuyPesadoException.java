package com.chimchar.skycampus.v2;

public class PaqueteMuyPesadoException extends RuntimeException {

    private final int pesoMaximoGramos;

    public PaqueteMuyPesadoException(int pesoMaximoGramos) {
        super("El paquete supera la capacidad máxima disponible: "
                + pesoMaximoGramos + " g.");
        this.pesoMaximoGramos = pesoMaximoGramos;
    }

    public int pesoMaximoGramos() {
        return pesoMaximoGramos;
    }
}
