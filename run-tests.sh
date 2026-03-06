#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
MAIN_SRC="$PROJECT_ROOT/src/main/java"
TEST_SRC="$PROJECT_ROOT/src/test/java"
BIN_DIR="$PROJECT_ROOT/bin"
JUNIT_JAR="$PROJECT_ROOT/lib/junit-platform-console-standalone-1.10.2.jar"

mkdir -p "$BIN_DIR"

echo "Compiling main sources..."
find "$MAIN_SRC" -name "*.java" | xargs javac -d "$BIN_DIR"

echo "Compiling test sources..."
find "$TEST_SRC" -name "*.java" | xargs javac -cp "$BIN_DIR:$JUNIT_JAR" -d "$BIN_DIR"

echo "Running tests..."
java -jar "$JUNIT_JAR" \
  --class-path "$BIN_DIR" \
  --scan-class-path \
  --details=verbose
