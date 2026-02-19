#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BIN_DIR="$PROJECT_ROOT/bin"
OUT_DIR="$PROJECT_ROOT/out"
JAR_NAME="Segatakai.jar"

mkdir -p "$BIN_DIR"
mkdir -p "$OUT_DIR"

find "$BIN_DIR" -name "*.class" -delete
javac -cp "$PROJECT_ROOT/src/main/java" -d "$BIN_DIR" $(find "$PROJECT_ROOT/src/main/java" -name "*.java")
jar cfe "$OUT_DIR/$JAR_NAME" duke.Segatakai -C "$BIN_DIR" .

echo "Created $OUT_DIR/$JAR_NAME"
