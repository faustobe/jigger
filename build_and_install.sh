#!/bin/bash

echo "========================================="
echo "Jigger - Build e Install APK"
echo "========================================="
echo ""

# Vai nella directory del progetto
cd "$(dirname "$0")"

# 0. Verifica/scarica Gradle Wrapper
if [ ! -f "gradlew" ]; then
    echo "0. Creazione Gradle Wrapper..."

    # Crea gradlew
    cat > gradlew << 'GRADLEW'
#!/bin/sh
# Gradle wrapper script

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD="java"
fi

# Setup the classpath
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Execute Gradle
exec "$JAVACMD" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
GRADLEW
    chmod +x gradlew

    # Scarica gradle-wrapper.jar
    WRAPPER_JAR="gradle/wrapper/gradle-wrapper.jar"
    if [ ! -f "$WRAPPER_JAR" ]; then
        echo "   Scaricamento gradle-wrapper.jar..."
        mkdir -p gradle/wrapper
        curl -sL "https://github.com/gradle/gradle/raw/v8.2.0/gradle/wrapper/gradle-wrapper.jar" -o "$WRAPPER_JAR" 2>/dev/null
        if [ ! -f "$WRAPPER_JAR" ] || [ ! -s "$WRAPPER_JAR" ]; then
            # Metodo alternativo: usa gradle init
            echo "   Uso gradle locale per creare wrapper..."
            if command -v gradle &> /dev/null; then
                gradle wrapper --gradle-version 8.2
            else
                echo "❌ Gradle non trovato. Installa Gradle o scarica manualmente il wrapper."
                echo "   sudo apt install gradle"
                exit 1
            fi
        fi
    fi
    echo "✓ Gradle Wrapper creato"
    echo ""
fi

# 1. Verifica connessione ADB
echo "1. Verifica dispositivi connessi..."
if ! command -v adb &> /dev/null; then
    echo "❌ ADB non trovato!"
    echo "   Installa Android SDK Platform Tools:"
    echo "   sudo apt install android-sdk-platform-tools"
    exit 1
fi

DEVICES=$(adb devices | grep -w "device$" | awk '{print $1}')

if [ -z "$DEVICES" ]; then
    echo "❌ Nessun dispositivo Android connesso!"
    echo "   Collega il dispositivo e abilita USB debugging"
    echo "   Oppure avvia un emulatore"
    exit 1
fi

DEVICE_COUNT=$(echo "$DEVICES" | grep -c .)
echo "✓ Trovati $DEVICE_COUNT dispositivo/i:"
for device in $DEVICES; do
    MODEL=$(adb -s "$device" shell getprop ro.product.model 2>/dev/null | tr -d '\r')
    echo "   - $device ($MODEL)"
done
echo ""

# 2. Verifica ANDROID_HOME
if [ -z "$ANDROID_HOME" ]; then
    # Prova percorsi comuni
    if [ -d "$HOME/Android/Sdk" ]; then
        export ANDROID_HOME="$HOME/Android/Sdk"
    elif [ -d "/usr/lib/android-sdk" ]; then
        export ANDROID_HOME="/usr/lib/android-sdk"
    else
        echo "⚠ ANDROID_HOME non impostato. Provo a continuare..."
    fi
fi

if [ -n "$ANDROID_HOME" ]; then
    echo "   ANDROID_HOME: $ANDROID_HOME"
fi

# 3. Build APK
echo ""
echo "2. Compilazione APK (potrebbe richiedere alcuni minuti)..."
echo ""

chmod +x gradlew 2>/dev/null
./gradlew assembleDebug --stacktrace

if [ $? -ne 0 ]; then
    echo ""
    echo "❌ Errore durante la compilazione!"
    echo ""
    echo "Possibili soluzioni:"
    echo "1. Verifica che JAVA_HOME punti a JDK 17+"
    echo "   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64"
    echo "2. Verifica ANDROID_HOME"
    echo "   export ANDROID_HOME=\$HOME/Android/Sdk"
    echo "3. Accetta le licenze Android SDK:"
    echo "   \$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --licenses"
    exit 1
fi

echo ""
echo "✓ APK compilato"
echo ""

# 4. Trova APK
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
if [ ! -f "$APK_PATH" ]; then
    echo "❌ APK non trovato in $APK_PATH"
    exit 1
fi

APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
echo "   APK: $APK_PATH ($APK_SIZE)"
echo ""

# 5. Installa su tutti i dispositivi
PACKAGE_NAME="it.faustobe.jigger"

echo "3. Installazione su tutti i dispositivi..."
echo ""

INSTALL_SUCCESS=0
INSTALL_FAILED=0

for device in $DEVICES; do
    MODEL=$(adb -s "$device" shell getprop ro.product.model 2>/dev/null | tr -d '\r')
    echo "📱 Dispositivo: $device ($MODEL)"

    # Disinstalla versione precedente (ignora errori)
    adb -s "$device" uninstall "$PACKAGE_NAME" 2>/dev/null >/dev/null

    # Installa APK
    echo "   Installazione in corso..."
    INSTALL_OUTPUT=$(adb -s "$device" install -r "$APK_PATH" 2>&1)

    if echo "$INSTALL_OUTPUT" | grep -q "Success"; then
        echo "   ✓ Installazione completata"
        INSTALL_SUCCESS=$((INSTALL_SUCCESS + 1))

        # Avvia l'app
        echo "   Avvio app..."
        adb -s "$device" shell am start -n "$PACKAGE_NAME/.ui.shift.ShiftActivity" 2>/dev/null
    else
        echo "   ❌ Errore installazione"
        echo "   $INSTALL_OUTPUT"
        INSTALL_FAILED=$((INSTALL_FAILED + 1))
    fi
    echo ""
done

echo "========================================="
if [ $INSTALL_FAILED -eq 0 ]; then
    echo "✓ Jigger installato su tutti i $INSTALL_SUCCESS dispositivi!"
else
    echo "⚠ Installazione completata su $INSTALL_SUCCESS/$DEVICE_COUNT dispositivi"
    echo "  ($INSTALL_FAILED falliti)"
fi
echo "========================================="
echo ""
