# MediTrack - Clinic Appointment Management System

MediTrack is a Java console application designed to demonstrate core and advanced Java concepts through a practical clinic appointment and billing workflow.

## Learning Coverage
- Java setup and JVM basics (paired with `docs/Setup_Instructions.md` and `docs/JVM_Report.md`)
- OOP: encapsulation, inheritance, polymorphism, abstraction
- Advanced OOP: deep cloning, immutability, enums, static initialization
- Collections, generics, comparators, iterators, equals/hashCode
- Exception handling with custom exceptions and chaining
- File I/O with CSV parsing and serialization utilities
- Concurrency intro with `TimerTask`, synchronization, `AtomicInteger`
- Design patterns: Singleton, Factory, Strategy, Template Method, Observer
- Java 8+ streams and lambdas
- Manual testing via `TestRunner`

## Project Structure
Base package: `com.airtribe.meditrack`

```text
src/com/airtribe/meditrack
├── Main.java
├── constants
├── entity
├── enums
├── exception
├── factory
├── interfaces
├── observer
├── service
├── strategy
├── test
└── util
```

> Note: Java package name `interface` is invalid (keyword), so `interfaces` is used.

## Setup
See detailed setup with screenshot checklist in:
- `docs/Setup_Instructions.md`

## Build & Run
```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out com.airtribe.meditrack.Main
```

Run with data loading:
```bash
java -cp out com.airtribe.meditrack.Main --loadData
```

Run manual tests:
```bash
java -cp out com.airtribe.meditrack.test.TestRunner
```

## Key Features
1. Patient/Doctor CRUD-style add/list/search flows
2. Appointment create/view/cancel with `AppointmentStatus`
3. Billing with tax and strategy-based calculation
4. Observer notifications for appointment events
5. Streams analytics (average fee, appointments per doctor)
6. AI-like rule-based specialization suggestion
7. CSV persistence through `CSVUtil`

## Documentation
- JVM internals report: `docs/JVM_Report.md`
- Setup instructions: `docs/Setup_Instructions.md`
