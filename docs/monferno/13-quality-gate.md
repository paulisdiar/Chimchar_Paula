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
| LINE | 92.2% (506 de 549) |
| BRANCH | 88.7% (141 de 159) |

Las 70 pruebas pasan.

## Pendiente

El análisis con SonarQube (0 bugs, 0 vulnerabilidades, deuda técnica de 30 min o menos, Quality Gate en verde y las capturas del dashboard antes y después) no se ha ejecutado, porque requiere una instancia de SonarQube. Los comandos están en el `README.md` y las propiedades `sonar.*` ya están en el `pom.xml`.
