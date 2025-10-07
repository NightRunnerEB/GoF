# Java Modules

Java-проект организован как многомодульный Gradle-проект. Каждое семейство паттернов располагается в отдельной группе (`creational`, `structural`, `behavioral`), а конкретный паттерн — в модуле с именем в `kebab-case`.

## Структура

```
java/
├── build.gradle.kts        # Общие настройки (Java 21, JUnit 5, AssertJ)
├── settings.gradle.kts     # Список модулей
├── creational/
│   └── factory-method/
├── structural/
│   └── adapter/
└── behavioral/
    └── observer/
```

## Запуск тестов

По умолчанию ожидется наличие Gradle Wrapper. Создайте его один раз, установив Gradle локально и выполнив:

```bash
cd java
gradle wrapper
./gradlew test
```

После генерации wrapper-а тесты можно запускать командой `./gradlew test`.

## Добавление нового паттерна

1. Сгенерируйте каркас: `./scripts/new_java_pattern.sh <category> <pattern>`.
2. Добавьте модуль в `settings.gradle.kts` через `include("<category>:<pattern>")`.
3. Реализуйте код паттерна, подготовьте README и тесты.
