# JVM Report

## 1. Class Loader Subsystem
The JVM Class Loader loads `.class` files into memory on demand using:
- **Bootstrap Class Loader**: core Java libraries.
- **Platform Class Loader**: platform libraries.
- **Application Class Loader**: project classes (like `com.airtribe.meditrack.*`).

## 2. Runtime Data Areas
- **Heap**: stores objects (e.g., `Patient`, `Doctor`, `Appointment` instances).
- **Stack**: method frames, local variables, return states for each thread.
- **Method Area**: class metadata, static variables, constant pool.
- **PC Register**: stores current executing instruction address for each thread.
- **Native Method Stack**: supports native (non-Java) method calls.

## 3. Execution Engine
The execution engine runs bytecode by:
1. Reading bytecode instructions.
2. Executing directly (interpreter) or compiling hot code to native machine code (JIT).

## 4. JIT Compiler vs Interpreter
- **Interpreter**: starts fast, executes bytecode line by line, slower for repeated code paths.
- **JIT Compiler**: identifies frequently used methods/blocks and compiles them to native code for better runtime performance.

## 5. WORA: Write Once, Run Anywhere
Java source is compiled to platform-independent bytecode (`.class`).  
Any OS with a compatible JVM can execute the same bytecode without recompiling.

## 6. JVM in MediTrack
In this project:
- Collections (`ArrayList`, `HashMap`) and entities are heap objects.
- Static initialization in `Constants` and `IdGenerator` is in method area.
- `TimerTask` reminders run on separate JVM-managed threads.
- `AtomicInteger` provides lock-free thread-safe counter updates.
