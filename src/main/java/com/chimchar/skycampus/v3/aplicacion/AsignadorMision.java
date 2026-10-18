package com.chimchar.skycampus.v3.aplicacion;

import com.chimchar.skycampus.v2.EstadoDrone;
import com.chimchar.skycampus.v3.dominio.Drone;
import com.chimchar.skycampus.v3.dominio.Mision;
import com.chimchar.skycampus.v3.dominio.MotivoRechazo;
import com.chimchar.skycampus.v3.dominio.RegistroSedes;
import com.chimchar.skycampus.v3.dominio.RepositorioDrones;
import com.chimchar.skycampus.v3.dominio.RepositorioMisiones;
import com.chimchar.skycampus.v3.dominio.ResultadoAsignacion;
import com.chimchar.skycampus.v3.dominio.SeleccionDrone;
import com.chimchar.skycampus.v3.dominio.ServicioAerocivil;
import com.chimchar.skycampus.v3.dominio.ServicioClima;

import java.util.List;

public class AsignadorMision {

    private final RegistroSedes sedes;
    private final ServicioClima clima;
    private final ServicioAerocivil aerocivil;
    private final RepositorioDrones drones;
    private final RepositorioMisiones misiones;
    private final SeleccionDrone seleccion;

    public AsignadorMision(
            RegistroSedes sedes,
            ServicioClima clima,
            ServicioAerocivil aerocivil,
            RepositorioDrones drones,
            RepositorioMisiones misiones,
            SeleccionDrone seleccion
    ) {
        this.sedes = sedes;
        this.clima = clima;
        this.aerocivil = aerocivil;
        this.drones = drones;
        this.misiones = misiones;
        this.seleccion = seleccion;
    }

    public ResultadoAsignacion asignar(Mision mision) {
        if (!sedes.estaActiva(mision.sedeOrigen())) {
            return ResultadoAsignacion.rechazado(MotivoRechazo.SEDE_INACTIVA);
        }
        if (!clima.condicionesAptas(mision.sedeOrigen())) {
            return ResultadoAsignacion.rechazado(MotivoRechazo.CLIMA_ADVERSO);
        }

        List<Drone> aptos = drones.porSede(mision.sedeOrigen()).stream()
                .filter(Drone::estaApto)
                .toList();
        if (aptos.isEmpty()) {
            return ResultadoAsignacion.rechazado(MotivoRechazo.SIN_DRONES);
        }

        List<Drone> conCapacidad = aptos.stream()
                .filter(d -> d.capacidadGramos() >= mision.pesoGramos())
                .toList();
        if (conCapacidad.isEmpty()) {
            return ResultadoAsignacion.rechazado(MotivoRechazo.PAQUETE_PESADO);
        }
        if (!aerocivil.autoriza(mision)) {
            return ResultadoAsignacion.rechazado(MotivoRechazo.AEROCIVIL_RECHAZA);
        }

        return seleccion.seleccionar(conCapacidad, mision)
                .map(d -> confirmar(d, mision))
                .orElse(ResultadoAsignacion.rechazado(MotivoRechazo.SIN_DRONES));
    }

    private ResultadoAsignacion confirmar(Drone elegido, Mision mision) {
        Drone enVuelo = elegido.conEstado(EstadoDrone.EN_VUELO);
        drones.guardar(enVuelo);
        misiones.guardar(mision.asignada(enVuelo.id()));
        return ResultadoAsignacion.asignado(enVuelo);
    }
}
