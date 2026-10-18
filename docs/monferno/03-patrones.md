# Monferno 03 - Strategy y Observer

## Strategy: asignación intercambiable

**Problema:** hoy se asigna el drone con más batería, mañana puede ser el más cercano o el de menor uso, y el algoritmo debe cambiarse sin tocar `GestorMisiones`.

**Solución:** `EstrategiaAsignacion` define `seleccionar(List<Drone>, Mision)` y tiene tres implementaciones:

| Clase | Criterio |
|---|---|
| `AsignacionMayorBateria` | el drone apto con más batería |
| `AsignacionMenorUso` | el drone apto con menos misiones acumuladas, y a igualdad el de más batería |
| `AsignacionTipoCompatible` | para misiones URGENTES el EXPRESS apto con más batería; si no hay, el drone apto de menor capacidad que cabe el paquete |

Las tres filtran con `CriterioApto`: drone disponible, batería de 30% o más, capacidad suficiente y que un CARGO no lleve paquetes menores a 100 g.

## Observer: notificación de cambios de estado

**Problema:** cada vez que un drone cambia de estado deben enterarse el panel del operador, el log y, si entra en FALLO, el técnico, y agregar un observador no debe obligar a tocar `Drone`.

**Solución:** `GestorFlota` guarda una lista de `ObservadorDrone` y en `cambiarEstado` los avisa a todos. Los tres suscriptores son:

| Clase | Qué hace |
|---|---|
| `PanelOperador` | guarda las notificaciones y no acumula más de 7 sin confirmar |
| `SistemaLog` | registra cada cambio de estado |
| `AlertaTecnico` | solo reacciona cuando el nuevo estado es FALLO |

## Prueba del cuarto observador

`ObservadoresTest.cuartoObservador` suscribe un observador nuevo como lambda y recibe la notificación sin que `GestorFlota` cambie ni una línea.
