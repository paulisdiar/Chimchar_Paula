# Infernape 07 - Plantilla DOSW de SC-15

| Campo | Valor |
|---|---|
| Código | SC-15 |
| Nombre | Planificar ruta multi-etapa inter-sede |
| Actor | Coordinador de sede |

## Precondiciones

- La sede de origen y la sede de destino están activas.
- Existe una misión registrada en estado PENDIENTE.
- La sede de origen tiene al menos un drone en estado DISPONIBLE con batería de 30 % o más.

## Datos de entrada

| Nombre | Tipo de campo | Reglas | Oblig. |
|---|---|---|---|
| mision | - | - | Sí |
| mision.id | String | Formato M-XX | Sí |
| mision.paquete | - | - | Sí |
| mision.paquete.peso | Integer | Entre 1 y 2000 gramos | Sí |
| mision.paquete.tipo | Enum(SOBRE,CARPETA,LIBRO,EQUIPO) | - | Sí |
| mision.paquete.prioridad | Enum(URGENTE,NORMAL,BAJO) | - | Sí |
| mision.paquete.restricciones | Enum(NINGUNA,FRAGIL,CONFIDENCIAL) | Una por paquete | Sí |
| mision.sedeOrigen | Enum(ECI,UNAL,UNIANDES,JAVERIANA) | Distinta de la sede de destino | Sí |
| mision.sedeDestino | Enum(ECI,UNAL,UNIANDES,JAVERIANA) | Distinta de la sede de origen | Sí |
| espacioAereo | - | - | Sí |
| espacioAereo.vientoKmH | Integer | Entre 0 y 120 | Sí |
| espacioAereo.zonaRestringida | Boolean | - | Sí |
| espacioAereo.corredorDisponible | Boolean | - | Sí |

## Datos de salida

| Nombre | Tipo de campo | Reglas | Oblig. |
|---|---|---|---|
| ruta | - | Calculada por el sistema | No (salida) |
| ruta.distanciaTotalMetros | Integer | Mayor que 0 | No (salida) |
| ruta.etapas | List<Etapa(numero:Integer, origen:String, destino:String, distanciaMetros:Integer, droneId:String, estacionCarga:String)> | Cada etapa mide como máximo 5000 m | No (salida) |
| autorizacionAerocivil | - | - | No (salida) |
| autorizacionAerocivil.codigo | String | Formato AC-XXXX | No (salida) |
| autorizacionAerocivil.vigenciaMinutos | Integer | Entre 1 y 60 | No (salida) |
| mision.estado | Enum(PENDIENTE,EN_VUELO,ENTREGADA,FALLIDA) | EN_VUELO si la ruta se confirmó | No (salida) |

## Flujo básico

1. El coordinador selecciona una misión pendiente y pide planificar su ruta.
2. El sistema verifica con Aerocivil que el corredor entre las dos sedes está autorizado.
3. El sistema calcula las etapas, dividiendo la distancia en tramos de máximo 5 km.
4. El sistema asigna una estación de carga a cada parada intermedia.
5. El sistema asigna un drone a cada etapa según el peso del paquete y la distancia del tramo.
6. El sistema muestra la ruta completa y el coordinador la confirma.
7. El sistema inicia el vuelo, deja la misión en EN_VUELO y avisa cada etapa completada.

## Flujos alternos

**A. Aerocivil rechaza (paso 2).** El sistema no planifica, deja la misión PENDIENTE y muestra "Aerocivil no autorizó el corredor. Motivo: {motivo}. Puede reintentar con otro horario."

**B. No hay estación de carga disponible (paso 4).** El sistema propone la estación libre más cercana dentro del alcance del drone y, si no existe, deja la misión PENDIENTE y muestra "No hay estación de carga disponible en la parada {nombre}. Se reintentará en 15 minutos."

**C. Paquete demasiado pesado para una ruta larga (paso 5).** Si el peso supera la capacidad de los drones aptos para ese tramo, el sistema muestra "El paquete de {peso} g supera la capacidad de la ruta ({capacidad} g). Divida el envío." y no continúa.

## Reglas de negocio

- RN-10: un drone no puede volar más de 5 km con carga sin recargar.
- RN-11: un paquete no puede quedar en una estación más de 30 minutos. Si el drone necesita recargar, el paquete pasa a otro drone ya cargado.
- RN-12: no se vuela sin autorización vigente de Aerocivil.
- RN-13: un drone necesita al menos 30 % de batería para ser asignado.
- RN-14: una sede inactiva no puede ser origen ni destino.
