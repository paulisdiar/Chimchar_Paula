# Reto 09 - Jira

## Épica

Digitalizar el reparto interno de la ECI mediante una flota de drones supervisada.

## Feature

Gestión de flota de drones del campus.

## Historias de usuario

**HU-1. Ver flota.** Como operador, quiero ver los drones con su batería, estado y ubicación para decidir cuál asignar a una misión.

**HU-2. Asignar drone a misión.** Como operador, quiero asignar un drone disponible a una misión pendiente para que el documento sea entregado en su destino.

**HU-3. Cancelar misión pendiente.** Como operador, quiero cancelar una misión que aún está pendiente para recuperar el control si la solicitud cambió o se registró por error.

## HU-2: subtareas

1. Implementar la consulta que filtra drones disponibles con batería de 30% o más usando Stream.
2. Implementar la cadena de validación (batería, destino y carga) con `ValidadorBateria`, `ValidadorDestino` y `ValidadorCarga`.
3. Escribir las pruebas JUnit de `ValidadorMision` con el patrón AAA.

## HU-2: criterios de aceptación

1. **Dado** una misión PENDIENTE y un drone disponible con batería de 30% o más, **cuando** el operador lo asigna, **entonces** la misión pasa a EN_VUELO y el drone queda no disponible.
2. **Dado** un drone con batería menor a 30%, **cuando** el operador intenta asignarlo, **entonces** el sistema rechaza la asignación con el mensaje "El drone D-04 tiene batería insuficiente (18%). Mínimo requerido: 30%." y la misión sigue PENDIENTE.
