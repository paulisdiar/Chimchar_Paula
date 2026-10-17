package com.chimchar.skycampus;

public class AlertaConsola implements AlertaOperador {

    @Override
    public void enviar(String operador, String mensaje) {
        System.out.println("[ALERTA] " + operador + ": " + mensaje);
    }
}
