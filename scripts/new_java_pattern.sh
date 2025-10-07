#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 2 ]]; then
  echo "Usage: $0 <category> <pattern-slug>" >&2
  echo "Categories: creational | structural | behavioral" >&2
  exit 1
fi

category=$1
pattern_slug=$2
case "$category" in
  creational|structural|behavioral) ;;
  *)
    echo "Unknown category: $category" >&2
    exit 1
    ;;
esac

module_path="java/${category}/${pattern_slug}"
package_dir="org/example/gof/${category}/${pattern_slug//-/_}"

if [[ -d "$module_path" ]]; then
  echo "Module already exists at $module_path" >&2
  exit 1
fi

mkdir -p "$module_path/src/main/java/${package_dir}" \
         "$module_path/src/test/java/${package_dir}"

cat <<KTS > "$module_path/build.gradle.kts"
description = "GoF ${pattern_slug//-/ } pattern"
KTS

cat <<JAVA > "$module_path/src/main/java/${package_dir}/PackageInfo.java"
package org.example.gof.${category}.${pattern_slug//-/_};

/**
 * TODO: Добавьте описание паттерна ${pattern_slug//-/ }.
 */
public final class PackageInfo {
    private PackageInfo() {
    }
}
JAVA

cat <<JAVA > "$module_path/src/test/java/${package_dir}/PlaceholderTest.java"
package org.example.gof.${category}.${pattern_slug//-/_};

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PlaceholderTest {

    @Test
    @Disabled("Replace with real tests")
    void todo() {
    }
}
JAVA

echo "Module scaffold created at $module_path"
echo "Remember to add \"${category}:${pattern_slug}\" to java/settings.gradle.kts"
