# Monferno 02 - GitFlow: conflicto de merge en AsignadorMision

## Escenario

Dos ramas salen del mismo commit de `develop` (merge de la base de v2) y ambas modifican `AsignadorMision.java` en el método `asignar`:

| Rama | Cambio |
|---|---|
| `feature/Diaz-asignacion-automatica` | valida que el paquete no supere la capacidad máxima de la flota y lanza `PaqueteMuyPesadoException` |
| `feature/Diaz-alertas-estado` | agrega el `ObservadorDrone` al constructor y notifica `EN_VUELO` cuando se asigna un drone |

Se integró primero la rama de asignación automática y después la de alertas. El segundo merge produjo conflicto en `AsignadorMision.java`.

## Resolución

Se conservaron los dos cambios: la validación de peso antes de elegir el drone y la notificación después de elegirlo. Se borraron las marcas `<<<<<<<`, `=======` y `>>>>>>>`, se compiló y se hizo el commit `merge: integra asignacion automatica y sistema de alertas`.

```
git checkout develop
git merge --no-ff feature/Diaz-asignacion-automatica
git merge --no-ff feature/Diaz-alertas-estado
git add src/main/java/com/chimchar/skycampus/v2/AsignadorMision.java
git commit -m "merge: integra asignacion automatica y sistema de alertas"
git tag -a v2.0.0 -m "SkyCampus v2: flota autonoma, 3 tipos de drone, alertas"
git push origin v2.0.0
```

## git log --graph tras el merge

```
*   aadbd3d (HEAD -> develop) merge: integra asignacion automatica y sistema de alertas
|\  
| * a342475 (feature/Diaz-alertas-estado) feat: notifica a los observadores cuando se asigna un drone
* |   e0935a7 merge: integra asignacion automatica con validacion de peso
|\ \  
| |/  
|/|   
| * e6b9fa0 (feature/Diaz-asignacion-automatica) feat: agrega validacion de peso maximo en AsignadorMision
|/  
*   6244e71 merge: integra base de SkyCampus v2 en develop
|\  
| * 8e19ff3 (feature/Diaz-monferno-base) feat: agrega AsignadorMision base con consulta de clima y estrategia
| * f0a402a feat: agrega Observer de estado de drone con panel, log y alerta tecnica
| * c84a28a feat: agrega EstrategiaAsignacion con tres implementaciones
| * e2e88e2 feat: agrega modelo v2 con tipos de drone, prioridad y estadisticas con Streams
|/  
*   6f0623c merge: integra Chimchar completo en develop
|\  
| * ee24e17 (feature/Diaz-monferno-v2, feature/Diaz-chimchar-completo) Chimchar completo
|/  
o e0a903e (origin/develop) Adicion ValidadorCarga
```

Las dos ramas `feature/Diaz-asignacion-automatica` y `feature/Diaz-alertas-estado` salen del mismo commit y convergen en el merge final.

## merge o rebase

Se usó `merge` porque las dos ramas son compartidas y se quiere conservar la historia. `rebase` se deja para ramas locales que todavía no se han subido.
