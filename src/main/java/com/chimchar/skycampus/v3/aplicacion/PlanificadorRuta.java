package com.chimchar.skycampus.v3.aplicacion;

import com.chimchar.skycampus.v2.TipoDrone;
import com.chimchar.skycampus.v3.dominio.Drone;
import com.chimchar.skycampus.v3.dominio.ruta.FabricaCargo;
import com.chimchar.skycampus.v3.dominio.ruta.FabricaDrone;
import com.chimchar.skycampus.v3.dominio.ruta.FabricaExpress;
import com.chimchar.skycampus.v3.dominio.ruta.FabricaMini;
import com.chimchar.skycampus.v3.dominio.ruta.OptimizadorRuta;
import com.chimchar.skycampus.v3.dominio.ruta.Ruta;
import com.chimchar.skycampus.v3.dominio.ruta.RutaCompuesta;
import com.chimchar.skycampus.v3.dominio.ruta.Tramo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlanificadorRuta {

    public static final int ALCANCE_CON_CARGA_METROS = 5000;
    public static final int PESO_CARGO_GRAMOS = 500;
    public static final int DISTANCIA_EXPRESS_METROS = 3000;

    private final OptimizadorRuta optimizador;
    private final Map<TipoDrone, FabricaDrone> fabricas = Map.of(
            TipoDrone.MINI, new FabricaMini(),
            TipoDrone.CARGO, new FabricaCargo(),
            TipoDrone.EXPRESS, new FabricaExpress()
    );

    public PlanificadorRuta(OptimizadorRuta optimizador) {
        this.optimizador = optimizador;
    }

    public Ruta dividir(String origen, String destino, int distanciaMetros) {
        if (distanciaMetros <= 0) {
            throw new IllegalArgumentException("La distancia debe ser mayor que 0");
        }
        RutaCompuesta ruta = new RutaCompuesta();
        int restante = distanciaMetros;
        int numero = 1;
        String desde = origen;
        while (restante > 0) {
            int tramo = Math.min(restante, ALCANCE_CON_CARGA_METROS);
            restante -= tramo;
            String hasta = restante == 0 ? destino : "estacion-" + numero;
            ruta.agregar(new Tramo(desde, hasta, tramo));
            desde = hasta;
            numero++;
        }
        return ruta;
    }

    public Optional<Ruta> mejor(List<Ruta> candidatas) {
        return optimizador.elegir(candidatas);
    }

    public Drone droneParaEtapa(Tramo tramo, int pesoGramos, String idDrone) {
        TipoDrone tipo = tipoPara(tramo, pesoGramos);
        return fabricas.get(tipo).preparar(idDrone, tramo.origen());
    }

    private TipoDrone tipoPara(Tramo tramo, int pesoGramos) {
        if (pesoGramos > PESO_CARGO_GRAMOS) {
            return TipoDrone.CARGO;
        }
        if (tramo.distanciaMetros() > DISTANCIA_EXPRESS_METROS) {
            return TipoDrone.EXPRESS;
        }
        return TipoDrone.MINI;
    }
}
