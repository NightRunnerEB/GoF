# Java Modules

The Java workspace uses Gradle in a multi-project setup. Each pattern sits in its own module under `creational`, `structural`, or `behavioral`, and you keep the entire example (implementation plus client) inside a single file within that module.

## Layout

```
java/
├── build.gradle.kts
├── settings.gradle.kts
├── creational/
│   └── factory-method/      # src/main/java/FactoryMethodExample.java
├── structural/
│   └── adapter/             # src/main/java/AdapterExample.java
└── behavioral/
    ├── observer/            # src/main/java/ObserverExample.java
    └── iterator/            # src/main/java/Main.java
```

## Running Tests

Generate the Gradle Wrapper once, then use it for builds and tests (add test sources later if you need them):

```bash
cd java
gradle wrapper
./gradlew test
```

Run the single-file example from your IDE or wire the Gradle application plugin when you are ready to execute it from the command line. Each example lives in the default package to keep the path short.

## Adding A New Pattern

1. Create a folder under the relevant category (for example `java/structural/composite`).
2. Add the module to `settings.gradle.kts` with `include("<category>:<pattern>")`.
3. Create `src/main/java/.../PatternExample.java` and keep both implementation and usage inside that file.
