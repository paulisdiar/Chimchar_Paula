# Reto 08 - Manual de identidad y UX/UI

## Paleta

| Uso | Color | Hex |
|---|---|---|
| Fondo | Azul noche | #0B1020 |
| Superficie | Azul pizarra oscuro | #131A2E |
| Superficie elevada | Azul pizarra | #1B2440 |
| Primario | Azul oscuro | #1E3A8A |
| Acento | Morado técnico | #7C3AED |
| Texto | Blanco azulado | #E5EAF5 |
| Texto secundario | Gris pizarra | #94A3B8 |

El azul oscuro y el gris pizarra transmiten un sistema de control confiable, y el morado se reserva para la acción principal (Asignar) para que sea lo primero que se ve.

## Colores de estado

| Estado | Color | Hex |
|---|---|---|
| Disponible | Verde | #22C55E |
| En vuelo | Azul | #3B82F6 |
| En carga | Amarillo | #EAB308 |
| Fallo | Rojo | #EF4444 |

El estado nunca se comunica solo con color: siempre va acompañado de texto.

## Tipografía

- Interfaz: Inter (sans-serif), pesos 400 y 600.
- Códigos de misión e IDs de drone: JetBrains Mono.

## Tono de voz

Técnico y claro, mensajes directos con el dato concreto. Ejemplo: "El drone D-04 tiene batería insuficiente (18%). Mínimo requerido: 30%."

## Mock

Panel de monitoreo de la flota con los 5 drones en `docs/mock/panel-flota.html` y capturas en `docs/mock/panel-normal.png`.

## Heurísticas de Nielsen que cumple el mock

1. **#1 Visibilidad del estado:** cada drone muestra su estado con punto de color y texto, y el encabezado resume cuántos hay por estado, sin hacer clic.
2. **#4 Consistencia:** los mismos colores de estado se usan en la fila, en el resumen y en los banners.
3. **#5 Prevención de errores:** el botón Asignar está deshabilitado para drones con batería menor a 30%, en vuelo o en fallo, así el error no llega a ocurrir.
4. **#8 Diseño minimalista:** cada fila muestra solo ID, estado, batería, ubicación y acciones.
5. **#9 Mensajes de error claros:** bajo el botón deshabilitado aparece el motivo con valores ("Batería insuficiente (18%). Mínimo requerido: 30%.") y en el estado de alerta un banner nombra el drone y el bloque.
6. **#6 Reconocer antes que recordar:** las acciones Asignar y Ver detalle están visibles en cada fila, sin menús ocultos.
