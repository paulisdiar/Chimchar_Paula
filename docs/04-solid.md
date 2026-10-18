# Reto 04 - Principios SOLID sobre GestorDrone

## Principios violados

**S - Responsabilidad única.** `GestorDrone` asigna misiones, guarda en base de datos, envía correos, genera PDF y calcula rutas, así que tiene cinco razones para cambiar.

**O - Abierto/cerrado.** `calcularRuta` usa un `if/else` por tipo de ruta, y cada ruta nueva obliga a modificar la clase en lugar de extenderla.

**D - Inversión de dependencias.** `guardarEnBD` llama directamente a `DriverManager.getConnection` con la URL y la clave escritas en el código, lo que acopla la lógica a MySQL.

**I - Segregación de interfaces (posible).** Quien solo necesita asignar misiones queda atado a guardar, alertar y reportar porque todo vive en una sola clase.

**L - Sustitución de Liskov.** No se viola, porque el fragmento no usa herencia.

## Rediseño

| Clase | Única razón para cambiar |
|---|---|
| `AsignadorMision` | la regla de cómo se asigna una misión |
| `RepositorioMision` (interfaz) y `RepositorioMisionMemoria` | la forma de almacenar misiones |
| `AlertaOperador` (interfaz) y `AlertaConsola` | el canal por el que se avisa al operador |
| `GeneradorReporte` | el formato del reporte |
| `EstrategiaRuta` (interfaz), `RutaDirecta` y `RutaEvitandoEdificios` | el algoritmo de cada ruta |

`AsignadorMision` depende de las abstracciones `EstrategiaAsignacion`, `RepositorioMision` y `AlertaOperador`, que recibe por constructor. Cambiar de memoria a MySQL o de consola a correo es crear otra implementación, sin tocar el asignador. Una ruta nueva es una clase nueva que implementa `EstrategiaRuta`.
