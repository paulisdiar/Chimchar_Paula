package com.chimchar.skycampus.v3.infraestructura;

import com.chimchar.skycampus.EstadoMision;
import com.chimchar.skycampus.v3.aplicacion.AsignadorMision;
import com.chimchar.skycampus.v3.dominio.Mision;
import com.chimchar.skycampus.v3.dominio.MotivoRechazo;
import com.chimchar.skycampus.v3.dominio.ResultadoAsignacion;

import java.util.concurrent.atomic.AtomicInteger;

public class ApiMisiones {

    private final AsignadorMision asignador;
    private final AtomicInteger consecutivo = new AtomicInteger();

    public ApiMisiones(AsignadorMision asignador) {
        this.asignador = asignador;
    }

    public RespuestaMision crear(SolicitudMision solicitud) {
        Mision mision = new Mision(
                "M-" + consecutivo.incrementAndGet(),
                solicitud.sedeOrigen(),
                solicitud.sedeDestino(),
                solicitud.pesoGramos(),
                solicitud.prioridad(),
                EstadoMision.PENDIENTE,
                null,
                0
        );
        ResultadoAsignacion resultado = asignador.asignar(mision);
        return resultado.drone()
                .map(d -> new RespuestaMision(201, d.id(), EstadoMision.EN_VUELO.name(), "Misión asignada"))
                .orElseGet(() -> rechazo(resultado.motivo().orElseThrow()));
    }

    private RespuestaMision rechazo(MotivoRechazo motivo) {
        int codigo = motivo == MotivoRechazo.PAQUETE_PESADO ? 422 : 409;
        return new RespuestaMision(codigo, null, EstadoMision.PENDIENTE.name(), motivo.name());
    }
}
