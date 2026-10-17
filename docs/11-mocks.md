# Reto 11 - Mocks del panel de operador

Los tres estados están en `docs/mock/panel-flota.html` y exportados como `docs/mock/panel-normal.png`, `docs/mock/panel-alerta.png` y `docs/mock/panel-vacio.png`.

## Prompt

```
Actúa como diseñador UX/UI senior de sistemas de control.
SISTEMA: SkyCampus - Panel de control de flota de drones ECI
PANTALLA: Panel de monitoreo de la flota (vista principal del operador)
ESTILO: fondo #0B1020, superficie #131A2E, primario #1E3A8A, acento #7C3AED, texto #E5EAF5.
Tipografía Inter para la interfaz y JetBrains Mono para IDs. Fondo oscuro tipo dashboard técnico.
ACTOR: Operador de drones - necesita tomar decisiones rápidas
DATOS A MOSTRAR POR DRONE: ID (formato D-XX), batería en %, estado
(DISPONIBLE/EN_VUELO/EN_CARGA/FALLO), ubicación actual
ACCIONES DEL OPERADOR: Seleccionar drone, Asignar a misión, Ver detalle
ESTADOS DE LA PANTALLA:
  1. Normal: flota con drones en distintos estados
  2. Alerta: un drone en estado FALLO (destacado visualmente)
  3. Vacío: todos los drones en misión simultáneamente
Nielsen: visibilidad del estado (#1), minimalismo (#8), prevención errores (#5)
```

## Heurísticas que cumplen

| Estado | Heurísticas |
|---|---|
| Normal | #1 estado visible por color y texto, #5 Asignar deshabilitado con batería menor a 30%, #8 solo ID, estado, batería, ubicación y acciones |
| Alerta | #1 fila y banner en rojo para el drone en FALLO, #9 el banner nombra el drone y el bloque, #5 Asignar deshabilitado en el drone con fallo |
| Vacío | #1 el resumen marca 0 disponibles, #9 el mensaje explica por qué no se puede asignar, #5 todos los botones Asignar deshabilitados |
