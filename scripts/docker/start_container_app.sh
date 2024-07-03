#!/bin/sh
export VK_JASYPT_SECRET=$(cat /run/secrets/vk_jasypt_secret)

if [ -z "$1" ]; then
  echo "Error: No JAR file path provided."
  exit 1
else
  echo "Starting Java application at $1"
  exec java -jar "$1"
fi