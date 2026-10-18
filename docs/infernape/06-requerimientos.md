# Infernape 06 - Matriz de trazabilidad de SkyCampus Enterprise

## Requerimientos funcionales

| Código | Nombre | MoSCoW | CU | HU en Jira | Prueba que lo valida |
|---|---|---|---|---|---|
| RF-12 | Asignar misión inter-sede con el drone apto de mayor batería de la sede de origen | Must | CU-E02 | CHIM-17 | `AsignadorMisionTest.asignaMayorBateria`, `ApiMisionesIntegracionTest.climaAptoCreaMision` |
| RF-13 | Pedir autorización a Aerocivil antes de asignar | Must | CU-E05 | CHIM-19 | `AsignadorMisionTest.aerocivilRechaza`, `ApiMisionesIntegracionTest.aerocivilRechaza` |
| RF-14 | Planificar la ruta en tramos de máximo 5 km, con estación de carga y el tipo de drone adecuado por etapa | Must | CU-E06, CU-E07 | CHIM-20, CHIM-21, CHIM-23 | `RutasTest.divideEnTramos`, `RutasTest.fabricaPorTramo`, `RutasTest.distanciaNoPositiva` |
| RF-15 | Avisar cada etapa completada de la ruta | Should | CU-E08 | CHIM-22 | `RutasTest.observadorDeEtapas` |
| RF-16 | Rechazar misiones de una sede inactiva | Must | CU-E12 | CHIM-16 | `AsignadorMisionTest.sedeInactiva`, `ApiMisionesIntegracionTest.sedeInactiva` |
| RF-17 | Rechazar un paquete más pesado que cualquier drone apto de la sede | Must | CU-E02 (flujo alterno) | CHIM-18 | `AsignadorMisionTest.paqueteMuyPesado`, `ApiMisionesIntegracionTest.paquetePesado` |
| RF-18 | Calcular por sede tasa de éxito, tiempo promedio, drone más usado, porcentaje de urgentes y ranking | Should | CU-E14, CU-E15 | CHIM-24, CHIM-26 | `AnalisisRedTest` |
| RF-19 | Mostrar la flota de toda la red por sede y tipo | Could | CU-E09, CU-E16 | CHIM-25 | `ApiMisionesIntegracionTest.sedeInactiva` (usa `MonitorFlota`) |

## Requerimientos no funcionales

| Código | Nombre | MoSCoW | CU | HU en Jira | Prueba que lo valida |
|---|---|---|---|---|---|
| RNF-08 | Cobertura de líneas de 85 % o más y de ramas de 75 % o más | Must | todos | transversal | regla `check` de JaCoCo en el `pom.xml`, que hace fallar el build |
| RNF-09 | Asignación en menos de 500 ms con 100 drones, sin contar la consulta a Aerocivil | Must | CU-E02 | CHIM-17 | `ApiMisionesIntegracionTest.asignacionRapidaConCienDrones` |
| RNF-10 | Una sede nueva configura su identidad visual solo con tokens, en menos de 2 horas | Should | CU-E13 | CHIM-27 | revisión del prototipo y de `mock/tokens-sedes.html` |
| RNF-11 | El dominio no depende de librerías externas ni de las capas superiores | Must | todos | transversal | `ArquitecturaTest` |

Ningún RF queda sin caso de uso ni sin prueba. Los CU se describen en `10-casos-de-uso.md`.

## Tensión RF-12 y RNF-09

RF-12 y RF-13 obligan a consultar a Aerocivil, un sistema externo cuyo tiempo de respuesta no controlamos, y RNF-09 pide asignar en menos de 500 ms.

**Cómo se resuelve:**

1. La seguridad gana: sin autorización de Aerocivil no se asigna. RF-13 no se negocia.
2. RNF-09 se redefine para medir lo que sí controlamos: la parte local de la asignación (sede, clima, selección y guardado), con Aerocivil simulado, sobre 100 drones.
3. La consulta a Aerocivil tiene su propio límite de espera de 300 ms. Si no responde a tiempo, la misión se rechaza con el motivo `AEROCIVIL_RECHAZA` y queda pendiente, nunca se asigna sin autorización.
4. Las autorizaciones por corredor se pueden guardar 10 minutos para no repetir la consulta en misiones iguales.

## Otra contradicción: RF-14 y el tiempo de carga

RF-14 limita a 5 km cada tramo, pero un drone que se recarga en la estación tarda más de 30 minutos en volver a estar listo, y la regla del paquete dice que no puede quedar más de 30 minutos en estación.

**Cómo se resuelve:** el paquete no espera al mismo drone. En la estación pasa a otro drone ya cargado (relevo). Por eso `PlanificadorRuta.droneParaEtapa` crea un drone por etapa, y el drone que llegó queda en carga sin retener el paquete.
