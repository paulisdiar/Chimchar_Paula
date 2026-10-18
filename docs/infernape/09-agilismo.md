# Infernape 09 - Roadmap de Enterprise y retrospectiva

Épica en Jira: CHIM-15. Tres sprints de 2 semanas en el tablero del proyecto CHIM. Velocidad de referencia: 18 puntos, la del Sprint 1 de Monferno, y capacidad de 20.

| Sprint | Objetivo | Fechas |
|---|---|---|
| Enterprise Sprint 1 | Conectividad multi-sede básica | 2 al 13 de noviembre de 2026 |
| Enterprise Sprint 2 | Rutas multi-etapa y estaciones de carga | 16 al 27 de noviembre de 2026 |
| Enterprise Sprint 3 | Analytics y panel de superadmin | 30 de noviembre al 11 de diciembre de 2026 |

## Sprint 1: conectividad multi-sede básica (19 puntos)

| Jira | Historia | Puntos |
|---|---|---|
| CHIM-16 | HU-E1 Activar y desactivar sedes de la red | 3 |
| CHIM-17 | HU-E2 Asignar misión inter-sede | 8 |
| CHIM-18 | HU-E3 Rechazar paquetes que superan la capacidad | 3 |
| CHIM-19 | HU-E4 Autorización de Aerocivil antes de lanzar | 5 |

## Sprint 2: rutas multi-etapa y estaciones de carga (19 puntos)

| Jira | Historia | Puntos |
|---|---|---|
| CHIM-20 | HU-E5 Planificar ruta en tramos de máximo 5 km | 8 |
| CHIM-21 | HU-E6 Asignar estaciones de carga a la ruta | 5 |
| CHIM-22 | HU-E7 Aviso al completar cada etapa | 3 |
| CHIM-23 | HU-E8 Drone adecuado para cada etapa | 3 |

## Sprint 3: analytics y panel de superadmin (19 puntos)

| Jira | Historia | Puntos |
|---|---|---|
| CHIM-24 | HU-E9 Analytics de eficiencia por sede | 8 |
| CHIM-25 | HU-E10 Panel de superadmin con la flota por sede | 5 |
| CHIM-26 | HU-E11 Ranking de sedes | 3 |
| CHIM-27 | HU-E12 Identidad visual por sede con tokens | 3 |

| Sprint | Puntos planificados | Velocity estimada |
|---|---|---|
| 1 | 19 | 18 a 20 |
| 2 | 19 | 19 |
| 3 | 19 | 19 |
| Total del trimestre | 57 | |

## Retrospectiva simulada del Sprint 1

Retrospectiva ficticia, escrita para el ejercicio: el Sprint 1 todavía no se ejecuta.

**Lo que salió bien**

1. Los puertos del dominio permitieron probar el asignador con Mockito antes de tener Aerocivil real.
2. Los cinco flujos alternos quedaron con prueba desde el primer día.
3. La definición de hecho (pruebas, cobertura y revisión) evitó historias a medias.

**Lo que hay que mejorar**

1. HU-E2 se estimó en 8 y ocupó casi todo el sprint: hay que partir las historias de más de 5 puntos.
2. La integración con Aerocivil se probó solo con un simulador y falta un entorno de pruebas real.
3. Las decisiones de tensión entre requerimientos se tomaron tarde, en la mitad del sprint.

**Compromisos**

| Compromiso | Responsable | Fecha |
|---|---|---|
| Partir toda historia de más de 5 puntos antes de planificar | Paula Díaz (dueña del producto) | 13 de noviembre de 2026 |
| Pedir a Aerocivil acceso a su entorno de pruebas | Coordinador de sede ECI | 20 de noviembre de 2026 |
| Revisar las tensiones entre RF y RNF en el refinamiento, antes del sprint | Paula Díaz | 16 de noviembre de 2026 |
