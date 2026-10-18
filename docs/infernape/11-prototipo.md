# Infernape 11 - Prototipo navegable del dashboard Enterprise

Archivo: `mock/prototipo-dashboard.html`. Se abre en cualquier navegador, sin servidor. Es una sola página con HTML, CSS y JavaScript, y no necesita internet.

## Qué cubre cada punto del reto

| Punto del reto | Cómo se cumple |
|---|---|
| 1. Red completa de 4 sedes con el estado de la flota en tiempo real | La sección "Red de sedes" muestra ECI, UNAL, Uniandes y Javeriana con drones disponibles, en vuelo, en carga y en fallo. Cada 4 segundos cambia el estado de una sede y se actualiza la hora. |
| 2. Dos identidades solo cambiando tokens | Los botones ECI y UNAL de la barra solo cambian el atributo `data-sede` de la página. Todos los colores salen de las variables de `08-design-tokens.md`. |
| 3. Flujo completo de planificación de ruta con todos los estados de error | Se describe abajo. |
| 4. Prueba con un compañero sin instrucciones | El protocolo está abajo. El resultado de la prueba debe llenarlo quien la haga. |

## Flujo de planificación de ruta

Formulario con origen, destino, peso y prioridad, y el botón "Planificar ruta". Al enviarlo se muestran cuatro pasos con indicador de progreso (Aerocivil, etapas, estaciones y drones). Si todo sale bien aparece la tabla de etapas con su drone, y el botón "Confirmar y despegar" abre el seguimiento etapa por etapa.

| Estado | Cómo se provoca | Qué ve el usuario |
|---|---|---|
| Validación de campos | Mismo origen y destino, o peso fuera de 1 a 2000 | Mensaje bajo el campo, sin enviar |
| Sede inactiva | Pulsar "Desactivar sede" en una tarjeta y planificar con ella | Error con botón "Activar {sede}" |
| Clima adverso | Origen UNAL (viento 38 km/h) | Error con el viento medido y el máximo permitido |
| Aerocivil rechaza | ECI hacia Uniandes | Error con motivo y sugerencia |
| Paquete demasiado pesado | Más de 500 g en una ruta de 3 o más etapas (ECI hacia Javeriana con 600 g) | Error con el máximo de la ruta |
| Sin estación de carga | ECI hacia Javeriana con 300 g | Error con botón "Reintentar ahora" |
| Éxito | ECI hacia UNAL con 300 g | Tabla de etapas, confirmación y seguimiento |

Todos los errores usan el mismo patrón: qué pasó, por qué y qué puede hacer el usuario ahora.

## Heurísticas de Nielsen

| # | Heurística | En el prototipo |
|---|---|---|
| 1 | Visibilidad del estado | Indicador de pasos, hora de actualización y seguimiento de etapas |
| 2 | Lenguaje del usuario | "Sede", "etapa", "estación de carga"; sin códigos internos |
| 3 | Control y libertad | Botón "Cambiar datos" en cada error y en el resultado |
| 4 | Consistencia | Mismo patrón de aviso y mismo botón de acción principal en toda la página |
| 5 | Prevención de errores | Mínimo y máximo del peso, y validación antes de enviar |
| 6 | Reconocer antes que recordar | Los selectores muestran las sedes y la ayuda del peso está visible |
| 7 | Flexibilidad y eficiencia | Los botones de reintento y activación están en el mismo mensaje de error |
| 8 | Diseño minimalista | Una pantalla con dos bloques: red y planificación |
| 9 | Recuperación de errores | Cada error propone una acción concreta |
| 10 | Ayuda y documentación | Texto de ayuda en el campo de peso y mensajes que se explican solos |

## Protocolo de la prueba con un compañero

1. Abrir `mock/prototipo-dashboard.html` y entregar la pantalla sin explicar nada.
2. Pedir tres tareas: planificar una ruta de ECI a UNAL, cambiar la identidad a UNAL y resolver el error que aparece al planificar de ECI a Uniandes.
3. Observar en silencio. Cada pregunta del compañero es un problema de Nielsen que hay que corregir.
4. Anotar tiempo por tarea, preguntas hechas y puntos de confusión.

## Resultado de la prueba

Pendiente: la prueba necesita una persona real y no se puede simular. Se completa cuando un compañero la haga.

| Campo | Valor |
|---|---|
| Compañero | por completar |
| Fecha | por completar |
| Tarea 1: tiempo y preguntas | por completar |
| Tarea 2: tiempo y preguntas | por completar |
| Tarea 3: tiempo y preguntas | por completar |
| Problemas de Nielsen encontrados y corrección | por completar |
