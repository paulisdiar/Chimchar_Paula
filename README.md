# SkyCampus - Chimchar y Monferno

Sistema de reparto de documentos con drones de la ECI: 5 drones del mismo modelo, destinos fijos (Bloque A, B, C, D y Biblioteca) y un operador que asigna el drone a mano.

El paquete `com.chimchar.skycampus` es el MVP (Chimchar). El paquete `com.chimchar.skycampus.v2` es SkyCampus v2 (Monferno): 20 drones de 3 tipos, asignación automática con Strategy, notificaciones con Observer y consulta de clima.

## Ejecutar

```
mvn compile exec:java -Dexec.mainClass=com.chimchar.skycampus.SkyCampus
mvn test
mvn verify
```

`mvn verify` falla si la cobertura baja de 80% en líneas o de 70% en ramas.

El reporte de cobertura queda en `target/site/jacoco/index.html`.

Para SonarQube:

```
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community
mvn verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=TU_TOKEN
```

## Estructura

| Reto | Dónde está |
|---|---|
| 01 Streams | `RetoStreams` |
| 02 GitFlow | ramas `main`, `develop` y `feature/Diaz-*` |
| 03 Patrones | `MisionBuilder`, `Validador*`, `EstrategiaAsignacion` y `docs/03-patrones.md` |
| 04 SOLID | `AsignadorMision`, `RepositorioMision`, `AlertaOperador`, `GeneradorReporte`, `EstrategiaRuta` y `docs/04-solid.md` |
| 05 Contexto C4 | `docs/05-diagrama-contexto-c4.png` |
| 06 RF y RNF | `docs/06-requerimientos.md` |
| 07 Plantilla DOSW | `docs/07-plantilla-dosw.md` |
| 08 Identidad y UX | `docs/08-identidad-ux.md` |
| 09 Jira | `docs/09-jira.md` |
| 10 Casos de uso | `docs/10-diagrama-casos-de-uso.png` |
| 11 Mocks | `docs/11-mocks.md` y `docs/mock/` |
| 12 TDD | `ValidadorMision` y `src/test/java` |
| 13 JaCoCo | `pom.xml` |

## Monferno

| Reto | Dónde está |
|---|---|
| 01 Streams | `v2/EstadisticasMisiones` |
| 02 GitFlow | `docs/monferno/02-gitflow.md` y el tag `v2.0.0` |
| 03 Patrones | `v2/EstrategiaAsignacion`, `v2/ObservadorDrone` y `docs/monferno/03-patrones.md` |
| 04 SOLID | `docs/monferno/04-solid.md` y `v2/GestorMisionesTest` |
| 05 Contexto | `docs/monferno/05-contexto.md` |
| 06 RF y RNF | `docs/monferno/06-requerimientos.md` |
| 07 Plantilla DOSW | `docs/monferno/07-plantilla-dosw.md` |
| 08 Sistema de diseño | `docs/monferno/08-sistema-diseno.md` |
| 09 Agilismo | `docs/monferno/09-agilismo.md` |
| 10 Casos de uso | `docs/monferno/10-casos-de-uso.md` |
| 11 Mocks | `docs/monferno/11-mocks.md` y `docs/monferno/mock/` |
| 12 TDD | `v2/AsignadorMision` y `src/test/java/com/chimchar/skycampus/v2` |
| 13 Quality Gate | `pom.xml` y `docs/monferno/13-quality-gate.md` |
