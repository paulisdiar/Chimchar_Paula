package com.chimchar.skycampus.v3.aplicacion;

import com.chimchar.skycampus.v3.dominio.RegistroSedes;

import java.util.HashSet;
import java.util.Set;

public class GestorZonas implements RegistroSedes {

    private final Set<String> activas = new HashSet<>();

    public GestorZonas(Set<String> sedesIniciales) {
        activas.addAll(sedesIniciales);
    }

    public void activar(String sede) {
        activas.add(sede);
    }

    public void desactivar(String sede) {
        activas.remove(sede);
    }

    @Override
    public boolean estaActiva(String sede) {
        return activas.contains(sede);
    }
}
