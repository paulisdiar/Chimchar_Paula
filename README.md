# SkyCampus MVP - Chimchar

Sistema de reparto de documentos con drones de la ECI: 5 drones del mismo modelo, destinos fijos (Bloque A, B, C, D y Biblioteca) y un operador que asigna el drone a mano.

## Ejecutar

```
mvn compile exec:java -Dexec.mainClass=com.chimchar.skycampus.SkyCampus
mvn test
mvn verify
```

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
