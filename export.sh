#!/usr/bin/env bash

# Exit immediately if a command exits with a non-zero status,
# if an unset variable is referenced, or if a piped command fails.
set -euo pipefail

# 1. Determine the absolute directory where this script resides
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Change to the script's directory so it always runs relative to the repo root
cd "$SCRIPT_DIR"

# 2. Quietly verify if git is installed and if this is a valid git repository
if ! command -v git &> /dev/null || ! git rev-parse --is-inside-work-tree &> /dev/null; then
    # Exit silently per requirements if git is missing or not operational
    exit 0
fi

# 3. Define output path configuration
OUTPUT_DIR="docs/llm"
OUTPUT_FILE="$OUTPUT_DIR/dump.txt"

# Temporary file to collect content safely before outputting
# This avoids partial writes or reading the file while writing to it
TEMP_DUMP="$(mktemp)"

# Ensure the temporary file is cleaned up on exit, even if interrupted
trap 'rm -f "$TEMP_DUMP"' EXIT

# 4. Gather git-tracked files, filter out the excluded directory, and concatenate
# - 'git ls-files' ensures we only look at files tracked by git
# - 'grep -v' excludes anything inside the 'docs/llm/' directory
# - 'xargs -d' safely handles filenames with spaces or special characters
git ls-files | grep -v "^${OUTPUT_DIR}/" | xargs -d '\n' cat > "$TEMP_DUMP" 2>/dev/null || true

# 5. Ensure the target directory exists
mkdir -p "$OUTPUT_DIR"

# 6. Copy the contents to the final destination
cp "$TEMP_DUMP" "$OUTPUT_FILE"

# 7. Print the entire content to stdout (the window)
cat "$OUTPUT_FILE"
