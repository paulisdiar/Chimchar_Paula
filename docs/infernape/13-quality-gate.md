# Infernape 13 y 14 - Quality Gate de Enterprise

## Estándares configurados

| Métrica | MVP | v2 | Enterprise | Dónde se exige |
|---|---|---|---|---|
| Line coverage | 80 % | 80 % | 85 % o más | regla `check` de JaCoCo en el `pom.xml` |
| Branch coverage | no aplica | 70 % | 75 % o más | regla `check` de JaCoCo en el `pom.xml` |
| Bugs | 0 | 0 | 0 | Quality Gate de SonarCloud |
| Vulnerabilidades | 0 | 0 | 0 | Quality Gate de SonarCloud |
| Deuda técnica | 0 | menos de 30 min | menos de 15 min | Quality Gate de SonarCloud |
| Duplicación | no aplica | menos del 5 % | menos del 3 % | Quality Gate de SonarCloud |

## Medido localmente con JaCoCo

LINE 90.5 % y BRANCH 85.9 %, por encima de los mínimos de Enterprise.

## Pendiente

El análisis de SonarCloud sobre el sistema Enterprise (deuda técnica por módulo `PlanificadorRuta`, `GestorZonas` y `MonitorFlota`, issues críticos con su causa raíz y commit de corrección, y el Quality Gate final en verde) se completa cuando corra el workflow `.github/workflows/sonar.yml` con el secreto `SONAR_TOKEN`. El primer análisis automático de la rama `main` mostró 0 vulnerabilidades, 0 bugs, 57 issues de mantenibilidad y 0.0 % de duplicación sobre el código base, sin cobertura. No se anotan cifras del sistema Enterprise hasta tener el análisis real.
