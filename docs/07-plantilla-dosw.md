# Reto 07 - Plantilla DOSW

| Campo | Valor |
|---|---|
| Código | SC-01 |
| Nombre | Registrar misión de reparto de documento |
| Actor | Operador de drones |

## Precondiciones

- Debe existir al menos un drone disponible con batería de 30% o más.
- El operador debe haber iniciado el sistema.

## Datos de entrada

| Campo | Tipo |
|---|---|
| drone asignado | Drone(id:String, modelo:String, bateria:int, disponible:boolean, ubicacion:String) |
| origen | String |
| destino | Enum(Bloque A, Bloque B, Bloque C, Bloque D, Biblioteca) |
| tipoCarga | Enum(SOBRE, CARPETA, LIBRO) |

## Datos de salida

| Campo | Tipo |
|---|---|
| codigoMision | String (formato "M" + número consecutivo, ejemplo M1) |
| estado | Enum(PENDIENTE, EN_VUELO, ENTREGADA, FALLIDA) |
| dronesDisponibles | List<Drone(id:String, bateria:int)> |

## Flujo básico

1. El operador elige la opción "Registrar solicitud de reparto".
2. El operador ingresa el origen, selecciona el destino y selecciona el tipo de carga.
3. El sistema valida los datos, genera el código de misión y deja la misión en estado PENDIENTE.
4. El sistema muestra la lista de drones disponibles con su batería.
5. El operador elige un drone, el sistema valida batería, destino y carga, asigna el drone y la misión pasa a EN_VUELO.

## Flujos alternos

**A. Drone sin batería suficiente (paso 5).** El sistema rechaza la asignación con el mensaje "El drone D-04 tiene batería insuficiente (18%). Mínimo requerido: 30%." y la misión sigue en PENDIENTE. El operador vuelve al paso 4 y elige otro drone.

**B. Destino inválido (paso 2).** El sistema muestra "Origen o destino inválido.", no registra la misión y el operador vuelve al paso 2.

## Reglas de negocio

- RN-01: un drone no puede tener más de una misión activa al mismo tiempo.
- RN-02: el peso del tipo de carga no puede superar la capacidad del drone (SOBRE 0.05 kg, CARPETA 0.2 kg, LIBRO 0.5 kg; capacidad de cada drone 0.5 kg).
