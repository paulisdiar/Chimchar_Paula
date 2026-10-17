# Monferno 07 - Plantilla DOSW

| Campo | Valor |
|---|---|
| Código | SC-07 |
| Nombre | Asignar automáticamente drone a misión |
| Actor | Operador de drones |

## Precondiciones

- Debe existir una solicitud de misión registrada en estado PENDIENTE.
- Debe existir al menos un drone en estado DISPONIBLE con batería de 30% o más.

## Datos de entrada

| Nombre | Tipo de campo | Reglas | Oblig. |
|---|---|---|---|
| paquete | - | - | Sí |
| paquete.peso | Integer | Entre 1 y 2000 gramos | Sí |
| paquete.tipo | Enum(SOBRE,CARPETA,LIBRO,EQUIPO) | - | Sí |
| paquete.prioridad | Enum(URGENTE,NORMAL,BAJO) | - | Sí |
| destino | Enum(BLOQUE_A,BLOQUE_B,BLOQUE_C,BLOQUE_D,BIBLIOTECA) | - | Sí |
| flota | List<Drone(id:String, tipo:Enum(MINI,CARGO,EXPRESS), bateria:Integer, estado:Enum(DISPONIBLE,EN_VUELO,ATERRIZANDO,EN_CARGA,FALLO,MANTENIMIENTO), capacidadGramos:Integer)> | Hasta 50 drones | Sí |

## Datos de salida

| Nombre | Tipo de campo | Reglas | Oblig. |
|---|---|---|---|
| droneAsignado | - | Calculado por el sistema según la estrategia activa | No (salida) |
| droneAsignado.id | String | Formato D-XX | No (salida) |
| droneAsignado.bateria | Integer | Entre 30 y 100 | No (salida) |
| droneAsignado.tipo | Enum(MINI,CARGO,EXPRESS) | - | No (salida) |
| mision.estado | Enum(PENDIENTE,EN_VUELO,ENTREGADA,FALLIDA) | EN_VUELO si se asignó | No (salida) |

## Flujo básico

1. El sistema recibe la solicitud de misión con el paquete y el destino.
2. El sistema consulta el clima a la API meteorológica.
3. El sistema filtra los drones aptos: disponibles, con batería de 30% o más y capacidad suficiente.
4. El sistema aplica la estrategia de selección activa.
5. El sistema valida batería, capacidad y disponibilidad del drone elegido.
6. El sistema asigna el drone y deja la misión en EN_VUELO.
7. El sistema notifica a los observadores (panel del operador, log y, si aplica, técnico).

## Flujos alternos

**A. Sin drones disponibles (paso 3).** El sistema no asigna, deja la misión PENDIENTE y muestra "No hay drones disponibles. Puede programar la misión para más tarde."

**B. Condiciones climáticas adversas (paso 2).** El sistema bloquea la asignación y muestra "Condiciones climáticas adversas: no se puede lanzar el drone." La misión sigue PENDIENTE.

**C. Paquete supera la capacidad máxima (paso 3).** El sistema lanza `PaqueteMuyPesadoException` y muestra el peso máximo de la flota disponible, por ejemplo "El paquete supera la capacidad máxima disponible: 2000 g."

## Reglas de negocio

- RN-03: un drone necesita al menos 30% de batería para ser asignado.
- RN-04: un drone de tipo CARGO no puede usarse para paquetes de menos de 100 g.
- RN-05: una misión URGENTE recibe un EXPRESS apto si existe (resuelve la tensión RF-07 y RF-08).
