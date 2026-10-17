# Monferno 04 - SOLID: del GestorDrone del MVP a la arquitectura v2

| Principio | Cómo estaba violado en GestorDrone | Cómo lo corrige v2 |
|---|---|---|
| S | una clase asignaba, guardaba en BD, enviaba correo, generaba PDF y calculaba rutas | `AsignadorMision` solo asigna, `SistemaLog` solo registra, `PanelOperador` solo muestra y `AlertaTecnico` solo alerta |
| O | `calcularRuta` usaba `if/else` y cada tipo nuevo obligaba a editar la clase | una estrategia nueva es una clase nueva que implementa `EstrategiaAsignacion`, y `GestorMisiones` no cambia |
| D | `guardarEnBD` dependía de `DriverManager` y MySQL escritos en el código | `AsignadorMision` recibe `ApiMeteorologica`, `EstrategiaAsignacion` y `ObservadorDrone` por constructor y `GestorFlota` depende de la interfaz `ObservadorDrone` |

La cadena de validadores del MVP (`Validador`, `ValidadorBateria`, `ValidadorDestino`, `ValidadorCarga`) refuerza S y O: cada validador tiene una sola regla y uno nuevo es una clase nueva.

## Prueba

`GestorMisionesTest.funcionaConCualquierEstrategia` es parametrizada y ejecuta `GestorMisiones` con las tres estrategias y con una cuarta escrita como lambda en la propia prueba. El resultado es el mismo flujo (misión EN_VUELO, un observador notificado, una misión registrada) sin tocar el código de `GestorMisiones`.
