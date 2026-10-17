# Reto 03 - Patrones de diseño

## Problema 1: Mision con campos obligatorios y opcionales

**Patrón:** Builder.

**Por qué:** la misión tiene tres datos obligatorios (drone, origen, destino) y varios opcionales (prioridad, notas, hora máxima), y un constructor por cada combinación no se sostiene.

**Implementación:** `MisionBuilder` arma la misión paso a paso, asigna valores por defecto a los opcionales (prioridad 3, notas vacías) y en `build()` lanza `IllegalStateException` si falta algo obligatorio.

```java
Mision m = new MisionBuilder()
        .id("M-1")
        .drone(d03)
        .origen("Bloque C")
        .destino("Biblioteca")
        .tipoCarga(TipoCarga.SOBRE)
        .notas("Urgente: examen mañana")
        .build();
```

## Problema 2: validaciones en orden antes de lanzar el drone

**Patrón:** Chain of Responsibility.

**Por qué:** cada validación decide si la misión pasa o se rechaza, y si pasa la entrega al siguiente eslabón sin que el resto del código conozca el orden.

**Implementación:** `Validador` es la clase base que guarda el siguiente eslabón. Los tres validadores son:

| Clase | Rechaza cuando |
|---|---|
| `ValidadorBateria` | la batería del drone es menor a 30% |
| `ValidadorDestino` | el destino no está en la lista de destinos |
| `ValidadorCarga` | el peso del tipo de carga supera la capacidad del drone |

```java
Validador bateria = new ValidadorBateria();
bateria.establecerSiguiente(new ValidadorDestino(ValidadorMision.DESTINOS))
        .establecerSiguiente(new ValidadorCarga(capacidades, pesos));
boolean pasa = bateria.validar(mision);
```

La cadena ya está conectada en `SkyCampus.asignarDrone`.

## Problema 3: algoritmo de asignación intercambiable

**Patrón:** Strategy.

**Por qué:** hoy se asigna el drone con más batería, pero mañana puede ser el más cercano o el más rápido, y el algoritmo debe cambiarse sin tocar el resto del código.

**Implementación:** `EstrategiaAsignacion` define `elegir(List<Drone>)` y `AsignacionMayorBateria` es la estrategia del MVP. `AsignadorMision` recibe la estrategia por constructor.

```java
EstrategiaAsignacion estrategia = new AsignacionMayorBateria();
Optional<Drone> elegido = estrategia.elegir(flota);
```
