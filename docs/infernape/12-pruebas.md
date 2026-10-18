# Infernape 12 - Tres capas de pruebas

## Pirámide

| Capa | Pruebas | Qué prueban |
|---|---|---|
| Unitarias | `AsignadorMisionTest`, `AnalisisRedTest`, `RutasTest`, `ArquitecturaTest` | Lógica aislada. El asignador se prueba con puertos simulados con Mockito. |
| Integración | `ApiMisionesIntegracionTest` | `ApiMisiones`, `AsignadorMision`, `GestorZonas` y los repositorios en memoria juntos, con clima y Aerocivil simulados. |
| Rendimiento | `asignacionRapidaConCienDrones` | Asignación con 100 drones en menos de 500 ms (`assertTimeout`). |

La capa de integración usa repositorios en memoria en lugar de H2 y llama a `ApiMisiones` en lugar de MockMvc porque el proyecto aún no tiene Spring (ver `04-solid.md`).

## Los 5 flujos alternos

| Flujo | Unitaria | Integración |
|---|---|---|
| Clima adverso | `climaAdverso` | `climaAdverso` |
| Sin drones | `sinDrones` | `sinDrones` |
| Paquete pesado | `paqueteMuyPesado` | `paquetePesado` |
| Aerocivil rechaza | `aerocivilRechaza` | `aerocivilRechaza` |
| Sede inactiva | `sedeInactiva` | `sedeInactiva` |

## Cobertura medida con JaCoCo

| Contador | Resultado |
|---|---|
| LINE | 90.5 % (674 de 745) |
| BRANCH | 85.9 % (171 de 199) |

Las 94 pruebas pasan. La regla `check` del `pom.xml` exige LINE 85 % y BRANCH 75 %.
