# Monferno 13 y 14 - Quality Gate

## Configurado

La regla `check` de JaCoCo en el `pom.xml` hace fallar `mvn verify` si la cobertura baja de:

| Contador | Mínimo |
|---|---|
| LINE | 80% |
| BRANCH | 70% |

## Medido con JaCoCo (0.8.11, pruebas ejecutadas con JUnit 5 y Mockito)

| Contador | Resultado |
|---|---|
| LINE | 89.4% (491 de 549) |
| BRANCH | 81.8% (130 de 159) |

Las 65 pruebas pasan.

## SonarCloud

Organización `paulisdiar`, proyecto `paulisdiar_Chimchar_Paula`, Quality Gate "Sonar way".

Primer análisis de la rama `main` (392 líneas de código, solo el código base):

| Medida | Resultado |
|---|---|
| Security | 0 issues, calificación A |
| Reliability | 0 issues, calificación A |
| Maintainability | 57 issues, calificación A |
| Duplications | 0.0% |
| Coverage | sin datos todavía |
| Quality Gate | no calculado |

Cobertura y Quality Gate quedan sin calcular porque ese análisis fue automático y no recibe el reporte de JaCoCo. El workflow `.github/workflows/sonar.yml` ejecuta `mvn verify` y `sonar:sonar` con el secreto `SONAR_TOKEN` en cada push a `main` y `develop`. Las capturas del dashboard antes y después se toman con el primer análisis de ese workflow.
