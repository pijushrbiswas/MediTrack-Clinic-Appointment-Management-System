# MediTrack Java Setup Instructions

## 1. Install Java (JDK + JRE)
1. Download JDK 17+ from Oracle/OpenJDK.
2. Install and verify:
   ```bash
   java -version
   javac -version
   ```

## 2. Configure JAVA_HOME
macOS example:
```bash
export JAVA_HOME=$(/usr/libexec/java_home)
export PATH="$JAVA_HOME/bin:$PATH"
```

## 3. Clone and compile project
```bash
git clone <your-repo-url>
cd MediTrack-Clinic-Appointment-Management-System
mkdir -p out
javac -d out $(find src -name "*.java")
```

## 4. Run the application
```bash
java -cp out com.airtribe.meditrack.Main
```

Run with CSV loading:
```bash
java -cp out com.airtribe.meditrack.Main --loadData
```

## 5. Run manual tests
```bash
java -cp out com.airtribe.meditrack.test.TestRunner
```

## Screenshot checklist
- `java -version` output
- `javac -version` output
- Successful compile output
- Running menu screen in terminal
