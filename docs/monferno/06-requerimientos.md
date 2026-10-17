# Monferno 06 - RF y RNF de SkyCampus v2

## Requerimientos funcionales

| Código | Requerimiento | MoSCoW | Justificación |
|---|---|---|---|
| RF-07 | El sistema asigna automáticamente a una misión NORMAL o BAJO el drone apto de mayor batería. | Must | Es la novedad central de v2 y reemplaza la asignación manual. |
| RF-08 | El sistema asigna a una misión URGENTE el drone EXPRESS apto de mayor batería, y si no hay ninguno, el drone apto de menor capacidad que cabe el paquete. | Must | Las urgencias deben salir lo más rápido posible. |
| RF-09 | El sistema notifica al técnico de mantenimiento cuando un drone entra en estado FALLO, indicando el ID del drone. | Must | Un drone en fallo sin atender reduce la flota. |
| RF-10 | El sistema consulta el clima antes de lanzar y bloquea la asignación si las condiciones no son aptas, explicando el motivo al operador. | Must | No se vuela con viento fuerte o lluvia. |
| RF-11 | El técnico marca un drone en FALLO como reparado y el drone vuelve a DISPONIBLE. | Should | Cierra el ciclo del fallo, pero la operación continúa sin él. |

Los cuatro RF que cubren las funcionalidades pedidas son RF-07 (asignación automática), RF-09 (alertas de fallo), RF-10 (consulta meteorológica) y RF-11 (gestión por el técnico). RF-08 se documenta aparte por su tensión con RF-07.

## Requerimientos no funcionales

| Código | Requerimiento | MoSCoW | Justificación |
|---|---|---|---|
| RNF-04 | La asignación automática debe seleccionar el drone en menos de 500 ms para una flota de hasta 50 drones, medido con JUnit 5 y `assertTimeout`. | Must | El operador no debe esperar con misiones urgentes en cola. |
| RNF-05 | Un cambio de estado de drone debe llegar al panel, al log y a la alerta técnica en menos de 1 segundo, medido con una prueba que mide el tiempo del `cambiarEstado`. | Must | Es la base de la visibilidad del estado. |
| RNF-06 | La cobertura debe ser de al menos 80% en líneas y 70% en ramas, verificada con la regla `check` de JaCoCo que hace fallar el build. | Must | Es el Quality Gate acordado para v2. |
| RNF-07 | El panel de alertas no debe acumular más de 7 notificaciones sin confirmar, verificado con una prueba de `PanelOperador`. | Should | Evita saturar al operador (número de Miller). |

## Tensión entre RF-07 y RF-08

"Mayor batería" y "más rápido" pueden apuntar a drones distintos: el de más batería puede ser un MINI lento y el EXPRESS más rápido puede tener menos carga.

**Cómo se resuelve:**

1. La prioridad de la misión decide qué regla aplica: URGENTE usa RF-08 y el resto usa RF-07.
2. RF-08 no ignora la seguridad: el EXPRESS elegido debe cumplir igual el mínimo de 30% de batería, la capacidad del paquete y la disponibilidad (`CriterioApto`).
3. Entre varios EXPRESS aptos se elige el de mayor batería, así se aplica RF-07 como desempate.
4. Si no hay EXPRESS apto, la misión urgente no se queda esperando: usa el drone apto de menor capacidad que cabe el paquete.

Está implementado en `AsignacionTipoCompatible` y probado en `AsignadorMisionTest.misionUrgente_asignaDroneExpress` y `EstrategiasAsignacionTest.urgenteSinExpress`.
