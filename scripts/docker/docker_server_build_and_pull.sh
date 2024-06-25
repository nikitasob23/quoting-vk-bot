#!/bin/bash

. scripts/jars/build_jars.sh

echo "Building Docker image for linux/amd64 and pushing to Docker Hub"
docker build --platform linux/amd64 . -t niksob/quoting-vk-bot:quoting-vk-bot-1.0.0 --push
ssh -p 65001 root@80.242.58.161 'docker pull niksob/quoting-vk-bot:quoting-vk-bot-1.0.0'
scp -P 65001 compose-env.yml root@80.242.58.161:/root/quoting-vk-bot