# Monferno 11 - Flujo de asignación automática

## Pantallas

| Pantalla | Archivo |
|---|---|
| Panel de flota | `mock/01-panel-flota.png` |
| Formulario de misión | `mock/02-formulario-mision.png` |
| Confirmación | `mock/03-confirmacion.png` |

## Estados de error

| Error | Archivo | Mensaje |
|---|---|---|
| Sin drones disponibles | `mock/error-sin-drones.png` | explica por qué y ofrece «Programar para más tarde» |
| Clima adverso | `mock/error-clima-adverso.png` | bloquea el flujo con el viento medido y el máximo permitido |
| Paquete muy pesado | `mock/error-paquete-pesado.png` | muestra el peso ingresado y el máximo de la flota disponible |

El HTML con todo está en `mock/flujo-asignacion.html`.

## Prompt

```
Actúa como diseñador UX/UI senior de sistemas de control.
SISTEMA: SkyCampus v2 - flujo de asignación automática de drone (flota de 20 drones)
PANTALLAS: 1) Panel de flota agrupado por estado, 2) Formulario de misión con drones aptos
en tiempo real, 3) Confirmación con drone elegido, ruta estimada y ETA.
ESTILO: identidad de SkyCampus (fondo #0B1020, primario #1E3A8A, acento #7C3AED, Inter y JetBrains Mono),
estados Disponible verde, En vuelo azul, En carga amarillo, Fallo rojo, Mantenimiento gris.
ERRORES: sin drones disponibles, clima adverso, paquete muy pesado.
Nielsen: #1, #3, #5, #6, #8, #9.
```

## Principios de Nielsen que cumple (8)

1. **#1 Visibilidad del estado:** cada drone muestra estado con color y texto, el encabezado cuenta los drones por estado y un indicador de pasos muestra en qué pantalla está el operador.
2. **#2 Lenguaje del mundo real:** usa términos del operador (misión, drone, batería, ETA) y no términos técnicos del sistema.
3. **#3 Control y libertad:** hay «Cancelar misión en vuelo» siempre visible, «Cancelar» en la confirmación y «Volver al panel» en el error de clima.
4. **#4 Consistencia:** los mismos colores de estado en tarjetas, resumen, grupos y banners.
5. **#5 Prevención de errores:** «Asignar automáticamente» se deshabilita cuando hay un error, y los drones con batería menor a 30% aparecen en rojo con advertencia.
6. **#6 Reconocer antes que recordar:** el formulario muestra a la derecha los drones aptos y la regla que se aplicará, sin que el operador tenga que recordarlas.
7. **#8 Diseño minimalista:** cada tarjeta muestra solo ID, tipo, estado, batería y ubicación.
8. **#9 Mensajes de error claros:** cada error dice qué pasó, con el valor y el límite (38 km/h contra 25 km/h, 2500 g contra 2000 g), y propone la salida.
