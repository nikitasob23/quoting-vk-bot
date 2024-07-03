#!/bin/bash

. scripts/jars/build_jars.sh

echo "Building Docker image for linux/amd64 and pushing to Docker Hub"
docker build --platform linux/amd64 -f config-service/Dockerfile . -t niksob/vk-bot:config-service-1.0.0 --push
docker build --platform linux/amd64 -f quoting-service/Dockerfile . -t niksob/vk-bot:quoting-service-2.0.0 --push

ssh -p 65001 root@80.242.58.161 '
  docker pull niksob/vk-bot:config-service-1.0.0
  docker pull niksob/vk-bot:quoting-service-2.0.0
'

scp -P 65001 config/config_service-prod.yml root@80.242.58.161:/root/vk-bot/config-service/config/application.yml
scp -P 65001 config/quoting_service-prod.yml root@80.242.58.161:/root/vk-bot/quoting-service/config/application.yml

scp -P 65001 docker-compose.yml root@80.242.58.161:/root/vk-bot