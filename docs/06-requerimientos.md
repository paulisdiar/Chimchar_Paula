# Reto 06 - Requerimientos y MoSCoW del SkyCampus MVP

## Requerimientos funcionales

| Código | Requerimiento | MoSCoW | Justificación |
|---|---|---|---|
| RF-01 | El operador registra una misión indicando origen, destino y tipo de carga, y el sistema le muestra el código de misión generado. | Must | Sin misiones registradas el sistema no cumple su propósito. |
| RF-02 | El operador asigna manualmente un drone disponible con batería de 30% o más, y la misión pasa a estado EN_VUELO. | Must | Es el paso central del flujo del MVP y la asignación es manual. |
| RF-03 | El operador consulta la lista de misiones con su origen, destino, carga, drone y estado. | Should | Ayuda a dar seguimiento, pero el reparto funciona sin ella. |

## Requerimientos no funcionales

| Código | Requerimiento | MoSCoW | Justificación |
|---|---|---|---|
| RNF-01 | La consulta de la flota debe mostrarse en menos de 2 segundos con 5 drones, medido con 10 ejecuciones consecutivas. | Must | El operador decide rápido y necesita ver el estado sin esperar. |
| RNF-02 | El código debe tener al menos 80% de cobertura de líneas, verificado con el reporte de JaCoCo. | Must | Es el mínimo exigido para dar el MVP por terminado. |
| RNF-03 | Todo mensaje de rechazo debe incluir el ID del drone, el valor encontrado y el mínimo requerido (ejemplo: "El drone D-04 tiene batería insuficiente (18%). Mínimo requerido: 30%."). | Should | Mejora la claridad para el operador, pero no bloquea el flujo. |

## Fuera del MVP (Won't Have)

Asignación automática y ruta optimizada: se dejan para Monferno.
