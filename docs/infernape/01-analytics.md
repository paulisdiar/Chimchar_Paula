# Infernape 01 - Analytics de la red en una sola pasada

Clase: `AnalisisRed` (`com.chimchar.skycampus.v3.aplicacion`).

Para cada sede se calculan en una sola pasada de Stream: tasa de éxito (entregadas sobre total), tiempo promedio de entrega, drone más utilizado y porcentaje de misiones urgentes. La pasada usa `groupingBy(sede)` con un `Collector.of` propio, así cada misión se visita una sola vez y no se recorre la lista una vez por métrica.

| Método | Qué usa de Streams |
|---|---|
| `metricasPorSede` | `groupingBy` + `Collector.of` con acumulador propio |
| `metricasDe` | `Optional`: vacío si la sede no tiene misiones |
| `sedeMasActiva` | `groupingBy` + `counting` + `max` con `Comparator` compuesto, devuelve `Optional` |
| `ranking` | `Comparator` compuesto: tasa de éxito descendente, misiones descendente y nombre |
| `MonitorFlota.dronesPorSedeYTipo` | `groupingBy` anidado sede, tipo y `counting` |

## Pruebas

`AnalisisRedTest` tiene una prueba parametrizada con los 4 escenarios pedidos:

| Escenario | Resultado esperado |
|---|---|
| Sede vacía | `Optional.empty()` |
| Sede con una misión | tasa de éxito 100 % |
| Empate entre sedes | ambas con 100 %, el ranking desempata por nombre |
| Red completa | ECI con 2 de 3 entregadas, 66.7 % |

Otras dos pruebas comprueban el tiempo promedio, el drone más usado, el porcentaje de urgentes y el ranking.
