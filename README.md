# Test Automation Demo - Java

Ez a projekt bemutatja a teszt automatizálást Java nyelven, különböző tesztelési típusokkal, mint API és Selenium tesztek. A tesztek futtatásához Maven és GitHub Actions CI/CD használatával készült.

## Futtatási módok

A tesztek futtatása háromféleképpen lehetséges:

- **API tesztek**: `mvn test -DtestType=api`
- **Selenium tesztek**: `mvn test -DtestType=selenium`
- **Minden teszt** (API és Selenium is): `mvn test`
- **GitHub Actions**: Tesztek futtatása a GitHub Actions CI/CD pipeline-ján keresztül (lásd alább).

### Lokális futtatás

A lokális futtatáshoz a Maven parancsok használhatók. A `runMode` értéke alapértelmezetten **Local**, tehát ha lokálisan futtatod a teszteket, a riport a "Local" értéket fogja mutatni.

#### Parancsok:

- API tesztek futtatása:

```bash
mvn test -DtestType=api
Selenium tesztek futtatása (headless mód nélkül):

bash
Kód másolása
mvn test -DtestType=selenium
Selenium tesztek futtatása headless módban:

bash
Kód másolása
mvn test -DtestType=selenium -Dheadless=true
GitHub Actions CI
A GitHub Actions segítségével a tesztek futtathatók CI környezetben. A teszt futtatása előtt választható, hogy headless módban futtassuk a Selenium teszteket. A GitHub Actions workflow-ban választható, hogy melyik teszteket futtassuk és ha headless módban legyenek-e.

A runMode értéke CI lesz, ha a tesztet a GitHub Actions pipeline futtatja. A tesztek futásának eredményei az Actions logs-ban és a riportokban is nyomon követhetők.

GitHub Actions Workflow Trigger
A GitHub Actions workflow automatikusan triggerelődik a következő események esetén:

Push a main branch-re

Pull Request a main branch-re

Manuális futtatás (workflow_dispatch)

A manuális futtatáskor beállítható a következő bemeneti paraméterek:

testType: Melyik tesztet futtassuk? (api, selenium, all)

headlessMode: Futtassuk headless módban? (true, false)

Példa futtatás:
yaml
Kód másolása
on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - main
  workflow_dispatch:
    inputs:
      testType:
        description: 'Melyik teszteket futtassuk? (api, selenium, all)'
        required: true
        default: 'all'
      headlessMode:
        description: 'Futtassuk headless módban? (true/false)'
        required: true
        default: 'false'
Riportok
A teszt futtatások eredményei a következő könyvtárakba kerülnek:

API tesztek: target/apiReports

Selenium tesztek: target/seleniumReports

Általános teszt eredmények: target/reports

A riportok minden futtatás után létrejönnek, és tartalmazzák a tesztek sikerességét, illetve a hibákat. A GitHub Actions futtatások során a riportokban az Execution Mode értéke CI lesz, míg a lokális futtatásnál Local.