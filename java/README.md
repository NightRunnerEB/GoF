# Java Modules

Each pattern lives in its own Gradle module under `creational`, `structural`, or `behavioral`. Inside every module there is a single file that holds both the pattern implementation and a `demo()` helper showing client usage.

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
    ├── iterator/            # src/main/java/IteratorExample.java
    └── visitor/             # src/main/java/VisitorExample.java
```

## Working With Examples

- Write pattern code and demo usage inside the same class file.
- Call the `demo()` method from tests, a REPL, or a tiny wrapper when you want to execute it.
- The default package keeps file paths short; add packages later if you need stronger structure.
