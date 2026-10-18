# Infernape 08 - Design tokens de SkyCampus Enterprise

Demostración: `mock/tokens-sedes.html`. Muestra el "Panel de misión activa" y la "Tarjeta de drone" en tres identidades (ECI, UNAL y Uniandes) con exactamente el mismo HTML y el mismo CSS. Lo único que cambia de una sede a otra es el atributo `data-sede` y el bloque de tokens de esa sede.

## Tokens globales (iguales en todas las sedes)

| Token | Valor | Uso |
|---|---|---|
| `--radio` | 10px | esquinas de tarjetas y botones |
| `--espacio` | 16px | separación base |
| `--fuente` | Inter, Segoe UI, Arial | interfaz |
| `--mono` | JetBrains Mono, Consolas | IDs de drone y códigos de misión |
| `--disponible` | `#22C55E` | estado Disponible |
| `--vuelo` | `#3B82F6` | estado En vuelo |
| `--carga` | `#EAB308` | estado En carga |
| `--fallo` | `#EF4444` | estado Fallo |
| `--mantenimiento` | `#9CA3AF` | estado Mantenimiento |

Los colores de estado no cambian por sede: un drone en fallo debe verse igual de rojo en las tres universidades, porque el color comunica una situación y no una marca.

## Tokens por sede (los 8 que se configuran)

| Token | ECI | UNAL | Uniandes | Uso |
|---|---|---|---|---|
| `--fondo` | `#0B1020` | `#0A1F14` | `#111111` | fondo de pantalla |
| `--superficie` | `#131A2E` | `#10301F` | `#1C1C1C` | tarjetas |
| `--primario` | `#1E3A8A` | `#166534` | `#FFD100` | barra superior |
| `--acento` | `#7C3AED` | `#FACC15` | `#FFFFFF` | acción principal y progreso |
| `--texto` | `#E5EAF5` | `#ECFDF3` | `#F5F5F5` | texto principal |
| `--texto-sec` | `#94A3B8` | `#9CC5AB` | `#A3A3A3` | texto secundario |
| `--sobre-primario` | `#FFFFFF` | `#FFFFFF` | `#111111` | texto sobre la barra |
| `--sobre-acento` | `#FFFFFF` | `#1A1A00` | `#111111` | texto sobre el botón |

Los colores de UNAL y Uniandes son de referencia para el ejercicio y no son las paletas oficiales.

## Contraste verificado

Relación de contraste calculada con la fórmula de WCAG (el mínimo exigido es 4.5):

| Par | ECI | UNAL | Uniandes |
|---|---|---|---|
| `--texto` sobre `--fondo` | 15.7 | 16.3 | 17.3 |
| `--texto` sobre `--superficie` | 14.3 | 13.6 | 15.6 |
| `--texto-sec` sobre `--superficie` | 6.7 | 7.5 | 6.8 |
| `--sobre-primario` sobre `--primario` | 10.4 | 7.1 | 12.9 |
| `--sobre-acento` sobre `--acento` | 5.7 | 11.5 | 18.9 |

## Por qué el componente no cambia

La "Tarjeta de drone" y el "Panel de misión activa" solo leen variables (`var(--primario)`, `var(--acento)`, etc.). Ninguna regla del componente menciona un color, una universidad o una clase por sede. Agregar una sede no toca el HTML ni el CSS del componente: se agrega un bloque de 8 variables.

## Configurar una sede nueva en menos de 2 horas

| Paso | Tiempo estimado |
|---|---|
| 1. Copiar el bloque `[data-sede='eci']` y renombrarlo con el código de la nueva sede | 5 min |
| 2. Elegir color primario y de acento a partir del manual de marca de la universidad | 30 min |
| 3. Derivar fondo, superficie y textos con el mismo orden de la tabla | 20 min |
| 4. Verificar los 5 pares de contraste de la tabla de arriba (mínimo 4.5) | 20 min |
| 5. Poner `data-sede` en la raíz y abrir `mock/tokens-sedes.html` para revisar los dos componentes | 15 min |
| 6. Registrar la sede y subir los cambios | 15 min |
| Total | 1 h 45 min |
