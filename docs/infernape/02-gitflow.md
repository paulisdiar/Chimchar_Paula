# Infernape 02 - Release v3.0.0 y hotfix v3.0.1

Se simuló con GitFlow real en este repositorio. Todos los commits usan commits convencionales.

## Pasos

| Paso | Qué se hizo |
|---|---|
| 1 | Se integró `feature/Diaz-infernape` en `develop` con `--no-ff`. |
| 2 | Se creó `release/v3.0.0` desde `develop` con 2 commits de preparación: `chore(release): sube la versión del pom a 3.0.0` y `docs(release): agrega notas de la versión 3.0.0`. |
| 3 | `release/v3.0.0` se mergeó a `main` con `--no-ff` y se etiquetó `v3.0.0`. |
| 4 | `release/v3.0.0` se mergeó de vuelta a `develop` y la rama de release se borró. |
| 5 | Se creó `hotfix/v3.0.1` desde `main` para un error crítico: una distancia de 0 devolvía una ruta vacía y la misión quedaba sin etapas. El commit `fix(ruta): rechaza distancias no positivas al planificar` agrega la validación y su prueba. |
| 6 | `chore(release): version 3.0.1` sube la versión y el changelog. |
| 7 | `hotfix/v3.0.1` se mergeó a `main` (etiqueta `v3.0.1`) y de vuelta a `develop`. |

## git log --graph final

```
*   58069e8 (HEAD -> develop) merge: hotfix v3.0.1 de vuelta a develop
|\  
| * 4ef280d chore(release): version 3.0.1
| * c4b082f fix(ruta): rechaza distancias no positivas al planificar
| *   5dc4eb3 (tag: v3.0.0) merge: release v3.0.0 a main
| |\  
* | \   76bb3fd merge: release v3.0.0 de vuelta a develop
|\ \ \  
| | |/  
| |/|   
| * | e3e67a9 docs(release): agrega notas de la versión 3.0.0
| * | 0c78de9 chore(release): sube la versión del pom a 3.0.0
|/ /  
* |   c606337 merge: integra Infernape completo en develop
|\ \  
| * | c9f4977 (feature/Diaz-infernape) Infernape completo
|/ /  
* | 7b88008 (origin/develop) Monferno completo
* | 14a4ad6 docs: claves de Jira en reto 09 de Chimchar y Monferno
* |   d5c2807 (tag: v2.0.0) merge: integra pruebas, quality gate y documentacion de Monferno
|\ \  
| * | 33ff5df (origin/feature/Diaz-monferno-calidad, feature/Diaz-monferno-calidad) docs: agrega diagramas de contexto y casos de uso v2 y el flujo de mocks de Monferno
| * | 8addc41 docs: agrega documentos de Monferno (gitflow, patrones, SOLID, requerimientos, DOSW, diseno, agilismo, casos de uso, mocks y quality gate)
```
