# Monferno 09 - Sprint 1 de SkyCampus v2

Épica CHIM-9. Sprint 1 creado en el tablero del proyecto CHIM.

Velocidad del equipo: 20 puntos. Estimación en Fibonacci (1, 2, 3, 5, 8).

## Planificación

| HU | Historia | Puntos | Entra al sprint |
|---|---|---|---|
| HU-S1 (CHIM-10) | Como operador, quiero que el sistema asigne automáticamente el drone de mayor batería para no elegirlo a mano en cada misión. | 5 | Sí |
| HU-S2 (CHIM-11) | Como solicitante de un envío urgente, quiero que se asigne un drone EXPRESS para que mi paquete llegue lo más rápido posible. | 5 | Sí |
| HU-S3 (CHIM-12) | Como operador, quiero que el sistema consulte el clima antes de lanzar para no poner en riesgo un drone. | 3 | Sí |
| HU-S4 (CHIM-13) | Como técnico de mantenimiento, quiero recibir una alerta cuando un drone entra en FALLO para atenderlo de inmediato. | 5 | Sí |
| HU-S5 (CHIM-14) | Como técnico de mantenimiento, quiero marcar un drone como reparado para que vuelva a estar disponible. | 3 | No |

Entran HU-S1, HU-S2, HU-S3 y HU-S4 con 18 puntos. HU-S5 sumaría 21 y superaría la velocidad de 20, por eso queda para el Sprint 2.

## Criterios de aceptación (Gherkin)

### HU-S1 Asignación automática por mayor batería

```gherkin
Escenario: Se asigna el drone de mayor batería
  DADO QUE hay 3 drones disponibles con batería de 85%, 91% y 67%
    Y la solicitud es de prioridad NORMAL con un paquete de 200 g
  CUANDO el sistema ejecuta la asignación automática
  ENTONCES selecciona el drone con 91% de batería
    Y notifica al PanelOperador y al SistemaLog

Escenario: Se ignoran los drones con batería insuficiente
  DADO QUE el único drone disponible tiene 18% de batería
  CUANDO el sistema ejecuta la asignación automática
  ENTONCES no asigna ningún drone
    Y la misión permanece PENDIENTE
```

### HU-S2 Misión urgente a EXPRESS

```gherkin
Escenario: Una misión urgente recibe un EXPRESS
  DADO QUE hay 3 drones disponibles con batería de 30% o más
    Y la solicitud es de tipo URGENTE con un paquete de 300 g
  CUANDO el sistema ejecuta la asignación automática
  ENTONCES selecciona el drone de tipo EXPRESS con mayor batería
    Y cambia el estado del drone a EN_VUELO
    Y notifica al PanelOperador y al SistemaLog en menos de 500 ms

Escenario: Sin EXPRESS apto la misión urgente no espera
  DADO QUE no hay ningún drone EXPRESS con 30% de batería o más
    Y la solicitud es URGENTE con un paquete de 300 g
  CUANDO el sistema ejecuta la asignación automática
  ENTONCES asigna el drone apto de menor capacidad que cabe el paquete
```

### HU-S3 Consulta de clima

```gherkin
Escenario: El clima es apto
  DADO QUE la API meteorológica responde que las condiciones son aptas
  CUANDO el operador solicita la asignación
  ENTONCES el sistema continúa con la selección del drone

Escenario: El clima es adverso
  DADO QUE la API meteorológica responde que las condiciones no son aptas
  CUANDO el operador solicita la asignación
  ENTONCES el sistema no asigna ningún drone
    Y muestra "Condiciones climáticas adversas: no se puede lanzar el drone."
```

### HU-S4 Alerta de fallo al técnico

```gherkin
Escenario: Un drone entra en FALLO
  DADO QUE el AlertaTecnico está suscrito al GestorFlota
  CUANDO el drone D-04 cambia al estado FALLO
  ENTONCES el técnico recibe la alerta "Revisar drone D-04 en FALLO"
    Y el SistemaLog registra el cambio

Escenario: Un cambio normal no alerta al técnico
  DADO QUE el AlertaTecnico está suscrito al GestorFlota
  CUANDO el drone D-01 cambia al estado EN_VUELO
  ENTONCES el técnico no recibe ninguna alerta
```

### HU-S5 Drone reparado (Sprint 2)

```gherkin
Escenario: El técnico marca un drone como reparado
  DADO QUE el drone D-04 está en estado FALLO
  CUANDO el técnico lo marca como reparado
  ENTONCES el drone pasa a DISPONIBLE

Escenario: Solo se repara un drone en FALLO
  DADO QUE el drone D-01 está EN_VUELO
  CUANDO el técnico intenta marcarlo como reparado
  ENTONCES el sistema rechaza la acción
```

## Definition of Done del equipo SkyCampus v2

- Código revisado en PR por al menos otro miembro del equipo.
- Pruebas unitarias con cobertura JaCoCo de 80% o más.
- SonarQube con 0 bugs y 0 vulnerabilidades.
- Todos los criterios Gherkin de la HU pasan.
- Mergeado a `develop` con el flujo GitFlow correcto.
- Diagrama de contexto y plantilla DOSW actualizados si el RF cambió.
