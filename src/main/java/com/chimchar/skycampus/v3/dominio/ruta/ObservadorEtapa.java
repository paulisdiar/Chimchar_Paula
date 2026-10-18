package com.chimchar.skycampus.v3.dominio.ruta;

public interface ObservadorEtapa {

    void onEtapaCompletada(Tramo tramo, int numero, int total);
}
