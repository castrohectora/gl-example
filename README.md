# gl-example

Para IntelliJIDEa abrir el proyecto y seleccionar las siguientes opciones:

- Use auto-import
- Create directories for empty content roots automatically
- Create separate module per source set

finalmente

- Use default gradle wrapper

## Estructura del proyecto

```
root
├── README.md
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
├── src
│   └── main
│       ├── java
│       └── resources
└── src
    └── test
        ├── groovy
        ├── java
        └── resources
```

## Requerimientos

- Java 8
- Gradle 7.2

## Ejecución

Para MacOS y Linux

`` ./gradlew bootRun ``  

o para Windows

`` gradlew.bat bootRun``

## Ejecución tests

Para MacOS y Linux

`` ./gradlew test ``  

para Windows

`` gradlew.bat test``
