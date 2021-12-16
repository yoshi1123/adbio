#!/usr/bin/env bash
set -e

shafile="gradle-6.7.1-wrapper.jar.sha256"
echo "retrieving https://services.gradle.org/distributions/gradle-6.7.1-wrapper.jar.sha256"
curl --silent --location --output "$shafile" \
     "https://services.gradle.org/distributions/gradle-6.7.1-wrapper.jar.sha256"
echo "  gradle/wrapper/gradle-wrapper.jar" >> "$shafile"
echo "> cat ./gradle-6.7.1-wrapper.jar.sha256"
cat "$shafile"
echo
echo "> sha256sum --check ./$shafile"
sha256sum --check "$shafile"
